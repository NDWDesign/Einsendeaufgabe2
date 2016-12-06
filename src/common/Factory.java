package common;

import client.ClientCore;
import common.Events.EventManager;
import common.connection.Connection;
import server.ServerCore;
import common.connection.ConnectionRequestHandler;

import java.io.IOException;
import java.net.Socket;

/**
 * Einsendeaufgabe2 04.12.2016
 *
 * @author Nils Daniel Wittwer
 */
public class Factory {

    private CommandFactory commandFactory;
    private ApplicationState applicationState;
    private ServerCore serverCore;
    private EventManager eventHandler;
    private Configuration configuration;
    private ConnectionRequestHandler connectionRequestHandler;
    private Connection connection;
    private ClientCore clientCore;

    public Factory(ApplicationState applicationState) {
        this.applicationState = applicationState;
    }

    public CommandFactory createCommandFactory(boolean createNew) {

        if (this.commandFactory == null || createNew) {
            this.commandFactory = new CommandFactory(this.getApplicationState());
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
            this.eventHandler = new EventManager(this.getApplicationState());
        }

        return this.eventHandler;
    }

    public Configuration createConfiguration(boolean createNew) {

        if (this.configuration == null || createNew) {
            this.configuration = new Configuration();
        }

        return this.configuration;
    }

    public ConnectionRequestHandler createClientConnectionHandler(boolean createNew) {

        if (this.connectionRequestHandler == null || createNew) {
            this.connectionRequestHandler = new ConnectionRequestHandler(
                    this.getApplicationState(),
                    this.getApplicationState().getFactory(),
                    this.createEventManager(false),
                    this.getApplicationState().getOutput()
            );
        }

        return this.connectionRequestHandler;
    }

    public ApplicationState getApplicationState() {
        return applicationState;
    }

    public Connection createConnection(Socket socket, boolean createNew) throws IOException {

        if (null == this.connection || createNew) {
            this.connection = new Connection(
                    this.getApplicationState(),
                    this.createCommandFactory(false),
                    this.getApplicationState().getOutput(),
                    socket
            );
        }

        return this.connection;
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
}
