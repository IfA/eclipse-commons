/**
 */
package de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage;

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
 * @see de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.ModelStorageFactory
 * @model kind="package"
 * @generated
 */
public interface ModelStoragePackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "modelStorage";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://et.tu-dresden.de/ifa/EMFModelStorage/model/v0.1";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "ms";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	ModelStoragePackage eINSTANCE = de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.impl.ModelStoragePackageImpl.init();

	/**
	 * The meta object id for the '{@link de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.impl.ModelStorageImpl <em>Model Storage</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.impl.ModelStorageImpl
	 * @see de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.impl.ModelStoragePackageImpl#getModelStorage()
	 * @generated
	 */
	int MODEL_STORAGE = 0;

	/**
	 * The feature id for the '<em><b>Model</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODEL_STORAGE__MODEL = 0;

	/**
	 * The feature id for the '<em><b>Connector</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODEL_STORAGE__CONNECTOR = 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODEL_STORAGE__NAME = 2;

	/**
	 * The number of structural features of the '<em>Model Storage</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODEL_STORAGE_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>Model Storage</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODEL_STORAGE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.impl.PassiveModelStorageImpl <em>Passive Model Storage</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.impl.PassiveModelStorageImpl
	 * @see de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.impl.ModelStoragePackageImpl#getPassiveModelStorage()
	 * @generated
	 */
	int PASSIVE_MODEL_STORAGE = 1;

	/**
	 * The feature id for the '<em><b>Model</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PASSIVE_MODEL_STORAGE__MODEL = MODEL_STORAGE__MODEL;

	/**
	 * The feature id for the '<em><b>Connector</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PASSIVE_MODEL_STORAGE__CONNECTOR = MODEL_STORAGE__CONNECTOR;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PASSIVE_MODEL_STORAGE__NAME = MODEL_STORAGE__NAME;

	/**
	 * The number of structural features of the '<em>Passive Model Storage</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PASSIVE_MODEL_STORAGE_FEATURE_COUNT = MODEL_STORAGE_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Passive Model Storage</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PASSIVE_MODEL_STORAGE_OPERATION_COUNT = MODEL_STORAGE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.impl.ModelImpl <em>Model</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.impl.ModelImpl
	 * @see de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.impl.ModelStoragePackageImpl#getModel()
	 * @generated
	 */
	int MODEL = 2;

	/**
	 * The feature id for the '<em><b>Content</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODEL__CONTENT = 0;

	/**
	 * The feature id for the '<em><b>Uri</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODEL__URI = 1;

	/**
	 * The feature id for the '<em><b>Import Adapter</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODEL__IMPORT_ADAPTER = 2;

	/**
	 * The number of structural features of the '<em>Model</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODEL_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>Model</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODEL_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.impl.LinkedModelImpl <em>Linked Model</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.impl.LinkedModelImpl
	 * @see de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.impl.ModelStoragePackageImpl#getLinkedModel()
	 * @generated
	 */
	int LINKED_MODEL = 3;

	/**
	 * The feature id for the '<em><b>Content</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LINKED_MODEL__CONTENT = MODEL__CONTENT;

	/**
	 * The feature id for the '<em><b>Uri</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LINKED_MODEL__URI = MODEL__URI;

	/**
	 * The feature id for the '<em><b>Import Adapter</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LINKED_MODEL__IMPORT_ADAPTER = MODEL__IMPORT_ADAPTER;

	/**
	 * The number of structural features of the '<em>Linked Model</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LINKED_MODEL_FEATURE_COUNT = MODEL_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Linked Model</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LINKED_MODEL_OPERATION_COUNT = MODEL_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.impl.ModelAdapterImpl <em>Model Adapter</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.impl.ModelAdapterImpl
	 * @see de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.impl.ModelStoragePackageImpl#getModelAdapter()
	 * @generated
	 */
	int MODEL_ADAPTER = 4;

	/**
	 * The feature id for the '<em><b>Content</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODEL_ADAPTER__CONTENT = MODEL__CONTENT;

	/**
	 * The feature id for the '<em><b>Uri</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODEL_ADAPTER__URI = MODEL__URI;

	/**
	 * The feature id for the '<em><b>Import Adapter</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODEL_ADAPTER__IMPORT_ADAPTER = MODEL__IMPORT_ADAPTER;

	/**
	 * The number of structural features of the '<em>Model Adapter</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODEL_ADAPTER_FEATURE_COUNT = MODEL_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Model Adapter</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODEL_ADAPTER_OPERATION_COUNT = MODEL_OPERATION_COUNT + 0;


	/**
	 * Returns the meta object for class '{@link de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.ModelStorage <em>Model Storage</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Model Storage</em>'.
	 * @see de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.ModelStorage
	 * @generated
	 */
	EClass getModelStorage();

	/**
	 * Returns the meta object for the containment reference list '{@link de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.ModelStorage#getModel <em>Model</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Model</em>'.
	 * @see de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.ModelStorage#getModel()
	 * @see #getModelStorage()
	 * @generated
	 */
	EReference getModelStorage_Model();

	/**
	 * Returns the meta object for the containment reference list '{@link de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.ModelStorage#getConnector <em>Connector</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Connector</em>'.
	 * @see de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.ModelStorage#getConnector()
	 * @see #getModelStorage()
	 * @generated
	 */
	EReference getModelStorage_Connector();

	/**
	 * Returns the meta object for the attribute '{@link de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.ModelStorage#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.ModelStorage#getName()
	 * @see #getModelStorage()
	 * @generated
	 */
	EAttribute getModelStorage_Name();

	/**
	 * Returns the meta object for class '{@link de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.PassiveModelStorage <em>Passive Model Storage</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Passive Model Storage</em>'.
	 * @see de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.PassiveModelStorage
	 * @generated
	 */
	EClass getPassiveModelStorage();

	/**
	 * Returns the meta object for class '{@link de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.Model <em>Model</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Model</em>'.
	 * @see de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.Model
	 * @generated
	 */
	EClass getModel();

	/**
	 * Returns the meta object for the reference list '{@link de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.Model#getContent <em>Content</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Content</em>'.
	 * @see de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.Model#getContent()
	 * @see #getModel()
	 * @generated
	 */
	EReference getModel_Content();

	/**
	 * Returns the meta object for the attribute '{@link de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.Model#getUri <em>Uri</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Uri</em>'.
	 * @see de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.Model#getUri()
	 * @see #getModel()
	 * @generated
	 */
	EAttribute getModel_Uri();

	/**
	 * Returns the meta object for the containment reference '{@link de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.Model#getImportAdapter <em>Import Adapter</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Import Adapter</em>'.
	 * @see de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.Model#getImportAdapter()
	 * @see #getModel()
	 * @generated
	 */
	EReference getModel_ImportAdapter();

	/**
	 * Returns the meta object for class '{@link de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.LinkedModel <em>Linked Model</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Linked Model</em>'.
	 * @see de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.LinkedModel
	 * @generated
	 */
	EClass getLinkedModel();

	/**
	 * Returns the meta object for class '{@link de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.ModelAdapter <em>Model Adapter</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Model Adapter</em>'.
	 * @see de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.ModelAdapter
	 * @generated
	 */
	EClass getModelAdapter();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	ModelStorageFactory getModelStorageFactory();

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
		 * The meta object literal for the '{@link de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.impl.ModelStorageImpl <em>Model Storage</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.impl.ModelStorageImpl
		 * @see de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.impl.ModelStoragePackageImpl#getModelStorage()
		 * @generated
		 */
		EClass MODEL_STORAGE = eINSTANCE.getModelStorage();

		/**
		 * The meta object literal for the '<em><b>Model</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MODEL_STORAGE__MODEL = eINSTANCE.getModelStorage_Model();

		/**
		 * The meta object literal for the '<em><b>Connector</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MODEL_STORAGE__CONNECTOR = eINSTANCE.getModelStorage_Connector();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MODEL_STORAGE__NAME = eINSTANCE.getModelStorage_Name();

		/**
		 * The meta object literal for the '{@link de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.impl.PassiveModelStorageImpl <em>Passive Model Storage</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.impl.PassiveModelStorageImpl
		 * @see de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.impl.ModelStoragePackageImpl#getPassiveModelStorage()
		 * @generated
		 */
		EClass PASSIVE_MODEL_STORAGE = eINSTANCE.getPassiveModelStorage();

		/**
		 * The meta object literal for the '{@link de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.impl.ModelImpl <em>Model</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.impl.ModelImpl
		 * @see de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.impl.ModelStoragePackageImpl#getModel()
		 * @generated
		 */
		EClass MODEL = eINSTANCE.getModel();

		/**
		 * The meta object literal for the '<em><b>Content</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MODEL__CONTENT = eINSTANCE.getModel_Content();

		/**
		 * The meta object literal for the '<em><b>Uri</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MODEL__URI = eINSTANCE.getModel_Uri();

		/**
		 * The meta object literal for the '<em><b>Import Adapter</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MODEL__IMPORT_ADAPTER = eINSTANCE.getModel_ImportAdapter();

		/**
		 * The meta object literal for the '{@link de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.impl.LinkedModelImpl <em>Linked Model</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.impl.LinkedModelImpl
		 * @see de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.impl.ModelStoragePackageImpl#getLinkedModel()
		 * @generated
		 */
		EClass LINKED_MODEL = eINSTANCE.getLinkedModel();

		/**
		 * The meta object literal for the '{@link de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.impl.ModelAdapterImpl <em>Model Adapter</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.impl.ModelAdapterImpl
		 * @see de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.impl.ModelStoragePackageImpl#getModelAdapter()
		 * @generated
		 */
		EClass MODEL_ADAPTER = eINSTANCE.getModelAdapter();

	}

} //modelStoragePackage
