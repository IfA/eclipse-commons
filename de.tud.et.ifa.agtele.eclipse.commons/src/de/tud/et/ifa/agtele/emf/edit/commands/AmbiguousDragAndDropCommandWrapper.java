package de.tud.et.ifa.agtele.emf.edit.commands;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.command.AbstractCommand;
import org.eclipse.emf.common.command.UnexecutableCommand;
import org.eclipse.emf.edit.command.DragAndDropFeedback;

import de.tud.et.ifa.agtele.emf.edit.ICommandSelectionStrategy;
import de.tud.et.ifa.agtele.emf.exceptions.SorryNoOtherWorkaroundException;

/**
 * This represents an <em>unambiguous</em> command. This means that this {@link AbstractCommand} wraps a list of other
 * commands. Before executing, one command is selected from the list based on the specified
 * {@link ICommandSelectionStrategy}. Furthermore, this provides {@link DragAndDropFeedback} and can thus be used as
 * DragAndDropCommand.
 */
public class AmbiguousDragAndDropCommandWrapper extends AbstractCommand implements DragAndDropFeedback {

	/**
	 * The (ambiguous) list of commands that this wraps.
	 */
	protected ArrayList<AbstractCommand> commands;

	/**
	 * The (ambiguous) list of commands that are currently {@link #validate(Object, float, int, int, Collection) valid}.
	 */
	protected ArrayList<AbstractCommand> validCommands = new ArrayList<>();

	/**
	 * The single, unambiguous command after resolving the ambiguities.
	 */
	protected AbstractCommand command;

	/**
	 * The {@link ICommandSelectionStrategy} that shall be used to select a single command to execute.
	 */
	protected ICommandSelectionStrategy strategy;

	/**
	 * This creates an instance.
	 *
	 * @param owner
	 *            The object on that the command will be executed.
	 * @param location
	 *            The location (between 0.0 and 1.0) in relation to the '<em>owner</em>' where the command will be
	 *            executed.
	 * @param operations
	 *            The permitted operations.
	 * @param operation
	 *            The desired operation.
	 * @param collection
	 *            The dragged elements.
	 * @param commands
	 *            The ambiguous list of {@link AbstractCommand commands} that this shall wrap.
	 * @param strategy
	 *            The {@link ICommandSelectionStrategy} that shall be applied to select an unambiguous command before
	 *            execution.
	 */
	public AmbiguousDragAndDropCommandWrapper(Object owner, float location, int operations, int operation,
			Collection<?> collection, List<AbstractCommand> commands, ICommandSelectionStrategy strategy) {

		this.commands = commands == null ? new ArrayList<>() : (ArrayList<AbstractCommand>) commands;

		if (commands == null || commands.isEmpty()) {
			this.command = UnexecutableCommand.INSTANCE;
		} else if (commands.size() == 1) {
			this.command = commands.iterator().next();
		} else {
			this.command = null;
		}

		this.strategy = strategy;

		/*
		 * we need to validate the command already in the constructor as it seems that a new command is created directly
		 * before execution that would not get validated otherwise
		 */
		this.validate(owner, location, operations, operation, collection);
	}

	@Override
	public boolean validate(Object owner, float location, int operations, int operation, Collection<?> collection) {

		this.validCommands.clear();
		for (AbstractCommand c : this.commands) {
			if (c instanceof DragAndDropFeedback
					&& ((DragAndDropFeedback) c).validate(owner, location, operations, operation, collection)) {
				this.validCommands.add(c);
			}
		}

		return !this.validCommands.isEmpty();
	}

	@Override
	public boolean canExecute() {

		// This can execute if at least one of the wrapped commands can execute
		for (AbstractCommand c : this.commands) {
			if (c.canExecute()) {
				return true;
			}
		}
		return false;
	}

	@Override
	public int getFeedback() {

		// Determine the feedback to be shown based on the number and types of 'validCommands'
		if (this.validCommands.isEmpty()) {
			return DragAndDropFeedback.FEEDBACK_NONE;
		} else if (this.validCommands.size() == 1 && this.validCommands.get(0) instanceof DragAndDropFeedback) {
			return ((DragAndDropFeedback) this.validCommands.get(0)).getFeedback();
		} else {
			return DragAndDropFeedback.FEEDBACK_SELECT;
		}
	}

	@Override
	public int getOperation() {

		// Determine the operation based on the number and types of 'validCommands'
		if (this.validCommands.isEmpty()) {
			return DragAndDropFeedback.DROP_NONE;
		} else if (this.validCommands.size() == 1 && this.validCommands.get(0) instanceof DragAndDropFeedback) {
			return ((DragAndDropFeedback) this.validCommands.get(0)).getOperation();
		} else {
			return DragAndDropFeedback.DROP_LINK;
		}
	}

	@Override
	public void execute() {

		if (this.command == null) {
			if (this.validCommands.isEmpty()) {
				this.command = null;
			} else if (this.validCommands.size() == 1) {
				this.command = this.validCommands.get(0);
			} else {
				this.command = this.strategy.selectCommandToExecute(this.commands);
			}
			this.validCommands.clear();
		}

		if (this.command == null) {
			// We have to throw an exception in order to avoid the editor to be marked as dirty. Yes, this will pollute
			// the console, but...
			throw new SorryNoOtherWorkaroundException();
		}

		this.command.execute();
	}

	@Override
	public void redo() {

		if (this.command != null) {
			this.command.redo();
		}
	}

	@Override
	public void undo() {

		if (this.command != null) {
			this.command.undo();
		} else {
			super.undo();
		}
	}

}
