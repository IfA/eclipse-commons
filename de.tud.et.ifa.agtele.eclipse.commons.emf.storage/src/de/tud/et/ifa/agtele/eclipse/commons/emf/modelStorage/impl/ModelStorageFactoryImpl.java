/**
 */
package de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.impl;

import java.util.ArrayList;
import java.util.WeakHashMap;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.*;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class ModelStorageFactoryImpl extends EFactoryImpl implements ModelStorageFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static ModelStorageFactory init() {
		try {
			ModelStorageFactory theModelStorageFactory = (ModelStorageFactory)EPackage.Registry.INSTANCE.getEFactory(ModelStoragePackage.eNS_URI);
			if (theModelStorageFactory != null) {
				return theModelStorageFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new ModelStorageFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ModelStorageFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case ModelStoragePackage.MODEL_STORAGE: return createModelStorage();
			case ModelStoragePackage.PASSIVE_MODEL_STORAGE: return createPassiveModelStorage();
			case ModelStoragePackage.MODEL: return createModel();
			case ModelStoragePackage.LINKED_MODEL: return createLinkedModel();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ModelStorage createModelStorage() {
		ModelStorageImpl modelStorage = new ModelStorageImpl();
		return modelStorage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public PassiveModelStorage createPassiveModelStorage() {
		PassiveModelStorageImpl passiveModelStorage = new PassiveModelStorageImpl();
		return passiveModelStorage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Model createModel() {
		ModelImpl model = new ModelImpl();
		return model;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public LinkedModel createLinkedModel() {
		LinkedModelImpl linkedModel = new LinkedModelImpl();
		return linkedModel;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ModelStoragePackage getModelStoragePackage() {
		return (ModelStoragePackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static ModelStoragePackage getPackage() {
		return ModelStoragePackage.eINSTANCE;
	}
	
	protected WeakHashMap<Model, ArrayList<ModelAdapter>> adapterMap = new WeakHashMap<>();
	
	public <T extends ModelAdapter> T adapt(Model model, Class<T> adapterClass) {
		ArrayList<ModelAdapter> adapterList;
		if (!adapterMap.containsKey(model)) {
			adapterList = new ArrayList<>();
			adapterMap.put(model, adapterList);
		} else {
			adapterList = adapterMap.get(model);
		}
		for (ModelAdapter adapter : adapterList) {
			if (adapterClass.isInstance(adapter)) {
				return (T) adapter;
			}
		}
		try {
			ModelAdapter adapter = adapterClass.newInstance();
			adapterList.add(adapter);
			return (T) adapter;
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}

} //storageFactoryImpl
