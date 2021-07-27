/**
 */
package de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.uaModelStorageClient;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;

import de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.importAdapter.ImportAdapterPackage;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each operation of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.uaModelStorageClient.UAModelStorageClientFactory
 * @model kind="package"
 * @generated
 */
public interface UAModelStorageClientPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "uaModelStorageClient";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://et.tu-dresden.de/ifa/EMFModelStorage/UA/model/v0.1";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "uamsc";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	UAModelStorageClientPackage eINSTANCE = de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.uaModelStorageClient.impl.UAModelStorageClientPackageImpl.init();

	/**
	 * The meta object id for the '{@link de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.uaModelStorageClient.impl.AgteleEMFUAConnectorImpl <em>Agtele EMFUA Connector</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.uaModelStorageClient.impl.AgteleEMFUAConnectorImpl
	 * @see de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.uaModelStorageClient.impl.UAModelStorageClientPackageImpl#getAgteleEMFUAConnector()
	 * @generated
	 */
	int AGTELE_EMFUA_CONNECTOR = 0;

	/**
	 * The feature id for the '<em><b>Connection Uri</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AGTELE_EMFUA_CONNECTOR__CONNECTION_URI = ImportAdapterPackage.CONNECTOR__CONNECTION_URI;

	/**
	 * The number of structural features of the '<em>Agtele EMFUA Connector</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AGTELE_EMFUA_CONNECTOR_FEATURE_COUNT = ImportAdapterPackage.CONNECTOR_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Agtele EMFUA Connector</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AGTELE_EMFUA_CONNECTOR_OPERATION_COUNT = ImportAdapterPackage.CONNECTOR_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.uaModelStorageClient.impl.AgteleEMFUAImportAdapterImpl <em>Agtele EMFUA Import Adapter</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.uaModelStorageClient.impl.AgteleEMFUAImportAdapterImpl
	 * @see de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.uaModelStorageClient.impl.UAModelStorageClientPackageImpl#getAgteleEMFUAImportAdapter()
	 * @generated
	 */
	int AGTELE_EMFUA_IMPORT_ADAPTER = 1;

	/**
	 * The feature id for the '<em><b>Connector</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AGTELE_EMFUA_IMPORT_ADAPTER__CONNECTOR = ImportAdapterPackage.IMPORT_ADAPTER__CONNECTOR;

	/**
	 * The feature id for the '<em><b>Model</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AGTELE_EMFUA_IMPORT_ADAPTER__MODEL = ImportAdapterPackage.IMPORT_ADAPTER__MODEL;

	/**
	 * The number of structural features of the '<em>Agtele EMFUA Import Adapter</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AGTELE_EMFUA_IMPORT_ADAPTER_FEATURE_COUNT = ImportAdapterPackage.IMPORT_ADAPTER_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Agtele EMFUA Import Adapter</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AGTELE_EMFUA_IMPORT_ADAPTER_OPERATION_COUNT = ImportAdapterPackage.IMPORT_ADAPTER_OPERATION_COUNT + 0;


	/**
	 * Returns the meta object for class '{@link de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.uaModelStorageClient.AgteleEMFUAConnector <em>Agtele EMFUA Connector</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Agtele EMFUA Connector</em>'.
	 * @see de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.uaModelStorageClient.AgteleEMFUAConnector
	 * @generated
	 */
	EClass getAgteleEMFUAConnector();

	/**
	 * Returns the meta object for class '{@link de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.uaModelStorageClient.AgteleEMFUAImportAdapter <em>Agtele EMFUA Import Adapter</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Agtele EMFUA Import Adapter</em>'.
	 * @see de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.uaModelStorageClient.AgteleEMFUAImportAdapter
	 * @generated
	 */
	EClass getAgteleEMFUAImportAdapter();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	UAModelStorageClientFactory getUAModelStorageClientFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each operation of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.uaModelStorageClient.impl.AgteleEMFUAConnectorImpl <em>Agtele EMFUA Connector</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.uaModelStorageClient.impl.AgteleEMFUAConnectorImpl
		 * @see de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.uaModelStorageClient.impl.UAModelStorageClientPackageImpl#getAgteleEMFUAConnector()
		 * @generated
		 */
		EClass AGTELE_EMFUA_CONNECTOR = eINSTANCE.getAgteleEMFUAConnector();

		/**
		 * The meta object literal for the '{@link de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.uaModelStorageClient.impl.AgteleEMFUAImportAdapterImpl <em>Agtele EMFUA Import Adapter</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.uaModelStorageClient.impl.AgteleEMFUAImportAdapterImpl
		 * @see de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.uaModelStorageClient.impl.UAModelStorageClientPackageImpl#getAgteleEMFUAImportAdapter()
		 * @generated
		 */
		EClass AGTELE_EMFUA_IMPORT_ADAPTER = eINSTANCE.getAgteleEMFUAImportAdapter();

	}

} //UAModelStorageClientPackage
