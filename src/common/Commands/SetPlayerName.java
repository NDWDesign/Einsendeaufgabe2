package common.Commands;

import java.io.PrintStream;
import java.util.ArrayList;

/**
 * Setzt den Spielernamen für die aktuelle Verbindung
 *
 * @author Nils Daniel Wittwer
 */
public class SetPlayerName extends AbstractXmlCommand {

    private final PrintStream output;

    public SetPlayerName(PrintStream output) {

        this.output = output;
    }

    @Override
    public void run() {

        this.output.println(
                "SetPlayerName.run(): Setzte Spielernamen von Verbindung "
                        + this.connection.getUid()
                        + "..."
        );

        this.connection.setPlayerName(this.getPlayerName());
    }

    public CommandInterface setPlayerName(String playerName) {
        this.parameters.add(0, playerName);
        return this;
    }

    public String getPlayerName() {
        if (this.parameters.isEmpty()) {
            return "";
        }
        return this.parameters.get(0);
    }
}
