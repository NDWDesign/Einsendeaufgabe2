package server;

import common.Events.ConnectionRequested;
import common.Events.EventManager;
import common.Factory;

/**
 * Server Event Registry - Initialisiert serverseitig benötigte Events
 */
class EventRegistry implements Runnable {

    private final EventManager eventManager;
    private final Factory factory;

    EventRegistry(Factory factory, EventManager eventManager) {
        this.factory = factory;
        this.eventManager = eventManager;

    }

    public void run() {

        eventManager.register(ConnectionRequested.class.getSimpleName(),
                factory.createClientConnectionHandler(false)
        );
    }

}
