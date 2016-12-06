package client;

import common.ApplicationState;
import common.Factory;

/**
 * Snake Klient
 */
public class Client {
    public static void main(String[] args) {
        ApplicationState applicationState = new ApplicationState();
        applicationState.setOutput(System.out);
        applicationState.getOutput().println("Server.main(): Globaler Status erstellt.");

        applicationState.getOutput().println("Client.main(): Erstelle Factory...");
        Factory factory = new Factory(applicationState);

        applicationState.getOutput().println("Client.main(): Registriere Events...");
        new EventRegistry(factory, factory.createEventManager(false)).run();

        applicationState.getOutput().println("Server.main(): Starte ClientCore...");
        factory.createClientCore(false).run();
    }
}
