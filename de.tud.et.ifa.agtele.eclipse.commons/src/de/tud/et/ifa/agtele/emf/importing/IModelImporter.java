package de.tud.et.ifa.agtele.emf.importing;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;

public interface IModelImporter {

	IModelConnector getConnector();
	
	void setConnector(IModelConnector connector);
		
	default void importModel(Collection<EObject> target) {
		if (!this.getConnector().isConnected()) {
			this.getConnector().connect();
		}
		if (this.getConnector().isConnected()) {
					
			Collection<Object> rootNodes =  this.findRootContentNodes();
			
			for (Object node : rootNodes) {
				this.importRootContentNode(node, target);
			}	
			
			for (EObject obj : this.getCreatedEObjects()) {
				this.restoreAttributes(obj);
			}
			for (EObject obj : this.getCreatedEObjects()) {
				this.restoreReferences(obj);
			}
			
			for (EObject obj : this.getCreatedEObjects()) {
				this.postImport(obj);
			}			
			this.getConnector().disconnect();
		}
	}	

	default void importRootContentNode (Object node, Collection<EObject> target) {
		INodeDescriptor descriptor = this.getConnector().getTypeInfo(node);
		if (descriptor != null) {
			EObject contentElement = this.createEObject(descriptor);
			target.add(contentElement);
			this.importAllContents(contentElement);
		}
	}
	
	default void importAllContents (EObject element) {
		this.importContents(element);
		for(EObject obj : element.eContents()) {
			this.importAllContents(obj);
		}
	}
	
	default void init() {};
	
	IModelElementImportRegistry getImportRegistry();
	
	default EObject createEObject (INodeDescriptor descriptor) {
		EPackage.Registry registry = this.getEPackageRegistry();
		if (registry == null) {
			return null;
		}
		EPackage pkg = registry.getEPackage(descriptor.getNsUri());
		if (pkg == null) {
			return null;
		}
		EClassifier classifier = pkg.getEClassifier(descriptor.getTypeName());
		if (!(classifier instanceof EClass) || ((EClass)classifier).isAbstract()) {
			return null;
		}
		EObject obj = null;
		obj = pkg.getEFactoryInstance().create((EClass)classifier);
		
		this.getImportRegistry().registerImportedElement(descriptor.getNode(), obj);
		
		return obj;
	}
	
	default EObject getCreatedEObject (Object node) {
		return this.getImportRegistry().getImportedElement(node);
	}
	
	default Object getOriginalNode (EObject eObject) {
		return this.getImportRegistry().getOriginalElement(eObject);
	}

	default Collection<EObject> getCreatedEObjects() {
		return this.getImportRegistry().getImportedElements();
	}
			
	default void importContents(EObject eObject) {
		for (EReference ref : this.getImportStrategy(eObject.eClass()).getEContainmentsForImport(this, this.getConnector(), eObject)) {
			this.importContent(eObject, ref);
		}
	}
	
	default void restoreReferences(EObject eObject) {
		for (EReference ref : this.getImportStrategy(eObject.eClass()).getEReferencesForImport(this, this.getConnector(), eObject)) {
			this.restoreReference(eObject, ref);
		}
	}
	
	default void restoreAttributes(EObject eObject) {
		for (EAttribute attr : this.getImportStrategy(eObject.eClass()).getEAttributesForImport(this, this.getConnector(), eObject)) {
			this.restoreAttribute(eObject, attr);
		}
	}
	
	default void importContent(EObject eObject, EReference reference) {
		this.getImportStrategy(eObject.eClass(), reference)
			.importContent(this, this.getConnector(), eObject, reference);
	}

	default void restoreReference(EObject eObject, EReference reference) {
		this.getImportStrategy(eObject.eClass(), reference).restoreReference(this, this.getConnector(), eObject, reference);
	}
	
	default void restoreAttribute(EObject eObject, EAttribute attribute) {
		this.getImportStrategy(eObject.eClass(), attribute).restoreAttribute(this, this.getConnector(), eObject, attribute);
	}
	
