package de.tud.et.ifa.agtele.ui.util;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

/**
 * This provides convenience methods related to the Eclipse UI.
 *
 * @author mfreund
 */
public interface UIHelper {

	/**
	 * Returns the {@link Shell} from the active workbench window or a new one.
	 * <br /><br />
	 * <b>Important:</b> This must be called from the UI thread. If called from a non-UI thread,
	 * it will throw an 'InvalidThreadAccessException'.
	 *
	 * @return The {@link Shell} from the active workbench window or a new one.
	 */
	public static Shell getShell() {
		IWorkbench workbench = PlatformUI.getWorkbench();
		IWorkbenchWindow window = workbench.getActiveWorkbenchWindow();
		return window != null && window.getShell() != null
				? window.getShell() : new Shell();
	}

	/**
	 * This returns the {@link IEditorInput} of the currently active editor.
	 * <p />
	 * <b>Important:</b> This must be called from the UI thread. If called from a non-UI thread,
	 * it will throw an 'InvalidThreadAccessException'.
	 *
	 * @return The {@link IEditorInput} or '<em><b>null</b></em>' if the editor input could not
	 * be determined or if there is no open editor.
	 */
	public static IEditorInput getCurrentEditorInput() {
		IWorkbench wb = PlatformUI.getWorkbench();
		IWorkbenchWindow window = wb.getActiveWorkbenchWindow();
		IWorkbenchPage page = window.getActivePage();
		if(page == null) {
			return null;
		}
		IEditorPart editor = page.getActiveEditor();
		return editor == null ? null : editor.getEditorInput();
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
		return window.getSelectionService().getSelection();
	}
}
