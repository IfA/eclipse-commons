package de.tud.et.ifa.agtele.ui.emf.edit.action.filter;

import java.util.Set;

import org.eclipse.jface.dialogs.IDialogSettings;

import de.tud.et.ifa.agtele.notifier.Notifier;

public abstract class CreateActionFilter extends Notifier<FilterChangedNotification, CreateActionChangeListener> implements ICreateActionFilter {
	
	protected String context = null;
	protected ICompoundCreateActionFilter owner;
	protected boolean active = false;
	
	@Override
	public String getContext() {
		return this.context;
	}
	
	@Override
	public void setContext(String context) {
		this.context = context;
	}
	
	@Override
	public void dispatchNotification(FilterChangedNotification notification) {
		Set<ICreateActionFilter> filtersToNotifiy = notification.getChangedFilters();
		filtersToNotifiy.forEach(f -> f.notifiyListeners(notification));
	}
	
	@Override
	public void notifiyListeners(FilterChangedNotification notification) {
		if (!notification.getChangedFilters().isEmpty()) {			
			super.notifiyListeners(notification);			
		}
	}

	public ICompoundCreateActionFilter getOwner() {
		return this.owner;
	}
	
	public void setOwner(ICompoundCreateActionFilter owner) {
		this.owner = owner;
	}
	

	@Override
	public void persist(IDialogSettings settings) {
		this.doPersist(settings);
	}

	@Override
	public void restore(IDialogSettings settings) {
		this.doRestore(settings);
	}
	
	@Override
	public boolean isActive() {
		return this.active;
	}
	
	@Override
	public void setActive(boolean active, FilterChangedNotification notification) {
		FilterChangedNotification myNotification = notification != null ? notification : new FilterChangedNotification();
		if (active != this.active) {
			this.active = active;
			myNotification.addChangedFilter(this);
		}
		if (notification == null) {
			this.dispatchNotification(myNotification);
		}
	}
}
