package common.commands;

import common.ApplicationState;
import server.connection.ClientConnection;

import java.io.OutputStream;
import java.util.ArrayList;

/**
 * Einsendeaufgabe2 04.12.2016
 *
 * @author Nils Daniel Wittwer
 */
public class Disconnect extends AbstractCommand {

	public Disconnect(
			ApplicationState applicationState,
			ClientConnection clientConnection,
			ArrayList<String> parameters
	) {

		super(applicationState, clientConnection, parameters);
	}

	public void run() {

		this.applicationState.getOutput().println("Unterbreche Verbindung!");
		this.clientConnection.interrupt();
	}
}
