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
