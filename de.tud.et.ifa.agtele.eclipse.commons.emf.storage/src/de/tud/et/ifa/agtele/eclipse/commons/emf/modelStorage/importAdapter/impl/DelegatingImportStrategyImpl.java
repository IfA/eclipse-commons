package de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.importAdapter.impl;

import de.tud.et.ifa.agtele.emf.importing.IDelegatingModelImportStrategy;
import de.tud.et.ifa.agtele.emf.importing.IModelImportStrategy;

public abstract class DelegatingImportStrategyImpl implements IDelegatingModelImportStrategy {

	protected IModelImportStrategy delegate = null;
	
	@Override
	public IModelImportStrategy getImportStrategyDelegate() {		
		return this.delegate;
	}

	@Override
	public void setImportStrategyDelegate(IModelImportStrategy delegate) {
		this.delegate = delegate;
	}

}
