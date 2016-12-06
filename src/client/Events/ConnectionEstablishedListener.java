package client.Events;

import common.CommandFactory;
import common.Events.ConnectionEstablished;
import common.Events.EventInterface;
import common.Events.ListenerInterface;

import java.util.ArrayList;

/**
 * Created by Nils Daniel Wittwer on 06.12.2016.
 */
public class ConnectionEstablishedListener implements ListenerInterface {

    private final CommandFactory commandFactory;

    public ConnectionEstablishedListener(CommandFactory commandFactory) {

        this.commandFactory = commandFactory;
    }

    @Override
    public void run(EventInterface event) {

        ConnectionEstablished connectionEstablished = (ConnectionEstablished) event;

        this.commandFactory.createCommand(connectionEstablished.getConnection(),
                "disconnect",
                new ArrayList<String>()
        ).send();
    }
}
