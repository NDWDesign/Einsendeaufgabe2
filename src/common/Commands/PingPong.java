package common.Commands;

import common.Events.ConnectionEstablished;
import common.Events.EventInterface;
import common.Listeners.ListenerInterface;

import java.io.PrintStream;

/**
 * PingPong Kommando - Führt sich selbst auf der Gegenseite der Verbindung aus
 *
 * @author Nils Daniel Wittwer.
 * @version 07.12.2016
 */
public class PingPong extends AbstractXmlCommand implements ListenerInterface {

    private final PrintStream output;

    public PingPong(PrintStream output) {
        this.output = output;
    }

    @Override
    public void run() {

        this.output.println("PingPong.run(): Schlafe für 100ms...");
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {

            e.printStackTrace();
        }
        this.output.println("PingPong.run(): Noch " + this.getCount() + " Ausführungen...");
        this.setCount(this.getCount() - 1);
        if(this.getCount() > 0) {
            this.send();
        } else {
            this.output.println("PingPong.run(): Beendet");
        }
    }

    public PingPong setCount(int count) {
        this.parameters.add(0, Integer.toString(count));
        return this;
    }

    public int getCount() {
        return Integer.parseInt(this.parameters.get(0));
    }

    @Override
    public ListenerInterface setEvent(EventInterface event) {
        this.setConnection(
                ((ConnectionEstablished) event)
                        .getConnection()
        );
        return this;
    }
}
