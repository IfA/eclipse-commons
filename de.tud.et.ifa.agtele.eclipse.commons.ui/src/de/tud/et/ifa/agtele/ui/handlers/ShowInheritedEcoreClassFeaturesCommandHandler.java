package de.tud.et.ifa.agtele.ui.handlers;

import java.util.Map;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.Command;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.commands.ICommandService;
import org.eclipse.ui.commands.IElementUpdater;
import org.eclipse.ui.menus.UIElement;

import de.tud.et.ifa.agtele.ui.AgteleUIPlugin;
import de.tud.et.ifa.agtele.ui.providers.AgteleEcoreContentProvider;

public class ShowInheritedEcoreClassFeaturesCommandHandler extends AbstractHandler implements IElementUpdater {

	protected static IPreferenceStore store = AgteleUIPlugin.getPlugin().getPreferenceStore();

	public static final String VISIBILITY_SETTING_KEY = "AGTELE_ECORE_EDITOR_SHOW_INHERITED_CLASS_FEATURES";

	protected static boolean checked = ShowInheritedEcoreClassFeaturesCommandHandler.getInitialVisibility();

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		Command command = event.getCommand();

		ShowInheritedEcoreClassFeaturesCommandHandler.checked = !ShowInheritedEcoreClassFeaturesCommandHandler.checked;

		ShowInheritedEcoreClassFeaturesCommandHandler.store.setValue(
				ShowInheritedEcoreClassFeaturesCommandHandler.VISIBILITY_SETTING_KEY,
				ShowInheritedEcoreClassFeaturesCommandHandler.checked);

		AgteleEcoreContentProvider.setInheritedContentVisibility(ShowInheritedEcoreClassFeaturesCommandHandler.checked);

		return null;
	}

	public static void updateMenus() {
		PlatformUI.getWorkbench().getService(ICommandService.class).refreshElements(
				"de.tud.et.ifa.agtele.eclipse.commons.ui.showInheritedEcoreClassFeaturesCommand", null);
	}

	@Override
	public void updateElement(UIElement element, Map parameters) {
		element.setChecked(ShowInheritedEcoreClassFeaturesCommandHandler.checked);
	}

	protected static boolean getInitialVisibility() {
		return ShowInheritedEcoreClassFeaturesCommandHandler.store
				.getBoolean(ShowInheritedEcoreClassFeaturesCommandHandler.VISIBILITY_SETTING_KEY);
	}

	public static boolean isVisible() {
		ShowInheritedEcoreClassFeaturesCommandHandler.updateMenus();
		return ShowInheritedEcoreClassFeaturesCommandHandler.checked;
	}
}
