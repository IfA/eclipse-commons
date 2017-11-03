package de.tud.et.ifa.agtele.emf.edit;

import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.CopyCommand;
import org.eclipse.emf.edit.command.MoveCommand;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.command.SetCommand;

/**
 * IRequireRelatedModelUpdateProvider provides an interfaces that can be
 * implemented by model element providers in order to automatically
 * create/delete/manipulate depending elements/parts of the model when this
 * element is created. This interface provides checks if changes are necessary
 * when the initial model change is done and provides command parameters in
 * order to update any model in any way and restorable.
 *
 * @author Nils Frederik Dickmann
 *
 */
public interface IRequireRelatedModelUpdateProvider {

	/**
	 * Checks if a model change is required depending on the original command.
	 *
	 * @param originalCommand
	 * @return
	 */
	default boolean isModelUpdateRequired(Command originalCommand) {
		if (originalCommand instanceof AddCommand) {
			return this.isModelUpdateRequiredForAdd((AddCommand) originalCommand);
		}
		if (originalCommand instanceof RemoveCommand) {
			return this.isModelUpdateRequiredForRemove((RemoveCommand) originalCommand);
		}
		if (originalCommand instanceof MoveCommand) {
			return this.isModelUpdateRequiredForMove((MoveCommand) originalCommand);
		}
		if (originalCommand instanceof CopyCommand) {
			return this.isModelUpdateRequiredForCopy((CopyCommand) originalCommand);
		}
		if (originalCommand instanceof SetCommand) {
			return this.isModelUpdateRequiredForSet((SetCommand) originalCommand);
		}
		return false;
	}

	/**
	 * Convenience method in order to check if a model change is required when an
	 * element is added to the model.
	 *
	 * @param originalCommand
	 * @return
	 */
	default boolean isModelUpdateRequiredForAdd(AddCommand originalCommand) {
		return false;
	}

	/**
	 * Convenience method in order to check if a model change is required when an
	 * element is removed from the model.
	 *
	 * @param originalCommand
	 * @return
	 */
	default boolean isModelUpdateRequiredForRemove(RemoveCommand originalCommand) {
		return false;
	}

	/**
	 * Convenience method in order to check if a model change is required when an
	 * element is moved.
	 *
	 * @param originalCommand
	 * @return
	 */
	default boolean isModelUpdateRequiredForMove(MoveCommand originalCommand) {
		return false;
	}

	/**
	 * Convenience method in order to check if a model change is required when an
	 * element is copied.
	 *
	 * @param originalCommand
	 * @return
	 */
	default boolean isModelUpdateRequiredForCopy(CopyCommand originalCommand) {
		return false;
	}

	/**
	 * Convenience method in order to check if a model change is required when
	 * something is set on this command.
	 *
	 * @param originalCommand
	 * @return
	 */
	default boolean isModelUpdateRequiredForSet(SetCommand originalCommand) {
		return false;
	}

	/**
	 * Returns the list of Commands that need to be executed in conjunction with the
	 * original command in order to ensure a proper model.
	 *
	 * @param originalCommand
	 * @return
	 */
	default List<Command> getModelUpdateCommands(Command originalCommand) {
		if (originalCommand instanceof AddCommand) {
			return this.getModelUpdateCommandsForAdd((AddCommand) originalCommand);
		}
		if (originalCommand instanceof RemoveCommand) {
			return this.getModelUpdateCommandsForRemove((RemoveCommand) originalCommand);
		}
		if (originalCommand instanceof MoveCommand) {
			return this.getModelUpdateCommandsForMove((MoveCommand) originalCommand);
		}
		if (originalCommand instanceof SetCommand) {
			return this.getModelUpdateCommandsForSet((SetCommand) originalCommand);
		}
		if (originalCommand instanceof CopyCommand) {
			return this.getModelUpdateCommandsForCopy((CopyCommand) originalCommand);
		}
		return Collections.emptyList();
	}

	/**
	 * Convenience method for returning the list of Commands that need to be
	 * executed in conjunction with the original add command.
	 *
	 * @param originalCommand
	 * @return
	 */
	default List<Command> getModelUpdateCommandsForAdd(AddCommand originalCommand) {
		return Collections.emptyList();
	}

	/**
	 * Convenience method for returning the list of Commands that need to be
	 * executed in conjunction with the original remove command.
	 *
	 * @param originalCommand
	 * @return
	 */
	default List<Command> getModelUpdateCommandsForRemove(RemoveCommand originalCommand) {
		return Collections.emptyList();
	}

	/**
	 * Convenience method for returning the list of Commands that need to be
	 * executed in conjunction with the original move command.
	 *
	 * @param originalCommand
	 * @return
	 */
	default List<Command> getModelUpdateCommandsForMove(MoveCommand originalCommand) {
		return Collections.emptyList();
	}

	/**
	 * Convenience method for returning the list of Commands that need to be
	 * executed in conjunction with the original copy command.
	 *
	 * @param originalCommand
	 * @return
	 */
	default List<Command> getModelUpdateCommandsForCopy(CopyCommand originalCommand) {
		return Collections.emptyList();
	}

	/**
	 * Convenience method for returning the list of Commands that need to be
	 * executed in conjunction with the original set command.
	 *
	 * @param originalCommand
	 * @return
	 */
	default List<Command> getModelUpdateCommandsForSet(SetCommand originalCommand) {
		return Collections.emptyList();
	}

	/**
	 * Wraps the original command in a {@link CompoundCommand}, if needed.
	 *
	 * @param originalCommand
	 * @return
	 */
	default Command wrapOriginalCommand(Command originalCommand) {
		if (!this.isModelUpdateRequired(originalCommand)) {
			return originalCommand;
		}
		List<Command> requiredUpdates = this.getModelUpdateCommands(originalCommand);
		if (requiredUpdates.isEmpty()) {
			return originalCommand;
		}

		CompoundCommand result = new CompoundCommand();

		for (Command c : requiredUpdates) {
			result.append(c);
		}
		result.append(originalCommand);

		return result;
	}
}
