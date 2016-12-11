package common.Loggers;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Naiver Logger
 *
 * @author Nils Daniel Wittwer
 */
public class Logger {

	/**
	 * Gibt die übergebenen Nachricht mit Uhrzeit, Aufrufender Klasse, Methode und Zeilennummer aus.
	 *
	 * @param message Die auszugebende Nachricht
	 */
	public void println(Object message) {

		this.printLog(message);
	}

	/**
	 * Gibt die übergebenen Nachricht mit Uhrzeit, Aufrufender Klasse, Methode und Zeilennummer aus.
	 * Außerdem die String Reprästentation des übergebenen Objekts
	 *
	 * @param message Nachricht
	 * @param object  Objekt
	 */
	public void println(Object message, Object object) {

		this.printLog(
				message
						+ " [" + object.getClass().getSimpleName()
						+ ": " + object + "]"
		);
	}

	private void printLog(Object message) {

		Calendar calendar = new GregorianCalendar();
		StackTraceElement caller = Thread.currentThread().getStackTrace()[3];

		System.out.println(
				calendar.get(GregorianCalendar.HOUR_OF_DAY) + ":"
						+ calendar.get(GregorianCalendar.MINUTE) + ":"
						+ calendar.get(GregorianCalendar.SECOND) + ":"
						+ calendar.get(GregorianCalendar.MILLISECOND) + "  "
						+ caller.getClassName()
						+ "." + caller.getMethodName() + "(): "
						+ message
						+ " [Line: " + caller.getLineNumber() + "] "
		);
	}
}
