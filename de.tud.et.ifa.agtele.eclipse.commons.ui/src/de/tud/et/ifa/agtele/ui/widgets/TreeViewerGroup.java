package de.tud.et.ifa.agtele.ui.widgets;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.commands.Command;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.NotEnabledException;
import org.eclipse.core.commands.NotHandledException;
import org.eclipse.core.commands.Parameterization;
import org.eclipse.core.commands.ParameterizedCommand;
import org.eclipse.core.commands.common.NotDefinedException;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.commands.ECommandService;
import org.eclipse.emf.common.ui.viewer.ColumnViewerInformationControlToolTipSupport;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
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
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreePath;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
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
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.commands.ICommandService;
import org.eclipse.ui.dialogs.FilteredTree;
import org.eclipse.ui.dialogs.PatternFilter;
import org.eclipse.ui.handlers.IHandlerService;
import org.eclipse.ui.services.IEvaluationService;
import org.eclipse.ui.services.IServiceLocator;

import de.tud.et.ifa.agtele.resources.BundleContentHelper;
import de.tud.et.ifa.agtele.ui.AgteleUIPlugin;
import de.tud.et.ifa.agtele.ui.interfaces.IFeatureValidator;
import de.tud.et.ifa.agtele.ui.interfaces.IPersistable;
import de.tud.et.ifa.agtele.ui.listeners.SelectionListener2;
import de.tud.et.ifa.agtele.ui.widgets.TreeViewerGroup.TreeViewerGroupToolbarAddButtonOption.AddDropDownSelectionListener;

/**
 * A class that represents an SWT {@link Group} containing a {@link FilteredTree filtered tree viewer} and optionally a
 * {@link ToolBar}. It supports {@link IPersistable persisting and restoring} the state of the tree viewer (i.e. the
 * expanded paths).
 *
 * @author mfreund
 *
 */
public class TreeViewerGroup extends FilteredTree implements IPersistable {

	/**
	 * The default option to add a collapse all button to the tool bar.
	 * @return a new instance of the TreeViewerGroupToolbarCollapseAllButtonOption
	 */
	public static TreeViewerGroupToolbarCollapseAllButtonOption TOOLBAR_COLLAPSE_ALL_BUTTON() {
		return new TreeViewerGroupToolbarCollapseAllButtonOption();
	}
	
	/**
	 * The default option to add an add button to the tool bar.
	 * @return a new instance of TreeViewerGroupToolbarAddButtonOption
	 */
	public static TreeViewerGroupToolbarAddButtonOption TOOLBAR_ADD_BUTTON() {
		return new TreeViewerGroupToolbarAddButtonOption();
	}
	
	/**
	 * The default option to add the model elements tool palette. 
	 * @return a new instance of TreeViewerGroupAddToolPaletteOption
	 */
	public static TreeViewerGroupAddToolPaletteOption PALETTE_MODEL_ELEMENTS() {
		return new TreeViewerGroupAddToolPaletteOption();
	}
	
	
	/**
	 * The default option to add a toggle editor split vertically button. 
	 * @return a new instance of TreeViewerGroupToolbarToggleSplitEditorVerticallyButtonOption
	 */
	public static TreeViewerGroupToolbarToggleSplitEditorVerticallyButtonOption TOOLBAR_TOGGLE_SPLIT_VERTICALLY_BUTTON() {
		return new TreeViewerGroupToolbarToggleSplitEditorVerticallyButtonOption();
	}
		
	protected final String bundleID = AgteleUIPlugin.getPlugin().getSymbolicName();

	/**
	 * This is the adapter factory used to create content and label providers.
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
	 * The {@link EditingDomain} for the model to be displayed in this tree viewer. It is, e.g., used to extract labels
	 * for the elements to be displayed in the tree.
	 */
	protected EditingDomain editingDomain;

	/**
	 * The {@link IDialogSettings} that are used to persist and restore the state of this viewer.
	 *
	 * @see IPersistable
	 */
	private IDialogSettings dialogSettings;

	/**
	 * The {@link Composite} holding the {@link ToolBar} that displays the various buttons.
	 */
	private Composite toolbarComposite;

	/**
	 * The options, this instance has been initialized with.
	 */
	protected TreeViewerGroupOption[] options;

	/**
	 * The sash containing the vertically split elements.
	 */
	protected SashForm treePaletteSeparator;

	/**
	 * The weights of the treePaletteSeparator
	 */
	private int[] sashWeights;

	protected DisposeListener disposeListener;
	
	/**
	 * Use this constructor if you do not want to add a tool bar to the viewer.
	 *
	 * @param parent
	 *            The parent composite.
	 * @param adapterFactory
	 *            The adapter factory used to create label and content adapters.
	 * @param editingDomain
	 *            The editing domain that is used for the viewer.
	 * @param dialogSettings
	 *            The dialog settings belonging to the editor (e.g. XYZPlugin..getPlugin().getDialogSettings()).
	 * @param groupText
	 *            The label of the group widget that hold all other widgets. If this is null no surrounding group will
	 *            created.
	 */
	public TreeViewerGroup(Composite parent, ComposedAdapterFactory adapterFactory, EditingDomain editingDomain,
			IDialogSettings dialogSettings, String groupText) {
		super(parent, true);
		this.parent = parent;
		this.groupText = groupText;
		this.editingDomain = editingDomain;
		this.adapterFactory = adapterFactory;
		this.dialogSettings = dialogSettings;
		this.options = new TreeViewerGroupOption[0]; 
		this.init(SWT.MULTI, new PatternFilter());
	}

