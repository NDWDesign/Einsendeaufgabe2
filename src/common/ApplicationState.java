package common;

import common.connection.Connection;

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
	private List<Connection> connections = new ArrayList<>();

	private PrintStream output;

	private Factory factory;


	public PrintStream getOutput() {
		return output;
	}

	public List<Connection> getConnections() {
		return connections;
	}

	public Factory getFactory() {

		return factory;
	}

	public void setFactory(Factory factory) {
		this.factory = factory;
	}

	public void setOutput(PrintStream output) {
		this.output = output;
	}
}
