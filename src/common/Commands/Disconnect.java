package common.Commands;

import java.io.PrintStream;

/**
 * Disconnect Kommando - Unterbricht die enthaltene Verbindung.
 *
 * @author Nils Daniel Wittwer
 */
public class Disconnect extends AbstractXmlCommand {

    private final PrintStream output;

    public Disconnect(PrintStream output) {

        this.output = output;
    }

    @Override

    public void run() {

        this.output.println("Disconnect.run(): Unterbreche Verbindung!");
        // Todo Thread beenden klappt nicht. Prüfen.
        this.connection.interrupt();
    }

    public void send() {
        this.connection.send(this);
    }
}
