package common.Events;

import common.Connection;

/**
 * ConnectionEstablished Event - Wird ausgelöst wenn eine neue Verbindung (Connection) erfolgreich erstellt wurde.
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
