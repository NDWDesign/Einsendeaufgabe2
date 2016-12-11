package server;

import common.Commands.CommandFactory;
import common.Configuration;
import common.Container;
import common.Events.EventManager;
import common.Loggers.Logger;
import server.Listeners.ConnectionEstablishedListener;

/**
 * Einsendeaufgabe2 11.12.2016
 *
 * @author Nils Daniel Wittwer
 */
public class Factory extends common.Factory {
	public Factory(Container container) {
		super(container);
	}

	/**
	 * ToDo Möglichkeit finden Polymorphismus anstatt dieser hässlichen Funktion zu verwenden.
	 * Erstellt einen Instanz der übergebenen Klasse
	 *
	 * @param requiredClass Klasse von der einen Instanz erstellt werden soll.
	 * @param <T>           Typ der Instanz.
	 *
	 * @return Instanz der angeforderten Klasse vom Typ T,
	 * wenn keine Implementierung existiert wird null zurück gegeben.
	 */
	public <T> T create(Class<T> requiredClass) {

		// Versuchen ob common.Factory die Instanz erstellen kann.
		T instance = super.create(requiredClass);
		if (null != instance) {
			return instance;
		}

		switch (requiredClass.getSimpleName()) {

			case ("ConnectionEstablishedListener"):
				return (T) this.createConnectionEstablishedListener();

			case ("CoreInterface"):
				return (T) this.createServerCore();

			case ("EventRegistryInterface"):
				return (T) this.createEventRegistry();
		}
		return null;
	}

	private ConnectionEstablishedListener createConnectionEstablishedListener() {
		return new ConnectionEstablishedListener(
				this.container.get(CommandFactory.class),
				this.container.get(Logger.class)
		);
	}

	private EventRegistry createEventRegistry() {
		return new EventRegistry(
				this.container,
				this.container.get(EventManager.class)
		);
	}

	public ServerCore createServerCore() {
		return new ServerCore(
				this.container.get(EventManager.class),
				this.container.get(Logger.class),
				(int) this.container.get(Configuration.class).get("ServerPort")
		);
	}
}
