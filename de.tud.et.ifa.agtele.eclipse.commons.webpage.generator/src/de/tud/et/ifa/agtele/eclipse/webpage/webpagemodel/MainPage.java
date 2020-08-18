/**
 */
package de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel;

import java.util.Map;

import org.eclipse.emf.common.util.DiagnosticChain;
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
 *   <li>{@link de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.MainPage#getMainPages <em>Main Pages</em>}</li>
 *   <li>{@link de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.MainPage#getAdditionalPages <em>Additional Pages</em>}</li>
 *   <li>{@link de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.MainPage#getSrcPath <em>Src Path</em>}</li>
 *   <li>{@link de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.MainPage#isSuppressMainMenu <em>Suppress Main Menu</em>}</li>
 * </ul>
 *
 * @see de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.WebPageModelPackage#getMainPage()
 * @model annotation="http://www.eclipse.org/emf/2002/Ecore constraints='DummyConstraint'"
 * @generated
 */
public interface MainPage extends Page {
	/**
	 * Returns the value of the '<em><b>Main Pages</b></em>' containment reference list.
	 * The list contents are of type {@link de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.MainPage}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Main Pages</em>' containment reference list.
	 * @see de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.WebPageModelPackage#getMainPage_MainPages()
	 * @model containment="true"
	 * @generated
	 */
	EList<MainPage> getMainPages();

	/**
	 * Returns the value of the '<em><b>Additional Pages</b></em>' containment reference list.
	 * The list contents are of type {@link de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.Page}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Additional Pages</em>' containment reference list.
	 * @see de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.WebPageModelPackage#getMainPage_AdditionalPages()
	 * @model containment="true"
	 * @generated
	 */
	EList<Page> getAdditionalPages();

	/**
	 * Returns the value of the '<em><b>Src Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Src Path</em>' attribute.
	 * @see #setSrcPath(String)
	 * @see de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.WebPageModelPackage#getMainPage_SrcPath()
	 * @model
	 * @generated
	 */
	String getSrcPath();

	/**
	 * Sets the value of the '{@link de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.MainPage#getSrcPath <em>Src Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Src Path</em>' attribute.
	 * @see #getSrcPath()
	 * @generated
	 */
	void setSrcPath(String value);

	/**
	 * Returns the value of the '<em><b>Suppress Main Menu</b></em>' attribute.
	 * The default value is <code>"false"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Suppress Main Menu</em>' attribute.
	 * @see #setSuppressMainMenu(boolean)
	 * @see de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.WebPageModelPackage#getMainPage_SuppressMainMenu()
	 * @model default="false"
	 * @generated
	 */
	boolean isSuppressMainMenu();

	/**
	 * Sets the value of the '{@link de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.MainPage#isSuppressMainMenu <em>Suppress Main Menu</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Suppress Main Menu</em>' attribute.
	 * @see #isSuppressMainMenu()
	 * @generated
	 */
	void setSuppressMainMenu(boolean value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model annotation="http://www.eclipse.org/emf/2002/GenModel"
	 * @generated
	 */
	boolean validateMainPageNotContainingWebPage(DiagnosticChain diagnostics, Map<?, ?> context);

} // MainPage
