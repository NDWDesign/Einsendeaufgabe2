package common.Events;

import common.ApplicationState;
import common.Events.EventInterface;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Einsendeaufgabe2 04.12.2016
 *
 * @author Nils Daniel Wittwer
 */
public class EventManager {

	private final ApplicationState applicationState;
	private final PrintStream output;

	public EventManager(ApplicationState applicationState, PrintStream output) {

		this.applicationState = applicationState;
		this.output = output;
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
			this.output.println("EventManager.register(): Neues Event \"" + eventClassName + "\"");
			this.listeners.put(eventClassName, new ArrayList<ListenerInterface>());
		}
		this.output.println(
				"EventManager.register(): Registeriere Event-Listner \""
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
		this.output.println("EventManager.dispatch(): Dispatche Event \"" + event.getClass()
		                                                                         .getSimpleName() + "\"...");
		try {
			for (ListenerInterface listener : listeners.get(event.getClass().getSimpleName())) {
				if (null == listener) {
					this.output.println(
							"EventManager.dispatch(): Leerer Eintrag für Event \""
							+ event.getClass().getSimpleName()
							+ "\""
					);
				}
				else {
					this.output.println(
							"EventManager.dispatch(): Führe Event-Listener \""
									+ listener.getClass().getSimpleName()
									+ "\" aus...");
					listener.run(event);
				}
			}
		} catch (Exception e) {

			this.output.println(
					"EventManager.dispatch(): Fehler beim Dispatchen von Event \""
							+ event.getClass().getSimpleName()
							+ "\" ("
							+ e.getMessage()
							+ ")"
			);
		}
	}
}
