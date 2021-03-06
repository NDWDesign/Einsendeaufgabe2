package common.Commands;

import common.Connection;

import java.util.HashMap;

/**
 * CommandInterface - Beschreibung eines Kommandos
 *
 * @author Nils Daniel Wittwer
 */
public interface CommandInterface {

    /**
     * Führt das Kommando aus
     **/
    void run();

    /**
     * Sendet das Kommando über die enthaltene Verbindung
     */
    void send();

    /**
     * Lädt die übergebenen Parameter in das Kommando
     *
     * @param parameters Die Parameter die in das Kommando geladen werden sollen.
     * @return this
     */
    CommandInterface loadParameters(
            HashMap<String, String> parameters
    );

    /**
     * @return Die Parameter des Kommandos
     */
    HashMap<String, String> getParameters();

    /**
     * Setzt die Verbindung zu der das Kommando gehört
     *
     * @param connection Die Verbindung zu der das Kommando gehört
     * @return this
     */
    CommandInterface setConnection(Connection connection);
}
