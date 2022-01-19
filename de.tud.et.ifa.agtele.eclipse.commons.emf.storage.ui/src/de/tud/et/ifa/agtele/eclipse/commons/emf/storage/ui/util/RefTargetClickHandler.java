package de.tud.et.ifa.agtele.eclipse.commons.emf.storage.ui.util;

import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.jface.viewers.ITreeSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;

import de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.IModelContributor;
import de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.IResolveResult;
import de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.Model;
import de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.ModelStorage;
import de.tud.et.ifa.agtele.eclipse.commons.emf.storage.ui.util.RefTargetResolveCache.CacheEntry;
import de.tud.et.ifa.agtele.ui.listeners.SelectionListener2;

public class RefTargetClickHandler implements SelectionListener2 {
	/**
	 * The modifier mask that selects events with the Ctrl-Button pressed;
	 */
	private final int modifierMask = SWT.CTRL;
	
	
	protected ResourceSet resourceSet;
	protected RefTargetResolveCache refTargetCache;
	protected TreeViewer treeViewer;
	protected Map<Object, Object> loadOptions;
	protected ReferenceResolvingLabelProvider labelProvider;
	

	protected ConcurrentLinkedQueue<String> requestedIds = new ConcurrentLinkedQueue<>();

	@SuppressWarnings("unused")
	private RefTargetClickHandler() {};
	
	public RefTargetClickHandler(TreeViewer treeViewer, ResourceSet set, Map<Object, Object> loadOptions, ModelStorage refTargetResolveModelStorage, ReferenceResolvingLabelProvider labelProvider) {
		this.treeViewer = treeViewer;
		this.resourceSet = set;
		this.labelProvider = labelProvider;
		this.refTargetCache = RefTargetResolveCache.getInstance(refTargetResolveModelStorage);
		this.loadOptions = loadOptions;
	}
	
	@Override
	public void widgetSelected(SelectionEvent e) {
		if (this.treeViewer == null || this.treeViewer.getTree().isDisposed()) {
			return;
		}

		if ((e.stateMask & this.modifierMask) == 0 || !(e.item instanceof TreeItem)) {
			return;
		}

		Object selectedElement = ((TreeItem) e.item).getData();

		if (selectedElement == null) {
			return;
		}

		ITreeSelection previousSelection = this.treeViewer.getStructuredSelection();

		// Check if the user selected a single element
		//
		if (previousSelection.size() > 1) {
			return;
		}
		if (!previousSelection.isEmpty() && !selectedElement.equals(previousSelection.getFirstElement())) {
			return;
		}
		
		if (selectedElement instanceof EObject && this.labelProvider.isReferencingElement(selectedElement)) {
			this.handleReferenceClick((EObject)selectedElement);
		}
		
//		if (selectedElement instanceof Identifier && ((Identifier)selectedElement).isRefTarget()) {
//			this.handleRefTargetIdentifier((Identifier)selectedElement);
//		} else if (selectedElement instanceof Reference && !((Reference)selectedElement).getRefTarget().isEmpty()) {
//			this.handleReference((Reference)selectedElement);
//		}
		
	}
	
	protected void handleNonResolved (EObject element, List<String> targetIds) {
		for (String id : targetIds) {
			CacheEntry entry = this.labelProvider.getCachedEntry(id, true);
			if (entry != null) {
				this.handleCachedTarget(entry);
				return;
			}
		}
	}
	
	protected void handleReferenceClick (EObject obj) {
		List<String> targetIds = this.labelProvider.getReferenceTargetIds(obj);
		if (targetIds == null || targetIds.isEmpty()) {
			return;
		}
		List<IResolveResult> resolved = ReferenceResolvingLabelProvider.resolve(targetIds);
		if (resolved == null || resolved.isEmpty()) {
			this.handleNonResolved(obj, targetIds);
			return;
		}
		IResolveResult target = this.labelProvider.pickBestResolveResult(
				obj,
				resolved, 
				this.resourceSet);
		if (target != null) {
			this.handleResolvedTarget(target);
		} else {
			this.handleNonResolved(obj, targetIds);
		}
	}
	
	protected void handleResolvedTarget(IResolveResult target) {
		if (target.getElement().eResource().getResourceSet() == this.resourceSet) {
			this.handleTreeViewerTarget(target);
		} else {
			this.handleContributorTarget(target);
		}
	}

	protected void handleTreeViewerTarget(IResolveResult target) {
		EObject element = target.getElement();
		element = this.labelProvider.getRefTargetLabelElement(element);
		try {
			this.requestedIds.remove(target.getId());
			this.treeViewer.setSelection(new StructuredSelection(element));
		} catch (Exception e) {
			//Do nothing
		}
	}
	
