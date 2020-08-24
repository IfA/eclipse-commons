/**
 */
package de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Html Include</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.HtmlInclude#isInline <em>Inline</em>}</li>
 * </ul>
 *
 * @see de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.WebPageModelPackage#getHtmlInclude()
 * @model
 * @generated
 */
public interface HtmlInclude extends AbstractKeyVal {

	/**
	 * Returns the value of the '<em><b>Inline</b></em>' attribute.
	 * The default value is <code>"false"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Inline</em>' attribute.
	 * @see #setInline(boolean)
	 * @see de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.WebPageModelPackage#getHtmlInclude_Inline()
	 * @model default="false"
	 * @generated
	 */
	boolean isInline();

	/**
	 * Sets the value of the '{@link de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.HtmlInclude#isInline <em>Inline</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Inline</em>' attribute.
	 * @see #isInline()
	 * @generated
	 */
	void setInline(boolean value);
} // HtmlInclude
