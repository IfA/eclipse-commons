package de.tud.et.ifa.agtele.emf.edit;

import java.util.Collection;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.edit.domain.EditingDomain;
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

	@Override
	protected Command createDragAndDropCommand(EditingDomain domain, Object owner, float location, int operations,
			int operation, Collection<?> collection) {
		
		// If possible use the special functionality provided by the 'IDragAndDropProvider' interface...
		if(this instanceof IDragAndDropProvider) {
			return ((IDragAndDropProvider) this).createCustomDragAndDropCommand(domain, owner, location, operations, operation, collection, 
					((IDragAndDropProvider) this).getCommandSelectionStrategy());
		} else {
			return super.createDragAndDropCommand(domain, owner, location, operations, operation, collection);
		}
	}
	
}
