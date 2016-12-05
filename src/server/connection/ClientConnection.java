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

	ClientConnection(
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

		String commandName = this.extractCommandName(commandString);
		ArrayList<String> parameters = this.extractCommandParameters(commandString);

		this.print(commandName + " wird ausgeführt. Parameter: " + parameters.toString());

		try {
			this.commandFactory.createCommand(
					this,
					commandName,
					parameters
			).execute();
		} catch (Exception e) {
			this.applicationState.getOutput()
			                     .println("Fehler: " + commandName + " konnte nicht richtig ausgeführt werden!");
		}
	}

	/**
	 * Gibt eine Nachricht aus.
	 *
	 * @param msg - Nachricht die ausgegeben werden soll.
	 */
	private void print(String msg) {
		this.applicationState.getOutput().println(msg);
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
	 * TODO: Methode implementieren
	 *
	 * @param commandString - String mit vollständiger Definition des Kommandos
	 *
	 * @return Der Name der Kommando-Klasse
	 */
	private String extractCommandName(String commandString) {

		return "";
	}

	/**
	 * Extrahiert die Parameter aus dem übergebenen String
	 * TODO: Methode implementieren
	 *
	 * @param commandString - String mit vollständiger Definition des Kommandos
	 */
	private ArrayList<String> extractCommandParameters(String commandString) {

		return new ArrayList<String>();
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
