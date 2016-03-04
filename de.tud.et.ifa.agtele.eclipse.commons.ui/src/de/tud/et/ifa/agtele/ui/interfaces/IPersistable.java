package de.tud.et.ifa.agtele.ui.interfaces;

import org.eclipse.jface.dialogs.IDialogSettings;

/**
 * Objects implementing this interface are capable of saving their state to and restoring their state from an 
 * instance of {@link IDialogSettings}.
 * 
 * @author mfreund
 */
public interface IPersistable {

	/**
	 * Persist the state to the given instance of {@link IDialogSettings}.
	 * @param settings
	 */
	public void persist(IDialogSettings settings);
	
	/**
	 * Restore the state from the given instance of {@link IDialogSettings}.
	 * @param settings
	 */
	public void restore(IDialogSettings settings);
}
