package de.tud.et.ifa.agtele.emf.edit;

import java.util.ArrayList;

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
	public default AbstractCommand selectCommandToExecute(ArrayList<AbstractCommand> commands) {
		return (commands != null && !commands.isEmpty() ? commands.iterator().next() : UnexecutableCommand.INSTANCE);
	}

}