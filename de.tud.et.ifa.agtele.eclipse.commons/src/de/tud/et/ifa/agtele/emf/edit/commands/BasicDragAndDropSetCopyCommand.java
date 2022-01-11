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

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.command.DragAndDropFeedback;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.swt.dnd.DND;

/**
 * A {@link SetCommand} that provides DragAndDropFeedback and can thus be returned by e.g. the
 * 'createDragAndDropCommand' function in EMF item providers.
 *
 */
public class BasicDragAndDropSetCopyCommand extends BasicDragAndDropSetCommand {

	/**
	 * This creates an instance.
	 *
	 * @param domain
	 *            The {@link EditingDomain} on which the command shall be executed.
	 * @param owner
	 *            The {@link EObject} that shall hold the <em>value</em> to be set.
	 * @param feature
	 *            The {@link EStructuralFeature} that shall hold the given <em>value</em> to be set.
	 * @param value
	 *            The value to be set.
	 * @param index
	 *            At which index the given <em>value</em> shall be set in the feature.
	 */
	public BasicDragAndDropSetCopyCommand(EditingDomain domain, EObject owner, EStructuralFeature feature, Object value,
			int index) {
		super(domain, owner, feature, value, index);
	}

	@Override
	public int getFeedback() {
		return DND.FEEDBACK_SELECT;
	}

	@Override
	public int getOperation() {
		return DND.DROP_COPY;
	}

	@Override
	public String doGetLabel() {

		String ret = "Set copy as '" + this.feature.getName() + "'";

		if (this.feature instanceof EReference) {
			ret += (((EReference) this.feature).isContainment() ? " (containment " : " (non-containment ")
					+ "reference)";
		} else if (this.feature instanceof EAttribute) {
			ret += " (attribute)";
		} else {
			ret += " (feature)";
		}

		return ret;
	}
}
