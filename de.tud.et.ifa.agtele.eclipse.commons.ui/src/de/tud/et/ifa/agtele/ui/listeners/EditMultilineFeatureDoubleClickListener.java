package de.tud.et.ifa.agtele.ui.listeners;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.impl.EStringToStringMapEntryImpl;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.ItemProviderAdapter;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.window.Window;

import de.tud.et.ifa.agtele.emf.AgteleEcoreUtil;
import de.tud.et.ifa.agtele.ui.dialogs.InputDialog;
import de.tud.et.ifa.agtele.ui.dialogs.MultiLineInputDialog;
import de.tud.et.ifa.agtele.ui.util.UIHelper;

/**
 * This listener enables a tree viewer to popup the multiline edit dialog, if an element has exactly one multiline property
 * @author Baron
 *
 */
public class EditMultilineFeatureDoubleClickListener implements IDoubleClickListener {

	private TreeViewer selectionViewer;
	private AdapterFactory adapterFactory;

	public EditMultilineFeatureDoubleClickListener(TreeViewer viewer, AdapterFactory adapterFactory) {
		this.selectionViewer = viewer;
		this.adapterFactory = adapterFactory;
		this.selectionViewer.addDoubleClickListener(this);
	}

	@Override
	public void doubleClick(DoubleClickEvent event) {
		ISelection selection = event.getSelection();
		if (selection.isEmpty()) {
			return;
		}
		
		EObject selected = null;
		
		if (selection instanceof IStructuredSelection) {
			if (((IStructuredSelection) selection).getFirstElement() instanceof EObject) {
				selected = (EObject) ((IStructuredSelection) selection).getFirstElement();
			}
		}
		if (selected == null) {
			return;
		}

		EditingDomain dom = AgteleEcoreUtil.getEditingDomainFor(selected);
		
		EAttribute feature = null;
		IItemPropertyDescriptor featurePropertyDescriptor = null;
		IItemPropertySource adapter = (IItemPropertySource) this.adapterFactory.adapt(selected, IItemPropertySource.class);
		
		if (adapter == null) {
			return;
		}
		
		for (IItemPropertyDescriptor descriptor : adapter.getPropertyDescriptors(selected)) {
			if (descriptor.isMultiLine(null)) {
				if (feature == null) {
					feature = (EAttribute) descriptor.getFeature(null);		
					featurePropertyDescriptor = descriptor; 
				} else {
					return;
				}
			}
		}
		
		if (feature == null) {
			return;
		}
		
		String oldValue = (String) selected.eGet(feature);
		if (oldValue == null) {
			oldValue = "";
		}
		String newValue = null;

		InputDialog dialog = new MultiLineInputDialog(UIHelper.getShell(),
				"Set value of " + featurePropertyDescriptor.getDisplayName(null) + " property", "Set " + featurePropertyDescriptor.getDisplayName(null) + " Value", oldValue, null);
		if (dialog.open() == Window.OK) {
			newValue = dialog.getValue();
		} else {
			return;
		}

		SetCommand command = null;

		if (!oldValue.equals(newValue)) {
			command = new SetCommand(dom, selected, feature, newValue);
			dom.getCommandStack().execute(command);
		}
	}

	public void dispose() {
		this.selectionViewer.removeDoubleClickListener(this);		
	}

}
