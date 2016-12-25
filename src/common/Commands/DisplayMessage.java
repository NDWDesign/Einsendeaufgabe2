package common.Commands;

import common.Loggers.Logger;

/**
 * @author Nils Daniel Wittwer.
 * @created 25.12.2016
 */
public class DisplayMessage extends AbstractCommand {

    private final Logger logger;

    public DisplayMessage(Logger logger) {
        this.logger = logger;
        this.parameters.put("message", "");
    }

    @Override
    public void run() {
        this.logger.println(this.parameters.get("message"));
    }

    public void setMessage(String message) {
        this.parameters.put("message", message);
    }
}
