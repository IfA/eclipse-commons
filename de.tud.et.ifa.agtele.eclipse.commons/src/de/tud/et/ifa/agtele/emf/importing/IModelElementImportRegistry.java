package de.tud.et.ifa.agtele.emf.importing;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;

public interface IModelElementImportRegistry {
	
	void registerImportedElement (Object original, EObject imported);
	void registerImportedElementUnique (Object original, EObject imported);
	EObject getImportedElement (Object original);
	Set<EObject> getImportedElements(Object original);
	EObject getImportedElement (Resource res, Object original);
	Object getOriginalElement (EObject imported);
	
	void deregister (Object createdOrOriginal);
	
	Collection<EObject> getImportedElements ();
	Collection<Object> getOriginalElements ();
}
