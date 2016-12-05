package server;

import common.ApplicationState;
import common.Events.EventHandler;
import common.Factory;
import server.connection.ClientConnectionHandler;

/**
 * Einsendeaufgabe2 04.12.2016
 *
 * @author Nils Daniel Wittwer
 */
public class Server {

	public static void main() {

		ApplicationState applicationState = new ApplicationState();
		Factory factory = new Factory(applicationState);

		registerEventListeners(factory);

		factory.getServerCore(false).run();
	}

	private static void registerEventListeners(Factory factory) {

		EventHandler eventHandler = factory.getEventHandler(false);

		eventHandler.register("clientConnected",
				factory.getClientConnectionHandler(false)
		);
	}
}
