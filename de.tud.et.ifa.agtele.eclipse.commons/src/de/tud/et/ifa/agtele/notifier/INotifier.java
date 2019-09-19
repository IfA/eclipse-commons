package de.tud.et.ifa.agtele.notifier;

import java.util.List;

public interface INotifier <NotificationType, ListenerType extends IListener<NotificationType>> {

	List<ListenerType> getListeners();

	void addListener(ListenerType listener);

	void removeListener(ListenerType listener);

	public default List<ListenerType> getRelevantListeners (NotificationType notification) {
		return this.getListeners();
	}
	
	public default void notifiyListeners (NotificationType notification) {
		this.getRelevantListeners(notification).forEach(l -> l.notifiy(notification));
	}
}
