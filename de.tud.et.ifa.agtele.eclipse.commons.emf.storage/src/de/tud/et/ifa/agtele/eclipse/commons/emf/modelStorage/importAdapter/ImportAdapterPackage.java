/**
 */
package de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.importAdapter;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

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
 * @see de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.importAdapter.ImportAdapterFactory
 * @model kind="package"
 * @generated
 */
public interface ImportAdapterPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "importAdapter";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://et.tu-dresden.de/ifa/EMFModelStorage/model/v0.1/importAdapter";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "msi";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	ImportAdapterPackage eINSTANCE = de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.importAdapter.impl.ImportAdapterPackageImpl.init();

	/**
	 * The meta object id for the '{@link de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.importAdapter.impl.ConnectorImpl <em>Connector</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.importAdapter.impl.ConnectorImpl
	 * @see de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.importAdapter.impl.ImportAdapterPackageImpl#getConnector()
	 * @generated
	 */
	int CONNECTOR = 0;

	/**
	 * The feature id for the '<em><b>Connection Uri</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONNECTOR__CONNECTION_URI = 0;

	/**
	 * The number of structural features of the '<em>Connector</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONNECTOR_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Connector</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONNECTOR_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.importAdapter.impl.ImportAdapterImpl <em>Import Adapter</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.importAdapter.impl.ImportAdapterImpl
	 * @see de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.importAdapter.impl.ImportAdapterPackageImpl#getImportAdapter()
	 * @generated
	 */
	int IMPORT_ADAPTER = 1;

	/**
	 * The feature id for the '<em><b>Connector</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IMPORT_ADAPTER__CONNECTOR = 0;

	/**
	 * The feature id for the '<em><b>Model</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IMPORT_ADAPTER__MODEL = 1;

	/**
	 * The number of structural features of the '<em>Import Adapter</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IMPORT_ADAPTER_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Import Adapter</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IMPORT_ADAPTER_OPERATION_COUNT = 0;


	/**
	 * The meta object id for the '{@link de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.importAdapter.impl.FileSystemConnectorImpl <em>File System Connector</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.importAdapter.impl.FileSystemConnectorImpl
	 * @see de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.importAdapter.impl.ImportAdapterPackageImpl#getFileSystemConnector()
	 * @generated
	 */
	int FILE_SYSTEM_CONNECTOR = 2;

	/**
	 * The feature id for the '<em><b>Connection Uri</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILE_SYSTEM_CONNECTOR__CONNECTION_URI = CONNECTOR__CONNECTION_URI;

	/**
	 * The number of structural features of the '<em>File System Connector</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILE_SYSTEM_CONNECTOR_FEATURE_COUNT = CONNECTOR_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>File System Connector</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILE_SYSTEM_CONNECTOR_OPERATION_COUNT = CONNECTOR_OPERATION_COUNT + 0;


	/**
	 * Returns the meta object for class '{@link de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.importAdapter.Connector <em>Connector</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Connector</em>'.
	 * @see de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.importAdapter.Connector
	 * @generated
	 */
	EClass getConnector();

	/**
	 * Returns the meta object for the attribute '{@link de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.importAdapter.Connector#getConnectionUri <em>Connection Uri</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Connection Uri</em>'.
	 * @see de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.importAdapter.Connector#getConnectionUri()
	 * @see #getConnector()
	 * @generated
	 */
	EAttribute getConnector_ConnectionUri();

	/**
	 * Returns the meta object for class '{@link de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.importAdapter.ImportAdapter <em>Import Adapter</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Import Adapter</em>'.
	 * @see de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.importAdapter.ImportAdapter
	 * @generated
	 */
	EClass getImportAdapter();

	/**
	 * Returns the meta object for the reference '{@link de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.importAdapter.ImportAdapter#getConnector <em>Connector</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Connector</em>'.
	 * @see de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.importAdapter.ImportAdapter#getConnector()
	 * @see #getImportAdapter()
	 * @generated
	 */
	EReference getImportAdapter_Connector();

	/**
	 * Returns the meta object for the container reference '{@link de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.importAdapter.ImportAdapter#getModel <em>Model</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Model</em>'.
	 * @see de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.importAdapter.ImportAdapter#getModel()
	 * @see #getImportAdapter()
	 * @generated
	 */
	EReference getImportAdapter_Model();

	/**
	 * Returns the meta object for class '{@link de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.importAdapter.FileSystemConnector <em>File System Connector</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>File System Connector</em>'.
	 * @see de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.importAdapter.FileSystemConnector
	 * @generated
	 */
	EClass getFileSystemConnector();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	ImportAdapterFactory getImportAdapterFactory();

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
		 * The meta object literal for the '{@link de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.importAdapter.impl.ConnectorImpl <em>Connector</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.importAdapter.impl.ConnectorImpl
		 * @see de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.importAdapter.impl.ImportAdapterPackageImpl#getConnector()
		 * @generated
		 */
		EClass CONNECTOR = eINSTANCE.getConnector();

		/**
		 * The meta object literal for the '<em><b>Connection Uri</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONNECTOR__CONNECTION_URI = eINSTANCE.getConnector_ConnectionUri();

		/**
		 * The meta object literal for the '{@link de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.importAdapter.impl.ImportAdapterImpl <em>Import Adapter</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.importAdapter.impl.ImportAdapterImpl
		 * @see de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.importAdapter.impl.ImportAdapterPackageImpl#getImportAdapter()
		 * @generated
		 */
		EClass IMPORT_ADAPTER = eINSTANCE.getImportAdapter();

		/**
		 * The meta object literal for the '<em><b>Connector</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IMPORT_ADAPTER__CONNECTOR = eINSTANCE.getImportAdapter_Connector();

		/**
		 * The meta object literal for the '<em><b>Model</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IMPORT_ADAPTER__MODEL = eINSTANCE.getImportAdapter_Model();

		/**
		 * The meta object literal for the '{@link de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.importAdapter.impl.FileSystemConnectorImpl <em>File System Connector</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.importAdapter.impl.FileSystemConnectorImpl
		 * @see de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.importAdapter.impl.ImportAdapterPackageImpl#getFileSystemConnector()
		 * @generated
		 */
		EClass FILE_SYSTEM_CONNECTOR = eINSTANCE.getFileSystemConnector();

	}

} //ImportAdapterPackage
