/**
 * 
 */
package de.tud.et.ifa.agtele.emf;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.domain.IEditingDomainProvider;
import org.eclipse.emf.edit.provider.AdapterFactoryItemDelegator;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;

/**
 * This class contains convenient static methods for working with EMF objects.
 * 
 * @author AG Tele
 *
 */
public class AgteleEcoreUtil {

	/**
	 * Returns the {@link EditingDomain} of an {@link EObject} or null if none
	 * can be found
	 * 
	 * @param object
	 * @return The {@link EditingDomain} or null
	 */
	public static EditingDomain getEditingDomainFor(EObject object) {

		IEditingDomainProvider editingDomainProvider =

				(IEditingDomainProvider) EcoreUtil.getExistingAdapter(object, IEditingDomainProvider.class);
		if (editingDomainProvider != null) {
			return editingDomainProvider.getEditingDomain();
		} else if (object.eResource() != null) {
			ResourceSet resourceSet = object.eResource().getResourceSet();
			if (resourceSet instanceof IEditingDomainProvider) {
				EditingDomain editingDomain = ((IEditingDomainProvider) resourceSet).getEditingDomain();
				return editingDomain;
			} else if (resourceSet != null) {
				editingDomainProvider = (IEditingDomainProvider) EcoreUtil.getExistingAdapter(resourceSet,
						IEditingDomainProvider.class);
				if (editingDomainProvider != null) {
					return editingDomainProvider.getEditingDomain();
				}
			}
		}
		return null;
	}

	/**
	 * Returns the {@link AdapterFactoryItemDelegator} of an {@link EObject} or
	 * null if none can be found
	 * 
	 * @param object
	 * @return The {@link AdapterFactoryItemDelegator} or null
	 */
	public static AdapterFactoryItemDelegator getAdapterFactoryItemDelegatorFor(EObject object) {
		// get all property descriptors for the current eObject and
		// therefore do some weird voodoo stuff according to
		// https://www.eclipse.org/forums/index.php/t/162266/
		if (getEditingDomainFor(object) instanceof AdapterFactoryEditingDomain) {
			AdapterFactoryEditingDomain afed = (AdapterFactoryEditingDomain) getEditingDomainFor(object);
			if (afed.getAdapterFactory().isFactoryForType(object)) {
				return new AdapterFactoryItemDelegator(
						((ComposedAdapterFactory) afed.getAdapterFactory()).getRootAdapterFactory());
			}
		}
		return null;
	}

}