	/**
	 * Use this constructor if you want to add a tool bar to the viewer.
	 *
	 * @param parent
	 *            The parent composite.
	 * @param adapterFactory
	 *            The adapter factory used to create label and content adapters.
	 * @param editingDomain
	 *            The editing domain that is used for the viewer.
	 * @param dialogSettings
	 *            The dialog settings belonging to the editor (e.g. XYZPlugin..getPlugin().getDialogSettings()).
	 * @param groupText
	 *            The label of the group widget that hold all other widgets. If this is null no surrounding group will
	 *            created.
	 * @param images
	 *            A list of images used as icons for the items of the tool bar.
	 * @param listeners
	 *            A list of SelectionListeners used for the items of the tool bar.
	 * @param displayCollapseAll
	 *            If to include a 'collapseAll' button in the tool bar.
	 * @param displayAdd
	 *            If to include an 'add' button in the tool bar.
	 * @deprecated use {@link TreeViewerGroup#TreeViewerGroup(Composite, ComposedAdapterFactory, EditingDomain, IDialogSettings, String, TreeViewerGroupOption...)} instead
	 */
	public TreeViewerGroup(Composite parent, ComposedAdapterFactory adapterFactory, EditingDomain editingDomain,
			IDialogSettings dialogSettings, String groupText, List<Image> images, List<SelectionListener> listeners,
			boolean displayCollapseAll, boolean displayAdd) {
		//Remove this, if there are no dependencies anymore
		super(parent, true);
		this.parent = parent;
		this.groupText = groupText;
		this.editingDomain = editingDomain;
		this.adapterFactory = adapterFactory;
		this.dialogSettings = dialogSettings;		
		
		this.options = (displayCollapseAll && displayAdd) ? ( new TreeViewerGroupOption[2]) : 
					((displayAdd || displayCollapseAll) ? (new TreeViewerGroupOption[1]) : new TreeViewerGroupOption[0]);
		if (displayCollapseAll) {
			this.options[0] = new TreeViewerGroupToolbarCollapseAllButtonOption();
			if (displayAdd) {
				this.options[1] = new TreeViewerGroupToolbarAddButtonOption();
			}
		} else if (displayAdd) {
				this.options[0] = new TreeViewerGroupToolbarAddButtonOption();			
		}
		this.init(SWT.MULTI, new PatternFilter());
	}
		
	/**
	 * Use this constructor if you do not want to add a tool bar to the viewer.
	 *
	 * @param parent
	 *            The parent composite.
	 * @param adapterFactory
	 *            The adapter factory used to create label and content adapters.
	 * @param editingDomain
	 *            The editing domain that is used for the viewer.
	 * @param dialogSettings
	 *            The dialog settings belonging to the editor (e.g. XYZPlugin..getPlugin().getDialogSettings()).
	 * @param groupText
	 *            The label of the group widget that hold all other widgets. If this is null no surrounding group will
	 *            created.
	 * @param options
	 * 		  	  A set of options that are used to alter the default composition of the TreeViewerGroup	
	 */
	public TreeViewerGroup(Composite parent, ComposedAdapterFactory adapterFactory, EditingDomain editingDomain,
			IDialogSettings dialogSettings, String groupText, TreeViewerGroupOption... options) {
		super(parent, true);
		this.parent = parent;
		this.groupText = groupText;
		this.editingDomain = editingDomain;
		this.adapterFactory = adapterFactory;
		this.dialogSettings = dialogSettings;
		this.options = options == null? new TreeViewerGroupOption[0] : options; 
		this.init(SWT.MULTI, new PatternFilter());
	}
	
	/**
	 * A setter for the tool bar images that checks for 'null' parameter.
	 *
	 * @param toolbarImages
	 *            (may be 'null')
	 * @deprecated Remove, if there are no dependencies anymore
	 */
	protected void setToolbarImages(ArrayList<Image> toolbarImages) {
		//Not used anymore
	}

	/**
	 * A setter for the tool bar listeners that checks for 'null' parameter.
	 *
	 * @param toolbarListeners
	 *            (may be 'null')
	 * @deprecated Remove, if there are no dependencies anymore
	 */
	protected void setToolbarListeners(ArrayList<SelectionListener> toolbarListeners) {
		//not used anymore
	}

