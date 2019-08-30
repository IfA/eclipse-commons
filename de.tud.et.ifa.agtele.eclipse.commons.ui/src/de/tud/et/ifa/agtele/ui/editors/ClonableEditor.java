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
package de.tud.et.ifa.agtele.ui.editors;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.EMFPlugin;
import org.eclipse.emf.common.command.BasicCommandStack;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CommandStack;
import org.eclipse.emf.common.command.CommandStackListener;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.actions.WorkspaceModifyOperation;
import org.eclipse.ui.ide.FileStoreEditorInput;
import org.eclipse.ui.part.MultiPageEditorPart;
import org.eclipse.ui.views.properties.PropertySheetPage;

import de.tud.et.ifa.agtele.emf.edit.AgteleAdapterFactoryEditingDomain;

public abstract class ClonableEditor extends MultiPageEditorPart{
	/**
	 * stores the editing domains per file path in order to be able to reuse it for clonable model editors
	 */
	protected static Map<String, AdapterFactoryEditingDomain> editingDomains = new HashMap<>();

	/**
	 * stores each opened editor instance by editing domain
	 */
	protected static Map<AdapterFactoryEditingDomain, List<ClonableEditor>> editingDomainShare = new HashMap<>();

	protected CommandStackListener commandStackListener;

	/**
	 * This creates a model editor.
	 * Do not call initializeEditingDomain after the invokation of this constructor
	 */
	protected ClonableEditor() {
		super();
		this.initializeEditingDomainGen();
	}

	/**
	 * This sets up the editing domain for the model editor.
	 */
	protected abstract void initializeEditingDomain();

	protected abstract Viewer getCurrentViewer();
	protected abstract void setSelectionToViewer(Collection<?> affectedObjects);
	protected abstract List<PropertySheetPage> getPropertySheetPages();
	protected abstract ComposedAdapterFactory internalGetAdapterFactory();
	protected abstract boolean isPersisted(Resource resource);
	protected abstract Map<Resource, Diagnostic> getResourceToDiagnosticMap();
	protected abstract void setUpdateProblemIndication(boolean updateProblemIndication);
	/**
	 * Return the Plugin, to where the log should be written.
	 * @return
	 */
	protected abstract EMFPlugin getPlugin();
	protected abstract void updateProblemIndication();
	protected abstract Diagnostic analyzeResourceProblems(Resource resource, Exception exception);
	/**
	 * @return the removedResources
	 */
	protected abstract Collection<Resource> getRemovedResources();

	/**
	 * @param removedResources the removedResources to set
	 */
	protected abstract void setRemovedResources(Collection<Resource> removedResources);

	/**
	 * @return the changedResources
	 */
	protected abstract Collection<Resource> getChangedResources();

	/**
	 * @param changedResources the changedResources to set
	 */
	protected abstract void setChangedResources(Collection<Resource> changedResources);

	/**
	 * @return the savedResources
	 */
	protected abstract Collection<Resource> getSavedResources();

	/**
	 * @param savedResources the savedResources to set
	 */
	protected abstract void setSavedResources(Collection<Resource> savedResources);

	/**
	 * @param adapterFactory the adapterFactory to set
	 */
	protected abstract void setAdapterFactory(ComposedAdapterFactory adapterFactory);

	/**
	 * @param editingDomain the editingDomain to set
	 */
	protected abstract void setEditingDomain(AdapterFactoryEditingDomain editingDomain);

	/**
	 * @return the editingDomain
	 */
	protected abstract AdapterFactoryEditingDomain getEditingDomain();


	/**
	 * @return the resourceChangeListener
	 */
	protected abstract IResourceChangeListener getResourceChangeListener();

	/**
	 * @param resourceChangeListener the resourceChangeListener to set
	 */
	protected abstract void setResourceChangeListener(IResourceChangeListener resourceChangeListener);

