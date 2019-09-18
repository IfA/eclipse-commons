package de.tud.et.ifa.agtele.ui.emf.editor;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.edit.command.CommandParameter;
import org.eclipse.emf.edit.ui.action.CreateChildAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IEditorPart;

public class ExtendedCreateChildAction extends CreateChildAction implements IExtendedCreateElementAction {

	public ExtendedCreateChildAction(IEditorPart editorPart, ISelection selection, Object descriptor) {
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
