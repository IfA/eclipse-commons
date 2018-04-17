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
package de.tud.et.ifa.agtele.emf.edit;

import java.util.List;

import org.eclipse.emf.common.command.AbstractCommand;
import org.eclipse.emf.common.command.UnexecutableCommand;

/**
 * This interface defines a method to select one of a list of commands to be executed.
 *  
 * @author mfreund
 */
public interface ICommandSelectionStrategy {

	/**
	 * This selects and returns exactly one of the given '<em>commands</em>' that shall be executed.
	 * <p />
	 * The default implementation selects the first choice.
	 * 
	 * @param commands The ambiguous list of commands that might be executed.
	 * @return The single command that is to be executed.
	 */
	public default AbstractCommand selectCommandToExecute(List<AbstractCommand> commands) {
		return (commands != null && !commands.isEmpty() ? commands.iterator().next() : UnexecutableCommand.INSTANCE);
	}

}
