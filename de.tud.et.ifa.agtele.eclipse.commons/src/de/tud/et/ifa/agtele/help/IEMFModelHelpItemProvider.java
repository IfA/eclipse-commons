package de.tud.et.ifa.agtele.help;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.command.CommandParameter;
import org.eclipse.emf.edit.provider.AdapterFactoryItemDelegator;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;

import de.tud.et.ifa.agtele.emf.AgteleEcoreUtil;

/**
 * This interface may be implemented to provide a help text.
 * 
 * @author martin
 *
 */
public interface IEMFModelHelpItemProvider {

	/**
	 * This method renders the HTML that may be used for the user help. This
	 * default implementation only lists all properties separated by line
	 * breaks.
	 * 
	 * @param helpItemDescription
	 *            contains the {@link HelpItemDescription properties} that are
	 *            relevant for the rendering of a help page
	 * @return the help text to be displayed (as HTML)
	 */
	default public String render(HelpItemDescription helpItemDescription) {
		return helpItemDescription.toString();
	}

	/**
	 * Returns all relevant {@link HelpItemDescription help data} of an
	 * {@link EObject} by gathering {@link HelpItemData} for the {@link EClass},
	 * {@link EAttribute EAttributes}, {@link EReference containment References}
	 * including their possible {@link EClass child elements}, and
	 * {@link EReference non-containment References} including their possible
	 * targets in the model that the eObject originates from.
	 * 
	 * @param eObject
	 * @return all relevant {@link HelpItemDescription}
	 */
	@SuppressWarnings("unchecked")
	default public HelpItemDescription getHelpItemDescription(EObject eObject) {
		HelpItemDescription helpItemDescription = new HelpItemDescription(eObject);

		/**
		 * Generates the Documentation of the properties of a given
		 * {@link EObject}
		 */
		helpItemDescription.setEClassDescription(new EClassHelpItemData(eObject.eClass()));

		if (AgteleEcoreUtil.getAdapterFactoryItemDelegatorFor(eObject) != null) {
			AdapterFactoryItemDelegator afid = AgteleEcoreUtil.getAdapterFactoryItemDelegatorFor(eObject);
			/**
			 * Generates the Documentation of the {@link EReference Containment
			 * References} and the possible Children of a given {@link EObject}
			 */
			List<IItemPropertyDescriptor> propertyDescriptors = afid.getPropertyDescriptors(eObject);

			for (IItemPropertyDescriptor itemPropertyDescriptor : propertyDescriptors) {
				// EAttribute
				if (itemPropertyDescriptor.getFeature(null) instanceof EAttribute) {
					EAttribute attr = (EAttribute) itemPropertyDescriptor.getFeature(null);

					helpItemDescription.addAttributeDescription(new EAttributeHelpItemData(attr));
				}
				// Non-Containment References
				else if (itemPropertyDescriptor.getFeature(null) instanceof EReference) {
					EReference ncRef = (EReference) itemPropertyDescriptor.getFeature(null);
					// display Non-Containment References only if a type is
					// bound to it
					if (ncRef.getEGenericType().getEClassifier() != null) {
						helpItemDescription.addNonContainmentReferenceDescription(new EReferenceHelpItemData(ncRef,
								(List<EObject>) itemPropertyDescriptor.getChoiceOfValues(eObject)));
					}
				}
			}
			// Containment References
			for (Object cd : afid.getNewChildDescriptors(eObject, AgteleEcoreUtil.getEditingDomainFor(eObject), null)) {
				if (cd instanceof CommandParameter) {
					// the reference and the target type from the command
					EStructuralFeature feature = ((CommandParameter) cd).getEStructuralFeature();
					EClass target = ((CommandParameter) cd).getEValue().eClass();
					// check if a description for this feature already exists
					List<EReferenceHelpItemData> matchingDesc = helpItemDescription.getContainmentReferenceDescription()
							.stream().filter(cRD -> cRD.getEReference().equals((EReference) feature))
							.collect(Collectors.toList());
					// if it doesn't create a new description for it
					if (matchingDesc.isEmpty()) {
						helpItemDescription.addContainmentReferenceDescription(
								new EReferenceHelpItemData((EReference) feature, Arrays.asList(target)));
					} else {
						// if it does add the child element to it
						// therefore remove the old version of it first
						helpItemDescription.removeContainmentReferenceDescription(matchingDesc.get(0));
						// add a child description
						matchingDesc.get(0).addChildData(new EClassHelpItemData(target));
						// and add it again
						helpItemDescription.addContainmentReferenceDescription(matchingDesc.get(0));
					}
				}
			}
		}
		return helpItemDescription;
	}
}
