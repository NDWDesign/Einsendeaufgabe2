package common.commands;

import common.ApplicationState;
import common.connection.Connection;

import java.util.ArrayList;

/**
 * Setzt den Spielernamen für die aktuelle Verbindung
 *
 * @author Nils Daniel Wittwer
 */
public class SetPlayerName implements CommandInterface {

	private ApplicationState applicationState;
	private Connection connection;
	private String playerName;

	@Override
	public void execute() {
		this.connection.setPlayerName(this.playerName);
	}

	@Override
	public String serialize() {
		return "<command name=\"SetPlayerName\">\n"
				+ "<parameter>"
				+ this.playerName
				+ "</parameter>"
				+ "</command>";
	}

	@Override
	public void loadParameters(
			ApplicationState applicationState, Connection connection, ArrayList<String> parameters
	) {
		this.playerName = parameters.get(0);
	}

	public SetPlayerName setPlayerName(String playerName) {
		this.playerName = playerName;
		return this;
	}

	public SetPlayerName setConnection(Connection connection) {
		this.connection = connection;
		return this;
	}

	public void send() {
		this.connection.send(this);
	}
}
