package de.tud.et.ifa.agtele.ui.listeners;

import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.impl.EStringToStringMapEntryImpl;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.window.Window;

import de.tud.et.ifa.agtele.emf.AgteleEcoreUtil;
import de.tud.et.ifa.agtele.ui.dialogs.InputDialog;
import de.tud.et.ifa.agtele.ui.handlers.AddDocumentationAnnotationHandler.MultiLineInputDialog;
import de.tud.et.ifa.agtele.ui.util.UIHelper;

/**
 * This is a simple double click listener, that opens an input dialog for string
 * to string map entries, mostly used in the details feature of ecore
 * annotations.
 *
 * @author Baron
 *
 */
public class AnnotationDetailsEntryDoubleClickListener implements IDoubleClickListener {

	protected TreeViewer viewer;

	public AnnotationDetailsEntryDoubleClickListener(TreeViewer viewer) {
		this.viewer = viewer;
		this.viewer.addDoubleClickListener(this);
	}

	@Override
	public void doubleClick(DoubleClickEvent event) {

		EStringToStringMapEntryImpl entry = null;

		ISelection selection = event.getSelection();
		if (selection.isEmpty()) {
			return;
		}
		if (selection instanceof IStructuredSelection) {
			if (((IStructuredSelection) selection).getFirstElement() instanceof EStringToStringMapEntryImpl) {
				entry = (EStringToStringMapEntryImpl) ((IStructuredSelection) selection).getFirstElement();
			}
		} else if (selection instanceof EStringToStringMapEntryImpl) {
			entry = (EStringToStringMapEntryImpl) selection;
		}
		if (entry == null) {
			return;
		}

		EditingDomain dom = AgteleEcoreUtil.getEditingDomainFor(entry);
		String oldValue = entry.getValue();
		if (oldValue == null) {
			oldValue = "";
		}
		String newValue = null;

		InputDialog dialog = new MultiLineInputDialog(UIHelper.getShell(),
				"Set value of Details Entry '" + entry.getKey() + "'", "Set Details Key Value", oldValue, null);
		if (dialog.open() == Window.OK) {
			newValue = dialog.getValue();
		} else {
			return;
		}

		SetCommand command = null;

		if (!oldValue.equals(newValue)) {
			command = new SetCommand(dom, entry, EcorePackage.Literals.ESTRING_TO_STRING_MAP_ENTRY__VALUE, newValue);
			dom.getCommandStack().execute(command);
		}
	}

}
