package common.Events;

import java.net.Socket;

/**
 * Einsendeaufgabe2 04.12.2016
 *
 * @author Nils Daniel Wittwer
 */
public class ConnectionRequested implements EventInterface {

	private Socket socket;

	public ConnectionRequested(Socket socket) {

		this.socket = socket;
	}

	public Socket getSocket() {

		return socket;
	}
}
