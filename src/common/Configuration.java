package common;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Einsendeaufgabe2 04.12.2016
 *
 * @author Nils Daniel Wittwer
 */
class Configuration extends HashMap<String, String> {

	Configuration() {
		this.put("ServerPort", "3000");
	}
}
