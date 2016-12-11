package common.Events;

import common.Container;
import common.Listeners.ConnectionRequestListener;

/**
 * Common Event Registry - Initialisiert allgemein benötigte Events
 */
public class EventRegistry implements EventRegistryInterface {

	protected final EventManager eventManager;
	protected final Container container;

	public EventRegistry(Container container, EventManager eventManager) {
		this.container = container;
		this.eventManager = eventManager;

	}

	public void run() {
		eventManager.register(
				ConnectionRequested.class.getSimpleName(),
				container.get(ConnectionRequestListener.class)
	        );
	}

}
