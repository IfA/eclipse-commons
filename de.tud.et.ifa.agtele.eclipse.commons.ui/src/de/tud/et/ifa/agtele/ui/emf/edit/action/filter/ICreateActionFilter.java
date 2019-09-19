package de.tud.et.ifa.agtele.ui.emf.edit.action.filter;

import java.util.List;

import org.eclipse.emf.edit.command.CommandParameter;
import org.eclipse.jface.dialogs.IDialogSettings;

import de.tud.et.ifa.agtele.notifier.INotifier;
import de.tud.et.ifa.agtele.ui.interfaces.IPersistable;

public interface ICreateActionFilter extends IPersistable, INotifier<FilterChangedNotification, CreateActionChangeListener> {
	public boolean isActive();
	
	public default int getFilterState () {
		return this.isActive() ? 1 : 0;
	}
	
	public default void setFilterState (int state) {
		if (state == 0) {
			this.deactivate();
		} else {
			this.activate();
		}
	}
	
	public default void setActive(boolean active) {
		if (this.isChangeAllowed()) {
			this.setActive(active, null);
		}
	}
	public void setActive(boolean active, FilterChangedNotification notification);
	
	public default void activate() {
		if (this.isChangeAllowed()) {
			this.setActive(true, null);			
		}
	}
	
	public default void deactivate() {
		if (this.isChangeAllowed()) {
			this.setActive(false, null);
		}
	}
	
	public default void activate(FilterChangedNotification notification) {
		this.setActive(true, notification);
	}
	
	public default void deactivate(FilterChangedNotification notification) {
		this.setActive(false, notification);
	}
	
	public String getContext();
	
	public void setContext(String context);
	
	public default void doPersist(IDialogSettings settings) {
		settings.put("ACTIVE", this.isActive());
		settings.put("FILTER_STATE", this.getFilterState());
	}
	
	public default void doRestore(IDialogSettings settings) {
		if (settings.getBoolean("ACTIVE")) {
			this.setActive(true, null);
		}
		try {
			int filterState = settings.getInt("FILTER_STATE");	
			this.setFilterState(filterState);		
		} catch (NumberFormatException e) {
			this.setFilterState(this.isActive() ? 1 : 0);		
		}
	}
	
	public ICompoundCreateActionFilter getOwner();
	
	public void setOwner(ICompoundCreateActionFilter owner);
	
	public default boolean isChangeAllowed () {
		if (this.getOwner() != null) {
			return this.getOwner().isChangeAllowed(this);
		}
		return true;
	}
	
	public List<CommandParameter> filterCommands (List<? extends CommandParameter> currentFilterState, List<? extends CommandParameter> originalCommands);
//	
//	public default List<? extends CommandParameter> getCreateChildActions(List<? extends CommandParameter> actions) {
//		ArrayList<CommandParameter> result = new ArrayList<>();	
//		if (actions == null) {
//			return result;
//		}	
//		for (IAction action : actions) {
//			if (action instanceof IExtendedCreateElementAction) {
//				result.add((IExtendedCreateElementAction)action);
//			}			
//		}
//		return result;
//	}

	public void dispatchNotification(FilterChangedNotification notification);
}
