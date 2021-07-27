/**
 */
package de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.importAdapter;

import org.eclipse.emf.ecore.EObject;
import de.tud.et.ifa.agtele.emf.importing.IModelConnector;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Connector</b></em>'.
 * @implements IModelConnector
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.importAdapter.Connector#getConnectionUri <em>Connection Uri</em>}</li>
 * </ul>
 *
 * @see de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.importAdapter.ImportAdapterPackage#getConnector()
 * @model abstract="true"
 * @generated
 */
public interface Connector extends EObject, IModelConnector {
	/**
	 * Returns the value of the '<em><b>Connection Uri</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Connection Uri</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Connection Uri</em>' attribute.
	 * @see #setConnectionUri(String)
	 * @see de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.importAdapter.ImportAdapterPackage#getConnector_ConnectionUri()
	 * @model required="true"
	 * @generated
	 */
	String getConnectionUri();

	/**
	 * Sets the value of the '{@link de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.importAdapter.Connector#getConnectionUri <em>Connection Uri</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Connection Uri</em>' attribute.
	 * @see #getConnectionUri()
	 * @generated
	 */
	void setConnectionUri(String value);
	
} // Connector
