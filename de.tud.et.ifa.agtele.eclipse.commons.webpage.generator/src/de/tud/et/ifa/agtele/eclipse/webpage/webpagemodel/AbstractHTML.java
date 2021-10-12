/**
 */
package de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;

import de.tud.et.ifa.agtele.IStringSubstitutor;
import de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.util.WebPageModelUtils;
import de.tud.et.ifa.agtele.emf.AgteleEcoreUtil;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Abstract HTML</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.AbstractHTML#getFooter <em>Footer</em>}</li>
 *   <li>{@link de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.AbstractHTML#getHeader <em>Header</em>}</li>
 *   <li>{@link de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.AbstractHTML#getSrcPathFragment <em>Src Path Fragment</em>}</li>
 *   <li>{@link de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.AbstractHTML#getScripts <em>Scripts</em>}</li>
 *   <li>{@link de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.AbstractHTML#getStyles <em>Styles</em>}</li>
 *   <li>{@link de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.AbstractHTML#getTitle <em>Title</em>}</li>
 *   <li>{@link de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.AbstractHTML#getStaticResources <em>Static Resources</em>}</li>
 *   <li>{@link de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.AbstractHTML#getContent <em>Content</em>}</li>
 *   <li>{@link de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.AbstractHTML#getExternalUrl <em>External Url</em>}</li>
 *   <li>{@link de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.AbstractHTML#getAnnouncement <em>Announcement</em>}</li>
 *   <li>{@link de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.AbstractHTML#getNavName <em>Nav Name</em>}</li>
 * </ul>
 *
 * @see de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.WebPageModelPackage#getAbstractHTML()
 * @model abstract="true"
 * @generated
 */
public interface AbstractHTML extends Base {
	/**
	 * Returns the value of the '<em><b>Footer</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Footer</em>' containment reference.
	 * @see #setFooter(Value)
	 * @see de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.WebPageModelPackage#getAbstractHTML_Footer()
	 * @model containment="true"
	 * @generated
	 */
	Value getFooter();

	/**
	 * Sets the value of the '{@link de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.AbstractHTML#getFooter <em>Footer</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Footer</em>' containment reference.
	 * @see #getFooter()
	 * @generated
	 */
	void setFooter(Value value);

	/**
	 * Returns the value of the '<em><b>Header</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Header</em>' containment reference.
	 * @see #setHeader(Value)
	 * @see de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.WebPageModelPackage#getAbstractHTML_Header()
	 * @model containment="true"
	 * @generated
	 */
	Value getHeader();

	/**
	 * Sets the value of the '{@link de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.AbstractHTML#getHeader <em>Header</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Header</em>' containment reference.
	 * @see #getHeader()
	 * @generated
	 */
	void setHeader(Value value);

	/**
	 * Returns the value of the '<em><b>Src Path Fragment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Src Path Fragment</em>' attribute.
	 * @see #setSrcPathFragment(String)
	 * @see de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.WebPageModelPackage#getAbstractHTML_SrcPathFragment()
	 * @model
	 * @generated
	 */
	String getSrcPathFragment();

	/**
	 * Sets the value of the '{@link de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.AbstractHTML#getSrcPathFragment <em>Src Path Fragment</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Src Path Fragment</em>' attribute.
	 * @see #getSrcPathFragment()
	 * @generated
	 */
	void setSrcPathFragment(String value);

	/**
	 * Returns the value of the '<em><b>Scripts</b></em>' containment reference list.
	 * The list contents are of type {@link de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.HtmlInclude}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Scripts</em>' containment reference list.
	 * @see de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.WebPageModelPackage#getAbstractHTML_Scripts()
	 * @model containment="true"
	 * @generated
	 */
	EList<HtmlInclude> getScripts();

	/**
	 * Returns the value of the '<em><b>Styles</b></em>' containment reference list.
	 * The list contents are of type {@link de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.HtmlInclude}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Styles</em>' containment reference list.
	 * @see de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.WebPageModelPackage#getAbstractHTML_Styles()
	 * @model containment="true"
	 * @generated
	 */
	EList<HtmlInclude> getStyles();

	/**
	 * Returns the value of the '<em><b>Title</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Title</em>' attribute.
	 * @see #setTitle(String)
	 * @see de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.WebPageModelPackage#getAbstractHTML_Title()
	 * @model required="true"
	 * @generated
	 */
	String getTitle();

	/**
	 * Sets the value of the '{@link de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.AbstractHTML#getTitle <em>Title</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Title</em>' attribute.
	 * @see #getTitle()
	 * @generated
	 */
	void setTitle(String value);

	/**
	 * Returns the value of the '<em><b>Static Resources</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Static Resources</em>' attribute list.
	 * @see de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.WebPageModelPackage#getAbstractHTML_StaticResources()
	 * @model
	 * @generated
	 */
	EList<String> getStaticResources();

	/**
	 * Returns the value of the '<em><b>Content</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Content</em>' containment reference.
	 * @see #setContent(Value)
	 * @see de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.WebPageModelPackage#getAbstractHTML_Content()
	 * @model containment="true"
	 * @generated
	 */
	Value getContent();

	/**
	 * Sets the value of the '{@link de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.AbstractHTML#getContent <em>Content</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Content</em>' containment reference.
	 * @see #getContent()
	 * @generated
	 */
	void setContent(Value value);

	/**
	 * Returns the value of the '<em><b>External Url</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>External Url</em>' attribute.
	 * @see #setExternalUrl(String)
	 * @see de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.WebPageModelPackage#getAbstractHTML_ExternalUrl()
	 * @model
	 * @generated
	 */
	String getExternalUrl();

	/**
	 * Sets the value of the '{@link de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.AbstractHTML#getExternalUrl <em>External Url</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>External Url</em>' attribute.
	 * @see #getExternalUrl()
	 * @generated
	 */
	void setExternalUrl(String value);

	/**
	 * Returns the value of the '<em><b>Announcement</b></em>' containment reference list.
	 * The list contents are of type {@link de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.Announcement}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Announcement</em>' containment reference list.
	 * @see de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.WebPageModelPackage#getAbstractHTML_Announcement()
	 * @model containment="true"
	 * @generated
	 */
	EList<Announcement> getAnnouncement();

	/**
	 * Returns the value of the '<em><b>Nav Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Nav Name</em>' attribute.
	 * @see #setNavName(String)
	 * @see de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.WebPageModelPackage#getAbstractHTML_NavName()
	 * @model
	 * @generated
	 */
	String getNavName();

	/**
	 * Sets the value of the '{@link de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.AbstractHTML#getNavName <em>Nav Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Nav Name</em>' attribute.
	 * @see #getNavName()
	 * @generated
	 */
	void setNavName(String value);

	default List<HtmlInclude> getLocalScriptList () {		
		return WebPageModelUtils.uniqueifyNames(this.getScripts());
	}
	
	default List<HtmlInclude> scriptList () {
		if (this.eContainer() instanceof AbstractHTML) {
			return WebPageModelUtils.applyKeyVal(this.getLocalScriptList(), ((AbstractHTML)this.eContainer()).scriptList());
		}
		return this.getLocalScriptList();
	}
	
	default List<HtmlInclude> getLocalStyleList () {		
		return WebPageModelUtils.uniqueifyNames(this.getStyles());
	}	
	default List<HtmlInclude> styleList () {
		if (this.eContainer() instanceof AbstractHTML) {
			return WebPageModelUtils.applyKeyVal(this.getLocalStyleList(), ((AbstractHTML)this.eContainer()).styleList());
		}
		return this.getLocalStyleList();
	}
	
	default List<HtmlInclude> filterIncludes(List<HtmlInclude> includes, boolean inline) {
		return includes.stream().filter(i -> i.isInline() == inline).collect(Collectors.toList());
	}
		
	default List<Value> expandIncludes(List<HtmlInclude> includes, boolean inline) {
		final List<Value> result = new ArrayList<>();
		this.filterIncludes(includes, inline).forEach(i -> {
			result.addAll(i.getValue());
		});
		return result;
	}
	
	default String getFileExtension() {
		if (this.getContent() instanceof FileValue) {
			FileValue val = (FileValue) this.getContent();
			if (val.getValue() != null && !val.getValue().isBlank() ) {
				String contentFile = val.getValue();
				int i = contentFile.lastIndexOf(".");
				if (i > 0 && i < contentFile.length() - 2) {
					return contentFile.substring(i + 1);
				}
			}
		}
		return "html";
	}
	
	default String getSrcDir () {
		return null;
	}
	
	
	default boolean isNode () {
		return false;
	}
	
	default String getTargetFileName () {
		if (!this.isNode()) {
			return  WebPageModelUtils.getUrlSafeName(this.getName()) + "." + this.getFileExtension();
		}
		return "index." + this.getFileExtension();
	}	

	default String getTargetDir () {
		String parentDir = "";
		if (this.eContainer() instanceof AbstractHTML) {
			parentDir = ((AbstractHTML) this.eContainer()).getTargetDir();
		}
		if (!this.isNode()) {
			return parentDir;
		}
		return parentDir + "/" + WebPageModelUtils.getUrlSafeName(this.getName());
	}
	default String getTargetFilePath () {
		String dir = this.getTargetDir();
		return dir + (!dir.isBlank() ? "/" : "") + this.getTargetFileName();
	}
	default String getRelativeTargetDir () {
		String parentDir = "";
		if (this.eContainer() instanceof AbstractHTML) {
			parentDir = ((AbstractHTML) this.eContainer()).getRelativeTargetDir();
		}
		if (!this.isNode()) {
			return parentDir;
		}
		return parentDir + (!parentDir.isBlank() ? "/" : "") + WebPageModelUtils.getUrlSafeName(this.getName());
	}
	default String getRelativeTargetFilePath() {
		String dir = this.getRelativeTargetDir();
		return dir + (!dir.isBlank() ? "/" : "") + this.getTargetFileName();
	}
	
	default String getParentUrl () {
		String parentUrl = "";
		if (this.eContainer() instanceof AbstractHTML) {
			parentUrl = ((AbstractHTML) this.eContainer()).getParentUrl();
		}
		if (this.isNode()) {
			parentUrl = parentUrl + "/" + WebPageModelUtils.getUrlSafeName(this.getName());
		}
		return parentUrl;
	}
	default String getUrl () {
		if (this.isExternal()) {
			if (this.getExternalUrl().startsWith("http://") || this.getExternalUrl().startsWith("https://")) {
				return this.getExternalUrl();
			}
			if (this.getExternalUrl().startsWith("/")) {
				return this.getExternalUrl();
			}
			return this.getParentUrl() + "/" + this.getExternalUrl();
		}
		return this.getParentUrl() + "/" + this.getTargetFileName();
	}
	
	
	default String getSrcContentPath () {
		String lastFragment = null;
		if (this.getSrcPathFragment() != null && !this.getSrcPathFragment().isBlank()) {
			lastFragment = this.getSrcPathFragment();
		} else {
			lastFragment = WebPageModelUtils.getUrlSafeName(this.getName()) + "." + this.getFileExtension();
			
		}
		if (lastFragment == null || lastFragment.isBlank()) {
			return null;
		}
		
		if (WebPageModelUtils.isAbsolutePath(lastFragment)) {
			return this.getSrcDir() + "/" + lastFragment;
		}
		return lastFragment;
	}
	
	default List<String> getAllLocalStaticResources() {
		return new ArrayList<>(this.getStaticResources());
	}
	
	default Map<String,String> getStaticResourceMap () {
		Map<String,String> result = new LinkedHashMap<>();
		
		for (String res : this.getAllLocalStaticResources()) {
			if (res != null && !res.isBlank()) {
				String src = null, target= null;
				if (WebPageModelUtils.isAbsolutePath(res)) {
					src = this.getMainPage().getSrcDir() + res; 
					target = this.getMainPage().getTargetDir() + res; 
				} else {
					src = this.getSrcDir() + "/" + res;
					target = this.getTargetDir() + "/" + res;
				}
				result.put(src, target);
			}
		}
		
		return result;
	}
	

	default WebPage getWebPage() {
		return (WebPage) AgteleEcoreUtil.getAllContainers(this, true).stream()
				.filter(o -> o instanceof WebPage).findFirst().orElse(null);
	}
	
	default MainPage getMainPage() {
		return (MainPage) AgteleEcoreUtil.getAllContainers(this, false).stream()
				.filter(o -> o instanceof MainPage).findFirst().orElse(null);
	}
	
	default MainPage getPage() {
		return (MainPage) AgteleEcoreUtil.getAllContainers(this, false).stream()
				.filter(o -> o instanceof Page).findFirst().orElse(null);
	}
	
	default Value getTargetFooter () {
		Value footer = this.getFooter();
		if (footer != null) {
			return footer;
		}
		if (this.eContainer() instanceof AbstractHTML) {
			return ((AbstractHTML)this.eContainer()).getTargetFooter();
		}
		return null;
	}
	default Value getTargetHeader () {
		Value header = this.getHeader();
		if (header != null) {
			return header;
		}
		if (this.eContainer() instanceof AbstractHTML) {
			return ((AbstractHTML)this.eContainer()).getTargetHeader();
		}
		return null;
	}

	default URI getSrcPath(IStringSubstitutor substitutor, String appendix) {
		String srcPath = "";
		if (appendix != null && !appendix.isBlank()) {
			if (WebPageModelUtils.isAbsolutePath(appendix)) {
				srcPath = appendix; 
			} else {
				srcPath = (this.getSrcDir() != null && !this.getSrcDir().isBlank()? this.getSrcDir() + "/" : "") + appendix;
			}
		} else {
			srcPath = this.getSrcDir();
		}
		
		srcPath = substitutor.substitute(srcPath);
		
		URI p = URI.createURI(srcPath);
		if (!p.hasAbsolutePath()) {
			return p.resolve(this.getWebPage().getBasePath().appendSegment("someFile.tmp"));
		}
		return URI.createURI(srcPath);
	}
	
	default boolean isSuppressArtifact () {
		return this.isExternal();
	}
	
	default boolean isExternal() {
		return this.getExternalUrl() != null && !this.getExternalUrl().isBlank();
	}
	
	default Map<String, Announcement> getLocalAnnouncements(AnnouncementLocationEnum type, boolean propagateOnly) {
		Map<String, Announcement> result = new LinkedHashMap<>();
		for (Announcement a : this.getAnnouncement()) {
			if (a.getName() == null || a.getName().isBlank() || a.isDisable()) {
				continue;
			}
			if (a.getLocation() == type && (!propagateOnly || a.isPropagate())) {
				result.put(a.getName(), a);
			}
		}
		return result;
	}
	
	default Map<String, Announcement> getInheritedAnnouncements(AnnouncementLocationEnum type) {
		Map<String, Announcement> result = new LinkedHashMap<>();
		result.putAll(this.getLocalAnnouncements(type, true));
		
		if (this.eContainer() instanceof AbstractHTML) {
			Map<String, Announcement> inherited = ((AbstractHTML)this.eContainer()).getInheritedAnnouncements(type);
			for (String key : inherited.keySet()) {
				result.putIfAbsent(key, inherited.get(key));
			}			
		}
		
		return result;
		
	}

	default List<Announcement> getCompiledAnnouncements(AnnouncementLocationEnum type) {
		Map<String, Announcement> result = new LinkedHashMap<>();
		result.putAll(this.getLocalAnnouncements(type, false));
		
		if (this.eContainer() instanceof AbstractHTML) {
			Map<String, Announcement> inherited = ((AbstractHTML)this.eContainer()).getInheritedAnnouncements(type);
			for (String key : inherited.keySet()) {
				result.putIfAbsent(key, inherited.get(key));
			}			
		}
		
		return new ArrayList<>(result.values());
	}
	
	default String compiledNavName () {
		if (this.getNavName() == null) {
			return this.getTitle();
		}
		return this.getNavName();
	}
} // AbstractHTML
