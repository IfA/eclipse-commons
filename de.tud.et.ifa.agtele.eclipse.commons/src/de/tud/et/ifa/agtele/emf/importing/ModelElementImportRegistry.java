package de.tud.et.ifa.agtele.emf.importing;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;

public class ModelElementImportRegistry implements IModelElementImportRegistry {

	protected Map<Object,LinkedHashSet<EObject>> importedObjects = new HashMap<>(); //TODO there may be multiple imported objects per aas element
	protected Map<EObject,Object> originalNodes = new HashMap<>();
	
	
	@Override
	public void registerImportedElement(Object original, EObject imported) {
		LinkedHashSet<EObject> set = importedObjects.get(original);
		if (set == null) {
			set = new LinkedHashSet<>();
			importedObjects.put(original, set);
		}
		set.add(imported);
		this.originalNodes.put(imported, original);
	}
	@Override
	public void registerImportedElementUnique(Object original, EObject imported) {
		this.deregister(original);
		this.registerImportedElement(original, imported);
	}

	@Override
	public EObject getImportedElement(Object original) {
		Set<EObject> set = importedObjects.get(original);
		if (set != null) {
			return (new ArrayList<EObject>(set)).get(set.size()-1);
		}
		
		return null;
	}

	@Override
	public Object getOriginalElement(EObject imported) {
		return this.originalNodes.get(imported);
	}


	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void deregister(Object createdOrOriginal) {
		Object original = null;
		Set<EObject> imported = null;
		EObject oneImported = null;
		
		if (this.importedObjects.containsKey(createdOrOriginal)) {
			//an original was handed
			original = createdOrOriginal;
			imported = this.importedObjects.get(createdOrOriginal);
			this.importedObjects.remove(original);
			for (EObject imp : imported) {
				this.originalNodes.remove(imp);
			}
		} else if (this.originalNodes.containsKey(createdOrOriginal)) {
			//one of many imported elements was handed
			original = this.originalNodes.get(original);
			oneImported = (EObject)createdOrOriginal;
			imported = this.importedObjects.get(original);
			
			imported.remove(oneImported);
			if (imported.isEmpty() ) {
				this.importedObjects.remove(original);
			}
			this.originalNodes.remove(oneImported);			
		} else if (this.importedObjects.containsValue(createdOrOriginal)) {
			//a list of imported objects was specified
			imported = (Set)createdOrOriginal;
			for (Entry<Object,LinkedHashSet<EObject>>e : this.importedObjects.entrySet()) {
				if (e.getValue() == createdOrOriginal) {
					original = e.getKey();
				}
			}
			this.importedObjects.remove(original);
			for (EObject imp : imported) {
				this.originalNodes.remove(imp);
			}
		}
	}

	
	@Override
	public Collection<EObject> getImportedElements() {
		return this.originalNodes.keySet();
	}

	@Override
	public Collection<Object> getOriginalElements() {
		return this.importedObjects.keySet();
	}


	@Override
	public Set<EObject> getImportedElements(Object original) {
		if (this.importedObjects.containsKey(original)) {
			return this.importedObjects.get(original);
		}
		return null;
	}

	@Override
	public EObject getImportedElement(Resource res, Object original) {
		Set<EObject>set = this.getImportedElements(original);
		if (set != null) {
			for (EObject obj : set) {
				if (obj.eResource() == res) {
					return obj;
				}
			}
		}
		return null;
	}

}
