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
package de.tud.et.ifa.agtele.ui.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.ui.viewer.IViewerProvider;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.edit.domain.IEditingDomainProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchPartSite;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;

import de.tud.et.ifa.agtele.emf.AgteleEcoreUtil;
import de.tud.et.ifa.agtele.resources.ResourceHelper;
import de.tud.et.ifa.agtele.ui.AgteleUIPlugin;

/**
 * This provides convenience methods related to the Eclipse UI.
 *
 * @author mfreund
 */
public interface UIHelper {

	/**
	 * Returns the {@link Shell} from the active workbench window or a new one. <br />
	 * <br />
	 * <b>Important:</b> This must be called from the UI thread. If called from a non-UI thread, it will throw an
	 * 'InvalidThreadAccessException'.
	 *
	 * @return The {@link Shell} from the active workbench window or a new one.
	 */
	public static Shell getShell() {

		IWorkbench workbench = PlatformUI.getWorkbench();
		IWorkbenchWindow window = workbench.getActiveWorkbenchWindow();
		return window != null && window.getShell() != null ? window.getShell() : new Shell();
	}

	/**
	 * Returns the active {@link IWorkbenchPage} from the active {@link IWorkbenchWindow}.
	 * <p />
	 * <b>Important:</b> This must be called from the UI thread. If called from a non-UI thread, it will throw an
	 * 'InvalidThreadAccessException'.
	 *
	 * @return The currently active {@link IWorkbenchPage} or '<em><b>null</b></em>' if there is no open window/page.
	 */
	public static IWorkbenchPage getCurrentPage() {

		IWorkbench wb = PlatformUI.getWorkbench();
		IWorkbenchWindow window = wb.getActiveWorkbenchWindow();
		if (window == null) {
			return null;
		}
		return window.getActivePage();
	}

	/**
	 * This returns the currently active {@link IWorkbenchPart part}.
	 * <p />
	 * <b>Important:</b> This must be called from the UI thread. If called from a non-UI thread, it will throw an
	 * 'InvalidThreadAccessException'.
	 *
	 * @return The currently active {@link IWorkbenchPart part} or '<em><b>null</b></em>' if there is no open part.
	 */
	public static IWorkbenchPart getCurrentPart() {

		IWorkbenchPage page = UIHelper.getCurrentPage();
		return page != null ? page.getActivePart() : null;
	}

	/**
	 * This returns the currently active {@link IEditorPart editor}.
	 * <p />
	 * <b>Important:</b> This must be called from the UI thread. If called from a non-UI thread, it will throw an
	 * 'InvalidThreadAccessException'.
	 *
	 * @return The currently active {@link IEditorPart editor} or '<em><b>null</b></em>' if there is no open editor.
	 */
	public static IEditorPart getCurrentEditor() {

		IWorkbenchPage page = UIHelper.getCurrentPage();
		return page != null ? page.getActiveEditor() : null;
	}

	/**
	 * This returns all open {@link IEditorPart editors}.
	 * <p />
	 * <b>Important:</b> This must be called from the UI thread. If called from a non-UI thread, it will throw an
	 * 'InvalidThreadAccessException'.
	 *
	 * @return The open {@link IEditorPart editors} or an empty list if there is no open editor.
	 */
	public static List<IEditorPart> getAllEditors() {

		IWorkbenchPage page = UIHelper.getCurrentPage();
		return page == null ? new ArrayList<>()
				: Arrays.asList(page.getEditorReferences()).stream().map(r -> r.getEditor(false))
						.collect(Collectors.toList());

	}

	/**
	 * This returns the {@link IEditorInput} of the currently active editor.
	 * <p />
	 * <b>Important:</b> This must be called from the UI thread. If called from a non-UI thread, it will throw an
	 * 'InvalidThreadAccessException'.
	 *
	 * @return The {@link IEditorInput} or '<em><b>null</b></em>' if the editor input could not be determined or if
	 *         there is no open editor.
	 */
	public static IEditorInput getCurrentEditorInput() {

		IEditorPart editor = UIHelper.getCurrentEditor();
		return editor == null ? null : editor.getEditorInput();
	}

