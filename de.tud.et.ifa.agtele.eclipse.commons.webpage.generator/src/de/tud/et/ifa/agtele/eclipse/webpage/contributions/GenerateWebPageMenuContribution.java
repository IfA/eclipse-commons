package de.tud.et.ifa.agtele.eclipse.webpage.contributions;

import org.eclipse.swt.SWT;
import org.eclipse.ui.menus.CommandContributionItem;
import org.eclipse.ui.menus.CommandContributionItemParameter;
import org.eclipse.ui.menus.ExtensionContributionFactory;
import org.eclipse.ui.menus.IContributionRoot;
import org.eclipse.ui.services.IServiceLocator;

import de.tud.et.ifa.agtele.resources.BundleContentHelper;

public class GenerateWebPageMenuContribution extends ExtensionContributionFactory {

	private static final String BUNDLE_ID = "de.tud.et.ifa.agtele.eclipse.commons.webpage.generator";
	
	@Override
	public void createContributionItems(IServiceLocator serviceLocator, IContributionRoot additions) {
		// This represents the 'Download File' menu entry
		//
		CommandContributionItemParameter downloadAASFileCommandParameter = new CommandContributionItemParameter(
				serviceLocator, "", "de.tud.et.ifa.agtele.eclipse.commons.webpage.generator.GenerateWebPageCommand", SWT.PUSH);
		downloadAASFileCommandParameter.label = "Download File";
		downloadAASFileCommandParameter.icon = BundleContentHelper
				.getBundleImageDescriptor(GenerateWebPageMenuContribution.BUNDLE_ID, "icons/generate.gif");
		downloadAASFileCommandParameter.tooltip = "Generate the static web page.";
		
		//TODO only make the command visible, if a File (FSElement) or a collection of thereof is selected
		downloadAASFileCommandParameter.visibleEnabled = false;

		// Add the menu entries to the menu
		//
		additions.addContributionItem(new CommandContributionItem(downloadAASFileCommandParameter), null);
	}

}
