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
package de.tud.et.ifa.agtele.emf.edit;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.command.AbstractCommand;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.CommandActionDelegate;
import org.eclipse.emf.edit.command.CopyCommand;
import org.eclipse.emf.edit.command.CreateChildCommand;
import org.eclipse.emf.edit.command.DragAndDropFeedback;
import org.eclipse.emf.edit.command.MoveCommand;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.provider.ItemProvider;

import de.tud.et.ifa.agtele.emf.AgteleEcoreUtil;
import de.tud.et.ifa.agtele.emf.edit.CommonItemProviderAdapter.CreateChildCommandWithExtendedAccess;

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
			return this.isModelUpdateRequiredForCreateChild((CreateChildCommandWithExtendedAccess) originalCommand);
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
	 * When this method is invoked, the original command has already been executed.
	 *
	 * @param originalCommand
	 * @return
	 */
	default boolean isModelUpdateRequiredForCreateChild(CreateChildCommandWithExtendedAccess originalCommand) {
		return false;
	}

	/**
	 * Convenience method in order to check if a model change is required when an
	 * element is added to the model.
	 *
	 * When this method is invoked, the original command has already been executed.
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
	 * This method is invoked before the original command will be executed.
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
	 * When this method is invoked, the original command has already been executed.
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
	 * When this method is invoked, the original command has already been executed.
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
	 * When this method is invoked, the original command has already been executed.
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
			return this.getModelUpdateCommandsForCreateChild((CreateChildCommandWithExtendedAccess) originalCommand);
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
	 * When this method is invoked, the original command has already been executed.
	 *
	 * @param originalCommand
	 * @return
	 */
	default List<Command> getModelUpdateCommandsForCreateChild(CreateChildCommandWithExtendedAccess originalCommand) {
		return new ArrayList<>();
	}

	/**
	 * Convenience method for returning the list of Commands that need to be
	 * executed in conjunction with the original add command.
	 *
	 * When this method is invoked, the original command has already been executed.
	 *
	 * @param originalCommand
	 * @return
	 */
	default List<Command> getModelUpdateCommandsForAdd(AddCommand originalCommand) {
		return new ArrayList<>();
	}

	/**
	 * Convenience method for returning the list of Commands that need to be
	 * executed in conjunction with the original remove command.
	 *
	 * This method is invoked, before the original command will be executed.
	 *
	 * @param originalCommand
	 * @return
	 */
	default List<Command> getModelUpdateCommandsForRemove(RemoveCommand originalCommand) {
		return new ArrayList<>();
	}

	/**
	 * Convenience method for returning the list of Commands that need to be
	 * executed in conjunction with the original move command.
	 *
	 * When this method is invoked, the original command has already been executed.
	 *
	 * @param originalCommand
	 * @return
	 */
	default List<Command> getModelUpdateCommandsForMove(MoveCommand originalCommand) {
		return new ArrayList<>();
	}

	/**
	 * Convenience method for returning the list of Commands that need to be
	 * executed in conjunction with the original copy command.
	 *
	 * When this method is invoked, the original command has already been executed.
	 *
	 * @param originalCommand
	 * @return
	 */
	default List<Command> getModelUpdateCommandsForCopy(CopyCommand originalCommand) {
		return new ArrayList<>();
	}

	/**
	 * Convenience method for returning the list of Commands that need to be
	 * executed in conjunction with the original set command.
	 *
	 * When this method is invoked, the original command has already been executed.
	 *
	 * @param originalCommand
	 * @return
	 */
	default List<Command> getModelUpdateCommandsForSet(SetCommand originalCommand) {
		return new ArrayList<>();
	}

	/**
	 * Wraps the original command in a {@link DelayedWrappingCommand}, if needed. If
	 * the original command is a {@link CommandActionDelegate}, the returned Command
	 * is a {@link DelegatingDelayedWrappingCommand}. If the original command is a
	 * {@link DragAndDropFeedback}, the returned Command is a
	 * {@link DragAndDropFeedbackDelayedWrappingCommand}.
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
		if (originalCommand instanceof CommandActionDelegate) {
			return new DelegatingDelayedWrappingCommand(originalCommand, originalProvider);
		}
		if (originalCommand instanceof DragAndDropFeedback) {
			return new DragAndDropFeedbackDelayedWrappingCommand(originalCommand, originalProvider);
		}
		return new DelayedWrappingCommand(originalCommand, originalProvider);
	}

	/**
	 * Fetches the created class instance from the command.
	 *
	 * @param cmd
	 * @return
	 */
	static <T> T getCreatedElement(CreateChildCommandWithExtendedAccess cmd, Class<T> cls) {
		Collection<?> col = cmd.getResult();
		if (col.isEmpty()) {
			return null;
		}
		Object ele = col.iterator().next();
		if (cls.isAssignableFrom(ele.getClass())) {
			return (T) ele;
		}
		return null;
	}

	/**
	 * Fetches the removed class instances from the command.
	 *
	 * @param <T>
	 *
	 * @param cmd
	 * @param cls
	 * @return
	 */
	@SuppressWarnings("unchecked")
	static <T> List<T> getRemovedElements(RemoveCommand cmd, Class<T> cls) {
		Collection<?> col = cmd.getCollection();
		if (col.isEmpty()) {
			return null;
		}
		List<T> result = new ArrayList<>();
		for (Object o : col) {
			if (cls.isAssignableFrom(o.getClass())) {
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
	static <T> List<T> getAddedElements(AddCommand cmd, Class<T> cls) {
		Collection<?> col = cmd.getCollection();
		if (col.isEmpty()) {
			return null;
		}
		List<T> result = new ArrayList<>();
		for (Object o : col) {
			if (cls.isAssignableFrom(o.getClass())) {
				result.add((T) o);
			}
		}
		return result;
	}

	/**
	 * This class emulates a special version of a compound command. It is
	 * initialized with an {@link #originalCommand}.
	 *
	 * When this {@link #originalCommand} is being executed,
	 * {@link #additionalCommands} are being determined by use of the
	 * {@link IRequireRelatedModelUpdateProvider} interface that can optionally be
	 * implemented by the {@link #originalProvider} provider that created the
	 * {@link #originalCommand} in the first place or by the elements that are the
	 * result of the {@link #originalCommand}.
	 *
	 * The type of the {@link #originalCommand} determines, whether it is executed
	 * first.
	 *
	 * Redo, Undo are supported, if the {@link #originalCommand} and all
	 * {@link #additionalCommands} support it.
	 *
	 * Supported original command classes are
	 * {@link CreateChildCommandWithExtendedAccess}, {@link AddCommand},
	 * {@link RemoveCommand}, {@link MoveCommand}, {@link SetCommand}, and
	 * {@link SetCommand}
	 *
	 * If the command shall support Command Actions, use
	 * {@link DelegatingDelayedWrappingCommand}
	 *
	 * @author Baron
	 *
	 */
	public static class DelayedWrappingCommand extends AbstractCommand {
		//TODO make it a Coumpound Command?
		/**
		 * Flag indicating, that the original command will be executed first
		 */
		protected boolean executeOriginalCommandFirst = false;
		/**
		 * The original command, this command wraps
		 */
		protected Command originalCommand;
		/**
		 * Additional commands determined by the
		 * {@link IRequireRelatedModelUpdateProvider} interface.
		 */
		protected List<Command> additionalCommands = null;
		/**
		 * The {@link ItemProvider} that created the {@link #originalCommand}
		 */
		protected Object originalProvider;

		/**
		 *
		 * @param originalCommand
		 *            The command to wrap and to determine {@link #additionalCommands}
		 *            for
		 * @param originalProvider
		 *            The {@link ItemProvider} that created the original command
		 */
		public DelayedWrappingCommand(Command originalCommand, Object originalProvider) {
			super();
			this.originalCommand = originalCommand;
			this.originalProvider = originalProvider;
			this.executeOriginalCommandFirst = this.getExecuteOriginalCommandFirst();
		}

		/**
		 * Returns the {@link #executeOriginalCommandFirst} flag
		 *
		 * @return
		 */
		public boolean getExecuteOriginalCommandFirst() {
			return this.originalCommand instanceof CreateChildCommand || this.originalCommand instanceof SetCommand
					|| this.originalCommand instanceof AddCommand || this.originalCommand instanceof CopyCommand
					|| this.originalCommand instanceof MoveCommand;
		}

		/**
		 * Adapted in order to also execute the {@link #additionalCommands}.
		 */
		@Override
		public void execute() {
			if (this.executeOriginalCommandFirst && this.originalCommand.canExecute()) {
				this.originalCommand.execute();
			}
			if (this.additionalCommands == null) {
				this.additionalCommands = this.getAdditionalCommands();
			}
			for (Command cmd : this.additionalCommands) {
				if (cmd.canExecute()) {
					cmd.execute();
				}
			}
			if (!this.executeOriginalCommandFirst && this.originalCommand.canExecute()) {
				this.originalCommand.execute();
			}
		}

		@Override
		public boolean canExecute() {
			if (this.additionalCommands != null) {
				for (Command cmd : this.additionalCommands) {
					if (!cmd.canExecute()) {
						return false;
					}
				}
			}
			return this.originalCommand.canExecute();
		}

		@Override
		public boolean canUndo() {
			if (this.additionalCommands != null) {
				for (Command cmd : this.additionalCommands) {
					if (!cmd.canUndo()) {
						return false;
					}
				}
			}
			return this.originalCommand.canUndo();
		}

		@Override
		public void redo() {
			if (this.executeOriginalCommandFirst) {
				this.originalCommand.redo();
			}
			for (Command cmd : this.additionalCommands) {
				cmd.redo();
			}
			if (!this.executeOriginalCommandFirst) {
				this.originalCommand.redo();
			}
		}

		@Override
		public void undo() {
			if (!this.executeOriginalCommandFirst && this.originalCommand.canUndo()) {
				this.originalCommand.undo();
			}
			List<Command> reverse = new ArrayList<>();
			reverse.addAll(this.additionalCommands);
			Collections.reverse(reverse);

			for (Command cmd : reverse) {
				try {
					if (cmd.canUndo()) {
						cmd.undo();
					}
				} catch (UnsupportedOperationException e) {
					for (int i = reverse.indexOf(cmd) - 1; i >= 0; i -= 1) {
						Command cmd2 = reverse.get(i);
						cmd2.redo();
					}
					throw e;
				}
			}
			if (this.executeOriginalCommandFirst) {
				try {
					if (this.originalCommand.canUndo()) {
						this.originalCommand.undo();
					}
				} catch (UnsupportedOperationException e) {
					Collections.reverse(reverse);
					for (Command cmd : reverse) {
						cmd.redo();
					}
					throw e;
				}
			}
		}

		/**
		 * Determines the {@link #additionalCommands} to be executed either before or
		 * after the {@link #originalCommand}.
		 *
		 * @return
		 */
		protected List<Command> getAdditionalCommands() {
			ArrayList<Command> result = new ArrayList<>();

			//Query the hosting provider first
			if (this.originalProvider instanceof IRequireRelatedModelUpdateProvider) {
				if (((IRequireRelatedModelUpdateProvider) this.originalProvider)
						.isModelUpdateRequired(this.originalCommand)) {
					for (Command c : ((IRequireRelatedModelUpdateProvider) this.originalProvider)
							.getModelUpdateCommands(this.originalCommand)) {
						result.add(c);
					}
				}
			}

			//query providers of the possible child/affected elements
			Collection<?> elements = null;
			if (this.originalCommand instanceof AddCommand) {
				elements = ((AddCommand) this.originalCommand).getCollection();
			}
			if (this.originalCommand instanceof RemoveCommand) {
				elements = ((RemoveCommand) this.originalCommand).getCollection();
			}
			if (this.originalCommand instanceof CreateChildCommandWithExtendedAccess) {
				elements = ((CreateChildCommand) this.originalCommand).getResult();
			}
			if (this.originalCommand instanceof MoveCommand) {
				elements = ((MoveCommand) this.originalCommand).getAffectedObjects();
			}
			if (this.originalCommand instanceof SetCommand) {
				elements = new ArrayList<>();
				((ArrayList) elements).add(((SetCommand) this.originalCommand).getOldValue());
				((ArrayList) elements).add(((SetCommand) this.originalCommand).getValue());
			}

			if (elements != null && !elements.isEmpty()) {
				//perform the collection of related commands only once per provider instance (if singleton pattern is used, once per provider class)
				Collection<IRequireRelatedModelUpdateProvider> providers = new ArrayList<>();
				for (Object element : elements) {
					if (element != null && element instanceof EObject) {
						IRequireRelatedModelUpdateProvider elementProvider = AgteleEcoreUtil.adapt((EObject) element,
								IRequireRelatedModelUpdateProvider.class);
						if (elementProvider != null && !providers.contains(elementProvider)) {
							providers.add(elementProvider);
							if (elementProvider.isModelUpdateRequired(this.originalCommand)) {
								result.addAll(elementProvider.getModelUpdateCommands(this.originalCommand));
							}
						}
					}
				}
			}

			result.remove(this.originalCommand);
			return result;
		}

		@Override
		public String getDescription() {
			return this.originalCommand.getDescription();
		}

		@Override
		public String getLabel() {
			return this.originalCommand.getLabel();
		}

		@SuppressWarnings("unchecked")
		@Override
		public Collection<?> getAffectedObjects() {
			@SuppressWarnings({ "rawtypes" })
			ArrayList result = new ArrayList(this.originalCommand.getAffectedObjects());
			if (this.additionalCommands != null) {
				for (Command cmd : this.additionalCommands) {
					result.addAll(cmd.getAffectedObjects());
				}
			}
			return result;
		}

		@SuppressWarnings("unchecked")
		@Override
		public Collection<?> getResult() {
			@SuppressWarnings("rawtypes")
			ArrayList result = new ArrayList(this.originalCommand.getResult());
			if (this.additionalCommands != null) {
				for (Command cmd : this.additionalCommands) {
					result.addAll(cmd.getResult());
				}
			}
			return result;
		}

		public Command getOriginalCommand() {
			return this.originalCommand;
		}

		public Object getOriginalItemProvider() {
			return this.originalProvider;
		}
	}

	/**
	 * Adds support for Command Action Delegation.
	 *
	 * @author Baron
	 */
	public static class DelegatingDelayedWrappingCommand extends DelayedWrappingCommand implements CommandActionDelegate {

		/**
		 * @see DelayedWrappingCommand
		 * @param originalCommand
		 * @param originalProvider
		 */
		public DelegatingDelayedWrappingCommand(Command originalCommand, Object originalProvider) {
			super(originalCommand, originalProvider);
		}

		@Override
		public Object getImage() {
			return ((CommandActionDelegate) this.originalCommand).getImage();
		}

		@Override
		public String getText() {
			return ((CommandActionDelegate) this.originalCommand).getText();
		}

		@Override
		public String getToolTipText() {
			return ((CommandActionDelegate) this.originalCommand).getToolTipText();
		}
	}

	/**
	 * Adds support for DragAndDropFeedback.
	 *
	 * @author Baron
	 */
	public static class DragAndDropFeedbackDelayedWrappingCommand extends DelayedWrappingCommand
			implements DragAndDropFeedback {

		public DragAndDropFeedbackDelayedWrappingCommand(Command originalCommand, Object originalProvider) {
			super(originalCommand, originalProvider);
		}

		@Override
		public boolean validate(Object owner, float location, int operations, int operation, Collection<?> collection) {
			return ((DragAndDropFeedback) this.originalCommand).validate(owner, location, operations, operation,
					collection);
		}

		@Override
		public int getFeedback() {
			return ((DragAndDropFeedback) this.originalCommand).getFeedback();
		}

		@Override
		public int getOperation() {
			return ((DragAndDropFeedback) this.originalCommand).getOperation();
		}

	}
}
