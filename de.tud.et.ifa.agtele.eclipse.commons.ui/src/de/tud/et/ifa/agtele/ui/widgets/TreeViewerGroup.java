package de.tud.et.ifa.agtele.ui.widgets;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.emf.common.ui.viewer.ColumnViewerInformationControlToolTipSupport;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.command.CommandParameter;
import org.eclipse.emf.edit.command.CreateChildCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.ui.action.CreateChildAction;
import org.eclipse.emf.edit.ui.action.CreateSiblingAction;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.emf.edit.ui.provider.DecoratingColumLabelProvider;
import org.eclipse.emf.edit.ui.provider.DelegatingStyledCellLabelProvider;
import org.eclipse.emf.edit.ui.provider.DiagnosticDecorator;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreePath;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.FilteredTree;
import org.eclipse.ui.dialogs.PatternFilter;

import de.tud.et.ifa.agtele.resources.BundleContentHelper;
import de.tud.et.ifa.agtele.ui.AgteleUIPlugin;
import de.tud.et.ifa.agtele.ui.interfaces.IFeatureValidator;
import de.tud.et.ifa.agtele.ui.interfaces.IPersistable;

/**
 * A class that represents an SWT {@link Group} containing a {@link FilteredTree filtered tree viewer} and 
 * optionally a {@link ToolBar}. It supports {@link IPersistable persisting and restoring}
 * the state of the tree viewer (i.e. the expanded paths).
 * 
 * @author mfreund
 *
 */
public class TreeViewerGroup extends FilteredTree implements IPersistable {

	protected final String bundleID = AgteleUIPlugin.getPlugin().getSymbolicName();

	/**
	 * This is the adapter factory used to create content and
	 * label providers.
	 */
	protected ComposedAdapterFactory adapterFactory;

	/**
	 * This is the group that contains all other parts.
	 */
	protected Group group;

	/**
	 * This is the tool bar.
	 */
	protected ToolBar toolbar;

	/**
	 * This is the label of the group containing all other widgets.
	 */
	protected String groupText;

	/**
	 * This is the list of additional images to display in the tool bar
	 * (besides standard icons like 'collapseAll').
	 */
	protected ArrayList<Image> toolbarImages;

	/**
	 * A setter for the tool bar images that checks for 'null' parameter.
	 * 
	 * @param toolbarImages (may be 'null')
	 */
	protected void setToolbarImages(ArrayList<Image> toolbarImages) {
		this.toolbarImages = 
				(toolbarImages != null ? toolbarImages : new ArrayList<Image>()); 
	}

	/**
	 * This is the list of additional listeners that are invoked when the
	 * user selects one of the additional images in the tool bar.
	 */
	protected ArrayList<SelectionListener> toolbarListeners;

	/**
	 * Whether to display a 'collapseAll' button in the tool bar.
	 */
	protected boolean displayCollapseAll;

	/**
	 * Whether to display an 'add' button in the tool bar.
	 */
	protected boolean displayAdd;

	protected EditingDomain editingDomain;

	private IDialogSettings dialogSettings;

	private Composite toolbarComposite;

	/**
	 * A setter for the tool bar listeners that checks for 'null' parameter.
	 * 
	 * @param toolbarListeners (may be 'null')
	 */
	protected void setToolbarListeners(ArrayList<SelectionListener> toolbarListeners) {
		this.toolbarListeners = 
				(toolbarListeners != null ? toolbarListeners : new ArrayList<SelectionListener>()); 
	}

	/**
	 * Use this constructor if you do not want to add a tool bar to the viewer.
	 * 
	 * @param parent The parent composite.
	 * @param adapterFactory The adapter factory used to create label and
	 * 	content adapters.
	 * @param editingDomain The editing domain that is used for the viewer.
	 * @param dialogSettings The dialog settings belonging to the editor 
	 *  (e.g. XYZPlugin..getPlugin().getDialogSettings()).
	 * @param groupText The label of the group widget that hold all other
	 * 	widgets. If this is null no surrounding group will created.
	 */
	public TreeViewerGroup(Composite parent, ComposedAdapterFactory adapterFactory,
			EditingDomain editingDomain, IDialogSettings dialogSettings, String groupText) {
		super(parent, true);
		this.parent = parent;
		this.groupText = groupText;
		this.setToolbarImages(null);
		this.setToolbarListeners(null);
		this.displayCollapseAll = false;
		this.displayAdd = false;
		this.editingDomain = editingDomain;
		this.adapterFactory = adapterFactory;
		this.dialogSettings = dialogSettings;
		this.init(SWT.MULTI, new PatternFilter());
	}

