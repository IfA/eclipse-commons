package de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.util;

import java.util.ArrayList;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.ItemProviderAdapter;

import de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.importAdapter.ImportAdapter;
import de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.importAdapter.impl.DelegatingImportStrategyImpl;
import de.tud.et.ifa.agtele.emf.AgteleEcoreUtil;
import de.tud.et.ifa.agtele.emf.edit.IReferencingIdentificationStringProvider;
import de.tud.et.ifa.agtele.emf.importing.IAdapterFactoryDelegatingModelImportStrategy;
import de.tud.et.ifa.agtele.emf.importing.IDelegatingModelImportStrategy;
import de.tud.et.ifa.agtele.emf.importing.IModelConnector;
import de.tud.et.ifa.agtele.emf.importing.IModelImportStrategy;
import de.tud.et.ifa.agtele.emf.importing.IModelImporter;

public class IdentifierRegistrationDelegatingImportStrategy extends DelegatingImportStrategyImpl  implements IAdapterFactoryDelegatingModelImportStrategy {

	@Override
	public IDelegatingModelImportStrategy wrap(IModelImportStrategy delegate) {
		IdentifierRegistrationDelegatingImportStrategy strategy = new IdentifierRegistrationDelegatingImportStrategy();
		strategy.setImportStrategyDelegate(delegate);		
		return strategy;
	}

	@Override
	public void postImport(IModelImporter adapter, IModelConnector connector, EObject eObject) {
		Adapter itemProviderAdapter = null;
		try {
			itemProviderAdapter = AgteleEcoreUtil.getAdapter(eObject, IEditingDomainItemProvider.class);
			if (itemProviderAdapter == null || !(itemProviderAdapter instanceof ItemProviderAdapter)) {
				itemProviderAdapter = AgteleEcoreUtil.getAdapter(eObject, ItemProviderAdapter.class);
			}
		} catch (Exception e) {
			//Do nothing
		}
		
		if (((ImportAdapter)adapter).getModel() != null &&
				itemProviderAdapter != null && 
				itemProviderAdapter instanceof IReferencingIdentificationStringProvider) {
			ArrayList<String> identifiers = new ArrayList<>(((IReferencingIdentificationStringProvider)itemProviderAdapter).getReferencingIdentificationStrings(eObject));
			
			for (String id : new ArrayList<>(identifiers)) {
				if (IReferencingIdentificationStringProvider.hasUriPrefix(id)) {
					identifiers.add(IReferencingIdentificationStringProvider.removeUriPrefix(id));
				}
			}
						
			((ImportAdapter)adapter).getModel().registerIdentifyableElement(identifiers, eObject);				
		}
		
		super.postImport(adapter, connector, eObject);
	}
}
