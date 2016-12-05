package common;

import common.commands.*;
import server.connection.ClientConnection;

import java.util.ArrayList;

/**
 * CommandFactory - Erstellt eine neue Instanz eines Kommandos.
 *
 * @author Nils Daniel Wittwer
 */
public class CommandFactory {

	private final ApplicationState applicationState;

	CommandFactory(ApplicationState applicationState) {

		this.applicationState = applicationState;
	}

	/**
	 * Erstellt eine neue Instanz eines Kommandos.
	 *
	 * @param clientConnection - Verbindung von dem das Kommando stammt.
	 * @param commandName      - Name des Kommandos.
	 * @param parameters       - Eventuell für das Kommando benötigte Parameter.
	 *
	 * @return CommandInterface
	 */
	public CommandInterface createCommand(
			ClientConnection clientConnection,
			String commandName,
			ArrayList<String> parameters
	) {

		CommandInterface command = null;
		try {
			switch (commandName) {
				case "disconnect":
					command = new Disconnect();
				case "setPlayerName":
					command = new SetPlayerName();
			}
		} catch (Exception e) {
			applicationState.getOutput().println("CommandFactory.createCommand(): Fehler: \"" + commandName + "\" konnte nicht instantiiert werden!");
			e.getStackTrace();
		}

		if (null != command) {
			applicationState.getOutput().println("CommandFactory.createCommand(): Kommando \"" + commandName + "\" erstellt. Übergebe Parameter.");
			command.loadParameters(this.applicationState, clientConnection, parameters);
		}

		return command;
	}
}
