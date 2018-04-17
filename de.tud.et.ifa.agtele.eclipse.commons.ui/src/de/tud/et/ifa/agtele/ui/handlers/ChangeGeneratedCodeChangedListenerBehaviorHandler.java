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
package de.tud.et.ifa.agtele.ui.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.Status;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.handlers.RadioState;

import de.tud.et.ifa.agtele.ui.AgteleUIPlugin;
import de.tud.et.ifa.agtele.ui.listeners.GeneratedCodeChangedListener;
import de.tud.et.ifa.agtele.ui.listeners.GeneratedCodeChangedListener.GeneratedCodeChangedListenerMode;

/**
 * This handler changes the {@link GeneratedCodeChangedListenerMode operating mode} of the
 * {@link GeneratedCodeChangedListener}.
 *
 * @author mfreund
 *
 */
public class ChangeGeneratedCodeChangedListenerBehaviorHandler extends AbstractHandler {

	/**
	 * Changes the {@link GeneratedCodeChangedListener#setMode(GeneratedCodeChangedListenerMode) operating mode} of the
	 * {@link GeneratedCodeChangedListener}.
	 *
	 */
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {

		if (HandlerUtil.matchesRadioState(event)) {
			// Nothing to be done
			//
			return null;
		}

		// The mode to set
		//
		int newMode;
		try {
			newMode = Integer.parseInt(event.getParameter(RadioState.PARAMETER_ID));
		} catch (NumberFormatException e) {
			AgteleUIPlugin.getPlugin().getLog().log(new Status(Status.ERROR, "de.tud.et.ifa.agtele.eclipse.commons.ui",
					e.getMessage() != null ? e.getMessage() : e.toString(), e));
			return null;
		}

		// Set the mode
		//
		GeneratedCodeChangedListener.setMode(newMode);

		// Finally, update the current state
		//
		HandlerUtil.updateRadioState(event.getCommand(), String.valueOf(newMode));

		return null;
	}

}
