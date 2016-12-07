package common;

import common.Commands.*;

import java.io.PrintStream;

/**
 * CommandFactory - Erstellt eine neue Instanz eines Kommandos.
 *
 * @author Nils Daniel Wittwer
 */
public class CommandFactory {

    private final ApplicationState applicationState;
    private final PrintStream output;

    CommandFactory(ApplicationState applicationState, PrintStream output) {

        this.applicationState = applicationState;
        this.output = output;
    }

    /**
     * Erstellt eine neue Instanz eines Kommandos.
     *
     * @param commandName - Name des Kommandos.
     * @return CommandInterface
     */
    public CommandInterface createCommand(
            String commandName
    ) {

        CommandInterface command = null;
        try {
            if (commandName.equals(Disconnect.class.getSimpleName())) {
                command = new Disconnect(this.applicationState.getOutput());

            } else if (commandName.equals(SetPlayerName.class.getSimpleName())) {
                command = new SetPlayerName(this.applicationState.getOutput());
            }

        } catch (Exception e) {
            applicationState.getOutput().println("CommandFactory.createCommand(): Fehler: \"" + commandName + "\" konnte nicht instantiiert werden!");
            e.getStackTrace();
        }

        return command;
    }
}
