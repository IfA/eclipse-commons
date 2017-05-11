package de.tud.et.ifa.agtele.ui.util;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;

/**
 * This provides convenience methods related to the Eclipse UI.
 *
 * @author mfreund
 */
public interface UIHelper {

	/**
	 * Returns the {@link Shell} from the active workbench window or a new one.
	 * <br />
	 * <br />
	 * <b>Important:</b> This must be called from the UI thread. If called from
	 * a non-UI thread, it will throw an 'InvalidThreadAccessException'.
	 *
	 * @return The {@link Shell} from the active workbench window or a new one.
	 */
	public static Shell getShell() {
		IWorkbench workbench = PlatformUI.getWorkbench();
		IWorkbenchWindow window = workbench.getActiveWorkbenchWindow();
		return window != null && window.getShell() != null ? window.getShell() : new Shell();
	}

	/**
	 * This returns the currently active {@link IEditorPart editor}.
	 * <p />
	 * <b>Important:</b> This must be called from the UI thread. If called from
	 * a non-UI thread, it will throw an 'InvalidThreadAccessException'.
	 *
	 * @return The currently active {@link IEditorPart editor} or
	 *         '<em><b>null</b></em>' if there is no open editor.
	 */
	public static IEditorPart getCurrentEditor() {

		IWorkbench wb = PlatformUI.getWorkbench();
		IWorkbenchWindow window = wb.getActiveWorkbenchWindow();
		if (window == null) {
			return null;
		}
		IWorkbenchPage page = window.getActivePage();
		if (page == null) {
			return null;
		}
		return page.getActiveEditor();
	}

	/**
	 * This returns the currently active {@link IWorkbenchPart part}.
	 * <p />
	 * <b>Important:</b> This must be called from the UI thread. If called from
	 * a non-UI thread, it will throw an 'InvalidThreadAccessException'.
	 *
	 * @return The currently active {@link IWorkbenchPart part} or
	 *         '<em><b>null</b></em>' if there is no open part.
	 */
	public static IWorkbenchPart getCurrentPart() {

		IWorkbench wb = PlatformUI.getWorkbench();
		IWorkbenchWindow window = wb.getActiveWorkbenchWindow();
		if (window == null) {
			return null;
		}
		IWorkbenchPage page = window.getActivePage();
		if (page == null) {
			return null;
		}
		return page.getActivePart();
	}

	/**
	 * This returns the {@link IEditorInput} of the currently active editor.
	 * <p />
	 * <b>Important:</b> This must be called from the UI thread. If called from
	 * a non-UI thread, it will throw an 'InvalidThreadAccessException'.
	 *
	 * @return The {@link IEditorInput} or '<em><b>null</b></em>' if the editor
	 *         input could not be determined or if there is no open editor.
	 */
	public static IEditorInput getCurrentEditorInput() {

		IEditorPart editor = UIHelper.getCurrentEditor();
		return editor == null ? null : editor.getEditorInput();
	}

	/**
	 * This returns the current {@link ISelection}.
	 * <p />
	 * <b>Important:</b> This must be called from the UI thread. If called from
	 * a non-UI thread, it will throw an 'InvalidThreadAccessException'.
	 *
	 * @return The current {@link ISelection}.
	 */
	public static ISelection getCurrentSelection() {

		IWorkbench wb = PlatformUI.getWorkbench();
		IWorkbenchWindow window = wb.getActiveWorkbenchWindow();
		return window != null ? window.getSelectionService().getSelection() : null;
	}

