/**
 */
package de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;

import de.tud.et.ifa.agtele.emf.AgteleEcoreUtil;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Web Page</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.WebPage#getBaseUrl <em>Base Url</em>}</li>
 *   <li>{@link de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.WebPage#getOutDir <em>Out Dir</em>}</li>
 *   <li>{@link de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.WebPage#getLang <em>Lang</em>}</li>
 *   <li>{@link de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.WebPage#getResourcesOutPathFragment <em>Resources Out Path Fragment</em>}</li>
 *   <li>{@link de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.WebPage#getAlternatives <em>Alternatives</em>}</li>
 *   <li>{@link de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.WebPage#getInvAlternatives <em>Inv Alternatives</em>}</li>
 * </ul>
 *
 * @see de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.WebPageModelPackage#getWebPage()
 * @model
 * @generated
 */
public interface WebPage extends MainPage {
	/**
	 * Returns the value of the '<em><b>Base Url</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Base Url</em>' attribute.
	 * @see #setBaseUrl(String)
	 * @see de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.WebPageModelPackage#getWebPage_BaseUrl()
	 * @model
	 * @generated
	 */
	String getBaseUrl();

	/**
	 * Sets the value of the '{@link de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.WebPage#getBaseUrl <em>Base Url</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Base Url</em>' attribute.
	 * @see #getBaseUrl()
	 * @generated
	 */
	void setBaseUrl(String value);

	/**
	 * Returns the value of the '<em><b>Out Dir</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Out Dir</em>' attribute.
	 * @see #setOutDir(String)
	 * @see de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.WebPageModelPackage#getWebPage_OutDir()
	 * @model
	 * @generated
	 */
	String getOutDir();

	/**
	 * Sets the value of the '{@link de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.WebPage#getOutDir <em>Out Dir</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Out Dir</em>' attribute.
	 * @see #getOutDir()
	 * @generated
	 */
	void setOutDir(String value);

	/**
	 * Returns the value of the '<em><b>Lang</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Lang</em>' attribute.
	 * @see #setLang(String)
	 * @see de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.WebPageModelPackage#getWebPage_Lang()
	 * @model required="true"
	 * @generated
	 */
	String getLang();

	/**
	 * Sets the value of the '{@link de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.WebPage#getLang <em>Lang</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Lang</em>' attribute.
	 * @see #getLang()
	 * @generated
	 */
	void setLang(String value);

	/**
	 * Returns the value of the '<em><b>Resources Out Path Fragment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Resources Out Path Fragment</em>' attribute.
	 * @see #setResourcesOutPathFragment(String)
	 * @see de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.WebPageModelPackage#getWebPage_ResourcesOutPathFragment()
	 * @model
	 * @generated
	 */
	String getResourcesOutPathFragment();

	/**
	 * Sets the value of the '{@link de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.WebPage#getResourcesOutPathFragment <em>Resources Out Path Fragment</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Resources Out Path Fragment</em>' attribute.
	 * @see #getResourcesOutPathFragment()
	 * @generated
	 */
	void setResourcesOutPathFragment(String value);

	/**
	 * Returns the value of the '<em><b>Alternatives</b></em>' reference list.
	 * The list contents are of type {@link de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.WebPage}.
	 * It is bidirectional and its opposite is '{@link de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.WebPage#getInvAlternatives <em>Inv Alternatives</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Alternatives</em>' reference list.
	 * @see de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.WebPageModelPackage#getWebPage_Alternatives()
	 * @see de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.WebPage#getInvAlternatives
	 * @model opposite="invAlternatives"
	 * @generated
	 */
	EList<WebPage> getAlternatives();

	/**
	 * Returns the value of the '<em><b>Inv Alternatives</b></em>' reference list.
	 * The list contents are of type {@link de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.WebPage}.
	 * It is bidirectional and its opposite is '{@link de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.WebPage#getAlternatives <em>Alternatives</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Inv Alternatives</em>' reference list.
	 * @see de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.WebPageModelPackage#getWebPage_InvAlternatives()
	 * @see de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.WebPage#getAlternatives
	 * @model opposite="alternatives"
	 * @generated
	 */
	EList<WebPage> getInvAlternatives();

	default String getParentUrl () {
		return this.getBaseUrl();
	}
	
	default String getTargetDir () {
		if (this.getOutDir() != null && !this.getOutDir().isBlank()) {
			return this.getOutDir();
		}
		return "/out";
	}
	default String getRelativeTargetDir () {
		return "";
	}
	default List<WebPage> getAllAlternativePages () {
		Set<WebPage> result = new LinkedHashSet<>();
		result.addAll(this.getAlternatives());
		result.addAll(this.getInvAlternatives());
		return new ArrayList<>(result);
	}
	
	default Map<String, WebPage> getAllAlternativesByLanguage () {
		Map<String, WebPage> result = new LinkedHashMap<>();
		for (WebPage page : this.getAllAlternativePages() ) {
			if (!result.containsKey(page.getLang())) {
				result.put(page.getLang(), page);
			}
		}
		return result;
	}
	
	default Map<String, String> getAllResourcesToCopy () {
		Map<String, String> result = this.getStaticResourceMap();
		for (EObject o : AgteleEcoreUtil.getAllInstances(WebPageModelPackage.Literals.ABSTRACT_HTML, this)) {
			Map<String, String> resources =  ((AbstractHTML)o).getStaticResourceMap();
			for (String s : resources.keySet()) {				
				result.putIfAbsent(s, resources.get(s));
			}			
		}
		return result;
	}
	
	default List<AbstractHTML> getAllNodes () {
		List<AbstractHTML> result = new ArrayList<>();
		result.add(this);
		for (EObject o : AgteleEcoreUtil.getAllInstances(WebPageModelPackage.Literals.ABSTRACT_HTML, this)) {
			if (((AbstractHTML)o).isNode()) {
				result.add((AbstractHTML)o);
			}
		}		
		return result;
	}
	
	default URI getBasePath() {
		URI filePath = null;
		filePath = this.eResource().getURI();
		URI result = filePath.trimSegments(1);
		return result;
	}
	
	default WebPage getWebPage () {
		return this;
	}
} // WebPage
 