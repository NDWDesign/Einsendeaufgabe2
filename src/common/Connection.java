package common;

import common.Commands.CommandInterface;
import common.Commands.CommandParserInterface;

import java.io.*;
import java.net.Socket;
import java.rmi.server.UID;
import java.util.ArrayList;

/**
 * Connection - Managed die Verbindung zu einem Spieler
 *
 * @author Nils Daniel Wittwer
 */
public class Connection extends Thread {

    private final PrintStream output;
    private final UID uid;
    private final CommandParserInterface commandParser;
    private BufferedReader inputStream;
    private PrintWriter outputStream;
    private String playerName = "Default Player Name";
    private ApplicationState applicationState;
    private CommandFactory commandFactory;

    public Connection(
            ApplicationState applicationState,
            CommandFactory commandFactory,
            CommandParserInterface commandParser,
            PrintStream output,
            Socket socket
    ) throws IOException {
        this.uid = new UID();
        this.applicationState = applicationState;
        this.commandFactory = commandFactory;
        this.commandParser = commandParser;
        this.output = output;
        this.inputStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.outputStream = new PrintWriter(socket.getOutputStream());
    }

    public void run() {

        this.output.println("Connection[" + this.uid + "].run(): Warte auf Kommando...");

        while (!this.isInterrupted()) {
            try {
                while (!this.commandParser.detectCommand(this.inputStream.readLine()) && !this.isInterrupted()) {
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (this.commandParser.commandDetected()){

                this.output.println(
                        "Connection.run(): Kommando erkannt"
                );
                this.processCommand(
                        this.commandParser.getCommandName(),
                        this.commandParser.getCommandParameters()
                );
            }
        }
    }

    /**
     * Führt im Input-Stream vorkommende Kommandos aus.
     */
    private void processCommand(String commandName, ArrayList<String> parameters) {

        this.output.println(
                "Connection[" + this.uid + "].processCommand(): \""
                        + commandName
                        + "\" wird ausgeführt. Parameter: "
                        + parameters.toString()
        );

        try {
            this.commandFactory.createCommand(commandName)
                    .setConnection(this)
                    .setParameters(parameters)
                    .run();

        } catch (Exception e) {
            this.output.println(
                    "Connection[" + this.uid + "].processCommand(): Fehler! \""
                            + commandName
                            + "\" konnte nicht "
                            + "richtig ausgeführt werden ("
                            + e.getClass().getSimpleName() + ": " + e.getMessage() + "\n"
                            + ")"
            );
            e.printStackTrace(this.output);
        }
    }

    /**
     * Sendet ein Kommando
     *
     * @param command - Das Kommando das gesendet werden soll
     */
    public void send(CommandInterface command) {
        this.output.println(
                "Connection[" + this.uid + "].send(): Sende Kommando \""
                        + command.getClass().getSimpleName()
                        + "\""
        );
        outputStream.println(command.serialize());
        outputStream.flush();
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {

        this.output.println(
                "Connection[" + this.uid + "].setPlayerName: Setze Spielernamen \""
                        + playerName
                        + "\""
        );
        this.playerName = playerName;
    }

    public UID getUid() {
        return uid;
    }
}
