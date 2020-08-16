/**
 */
package de.tud.et.ifa.agtele.eclipse.webpage;

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
 * @see de.tud.et.ifa.agtele.eclipse.webpage.WebpageFactory
 * @model kind="package"
 * @generated
 */
public interface WebpagePackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "webpage";

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
	WebpagePackage eINSTANCE = de.tud.et.ifa.agtele.eclipse.webpage.impl.WebpagePackageImpl.init();

	/**
	 * The meta object id for the '{@link de.tud.et.ifa.agtele.eclipse.webpage.impl.PageImpl <em>Page</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.tud.et.ifa.agtele.eclipse.webpage.impl.PageImpl
	 * @see de.tud.et.ifa.agtele.eclipse.webpage.impl.WebpagePackageImpl#getPage()
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
	int PAGE__NAME = 0;

	/**
	 * The feature id for the '<em><b>Footer</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAGE__FOOTER = 1;

	/**
	 * The number of structural features of the '<em>Page</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAGE_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Page</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAGE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link de.tud.et.ifa.agtele.eclipse.webpage.impl.MainPageImpl <em>Main Page</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.tud.et.ifa.agtele.eclipse.webpage.impl.MainPageImpl
	 * @see de.tud.et.ifa.agtele.eclipse.webpage.impl.WebpagePackageImpl#getMainPage()
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
	 * The feature id for the '<em><b>Footer</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAIN_PAGE__FOOTER = PAGE__FOOTER;

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
	 * The number of structural features of the '<em>Main Page</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAIN_PAGE_FEATURE_COUNT = PAGE_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>Main Page</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAIN_PAGE_OPERATION_COUNT = PAGE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link de.tud.et.ifa.agtele.eclipse.webpage.impl.WebPageImpl <em>Web Page</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.tud.et.ifa.agtele.eclipse.webpage.impl.WebPageImpl
	 * @see de.tud.et.ifa.agtele.eclipse.webpage.impl.WebpagePackageImpl#getWebPage()
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
	 * The feature id for the '<em><b>Footer</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WEB_PAGE__FOOTER = MAIN_PAGE__FOOTER;

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
	 * The feature id for the '<em><b>Base Url</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WEB_PAGE__BASE_URL = MAIN_PAGE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Out</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WEB_PAGE__OUT = MAIN_PAGE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Lang</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WEB_PAGE__LANG = MAIN_PAGE_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Web Page</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WEB_PAGE_FEATURE_COUNT = MAIN_PAGE_FEATURE_COUNT + 3;

	/**
	 * The number of operations of the '<em>Web Page</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WEB_PAGE_OPERATION_COUNT = MAIN_PAGE_OPERATION_COUNT + 0;


	/**
	 * Returns the meta object for class '{@link de.tud.et.ifa.agtele.eclipse.webpage.WebPage <em>Web Page</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Web Page</em>'.
	 * @see de.tud.et.ifa.agtele.eclipse.webpage.WebPage
	 * @generated
	 */
	EClass getWebPage();

	/**
	 * Returns the meta object for the attribute '{@link de.tud.et.ifa.agtele.eclipse.webpage.WebPage#getBaseUrl <em>Base Url</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Base Url</em>'.
	 * @see de.tud.et.ifa.agtele.eclipse.webpage.WebPage#getBaseUrl()
	 * @see #getWebPage()
	 * @generated
	 */
	EAttribute getWebPage_BaseUrl();

	/**
	 * Returns the meta object for the attribute '{@link de.tud.et.ifa.agtele.eclipse.webpage.WebPage#getOut <em>Out</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Out</em>'.
	 * @see de.tud.et.ifa.agtele.eclipse.webpage.WebPage#getOut()
	 * @see #getWebPage()
	 * @generated
	 */
	EAttribute getWebPage_Out();

	/**
	 * Returns the meta object for the attribute '{@link de.tud.et.ifa.agtele.eclipse.webpage.WebPage#getLang <em>Lang</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Lang</em>'.
	 * @see de.tud.et.ifa.agtele.eclipse.webpage.WebPage#getLang()
	 * @see #getWebPage()
	 * @generated
	 */
	EAttribute getWebPage_Lang();

	/**
	 * Returns the meta object for class '{@link de.tud.et.ifa.agtele.eclipse.webpage.MainPage <em>Main Page</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Main Page</em>'.
	 * @see de.tud.et.ifa.agtele.eclipse.webpage.MainPage
	 * @generated
	 */
	EClass getMainPage();

	/**
	 * Returns the meta object for the containment reference list '{@link de.tud.et.ifa.agtele.eclipse.webpage.MainPage#getMainPages <em>Main Pages</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Main Pages</em>'.
	 * @see de.tud.et.ifa.agtele.eclipse.webpage.MainPage#getMainPages()
	 * @see #getMainPage()
	 * @generated
	 */
	EReference getMainPage_MainPages();

	/**
	 * Returns the meta object for the containment reference list '{@link de.tud.et.ifa.agtele.eclipse.webpage.MainPage#getAdditionalPages <em>Additional Pages</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Additional Pages</em>'.
	 * @see de.tud.et.ifa.agtele.eclipse.webpage.MainPage#getAdditionalPages()
	 * @see #getMainPage()
	 * @generated
	 */
	EReference getMainPage_AdditionalPages();

	/**
	 * Returns the meta object for class '{@link de.tud.et.ifa.agtele.eclipse.webpage.Page <em>Page</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Page</em>'.
	 * @see de.tud.et.ifa.agtele.eclipse.webpage.Page
	 * @generated
	 */
	EClass getPage();

	/**
	 * Returns the meta object for the attribute '{@link de.tud.et.ifa.agtele.eclipse.webpage.Page#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see de.tud.et.ifa.agtele.eclipse.webpage.Page#getName()
	 * @see #getPage()
	 * @generated
	 */
	EAttribute getPage_Name();

	/**
	 * Returns the meta object for the attribute '{@link de.tud.et.ifa.agtele.eclipse.webpage.Page#getFooter <em>Footer</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Footer</em>'.
	 * @see de.tud.et.ifa.agtele.eclipse.webpage.Page#getFooter()
	 * @see #getPage()
	 * @generated
	 */
	EAttribute getPage_Footer();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	WebpageFactory getWebpageFactory();

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
		 * The meta object literal for the '{@link de.tud.et.ifa.agtele.eclipse.webpage.impl.WebPageImpl <em>Web Page</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.tud.et.ifa.agtele.eclipse.webpage.impl.WebPageImpl
		 * @see de.tud.et.ifa.agtele.eclipse.webpage.impl.WebpagePackageImpl#getWebPage()
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
		 * The meta object literal for the '<em><b>Out</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute WEB_PAGE__OUT = eINSTANCE.getWebPage_Out();

		/**
		 * The meta object literal for the '<em><b>Lang</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute WEB_PAGE__LANG = eINSTANCE.getWebPage_Lang();

		/**
		 * The meta object literal for the '{@link de.tud.et.ifa.agtele.eclipse.webpage.impl.MainPageImpl <em>Main Page</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.tud.et.ifa.agtele.eclipse.webpage.impl.MainPageImpl
		 * @see de.tud.et.ifa.agtele.eclipse.webpage.impl.WebpagePackageImpl#getMainPage()
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
		 * The meta object literal for the '{@link de.tud.et.ifa.agtele.eclipse.webpage.impl.PageImpl <em>Page</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.tud.et.ifa.agtele.eclipse.webpage.impl.PageImpl
		 * @see de.tud.et.ifa.agtele.eclipse.webpage.impl.WebpagePackageImpl#getPage()
		 * @generated
		 */
		EClass PAGE = eINSTANCE.getPage();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PAGE__NAME = eINSTANCE.getPage_Name();

		/**
		 * The meta object literal for the '<em><b>Footer</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PAGE__FOOTER = eINSTANCE.getPage_Footer();

	}

} //WebpagePackage
