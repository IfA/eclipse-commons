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
package de.tud.et.ifa.agtele.ui.listeners;

import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;


/**
 * This interface extends the standard SWT {@link MouseListener} interface in a way that it provides default
 * implementations for the {@link #mouseDown(MouseEvent)} and {@link #mouseUp(MouseEvent)} methods that do nothing.
 * Thus, the user only has to implement the {@link #mouseDoubleClick(MouseEvent)} method.
 *
 * @author mfreund
 */
public interface MouseDoubleClickListener extends MouseListener {

	@Override
	default void mouseDown(MouseEvent e) {

		// do nothing when a mouse button is pressed

	}

	@Override
	default void mouseUp(MouseEvent e) {

		// do nothing when a mouse button is released

	}
}
