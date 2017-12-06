package de.tud.et.ifa.agtele.emf.edit;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.xmi.XMIResource;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceImpl;

/**
 * The agtele XMIResource will be used by the
 * {@link AgteleAdapterFactoryEditingDomain} in order to replace a standard
 * {@link XMIResource}.
 *
 * @author Baron
 *
 */
public class AgteleXMIResourceImpl extends XMIResourceImpl implements IAgteleResource {

	protected boolean useUUIDs = false;

	public AgteleXMIResourceImpl(URI uri) {
		super(uri);
	}

	@Override
	public boolean isSaveUsingUUIDs() {
		return this.useUUIDs;
	}

	@Override
	public void setSaveUsingUUIDs(boolean useUUIDs) {
		this.useUUIDs = useUUIDs;
	}

	@Override
	protected boolean useUUIDs() {
		return this.useUUIDs;
	}
}
