package common.Commands;

import common.Loggers.Logger;

/**
 * Disconnect Kommando - Unterbricht die enthaltene Verbindung.
 *
 * @author Nils Daniel Wittwer
 */
public class Disconnect extends AbstractXmlCommand {

    private final Logger logger;

    public Disconnect(Logger logger) {

        this.logger = logger;
    }

    @Override

    public void run() {

        this.logger.println(" Unterbreche Verbindung!");

        this.connection.interrupt();
    }

    public void send() {
        this.connection.send(this);
    }
}
