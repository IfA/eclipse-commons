/*******************************************************************************
 * Copyright (C) 2016-2018 Institute of Automation, TU Dresden.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Institute of Automation, TU Dresden - initial API and implementation
 ******************************************************************************/
package de.tud.et.ifa.agtele.ui.contributions;

import org.eclipse.jface.action.MenuManager;
import org.eclipse.swt.SWT;
import org.eclipse.ui.menus.CommandContributionItem;
import org.eclipse.ui.menus.CommandContributionItemParameter;
import org.eclipse.ui.menus.ExtensionContributionFactory;
import org.eclipse.ui.menus.IContributionRoot;
import org.eclipse.ui.services.IServiceLocator;

import de.tud.et.ifa.agtele.resources.BundleContentHelper;
import de.tud.et.ifa.agtele.ui.handlers.OpenCodeHandler;

/**
 * An {@link ExtensionContributionFactory} that is responsible for creating the
 * 'Open Code' menu as well as the menu entries for the various
 * {@link OpenCodeHandler OpenCodeHandlers}.
 *
 * @author mfreund
 */
public class OpenCodeMenuContribution extends ExtensionContributionFactory {

	private static final String BUNDLE_ID = "de.tud.et.ifa.agtele.eclipse.commons.ui";

	@Override
	public void createContributionItems(IServiceLocator serviceLocator, IContributionRoot additions) {

		// This represents the Menu that will hold the various 'Open ... Code'
		// menu entries
		//
		MenuManager menuManager = new MenuManager("Open Generated Code");
		menuManager.setImageDescriptor(BundleContentHelper.getBundleImageDescriptor(OpenCodeMenuContribution.BUNDLE_ID,
				"icons/JavaClassFile.gif"));
		additions.addContributionItem(menuManager, null);

		// This represents the 'Open Model Code (Interface)' menu entry
		//
		CommandContributionItemParameter openInterfaceModelCodeCommandParameter = new CommandContributionItemParameter(
				serviceLocator, "", "de.tud.et.ifa.agtele.ui.commands.OpenInterfaceModelCode", SWT.PUSH);
		openInterfaceModelCodeCommandParameter.label = "Open Model Code (Interface)";
		openInterfaceModelCodeCommandParameter.tooltip = "Jumps to the Java class generated for the selected element";
		openInterfaceModelCodeCommandParameter.visibleEnabled = true;

		// This represents the 'Open Model Code (Impl)' menu entry
		//
		CommandContributionItemParameter openImplModelCodeCommandParameter = new CommandContributionItemParameter(
				serviceLocator, "", "de.tud.et.ifa.agtele.ui.commands.OpenImplModelCode", SWT.PUSH);
		openImplModelCodeCommandParameter.label = "Open Model Code (Impl)";
		openImplModelCodeCommandParameter.tooltip = "Jumps to the Java class generated for the selected element";
		openImplModelCodeCommandParameter.visibleEnabled = true;

		// This represents the 'Open Edit Code' menu entry
		//
		CommandContributionItemParameter openEditCodeCommandParameter = new CommandContributionItemParameter(
				serviceLocator, "", "de.tud.et.ifa.agtele.ui.commands.OpenEditCode", SWT.PUSH);
		openEditCodeCommandParameter.label = "Open Edit Code";
		openEditCodeCommandParameter.tooltip = "Jumps to the Java class generated for the selected element";
		openEditCodeCommandParameter.visibleEnabled = true;

		// This represents the 'Open Editor Code' menu entry
		//
		CommandContributionItemParameter openEditorCodeCommandParameter = new CommandContributionItemParameter(
				serviceLocator, "", "de.tud.et.ifa.agtele.ui.commands.OpenEditorCode", SWT.PUSH);
		openEditorCodeCommandParameter.label = "Open Editor Code";
		openEditorCodeCommandParameter.tooltip = "Jumps to the Java class generated for the selected element";
		openEditorCodeCommandParameter.visibleEnabled = true;

		// This represents the 'Open Factory Code' menu entry
		//
		CommandContributionItemParameter openFactoryCodeCommandParameter = new CommandContributionItemParameter(
				serviceLocator, "", "de.tud.et.ifa.agtele.ui.commands.OpenFactoryCode", SWT.PUSH);
		openFactoryCodeCommandParameter.label = "Open Factory Code";
		openFactoryCodeCommandParameter.tooltip = "Jumps to the Java class generated for the selected element";
		openFactoryCodeCommandParameter.visibleEnabled = true;

		// Add the menu entries to the menu
		//
		menuManager.add(new CommandContributionItem(openInterfaceModelCodeCommandParameter));
		menuManager.add(new CommandContributionItem(openImplModelCodeCommandParameter));
		menuManager.add(new CommandContributionItem(openEditCodeCommandParameter));
		menuManager.add(new CommandContributionItem(openEditorCodeCommandParameter));
		menuManager.add(new CommandContributionItem(openFactoryCodeCommandParameter));

	}

}
