/**
 */
package de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Sub Page</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.SubPage#getSubPage <em>Sub Page</em>}</li>
 * </ul>
 *
 * @see de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.WebPageModelPackage#getSubPage()
 * @model
 * @generated
 */
public interface SubPage extends AbstractHTML {
	/**
	 * Returns the value of the '<em><b>Sub Page</b></em>' containment reference list.
	 * The list contents are of type {@link de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.SubPage}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Sub Page</em>' containment reference list.
	 * @see de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.WebPageModelPackage#getSubPage_SubPage()
	 * @model containment="true"
	 * @generated
	 */
	EList<SubPage> getSubPage();
	
	default MainPage getMainPage() {
		return null;
	}

} // SubPage
