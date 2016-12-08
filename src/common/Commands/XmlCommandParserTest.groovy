package common.Commands

/**
 * @author Nils Daniel Wittwer.
 * @created 08.12.2016
 */
public class XmlCommandParserTest {

    public static void main(String[] args) {

        XmlCommandParser commandParser = new XmlCommandParserTest();
        SetPlayerName testCommand = new SetPlayerName(System.out);
        testCommand.setPlayerName('test')

        if (!commandParser.detectCommand(testCommand.serialize())) {
            System.out.println("XmlCommandParser.detectCommand(): Gültiges Kommando wurde nicht erkannt!");
        } else {
            System.out.println("XmlCommandParser.detectCommand(): Ok");
        }

        if (!commandParser.commandDetected()) {
            System.out.println("XmlCommandParser.commandDetected(): Hätte true zurück geben sollen!");
        } else {
            System.out.println("XmlCommandParser.commandDetected(): Ok");
        }

        System.out.println("Teste XmlCommandParser.getCommandName()...");
        if ("SetPlayerName" == commandParser.getCommandName()) {
            System.out.println("Ok");
        } else {
            System.out.println("Fehler!");
        }
    }
}
