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
package de.tud.et.ifa.agtele.emf.edit.commands;

import java.util.Collection;

import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.edit.command.DragAndDropFeedback;
import org.eclipse.swt.dnd.DND;

/**
 * A {@link CompoundCommand} that provides DragAndDropFeedback and can thus be returned by e.g. the
 * 'createDragAndDropCommand' function in EMF item providers.
 *
 */
public class BasicDragAndDropCompoundCommand extends CompoundCommand implements DragAndDropFeedback {

	/**
	 * This creates an instance.
	 */
	public BasicDragAndDropCompoundCommand() {
		super();
	}

	@Override
	public boolean validate(Object owner, float location, int operations, int operation, Collection<?> collection) {

		return this.canExecute() && location >= 0.2 && location <= 0.8;
	}

	@Override
	public int getFeedback() {

		return DND.FEEDBACK_SELECT;
	}

	@Override
	public int getOperation() {

		return DND.DROP_LINK;
	}

}
