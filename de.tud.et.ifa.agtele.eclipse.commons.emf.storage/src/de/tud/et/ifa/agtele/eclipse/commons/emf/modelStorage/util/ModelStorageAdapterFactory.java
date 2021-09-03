/**
 */
package de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.util;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

import de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.*;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.ModelStoragePackage
 * @generated
 */
public class ModelStorageAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static ModelStoragePackage modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ModelStorageAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = ModelStoragePackage.eINSTANCE;
		}
	}

	/**
	 * Returns whether this factory is applicable for the type of the object.
	 * <!-- begin-user-doc -->
	 * This implementation returns <code>true</code> if the object is either the model's package or is an instance object of the model.
	 * <!-- end-user-doc -->
	 * @return whether this factory is applicable for the type of the object.
	 * @generated
	 */
	@Override
	public boolean isFactoryForType(Object object) {
		if (object == modelPackage) {
			return true;
		}
		if (object instanceof EObject) {
			return ((EObject)object).eClass().getEPackage() == modelPackage;
		}
		return false;
	}

	/**
	 * The switch that delegates to the <code>createXXX</code> methods.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ModelStorageSwitch<Adapter> modelSwitch =
		new ModelStorageSwitch<Adapter>() {
			@Override
			public Adapter caseModelStorage(ModelStorage object) {
				return createModelStorageAdapter();
			}
			@Override
			public Adapter casePassiveModelStorage(PassiveModelStorage object) {
				return createPassiveModelStorageAdapter();
			}
			@Override
			public Adapter caseModel(Model object) {
				return createModelAdapter();
			}
			@Override
			public Adapter caseLinkedModel(LinkedModel object) {
				return createLinkedModelAdapter();
			}
			@Override
			public Adapter caseModelAdapter(ModelAdapter object) {
				return createModelAdapterAdapter();
			}
			@Override
			public Adapter caseUpdateableElement(UpdateableElement object) {
				return createUpdateableElementAdapter();
			}
			@Override
			public Adapter defaultCase(EObject object) {
				return createEObjectAdapter();
			}
		};

	/**
	 * Creates an adapter for the <code>target</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param target the object to adapt.
	 * @return the adapter for the <code>target</code>.
	 * @generated
	 */
	@Override
	public Adapter createAdapter(Notifier target) {
		return modelSwitch.doSwitch((EObject)target);
	}


	/**
	 * Creates a new adapter for an object of class '{@link de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.ModelStorage <em>Model Storage</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.ModelStorage
	 * @generated
	 */
	public Adapter createModelStorageAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.PassiveModelStorage <em>Passive Model Storage</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.PassiveModelStorage
	 * @generated
	 */
	public Adapter createPassiveModelStorageAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.Model <em>Model</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.Model
	 * @generated
	 */
	public Adapter createModelAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.LinkedModel <em>Linked Model</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.LinkedModel
	 * @generated
	 */
	public Adapter createLinkedModelAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.ModelAdapter <em>Model Adapter</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.ModelAdapter
	 * @generated
	 */
	public Adapter createModelAdapterAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.UpdateableElement <em>Updateable Element</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.UpdateableElement
	 * @generated
	 */
	public Adapter createUpdateableElementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for the default case.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @generated
	 */
	public Adapter createEObjectAdapter() {
		return null;
	}

} //ModelStorageAdapterFactory
