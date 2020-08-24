/**
 */
package de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.impl;

import de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.AbstractHTML;
import de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.AbstractKeyVal;
import de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.Announcement;
import de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.AnnouncementLocationEnum;
import de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.AnnouncementTypeEnum;
import de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.Base;
import de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.FileValue;
import de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.HtmlInclude;
import de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.MainPage;
import de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.Page;
import de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.StringValue;
import de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.SubPage;
import de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.Value;
import de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.WebPage;
import de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.WebPageModelFactory;
import de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.WebPageModelPackage;

import de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.util.WebPageModelValidator;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EGenericType;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EValidator;

import org.eclipse.emf.ecore.impl.EPackageImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class WebPageModelPackageImpl extends EPackageImpl implements WebPageModelPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass webPageEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass mainPageEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass pageEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass abstractHTMLEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass announcementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass subPageEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass abstractKeyValEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass htmlIncludeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass valueEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass fileValueEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass stringValueEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass baseEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum announcementTypeEnumEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum announcementLocationEnumEEnum = null;

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
	 * @see de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.WebPageModelPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private WebPageModelPackageImpl() {
		super(eNS_URI, WebPageModelFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link WebPageModelPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static WebPageModelPackage init() {
		if (isInited) return (WebPageModelPackage)EPackage.Registry.INSTANCE.getEPackage(WebPageModelPackage.eNS_URI);

		// Obtain or create and register package
		Object registeredWebPageModelPackage = EPackage.Registry.INSTANCE.get(eNS_URI);
		WebPageModelPackageImpl theWebPageModelPackage = registeredWebPageModelPackage instanceof WebPageModelPackageImpl ? (WebPageModelPackageImpl)registeredWebPageModelPackage : new WebPageModelPackageImpl();

		isInited = true;

		// Create package meta-data objects
		theWebPageModelPackage.createPackageContents();

		// Initialize created meta-data
		theWebPageModelPackage.initializePackageContents();

		// Register package validator
		EValidator.Registry.INSTANCE.put
			(theWebPageModelPackage,
			 new EValidator.Descriptor() {
				 @Override
				 public EValidator getEValidator() {
					 return WebPageModelValidator.INSTANCE;
				 }
			 });

		// Mark meta-data to indicate it can't be changed
		theWebPageModelPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(WebPageModelPackage.eNS_URI, theWebPageModelPackage);
		return theWebPageModelPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getWebPage() {
		return webPageEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getWebPage_BaseUrl() {
		return (EAttribute)webPageEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getWebPage_OutDir() {
		return (EAttribute)webPageEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getWebPage_Lang() {
		return (EAttribute)webPageEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getWebPage_ResourcesOutPathFragment() {
		return (EAttribute)webPageEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getWebPage_Alternatives() {
		return (EReference)webPageEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getWebPage_InvAlternatives() {
		return (EReference)webPageEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getMainPage() {
		return mainPageEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getMainPage_MainPages() {
		return (EReference)mainPageEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getMainPage_AdditionalPages() {
		return (EReference)mainPageEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getMainPage_SrcPath() {
		return (EAttribute)mainPageEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getMainPage__ValidateMainPageNotContainingWebPage__DiagnosticChain_Map() {
		return mainPageEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getPage() {
		return pageEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getPage_NavIcon() {
		return (EReference)pageEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getPage_SuppressMainMenu() {
		return (EAttribute)pageEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getAbstractHTML() {
		return abstractHTMLEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getAbstractHTML_Footer() {
		return (EReference)abstractHTMLEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getAbstractHTML_Header() {
		return (EReference)abstractHTMLEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getAbstractHTML_SrcPathFragment() {
		return (EAttribute)abstractHTMLEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getAbstractHTML_Scripts() {
		return (EReference)abstractHTMLEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getAbstractHTML_Styles() {
		return (EReference)abstractHTMLEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getAbstractHTML_Title() {
		return (EAttribute)abstractHTMLEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getAbstractHTML_StaticResources() {
		return (EAttribute)abstractHTMLEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getAbstractHTML_Content() {
		return (EReference)abstractHTMLEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getAbstractHTML_ExternalUrl() {
		return (EAttribute)abstractHTMLEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getAbstractHTML_Announcement() {
		return (EReference)abstractHTMLEClass.getEStructuralFeatures().get(9);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getAnnouncement() {
		return announcementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getAnnouncement_Propagate() {
		return (EAttribute)announcementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getAnnouncement_Type() {
		return (EAttribute)announcementEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getAnnouncement_Location() {
		return (EAttribute)announcementEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getAnnouncement_Content() {
		return (EReference)announcementEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getAnnouncement_Closable() {
		return (EAttribute)announcementEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getSubPage() {
		return subPageEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getSubPage_SubPage() {
		return (EReference)subPageEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getSubPage__ValidateSubPage__DiagnosticChain_Map() {
		return subPageEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getAbstractKeyVal() {
		return abstractKeyValEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getAbstractKeyVal_Value() {
		return (EReference)abstractKeyValEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getAbstractKeyVal__ValidateName__DiagnosticChain_Map() {
		return abstractKeyValEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getHtmlInclude() {
		return htmlIncludeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getHtmlInclude_Inline() {
		return (EAttribute)htmlIncludeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getValue() {
		return valueEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getFileValue() {
		return fileValueEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getFileValue_Value() {
		return (EAttribute)fileValueEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getStringValue() {
		return stringValueEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getStringValue_Value() {
		return (EAttribute)stringValueEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getBase() {
		return baseEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getBase_Name() {
		return (EAttribute)baseEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getBase_Id() {
		return (EAttribute)baseEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getBase_CreatedOn() {
		return (EAttribute)baseEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getBase_CreatedBy() {
		return (EAttribute)baseEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getBase_LastModified() {
		return (EAttribute)baseEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getBase_LastModifiedBy() {
		return (EAttribute)baseEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EEnum getAnnouncementTypeEnum() {
		return announcementTypeEnumEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EEnum getAnnouncementLocationEnum() {
		return announcementLocationEnumEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public WebPageModelFactory getWebPageModelFactory() {
		return (WebPageModelFactory)getEFactoryInstance();
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
		webPageEClass = createEClass(WEB_PAGE);
		createEAttribute(webPageEClass, WEB_PAGE__BASE_URL);
		createEAttribute(webPageEClass, WEB_PAGE__OUT_DIR);
		createEAttribute(webPageEClass, WEB_PAGE__LANG);
		createEAttribute(webPageEClass, WEB_PAGE__RESOURCES_OUT_PATH_FRAGMENT);
		createEReference(webPageEClass, WEB_PAGE__ALTERNATIVES);
		createEReference(webPageEClass, WEB_PAGE__INV_ALTERNATIVES);

		mainPageEClass = createEClass(MAIN_PAGE);
		createEReference(mainPageEClass, MAIN_PAGE__MAIN_PAGES);
		createEReference(mainPageEClass, MAIN_PAGE__ADDITIONAL_PAGES);
		createEAttribute(mainPageEClass, MAIN_PAGE__SRC_PATH);
		createEOperation(mainPageEClass, MAIN_PAGE___VALIDATE_MAIN_PAGE_NOT_CONTAINING_WEB_PAGE__DIAGNOSTICCHAIN_MAP);

		pageEClass = createEClass(PAGE);
		createEAttribute(pageEClass, PAGE__SUPPRESS_MAIN_MENU);
		createEReference(pageEClass, PAGE__NAV_ICON);

		abstractHTMLEClass = createEClass(ABSTRACT_HTML);
		createEReference(abstractHTMLEClass, ABSTRACT_HTML__FOOTER);
		createEReference(abstractHTMLEClass, ABSTRACT_HTML__HEADER);
		createEAttribute(abstractHTMLEClass, ABSTRACT_HTML__SRC_PATH_FRAGMENT);
		createEReference(abstractHTMLEClass, ABSTRACT_HTML__SCRIPTS);
		createEReference(abstractHTMLEClass, ABSTRACT_HTML__STYLES);
		createEAttribute(abstractHTMLEClass, ABSTRACT_HTML__TITLE);
		createEAttribute(abstractHTMLEClass, ABSTRACT_HTML__STATIC_RESOURCES);
		createEReference(abstractHTMLEClass, ABSTRACT_HTML__CONTENT);
		createEAttribute(abstractHTMLEClass, ABSTRACT_HTML__EXTERNAL_URL);
		createEReference(abstractHTMLEClass, ABSTRACT_HTML__ANNOUNCEMENT);

		announcementEClass = createEClass(ANNOUNCEMENT);
		createEAttribute(announcementEClass, ANNOUNCEMENT__PROPAGATE);
		createEAttribute(announcementEClass, ANNOUNCEMENT__TYPE);
		createEAttribute(announcementEClass, ANNOUNCEMENT__LOCATION);
		createEReference(announcementEClass, ANNOUNCEMENT__CONTENT);
		createEAttribute(announcementEClass, ANNOUNCEMENT__CLOSABLE);

		subPageEClass = createEClass(SUB_PAGE);
		createEReference(subPageEClass, SUB_PAGE__SUB_PAGE);
		createEOperation(subPageEClass, SUB_PAGE___VALIDATE_SUB_PAGE__DIAGNOSTICCHAIN_MAP);

		abstractKeyValEClass = createEClass(ABSTRACT_KEY_VAL);
		createEReference(abstractKeyValEClass, ABSTRACT_KEY_VAL__VALUE);
		createEOperation(abstractKeyValEClass, ABSTRACT_KEY_VAL___VALIDATE_NAME__DIAGNOSTICCHAIN_MAP);

		htmlIncludeEClass = createEClass(HTML_INCLUDE);
		createEAttribute(htmlIncludeEClass, HTML_INCLUDE__INLINE);

		valueEClass = createEClass(VALUE);

		fileValueEClass = createEClass(FILE_VALUE);
		createEAttribute(fileValueEClass, FILE_VALUE__VALUE);

		stringValueEClass = createEClass(STRING_VALUE);
		createEAttribute(stringValueEClass, STRING_VALUE__VALUE);

		baseEClass = createEClass(BASE);
		createEAttribute(baseEClass, BASE__NAME);
		createEAttribute(baseEClass, BASE__ID);
		createEAttribute(baseEClass, BASE__CREATED_ON);
		createEAttribute(baseEClass, BASE__CREATED_BY);
		createEAttribute(baseEClass, BASE__LAST_MODIFIED);
		createEAttribute(baseEClass, BASE__LAST_MODIFIED_BY);

		// Create enums
		announcementTypeEnumEEnum = createEEnum(ANNOUNCEMENT_TYPE_ENUM);
		announcementLocationEnumEEnum = createEEnum(ANNOUNCEMENT_LOCATION_ENUM);
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

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		webPageEClass.getESuperTypes().add(this.getMainPage());
		mainPageEClass.getESuperTypes().add(this.getPage());
		pageEClass.getESuperTypes().add(this.getSubPage());
		abstractHTMLEClass.getESuperTypes().add(this.getBase());
		announcementEClass.getESuperTypes().add(this.getBase());
		subPageEClass.getESuperTypes().add(this.getAbstractHTML());
		abstractKeyValEClass.getESuperTypes().add(this.getBase());
		htmlIncludeEClass.getESuperTypes().add(this.getAbstractKeyVal());
		fileValueEClass.getESuperTypes().add(this.getValue());
		stringValueEClass.getESuperTypes().add(this.getValue());

		// Initialize classes, features, and operations; add parameters
		initEClass(webPageEClass, WebPage.class, "WebPage", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getWebPage_BaseUrl(), ecorePackage.getEString(), "baseUrl", null, 0, 1, WebPage.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getWebPage_OutDir(), ecorePackage.getEString(), "outDir", null, 0, 1, WebPage.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getWebPage_Lang(), ecorePackage.getEString(), "lang", null, 1, 1, WebPage.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getWebPage_ResourcesOutPathFragment(), ecorePackage.getEString(), "resourcesOutPathFragment", null, 0, 1, WebPage.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getWebPage_Alternatives(), this.getWebPage(), this.getWebPage_InvAlternatives(), "alternatives", null, 0, -1, WebPage.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getWebPage_InvAlternatives(), this.getWebPage(), this.getWebPage_Alternatives(), "invAlternatives", null, 0, -1, WebPage.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(mainPageEClass, MainPage.class, "MainPage", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getMainPage_MainPages(), this.getMainPage(), null, "mainPages", null, 0, -1, MainPage.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getMainPage_AdditionalPages(), this.getPage(), null, "additionalPages", null, 0, -1, MainPage.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getMainPage_SrcPath(), ecorePackage.getEString(), "srcPath", null, 0, 1, MainPage.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		EOperation op = initEOperation(getMainPage__ValidateMainPageNotContainingWebPage__DiagnosticChain_Map(), ecorePackage.getEBoolean(), "validateMainPageNotContainingWebPage", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEDiagnosticChain(), "diagnostics", 0, 1, IS_UNIQUE, IS_ORDERED);
		EGenericType g1 = createEGenericType(ecorePackage.getEMap());
		EGenericType g2 = createEGenericType();
		g1.getETypeArguments().add(g2);
		g2 = createEGenericType();
		g1.getETypeArguments().add(g2);
		addEParameter(op, g1, "context", 0, 1, IS_UNIQUE, IS_ORDERED);

		initEClass(pageEClass, Page.class, "Page", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getPage_SuppressMainMenu(), ecorePackage.getEBoolean(), "suppressMainMenu", "false", 0, 1, Page.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getPage_NavIcon(), this.getValue(), null, "navIcon", null, 0, 1, Page.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(abstractHTMLEClass, AbstractHTML.class, "AbstractHTML", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getAbstractHTML_Footer(), this.getValue(), null, "footer", null, 0, 1, AbstractHTML.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getAbstractHTML_Header(), this.getValue(), null, "header", null, 0, 1, AbstractHTML.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAbstractHTML_SrcPathFragment(), ecorePackage.getEString(), "srcPathFragment", null, 0, 1, AbstractHTML.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getAbstractHTML_Scripts(), this.getHtmlInclude(), null, "scripts", null, 0, -1, AbstractHTML.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getAbstractHTML_Styles(), this.getHtmlInclude(), null, "styles", null, 0, -1, AbstractHTML.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAbstractHTML_Title(), ecorePackage.getEString(), "title", null, 1, 1, AbstractHTML.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAbstractHTML_StaticResources(), ecorePackage.getEString(), "staticResources", null, 0, -1, AbstractHTML.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getAbstractHTML_Content(), this.getValue(), null, "content", null, 0, 1, AbstractHTML.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAbstractHTML_ExternalUrl(), ecorePackage.getEString(), "externalUrl", null, 0, 1, AbstractHTML.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getAbstractHTML_Announcement(), this.getAnnouncement(), null, "announcement", null, 0, -1, AbstractHTML.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(announcementEClass, Announcement.class, "Announcement", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getAnnouncement_Propagate(), ecorePackage.getEBoolean(), "propagate", "false", 0, 1, Announcement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAnnouncement_Type(), this.getAnnouncementTypeEnum(), "type", "primary", 0, 1, Announcement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAnnouncement_Location(), this.getAnnouncementLocationEnum(), "location", "AboveHeading", 0, 1, Announcement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getAnnouncement_Content(), this.getValue(), null, "content", null, 0, 1, Announcement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAnnouncement_Closable(), ecorePackage.getEBoolean(), "closable", "false", 0, 1, Announcement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(subPageEClass, SubPage.class, "SubPage", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getSubPage_SubPage(), this.getSubPage(), null, "subPage", null, 0, -1, SubPage.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		op = initEOperation(getSubPage__ValidateSubPage__DiagnosticChain_Map(), ecorePackage.getEBoolean(), "validateSubPage", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEDiagnosticChain(), "diagnostics", 0, 1, IS_UNIQUE, IS_ORDERED);
		g1 = createEGenericType(ecorePackage.getEMap());
		g2 = createEGenericType();
		g1.getETypeArguments().add(g2);
		g2 = createEGenericType();
		g1.getETypeArguments().add(g2);
		addEParameter(op, g1, "context", 0, 1, IS_UNIQUE, IS_ORDERED);

		initEClass(abstractKeyValEClass, AbstractKeyVal.class, "AbstractKeyVal", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getAbstractKeyVal_Value(), this.getValue(), null, "value", null, 0, -1, AbstractKeyVal.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		op = initEOperation(getAbstractKeyVal__ValidateName__DiagnosticChain_Map(), ecorePackage.getEBoolean(), "validateName", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEDiagnosticChain(), "diagnostics", 0, 1, IS_UNIQUE, IS_ORDERED);
		g1 = createEGenericType(ecorePackage.getEMap());
		g2 = createEGenericType();
		g1.getETypeArguments().add(g2);
		g2 = createEGenericType();
		g1.getETypeArguments().add(g2);
		addEParameter(op, g1, "context", 0, 1, IS_UNIQUE, IS_ORDERED);

		initEClass(htmlIncludeEClass, HtmlInclude.class, "HtmlInclude", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getHtmlInclude_Inline(), ecorePackage.getEBoolean(), "inline", "false", 0, 1, HtmlInclude.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(valueEClass, Value.class, "Value", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(fileValueEClass, FileValue.class, "FileValue", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getFileValue_Value(), ecorePackage.getEString(), "value", null, 0, 1, FileValue.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(stringValueEClass, StringValue.class, "StringValue", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getStringValue_Value(), ecorePackage.getEString(), "value", null, 0, 1, StringValue.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(baseEClass, Base.class, "Base", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getBase_Name(), ecorePackage.getEString(), "name", null, 1, 1, Base.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getBase_Id(), ecorePackage.getEString(), "id", null, 1, 1, Base.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getBase_CreatedOn(), ecorePackage.getEString(), "createdOn", null, 0, 1, Base.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getBase_CreatedBy(), ecorePackage.getEString(), "createdBy", null, 0, 1, Base.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getBase_LastModified(), ecorePackage.getEString(), "lastModified", null, 0, 1, Base.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getBase_LastModifiedBy(), ecorePackage.getEString(), "lastModifiedBy", null, 0, 1, Base.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Initialize enums and add enum literals
		initEEnum(announcementTypeEnumEEnum, AnnouncementTypeEnum.class, "AnnouncementTypeEnum");
		addEEnumLiteral(announcementTypeEnumEEnum, AnnouncementTypeEnum.PRIMARY);
		addEEnumLiteral(announcementTypeEnumEEnum, AnnouncementTypeEnum.SECONDARY);
		addEEnumLiteral(announcementTypeEnumEEnum, AnnouncementTypeEnum.SUCCESS);
		addEEnumLiteral(announcementTypeEnumEEnum, AnnouncementTypeEnum.DANGER);
		addEEnumLiteral(announcementTypeEnumEEnum, AnnouncementTypeEnum.WARNING);
		addEEnumLiteral(announcementTypeEnumEEnum, AnnouncementTypeEnum.INFO);
		addEEnumLiteral(announcementTypeEnumEEnum, AnnouncementTypeEnum.LIGHT);
		addEEnumLiteral(announcementTypeEnumEEnum, AnnouncementTypeEnum.DARK);

		initEEnum(announcementLocationEnumEEnum, AnnouncementLocationEnum.class, "AnnouncementLocationEnum");
		addEEnumLiteral(announcementLocationEnumEEnum, AnnouncementLocationEnum.ABOVE_PAGE);
		addEEnumLiteral(announcementLocationEnumEEnum, AnnouncementLocationEnum.ABOVE_CONTENT);
		addEEnumLiteral(announcementLocationEnumEEnum, AnnouncementLocationEnum.ABOVE_HEADING);
		addEEnumLiteral(announcementLocationEnumEEnum, AnnouncementLocationEnum.BELOW_HEADING);
		addEEnumLiteral(announcementLocationEnumEEnum, AnnouncementLocationEnum.BELOW_CONTENT);
		addEEnumLiteral(announcementLocationEnumEEnum, AnnouncementLocationEnum.ABOVE_FOOTER);
		addEEnumLiteral(announcementLocationEnumEEnum, AnnouncementLocationEnum.BELOW_FOOTER);

		// Create resource
		createResource(eNS_URI);

		// Create annotations
		// http://www.eclipse.org/emf/2002/Ecore
		createEcoreAnnotations();
		// http://www.eclipse.org/emf/2002/GenModel
		createGenModelAnnotations();
	}

	/**
	 * Initializes the annotations for <b>http://www.eclipse.org/emf/2002/Ecore</b>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void createEcoreAnnotations() {
		String source = "http://www.eclipse.org/emf/2002/Ecore";
		addAnnotation
		  (mainPageEClass,
		   source,
		   new String[] {
			   "constraints", "DummyConstraint"
		   });
	}

	/**
	 * Initializes the annotations for <b>http://www.eclipse.org/emf/2002/GenModel</b>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void createGenModelAnnotations() {
		String source = "http://www.eclipse.org/emf/2002/GenModel";
		addAnnotation
		  (getMainPage__ValidateMainPageNotContainingWebPage__DiagnosticChain_Map(),
		   source,
		   new String[] {
			   "body", "for (<%org.eclipse.emf.ecore.EObject%> o : this.eContents()) {\r\n\tif (o instanceof <%de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.WebPage%>) {\r\n\t\tif (diagnostics != null) {\r\n\t\t\tdiagnostics.add(new <%org.eclipse.emf.common.util.BasicDiagnostic%>(<%org.eclipse.emf.common.util.Diagnostic%>.ERROR, <%de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.util.WebPageModelValidator%>.DIAGNOSTIC_SOURCE,\r\n\t\t\t\t\tWebPageModelValidator.MAIN_PAGE__VALIDATE_MAIN_PAGE_NOT_CONTAINING_WEB_PAGE,\r\n\t\t\t\t\t\"WebPage must be the model root\", new Object[] { this }));\r\n\t\t}\r\n\t\treturn false;\r\n\t}\r\n}\r\nreturn true;"
		   });
		addAnnotation
		  (getSubPage__ValidateSubPage__DiagnosticChain_Map(),
		   source,
		   new String[] {
			   "body", "boolean valid = true;\r\nfor (<%de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.SubPage%> page : this.getSubPage()) {\r\n\tif (page instanceof <%de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.Page%>) {\r\n\t\tif (diagnostics != null) {\r\n\t\t\tdiagnostics.add(new <%org.eclipse.emf.common.util.BasicDiagnostic%>(<%org.eclipse.emf.common.util.Diagnostic%>.ERROR, <%de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.util.WebPageModelValidator%>.DIAGNOSTIC_SOURCE,\r\n\t\t\t\t\tWebPageModelValidator.SUB_PAGE__VALIDATE_SUB_PAGE, \"SubPage must only contain SubPages\",\r\n\t\t\t\t\tnew Object[] { this, page }));\r\n\t\t}\r\n\t\tvalid = false;\r\n\t}\r\n}\r\nreturn valid;"
		   });
		addAnnotation
		  (getAbstractKeyVal__ValidateName__DiagnosticChain_Map(),
		   source,
		   new String[] {
			   "body", "<%org.eclipse.emf.ecore.EStructuralFeature%> feature = this.eContainingFeature();\r\n<%org.eclipse.emf.ecore.EObject%> container = this.eContainer();\r\nboolean result = true;\r\nif (feature.isMany()) {\r\n\t<%org.eclipse.emf.common.util.EList%><? extends <%de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.AbstractKeyVal%>> value = (EList<? extends AbstractKeyVal>) container.eGet(feature);\r\n\tint index = value.indexOf(this);\r\n\r\n\tfor (int i = 0; i < index; i += 1) {\r\n\t\tAbstractKeyVal val = value.get(i);\r\n\t\tif (val.getName() != null && val.getName().equals(this.getName())) {\r\n\t\t\tif (diagnostics != null) {\r\n\t\t\t\tdiagnostics\r\n\t\t\t\t\t\t.add(new <%org.eclipse.emf.common.util.BasicDiagnostic%>(<%org.eclipse.emf.common.util.Diagnostic%>.ERROR, <%de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.util.WebPageModelValidator%>.DIAGNOSTIC_SOURCE,\r\n\t\t\t\t\t\t\t\tWebPageModelValidator.ABSTRACT_KEY_VAL__VALIDATE_NAME, \"Duplicate name \'\"\r\n\t\t\t\t\t\t\t\t\t\t+ this.getName() + \"\' in feature \'\" + feature.getName() + \"\'\",\r\n\t\t\t\t\t\t\t\tnew Object[] { this }));\r\n\t\t\t}\r\n\t\t\tresult = false;\r\n\t\t}\r\n\t}\r\n\r\n}\r\n\r\nif (this.getName() == null || this.getName().isBlank()) {\r\n\tif (diagnostics != null) {\r\n\t\tdiagnostics.add(new BasicDiagnostic(Diagnostic.ERROR, WebPageModelValidator.DIAGNOSTIC_SOURCE,\r\n\t\t\t\tWebPageModelValidator.ABSTRACT_KEY_VAL__VALIDATE_NAME, \"name must be set\",\r\n\t\t\t\tnew Object[] { this }));\r\n\t}\r\n\tresult = false;\r\n}\r\nreturn result;"
		   });
	}

} //WebPageModelPackageImpl