	default void postImport(EObject eObject) {
		this.getImportStrategy(eObject.eClass()).postImport(this, this.getConnector(), eObject);
	}
	
	default Collection<Object> findRootContentNodes() {
		ArrayList<Object> result = new ArrayList<>();
		Collection<Object> content = this.getConnector().browse(null);
		if (content != null && !content.isEmpty()) {
			for (Object obj : content) {
				if (this.getConnector().isValidRootSearchNode(obj)) {
					result.addAll(this.findRootContentNodes(obj));					
				}
			}
		}
		return result;
	}

	default Collection<Object> findRootContentNodes(Object node) {		
		ArrayList<Object> result = new ArrayList<>();
		
		if (this.getConnector().getTypeInfo(node) != null) {
			result.add(node);
		} else {
			if (this.getConnector().isValidRootSearchNode(node)) {
				Collection<Object> content = this.getConnector().browse(node);
				for (Object obj : content) {
					result.addAll(this.findRootContentNodes(obj));
				}
			}
		}		
		return result;
	}
	
	final Map<EClass, IModelImportStrategy> globalImportStrategyRegistry = new HashMap<>();
			
	public static void registerGlobalImportStrategy(EClass cls, IModelImportStrategy strategy) {
		if (cls != null && strategy != null) {
			globalImportStrategyRegistry.put(cls, strategy);
		}
	}
	
	public static IModelImportStrategy getGlobalImportStrategy(EClass cls) {
		return globalImportStrategyRegistry.get(cls);
	}
	
	
	default IModelImportStrategy getImportStrategy(EClass cls) {
		if (this.getLocalImportStrategy(cls) != null) {
			return this.wrapImportStrategy(this.getLocalImportStrategy(cls));
		}
		if (IModelImporter.getGlobalImportStrategy(cls) != null) {
			return this.wrapImportStrategy(IModelImporter.getGlobalImportStrategy(cls));
		}
		return this.wrapImportStrategy(this.getDefaultImportStrategy());
	}
	
	default IModelImportStrategy getImportStrategy(EClass cls, EStructuralFeature feature) {
		if (this.getImportStrategy(feature.getEContainingClass()).unwrap()==this.getDefaultImportStrategy()) {
			return this.getImportStrategy(cls);
		}
		return this.getImportStrategy(feature.getEContainingClass());
	}

	default EPackage.Registry getEPackageRegistry() {
		return EPackage.Registry.INSTANCE;
	}
	
	public static final IModelImportStrategy DEFAULT_IMPORTSTRATEGY = new IModelImportStrategy() {};

	default IModelImportStrategy getDefaultImportStrategy() {
		return IModelImporter.DEFAULT_IMPORTSTRATEGY;
	}
		
	void setImportStrategyWrappers (IDelegatingModelImportStrategy[] wrappers);

	IDelegatingModelImportStrategy[] getImportStrategyWrappers();

	default IModelImportStrategy wrapImportStrategy(IModelImportStrategy strategy) {
		if (strategy == null) {
			return null;
		}
		IDelegatingModelImportStrategy[] wrappers = this.getImportStrategyWrappers();
		
		if (wrappers == null || wrappers.length == 0) {
			return strategy;		
		}
		IDelegatingModelImportStrategy currentStrategy = wrappers[wrappers.length -1].wrap(strategy);
		for (int i = wrappers.length - 2; i >= 0; i-=1) {
			if (currentStrategy == null) {
				break;
			}
			currentStrategy = wrappers[wrappers.length -1].wrap(currentStrategy);
		}
		return currentStrategy;
	}
	//TODO isValidContentNode -> get Eclass from NodeDescriptor

	void registerLocalImportStrategy(EClass cls, IModelImportStrategy strategy);
	
	IModelImportStrategy getLocalImportStrategy(EClass cls);
}
