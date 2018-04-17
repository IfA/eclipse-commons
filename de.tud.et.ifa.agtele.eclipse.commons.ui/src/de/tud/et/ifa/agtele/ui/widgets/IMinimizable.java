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
package de.tud.et.ifa.agtele.ui.widgets;

/**
 * This interface may be implemented by elements in order to indicate that there UI state distinguishes between
 * <em>minimized</em> and <em>restored</em>.
 * <p />
 * Components that layout other controls may call the methods {@link #minimize()} and {@link #restore()} in order to
 * trigger a change in the UI state.
 *
 * @see IMinimizedHeightProvider
 *
 * @author mfreund
 */
public interface IMinimizable {

	/**
	 * Switch to the control's <em>minimized</em> UI state.
	 */
	public void minimize();

	/**
	 * Restores the control (removes the '<em>minimized</em>' state).
	 */
	public void restore();

}
