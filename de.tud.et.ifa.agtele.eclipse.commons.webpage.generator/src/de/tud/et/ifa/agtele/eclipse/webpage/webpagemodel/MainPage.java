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
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model annotation="http://www.eclipse.org/emf/2002/GenModel body='for (&lt;%org.eclipse.emf.ecore.EObject%&gt; o : this.eContents()) {\r\n\tif (o instanceof &lt;%de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.WebPage%&gt;) {\r\n\t\tif (diagnostics != null) {\r\n\t\t\tdiagnostics.add(new &lt;%org.eclipse.emf.common.util.BasicDiagnostic%&gt;(&lt;%org.eclipse.emf.common.util.Diagnostic%&gt;.ERROR, &lt;%de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.util.WebPageModelValidator%&gt;.DIAGNOSTIC_SOURCE,\r\n\t\t\t\t\tWebPageModelValidator.MAIN_PAGE__VALIDATE_MAIN_PAGE_NOT_CONTAINING_WEB_PAGE,\r\n\t\t\t\t\t\"WebPage must be the model root\", new Object[] { this }));\r\n\t\t}\r\n\t\treturn false;\r\n\t}\r\n}\r\nreturn true;'"
	 * @generated
	 */
	boolean validateMainPageNotContainingWebPage(DiagnosticChain diagnostics, Map<?, ?> context);


	default String getSrcDir () {
		if (this.getSrcPath() != null && !this.getSrcPath().isBlank()) {
			return this.getSrcPath();
		}
		return Page.super.getSrcDir();
	}

} // MainPage
