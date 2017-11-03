package de.tud.et.ifa.agtele.emf.edit;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.CopyCommand;
import org.eclipse.emf.edit.command.CreateChildCommand;
import org.eclipse.emf.edit.command.MoveCommand;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.command.SetCommand;

import de.tud.et.ifa.agtele.emf.AgteleEcoreUtil;

/**
 * IRequireRelatedModelUpdateProvider provides an interfaces that can be
 * implemented by model element providers in order to automatically
 * create/delete/manipulate depending elements/parts of the model when this
 * element is created. This interface provides checks if changes are necessary
 * when the initial model change is done and provides command parameters in
 * order to update any model in any way and restorable.
 *
 * If implemented, the interface will be queried two times, on the model element
 * that is updated in some of its features and the target element, that is
 * referenced through the feature.
 *
 * @author Nils Frederik Dickmann
 *
 */
public interface IRequireRelatedModelUpdateProvider {

	/**
	 * Checks if a model change is required depending on the original command.
	 *
	 * The element, this provider is responsible for, can always be the owner (some
	 * of its features changes) and the target (a new instance of a model element,
	 * for example) element. If a command affects multiple elements, e.g. of a
	 * multi-valued reference, each provider for a certain element class is expected
	 * to be called only once per command.
	 *
	 * @param originalCommand
	 * @return
	 */
	default boolean isModelUpdateRequired(Command originalCommand) {
		if (originalCommand instanceof CreateChildCommand) {
			return this.isModelUpdateRequiredForCreateChild((CreateChildCommand) originalCommand);
		}
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
	 * element is created.
	 *
	 * @param originalCommand
	 * @return
	 */
	default boolean isModelUpdateRequiredForCreateChild(CreateChildCommand originalCommand) {
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
		if (originalCommand instanceof CreateChildCommand) {
			return this.getModelUpdateCommandsForCreateChild((CreateChildCommand) originalCommand);
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
	 * executed in conjunction with the original create child command.
	 *
	 * @param originalCommand
	 * @return
	 */
	default List<Command> getModelUpdateCommandsForCreateChild(CreateChildCommand originalCommand) {
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
	 * @param originalProvider
	 *            The Provider the gets involved first, when a subordinate feature
	 *            changes.
	 * @param originalCommand
	 *            The original command, that has been created.
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	static Command wrapOriginalCommand(Object originalProvider, Command originalCommand) {
		CompoundCommand result = new CompoundCommand();

		//Query the hosting provider first
		if (originalProvider instanceof IRequireRelatedModelUpdateProvider) {
			if (((IRequireRelatedModelUpdateProvider) originalProvider).isModelUpdateRequired(originalCommand)) {
				for (Command c : ((IRequireRelatedModelUpdateProvider) originalProvider)
						.getModelUpdateCommands(originalCommand)) {
					result.append(c);
				}
			}
		}

		//query providers of the possible child/affected elements
		Collection<?> elements = null;
		if (originalCommand instanceof AddCommand) {
			elements = ((AddCommand) originalCommand).getCollection();
		}
		if (originalCommand instanceof RemoveCommand) {
			elements = ((RemoveCommand) originalCommand).getCollection();
		}
		if (originalCommand instanceof CreateChildCommand) {
			elements = ((CreateChildCommand) originalCommand).getResult();
		}
		if (originalCommand instanceof MoveCommand) {
			elements = ((MoveCommand) originalCommand).getAffectedObjects();
		}
		if (originalCommand instanceof SetCommand) {
			elements = new ArrayList<>();
			((ArrayList) elements).add(((SetCommand) originalCommand).getOldValue());
			((ArrayList) elements).add(((SetCommand) originalCommand).getValue());
		}

		if (elements != null && !elements.isEmpty()) {
			//perform the collection of related commands only once per provider instance (if singleton pattern is used, once per provider class)
			Collection<IRequireRelatedModelUpdateProvider> providers = new ArrayList<>();

			IRequireRelatedModelUpdateProvider elementProvider = AgteleEcoreUtil
					.adapt((EObject) elements.iterator().next(), IRequireRelatedModelUpdateProvider.class);
			if (elementProvider != null && !providers.contains(elementProvider)
					&& elementProvider.isModelUpdateRequired(originalCommand)) {
				providers.add(elementProvider);
				for (Command c : elementProvider.getModelUpdateCommands(originalCommand)) {
					result.append(c);
				}
			}
		}

		result.append(originalCommand);
		if (result.getCommandList().size() == 1) {
			return originalCommand;
		}
		return result;
	}

	/**
	 * Fetches the created class instance from the command.
	 *
	 * @param cmd
	 * @return
	 */
	default <T> T getCreatedElement(CreateChildCommand cmd, Class<T> cls) {
		Collection<?> col = cmd.getResult();
		if (col.isEmpty()) {
			return null;
		}
		Object ele = col.iterator().next();
		if (ele.getClass().isAssignableFrom(cls)) {
			return (T) ele;
		}
		return null;
	}

	/**
	 * Fetches the removed class instances from the command.
	 *
	 * @param cmd
	 * @return
	 */
	default <T> List<T> getRemovedElements(RemoveCommand cmd, Class<T> cls) {
		Collection<?> col = cmd.getCollection();
		if (col.isEmpty()) {
			return null;
		}
		List<T> result = new ArrayList<>();
		for (Object o : col) {
			if (o.getClass().isAssignableFrom(cls)) {
				result.add((T) o);
			}
		}
		return result;
	}

	/**
	 * Fetches the added class instances from the command.
	 *
	 * @param cmd
	 * @return
	 */
	default <T> List<T> getAddedElements(AddCommand cmd, Class<T> cls) {
		Collection<?> col = cmd.getCollection();
		if (col.isEmpty()) {
			return null;
		}
		List<T> result = new ArrayList<>();
		for (Object o : col) {
			if (o.getClass().isAssignableFrom(cls)) {
				result.add((T) o);
			}
		}
		return result;
	}
}
