package common.Events;

import common.Events.EventInterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Einsendeaufgabe2 04.12.2016
 *
 * @author Nils Daniel Wittwer
 */
public class EventHandler {

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
			this.listeners.put(eventClassName, new ArrayList<ListenerInterface>());
		}
		this.listeners.get(eventClassName).add(listener);

	}

	/**
	 * Leitet ein Event an alle für die Event-Klasse registrierten Event-Listener weiter
	 *
	 * @param event - Event das an die Listener weitergeleitet werden soll.
	 */
	public void dispatch(EventInterface event) {

		for (ListenerInterface listener : listeners.get(event.getClass().getSimpleName())) {
			listener.run(event);
		}
	}
}
