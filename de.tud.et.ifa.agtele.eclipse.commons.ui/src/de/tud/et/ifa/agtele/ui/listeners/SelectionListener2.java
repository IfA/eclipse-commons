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

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;

/**
 * This interface extends the standard SWT 'SelectionListener' interface
 * in a way that it provides a default implementation for the
 * 'widgetDefaultSelected' method that does nothing. Thus, the user only
 * has to implement the 'widgetSelected' method.
 *
 * @author mfreund
 */
@FunctionalInterface
public interface SelectionListener2 extends SelectionListener {

	@Override
	public default void widgetDefaultSelected(SelectionEvent e) {
		// do nothing on default selection
	}
}