	/**
	 * This returns the {@link IEditorInput IEditorInputs} of all open editors.
	 * <p />
	 * <b>Important:</b> This must be called from the UI thread. If called from a non-UI thread, it will throw an
	 * 'InvalidThreadAccessException'.
	 *
	 * @return The {@link IEditorInput IEditorInputs} or an empty list if the editor inputs could not be determined or
	 *         if there is no open editor.
	 */
	public static List<IEditorInput> getAllEditorInputs() {

		List<IEditorPart> editors = UIHelper.getAllEditors();
		return editors.stream().filter(e -> e != null && e instanceof IEditorPart).collect(Collectors.toList()).stream()
				.map(IEditorPart::getEditorInput).collect(Collectors.toList());
	}

	/**
	 * This returns the current {@link ISelection}.
	 * <p />
	 * <b>Important:</b> This must be called from the UI thread. If called from a non-UI thread, it will throw an
	 * 'InvalidThreadAccessException'.
	 *
	 * @return The current {@link ISelection}.
	 */
	public static ISelection getCurrentSelection() {

		IWorkbench wb = PlatformUI.getWorkbench();
		IWorkbenchWindow window = wb.getActiveWorkbenchWindow();
		return window != null ? window.getSelectionService().getSelection() : null;
	}

	/**
	 * This returns the first element of the current {@link StructuredSelection}.
	 * <p />
	 * <b>Important:</b> This must be called from the UI thread. If called from a non-UI thread, it will throw an
	 * 'InvalidThreadAccessException'.
	 *
	 * @return The first element of the current {@link StructuredSelection} or <em>null</em> if either nothing is
	 *         selected or if the {@link #getCurrentSelection() current selection} is not a {@link StructuredSelection}.
	 */
	public static Object getFirstSelection() {

		ISelection selection = UIHelper.getCurrentSelection();
		if (selection instanceof StructuredSelection) {
			return ((StructuredSelection) selection).getFirstElement();
		} else {
			return null;
		}
	}

	/**
	 * Selects the given {@link EObject} in an editor.
	 * <p />
	 * <b>Important:</b> This must be called from the UI thread. If called from a non-UI thread, it will throw an
	 * 'InvalidThreadAccessException'.
	 * <p />
	 * Note: The given {@link EObject} does not need be part of the {@link ResourceSet} used by the editor as
	 * {@link AgteleEcoreUtil#getEquivalentElementFrom(EObject, ResourceSet) an equivalent element} will determined
	 * automatically.
	 *
	 * @param elementToSelect
	 *            The {@link EObject} to select.
	 * @param editorToUse
	 *            An optional {@link IEditorPart} to use. If no editor is given, a new editor will be
	 *            {@link #openEditor(IFile) opened}.
	 * @return The {@link IEditorPart} in which the element was selected.
	 * @throws PartInitException
	 *             If the editor could not be created or initialized.
	 * @throws ClassCastException
	 *             If the opened editor does not implement {@link IEditingDomainProvider} or {@link IViewerProvider}.
	 */
	public static IEditorPart selectEObjectInEditor(EObject elementToSelect, Optional<IEditorPart> editorToUse)
			throws PartInitException, ClassCastException {

		IEditorPart editor = editorToUse
				.orElse(UIHelper.openEditor(ResourceHelper.getFileForResource(elementToSelect.eResource())));

		EObject equivalentElement = AgteleEcoreUtil.getEquivalentElementFrom(elementToSelect,
				((IEditingDomainProvider) editor).getEditingDomain().getResourceSet());
		((IViewerProvider) editor).getViewer().setSelection(new StructuredSelection(equivalentElement));

		return editor;
	}

