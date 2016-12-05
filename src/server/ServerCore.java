package server;

import common.ApplicationState;
import common.Events.ClientConnected;
import common.Events.EventManager;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * ServerCore - Wartet auf neue Netzwerkverbindungen.
 *
 * @author Nils Daniel Wittwer
 */
public class ServerCore extends Thread {

	private final ApplicationState applicationState;
	private EventManager eventHandler;

	private ServerSocket serverSocket;

	public ServerCore(ApplicationState applicationState, EventManager eventHandler, int port)  {

		this.applicationState = applicationState;
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
				this.applicationState.getOutput().println("ServerCore.run(): Listening for new connections...");
				eventHandler.dispatch(
						new ClientConnected(serverSocket.accept())
				);
			}
		} catch (IOException exception) {
			exception.printStackTrace();
		}
	}
}
