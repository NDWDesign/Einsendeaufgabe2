package client;

import common.CoreInterface;
import common.Events.ConnectionRequested;
import common.Events.EventManager;
import common.Loggers.Logger;

import java.io.IOException;
import java.net.Socket;

/**
 * ClientCore - Kern der  Klientanwendung
 */
public class ClientCore extends Thread implements CoreInterface {

    private final EventManager eventManager;
    private final Logger logger;

    public ClientCore(EventManager eventManager, Logger logger) {
        this.eventManager = eventManager;
        this.logger = logger;
    }

    @Override
    public void run() {
        this.logger.println(
                "Versuche Verbindung zu Server aufzubauen..."
        );

        try {
            this.eventManager.dispatch(
                    new ConnectionRequested(new Socket("localhost", 3000))
            );
        } catch (IOException e) {
            this.logger.println(
                    "Fehler! Verbindung zu Server konnte nicht hergestellt werden ("
                            + e.getMessage()
                            + ")"
            );
        }
    }
}
