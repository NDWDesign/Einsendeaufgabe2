package common;

import java.util.HashMap;

/**
 * Singleton-Container
 *
 * @author Nils Daniel Wittwer.
 * @created 10.12.2016
 */
public class Container {

    /**
     * Speichert Instanzen für angeforderte Klassen
     **/
    private HashMap instances = new HashMap<Class, Object>();

    /**
     * Erstellt neue Instanzen
     **/
    private Factory factory;


    public static void main(String[] argv) {

        // Neuen Container erstellen
        Container container = new Container();

        // Instanz von Test anfordern
        Test t1 = container.get(Test.class);
        t1.value = 23;

        // Instanz von Test anfordern, sollte Instanz wie in t1 sein.
        Test t2 = container.get(Test.class);
        System.out.println("Diese beiden Werte sollten gleich sein: \nt1.value = " + t1.value + ", \nt2.value = " + t1.value);
    }

    /**
     * Container referenziert eine Factory die neue Instanzen erstellt
     **/
    private Container() {
        this.factory = new Factory();
    }

    /**
     * Liefert die gespeicherte Instanz der angeforderten Klasse oder erstelle eine neue wenn keine vorhanden ist.
     * <p>
     * *****************************************************************************************************************
     * **     Frage: Wie schreibe ich Parametertyp und Rückgabetyp so,                                                **
     * **     dass nicht für jede Klasse eine Kopie dieser Methode benötigt wird?                                     **
     * *****************************************************************************************************************
     **/
    private Test get(Class<Test> className) {
        if (!this.instances.containsKey(className)) {
            this.instances.put(className, this.factory.create(className));
        }
        return (Test) this.instances.get(className);
    }

    /**
     * Erstellt neue Instanzen, hat eine Methode für jede Klasse
     **/
    private class Factory {

        /**
         * Methode um eine neue Instanz von Test zu erstellen
         **/
        Test create(Class<Test> testClass) {
            return new Test();
        }
    }

    /**
     * Eine Beispiel-Klasse
     */
    private class Test {

        int value = 0;

        Test() {
            System.out.println("Dieser Text sollte nur einmal ausgegeben werden!\n");
        }
    }
}


