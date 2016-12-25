package common.Commands;

import common.Loggers.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Nils Daniel Wittwer.
 * @version 08.12.2016
 */
public class XmlCommandParser implements CommandParserInterface {

	private final Logger logger;

	public XmlCommandParser(Logger logger) {

		this.logger = logger;
	}

	private String commandBuffer = "";

	/**
	 * Speichert den übergebenen String im Kommando-Puffer und erkennt ob ein Kommando enthalten ist.
	 *
	 * @param string Zu testender String
	 *
	 * @return True: Kommando erkannt
	 */
	public boolean detectCommand(String string) {

		this.commandBuffer += string;
		this.logger.println("Prüfe ", this.commandBuffer);
		Boolean commandFound = this.commandDetected();
		if (commandFound) {
			this.logger.println("Kommando erkannt.");
		}
		return commandFound;
	}

	/**
	 * @return true: Es wurde ein Kommando erkannt.
	 */
	public boolean commandDetected() {
		Pattern pattern = Pattern.compile("(?ims)<command.*</command>");
		Matcher matcher = pattern.matcher(this.commandBuffer);
		return matcher.find();
	}

	/**
	 * Liefert den Namen des Kommandos im aktuellen Kommando-Puffer
	 */
	public String getCommandName() {

		Pattern pattern = Pattern.compile("(?i)<command.*?name=\"(?<commandName>.*?)\".*?>");
		Matcher matcher = pattern.matcher(this.commandBuffer);
		if (matcher.find()) {
			return matcher.group("commandName");
		}
		return null;
	}

	/**
	 * Liefert die Parameter des aktuellen Kommando-Puffers
	 */
	public HashMap<String, String> getCommandParameters() {
		Pattern pattern = Pattern.compile(
				"(?i)<parameter.*?=\"(?<parameterName>.*?)\">(?<parameterValue>.*?)</parameter>");
		Matcher matcher = pattern.matcher(this.commandBuffer);

		HashMap<String, String> parameters = new HashMap<String, String>();
		while (matcher.find()) {
			parameters.put(matcher.group("parameterName"), matcher.group("parameterValue"));
		}

		return parameters;
	}

	/**
	 * Serialisiert ein Kommando als XML-String
	 *
	 * @param command Das zu serialisierende Kommando
	 * @return XML-Repräsentation des Kommandos
	 */
	public String serialize(CommandInterface command) {

		String commandString = "<command name=\""
				+ command.getClass().getSimpleName() +
				"\">";

		HashMap<String, String> parameters = command.getParameters();
		if (!parameters.isEmpty()) {
			for (Map.Entry<String, String> entry : parameters.entrySet()) {
				commandString += "<parameter name=\""
						+ entry.getKey() + "\">"
						+ entry.getValue()
						+ "</parameter>";
			}
		}
		commandString += "</command>";

		return commandString;
	}
	/**
	 * Löscht den aktuellen Kommando-Puffer
	 */
	public void flush() {
		this.commandBuffer = "";
		this.logger.println("Puffer zurückgesetzt.");
	}
}
