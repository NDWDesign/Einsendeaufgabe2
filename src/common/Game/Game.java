package common.Game;

import common.Commands.CommandFactory;
import common.Commands.DisplayMessage;
import common.Configuration;
import common.Connection;

import java.rmi.server.UID;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

/**
 * @author Nils Daniel Wittwer.
 * @created 25.12.2016
 */
public class Game extends Thread {

    private final Configuration configuration;
    private final CommandFactory commandFactory;
    private UID id;
    private HashMap<Connection, Worm> worms;
    private Board board;

    public Game(Configuration configuration, CommandFactory commandFactory) {

        this.configuration = configuration;
        this.commandFactory = commandFactory;
        this.id = new UID();
    }

    public void run() {
        while (!this.isInterrupted()) {
            this.process();
            try {
                Thread.sleep(1000 / (int) this.configuration.get("GameRefreshRate"));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    /**
     * @ToDo Implementieren!
     * Führt einen Berechnungsschritt für das Spiel aus.
     */
    private void process() {
    }

    /**
     * Fügt eine Spielerverbindung zum Spiel hinzu und initialisiert sie.
     *
     * @param connection Verbindung die dem Spiel hinzugefügt werden soll.
     */
    public void join(Connection connection) {

        this.worms.put(
                connection,
                new Worm(
                        connection.getPlayerName(),
                        this.generateStartPosition(),
                        (int) this.configuration.get("InitialWormLength")
                )
        );

        DisplayMessage command = (DisplayMessage) this.commandFactory.createCommand("displayMessage");
        command.setMessage("Neuer Spieler: " + connection.getPlayerName());
        this.broadcast(command);
    }

    /**
     * Sendet ein Kommando an alle Verbindungen
     *
     * @param command Das Kommando das an die Verbindungen gesendet werden soll.
     */
    private void broadcast(DisplayMessage command) {
        for (Map.Entry<Connection, Worm> worm : this.worms.entrySet()) {
            worm.getKey().send(command);
        }
    }

    /**
     * @return Eine gültige Startposition
     * @Todo Implementieren!
     */
    private Vector<Float> generateStartPosition() {

        return new Vector<Float>();
    }
}
