package common;

import server.connection.ClientConnection;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

/**
 * ApplicationState - hällt den globalen Status
 *
 * @author Nils Daniel Wittwer
 */
public class ApplicationState {


	/** Speichert alle aktiven Netzwerkverbindungen */
	private List<ClientConnection> clientConnections = new ArrayList<>();

	private final PrintStream output = System.out;

	private Factory factory;


	public PrintStream getOutput() {
		return output;
	}

	public List<ClientConnection> getClientConnections() {
		return clientConnections;
	}

	public Factory getFactory() {

		return factory;
	}

	public void setFactory(Factory factory) {
		this.factory = factory;
	}
}
