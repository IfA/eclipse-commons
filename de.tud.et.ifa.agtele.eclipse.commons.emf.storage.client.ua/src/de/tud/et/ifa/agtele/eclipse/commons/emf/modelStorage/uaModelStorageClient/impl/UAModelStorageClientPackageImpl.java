/**
 */
package de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.uaModelStorageClient.impl;

import de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.ModelStoragePackage;
import de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.importAdapter.ImportAdapterPackage;
import de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.uaModelStorageClient.AgteleEMFUAConnector;
import de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.uaModelStorageClient.AgteleEMFUAImportAdapter;
import de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.uaModelStorageClient.UAModelStorageClientFactory;
import de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.uaModelStorageClient.UAModelStorageClientPackage;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcorePackage;

import org.eclipse.emf.ecore.impl.EPackageImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class UAModelStorageClientPackageImpl extends EPackageImpl implements UAModelStorageClientPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass agteleEMFUAConnectorEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass agteleEMFUAImportAdapterEClass = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
	 * package URI value.
	 * <p>Note: the correct way to create the package is via the static
	 * factory method {@link #init init()}, which also performs
	 * initialization of the package, or returns the registered package,
	 * if one already exists.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.uaModelStorageClient.UAModelStorageClientPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private UAModelStorageClientPackageImpl() {
		super(eNS_URI, UAModelStorageClientFactory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
	 *
	 * <p>This method is used to initialize {@link UAModelStorageClientPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static UAModelStorageClientPackage init() {
		if (isInited) return (UAModelStorageClientPackage)EPackage.Registry.INSTANCE.getEPackage(UAModelStorageClientPackage.eNS_URI);

		// Obtain or create and register package
		Object registeredUAModelStorageClientPackage = EPackage.Registry.INSTANCE.get(eNS_URI);
		UAModelStorageClientPackageImpl theUAModelStorageClientPackage = registeredUAModelStorageClientPackage instanceof UAModelStorageClientPackageImpl ? (UAModelStorageClientPackageImpl)registeredUAModelStorageClientPackage : new UAModelStorageClientPackageImpl();

		isInited = true;

		// Initialize simple dependencies
		EcorePackage.eINSTANCE.eClass();
		ModelStoragePackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theUAModelStorageClientPackage.createPackageContents();

		// Initialize created meta-data
		theUAModelStorageClientPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theUAModelStorageClientPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(UAModelStorageClientPackage.eNS_URI, theUAModelStorageClientPackage);
		return theUAModelStorageClientPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getAgteleEMFUAConnector() {
		return agteleEMFUAConnectorEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getAgteleEMFUAImportAdapter() {
		return agteleEMFUAImportAdapterEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public UAModelStorageClientFactory getUAModelStorageClientFactory() {
		return (UAModelStorageClientFactory)getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isCreated = false;

	/**
	 * Creates the meta-model objects for the package.  This method is
	 * guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void createPackageContents() {
		if (isCreated) return;
		isCreated = true;

		// Create classes and their features
		agteleEMFUAConnectorEClass = createEClass(AGTELE_EMFUA_CONNECTOR);

		agteleEMFUAImportAdapterEClass = createEClass(AGTELE_EMFUA_IMPORT_ADAPTER);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model.  This
	 * method is guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void initializePackageContents() {
		if (isInitialized) return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Obtain other dependent packages
		ImportAdapterPackage theImportAdapterPackage = (ImportAdapterPackage)EPackage.Registry.INSTANCE.getEPackage(ImportAdapterPackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		agteleEMFUAConnectorEClass.getESuperTypes().add(theImportAdapterPackage.getConnector());
		agteleEMFUAImportAdapterEClass.getESuperTypes().add(theImportAdapterPackage.getImportAdapter());

		// Initialize classes, features, and operations; add parameters
		initEClass(agteleEMFUAConnectorEClass, AgteleEMFUAConnector.class, "AgteleEMFUAConnector", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(agteleEMFUAImportAdapterEClass, AgteleEMFUAImportAdapter.class, "AgteleEMFUAImportAdapter", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		// Create resource
		createResource(eNS_URI);
	}

} //UAModelStorageClientPackageImpl
