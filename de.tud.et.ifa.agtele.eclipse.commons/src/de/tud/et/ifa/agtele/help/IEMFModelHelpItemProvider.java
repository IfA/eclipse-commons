package de.tud.et.ifa.agtele.help;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;

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
	public String render(HelpItemDescription helpItemDescription);

	/**
	 * Returns all relevant {@link HelpItemDescription help data} of an {@link EObject} by
	 * gathering {@link HelpItemData} for the {@link EClass},
	 * {@link EAttribute EAttributes} and {@link EReference non-containtment and
	 * containment References} and their possible {@link EClass child elements}
	 * 
	 * @param eObject
	 * @return all relevant {@link HelpItemDescription}
	 */
	public HelpItemDescription getHelpItemDescription(EObject eObject);

}
