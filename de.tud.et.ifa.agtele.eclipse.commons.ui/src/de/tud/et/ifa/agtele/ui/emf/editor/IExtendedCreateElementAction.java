package de.tud.et.ifa.agtele.ui.emf.editor;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.edit.command.CommandParameter;
import org.eclipse.jface.action.IAction;

public interface IExtendedCreateElementAction extends IAction {
	public CommandParameter getDescriptor();
	
	public Command getCommand();
}
