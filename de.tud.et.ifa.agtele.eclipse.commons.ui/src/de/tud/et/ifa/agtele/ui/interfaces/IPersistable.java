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
}
