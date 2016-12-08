package common.Commands;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Nils Daniel Wittwer.
 * @version 08.12.2016
 */
public class XmlCommandParser implements CommandParserInterface {

	private final PrintStream output;

	public XmlCommandParser(PrintStream output) {

		this.output = output;
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
		this.output.println("XmlCommandParser.detectCommand(): Prüfe \""
				+ this.commandBuffer
				+ "\"..."
		);
		Boolean commandFound = this.commandDetected();
		if (commandFound) {
			this.output.println("XmlCommandParser.commandDetected(): Kommando wurde erkannt!");
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

		Pattern pattern = Pattern.compile("(?i)<command.*name=\"(?<commandName>.*)\".*>");
		Matcher matcher = pattern.matcher(this.commandBuffer);
		if (matcher.find()) {
			return matcher.group("commandName");
		}
		return null;
	}

	/**
	 * Liefert die Parameter des aktuellen Kommando-Puffers
	 */
	public ArrayList<String> getCommandParameters() {
		//ToDo Da stimmt noch was mit der RegEx nicht.
		Pattern pattern = Pattern.compile("(?i)(<parameter>)(?<parameterValue>.*?)(</parameter>)");
		Matcher matcher = pattern.matcher(this.commandBuffer);

		ArrayList<String> parameters = new ArrayList<String>();
		while (matcher.find()) {
			parameters.add(matcher.group("parameterValue"));
		}

		return parameters;
	}

	/**
	 * Löscht den aktuellen Kommando-Puffer
	 */
	public void flush() {
		this.commandBuffer = "";
		this.output.println("XmlCommandParser.flush(): Puffer zurückgesetzt \"" + this.commandBuffer + "\"");
	}
}
