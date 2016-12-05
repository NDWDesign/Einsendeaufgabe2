package server.connection;

import common.ApplicationState;
import common.CommandFactory;
import common.commands.CommandInterface;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ClientConnection - Managed die Verbindung zu einem Spieler
 *
 * @author Nils Daniel Wittwer
 */
public class ClientConnection extends Thread {

	private BufferedReader input;

	private PrintWriter output;

	private String playerName = "Default Player Name";

	private ApplicationState applicationState;

	private CommandFactory commandFactory;

	public ClientConnection(
			ApplicationState applicationState,
			CommandFactory commandFactory,
			Socket socket
	) throws IOException {

		this.input = new BufferedReader(new InputStreamReader(socket.getInputStream()));

		this.output = new PrintWriter(socket.getOutputStream());

		this.commandFactory = commandFactory;

		this.applicationState = applicationState;
	}

	public void run() {

		try {
			while (!currentThread().isInterrupted()) {
				this.applicationState.getOutput().println("ClientConnection.processCommand(): Warte auf Kommando");
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
		this.applicationState.getOutput().println(
				"ClientConnection.processCommand(): Neues Kommando erkannt: \""
						+ commandString
						+ "\""
		);

		String commandName = this.extractCommandName(commandString);

		if (null == commandString) {
			this.applicationState.getOutput().println(
					"ClientConnection.process(): Fehler: Kommandoname konnte nicht erkannt werden."
			);
			return;
		}

		ArrayList<String> parameters = this.extractCommandParameters(commandString);

		try {
			this.applicationState.getOutput().println(
					"ClientConnection.process(): " + commandName + " wird ausgeführt. Parameter: " + parameters
					.toString()
			);
			this.commandFactory.createCommand(
					this,
					commandName,
					parameters
			).execute();
		} catch (Exception e) {
			this.applicationState.getOutput().println(
					"ClientConnection.process(): Fehler: \""
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
	 *
	 * @throws IOException Falls ein Fehler beim Lesen des Input-Streams auftritt.
	 */
	private String readCommandString() throws IOException {

		String commandString = this.input.readLine();

		while (!commandString.endsWith("</command>")) {
			commandString = commandString.concat(this.input.readLine());
		}

		return commandString;
	}

	/**
	 * Extrahiert den Namen des Kommandos aus dem übergebenen String
	 *
	 * @param commandString - String mit vollständiger Definition des Kommandos
	 *
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
		output.print(command.serialize());
		output.flush();
	}

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}
}
