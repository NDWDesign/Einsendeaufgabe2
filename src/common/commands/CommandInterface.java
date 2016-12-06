package common.commands;

import common.ApplicationState;
import common.connection.Connection;

import java.util.ArrayList;

/**
 * CommandInterface - Beschreibung eines Kommandos
 *
 * @author Nils Daniel Wittwer
 */
public interface CommandInterface {

	/** Führt das Kommando aus **/
	void execute();

	/** Sendet das Kommando über die enthaltene Verbindung */
	void send();

	/**
	 * Serialisiert eine Kommando so das es über einen Stream gesendet werden kann.
	 *
	 * @return Das serialisierte Kommando
	 */
	String serialize();

	/**
	 * Lädt die übergebenen Parameter in das Kommando
	 */
	void loadParameters(
			ApplicationState applicationState,
			Connection connection,
			ArrayList<String> parameters
	);
}
