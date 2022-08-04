/*******************************************************************************
 * Copyright (C) 2016-2018 Institute of Automation, TU Dresden.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Institute of Automation, TU Dresden - initial API and implementation
 ******************************************************************************/
package de.tud.et.ifa.agtele.emf.edit;

import java.util.Collection;
import java.util.Map;

import org.eclipse.emf.common.command.CommandStack;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.XMIResource;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.domain.IEditingDomainProvider;

/**
 * This editing domain is used in order to use a custom {@link ResourceSet}
 * implementation that manipulates the {@link Resource} instantiation in
 * selected cases.
 *
 * @author Baron
 *
 */
public class AgteleAdapterFactoryEditingDomain extends AdapterFactoryEditingDomain {

	public static Collection<Object> globalClipboard = null;
	
	/**
	 * Stores whether UUIDs shall be used to store resources.
	 */
	boolean saveUsingUUIDs = false;

	public AgteleAdapterFactoryEditingDomain(AdapterFactory adapterFactory, CommandStack commandStack) {
		super(adapterFactory, commandStack);
		this.resourceSet = this.createResourceSet();
	}

	public AgteleAdapterFactoryEditingDomain(AdapterFactory adapterFactory, CommandStack commandStack,
			Map<Resource, Boolean> resourceToReadOnlyMap) {
		super(adapterFactory, commandStack, resourceToReadOnlyMap);
		this.resourceSet = this.createResourceSet();
	}

	/**
	 * Creates the custom resource set implementation.
	 *
	 * @return
	 */
	protected ResourceSet createResourceSet() {
		return new AdapterFactoryEditingDomainResourceSet();
	}

	/**
	 * Sets the internal flag to use UUIDs when saving the resource set. If this
	 * setting can be realized should be checked using @link
	 * {@link AgteleAdapterFactoryEditingDomain#getSafeUsingUUIDsStatus()}
	 *
	 * @param useUUIDs
	 */
	public void setSaveUsingUUIDs(boolean useUUIDs) {
		this.saveUsingUUIDs = useUUIDs;
		for (Resource res : this.getResourceSet().getResources()) {
			if (res instanceof IAgteleResource) {
				IAgteleResource mRes = (IAgteleResource) res;
				if (mRes.isSaveUsingUUIDs() != this.saveUsingUUIDs && mRes.isSaveUsingUUIDsChangeAllowed()) {
					mRes.setSaveUsingUUIDs(this.saveUsingUUIDs);
				}
			}
		}
	}

	/**
	 * Returns whether uuids shall be used when saving the resources.
	 * 
	 * @return
	 */
	public boolean isSafeUsingUUIDs() {
		return this.saveUsingUUIDs;
	}

	/**
	 * Returns the status of the safe using UUIDs option.
	 *
	 * 1, if option is set, but not all resources can be safed using this
	 * option, 2 if option is set and all resources will use it, -1, if option
	 * is reset, but not all resources follow, -2 if option is reset and all
	 * resources follow.
	 *
	 * The status is determined using the {@link IAgteleResource} interface.
	 *
	 * @return the status
	 */
	public int getSafeUsingUUIDsStatus() {
		int nbUnknownStatus = 0, nbSetStatus = 0, nbResetStatus = 0;
		for (Resource res : this.getResourceSet().getResources()) {
			if (res instanceof IAgteleResource) {
				IAgteleResource mRes = (IAgteleResource) res;
				if (mRes.isSaveUsingUUIDs() != this.saveUsingUUIDs && mRes.isSaveUsingUUIDsChangeAllowed()) {
					mRes.setSaveUsingUUIDs(this.saveUsingUUIDs);
				}
				if (mRes.isSaveUsingUUIDs()) {
					nbSetStatus += 1;
				} else {
					nbResetStatus += 1;
				}
			} else {
				nbUnknownStatus += 1;
			}
		}
		if (this.saveUsingUUIDs) {
			return nbUnknownStatus > 0 || nbResetStatus > 0 ? 1 : 2;
		} else {
			return nbUnknownStatus > 0 || nbSetStatus > 0 ? -1 : -2;
		}
	}

	/**
	 * This {@link ResourceSet} overrides the resource creation in order to
	 * modify the standard {@link XMIResource}s.
	 *
	 * @author Baron
	 *
	 */
	public class AdapterFactoryEditingDomainResourceSet extends ResourceSetImpl implements IEditingDomainProvider {
		public AdapterFactoryEditingDomainResourceSet() {
			super();
		}

		@Override
		public EditingDomain getEditingDomain() {
			return AgteleAdapterFactoryEditingDomain.this;
		}

		@Override
		public Resource createResource(URI uri, String contentType) {
			Resource.Factory resourceFactory = this.getResourceFactoryRegistry().getFactory(uri, contentType);
			if (resourceFactory != null && resourceFactory instanceof XMIResourceFactoryImpl) {
				Resource result = new AgteleXMIResourceImpl(uri);
				this.getResources().add(result);
				return result;
			}
			return super.createResource(uri, contentType);
		}

		@Override
		public Resource getResource(URI uri, boolean loadOnDemand) {
			Resource result = super.getResource(uri, loadOnDemand);

			if (result instanceof AgteleXMIResourceImpl) {
				AgteleXMIResourceImpl mRes = (AgteleXMIResourceImpl) result;
				if (mRes.isSaveUsingUUIDsChangeAllowed()) {
					mRes.setSaveUsingUUIDs(AgteleAdapterFactoryEditingDomain.this.saveUsingUUIDs);
				}
			}

			return result;
		}
	}
	
	@Override
	public Collection<Object> getClipboard() {
		return AgteleAdapterFactoryEditingDomain.globalClipboard;
	}
	
	@Override
	public void setClipboard(Collection<Object> clipboard) {
		AgteleAdapterFactoryEditingDomain.globalClipboard = clipboard;
	}
}
