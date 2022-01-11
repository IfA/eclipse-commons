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
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.DragAndDropFeedback;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.swt.dnd.DND;

/**
 * An {@link AddCommand} that provides DragAndDropFeedback and can thus be returned by e.g. the
 * 'createDragAndDropCommand' function in EMF item providers.
 *
 */
public class BasicDragAndDropAddCopyCommand extends BasicDragAndDropAddCommand {

	/**
	 * This creates an instance.
	 *
	 * @param domain
	 *            The {@link EditingDomain} on which the command shall be executed.
	 * @param owner
	 *            The {@link EObject} to which the given <em>values</em> shall be added.
	 * @param feature
	 *            The {@link EStructuralFeature} to which the given <em>values</em> shall be added.
	 * @param values
	 *            The collection of values to be added.
	 */
	public BasicDragAndDropAddCopyCommand(EditingDomain domain, EObject owner, EStructuralFeature feature,
			Collection<?> values) {
		super(domain, owner, feature, values);
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

		String ret = "Copy to '" + this.feature.getName() + "'";

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
