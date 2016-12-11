package common.Commands;

import common.Loggers.Logger;

import java.io.PrintStream;

/**
 * Setzt den Spielernamen für die aktuelle Verbindung
 *
 * @author Nils Daniel Wittwer
 */
public class SetPlayerName extends AbstractXmlCommand {

    private final Logger logger;

    public SetPlayerName(Logger logger) {

        this.logger = logger;
        this.parameters.put("playerName", "");
    }

    @Override
    public void run() {

        this.logger.println(
                "Setzte Spielernamen von Verbindung "
                        + this.connection.getUid()
                        + "..."
        );

        this.connection.setPlayerName(this.getPlayerName());
    }

    public CommandInterface setPlayerName(String playerName) {
        this.parameters.put("playerName", playerName);
        return this;
    }

    public String getPlayerName() {
        if (this.parameters.isEmpty()) {
            return "";
        }
        return this.parameters.get("playerName");
    }
}
