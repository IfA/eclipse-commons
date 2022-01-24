package de.tud.et.ifa.agtele.eclipse.commons.emf.storage.ui.views;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;

import de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.IModelContributor;
import de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.Model;
import de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.ModelStorage;
import de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.importAdapter.ImportAdapter;
import de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.importAdapter.util.ImportAdapterAdapterFactory;
import de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.util.ModelStorageAdapterFactory;
import de.tud.et.ifa.agtele.eclipse.commons.emf.storage.ui.EMFStorageUIPlugin;
import de.tud.et.ifa.agtele.eclipse.commons.emf.storage.ui.util.GenericReferenceResolvingLabelProvider;
import de.tud.et.ifa.agtele.eclipse.commons.emf.storage.ui.util.RefTargetClickHandler;
import de.tud.et.ifa.agtele.eclipse.commons.emf.storage.ui.util.RefTargetResolveCache;
import de.tud.et.ifa.agtele.emf.AgteleEcoreUtil;
import de.tud.et.ifa.agtele.resources.BundleContentHelper;
import org.eclipse.jface.viewers.*;
import org.eclipse.jface.action.*;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.ui.*;
import org.eclipse.ui.views.properties.IPropertySheetPage;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.PropertySheet;
import org.eclipse.ui.views.properties.PropertySheetPage;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.graphics.Image;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.IJobChangeListener;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.runtime.jobs.JobGroup;
import org.eclipse.e4.ui.di.UISynchronize;
import org.eclipse.emf.common.EMFPlugin;
import org.eclipse.emf.common.command.BasicCommandStack;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.UnexecutableCommand;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.ui.ViewerPane;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EContentAdapter;
import org.eclipse.emf.edit.command.CommandParameter;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.ReflectiveItemProviderAdapterFactory;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.emf.edit.ui.provider.PropertySource;
import org.eclipse.emf.edit.ui.view.ExtendedPropertySheetPage;

import de.tud.et.ifa.agtele.ui.interfaces.IPersistable;

import javax.inject.Inject;


/**
 * This sample class demonstrates how to plug-in a new
 * workbench view. The view shows data obtained from the
 * model. The sample creates a dummy model on the fly,
 * but a real implementation would connect to the model
 * available either in this or another plug-in (e.g. the workspace).
 * The view is connected to the model using a content provider.
 * <p>
 * The view uses a label provider to define how model
 * objects should be presented in the view. Each
 * view can present the same model objects using
 * different labels and icons, if needed. Alternatively,
 * a single label provider can be shared between views
 * in order to ensure that objects of the same type are
 * presented in the same way everywhere.
 * <p>
 */

public class ModelStorageView extends MultiPageView implements IViewPart, IPersistable, IModelContributor {

	/**
	 * The ID of the view as specified by the extension.
	 */
	public static final String ID = "de.tud.et.ifa.agtele.eclipse.commons.emf.storage.ui.views.ModelStorageView";

	@Inject IWorkbench workbench;
	@Inject UISynchronize sync;	
	
	protected TreeViewer modelStoragesViewer;

	protected final ArrayList<ModelStorage> viewedModelStorages = new ArrayList<>();

	protected ComposedAdapterFactory adapterFactory;
	
	protected Map<Control, ViewerPane> controlToViewerPaneMap = new HashMap<>();

	protected IPartListener partListener =
			new IPartListener() {
				public void partActivated(IWorkbenchPart p) {
					if (p instanceof PropertySheet) {
						if (ModelStorageView.this.propertySheetPages.contains(((PropertySheet)p).getCurrentPage())) {
							ModelStorageView.this.handleActivate();
						}
					}
					else if (p == ModelStorageView.this) {
						ModelStorageView.this.setCurrentViewer(ModelStorageView.this.currentViewer);
						ModelStorageView.this.handleActivate();
					}
				}
				@Override
				public void partBroughtToTop(IWorkbenchPart p) {
					// Ignore.
				}
				@Override
				public void partClosed(IWorkbenchPart p) {
					if (p == ModelStorageView.this) {
						ModelStorageView.this.doPersist();
					}
				}
				@Override
				public void partDeactivated(IWorkbenchPart p) {
					// Ignore.
				}
				@Override
				public void partOpened(IWorkbenchPart p) {
					if (p == ModelStorageView.this) {

						// Restore the UI state
						//
						IDialogSettings settings = EMFStorageUIPlugin.getPlugin().getDialogSettings();
						IDialogSettings section = settings.getSection("UI_STATE");
						if (section != null) {
							ModelStorageView.this.restore(section);
						}
					}
				}
			};

