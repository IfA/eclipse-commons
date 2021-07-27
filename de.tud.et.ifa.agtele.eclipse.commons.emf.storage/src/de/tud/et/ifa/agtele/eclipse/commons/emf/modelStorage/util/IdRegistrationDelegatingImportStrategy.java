package de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.util;

import java.util.Collections;

import org.eclipse.emf.ecore.EObject;

import de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.importAdapter.ImportAdapter;
import de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.importAdapter.impl.DelegatingImportStrategyImpl;
import de.tud.et.ifa.agtele.emf.importing.IDelegatingModelImportStrategy;
import de.tud.et.ifa.agtele.emf.importing.IModelConnector;
import de.tud.et.ifa.agtele.emf.importing.IModelImportStrategy;
import de.tud.et.ifa.agtele.emf.importing.IModelImporter;

public class IdRegistrationDelegatingImportStrategy extends DelegatingImportStrategyImpl {

	@Override
	public IDelegatingModelImportStrategy wrap(IModelImportStrategy delegate) {
		IdRegistrationDelegatingImportStrategy strategy = new IdRegistrationDelegatingImportStrategy();
		strategy.setImportStrategyDelegate(delegate);		
		return strategy;
	}

	@Override
	public void postImport(IModelImporter adapter, IModelConnector connector, EObject eObject) {
		if (eObject.eClass().getEIDAttribute() != null) {
			Object value = eObject.eGet(eObject.eClass().getEIDAttribute());
			if (value instanceof String) {
				((ImportAdapter)adapter).getModel().registerIdentifyableElement(Collections.singleton((String)value), eObject);				
			}
		}		
		super.postImport(adapter, connector, eObject);
	}
}
