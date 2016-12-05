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

	private ApplicationState applicationState;

	public ClientConnectionHandler(ApplicationState applicationState) {

		this.applicationState = applicationState;
	}

	public void run(EventInterface event) {

		ClientConnected clientConnectedEvent = (ClientConnected) event;

		try {
			this.applicationState
					.getOutput()
					.println("ClientConnectionHandler.run(): Trying to setup new ClientConnection");

			ClientConnection newClientConnection = this.applicationState
					.getFactory()
					.createClientConnection(
							clientConnectedEvent.getSocket(),
							true
					);

			this.applicationState.getClientConnections().add(newClientConnection);

			this.applicationState
					.getOutput()
					.println("ClientConnectionHandler.run(): Starting new ClientConnection");
			newClientConnection.run();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
