package common.Commands;

import common.Connection;

import java.util.HashMap;
import java.util.Map;

/**
 * Kodiert Kommandos in XML
 */
abstract public class AbstractXmlCommand implements CommandInterface {

	protected HashMap<String, String> parameters = new HashMap<String, String>();
	protected Connection connection;

	public String serialize() {

		String commandString = "<command name=\""
				+ this.getClass().getSimpleName() +
				"\">";

		if (!this.parameters.isEmpty()) {
			for (Map.Entry<String, String> entry : this.parameters.entrySet()) {
				commandString += "<parameter name=\""
						+ entry.getKey() + "\">"
						+ entry.getValue()
						+ "</parameter>";
			}
		}
		commandString += "</command>";

		return commandString;
	}

	public CommandInterface loadParameters(HashMap<String, String> parameters) {

		System.out.println(parameters);
		parameters.forEach(this::setParameter);

		return this;
	}

	private void setParameter(String parameterName, String parameterValue) {

		this.parameters.put(parameterName, parameterValue);
	}

	public CommandInterface setConnection(Connection connection) {

		this.connection = connection;
		return this;
	}

	public Connection getConnection() {

		return this.connection;
	}

	public void send() {
		this.connection.send(this);
	}
}