	/**
	 * Create the filtered tree's controls. This is copied from the standard 'FilteredTree' - changes are only
	 * introduced in the layout of the filter composite.
	 *
	 * @param parent
	 * @param treeStyle
	 */
	@Override
	protected void createControl(Composite parent, int treeStyle) {

		this.disposeListener = new DisposeListener() {
			@Override
			public void widgetDisposed(DisposeEvent e) {
				parent.removeDisposeListener(disposeListener);
				TreeViewerGroup.this.dispose();
			}			
		};
		
		parent.addDisposeListener(disposeListener);
		
		// Set the layout for the main composite
		if (parent.getLayout() instanceof GridLayout) {
			this.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		}
		GridLayout layout = new GridLayout();
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		this.setLayout(layout);

		// The group to hold everything else.
		// only create Group if the groupText is set
		// else use a
		if (this.groupText != null) {
			this.group = new Group(this, SWT.NONE);
			this.group.setText(this.groupText);
			this.group.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
			this.group.setLayout(new GridLayout(1, true));
		}

		if (this.showFilterControls) {
			this.createFilterTools();
			this.createToolbar(this.toolbarComposite);
		}

		int paletteCount = 0;
		int treeIndex = 0;
		
		//creates the palette controls to the left side of the tree
		for (TreeViewerGroupOption i : options) {
			if (i instanceof TreeViewerGroupPaletteOption && ((TreeViewerGroupPaletteOption)i).addToLeftSide()) {
				if (treePaletteSeparator == null) {
					treePaletteSeparator = new SashForm(this.group == null ? this : this.group, SWT.SMOOTH);
					treePaletteSeparator.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
				}				
				
				((TreeViewerGroupPaletteOption)i).addPaletteControls(this, treePaletteSeparator, paletteCount, options);
				paletteCount += 1;
			}
		}
		treeIndex = paletteCount;
		paletteCount += 1;
		
		//create the tree and its composite
		if (treePaletteSeparator != null) {
			this.treeComposite = new Composite(treePaletteSeparator, SWT.NONE);			
		} else {
			this.treeComposite = new Composite(this.group == null ? this : this.group, SWT.NONE);				
		}
		
		GridLayout treeCompositeLayout = new GridLayout();
		treeCompositeLayout.marginHeight = 0;
		treeCompositeLayout.marginWidth = 0;
		this.treeComposite.setLayout(treeCompositeLayout);
		GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
		this.treeComposite.setLayoutData(data);
		this.createTreeControl(this.treeComposite, treeStyle);
		
		//adds the palettes to the right of the tree
		for (TreeViewerGroupOption i : options) {
			if (i instanceof TreeViewerGroupPaletteOption && ((TreeViewerGroupPaletteOption)i).addToRightSide()) {
				if (treePaletteSeparator == null) {
					treePaletteSeparator = new SashForm(this.group == null ? this : this.group, SWT.SMOOTH);
					treePaletteSeparator.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
					//Move the tree
					this.treeComposite.setParent(treePaletteSeparator);
				}			
				((TreeViewerGroupPaletteOption) i).addPaletteControls(this, treePaletteSeparator, paletteCount, options);
				paletteCount += 1;
			}
		}
		
		//calculate the weights of the sash form
		if (treePaletteSeparator != null && (sashWeights == null || sashWeights.length != paletteCount)) {
			sashWeights = new int[paletteCount];
			for (int i = 0; i<paletteCount; i+= 1) {
				sashWeights[i] = 1;
			}
			sashWeights[treeIndex] = paletteCount;
			treePaletteSeparator.setWeights(sashWeights);
		}
	}
	
