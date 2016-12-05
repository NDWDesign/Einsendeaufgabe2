package server.connection;

import common.ApplicationState;
import common.CommandFactory;
import common.Events.ClientConnected;
import common.Events.EventInterface;
import common.Events.ListenerInterface;

import java.io.IOException;

/**
 * ClientConnectionHandler - Reagiert auf neue Netzwerkverbindungen.
 *
 * @author Nils Daniel Wittwer
 */
public class ClientConnectionHandler implements ListenerInterface {

	private final CommandFactory commandFactory;

	private ApplicationState applicationState;

	public ClientConnectionHandler(ApplicationState applicationState, CommandFactory commandFactory) {

		this.applicationState = applicationState;
		this.commandFactory = commandFactory;
	}

	public void run(EventInterface event) {

		ClientConnected clientConnectedEvent = (ClientConnected) event;

		try {
			this.applicationState.getClientConnections().add(
					new ClientConnection(this.applicationState, this.commandFactory, clientConnectedEvent.getSocket())
			);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
