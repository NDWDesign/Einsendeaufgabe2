package common.Commands;

import java.util.ArrayList;
import java.util.Objects;

/**
 * @author Nils Daniel Wittwer.
 * @created 08.12.2016
 */
public class XmlCommandParserTest {

	public static void main(String[] args) {

		ArrayList<String> expectedParameters = new ArrayList<String>();
		expectedParameters.add(0, "test");
		expectedParameters.add(1, "test2");
		expectedParameters.add(2, "test3");

		XmlCommandParser commandParser = new XmlCommandParser(System.out);
		CommandInterface testCommand = new TestCommand();
		testCommand.setParameters(expectedParameters);

		System.out.print("Teste XmlCommandParser.detectCommand(): ");
		if (!commandParser.detectCommand(testCommand.serialize())) {
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

		ArrayList<String> commandParameters = commandParser.getCommandParameters();
		if (expectedParameters.equals(commandParameters)) {
			System.out.println(" Ok");
		}
		else {
			System.out.println("Fehler! Erwartet: "+expectedParameters+" Gefunden: " + commandParameters);
		}
	}
}