	/**
	 * creates the filter controls
	 */
	protected void createFilterTools () {
		// This composite hosts two children: the filter composite and the
		// tool bar composite.
		this.filterComposite = new Composite(this.group == null ? this : this.group, SWT.NONE);
		this.filterComposite.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false));

		GridLayout filterLayout = new GridLayout(2, false);
		filterLayout.marginHeight = 0;
		filterLayout.marginWidth = 0;
		this.filterComposite.setLayout(filterLayout);

		// create filter controls that will take up all of the horizontal space
		// except the one that is taken by the tool bar
		Composite filterBoxComposite = new Composite(this.filterComposite, SWT.BORDER);
		filterBoxComposite.setBackground(this.getDisplay().getSystemColor(SWT.COLOR_LIST_BACKGROUND));
		filterBoxComposite.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false));
		GridLayout filterBoxLayout = new GridLayout(2, false);
		filterBoxLayout.marginHeight = 0;
		filterBoxLayout.marginWidth = 0;
		filterBoxComposite.setLayout(filterLayout);
		filterBoxComposite.setFont(parent.getFont());
		this.createFilterControls(filterBoxComposite);

		// create a toolbar besides the filter controls
		this.toolbarComposite = new Composite(this.filterComposite, SWT.NONE);
		this.toolbarComposite.setLayoutData(new GridData(SWT.BEGINNING, SWT.BEGINNING, false, false));
		GridLayout toolbarLayout = new GridLayout(1, false);
		toolbarLayout.marginHeight = 0;
		toolbarLayout.marginWidth = 0;
		this.toolbarComposite.setLayout(toolbarLayout);
	}

	/**
	 * Exposes the internal tree composite.
	 *
	 * @return The TreeComposite of the TreeViewerGroup
	 */
	public Composite getTreeComposite() {
		return this.treeComposite;
	}

	/**
	 * The getter for the {@link #toolbarComposite}.
	 *
	 * @return The {@link Composite} holding the {@link ToolBar} that displays the various buttons.
	 */
	public Composite getToolbar() {
		return this.toolbarComposite;
	}

	/**
	 * Override the standard method so that default label and content providers based on the adapter factory are
	 * created.
	 *
	 * @param parent
	 *            The parent composite.
	 * @param style
	 *            SWT style bits used to create the tree viewer.
	 * @return
	 */
	@Override
	protected TreeViewer doCreateTreeViewer(Composite parent, int style) {

		TreeViewer treeViewer = super.doCreateTreeViewer(parent, style);

		treeViewer.setLabelProvider(
				new DelegatingStyledCellLabelProvider(new DecoratingColumLabelProvider.StyledLabelProvider(
						new AdapterFactoryLabelProvider.StyledLabelProvider(this.adapterFactory, treeViewer),
						new DiagnosticDecorator.Styled(this.editingDomain, treeViewer, this.dialogSettings)) {

					@Override
					public String getToolTipText(Object element) {
						String toolTip = super.getToolTipText(element);
						return toolTip == null ? element instanceof EObject
								? "Element Type: " + ((EObject) element).eClass().getName() : null : toolTip;
					}
				}));
		treeViewer.setContentProvider(new AdapterFactoryContentProvider(this.adapterFactory));
		new ColumnViewerInformationControlToolTipSupport(treeViewer,
				new DiagnosticDecorator.Styled.EditingDomainLocationListener(this.editingDomain, treeViewer));
		return treeViewer;
	}
	
	/**
	 * Returns the tree viewer
	 * @return
	 */
	protected TreeViewer getTreeViewer () {
		return treeViewer;
	}

	/**
	 * This creates a tool bar if necessary.
	 *
	 * @param parent
	 *            The parent composite.
	 * @param images
	 *            The images used for the tool items.
	 * @param listeners
	 *            The listeners used for the tool items.
	 * @param displayCollapseAll
	 *            If to include a 'collapseAll' button in the tool bar.
	 * @param displayAdd
	 *            If to include an add button.
	 * @deprecated remove, if there are no dependencies anymore
	 */
	protected void createToolbar(Composite parent, List<Image> images, List<SelectionListener> listeners,
			boolean displayCollapseAll, boolean displayAdd) {
		this.createToolbar(parent);
	}
	
	/**
	 * This creates a tool bar if necessary.
	 *
	 * @param parent
	 *            The parent composite.
	 */
	protected void createToolbar(Composite parent) {
		for (TreeViewerGroupOption i : this.options) {
			if (i instanceof TreeViewerGroupToolbarOption) {
				if (this.toolbar == null) {
					// Create the button area
					this.toolbar = new ToolBar(parent, SWT.NONE);
					this.toolbar.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
				}
			
				((TreeViewerGroupToolbarOption) i).addToolbarControls(this, this.toolbar, this.options);
			}
		}

		// Add additional stuff to the tool bar
		this.createAdditionalToolbarItems(this.toolbar);

		if (this.toolbar != null) {
			this.toolbar.pack();			
		}
	}

	/**
	 * This creates additional tool bar items besides the standard items like 'collapseAll'. The default implementation
	 * does nothing - subclasses may override.
	 *
	 * @param toolbar
	 *            The parent tool bar.
	 * @deprecated remove, if there are no dependencies anymore
	 */
	protected void createAdditionalToolbarItems(ToolBar toolbar) {
		// allow extending classes to easily configure the tool bar
	}

	@Override
	public void persist(IDialogSettings settings) {

		// Persist the expanded tree paths of the tree viewer
		//
		ArrayList<String> paths = new ArrayList<>();
		for (int i = 0; i < this.treeViewer.getExpandedTreePaths().length; i++) {
			TreePath path = this.treeViewer.getExpandedTreePaths()[i];
			// TODO currently, persisting only works for objects representing a real eObject
			if (path.getLastSegment() instanceof EObject) {
				try {
					/*
					 * use the URI of the eObject as unique identifier
					 */
					paths.add(EcoreUtil.getURI((EObject) path.getLastSegment()).toString());
				} catch (IllegalArgumentException e) {
					// do nothing
				}
			} else if (path.getLastSegment() instanceof Resource) {
				try {
					/*
					 * use the URI of the eObject as unique identifier
					 */
					paths.add(((Resource) path.getLastSegment()).getURI().toString());
				} catch (IllegalArgumentException e) {
					// do nothing
				}
			}
		}
		settings.put("EXPANDED_TREE_PATHS", paths.toArray(new String[paths.size()]));

		// Persist the filter text
		//
		settings.put("FILTER_TEXT", this.getFilterString() != null ? this.getFilterString() : "");
		
		if (treePaletteSeparator != null) {
			sashWeights = treePaletteSeparator.getWeights();
			String[] weights = new String[sashWeights.length];
			for (int i = 0; i<sashWeights.length; i+=1) {
				weights[i] = Integer.toString(sashWeights[i]);
			}
			
			settings.put("PALETTE_WEIGHTS", weights);
			//System.out.println("Store PALETTE_WEIGHTS " + stringArrayToString(weights));
		}
		
		for (TreeViewerGroupOption i : this.options) {
			if (i instanceof TreeViewerGroupPersistableOption) {
				((TreeViewerGroupPersistableOption) i).persist(settings);
			}
		}
	}
	public String stringArrayToString (String[] ar) {
		String result = "[";
		
		for (int i = 0; i < ar.length; i += 1) {
			if (i>0) {result += ",";}
			result += "'" + ar[i] + "'";
		}
		
		return result + "]";
	}
	
	public String intArrayToString (int[] ar) {
		String result = "[";
		
		for (int i = 0; i < ar.length; i += 1) {
			if (i>0) {result += ",";}
			result += Integer.toString(ar[i]);
		}
		
		return result + "]";
	}

	@Override
	public void restore(IDialogSettings settings) {

		// Restore the expanded tree paths of the tree viewer
		//
		if (settings.getArray("EXPANDED_TREE_PATHS") != null) {
			String[] paths = settings.getArray("EXPANDED_TREE_PATHS");

			for (String path : paths) {
				Object expanded;
				/*
				 * as the URI of an eObject also reflects the containing resource, we can use this to uniquely identify
				 * an eObject inside a resource set
				 */
				try {
					expanded = this.editingDomain.getResourceSet().getEObject(URI.createURI(path), true);
				} catch (Exception e) {
					// If the EObject can't be loaded, it may be because it's a resource
					expanded = this.editingDomain.getResourceSet().getResource(URI.createURI(path), true);
				}
				if (expanded != null) {
					this.treeViewer.setExpandedState(expanded, true);
				}
			}
		}

		// Restore the filter text
		//
		String filterText = settings.get("FILTER_TEXT");
		if (filterText != null && !filterText.isEmpty()) {
			this.setFilterText(filterText);
		}

		try {
		String[] weights = settings.getArray("PALETTE_WEIGHTS");
			if (weights != null) {
				sashWeights = new int[weights.length];
				for (int i = 0; i < weights.length; i+=1) {
					sashWeights[i] = new Integer(weights[i]);
				}
				if (treePaletteSeparator != null && !treePaletteSeparator.isDisposed()) {
					treePaletteSeparator.setWeights(sashWeights);
					//System.out.println("Restore PALETTE_WEIGHTS " + stringArrayToString(weights));
				}
			} else {
				//System.out.println("Did not restore PALETTE_WEIGHTS");
			}
		} catch (Exception e) {
			//do nothing, default weights will kick in
			//System.out.println("Did not restore PALETTE_WEIGHTS");
		}
		
		for (TreeViewerGroupOption i : this.options) {
			if (i instanceof TreeViewerGroupPersistableOption) {
				((TreeViewerGroupPersistableOption) i).restore(settings);
			}
		}
	}
	
	/**
	 * This is used by {@link AddDropDownSelectionListener#createCreateChildAction(IEditorPart, ISelection, Object)} and
	 * {@link AddDropDownSelectionListener#createCreateSiblingAction(IEditorPart, ISelection, Object)} to perform
	 * additional checks if an action corresponding to the given <em>descriptor</em> is valid for the active <em>content
	 * provider</em>.
	 *
	 * @param descriptor
	 *            The {@link CommandParameter} that describes an action to be executed.
	 * @param provider
	 *            The {@link IContentProvider content provider} that is associated with the active viewer.
	 * @return '<em><b>true</b></em>' if the descriptor is valid for the active viewer; '<em><b>false</b></em>'
	 *         otherwise.
	 */
	public boolean isValidDescriptor(Object descriptor, IContentProvider provider) {

		if (descriptor == null || provider == null) {
			return false;
		}

		if (!(descriptor instanceof CommandParameter)
				|| !(((CommandParameter) descriptor).getFeature() instanceof EStructuralFeature)) {
			return true;
		}

		CommandParameter commandParam = (CommandParameter) descriptor;

		if (provider instanceof IFeatureValidator) {
			return ((IFeatureValidator) provider).isValidFeature((EStructuralFeature) commandParam.getFeature());
		}

		return true;
	}
	
	/**
	 * 
	 */
	@Override
	public void dispose () {
		for (TreeViewerGroupOption i : this.options) {
			i.dispose();
		}
	}

	/**
	 * Can be anything, that alters the TreeViewerGroup
	 * @author Lukas
	 */
	public static interface TreeViewerGroupOption {

		/**
		 * Disposes the created UI elements and removes created event listeners from other elements.
		 */
		public void dispose();
	};
	
	/**
	 * A ToolbarOption alters the contents of the Toolbar.
	 * @author Lukas
	 */
	public static interface TreeViewerGroupToolbarOption extends TreeViewerGroupOption {
		/**
		 * Add some Controls to the ToolBar.
		 * @param group The containing TreeViewerGroup
		 * @param toolbar The toolbar to manipulate
		 * @param options All Options the TreeViewerGroup has been initialized with.
		 */
		public default void  addToolbarControls (TreeViewerGroup group, ToolBar toolbar, TreeViewerGroupOption[] options) {}	
	}
	
	/**
	 * A Palette Option can alter the side content next to the actual tree.
	 * @author Lukas
	 */
	public static interface TreeViewerGroupPaletteOption extends TreeViewerGroupOption {
		/**
		 * Add some Controls to the ToolBar.
		 * @param group The containing TreeViewerGroup
		 * @param sash The sash to add this element to
		 * @param index The index of this palette in the sash
		 * @param options All Options the TreeViewerGroup has been initialized with.
		 */
		public default void  addPaletteControls (TreeViewerGroup group, SashForm sash, int index, TreeViewerGroupOption[] options) {}
		
		/**
		 * Whether content shall be added to the left side
		 * @return Return true, if the palette shall be added to the left side.
		 */
		public default boolean addToLeftSide () {
			return false;
		}

		/**
		 * Whether content shall be added to the left side
		 * @return Return true, if the palette shall be added to the right side.
		 */
		public default boolean addToRightSide() {
			return false;
		}
	}
	
	/**
	 * An option that adds info to the persisted group data
	 * @author Lukas
	 */
	public static interface TreeViewerGroupPersistableOption extends TreeViewerGroupOption, IPersistable {
		
	}
	
	/**
	 * An option to add a collapse all button to the toolbar.
	 * @author Lukas
	 */
	public static class TreeViewerGroupToolbarCollapseAllButtonOption implements TreeViewerGroupToolbarOption {
		/**
		 * The added item.
		 */
		protected ToolItem item;
		
		@Override
		public void addToolbarControls(TreeViewerGroup group, ToolBar toolbar, TreeViewerGroupOption[] options) {
			item = new ToolItem(toolbar, SWT.PUSH | SWT.TRAIL);
			item.setImage(BundleContentHelper.getBundleImage(group.bundleID, "icons/collapse_all.gif"));
			item.setToolTipText("Collapse Tree");
			item.addSelectionListener((SelectionListener2) e -> group.getViewer().collapseAll());
		}
		
		@Override
		public void dispose() {			
			item.dispose();
		}
	}
	
	/**
	 * An option to add a split editor vertically button to the toolbar.
	 * @author Lukas
	 */
	public static class TreeViewerGroupToolbarToggleSplitEditorVerticallyButtonOption implements TreeViewerGroupToolbarOption {
		/**
		 * The added item.
		 */
		protected ToolItem item;
		
		@Override
		public void addToolbarControls(TreeViewerGroup group, ToolBar toolbar, TreeViewerGroupOption[] options) {
			item = new ToolItem(toolbar, SWT.PUSH | SWT.TRAIL);
			item.setImage(BundleContentHelper.getBundleImage(group.bundleID, "icons/th_horizontal.gif"));
			item.setToolTipText("Toggle Split Editor");
			item.addSelectionListener( new SelectionListener2 () {				
				@Override
				public void widgetSelected(SelectionEvent e) {
					ICommandService commandService = (ICommandService) ((IServiceLocator) PlatformUI.getWorkbench())
	                         .getService(ICommandService.class);

					Command command = commandService.getCommand("org.eclipse.ui.window.splitEditor");
					
					IHandlerService handlerService = (IHandlerService) ((IServiceLocator) PlatformUI.getWorkbench())
		                    .getService(IHandlerService.class);

					try {
						Parameterization[] params = new Parameterization[] { new Parameterization(
						                    command.getParameter("Splitter.isHorizontal"), "false") };					
						ParameterizedCommand parametrizedCommand = new ParameterizedCommand(command, params);
						handlerService.executeCommand(parametrizedCommand, null);
					} catch (ExecutionException | NotDefinedException | NotEnabledException | NotHandledException e1) {
						e1.printStackTrace();
					}
				}				
			});
		}		
		@Override
		public void dispose() {
			item.dispose();			
		}
	}
	
	/**
	 * Adds an Add Button to the Toolbar.
	 * @author Lukas
	 */
	public static class TreeViewerGroupToolbarAddButtonOption implements TreeViewerGroupToolbarOption {
		/**
		 * The added item.
		 */
		protected ToolItem item;
		
		protected TreeViewerGroup group;
		
		protected AddDropDownSelectionListener listener;
		
		/**
		 * Add some Controls to the ToolBar.
		 * @param toolbar The toolbar to manipulate
		 * @param options All Options the TreeViewerGroup has been initialized with.
		 */
		public void addToolbarControls (TreeViewerGroup group, ToolBar toolbar, TreeViewerGroupOption[] options) {
			this.group = group;
			item = new ToolItem(toolbar, SWT.DROP_DOWN);
			item.setImage(BundleContentHelper.getBundleImage(group.bundleID, "icons/add_obj.gif"));
			item.setToolTipText("Add Sibling of Same Type");
			listener = this.createAddDropDownSelectionListener(item);
			item.addSelectionListener(listener);
		}		
		
		protected AddDropDownSelectionListener createAddDropDownSelectionListener(ToolItem item) {
			return new AddDropDownSelectionListener(item);
		}
		
		
		/**
		 * A {@link SelectionAdapter} that operates on a {@link ToolItem} and allows the user to add items based on the
		 * element currently selected in the tree.
		 *
		 * @author mfreund
		 */
		public class AddDropDownSelectionListener extends SelectionAdapter {
		
			/**
			 * The {@link MenuManager} that creates the menu with the various options for adding an element that can be
			 * selected by the user.
			 */
			private MenuManager menuManager;
		
			/**
			 * This creates an instance.
			 *
			 * @param dropdown
			 *            The {@link ToolItem} on that this listener listens.
			 */
			public AddDropDownSelectionListener(ToolItem dropdown) {
				this.menuManager = new MenuManager();
				this.menuManager.createContextMenu(dropdown.getParent());
			}
		
			@Override
			public void widgetSelected(SelectionEvent event) {
				if (event.detail == SWT.ARROW) {
					ToolItem item = (ToolItem) event.widget;
					Rectangle rect = item.getBounds();
					Point pt = item.getParent().toDisplay(new Point(rect.x, rect.y));
					this.createMenu();
					this.menuManager.getMenu().setLocation(pt.x, pt.y + rect.height);
					this.menuManager.getMenu().setVisible(true);
				} else {
					this.addDefaultElement();
				}
			}
		
			/**
			 * This determines the currently selected element and adds an element of the same type to its parent.
			 */
			private void addDefaultElement() {
				ISelection selection = TreeViewerGroupToolbarAddButtonOption.this.group.treeViewer.getSelection();
				if (selection.isEmpty() || !(selection instanceof StructuredSelection)) {
					// nothing to be done
					return;
				}
				StructuredSelection structuredSelection = (StructuredSelection) selection;
				Object selectedObject = structuredSelection.getFirstElement();
				if (!(selectedObject instanceof EObject)) {
					// nothing to be done
					return;
				}
		
				// A command that will create a new eObject that is of the same
				// type of the selected eObject. The newly created object will then
				// be added to the parent.
				CreateChildCommand command = new CreateChildCommand(TreeViewerGroupToolbarAddButtonOption.this.group.editingDomain,
						((EObject) selectedObject).eContainer(), ((EObject) selectedObject).eContainingFeature(),
						EcoreUtil.create(((EObject) selectedObject).eClass()), null);
		
				// Execute the command and set the selection of the viewer to the
				// newly created object.
				TreeViewerGroupToolbarAddButtonOption.this.group.editingDomain.getCommandStack().execute(command);
				TreeViewerGroupToolbarAddButtonOption.this.group.treeViewer.setSelection(new StructuredSelection(command.getResult().toArray()));
			}
		
			/**
			 * This creates a new menu based on the currently selected element that allows to create children and siblings.
			 */
			private void createMenu() {
		
				// Clear the context menu
				//
				this.menuManager.removeAll();
		
				// Query the new selection for appropriate new child/sibling descriptors
				//
				Collection<?> newChildDescriptors;
				Collection<?> newSiblingDescriptors;
				ISelection selection = TreeViewerGroupToolbarAddButtonOption.this.group.treeViewer.getSelection();
				if (!(selection instanceof IStructuredSelection) || ((IStructuredSelection) selection).size() > 1) {
					// nothing to be done
					return;
				}
		
				boolean doNotCreateSiblingActions = false;
				if (((IStructuredSelection) selection).isEmpty()) {
					// if nothing is selected,we manually select the viewer input; this will allow to add the
					// top level elements in this viewer
					doNotCreateSiblingActions = true; // in this case, we only want allow to create child actions
					try {
						selection = new StructuredSelection(TreeViewerGroupToolbarAddButtonOption.this.group.treeViewer.getInput());
					} catch (Exception e) {
						return;
					}
				}
		
				Object object = ((IStructuredSelection) selection).getFirstElement();
		
				newChildDescriptors = TreeViewerGroupToolbarAddButtonOption.this.group.editingDomain.getNewChildDescriptors(object, null);
				if (doNotCreateSiblingActions) {
					newSiblingDescriptors = new ArrayList<>();
				} else {
					newSiblingDescriptors = TreeViewerGroupToolbarAddButtonOption.this.group.editingDomain.getNewChildDescriptors(null, object);
				}
		
				IEditorPart editorPart = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
						.getActiveEditor();
		
				// Generate actions for selection; populate and redraw the menus.
				//
				ArrayList<IAction> createChildActions = new ArrayList<>();
				for (Object descriptor : newChildDescriptors) {
					if (TreeViewerGroupToolbarAddButtonOption.this.group.isValidDescriptor(descriptor,
							TreeViewerGroupToolbarAddButtonOption.this.group.treeViewer.getContentProvider())) {
						createChildActions.add(this.createCreateChildAction(editorPart, selection, descriptor));
					}
				}
				ArrayList<IAction> createSiblingActions = new ArrayList<>();
				for (Object descriptor : newSiblingDescriptors) {
					if (TreeViewerGroupToolbarAddButtonOption.this.group.isValidDescriptor(descriptor,
							TreeViewerGroupToolbarAddButtonOption.this.group.treeViewer.getContentProvider())) {
						createSiblingActions.add(this.createCreateSiblingAction(editorPart, selection, descriptor));
					}
				}
		
				// Populate the context menu
				//
				this.menuManager.add(new Separator("Add Child"));
				for (IAction iAction : createChildActions) {
					this.menuManager.add(iAction);
				}
				this.menuManager.add(new Separator("Add Sibling"));
				for (IAction iAction : createSiblingActions) {
					this.menuManager.add(iAction);
				}
			}
		
			protected IAction createCreateChildAction(IEditorPart editorPart, ISelection selection, Object descriptor) {
				return new CreateChildAction(editorPart, selection, descriptor);
			}
		
			protected IAction createCreateSiblingAction(IEditorPart editorPart, ISelection selection, Object descriptor) {
				return new CreateSiblingAction(editorPart, selection, descriptor);
			}
			
			public void dispose() {
				menuManager.dispose();
			}
		}
		
		@Override
		public void dispose() {
			listener.dispose();
			item.dispose();
		}
	}
	
	/**
	 * Adds a Toolbar for the model editing to the right side of the tree view.
	 * @author Lukas
	 */
	public static class TreeViewerGroupAddToolPaletteOption implements TreeViewerGroupPaletteOption, TreeViewerGroupPersistableOption {
		
		public TreeViewerGroupToolbarHideEMFPaletteOption TOOLBAR_HIDE_PALLETTE_BUTTON = new TreeViewerGroupToolbarHideEMFPaletteOption();
		
		/**
		 * If the palette is currently hidden.
		 */
		protected boolean hidden = false;
		
		/**
		 * The created palette.
		 */
		protected ModelElementPalette elementPalette;
		
		/**
		 * The group, this palette has been added to.
		 */
		protected TreeViewerGroup group;

		/**
		 * The index of this palette in the sash.
		 */
		protected int index;
		
		/**
		 * The weight, the palette had before hiding.
		 */		
		protected int oldWeight = -1;

		/**
		 * The listener that listens for changed selections on the tree
		 */
		protected ISelectionChangedListener listener;
		
		@Override
		public void addPaletteControls(TreeViewerGroup group, SashForm sash, int index, TreeViewerGroupOption[] options) {
			this.group = group;
			this.index = index;
			elementPalette = getElementPalette();
			elementPalette.createPalette(sash);
			
			if (oldWeight > -1) {
				this.setVisibility(hidden);
			}
			
			this.listener = new ISelectionChangedListener () {
				@Override
				public void selectionChanged(SelectionChangedEvent event) {
					elementPalette.update();
				}				
			};
			
			group.treeViewer.addSelectionChangedListener(this.listener);
			
			elementPalette.update();
		}
		
		/**
		 * Creates an instance of the ModelElementPalette. Override in order to modify the class used.
		 * @return
		 */
		protected TreeViewerGroupModelElementPalette getElementPalette () {
			return new TreeViewerGroupModelElementPalette();
		}
		
		/**
		 * Persists the palette settings.
		 */
		@Override
		public void persist(IDialogSettings settings) {
			settings.put("MODEL_ELEMENT_PALETTE_HIDDEN", isHidden());
			settings.put("MODEL_ELEMENT_PALETTE_OLD_WEIGHT", oldWeight);
			//System.out.println("Store MODEL_ELEMENT_PALETTE_HIDDEN " + isHidden());
			//System.out.println("Store MODEL_ELEMENT_PALETTE_OLD_WEIGHT " + oldWeight);
		}

		/**
		 * Restores the palette settings.
		 */
		@Override
		public void restore(IDialogSettings settings) {
			try {
				this.oldWeight = settings.getInt("MODEL_ELEMENT_PALETTE_OLD_WEIGHT");
				//System.out.println("Restore MODEL_ELEMENT_PALETTE_OLD_WEIGHT " + oldWeight);
			} catch (Exception e) {
				this.oldWeight = 35;
				//System.out.println("Did not Restore MODEL_ELEMENT_PALETTE_OLD_WEIGHT");
			}
			try {
				this.setVisibility(settings.getBoolean("MODEL_ELEMENT_PALETTE_HIDDEN"));
				//System.out.println("Restore MODEL_ELEMENT_PALETTE_HIDDEN " + hidden);
			} catch (Exception e) {
				this.setVisibility(true);
				//System.out.println("Did not Restore MODEL_ELEMENT_PALETTE_HIDDEN");
			}
		}
		
		@Override
		public boolean addToRightSide() {
			return true;
		}
		
		/**
		 * Hide or show the tool palette.
		 * @param hidden Set to true, in order to hide the palette.
		 */
		public void setVisibility (boolean hidden) {
			if (hidden != isHidden()) {
				this.hidden = hidden;
				int[] weights = this.group.treePaletteSeparator.getWeights();
				
				if (elementPalette == null) {
					return;
				}
				
				if (hidden) {
					oldWeight = weights[index];
					weights[index] = 0;
					this.group.treePaletteSeparator.setWeights(weights);
				} else {				
					weights[index] = oldWeight < 8 ? 8 : oldWeight;
					this.group.treePaletteSeparator.setWeights(weights);
				}
			}
		}
		
		/**
		 * Hide or show the tool palette.
		 */
		public void toggleVisibility () {			
			setVisibility(!isHidden());
		}
		
		/**
		 * Returns the hidden property based on the actual size of the displayed palette.
		 * @return whether the palette is visible
		 */
		public boolean isHidden() {
			if (this.group.treePaletteSeparator.isDisposed()) {
				return hidden;
			}
			int[] weights = this.group.treePaletteSeparator.getWeights();
			return weights[index] <= 0;
		}
		
		/**
		 * Adds a show/hide button to the toolbar
		 * @author Lukas
		 */
		public class TreeViewerGroupToolbarHideEMFPaletteOption implements TreeViewerGroupToolbarOption {
			protected ToolItem item;
			protected SelectionListener2 listener;

			@Override
			public void addToolbarControls(TreeViewerGroup group, ToolBar toolbar, TreeViewerGroupOption[] options) {
				item = new ToolItem(toolbar, SWT.PUSH | SWT.TRAIL);
				item.setImage(BundleContentHelper.getBundleImage(group.bundleID, "icons/toggledetailpane_co.gif"));
				item.setToolTipText("Show/Hide Tool Elemet Palette");
				//TODO store and remove the listener
				this.listener = (SelectionListener2) e -> TreeViewerGroupAddToolPaletteOption.this.toggleVisibility();
				item.addSelectionListener(this.listener);
			}
			@Override
			public void dispose() {
				item.removeSelectionListener(listener);
				item.dispose();				
			}
		}
		
		public class TreeViewerGroupModelElementPalette extends ModelElementPalette {
			@Override
			protected ISelection getSelection() {
				return TreeViewerGroupAddToolPaletteOption.this.group.treeViewer.getSelection();
			}

			@Override
			protected EditingDomain getEditingDomain() {
				return TreeViewerGroupAddToolPaletteOption.this.group.editingDomain;
			}

			@Override
			protected TreeViewerGroup getTreeViewerGroup() {
				return TreeViewerGroupAddToolPaletteOption.this.group;
			}
		}
		
		@Override
		public void dispose() {
			this.group.treeViewer.removeSelectionChangedListener(listener);
			elementPalette.dispose();
		}
	}
}
