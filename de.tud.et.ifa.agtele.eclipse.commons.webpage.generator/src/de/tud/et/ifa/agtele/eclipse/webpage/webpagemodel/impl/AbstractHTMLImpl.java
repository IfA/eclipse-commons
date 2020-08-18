/**
 */
package de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.impl;

import de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.AbstractHTML;
import de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.HtmlInclude;
import de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.Value;
import de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.WebPageModelPackage;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EDataTypeUniqueEList;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Abstract HTML</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.impl.AbstractHTMLImpl#getFooter <em>Footer</em>}</li>
 *   <li>{@link de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.impl.AbstractHTMLImpl#getHeader <em>Header</em>}</li>
 *   <li>{@link de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.impl.AbstractHTMLImpl#getSrcPathFragment <em>Src Path Fragment</em>}</li>
 *   <li>{@link de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.impl.AbstractHTMLImpl#getScripts <em>Scripts</em>}</li>
 *   <li>{@link de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.impl.AbstractHTMLImpl#getStyles <em>Styles</em>}</li>
 *   <li>{@link de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.impl.AbstractHTMLImpl#getTitle <em>Title</em>}</li>
 *   <li>{@link de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.impl.AbstractHTMLImpl#getStaticResources <em>Static Resources</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class AbstractHTMLImpl extends BaseImpl implements AbstractHTML {
	/**
	 * The cached value of the '{@link #getFooter() <em>Footer</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFooter()
	 * @generated
	 * @ordered
	 */
	protected Value footer;

	/**
	 * The cached value of the '{@link #getHeader() <em>Header</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getHeader()
	 * @generated
	 * @ordered
	 */
	protected Value header;

	/**
	 * The default value of the '{@link #getSrcPathFragment() <em>Src Path Fragment</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSrcPathFragment()
	 * @generated
	 * @ordered
	 */
	protected static final String SRC_PATH_FRAGMENT_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getSrcPathFragment() <em>Src Path Fragment</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSrcPathFragment()
	 * @generated
	 * @ordered
	 */
    protected String srcPathFragment = SRC_PATH_FRAGMENT_EDEFAULT;
	/**
	 * The cached value of the '{@link #getScripts() <em>Scripts</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getScripts()
	 * @generated
	 * @ordered
	 */
	protected EList<HtmlInclude> scripts;

	/**
	 * The cached value of the '{@link #getStyles() <em>Styles</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStyles()
	 * @generated
	 * @ordered
	 */
	protected EList<HtmlInclude> styles;

	/**
	 * The default value of the '{@link #getTitle() <em>Title</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTitle()
	 * @generated
	 * @ordered
	 */
	protected static final String TITLE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getTitle() <em>Title</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTitle()
	 * @generated
	 * @ordered
	 */
    protected String title = TITLE_EDEFAULT;
	/**
	 * The cached value of the '{@link #getStaticResources() <em>Static Resources</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStaticResources()
	 * @generated
	 * @ordered
	 */
	protected EList<String> staticResources;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AbstractHTMLImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return WebPageModelPackage.Literals.ABSTRACT_HTML;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Value getFooter() {
	
		return footer;
	}
/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetFooter(Value newFooter, NotificationChain msgs) {
		Value oldFooter = footer;
		footer = newFooter;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, WebPageModelPackage.ABSTRACT_HTML__FOOTER, oldFooter, newFooter);
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
	public void setFooter(Value newFooter) {
	
		if (newFooter != footer) {
			NotificationChain msgs = null;
			if (footer != null)
				msgs = ((InternalEObject)footer).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - WebPageModelPackage.ABSTRACT_HTML__FOOTER, null, msgs);
			if (newFooter != null)
				msgs = ((InternalEObject)newFooter).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - WebPageModelPackage.ABSTRACT_HTML__FOOTER, null, msgs);
			msgs = basicSetFooter(newFooter, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, WebPageModelPackage.ABSTRACT_HTML__FOOTER, newFooter, newFooter));
	
	}
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Value getHeader() {
	
		return header;
	}
/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetHeader(Value newHeader, NotificationChain msgs) {
		Value oldHeader = header;
		header = newHeader;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, WebPageModelPackage.ABSTRACT_HTML__HEADER, oldHeader, newHeader);
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
	public void setHeader(Value newHeader) {
	
		if (newHeader != header) {
			NotificationChain msgs = null;
			if (header != null)
				msgs = ((InternalEObject)header).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - WebPageModelPackage.ABSTRACT_HTML__HEADER, null, msgs);
			if (newHeader != null)
				msgs = ((InternalEObject)newHeader).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - WebPageModelPackage.ABSTRACT_HTML__HEADER, null, msgs);
			msgs = basicSetHeader(newHeader, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, WebPageModelPackage.ABSTRACT_HTML__HEADER, newHeader, newHeader));
	
	}
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getSrcPathFragment() {
	
		return srcPathFragment;
	}
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setSrcPathFragment(String newSrcPathFragment) {
	
		String oldSrcPathFragment = srcPathFragment;
		srcPathFragment = newSrcPathFragment;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, WebPageModelPackage.ABSTRACT_HTML__SRC_PATH_FRAGMENT, oldSrcPathFragment, srcPathFragment));
	
	}
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<HtmlInclude> getScripts() {
	
		if (scripts == null) {
			scripts = new EObjectResolvingEList<HtmlInclude>(HtmlInclude.class, this, WebPageModelPackage.ABSTRACT_HTML__SCRIPTS);
		}
		return scripts;
	}
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<HtmlInclude> getStyles() {
	
		if (styles == null) {
			styles = new EObjectResolvingEList<HtmlInclude>(HtmlInclude.class, this, WebPageModelPackage.ABSTRACT_HTML__STYLES);
		}
		return styles;
	}
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getTitle() {
	
		return title;
	}
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setTitle(String newTitle) {
	
		String oldTitle = title;
		title = newTitle;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, WebPageModelPackage.ABSTRACT_HTML__TITLE, oldTitle, title));
	
	}
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<String> getStaticResources() {
	
		if (staticResources == null) {
			staticResources = new EDataTypeUniqueEList<String>(String.class, this, WebPageModelPackage.ABSTRACT_HTML__STATIC_RESOURCES);
		}
		return staticResources;
	}
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case WebPageModelPackage.ABSTRACT_HTML__FOOTER:
				return basicSetFooter(null, msgs);
			case WebPageModelPackage.ABSTRACT_HTML__HEADER:
				return basicSetHeader(null, msgs);
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
			case WebPageModelPackage.ABSTRACT_HTML__FOOTER:
				return getFooter();
			case WebPageModelPackage.ABSTRACT_HTML__HEADER:
				return getHeader();
			case WebPageModelPackage.ABSTRACT_HTML__SRC_PATH_FRAGMENT:
				return getSrcPathFragment();
			case WebPageModelPackage.ABSTRACT_HTML__SCRIPTS:
				return getScripts();
			case WebPageModelPackage.ABSTRACT_HTML__STYLES:
				return getStyles();
			case WebPageModelPackage.ABSTRACT_HTML__TITLE:
				return getTitle();
			case WebPageModelPackage.ABSTRACT_HTML__STATIC_RESOURCES:
				return getStaticResources();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case WebPageModelPackage.ABSTRACT_HTML__FOOTER:
				setFooter((Value)newValue);
				return;
			case WebPageModelPackage.ABSTRACT_HTML__HEADER:
				setHeader((Value)newValue);
				return;
			case WebPageModelPackage.ABSTRACT_HTML__SRC_PATH_FRAGMENT:
				setSrcPathFragment((String)newValue);
				return;
			case WebPageModelPackage.ABSTRACT_HTML__SCRIPTS:
				getScripts().clear();
				getScripts().addAll((Collection<? extends HtmlInclude>)newValue);
				return;
			case WebPageModelPackage.ABSTRACT_HTML__STYLES:
				getStyles().clear();
				getStyles().addAll((Collection<? extends HtmlInclude>)newValue);
				return;
			case WebPageModelPackage.ABSTRACT_HTML__TITLE:
				setTitle((String)newValue);
				return;
			case WebPageModelPackage.ABSTRACT_HTML__STATIC_RESOURCES:
				getStaticResources().clear();
				getStaticResources().addAll((Collection<? extends String>)newValue);
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
			case WebPageModelPackage.ABSTRACT_HTML__FOOTER:
				setFooter((Value)null);
				return;
			case WebPageModelPackage.ABSTRACT_HTML__HEADER:
				setHeader((Value)null);
				return;
			case WebPageModelPackage.ABSTRACT_HTML__SRC_PATH_FRAGMENT:
				setSrcPathFragment(SRC_PATH_FRAGMENT_EDEFAULT);
				return;
			case WebPageModelPackage.ABSTRACT_HTML__SCRIPTS:
				getScripts().clear();
				return;
			case WebPageModelPackage.ABSTRACT_HTML__STYLES:
				getStyles().clear();
				return;
			case WebPageModelPackage.ABSTRACT_HTML__TITLE:
				setTitle(TITLE_EDEFAULT);
				return;
			case WebPageModelPackage.ABSTRACT_HTML__STATIC_RESOURCES:
				getStaticResources().clear();
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
			case WebPageModelPackage.ABSTRACT_HTML__FOOTER:
				return footer != null;
			case WebPageModelPackage.ABSTRACT_HTML__HEADER:
				return header != null;
			case WebPageModelPackage.ABSTRACT_HTML__SRC_PATH_FRAGMENT:
				return SRC_PATH_FRAGMENT_EDEFAULT == null ? srcPathFragment != null : !SRC_PATH_FRAGMENT_EDEFAULT.equals(srcPathFragment);
			case WebPageModelPackage.ABSTRACT_HTML__SCRIPTS:
				return scripts != null && !scripts.isEmpty();
			case WebPageModelPackage.ABSTRACT_HTML__STYLES:
				return styles != null && !styles.isEmpty();
			case WebPageModelPackage.ABSTRACT_HTML__TITLE:
				return TITLE_EDEFAULT == null ? title != null : !TITLE_EDEFAULT.equals(title);
			case WebPageModelPackage.ABSTRACT_HTML__STATIC_RESOURCES:
				return staticResources != null && !staticResources.isEmpty();
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
		result.append(" (srcPathFragment: ");
		result.append(srcPathFragment);
		result.append(", title: ");
		result.append(title);
		result.append(", staticResources: ");
		result.append(staticResources);
		result.append(')');
		return result.toString();
	}

} //AbstractHTMLImpl
