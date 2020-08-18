/**
 */
package de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.impl;

import de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.Page;
import de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.WebPageModelPackage;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Page</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.impl.PageImpl#getNavIcon <em>Nav Icon</em>}</li>
 * </ul>
 *
 * @generated
 */
public class PageImpl extends SubPageImpl implements Page {
	/**
	 * The default value of the '{@link #getNavIcon() <em>Nav Icon</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNavIcon()
	 * @generated
	 * @ordered
	 */
	protected static final String NAV_ICON_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getNavIcon() <em>Nav Icon</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNavIcon()
	 * @generated
	 * @ordered
	 */
    protected String navIcon = NAV_ICON_EDEFAULT;
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PageImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return WebPageModelPackage.Literals.PAGE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getNavIcon() {
	
		return navIcon;
	}
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setNavIcon(String newNavIcon) {
	
		String oldNavIcon = navIcon;
		navIcon = newNavIcon;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, WebPageModelPackage.PAGE__NAV_ICON, oldNavIcon, navIcon));
	
	}
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case WebPageModelPackage.PAGE__NAV_ICON:
				return getNavIcon();
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
			case WebPageModelPackage.PAGE__NAV_ICON:
				setNavIcon((String)newValue);
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
			case WebPageModelPackage.PAGE__NAV_ICON:
				setNavIcon(NAV_ICON_EDEFAULT);
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
			case WebPageModelPackage.PAGE__NAV_ICON:
				return NAV_ICON_EDEFAULT == null ? navIcon != null : !NAV_ICON_EDEFAULT.equals(navIcon);
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
		result.append(" (navIcon: ");
		result.append(navIcon);
		result.append(')');
		return result.toString();
	}

} //PageImpl
