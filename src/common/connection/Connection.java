package common.connection;

import common.ApplicationState;
import common.CommandFactory;
import common.commands.CommandInterface;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Connection - Managed die Verbindung zu einem Spieler
 *
 * @author Nils Daniel Wittwer
 */
public class Connection extends Thread {

    private final PrintStream output;
    private BufferedReader inputStream;
    private PrintWriter outputStream;
    private String playerName = "Default Player Name";
    private ApplicationState applicationState;
    private CommandFactory commandFactory;

    public Connection(
            ApplicationState applicationState,
            CommandFactory commandFactory,
            PrintStream output,
            Socket socket
    ) throws IOException {

        this.applicationState = applicationState;
        this.commandFactory = commandFactory;
        this.output = output;
        this.inputStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.outputStream = new PrintWriter(socket.getOutputStream());


    }

    public void run() {

        try {
            while (!currentThread().isInterrupted()) {
                this.applicationState.getOutput().println("Connection.processCommand(): Warte auf Kommando");
                this.processCommand();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Führt im Input-Stream vorkommende Kommandos aus.
     */
    private void processCommand() throws IOException {

        String commandString = this.readCommandString();
        this.output.println(
                "Connection.processCommand(): Neues Kommando erkannt: \""
                        + commandString
                        + "\""
        );

        String commandName = this.extractCommandName(commandString);

        if (null == commandString) {
            this.output.println(
                    "Connection.process(): Fehler: Kommandoname konnte nicht erkannt werden."
            );
            return;
        }

        ArrayList<String> parameters = this.extractCommandParameters(commandString);

        try {
            this.output.println(
                    "Connection.process(): " + commandName + " wird ausgeführt. Parameter: " + parameters
                            .toString()
            );
            this.commandFactory.createCommand(
                    this,
                    commandName,
                    parameters
            ).execute();
        } catch (Exception e) {
            this.output.println(
                    "Connection.process(): Fehler: \""
                            + commandName
                            + "\" konnte nicht "
                            + "richtig ausgeführt werden!"
            );
        }
    }

    /**
     * Liest einen vollständigen Kommando-String aus dem Input-Stream ein.
     *
     * @return Der vollständinge Kommand-String
     * @throws IOException Falls ein Fehler beim Lesen des Input-Streams auftritt.
     */
    private String readCommandString() throws IOException {

        String commandString = this.inputStream.readLine();

        while (!commandString.endsWith("</command>")) {
            commandString = commandString.concat(this.inputStream.readLine());
        }

        return commandString;
    }

    /**
     * Extrahiert den Namen des Kommandos aus dem übergebenen String
     *
     * @param commandString - String mit vollständiger Definition des Kommandos
     * @return Der Name der Kommando-Klasse
     */
    private String extractCommandName(String commandString) {

        Pattern pattern = Pattern.compile("(?i)<command.*name=\"(?<commandName>.*)\".*>");
        Matcher matcher = pattern.matcher(commandString);
        if (matcher.find()) {
            return matcher.group("commandName");
        }
        return null;
    }

    /**
     * Extrahiert die Parameter aus dem übergebenen String
     *
     * @param commandString - String mit vollständiger Definition des Kommandos
     */
    private ArrayList<String> extractCommandParameters(String commandString) {

        Pattern pattern = Pattern.compile("(?i)<parameter>(?<parameterValue>.*)</parameter>");
        Matcher matcher = pattern.matcher(commandString);

        ArrayList<String> parameters = new ArrayList<String>();
        while (matcher.find()) {
            parameters.add(matcher.group("parameterValue"));
        }

        return parameters;
    }

    /**
     * Sendet ein Kommando
     *
     * @param command - Das Kommando die gesendet werden soll
     */
    public void send(CommandInterface command) {
        outputStream.print(command.serialize());
        outputStream.flush();
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }
}
