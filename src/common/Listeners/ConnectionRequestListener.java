package common.Listeners;

import common.ApplicationState;
import common.Events.ConnectionEstablished;
import common.Events.ConnectionRequested;
import common.Events.EventInterface;
import common.Events.EventManager;
import common.Factory;
import common.Connection;
import common.Listeners.ListenerInterface;

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
    private final PrintStream output;
    private ApplicationState applicationState;
    private ConnectionRequested event;

    public ConnectionRequestListener(ApplicationState applicationState, Factory factory, EventManager eventManager, PrintStream output) {
        this.applicationState = applicationState;
        this.factory = factory;
        this.eventManager = eventManager;
        this.output = output;
    }

    public void run() {

        try {
            this.output
                    .println("ConnectionRequestListener.run(): Versuche neue Verbingung zu etablieren...");

            Connection connection = this.factory.createConnection(
                    this.event.getSocket(),
                    true
            );

            this.applicationState.getConnections().add(connection);

            this.output.println("ConnectionRequestListener.run(): Starte neue Verbindung");
            new Thread(connection).start();

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
