package de.tud.et.ifa.agtele.notifier;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class Notifier <NotificationType, ListenerType extends IListener<NotificationType>> implements INotifier <NotificationType, ListenerType> {

	protected List<WeakReference<ListenerType>> listeners = new ArrayList<>();
	
	@Override
	public List<ListenerType> getListeners() {
		this.listeners.removeAll(this.listeners.parallelStream().filter(r -> r.get() == null).collect(Collectors.toList()));
		
		return this.listeners.parallelStream().map(r -> r.get()).collect(Collectors.toList());
	}

	@Override
	public void registerRegistrationChangeListener(ListenerType listener) {
		this.listeners.add(new WeakReference<>(listener));
	}

	@Override
	public void deregisterRegistrationChangeListener(ListenerType listener) {
		this.listeners.removeAll(this.listeners.parallelStream().filter(r -> r.get() == null || r.get() == listener).collect(Collectors.toList()));		
	}
}