	/**
	 * This returns the first element of the current
	 * {@link StructuredSelection}.
	 * <p />
	 * <b>Important:</b> This must be called from the UI thread. If called from
	 * a non-UI thread, it will throw an 'InvalidThreadAccessException'.
	 *
	 * @return The first element of the current {@link StructuredSelection} or
	 *         <em>null</em> if either nothing is selected or if the
	 *         {@link #getCurrentSelection() current selection} is not a
	 *         {@link StructuredSelection}.
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
	 * This opens an editor identified by a given 'editorID' for a given
	 * {@link IEditorInput}.
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
	 * @return The opened {@link IEditorPart} or <em>null</em> if no editor
	 *         could be opened.
	 * @throws PartInitException
	 *             If the editor could not be created or initialized.
	 */
	public static IEditorPart openEditor(IEditorInput editorInput, String editorID) throws PartInitException {

		IWorkbench wb = PlatformUI.getWorkbench();
		IWorkbenchWindow window = wb.getActiveWorkbenchWindow();
		IWorkbenchPage page = window.getActivePage();

		return page.openEditor(editorInput, editorID);
	}

	/**
	 * This opens an editor identified by a given {@link IEditorDescriptor} for
	 * a given {@link IEditorInput}.
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
	 * @return The opened {@link IEditorPart} or <em>null</em> if no editor
	 *         could be opened.
	 * @throws PartInitException
	 *             If the editor could not be created or initialized.
	 */
	public static IEditorPart openEditor(IEditorInput editorInput, IEditorDescriptor editorDescriptor)
			throws PartInitException {

		IWorkbench wb = PlatformUI.getWorkbench();
		IWorkbenchWindow window = wb.getActiveWorkbenchWindow();
		IWorkbenchPage page = window.getActivePage();

		return page.openEditor(editorInput, editorDescriptor.getId());
	}

	/**
	 * This opens an editor identified by a given 'editorID' for a given
	 * {@link IFile}.
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
	 * @return The opened {@link IEditorPart} or <em>null</em> if no editor
	 *         could be opened.
	 * @throws PartInitException
	 *             If the editor could not be created or initialized.
	 */
	public static IEditorPart openEditor(IFile editorInput, String editorID) throws PartInitException {

		IWorkbench wb = PlatformUI.getWorkbench();
		IWorkbenchWindow window = wb.getActiveWorkbenchWindow();
		IWorkbenchPage page = window.getActivePage();

		return page.openEditor(new FileEditorInput(editorInput), editorID);
	}

	/**
	 * This opens an editor identified by a given {@link IEditorDescriptor} for
	 * a given {@link IFile}.
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
	 * @return The opened {@link IEditorPart} or <em>null</em> if no editor
	 *         could be opened.
	 * @throws PartInitException
	 *             If the editor could not be created or initialized.
	 */
	public static IEditorPart openEditor(IFile editorInput, IEditorDescriptor editorDescriptor)
			throws PartInitException {

		IWorkbench wb = PlatformUI.getWorkbench();
		IWorkbenchWindow window = wb.getActiveWorkbenchWindow();
		IWorkbenchPage page = window.getActivePage();

		return page.openEditor(new FileEditorInput(editorInput), editorDescriptor.getId());
	}

	/**
	 * This opens the default editor for a given {@link IFile}.
	 *
	 * @see #openEditor(IEditorInput, String)
	 * @see #openEditor(IEditorInput, IEditorDescriptor)
	 * @see #openEditor(IFile, String)
	 * @see #openEditor(IFile, IEditorDescriptor)
	 *
	 * @param editorInput
	 *            The {@link IFile} on which to open the editor.
	 *
	 * @return The opened {@link IEditorPart} or <em>null</em> if no editor
	 *         could be opened.
	 * @throws PartInitException
	 *             If the editor could not be created or initialized.
	 */
	public static IEditorPart openEditor(IFile editorInput) throws PartInitException {

		IWorkbench wb = PlatformUI.getWorkbench();
		IWorkbenchWindow window = wb.getActiveWorkbenchWindow();
		IWorkbenchPage page = window.getActivePage();

		IEditorDescriptor editorDescriptor = PlatformUI.getWorkbench().getEditorRegistry()
				.getDefaultEditor(editorInput.getName());

		return page.openEditor(new FileEditorInput(editorInput), editorDescriptor.getId());
	}

}
