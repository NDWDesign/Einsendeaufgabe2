package common.Events;

import common.Container;
import common.Listeners.ListenerInterface;
import common.Loggers.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Einsendeaufgabe2 04.12.2016
 *
 * @author Nils Daniel Wittwer
 */
public class EventManager {

	private final Container container;
	private final Logger logger;

	public EventManager(Container container, Logger logger) {

		this.container = container;
		this.logger = logger;
	}

	/**
	 * Speichert die registrierten Event-Listener geordnet nach Event-Klasse
	 */
	private Map<String, ArrayList<ListenerInterface>> listeners = new HashMap<String, ArrayList<ListenerInterface>>();

	/**
	 * Registriert einen neuen Event-Listener für eine Event-Klasse.
	 *
	 * @param eventClassName Name der Event-Klasse
	 * @param listener       Der Event-Listener der registriert werden soll.
	 */
	public void register(String eventClassName, ListenerInterface listener) {

		if (!this.listeners.containsKey(eventClassName)) {
			this.logger.println("Neues Event", eventClassName);
			this.listeners.put(eventClassName, new ArrayList<ListenerInterface>());
		}
		this.logger.println(
				"Registriere Event-Listener \""
						+ listener.getClass().getSimpleName()
						+ "\" for Event \""
						+ eventClassName
						+ "\""
		);
		this.listeners.get(eventClassName).add(listener);

	}

	/**
	 * Leitet ein Event an alle für die Event-Klasse registrierten Event-Listener weiter
	 *
	 * @param event - Event das an die Listener weitergeleitet werden soll.
	 */
	public void dispatch(EventInterface event) {
		String eventName = event.getClass().getSimpleName();
		this.logger.println("Dispatche Event \"" + eventName + "\"...");

		ArrayList<ListenerInterface> eventListeners = listeners.get(eventName);

		if (null == eventListeners) {
			this.logger.println(
					"Keine Listener für Event \""
							+ eventName
							+ "\" gefunden."
			);
			return;
		}

		for (ListenerInterface listener : eventListeners) {
			if (null == listener) {
				this.logger.println(
						"Leerer Eintrag für Event \""
								+ eventName
								+ "\"."
				);
			}
			else {
				this.logger.println(
						"Führe Event-Listener \""
								+ listener.getClass().getSimpleName()
								+ "\" aus...");
				try {
					listener.setEvent(event);
					new Thread(listener).start();
				} catch (Exception e) {

					this.logger.println(
							"Fehler! Ausführen von Listener \""
									+ listener.getClass().getSimpleName()
									+ "\" schlug fehl. ("
									+ e.getMessage()
									+ ")"
					);
				}
			}
		}
	}
}
