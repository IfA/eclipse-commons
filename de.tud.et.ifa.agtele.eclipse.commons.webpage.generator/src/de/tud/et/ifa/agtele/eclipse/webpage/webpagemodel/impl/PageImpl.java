/**
 */
package de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.impl;

import de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.Page;
import de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.Value;
import de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.WebPageModelPackage;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Page</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.impl.PageImpl#isSuppressMainMenu <em>Suppress Main Menu</em>}</li>
 *   <li>{@link de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.impl.PageImpl#getNavIcon <em>Nav Icon</em>}</li>
 * </ul>
 *
 * @generated
 */
public class PageImpl extends SubPageImpl implements Page {
	/**
	 * The default value of the '{@link #isSuppressMainMenu() <em>Suppress Main Menu</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSuppressMainMenu()
	 * @generated
	 * @ordered
	 */
	protected static final boolean SUPPRESS_MAIN_MENU_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isSuppressMainMenu() <em>Suppress Main Menu</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSuppressMainMenu()
	 * @generated
	 * @ordered
	 */
    protected boolean suppressMainMenu = SUPPRESS_MAIN_MENU_EDEFAULT;

	/**
	 * The cached value of the '{@link #getNavIcon() <em>Nav Icon</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNavIcon()
	 * @generated
	 * @ordered
	 */
    protected Value navIcon;
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
	public Value getNavIcon() {	
	
		return navIcon;
	}
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetNavIcon(Value newNavIcon, NotificationChain msgs) {
		Value oldNavIcon = navIcon;
		navIcon = newNavIcon;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, WebPageModelPackage.PAGE__NAV_ICON, oldNavIcon, newNavIcon);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setNavIcon(Value newNavIcon) {
	
		if (newNavIcon != navIcon) {
			NotificationChain msgs = null;
			if (navIcon != null)
				msgs = ((InternalEObject)navIcon).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - WebPageModelPackage.PAGE__NAV_ICON, null, msgs);
			if (newNavIcon != null)
				msgs = ((InternalEObject)newNavIcon).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - WebPageModelPackage.PAGE__NAV_ICON, null, msgs);
			msgs = basicSetNavIcon(newNavIcon, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, WebPageModelPackage.PAGE__NAV_ICON, newNavIcon, newNavIcon));
	
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case WebPageModelPackage.PAGE__NAV_ICON:
				return basicSetNavIcon(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isSuppressMainMenu() {	
	
		return suppressMainMenu;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setSuppressMainMenu(boolean newSuppressMainMenu) {
	
		boolean oldSuppressMainMenu = suppressMainMenu;
		suppressMainMenu = newSuppressMainMenu;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, WebPageModelPackage.PAGE__SUPPRESS_MAIN_MENU, oldSuppressMainMenu, suppressMainMenu));
	
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case WebPageModelPackage.PAGE__SUPPRESS_MAIN_MENU:
				return isSuppressMainMenu();
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
			case WebPageModelPackage.PAGE__SUPPRESS_MAIN_MENU:
				setSuppressMainMenu((Boolean)newValue);
				return;
			case WebPageModelPackage.PAGE__NAV_ICON:
				setNavIcon((Value)newValue);
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
			case WebPageModelPackage.PAGE__SUPPRESS_MAIN_MENU:
				setSuppressMainMenu(SUPPRESS_MAIN_MENU_EDEFAULT);
				return;
			case WebPageModelPackage.PAGE__NAV_ICON:
				setNavIcon((Value)null);
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
			case WebPageModelPackage.PAGE__SUPPRESS_MAIN_MENU:
				return suppressMainMenu != SUPPRESS_MAIN_MENU_EDEFAULT;
			case WebPageModelPackage.PAGE__NAV_ICON:
				return navIcon != null;
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
		result.append(" (suppressMainMenu: ");
		result.append(suppressMainMenu);
		result.append(')');
		return result.toString();
	}

} //PageImpl
