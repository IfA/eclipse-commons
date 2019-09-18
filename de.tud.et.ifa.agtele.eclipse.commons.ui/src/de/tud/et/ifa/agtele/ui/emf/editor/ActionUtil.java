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
package de.tud.et.ifa.agtele.ui.emf.editor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.command.CommandParameter;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.ui.action.CreateChildAction;
import org.eclipse.emf.edit.ui.action.CreateSiblingAction;
import org.eclipse.emf.edit.ui.action.EditingDomainActionBarContributor;
import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.SubMenuManager;
import org.eclipse.jface.viewers.ContentViewer;
import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IEditorActionBarContributor;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorSite;

import de.tud.et.ifa.agtele.emf.AgteleEcoreUtil;
import de.tud.et.ifa.agtele.ui.interfaces.IFeatureValidator;
import de.tud.et.ifa.agtele.ui.util.UIHelper;

/**
 * A helper class that provides utility functions that are related to
 * {@link IAction IActions} that can be executed in an editor.
 *
 * @author mfreund
 */
public class ActionUtil {

	private ActionUtil() {
		// Do not allow to create instances
		//
	}

	/**
	 * For the given {@link ContentViewer viewer} and its current selection,
	 * this returns all available {@link CreateChildAction CreateChildActions}.
	 *
	 * @param viewer
	 *            The {@link ContentViewer} for which the available
	 *            {@link CreateChildAction CreateChildActions} shall be
	 *            returned.
	 * @return The list of {@link CreateChildAction CreateChildActions}.
	 */
	public static List<CreateChildAction> getCreateChildActions(ContentViewer viewer) {

		return viewer.getSelection() instanceof StructuredSelection
				? ActionUtil.getCreateChildActions(viewer, (StructuredSelection) viewer.getSelection())
				: new ArrayList<>();

	}

	/**
	 * For the given {@link ContentViewer viewer} and the given
	 * {@link StructuredSelection}, this returns all available
	 * {@link CreateChildAction CreateChildActions}.
	 * <p />
	 * Note: This will not actually change the selection in the viewer to the
	 * given <em>selection</em>!
	 *
	 * @param viewer
	 *            The {@link ContentViewer} for which the available
	 *            {@link CreateChildAction CreateChildActions} shall be
	 *            returned.
	 * @param selection
	 *            The {@link StructuredSelection} in the given <em>viewer</em>
	 *            for which the available Actions shall be returned.
	 * @return The list of {@link CreateChildAction CreateChildActions}.
	 */
	public static List<CreateChildAction> getCreateChildActions(ContentViewer viewer, StructuredSelection selection) {

		IEditorPart editor = UIHelper.getCurrentEditor();
		IEditorActionBarContributor contributor = editor != null
				? ((IEditorSite) editor.getSite()).getActionBarContributor() : null;

		if (contributor instanceof EditingDomainActionBarContributor) {

			// If the ActionBarContributor associated with the current editor is
			// able to provide a menu, we use the
			// CreateChildActions from this menu. In contrast to the 'classic'
			// approach (see below) which simply relies
			// on the 'edit' code and on the content provider associated with
			// the viewer, this takes into account
			// additional restrictions/capabilities of the 'editor' and its
			// action bar contributor.
			//
			List<IAction> actions = ActionUtil.getMenuActions(selection, editor,
					(EditingDomainActionBarContributor) contributor);

			return actions.parallelStream().filter(i -> i instanceof CreateChildAction).map(i -> (CreateChildAction) i)
					.collect(Collectors.toList());
		} else {

			// Use the editing domain to retrieve the available
			// 'newChildDescriptors' and create an action for each of
			// those.
			//
			Object object = selection.getFirstElement();

			if (!(object instanceof EObject)) {
				return new ArrayList<>();
			}

			Collection<?> newChildDescriptors = AgteleEcoreUtil.getAdapterFactoryItemDelegatorFor((EObject) object)
					.getNewChildDescriptors(object, AdapterFactoryEditingDomain.getEditingDomainFor(object), null);

			return newChildDescriptors.stream()
					.filter(d -> ActionUtil.isValidDescriptor(d, viewer.getContentProvider()))
					.map(d -> new ExtendedCreateChildAction(editor, selection, d)).collect(Collectors.toList());

		}

	}

