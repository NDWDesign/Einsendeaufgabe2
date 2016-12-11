package common.Commands;

import common.Loggers.Logger;

/**
 * CommandFactory - Erstellt eine neue Instanz eines Kommandos.
 *
 * @author Nils Daniel Wittwer
 */
public class CommandFactory {

    private final Logger logger;

    public CommandFactory(Logger logger) {

        this.logger = logger;
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
                command = new Disconnect(this.logger);

            } else if (commandName.equals(SetPlayerName.class.getSimpleName())) {
                command = new SetPlayerName(this.logger);
            } else if (commandName.equals(PingPong.class.getSimpleName())) {
                command = new PingPong(this.logger);
            }

        } catch (Exception e) {
            this.logger.println("Fehler! \"" + commandName + "\" konnte nicht instantiiert werden!");
            e.getStackTrace();
        }

        return command;
    }
}
