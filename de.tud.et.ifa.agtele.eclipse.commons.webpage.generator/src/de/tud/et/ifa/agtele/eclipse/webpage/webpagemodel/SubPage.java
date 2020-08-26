/**
 */
package de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel;

import org.eclipse.emf.common.util.EList;

import de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.util.WebPageModelUtils;
import java.util.Map;
import org.eclipse.emf.common.util.DiagnosticChain;

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
		
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model annotation="http://www.eclipse.org/emf/2002/GenModel body='boolean valid = true;\r\nfor (&lt;%de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.SubPage%&gt; page : this.getSubPage()) {\r\n\tif (page instanceof &lt;%de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.Page%&gt;) {\r\n\t\tif (diagnostics != null) {\r\n\t\t\tdiagnostics.add(new &lt;%org.eclipse.emf.common.util.BasicDiagnostic%&gt;(&lt;%org.eclipse.emf.common.util.Diagnostic%&gt;.ERROR, &lt;%de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.util.WebPageModelValidator%&gt;.DIAGNOSTIC_SOURCE,\r\n\t\t\t\t\tWebPageModelValidator.SUB_PAGE__VALIDATE_SUB_PAGE, \"SubPage must only contain SubPages\",\r\n\t\t\t\t\tnew Object[] { this, page }));\r\n\t\t}\r\n\t\tvalid = false;\r\n\t}\r\n}\r\nreturn valid;'"
	 * @generated
	 */
	boolean validateSubPage(DiagnosticChain diagnostics, Map<?, ?> context);

	default String getSrcDir () {
		String parentDir = "",
				srcPathFragment = this.getSrcPathFragment();
		if (this.eContainer() instanceof AbstractHTML) {
			parentDir = ((AbstractHTML) this.eContainer()).getSrcDir();
		}
		if (!this.isNode()) {
			return parentDir;
		}
		if (srcPathFragment != null && !srcPathFragment.isBlank()) {
			if (WebPageModelUtils.isAbsolutePath(srcPathFragment)) {
				return srcPathFragment;
			}
		} else {
			srcPathFragment = WebPageModelUtils.getUrlSafeName(this.getName());
		}
		return (!parentDir.isBlank() ? parentDir + "/" : "") + srcPathFragment;
	}
	
	default boolean isNode () {
		return !this.getSubPage().isEmpty();
	}

} // SubPage