	/**
	 * recreates the resource listener and stores it in a class variable
	 * needs also to recreate the editing domain and the command stack in order to attach the newly created resource listener.
	 * the adapter factory can be reused.
	 */
	protected void initializeEditingDomainGen() {
		this.initializeEditingDomain();
		// Create the command stack that will notify this editor as commands are executed.

		BasicCommandStack commandStack = new BasicCommandStack();

		this.commandStackListener = event -> ClonableEditor.this.getContainer().getDisplay().asyncExec
				(() -> {
					ClonableEditor.this.firePropertyChange(IEditorPart.PROP_DIRTY);

					// Try to select the affected objects.
					//
					Command mostRecentCommand = ((CommandStack)event.getSource()).getMostRecentCommand();
					if (mostRecentCommand != null && ClonableEditor.this.getCurrentViewer() != null && ClonableEditor.this.getCurrentViewer().getControl().isFocusControl()) {
						ClonableEditor.this.setSelectionToViewer(mostRecentCommand.getAffectedObjects());
					}
					for (Iterator<PropertySheetPage> i = ClonableEditor.this.getPropertySheetPages().iterator(); i.hasNext(); ) {
						PropertySheetPage propertySheetPage = i.next();
						if (propertySheetPage.getControl().isDisposed()) {
							i.remove();
						}
						else {
							propertySheetPage.refresh();
						}
					}
				});

		// Add a listener to set the most recent command's affected objects to be the selection of the viewer with focus.
		//
		commandStack.addCommandStackListener(this.commandStackListener);


		// Create the editing domain with a special command stack.
		//
		this.setEditingDomain(new AgteleAdapterFactoryEditingDomain(this.internalGetAdapterFactory(), commandStack,
				new HashMap<Resource, Boolean>()));
	}


	/**
	 * This is for implementing {@link IEditorPart} and simply saves the model file.
	 * Don't do anything in the sub class.
	 */
	@Override
	public void doSave(IProgressMonitor progressMonitors) {
		this.doSave(progressMonitors, null);
	}

	/**
	 * This is for implementing {@link IEditorPart} and simply saves the model file.
	 * Don't do anything in the sub class.
	 */
	public void doSave(IProgressMonitor progressMonitor, Map<Object, Object> options) {
		// Save only resources that have actually changed.
		//
		final Map<Object, Object> saveOptions = options == null ? new HashMap<>() : options;
		saveOptions.put(Resource.OPTION_LINE_DELIMITER, Resource.OPTION_LINE_DELIMITER_UNSPECIFIED);

		// Do the work within an operation because this is a long running activity that modifies the workbench.
		//
		WorkspaceModifyOperation operation =
				new WorkspaceModifyOperation() {
			// This is the method that gets invoked when the operation runs.
			//
			@Override
			public void execute(IProgressMonitor monitor) {
				// Save the resources to the file system.
				//
				boolean first = true;
				for (Resource resource : ClonableEditor.this.getEditingDomain().getResourceSet().getResources()) {
					if ((first || !resource.getContents().isEmpty() || ClonableEditor.this.isPersisted(resource)) && !ClonableEditor.this.getEditingDomain().isReadOnly(resource)) {
						try {
							long timeStamp = resource.getTimeStamp();
							resource.save(saveOptions);
							if (resource.getTimeStamp() != timeStamp) {
								ClonableEditor.this.getSavedResources().add(resource);
							}
						}
						catch (Exception exception) {
							ClonableEditor.this.getResourceToDiagnosticMap().put(resource, ClonableEditor.this.analyzeResourceProblems(resource, exception));
						}
						first = false;
					}
				}
			}
		};

		this.setUpdateProblemIndication(false);
		try {
			// This runs the options, and shows progress.
			//
			new ProgressMonitorDialog(this.getSite().getShell()).run(true, false, operation);

			// Refresh the necessary state.
			//
			((BasicCommandStack)this.getEditingDomain().getCommandStack()).saveIsDone();

			this.updateDirtyState();

		}
		catch (Exception exception) {
			// Something went wrong that shouldn't.
			//
			this.getPlugin().log(exception);
		}
		this.setUpdateProblemIndication(true);
		this.updateProblemIndication();
	}

	protected void updateDirtyState() {
		//need to fire the property change on each editor in order to update the part dirty state properly
		for (ClonableEditor editor : ClonableEditor.editingDomainShare.get(this.getEditingDomain())) {
			editor.firePropertyChange(IEditorPart.PROP_DIRTY);
		}
	}

	@Override
	protected void firePropertyChange(int propertyId) {
		super.firePropertyChange(propertyId);
	}

