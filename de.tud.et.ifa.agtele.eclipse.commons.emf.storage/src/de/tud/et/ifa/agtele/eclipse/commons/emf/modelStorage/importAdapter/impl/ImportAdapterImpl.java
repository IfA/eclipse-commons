/**
 */
package de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.importAdapter.impl;

import de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.Model;
import de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.ModelStoragePackage;
import de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.importAdapter.Connector;
import de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.importAdapter.ImportAdapter;
import de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.importAdapter.ImportAdapterPackage;
import de.tud.et.ifa.agtele.emf.importing.IDelegatingModelImportStrategy;
import de.tud.et.ifa.agtele.emf.importing.IModelConnector;
import de.tud.et.ifa.agtele.emf.importing.IModelElementImportRegistry;
import de.tud.et.ifa.agtele.emf.importing.IModelImportStrategy;
import de.tud.et.ifa.agtele.emf.importing.ModelElementImportRegistry;

import java.util.HashMap;
import java.util.Map;
import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Import Adapter</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.importAdapter.impl.ImportAdapterImpl#getConnector <em>Connector</em>}</li>
 *   <li>{@link de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.importAdapter.impl.ImportAdapterImpl#getModel <em>Model</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ImportAdapterImpl extends MinimalEObjectImpl.Container implements ImportAdapter {
	/**
	 * The cached value of the '{@link #getConnector() <em>Connector</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getConnector()
	 * @generated
	 * @ordered
	 */
	protected Connector connector;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ImportAdapterImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ImportAdapterPackage.Literals.IMPORT_ADAPTER;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Connector getConnector() {	
	
		if (connector != null && connector.eIsProxy()) {
			InternalEObject oldConnector = (InternalEObject)connector;
			connector = (Connector)eResolveProxy(oldConnector);
			if (connector != oldConnector) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, ImportAdapterPackage.IMPORT_ADAPTER__CONNECTOR, oldConnector, connector));
			}
		}
		return connector;
	}
/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Connector basicGetConnector() {
		return connector;
	}
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setConnector(Connector newConnector) {
	
		Connector oldConnector = connector;
		connector = newConnector;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ImportAdapterPackage.IMPORT_ADAPTER__CONNECTOR, oldConnector, connector));
	
	}
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Model getModel() {	
	
		if (eContainerFeatureID() != ImportAdapterPackage.IMPORT_ADAPTER__MODEL) return null;
		return (Model)eContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Model basicGetModel() {
		if (eContainerFeatureID() != ImportAdapterPackage.IMPORT_ADAPTER__MODEL) return null;
		return (Model)eInternalContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetModel(Model newModel, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject)newModel, ImportAdapterPackage.IMPORT_ADAPTER__MODEL, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setModel(Model newModel) {
	
		if (newModel != eInternalContainer() || (eContainerFeatureID() != ImportAdapterPackage.IMPORT_ADAPTER__MODEL && newModel != null)) {
			if (EcoreUtil.isAncestor(this, newModel))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newModel != null)
				msgs = ((InternalEObject)newModel).eInverseAdd(this, ModelStoragePackage.MODEL__IMPORT_ADAPTER, Model.class, msgs);
			msgs = basicSetModel(newModel, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ImportAdapterPackage.IMPORT_ADAPTER__MODEL, newModel, newModel));
	
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ImportAdapterPackage.IMPORT_ADAPTER__MODEL:
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return basicSetModel((Model)otherEnd, msgs);
		}
		return super.eInverseAdd(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ImportAdapterPackage.IMPORT_ADAPTER__MODEL:
				return basicSetModel(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eBasicRemoveFromContainerFeature(NotificationChain msgs) {
		switch (eContainerFeatureID()) {
			case ImportAdapterPackage.IMPORT_ADAPTER__MODEL:
				return eInternalContainer().eInverseRemove(this, ModelStoragePackage.MODEL__IMPORT_ADAPTER, Model.class, msgs);
		}
		return super.eBasicRemoveFromContainerFeature(msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ImportAdapterPackage.IMPORT_ADAPTER__CONNECTOR:
				if (resolve) return getConnector();
				return basicGetConnector();
			case ImportAdapterPackage.IMPORT_ADAPTER__MODEL:
				if (resolve) return getModel();
				return basicGetModel();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case ImportAdapterPackage.IMPORT_ADAPTER__CONNECTOR:
				setConnector((Connector)newValue);
				return;
			case ImportAdapterPackage.IMPORT_ADAPTER__MODEL:
				setModel((Model)newValue);
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
			case ImportAdapterPackage.IMPORT_ADAPTER__CONNECTOR:
				setConnector((Connector)null);
				return;
			case ImportAdapterPackage.IMPORT_ADAPTER__MODEL:
				setModel((Model)null);
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
			case ImportAdapterPackage.IMPORT_ADAPTER__CONNECTOR:
				return connector != null;
			case ImportAdapterPackage.IMPORT_ADAPTER__MODEL:
				return basicGetModel() != null;
		}
		return super.eIsSet(featureID);
	}
	
	Resource res = null;

	public Resource createResource () {
		return new XMIResourceImpl();
	}
		
	@Override
	public void importModel() {
		if (this.getModel() == null) {
			return;
		}
		this.res = this.createResource();
		ImportAdapter.addToResourceSet(this.getModel().getResourceSet(), res);
		
		this.importModel(this.res.getContents());
	}
	
	protected IDelegatingModelImportStrategy[] importStrategyWrappers;
	
	protected IModelElementImportRegistry registry = new ModelElementImportRegistry();
	
	@Override
	public IModelElementImportRegistry getImportRegistry() {
		return this.registry;
	}
	
	@Override
	public void setImportStrategyWrappers(IDelegatingModelImportStrategy[] wrappers) {
		this.importStrategyWrappers = wrappers;		
	}	
	
	@Override
	public IDelegatingModelImportStrategy[] getImportStrategyWrappers() {
		return this.importStrategyWrappers;		
	}	
		
	protected Map<EClass, IModelImportStrategy> importStrategyRegistry = new HashMap<>();
	
	@Override
	public void registerLocalImportStrategy(EClass cls, IModelImportStrategy strategy) {
		if (cls != null && strategy != null) {
			importStrategyRegistry.put(cls, strategy);
		}
	}

	@Override
	public IModelImportStrategy getLocalImportStrategy(EClass cls) {
		return this.importStrategyRegistry.get(cls);
	}

	@Override
	public void setConnector(IModelConnector connector) {
		this.setConnector((Connector)connector);
	}

} //ImportAdapterImpl
