package server;

import common.Container;
import common.Events.ConnectionEstablished;
import common.Events.EventManager;
import server.Listeners.ConnectionEstablishedListener;

/**
 * Server Event Registry - Initialisiert serverseitig benötigte Events
 */
class EventRegistry extends common.Events.EventRegistry {

	public EventRegistry(Container container, EventManager eventManager) {
		super(container, eventManager);
	}

	public void run() {

		super.run();

		eventManager.register(
				ConnectionEstablished.class.getSimpleName(),
				container.get(ConnectionEstablishedListener.class)
		);

	}
}
