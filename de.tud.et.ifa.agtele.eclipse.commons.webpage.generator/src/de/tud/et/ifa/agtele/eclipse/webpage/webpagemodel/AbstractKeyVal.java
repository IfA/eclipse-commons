/**
 */
package de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel;

import java.util.Map;
import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Abstract Key Val</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.AbstractKeyVal#getValue <em>Value</em>}</li>
 * </ul>
 *
 * @see de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.WebPageModelPackage#getAbstractKeyVal()
 * @model abstract="true"
 * @generated
 */
public interface AbstractKeyVal extends Base {
	/**
	 * Returns the value of the '<em><b>Value</b></em>' containment reference list.
	 * The list contents are of type {@link de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.Value}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Value</em>' containment reference list.
	 * @see de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.WebPageModelPackage#getAbstractKeyVal_Value()
	 * @model containment="true"
	 * @generated
	 */
	EList<Value> getValue();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model annotation="http://www.eclipse.org/emf/2002/GenModel body='&lt;%org.eclipse.emf.ecore.EStructuralFeature%&gt; feature = this.eContainingFeature();\r\n&lt;%org.eclipse.emf.ecore.EObject%&gt; container = this.eContainer();\r\nboolean result = true;\r\nif (feature.isMany()) {\r\n\t&lt;%org.eclipse.emf.common.util.EList%&gt;&lt;? extends &lt;%de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.AbstractKeyVal%&gt;&gt; value = (EList&lt;? extends AbstractKeyVal&gt;) container.eGet(feature);\r\n\tint index = value.indexOf(this);\r\n\r\n\tfor (int i = 0; i &lt; index; i += 1) {\r\n\t\tAbstractKeyVal val = value.get(i);\r\n\t\tif (val.getName() != null &amp;&amp; val.getName().equals(this.getName())) {\r\n\t\t\tif (diagnostics != null) {\r\n\t\t\t\tdiagnostics\r\n\t\t\t\t\t\t.add(new &lt;%org.eclipse.emf.common.util.BasicDiagnostic%&gt;(&lt;%org.eclipse.emf.common.util.Diagnostic%&gt;.ERROR, &lt;%de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.util.WebPageModelValidator%&gt;.DIAGNOSTIC_SOURCE,\r\n\t\t\t\t\t\t\t\tWebPageModelValidator.ABSTRACT_KEY_VAL__VALIDATE_NAME, \"Duplicate name \'\"\r\n\t\t\t\t\t\t\t\t\t\t+ this.getName() + \"\' in feature \'\" + feature.getName() + \"\'\",\r\n\t\t\t\t\t\t\t\tnew Object[] { this }));\r\n\t\t\t}\r\n\t\t\tresult = false;\r\n\t\t}\r\n\t}\r\n\r\n}\r\n\r\nif (this.getName() == null || this.getName().isBlank()) {\r\n\tif (diagnostics != null) {\r\n\t\tdiagnostics.add(new BasicDiagnostic(Diagnostic.ERROR, WebPageModelValidator.DIAGNOSTIC_SOURCE,\r\n\t\t\t\tWebPageModelValidator.ABSTRACT_KEY_VAL__VALIDATE_NAME, \"name must be set\",\r\n\t\t\t\tnew Object[] { this }));\r\n\t}\r\n\tresult = false;\r\n}\r\nreturn result;'"
	 * @generated
	 */
	boolean validateName(DiagnosticChain diagnostics, Map<?, ?> context);

} // AbstractKeyVal
