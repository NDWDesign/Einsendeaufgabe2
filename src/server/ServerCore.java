package server;

import common.Events.ConnectionRequested;
import common.Events.EventManager;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;

/**
 * ServerCore - Wartet auf neue Netzwerkverbindungen.
 *
 * @author Nils Daniel Wittwer
 */
public class ServerCore extends Thread {

    private final int serverPort;
    private final EventManager eventHandler;
    private final PrintStream output;

    public ServerCore(EventManager eventHandler, PrintStream output, int port) {

        this.eventHandler = eventHandler;
        this.output = output;
        this.serverPort = port;
    }

    public void run() {

        ServerSocket serverSocket;

        try {
            serverSocket = new ServerSocket(this.serverPort);
        } catch (IOException e) {
            this.output.println(
                    "ServerCore.run(): Fehler! ServerSocket für Port \"" + this.serverPort + "\" konnte nicht erstellt werden!"
                            + "\nNachricht: " + e.getMessage()
            );
            return;
        }

        try {
            while (!currentThread().isInterrupted()) {
                this.output.println(
                        "ServerCore.run(): Warte auf neue Verbindungen..."
                );
                eventHandler.dispatch(
                        new ConnectionRequested(serverSocket.accept())
                );
            }
        } catch (IOException e) {
            this.output.println(
                    "ServerCore.run(): Fehler beim Warten auf Verbindung!"
                            + "\nNachricht: " + e.getMessage()
            );
        }
    }
}
