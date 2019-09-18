package de.tud.et.ifa.agtele.ui.emf.editor;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.edit.command.CommandParameter;
import org.eclipse.emf.edit.ui.action.CreateSiblingAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IEditorPart;

public class ExtendedCreateSiblingAction extends CreateSiblingAction implements IExtendedCreateElementAction {

	public ExtendedCreateSiblingAction(IEditorPart editorPart, ISelection selection, Object descriptor) {
		super(editorPart, selection, descriptor);
	}

	@Override
	public CommandParameter getDescriptor() {
		return this.descriptor instanceof CommandParameter ? (CommandParameter) this.descriptor : null;
	}
	
	@Override
	public Command getCommand() {
		return this.command;
	}
}
