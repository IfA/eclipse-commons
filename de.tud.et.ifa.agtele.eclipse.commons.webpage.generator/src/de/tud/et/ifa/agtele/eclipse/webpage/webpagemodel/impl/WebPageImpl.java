/**
 */
package de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.impl;

import de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.WebPage;
import de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.WebPageModelPackage;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Web Page</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.impl.WebPageImpl#getBaseUrl <em>Base Url</em>}</li>
 *   <li>{@link de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.impl.WebPageImpl#getOutDir <em>Out Dir</em>}</li>
 *   <li>{@link de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.impl.WebPageImpl#getLang <em>Lang</em>}</li>
 *   <li>{@link de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.impl.WebPageImpl#getResourcesOutPathFragment <em>Resources Out Path Fragment</em>}</li>
 * </ul>
 *
 * @generated
 */
public class WebPageImpl extends MainPageImpl implements WebPage {
	/**
	 * The default value of the '{@link #getBaseUrl() <em>Base Url</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBaseUrl()
	 * @generated
	 * @ordered
	 */
	protected static final String BASE_URL_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getBaseUrl() <em>Base Url</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBaseUrl()
	 * @generated
	 * @ordered
	 */
    protected String baseUrl = BASE_URL_EDEFAULT;
	/**
	 * The default value of the '{@link #getOutDir() <em>Out Dir</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOutDir()
	 * @generated
	 * @ordered
	 */
	protected static final String OUT_DIR_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getOutDir() <em>Out Dir</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOutDir()
	 * @generated
	 * @ordered
	 */
    protected String outDir = OUT_DIR_EDEFAULT;
	/**
	 * The default value of the '{@link #getLang() <em>Lang</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLang()
	 * @generated
	 * @ordered
	 */
	protected static final String LANG_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getLang() <em>Lang</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLang()
	 * @generated
	 * @ordered
	 */
    protected String lang = LANG_EDEFAULT;
	/**
	 * The default value of the '{@link #getResourcesOutPathFragment() <em>Resources Out Path Fragment</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getResourcesOutPathFragment()
	 * @generated
	 * @ordered
	 */
	protected static final String RESOURCES_OUT_PATH_FRAGMENT_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getResourcesOutPathFragment() <em>Resources Out Path Fragment</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getResourcesOutPathFragment()
	 * @generated
	 * @ordered
	 */
    protected String resourcesOutPathFragment = RESOURCES_OUT_PATH_FRAGMENT_EDEFAULT;
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected WebPageImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return WebPageModelPackage.Literals.WEB_PAGE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getBaseUrl() {
	
		return baseUrl;
	}
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setBaseUrl(String newBaseUrl) {
	
		String oldBaseUrl = baseUrl;
		baseUrl = newBaseUrl;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, WebPageModelPackage.WEB_PAGE__BASE_URL, oldBaseUrl, baseUrl));
	
	}
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getOutDir() {
	
		return outDir;
	}
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setOutDir(String newOutDir) {
	
		String oldOutDir = outDir;
		outDir = newOutDir;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, WebPageModelPackage.WEB_PAGE__OUT_DIR, oldOutDir, outDir));
	
	}
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getLang() {
	
		return lang;
	}
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setLang(String newLang) {
	
		String oldLang = lang;
		lang = newLang;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, WebPageModelPackage.WEB_PAGE__LANG, oldLang, lang));
	
	}
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getResourcesOutPathFragment() {
	
		return resourcesOutPathFragment;
	}
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setResourcesOutPathFragment(String newResourcesOutPathFragment) {
	
		String oldResourcesOutPathFragment = resourcesOutPathFragment;
		resourcesOutPathFragment = newResourcesOutPathFragment;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, WebPageModelPackage.WEB_PAGE__RESOURCES_OUT_PATH_FRAGMENT, oldResourcesOutPathFragment, resourcesOutPathFragment));
	
	}
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case WebPageModelPackage.WEB_PAGE__BASE_URL:
				return getBaseUrl();
			case WebPageModelPackage.WEB_PAGE__OUT_DIR:
				return getOutDir();
			case WebPageModelPackage.WEB_PAGE__LANG:
				return getLang();
			case WebPageModelPackage.WEB_PAGE__RESOURCES_OUT_PATH_FRAGMENT:
				return getResourcesOutPathFragment();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case WebPageModelPackage.WEB_PAGE__BASE_URL:
				setBaseUrl((String)newValue);
				return;
			case WebPageModelPackage.WEB_PAGE__OUT_DIR:
				setOutDir((String)newValue);
				return;
			case WebPageModelPackage.WEB_PAGE__LANG:
				setLang((String)newValue);
				return;
			case WebPageModelPackage.WEB_PAGE__RESOURCES_OUT_PATH_FRAGMENT:
				setResourcesOutPathFragment((String)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case WebPageModelPackage.WEB_PAGE__BASE_URL:
				setBaseUrl(BASE_URL_EDEFAULT);
				return;
			case WebPageModelPackage.WEB_PAGE__OUT_DIR:
				setOutDir(OUT_DIR_EDEFAULT);
				return;
			case WebPageModelPackage.WEB_PAGE__LANG:
				setLang(LANG_EDEFAULT);
				return;
			case WebPageModelPackage.WEB_PAGE__RESOURCES_OUT_PATH_FRAGMENT:
				setResourcesOutPathFragment(RESOURCES_OUT_PATH_FRAGMENT_EDEFAULT);
				return;
		}
		super.eUnset(featureID);
	}

 	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case WebPageModelPackage.WEB_PAGE__BASE_URL:
				return BASE_URL_EDEFAULT == null ? baseUrl != null : !BASE_URL_EDEFAULT.equals(baseUrl);
			case WebPageModelPackage.WEB_PAGE__OUT_DIR:
				return OUT_DIR_EDEFAULT == null ? outDir != null : !OUT_DIR_EDEFAULT.equals(outDir);
			case WebPageModelPackage.WEB_PAGE__LANG:
				return LANG_EDEFAULT == null ? lang != null : !LANG_EDEFAULT.equals(lang);
			case WebPageModelPackage.WEB_PAGE__RESOURCES_OUT_PATH_FRAGMENT:
				return RESOURCES_OUT_PATH_FRAGMENT_EDEFAULT == null ? resourcesOutPathFragment != null : !RESOURCES_OUT_PATH_FRAGMENT_EDEFAULT.equals(resourcesOutPathFragment);
		}
		return super.eIsSet(featureID);
	}
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuilder result = new StringBuilder(super.toString());
		result.append(" (baseUrl: ");
		result.append(baseUrl);
		result.append(", outDir: ");
		result.append(outDir);
		result.append(", lang: ");
		result.append(lang);
		result.append(", resourcesOutPathFragment: ");
		result.append(resourcesOutPathFragment);
		result.append(')');
		return result.toString();
	}

} //WebPageImpl
