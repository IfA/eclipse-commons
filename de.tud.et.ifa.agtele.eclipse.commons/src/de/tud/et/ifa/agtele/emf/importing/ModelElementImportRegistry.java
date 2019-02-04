package de.tud.et.ifa.agtele.emf.importing;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;

public class ModelElementImportRegistry implements IModelElementImportRegistry {

	protected Map<Object,EObject> importedObjects = new HashMap<>();
	protected Map<EObject,Object> originalNodes = new HashMap<>();
	
	
	@Override
	public void registerImportedElement(Object original, EObject imported) {
		this.importedObjects.put(original, imported);
		this.originalNodes.put(imported, original);
	}


	@Override
	public EObject getImportedElement(Object original) {
		return this.importedObjects.get(original);
	}


	@Override
	public Object getOriginalElement(EObject imported) {
		return this.originalNodes.get(imported);
	}


	@Override
	public void deregister(Object createdOrOrigninal) {
		Object original = null;
		EObject imported = null;
		
		if (this.importedObjects.containsKey(createdOrOrigninal)) {
			original = createdOrOrigninal;
			imported = this.importedObjects.get(original);
		} else if (this.originalNodes.containsKey(createdOrOrigninal)) {
			imported = (EObject)createdOrOrigninal;
			original = this.originalNodes.get(imported);
		} else {
			return;
		}
		this.originalNodes.remove(imported);
		this.importedObjects.remove(original);
	}


	@Override
	public Collection<EObject> getImportedElements() {
		return this.originalNodes.keySet();
	}

}
