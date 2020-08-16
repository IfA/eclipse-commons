/**
 */
package de.tud.et.ifa.agtele.eclipse.webpage.impl;

import de.tud.et.ifa.agtele.eclipse.webpage.WebPage;
import de.tud.et.ifa.agtele.eclipse.webpage.WebpagePackage;

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
 *   <li>{@link de.tud.et.ifa.agtele.eclipse.webpage.impl.WebPageImpl#getBaseUrl <em>Base Url</em>}</li>
 *   <li>{@link de.tud.et.ifa.agtele.eclipse.webpage.impl.WebPageImpl#getOut <em>Out</em>}</li>
 *   <li>{@link de.tud.et.ifa.agtele.eclipse.webpage.impl.WebPageImpl#getLang <em>Lang</em>}</li>
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
	 * The default value of the '{@link #getOut() <em>Out</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOut()
	 * @generated
	 * @ordered
	 */
	protected static final String OUT_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getOut() <em>Out</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOut()
	 * @generated
	 * @ordered
	 */
    protected String out = OUT_EDEFAULT;
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
		return WebpagePackage.Literals.WEB_PAGE;
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
			eNotify(new ENotificationImpl(this, Notification.SET, WebpagePackage.WEB_PAGE__BASE_URL, oldBaseUrl, baseUrl));
	
	}
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getOut() {	
	
		return out;
	}
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setOut(String newOut) {
	
		String oldOut = out;
		out = newOut;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, WebpagePackage.WEB_PAGE__OUT, oldOut, out));
	
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
			eNotify(new ENotificationImpl(this, Notification.SET, WebpagePackage.WEB_PAGE__LANG, oldLang, lang));
	
	}
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case WebpagePackage.WEB_PAGE__BASE_URL:
				return getBaseUrl();
			case WebpagePackage.WEB_PAGE__OUT:
				return getOut();
			case WebpagePackage.WEB_PAGE__LANG:
				return getLang();
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
			case WebpagePackage.WEB_PAGE__BASE_URL:
				setBaseUrl((String)newValue);
				return;
			case WebpagePackage.WEB_PAGE__OUT:
				setOut((String)newValue);
				return;
			case WebpagePackage.WEB_PAGE__LANG:
				setLang((String)newValue);
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
			case WebpagePackage.WEB_PAGE__BASE_URL:
				setBaseUrl(BASE_URL_EDEFAULT);
				return;
			case WebpagePackage.WEB_PAGE__OUT:
				setOut(OUT_EDEFAULT);
				return;
			case WebpagePackage.WEB_PAGE__LANG:
				setLang(LANG_EDEFAULT);
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
			case WebpagePackage.WEB_PAGE__BASE_URL:
				return BASE_URL_EDEFAULT == null ? baseUrl != null : !BASE_URL_EDEFAULT.equals(baseUrl);
			case WebpagePackage.WEB_PAGE__OUT:
				return OUT_EDEFAULT == null ? out != null : !OUT_EDEFAULT.equals(out);
			case WebpagePackage.WEB_PAGE__LANG:
				return LANG_EDEFAULT == null ? lang != null : !LANG_EDEFAULT.equals(lang);
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
		result.append(", out: ");
		result.append(out);
		result.append(", lang: ");
		result.append(lang);
		result.append(')');
		return result.toString();
	}

} //WebPageImpl
