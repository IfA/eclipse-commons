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
package de.tud.et.ifa.agtele.ui.widgets;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.eclipse.core.commands.Command;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.NotEnabledException;
import org.eclipse.core.commands.NotHandledException;
import org.eclipse.core.commands.Parameterization;
import org.eclipse.core.commands.ParameterizedCommand;
import org.eclipse.core.commands.common.NotDefinedException;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.ui.viewer.ColumnViewerInformationControlToolTipSupport;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.command.CommandParameter;
import org.eclipse.emf.edit.command.CreateChildCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.ui.action.CreateChildAction;
import org.eclipse.emf.edit.ui.action.CreateSiblingAction;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.emf.edit.ui.provider.DecoratingColumLabelProvider;
import org.eclipse.emf.edit.ui.provider.DelegatingStyledCellLabelProvider;
import org.eclipse.emf.edit.ui.provider.DiagnosticDecorator;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.commands.ICommandService;
import org.eclipse.ui.dialogs.FilteredTree;
import org.eclipse.ui.dialogs.PatternFilter;
import org.eclipse.ui.handlers.IHandlerService;
import org.eclipse.ui.services.IServiceLocator;

import de.tud.et.ifa.agtele.emf.AgteleEcoreUtil;
import de.tud.et.ifa.agtele.resources.BundleContentHelper;
import de.tud.et.ifa.agtele.ui.AgteleUIPlugin;
import de.tud.et.ifa.agtele.ui.emf.edit.action.filter.CreateActionChangeListener;
import de.tud.et.ifa.agtele.ui.emf.edit.action.filter.ICreateActionFilter;
import de.tud.et.ifa.agtele.ui.emf.edit.action.filter.IFilterItemFactory;
import de.tud.et.ifa.agtele.ui.emf.edit.action.filter.ItemProviderCreateActionFilter;
import de.tud.et.ifa.agtele.ui.emf.editor.ActionUtil;
import de.tud.et.ifa.agtele.ui.emf.editor.IExtendedCreateElementAction;
import de.tud.et.ifa.agtele.ui.interfaces.IPersistable;
import de.tud.et.ifa.agtele.ui.listeners.EditMultilineFeatureDoubleClickListener;
import de.tud.et.ifa.agtele.ui.listeners.SelectionListener2;
import de.tud.et.ifa.agtele.ui.providers.StateRestoringViewerContentProvider;
import de.tud.et.ifa.agtele.ui.views.EMFModelHelpView;
import de.tud.et.ifa.agtele.ui.views.EMFModelHelpView.HelpListener;

/**
 * A class that represents an SWT {@link Group} containing a {@link FilteredTree filtered tree viewer} and optionally a
 * {@link ToolBar}. It supports {@link IPersistable persisting and restoring} the state of the tree viewer (i.e. the
 * expanded paths).
 *
 * @author mfreund
 *
 */
public class TreeViewerGroup extends SelectionRestoringFilteredTree implements IPersistable, ISelectionProvider {

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
	 * Use this constructor if you want to add one or multiple {@link TreeViewerGroupOption TreeViewerGroupOptions} to
	 * the viewer.
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
	 *            A set of options that are used to alter the default composition of the TreeViewerGroup
	 */
	public TreeViewerGroup(Composite parent, ComposedAdapterFactory adapterFactory, EditingDomain editingDomain,
			IDialogSettings dialogSettings, String groupText, TreeViewerGroupOption... options) {

		super(parent,true,true);
		this.parent = parent;
		this.groupText = groupText;
		this.editingDomain = editingDomain;
		this.adapterFactory = adapterFactory;
		this.dialogSettings = dialogSettings;
		this.options = options == null ? new TreeViewerGroupOption[0] : options;
		this.init(SWT.MULTI, new PatternFilter());
	}

	/**
	 * The default option to add a collapse all button to the tool bar.
	 *
	 * @return a new instance of the TreeViewerGroupToolbarCollapseAllButtonOption
	 */
	public static TreeViewerGroupToolbarCollapseAllButtonOption TOOLBAR_COLLAPSE_ALL_BUTTON() {

		return new TreeViewerGroupToolbarCollapseAllButtonOption();
	}

	/**
	 * The default option to add an add button to the tool bar.
	 *
	 * @return a new instance of TreeViewerGroupToolbarAddButtonOption
	 */
	public static TreeViewerGroupToolbarAddButtonOption TOOLBAR_ADD_BUTTON() {

		return new TreeViewerGroupToolbarAddButtonOption();
	}

	/**
	 * The default option to add the model elements tool palette.
	 *
	 * @return a new instance of TreeViewerGroupAddToolPaletteOption
	 */
	public static TreeViewerGroupAddToolPaletteOption PALETTE_MODEL_ELEMENTS() {

		return new TreeViewerGroupAddToolPaletteOption();
	}

	/**
	 * The default option to add a toggle editor split vertically button.
	 *
	 * @return a new instance of TreeViewerGroupToolbarToggleSplitEditorVerticallyButtonOption
	 */
	public static TreeViewerGroupToolbarToggleSplitEditorVerticallyButtonOption TOOLBAR_TOGGLE_SPLIT_VERTICALLY_BUTTON() {

		return new TreeViewerGroupToolbarToggleSplitEditorVerticallyButtonOption();
	}
	
