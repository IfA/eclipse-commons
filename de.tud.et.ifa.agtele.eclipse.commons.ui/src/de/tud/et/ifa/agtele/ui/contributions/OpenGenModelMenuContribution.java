package de.tud.et.ifa.agtele.ui.contributions;

import org.eclipse.swt.SWT;
import org.eclipse.ui.menus.CommandContributionItem;
import org.eclipse.ui.menus.CommandContributionItemParameter;
import org.eclipse.ui.menus.ExtensionContributionFactory;
import org.eclipse.ui.menus.IContributionRoot;
import org.eclipse.ui.services.IServiceLocator;

import de.tud.et.ifa.agtele.resources.BundleContentHelper;
import de.tud.et.ifa.agtele.ui.handlers.OpenGenModelHandler;

/**
 * An {@link ExtensionContributionFactory} that is responsible for creating the
 * 'Open GenModel' menu entry for the {@link OpenGenModelHandler}.
 *
 * @author mfreund
 */
public class OpenGenModelMenuContribution extends ExtensionContributionFactory {

	private static final String BUNDLE_ID = "de.tud.et.ifa.agtele.eclipse.commons.ui";

	@Override
	public void createContributionItems(IServiceLocator serviceLocator, IContributionRoot additions) {

		// This represents the 'Open GenModel' menu entry
		//
		CommandContributionItemParameter openGenModelCommandParameter = new CommandContributionItemParameter(
				serviceLocator, "", "de.tud.et.ifa.agtele.ui.commands.OpenGenModel", SWT.PUSH);
		openGenModelCommandParameter.label = "Open GenModel";
		openGenModelCommandParameter.icon = BundleContentHelper
				.getBundleImageDescriptor(OpenGenModelMenuContribution.BUNDLE_ID, "icons/GenModelModelFile.gif");
		openGenModelCommandParameter.tooltip = "Jumps to the corresponding element in the GenModel associated with this Ecore model";
		openGenModelCommandParameter.visibleEnabled = true;

		// Add the menu entries to the menu
		//
		additions.addContributionItem(new CommandContributionItem(openGenModelCommandParameter), null);

	}

}
