package de.tud.et.ifa.agtele.ui.providers;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;
import org.eclipse.ui.views.properties.IPropertySource;

public class AgteleContentProvider extends AdapterFactoryContentProvider {

	public AgteleContentProvider(AdapterFactory adapterFactory) {
		super(adapterFactory);
	}
	
	
	@Override
	protected IPropertySource createPropertySource(Object object, IItemPropertySource itemPropertySource) {
		return new AgtelePropertySource(object, itemPropertySource);
	}

}
