/**
 */
package de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage;

import java.util.List;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

public interface IResolveResult {
	
	default List<IModelContributor> getContributors() {
		return this.getModelStorage().getContributors(this.getModel());
	};

	Model getModel();

	default ModelStorage getModelStorage() {
		return this.getModel().getStorage();
	};

	EObject getElement();
	
	String getId();

} // IResolveResult
