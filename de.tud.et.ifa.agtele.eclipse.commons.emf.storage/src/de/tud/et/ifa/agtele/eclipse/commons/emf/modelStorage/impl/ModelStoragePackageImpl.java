/**
 */
package de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcorePackage;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.LinkedModel;
import de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.Model;
import de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.ModelAdapter;
import de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.ModelStorage;
import de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.ModelStorageFactory;
import de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.ModelStoragePackage;
import de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.PassiveModelStorage;
import de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.importAdapter.ImportAdapterPackage;
import de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.importAdapter.impl.ImportAdapterPackageImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class ModelStoragePackageImpl extends EPackageImpl implements ModelStoragePackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass modelStorageEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass passiveModelStorageEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass modelEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass linkedModelEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass modelAdapterEClass = null;

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
	 * @see de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.ModelStoragePackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private ModelStoragePackageImpl() {
		super(eNS_URI, ModelStorageFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link ModelStoragePackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static ModelStoragePackage init() {
		if (isInited) return (ModelStoragePackage)EPackage.Registry.INSTANCE.getEPackage(ModelStoragePackage.eNS_URI);

		// Obtain or create and register package
		Object registeredModelStoragePackage = EPackage.Registry.INSTANCE.get(eNS_URI);
		ModelStoragePackageImpl theModelStoragePackage = registeredModelStoragePackage instanceof ModelStoragePackageImpl ? (ModelStoragePackageImpl)registeredModelStoragePackage : new ModelStoragePackageImpl();

		isInited = true;

		// Initialize simple dependencies
		EcorePackage.eINSTANCE.eClass();

		// Obtain or create and register interdependencies
		Object registeredPackage = EPackage.Registry.INSTANCE.getEPackage(ImportAdapterPackage.eNS_URI);
		ImportAdapterPackageImpl theImportAdapterPackage = (ImportAdapterPackageImpl)(registeredPackage instanceof ImportAdapterPackageImpl ? registeredPackage : ImportAdapterPackage.eINSTANCE);

		// Create package meta-data objects
		theModelStoragePackage.createPackageContents();
		theImportAdapterPackage.createPackageContents();

		// Initialize created meta-data
		theModelStoragePackage.initializePackageContents();
		theImportAdapterPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theModelStoragePackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(ModelStoragePackage.eNS_URI, theModelStoragePackage);
		return theModelStoragePackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getModelStorage() {
		return modelStorageEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getModelStorage_Model() {
		return (EReference)modelStorageEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getModelStorage_Connector() {
		return (EReference)modelStorageEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getModelStorage_Name() {
		return (EAttribute)modelStorageEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getPassiveModelStorage() {
		return passiveModelStorageEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getModel() {
		return modelEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getModel_Content() {
		return (EReference)modelEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getModel_Uri() {
		return (EAttribute)modelEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getModel_ImportAdapter() {
		return (EReference)modelEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getLinkedModel() {
		return linkedModelEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getModelAdapter() {
		return modelAdapterEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ModelStorageFactory getModelStorageFactory() {
		return (ModelStorageFactory)getEFactoryInstance();
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
		modelStorageEClass = createEClass(MODEL_STORAGE);
		createEReference(modelStorageEClass, MODEL_STORAGE__MODEL);
		createEReference(modelStorageEClass, MODEL_STORAGE__CONNECTOR);
		createEAttribute(modelStorageEClass, MODEL_STORAGE__NAME);

		passiveModelStorageEClass = createEClass(PASSIVE_MODEL_STORAGE);

		modelEClass = createEClass(MODEL);
		createEReference(modelEClass, MODEL__CONTENT);
		createEAttribute(modelEClass, MODEL__URI);
		createEReference(modelEClass, MODEL__IMPORT_ADAPTER);

		linkedModelEClass = createEClass(LINKED_MODEL);

		modelAdapterEClass = createEClass(MODEL_ADAPTER);
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
		EcorePackage theEcorePackage = (EcorePackage)EPackage.Registry.INSTANCE.getEPackage(EcorePackage.eNS_URI);

		// Add subpackages
		getESubpackages().add(theImportAdapterPackage);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		passiveModelStorageEClass.getESuperTypes().add(this.getModelStorage());
		linkedModelEClass.getESuperTypes().add(this.getModel());
		modelAdapterEClass.getESuperTypes().add(this.getModel());

		// Initialize classes, features, and operations; add parameters
		initEClass(modelStorageEClass, ModelStorage.class, "ModelStorage", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getModelStorage_Model(), this.getModel(), null, "model", null, 0, -1, ModelStorage.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getModelStorage_Connector(), theImportAdapterPackage.getConnector(), null, "connector", null, 0, -1, ModelStorage.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getModelStorage_Name(), theEcorePackage.getEString(), "name", null, 0, 1, ModelStorage.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(passiveModelStorageEClass, PassiveModelStorage.class, "PassiveModelStorage", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(modelEClass, Model.class, "Model", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getModel_Content(), theEcorePackage.getEObject(), null, "content", null, 0, -1, Model.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEAttribute(getModel_Uri(), ecorePackage.getEString(), "uri", null, 1, 1, Model.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getModel_ImportAdapter(), theImportAdapterPackage.getImportAdapter(), theImportAdapterPackage.getImportAdapter_Model(), "importAdapter", null, 0, 1, Model.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(linkedModelEClass, LinkedModel.class, "LinkedModel", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(modelAdapterEClass, ModelAdapter.class, "ModelAdapter", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		// Create resource
		createResource(eNS_URI);

		// Create annotations
		// http://et.tu-dresden.de/ifa/emf/commons/2018/AgTeleGenModel
		createAgTeleGenModelAnnotations();
	}

	/**
	 * Initializes the annotations for <b>http://et.tu-dresden.de/ifa/emf/commons/2018/AgTeleGenModel</b>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void createAgTeleGenModelAnnotations() {
		String source = "http://et.tu-dresden.de/ifa/emf/commons/2018/AgTeleGenModel";
		addAnnotation
		  (getModelStorage_Model(),
		   source,
		   new String[] {
			   "get", "\r\nif (model == null) {\r\n\tmodel = new <%org.eclipse.emf.ecore.util.EObjectContainmentEList%>.Resolving<<%de.tud.et.ifa.agtele.eclipse.emf.modelStorage.Model%>>(Model.class, this,\r\n\t\t\t<%de.tud.et.ifa.agtele.eclipse.emf.modelStorage.ModelStoragePackage%>.MODEL_STORAGE__MODEL);\r\n\tthis.eAdapters().add(new <%org.eclipse.emf.ecore.util.EContentAdapter%>() {\r\n\r\n\t\t@SuppressWarnings(\"rawtypes\")\r\n\t\t@Override\r\n\t\tpublic void notifyChanged(<%org.eclipse.emf.common.notify.Notification%> notification) {\r\n\t\t\tif (notification.getFeature() == ModelStoragePackage.Literals.MODEL_STORAGE__MODEL) {\r\n\t\t\t\tif (notification.getEventType() == Notification.ADD) {\r\n\t\t\t\t\tif (notification.getNewValue() instanceof Model) {\r\n\t\t\t\t\t\tModel model = (Model) notification.getNewValue();\r\n\t\t\t\t\t\tif (model.getResourceSet() != null) {\r\n\t\t\t\t\t\t\tModelStorageImpl.this.registerResourceSet(model.getResourceSet(), model);\r\n\t\t\t\t\t\t}\r\n\t\t\t\t\t}\r\n\t\t\t\t} else if (notification.getEventType() == Notification.ADD_MANY) {\r\n\t\t\t\t\tif (notification.getNewValue() instanceof <%java.util.Collection%>) {\r\n\t\t\t\t\t\tfor (Object element : (Collection) notification.getNewValue()) {\r\n\t\t\t\t\t\t\tif (element instanceof Model) {\r\n\t\t\t\t\t\t\t\tif (((Model) element).getResourceSet() != null) {\r\n\t\t\t\t\t\t\t\t\tModelStorageImpl.this.registerResourceSet(\r\n\t\t\t\t\t\t\t\t\t\t\t((Model) element).getResourceSet(), ((Model) element));\r\n\t\t\t\t\t\t\t\t}\r\n\t\t\t\t\t\t\t}\r\n\t\t\t\t\t\t}\r\n\t\t\t\t\t}\r\n\t\t\t\t} else if (notification.getEventType() == Notification.REMOVE) {\r\n\t\t\t\t\tif (notification.getOldValue() instanceof Model) {\r\n\t\t\t\t\t\tModel model = (Model) notification.getOldValue();\r\n\t\t\t\t\t\tif (model.getResourceSet() != null) {\r\n\t\t\t\t\t\t\tModelStorageImpl.this.registerResourceSet(model.getResourceSet(), null);\r\n\t\t\t\t\t\t}\r\n\t\t\t\t\t}\r\n\t\t\t\t} else if (notification.getEventType() == Notification.REMOVE_MANY) {\r\n\t\t\t\t\tif (notification.getOldValue() instanceof Collection) {\r\n\t\t\t\t\t\tfor (Object element : (Collection) notification.getOldValue()) {\r\n\t\t\t\t\t\t\tif (element instanceof Model) {\r\n\t\t\t\t\t\t\t\tif (((Model) element).getResourceSet() != null) {\r\n\t\t\t\t\t\t\t\t\tModelStorageImpl.this\r\n\t\t\t\t\t\t\t\t\t\t\t.registerResourceSet(((Model) element).getResourceSet(), null);\r\n\t\t\t\t\t\t\t\t}\r\n\t\t\t\t\t\t\t}\r\n\t\t\t\t\t\t}\r\n\t\t\t\t\t}\r\n\t\t\t\t}\r\n\t\t\t}\r\n\t\t}\r\n\t});\r\n}\r\nreturn model;"
		   });
		addAnnotation
		  (getModel_Content(),
		   source,
		   new String[] {
			   "get", "\r\nif (content == null) {\r\n\tcontent = new <%org.eclipse.emf.ecore.util.EObjectResolvingEList%><<%org.eclipse.emf.ecore.EObject%>>(EObject.class, this, <%de.tud.et.ifa.agtele.eclipse.emf.modelStorage.ModelStoragePackage%>.MODEL__CONTENT);\r\n}\r\n\r\n<%java.util.ArrayList%><EObject> currentContent = new ArrayList<>();\r\n<%java.util.List%><EObject> toAdd, toRemove;\r\n\r\nfor (<%org.eclipse.emf.ecore.resource.Resource%> res : this.resourceSet.getResources()) {\r\n\tcurrentContent.addAll(res.getContents());\r\n}\r\n\r\ntoRemove = content.stream().filter(o -> !currentContent.contains(o)).collect(<%java.util.stream.Collectors%>.toList());\r\ntoAdd = currentContent.stream().filter(o -> !content.contains(o)).collect(Collectors.toList());\r\n\r\ncontent.removeAll(toRemove);\r\ncontent.addAll(toAdd);\r\n\r\nreturn content;"
		   });
	}

} //modelStoragePackageImpl
