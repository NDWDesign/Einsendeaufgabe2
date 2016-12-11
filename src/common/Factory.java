package common;

import common.Commands.CommandFactory;
import common.Commands.CommandParserInterface;
import common.Commands.XmlCommandParser;
import common.Events.EventManager;
import common.Listeners.ConnectionRequestListener;
import common.Loggers.Logger;

import java.io.IOException;
import java.net.Socket;

/**
 * Factory - Erstellt Instanzen mit den dazugehörigen Abhängigkeiten
 *
 * @author Nils Daniel Wittwer
 */
public class Factory {

	protected final Container container;

	public Factory(Container container) {

		this.container = container;
	}

	/**
	 * ToDo Möglichkeit finden Polymorphismus anstatt dieser hässlichen Funktion zu verwenden.
	 * Erstellt einen Instanz der übergebenen Klasse
	 *
	 * @param requiredClass Klasse von der einen Instanz erstellt werden soll.
	 * @param <T>           Typ der Instanz.
	 *
	 * @return Instanz der angeforderten Klasse vom Typ T
	 * wenn keine Implementierung existiert wird null zurück gegeben.
	 */

	public <T> T create(Class<T> requiredClass) {

		switch (requiredClass.getSimpleName()) {

			case ("CommandFactory"):
				return (T) this.createCommandFactory();

			case ("Configuration"):
				return (T) this.createConfiguration();

			case ("ConnectionRequestListener"):
				return (T) this.createConnectionRequestListener();

			case ("Connections"):
				return (T) this.createConnections();

			case ("CommandParserInterface"):
				return (T) this.createXmlCommandParser();

			case ("EventManager"):
				return (T) this.createEventManager();

			case ("Logger"):
				return (T) this.createLogger();
		}

		return null;
	}

	private Connections createConnections() {

		return new Connections();
	}

	public CommandFactory createCommandFactory() {

		return new CommandFactory(
				this.container.get(Logger.class)
		);
	}


	public EventManager createEventManager() {

		return new EventManager(
				this.container,
				this.container.get(Logger.class)
		);
	}

	public Configuration createConfiguration() {

		return new Configuration();
	}

	public ConnectionRequestListener createConnectionRequestListener() {

		return new ConnectionRequestListener(
				this.container.get(Connections.class),
				this,
				this.container.get(EventManager.class),
				this.container.get(Logger.class)
		);

	}

	public Connection createConnection(Socket socket) throws IOException {

		return new Connection(
				this.container.get(CommandFactory.class),
				this.container.get(CommandParserInterface.class),
				this.container.get(Logger.class),
				socket
		);
	}

	public CommandParserInterface createXmlCommandParser() {

		return new XmlCommandParser(
				this.container.get(Logger.class)
		);
	}

	public Logger createLogger() {
		return new Logger();
	}
}
