/**
 */
package de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Updateable Element</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.UpdateableElement#isUpdating <em>Updating</em>}</li>
 * </ul>
 *
 * @see de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.ModelStoragePackage#getUpdateableElement()
 * @model abstract="true"
 * @generated
 */
public interface UpdateableElement extends EObject {
	/**
	 * Returns the value of the '<em><b>Updating</b></em>' attribute.
	 * The default value is <code>"false"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Updating</em>' attribute.
	 * @see #setUpdating(boolean)
	 * @see de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.ModelStoragePackage#getUpdateableElement_Updating()
	 * @model default="false" transient="true"
	 * @generated
	 */
	boolean isUpdating();

	/**
	 * Sets the value of the '{@link de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.UpdateableElement#isUpdating <em>Updating</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Updating</em>' attribute.
	 * @see #isUpdating()
	 * @generated
	 */
	void setUpdating(boolean value);

} // UpdateableElement
