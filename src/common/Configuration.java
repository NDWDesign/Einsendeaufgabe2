package common;

import java.util.HashMap;

/**
 * @Todo ggf. Werte aus Konfigurationsdatei laden
 * Einsendeaufgabe2 04.12.2016
 *
 * @author Nils Daniel Wittwer
 */
public class Configuration extends HashMap<String, Object> {

    Configuration() {
        // Port der für Verbindungen genutzt wird
        this.put("ServerPort", 3000);
        // Initiale Länge eines Wurms
        this.put("InitialWormLength", 1);
        // Wiederholungsrate zur Berechnung des Spielstatus in Hz (nicht genau da Berechnungsdauer hinzu kommt)
        this.put("GameRefreshRate", 30);
    }
}
