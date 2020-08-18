/**
 */
package de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.impl;

import de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.*;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class WebPageModelFactoryImpl extends EFactoryImpl implements WebPageModelFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static WebPageModelFactory init() {
		try {
			WebPageModelFactory theWebPageModelFactory = (WebPageModelFactory)EPackage.Registry.INSTANCE.getEFactory(WebPageModelPackage.eNS_URI);
			if (theWebPageModelFactory != null) {
				return theWebPageModelFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new WebPageModelFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public WebPageModelFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case WebPageModelPackage.WEB_PAGE: return createWebPage();
			case WebPageModelPackage.MAIN_PAGE: return createMainPage();
			case WebPageModelPackage.PAGE: return createPage();
			case WebPageModelPackage.SUB_PAGE: return createSubPage();
			case WebPageModelPackage.HTML_INCLUDE: return createHtmlInclude();
			case WebPageModelPackage.FILE_VALUE: return createFileValue();
			case WebPageModelPackage.STRING_VALUE: return createStringValue();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public WebPage createWebPage() {
		WebPageImpl webPage = new WebPageImpl();
		return webPage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public MainPage createMainPage() {
		MainPageImpl mainPage = new MainPageImpl();
		return mainPage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Page createPage() {
		PageImpl page = new PageImpl();
		return page;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public SubPage createSubPage() {
		SubPageImpl subPage = new SubPageImpl();
		return subPage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public HtmlInclude createHtmlInclude() {
		HtmlIncludeImpl htmlInclude = new HtmlIncludeImpl();
		return htmlInclude;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public FileValue createFileValue() {
		FileValueImpl fileValue = new FileValueImpl();
		return fileValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public StringValue createStringValue() {
		StringValueImpl stringValue = new StringValueImpl();
		return stringValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public WebPageModelPackage getWebPageModelPackage() {
		return (WebPageModelPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static WebPageModelPackage getPackage() {
		return WebPageModelPackage.eINSTANCE;
	}

} //WebPageModelFactoryImpl
