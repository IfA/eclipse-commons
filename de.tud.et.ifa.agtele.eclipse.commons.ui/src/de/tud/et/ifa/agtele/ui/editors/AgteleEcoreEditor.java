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
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IPartListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.part.FileEditorInput;

import de.tud.et.ifa.agtele.ui.AgteleUIPlugin;
import de.tud.et.ifa.agtele.ui.interfaces.IPersistable;
import de.tud.et.ifa.agtele.ui.widgets.TreeViewerGroup;

/**
 * An enhanced version of the Sample (Reflective) Ecore Model Editor
 *
 * @author cmartin
 */
public class AgteleEcoreEditor extends EcoreEditor implements IPersistable {

	private TreeViewerGroup tree;

	/**
	 * This listens for when the editor is closed or opened and enables persisting of the UI state
	 *
	 */
	protected IPartListener persistPartListener = new IPartListener() {

		@Override
		public void partActivated(IWorkbenchPart p) {
			// Ignore.
		}

		@Override
		public void partBroughtToTop(IWorkbenchPart p) {
			// Ignore.
		}

		@Override
		public void partClosed(IWorkbenchPart p) {
			if (p == AgteleEcoreEditor.this && AgteleEcoreEditor.this.getEditorInput() instanceof FileEditorInput) {
				AgteleEcoreEditor.this.doPersist();
			}
		}

		@Override
		public void partDeactivated(IWorkbenchPart p) {
			// Ignore.
		}

		@Override
		public void partOpened(IWorkbenchPart p) {
			if (p == AgteleEcoreEditor.this && AgteleEcoreEditor.this.getEditorInput() instanceof FileEditorInput) {

				// Restore the UI state
				//
				IDialogSettings settings = AgteleUIPlugin.getPlugin().getDialogSettings();
				IDialogSettings section = settings.getSection("UI_STATE");
				if (section != null) {
					String pamtramFile = ((FileEditorInput) AgteleEcoreEditor.this.getEditorInput()).getFile()
							.toString();
					IDialogSettings project = section.getSection(pamtramFile);

					if (project != null) {
						AgteleEcoreEditor.this.restore(project);
					}
				}
			}
		}
	};

	/**
	 * This is called during startup. <!-- begin-user-doc --> <!-- end-user-doc -->
	 */
	@Override
	public void init(IEditorSite site, IEditorInput editorInput) {
		super.init(site, editorInput);

		site.getPage().addPartListener(this.persistPartListener);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 */
	@Override
	public void dispose() {
		this.getSite().getPage().removePartListener(this.persistPartListener);

		super.dispose();
	}

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
			this.tree = new TreeViewerGroup(this.getContainer(), this.adapterFactory, this.editingDomain,
					AgteleUIPlugin.getPlugin().getDialogSettings(), null, null, null, true, true);
			this.selectionViewer = this.tree.getViewer();
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
			int pageIndex = this.addPage(this.tree);
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

	@Override
	public void persist(IDialogSettings settings) {

		// persist the active page
		int index = this.getActivePage();
		settings.put("ACTIVE_PAGE", index);

		// persist the state of the pages displayed by the editor
		this.tree.persist(settings.addNewSection("MAIN_PAGE"));
	}

	@Override
	public void restore(final IDialogSettings settings) {
		// perform the restore operations in an asynchronous way
		try {
			this.getSite().getShell().getDisplay().asyncExec(() -> {
				// restore the active page
				int index = settings.getInt("ACTIVE_PAGE");
				AgteleEcoreEditor.this.setActivePage(index);

				// restore the state of the pages displayed by the editor
				IDialogSettings page = settings.getSection("MAIN_PAGE");
				if (page != null) {
					this.tree.restore(page);
				}
			});
		} catch (Exception e) {
			// do nothing
		}
	}

	/**
	 *
	 */
	protected void doPersist() {
		// Save the UI state
		//
		IDialogSettings settings = AgteleUIPlugin.getPlugin().getDialogSettings();
		IDialogSettings section = settings.getSection("UI_STATE");
		if (section == null) {
			section = settings.addNewSection("UI_STATE");
		}
		String ecoreFile = ((FileEditorInput) this.getEditorInput()).getFile().toString();
		IDialogSettings project = settings.getSection(ecoreFile);
		if (project == null) {
			project = section.addNewSection(ecoreFile);
		}
		AgteleEcoreEditor.this.persist(project);
	}
}
