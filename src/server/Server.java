package server;

import common.ApplicationState;
import common.Events.EventManager;
import common.Factory;

/**
 * Einsendeaufgabe2 04.12.2016
 *
 * @author Nils Daniel Wittwer
 */
public class Server {

	public static void main(String[] args) {
		ApplicationState applicationState = new ApplicationState();

		applicationState.getOutput().println("Server.main(): Creating factory...");
		applicationState.setFactory(new Factory(applicationState));

		applicationState.getOutput().println("Server.main(): Registering event listeners...");
		registerEventListeners(applicationState.getFactory());

		applicationState.getOutput().println("Server.main(): Starting server core...");
		applicationState.getFactory().createServerCore(false).run();
	}

	private static void registerEventListeners(Factory factory) {

		EventManager eventManager = factory.createEventHandler(false);

		eventManager.register("ClientConnected",
				factory.createClientConnectionHandler(false)
		);
	}
}
