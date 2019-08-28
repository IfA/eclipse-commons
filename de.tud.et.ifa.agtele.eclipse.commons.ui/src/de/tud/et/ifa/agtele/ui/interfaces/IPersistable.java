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
	
	/**
	 * Some persistable objects, by means of the settings path, may occur multiple times, e.g. in case of the clonable editor.
	 * In this case, each instance may store individual settings in order to be able to restore the individual state per instance.
	 * @return the instance id. When returning 0, also settings without an instance id in the path may be used for restoring the state.
	 * @author lbaron
	 */
	public default int getInstanceId() {
		return 0;
	}
}
