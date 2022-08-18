/**
 */
package de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.impl;

import de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.Announcement;
import de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.AnnouncementLocationEnum;
import de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.AnnouncementTypeEnum;
import de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.Value;
import de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.WebPageModelPackage;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Announcement</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.impl.AnnouncementImpl#isPropagate <em>Propagate</em>}</li>
 *   <li>{@link de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.impl.AnnouncementImpl#getType <em>Type</em>}</li>
 *   <li>{@link de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.impl.AnnouncementImpl#getLocation <em>Location</em>}</li>
 *   <li>{@link de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.impl.AnnouncementImpl#getContent <em>Content</em>}</li>
 *   <li>{@link de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.impl.AnnouncementImpl#isClosable <em>Closable</em>}</li>
 *   <li>{@link de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.impl.AnnouncementImpl#isDisable <em>Disable</em>}</li>
 * </ul>
 *
 * @generated
 */
public class AnnouncementImpl extends BaseImpl implements Announcement {
	/**
	 * The default value of the '{@link #isPropagate() <em>Propagate</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isPropagate()
	 * @generated
	 * @ordered
	 */
	protected static final boolean PROPAGATE_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isPropagate() <em>Propagate</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isPropagate()
	 * @generated
	 * @ordered
	 */
    protected boolean propagate = PROPAGATE_EDEFAULT;
	/**
	 * The default value of the '{@link #getType() <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected static final AnnouncementTypeEnum TYPE_EDEFAULT = AnnouncementTypeEnum.PRIMARY;

	/**
	 * The cached value of the '{@link #getType() <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
    protected AnnouncementTypeEnum type = TYPE_EDEFAULT;
	/**
	 * The default value of the '{@link #getLocation() <em>Location</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLocation()
	 * @generated
	 * @ordered
	 */
	protected static final AnnouncementLocationEnum LOCATION_EDEFAULT = AnnouncementLocationEnum.ABOVE_HEADING;

	/**
	 * The cached value of the '{@link #getLocation() <em>Location</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLocation()
	 * @generated
	 * @ordered
	 */
    protected AnnouncementLocationEnum location = LOCATION_EDEFAULT;
	/**
	 * The cached value of the '{@link #getContent() <em>Content</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getContent()
	 * @generated
	 * @ordered
	 */
	protected Value content;

	/**
	 * The default value of the '{@link #isClosable() <em>Closable</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isClosable()
	 * @generated
	 * @ordered
	 */
	protected static final boolean CLOSABLE_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isClosable() <em>Closable</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isClosable()
	 * @generated
	 * @ordered
	 */
    protected boolean closable = CLOSABLE_EDEFAULT;
	/**
	 * The default value of the '{@link #isDisable() <em>Disable</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isDisable()
	 * @generated
	 * @ordered
	 */
	protected static final boolean DISABLE_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isDisable() <em>Disable</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isDisable()
	 * @generated
	 * @ordered
	 */
    protected boolean disable = DISABLE_EDEFAULT;
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AnnouncementImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return WebPageModelPackage.Literals.ANNOUNCEMENT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isPropagate() {	
	
		return propagate;
	}
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setPropagate(boolean newPropagate) {
	
		boolean oldPropagate = propagate;
		propagate = newPropagate;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, WebPageModelPackage.ANNOUNCEMENT__PROPAGATE, oldPropagate, propagate));
	
	}
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public AnnouncementTypeEnum getType() {	
	
		return type;
	}
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setType(AnnouncementTypeEnum newType) {
	
		AnnouncementTypeEnum oldType = type;
		type = newType == null ? TYPE_EDEFAULT : newType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, WebPageModelPackage.ANNOUNCEMENT__TYPE, oldType, type));
	
	}
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public AnnouncementLocationEnum getLocation() {	
	
		return location;
	}
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setLocation(AnnouncementLocationEnum newLocation) {
	
		AnnouncementLocationEnum oldLocation = location;
		location = newLocation == null ? LOCATION_EDEFAULT : newLocation;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, WebPageModelPackage.ANNOUNCEMENT__LOCATION, oldLocation, location));
	
	}
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Value getContent() {	
	
		return content;
	}
