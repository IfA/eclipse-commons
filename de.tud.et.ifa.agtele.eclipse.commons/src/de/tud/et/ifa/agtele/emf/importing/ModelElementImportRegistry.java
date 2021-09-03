package de.tud.et.ifa.agtele.emf.importing;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;

public class ModelElementImportRegistry implements IModelElementImportRegistry {

	protected Map<Object,LinkedHashSet<EObject>> importedObjects = new ConcurrentHashMap<>();
	protected Map<EObject,Object> originalNodes = new ConcurrentHashMap<>();
	protected EObject creationContext;
	
	protected Map<EObject, Set<EObject>> contextMap = new ConcurrentHashMap<>();

	@Override
	public void registerImportedElement(Object original, EObject imported, EObject context) {	
		LinkedHashSet<EObject> set = importedObjects.get(original);
		if (set == null) {
			set = new LinkedHashSet<>();
			importedObjects.put(original, set);
		}
		set.add(imported);
		this.originalNodes.put(imported, original);
		if (context != null) {
			if (!this.contextMap.containsKey(context)) {
				this.contextMap.put(context, new HashSet<>());
			}
			this.contextMap.get(context).add(imported);
		}
	}
	@Override
	public void registerImportedElement(Object original, EObject imported) {
		this.registerImportedElement(original, imported, this.creationContext);
	}
	@Override
	public void registerImportedElementUnique(Object original, EObject imported, EObject context) {		
		this.deregister(original);
		this.registerImportedElement(original, imported, context);
	}
	@Override
	public void registerImportedElementUnique(Object original, EObject imported) {
		this.registerImportedElementUnique(original, imported, this.creationContext);
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
	public EObject getImportedElement(Object original, EObject context) {
		Map<EObject, EObject> byContext = this.getImportedElementsByContext(original);		
		return byContext.get(context);
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
				
				//clear from context map
				if (this.contextMap.containsKey(imp)) {
					this.contextMap.remove(imp);
				}
				EObject context = this.getContextOfImported(imp);
				if (context != null) {
					Collection<EObject> importedByContext = this.contextMap.get(context);
					importedByContext.remove(imp);
					if (importedByContext.isEmpty()) {
						this.contextMap.remove(context);
					}
				}
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
			
			//clear from context map
			if (this.contextMap.containsKey(oneImported)) {
				this.contextMap.remove(oneImported);
			}
			EObject context = this.getContextOfImported(oneImported);
			if (context != null) {
				Collection<EObject> importedByContext = this.contextMap.get(context);
				importedByContext.remove(oneImported);
				if (importedByContext.isEmpty()) {
					this.contextMap.remove(context);
				}
			}
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
				
				//clear from context map
				if (this.contextMap.containsKey(imp)) {
					this.contextMap.remove(imp);
				}
				EObject context = this.getContextOfImported(imp);
				if (context != null) {
					Collection<EObject> importedByContext = this.contextMap.get(context);
					importedByContext.remove(imp);
					if (importedByContext.isEmpty()) {
						this.contextMap.remove(context);
					}
				}
			}
		}
		
	}
	
	@Override
	public Collection<EObject> getContexts() {
		return this.contextMap.keySet();
	}
	
	@Override
	public Collection<EObject> getImportedElementsOfContext(EObject context) {
		return this.contextMap.get(context);
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
	public EObject getContextOfImported(EObject element) {
		for (Entry<EObject, Set<EObject>> entry : this.contextMap.entrySet()) {
			if (entry.getValue().contains(element)) {
				return entry.getKey();
			}
		}
		return null;
	}
	
	@Override
	public Map<EObject, EObject> getImportedElementsByContext (Object original) {
		Collection<EObject> imported = this.getImportedElements(original);
		Map<EObject, EObject> result = new HashMap<>();
		
		if (imported == null) {
			return result;
		}
		
		for (EObject i : imported) {
			EObject context = this.getContextOfImported(i);
			if (context != null) {
				result.put(context, i);
			}			
		}
		
		return result;
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
	@Override
	public void setContext(EObject modelRoot) {
		this.creationContext = modelRoot;	
		if (modelRoot != null && !this.contextMap.containsKey(modelRoot)) {
			this.contextMap.put(modelRoot, new HashSet<>());
		}
	}

	@Override
	public EObject getCurrentContext () {
		return this.creationContext;
	}
	@Override
	public boolean containsOriginal(Object original) {
		return this.importedObjects.containsKey(original);
	}
	@Override
	public boolean containsImported(Object imported) {
		return this.originalNodes.containsKey(imported);
	}
}
