/**
 */
package de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.importAdapter.impl;

import de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.importAdapter.FileSystemConnector;
import de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.importAdapter.ImportAdapter;
import de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.importAdapter.ImportAdapterFactory;
import de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.importAdapter.ImportAdapterPackage;
import de.tud.et.ifa.agtele.emf.importing.INodeDescriptor;
import de.tud.et.ifa.agtele.emf.importing.NodeDescriptorImpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>File System Connector</b></em>'.
 * <!-- end-user-doc -->
 *
 * @generated
 */
public class FileSystemConnectorImpl extends ConnectorImpl implements FileSystemConnector {
	protected ResourceSetImpl resourceSet;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected FileSystemConnectorImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ImportAdapterPackage.Literals.FILE_SYSTEM_CONNECTOR;
	}
	
	protected Map<?,?> getLoadOptions() {
		HashMap<String, Object> result = new HashMap<>();
		
		return result;
	}
	
	protected boolean connected = false;
	
	protected XMIResourceImpl rootResource = null;

	@Override
	public void connect() {
		if (connected) {
			return;
		}
		this.resourceSet = new ResourceSetImpl();
		rootResource = new XMIResourceImpl();	
		rootResource.setURI(URI.createURI(this.getConnectionUri()));
		this.resourceSet.getResources().add(rootResource);
		try {
			rootResource.load(getLoadOptions());
			connected = true;
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}

	@Override
	public void disconnect() {
		if (!connected) {
			return;
		}
		this.resourceSet.getResources().clear();
		this.resourceSet = null;	
		this.rootResource = null;
		connected = false;
	}

	@Override
	public boolean isConnected() {
		return connected;
	}

	@Override
	public ImportAdapter createImportAdapter() {
		this.adapter = ImportAdapterFactory.eINSTANCE.createImportAdapter();
		this.adapter.setConnector(this);
		return this.adapter;
	}

	@Override
	public Collection<Object> browse(Object node) {
		ArrayList<Object> result = new ArrayList<>();
		if (node == null) {
			result.addAll(this.rootResource.getContents());
			return result;
		}
		if (node instanceof EObject) {
			result.addAll(((EObject)node).eContents());
		}
		
		return result;
	}

	@Override
	public INodeDescriptor getTypeInfo(Object node) {
		if (node instanceof EObject) {
			EObject o = (EObject) node;
			return new NodeDescriptorImpl(node, o.eClass().getEPackage().getNsURI(), o.eClass().getName());
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<Object> readReference(Object node, EReference ref) {
		ArrayList<Object> result = new ArrayList<>();				
		if (node instanceof EObject) {
			Object value = ((EObject)node).eGet(ref);
			if (value instanceof EList) {
				result.addAll((Collection<? extends Object>)value);
			} else if (value != null) {
				result.add(value);
			}			
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<Object> readAttribute(Object node, EAttribute attribute) {
		ArrayList<Object> result = new ArrayList<>();				
		if (node instanceof EObject) {
			Object value = ((EObject)node).eGet(attribute);
			if (value instanceof EList) {
				result.addAll((Collection<? extends Object>)value);
			} else if (value != null) {
				result.add(value);
			}			
		}
		return result;
	}

} //FileSystemConnectorImpl
