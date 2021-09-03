/**
 */
package de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.ResourceSet;

import de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.importAdapter.ImportAdapter;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Model</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.Model#getContent <em>Content</em>}</li>
 *   <li>{@link de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.Model#getUri <em>Uri</em>}</li>
 *   <li>{@link de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.Model#getImportAdapter <em>Import Adapter</em>}</li>
 * </ul>
 *
 * @see de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.ModelStoragePackage#getModel()
 * @model
 * @generated
 */
public interface Model extends UpdateableElement {
	/**
	 * Returns the value of the '<em><b>Content</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.emf.ecore.EObject}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Content</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Content</em>' reference list.
	 * @see de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.ModelStoragePackage#getModel_Content()
	 * @model derived="true"
	 *        annotation="http://et.tu-dresden.de/ifa/emf/commons/2018/AgTeleGenModel get='\r\nif (content == null) {\r\n\tcontent = new &lt;%org.eclipse.emf.ecore.util.EObjectResolvingEList%&gt;&lt;&lt;%org.eclipse.emf.ecore.EObject%&gt;&gt;(EObject.class, this, &lt;%de.tud.et.ifa.agtele.eclipse.emf.modelStorage.ModelStoragePackage%&gt;.MODEL__CONTENT);\r\n}\r\n\r\n&lt;%java.util.ArrayList%&gt;&lt;EObject&gt; currentContent = new ArrayList&lt;&gt;();\r\n&lt;%java.util.List%&gt;&lt;EObject&gt; toAdd, toRemove;\r\n\r\nfor (&lt;%org.eclipse.emf.ecore.resource.Resource%&gt; res : this.resourceSet.getResources()) {\r\n\tcurrentContent.addAll(res.getContents());\r\n}\r\n\r\ntoRemove = content.stream().filter(o -&gt; !currentContent.contains(o)).collect(&lt;%java.util.stream.Collectors%&gt;.toList());\r\ntoAdd = currentContent.stream().filter(o -&gt; !content.contains(o)).collect(Collectors.toList());\r\n\r\ncontent.removeAll(toRemove);\r\ncontent.addAll(toAdd);\r\n\r\nreturn content;'"
	 * @generated
	 */
	EList<EObject> getContent();

	/**
	 * Returns the value of the '<em><b>Uri</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Uri</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Uri</em>' attribute.
	 * @see #setUri(String)
	 * @see de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.ModelStoragePackage#getModel_Uri()
	 * @model required="true"
	 * @generated
	 */
	String getUri();

	/**
	 * Sets the value of the '{@link de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.Model#getUri <em>Uri</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Uri</em>' attribute.
	 * @see #getUri()
	 * @generated
	 */
	void setUri(String value);

	/**
	 * Returns the value of the '<em><b>Import Adapter</b></em>' containment reference.
	 * It is bidirectional and its opposite is '{@link de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.importAdapter.ImportAdapter#getModel <em>Model</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Import Adapter</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Import Adapter</em>' containment reference.
	 * @see #setImportAdapter(ImportAdapter)
	 * @see de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.ModelStoragePackage#getModel_ImportAdapter()
	 * @see de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.importAdapter.ImportAdapter#getModel
	 * @model opposite="model" containment="true" resolveProxies="true"
	 * @generated
	 */
	ImportAdapter getImportAdapter();

	/**
	 * Sets the value of the '{@link de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.Model#getImportAdapter <em>Import Adapter</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Import Adapter</em>' containment reference.
	 * @see #getImportAdapter()
	 * @generated
	 */
	void setImportAdapter(ImportAdapter value);

	void resetContent();
	
	ResourceSet getResourceSet();

	void registerIdentifyableElement(Collection<String> identifiers, EObject element);

	List<IResolveResult> resolve(String identifier);

	void deregisterIdentifyableElement(EObject element);
	
	public default List<IResolveResult> factorResolveResults(String id, List<EObject> resolvedObjects) {
		ArrayList<IResolveResult> result = new ArrayList<>();
		
		for (EObject resolved : resolvedObjects) {
			//ResourceSet set = resolved.eResource().getResourceSet();
			result.add(new IResolveResult() {
				@Override
				public EObject getElement() {
					return resolved;
				}
				@Override
				public Model getModel() {
					return Model.this;
				}
				@Override
				public String getId() {
					return id;
				}
			});
		}
		
		return result;
	}
	
	public default ModelStorage getStorage() {
		if (this.eContainer() instanceof ModelStorage) {
			return (ModelStorage) this.eContainer();
		}
		return null;
	}
	
	public boolean isInitialized();
	public void setInitialized();

	public void dispose();

} // Model
