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
 * This interface may be implemented by {@link IMinimizable minimizable controls} that specify a specific <em>minimized
 * height</em>, i.e. their preferred height in a <em>minimized</em> state. If a {@link IMinimizable minimizable control}
 * does not implement this interface, the parent control will assign a <em>default minimized height</em>.
 *
 * @see IMinimizable
 *
 * @author mfreund
 */
@FunctionalInterface
public interface IMinimizedHeightProvider {

	/**
	 * Return the preferred height that the control should have in <em>minimized</em> state.
	 *
	 * @return The preferred height of the control in <em>minimized</em> state.
	 */
	public int getMinimizedHeight();
}
