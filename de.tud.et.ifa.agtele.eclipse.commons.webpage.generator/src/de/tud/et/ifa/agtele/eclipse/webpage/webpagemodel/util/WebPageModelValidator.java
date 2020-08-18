/**
 */
package de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.util;

import de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.*;

import java.util.Map;

import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.common.util.ResourceLocator;

import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.util.EObjectValidator;

/**
 * <!-- begin-user-doc -->
 * The <b>Validator</b> for the model.
 * <!-- end-user-doc -->
 * @see de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.WebPageModelPackage
 * @generated
 */
public class WebPageModelValidator extends EObjectValidator {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final WebPageModelValidator INSTANCE = new WebPageModelValidator();

	/**
	 * A constant for the {@link org.eclipse.emf.common.util.Diagnostic#getSource() source} of diagnostic {@link org.eclipse.emf.common.util.Diagnostic#getCode() codes} from this package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.common.util.Diagnostic#getSource()
	 * @see org.eclipse.emf.common.util.Diagnostic#getCode()
	 * @generated
	 */
	public static final String DIAGNOSTIC_SOURCE = "de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel";

	/**
	 * The {@link org.eclipse.emf.common.util.Diagnostic#getCode() code} for constraint 'Validate Main Page Not Containing Web Page' of 'Main Page'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final int MAIN_PAGE__VALIDATE_MAIN_PAGE_NOT_CONTAINING_WEB_PAGE = 1;

	/**
	 * A constant with a fixed name that can be used as the base value for additional hand written constants.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final int GENERATED_DIAGNOSTIC_CODE_COUNT = 1;

	/**
	 * A constant with a fixed name that can be used as the base value for additional hand written constants in a derived class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static final int DIAGNOSTIC_CODE_COUNT = GENERATED_DIAGNOSTIC_CODE_COUNT;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public WebPageModelValidator() {
		super();
	}

	/**
	 * Returns the package of this validator switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EPackage getEPackage() {
	  return WebPageModelPackage.eINSTANCE;
	}

	/**
	 * Calls <code>validateXXX</code> for the corresponding classifier of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected boolean validate(int classifierID, Object value, DiagnosticChain diagnostics, Map<Object, Object> context) {
		switch (classifierID) {
			case WebPageModelPackage.WEB_PAGE:
				return validateWebPage((WebPage)value, diagnostics, context);
			case WebPageModelPackage.MAIN_PAGE:
				return validateMainPage((MainPage)value, diagnostics, context);
			case WebPageModelPackage.PAGE:
				return validatePage((Page)value, diagnostics, context);
			case WebPageModelPackage.ABSTRACT_HTML:
				return validateAbstractHTML((AbstractHTML)value, diagnostics, context);
			case WebPageModelPackage.SUB_PAGE:
				return validateSubPage((SubPage)value, diagnostics, context);
			case WebPageModelPackage.ABSTRACT_KEY_VAL:
				return validateAbstractKeyVal((AbstractKeyVal)value, diagnostics, context);
			case WebPageModelPackage.HTML_INCLUDE:
				return validateHtmlInclude((HtmlInclude)value, diagnostics, context);
			case WebPageModelPackage.VALUE:
				return validateValue((Value)value, diagnostics, context);
			case WebPageModelPackage.FILE_VALUE:
				return validateFileValue((FileValue)value, diagnostics, context);
			case WebPageModelPackage.STRING_VALUE:
				return validateStringValue((StringValue)value, diagnostics, context);
			case WebPageModelPackage.BASE:
				return validateBase((Base)value, diagnostics, context);
			default:
				return true;
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateWebPage(WebPage webPage, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment(webPage, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms(webPage, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms(webPage, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained(webPage, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired(webPage, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves(webPage, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID(webPage, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique(webPage, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique(webPage, diagnostics, context);
		if (result || diagnostics != null) result &= validateMainPage_DummyConstraint(webPage, diagnostics, context);
		if (result || diagnostics != null) result &= validateMainPage_validateMainPageNotContainingWebPage(webPage, diagnostics, context);
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateMainPage(MainPage mainPage, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment(mainPage, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms(mainPage, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms(mainPage, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained(mainPage, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired(mainPage, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves(mainPage, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID(mainPage, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique(mainPage, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique(mainPage, diagnostics, context);
		if (result || diagnostics != null) result &= validateMainPage_DummyConstraint(mainPage, diagnostics, context);
		if (result || diagnostics != null) result &= validateMainPage_validateMainPageNotContainingWebPage(mainPage, diagnostics, context);
		return result;
	}

	/**
	 * Validates the DummyConstraint constraint of '<em>Main Page</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateMainPage_DummyConstraint(MainPage mainPage, DiagnosticChain diagnostics, Map<Object, Object> context) {
		// TODO implement the constraint
		// -> specify the condition that violates the constraint
		// -> verify the diagnostic details, including severity, code, and message
		// Ensure that you remove @generated or mark it @generated NOT
		if (false) {
			if (diagnostics != null) {
				diagnostics.add
					(createDiagnostic
						(Diagnostic.ERROR,
						 DIAGNOSTIC_SOURCE,
						 0,
						 "_UI_GenericConstraint_diagnostic",
						 new Object[] { "DummyConstraint", getObjectLabel(mainPage, context) },
						 new Object[] { mainPage },
						 context));
			}
			return false;
		}
		return true;
	}

	/**
	 * Validates the validateMainPageNotContainingWebPage constraint of '<em>Main Page</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateMainPage_validateMainPageNotContainingWebPage(MainPage mainPage, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return mainPage.validateMainPageNotContainingWebPage(diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validatePage(Page page, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(page, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateAbstractHTML(AbstractHTML abstractHTML, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(abstractHTML, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateSubPage(SubPage subPage, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(subPage, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateAbstractKeyVal(AbstractKeyVal abstractKeyVal, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(abstractKeyVal, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateHtmlInclude(HtmlInclude htmlInclude, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(htmlInclude, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateValue(Value value, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(value, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateFileValue(FileValue fileValue, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(fileValue, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateStringValue(StringValue stringValue, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(stringValue, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateBase(Base base, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(base, diagnostics, context);
	}

	/**
	 * Returns the resource locator that will be used to fetch messages for this validator's diagnostics.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ResourceLocator getResourceLocator() {
		// TODO
		// Specialize this to return a resource locator for messages specific to this validator.
		// Ensure that you remove @generated or mark it @generated NOT
		return super.getResourceLocator();
	}

} //WebPageModelValidator
