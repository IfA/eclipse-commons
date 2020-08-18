/**
 */
package de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.impl;

import de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.MainPage;
import de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.Page;
import de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.WebPageModelPackage;

import de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.util.WebPageModelValidator;

import java.lang.reflect.InvocationTargetException;

import java.util.Collection;
import java.util.Map;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectValidator;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Main Page</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.impl.MainPageImpl#getMainPages <em>Main Pages</em>}</li>
 *   <li>{@link de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.impl.MainPageImpl#getAdditionalPages <em>Additional Pages</em>}</li>
 *   <li>{@link de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.impl.MainPageImpl#getSrcPath <em>Src Path</em>}</li>
 *   <li>{@link de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.impl.MainPageImpl#isSuppressMainMenu <em>Suppress Main Menu</em>}</li>
 * </ul>
 *
 * @generated
 */
public class MainPageImpl extends PageImpl implements MainPage {
	/**
	 * The cached value of the '{@link #getMainPages() <em>Main Pages</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMainPages()
	 * @generated
	 * @ordered
	 */
	protected EList<MainPage> mainPages;

	/**
	 * The cached value of the '{@link #getAdditionalPages() <em>Additional Pages</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAdditionalPages()
	 * @generated
	 * @ordered
	 */
	protected EList<Page> additionalPages;

	/**
	 * The default value of the '{@link #getSrcPath() <em>Src Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSrcPath()
	 * @generated
	 * @ordered
	 */
	protected static final String SRC_PATH_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getSrcPath() <em>Src Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSrcPath()
	 * @generated
	 * @ordered
	 */
    protected String srcPath = SRC_PATH_EDEFAULT;
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
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected MainPageImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return WebPageModelPackage.Literals.MAIN_PAGE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<MainPage> getMainPages() {
	
		if (mainPages == null) {
			mainPages = new EObjectContainmentEList<MainPage>(MainPage.class, this, WebPageModelPackage.MAIN_PAGE__MAIN_PAGES);
		}
		return mainPages;
	}
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Page> getAdditionalPages() {
	
		if (additionalPages == null) {
			additionalPages = new EObjectContainmentEList<Page>(Page.class, this, WebPageModelPackage.MAIN_PAGE__ADDITIONAL_PAGES);
		}
		return additionalPages;
	}
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getSrcPath() {
	
		return srcPath;
	}
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setSrcPath(String newSrcPath) {
	
		String oldSrcPath = srcPath;
		srcPath = newSrcPath;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, WebPageModelPackage.MAIN_PAGE__SRC_PATH, oldSrcPath, srcPath));
	
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
			eNotify(new ENotificationImpl(this, Notification.SET, WebPageModelPackage.MAIN_PAGE__SUPPRESS_MAIN_MENU, oldSuppressMainMenu, suppressMainMenu));
	
	}
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean validateMainPageNotContainingWebPage(DiagnosticChain diagnostics, Map<?, ?> context) {
		// TODO: implement this method
		// -> specify the condition that violates the invariant
		// -> verify the details of the diagnostic, including severity and message
		// Ensure that you remove @generated or mark it @generated NOT
		if (false) {
			if (diagnostics != null) {
				diagnostics.add
					(new BasicDiagnostic
						(Diagnostic.ERROR,
						 WebPageModelValidator.DIAGNOSTIC_SOURCE,
						 WebPageModelValidator.MAIN_PAGE__VALIDATE_MAIN_PAGE_NOT_CONTAINING_WEB_PAGE,
						 EcorePlugin.INSTANCE.getString("_UI_GenericInvariant_diagnostic", new Object[] { "validateMainPageNotContainingWebPage", EObjectValidator.getObjectLabel(this, (Map<Object, Object>) context) }),
						 new Object [] { this }));
			}
			return false;
		}
		return true;	
	}
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case WebPageModelPackage.MAIN_PAGE__MAIN_PAGES:
				return ((InternalEList<?>)getMainPages()).basicRemove(otherEnd, msgs);
			case WebPageModelPackage.MAIN_PAGE__ADDITIONAL_PAGES:
				return ((InternalEList<?>)getAdditionalPages()).basicRemove(otherEnd, msgs);
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
			case WebPageModelPackage.MAIN_PAGE__MAIN_PAGES:
				return getMainPages();
			case WebPageModelPackage.MAIN_PAGE__ADDITIONAL_PAGES:
				return getAdditionalPages();
			case WebPageModelPackage.MAIN_PAGE__SRC_PATH:
				return getSrcPath();
			case WebPageModelPackage.MAIN_PAGE__SUPPRESS_MAIN_MENU:
				return isSuppressMainMenu();
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
			case WebPageModelPackage.MAIN_PAGE__MAIN_PAGES:
				getMainPages().clear();
				getMainPages().addAll((Collection<? extends MainPage>)newValue);
				return;
			case WebPageModelPackage.MAIN_PAGE__ADDITIONAL_PAGES:
				getAdditionalPages().clear();
				getAdditionalPages().addAll((Collection<? extends Page>)newValue);
				return;
			case WebPageModelPackage.MAIN_PAGE__SRC_PATH:
				setSrcPath((String)newValue);
				return;
			case WebPageModelPackage.MAIN_PAGE__SUPPRESS_MAIN_MENU:
				setSuppressMainMenu((Boolean)newValue);
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
			case WebPageModelPackage.MAIN_PAGE__MAIN_PAGES:
				getMainPages().clear();
				return;
			case WebPageModelPackage.MAIN_PAGE__ADDITIONAL_PAGES:
				getAdditionalPages().clear();
				return;
			case WebPageModelPackage.MAIN_PAGE__SRC_PATH:
				setSrcPath(SRC_PATH_EDEFAULT);
				return;
			case WebPageModelPackage.MAIN_PAGE__SUPPRESS_MAIN_MENU:
				setSuppressMainMenu(SUPPRESS_MAIN_MENU_EDEFAULT);
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
			case WebPageModelPackage.MAIN_PAGE__MAIN_PAGES:
				return mainPages != null && !mainPages.isEmpty();
			case WebPageModelPackage.MAIN_PAGE__ADDITIONAL_PAGES:
				return additionalPages != null && !additionalPages.isEmpty();
			case WebPageModelPackage.MAIN_PAGE__SRC_PATH:
				return SRC_PATH_EDEFAULT == null ? srcPath != null : !SRC_PATH_EDEFAULT.equals(srcPath);
			case WebPageModelPackage.MAIN_PAGE__SUPPRESS_MAIN_MENU:
				return suppressMainMenu != SUPPRESS_MAIN_MENU_EDEFAULT;
		}
		return super.eIsSet(featureID);
	}
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eInvoke(int operationID, EList<?> arguments) throws InvocationTargetException {
		switch (operationID) {
			case WebPageModelPackage.MAIN_PAGE___VALIDATE_MAIN_PAGE_NOT_CONTAINING_WEB_PAGE__DIAGNOSTICCHAIN_MAP:
				return validateMainPageNotContainingWebPage((DiagnosticChain)arguments.get(0), (Map<?, ?>)arguments.get(1));
		}
		return super.eInvoke(operationID, arguments);
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
		result.append(" (srcPath: ");
		result.append(srcPath);
		result.append(", suppressMainMenu: ");
		result.append(suppressMainMenu);
		result.append(')');
		return result.toString();
	}

} //MainPageImpl