	protected ArrayList<ModelStorageViewPage> additionalPages = new ArrayList<>();
	
	protected static List<ModelStorageView> viewInstances = new ArrayList<>();
	
	public ModelStorageView () {	
		super();
		this.initializeViewedModelStorages();
		this.initializeAdapterFactory();
		viewInstances.add(this);
	}
	
	protected int instanceId = 0;
		
	protected void doPersist() {
		// Save the UI state
		//
		IDialogSettings settings = EMFStorageUIPlugin.getPlugin().getDialogSettings();
		IDialogSettings section = settings.getSection("UI_STATE");
		if (section == null) {
			section = settings.addNewSection("UI_STATE");
		}
		
		IDialogSettings instance = section.getSection(String.valueOf(this.instanceId));
		if (instance == null) {
			instance = section.addNewSection(String.valueOf(this.instanceId));
		}
		this.persist(instance);
		for (ModelStorageViewPage page : this.additionalPages) {
			page.persist(instance);
		}
		
		EMFStorageUIPlugin.getPlugin().saveDialogSettings();
	}

	protected void handleActivate() {
		// TODO Auto-generated method stub
		
	}

	public Collection<ModelStorage> getViewedModelStorages() {
		return this.viewedModelStorages;
	}
	
	public void notifyUpdatedViewedModelStorages() {
		this.modelStoragesViewer.refresh();
	}
	
	public void addModelStorage(ModelStorage storage) {
		if (!this.viewedModelStorages.contains(storage)) {
			this.viewedModelStorages.add(storage);
			this.notifyUpdatedViewedModelStorages();
			storage.addModelContributor(this);
		}
	}
	
	public void initializeViewedModelStorages () {
		if (!this.viewedModelStorages.contains(ModelStorage.DEFAULT_INSTANCE)) {
			this.viewedModelStorages.add(ModelStorage.DEFAULT_INSTANCE);
			ModelStorage.DEFAULT_INSTANCE.addModelContributor(this);
		}
	
		ArrayList<String> defaultUris = new ArrayList<>();
		
		for (String uri : EMFStorageUIPlugin.getDefaultModelStorageConnectionUris()) {
			ModelStorage.DEFAULT_INSTANCE.addModel(uri);
			defaultUris.add(uri);
		}
//		ModelStorage.DEFAULT_INSTANCE.addModel("aas.emf.opc.tcp://EATP71:50300/pnp/demo/i40Component"); //debug //TRICORDER
//		defaultUris.add("aas.emf.opc.tcp://EATP71:50300/pnp/demo/i40Component"); //debug
		
		ArrayList<Model> toRemove = new ArrayList<>();
		for (Model model : ModelStorage.DEFAULT_INSTANCE.getModel()) {
			if (!defaultUris.contains(model.getUri())) {
				toRemove.add(model);
			}
		}
		ModelStorage.DEFAULT_INSTANCE.getModel().removeAll(toRemove);
	}
	
