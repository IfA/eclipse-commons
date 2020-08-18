/**
 */
package de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Web Page</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.WebPage#getBaseUrl <em>Base Url</em>}</li>
 *   <li>{@link de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.WebPage#getOutDir <em>Out Dir</em>}</li>
 *   <li>{@link de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.WebPage#getLang <em>Lang</em>}</li>
 *   <li>{@link de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.WebPage#getResourcesOutPathFragment <em>Resources Out Path Fragment</em>}</li>
 * </ul>
 *
 * @see de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.WebPageModelPackage#getWebPage()
 * @model
 * @generated
 */
public interface WebPage extends MainPage {
	/**
	 * Returns the value of the '<em><b>Base Url</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Base Url</em>' attribute.
	 * @see #setBaseUrl(String)
	 * @see de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.WebPageModelPackage#getWebPage_BaseUrl()
	 * @model
	 * @generated
	 */
	String getBaseUrl();

	/**
	 * Sets the value of the '{@link de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.WebPage#getBaseUrl <em>Base Url</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Base Url</em>' attribute.
	 * @see #getBaseUrl()
	 * @generated
	 */
	void setBaseUrl(String value);

	/**
	 * Returns the value of the '<em><b>Out Dir</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Out Dir</em>' attribute.
	 * @see #setOutDir(String)
	 * @see de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.WebPageModelPackage#getWebPage_OutDir()
	 * @model
	 * @generated
	 */
	String getOutDir();

	/**
	 * Sets the value of the '{@link de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.WebPage#getOutDir <em>Out Dir</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Out Dir</em>' attribute.
	 * @see #getOutDir()
	 * @generated
	 */
	void setOutDir(String value);

	/**
	 * Returns the value of the '<em><b>Lang</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Lang</em>' attribute.
	 * @see #setLang(String)
	 * @see de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.WebPageModelPackage#getWebPage_Lang()
	 * @model required="true"
	 * @generated
	 */
	String getLang();

	/**
	 * Sets the value of the '{@link de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.WebPage#getLang <em>Lang</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Lang</em>' attribute.
	 * @see #getLang()
	 * @generated
	 */
	void setLang(String value);

	/**
	 * Returns the value of the '<em><b>Resources Out Path Fragment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Resources Out Path Fragment</em>' attribute.
	 * @see #setResourcesOutPathFragment(String)
	 * @see de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.WebPageModelPackage#getWebPage_ResourcesOutPathFragment()
	 * @model
	 * @generated
	 */
	String getResourcesOutPathFragment();

	/**
	 * Sets the value of the '{@link de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.WebPage#getResourcesOutPathFragment <em>Resources Out Path Fragment</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Resources Out Path Fragment</em>' attribute.
	 * @see #getResourcesOutPathFragment()
	 * @generated
	 */
	void setResourcesOutPathFragment(String value);

} // WebPage