	/**
	 * Selects the given {@link EObject} in an editor.
	 * <p />
	 * <b>Important:</b> This must be called from the UI thread. If called from a non-UI thread, it will throw an
	 * 'InvalidThreadAccessException'.
	 * <p />
	 * Note: The given {@link EObject} does not need be part of the {@link ResourceSet} used by the editor as
	 * {@link AgteleEcoreUtil#getEquivalentElementFrom(EObject, ResourceSet) an equivalent element} will determined
	 * automatically.
	 *
	 * @param elementToSelect
	 *            The {@link EObject} to select.
	 * @param editorToUse
	 *            The id of the editor to use.
	 * @return The {@link IEditorPart} in which the element was selected.
	 * @throws PartInitException
	 *             If the editor could not be created or initialized.
	 * @throws ClassCastException
	 *             If the opened editor does not implement {@link IEditingDomainProvider} or {@link IViewerProvider}.
	 */
	public static IEditorPart selectEObjectInEditor(EObject elementToSelect, String editorToUse)
			throws PartInitException, ClassCastException {

		IEditorPart editor = UIHelper.openEditor(ResourceHelper.getFileForResource(elementToSelect.eResource()),
				editorToUse);

		return UIHelper.selectEObjectInEditor(elementToSelect, Optional.of(editor));
	}

	/**
	 * This opens an editor identified by a given 'editorID' for a given {@link IEditorInput}.
	 * <p />
	 * <b>Important:</b> This must be called from the UI thread. If called from a non-UI thread, it will throw an
	 * 'InvalidThreadAccessException'.
	 *
	 * @see #openEditor(IEditorInput, IEditorDescriptor)
	 * @see #openEditor(IFile, String)
	 * @see #openEditor(IFile, IEditorDescriptor)
	 * @see #openEditor(IFile)
	 *
	 * @param editorInput
	 *            The {@link IEditorInput} on which to open the editor.
	 * @param editorID
	 *            The id of the editor to use.
	 *
	 * @return The opened {@link IEditorPart} or <em>null</em> if no editor could be opened.
	 * @throws PartInitException
	 *             If the editor could not be created or initialized.
	 */
	public static IEditorPart openEditor(IEditorInput editorInput, String editorID) throws PartInitException {

		IWorkbenchPage page = UIHelper.getCurrentPage();

		return page != null ? page.openEditor(editorInput, editorID) : null;
	}

	/**
	 * This opens an editor identified by a given {@link IEditorDescriptor} for a given {@link IEditorInput}.
	 * <p />
	 * <b>Important:</b> This must be called from the UI thread. If called from a non-UI thread, it will throw an
	 * 'InvalidThreadAccessException'.
	 *
	 * @see #openEditor(IEditorInput, String)
	 * @see #openEditor(IFile, String)
	 * @see #openEditor(IFile, IEditorDescriptor)
	 * @see #openEditor(IFile)
	 *
	 * @param editorInput
	 *            The {@link IEditorInput} on which to open the editor.
	 * @param editorDescriptor
	 *            An {@link IEditorDescriptor} of the editor to use.
	 *
	 * @return The opened {@link IEditorPart} or <em>null</em> if no editor could be opened.
	 * @throws PartInitException
	 *             If the editor could not be created or initialized.
	 */
	public static IEditorPart openEditor(IEditorInput editorInput, IEditorDescriptor editorDescriptor)
			throws PartInitException {

		IWorkbenchPage page = UIHelper.getCurrentPage();

		return page != null ? page.openEditor(editorInput, editorDescriptor.getId()) : null;
	}

	/**
	 * This opens an editor identified by a given 'editorID' for a given {@link IFile}.
	 * <p />
	 * <b>Important:</b> This must be called from the UI thread. If called from a non-UI thread, it will throw an
	 * 'InvalidThreadAccessException'.
	 *
	 * @see #openEditor(IEditorInput, String)
	 * @see #openEditor(IEditorInput, IEditorDescriptor)
	 * @see #openEditor(IFile, IEditorDescriptor)
	 * @see #openEditor(IFile)
	 *
	 * @param editorInput
	 *            The {@link IFile} on which to open the editor.
	 * @param editorID
	 *            The id of the editor to use.
	 *
	 * @return The opened {@link IEditorPart} or <em>null</em> if no editor could be opened.
	 * @throws PartInitException
	 *             If the editor could not be created or initialized.
	 */
	public static IEditorPart openEditor(IFile editorInput, String editorID) throws PartInitException {

		IWorkbenchPage page = UIHelper.getCurrentPage();

		return page != null ? page.openEditor(new FileEditorInput(editorInput), editorID) : null;
	}

