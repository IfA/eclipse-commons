package de.tud.et.ifa.agtele.eclipse.webpage.util;

import org.eclipse.emf.common.notify.AdapterFactory;

import de.tud.et.ifa.agtele.emf.edit.CommonItemProviderAdapter;
import de.tud.et.ifa.agtele.emf.edit.IDragAndDropProvider;
import de.tud.et.ifa.agtele.emf.edit.IRequireRelatedModelUpdateProvider;
import de.tud.et.ifa.agtele.help.IEMFModelHelpItemProvider;

public class WebPageItemProviderAdapter extends CommonItemProviderAdapter
	implements IEMFModelHelpItemProvider, IDragAndDropProvider, IRequireRelatedModelUpdateProvider {

	public WebPageItemProviderAdapter(AdapterFactory adapterFactory) {
		super(adapterFactory);
	}
}
