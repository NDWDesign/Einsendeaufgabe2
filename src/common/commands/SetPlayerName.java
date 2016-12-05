package common.commands;

import common.ApplicationState;
import server.connection.ClientConnection;

import java.util.ArrayList;

/**
 * Setzt den Spielernamen für die aktuelle Verbindung
 *
 * @author Nils Daniel Wittwer
 */
public class SetPlayerName extends AbstractCommand implements Runnable {

	public SetPlayerName(
			ApplicationState applicationState,
			ClientConnection clientConnection,
			ArrayList<String> parameters
	) {
		super(applicationState, clientConnection, parameters);
	}


	@Override
	public void run() {

		this.clientConnection.setPlayerName(this.parameters.get(0));
	}
}