	/**
	 * This opens an editor identified by a given {@link IEditorDescriptor} for a given {@link IFile}.
	 * <p />
	 * <b>Important:</b> This must be called from the UI thread. If called from a non-UI thread, it will throw an
	 * 'InvalidThreadAccessException'.
	 *
	 * @see #openEditor(IEditorInput, String)
	 * @see #openEditor(IEditorInput, IEditorDescriptor)
	 * @see #openEditor(IFile, String)
	 * @see #openEditor(IFile)
	 *
	 * @param editorInput
	 *            The {@link IFile} on which to open the editor.
	 * @param editorDescriptor
	 *            An {@link IEditorDescriptor} of the editor to use.
	 *
	 * @return The opened {@link IEditorPart} or <em>null</em> if no editor could be opened.
	 * @throws PartInitException
	 *             If the editor could not be created or initialized.
	 */
	public static IEditorPart openEditor(IFile editorInput, IEditorDescriptor editorDescriptor)
			throws PartInitException {

		IWorkbenchPage page = UIHelper.getCurrentPage();

		return page != null ? page.openEditor(new FileEditorInput(editorInput), editorDescriptor.getId()) : null;
	}

	/**
	 * This opens the default editor for a given {@link IFile}.
	 * <p />
	 * <b>Important:</b> This must be called from the UI thread. If called from a non-UI thread, it will throw an
	 * 'InvalidThreadAccessException'.
	 *
	 * @see #openEditor(IEditorInput, String)
	 * @see #openEditor(IEditorInput, IEditorDescriptor)
	 * @see #openEditor(IFile, String)
	 * @see #openEditor(IFile, IEditorDescriptor)
	 *
	 * @param editorInput
	 *            The {@link IFile} on which to open the editor.
	 *
	 * @return The opened {@link IEditorPart} or <em>null</em> if no editor could be opened.
	 * @throws PartInitException
	 *             If the editor could not be created or initialized.
	 */
	public static IEditorPart openEditor(IFile editorInput) throws PartInitException {

		IWorkbenchPage page = UIHelper.getCurrentPage();

		IEditorDescriptor editorDescriptor = PlatformUI.getWorkbench().getEditorRegistry()
				.getDefaultEditor(editorInput.getName());

		// If there is no default editor for the given input, use the standard text editor instead.
		//
		if (editorDescriptor == null) {
			editorDescriptor = PlatformUI.getWorkbench().getEditorRegistry()
					.findEditor("org.eclipse.ui.DefaultTextEditor");
		}

		return editorDescriptor != null && page != null
				? page.openEditor(new FileEditorInput(editorInput), editorDescriptor.getId())
				: null;
	}

	/**
	 * This activates the given {@link IEditorPart editor}.
	 * <p />
	 * <b>Important:</b> This must be called from the UI thread. If called from a non-UI thread, it will throw an
	 * 'InvalidThreadAccessException'.
	 *
	 * @param editor
	 *            The {@link IEditorPart} to activate.
	 */
	public static void activateEditor(IEditorPart editor) {

		IWorkbenchPartSite site = editor.getSite();

		if (site == null) {
			return;
		}

		IWorkbenchPage page = site.getPage();

		if (page != null) {
			page.activate(editor);
		}
	}

	/**
	 * Logs the given {@link Throwable} to the error log of the {@link AgteleUIPlugin}.
	 *
	 * @param throwable
	 *            The {@link Throwable} to log.
	 */
	public static void log(Throwable throwable) {

		AgteleUIPlugin.getPlugin().getLog().log(new Status(Status.ERROR, AgteleUIPlugin.PLUGIN_ID,
				throwable.getMessage() != null ? throwable.getMessage() : throwable.toString(), throwable));
	}

}
