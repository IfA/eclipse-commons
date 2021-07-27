/**
 */
package de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage;

import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;


public interface IModelContributor {

	Set<Model> getContributedModels();

	int getContributorPriority();
	
	ModelStorage getModelStorage();
	
	public default void requestFocus() {}

	public default void requestFocus(EObject element) {}
	
	public default void requestSelect(EObject element) {}
	
	public default void requestSelect(List<EObject> element) {}
	
	public boolean isWorkbenchPart();

	/**
	 * Request to load a resource and to set focus to the given id afterwards.
	 * @param m
	 * @param id
	 * @return if the request is going to be handled 
	 */
	public default boolean requestLoad(Model m, String id) {
		return false;
	}
	
} // IModelContributor
