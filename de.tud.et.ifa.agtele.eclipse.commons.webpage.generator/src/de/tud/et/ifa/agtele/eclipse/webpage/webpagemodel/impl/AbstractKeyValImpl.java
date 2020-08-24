/**
 */
package de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.impl;

import de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.AbstractKeyVal;
import de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.Value;
import de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.WebPageModelPackage;

import de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.util.WebPageModelValidator;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Map;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Abstract Key Val</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.impl.AbstractKeyValImpl#getValue <em>Value</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class AbstractKeyValImpl extends BaseImpl implements AbstractKeyVal {
	/**
	 * The cached value of the '{@link #getValue() <em>Value</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getValue()
	 * @generated
	 * @ordered
	 */
	protected EList<Value> value;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AbstractKeyValImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return WebPageModelPackage.Literals.ABSTRACT_KEY_VAL;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Value> getValue() {	
	
		if (value == null) {
			value = new EObjectContainmentEList<Value>(Value.class, this, WebPageModelPackage.ABSTRACT_KEY_VAL__VALUE);
		}
		return value;
	}
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean validateName(final DiagnosticChain diagnostics, final Map<?, ?> context) {
		EStructuralFeature feature = this.eContainingFeature();
		EObject container = this.eContainer();
		boolean result = true;
		if (feature.isMany()) {
			EList<? extends AbstractKeyVal> value = (EList<? extends AbstractKeyVal>) container.eGet(feature);
			int index = value.indexOf(this);
		
			for (int i = 0; i < index; i += 1) {
				AbstractKeyVal val = value.get(i);
				if (val.getName() != null && val.getName().equals(this.getName())) {
					if (diagnostics != null) {
						diagnostics
								.add(new BasicDiagnostic(Diagnostic.ERROR, WebPageModelValidator.DIAGNOSTIC_SOURCE,
										WebPageModelValidator.ABSTRACT_KEY_VAL__VALIDATE_NAME, "Duplicate name '"
												+ this.getName() + "' in feature '" + feature.getName() + "'",
										new Object[] { this }));
					}
					result = false;
				}
			}
		
		}
		
		if (this.getName() == null || this.getName().isBlank()) {
			if (diagnostics != null) {
				diagnostics.add(new BasicDiagnostic(Diagnostic.ERROR, WebPageModelValidator.DIAGNOSTIC_SOURCE,
						WebPageModelValidator.ABSTRACT_KEY_VAL__VALIDATE_NAME, "name must be set",
						new Object[] { this }));
			}
			result = false;
		}
		return result;	
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case WebPageModelPackage.ABSTRACT_KEY_VAL__VALUE:
				return ((InternalEList<?>)getValue()).basicRemove(otherEnd, msgs);
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
			case WebPageModelPackage.ABSTRACT_KEY_VAL__VALUE:
				return getValue();
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
			case WebPageModelPackage.ABSTRACT_KEY_VAL__VALUE:
				getValue().clear();
				getValue().addAll((Collection<? extends Value>)newValue);
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
			case WebPageModelPackage.ABSTRACT_KEY_VAL__VALUE:
				getValue().clear();
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
			case WebPageModelPackage.ABSTRACT_KEY_VAL__VALUE:
				return value != null && !value.isEmpty();
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
			case WebPageModelPackage.ABSTRACT_KEY_VAL___VALIDATE_NAME__DIAGNOSTICCHAIN_MAP:
				return validateName((DiagnosticChain)arguments.get(0), (Map<?, ?>)arguments.get(1));
		}
		return super.eInvoke(operationID, arguments);
	}
	
} //AbstractKeyValImpl
