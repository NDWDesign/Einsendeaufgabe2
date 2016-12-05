package common;

import common.Events.EventManager;
import server.ServerCore;
import server.connection.ClientConnection;
import server.connection.ClientConnectionHandler;

import java.io.IOException;
import java.net.Socket;

/**
 * Einsendeaufgabe2 04.12.2016
 *
 * @author Nils Daniel Wittwer
 */
public class Factory {

	private CommandFactory commandFactory;
	private ApplicationState applicationState;
	private ServerCore serverCore;
	private EventManager eventHandler;
	private Configuration configuration;
	private ClientConnectionHandler clientConnectionHandler;
	private ClientConnection clientConnection;

	public Factory(ApplicationState applicationState) {
		this.applicationState = applicationState;
	}

	public CommandFactory createCommandFactory(boolean createNew) {

		if (this.commandFactory == null || createNew) {
			this.commandFactory = new CommandFactory(this.getApplicationState());
		}
		return this.commandFactory;
	}

	public ServerCore createServerCore(boolean createNew) {
		if (this.serverCore == null || createNew) {
			this.serverCore = new ServerCore(
					this.getApplicationState(),
					this.createEventHandler(createNew),
					Integer.parseInt(this.createConfiguration(createNew).get("ServerPort"))
			);
		}
		return this.serverCore;
	}

	public EventManager createEventHandler(boolean createNew) {

		if (this.eventHandler == null || createNew) {
			this.eventHandler = new EventManager(this.getApplicationState());
		}

		return this.eventHandler;
	}

	public Configuration createConfiguration(boolean createNew) {

		if (this.configuration == null || createNew) {
			this.configuration = new Configuration();
		}

		return this.configuration;
	}

	public ClientConnectionHandler createClientConnectionHandler(boolean createNew) {

		if (this.clientConnectionHandler == null || createNew) {
			this.clientConnectionHandler = new ClientConnectionHandler(
					this.getApplicationState()
			);
		}

		return this.clientConnectionHandler;
	}

	public ApplicationState getApplicationState() {
		return applicationState;
	}

	public ClientConnection createClientConnection(Socket socket, boolean createNew) throws IOException {

		if (null == this.clientConnection || createNew) {
			this.clientConnection = new ClientConnection(
					this.getApplicationState(),
					this.createCommandFactory(false),
					socket
			);
		}

		return this.clientConnection;
	}
}