	/**
	 * For the given {@link ContentViewer viewer} and its current selection,
	 * this returns all available {@link CreateSiblingAction
	 * CreateSiblingActions}.
	 *
	 * @param viewer
	 *            The {@link ContentViewer} for which the available
	 *            {@link CreateSiblingAction CreateSiblingActions} shall be
	 *            returned.
	 * @return The list of {@link CreateSiblingAction CreateSiblingActions}.
	 */
	public static List<CreateSiblingAction> getCreateSiblingActions(ContentViewer viewer) {

		return viewer.getSelection() instanceof StructuredSelection
				? ActionUtil.getCreateSiblingActions(viewer, (StructuredSelection) viewer.getSelection())
				: new ArrayList<>();

	}

	/**
	 * For the given {@link ContentViewer viewer} and the given
	 * {@link StructuredSelection}, this returns all available
	 * {@link CreateSiblingAction CreateSiblingActions}.
	 * <p />
	 * Note: This will not actually change the selection in the viewer to the
	 * given <em>selection</em>!
	 *
	 * @param viewer
	 *            The {@link ContentViewer} for which the available
	 *            {@link CreateSiblingAction CreateSiblingActions} shall be
	 *            returned.
	 * @param selection
	 *            The {@link StructuredSelection} in the given <em>viewer</em>
	 *            for which the available Actions shall be returned.
	 * @return The list of {@link CreateSiblingAction CreateSiblingActions}.
	 */
	public static List<CreateSiblingAction> getCreateSiblingActions(ContentViewer viewer,
			StructuredSelection selection) {

		IEditorPart editor = UIHelper.getCurrentEditor();
		IEditorActionBarContributor contributor = editor != null
				? ((IEditorSite) editor.getSite()).getActionBarContributor() : null;

		if (contributor instanceof EditingDomainActionBarContributor) {

			// If the ActionBarContributor associated with the current editor is
			// able to provide a menu, we use the
			// CreateSiblingActions from this menu. In contrast to the 'classic'
			// approach (see below) which simply
			// relies on the 'edit' code and on the content provider associated
			// with the viewer, this takes into account
			// additional restrictions/capabilities of the 'editor' and its
			// action bar contributor.
			//
			List<IAction> actions = ActionUtil.getMenuActions(selection, editor,
					(EditingDomainActionBarContributor) contributor);

			return actions.parallelStream().filter(i -> i instanceof CreateSiblingAction)
					.map(i -> (CreateSiblingAction) i).collect(Collectors.toList());
		} else {

			// Use the editing domain to retrieve the available
			// 'newSiblingDescriptors' and create an action for each of
			// those.
			//
			Object object = selection.getFirstElement();

			if (!(object instanceof EObject)) {
				return new ArrayList<>();
			}

			Collection<?> newSiblingDescriptors = AgteleEcoreUtil.getAdapterFactoryItemDelegatorFor((EObject) object)
					.getNewChildDescriptors(null, AdapterFactoryEditingDomain.getEditingDomainFor(object), object);

			return newSiblingDescriptors.stream()
					.filter(d -> ActionUtil.isValidDescriptor(d, viewer.getContentProvider()))
					.map(d -> new ExtendedCreateSiblingAction(editor, selection, d)).collect(Collectors.toList());

		}

	}

