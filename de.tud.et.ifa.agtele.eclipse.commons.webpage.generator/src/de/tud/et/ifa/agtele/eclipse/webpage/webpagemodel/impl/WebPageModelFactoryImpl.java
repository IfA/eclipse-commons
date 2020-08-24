/**
 */
package de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.impl;

import de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.*;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
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
			case WebPageModelPackage.ANNOUNCEMENT: return createAnnouncement();
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
	public Object createFromString(EDataType eDataType, String initialValue) {
		switch (eDataType.getClassifierID()) {
			case WebPageModelPackage.ANNOUNCEMENT_TYPE_ENUM:
				return createAnnouncementTypeEnumFromString(eDataType, initialValue);
			case WebPageModelPackage.ANNOUNCEMENT_LOCATION_ENUM:
				return createAnnouncementLocationEnumFromString(eDataType, initialValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String convertToString(EDataType eDataType, Object instanceValue) {
		switch (eDataType.getClassifierID()) {
			case WebPageModelPackage.ANNOUNCEMENT_TYPE_ENUM:
				return convertAnnouncementTypeEnumToString(eDataType, instanceValue);
			case WebPageModelPackage.ANNOUNCEMENT_LOCATION_ENUM:
				return convertAnnouncementLocationEnumToString(eDataType, instanceValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
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
	public Announcement createAnnouncement() {
		AnnouncementImpl announcement = new AnnouncementImpl();
		return announcement;
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
	public AnnouncementTypeEnum createAnnouncementTypeEnumFromString(EDataType eDataType, String initialValue) {
		AnnouncementTypeEnum result = AnnouncementTypeEnum.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertAnnouncementTypeEnumToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AnnouncementLocationEnum createAnnouncementLocationEnumFromString(EDataType eDataType, String initialValue) {
		AnnouncementLocationEnum result = AnnouncementLocationEnum.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertAnnouncementLocationEnumToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
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
