package common.Events;

import java.net.Socket;

/**
 * Einsendeaufgabe2 04.12.2016
 *
 * @author Nils Daniel Wittwer
 */
public class ClientConnected implements EventInterface {

	private Socket socket;

	public ClientConnected(Socket clientSocket) {

		this.socket = clientSocket;
	}

	public Socket getSocket() {

		return socket;
	}
}