	protected void handleContributorTarget(IResolveResult target) {
		IModelContributor contributor = this.pickBestContributor(target);
		if (contributor != null) {
			EObject element = target.getElement();
			element = this.labelProvider.getRefTargetLabelElement(element);
			this.requestedIds.remove(target.getId());
			try {
				contributor.requestFocus(element);
			} catch (Exception e) {
				//Do nothing
			}
		}
	}
	
	protected void handleCachedTarget(CacheEntry entry) {
		Set<String> projectResources = new HashSet<>();
		Set<String> otherResources = new HashSet<>();
		
		for(String res : entry.getResources()) {
			if (res.startsWith("platform:/resource")) {
				projectResources.add(res);
			} else {
				otherResources.add(res);
			}
		}
		
		if (!otherResources.isEmpty()) {
			for (String res : otherResources) {
				Model anyModel = ModelStorage.DEFAULT_INSTANCE.resolveModelGlobal(res);
				if (anyModel != null) {
					if (anyModel.isInitialized()) {
						this.refTargetCache.removeEntry(entry.getId(), res);
						continue;
					}
					
					for (IModelContributor c : ModelStorage.DEFAULT_INSTANCE.getContributors(anyModel)) {
						if (c.requestLoad(anyModel, entry.getId())) {
							c.requestFocus();
							return;
						}
					}	
				}
							
			}			
		}
		// try to find Contributor for other resources and request to load it first, since the resource could be loaded in a workbench view
		if (!projectResources.isEmpty()) {
			//open Dialog in order to select the resource to load and the target
			RefTargetResourceLoadSelectionDialog dialog = new RefTargetResourceLoadSelectionDialog(projectResources, new DialogResult(this.refTargetCache, entry.getId()));
			dialog.open();			
		}
	}
		
	protected void handleId(String id, String selectedResource) {
		if (this.requestedIds.contains(id)) {
			this.requestedIds.remove(id);
			return;
		}
		this.requestedIds.add(id);
		
		IResolveResult target = this.labelProvider.pickBestResolveResult(
				null,
				ReferenceResolvingLabelProvider.resolve(id), 
				this.resourceSet);
		if (target != null) {
			this.handleResolvedTarget(target);
		} else {
			//A possible resource has been opened, but does not contain the requested id anymore
			this.refTargetCache.removeEntry(id, selectedResource);
			
			CacheEntry entry = this.labelProvider.getCachedEntry(id, true);
			if (entry != null) {
				this.handleCachedTarget(entry);
			}
		}		
	}
	
	protected IModelContributor pickBestContributor (IResolveResult result) {
		if (result.getContributors().isEmpty()) {
			return null;
		}
		for (Class<?> cls : this.labelProvider.getPreferredContributorClasses()) {			
			//pick contributor that is an i40 component editor
			for (IModelContributor c : result.getContributors()) {
				if (cls.isInstance(c)) {
					return c;
				}
			}
		}
		
		//pick an editor that is a workbench part
		for (IModelContributor c : result.getContributors()) {
			if (c.isWorkbenchPart()) {
				return c;
			}
		}
		return null;
	}
		
	protected class DialogResult extends RefTargetResourceLoadSelectionDialog.Result {
		
		protected String id;
		protected RefTargetResolveCache cache;

		protected DialogResult (RefTargetResolveCache cache, String id) {
			this.id = id;
			this.cache = cache;
		}
		
		@Override
		public void dialogClosed() {
			for (String res : this.resourcesToRemove) {
				this.cache.removeResource(res);
			}
			if (this.openInCurrentEditor) {
				Resource newRes = RefTargetClickHandler.this.resourceSet.createResource(URI.createURI(this.selectedResource));
				try {
					newRes.load(RefTargetClickHandler.this.loadOptions);
					
					Display.getDefault().asyncExec(new Runnable() {
						@Override
						public void run() {
							RefTargetClickHandler.this.handleId(DialogResult.this.id, DialogResult.this.selectedResource);								
						}
						
					});
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (this.openInNewEditor) {						
				IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage(); 
	    		URI uri = URI.createURI(this.selectedResource);    
				IFile file = ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(uri.toPlatformString(true)));
				IEditorDescriptor desc = PlatformUI.getWorkbench().
				        getEditorRegistry().getDefaultEditor(file.getName());
				Display.getDefault().asyncExec(new Runnable() {
					@Override
					public void run() {
						try {
							page.openEditor(new FileEditorInput(file), desc.getId());
							
							Display.getDefault().asyncExec(new Runnable() {
								@Override
								public void run() {
									RefTargetClickHandler.this.handleId(DialogResult.this.id, DialogResult.this.selectedResource);								
								}
								
							});
						} catch (PartInitException e) {
							e.printStackTrace();
						}								
					}							
				});
			}					
		}				
	}
}
