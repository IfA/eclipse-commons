/**
 */
package de.tud.et.ifa.agtele.eclipse.webpage.impl;

import de.tud.et.ifa.agtele.eclipse.webpage.MainPage;
import de.tud.et.ifa.agtele.eclipse.webpage.Page;
import de.tud.et.ifa.agtele.eclipse.webpage.WebpagePackage;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Main Page</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link de.tud.et.ifa.agtele.eclipse.webpage.impl.MainPageImpl#getMainPages <em>Main Pages</em>}</li>
 *   <li>{@link de.tud.et.ifa.agtele.eclipse.webpage.impl.MainPageImpl#getAdditionalPages <em>Additional Pages</em>}</li>
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
		return WebpagePackage.Literals.MAIN_PAGE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<MainPage> getMainPages() {	
	
		if (mainPages == null) {
			mainPages = new EObjectContainmentEList<MainPage>(MainPage.class, this, WebpagePackage.MAIN_PAGE__MAIN_PAGES);
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
			additionalPages = new EObjectContainmentEList<Page>(Page.class, this, WebpagePackage.MAIN_PAGE__ADDITIONAL_PAGES);
		}
		return additionalPages;
	}
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case WebpagePackage.MAIN_PAGE__MAIN_PAGES:
				return ((InternalEList<?>)getMainPages()).basicRemove(otherEnd, msgs);
			case WebpagePackage.MAIN_PAGE__ADDITIONAL_PAGES:
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
			case WebpagePackage.MAIN_PAGE__MAIN_PAGES:
				return getMainPages();
			case WebpagePackage.MAIN_PAGE__ADDITIONAL_PAGES:
				return getAdditionalPages();
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
			case WebpagePackage.MAIN_PAGE__MAIN_PAGES:
				getMainPages().clear();
				getMainPages().addAll((Collection<? extends MainPage>)newValue);
				return;
			case WebpagePackage.MAIN_PAGE__ADDITIONAL_PAGES:
				getAdditionalPages().clear();
				getAdditionalPages().addAll((Collection<? extends Page>)newValue);
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
			case WebpagePackage.MAIN_PAGE__MAIN_PAGES:
				getMainPages().clear();
				return;
			case WebpagePackage.MAIN_PAGE__ADDITIONAL_PAGES:
				getAdditionalPages().clear();
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
			case WebpagePackage.MAIN_PAGE__MAIN_PAGES:
				return mainPages != null && !mainPages.isEmpty();
			case WebpagePackage.MAIN_PAGE__ADDITIONAL_PAGES:
				return additionalPages != null && !additionalPages.isEmpty();
		}
		return super.eIsSet(featureID);
	}
} //MainPageImpl
