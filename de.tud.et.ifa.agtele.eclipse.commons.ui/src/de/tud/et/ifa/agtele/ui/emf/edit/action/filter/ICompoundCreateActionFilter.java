package de.tud.et.ifa.agtele.ui.emf.edit.action.filter;

import java.util.List;

public interface ICompoundCreateActionFilter extends ICreateActionFilter {
	public List<ICreateActionFilter> getSubFilters();
	
	public void addSubFilter(ICreateActionFilter filter);
	
	public void removeSubFilter(ICreateActionFilter filter);
	
	public boolean isChangeAllowed(ICreateActionFilter filter);
	
	public void notifySubFilterChange(ICreateActionFilter filter, FilterChangedNotification notification);
}
