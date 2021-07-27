package de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.util;

import org.eclipse.emf.common.notify.AdapterFactory;

import de.tud.et.ifa.agtele.emf.edit.CommonItemProviderAdapter;
import de.tud.et.ifa.agtele.emf.edit.IDragAndDropProvider;
import de.tud.et.ifa.agtele.emf.edit.IRequireRelatedModelUpdateProvider;
import de.tud.et.ifa.agtele.help.IEMFModelHelpItemProvider;

public class ModelStorageItemProviderAdapter extends CommonItemProviderAdapter
	implements IEMFModelHelpItemProvider, IDragAndDropProvider, IRequireRelatedModelUpdateProvider {

	public ModelStorageItemProviderAdapter(AdapterFactory adapterFactory) {
		super(adapterFactory);
	}
}