	/**
	 * Use this constructor if you want to add a tool bar to the viewer.
	 * 
	 * @param parent The parent composite.
	 * @param adapterFactory The adapter factory used to create label and
	 	content adapters.
	 * @param editingDomain The editing domain that is used for the viewer.
	 * @param dialogSettings The dialog settings belonging to the editor 
	 *  (e.g. XYZPlugin..getPlugin().getDialogSettings()).
	 * @param groupText The label of the group widget that hold all other
	 * 	widgets. If this is null no surrounding group will created.
	 * @param images A list of images used as icons for the items of the tool bar.
	 * @param listeners A list of SelectionListeners used for the items of the tool bar.
	 * @param displayCollapseAll If to include a 'collapseAll' button in the tool bar.
	 * @param displayAdd If to include an 'add' button in the tool bar.
	 */
	public TreeViewerGroup(Composite parent, ComposedAdapterFactory adapterFactory, 
			EditingDomain editingDomain, IDialogSettings dialogSettings, String groupText, ArrayList<Image> images, 
			ArrayList<SelectionListener> listeners, boolean displayCollapseAll, boolean displayAdd) {
		super(parent, true);
		this.parent = parent;
		this.groupText = groupText;
		this.setToolbarImages(images);
		this.setToolbarListeners(listeners);
		this.displayCollapseAll = displayCollapseAll;
		this.displayAdd = displayAdd;
		this.adapterFactory = adapterFactory;
		this.editingDomain = editingDomain;
		this.dialogSettings = dialogSettings;
		this.init(SWT.MULTI, new PatternFilter());
	}

	/**
	 * Create the filtered tree's controls. This is copied from the 
	 * standard 'FilteredTree' - changes are only introduced in the
	 * layout of the filter composite. 
	 * 
	 * @param parent
	 * @param treeStyle
	 */
	@Override
	protected void createControl(Composite parent, int treeStyle) {

		// Set the layout for the main composite
		if (parent.getLayout() instanceof GridLayout) {
			setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		}
		GridLayout layout = new GridLayout();
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		setLayout(layout);

		// The group to hold everything else.
		// only create Group if the groupText is set
		// else use a 
		if (this.groupText != null) {
			group = new Group(this, SWT.NONE);
			group.setText(this.groupText);
			group.setLayoutData(
					new GridData(SWT.FILL, SWT.FILL, true, true));
			group.setLayout(new GridLayout(1, true));
		}

		if (showFilterControls) {

			// This composite hosts two children: the filter composite and the
			// tool bar composite.
			filterComposite = new Composite(group == null ? this : group, SWT.NONE);
			filterComposite.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING,
					true, false));

			GridLayout filterLayout= new GridLayout(2, false);
			filterLayout.marginHeight= 0;
			filterLayout.marginWidth= 0;
			filterComposite.setLayout(filterLayout);

			// create filter controls that will take up all of the horizontal space
			// except the one that is taken by the tool bar
			Composite filterBoxComposite = new Composite(filterComposite, SWT.BORDER);
			filterBoxComposite.setBackground(getDisplay().getSystemColor(SWT.COLOR_LIST_BACKGROUND));
			filterBoxComposite.setLayoutData(
					new GridData(SWT.FILL, SWT.BEGINNING, true, false));
			GridLayout filterBoxLayout = new GridLayout(2, false);
			filterBoxLayout.marginHeight = 0;
			filterBoxLayout.marginWidth = 0;
			filterBoxComposite.setLayout(filterLayout);
			filterBoxComposite.setFont(parent.getFont());
			createFilterControls(filterBoxComposite);

