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
			applicationState.getOutput().println("EventManager.register(): New EventName \"" + eventClassName + "\"");
			this.listeners.put(eventClassName, new ArrayList<ListenerInterface>());
		}
		applicationState.getOutput().println(
				"EventManager.register(): Registering listner \""
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
		applicationState.getOutput()
		                .println("EventManager.dispatch(): Dispatching \"" + event.getClass().getSimpleName() + "\"");
		try {
			for (ListenerInterface listener : listeners.get(event.getClass().getSimpleName())) {
				applicationState.getOutput()
				                .println("EventManager.dispatch(): Running \"" + listener.getClass()
				                                                                         .getSimpleName() + "\"");
				listener.run(event);
			}
		} catch (Exception e) {

			this.output.println(
					"EventManager.dispatch(): Fehler! Konnte keine Einträge für Event \""
							+ event.getClass().getSimpleName()
							+ "\"finden!"
			);
		}
	}
}
