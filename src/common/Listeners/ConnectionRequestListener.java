package common.Listeners;

import common.*;
import common.Events.ConnectionEstablished;
import common.Events.ConnectionRequested;
import common.Events.EventInterface;
import common.Events.EventManager;
import common.Loggers.Logger;

import java.io.IOException;
import java.io.PrintStream;

/**
 * ConnectionRequestListener - Reagiert auf neue Netzwerkverbindungen.
 *
 * @author Nils Daniel Wittwer
 */
public class ConnectionRequestListener implements ListenerInterface {

	private final Factory factory;
	private final EventManager eventManager;
	private final Logger logger;
	private Connections connections;
	private ConnectionRequested event;

	public ConnectionRequestListener(
			Connections connections, Factory factory,
			EventManager eventManager,
			Logger logger
	) {
		this.connections = connections;
		this.factory = factory;
		this.eventManager = eventManager;
		this.logger = logger;
	}

	public void run() {

		try {
			this.logger
					.println("Versuche neue Verbindung zu etablieren...");

			Connection connection = this.factory.createConnection(
					this.event.getSocket()
			);

			this.connections.add(connection);

			this.logger.println("Starte neue Verbindung");
			connection.start();

			this.eventManager.dispatch(
					new ConnectionEstablished(connection)
			);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public ListenerInterface setEvent(EventInterface event) {
		this.event = (ConnectionRequested) event;
		return this;
	}
}
