package server;

import common.Events.ClientConnected;
import common.Events.EventHandler;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * ServerCore - Wartet auf neue Netzwerkverbindungen.
 *
 * @author Nils Daniel Wittwer
 */
public class ServerCore extends Thread {

	private EventHandler eventHandler;

	private ServerSocket serverSocket;

	public ServerCore(EventHandler eventHandler, int port)  {

		this.eventHandler = eventHandler;

		try {
			this.serverSocket = new ServerSocket(port);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void run() {

		try {
			while (!currentThread().isInterrupted()) {

				eventHandler.dispatch(
						new ClientConnected(serverSocket.accept())
				);
			}
		} catch (IOException exception) {
			exception.printStackTrace();
		}
	}
}
