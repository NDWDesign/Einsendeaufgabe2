package common.commands;

import common.ApplicationState;
import common.connection.Connection;

import java.util.ArrayList;

/**
 * Einsendeaufgabe2 04.12.2016
 *
 * @author Nils Daniel Wittwer
 */
public class Disconnect implements CommandInterface {

    private ApplicationState applicationState;
    private Connection connection;

    @Override
    public String serialize() {
        return "<command name=\"disconnect\"></command>";
    }

    @Override
    public void loadParameters(
            ApplicationState applicationState, Connection connection, ArrayList<String> parameters
    ) {
        this.applicationState = applicationState;
        this.connection = connection;
    }

    public void execute() {

        this.applicationState.getOutput().println("Disconnect.execute(): Unterbreche Verbindung!");
        this.connection.interrupt();
    }

    public void send() {
        this.connection.send(this);
    }
}