	/**
	 * The default option to add bind the {@link HelpListener} so that the user can press F1 to open the
	 * {@link EMFModelHelpView}..
	 *
	 * @return a new instance of TreeViewerGroupBindHelpListenerOption
	 */
	public static TreeViewerGroupBindHelpListenerOption BIND_HELP_LISTENER() {
		
		return new TreeViewerGroupBindHelpListenerOption();
	}

	/**
	 * @return a new instance of TreeViewerGroupBindDoubleClickEditMultilineFeatureOption
	 */
	public static TreeViewerGroupBindDoubleClickEditMultilineFeatureOption BIND_DOUBLE_CLICK_EDIT_MULTILINE_FEATURE_LISTENER() {

		return new TreeViewerGroupBindDoubleClickEditMultilineFeatureOption();
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

		this.disposeListener = e -> {
			parent.removeDisposeListener(TreeViewerGroup.this.disposeListener);
			TreeViewerGroup.this.dispose();
		};

		parent.addDisposeListener(this.disposeListener);

		// Set the layout for the main composite
		if (parent.getLayout() instanceof GridLayout) {
			this.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		}

		GridLayoutFactory.swtDefaults().margins(0, 0).applyTo(this);

		// The group to hold everything else.
		// only create Group if the groupText is set
		// else use a
		if (this.groupText != null) {
			this.group = new Group(this, SWT.NONE);
			this.group.setText(this.groupText);
			GridDataFactory.swtDefaults().grab(true, true).align(SWT.FILL, SWT.FILL).applyTo(this.group);
			GridLayoutFactory.swtDefaults().applyTo(this.group);
		}

		if (this.showFilterControls) {
			this.createFilterTools();
			this.createToolbar(this.toolbarComposite);
		}

		List<TreeViewerGroupPaletteOption> paletteOptions = Arrays.asList(this.options).parallelStream()
				.filter(i -> i instanceof TreeViewerGroupPaletteOption).map(i -> (TreeViewerGroupPaletteOption) i)
				.collect(Collectors.toList());

		int paletteCount = 0;
		int treeIndex = 0;

		// creates the palette controls to the left side of the tree
		for (TreeViewerGroupPaletteOption i : paletteOptions) {
			if (i.addToLeftSide()) {
				if (this.treePaletteSeparator == null) {
					this.treePaletteSeparator = new SashForm(this.group == null ? this : this.group, SWT.SMOOTH);
					this.treePaletteSeparator.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
				}

				i.addPaletteControls(this, this.treePaletteSeparator, paletteCount, this.options);
				paletteCount += 1;
			}
		}
		treeIndex = paletteCount;
		paletteCount += 1;

		// create the tree and its composite
		if (this.treePaletteSeparator != null) {
			this.treeComposite = new Composite(this.treePaletteSeparator, SWT.NONE);
		} else {
			this.treeComposite = new Composite(this.group == null ? this : this.group, SWT.NONE);
		}

		GridLayoutFactory.swtDefaults().margins(0, 0).applyTo(this.treeComposite);
		GridDataFactory.swtDefaults().grab(true, true).align(SWT.FILL, SWT.FILL).applyTo(this.treeComposite);
		this.createTreeControl(this.treeComposite, treeStyle);

		// adds the palettes to the right of the tree
		for (TreeViewerGroupPaletteOption i : paletteOptions) {
			if (i.addToRightSide()) {
				if (this.treePaletteSeparator == null) {
					this.treePaletteSeparator = new SashForm(this.group == null ? this : this.group, SWT.SMOOTH);
					this.treePaletteSeparator.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
					// Move the tree
					this.treeComposite.setParent(this.treePaletteSeparator);
				}

				i.addPaletteControls(this, this.treePaletteSeparator, paletteCount, this.options);
				paletteCount += 1;
			}
		}

		// calculate the weights of the sash form
		if (this.treePaletteSeparator != null
				&& (this.sashWeights == null || this.sashWeights.length != paletteCount)) {
			this.sashWeights = new int[paletteCount];
			for (int i = 0; i < paletteCount; i += 1) {
				this.sashWeights[i] = 1;
			}
			this.sashWeights[treeIndex] = paletteCount;
			this.treePaletteSeparator.setWeights(this.sashWeights);
		}
	}

