package common;

import common.commands.*;
import common.connection.Connection;

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
	 * @param connection - Verbindung von dem das Kommando stammt.
	 * @param commandName      - Name des Kommandos.
	 * @param parameters       - Eventuell für das Kommando benötigte Parameter.
	 *
	 * @return CommandInterface
	 */
	public CommandInterface createCommand(
			Connection connection,
			String commandName,
			ArrayList<String> parameters
	) {

		CommandInterface command = null;
		try {
			switch (commandName) {
				case "disconnect":
					command = new Disconnect();
					break;
				case "setPlayerName":
					command = new SetPlayerName();
					break;
			}
		} catch (Exception e) {
			applicationState.getOutput().println("CommandFactory.createCommand(): Fehler: \"" + commandName + "\" konnte nicht instantiiert werden!");
			e.getStackTrace();
		}

		if (null != command) {
			applicationState.getOutput().println("CommandFactory.createCommand(): Kommando \"" + commandName + "\" erstellt. Übergebe Parameter.");
			command.loadParameters(this.applicationState, connection, parameters);
		}

		return command;
	}
}