	protected void initializeAdapterFactory() {		
		adapterFactory = new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE);
		adapterFactory.addAdapterFactory(new ModelStorageAdapterFactory());
		adapterFactory.addAdapterFactory(new ImportAdapterAdapterFactory());
		adapterFactory.addAdapterFactory(new ReflectiveItemProviderAdapterFactory());
	}
		
	public void createPartControl(Composite parent) {
		super.createPartControl(parent);
				
		//workbench.getHelpSystem().setHelp(modelStoragesViewer.getControl(), "de.tud.et.ifa.agtele.eclipse.emf.storage.ui.viewer");
		//getSite().setSelectionProvider(viewer);
		
		makeActions();
		hookContextMenu();
		hookDoubleClickAction();
		contributeToActionBars();		
	}
	
	protected void createPages(Composite parent) {
		this.getSite().getPage().addPartListener(partListener);
		{
			ViewerPane viewerPane =
				new FilteredTreeViewerPane(getSite().getPage(), ModelStorageView.this);
			viewerPane.createControl(getContainer());
			
			modelStoragesViewer = (TreeViewer)viewerPane.getViewer();
			modelStoragesViewer.setContentProvider(new ModelStorageContentProvider(adapterFactory, this));
			
			ModelStorageLabelProvider labelProvider = new ModelStorageLabelProvider(new AdapterFactoryLabelProvider.StyledLabelProvider(this.adapterFactory,
							this.modelStoragesViewer), EMFStorageUIPlugin.INSTANCE, ModelStorage.DEFAULT_INSTANCE, RefTargetResolveCache.initializeInstance(null, null));
			
			modelStoragesViewer.setLabelProvider(labelProvider);
			
			//new AdapterFactoryLabelProvider(adapterFactory));
			modelStoragesViewer.setInput(this.getViewedModelStorages());
			addDragSupport(modelStoragesViewer, null);
			
			
			this.controlToViewerPaneMap.put(viewerPane.getControl(), viewerPane);
			

			RefTargetClickHandler refTargetClickHandler = new RefTargetClickHandler(
					modelStoragesViewer, 
					null, 
					Collections.emptyMap(), 
					ModelStorage.DEFAULT_INSTANCE,
					labelProvider);
			modelStoragesViewer.getTree().addSelectionListener(refTargetClickHandler);
			
			int pageIndex = addPage(viewerPane.getControl());
			setPageText(pageIndex, "Model Storages");
		}
		
		for (ModelStorageViewPage additionalPage : this.getRegisteredPages()) {
			this.additionalPages.add(additionalPage);
			ViewerPane viewerPane = additionalPage.getViewerPane(this);
			viewerPane.createControl(getContainer());

			additionalPage.configure();
			
			addDragSupport(viewerPane.getViewer(), additionalPage);
			
			this.controlToViewerPaneMap.put(viewerPane.getControl(), viewerPane);
			int pageIndex = addPage(viewerPane.getControl());
			setPageText(pageIndex, additionalPage.getPageText());
		}
				
		getSite().getShell().getDisplay().asyncExec
			(new Runnable() {
				 public void run() {
					 setActivePage(0);
				 }
			 });
	}
	
	protected void addDragSupport (Viewer viewer, ModelStorageViewPage page) {
		if (viewer instanceof TreeViewer) {			
			Transfer [] transferTypes = page != null ? page.getTransferTypes() : ModelStorageViewPage.getDefaultTransferTypes();
					
			((TreeViewer)viewer).addDragSupport(page != null ? page.getDNDOperations() : ModelStorageViewPage.getDefaultDNDOperations(), transferTypes, 
					page != null ? page.getDragSourceListener((TreeViewer)viewer) : ModelStorageViewPage.getDefaultDragSourceListener((TreeViewer)viewer, this.adapterFactory));		
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object getAdapter(@SuppressWarnings("rawtypes") Class key) {
		if (key.equals(IPropertySheetPage.class)) {
			return getPropertySheetPage();
		} 
		return super.getAdapter(key);
	}

	protected List<PropertySheetPage> propertySheetPages = new ArrayList<>();

	private IActionBars bars;

    public IPropertySheetPage getPropertySheetPage() {

		PropertySheetPage propertySheetPage = new ExtendedPropertySheetPage(
				new AdapterFactoryEditingDomain(adapterFactory, new BasicCommandStack()) {
					@Override
					public Command createCommand(Class<? extends Command> commandClass, CommandParameter commandParameter) {
						return UnexecutableCommand.INSTANCE;
					}
				},
				ExtendedPropertySheetPage.Decoration.LIVE, EMFStorageUIPlugin.getPlugin().getDialogSettings()) {

			@Override
			public void setSelectionToViewer(List<?> selection) {
//				ModelStorageView.this.setSelection(selection);
//				ClonableEcoreEditor.this.setFocus();
			}

			@Override
			public void setActionBars(IActionBars actionBars) {
//				super.setActionBars(actionBars);
//				getActionBarContributor().shareGlobalActions(this, actionBars);
			}
		};
		propertySheetPage.setPropertySourceProvider(new ModelStorageContentProvider(adapterFactory, this));
		propertySheetPages.add(propertySheetPage);

		return propertySheetPage;
	}
    
	public Viewer getViewer () {
		return this.modelStoragesViewer;
	}
	
	public static class ModelStorageLabelProvider extends GenericReferenceResolvingLabelProvider {

		protected TreeViewer selectionViewer;
		
		public ModelStorageLabelProvider(IStyledLabelProvider labelProvider, EMFPlugin emfPlugin, ModelStorage modelStorage, RefTargetResolveCache cache) {
			super(labelProvider, emfPlugin, modelStorage, cache);
			//remove listener not required, not dynamic updates
		}
				
		@Override
		public Image getImage(Object element) {
			return super.getImage(element);
		}

		@Override
		protected RefreshRunner getRefreshRunner() {
			return null;
		}
		@Override
		public EContentAdapter getReferenceTargetUpdateNotifier(Object element) {
			return null;
		}
		
		@Override
		public EContentAdapter getTargetNameUpdateNotifier() {
			return null;
		}
	}
	
	public static class ModelStorageContentProvider extends AdapterFactoryContentProvider {
		
		protected ModelStorageView view;
		protected Collection<ModelStorage> storages;

		private ModelStorageContentProvider(AdapterFactory adapterFactory) {
			super(adapterFactory);
		}		
		
		public ModelStorageContentProvider(AdapterFactory adapterFactory, ModelStorageView view) {
			super(adapterFactory);
			this.view = view;
			this.storages = view.getViewedModelStorages();
		}
		
		@Override
		public Object[] getElements(Object parent) {
			if (parent.equals(view.getViewSite())) {
				return super.getElements(parent);				
			}
			return this.storages.toArray(new Object[this.storages.size()]);
		}
		
		@Override
		public Object[] getChildren(Object object) {
			if (object instanceof Model) {
				Object[] result = super.getChildren(object);
				ArrayList<Object> list = new ArrayList<>(Arrays.asList(result));
				if (!((Model)object).getContent().isEmpty()) {
					list.addAll(((Model)object).getContent());					
				}
				list.removeIf(o -> {return o instanceof ImportAdapter;});
				return list.toArray();
			}
			if (object != this.storages) {
				return super.getChildren(object);		
			}
			return this.getElements(object);
		}		
		
		public boolean hasChildren(Object object) {	
			return super.hasChildren(object);	
		}
				
		@Override
		public IPropertySource getPropertySource(Object object) {
			PropertySource superResult = (PropertySource)super.getPropertySource(object); 
			if (superResult == null) {
				return null;
			}
			return new PropertySource(object, superResult.getItemPropertySource()) {
				public void setPropertyValue(Object propertyId, Object value) {
					//Do nothing
				};
//				
//				@Override
//				public IPropertyDescriptor[] getPropertyDescriptors() {
//					IPropertyDescriptor[] descriptors = super.getPropertyDescriptors(); 
//					IPropertyDescriptor[] result = new IPropertyDescriptor[descriptors.length];
//					for (int i = 0; i < descriptors.length; i+=1) {
//						//TODO create a ReadOnly Property Descriptor
//						result[i] = new PropertyDescriptor(((PropertyDescriptor)descriptors[i]).getObject(), ((PropertyDescriptor)descriptors[i]).getItemPropertyDescriptor()) {
//							@Override
//							public CellEditor createPropertyEditor(Composite composite) {
//								//TODO Create Readonly property editors
//								return super.createPropertyEditor(composite);
//							}
//							@Override
//							protected CellEditor createEDataTypeCellEditor(final EDataType eDataType, final IItemPropertyDescriptor.ValueHandler specializedValueHandler, Composite composite)
//							  {
//							    if (itemPropertyDescriptor.isMultiLine(object))
//							    {
//							      EDataTypeValueHandler valueHandler = new EDataTypeValueHandler(eDataType, specializedValueHandler);
//							      return new MultiLineEDataTypeCellEditor(
//							        eDataType,
//							        valueHandler,
//							        EMFEditUIPlugin.INSTANCE.getString("_UI_FeatureEditorDialog_title", new Object []{ getDisplayName(), getEditLabelProvider().getText(object) }),
//							        composite) {
//							    	  
//							      };
//							    }
//							    return specializedValueHandler == null
//							      ? new EDataTypeCellEditor(eDataType, composite) {
//							    		
//							    	}
//							    	: new EDataTypeCellEditor(eDataType, new EDataTypeValueHandler(eDataType, specializedValueHandler), composite) {
//							    		
//							    	};
//							    
//							  }
//						};
//					}
//					return result;
//				}
			};
		}
	}
			
	protected void hookContextMenu() {
		MenuManager menuMgr = new MenuManager("#PopupMenu");
		menuMgr.setRemoveAllWhenShown(true);
		menuMgr.addMenuListener(new IMenuListener() {
			public void menuAboutToShow(IMenuManager manager) {
				ModelStorageView.this.fillContextMenu(manager);
			}
		});
		menuMgr.add(new Separator("additions"));
		Menu menu = menuMgr.createContextMenu(modelStoragesViewer.getControl());
		modelStoragesViewer.getControl().setMenu(menu);
		getSite().registerContextMenu(menuMgr, modelStoragesViewer);
	}

	protected void contributeToActionBars() {
		if (this.bars == null) {
			bars = getViewSite().getActionBars();
		}
		fillLocalPullDown(bars.getMenuManager());
		fillLocalToolBar(bars.getToolBarManager());		
		bars.updateActionBars();
	}

	
	protected void fillLocalPullDown(IMenuManager manager) {
		manager.add(this.updateAllModelStorageAction);		
		manager.add(this.newViewInstanceAction);
		manager.add(this.updateOnStartupOption);
	}

	protected void fillContextMenu(IMenuManager manager) {
	}
	
	protected void fillLocalToolBar(IToolBarManager manager) {
		manager.add(this.updateModelStorageAction);
	}

	protected Action updateModelStorageAction = null;
	protected Action updateAllModelStorageAction = null;

	protected NewViewInstanceAction newViewInstanceAction;
	
	protected void handleUpdateModelStorageAction() {
		this.handleUpdateModelStorageAction(false);
	}
	
	protected synchronized void handleUpdateModelStorageAction(boolean all) {
		if (!this.updateModelStorageAction.isEnabled()) {
			return;
		}
		
		ISelection selection = this.getSelection();
		ViewerPane pane = this.controlToViewerPaneMap.get(this.getSelectedPage());
		if (pane == null) {
			return;
		}
		Viewer viewer = pane.getViewer();
		
		if (viewer == this.modelStoragesViewer) {
			this.initializeViewedModelStorages();
			//ModelStorage.DEFAULT_INSTANCE.addModel("emf.opc.tcp://EATP71:50300/pnp/demo/i40Component");
		}
		
		Object input = viewer.getInput();
		
		UpdateModelActionExecutor executor = new UpdateModelActionExecutor("Updating Model Storage") {
			@Override
			protected void allJobsDone() {
				sync.syncExec(() -> {
					try {
						ModelStorageView.this.updateModelStorageAction.setEnabled(true);	
						ModelStorageView.this.updateAllModelStorageAction.setEnabled(true);	
						pane.getViewer().refresh();
					} catch (Exception e) {
						//Do nothing, view might have been closed
					}
				});
			}
		};
		executor.init(all ? null: selection, input);
		
		if (executor.isWorkToDo()) {
			this.updateModelStorageAction.setEnabled(false);	
			this.updateAllModelStorageAction.setEnabled(false);	
			executor.schedule();
		}
	}
	
	protected static class UpdateModelActionExecutor extends Job {
		public UpdateModelActionExecutor(String name) {
			super(name);
		}

		ArrayList<ModelStorage> modelStoragesToUpdate = new ArrayList<>();
		HashMap<ModelStorage, ArrayList<Model>> modelsToUpdate = new LinkedHashMap<>();
		ArrayList<Job> jobs = new ArrayList<>();
		
		Object currentViewerInput = null;
		
		protected void init (ISelection selection, Object currentViewerInput) {
			this.currentViewerInput = currentViewerInput;
			if (selection == null || selection.isEmpty()) {				
				if (currentViewerInput instanceof Collection && !(currentViewerInput instanceof EObject)) {
					for (Object obj : (Collection<?>)currentViewerInput) {
						this.addSelectedElementToJobCollection(obj);
					}
				} else if (currentViewerInput != null) {
					this.addSelectedElementToJobCollection(currentViewerInput);
				}			
			} else if (selection instanceof IStructuredSelection) {
				IStructuredSelection sSelection = (IStructuredSelection) selection;
				for (Object obj : sSelection.toList()) {
					this.addSelectedElementToJobCollection(obj);
				}
			}
			this.generateJobs();
		}
		protected ModelStorage getModelStorageForModel(Model model) {
			try {				
				return (ModelStorage) AgteleEcoreUtil.getAllContainers(model).stream().filter(e -> {return e instanceof ModelStorage;}).findFirst().get();
			} catch (Exception e) {
				//Do nothing
			}	
			return null;
		}		
		protected void addSelectedElementToJobCollection(Object element) {
			if (element instanceof ModelStorage) {
				modelStoragesToUpdate.add((ModelStorage)element);
			} else if (element instanceof Model) {
				ModelStorage storage = this.getModelStorageForModel((Model)element);
				if (storage != null) {
					ArrayList<Model> list = new ArrayList<>();
					if (modelsToUpdate.containsKey(storage)) {
						list = modelsToUpdate.get(storage);
					} else {
						modelsToUpdate.put(storage, list);
					}
					list.add((Model)element);
				}
			} else if (element instanceof EObject) {
				try {				
					EObject parentElement = null;
					EObject lastParent = null;
					for (EObject e : AgteleEcoreUtil.getAllContainers((EObject)element)) {
						if ((e instanceof Model) || (e instanceof ModelStorage)) {
							parentElement = e;
						}
						lastParent = e;
					}
					
					if (parentElement != null) {
						this.addSelectedElementToJobCollection(parentElement);						
					} else {
						ArrayList<Model> modelsToSearchFor = new ArrayList<>();
						
						if (currentViewerInput instanceof Model) {
							modelsToSearchFor.add((Model)currentViewerInput);
						} else if (currentViewerInput instanceof ModelStorage) {
							modelsToSearchFor.addAll(((ModelStorage)currentViewerInput).getModel());
						}
						for (Model m : modelsToSearchFor) {
							for (Resource r : m.getResourceSet().getResources()) {
								if (r.getContents().contains(lastParent)) {
									this.addSelectedElementToJobCollection(m);
								}
							}
						}					
						
					}
					
				} catch (Exception e) {
					//Do nothing
				}			
			}
		}
		
		protected boolean isWorkToDo() {
			return !this.jobs.isEmpty();
		}
		
		protected void generateJobs () {
			for (ModelStorage storage : this.modelsToUpdate.keySet()) {
				if (this.modelStoragesToUpdate.contains(storage)) {
					this.modelsToUpdate.remove(storage);
				}
			}
			
//			ArrayList<Model> models = new ArrayList<>();
//			this.modelsToUpdate.values().forEach(c -> models.addAll(c));
			
			//TODO remove model storages that are currently updating
			this.modelStoragesToUpdate.removeIf(s -> s.isUpdating());
			for (ModelStorage storage : this.modelsToUpdate.keySet()) {
				if (storage.isUpdating()) {
					this.modelsToUpdate.remove(storage);
				}
			}
			
			int counter = 1;
			for (ModelStorage storage : this.modelStoragesToUpdate) {
				Job job = new Job("Update Model Storage " + counter + " of " + (this.modelStoragesToUpdate.size() + this.modelsToUpdate.size())) {
					@Override
					protected IStatus run(IProgressMonitor monitor) {
						storage.update();
						monitor.done();
						return Status.OK_STATUS;
					}
				};
				counter += 1;
				this.jobs.add(job);
			}
			for (ModelStorage storage : this.modelsToUpdate.keySet()) {
				Job job = new Job("Update selected Models of Model Storage " + counter + " of " + (this.modelStoragesToUpdate.size() + this.modelsToUpdate.size())) {
					@Override
					protected IStatus run(IProgressMonitor monitor) {
						storage.update(UpdateModelActionExecutor.this.modelsToUpdate.get(storage));
						monitor.done();
						return Status.OK_STATUS;
					}
				};
				counter += 1;
				this.jobs.add(job);
			}
		}
		
		protected int jobsDone = 0;
		private IProgressMonitor monitor;
		
		protected synchronized void setJobDone() {
			jobsDone += 1;
			if (jobsDone == this.jobs.size()) {
				this.done(Status.OK_STATUS);
				this.monitor.done();
				this.allJobsDone();
			}
		}
		
		protected void allJobsDone () {}
		
		protected IStatus run (IProgressMonitor monitor) {
			if (this.monitor != null) {
				return new Status(Status.ERROR, EMFStorageUIPlugin.PLUGIN_ID, "Can only execute task once.");
			}
			this.monitor = monitor;
			monitor.beginTask("Updating Models", this.jobs.size());
			JobGroup group = new JobGroup("Updating Stored Models", 0, this.jobs.size());	
			for (Job job : this.jobs) {
				job.setJobGroup(group);
				job.addJobChangeListener(new IJobChangeListener () {
					@Override
					public void aboutToRun(IJobChangeEvent event) {}
					@Override
					public void awake(IJobChangeEvent event) {}
					@Override
					public void done(IJobChangeEvent event) {
						monitor.worked(jobsDone);
						UpdateModelActionExecutor.this.setJobDone();					
					}
					@Override
					public void running(IJobChangeEvent event) {}

					@Override
					public void scheduled(IJobChangeEvent event) {}

					@Override
					public void sleeping(IJobChangeEvent event) {}					
				});
				job.schedule();
			}
			//this.status = new Status(Status., EMFStorageUIPlugin.PLUGIN_ID , this.getName());
			return Status.OK_STATUS;
		}
	}
	
	protected void makeUpdateModelStorageAction() {
		updateModelStorageAction = new Action() {
			public void run() {
				ModelStorageView.this.handleUpdateModelStorageAction();
			}
		};
		updateModelStorageAction.setText("Update Model Storage");
		updateModelStorageAction.setToolTipText("Updates the content of the model storage by connecting to the specified storage connectors.");
		updateModelStorageAction.setImageDescriptor(BundleContentHelper
				.getBundleImageDescriptor(EMFStorageUIPlugin.PLUGIN_ID, "icons/update.png"));
		
		this.updateAllModelStorageAction = new Action() {
			public void run() {
				ModelStorageView.this.handleUpdateModelStorageAction(true);
			}
		};
		updateAllModelStorageAction.setText("Update All");
		updateAllModelStorageAction.setToolTipText("Updates the content of the model storage visible in the current view by connecting to the specified storage connectors.");
		updateAllModelStorageAction.setImageDescriptor(BundleContentHelper
				.getBundleImageDescriptor(EMFStorageUIPlugin.PLUGIN_ID, "icons/update.png"));
		
	}
	
	protected void makeNewViewInstanceAction () {
		this.newViewInstanceAction=new NewViewInstanceAction();
	}
		
	protected Action updateOnStartupOption = null;
	
	protected void makeUpdateOnStartupOption() {
		updateOnStartupOption = new Action () {
			@Override
			public int getStyle() {
				return Action.AS_CHECK_BOX;
			}
		};
		updateOnStartupOption.setText("Udate on Startup");
		updateOnStartupOption.setToolTipText("Enabling this option triggers the model storage view to automatically updates its contents when the part is activated.");
		updateOnStartupOption.setImageDescriptor(BundleContentHelper
				.getBundleImageDescriptor(EMFStorageUIPlugin.PLUGIN_ID, "icons/update.png"));
		
	}
	
	protected void makeActions() {
		makeUpdateModelStorageAction();
		makeNewViewInstanceAction();
		makeUpdateOnStartupOption();
	}

	private void hookDoubleClickAction() {
	}

	public static final String MODEL_STORAGE_VIEW_PART_EXTENSION_POINT_ID = "de.tud.et.ifa.agtele.eclipse.commons.emf.storage.ui.ModelStorageViewPage";
	
	protected ArrayList<ModelStorageViewPage> getRegisteredPages () {
		ArrayList<ModelStorageViewPage> result = new ArrayList<>();
		
		IExtensionRegistry registry = Platform.getExtensionRegistry();
		
		IConfigurationElement[] elements
	      = registry.getConfigurationElementsFor( MODEL_STORAGE_VIEW_PART_EXTENSION_POINT_ID );
		
		for(IConfigurationElement element : elements) {
            try {
				final Object o =
						element.createExecutableExtension("class");
				if (o instanceof ModelStorageViewPage) {
					result.add((ModelStorageViewPage)o);					
				}
			} catch (CoreException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	@Override
	public void persist(IDialogSettings settings) {
		
		// persist the active page
		int index = this.getActivePage();
		settings.put("ACTIVE_PAGE", index);
		settings.put("AUTO_UPDATE_ON_STARTUP", this.updateOnStartupOption.isChecked());
		
		IDialogSettings page = settings.getSection("MAIN_PAGE");
		if (page == null) {
			page = settings.addNewSection("MAIN_PAGE");
		}
		
		page.put("EXPANDED_TREE_PATHS", new String[0]);
	}
	
	@Override
	public void restore(IDialogSettings settings) {
		// perform the restore operations in an asynchronous way
		try {
			this.getSite().getShell().getDisplay().asyncExec(() -> {
				ModelStorageView.this.instanceId = ModelStorageView.this.getInstanceId();
				IDialogSettings settingsToRestore = settings.getSection(String.valueOf(ModelStorageView.this.instanceId));
				
				if (settingsToRestore == null && ModelStorageView.this.instanceId <= 0) {
					settingsToRestore = settings;
				}
				
				if (settingsToRestore == null) {
					return;
				}
				
				// restore the active page
				int index = settingsToRestore.getInt("ACTIVE_PAGE");
				if (index >= 0 && index < ModelStorageView.this.getPageCount()) {
					ModelStorageView.this.setActivePage(index);
					//ModelStorageView.this.setCurrentViewerPane(ModelStorageView.this.getPane(ModelStorageView.this.getControl(index)));
					if (index > 0 && ModelStorageView.this.additionalPages.size() > index - 1) {
						ModelStorageView.this.additionalPages.get(index - 1).requestActivation();						
					}
				}
				
				boolean updateOnStartup = settingsToRestore.getBoolean("AUTO_UPDATE_ON_STARTUP");
				this.updateOnStartupOption.setChecked(updateOnStartup);				

				IDialogSettings mainPage = settingsToRestore.getSection("MAIN_PAGE");
				if (mainPage != null) {
					if (mainPage.getArray("EXPANDED_TREE_PATHS") != null) {
						mainPage.getArray("EXPANDED_TREE_PATHS");
						
//						ModelStorageView.this.modelStoragesViewer.setExpandedTreePaths(paths);						
					}
				}

				for (ModelStorageViewPage page : ModelStorageView.this.additionalPages) {
					page.restore(settingsToRestore);
				}
				
				if(updateOnStartup) {
					this.updateAllModelStorageAction.run();
				}
			});
		} catch (Exception e) {
			// do nothing
		}
	}
	
	protected ModelStorageViewPage getViewPageForViewerPane (ViewerPane pane) {
		for (ModelStorageViewPage page : this.additionalPages) {
			if (pane == page.getViewerPane(this)) {
				return page;
			}
		}
		return null;
	}
	
	protected ModelStorageViewPage currentViewPage = null;
	
	@Override
	protected void pageChange(int newPageIndex) {
		super.pageChange(newPageIndex);
		Object selectedPage = getSelectedPage();
				
		if (currentViewPage != null) {
			currentViewPage.removeActionBarContributions(bars);
		}
		
		if (selectedPage != null) {
			this.currentViewerPane = this.controlToViewerPaneMap.get(selectedPage);
			this.currentViewer = this.currentViewerPane.getViewer();
			currentViewPage = this.getViewPageForViewerPane(this.currentViewerPane);
			if (currentViewPage != null) {
				currentViewPage.contributeToActionBars(bars);
			}
		}
		if (this.bars != null) {
			this.bars.updateActionBars();
		}
	}

	protected class NewViewInstanceAction extends Action {
		
		protected boolean running = false;
		
		public NewViewInstanceAction() {
			this.setText("New Model Storage View");
			this.setImageDescriptor(BundleContentHelper
					.getBundleImageDescriptor(EMFStorageUIPlugin.PLUGIN_ID, "icons/repositories.gif"));
		}
		
		@Override
		public void run() {
			
			IWorkbenchPage activePage = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
			try {
				activePage.showView(ModelStorageView.ID, Math.random()*1000 +"", IWorkbenchPage.VIEW_ACTIVATE);
			}
			catch (Exception ex) {
				ex.printStackTrace();
			}
		}
			
	}

	@Override
	ViewerPane getPane(Control control) {
		return this.controlToViewerPaneMap.get(control);
	}
	
	@Override
	public int getInstanceId() {
		return ModelStorageView.viewInstances.indexOf(this);
	}
	
	@Override
	public void dispose() {
		super.dispose();
		for (ModelStorageViewPage page : this.additionalPages ) {
			page.dispose();
		}
		ModelStorageView.viewInstances.remove(this);
	}

	@Override
	public Set<Model> getContributedModels() {
		Set<Model> result = new LinkedHashSet<>();
		
		for (ModelStorage s : this.viewedModelStorages) {
			result.addAll(s.getModel());
		}
		
		return result;
	}

	@Override
	public int getContributorPriority() {
		return 5 + (this.getActivePage() == 0 ? 1 : -1);
	}

	@Override
	public ModelStorage getModelStorage() {
		return ModelStorage.DEFAULT_INSTANCE;
	}

	@Override
	public boolean isWorkbenchPart() {
		return true;
	}
	
	@Override
	public void doRequestFocus() {
		this.getSite().getPage().activate(
				this.getSite().getPart()); 
		
		this.setActivePage(0);
		this.getViewer().getControl().setFocus();
	}
		
	@Override
	public void doRequestSelect(EObject element) {
		this.getViewer()
			.setSelection(
				new StructuredSelection(element), true
				);
		ResourceSet set = element.eResource().getResourceSet();
		Model m = this.getModelStorage().getModel(set);
		if (m != null) {
			((TreeViewer)this.getViewer()).setExpandedState(m, true);
			List<EObject> ancestors = new ArrayList<>(AgteleEcoreUtil.getAllContainers(element));
			for (int i = ancestors.size() -1 ; i>= 0; i-=1) {
				((TreeViewer)this.getViewer()).setExpandedState(ancestors.get(i), true);
				
			}
		}
	}
	
}
