package common.Commands;

import common.Connection;

import java.util.ArrayList;

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
     * Serialisiert eine Kommando so das es über einen Stream gesendet werden kann.
     *
     * @return Das serialisierte Kommando
     */
    String serialize();

    /**
     * Lädt die übergebenen Parameter in das Kommando
     *
     * @param parameters Die Parameter die in das Kommando geladen werden sollen.
     * @return this
     */
    CommandInterface setParameters(
            ArrayList<String> parameters
    );

    /**
     * Setzt die Verbindung zu der das Kommando gehört
     *
     * @param connection Die Verbindung zu der das Kommando gehört
     * @return this
     */
    CommandInterface setConnection(Connection connection);
}
