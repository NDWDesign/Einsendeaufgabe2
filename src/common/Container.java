package common;

import common.Loggers.Logger;

import java.io.PrintStream;
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


	/**
	 * Liefert die gespeicherte Instanz der angeforderten Klasse oder erstelle eine neue wenn keine vorhanden ist.
	 **/
	public <T> T get(Class<T> classType) {
		if (!this.instances.containsKey(classType)) {
			T instance = this.factory.create(classType);
			if (null == instance) {
				System.out.println(
						"FEHLER!  Container.get(): \""
								+ classType.getCanonicalName()
								+ "\" in Factory \""
								+ this.factory.getClass().getCanonicalName()
								+ "\" nicht bekannt!"
				);
				return null;
			}
			this.instances.put(
					classType,
					instance
			);
		}
		return (T) this.instances.get(classType);
	}

	/**
	 * Speichert die übergebenen Instanz.
	 */
	public void set(Object instance) {
		this.instances.put(
				instance.getClass(),
				instance
		);
	}

	/**
	 * Speichert die übergebenen Instanz.
	 */
	public void set(Class classType, Object instance) {
		this.instances.put(
				classType,
				instance
		);
	}

	public void setFactory(Factory factory) {
		this.factory = factory;
	}
}


