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
package de.tud.et.ifa.agtele.ui.handlers;

import java.util.Map;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.Command;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.commands.ICommandService;
import org.eclipse.ui.commands.IElementUpdater;
import org.eclipse.ui.menus.UIElement;

import de.tud.et.ifa.agtele.ui.AgteleUIPlugin;
import de.tud.et.ifa.agtele.ui.editors.AgteleEcoreEditor;
import de.tud.et.ifa.agtele.ui.providers.AgteleEcoreContentProvider;

/**
 * This handler changes the visibility of generic features of an {@link EClass}
 * in the {@link AgteleEcoreEditor} by calling
 * {@link AgteleEcoreContentProvider#setGenericContentVisibility(boolean)}.
 * This handler also stores the last setting in the {@link IPreferenceStore} of
 * the {@link AgteleUIPlugin} by use of the {@link #VISIBILITY_SETTING_KEY}.
 * 
 * @author Baron
 *
 */
public class ShowGenericEcoreFeaturesCommandHandler extends AbstractHandler implements IElementUpdater {

	/**
	 * The {@link IPreferenceStore} of the {@link AgteleUIPlugin}.
	 */
	protected static IPreferenceStore store = AgteleUIPlugin.getPlugin().getPreferenceStore();

	/**
	 * The settings key for the {@link IPreferenceStore}.
	 */
	public static final String VISIBILITY_SETTING_KEY = "AGTELE_ECORE_EDITOR_SHOW_GENERIC_FEATURES";

	/**
	 * Whether the command buttons shall be checked at the moment -> inherited
	 * content is visible.
	 */
	protected static boolean checked = ShowGenericEcoreFeaturesCommandHandler.getInitialVisibility();

	/**
	 * Changes the inherited content visibility
	 * ({@link AgteleEcoreContentProvider#setInheritedContentVisibility(boolean)})
	 * and updates all menu contributions using this {@link AbstractHandler}.
	 *
	 * Writes the initial visibility as {@link #VISIBILITY_SETTING_KEY} to the
	 * {@link IPreferenceStore} of the {@link AgteleUIPlugin}.
	 */
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		Command command = event.getCommand();

		ShowGenericEcoreFeaturesCommandHandler.checked = !ShowGenericEcoreFeaturesCommandHandler.checked;

		ShowGenericEcoreFeaturesCommandHandler.store.setValue(
				ShowGenericEcoreFeaturesCommandHandler.VISIBILITY_SETTING_KEY,
				ShowGenericEcoreFeaturesCommandHandler.checked);

		AgteleEcoreContentProvider.setGenericContentVisibility(ShowGenericEcoreFeaturesCommandHandler.checked);

		return null;
	}

	/**
	 * Updates the menu contributions using this {@link AbstractHandler} via the
	 * {@link ICommandService}.
	 */
	public static void updateMenus() {
		PlatformUI.getWorkbench().getService(ICommandService.class).refreshElements(
				"de.tud.et.ifa.agtele.eclipse.commons.ui.showGenericEcoreFeaturesCommand", null);
	}

	@Override
	public void updateElement(UIElement element, Map parameters) {
		element.setChecked(ShowGenericEcoreFeaturesCommandHandler.checked);
	}

	/**
	 * Reads the persisted initial visibility by reading the
	 * {@link #VISIBILITY_SETTING_KEY} from the {@link IPreferenceStore} of the
	 * {@link AgteleUIPlugin}.
	 * 
	 * @return
	 */
	protected static boolean getInitialVisibility() {
		return ShowGenericEcoreFeaturesCommandHandler.store
				.getBoolean(ShowGenericEcoreFeaturesCommandHandler.VISIBILITY_SETTING_KEY);
	}

	/**
	 * Reads the current visibility from the
	 * {@link ShowGenericEcoreFeaturesCommandHandler#checked} flag.
	 * 
	 * @return
	 */
	public static boolean isVisible() {
		ShowGenericEcoreFeaturesCommandHandler.updateMenus();
		return ShowGenericEcoreFeaturesCommandHandler.checked;
	}
}
