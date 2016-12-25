package common.Commands;

import common.Loggers.Logger;

import java.util.HashMap;

/**
 * @author Nils Daniel Wittwer.
 * @created 08.12.2016
 */
public class XmlCommandParserTest {

	public static void main(String[] args) {

		HashMap<String, String> expectedParameters = new HashMap<String, String>();
		expectedParameters.put("0", "test");
		expectedParameters.put("1", "test2");
		expectedParameters.put("2", "test3");

		XmlCommandParser commandParser = new XmlCommandParser(new Logger());
		CommandInterface testCommand = new TestCommand();
		testCommand.loadParameters(expectedParameters);

		System.out.print("Teste XmlCommandParser.detectCommand(): ");
		if (!commandParser.detectCommand(commandParser.serialize(testCommand))) {
			System.out.println("Fehler! Gültiges Kommando wurde nicht erkannt!");
		}
		else {
			System.out.println("XmlCommandParser.detectCommand(): Ok");
		}

		System.out.print("Teste ");
		if (!commandParser.commandDetected()) {
			System.out.println("XmlCommandParser.commandDetected(): Hätte true zurück geben sollen!");
		}
		else {
			System.out.println("XmlCommandParser.commandDetected(): Ok");
		}

		System.out.print("Teste XmlCommandParser.getCommandName(): ");
		if ("TestCommand".equals(commandParser.getCommandName())) {
			System.out.println("Ok");
		}
		else {
			System.out.println("Fehler!");
		}

		System.out.print("Teste XmlCommandParser.getCommandParameters():");

		HashMap<String, String>  commandParameters = commandParser.getCommandParameters();
		if (expectedParameters.equals(commandParameters)) {
			System.out.println(" Ok");
		}
		else {
			System.out.println("Fehler! Erwartet: "+expectedParameters+" Gefunden: " + commandParameters);
		}
	}
}
