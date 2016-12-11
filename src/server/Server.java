package server;

import common.Container;
import common.CoreInterface;
import common.Events.EventRegistryInterface;
import common.Loggers.Logger;

/**
 * Snake Server
 *
 * @author Nils Daniel Wittwer
 */
public class Server {

	/**
	 * ToDo Implementierung doppelt Server, nur mit anderem Namespace, wie ändern?
	 *
	 * @param args Nicht genutzt
	 */
	public static void main(String[] args) {

		Container container = new Container();
		container.setFactory(new Factory(container));

		Logger logger = container.get(Logger.class);
		logger.println("Factory und Globaler Status erstellt.");

		logger.println("Registriere Events...");
		container.get(EventRegistryInterface.class).run();

		logger.println("Starte Core...");
		container.get(CoreInterface.class).run();
	}
}
