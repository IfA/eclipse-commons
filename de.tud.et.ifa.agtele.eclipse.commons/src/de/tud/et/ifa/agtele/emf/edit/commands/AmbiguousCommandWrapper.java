package de.tud.et.ifa.agtele.emf.edit.commands;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.emf.common.command.AbstractCommand;
import org.eclipse.emf.common.command.UnexecutableCommand;
import org.eclipse.emf.edit.command.DragAndDropFeedback;
import org.eclipse.swt.dnd.DND;

import de.tud.et.ifa.agtele.emf.edit.ICommandSelectionStrategy;

/**
 * This represents an <em>unambiguous</em> command. This means that this {@link AbstractCommand} wraps a list of other 
 * commands. Before executing, one command is selected from the list based on the specified {@link ICommandSelectionStrategy}.
 * Furthermore, this provides {@link DragAndDropFeedback} and can thus be used as DragAndDropCommand.
 */
public class AmbiguousCommandWrapper extends AbstractCommand implements
		DragAndDropFeedback {

	/**
	 * The (ambiguous) list of commands that this wraps.
	 */
	protected ArrayList<AbstractCommand> commands;
	
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
	 * @param commands The ambiguous list of {@link AbstractCommand commands} that this shall wrap.
	 * @param strategy The {@link ICommandSelectionStrategy} that shall be applied to select an unambiguous 
	 * command before execution.
	 */
	public AmbiguousCommandWrapper(ArrayList<AbstractCommand> commands, ICommandSelectionStrategy strategy) {
		
		this.commands = (commands == null ? new ArrayList<>() : commands);
		
		if(commands == null || commands.isEmpty()) {
			this.command = UnexecutableCommand.INSTANCE;
		} else if(commands.size() == 1) {
			this.command = commands.iterator().next();
		} else {
			this.command = null;
		}
		
		this.strategy = strategy;
	}

	@Override
	public boolean validate(Object owner, float location, int operations,
			int operation, Collection<?> collection) {
		
		return canExecute();
	}
	
	@Override
	public boolean canExecute() {
		
		// This can execute if at least one of the wrapped commands can execute
		for (AbstractCommand command : commands) {
			if(command.canExecute()) {
				return true;
			}
		}
		return false;
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
	public void execute() {

		if(command == null) {
			command = strategy.selectCommandToExecute(commands);
			
		}
		
		if(command == null) {
			return;
		}
		
		
		command.execute();
	}

	@Override
	public void redo() {
		if(command != null) {
			command.redo();
		}
	}
	
	@Override
	public void undo() {
		if(command != null) {
			command.undo();
		}
	}
	

}
