package common.Commands;

import java.util.HashMap;

/**
 * @author Nils Daniel Wittwer.
 * @created 08.12.2016
 */
public interface CommandParserInterface {

    /**
     * Fügt den übergebenen String dem Kommando-Puffer hinzu und erkennt ob ein Kommando enthalten ist
     * @param string Der zu prüfende String.
     * @return True: Kommando erkannt
     */
    public boolean detectCommand(String string);

    /**
     * @return Der Name des Kommandos im aktuellen Kommando-Puffer
     */
    public String getCommandName();

    /**
     * @return Die im aktuellen Kommando-Puffer enthaltenen Parameter
     */
    public HashMap<String, String> getCommandParameters();

    /**
     * Löscht den Kommando-Puffer
     */
    public void flush();

    /**
     * @return true: Es wurde ein Kommando erkannt
     */
    boolean commandDetected();
}
