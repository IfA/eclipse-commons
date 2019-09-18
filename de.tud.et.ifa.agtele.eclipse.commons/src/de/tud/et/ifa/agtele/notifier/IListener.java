package de.tud.et.ifa.agtele.notifier;

public interface IListener <NotificationType> {

	public void notifiy(NotificationType notification);
	
}
