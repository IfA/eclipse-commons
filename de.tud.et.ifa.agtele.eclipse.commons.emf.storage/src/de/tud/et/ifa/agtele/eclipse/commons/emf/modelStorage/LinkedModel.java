/**
 */
package de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage;

import org.eclipse.emf.ecore.resource.ResourceSet;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Linked Model</b></em>'.
 * <!-- end-user-doc -->
 *
 *
 * @see de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.ModelStoragePackage#getLinkedModel()
 * @model
 * @generated
 */
public interface LinkedModel extends Model {
	public void setResourceSet(ResourceSet set);

	void initialize();
} // LinkedModel
