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
 * An {@link ExtensionContributionFactory} that is responsible for creating the
 * 'Open Metamodel' menu as well as the menu entries for the
 * {@link OpenMetamodelHandler}.
 *
 * @author mfreund
 */
public class OpenMetaModelContribution extends ExtensionContributionFactory {

	private static final String BUNDLE_ID = "de.tud.et.ifa.agtele.eclipse.commons.ui";

	@Override
	public void createContributionItems(IServiceLocator serviceLocator, IContributionRoot additions) {

		// This represents the 'Open Metamodel' menu entry
		//
		CommandContributionItemParameter openMetamodelCommandParameter = new CommandContributionItemParameter(
				serviceLocator, "", "de.tud.et.ifa.agtele.ui.commands.OpenMetamodel", SWT.PUSH);
		openMetamodelCommandParameter.label = "Open Metamodel";
		openMetamodelCommandParameter.icon = BundleContentHelper
				.getBundleImageDescriptor(OpenMetaModelContribution.BUNDLE_ID, "icons/EcoreModel.gif");
		openMetamodelCommandParameter.tooltip = "Jumps to the Metamodel element based on which the selection was generated";
		openMetamodelCommandParameter.visibleEnabled = true;

		// Add the menu entries to the menu
		//
		additions.addContributionItem(new CommandContributionItem(openMetamodelCommandParameter), null);

	}

}
