package de.tud.et.ifa.agtele.ui.emf.edit.action.filter;

import java.util.List;

import org.eclipse.emf.ecore.EObject;

public interface IItemProviderCreateActionFilter extends ICompoundCreateActionFilter {
	
	public List<ICreateActionFilter> getFilters(EObject selection);
	
	
}
