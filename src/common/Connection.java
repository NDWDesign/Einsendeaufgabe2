package common;

import common.Commands.CommandFactory;
import common.Commands.CommandInterface;
import common.Commands.CommandParserInterface;
import common.Commands.Disconnect;
import common.Loggers.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.rmi.server.UID;
import java.util.HashMap;

/**
 * Connection - Managed die Verbindung zu einem Spieler
 *
 * @author Nils Daniel Wittwer
 */
public class Connection extends Thread {

	private final Logger logger;
	private final UID uid;
	private final CommandParserInterface commandParser;
	private BufferedReader inputStream;
	private PrintWriter outputStream;
	private String playerName = "Default Player Name";
	private CommandFactory commandFactory;
	private Long latency;

	public Connection(
			CommandFactory commandFactory,
			CommandParserInterface commandParser,
			Logger logger,
			Socket socket
	) throws IOException {
		this.uid = new UID();
		this.commandFactory = commandFactory;
		this.commandParser = commandParser;
		this.logger = logger;
		this.inputStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		this.outputStream = new PrintWriter(socket.getOutputStream());
	}

	public void run() {
		// Todo Prüfen ob Kommandos gesendet werden können während auf Eingaben gewartet wird.
		while (!this.isInterrupted()) {
			this.logger.println("Warte auf Kommando...", this.uid);
			try {
				while (!this.commandParser.detectCommand(this.inputStream.readLine()) && !this.isInterrupted()) {
				}
			} catch (Exception e) {
				this.logger.println("Fehler! Beende Thread...", this.uid);
				this.interrupt();
			}
			if (this.commandParser.commandDetected()) {

				this.logger.println(
						"Kommando erkannt"
				);
				this.processCommand(
						this.commandParser.getCommandName(),
						this.commandParser.getCommandParameters()
				);
			}
			else {
				this.logger.println("Kein Kommando erkannt.");
			}
			this.commandParser.flush();
		}
		this.logger.println("Thread unterbrochen, sende Disconnect Kommando...");
		this.send(new Disconnect(this.logger));
	}

	/**
	 * Führt im Input-Stream vorkommende Kommandos aus.
	 */
	private void processCommand(String commandName, HashMap<String, String> parameters) {

		this.logger.println(
				"Connection[" + this.uid + "].processCommand(): \""
						+ commandName
						+ "\" wird ausgeführt",
				parameters.toString()
		);

		try {

			// Todo: Prüfen ob Kommandos als Thread ausgeführt werden können.
			this.commandFactory.createCommand(commandName)
			                   .loadParameters(parameters)
			                   .setConnection(this)
			                   .run();

		} catch (Exception e) {
			this.logger.println(
					"Connection[" + this.uid + "].processCommand(): Fehler! \""
							+ commandName
							+ "\" konnte nicht "
							+ "richtig ausgeführt werden ("
							+ e.getClass().getSimpleName() + ": " + e.getMessage() + "\n"
							+ ")"
			);
			e.printStackTrace();
		}
	}

	/**
	 * Sendet ein Kommando
	 *
	 * @param command - Das Kommando das gesendet werden soll
	 */
	public void send(CommandInterface command) {
		this.logger.println(
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

		this.logger.println(
				"Connection[" + this.uid + "].setPlayerName: Setze Spielernamen \""
						+ playerName
						+ "\""
		);
		this.playerName = playerName;
	}

	public UID getUid() {
		return uid;
	}

	public void setLatency(Long latency) {

		this.latency = latency;
	}

	public Long getLatency() {
		return latency;
	}
}
