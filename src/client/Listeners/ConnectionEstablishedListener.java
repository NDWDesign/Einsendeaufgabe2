package client.Listeners;

import common.CommandFactory;
import common.Events.ConnectionEstablished;
import common.Events.EventInterface;
import common.Listeners.ListenerInterface;
import common.Commands.SetPlayerName;
import common.Connection;

import java.io.PrintStream;

/**
 * ConnectionEstablished Listener - Hört auf ConnectionEstablished Event
 */
public class ConnectionEstablishedListener implements ListenerInterface {

    private final CommandFactory commandFactory;
    private final PrintStream output;
    private ConnectionEstablished event;

    public ConnectionEstablishedListener(CommandFactory commandFactory, PrintStream output) {

        this.commandFactory = commandFactory;
        this.output = output;
    }

    public ListenerInterface setEvent(EventInterface event) {
        this.event = (ConnectionEstablished) event;

        return this;
    }
    @Override
    public void run() {

        Connection connection = this.event.getConnection();
        this.output.println(
                "ConnectionEstablishedListener.run(): Sende Kommando \""
                + SetPlayerName.class.getSimpleName()
                + "\" an Verbindung["
                        + connection.getUid()
                        + "]..."
        );

        ((SetPlayerName) this.commandFactory.createCommand(SetPlayerName.class.getSimpleName()))
                .setPlayerName("My PlayerName")
                .setConnection(connection)
                .send();
    }
}