	/**
	 * creates the filter controls
	 */
	protected void createFilterTools() {

		// This composite hosts two children: the filter composite and the
		// tool bar composite.
		this.filterComposite = new Composite(this.group == null ? this : this.group, SWT.NONE);
		this.filterComposite.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false));

		GridLayoutFactory.swtDefaults().numColumns(2).margins(0, 0).applyTo(this.filterComposite);

		// create filter controls that will take up all of the horizontal space
		// except the one that is taken by the tool bar
		Composite filterBoxComposite = new Composite(this.filterComposite, SWT.BORDER);
		filterBoxComposite.setBackground(this.getDisplay().getSystemColor(SWT.COLOR_LIST_BACKGROUND));
		filterBoxComposite.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false));

		GridLayoutFactory.swtDefaults().numColumns(2).margins(0, 0).applyTo(filterBoxComposite);
		filterBoxComposite.setFont(this.parent.getFont());
		this.createFilterControls(filterBoxComposite);

		// create a toolbar besides the filter controls
		this.toolbarComposite = new Composite(this.filterComposite, SWT.NONE);
		GridDataFactory.swtDefaults().align(SWT.BEGINNING, SWT.BEGINNING).applyTo(this.toolbarComposite);
		GridLayoutFactory.swtDefaults().margins(0, 0).applyTo(this.toolbarComposite);
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
	 * @return The created {@link TreeViewer}.
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

						String documentation = "";

						if (element instanceof EObject && ((EObject) element).eClass() != null) {
							documentation = "<b>Element Type: " + ((EObject) element).eClass().getName() + "</b>";

							String eClassDoc = EcoreUtil.getDocumentation(((EObject) element).eClass());
							if (eClassDoc != null && !eClassDoc.isEmpty()) {
								documentation += "<p /><b>Documentation: </b>" + eClassDoc;
							}
						}

						String toolTip = super.getToolTipText(element);

						return toolTip == null || toolTip.isEmpty() ? documentation : toolTip + "<p />" + documentation;
					}
				}));
		treeViewer.setContentProvider(new StateRestoringViewerContentProvider(this.adapterFactory, treeViewer));
		new ColumnViewerInformationControlToolTipSupport(treeViewer,
				new DiagnosticDecorator.Styled.EditingDomainLocationListener(this.editingDomain, treeViewer));

		// If necessary, initialize additional options
		//
		Collection<TreeViewerGroupOption> options = Arrays.asList(this.options).parallelStream()
				.filter(o -> o instanceof TreeViewerGroupSelectionViewerOption).collect(Collectors.toList());
		for (TreeViewerGroupOption option : options) {
			((TreeViewerGroupSelectionViewerOption) option).init(treeViewer, this.adapterFactory, this);
		}

		return treeViewer;
	}
	
	@Override
	public boolean isFocusControl() {
		//This needs to be overridden in order to enable setting the focus after a model change
		return this.getTreeViewer().getControl().isFocusControl();
	}
	
	/**
	 * Returns the tree viewer
	 *
	 * @return The {@link TreeViewer}.
	 */
	public TreeViewer getTreeViewer() {

		return this.treeViewer;
	}

	/**
	 * This creates a tool bar if necessary.
	 *
	 * @param parent
	 *            The parent composite.
	 */
	protected void createToolbar(Composite parent) {

		List<TreeViewerGroupToolbarOption> toolbarOptions = Arrays.asList(this.options).parallelStream()
				.filter(i -> i instanceof TreeViewerGroupToolbarOption).map(i -> (TreeViewerGroupToolbarOption) i)
				.collect(Collectors.toList());

		// Nothing to be done
		//
		if (toolbarOptions.isEmpty()) {
			return;
		}

		// Create the toolbar
		//
		this.toolbar = new ToolBar(parent, SWT.NONE);
		this.toolbar.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));

		// Create the toolbar controls
		//
		toolbarOptions.stream().forEach(i -> i.addToolbarControls(this, this.toolbar, this.options));

		this.toolbar.pack();
	}

	@Override
	public void persist(IDialogSettings settings) {

		// Persist the expanded tree paths of the tree viewer
		//
		Set<String> paths = new LinkedHashSet<>();

		Arrays.asList(this.treeViewer.getExpandedTreePaths()).stream().forEach(path -> {

			if (path.getLastSegment() instanceof EObject) {
				// use the URI of the eObject as unique identifier
				//
				paths.add(EcoreUtil.getURI((EObject) path.getLastSegment()).toString());
			} else if (path.getLastSegment() instanceof Resource) {
				// use the URI of the eObject as unique identifier
				//
				paths.add(((Resource) path.getLastSegment()).getURI().toString());
			} else {
				// TODO currently, persisting only works for objects
				// representing a real eObject
				return;
			}
		});

		settings.put("EXPANDED_TREE_PATHS", paths.toArray(new String[paths.size()]));

		// Persist the filter text
		//
		settings.put("FILTER_TEXT", this.getFilterString() != null ? this.getFilterString() : "");

		if (this.treePaletteSeparator != null) {
			this.sashWeights = this.treePaletteSeparator.getWeights();
			String[] weights = new String[this.sashWeights.length];
			for (int i = 0; i < this.sashWeights.length; i += 1) {
				weights[i] = Integer.toString(this.sashWeights[i]);
			}

			settings.put("PALETTE_WEIGHTS", weights);
		}

		// Persist the various options passed by the user
		//
		Arrays.asList(this.options).stream().filter(i -> i instanceof TreeViewerGroupPersistableOption)
				.forEach(i -> ((TreeViewerGroupPersistableOption) i).persist(settings));
	}

	@Override
	public void restore(IDialogSettings settings) {

		// Restore the expanded tree paths of the tree viewer
		//
		if (settings.getArray("EXPANDED_TREE_PATHS") != null) {
			String[] paths = settings.getArray("EXPANDED_TREE_PATHS");
			List<Object> expandedList = new ArrayList<>();

			for (String path : new LinkedHashSet<String>(Arrays.asList(paths))) {
				Object expanded;
				/*
				 * as the URI of an eObject also reflects the containing resource, we can use this to uniquely identify
				 * an eObject inside a resource set
				 */
				try {
					expanded = this.editingDomain.getResourceSet().getEObject(URI.createURI(path), true);
				} catch (Exception e) {
					// If the EObject can't be loaded, it may be because it's a
					// resource
					expanded = this.editingDomain.getResourceSet().getResource(URI.createURI(path), true);
				}
				if (expanded != null) {
					expandedList.add(expanded);
				}
			}
			ITreeContentProvider provider = this.treeViewer.getContentProvider() instanceof ITreeContentProvider ? 
					(ITreeContentProvider) this.treeViewer.getContentProvider() : null;
			List<Object> treeContents = new ArrayList<>();
			if (provider != null) {
				this.initializeTreeContent(treeContents, provider);
			}
//			treeContents.forEach(o -> this.treeViewer.setExpandedState(o, false));
			
			ExpandedLoop:
			for (Object expanded : expandedList) {
				//check if all containers of the expanded elements are expanded as well in order to prevent endless expansion of nodes (bug in underlying treeviewer class)
				if (expanded instanceof Resource) {
					this.treeViewer.setExpandedState(expanded, true);					
				} else if (expanded instanceof EObject) {
					EObject expandedObject = (EObject) expanded;
					for (EObject container : AgteleEcoreUtil.getAllContainers(expandedObject)) {
						if (provider != null && treeContents.contains(container) && !expandedList.contains(container)) {
							continue ExpandedLoop;
						}
					}
					//do not check resource expanded state in order to  
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
				this.sashWeights = new int[weights.length];
				for (int i = 0; i < weights.length; i += 1) {
					this.sashWeights[i] = new Integer(weights[i]);
				}
				if (this.treePaletteSeparator != null && !this.treePaletteSeparator.isDisposed()) {
					this.treePaletteSeparator.setWeights(this.sashWeights);
				}
			}
		} catch (Exception e) {
			// do nothing, default weights will kick in
		}

		for (TreeViewerGroupOption i : this.options) {
			if (i instanceof TreeViewerGroupPersistableOption) {
				((TreeViewerGroupPersistableOption) i).restore(settings);
			}
		}
	}

	@SuppressWarnings("unchecked")
	protected void initializeTreeContent(List<Object> treeContents, ITreeContentProvider provider) {
		if (this.treeViewer.getInput() instanceof Collection) {
			treeContents.addAll((Collection<? extends Object>)this.treeViewer.getInput());			
		} else if (this.treeViewer.getInput().getClass().isArray()) {
			treeContents.addAll(Arrays.asList(this.treeViewer.getInput()));
		} else {
			treeContents.add(this.treeViewer.getInput());
		}
		
		for (Object o : new ArrayList<>(treeContents)) {
			this.addNodes(treeContents, provider, o);
		}
		
	}
	
	protected void addNodes(List<Object> treeContents, ITreeContentProvider provider, Object obj) {
		for (Object o : provider.getChildren(obj)) {
			if (!treeContents.contains(o)) {
				treeContents.add(o);
				this.addNodes(treeContents, provider, o);
			}
		}
	}

	/**
	 *
	 */
	@Override
	public void dispose() {

		Arrays.asList(this.options).stream().forEach(TreeViewerGroupOption::dispose);
	}

	@Override
	public void addSelectionChangedListener(ISelectionChangedListener listener) {
	
		this.treeViewer.addSelectionChangedListener(listener);
	}

	@Override
	public ISelection getSelection() {
	
		return this.treeViewer.getSelection();
	}

	@Override
	public void removeSelectionChangedListener(ISelectionChangedListener listener) {
	
		this.treeViewer.removeSelectionChangedListener(listener);
	}

	@Override
	public void setSelection(ISelection selection) {
	
		this.treeViewer.setSelection(selection);
	}

	/**
	 * Can be anything, that alters the TreeViewerGroup
	 *
	 * @author Lukas
	 */
	public static interface TreeViewerGroupOption {
		
		/**
		 * Disposes the created UI elements and removes created event listeners from other elements.
		 */
		public void dispose();
	}

	public static interface TreeViewerGroupSelectionViewerOption extends TreeViewerGroupOption {
		default public void init(TreeViewer viewer, AdapterFactory factory, TreeViewerGroup group) {}
	}
	
	/**
	 * A ToolbarOption alters the contents of the Toolbar.
	 *
	 * @author Lukas
	 */
	public static interface TreeViewerGroupToolbarOption extends TreeViewerGroupOption {

		/**
		 * Add some Controls to the ToolBar.
		 *
		 * @param group
		 *            The containing TreeViewerGroup
		 * @param toolbar
		 *            The toolbar to manipulate
		 * @param options
		 *            All Options the TreeViewerGroup has been initialized with.
		 */
		public default void addToolbarControls(TreeViewerGroup group, ToolBar toolbar,
				TreeViewerGroupOption[] options) {

		}
	}

	/**
	 * A Palette Option can alter the side content next to the actual tree.
	 *
	 * @author Lukas
	 */
	public static interface TreeViewerGroupPaletteOption extends TreeViewerGroupOption {

		/**
		 * Add some Controls to the ToolBar.
		 *
		 * @param group
		 *            The containing TreeViewerGroup
		 * @param sash
		 *            The sash to add this element to
		 * @param index
		 *            The index of this palette in the sash
		 * @param options
		 *            All Options the TreeViewerGroup has been initialized with.
		 */
		public default void addPaletteControls(TreeViewerGroup group, SashForm sash, int index,
				TreeViewerGroupOption[] options) {

		}

		/**
		 * Whether content shall be added to the left side
		 *
		 * @return Return true, if the palette shall be added to the left side.
		 */
		public default boolean addToLeftSide() {

			return false;
		}

		/**
		 * Whether content shall be added to the left side
		 *
		 * @return Return true, if the palette shall be added to the right side.
		 */
		public default boolean addToRightSide() {

			return false;
		}
	}

	/**
	 * An option that adds info to the persisted group data
	 *
	 * @author Lukas
	 */
	public static interface TreeViewerGroupPersistableOption extends TreeViewerGroupOption, IPersistable {

	}

	/**
	 * An option to add a collapse all button to the toolbar.
	 *
	 * @author Lukas
	 */
	public static class TreeViewerGroupToolbarCollapseAllButtonOption implements TreeViewerGroupToolbarOption {

		/**
		 * The added item.
		 */
		protected ToolItem item;

		@Override
		public void addToolbarControls(TreeViewerGroup group, ToolBar toolbar, TreeViewerGroupOption[] options) {

			this.item = new ToolItem(toolbar, SWT.PUSH | SWT.TRAIL);
			this.item.setImage(BundleContentHelper.getBundleImage(group.bundleID, "icons/collapse_all.gif"));
			this.item.setToolTipText("Collapse Tree");
			this.item.addSelectionListener((SelectionListener2) e -> group.getViewer().collapseAll());
		}

		@Override
		public void dispose() {
			this.item.getImage().dispose();
			this.item.dispose();
		}
	}

	/**
	 * An option to add a split editor vertically button to the toolbar.
	 *
	 * @author Lukas
	 */
	public static class TreeViewerGroupToolbarToggleSplitEditorVerticallyButtonOption
			implements TreeViewerGroupToolbarOption {

		/**
		 * The added item.
		 */
		protected ToolItem item;

		@Override
		public void addToolbarControls(TreeViewerGroup group, ToolBar toolbar, TreeViewerGroupOption[] options) {

			this.item = new ToolItem(toolbar, SWT.PUSH | SWT.TRAIL);
			this.item.setImage(BundleContentHelper.getBundleImage(group.bundleID, "icons/th_horizontal.gif"));
			this.item.setToolTipText("Toggle Split Editor");
			this.item.addSelectionListener((SelectionListener2) e -> {
				ICommandService commandService = ((IServiceLocator) PlatformUI.getWorkbench())
						.getService(ICommandService.class);

				Command command = commandService.getCommand("org.eclipse.ui.window.splitEditor");

				IHandlerService handlerService = ((IServiceLocator) PlatformUI.getWorkbench())
						.getService(IHandlerService.class);

				try {
					Parameterization[] params = new Parameterization[] {
							new Parameterization(command.getParameter("Splitter.isHorizontal"), "false") };
					ParameterizedCommand parametrizedCommand = new ParameterizedCommand(command, params);
					handlerService.executeCommand(parametrizedCommand, null);
				} catch (ExecutionException | NotDefinedException | NotEnabledException | NotHandledException e1) {
					e1.printStackTrace();
				}
			});
		}

		@Override
		public void dispose() {
			this.item.getImage().dispose();
			this.item.dispose();
		}
	}

	/**
	 * Adds an Add Button to the Toolbar.
	 *
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
		 *
		 * @param toolbar
		 *            The toolbar to manipulate
		 * @param options
		 *            All Options the TreeViewerGroup has been initialized with.
		 */
		@Override
		public void addToolbarControls(TreeViewerGroup group, ToolBar toolbar, TreeViewerGroupOption[] options) {

			this.group = group;
			this.item = new ToolItem(toolbar, SWT.DROP_DOWN);
			this.item.setImage(BundleContentHelper.getBundleImage(group.bundleID, "icons/add_obj.gif"));
			this.item.setToolTipText("Add Sibling of Same Type");
			this.listener = this.createAddDropDownSelectionListener(this.item);
			this.item.addSelectionListener(this.listener);
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
				// type of the selected eObject. The newly created object will
				// then
				// be added to the parent.
				CreateChildCommand command = new CreateChildCommand(
						TreeViewerGroupToolbarAddButtonOption.this.group.editingDomain,
						((EObject) selectedObject).eContainer(), ((EObject) selectedObject).eContainingFeature(),
						EcoreUtil.create(((EObject) selectedObject).eClass()), null);

				// Execute the command and set the selection of the viewer to
				// the
				// newly created object.
				TreeViewerGroupToolbarAddButtonOption.this.group.editingDomain.getCommandStack().execute(command);
				TreeViewerGroupToolbarAddButtonOption.this.group.treeViewer
						.setSelection(new StructuredSelection(command.getResult().toArray()));
			}

			/**
			 * This creates a new menu based on the currently selected element that allows to create children and
			 * siblings.
			 */
			private void createMenu() {

				// Clear the context menu
				//
				this.menuManager.removeAll();

				ISelection selection = TreeViewerGroupToolbarAddButtonOption.this.group.treeViewer.getSelection();
				if (!(selection instanceof IStructuredSelection) || ((IStructuredSelection) selection).size() > 1) {
					// nothing to be done
					return;
				}

				List<CreateChildAction> createChildActions;
				List<CreateSiblingAction> createSiblingActions = new ArrayList<>();

				if (((IStructuredSelection) selection).isEmpty()) {
					// if nothing is selected,we manually select the viewer
					// input; this will allow to add the
					// top level elements in this viewer
					//
					try {
						selection = new StructuredSelection(
								TreeViewerGroupToolbarAddButtonOption.this.group.treeViewer.getInput());
					} catch (Exception e) {
						return;
					}
					createChildActions = ActionUtil.getCreateChildActions(
							TreeViewerGroupToolbarAddButtonOption.this.group.getTreeViewer(),
							(StructuredSelection) selection);
				} else {

					createChildActions = ActionUtil
							.getCreateChildActions(TreeViewerGroupToolbarAddButtonOption.this.group.getTreeViewer());
					createSiblingActions = ActionUtil
							.getCreateSiblingActions(TreeViewerGroupToolbarAddButtonOption.this.group.getTreeViewer());
				}

				// Populate the context menu
				//
				this.menuManager.add(new Separator("Add Child"));
				createChildActions.stream().forEach(this.menuManager::add);
				this.menuManager.add(new Separator("Add Sibling"));
				createSiblingActions.stream().forEach(this.menuManager::add);
			}

			public void dispose() {

				this.menuManager.dispose();
			}
		}

		@Override
		public void dispose() {
			this.item.getImage().dispose();
			this.listener.dispose();
			this.item.dispose();
		}
	}

	/**
	 * Adds a Toolbar for the model editing to the right side of the tree view.
	 *
	 * @author Lukas
	 */
	public static class TreeViewerGroupAddToolPaletteOption
			implements TreeViewerGroupPaletteOption, TreeViewerGroupPersistableOption {

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
		protected float oldWeight = (float) -1.0;
		
		protected AddToolPaletteFilterOption toolPaletteFilterOption = null;

		/**
		 * The listener that listens for changed selections on the tree
		 */
		protected ISelectionChangedListener listener;

		@Override
		public void addPaletteControls(TreeViewerGroup group, SashForm sash, int index,
				TreeViewerGroupOption[] options) {

			this.group = group;
			this.index = index;
			this.elementPalette = this.getElementPalette();
			
			for(TreeViewerGroupOption option : options) {
				if (option instanceof AddToolPaletteFilterOption) {
					this.toolPaletteFilterOption = (AddToolPaletteFilterOption)option;
					this.elementPalette.setFilterOption(this.toolPaletteFilterOption);
				}
			}
			
			this.elementPalette.createPalette(sash);

			if (this.oldWeight > -1) {
				this.setVisibility(this.hidden);
			}

			this.listener = event -> TreeViewerGroupAddToolPaletteOption.this.elementPalette.update();

			group.treeViewer.addSelectionChangedListener(this.listener);

			this.elementPalette.update();
		}

		public int getTotalWeight(int[] ar) {

			return IntStream.of(ar).sum();
		}

		public float getRelativeWidth() {

			int[] weights = this.group.treePaletteSeparator.getWeights();
			int total = this.getTotalWeight(weights);

			return (float) weights[this.index] / (float) total;
		}

		public void setRelativeWidth(float width) {

			if (width < 0 || width > 1) {
				return;
			}

			int[] weights = this.group.treePaletteSeparator.getWeights();
			int total = this.getTotalWeight(weights) - weights[this.index];

			weights[this.index] = (int) (Math.floor(total / (1.0 - width)) * width);

			this.group.treePaletteSeparator.setWeights(weights);
		}

		/**
		 * Creates an instance of the ModelElementPalette. Override in order to modify the class used.
		 *
		 * @return
		 */
		protected TreeViewerGroupModelElementPalette getElementPalette() {
			TreeViewerGroupModelElementPalette result = new TreeViewerGroupModelElementPalette();
			
			if (this.toolPaletteFilterOption != null) {
				result.setFilterOption(this.toolPaletteFilterOption);			
			}
			
			return result;
		}

		/**
		 * Persists the palette settings.
		 */
		@Override
		public void persist(IDialogSettings settings) {

			settings.put("MODEL_ELEMENT_PALETTE_HIDDEN", this.isHidden());
			settings.put("MODEL_ELEMENT_PALETTE_OLD_WEIGHT", this.oldWeight);
		}

		/**
		 * Restores the palette settings.
		 */
		@Override
		public void restore(IDialogSettings settings) {

			try {
				this.oldWeight = settings.getFloat("MODEL_ELEMENT_PALETTE_OLD_WEIGHT");
			} catch (Exception e) {
				this.oldWeight = (float) .35;
			}
			try {
				this.setVisibility(settings.getBoolean("MODEL_ELEMENT_PALETTE_HIDDEN"));
			} catch (Exception e) {
				this.setVisibility(true);
			}
		}

		@Override
		public boolean addToRightSide() {

			return true;
		}

		/**
		 * Hide or show the tool palette.
		 *
		 * @param hidden
		 *            Set to true, in order to hide the palette.
		 */
		public void setVisibility(boolean hidden) {

			if (hidden != this.isHidden()) {
				this.hidden = hidden;
				int[] weights = this.group.treePaletteSeparator.getWeights();

				if (this.elementPalette == null) {
					return;
				}

				if (hidden) {
					this.oldWeight = this.getRelativeWidth();
					weights[this.index] = 0;
					this.group.treePaletteSeparator.setWeights(weights);
				} else {
					this.setRelativeWidth(this.oldWeight < .1 ? (float) .1 : this.oldWeight);
				}
			}
		}

		/**
		 * Hide or show the tool palette.
		 */
		public void toggleVisibility() {

			this.setVisibility(!this.isHidden());
		}

		/**
		 * Returns the hidden property based on the actual size of the displayed palette.
		 *
		 * @return whether the palette is visible
		 */
		public boolean isHidden() {

			if (this.group.treePaletteSeparator.isDisposed()) {
				return this.hidden;
			}
			int[] weights = this.group.treePaletteSeparator.getWeights();
			return weights[this.index] <= 0;
		}
		
		/**
		 * Adds a show/hide button to the toolbar
		 *
		 * @author Lukas
		 */
		public class TreeViewerGroupToolbarHideEMFPaletteOption implements TreeViewerGroupToolbarOption {
			
			protected ToolItem item;
			
			protected SelectionListener2 listener;
			
			@Override
			public void addToolbarControls(TreeViewerGroup group, ToolBar toolbar, TreeViewerGroupOption[] options) {
				
				this.item = new ToolItem(toolbar, SWT.PUSH | SWT.TRAIL);
				this.item.setImage(BundleContentHelper.getBundleImage(group.bundleID, "icons/toggledetailpane_co.gif"));
				this.item.setToolTipText("Show/Hide Tool Elemet Palette");
				this.listener = (SelectionListener2) e -> TreeViewerGroupAddToolPaletteOption.this.toggleVisibility();
				this.item.addSelectionListener(this.listener);
			}
			
			@Override
			public void dispose() {
				this.item.getImage().dispose();
				this.item.removeSelectionListener(this.listener);
				this.item.dispose();
			}
		}
		
		/**
		 * Adds the filter feature to the element palette
		 *
		 * @author Lukas
		 */
		public static class AddToolPaletteFilterOption
			implements TreeViewerGroupSelectionViewerOption, TreeViewerGroupPersistableOption {

			protected IFilterItemFactory factory;
			protected ToolBar childrenFilterBar;
			protected ToolBar siblingsFilterBar;
			
			protected ItemProviderCreateActionFilter childrenFilter, siblingsFilter;

			public AddToolPaletteFilterOption(IFilterItemFactory factory) {
				this.factory = factory;
				this.childrenFilter = new ItemProviderCreateActionFilter();
				this.siblingsFilter = new ItemProviderCreateActionFilter();
				this.childrenFilter.setContext("CHILDREN_FILTER"); //needed for filter persistence
				this.siblingsFilter.setContext("SIBLINGS_FILTER");
			}
			
			@Override
			public void dispose() {
				this.childrenFilterBar.dispose();
				this.siblingsFilterBar.dispose();
			}

			@Override
			public void persist(IDialogSettings settings) {
				this.childrenFilter.persist(settings);
				this.siblingsFilter.persist(settings);
			}

			@Override
			public void restore(IDialogSettings settings) {
				this.childrenFilter.restore(settings);
				this.siblingsFilter.restore(settings);
			}
			
			public void createFilterBar(Composite parent, int index) {
				if (index == 0) {
					this.createChildrenFilterBar(parent);
				} else {
					this.createSiblingsFilterBar(parent);
				}
			}
						
			public void createChildrenFilterBar (Composite parent) {
				this.childrenFilterBar = new ToolBar(parent, SWT.FLAT | SWT.RIGHT);
			}
			
			public void createSiblingsFilterBar (Composite parent) {
				this.siblingsFilterBar = new ToolBar(parent, SWT.FLAT | SWT.RIGHT);
			}
			
			public void addFilterChangeListener (CreateActionChangeListener listener) {
				this.childrenFilter.addListener(listener);
				this.siblingsFilter.addListener(listener);
			}
			
			public void removeFilterChangeListener (CreateActionChangeListener listener) {
				this.childrenFilter.removeListener(listener);
				this.siblingsFilter.removeListener(listener);
			}
			
			
			public void updateSelection(EObject selected) {
				for (ToolItem c : new ArrayList<>(Arrays.asList(this.childrenFilterBar.getItems()))) {
					c.dispose();
				}
				
				for (ToolItem c : new ArrayList<>(Arrays.asList(this.siblingsFilterBar.getItems()))) {
					c.dispose();
				}
				
				if (selected != null) {
					this.childrenFilter.getFilters(selected);
					this.siblingsFilter.getFilters(selected.eContainer());
					
					this.factory.createControls(childrenFilter, this.childrenFilterBar);
					this.factory.createControls(siblingsFilter, this.siblingsFilterBar);
				}
				this.childrenFilterBar.redraw();
				this.childrenFilterBar.update();
				this.siblingsFilterBar.redraw();
				this.siblingsFilterBar.update();
			}
			
			protected Map<IAction, CommandParameter> mapActions(List<? extends IAction> actions) {
				 Map<IAction, CommandParameter> result = new LinkedHashMap<>();
				 
				 for (IAction action:actions) {
					 if (action instanceof IExtendedCreateElementAction) {
						 result.put(action, ((IExtendedCreateElementAction)action).getDescriptor());
					 }
				 }
				 
				 return result;
			}
			
			public List<IAction> filterChildren (List<? extends IAction> actions) {
				return filter(actions, this.childrenFilter);
			}
			
			public List<IAction> filterSiblings (List<? extends IAction> actions) {
				return filter(actions, this.siblingsFilter);
			}

			protected List<IAction> filter(List<? extends IAction> actions, ItemProviderCreateActionFilter filter) {
				Map<IAction, CommandParameter> mapped = this.mapActions(actions);
				List<IAction> result = new ArrayList<>(actions);
				List<CommandParameter> parameters = new ArrayList<>(mapped.values());
				
				final List<CommandParameter> filteredParameters = filter.filterCommands(parameters, parameters);
				
				result.removeIf(a -> mapped.containsKey(a) && !filteredParameters.contains(mapped.get(a)));
				
				return result;
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

		public static class TreeViewerGroupAddToolPaletteToolbarHideEMFPaletteOption
				extends TreeViewerGroupAddToolPaletteOption implements TreeViewerGroupToolbarOption {

			protected ToolItem toolBarItem;

			protected SelectionListener2 toolbarListener;

			@Override
			public void addToolbarControls(TreeViewerGroup group, ToolBar toolbar, TreeViewerGroupOption[] options) {

				this.toolBarItem = new ToolItem(toolbar, SWT.PUSH | SWT.TRAIL);
				this.toolBarItem
						.setImage(BundleContentHelper.getBundleImage(group.bundleID, "icons/toggledetailpane_co.gif"));
				this.toolBarItem.setToolTipText("Show/Hide Tool Elemet Palette");
				this.toolbarListener = (SelectionListener2) e -> this.toggleVisibility();
				this.toolBarItem.addSelectionListener(this.toolbarListener);
			}

			@Override
			public void dispose() {
				this.toolBarItem.getImage().dispose();
				this.toolBarItem.removeSelectionListener(this.toolbarListener);
				this.toolBarItem.dispose();
				super.dispose();
			}
		}

		@Override
		public void dispose() {

			this.group.treeViewer.removeSelectionChangedListener(this.listener);
			this.elementPalette.dispose();
		}
	}

	/**
	 * Binds the {@link HelpListener} so that the user can press F1 to open the {@link EMFModelHelpView}.
	 *
	 * @author mfreund
	 */
	public static class TreeViewerGroupBindHelpListenerOption implements TreeViewerGroupSelectionViewerOption {

		/**
		 * The {@link TreeViewer} to which the help listener will be added.
		 */
		protected TreeViewer viewer;

		/**
		 * This listens to the HelpEvent (F1)
		 */
		protected EMFModelHelpView.HelpListener helpListener;
		
		protected TreeViewerGroup group;

		/**
		 * Initializes the {@link HelpListener} for the given {@link TreeViewer}.
		 *
		 * @param viewer
		 */
		@Override
		public void init(TreeViewer viewer, AdapterFactory factory, TreeViewerGroup group) {

			this.viewer = viewer;
			this.helpListener = new EMFModelHelpView.HelpListener();
			this.group = group;

			this.viewer.addHelpListener(this.helpListener);
			for (Control control : this.group.getChildren()) {
				control.addHelpListener(this.helpListener);
			}
		}

		@Override
		public void dispose() {

			this.viewer.removeHelpListener(this.helpListener);

		}
	}
	
	public static class TreeViewerGroupBindDoubleClickEditMultilineFeatureOption implements TreeViewerGroupSelectionViewerOption {
		
		/**
		 * The {@link TreeViewer} to which the help listener will be added.
		 */
		protected TreeViewer viewer;

		/**
		 * The {@link AdapterFactory} 
		 */
		protected AdapterFactory factory;
		
		protected EditMultilineFeatureDoubleClickListener doubleClickListener = null;
		
		protected TreeViewerGroup group = null;
		
		@Override
		public void init(TreeViewer viewer, AdapterFactory factory, TreeViewerGroup group) {
			this.viewer = viewer;
			this.factory = factory;
			this.doubleClickListener = new EditMultilineFeatureDoubleClickListener(this.viewer, this.factory);
		}
		
		@Override
		public void dispose() {
			this.doubleClickListener.dispose();
		}
	}
}
