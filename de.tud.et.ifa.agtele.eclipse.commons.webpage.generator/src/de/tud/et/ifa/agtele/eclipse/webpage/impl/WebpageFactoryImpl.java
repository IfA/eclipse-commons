/**
 */
package de.tud.et.ifa.agtele.eclipse.webpage.impl;

import de.tud.et.ifa.agtele.eclipse.webpage.*;

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
public class WebpageFactoryImpl extends EFactoryImpl implements WebpageFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static WebpageFactory init() {
		try {
			WebpageFactory theWebpageFactory = (WebpageFactory)EPackage.Registry.INSTANCE.getEFactory(WebpagePackage.eNS_URI);
			if (theWebpageFactory != null) {
				return theWebpageFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new WebpageFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public WebpageFactoryImpl() {
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
			case WebpagePackage.WEB_PAGE: return createWebPage();
			case WebpagePackage.MAIN_PAGE: return createMainPage();
			case WebpagePackage.PAGE: return createPage();
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
	public WebpagePackage getWebpagePackage() {
		return (WebpagePackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static WebpagePackage getPackage() {
		return WebpagePackage.eINSTANCE;
	}

} //WebpageFactoryImpl
