/**
 */
package de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage;

import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.swt.widgets.Display;


public interface IModelContributor {

	Set<Model> getContributedModels();

	int getContributorPriority();
	
	ModelStorage getModelStorage();
	
	public default void doRequestFocus() {}
	
	public default void requestFocus () {
		Display.getCurrent().asyncExec(new Runnable (){
			@Override
			public void run() {
				IModelContributor.this.doRequestFocus();
			}
		});
	}
	
	public default void doRequestFocus(EObject element) {
		this.doRequestSelect(element);
		this.doRequestFocus();		
	}

	public default void requestFocus(EObject element) {
		Display.getCurrent().asyncExec(new Runnable (){
			@Override
			public void run() {
				IModelContributor.this.doRequestFocus(element);
			}
		});
	}
	
	public default void doRequestSelect(EObject element) {}
	
	public default void requestSelect(EObject element) {
		Display.getCurrent().asyncExec(new Runnable (){
			@Override
			public void run() {
				IModelContributor.this.doRequestSelect(element);
			}
		});
	}
	
	public default void doRequestSelect(List<EObject> elements) {
		if (elements != null && !elements.isEmpty()) {
			this.doRequestSelect(elements.get(0));
		}
		
	}
	
	public default void requestSelect(List<EObject> elements) {
		Display.getCurrent().asyncExec(new Runnable (){
			@Override
			public void run() {
				IModelContributor.this.doRequestSelect(elements);
			}
		});
	}
	
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
