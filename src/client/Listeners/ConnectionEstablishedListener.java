package client.Listeners;

import common.Commands.CommandFactory;
import common.Commands.SetPlayerName;
import common.Connection;
import common.Events.ConnectionEstablished;
import common.Events.EventInterface;
import common.Listeners.ListenerInterface;
import common.Loggers.Logger;

/**
 * ConnectionEstablished Listener - Hört auf ConnectionEstablished Event
 */
public class ConnectionEstablishedListener implements ListenerInterface {

    private final CommandFactory commandFactory;
    private final Logger logger;
    private ConnectionEstablished event;

    public ConnectionEstablishedListener(CommandFactory commandFactory, Logger logger) {

        this.commandFactory = commandFactory;
        this.logger = logger;
    }

    public ListenerInterface setEvent(EventInterface event) {
        this.event = (ConnectionEstablished) event;

        return this;
    }
    @Override
    public void run() {

        Connection connection = this.event.getConnection();
        this.logger.println(
                "Sende Kommando \""
                + SetPlayerName.class.getSimpleName()
                + "\" an Verbindung ["
                        + connection.getUid()
                        + "]..."
        );

        ((SetPlayerName) this.commandFactory.createCommand(SetPlayerName.class.getSimpleName()))
                .setPlayerName("My PlayerName")
                .setConnection(connection)
                .send();
    }
}
