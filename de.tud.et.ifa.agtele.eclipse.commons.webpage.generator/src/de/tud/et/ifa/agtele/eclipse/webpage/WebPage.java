/**
 */
package de.tud.et.ifa.agtele.eclipse.webpage;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Web Page</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link de.tud.et.ifa.agtele.eclipse.webpage.WebPage#getBaseUrl <em>Base Url</em>}</li>
 *   <li>{@link de.tud.et.ifa.agtele.eclipse.webpage.WebPage#getOut <em>Out</em>}</li>
 *   <li>{@link de.tud.et.ifa.agtele.eclipse.webpage.WebPage#getLang <em>Lang</em>}</li>
 * </ul>
 *
 * @see de.tud.et.ifa.agtele.eclipse.webpage.WebpagePackage#getWebPage()
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
	 * @see de.tud.et.ifa.agtele.eclipse.webpage.WebpagePackage#getWebPage_BaseUrl()
	 * @model
	 * @generated
	 */
	String getBaseUrl();

	/**
	 * Sets the value of the '{@link de.tud.et.ifa.agtele.eclipse.webpage.WebPage#getBaseUrl <em>Base Url</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Base Url</em>' attribute.
	 * @see #getBaseUrl()
	 * @generated
	 */
	void setBaseUrl(String value);

	/**
	 * Returns the value of the '<em><b>Out</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Out</em>' attribute.
	 * @see #setOut(String)
	 * @see de.tud.et.ifa.agtele.eclipse.webpage.WebpagePackage#getWebPage_Out()
	 * @model
	 * @generated
	 */
	String getOut();

	/**
	 * Sets the value of the '{@link de.tud.et.ifa.agtele.eclipse.webpage.WebPage#getOut <em>Out</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Out</em>' attribute.
	 * @see #getOut()
	 * @generated
	 */
	void setOut(String value);

	/**
	 * Returns the value of the '<em><b>Lang</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Lang</em>' attribute.
	 * @see #setLang(String)
	 * @see de.tud.et.ifa.agtele.eclipse.webpage.WebpagePackage#getWebPage_Lang()
	 * @model required="true"
	 * @generated
	 */
	String getLang();

	/**
	 * Sets the value of the '{@link de.tud.et.ifa.agtele.eclipse.webpage.WebPage#getLang <em>Lang</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Lang</em>' attribute.
	 * @see #getLang()
	 * @generated
	 */
	void setLang(String value);

} // WebPage
