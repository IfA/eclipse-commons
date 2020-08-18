/**
 */
package de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EOperation;
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
 * @see de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.WebPageModelFactory
 * @model kind="package"
 * @generated
 */
public interface WebPageModelPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "webpagemodel";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://et.tu-dresden.de/ifa/WebPage/model/v0.1";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "wp";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	WebPageModelPackage eINSTANCE = de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.impl.WebPageModelPackageImpl.init();

	/**
	 * The meta object id for the '{@link de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.impl.BaseImpl <em>Base</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.impl.BaseImpl
	 * @see de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.impl.WebPageModelPackageImpl#getBase()
	 * @generated
	 */
	int BASE = 10;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BASE__NAME = 0;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BASE__ID = 1;

	/**
	 * The number of structural features of the '<em>Base</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BASE_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Base</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BASE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.impl.AbstractHTMLImpl <em>Abstract HTML</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.impl.AbstractHTMLImpl
	 * @see de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.impl.WebPageModelPackageImpl#getAbstractHTML()
	 * @generated
	 */
	int ABSTRACT_HTML = 3;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_HTML__NAME = BASE__NAME;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_HTML__ID = BASE__ID;

	/**
	 * The feature id for the '<em><b>Footer</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_HTML__FOOTER = BASE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Header</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_HTML__HEADER = BASE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Src Path Fragment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_HTML__SRC_PATH_FRAGMENT = BASE_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Scripts</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_HTML__SCRIPTS = BASE_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Styles</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_HTML__STYLES = BASE_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Title</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_HTML__TITLE = BASE_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Static Resources</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_HTML__STATIC_RESOURCES = BASE_FEATURE_COUNT + 6;

	/**
	 * The number of structural features of the '<em>Abstract HTML</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_HTML_FEATURE_COUNT = BASE_FEATURE_COUNT + 7;

	/**
	 * The number of operations of the '<em>Abstract HTML</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_HTML_OPERATION_COUNT = BASE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.impl.SubPageImpl <em>Sub Page</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.impl.SubPageImpl
	 * @see de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.impl.WebPageModelPackageImpl#getSubPage()
	 * @generated
	 */
	int SUB_PAGE = 4;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUB_PAGE__NAME = ABSTRACT_HTML__NAME;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUB_PAGE__ID = ABSTRACT_HTML__ID;

	/**
	 * The feature id for the '<em><b>Footer</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUB_PAGE__FOOTER = ABSTRACT_HTML__FOOTER;

	/**
	 * The feature id for the '<em><b>Header</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUB_PAGE__HEADER = ABSTRACT_HTML__HEADER;

	/**
	 * The feature id for the '<em><b>Src Path Fragment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUB_PAGE__SRC_PATH_FRAGMENT = ABSTRACT_HTML__SRC_PATH_FRAGMENT;

	/**
	 * The feature id for the '<em><b>Scripts</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUB_PAGE__SCRIPTS = ABSTRACT_HTML__SCRIPTS;

	/**
	 * The feature id for the '<em><b>Styles</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUB_PAGE__STYLES = ABSTRACT_HTML__STYLES;

	/**
	 * The feature id for the '<em><b>Title</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUB_PAGE__TITLE = ABSTRACT_HTML__TITLE;

	/**
	 * The feature id for the '<em><b>Static Resources</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUB_PAGE__STATIC_RESOURCES = ABSTRACT_HTML__STATIC_RESOURCES;

	/**
	 * The feature id for the '<em><b>Sub Page</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUB_PAGE__SUB_PAGE = ABSTRACT_HTML_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Sub Page</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUB_PAGE_FEATURE_COUNT = ABSTRACT_HTML_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Sub Page</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUB_PAGE_OPERATION_COUNT = ABSTRACT_HTML_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.impl.PageImpl <em>Page</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.impl.PageImpl
	 * @see de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.impl.WebPageModelPackageImpl#getPage()
	 * @generated
	 */
	int PAGE = 2;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAGE__NAME = SUB_PAGE__NAME;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAGE__ID = SUB_PAGE__ID;

	/**
	 * The feature id for the '<em><b>Footer</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAGE__FOOTER = SUB_PAGE__FOOTER;

	/**
	 * The feature id for the '<em><b>Header</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAGE__HEADER = SUB_PAGE__HEADER;

	/**
	 * The feature id for the '<em><b>Src Path Fragment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAGE__SRC_PATH_FRAGMENT = SUB_PAGE__SRC_PATH_FRAGMENT;

	/**
	 * The feature id for the '<em><b>Scripts</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAGE__SCRIPTS = SUB_PAGE__SCRIPTS;

	/**
	 * The feature id for the '<em><b>Styles</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAGE__STYLES = SUB_PAGE__STYLES;

	/**
	 * The feature id for the '<em><b>Title</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAGE__TITLE = SUB_PAGE__TITLE;

	/**
	 * The feature id for the '<em><b>Static Resources</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAGE__STATIC_RESOURCES = SUB_PAGE__STATIC_RESOURCES;

	/**
	 * The feature id for the '<em><b>Sub Page</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAGE__SUB_PAGE = SUB_PAGE__SUB_PAGE;

	/**
	 * The feature id for the '<em><b>Nav Icon</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAGE__NAV_ICON = SUB_PAGE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Page</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAGE_FEATURE_COUNT = SUB_PAGE_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Page</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAGE_OPERATION_COUNT = SUB_PAGE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.impl.MainPageImpl <em>Main Page</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.impl.MainPageImpl
	 * @see de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.impl.WebPageModelPackageImpl#getMainPage()
	 * @generated
	 */
	int MAIN_PAGE = 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAIN_PAGE__NAME = PAGE__NAME;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAIN_PAGE__ID = PAGE__ID;

	/**
	 * The feature id for the '<em><b>Footer</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAIN_PAGE__FOOTER = PAGE__FOOTER;

	/**
	 * The feature id for the '<em><b>Header</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAIN_PAGE__HEADER = PAGE__HEADER;

	/**
	 * The feature id for the '<em><b>Src Path Fragment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAIN_PAGE__SRC_PATH_FRAGMENT = PAGE__SRC_PATH_FRAGMENT;

	/**
	 * The feature id for the '<em><b>Scripts</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAIN_PAGE__SCRIPTS = PAGE__SCRIPTS;

	/**
	 * The feature id for the '<em><b>Styles</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAIN_PAGE__STYLES = PAGE__STYLES;

	/**
	 * The feature id for the '<em><b>Title</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAIN_PAGE__TITLE = PAGE__TITLE;

	/**
	 * The feature id for the '<em><b>Static Resources</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAIN_PAGE__STATIC_RESOURCES = PAGE__STATIC_RESOURCES;

	/**
	 * The feature id for the '<em><b>Sub Page</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAIN_PAGE__SUB_PAGE = PAGE__SUB_PAGE;

	/**
	 * The feature id for the '<em><b>Nav Icon</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAIN_PAGE__NAV_ICON = PAGE__NAV_ICON;

	/**
	 * The feature id for the '<em><b>Main Pages</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAIN_PAGE__MAIN_PAGES = PAGE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Additional Pages</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAIN_PAGE__ADDITIONAL_PAGES = PAGE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Src Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAIN_PAGE__SRC_PATH = PAGE_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Suppress Main Menu</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAIN_PAGE__SUPPRESS_MAIN_MENU = PAGE_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Main Page</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAIN_PAGE_FEATURE_COUNT = PAGE_FEATURE_COUNT + 4;

	/**
	 * The operation id for the '<em>Validate Main Page Not Containing Web Page</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAIN_PAGE___VALIDATE_MAIN_PAGE_NOT_CONTAINING_WEB_PAGE__DIAGNOSTICCHAIN_MAP = PAGE_OPERATION_COUNT + 0;

	/**
	 * The number of operations of the '<em>Main Page</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAIN_PAGE_OPERATION_COUNT = PAGE_OPERATION_COUNT + 1;

	/**
	 * The meta object id for the '{@link de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.impl.WebPageImpl <em>Web Page</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.impl.WebPageImpl
	 * @see de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.impl.WebPageModelPackageImpl#getWebPage()
	 * @generated
	 */
	int WEB_PAGE = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WEB_PAGE__NAME = MAIN_PAGE__NAME;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WEB_PAGE__ID = MAIN_PAGE__ID;

	/**
	 * The feature id for the '<em><b>Footer</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WEB_PAGE__FOOTER = MAIN_PAGE__FOOTER;

	/**
	 * The feature id for the '<em><b>Header</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WEB_PAGE__HEADER = MAIN_PAGE__HEADER;

	/**
	 * The feature id for the '<em><b>Src Path Fragment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WEB_PAGE__SRC_PATH_FRAGMENT = MAIN_PAGE__SRC_PATH_FRAGMENT;

	/**
	 * The feature id for the '<em><b>Scripts</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WEB_PAGE__SCRIPTS = MAIN_PAGE__SCRIPTS;

	/**
	 * The feature id for the '<em><b>Styles</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WEB_PAGE__STYLES = MAIN_PAGE__STYLES;

	/**
	 * The feature id for the '<em><b>Title</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WEB_PAGE__TITLE = MAIN_PAGE__TITLE;

	/**
	 * The feature id for the '<em><b>Static Resources</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WEB_PAGE__STATIC_RESOURCES = MAIN_PAGE__STATIC_RESOURCES;

	/**
	 * The feature id for the '<em><b>Sub Page</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WEB_PAGE__SUB_PAGE = MAIN_PAGE__SUB_PAGE;

	/**
	 * The feature id for the '<em><b>Nav Icon</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WEB_PAGE__NAV_ICON = MAIN_PAGE__NAV_ICON;

	/**
	 * The feature id for the '<em><b>Main Pages</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WEB_PAGE__MAIN_PAGES = MAIN_PAGE__MAIN_PAGES;

	/**
	 * The feature id for the '<em><b>Additional Pages</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WEB_PAGE__ADDITIONAL_PAGES = MAIN_PAGE__ADDITIONAL_PAGES;

	/**
	 * The feature id for the '<em><b>Src Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WEB_PAGE__SRC_PATH = MAIN_PAGE__SRC_PATH;

	/**
	 * The feature id for the '<em><b>Suppress Main Menu</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WEB_PAGE__SUPPRESS_MAIN_MENU = MAIN_PAGE__SUPPRESS_MAIN_MENU;

	/**
	 * The feature id for the '<em><b>Base Url</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WEB_PAGE__BASE_URL = MAIN_PAGE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Out Dir</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WEB_PAGE__OUT_DIR = MAIN_PAGE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Lang</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WEB_PAGE__LANG = MAIN_PAGE_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Resources Out Path Fragment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WEB_PAGE__RESOURCES_OUT_PATH_FRAGMENT = MAIN_PAGE_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Web Page</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WEB_PAGE_FEATURE_COUNT = MAIN_PAGE_FEATURE_COUNT + 4;

	/**
	 * The operation id for the '<em>Validate Main Page Not Containing Web Page</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WEB_PAGE___VALIDATE_MAIN_PAGE_NOT_CONTAINING_WEB_PAGE__DIAGNOSTICCHAIN_MAP = MAIN_PAGE___VALIDATE_MAIN_PAGE_NOT_CONTAINING_WEB_PAGE__DIAGNOSTICCHAIN_MAP;

	/**
	 * The number of operations of the '<em>Web Page</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WEB_PAGE_OPERATION_COUNT = MAIN_PAGE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.impl.AbstractKeyValImpl <em>Abstract Key Val</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.impl.AbstractKeyValImpl
	 * @see de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.impl.WebPageModelPackageImpl#getAbstractKeyVal()
	 * @generated
	 */
	int ABSTRACT_KEY_VAL = 5;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_KEY_VAL__NAME = BASE__NAME;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_KEY_VAL__ID = BASE__ID;

	/**
	 * The feature id for the '<em><b>Value</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_KEY_VAL__VALUE = BASE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Abstract Key Val</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_KEY_VAL_FEATURE_COUNT = BASE_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Abstract Key Val</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_KEY_VAL_OPERATION_COUNT = BASE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.impl.HtmlIncludeImpl <em>Html Include</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.impl.HtmlIncludeImpl
	 * @see de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.impl.WebPageModelPackageImpl#getHtmlInclude()
	 * @generated
	 */
	int HTML_INCLUDE = 6;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HTML_INCLUDE__NAME = ABSTRACT_KEY_VAL__NAME;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HTML_INCLUDE__ID = ABSTRACT_KEY_VAL__ID;

	/**
	 * The feature id for the '<em><b>Value</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HTML_INCLUDE__VALUE = ABSTRACT_KEY_VAL__VALUE;

	/**
	 * The number of structural features of the '<em>Html Include</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HTML_INCLUDE_FEATURE_COUNT = ABSTRACT_KEY_VAL_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Html Include</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HTML_INCLUDE_OPERATION_COUNT = ABSTRACT_KEY_VAL_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.impl.ValueImpl <em>Value</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.impl.ValueImpl
	 * @see de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.impl.WebPageModelPackageImpl#getValue()
	 * @generated
	 */
	int VALUE = 7;

	/**
	 * The number of structural features of the '<em>Value</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VALUE_FEATURE_COUNT = 0;

	/**
	 * The number of operations of the '<em>Value</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VALUE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.impl.FileValueImpl <em>File Value</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.impl.FileValueImpl
	 * @see de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.impl.WebPageModelPackageImpl#getFileValue()
	 * @generated
	 */
	int FILE_VALUE = 8;

	/**
	 * The number of structural features of the '<em>File Value</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILE_VALUE_FEATURE_COUNT = VALUE_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>File Value</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILE_VALUE_OPERATION_COUNT = VALUE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.impl.StringValueImpl <em>String Value</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.impl.StringValueImpl
	 * @see de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.impl.WebPageModelPackageImpl#getStringValue()
	 * @generated
	 */
	int STRING_VALUE = 9;

	/**
	 * The number of structural features of the '<em>String Value</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_VALUE_FEATURE_COUNT = VALUE_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>String Value</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_VALUE_OPERATION_COUNT = VALUE_OPERATION_COUNT + 0;


	/**
	 * Returns the meta object for class '{@link de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.WebPage <em>Web Page</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Web Page</em>'.
	 * @see de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.WebPage
	 * @generated
	 */
	EClass getWebPage();

	/**
	 * Returns the meta object for the attribute '{@link de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.WebPage#getBaseUrl <em>Base Url</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Base Url</em>'.
	 * @see de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.WebPage#getBaseUrl()
	 * @see #getWebPage()
	 * @generated
	 */
	EAttribute getWebPage_BaseUrl();

	/**
	 * Returns the meta object for the attribute '{@link de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.WebPage#getOutDir <em>Out Dir</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Out Dir</em>'.
	 * @see de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.WebPage#getOutDir()
	 * @see #getWebPage()
	 * @generated
	 */
	EAttribute getWebPage_OutDir();

	/**
	 * Returns the meta object for the attribute '{@link de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.WebPage#getLang <em>Lang</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Lang</em>'.
	 * @see de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.WebPage#getLang()
	 * @see #getWebPage()
	 * @generated
	 */
	EAttribute getWebPage_Lang();

	/**
	 * Returns the meta object for the attribute '{@link de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.WebPage#getResourcesOutPathFragment <em>Resources Out Path Fragment</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Resources Out Path Fragment</em>'.
	 * @see de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.WebPage#getResourcesOutPathFragment()
	 * @see #getWebPage()
	 * @generated
	 */
	EAttribute getWebPage_ResourcesOutPathFragment();

	/**
	 * Returns the meta object for class '{@link de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.MainPage <em>Main Page</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Main Page</em>'.
	 * @see de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.MainPage
	 * @generated
	 */
	EClass getMainPage();

	/**
	 * Returns the meta object for the containment reference list '{@link de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.MainPage#getMainPages <em>Main Pages</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Main Pages</em>'.
	 * @see de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.MainPage#getMainPages()
	 * @see #getMainPage()
	 * @generated
	 */
	EReference getMainPage_MainPages();

	/**
	 * Returns the meta object for the containment reference list '{@link de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.MainPage#getAdditionalPages <em>Additional Pages</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Additional Pages</em>'.
	 * @see de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.MainPage#getAdditionalPages()
	 * @see #getMainPage()
	 * @generated
	 */
	EReference getMainPage_AdditionalPages();

	/**
	 * Returns the meta object for the attribute '{@link de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.MainPage#getSrcPath <em>Src Path</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Src Path</em>'.
	 * @see de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.MainPage#getSrcPath()
	 * @see #getMainPage()
	 * @generated
	 */
	EAttribute getMainPage_SrcPath();

	/**
	 * Returns the meta object for the attribute '{@link de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.MainPage#isSuppressMainMenu <em>Suppress Main Menu</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Suppress Main Menu</em>'.
	 * @see de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.MainPage#isSuppressMainMenu()
	 * @see #getMainPage()
	 * @generated
	 */
	EAttribute getMainPage_SuppressMainMenu();

	/**
	 * Returns the meta object for the '{@link de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.MainPage#validateMainPageNotContainingWebPage(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map) <em>Validate Main Page Not Containing Web Page</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Validate Main Page Not Containing Web Page</em>' operation.
	 * @see de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.MainPage#validateMainPageNotContainingWebPage(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map)
	 * @generated
	 */
	EOperation getMainPage__ValidateMainPageNotContainingWebPage__DiagnosticChain_Map();

	/**
	 * Returns the meta object for class '{@link de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.Page <em>Page</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Page</em>'.
	 * @see de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.Page
	 * @generated
	 */
	EClass getPage();

	/**
	 * Returns the meta object for the attribute '{@link de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.Page#getNavIcon <em>Nav Icon</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Nav Icon</em>'.
	 * @see de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.Page#getNavIcon()
	 * @see #getPage()
	 * @generated
	 */
	EAttribute getPage_NavIcon();

	/**
	 * Returns the meta object for class '{@link de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.AbstractHTML <em>Abstract HTML</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Abstract HTML</em>'.
	 * @see de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.AbstractHTML
	 * @generated
	 */
	EClass getAbstractHTML();

	/**
	 * Returns the meta object for the containment reference '{@link de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.AbstractHTML#getFooter <em>Footer</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Footer</em>'.
	 * @see de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.AbstractHTML#getFooter()
	 * @see #getAbstractHTML()
	 * @generated
	 */
	EReference getAbstractHTML_Footer();

	/**
	 * Returns the meta object for the containment reference '{@link de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.AbstractHTML#getHeader <em>Header</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Header</em>'.
	 * @see de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.AbstractHTML#getHeader()
	 * @see #getAbstractHTML()
	 * @generated
	 */
	EReference getAbstractHTML_Header();

	/**
	 * Returns the meta object for the attribute '{@link de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.AbstractHTML#getSrcPathFragment <em>Src Path Fragment</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Src Path Fragment</em>'.
	 * @see de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.AbstractHTML#getSrcPathFragment()
	 * @see #getAbstractHTML()
	 * @generated
	 */
	EAttribute getAbstractHTML_SrcPathFragment();

	/**
	 * Returns the meta object for the reference list '{@link de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.AbstractHTML#getScripts <em>Scripts</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Scripts</em>'.
	 * @see de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.AbstractHTML#getScripts()
	 * @see #getAbstractHTML()
	 * @generated
	 */
	EReference getAbstractHTML_Scripts();

	/**
	 * Returns the meta object for the reference list '{@link de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.AbstractHTML#getStyles <em>Styles</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Styles</em>'.
	 * @see de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.AbstractHTML#getStyles()
	 * @see #getAbstractHTML()
	 * @generated
	 */
	EReference getAbstractHTML_Styles();

	/**
	 * Returns the meta object for the attribute '{@link de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.AbstractHTML#getTitle <em>Title</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Title</em>'.
	 * @see de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.AbstractHTML#getTitle()
	 * @see #getAbstractHTML()
	 * @generated
	 */
	EAttribute getAbstractHTML_Title();

	/**
	 * Returns the meta object for the attribute list '{@link de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.AbstractHTML#getStaticResources <em>Static Resources</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Static Resources</em>'.
	 * @see de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.AbstractHTML#getStaticResources()
	 * @see #getAbstractHTML()
	 * @generated
	 */
	EAttribute getAbstractHTML_StaticResources();

	/**
	 * Returns the meta object for class '{@link de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.SubPage <em>Sub Page</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Sub Page</em>'.
	 * @see de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.SubPage
	 * @generated
	 */
	EClass getSubPage();

	/**
	 * Returns the meta object for the containment reference list '{@link de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.SubPage#getSubPage <em>Sub Page</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Sub Page</em>'.
	 * @see de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.SubPage#getSubPage()
	 * @see #getSubPage()
	 * @generated
	 */
	EReference getSubPage_SubPage();

	/**
	 * Returns the meta object for class '{@link de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.AbstractKeyVal <em>Abstract Key Val</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Abstract Key Val</em>'.
	 * @see de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.AbstractKeyVal
	 * @generated
	 */
	EClass getAbstractKeyVal();

	/**
	 * Returns the meta object for the reference list '{@link de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.AbstractKeyVal#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Value</em>'.
	 * @see de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.AbstractKeyVal#getValue()
	 * @see #getAbstractKeyVal()
	 * @generated
	 */
	EReference getAbstractKeyVal_Value();

	/**
	 * Returns the meta object for class '{@link de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.HtmlInclude <em>Html Include</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Html Include</em>'.
	 * @see de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.HtmlInclude
	 * @generated
	 */
	EClass getHtmlInclude();

	/**
	 * Returns the meta object for class '{@link de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.Value <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Value</em>'.
	 * @see de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.Value
	 * @generated
	 */
	EClass getValue();

	/**
	 * Returns the meta object for class '{@link de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.FileValue <em>File Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>File Value</em>'.
	 * @see de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.FileValue
	 * @generated
	 */
	EClass getFileValue();

	/**
	 * Returns the meta object for class '{@link de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.StringValue <em>String Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>String Value</em>'.
	 * @see de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.StringValue
	 * @generated
	 */
	EClass getStringValue();

	/**
	 * Returns the meta object for class '{@link de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.Base <em>Base</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Base</em>'.
	 * @see de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.Base
	 * @generated
	 */
	EClass getBase();

	/**
	 * Returns the meta object for the attribute '{@link de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.Base#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.Base#getName()
	 * @see #getBase()
	 * @generated
	 */
	EAttribute getBase_Name();

	/**
	 * Returns the meta object for the attribute '{@link de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.Base#getId <em>Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.Base#getId()
	 * @see #getBase()
	 * @generated
	 */
	EAttribute getBase_Id();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	WebPageModelFactory getWebPageModelFactory();

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
		 * The meta object literal for the '{@link de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.impl.WebPageImpl <em>Web Page</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.impl.WebPageImpl
		 * @see de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.impl.WebPageModelPackageImpl#getWebPage()
		 * @generated
		 */
		EClass WEB_PAGE = eINSTANCE.getWebPage();

		/**
		 * The meta object literal for the '<em><b>Base Url</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute WEB_PAGE__BASE_URL = eINSTANCE.getWebPage_BaseUrl();

		/**
		 * The meta object literal for the '<em><b>Out Dir</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute WEB_PAGE__OUT_DIR = eINSTANCE.getWebPage_OutDir();

		/**
		 * The meta object literal for the '<em><b>Lang</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute WEB_PAGE__LANG = eINSTANCE.getWebPage_Lang();

		/**
		 * The meta object literal for the '<em><b>Resources Out Path Fragment</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute WEB_PAGE__RESOURCES_OUT_PATH_FRAGMENT = eINSTANCE.getWebPage_ResourcesOutPathFragment();

		/**
		 * The meta object literal for the '{@link de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.impl.MainPageImpl <em>Main Page</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.impl.MainPageImpl
		 * @see de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.impl.WebPageModelPackageImpl#getMainPage()
		 * @generated
		 */
		EClass MAIN_PAGE = eINSTANCE.getMainPage();

		/**
		 * The meta object literal for the '<em><b>Main Pages</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MAIN_PAGE__MAIN_PAGES = eINSTANCE.getMainPage_MainPages();

		/**
		 * The meta object literal for the '<em><b>Additional Pages</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MAIN_PAGE__ADDITIONAL_PAGES = eINSTANCE.getMainPage_AdditionalPages();

		/**
		 * The meta object literal for the '<em><b>Src Path</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MAIN_PAGE__SRC_PATH = eINSTANCE.getMainPage_SrcPath();

		/**
		 * The meta object literal for the '<em><b>Suppress Main Menu</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MAIN_PAGE__SUPPRESS_MAIN_MENU = eINSTANCE.getMainPage_SuppressMainMenu();

		/**
		 * The meta object literal for the '<em><b>Validate Main Page Not Containing Web Page</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation MAIN_PAGE___VALIDATE_MAIN_PAGE_NOT_CONTAINING_WEB_PAGE__DIAGNOSTICCHAIN_MAP = eINSTANCE.getMainPage__ValidateMainPageNotContainingWebPage__DiagnosticChain_Map();

		/**
		 * The meta object literal for the '{@link de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.impl.PageImpl <em>Page</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.impl.PageImpl
		 * @see de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.impl.WebPageModelPackageImpl#getPage()
		 * @generated
		 */
		EClass PAGE = eINSTANCE.getPage();

		/**
		 * The meta object literal for the '<em><b>Nav Icon</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PAGE__NAV_ICON = eINSTANCE.getPage_NavIcon();

		/**
		 * The meta object literal for the '{@link de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.impl.AbstractHTMLImpl <em>Abstract HTML</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.impl.AbstractHTMLImpl
		 * @see de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.impl.WebPageModelPackageImpl#getAbstractHTML()
		 * @generated
		 */
		EClass ABSTRACT_HTML = eINSTANCE.getAbstractHTML();

		/**
		 * The meta object literal for the '<em><b>Footer</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ABSTRACT_HTML__FOOTER = eINSTANCE.getAbstractHTML_Footer();

		/**
		 * The meta object literal for the '<em><b>Header</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ABSTRACT_HTML__HEADER = eINSTANCE.getAbstractHTML_Header();

		/**
		 * The meta object literal for the '<em><b>Src Path Fragment</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ABSTRACT_HTML__SRC_PATH_FRAGMENT = eINSTANCE.getAbstractHTML_SrcPathFragment();

		/**
		 * The meta object literal for the '<em><b>Scripts</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ABSTRACT_HTML__SCRIPTS = eINSTANCE.getAbstractHTML_Scripts();

		/**
		 * The meta object literal for the '<em><b>Styles</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ABSTRACT_HTML__STYLES = eINSTANCE.getAbstractHTML_Styles();

		/**
		 * The meta object literal for the '<em><b>Title</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ABSTRACT_HTML__TITLE = eINSTANCE.getAbstractHTML_Title();

		/**
		 * The meta object literal for the '<em><b>Static Resources</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ABSTRACT_HTML__STATIC_RESOURCES = eINSTANCE.getAbstractHTML_StaticResources();

		/**
		 * The meta object literal for the '{@link de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.impl.SubPageImpl <em>Sub Page</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.impl.SubPageImpl
		 * @see de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.impl.WebPageModelPackageImpl#getSubPage()
		 * @generated
		 */
		EClass SUB_PAGE = eINSTANCE.getSubPage();

		/**
		 * The meta object literal for the '<em><b>Sub Page</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SUB_PAGE__SUB_PAGE = eINSTANCE.getSubPage_SubPage();

		/**
		 * The meta object literal for the '{@link de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.impl.AbstractKeyValImpl <em>Abstract Key Val</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.impl.AbstractKeyValImpl
		 * @see de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.impl.WebPageModelPackageImpl#getAbstractKeyVal()
		 * @generated
		 */
		EClass ABSTRACT_KEY_VAL = eINSTANCE.getAbstractKeyVal();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ABSTRACT_KEY_VAL__VALUE = eINSTANCE.getAbstractKeyVal_Value();

		/**
		 * The meta object literal for the '{@link de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.impl.HtmlIncludeImpl <em>Html Include</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.impl.HtmlIncludeImpl
		 * @see de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.impl.WebPageModelPackageImpl#getHtmlInclude()
		 * @generated
		 */
		EClass HTML_INCLUDE = eINSTANCE.getHtmlInclude();

		/**
		 * The meta object literal for the '{@link de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.impl.ValueImpl <em>Value</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.impl.ValueImpl
		 * @see de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.impl.WebPageModelPackageImpl#getValue()
		 * @generated
		 */
		EClass VALUE = eINSTANCE.getValue();

		/**
		 * The meta object literal for the '{@link de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.impl.FileValueImpl <em>File Value</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.impl.FileValueImpl
		 * @see de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.impl.WebPageModelPackageImpl#getFileValue()
		 * @generated
		 */
		EClass FILE_VALUE = eINSTANCE.getFileValue();

		/**
		 * The meta object literal for the '{@link de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.impl.StringValueImpl <em>String Value</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.impl.StringValueImpl
		 * @see de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.impl.WebPageModelPackageImpl#getStringValue()
		 * @generated
		 */
		EClass STRING_VALUE = eINSTANCE.getStringValue();

		/**
		 * The meta object literal for the '{@link de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.impl.BaseImpl <em>Base</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.impl.BaseImpl
		 * @see de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.impl.WebPageModelPackageImpl#getBase()
		 * @generated
		 */
		EClass BASE = eINSTANCE.getBase();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BASE__NAME = eINSTANCE.getBase_Name();

		/**
		 * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BASE__ID = eINSTANCE.getBase_Id();

	}

} //WebPageModelPackage
