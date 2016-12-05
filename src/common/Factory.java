package common;

import common.Events.EventHandler;
import server.ServerCore;
import server.connection.ClientConnectionHandler;

/**
 * Einsendeaufgabe2 04.12.2016
 *
 * @author Nils Daniel Wittwer
 */
public class Factory {

	private CommandFactory commandFactory;
	private ApplicationState applicationState;
	private ServerCore serverCore;
	private EventHandler eventHandler;
	private Configuration configuration;
	private ClientConnectionHandler clientConnectionHandler;

	public Factory(ApplicationState applicationState) {
		this.applicationState = applicationState;
	}

	public CommandFactory getCommandFactory(boolean createNew) {

		if (this.commandFactory == null || createNew) {
			this.commandFactory = new CommandFactory(this.getApplicationState());
		}
		return this.commandFactory;
	}

	public ServerCore getServerCore(boolean createNew) {
		if (this.serverCore == null || createNew) {
			this.serverCore = new ServerCore(
					this.getEventHandler(createNew),
					Integer.parseInt(this.getConfiguration(createNew).get("ServerPort"))
			);
		}
		return this.serverCore;
	}

	public EventHandler getEventHandler(boolean createNew) {

		if (this.eventHandler == null || createNew) {
			this.eventHandler = new EventHandler();
		}

		return this.eventHandler;
	}

	public Configuration getConfiguration(boolean createNew) {

		if (this.configuration == null || createNew) {
			this.configuration = new Configuration();
		}

		return this.configuration;
	}

	public ClientConnectionHandler getClientConnectionHandler(boolean createNew) {

		if (this.clientConnectionHandler == null || createNew) {
			this.clientConnectionHandler = new ClientConnectionHandler(
					this.getApplicationState(),
					this.getCommandFactory(createNew)
			);
		}

		return this.clientConnectionHandler;
	}

	public ApplicationState getApplicationState() {
		return applicationState;
	}
}
