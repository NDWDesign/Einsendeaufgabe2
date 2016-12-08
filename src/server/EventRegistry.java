package server;

import common.Commands.PingPong;
import common.Events.ConnectionEstablished;
import common.Events.ConnectionRequested;
import common.Events.EventManager;
import common.Factory;
import common.Listeners.ListenerInterface;

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
        // Beim Zustandekommen einer Verbindung wird das PingPong-Kommando ausgeführt
        eventManager.register(ConnectionEstablished.class.getSimpleName(),
                (ListenerInterface) (
                        (PingPong) factory.createCommandFactory(false)
                                .createCommand(PingPong.class.getSimpleName()))
                        .setCount(20)
        );

    }

}
