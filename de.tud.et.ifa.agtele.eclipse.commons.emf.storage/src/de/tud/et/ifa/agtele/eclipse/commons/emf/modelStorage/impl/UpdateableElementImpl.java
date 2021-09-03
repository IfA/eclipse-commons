/**
 */
package de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.impl;

import de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.ModelStoragePackage;
import de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.UpdateableElement;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Updateable Element</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.impl.UpdateableElementImpl#isUpdating <em>Updating</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class UpdateableElementImpl extends MinimalEObjectImpl.Container implements UpdateableElement {
	/**
	 * The default value of the '{@link #isUpdating() <em>Updating</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isUpdating()
	 * @generated
	 * @ordered
	 */
	protected static final boolean UPDATING_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isUpdating() <em>Updating</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isUpdating()
	 * @generated
	 * @ordered
	 */
    protected boolean updating = UPDATING_EDEFAULT;
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected UpdateableElementImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ModelStoragePackage.Literals.UPDATEABLE_ELEMENT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isUpdating() {
	
		return updating;
	}
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT due to synchronized flag
	 */
	@Override
	public synchronized void setUpdating(boolean newUpdating) {
	
		boolean oldUpdating = updating;
		updating = newUpdating;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelStoragePackage.UPDATEABLE_ELEMENT__UPDATING, oldUpdating, updating));
	
	}
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ModelStoragePackage.UPDATEABLE_ELEMENT__UPDATING:
				return isUpdating();
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
			case ModelStoragePackage.UPDATEABLE_ELEMENT__UPDATING:
				setUpdating((Boolean)newValue);
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
			case ModelStoragePackage.UPDATEABLE_ELEMENT__UPDATING:
				setUpdating(UPDATING_EDEFAULT);
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
			case ModelStoragePackage.UPDATEABLE_ELEMENT__UPDATING:
				return updating != UPDATING_EDEFAULT;
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
		result.append(" (updating: ");
		result.append(updating);
		result.append(')');
		return result.toString();
	}

} //UpdateableElementImpl
