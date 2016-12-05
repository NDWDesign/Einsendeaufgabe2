package common.commands;

import common.ApplicationState;
import server.connection.ClientConnection;

import java.util.ArrayList;

/**
 * Einsendeaufgabe2 04.12.2016
 *
 * @author Nils Daniel Wittwer
 */
public class Disconnect implements CommandInterface {

	private ApplicationState applicationState;
	private ClientConnection clientConnection;

	@Override
	public String serialize() {
		return "<command name=\"disconnect\"></command>";
	}

	@Override
	public void loadParameters(
			ApplicationState applicationState, ClientConnection clientConnection, ArrayList<String> parameters
	) {
		this.applicationState = applicationState;
		this.clientConnection = clientConnection;
	}

	public void execute() {

		this.applicationState.getOutput().println("Disconnect.execute(): Unterbreche Verbindung!");
		this.clientConnection.interrupt();
	}
}
