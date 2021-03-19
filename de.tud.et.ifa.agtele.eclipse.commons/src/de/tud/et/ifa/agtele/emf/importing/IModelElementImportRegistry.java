package de.tud.et.ifa.agtele.emf.importing;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;

public interface IModelElementImportRegistry {
	
	void registerImportedElement (Object original, EObject imported);
	void registerImportedElement (Object original, EObject imported, EObject context);
	void registerImportedElementUnique (Object original, EObject imported);
	void registerImportedElementUnique (Object original, EObject imported, EObject context);
	EObject getImportedElement (Object original);
	Set<EObject> getImportedElements(Object original);
	EObject getImportedElement (Resource res, Object original);
	Object getOriginalElement (EObject imported);
	
	void deregister (Object createdOrOriginal);
	
	Collection<EObject> getImportedElements ();
	Collection<Object> getOriginalElements ();
	void setContext(EObject contentElement);
	Collection<EObject> getContexts();
	EObject getContextOfImported(EObject element);
	Collection<EObject> getImportedElementsOfContext(EObject context);
	Map<EObject, EObject> getImportedElementsByContext(Object original);
	EObject getImportedElement(Object original, EObject context);
	EObject getCurrentContext();
	
	boolean containsOriginal(Object original);
	boolean containsImported(Object imported);
	
}