	@Override
	public boolean isSaveAsAllowed() {
		Collection<?> c = ClonableEditor.editingDomainShare.get(this.getEditingDomain());
		return c == null || c.size() <= 1;
	}

	/**
	 * searches for an already existing editing domain for the resource path,
	 * fetches the editor instance from there and shares the editing domain
	 * @param path The path of the resource.
	 */
	protected void findEditingDomain (String path) {
		if (ClonableEditor.editingDomains.get(path) == null) {
			//I am a new editor instance, update the editing domain registry
			ClonableEditor.editingDomains.put(path, this.getEditingDomain());
			List<ClonableEditor> editors = new ArrayList<>();
			editors.add(this);
			ClonableEditor.editingDomainShare.put(this.getEditingDomain(), editors);
			ResourcesPlugin.getWorkspace().addResourceChangeListener(this.getResourceChangeListener(), IResourceChangeEvent.POST_CHANGE);
			return;
		}

		//there is already an existing editor instance, so reuse it.
		AdapterFactoryEditingDomain oldEditingDomain = this.getEditingDomain();
		AdapterFactoryEditingDomain editingDomain = ClonableEditor.editingDomains.get(path);
		ClonableEditor.editingDomainShare.get(editingDomain).add(this);
		oldEditingDomain.getCommandStack().removeCommandStackListener(this.commandStackListener);

		editingDomain.getCommandStack().addCommandStackListener(this.commandStackListener);

		ClonableEditor anotherEditor = ((ArrayList<ClonableEditor>)ClonableEditor.editingDomainShare.get(editingDomain)).get(0);
		this.setEditingDomain(editingDomain);
		this.setChangedResources(anotherEditor.getChangedResources());
		this.setRemovedResources(anotherEditor.getRemovedResources());
		this.setSavedResources(anotherEditor.getSavedResources());
		this.setResourceChangeListener(anotherEditor.getResourceChangeListener());
	}

	/**
	 * This is called during startup.
	 *
	 * Do not add a ResourceChangeListener to the Workspace.
	 */
	@Override
	public void init(IEditorSite site, IEditorInput editorInput) {
		if (editorInput instanceof IFileEditorInput) {
			this.findEditingDomain(((IFileEditorInput)editorInput).getFile().toString());			
		} else if (editorInput instanceof FileStoreEditorInput) {
			this.findEditingDomain(((FileStoreEditorInput)editorInput).getURI().toString());
		}
	}

	@Override
	public void setInputWithNotify(IEditorInput input) {
		super.setInputWithNotify(input);
	}

	@Override
	protected void setPartName(String partName) {
		super.setPartName(partName);
	}

	/**
	 * removes this editor from the editing domain and updates the editing domain registry
	 * in case this editor is the last editor opened for the editing domain, it will dispose everything
	 */
	protected void disposeFromEditorRegistry () {
		this.getEditingDomain().getCommandStack().removeCommandStackListener(this.commandStackListener);

		//clear the static editor registry from this editor and dispose adapterFacory, if this is the last editor for an editing domain
		Collection<?> c = ClonableEditor.editingDomainShare.get(this.getEditingDomain());
		c.remove(this);
		if (c.isEmpty()) {
			ClonableEditor.editingDomainShare.remove(this.getEditingDomain());
			for (Entry<String, AdapterFactoryEditingDomain> entry : ClonableEditor.editingDomains.entrySet()) {
				if (entry.getValue() == this.getEditingDomain()) {
					ClonableEditor.editingDomains.remove(entry.getKey());
					break;
				}
			}
			ResourcesPlugin.getWorkspace().removeResourceChangeListener(this.getResourceChangeListener());
			this.internalGetAdapterFactory().dispose();
		}
	}

	/**
	 * Do not call dispose on the adapterFactory yourself.
	 * Do not remove resourceChangeListeners.
	 */
	@Override
	public void dispose() {
		this.disposeFromEditorRegistry();
		if (Display.getCurrent() != null) {
			super.dispose();
		}
	}
	
	public List<ClonableEditor> getEditorInstances() {
		if (this.getEditingDomain() != null && ClonableEditor.editingDomainShare.get(this.getEditingDomain()) != null) {
			return ClonableEditor.editingDomainShare.get(this.getEditingDomain());
		}
		return Collections.emptyList();
	}
}
