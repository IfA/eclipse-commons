package de.tud.et.ifa.agtele.ui.contributions;

import org.eclipse.jface.action.Separator;
import org.eclipse.ui.menus.ExtensionContributionFactory;
import org.eclipse.ui.menus.IContributionRoot;
import org.eclipse.ui.services.IServiceLocator;

/**
 * An {@link ExtensionContributionFactory} that uses the
 * {@link OpenGenModelMenuContribution} and the {@link OpenCodeMenuContribution}
 * to assemble the menu contribution for an Ecore model editor.
 *
 * @author mfreund
 */
public class EcoreEditorMenuContribution extends ExtensionContributionFactory {

	@Override
	public void createContributionItems(IServiceLocator serviceLocator, IContributionRoot additions) {

		additions.addContributionItem(new Separator("begin-agtele-ecore-additions"), null);
		new OpenGenModelMenuContribution().createContributionItems(serviceLocator, additions);
		new OpenCodeMenuContribution().createContributionItems(serviceLocator, additions);
		new AddValidationEOperationMenuContribution().createContributionItems(serviceLocator, additions);
		new AddDocumentationAnnotationMenuContribution().createContributionItems(serviceLocator, additions);
		additions.addContributionItem(new Separator("end-agtele-ecore-additions"), null);
	}

}
