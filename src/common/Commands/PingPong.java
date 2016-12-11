package common.Commands;

import common.Events.ConnectionEstablished;
import common.Events.EventInterface;
import common.Listeners.ListenerInterface;
import common.Loggers.Logger;

import java.io.PrintStream;
import java.util.Date;

/**
 * PingPong Kommando - Führt sich selbst auf der Gegenseite der Verbindung aus
 *
 * @author Nils Daniel Wittwer.
 * @version 07.12.2016
 */
public class PingPong extends AbstractXmlCommand implements ListenerInterface {

	private final Logger logger;

	public PingPong(Logger logger) {

		this.logger = logger;

		this.parameters.put("receivedTime", "0");
		this.parameters.put("sendTime", "0");
		this.parameters.put("count", "2");

	}

	@Override
	public void run() {

		Long currentTime = new Date().getTime();

		if (this.getReceivedTime() > 0) {
			Long latency = (currentTime - this.getReceivedTime());
			this.logger.println("Latenz: " + latency + " ms.");
			this.connection.setLatency(latency);
		}

		this.setReceivedTime(this.getSendTime());
		this.setSendTime(currentTime);

		if (this.getCount() > 0) {
			this.setCount(this.getCount() - 1);
			this.send();
		}
	}

	public long getReceivedTime() {
		return Long.parseLong(this.parameters.get("receivedTime"));
	}

	public PingPong setReceivedTime(long time) {
		this.parameters.put("receivedTime", String.valueOf(time));
		return this;
	}

	public long getSendTime() {
		return Long.parseLong(this.parameters.get("sendTime"));
	}

	public PingPong setSendTime(long time) {
		this.parameters.put("sendTime", String.valueOf(time));
		return this;
	}

	public PingPong setCount(int count) {
		this.parameters.put("count", Integer.toString(count));
		return this;
	}

	public int getCount() {
		return Integer.parseInt(this.parameters.get("count"));
	}

	@Override
	public ListenerInterface setEvent(EventInterface event) {
		this.setConnection(
				((ConnectionEstablished) event)
						.getConnection()
		);
		return this;
	}
}
