package de.tud.et.ifa.agtele.eclipse.commons.emf.storage.ui.views;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.epsilon.dt.epackageregistryexplorer.PackageRegistryContentProvider;
import org.eclipse.epsilon.dt.epackageregistryexplorer.PackageRegistryExplorerView;
import org.eclipse.epsilon.dt.epackageregistryexplorer.SubTypesDescriptor;
import org.eclipse.epsilon.dt.epackageregistryexplorer.SuperTypesDescriptor;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DragSourceListener;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.FilteredTree;
import org.eclipse.ui.dialogs.PatternFilter;

import de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.IModelContributor;
import de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.IResolveResult;
import de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.LinkedModel;
import de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.Model;
import de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.ModelStorage;
import de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.ModelStorageFactory;
import de.tud.et.ifa.agtele.emf.AgteleEcoreUtil;
import de.tud.et.ifa.agtele.resources.BundleContentHelper;
import de.tud.et.ifa.agtele.ui.listeners.ReferencingIdentifierDragSourceListener;
import de.tud.et.ifa.agtele.ui.util.ReferencingIdentifierTransfer;

public class EPackageBrowserView extends PackageRegistryExplorerView implements IModelContributor {
	
	protected FilteredTree filteredTree;
	protected SashForm sashForm;
	protected ClassViewerPatternFilter filter;
	
	protected static final String E_PACKAGE_MODEL_URI = "http://et.tu-dresden.de/ifa/i40/EPackageRegistry/";
	protected static ModelStorage ePackageStorage = ModelStorageFactory.eINSTANCE.createModelStorage();
	protected static ResourceSet ePackageModelSet = new ResourceSetImpl();
	protected static LinkedModel ePackageModel = ePackageStorage.addLinkedModel(E_PACKAGE_MODEL_URI, ePackageModelSet);
	
	protected static synchronized void updateEPackageModelStorage (List<EPackage> packages) {
		if (!EPackageBrowserView.ePackageStorage.isInitialized()) {
			EPackageBrowserView.ePackageStorage.update();
		}
		EPackageBrowserView.ePackageModel.resetContent();
		for (EPackage pkg : packages) {			
			ePackageModel.registerIdentifyableElement(
					AgteleEcoreUtil.list(
							AgteleEcoreUtil.getEcoreElementReferencingIdentifier(pkg),
							AgteleEcoreUtil.getEcoreElementUri(pkg)),
					pkg);
			for (TreeIterator<EObject> it = pkg.eAllContents();it.hasNext();) {
				EObject n = it.next();
				String uri = AgteleEcoreUtil.getEcoreElementReferencingIdentifier(n);
				if (uri != null) {
					ePackageModel.registerIdentifyableElement(
							AgteleEcoreUtil.list(
									uri,
									AgteleEcoreUtil.getEcoreElementUri(n)),
							n);
				}
			}
		}
	}
	

	@SuppressWarnings("deprecation")
	@Override
	public void createPartControl(Composite parent) {
		super.createPartControl(parent);
		
		this.sashForm = (SashForm)parent.getChildren()[1];
		
		filter = new ClassViewerPatternFilter();
		this.filteredTree = new FilteredTree(this.sashForm, SWT.MULTI | SWT.H_SCROLL
				| SWT.V_SCROLL, filter, true) {
		};
		this.featureViewerForm.moveBelow(filteredTree);
		
		swapClassViewer();
		
		fixActionBars();
		
		addDragSupport(this.classViewer);
		addDragSupport(this.featureViewer);
	}

	private void addDragSupport(TreeViewer viewer) {
		viewer.addDragSupport(this.getDragOperations(), this.getTransferTypes(), this.getDragSourceListener(viewer));
	}

	private DragSourceListener getDragSourceListener(TreeViewer viewer) {
		return new ReferencingIdentifierDragSourceListener(viewer, null);
	}

	private int getDragOperations() {
		return DND.DROP_LINK;
	}

	private Transfer[] getTransferTypes() {
		return new Transfer[]{ReferencingIdentifierTransfer.getInstance()};	
	}

