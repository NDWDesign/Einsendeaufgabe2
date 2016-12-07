package common.Listeners;

import common.Events.EventInterface;

/**
 * Einsendeaufgabe2 04.12.2016
 *
 * @author Nils Daniel Wittwer
 */
public interface ListenerInterface extends Runnable{

	ListenerInterface setEvent(EventInterface event);
}
