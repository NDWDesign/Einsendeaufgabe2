package common;

import client.ClientCore;
import client.Listeners.ConnectionEstablishedListener;
import common.Commands.CommandParserInterface;
import common.Commands.XmlCommandParser;
import common.Events.EventManager;
import common.Listeners.ListenerInterface;
import server.ServerCore;
import common.Listeners.ConnectionRequestListener;

import java.io.IOException;
import java.net.Socket;

/**
 * Factory - Erstellt Instanzen mit den dazugehörigen Abhängigkeiten
 *
 * @author Nils Daniel Wittwer
 * @todo Factory und DI-Container trennen (Instanzen in ApplicationState ablegen)
 */
public class Factory {

    private CommandFactory commandFactory;
    private ApplicationState applicationState;
    private ServerCore serverCore;
    private EventManager eventHandler;
    private Configuration configuration;
    private ConnectionRequestListener connectionRequestListener;
    private Connection connection;
    private ClientCore clientCore;
    private ConnectionEstablishedListener connectionEstablishedListener;
    private XmlCommandParser xmlCommandParser;

    public Factory(ApplicationState applicationState) {
        this.applicationState = applicationState;
    }

    public CommandFactory createCommandFactory(boolean createNew) {

        if (this.commandFactory == null || createNew) {
            this.commandFactory = new CommandFactory(
                    this.getApplicationState(),
                    this.applicationState.getOutput()
            );
        }
        return this.commandFactory;
    }

    public ServerCore createServerCore(boolean createNew) {
        if (this.serverCore == null || createNew) {
            this.serverCore = new ServerCore(
                    this.createEventManager(createNew),
                    this.getApplicationState().getOutput(),
                    Integer.parseInt(this.createConfiguration(createNew).get("ServerPort"))
            );
        }

        return this.serverCore;
    }

    public EventManager createEventManager(boolean createNew) {

        if (this.eventHandler == null || createNew) {
            this.eventHandler = new EventManager(
                    this.getApplicationState(),
                    this.getApplicationState().getOutput()
            );
        }

        return this.eventHandler;
    }

    public Configuration createConfiguration(boolean createNew) {

        if (this.configuration == null || createNew) {
            this.configuration = new Configuration();
        }

        return this.configuration;
    }

    public ConnectionRequestListener createClientConnectionHandler(boolean createNew) {

        if (this.connectionRequestListener == null || createNew) {
            this.connectionRequestListener = new ConnectionRequestListener(
                    this.getApplicationState(),
                    this.getApplicationState().getFactory(),
                    this.createEventManager(false),
                    this.getApplicationState().getOutput()
            );
        }

        return this.connectionRequestListener;
    }

    public ApplicationState getApplicationState() {
        return applicationState;
    }

    public Connection createConnection(Socket socket, boolean createNew) throws IOException {

        if (null == this.connection || createNew) {
            this.connection = new Connection(
                    this.getApplicationState(),
                    this.createCommandFactory(false),
                    this.createXmlCommandParser(false),
                    this.getApplicationState().getOutput(),
                    socket
            );
        }

        return this.connection;
    }

    private CommandParserInterface createXmlCommandParser(boolean createNew) {

        if (null == this.xmlCommandParser || createNew) {

            this.xmlCommandParser = new XmlCommandParser(
                    this.getApplicationState().getOutput()
            );
        }

        return this.xmlCommandParser;
    }

    public ClientCore createClientCore(boolean createNew) {

        if (null == this.clientCore || createNew) {

            this.clientCore = new ClientCore(
                    this.getApplicationState(),
                    this.createEventManager(false),
                    this.getApplicationState().getOutput()
            );
        }

        return this.clientCore;
    }

    public ListenerInterface createConnectionEstablishedListener(boolean createNew) {

        if (null == this.connectionEstablishedListener || createNew) {
            this.connectionEstablishedListener = new ConnectionEstablishedListener(
                    this.createCommandFactory(false),
                    this.getApplicationState().getOutput()
            );
        }

        return this.connectionEstablishedListener;
    }
}
