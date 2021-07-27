/**
 */
package de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.impl;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.ResourceSet;

import de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.Model;
import de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.ModelAdapter;
import de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.ModelStoragePackage;
import de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.importAdapter.ImportAdapter;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Model Adapter</b></em>'.
 * <!-- end-user-doc -->
 *
 * @generated
 */
public abstract class ModelAdapterImpl extends ModelImpl implements ModelAdapter {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ModelAdapterImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ModelStoragePackage.Literals.MODEL_ADAPTER;
	}
	
	@Override
	public EList<EObject> getContent() {
		if (this.getModel() != null) {
			return this.getModel().getContent();
		}
		return super.getContent();
	}

	@Override
	public ImportAdapter getImportAdapter() {
		if (this.getModel() != null) {
			return this.getModel().getImportAdapter();
		}
		return null;
	}
	
	@Override
	public ResourceSet getResourceSet() {
		if (this.getModel() != null) {
			return this.getModel().getResourceSet();
		}
		return null;
	}
	
	@Override
	public String getUri() {
		if (this.getModel() != null) {
			return this.model.getUri();
		}
		return null;
	}
	
	protected Model model = null;
	
	public void setModel(Model model) {
		this.model = model;
	}
	
	public Model getModel() {
		return this.model;
	}
} //ModelAdapterImpl
