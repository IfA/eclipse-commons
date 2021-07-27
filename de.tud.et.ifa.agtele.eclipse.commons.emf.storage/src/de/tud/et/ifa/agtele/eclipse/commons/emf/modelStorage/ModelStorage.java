/**
 */
package de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage;

import de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.IRegistrationChangeListener.RegistrationChangeNotification;
import de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.impl.ModelStorageImpl;
import de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.importAdapter.Connector;
import de.tud.et.ifa.agtele.emf.AgteleEcoreUtil;
import de.tud.et.ifa.agtele.emf.importing.IDelegatingModelImportStrategy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Model Storage</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.ModelStorage#getModel <em>Model</em>}</li>
 *   <li>{@link de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.ModelStorage#getConnector <em>Connector</em>}</li>
 *   <li>{@link de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.ModelStorage#getName <em>Name</em>}</li>
 * </ul>
 *
 * @see de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.ModelStoragePackage#getModelStorage()
 * @model
 * @generated
 */
public interface ModelStorage extends EObject {
	/**
	 * Returns the value of the '<em><b>Model</b></em>' containment reference list.
	 * The list contents are of type {@link de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.Model}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Model</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Model</em>' containment reference list.
	 * @see de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.ModelStoragePackage#getModelStorage_Model()
	 * @model containment="true" resolveProxies="true"
	 *        annotation="http://et.tu-dresden.de/ifa/emf/commons/2018/AgTeleGenModel get='\r\nif (model == null) {\r\n\tmodel = new &lt;%org.eclipse.emf.ecore.util.EObjectContainmentEList%&gt;.Resolving&lt;&lt;%de.tud.et.ifa.agtele.eclipse.emf.modelStorage.Model%&gt;&gt;(Model.class, this,\r\n\t\t\t&lt;%de.tud.et.ifa.agtele.eclipse.emf.modelStorage.ModelStoragePackage%&gt;.MODEL_STORAGE__MODEL);\r\n\tthis.eAdapters().add(new &lt;%org.eclipse.emf.ecore.util.EContentAdapter%&gt;() {\r\n\r\n\t\t@SuppressWarnings(\"rawtypes\")\r\n\t\t@Override\r\n\t\tpublic void notifyChanged(&lt;%org.eclipse.emf.common.notify.Notification%&gt; notification) {\r\n\t\t\tif (notification.getFeature() == ModelStoragePackage.Literals.MODEL_STORAGE__MODEL) {\r\n\t\t\t\tif (notification.getEventType() == Notification.ADD) {\r\n\t\t\t\t\tif (notification.getNewValue() instanceof Model) {\r\n\t\t\t\t\t\tModel model = (Model) notification.getNewValue();\r\n\t\t\t\t\t\tif (model.getResourceSet() != null) {\r\n\t\t\t\t\t\t\tModelStorageImpl.this.registerResourceSet(model.getResourceSet(), model);\r\n\t\t\t\t\t\t}\r\n\t\t\t\t\t}\r\n\t\t\t\t} else if (notification.getEventType() == Notification.ADD_MANY) {\r\n\t\t\t\t\tif (notification.getNewValue() instanceof &lt;%java.util.Collection%&gt;) {\r\n\t\t\t\t\t\tfor (Object element : (Collection) notification.getNewValue()) {\r\n\t\t\t\t\t\t\tif (element instanceof Model) {\r\n\t\t\t\t\t\t\t\tif (((Model) element).getResourceSet() != null) {\r\n\t\t\t\t\t\t\t\t\tModelStorageImpl.this.registerResourceSet(\r\n\t\t\t\t\t\t\t\t\t\t\t((Model) element).getResourceSet(), ((Model) element));\r\n\t\t\t\t\t\t\t\t}\r\n\t\t\t\t\t\t\t}\r\n\t\t\t\t\t\t}\r\n\t\t\t\t\t}\r\n\t\t\t\t} else if (notification.getEventType() == Notification.REMOVE) {\r\n\t\t\t\t\tif (notification.getOldValue() instanceof Model) {\r\n\t\t\t\t\t\tModel model = (Model) notification.getOldValue();\r\n\t\t\t\t\t\tif (model.getResourceSet() != null) {\r\n\t\t\t\t\t\t\tModelStorageImpl.this.registerResourceSet(model.getResourceSet(), null);\r\n\t\t\t\t\t\t}\r\n\t\t\t\t\t}\r\n\t\t\t\t} else if (notification.getEventType() == Notification.REMOVE_MANY) {\r\n\t\t\t\t\tif (notification.getOldValue() instanceof Collection) {\r\n\t\t\t\t\t\tfor (Object element : (Collection) notification.getOldValue()) {\r\n\t\t\t\t\t\t\tif (element instanceof Model) {\r\n\t\t\t\t\t\t\t\tif (((Model) element).getResourceSet() != null) {\r\n\t\t\t\t\t\t\t\t\tModelStorageImpl.this\r\n\t\t\t\t\t\t\t\t\t\t\t.registerResourceSet(((Model) element).getResourceSet(), null);\r\n\t\t\t\t\t\t\t\t}\r\n\t\t\t\t\t\t\t}\r\n\t\t\t\t\t\t}\r\n\t\t\t\t\t}\r\n\t\t\t\t}\r\n\t\t\t}\r\n\t\t}\r\n\t});\r\n}\r\nreturn model;'"
	 * @generated
	 */
	EList<Model> getModel();

	/**
	 * Returns the value of the '<em><b>Connector</b></em>' containment reference list.
	 * The list contents are of type {@link de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.importAdapter.Connector}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Connector</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Connector</em>' containment reference list.
	 * @see de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.ModelStoragePackage#getModelStorage_Connector()
	 * @model containment="true" resolveProxies="true"
	 * @generated
	 */
	EList<Connector> getConnector();

	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.ModelStoragePackage#getModelStorage_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.ModelStorage#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	Set<IModelContributor> getContributors();
	
	void addModelContributor(IModelContributor contributor);
	void removeModelContributor(IModelContributor contributor);

	ModelStorage DEFAULT_INSTANCE = ModelStorage.createDefaultInstance();
		
	void importFromUris(String[] uri);
	
	void update();
	
	void update(Collection<Model> models);
	
	void update(Model model);
	
	Model getModel(String uri);
	
	default Model resolveModelGlobal(String uri) {
		for (ModelStorage s : ModelStorageImpl.getAllModelStorages() ) {
			if (s.getModel(uri) != null) {
				return s.getModel(uri);
			}
		}
		return null;
	}

	LinkedModel addLinkedModel(String uri, ResourceSet set);
	
	void registerResourceSet(ResourceSet set, Model model);

	Model addModel(String uri);
	void removeModel(String uri);
	void removeModel(Model model);
	
	void setInitialized();
	
	boolean isInitialized();
	
	public static ModelStorage createDefaultInstance() {
		ModelStorage storage = ModelStorageFactory.eINSTANCE.createModelStorage();
		storage.setName("Default Model Storage");
		return storage;
	}
	
	public static Model getModelByResourceSet(ResourceSet set) {
		return ModelStorageImpl.getModelByResourceSet(set);
	}
		
	List<IResolveResult> resolve (String identifier);

	IDelegatingModelImportStrategy[] getDelegatingImportStrategies();
	
	default List<EObject> getAllContents () {
		ArrayList<EObject> result = new ArrayList<>();
		
		for (Model m : this.getModel()) {
			result.add(m);
			
			for (EObject o : m.getContent()) {
				result.add(o);
				result.addAll(AgteleEcoreUtil.getAllInstances(EcorePackage.Literals.EOBJECT, o));
			}			
		}
		
		return result;	
	}
	
	static List<ModelStorage> getAllModelStorages () {
		return ModelStorageImpl.getAllModelStorages();
	}
	
	static List<IResolveResult> resolveGlobal (String id) {
		ArrayList<IResolveResult> result = new ArrayList<>();
		
		for (ModelStorage storage : ModelStorage.getAllModelStorages()) {
			result.addAll(storage.resolve(id));
		}
		
		return result;
	}
	
	public default List<IModelContributor> getContributors(Model model) {
		ArrayList<IModelContributor> result = new ArrayList<>();
		
		result.addAll(this.getContributors().stream()
				.filter(c -> c.getContributedModels().contains(model))
				.collect(Collectors.toList()));
		
		result.sort((o1,o2) -> {
			if (o1.getContributorPriority() == o2.getContributorPriority()) {					
				return 0;
			}
			if (o1.getContributorPriority() < o2.getContributorPriority()) {
				return -1;
			}
			return 1;
		});
		return result;
	}
	
	public List<IRegistrationChangeListener> getRegistrationChangeListeners();
	
	public default void notifyChanged(RegistrationChangeNotification notification) {
		List<IRegistrationChangeListener> listeners = 
				this.getRegistrationChangeListeners().parallelStream().filter(l -> 
					(l.filterByChangeType() == null || l.filterByChangeType() == notification.getChangeType()) &&
					(l.filterByElement() == null || l.filterByElement().contains(notification.getElement())) &&
					(l.filterByModelStorage() == null || l.filterByModelStorage() == notification.getModelStorage()) &&
					(l.filterByModel() == null || l.filterByModel() == notification.getModel()) &&
					(l.filterById() == null || (new HashSet<>(notification.getIds()).removeAll(l.filterById())))
						).collect(Collectors.toList());
		for (IRegistrationChangeListener listener : listeners) {
			listener.notifyChanged(notification);
		}
		
		if (this != ModelStorage.DEFAULT_INSTANCE) {
			ModelStorage.DEFAULT_INSTANCE.notifyChanged(notification);
		}
	}
	
	public void registerRegistrationChangeListener(IRegistrationChangeListener listener);
	
	public default void registerGlobalRegistrationChangeListener(IRegistrationChangeListener listener) {
		ModelStorage.DEFAULT_INSTANCE.registerRegistrationChangeListener(listener);
	}
	
	public void deregisterRegistrationChangeListener(IRegistrationChangeListener listener);
	
	public default void deregisterGlobalRegistrationChangeListener(IRegistrationChangeListener listener) {
		ModelStorage.DEFAULT_INSTANCE.deregisterRegistrationChangeListener(listener);
	}

	public default Set<IModelContributor> getContributors(String res) {
		Set<IModelContributor> result = new LinkedHashSet<>();
		
		for (IModelContributor c : this.getContributors()) {
			for (Model m : c.getContributedModels()) {
				Resource r = m.getResourceSet().getResource(URI.createURI(res), false);
				if (r != null) {
					result.add(c);
				}
			}
		}
		
		return result;
	}

	public default void dispose() {
		this.removeAllModels();
	}

	public default void removeAllModels() {
		for (Model model : new ArrayList<>(this.getModel())) {
			this.removeModel(model);
		}
	}
} // ModelStorage
