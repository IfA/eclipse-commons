/**
 */
package de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.util;

import de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.*;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.WebPageModelPackage
 * @generated
 */
public class WebPageModelAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static WebPageModelPackage modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public WebPageModelAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = WebPageModelPackage.eINSTANCE;
		}
	}

	/**
	 * Returns whether this factory is applicable for the type of the object.
	 * <!-- begin-user-doc -->
	 * This implementation returns <code>true</code> if the object is either the model's package or is an instance object of the model.
	 * <!-- end-user-doc -->
	 * @return whether this factory is applicable for the type of the object.
	 * @generated
	 */
	@Override
	public boolean isFactoryForType(Object object) {
		if (object == modelPackage) {
			return true;
		}
		if (object instanceof EObject) {
			return ((EObject)object).eClass().getEPackage() == modelPackage;
		}
		return false;
	}

	/**
	 * The switch that delegates to the <code>createXXX</code> methods.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected WebPageModelSwitch<Adapter> modelSwitch =
		new WebPageModelSwitch<Adapter>() {
			@Override
			public Adapter caseWebPage(WebPage object) {
				return createWebPageAdapter();
			}
			@Override
			public Adapter caseMainPage(MainPage object) {
				return createMainPageAdapter();
			}
			@Override
			public Adapter casePage(Page object) {
				return createPageAdapter();
			}
			@Override
			public Adapter caseAbstractHTML(AbstractHTML object) {
				return createAbstractHTMLAdapter();
			}
			@Override
			public Adapter caseSubPage(SubPage object) {
				return createSubPageAdapter();
			}
			@Override
			public Adapter caseAbstractKeyVal(AbstractKeyVal object) {
				return createAbstractKeyValAdapter();
			}
			@Override
			public Adapter caseHtmlInclude(HtmlInclude object) {
				return createHtmlIncludeAdapter();
			}
			@Override
			public Adapter caseValue(Value object) {
				return createValueAdapter();
			}
			@Override
			public Adapter caseFileValue(FileValue object) {
				return createFileValueAdapter();
			}
			@Override
			public Adapter caseStringValue(StringValue object) {
				return createStringValueAdapter();
			}
			@Override
			public Adapter caseBase(Base object) {
				return createBaseAdapter();
			}
			@Override
			public Adapter defaultCase(EObject object) {
				return createEObjectAdapter();
			}
		};

	/**
	 * Creates an adapter for the <code>target</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param target the object to adapt.
	 * @return the adapter for the <code>target</code>.
	 * @generated
	 */
	@Override
	public Adapter createAdapter(Notifier target) {
		return modelSwitch.doSwitch((EObject)target);
	}


	/**
	 * Creates a new adapter for an object of class '{@link de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.WebPage <em>Web Page</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.WebPage
	 * @generated
	 */
	public Adapter createWebPageAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.MainPage <em>Main Page</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.MainPage
	 * @generated
	 */
	public Adapter createMainPageAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.Page <em>Page</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.Page
	 * @generated
	 */
	public Adapter createPageAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.AbstractHTML <em>Abstract HTML</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.AbstractHTML
	 * @generated
	 */
	public Adapter createAbstractHTMLAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.SubPage <em>Sub Page</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.SubPage
	 * @generated
	 */
	public Adapter createSubPageAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.AbstractKeyVal <em>Abstract Key Val</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.AbstractKeyVal
	 * @generated
	 */
	public Adapter createAbstractKeyValAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.HtmlInclude <em>Html Include</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.HtmlInclude
	 * @generated
	 */
	public Adapter createHtmlIncludeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.Value <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.Value
	 * @generated
	 */
	public Adapter createValueAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.FileValue <em>File Value</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.FileValue
	 * @generated
	 */
	public Adapter createFileValueAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.StringValue <em>String Value</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.StringValue
	 * @generated
	 */
	public Adapter createStringValueAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.Base <em>Base</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.Base
	 * @generated
	 */
	public Adapter createBaseAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for the default case.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @generated
	 */
	public Adapter createEObjectAdapter() {
		return null;
	}

} //WebPageModelAdapterFactory
