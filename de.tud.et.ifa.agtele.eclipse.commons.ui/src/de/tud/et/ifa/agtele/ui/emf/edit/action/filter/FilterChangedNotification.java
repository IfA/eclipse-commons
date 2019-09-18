package de.tud.et.ifa.agtele.ui.emf.edit.action.filter;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class FilterChangedNotification {
	
	protected Set<ICreateActionFilter> filters = new LinkedHashSet<>();;

	public FilterChangedNotification ( Set<ICreateActionFilter> filters) {
		this.filters.addAll(filters);
	}
	
	public FilterChangedNotification() {
	}

	public Set<ICreateActionFilter> getChangedFilters() {
		return this.filters;
	}
	
	public void addChangedFilter(ICreateActionFilter filter) {
		this.filters.add(filter);
	}
}
