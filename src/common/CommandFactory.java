package common;

import common.commands.Disconnect;
import common.commands.SetPlayerName;
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
	 * @return AbstractCommand
	 */
	public Runnable createCommand(
			ClientConnection clientConnection,
			String commandName,
			ArrayList<String> parameters
	) {

		try {
			switch (commandName) {
				case "disconnect":
					return new Disconnect(this.applicationState, clientConnection, parameters);
				case "setPlayerName":
					return new SetPlayerName(this.applicationState, clientConnection, parameters);
			}
		} catch (Exception e) {
			applicationState.getOutput().println("Fehler: " + commandName + " konnte nicht instanziiert werden!");
			e.getStackTrace();
		}

		return null;
	}
}
