package common;

import java.util.HashMap;

/**
 * Einsendeaufgabe2 04.12.2016
 *
 * @author Nils Daniel Wittwer
 */
public class Configuration extends HashMap<String, Object> {

	Configuration() {
		this.put("ServerPort", 3000);
	}
}
