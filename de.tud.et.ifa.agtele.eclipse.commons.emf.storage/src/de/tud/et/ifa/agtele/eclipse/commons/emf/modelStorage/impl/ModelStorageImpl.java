/**
 */
package de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.impl;

import java.lang.ref.WeakReference;

import de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.IModelContributor;
import de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.IRegistrationChangeListener;
import de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.IResolveResult;
import de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.LinkedModel;
import de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.Model;
import de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.ModelStorage;
import de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.ModelStorageFactory;
import de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.ModelStoragePackage;
import de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.importAdapter.Connector;
import de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.importAdapter.ImportAdapter;
import de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.importAdapter.ImportAdapterFactory;
import de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.util.IdRegistrationDelegatingImportStrategy;
import de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.util.IdentifierRegistrationDelegatingImportStrategy;
import de.tud.et.ifa.agtele.emf.importing.IDelegatingModelImportStrategy;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.stream.Collectors;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EContentAdapter;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Model Storage</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.impl.ModelStorageImpl#getModel <em>Model</em>}</li>
 *   <li>{@link de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.impl.ModelStorageImpl#getConnector <em>Connector</em>}</li>
 *   <li>{@link de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.impl.ModelStorageImpl#getName <em>Name</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ModelStorageImpl extends UpdateableElementImpl implements ModelStorage {
		
	public static final WeakHashMap<ResourceSet, Model> resourceSetToModel = new WeakHashMap<>();
	
	/**
	 * The cached value of the '{@link #getModel() <em>Model</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getModel()
	 * @generated
	 * @ordered
	 */
	protected EList<Model> model;

	/**
	 * The cached value of the '{@link #getConnector() <em>Connector</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getConnector()
	 * @generated
	 * @ordered
	 */
	protected EList<Connector> connector;
	
	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
    protected String name = NAME_EDEFAULT;

	protected List<WeakReference<IModelContributor>> contributors = new ArrayList<>();

	protected boolean initialized = false;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT in order to keep track of this model storage instance
	 */
	protected ModelStorageImpl() {
		super();
		ModelStorageImpl.addToModelStorage(this);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ModelStoragePackage.Literals.MODEL_STORAGE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Model> getModel() {	
	
		
		if (model == null) {
			model = new EObjectContainmentEList.Resolving<de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.Model>(
					Model.class, this,
					de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.ModelStoragePackage.MODEL_STORAGE__MODEL);
			this.eAdapters().add(new EContentAdapter() {
		
				@SuppressWarnings("rawtypes")
				@Override
				public void notifyChanged(Notification notification) {
					if (notification.getFeature() == ModelStoragePackage.Literals.MODEL_STORAGE__MODEL) {
						if (notification.getEventType() == Notification.ADD) {
							if (notification.getNewValue() instanceof Model) {
								Model model = (Model) notification.getNewValue();
								if (model.getResourceSet() != null) {
									ModelStorageImpl.this.registerResourceSet(model.getResourceSet(), model);
								}
							}
						} else if (notification.getEventType() == Notification.ADD_MANY) {
							if (notification.getNewValue() instanceof Collection) {
								for (Object element : (Collection) notification.getNewValue()) {
									if (element instanceof Model) {
										if (((Model) element).getResourceSet() != null) {
											ModelStorageImpl.this.registerResourceSet(
													((Model) element).getResourceSet(), ((Model) element));
										}
									}
								}
							}
						} else if (notification.getEventType() == Notification.REMOVE) {
							if (notification.getOldValue() instanceof Model) {
								Model model = (Model) notification.getOldValue();
								if (model.getResourceSet() != null) {
									ModelStorageImpl.this.registerResourceSet(model.getResourceSet(), null);
								}
							}
						} else if (notification.getEventType() == Notification.REMOVE_MANY) {
							if (notification.getOldValue() instanceof Collection) {
								for (Object element : (Collection) notification.getOldValue()) {
									if (element instanceof Model) {
										if (((Model) element).getResourceSet() != null) {
											ModelStorageImpl.this
													.registerResourceSet(((Model) element).getResourceSet(), null);
										}
									}
								}
							}
						}
					}
				}
			});
		}
		return model;
	}
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Connector> getConnector() {	
	
		if (connector == null) {
			connector = new EObjectContainmentEList.Resolving<Connector>(Connector.class, this, ModelStoragePackage.MODEL_STORAGE__CONNECTOR);
		}
		return connector;
	}
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getName() {	
	
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setName(String newName) {
	
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelStoragePackage.MODEL_STORAGE__NAME, oldName, name));
	
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ModelStoragePackage.MODEL_STORAGE__MODEL:
				return ((InternalEList<?>)getModel()).basicRemove(otherEnd, msgs);
			case ModelStoragePackage.MODEL_STORAGE__CONNECTOR:
				return ((InternalEList<?>)getConnector()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ModelStoragePackage.MODEL_STORAGE__MODEL:
				return getModel();
			case ModelStoragePackage.MODEL_STORAGE__CONNECTOR:
				return getConnector();
			case ModelStoragePackage.MODEL_STORAGE__NAME:
				return getName();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case ModelStoragePackage.MODEL_STORAGE__MODEL:
				getModel().clear();
				getModel().addAll((Collection<? extends Model>)newValue);
				return;
			case ModelStoragePackage.MODEL_STORAGE__CONNECTOR:
				getConnector().clear();
				getConnector().addAll((Collection<? extends Connector>)newValue);
				return;
			case ModelStoragePackage.MODEL_STORAGE__NAME:
				setName((String)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case ModelStoragePackage.MODEL_STORAGE__MODEL:
				getModel().clear();
				return;
			case ModelStoragePackage.MODEL_STORAGE__CONNECTOR:
				getConnector().clear();
				return;
			case ModelStoragePackage.MODEL_STORAGE__NAME:
				setName(NAME_EDEFAULT);
				return;
		}
		super.eUnset(featureID);
	}

 	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case ModelStoragePackage.MODEL_STORAGE__MODEL:
				return model != null && !model.isEmpty();
			case ModelStoragePackage.MODEL_STORAGE__CONNECTOR:
				return connector != null && !connector.isEmpty();
			case ModelStoragePackage.MODEL_STORAGE__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuilder result = new StringBuilder(super.toString());
		result.append(" (name: ");
		result.append(name);
		result.append(')');
		return result.toString();
	}

	@Override
	public void importFromUris(String[] uris) {
		ArrayList<Model> modelsToUpdate = new ArrayList<>();
		if (uris == null) {
			return;
		}
		for (String uri : uris) {
			if (this.getModel(uri) == null) {
				Model model = ModelStorageFactory.eINSTANCE.createModel();
				model.setUri(uri);
				this.getModel().add(model);				
				modelsToUpdate.add(model);
			} else {
				modelsToUpdate.add(this.getModel(uri));
			}
		}
		this.update(modelsToUpdate);
	}
	
	@Override
	public void update() {
		this.update(this.getModel());
	}
	
	@Override
	public IDelegatingModelImportStrategy[] getDelegatingImportStrategies () {
		return new IDelegatingModelImportStrategy[] {new IdentifierRegistrationDelegatingImportStrategy(),new IdRegistrationDelegatingImportStrategy()} ;
	}
		
	@Override
	public synchronized void update(Collection<Model> models) {
		this.setInitialized();
		ArrayList<Model> processingModels = new ArrayList<>();
		for (Model model : models) {
			if (this.getModel().contains(model)) {
				model.resetContent();
				processingModels.add(model);
			}
		}
		this.setUpdating(true);
		processingModels.parallelStream().forEach(model -> {	
			if (model instanceof LinkedModel) {
				((LinkedModel)model).initialize();
				return;
			}
			try {
				model.setUpdating(true);
				Connector connector = ImportAdapterFactory.eINSTANCE.createConnector(model.getUri());
				if (connector == null) {
					System.err.println("Could not load model, because no import adapter has been found for uri '" + model.getUri() +"'");
					return;
				}				
				ImportAdapter adapter = (ImportAdapter)connector.createImportAdapter();
				adapter.setImportStrategyWrappers(this.getDelegatingImportStrategies());
				model.setImportAdapter(adapter);
				adapter.importModel();
				model.setInitialized();
			} catch (Exception e) {
				synchronized(this) {
					e.printStackTrace();
				}
			}
			model.setUpdating(false);
		});
		this.setUpdating(false);
	}
	
	
	@Override
	public void update(Model model) {
		Collection<Model> models = new ArrayList<>();
		models.add(model);
		this.update(models);
	}

	@Override
	public Model getModel(String uri) {
		for (Model model : this.getModel()) {
			if (model.getUri().equals(uri)) {
				return model;
			}
		}
		return null;
	}
	
	@Override
	public Model addModel(String uri) {
		if (this.getModel(uri) == null) {
			Model model = ModelStorageFactory.eINSTANCE.createModel();
			model.setUri(uri);
			this.getModel().add(model);
		}		
		return this.getModel(uri);
	}
	
	@Override
	public LinkedModel addLinkedModel(String uri, ResourceSet set) {
		if (this.getModel(uri) == null) {
			LinkedModel model = ModelStorageFactory.eINSTANCE.createLinkedModel();
			model.setUri(uri);
			this.getModel().add(model);
			model.setResourceSet(set);
		}		
		return (LinkedModel) this.getModel(uri);
	}

	@Override
	public void removeModel(String uri) {
		if (this.getModel(uri) != null) {
			this.getModel().remove(this.getModel(uri));		
		}
	}

	@Override
	public void setInitialized() {
		this.initialized = true;		
	}

	@Override
	public boolean isInitialized() {
		return this.initialized;
	}
	
	public static Model getModelByResourceSet(ResourceSet set) {
		return ModelStorageImpl.resourceSetToModel.get(set);
	}

	protected HashMap<String, ArrayList<EObject>> elementRegistry = new HashMap<>(); 	
	
	@Override
	public List<IResolveResult> resolve(String identifier) {
		ArrayList<IResolveResult> result = new ArrayList<>();
		for (Model m : this.getModel()) {
			result.addAll(m.resolve(identifier));
		}
		return result;
	}

	protected static Collection<WeakReference<ModelStorage>> ALL_STORAGES = new ArrayList<>();
	
	public static synchronized void addToModelStorage(ModelStorage storage) {
		if (ModelStorageImpl.ALL_STORAGES == null) {
			ModelStorageImpl.ALL_STORAGES = new ArrayList<>();
		}
		ModelStorageImpl.ALL_STORAGES.add(new WeakReference<>(storage));
	}
	
	public static List<ModelStorage> getAllModelStorages() {
		ArrayList<ModelStorage> result = new ArrayList<>();
		for (WeakReference<ModelStorage> ref : new ArrayList<>(ModelStorageImpl.ALL_STORAGES)) {
			if (ref.get() == null) {
				ModelStorageImpl.ALL_STORAGES.remove(ref);
			} else {
				result.add(ref.get());
			}
		}
		return result;
	}

	@Override
	public void registerResourceSet(ResourceSet set, Model model) {
		if (set == null) {
			throw new IllegalArgumentException("Set must not be null");
		}
		if (ModelStorageImpl.resourceSetToModel.containsKey(set) && !(ModelStorageImpl.resourceSetToModel.get(set) instanceof LinkedModel)) {
			return;
		}
		if (model != null) {
			ModelStorageImpl.resourceSetToModel.put(set, model);
		} else {
			ModelStorageImpl.resourceSetToModel.remove(set);
		}
	}

	protected List<WeakReference<IRegistrationChangeListener>> listeners = new ArrayList<>();
	
	@Override
	public List<IRegistrationChangeListener> getRegistrationChangeListeners() {
		this.listeners.removeAll(this.listeners.parallelStream().filter(r -> r.get() == null).collect(Collectors.toList()));
		
		return this.listeners.parallelStream().map(r -> r.get()).collect(Collectors.toList());
	}

	@Override
	public void registerRegistrationChangeListener(IRegistrationChangeListener listener) {
		this.listeners.add(new WeakReference<>(listener));
	}

	@Override
	public void deregisterRegistrationChangeListener(IRegistrationChangeListener listener) {
		this.listeners.removeAll(this.listeners.parallelStream().filter(r -> r.get() == null || r.get() == listener).collect(Collectors.toList()));		
	}
	@Override
	public Set<IModelContributor> getContributors() {		
		this.contributors.removeAll(this.listeners.parallelStream().filter(r -> r.get() == null).collect(Collectors.toList()));
	
		return this.contributors.parallelStream().map(r -> r.get()).collect(Collectors.toSet());
	}

	@Override
	public void addModelContributor(IModelContributor contributor) {
		this.contributors.add(new WeakReference<>(contributor));
	}

	@Override
	public void removeModelContributor(IModelContributor contributor) {
		this.contributors.removeAll(this.listeners.parallelStream().filter(r -> r.get() == null || r.get() == contributor).collect(Collectors.toList()));		
	}

	@Override
	public void removeModel(Model model) {
		this.getModel().remove(model);
	}

} //ModelStorageImpl
