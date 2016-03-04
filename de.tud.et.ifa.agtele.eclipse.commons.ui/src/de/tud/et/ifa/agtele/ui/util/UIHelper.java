package de.tud.et.ifa.agtele.ui.util;

import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

/**
 * This provides convenience methods related to the Eclipse UI.
 * 
 * @author mfreund
 */
public class UIHelper {

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
		return (window != null && window.getShell() != null) 
				? window.getShell() : new Shell(); 
	}
}
