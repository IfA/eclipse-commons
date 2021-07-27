/**
 */
package de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.ModelStoragePackage
 * @generated
 */
public interface ModelStorageFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	ModelStorageFactory eINSTANCE = de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.impl.ModelStorageFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Model Storage</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Model Storage</em>'.
	 * @generated
	 */
	ModelStorage createModelStorage();

	/**
	 * Returns a new object of class '<em>Passive Model Storage</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Passive Model Storage</em>'.
	 * @generated
	 */
	PassiveModelStorage createPassiveModelStorage();

	/**
	 * Returns a new object of class '<em>Model</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Model</em>'.
	 * @generated
	 */
	Model createModel();

	/**
	 * Returns a new object of class '<em>Linked Model</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Linked Model</em>'.
	 * @generated
	 */
	LinkedModel createLinkedModel();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	ModelStoragePackage getModelStoragePackage();

	public <T extends ModelAdapter> T adapt(Model model, Class<T> adapterClass);
} //storageFactory
