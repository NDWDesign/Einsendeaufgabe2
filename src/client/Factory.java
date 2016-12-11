package client;

import client.Listeners.ConnectionEstablishedListener;
import common.Commands.CommandFactory;
import common.Container;
import common.Events.EventManager;
import common.Loggers.Logger;

/**
 * Client Factory - Erstellt Client-spezifische Objekte
 *
 * @author Nils Daniel Wittwer
 */
public class Factory extends common.Factory {

	public Factory(Container container) {
		super(container);
	}

	/**
	 * ToDo Möglichkeit finden Polymorphismus anstatt dieser hässlichen Funktion zu verwenden.
	 * Falls die angeforderte Klasse nicht in der allgemeinen Fabrik angelegt wird,
	 * wird versucht eine Client-spezifische Implementierung anzulegen.
	 *
	 * @param requiredClass Klasse von der einen Instanz erstellt werden soll.
	 * @param <T>           Typ der Instanz.
	 *
	 * @return Instanz der angeforderten Klasse vom Typ T
	 */
	public <T> T create(Class<T> requiredClass) {

		// Versuchen ob common.Factory die Instanz erstellen kann.
		T instance = super.create(requiredClass);
		if (null != instance) {
			return instance;
		}

		// Selber bekannt Instanzen erstellen.
		switch (requiredClass.getSimpleName()) {

			case ("CoreInterface"):
				return (T) this.createClientCore();

			case ("EventRegistryInterface"):
				return (T) this.createEventRegistry();

			case ("ConnectionEstablishedListener"):
				return (T) this.createConnectionEstablishedListener();
		}

		return null;
	}

	private ConnectionEstablishedListener createConnectionEstablishedListener() {

		return
				new ConnectionEstablishedListener(
						this.container.get(CommandFactory.class),
						this.container.get(Logger.class)
				);
	}


	public ClientCore createClientCore() {

		return new ClientCore(
				this.container.get(EventManager.class),
				this.container.get(Logger.class)
		);
	}

	public EventRegistry createEventRegistry() {
		return new EventRegistry(
				this.container,
				this.container.get(EventManager.class)
		);
	}
}