/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetContent(Value newContent, NotificationChain msgs) {
		Value oldContent = content;
		content = newContent;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, WebPageModelPackage.ANNOUNCEMENT__CONTENT, oldContent, newContent);
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
	public void setContent(Value newContent) {
	
		if (newContent != content) {
			NotificationChain msgs = null;
			if (content != null)
				msgs = ((InternalEObject)content).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - WebPageModelPackage.ANNOUNCEMENT__CONTENT, null, msgs);
			if (newContent != null)
				msgs = ((InternalEObject)newContent).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - WebPageModelPackage.ANNOUNCEMENT__CONTENT, null, msgs);
			msgs = basicSetContent(newContent, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, WebPageModelPackage.ANNOUNCEMENT__CONTENT, newContent, newContent));
	
	}
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isClosable() {	
	
		return closable;
	}
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setClosable(boolean newClosable) {
	
		boolean oldClosable = closable;
		closable = newClosable;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, WebPageModelPackage.ANNOUNCEMENT__CLOSABLE, oldClosable, closable));
	
	}
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isDisable() {	
	
		return disable;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setDisable(boolean newDisable) {
	
		boolean oldDisable = disable;
		disable = newDisable;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, WebPageModelPackage.ANNOUNCEMENT__DISABLE, oldDisable, disable));
	
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case WebPageModelPackage.ANNOUNCEMENT__CONTENT:
				return basicSetContent(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case WebPageModelPackage.ANNOUNCEMENT__PROPAGATE:
				return isPropagate();
			case WebPageModelPackage.ANNOUNCEMENT__TYPE:
				return getType();
			case WebPageModelPackage.ANNOUNCEMENT__LOCATION:
				return getLocation();
			case WebPageModelPackage.ANNOUNCEMENT__CONTENT:
				return getContent();
			case WebPageModelPackage.ANNOUNCEMENT__CLOSABLE:
				return isClosable();
			case WebPageModelPackage.ANNOUNCEMENT__DISABLE:
				return isDisable();
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
			case WebPageModelPackage.ANNOUNCEMENT__PROPAGATE:
				setPropagate((Boolean)newValue);
				return;
			case WebPageModelPackage.ANNOUNCEMENT__TYPE:
				setType((AnnouncementTypeEnum)newValue);
				return;
			case WebPageModelPackage.ANNOUNCEMENT__LOCATION:
				setLocation((AnnouncementLocationEnum)newValue);
				return;
			case WebPageModelPackage.ANNOUNCEMENT__CONTENT:
				setContent((Value)newValue);
				return;
			case WebPageModelPackage.ANNOUNCEMENT__CLOSABLE:
				setClosable((Boolean)newValue);
				return;
			case WebPageModelPackage.ANNOUNCEMENT__DISABLE:
				setDisable((Boolean)newValue);
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
			case WebPageModelPackage.ANNOUNCEMENT__PROPAGATE:
				setPropagate(PROPAGATE_EDEFAULT);
				return;
			case WebPageModelPackage.ANNOUNCEMENT__TYPE:
				setType(TYPE_EDEFAULT);
				return;
			case WebPageModelPackage.ANNOUNCEMENT__LOCATION:
				setLocation(LOCATION_EDEFAULT);
				return;
			case WebPageModelPackage.ANNOUNCEMENT__CONTENT:
				setContent((Value)null);
				return;
			case WebPageModelPackage.ANNOUNCEMENT__CLOSABLE:
				setClosable(CLOSABLE_EDEFAULT);
				return;
			case WebPageModelPackage.ANNOUNCEMENT__DISABLE:
				setDisable(DISABLE_EDEFAULT);
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
			case WebPageModelPackage.ANNOUNCEMENT__PROPAGATE:
				return propagate != PROPAGATE_EDEFAULT;
			case WebPageModelPackage.ANNOUNCEMENT__TYPE:
				return type != TYPE_EDEFAULT;
			case WebPageModelPackage.ANNOUNCEMENT__LOCATION:
				return location != LOCATION_EDEFAULT;
			case WebPageModelPackage.ANNOUNCEMENT__CONTENT:
				return content != null;
			case WebPageModelPackage.ANNOUNCEMENT__CLOSABLE:
				return closable != CLOSABLE_EDEFAULT;
			case WebPageModelPackage.ANNOUNCEMENT__DISABLE:
				return disable != DISABLE_EDEFAULT;
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
		result.append(" (propagate: ");
		result.append(propagate);
		result.append(", type: ");
		result.append(type);
		result.append(", location: ");
		result.append(location);
		result.append(", closable: ");
		result.append(closable);
		result.append(", disable: ");
		result.append(disable);
		result.append(')');
		return result.toString();
	}

} //AnnouncementImpl
