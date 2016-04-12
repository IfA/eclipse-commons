package de.tud.et.ifa.agtele.emf.edit;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.edit.provider.ItemProviderAdapter;

/**
 * A special {@link ItemProviderAdapter} that encapsulates common functionalities.
 * <p />
 * In order to make use of it, the root item provider of your generated model must extend this instead
 * of the default '<em>ItemProviderAdapter</em>'.
 * 
 * @author mfreund
 */
public class CommonItemProviderAdapter extends ItemProviderAdapter {

	public CommonItemProviderAdapter(AdapterFactory adapterFactory) {
		super(adapterFactory);
	}

}
