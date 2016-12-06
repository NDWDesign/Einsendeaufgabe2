package common.connection;

import common.ApplicationState;
import common.Events.*;
import common.Factory;

import java.io.IOException;
import java.io.PrintStream;

/**
 * ConnectionRequestHandler - Reagiert auf neue Netzwerkverbindungen.
 *
 * @author Nils Daniel Wittwer
 */
public class ConnectionRequestHandler implements ListenerInterface {

	private final Factory factory;
	private final EventManager eventManager;
	private final PrintStream output;
	private ApplicationState applicationState;

	public ConnectionRequestHandler(ApplicationState applicationState, Factory factory, EventManager eventManager, PrintStream output) {

		this.applicationState = applicationState;
		this.factory = factory;
		this.eventManager = eventManager;
		this.output = output;
	}

	public void run(EventInterface event) {

		ConnectionRequested connectionRequestedEvent = (ConnectionRequested) event;

		try {
			this.output
					.println("ConnectionRequestHandler.run(): Versuche neue Verbingung zu etablieren...");

			Connection connection = this.factory.createConnection(
							connectionRequestedEvent.getSocket(),
							true
					);

			this.applicationState.getConnections().add(connection);

			this.output.println("ConnectionRequestHandler.run(): Starte neue Verbindung");
			new Thread(connection).start();

			this.eventManager.dispatch(
					new ConnectionEstablished(connection)
			);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