	protected void swapClassViewer() {
		TreeViewer newViewer = this.filteredTree.getViewer();
		newViewer.setLabelProvider(this.classViewer.getLabelProvider());
		newViewer.setContentProvider(new HierarchicalPackageRegistryContentProvider(this));
		newViewer.setComparator(this.classViewer.getComparator());
		newViewer.setInput(this.classViewer.getInput());

		newViewer.addSelectionChangedListener(new ClassViewerSelectionChangedListener());
		newViewer.addSelectionChangedListener(new ISelectionChangedListener() {

			public void selectionChanged(SelectionChangedEvent event) {				
				if (backRunning) return;
				
				TreeSelection selection = (TreeSelection) event.getSelection();
				
				if (selection.getPaths().length == 0) return;
				
				if (history.size() == 0) {
					history.add(0, selection.getPaths()[0]);
				}
				else if (history.get(0) != selection.getPaths()[0]) {
					history.add(0, selection.getPaths()[0]);
				}
			}			
		});
		
		this.sashForm.getChildren()[0].dispose();
		this.classViewer = newViewer;
	}	

	protected void fixActionBars () {
		IActionBars bars = getViewSite().getActionBars();
		bars.getToolBarManager().removeAll();
		bars.getToolBarManager().add(new BackAction());
		bars.getToolBarManager().add(new RefreshAction());
		bars.getToolBarManager().add(new NewViewInstanceAction());
		
	}
	
	public static class ClassViewerPatternFilter extends PatternFilter {
		@Override
		public boolean isElementVisible(Viewer viewer, Object element) {
			if (element instanceof SuperTypesDescriptor || element instanceof SubTypesDescriptor) {
				return false;
			}
			return super.isElementVisible(viewer, element);
		}
	}

	protected class ClassViewerSelectionChangedListener implements ISelectionChangedListener {

		public void selectionChanged(SelectionChangedEvent event) {
			IStructuredSelection selection = (IStructuredSelection) event.getSelection();
			TreeItem[] selectedItems = classViewer.getTree().getSelection();
			if (selectedItems.length > 0) {
				selectedClassLabel.setText(selectedItems[0].getText());
				selectedClassLabel.setImage(selectedItems[0].getImage());
				notifySelectionChangedListeners(classViewer);
			}
			featureViewer.setInput(selection.getFirstElement());
		}
		
	}
	
	protected class NewViewInstanceAction extends Action {
		
		protected boolean running = false;
		
		public NewViewInstanceAction() {
			this.setText("New EPackage Browser View");
			this.setImageDescriptor(BundleContentHelper
					.getBundleImageDescriptor(org.eclipse.epsilon.dt.epackageregistryexplorer.Activator.PLUGIN_ID, "icons/newviewinstance.png"));
		}
		
		@Override
		public void run() {
			
			IWorkbenchPage activePage = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
			try {
				activePage.showView(EPackageBrowserView.ID, Math.random()*1000 +"", IWorkbenchPage.VIEW_ACTIVATE);
			}
			catch (Exception ex) {
				ex.printStackTrace();
			}
		}
			
	}

	protected class BackAction extends Action {
		
		protected boolean running = false;
		
		public BackAction() {
			this.setText("Back");
			this.setImageDescriptor(BundleContentHelper
					.getBundleImageDescriptor(org.eclipse.epsilon.dt.epackageregistryexplorer.Activator.PLUGIN_ID, "icons/back.png"));
		}
		
		@Override
		public void run() {
			
			backRunning = true;
			
			if (history.size() > 1) {
				classViewer.setSelection(new TreeSelection(history.get(1)));
				history.remove(1);
			}
			
			backRunning = false;
		}		
	}
	
	protected class RefreshAction extends Action {
		
		public RefreshAction() {
			this.setText("Refresh");
			this.setImageDescriptor(BundleContentHelper
					.getBundleImageDescriptor(org.eclipse.epsilon.dt.epackageregistryexplorer.Activator.PLUGIN_ID, "icons/refresh.gif"));
		}
		
