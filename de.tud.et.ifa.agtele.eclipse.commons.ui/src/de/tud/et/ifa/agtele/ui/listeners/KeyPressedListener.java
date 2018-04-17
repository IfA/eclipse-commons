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

import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;


/**
 * This interface extends the standard SWT {@link KeyListener} interface in a way that it provides a default
 * implementation for the {@link #keyReleased(org.eclipse.swt.events.KeyEvent)} method that does nothing. Thus, the user
 * only has to implement the {@link #keyPressed(org.eclipse.swt.events.KeyEvent)} method.
 *
 * @author mfreund
 */
public interface KeyPressedListener extends KeyListener {

	@Override
	public default void keyReleased(KeyEvent e) {

		// do nothing when a key is released

	}
}
