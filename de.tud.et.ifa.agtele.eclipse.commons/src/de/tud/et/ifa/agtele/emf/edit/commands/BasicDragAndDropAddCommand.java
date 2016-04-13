package de.tud.et.ifa.agtele.emf.edit.commands;

import java.util.Collection;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.DragAndDropFeedback;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.swt.dnd.DND;

/**
 * An {@link AddCommand} that provides DragAndDropFeedback and can thus be returned by
 * e.g. the 'createDragAndDropCommand' function in EMF item providers.
 *
 */
public class BasicDragAndDropAddCommand extends AddCommand implements
		DragAndDropFeedback {

	public BasicDragAndDropAddCommand(EditingDomain domain, EObject owner,
			EStructuralFeature feature, Collection<?> value) {
		super(domain, owner, feature, value);
	}

	@Override
	public boolean validate(Object owner, float location, int operations,
			int operation, Collection<?> collection) {
		return isExecutable;
	}

	@Override
	public int getFeedback() {
		return DND.FEEDBACK_SELECT;
	}

	@Override
	public int getOperation() {
		return DND.DROP_LINK;
	}
	
	@Override
	public String doGetLabel() {
		return "Add: " + feature.getName();
	}

}
