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
package de.tud.et.ifa.agtele.ui.listeners;

import java.util.List;
import java.util.Optional;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IExecutionListener;
import org.eclipse.core.commands.NotHandledException;
import org.eclipse.ui.IWorkbenchCommandConstants;

import de.tud.et.ifa.agtele.ui.util.UIHelper;

/**
 * An {@link IExecutionListener} that only listens to certain types of {@link IWorkbenchCommandConstants workbench
 * commands}.
 * <p />
 * Note: For each call of the various methods of {@link IExecutionListener}, this just checks the specified
 * {@link #commandIDs} (and potential {@link #editorTypes}) and the (if necessary) calls the appropriate <em>do...</em>
 * version of the method. Thus, clients can simply overwrite those methods without actually having to check the id of
 * the triggering command.
 *
 * @author mfreund
 */
public abstract class WorkspaceCommandListener implements IExecutionListener {

	/**
	 * An optional list of classes identifying the types of editors in which save commands shall trigger this listener.
	 * <p />
	 * Note: If no (or an empty list of) editor types is specified, the listener triggers for save commands in all types
	 * of editors.
	 */
	protected Optional<List<Class<?>>> editorTypes;

	/**
	 * A list of {@link IWorkbenchCommandConstants workbench commands} that cause this listener to trigger.
	 */
	protected List<String> commandIDs;

	/**
	 * This creates an instance.
	 * <p />
	 * Note: This will set the {@link #editorTypes} to an empty Optional, so that the listener triggers for save
	 * commands in all types of editors.
	 *
	 * @see #WorkspaceCommandListener(List,List)
	 *
	 * @param commandIDs
	 *            The list of {@link IWorkbenchCommandConstants workbench commands} that shall cause this listener to
	 *            trigger.
	 */
	public WorkspaceCommandListener(List<String> commandIDs) {

		this.commandIDs = commandIDs;
		this.editorTypes = Optional.empty();
	}

	/**
	 * This creates an instance.
	 *
	 * @see #WorkspaceCommandListener(List)
	 *
	 * @param commandIDs
	 *            The list of {@link IWorkbenchCommandConstants workbench commands} that shall cause this listener to
	 *            trigger.
	 * @param editorTypes
	 *            A list of classes identifying the types of editors in which save commands shall trigger this listener.
	 */
	public WorkspaceCommandListener(List<String> commandIDs, List<Class<?>> editorTypes) {

		this.commandIDs = commandIDs;
		this.editorTypes = Optional.ofNullable(editorTypes);
	}

	@Override
	public void preExecute(String commandId, ExecutionEvent event) {

		if (this.shallTrigger(commandId)) {
			this.doPreExecute(commandId, event);
		}
	}

	/**
	 * Notifies the listener that a command is about to execute.
	 *
	 * @param commandId
	 *            The identifier of the command that is about to execute, never <code>null</code>.
	 * @param event
	 *            The event that will be passed to the <code>execute</code> method; never <code>null</code>.
	 */
	protected abstract void doPreExecute(String commandId, ExecutionEvent event);

	@Override
	public void notHandled(String commandId, NotHandledException exception) {

		if (this.shallTrigger(commandId)) {
			this.doNotHandled(commandId, exception);
		}
	}

	/**
	 * Notifies the listener that an attempt was made to execute a command with no handler.
	 *
	 * @param commandId
	 *            The identifier of command that is not handled; never <code>null</code>
	 * @param exception
	 *            The exception that occurred; never <code>null</code>.
	 */
	protected abstract void doNotHandled(String commandId, NotHandledException exception);

	@Override
	public void postExecuteFailure(String commandId, ExecutionException exception) {

		if (this.shallTrigger(commandId)) {
			this.doPostExecuteFailure(commandId, exception);
		}

	}

	/**
	 * Notifies the listener that a command has failed to complete execution.
	 *
	 * @param commandId
	 *            The identifier of the command that has executed; never <code>null</code>.
	 * @param exception
	 *            The exception that occurred; never <code>null</code>.
	 */
	protected abstract void doPostExecuteFailure(String commandId, ExecutionException exception);

	@Override
	public void postExecuteSuccess(String commandId, Object returnValue) {

		if (this.shallTrigger(commandId)) {
			this.doPostExecuteSuccess(commandId, returnValue);
		}

	}

	/**
	 * Notifies the listener that a command has completed execution successfully.
	 *
	 * @param commandId
	 *            The identifier of the command that has executed; never <code>null</code>.
	 * @param returnValue
	 *            The return value from the command; may be <code>null</code>.
	 */
	protected abstract void doPostExecuteSuccess(String commandId, Object returnValue);

	/**
	 * Whether the given {@link ExecutionEvent} shall trigger this listener.
	 *
	 */
	private boolean shallTrigger(String commandId) {

		return this.commandIDs.contains(commandId) && (!this.editorTypes.isPresent()
				|| this.editorTypes.get().contains(UIHelper.getCurrentEditor().getClass()));
	}

}
