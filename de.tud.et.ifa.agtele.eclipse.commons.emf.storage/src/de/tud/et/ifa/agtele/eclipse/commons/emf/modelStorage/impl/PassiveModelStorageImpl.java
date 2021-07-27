/**
 */
package de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.impl;

import de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.LinkedModel;
import de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.Model;
import de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.ModelStoragePackage;
import de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.PassiveModelStorage;
import de.tud.et.ifa.agtele.emf.importing.IDelegatingModelImportStrategy;
import java.util.Collection;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.util.EContentAdapter;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Passive Model Storage</b></em>'.
 * <!-- end-user-doc -->
 *
 * @generated
 */
public class PassiveModelStorageImpl extends ModelStorageImpl implements PassiveModelStorage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PassiveModelStorageImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ModelStoragePackage.Literals.PASSIVE_MODEL_STORAGE;
	}

	@Override
	public EList<Model> getModel() {
		if (model == null) {
			model = new EObjectContainmentEList.Resolving<Model>(LinkedModel.class, this,
					ModelStoragePackage.MODEL_STORAGE__MODEL);
			this.eAdapters().add(new EContentAdapter() {
		
				@SuppressWarnings("rawtypes")
				@Override
				public void notifyChanged(Notification notification) {
					if (notification.getFeature() == ModelStoragePackage.Literals.MODEL_STORAGE__MODEL) {
						if (notification.getEventType() == Notification.ADD) {
							if (notification.getNewValue() instanceof Model) {
								Model model = (Model) notification.getNewValue();
								ModelStorageImpl.resourceSetToModel.put(model.getResourceSet(), model);
							}
						} else if (notification.getEventType() == Notification.ADD_MANY) {
							if (notification.getNewValue() instanceof Collection) {
								for (Object element : (Collection) notification.getNewValue()) {
									if (element instanceof Model) {
										ModelStorageImpl.resourceSetToModel.put(((Model) element).getResourceSet(),
												((Model) element));
									}
								}
							}
						} else if (notification.getEventType() == Notification.REMOVE) {
							if (notification.getOldValue() instanceof Model) {
								Model model = (Model) notification.getOldValue();
								ModelStorageImpl.resourceSetToModel.remove(model.getResourceSet());
							}
						} else if (notification.getEventType() == Notification.REMOVE_MANY) {
							if (notification.getOldValue() instanceof Collection) {
								for (Object element : (Collection) notification.getOldValue()) {
									if (element instanceof Model) {
										ModelStorageImpl.resourceSetToModel.remove(((Model) element).getResourceSet());
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
	@Override
	public void importFromUris(String[] uris) {
		return;
	}
	
	@Override
	public IDelegatingModelImportStrategy[] getDelegatingImportStrategies () {
		return new IDelegatingModelImportStrategy[] {} ;
	}
	
	@Override
	public void update(Collection<Model> models) {
		return;
	}
	
	
} //PassiveModelStorageImpl
