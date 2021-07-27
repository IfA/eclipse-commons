/**
 */
package de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.importAdapter.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.importAdapter.Connector;
import de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.importAdapter.ImportAdapter;
import de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.importAdapter.ImportAdapterPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Connector</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.importAdapter.impl.ConnectorImpl#getConnectionUri <em>Connection Uri</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class ConnectorImpl extends MinimalEObjectImpl.Container implements Connector {
	/**
	 * The default value of the '{@link #getConnectionUri() <em>Connection Uri</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getConnectionUri()
	 * @generated
	 * @ordered
	 */
	protected static final String CONNECTION_URI_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getConnectionUri() <em>Connection Uri</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getConnectionUri()
	 * @generated
	 * @ordered
	 */
    protected String connectionUri = CONNECTION_URI_EDEFAULT;
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ConnectorImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ImportAdapterPackage.Literals.CONNECTOR;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getConnectionUri() {	
	
		return connectionUri;
	}
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setConnectionUri(String newConnectionUri) {
	
		String oldConnectionUri = connectionUri;
		connectionUri = newConnectionUri;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ImportAdapterPackage.CONNECTOR__CONNECTION_URI, oldConnectionUri, connectionUri));
	
	}
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ImportAdapterPackage.CONNECTOR__CONNECTION_URI:
				return getConnectionUri();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case ImportAdapterPackage.CONNECTOR__CONNECTION_URI:
				setConnectionUri((String)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case ImportAdapterPackage.CONNECTOR__CONNECTION_URI:
				setConnectionUri(CONNECTION_URI_EDEFAULT);
				return;
		}
		super.eUnset(featureID);
	}

 	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case ImportAdapterPackage.CONNECTOR__CONNECTION_URI:
				return CONNECTION_URI_EDEFAULT == null ? connectionUri != null : !CONNECTION_URI_EDEFAULT.equals(connectionUri);
		}
		return super.eIsSet(featureID);
	}
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuilder result = new StringBuilder(super.toString());
		result.append(" (connectionUri: ");
		result.append(connectionUri);
		result.append(')');
		return result.toString();
	}

	protected ImportAdapter adapter = null;
	
	public ImportAdapter getCurrentImportAdapter () {
		return this.adapter;
	}
} //ConnectorImpl
