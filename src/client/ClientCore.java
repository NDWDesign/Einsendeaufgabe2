package client;

import common.ApplicationState;
import common.Events.ConnectionRequested;
import common.Events.EventManager;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;

/**
 * Created by Nils Daniel Wittwer on 06.12.2016.
 */
public class ClientCore implements Runnable {

    private final ApplicationState applicationState;
    private final EventManager eventManager;
    private final PrintStream output;

    public ClientCore(ApplicationState applicationState, EventManager eventManager, PrintStream output) {
        this.applicationState = applicationState;
        this.eventManager = eventManager;
        this.output = output;
    }

    @Override
    public void run() {

        this.output.println(
                "ClientCore.run(): Versuche Verbindung zu Server aufzubauen..."
        );

        try {
            this.eventManager.dispatch(
                    new ConnectionRequested(new Socket("192.168.1.101", 3000))
            );
        } catch (IOException e) {
            this.output.println(
                    "ClientCore.run(): Fehler: Verbindung zu Server konnte nicht hergestellt werden ("
                            + e.getMessage()
                            + ")"
            );
        }
    }
}
