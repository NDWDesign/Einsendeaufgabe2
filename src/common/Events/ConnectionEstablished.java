package common.Events;

import common.connection.Connection;

/**
 * Created by Nils Daniel Wittwer on 06.12.2016.
 */
public class ConnectionEstablished implements EventInterface {

    private final Connection connection;

    public ConnectionEstablished(Connection connection) {

        this.connection = connection;
    }

    public Connection getConnection() {
        return connection;
    }
}
