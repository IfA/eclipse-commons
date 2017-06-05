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
public class PushCodeToEcoreMenuContribution extends ExtensionContributionFactory {

	private static final String BUNDLE_ID = "de.tud.et.ifa.agtele.eclipse.commons.ui";

	@Override
	public void createContributionItems(IServiceLocator serviceLocator, IContributionRoot additions) {

		// This represents the 'Add Validation EOperation' menu entry
		//
		CommandContributionItemParameter pushCodeToEcoreCommandParameter = new CommandContributionItemParameter(
				serviceLocator, "", "de.tud.et.ifa.agtele.ui.commands.PushCodeToEcoreHandler", SWT.PUSH);
		pushCodeToEcoreCommandParameter.label = "Push Code To Ecore";
		pushCodeToEcoreCommandParameter.icon = BundleContentHelper
				.getBundleImageDescriptor(PushCodeToEcoreMenuContribution.BUNDLE_ID, "icons/PushToEcore.gif");
		pushCodeToEcoreCommandParameter.tooltip = "Pushes the code section of this Java EMF Code to the corresponding Ecore model, if possible.";
		pushCodeToEcoreCommandParameter.visibleEnabled = true;

		// Add the menu entries to the menu
		//
		additions.addContributionItem(new CommandContributionItem(pushCodeToEcoreCommandParameter), null);
	}

}
