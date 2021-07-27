/**
 */
package de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Model Adapter</b></em>'.
 * <!-- end-user-doc -->
 *
 *
 * @see de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.ModelStoragePackage#getModelAdapter()
 * @model abstract="true"
 * @generated
 */
public interface ModelAdapter extends Model {

	public void setModel(Model model);
	public Model getModel();
	public boolean isAdaptable(Model model);
} // ModelAdapter
