package de.tud.et.ifa.agtele.ui.contributions;

import org.eclipse.swt.SWT;
import org.eclipse.ui.menus.CommandContributionItem;
import org.eclipse.ui.menus.CommandContributionItemParameter;
import org.eclipse.ui.menus.ExtensionContributionFactory;
import org.eclipse.ui.menus.IContributionRoot;
import org.eclipse.ui.services.IServiceLocator;

import de.tud.et.ifa.agtele.resources.BundleContentHelper;

/**
 * Menu contribution that adds the 'add documentation annotation' menu
 * contribution
 *
 * @author baron
 */
public class AddDocumentationAnnotationMenuContribution extends ExtensionContributionFactory {

	private static final String BUNDLE_ID = "de.tud.et.ifa.agtele.eclipse.commons.ui";

	@Override
	public void createContributionItems(IServiceLocator serviceLocator, IContributionRoot additions) {

		// This represents the 'Add Documentation Annotation' menu entry
		//
		CommandContributionItemParameter addDocumentationAnnotationCommandParameter = new CommandContributionItemParameter(
				serviceLocator, "", "de.tud.et.ifa.agtele.ui.commands.AddDocumentationAnnotation", SWT.PUSH);
		addDocumentationAnnotationCommandParameter.label = "Add Documentation Annotation";
		addDocumentationAnnotationCommandParameter.icon = BundleContentHelper
				.getBundleImageDescriptor(AddDocumentationAnnotationMenuContribution.BUNDLE_ID,
						"icons/CreateDocumentationAnnotation.gif");
		addDocumentationAnnotationCommandParameter.tooltip = "Adds a new documentation annotation to the selected element(s)";
		addDocumentationAnnotationCommandParameter.visibleEnabled = true;

		// Add the menu entries to the menu
		//
		additions.addContributionItem(new CommandContributionItem(addDocumentationAnnotationCommandParameter), null);
	}

}
