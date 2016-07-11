/**
 *
 */
package de.tud.et.ifa.agtele.ui.editors;

import org.eclipse.emf.common.ui.viewer.ColumnViewerInformationControlToolTipSupport;
import org.eclipse.emf.ecore.presentation.EcoreEditor;
import org.eclipse.emf.ecore.presentation.EcoreEditorPlugin;
import org.eclipse.emf.edit.ui.celleditor.AdapterFactoryTreeEditor;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.emf.edit.ui.provider.DecoratingColumLabelProvider;
import org.eclipse.emf.edit.ui.provider.DiagnosticDecorator;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;

import de.tud.et.ifa.agtele.ui.AgteleUIPlugin;
import de.tud.et.ifa.agtele.ui.widgets.TreeViewerGroup;

/**
 * An enhanced version of the Sample (Reflective) Ecore Model Editor
 *
 * @author cmartin
 */
public class AgteleEcoreEditor extends EcoreEditor {

	/**
	 * This is the method used by the framework to install your own controls. This is overwritten in order to use a
	 * different TreeViewer that allows filtering.
	 */
	@Override
	public void createPages() {
		// Creates the model from the editor input
		//
		this.createModel();

		// Only creates the other pages if there is something that can be edited
		//
		if (!this.getEditingDomain().getResourceSet().getResources().isEmpty()) {
			// Create a page for the selection tree view.
			//

			// Rather use the TreeViewerGroup instead of a standard Tree
			// Edited Section begin
			//
			TreeViewerGroup tree = new TreeViewerGroup(this.getContainer(), this.adapterFactory, this.editingDomain,
					AgteleUIPlugin.getPlugin().getDialogSettings(), null, null, null, true, true);
			this.selectionViewer = tree.getViewer();
			// Edited Section end
			//
			this.setCurrentViewer(this.selectionViewer);

			this.selectionViewer.setContentProvider(new AdapterFactoryContentProvider(this.adapterFactory));
			this.selectionViewer.setLabelProvider(new DecoratingColumLabelProvider(
					new AdapterFactoryLabelProvider(this.adapterFactory), new DiagnosticDecorator(this.editingDomain,
							this.selectionViewer, EcoreEditorPlugin.getPlugin().getDialogSettings())));
			this.selectionViewer.setInput(this.editingDomain.getResourceSet());
			this.selectionViewer.setSelection(
					new StructuredSelection(this.editingDomain.getResourceSet().getResources().get(0)), true);

			new AdapterFactoryTreeEditor(this.selectionViewer.getTree(), this.adapterFactory);
			new ColumnViewerInformationControlToolTipSupport(this.selectionViewer,
					new DiagnosticDecorator.EditingDomainLocationListener(this.editingDomain, this.selectionViewer));

			this.createContextMenuFor(this.selectionViewer);
			int pageIndex = this.addPage(tree);
			this.setPageText(pageIndex, "Ecore");

			this.getSite().getShell().getDisplay().asyncExec(() -> AgteleEcoreEditor.this.setActivePage(0));
		}

		// Ensures that this editor will only display the page's tab
		// area if there are more than one page
		//
		this.getContainer().addControlListener(new ControlAdapter() {

			boolean guard = false;

			@Override
			public void controlResized(ControlEvent event) {
				if (!this.guard) {
					this.guard = true;
					AgteleEcoreEditor.this.hideTabs();
					this.guard = false;
				}
			}
		});

		this.getSite().getShell().getDisplay().asyncExec(() -> AgteleEcoreEditor.this.updateProblemIndication());
	}
}
