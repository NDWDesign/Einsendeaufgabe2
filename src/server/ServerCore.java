package server;

import common.CoreInterface;
import common.Events.ConnectionRequested;
import common.Events.EventManager;
import common.Loggers.Logger;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * ServerCore - Wartet auf neue Netzwerkverbindungen.
 *
 * @author Nils Daniel Wittwer
 */
public class ServerCore extends Thread implements CoreInterface {

	private final int serverPort;
	private final EventManager eventHandler;
	private final Logger logger;

	public ServerCore(EventManager eventHandler, Logger logger, int port) {

		this.eventHandler = eventHandler;
		this.logger = logger;
		this.serverPort = port;
	}

	public void run() {

		ServerSocket serverSocket;

		try {
			serverSocket = new ServerSocket(this.serverPort);
		} catch (IOException e) {
			this.logger.println(
					" Fehler! ServerSocket für Port \"" + this.serverPort + "\" konnte nicht erstellt" +

							" werden!"
							+ "\nNachricht: " + e.getMessage()
			);
			return;
		}

		try {
			while (!currentThread().isInterrupted()) {
				this.logger.println(
						"Warte auf neue Verbindungen..."
				);
				eventHandler.dispatch(
						new ConnectionRequested(serverSocket.accept())
				);
			}
		} catch (IOException e) {
			this.logger.println(
					"Fehler beim Warten auf Verbindung!"
							+ "\nNachricht: " + e.getMessage()
			);
		}
	}
}