			// create a toolbar besides the filter controls
			toolbarComposite = new Composite(filterComposite, SWT.NONE);
			toolbarComposite.setLayoutData(
					new GridData(SWT.BEGINNING, SWT.BEGINNING, false, false));
			GridLayout toolbarLayout = new GridLayout(1, false);
			toolbarLayout.marginHeight = 0;
			toolbarLayout.marginWidth = 0;
			toolbarComposite.setLayout(toolbarLayout);
			createToolbar(toolbarComposite, toolbarImages, toolbarListeners, 
					displayCollapseAll, displayAdd);

		}

		treeComposite = new Composite(group == null ? this : group, SWT.NONE);
		GridLayout treeCompositeLayout = new GridLayout();
		treeCompositeLayout.marginHeight = 0;
		treeCompositeLayout.marginWidth = 0;
		treeComposite.setLayout(treeCompositeLayout);
		GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
		treeComposite.setLayoutData(data);
		createTreeControl(treeComposite, treeStyle);
	}
	
	/**
	 * Exposes the internal tree composite.
	 * 
	 * @return The TreeComposite of the TreeViewerGroup
	 */
	public Composite getTreeComposite () {
		return treeComposite;
	}
	
	public Composite getToolbar() {
		return toolbarComposite;
	}

	/**
	 * Override the standard method so that default label and content providers
	 * based on the adapter factory are created.
	 * 
	 * @param parent
	 * @param style
	 * @return
	 */
	@Override
	protected TreeViewer doCreateTreeViewer(Composite parent, int style) {
		TreeViewer treeViewer = super.doCreateTreeViewer(parent, style);
		
		treeViewer.setLabelProvider(new DelegatingStyledCellLabelProvider(new DecoratingColumLabelProvider.StyledLabelProvider(
				new AdapterFactoryLabelProvider.StyledLabelProvider(adapterFactory, treeViewer), 
				new DiagnosticDecorator.Styled(editingDomain, treeViewer, this.dialogSettings)) {
			@Override
			public String getToolTipText(Object element) {
				String toolTip = super.getToolTipText(element);
				return (toolTip == null ? (element instanceof EObject ? "Element Type: " + ((EObject) element).eClass().getName() : null) : toolTip);
			}
		}));
		treeViewer.setContentProvider(new AdapterFactoryContentProvider(adapterFactory));
		new ColumnViewerInformationControlToolTipSupport(
				treeViewer, 
				new DiagnosticDecorator.Styled.EditingDomainLocationListener(editingDomain, treeViewer));
		return treeViewer;
	}

	/**
	 * This creates a tool bar if necessary.
	 * 
	 * @param parent The parent composite.
	 * @param images The images used for the tool items.
	 * @param listeners The listeners used for the tool items.
	 * @param displayCollapseAll If to include a 'collapseAll' button in the tool bar.
	 * @param displayAdd If to include an add button.
	 */
	protected void createToolbar(Composite parent, ArrayList<Image> images,
			ArrayList<SelectionListener> listeners, boolean displayCollapseAll,
			boolean displayAdd) {

		if(images.size() != listeners.size()) {
			throw new RuntimeException("The number of images provided must match"
					+ " the number of listeners provided!");
		}

		if(images.isEmpty() && listeners.isEmpty() && !displayCollapseAll && !displayAdd) {
			// nothing to be done
			return;
		}

		// Create the button area
		toolbar = new ToolBar(parent, SWT.NONE);
		toolbar.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));

		// Add the 'collapseAll' tool bar item
		if(displayCollapseAll) {
			ToolItem item = new ToolItem(toolbar, SWT.PUSH | SWT.TRAIL);
			item.setImage(BundleContentHelper.getBundleImage(bundleID, "icons/collapse_all.gif"));
			item.setToolTipText("Collapse Tree");
			item.addSelectionListener(new SelectionListener() {

				@Override
				public void widgetSelected(SelectionEvent e) {
					getViewer().collapseAll();
				}

				@Override
				public void widgetDefaultSelected(SelectionEvent e) {}
			});
		}

		// Add the 'add' tool bar item
		if(displayAdd) {
			ToolItem item = new ToolItem(toolbar, SWT.DROP_DOWN);
			item.setImage(BundleContentHelper.getBundleImage(bundleID, "icons/add_obj.gif"));
			item.setToolTipText("Add Sibling of Same Type");

			item.addSelectionListener(createAddDropDownSelectionListener(item));
		}

		// Add the various tool bar items passed in the constructor
		for (int i=0; i<images.size(); i++) {
			ToolItem item = new ToolItem(toolbar, SWT.PUSH | SWT.TRAIL);
			item.setImage(images.get(i));
			item.addSelectionListener(listeners.get(i));
		}

		// Add additional stuff to the tool bar
		createAdditionalToolbarItems(toolbar);

		toolbar.pack();

	}

	protected AddDropDownSelectionListener createAddDropDownSelectionListener (ToolItem item) {
		return new AddDropDownSelectionListener(item);
	}
	
	/**
	 * This creates additional tool bar items besides the standard items
	 * like 'collapseAll'. The default implementation does nothing - 
	 * subclasses may override. 
	 * 
	 * @param toolbar The parent tool bar.
	 */
	protected void createAdditionalToolbarItems(ToolBar toolbar) {

		// allow extending classes to easily configure the tool bar
	}

	public class AddDropDownSelectionListener extends SelectionAdapter {

		private MenuManager menuManager;

		public AddDropDownSelectionListener(ToolItem dropdown) {
			menuManager = new MenuManager();
			menuManager.createContextMenu(dropdown.getParent());
		}

		@Override
		public void widgetSelected(SelectionEvent event) {
			if (event.detail == SWT.ARROW) {
				ToolItem item = (ToolItem) event.widget;
				Rectangle rect = item.getBounds();
				Point pt = item.getParent().toDisplay(new Point(rect.x, rect.y));
				createMenu();
				menuManager.getMenu().setLocation(pt.x, pt.y + rect.height);
				menuManager.getMenu().setVisible(true);
			} else {
				addDefaultElement();
			}
		}

		/**
		 * This determines the currently selected element and adds
		 * an element of the same type to its parent.
		 */
		private void addDefaultElement() {
			ISelection selection = treeViewer.getSelection();
			if(selection.isEmpty() || !(selection instanceof StructuredSelection)) {
				// nothing to be done
				return;
			}
			StructuredSelection structuredSelection = (StructuredSelection) selection;
			Object selectedObject = structuredSelection.getFirstElement();
			if(!(selectedObject instanceof EObject)) {
				// nothing to be done
				return;
			}

			// A command that will create a new eObject that is of the same
			// type of the selected eObject. The newly created object will then
			// be added to the parent.
			CreateChildCommand command = new CreateChildCommand(
					editingDomain,
					((EObject) selectedObject).eContainer(),
					((EObject) selectedObject).eContainingFeature(), 
					EcoreUtil.create(
							((EObject) selectedObject).eClass()),
					null);

			// Execute the command and set the selection of the viewer to the
			// newly created object.
			editingDomain.getCommandStack().execute(command);
			treeViewer.setSelection(new StructuredSelection(command.getResult().toArray()));
		}

		/**
		 * This creates a new menu based on the currently selected 
		 * element that allows to create children and siblings.
		 */
		private void createMenu() {

			// Clear the context menu
			//
			menuManager.removeAll();

			// Query the new selection for appropriate new child/sibling descriptors
			//
			Collection<?> newChildDescriptors = null;
			Collection<?> newSiblingDescriptors = null;
			ISelection selection = treeViewer.getSelection();
			if (!(selection instanceof IStructuredSelection) || ((IStructuredSelection)selection).size() > 1) {
				// nothing to be done
				return;
			}

			boolean doNotCreateSiblingActions = false;
			if(((IStructuredSelection)selection).isEmpty()) {
				// if nothing is selected,we manually select the viewer input; this will allow to add the
				// top level elements in this viewer
				doNotCreateSiblingActions = true; // in this case, we only want allow to create child actions
				try{
					selection = new StructuredSelection(treeViewer.getInput());					
				} catch(Exception e) {
					return;
				}
			}
			
			Object object = ((IStructuredSelection)selection).getFirstElement();

			newChildDescriptors = editingDomain.getNewChildDescriptors(object, null);
			if(doNotCreateSiblingActions) {
				newSiblingDescriptors = new ArrayList<Object>();
			} else {
				newSiblingDescriptors = editingDomain.getNewChildDescriptors(null, object);				
			} 

			IEditorPart editorPart = 
					PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor();

			// Generate actions for selection; populate and redraw the menus.
			//
			ArrayList<IAction> createChildActions = new ArrayList<>();
			for (Object descriptor : newChildDescriptors) {
				if(isValidDescriptor(descriptor, treeViewer.getContentProvider())) {
					createChildActions.add(createCreateChildAction(editorPart, selection, descriptor));
				}
			}
			ArrayList<IAction> createSiblingActions = new ArrayList<>();
			for (Object descriptor : newSiblingDescriptors) {
				if(isValidDescriptor(descriptor, treeViewer.getContentProvider())) {
					createSiblingActions.add(createCreateSiblingAction(editorPart, selection, descriptor));					
				}
			}

			// Populate the context menu
			//
			menuManager.add(new Separator("Add Child"));
			for (IAction iAction : createChildActions) {
				menuManager.add(iAction);
			}
			menuManager.add(new Separator("Add Sibling"));
			for (IAction iAction : createSiblingActions) {
				menuManager.add(iAction);
			}
		}
		protected IAction createCreateChildAction(IEditorPart editorPart, ISelection selection, Object descriptor) {
			return new CreateChildAction(editorPart, selection, descriptor);
		}
		protected IAction createCreateSiblingAction(IEditorPart editorPart, ISelection selection, Object descriptor) {
			return new CreateSiblingAction(editorPart, selection, descriptor);
		}
	}

	@Override
	public void persist(IDialogSettings settings) {
		
		// Persist the expanded tree paths of the tree viewer
		//
		ArrayList<String> paths = new ArrayList<>();
		for (int i = 0; i < treeViewer.getExpandedTreePaths().length; i++) {
			TreePath path = treeViewer.getExpandedTreePaths()[i];
			// TODO currently, persisting only works for objects representing a real eObject
			if(path.getLastSegment() instanceof EObject) {
				try {
					/*
					 * use the URI of the eObject as unique identifier
					 */
					paths.add(EcoreUtil.getURI((EObject) path.getLastSegment()).toString());
				} catch (IllegalArgumentException e) {
					// do nothing
				}
			}
		}
		settings.put("EXPANDED_TREE_PATHS", paths.toArray(new String[paths.size()]));
		
		// Persist the filter text
		//
		settings.put("FILTER_TEXT", (getFilterString() != null ? getFilterString() : ""));
	}

	@Override
	public void restore(IDialogSettings settings) {
		
		// Restore the expanded tree paths of the tree viewer
		//
		if(settings.getArray("EXPANDED_TREE_PATHS") != null) {
			String[] paths = settings.getArray("EXPANDED_TREE_PATHS");
			for (int i = 0; i < paths.length; i++) {
//				System.out.println(paths[i]);
				/*
				 * as the URI of an eObject also reflects the containing resource, we can use this to
				 * uniquely identify an eObject inside a resource set
				 */
				EObject expanded = editingDomain.getResourceSet().getEObject(URI.createURI(paths[i]), true);
				if(expanded != null) {
					treeViewer.setExpandedState(expanded, true);					
				}
			}
		}
		
		// Restore the filter text
		//
		String filterText = settings.get("FILTER_TEXT"); 
		if(filterText != null && !filterText.isEmpty()) {
			setFilterText(filterText);		
		}
	}
	
	/**
	 * This is used by {@link #generateCreateChildActions(Collection, ISelection)} and {@link #generateCreateSiblingActions(Collection, ISelection)}
	 * to perform additional checks if an action corresponding to the given <em>descriptor</em> is valid for the active <em>content provider</em>.
	 * 
	 * @param descriptor The {@link CommandParameter} that describes an action to be executed.
	 * @param provider The {@link IContentProvider content provider} that is associated with the active viewer.
	 * @return '<em><b>true</b></em>' if the descriptor is valid for the active viewer; '<em><b>false</b></em>' otherwise.
	 */
	public boolean isValidDescriptor(Object descriptor, IContentProvider provider) {
		
		if(descriptor == null || provider == null) {
			return false;
		}
		
		if(!(descriptor instanceof CommandParameter) || 
				!(((CommandParameter) descriptor).getFeature() instanceof EStructuralFeature)) {
			return true;
		}
		
		CommandParameter commandParam = (CommandParameter) descriptor;
		
		if(provider instanceof IFeatureValidator) {
			return ((IFeatureValidator) provider).isValidFeature((EStructuralFeature) commandParam.getFeature());
		}
		
		return true;
	}

}