	/**
	 * For the given {@link StructuredSelection}, this returns all
	 * {@link IAction IActions} that are added to a menu via the given
	 * {@link EditingDomainActionBarContributor}.
	 *
	 * @param viewer
	 *            The {@link ContentViewer} for that the actions shall be
	 *            returned.
	 * @param selection
	 *            The {@link StructuredSelection} for which the available
	 *            Actions shall be returned.
	 * @param actionBarContributor
	 *            The {@link IMenuListener action bar contributor} that is
	 *            associated with the <em>editor</em>.
	 * @return The list of {@link ActionContributionItem
	 *         ActionContributionItems}.
	 */
	private static List<IAction> getMenuActions(StructuredSelection selection, IEditorPart editor,
			EditingDomainActionBarContributor actionBarContributor) {

		ISelection oldSelection = null;
		if (editor instanceof ISelectionProvider) {

			// As an editor may incorporate multiple viewers and the current
			// editor selection may differ from the
			// selection
			// in the given viewer, we need to store the current editor
			// selection so that we can restore it at the end
			//
			oldSelection = ((ISelectionProvider) editor).getSelection();
		}

		if (actionBarContributor instanceof ISelectionChangedListener
				&& actionBarContributor.getActiveEditor() != null) {

			// Update the action bar contributor with the selection of the
			// current viewer so that 'menuAboutToShow' will
			// produce the menu for the viewer selection instead of for the
			// editor selection
			//
			((ISelectionChangedListener) actionBarContributor)
					.selectionChanged(new SelectionChangedEvent((ISelectionProvider) editor, selection));
		}

		// We use a temporary menu manager to create a new menu from which we
		// will extract the actions
		//
		MenuManager temp = new MenuManager();
		actionBarContributor.menuAboutToShow(temp);

		// Recursively extract ActionContributionItems from the menu manager and
		// potential sub-menu managers
		//
		List<ActionContributionItem> contributionItems = ActionUtil.getActionContributionItems(temp);

		if (actionBarContributor instanceof ISelectionChangedListener && oldSelection != null) {

			// Reset the editor selection
			//
			((ISelectionChangedListener) actionBarContributor).selectionChanged(
					new SelectionChangedEvent((ISelectionProvider) UIHelper.getCurrentEditor(), oldSelection));
		}

		return contributionItems.parallelStream().map(i -> i.getAction()).collect(Collectors.toList());

	}

	/**
	 * Recursively extracts all {@link ActionContributionItem
	 * ActionContributionItems} from a given {@link MenuManager} and all
	 * included {@link SubMenuManager SubMenuManagers}.
	 *
	 * @param menuManager
	 *            The {@link MenuManager} from which the
	 *            {@link ActionContributionItem ActionContributionItems} shall
	 *            be extracted.
	 * @return The list of {@link ActionContributionItem
	 *         ActionContributionItems}.
	 */
	private static List<ActionContributionItem> getActionContributionItems(MenuManager menuManager) {

		return Arrays.stream(menuManager.getItems()).parallel()
				.flatMap(i -> i instanceof MenuManager
						? ActionUtil.getActionContributionItems((MenuManager) i).parallelStream()
						: Arrays.asList(i).parallelStream())
				.filter(i -> i instanceof ActionContributionItem).map(i -> (ActionContributionItem) i)
				.collect(Collectors.toList());
	}

	/**
	 * This is used by
	 * {@link #getCreateChildActions(ContentViewer, StructuredSelection)} and
	 * {@link #getCreateSiblingActions(ContentViewer, StructuredSelection)} to
	 * perform additional checks if an action corresponding to the given
	 * <em>descriptor</em> is valid for the active <em>content provider</em>.
	 *
	 * @param descriptor
	 *            The {@link CommandParameter} that describes an action to be
	 *            executed.
	 * @param provider
	 *            The {@link IContentProvider content provider} that is
	 *            associated with the active viewer.
	 * @return '<em><b>true</b></em>' if the descriptor is valid for the active
	 *         viewer; '<em><b>false</b></em>' otherwise.
	 */
	private static boolean isValidDescriptor(Object descriptor, IContentProvider provider) {

		if (descriptor == null || provider == null) {
			return false;
		}

		if (!(descriptor instanceof CommandParameter)
				|| !(((CommandParameter) descriptor).getFeature() instanceof EStructuralFeature)) {
			return true;
		}

		CommandParameter commandParam = (CommandParameter) descriptor;

		if (provider instanceof IFeatureValidator) {
			return ((IFeatureValidator) provider).isValidFeature((EStructuralFeature) commandParam.getFeature());
		}

		return true;
	}

}
