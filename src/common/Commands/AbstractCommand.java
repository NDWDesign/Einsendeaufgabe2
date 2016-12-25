package common.Commands;

import common.Connection;

import java.util.HashMap;
import java.util.Map;

/**
 * Implementiert allgemeine gemeinsame Kommando-Funktionen
 */
abstract public class AbstractCommand implements CommandInterface {

    protected HashMap<String, String> parameters = new HashMap<String, String>();
    protected Connection connection;


    public HashMap<String, String> getParameters() {

        return this.parameters;
    }

    public CommandInterface loadParameters(HashMap<String, String> parameters) {

        System.out.println(parameters);
        parameters.forEach(this::setParameter);

        return this;
    }

    private void setParameter(String parameterName, String parameterValue) {

        this.parameters.put(parameterName, parameterValue);
    }

    public CommandInterface setConnection(Connection connection) {

        this.connection = connection;
        return this;
    }

    public Connection getConnection() {

        return this.connection;
    }

    public void send() {
        this.connection.send(this);
    }
}
