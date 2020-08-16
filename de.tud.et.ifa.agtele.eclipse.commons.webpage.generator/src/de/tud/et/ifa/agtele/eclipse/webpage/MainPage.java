/**
 */
package de.tud.et.ifa.agtele.eclipse.webpage;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Main Page</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link de.tud.et.ifa.agtele.eclipse.webpage.MainPage#getMainPages <em>Main Pages</em>}</li>
 *   <li>{@link de.tud.et.ifa.agtele.eclipse.webpage.MainPage#getAdditionalPages <em>Additional Pages</em>}</li>
 * </ul>
 *
 * @see de.tud.et.ifa.agtele.eclipse.webpage.WebpagePackage#getMainPage()
 * @model
 * @generated
 */
public interface MainPage extends Page {
	/**
	 * Returns the value of the '<em><b>Main Pages</b></em>' containment reference list.
	 * The list contents are of type {@link de.tud.et.ifa.agtele.eclipse.webpage.MainPage}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Main Pages</em>' containment reference list.
	 * @see de.tud.et.ifa.agtele.eclipse.webpage.WebpagePackage#getMainPage_MainPages()
	 * @model containment="true"
	 * @generated
	 */
	EList<MainPage> getMainPages();

	/**
	 * Returns the value of the '<em><b>Additional Pages</b></em>' containment reference list.
	 * The list contents are of type {@link de.tud.et.ifa.agtele.eclipse.webpage.Page}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Additional Pages</em>' containment reference list.
	 * @see de.tud.et.ifa.agtele.eclipse.webpage.WebpagePackage#getMainPage_AdditionalPages()
	 * @model containment="true"
	 * @generated
	 */
	EList<Page> getAdditionalPages();

} // MainPage
