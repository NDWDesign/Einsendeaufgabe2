package server;

import common.ApplicationState;
import common.Events.EventManager;
import common.Factory;

/**
 * Snake Server
 *
 * @author Nils Daniel Wittwer
 */
public class Server {

    public static void main(String[] args) {

        ApplicationState applicationState = new ApplicationState();
        applicationState.setOutput(System.out);
        applicationState.getOutput().println("Server.main(): Globaler Status erstellt.");

        applicationState.getOutput().println("Server.main(): Erstelle Factory...");
        Factory factory = new Factory(applicationState);
		applicationState.setFactory(factory);

        applicationState.getOutput().println("Server.main(): Registriere Event-Listeners...");
        new EventRegistry(factory, factory.createEventManager(false)).run();

        applicationState.getOutput().println("Server.main(): Starte ServerCore...");
        factory.createServerCore(false).run();
    }
}
