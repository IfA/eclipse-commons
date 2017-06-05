package de.tud.et.ifa.agtele.ui.contributions;

import org.eclipse.swt.SWT;
import org.eclipse.ui.menus.CommandContributionItem;
import org.eclipse.ui.menus.CommandContributionItemParameter;
import org.eclipse.ui.menus.ExtensionContributionFactory;
import org.eclipse.ui.menus.IContributionRoot;
import org.eclipse.ui.services.IServiceLocator;

import de.tud.et.ifa.agtele.resources.BundleContentHelper;
import de.tud.et.ifa.agtele.ui.handlers.OpenMetamodelHandler;

/**
 * Menu contribution that adds the 'add validation operation' menu contribution
 *
 * @author baron
 */
public class AddValidationEOperationMenuContribution extends ExtensionContributionFactory {

	private static final String BUNDLE_ID = "de.tud.et.ifa.agtele.eclipse.commons.ui";

	@Override
	public void createContributionItems(IServiceLocator serviceLocator, IContributionRoot additions) {

		// This represents the 'Add Validation EOperation' menu entry
		//
		CommandContributionItemParameter addValidationEOperationCommandParameter = new CommandContributionItemParameter(
				serviceLocator, "", "de.tud.et.ifa.agtele.ui.commands.AddValidationEOperation", SWT.PUSH);
		addValidationEOperationCommandParameter.label = "Add Validation EOperation";
		addValidationEOperationCommandParameter.icon = BundleContentHelper
				.getBundleImageDescriptor(AddValidationEOperationMenuContribution.BUNDLE_ID, "icons/AddValidationEOperation.gif");
		addValidationEOperationCommandParameter.tooltip = "Adds a new validation EOperation to the containing EClass";
		addValidationEOperationCommandParameter.visibleEnabled = true;

		// Add the menu entries to the menu
		//
		additions.addContributionItem(new CommandContributionItem(addValidationEOperationCommandParameter), null);
	}

}
