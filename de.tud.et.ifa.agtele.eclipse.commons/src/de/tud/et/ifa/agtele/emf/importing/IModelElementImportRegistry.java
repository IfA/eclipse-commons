package de.tud.et.ifa.agtele.emf.importing;

import java.util.Collection;

import org.eclipse.emf.ecore.EObject;

public interface IModelElementImportRegistry {
	void registerImportedElement (Object original, EObject imported);
	EObject getImportedElement (Object original);
	Object getOriginalElement (EObject imported);
	
	void deregister (Object createdOrOrigninal);
	
	Collection<EObject> getImportedElements ();
}
