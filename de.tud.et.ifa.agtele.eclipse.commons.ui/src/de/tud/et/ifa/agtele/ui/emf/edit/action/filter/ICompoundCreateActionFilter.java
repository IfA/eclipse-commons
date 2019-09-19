package de.tud.et.ifa.agtele.ui.emf.edit.action.filter;

import java.util.List;

public interface ICompoundCreateActionFilter extends ICreateActionFilter, CreateActionChangeListener {
	public List<ICreateActionFilter> getSubFilters();
	
	public default void addSubFilter(ICreateActionFilter filter) {
		this.addSubFilter(filter, null);
	}
	
	public default void removeSubFilter(ICreateActionFilter filter) {
		this.removeSubFilter(filter, null);
	}
	
	public default void addSubFilters(List<ICreateActionFilter> filter) {
		this.addSubFilters(filter, null);
	}
	
	public default void removeSubFilters(List<ICreateActionFilter> filters) {
		this.removeSubFilters(filters, null);
	}
	
	public void addSubFilter(ICreateActionFilter filter, FilterChangedNotification notification);
	
	public void removeSubFilter(ICreateActionFilter filter, FilterChangedNotification notification);
	
	public default void addSubFilters(List<ICreateActionFilter> filters, FilterChangedNotification notification) {
		FilterChangedNotification myNotification = notification != null ? notification : new FilterChangedNotification();
		for (ICreateActionFilter filter: filters) {
			this.addSubFilter(filter, myNotification);
		}
		if (notification == null) {
			this.dispatchNotification(myNotification);
		}		
	}
	
	public default void removeSubFilters(List<ICreateActionFilter> filters, FilterChangedNotification notification) {
		FilterChangedNotification myNotification = notification != null ? notification : new FilterChangedNotification();
		for (ICreateActionFilter filter: filters) {
			this.removeSubFilter(filter, myNotification);
		}
		if (notification == null) {
			this.dispatchNotification(myNotification);
		}		
	}
	
	public boolean isChangeAllowed(ICreateActionFilter filter);
	
	public void notifySubFilterChange(ICreateActionFilter filter, FilterChangedNotification notification);
}