		@Override
		public void run() {
			ArrayList<EPackage> ePackages = new ArrayList<EPackage>();
			
			//Avoid concurrent modification exceptions that can happen when iterating
			//directly over INSTANCE.values()
			ArrayList<Object> ePackageRegistryValues = new ArrayList<Object>();
			ePackageRegistryValues.addAll(EPackage.Registry.INSTANCE.values());
			
			for (Object o : ePackageRegistryValues) {
				if (o instanceof EPackage) {
					ePackages.add((EPackage)o);
				}
				else if (o instanceof EPackage.Descriptor) {
					try {
						ePackages.add(((EPackage.Descriptor) o).getEPackage());
					}
					catch (Exception ex) { 
						// Problematic package descriptor - ignore
					}
				}
			}
			if (EPackageBrowserView.this.ePackages == null || EPackageBrowserView.this.ePackages.isEmpty()) {
				EPackageBrowserView.ePackageStorage.addModelContributor(EPackageBrowserView.this);
			}
			EPackageBrowserView.this.ePackages = ePackages;
			EPackageBrowserView.updateEPackageModelStorage(EPackageBrowserView.this.ePackages);
			classViewer.refresh();
		}
	}
	
	public static class HierarchicalPackageRegistryContentProvider extends PackageRegistryContentProvider {

		public HierarchicalPackageRegistryContentProvider(PackageRegistryExplorerView view) {
			super(view);
		}
		
		@Override
		public Object[] getElements(Object inputElement) {
			return view.getEPackages().stream().filter(p -> {return p.getESuperPackage() == null;}).collect(Collectors.toList()).toArray();	
		}
		 @Override
		public Object[] getChildren(Object parentElement) {
			if (parentElement instanceof EPackage) {
				ArrayList<EObject>result = new ArrayList<>();
				result.addAll(((EPackage)parentElement).getESubpackages());
				result.addAll(((EPackage)parentElement).getEClassifiers());
				return result.toArray();
			}
			return super.getChildren(parentElement);
		}
	}

	@Override
	public Set<Model> getContributedModels() {
		return Collections.singleton(ePackageModel);
	}


	@Override
	public int getContributorPriority() {
		return 0;
	}


	@Override
	public ModelStorage getModelStorage() {
		return ePackageStorage;
	}


	@Override
	public boolean isWorkbenchPart() {
		return true;
	}

	@Override
	public void requestFocus() {
		Display.getCurrent().asyncExec(new Runnable (){
			@Override
			public void run() {
				EPackageBrowserView.this.getSite().getPage().activate(
						EPackageBrowserView.this.getSite().getPart()); 
				
				EPackageBrowserView.this.filteredTree.getViewer().getControl().setFocus();
			}	
		});
	}
	
	@Override
	public void requestFocus(EObject element) {
		this.requestSelect(element);
		this.requestFocus();
	}

	@Override
	public void requestSelect(EObject element) {
		if (element instanceof EPackage || element instanceof EClassifier) {
			ArrayList<EObject> ancestors = new ArrayList<>(AgteleEcoreUtil.getAllContainers(element));
			
			for (int i = ancestors.size()-1; i >= 0; i-=1) {
				this.classViewer.setExpandedState(ancestors.get(i), true);
			}
			
			this.classViewer.setSelection(new StructuredSelection(element), true);
		} else if (element instanceof EStructuralFeature) {
			this.requestSelect(element.eContainer());			
			this.featureViewer.setSelection(new StructuredSelection(element), true);
		}
	}
	
	@Override
	public boolean requestLoad(Model m, String id) {
		if (this.ePackages.isEmpty()) {
			RefreshAction action = new RefreshAction();
			action.run();
		}
		List<IResolveResult> results = EPackageBrowserView.ePackageModel.resolve(id);
		
		if (!results.isEmpty() ) {		
			IResolveResult firstResult = results.get(0);			
			this.requestFocus(firstResult.getElement());
		}
		
		return true;
	}	
}
