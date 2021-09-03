/**
 */
package de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.stream.Collectors;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;

import de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.IResolveResult;
import de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.Model;
import de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.ModelStoragePackage;
import de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.IRegistrationChangeListener.ChangeType;
import de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.IRegistrationChangeListener.RegistrationChangeNotification;
import de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.importAdapter.ImportAdapter;
import de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.importAdapter.ImportAdapterPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object
 * '<em><b>Model</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.impl.ModelImpl#getContent <em>Content</em>}</li>
 *   <li>{@link de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.impl.ModelImpl#getUri <em>Uri</em>}</li>
 *   <li>{@link de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.impl.ModelImpl#getImportAdapter <em>Import Adapter</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ModelImpl extends UpdateableElementImpl implements Model {
	/**
	 * The cached value of the '{@link #getContent() <em>Content</em>}' reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getContent()
	 * @generated
	 * @ordered
	 */
	protected EList<EObject> content;

	/**
	 * The default value of the '{@link #getUri() <em>Uri</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getUri()
	 * @generated
	 * @ordered
	 */
	protected static final String URI_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getUri() <em>Uri</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getUri()
	 * @generated
	 * @ordered
	 */
	protected String uri = URI_EDEFAULT;
	/**
	 * The cached value of the '{@link #getImportAdapter() <em>Import Adapter</em>}' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getImportAdapter()
	 * @generated
	 * @ordered
	 */
	protected ImportAdapter importAdapter;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	protected ModelImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ModelStoragePackage.Literals.MODEL;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT needs to be synchronized
	 */
	public synchronized EList<EObject> getContent() {
		if (content == null) {
			content = new EObjectResolvingEList<EObject>(EObject.class, this, ModelStoragePackage.MODEL__CONTENT);
		}

		ArrayList<EObject> currentContent = new ArrayList<>();
		List<EObject> toAdd, toRemove;

		for (Resource res : this.resourceSet.getResources()) {
			currentContent.addAll(res.getContents());
		}

		toRemove = new ArrayList<EObject>(content).stream().filter(o -> !currentContent.contains(o))
				.collect(Collectors.toList());
		toAdd = new ArrayList<EObject>(currentContent).stream().filter(o -> !content.contains(o))
				.collect(Collectors.toList());

		content.removeAll(toRemove);
		content.addAll(toAdd);

		return content;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getUri() {	
	
		return uri;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setUri(String newUri) {
	
		String oldUri = uri;
		uri = newUri;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelStoragePackage.MODEL__URI, oldUri, uri));
	
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ImportAdapter getImportAdapter() {	
	
		if (importAdapter != null && importAdapter.eIsProxy()) {
			InternalEObject oldImportAdapter = (InternalEObject)importAdapter;
			importAdapter = (ImportAdapter)eResolveProxy(oldImportAdapter);
			if (importAdapter != oldImportAdapter) {
				InternalEObject newImportAdapter = (InternalEObject)importAdapter;
				NotificationChain msgs =  oldImportAdapter.eInverseRemove(this, ImportAdapterPackage.IMPORT_ADAPTER__MODEL, ImportAdapter.class, null);
				if (newImportAdapter.eInternalContainer() == null) {
					msgs =  newImportAdapter.eInverseAdd(this, ImportAdapterPackage.IMPORT_ADAPTER__MODEL, ImportAdapter.class, msgs);
				}
				if (msgs != null) msgs.dispatch();
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, ModelStoragePackage.MODEL__IMPORT_ADAPTER, oldImportAdapter, importAdapter));
			}
		}
		return importAdapter;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public ImportAdapter basicGetImportAdapter() {
		return importAdapter;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetImportAdapter(ImportAdapter newImportAdapter, NotificationChain msgs) {
		ImportAdapter oldImportAdapter = importAdapter;
		importAdapter = newImportAdapter;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ModelStoragePackage.MODEL__IMPORT_ADAPTER, oldImportAdapter, newImportAdapter);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setImportAdapter(ImportAdapter newImportAdapter) {
	
		if (newImportAdapter != importAdapter) {
			NotificationChain msgs = null;
			if (importAdapter != null)
				msgs = ((InternalEObject)importAdapter).eInverseRemove(this, ImportAdapterPackage.IMPORT_ADAPTER__MODEL, ImportAdapter.class, msgs);
			if (newImportAdapter != null)
				msgs = ((InternalEObject)newImportAdapter).eInverseAdd(this, ImportAdapterPackage.IMPORT_ADAPTER__MODEL, ImportAdapter.class, msgs);
			msgs = basicSetImportAdapter(newImportAdapter, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelStoragePackage.MODEL__IMPORT_ADAPTER, newImportAdapter, newImportAdapter));
	
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ModelStoragePackage.MODEL__IMPORT_ADAPTER:
				if (importAdapter != null)
					msgs = ((InternalEObject)importAdapter).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ModelStoragePackage.MODEL__IMPORT_ADAPTER, null, msgs);
				return basicSetImportAdapter((ImportAdapter)otherEnd, msgs);
		}
		return super.eInverseAdd(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ModelStoragePackage.MODEL__IMPORT_ADAPTER:
				return basicSetImportAdapter(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ModelStoragePackage.MODEL__CONTENT:
				return getContent();
			case ModelStoragePackage.MODEL__URI:
				return getUri();
			case ModelStoragePackage.MODEL__IMPORT_ADAPTER:
				if (resolve) return getImportAdapter();
				return basicGetImportAdapter();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case ModelStoragePackage.MODEL__CONTENT:
				getContent().clear();
				getContent().addAll((Collection<? extends EObject>)newValue);
				return;
			case ModelStoragePackage.MODEL__URI:
				setUri((String)newValue);
				return;
			case ModelStoragePackage.MODEL__IMPORT_ADAPTER:
				setImportAdapter((ImportAdapter)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case ModelStoragePackage.MODEL__CONTENT:
				getContent().clear();
				return;
			case ModelStoragePackage.MODEL__URI:
				setUri(URI_EDEFAULT);
				return;
			case ModelStoragePackage.MODEL__IMPORT_ADAPTER:
				setImportAdapter((ImportAdapter)null);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case ModelStoragePackage.MODEL__CONTENT:
				return content != null && !content.isEmpty();
			case ModelStoragePackage.MODEL__URI:
				return URI_EDEFAULT == null ? uri != null : !URI_EDEFAULT.equals(uri);
			case ModelStoragePackage.MODEL__IMPORT_ADAPTER:
				return importAdapter != null;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuilder result = new StringBuilder(super.toString());
		result.append(" (uri: ");
		result.append(uri);
		result.append(')');
		return result.toString();
	}

	@Override
	public synchronized void resetContent() {
		for (Resource res : new ArrayList<>(this.getResourceSet().getResources())) {
			try {
				res.getContents().clear();
			} catch (Exception e) {
				// Do nothing
			}
		}
		this.deregisterIdentifyableElement(this.getAllRegisteredElements());
		while( this.getResourceSet().getResources().size() > 0) {
			try {
				this.getResourceSet().getResources().remove(0);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		this.getContent().clear();
		this.elementRegistry.clear();
	}

	public void deregisterIdentifyableElement(Set<EObject> elements) {
		for (EObject obj : elements) {
			this.deregisterIdentifyableElement(obj);
		}
	}

	protected ResourceSet resourceSet = this.initializeResourceSet();

	protected ResourceSet initializeResourceSet() {
		return new ResourceSetImpl();
	}

	@Override
	public ResourceSet getResourceSet() {
		return this.resourceSet;
	}

	protected ConcurrentHashMap<String, Queue<EObject>> elementRegistry = new ConcurrentHashMap<>();

	@Override
	public void registerIdentifyableElement(Collection<String> identifiers, EObject element) {
		if (identifiers == null || element == null || identifiers.size() == 0) {
			return;
		}
		Set<String> newRegisteredIds = new HashSet<>();
		for (String id : identifiers) {
			if (this.elementRegistry.containsKey(id)) {
				if (!this.elementRegistry.get(id).contains(element)) {
					this.elementRegistry.get(id).add(element);
					newRegisteredIds.add(id);
				}
			} else {
				ConcurrentLinkedQueue<EObject> list = new ConcurrentLinkedQueue<>();
				list.add(element);
				this.elementRegistry.put(id, list);
				newRegisteredIds.add(id);
			}
		}
		if (this.getStorage() != null && !newRegisteredIds.isEmpty()) {
			this.getStorage().notifyChanged(new RegistrationChangeNotification(this.getStorage(), this, element,
					newRegisteredIds, ChangeType.REGISTERED));
		}
	}

	@Override
	public List<IResolveResult> resolve(String identifier) {
		if (identifier != null && this.elementRegistry.containsKey(identifier)) {
			return this.factorResolveResults(identifier, new ArrayList<>(this.elementRegistry.get(identifier)));
		}
		return Collections.emptyList();
	}

	@Override
	public void deregisterIdentifyableElement(EObject element) {
		Set<String> deregisteredIds = new HashSet<>();
		for (Entry<String, Queue<EObject>> entry : new HashSet<>(this.elementRegistry.entrySet())) {
			if (entry.getValue().contains(element)) {
				entry.getValue().remove(element);
				deregisteredIds.add(entry.getKey());
			}
			if (entry.getValue().isEmpty()) {
				this.elementRegistry.remove(entry.getKey());
			}
		}

		if (this.getStorage() != null && !deregisteredIds.isEmpty()) {
			this.getStorage().notifyChanged(new RegistrationChangeNotification(this.getStorage(), this, element,
					deregisteredIds, ChangeType.DEREGISTERED));
		}
	}

	public Set<String> inverseResolve(Object obj) {
		HashSet<String> result = new HashSet<>();

		for (Entry<String, Queue<EObject>> entry : this.elementRegistry.entrySet()) {
			if (entry.getValue().contains(obj)) {
				result.add(entry.getKey());
			}
		}

		return result;
	}

	public Set<EObject> getAllRegisteredElements() {
		HashSet<EObject> result = new HashSet<>();
		for (Queue<EObject> list : this.elementRegistry.values()) {
			result.addAll(list);
		}
		return result;
	}

	@Override
	public void dispose() {
		this.resetContent();
		this.getStorage().removeModel(this);
	}

	protected boolean isInitialized = false;

	@Override
	public boolean isInitialized() {
		return this.isInitialized;
	}

	@Override
	public void setInitialized() {
		this.isInitialized = true;
	}

} // ModelImpl
