/**
 */
package de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.importAdapter;

import de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.Model;
import de.tud.et.ifa.agtele.emf.importing.IModelImporter;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;



/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Import Adapter</b></em>'.>
 * @implements IModelImporter 
 * <!-- end-user-doc --
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.importAdapter.ImportAdapter#getConnector <em>Connector</em>}</li>
 *   <li>{@link de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.importAdapter.ImportAdapter#getModel <em>Model</em>}</li>
 * </ul>
 *
 * @see de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.importAdapter.ImportAdapterPackage#getImportAdapter()
 * @model
 * @generated
 */
public interface ImportAdapter extends EObject, IModelImporter {
	/**
	 * Returns the value of the '<em><b>Connector</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Connector</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Connector</em>' reference.
	 * @see #setConnector(Connector)
	 * @see de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.importAdapter.ImportAdapterPackage#getImportAdapter_Connector()
	 * @model required="true"
	 * @generated
	 */
	Connector getConnector();

	/**
	 * Sets the value of the '{@link de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.importAdapter.ImportAdapter#getConnector <em>Connector</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Connector</em>' reference.
	 * @see #getConnector()
	 * @generated
	 */
	void setConnector(Connector value);
	
	/**
	 * Returns the value of the '<em><b>Model</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.Model#getImportAdapter <em>Import Adapter</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Model</em>' container reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Model</em>' container reference.
	 * @see #setModel(Model)
	 * @see de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.importAdapter.ImportAdapterPackage#getImportAdapter_Model()
	 * @see de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.Model#getImportAdapter
	 * @model opposite="importAdapter" required="true" transient="false"
	 * @generated
	 */
	Model getModel();

	/**
	 * Sets the value of the '{@link de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.importAdapter.ImportAdapter#getModel <em>Model</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Model</em>' container reference.
	 * @see #getModel()
	 * @generated
	 */
	void setModel(Model value);

	void importModel();	

	static void addToResourceSet(ResourceSet set, Resource res) {
		synchronized (ImportAdapter.class) {
			set.getResources().add(res);
		}
	}
} // ImportAdapter
