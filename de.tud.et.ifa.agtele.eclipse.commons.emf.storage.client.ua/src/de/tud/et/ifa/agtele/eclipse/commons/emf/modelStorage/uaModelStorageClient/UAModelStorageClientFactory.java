/**
 */
package de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.uaModelStorageClient;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.uaModelStorageClient.UAModelStorageClientPackage
 * @generated
 */
public interface UAModelStorageClientFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	UAModelStorageClientFactory eINSTANCE = de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.uaModelStorageClient.impl.UAModelStorageClientFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Agtele EMFUA Connector</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Agtele EMFUA Connector</em>'.
	 * @generated
	 */
	AgteleEMFUAConnector createAgteleEMFUAConnector();

	/**
	 * Returns a new object of class '<em>Agtele EMFUA Import Adapter</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Agtele EMFUA Import Adapter</em>'.
	 * @generated
	 */
	AgteleEMFUAImportAdapter createAgteleEMFUAImportAdapter();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	UAModelStorageClientPackage getUAModelStorageClientPackage();

} //UAModelStorageClientFactory
