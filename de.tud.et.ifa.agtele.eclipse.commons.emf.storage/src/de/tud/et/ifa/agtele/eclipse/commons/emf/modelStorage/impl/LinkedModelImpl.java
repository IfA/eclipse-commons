/**
 */
package de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.impl;

import de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.LinkedModel;
import de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.ModelStoragePackage;
import de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.importAdapter.ImportAdapter;
import de.tud.et.ifa.agtele.emf.AgteleEcoreUtil;
import de.tud.et.ifa.agtele.emf.edit.IReferencingIdentificationStringProvider;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EContentAdapter;
import org.eclipse.emf.edit.provider.ItemProviderAdapter;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Linked Model</b></em>'.
 * <!-- end-user-doc -->
 *
 * @generated
 */
public class LinkedModelImpl extends ModelImpl implements LinkedModel {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected LinkedModelImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ModelStoragePackage.Literals.LINKED_MODEL;
	}
	
	@Override
	public void setImportAdapter(ImportAdapter newImportAdapter) {
		return;
	}

	@Override
	public NotificationChain basicSetImportAdapter(ImportAdapter newImportAdapter, NotificationChain msgs) {
		return null;
	}
	
	@Override
	public void resetContent() {
		//Do nothing since the model does not own the resources
	}
	
	@Override
	public void dispose() {
		this.deregisterIdentifyableElement(this.getAllRegisteredElements());
		super.dispose();
	}
	
	@Override
	protected ResourceSet initializeResourceSet () {
		return null;
	}

	protected EContentAdapter idMapMaintainer = null;
	
	@Override
	protected void eBasicSetContainer(InternalEObject newContainer) {
		super.eBasicSetContainer(newContainer);		
//		if (this.getStorage() != null) {
//			this.getStorage().registerResourceSet(this.getResourceSet(), this);
//		}
	}
	
	@Override
	public void setResourceSet(ResourceSet set) {
		if (this.getStorage() != null) {
			this.getStorage().registerResourceSet(set, this);
		}
		this.resourceSet = set;
		
		this.idMapMaintainer = new EContentAdapter() {
			@SuppressWarnings("rawtypes")
			@Override
			public void notifyChanged(Notification notification) {
				if (notification.getFeature() instanceof EReference && 
						((EReference)notification.getFeature()).isContainment()
						|| notification.getNewValue() instanceof Resource || notification.getOldValue() instanceof Resource) {
					ArrayList<EObject> changedObjects = new ArrayList<>();
					Collection rawCollection = null;
					Object rawObject = null;
					boolean remove = notification.getEventType() == Notification.REMOVE || notification.getEventType() == Notification.REMOVE_MANY;
					if (notification.getEventType() == Notification.ADD || notification.getEventType() == Notification.ADD_MANY) {
						if (notification.getNewValue() instanceof Collection) {
							rawCollection = (Collection) notification.getNewValue();
						} else if (notification.getNewValue() instanceof Resource) { 
							rawCollection = ((Resource)notification.getNewValue()).getContents();
						} else {
							rawObject = notification.getNewValue();
						}						
					} else if (notification.getEventType() == Notification.REMOVE || notification.getEventType() == Notification.REMOVE_MANY) {
						if (notification.getOldValue() instanceof Collection) {
							rawCollection = (Collection) notification.getOldValue();
						} else if (notification.getOldValue() instanceof Resource) { 
							rawCollection = ((Resource)notification.getOldValue()).getContents();
						} else {
							rawObject = notification.getOldValue();
						}
					}
					if (rawObject != null) {
						if (rawObject instanceof EObject) {
							changedObjects.add((EObject) rawObject);
							changedObjects.addAll(AgteleEcoreUtil.getAllInstances(EcorePackage.Literals.EOBJECT, (EObject)rawObject));			
						} 
					}
					if (rawCollection != null) {
						for (Object o : rawCollection) {
							if (o instanceof EObject) {
								changedObjects.add((EObject) o);
								changedObjects.addAll(AgteleEcoreUtil.getAllInstances(EcorePackage.Literals.EOBJECT, (EObject)o));
							}
						}
					}
					for (EObject obj : changedObjects) {
						if (remove) {							
							LinkedModelImpl.this.deregisterIdentifyableElement(obj);
						} else {							
							Set<String> ids = LinkedModelImpl.this.getIdentifiers(obj);
							if (!ids.isEmpty()) {
								LinkedModelImpl.this.registerIdentifyableElement(ids, obj);
							}	
						}					
					}
					if (notification.getNotifier()instanceof EObject) {
						LinkedModelImpl.this.updateRegistration((EObject) notification.getNotifier());						
					}
				} else if (notification.getFeature() instanceof EAttribute && notification.getNotifier()instanceof EObject) {
					LinkedModelImpl.this.updateRegistration((EObject) notification.getNotifier());		
				}
				super.notifyChanged(notification);
			}
		};		
		set.eAdapters().add(idMapMaintainer);
		this.initialize();
	}
	
	@Override
	public void initialize() {
		this.elementRegistry.clear();
		this.setInitialized();
		if (this.resourceSet != null && !this.resourceSet.getResources().isEmpty()) {
			for (Resource res : this.resourceSet.getResources()) {
				if (!res.getContents().isEmpty()) {
					this.idMapMaintainer.notifyChanged(new ENotificationImpl(null, Notification.ADD, null, null, res));
				}
			}
		} else if (this.resourceSet == null) {
			for (EObject eObj : this.getAllRegisteredElements()) {
				this.deregisterIdentifyableElement(eObj);
			}
		}
	}
	
	public void updateRegistration (EObject obj) {
		Set<String> oldIds = inverseResolve(obj), newIds = this.getIdentifiers(obj),
				oldTest = new HashSet<>(oldIds), newTest = new HashSet<>(newIds);
		oldTest.removeAll(newIds);
		newTest.removeAll(oldIds);
				
		if (!oldTest.isEmpty() || !newTest.isEmpty()) {
			this.deregisterIdentifyableElement(obj);
			this.registerIdentifyableElement(newIds, obj);
		}
	}
	
	@SuppressWarnings("rawtypes")
	public Set<String> getIdentifiers (EObject obj) {
		HashSet<String> result = new HashSet<>();
		if (obj.eClass().getEIDAttribute() != null && obj.eGet(obj.eClass().getEIDAttribute())!= null) {
			Object idValue = obj.eGet(obj.eClass().getEIDAttribute());
			if (idValue instanceof Collection) {
				for (Object o : (Collection)idValue) {
					result.add(o.toString());
				}
			} else {
				result.add(idValue.toString());
			}
		}
		Adapter itemProviderAdapter = null;
		try {
			itemProviderAdapter = this.getAdapter(obj, ItemProviderAdapter.class);
		} catch (Exception e) {
			//Do nothing
		}
		
		if (itemProviderAdapter != null && 
				itemProviderAdapter instanceof IReferencingIdentificationStringProvider) {
			ArrayList<String> identifiers = new ArrayList<>(((IReferencingIdentificationStringProvider)itemProviderAdapter).getReferencingIdentificationStrings(obj));
			
			for (String id : new ArrayList<>(identifiers)) {
				if (IReferencingIdentificationStringProvider.hasUriPrefix(id)) {
					identifiers.add(IReferencingIdentificationStringProvider.removeUriPrefix(id));
				}
			}
			result.addAll(identifiers)	;			
		}
		
		return result;
	}
	
	public Adapter getAdapter (EObject eObject, Object type) {
		return AgteleEcoreUtil.getAdapter(eObject, type);
	}
} //LinkedModelImpl
