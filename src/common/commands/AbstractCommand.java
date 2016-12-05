package common.commands;

import common.ApplicationState;
import server.connection.ClientConnection;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.logging.Logger;

/**
 * Einsendeaufgabe2 04.12.2016
 *
 * @author Nils Daniel Wittwer
 */
abstract class AbstractCommand implements Runnable {

	protected final ApplicationState applicationState;

	protected final ClientConnection clientConnection;

	protected final ArrayList<String> parameters;

	protected AbstractCommand(
			ApplicationState applicationState,
			ClientConnection clientConnection,
			ArrayList<String> parameters
	) {

		this.applicationState = applicationState;
		this.clientConnection = clientConnection;
		this.parameters = parameters;
	}
}
