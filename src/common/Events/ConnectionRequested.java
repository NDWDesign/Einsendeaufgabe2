package common.Events;

import java.net.Socket;

/**
 * ConnectinoRequested Event - Wird ausgelöst wenn eine neue Socketverbindung aufgebaut wurde.
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
