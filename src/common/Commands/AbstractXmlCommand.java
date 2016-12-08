package common.Commands;

import common.Connection;

import java.util.ArrayList;

/**
 * Created by Nils Daniel Wittwer on 07.12.2016.
 */
abstract public class AbstractXmlCommand implements CommandInterface {

    protected ArrayList<String> parameters = new ArrayList<String>();
    protected Connection connection;

    public String serialize() {

        String commandString = "<command name=\""
                + this.getClass().getSimpleName() +
                "\">";

        if (!this.parameters.isEmpty()) {
            for (String parameter : this.parameters) {
                commandString += "<parameter>"
                        + parameter
                        + "</parameter>";
            }
        }

        commandString += "</command>";

        return commandString;
    }

    public CommandInterface setParameters(ArrayList<String> parameters) {
        this.parameters = parameters;

        return this;
    }

    public CommandInterface setConnection(Connection connection) {
        this.connection = connection;
        return this;
    }

    public Connection getConnection() {

        return this.getConnection();
    }

    public void send() {
        this.connection.send(this);
    }
}
