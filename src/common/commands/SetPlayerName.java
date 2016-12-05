package common.commands;

import common.ApplicationState;
import server.connection.ClientConnection;

import java.util.ArrayList;

/**
 * Setzt den Spielernamen für die aktuelle Verbindung
 *
 * @author Nils Daniel Wittwer
 */
public class SetPlayerName implements CommandInterface {

	private ApplicationState applicationState;
	private ClientConnection clientConnection;
	private String playerName;

	@Override
	public void execute() {
		this.clientConnection.setPlayerName(this.playerName);
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
			ApplicationState applicationState, ClientConnection clientConnection, ArrayList<String> parameters
	) {
		this.playerName = parameters.get(0);
	}
}
