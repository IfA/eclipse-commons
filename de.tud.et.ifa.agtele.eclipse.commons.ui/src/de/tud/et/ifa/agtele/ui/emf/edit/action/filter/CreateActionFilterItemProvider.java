package de.tud.et.ifa.agtele.ui.emf.edit.action.filter;

import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

public interface CreateActionFilterItemProvider {
	
	public default List<ICreateActionFilter> getFilter(EObject element, Map<EClass, List<ICreateActionFilter>> cache) {
		if (this.isFilterReuseAllowed(element) && this.getCacheKey(element) != null && cache.containsKey(this.getCacheKey(element)) ) {
			return cache.get(this.getCacheKey(element));
		}
		List<ICreateActionFilter> result = this.doGetFilter(element);
		if (this.isFilterReuseAllowed(element) && this.getCacheKey(element) != null && result != null && !result.isEmpty()) {
			cache.put(this.getCacheKey(element), result);
		}
		return result;
	}
	
	public List<ICreateActionFilter> doGetFilter(EObject element);
	
	public default boolean isFilterReuseAllowed(EObject element) {
		return this.getCacheKey(element) != null;
	}
	
	public default EClass getCacheKey(EObject element) {
		return element != null ? element.eClass() : null;
	}
}
