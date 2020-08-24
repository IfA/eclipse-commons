/**
 */
package de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel;

import org.eclipse.emf.common.util.URI;

import de.tud.et.ifa.agtele.eclipse.webpage.util.IStringSubstitutor;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>File Value</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.FileValue#getValue <em>Value</em>}</li>
 * </ul>
 *
 * @see de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.WebPageModelPackage#getFileValue()
 * @model
 * @generated
 */
public interface FileValue extends Value {

	/**
	 * Returns the value of the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Value</em>' attribute.
	 * @see #setValue(String)
	 * @see de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.WebPageModelPackage#getFileValue_Value()
	 * @model
	 * @generated
	 */
	String getValue();

	/**
	 * Sets the value of the '{@link de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.FileValue#getValue <em>Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Value</em>' attribute.
	 * @see #getValue()
	 * @generated
	 */
	void setValue(String value);

	default URI getSrcUri(IStringSubstitutor substitutor) {
		AbstractHTML container = null;
		if (this.eContainer() instanceof AbstractHTML) {
			container = (AbstractHTML)this.eContainer();
		} else if (this.eContainer() instanceof Announcement) {
			container = (AbstractHTML) this.eContainer().eContainer();
		}
		String path = this.getValue();
		if (container == null) {
			return URI.createURI(path);
		} else {
			path = this.getValue();
		}
		return container.getSrcPath(substitutor, path);
	}
} // FileValue
