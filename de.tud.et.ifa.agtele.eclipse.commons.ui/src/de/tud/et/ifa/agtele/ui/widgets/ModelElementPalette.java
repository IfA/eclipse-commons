package de.tud.et.ifa.agtele.ui.widgets;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.eclipse.e4.ui.css.swt.internal.theme.Theme;
import org.eclipse.e4.ui.css.swt.theme.IThemeEngine;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.ui.action.CreateChildAction;
import org.eclipse.emf.edit.ui.action.CreateSiblingAction;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.resource.ColorRegistry;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.Workbench;
import org.eclipse.ui.themes.IThemeManager;
import org.eclipse.wb.swt.ResourceManager;
import org.eclipse.wb.swt.SWTResourceManager;

import de.tud.et.ifa.agtele.ui.listeners.SelectionListener2;

public abstract class ModelElementPalette {

	private Composite parent;
	
	private SashForm palettesSash;

	private ScrolledComposite paletteContainerScroll;
	
	private Map<Button, Composite> expandButtonsCompositeMap;
	
	private ArrayList<Composite> actionContainer;
	
	private ArrayList<HoverLabel> currentHoverLabels;
			
	public ModelElementPalette() {
		actionContainer = new ArrayList<Composite>();
		expandButtonsCompositeMap = new LinkedHashMap<Button, Composite>();
		currentHoverLabels = new ArrayList<HoverLabel>();
	}
	
	public void createPalette(Composite parent) {
		this.parent = parent;
		
		paletteContainerScroll = new ScrolledComposite(parent, SWT.H_SCROLL);
		paletteContainerScroll.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		paletteContainerScroll.setExpandHorizontal(true);
		paletteContainerScroll.setExpandVertical(true);
		
		palettesSash = new SashForm(paletteContainerScroll, SWT.SMOOTH | SWT.VERTICAL);
		
		createSubPalette(palettesSash, "Create Child");
		createSubPalette(palettesSash, "Create Siblings");			
		
		palettesSash.setWeights(new int[] {2, 1});
		paletteContainerScroll.setContent(palettesSash);
		paletteContainerScroll.setMinSize(palettesSash.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		
		createButtons();
	}
	
	protected void createSubPalette (Composite parent, String categoryString) {
		Composite styleComposite = new Composite(parent, SWT.BORDER);
		styleComposite.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		GridLayout gl_styleComposite = new GridLayout(1, false);
		gl_styleComposite.verticalSpacing = 2;
		gl_styleComposite.marginWidth = 0;
		gl_styleComposite.marginHeight = 0;
		styleComposite.setLayout(gl_styleComposite);	
		
		createExpandButton(styleComposite, categoryString);
		
		ScrolledComposite scroll = new ScrolledComposite(styleComposite, SWT.V_SCROLL);	
		scroll.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));					
		scroll.setExpandHorizontal(true);
		scroll.setExpandVertical(true);
		scroll.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		
		Composite containerComposite = new Composite(scroll, SWT.NONE);
		containerComposite.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		GridLayout gl_containerComposite = new GridLayout(1, false);
		gl_containerComposite.verticalSpacing = 2;
		gl_containerComposite.marginWidth = 0;
		gl_containerComposite.marginHeight = 0;
		containerComposite.setLayout(gl_containerComposite);			
		scroll.setContent(containerComposite);
		containerComposite.layout();
		scroll.setMinSize(containerComposite.computeSize(paletteContainerScroll.getSize().x, SWT.DEFAULT));
		
		actionContainer.add(containerComposite);
	}
	
	protected void createExpandButton (Composite parent, String text) {
		Button expandButton = new Button(parent, SWT.NONE);
		expandButton.setFont(SWTResourceManager.getFont("Segoe UI", 7, SWT.NORMAL));
		expandButton.setAlignment(SWT.LEFT);
		GridData gd_expandButton = new GridData(SWT.FILL, SWT.TOP, true, false, 1, 1);
		gd_expandButton.heightHint = 18;
		expandButton.setLayoutData(gd_expandButton);
		expandButton.setText(text + " (0)");
		
		expandButton.setBackground(JFaceResources.getColorRegistry().get("CONTENT_ASSIST_BACKGROUND_COLOR"));
		expandButton.setForeground(JFaceResources.getColorRegistry().get("CONTENT_ASSIST_FOREGROUND_COLOR"));
		
		expandButton.addSelectionListener(new SelectionListener2() {				
			@Override
			public void widgetSelected(SelectionEvent e) {
				expandComposite(expandButtonsCompositeMap.get(expandButton));
			}
		});
		
		expandButtonsCompositeMap.put(expandButton, parent);
	}
		
	public void expandComposite(Composite comp) {
		Collection<Composite> cCollection = expandButtonsCompositeMap.values();
		Composite[] cArray = cCollection.toArray(new Composite[cCollection.size()]);
		int expandedWeight, minimizedWeight;
		
		minimizedWeight = (int)Math.round(18.0 / palettesSash.getSize().y * 1000);
		expandedWeight = (int)Math.round((((float)palettesSash.getSize().y) - (18.0 * cArray.length)) / palettesSash.getSize().y * 1000);
		
		int[] weights = new int[cArray.length];
		
		for (int i=0; i<cArray.length; i++) {
			if (cArray[i] == comp) {
				weights[i] = expandedWeight;
			} else {
				weights[i] = minimizedWeight;
			}
		}
		
		palettesSash.setWeights(weights);
	}
	
	public void removeButtons () {
		Iterator<Composite> c = actionContainer.iterator();
		
		while (c.hasNext()) {
			Composite actionsComposite = c.next();
			Control[] children = actionsComposite.getChildren();
			for (int i = 0; i < children.length; i++) {
//				if (children[i] instanceof CLabel) {
					children[i].dispose();
//				}
			}
			actionsComposite.layout();
			actionsComposite.getParent().layout();
		}
	}
	
	abstract protected ISelection getSelection();
	
	abstract protected EditingDomain getEditingDomain ();
	
	abstract protected TreeViewerGroup getTreeViewerGroup();
	
	protected TreeViewer getTreeViewer() {
		return getTreeViewerGroup().getTreeViewer();
	}
	
	public void update () {
		createButtons();
	}
	
	public void createButtons () {
		removeButtons();
					
		Collection<?> newChildDescriptors = null;
		Collection<?> newSiblingDescriptors = null;
		ISelection selection = getSelection();
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
				selection = new StructuredSelection(getTreeViewer().getInput());					
			} catch(Exception e) {
				return;
			}
		}
		
		Object object = ((IStructuredSelection)selection).getFirstElement();

		newChildDescriptors = getEditingDomain().getNewChildDescriptors(object, null);
		if(doNotCreateSiblingActions) {
			newSiblingDescriptors = new ArrayList<Object>();
		} else {
			newSiblingDescriptors = getEditingDomain().getNewChildDescriptors(null, object);				
		} 

		IEditorPart editorPart = 
				PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor();

		// Generate actions for selection; populate and redraw the menus.
		//
		ArrayList<IAction> createChildActions = new ArrayList<>();
		for (Object descriptor : newChildDescriptors) {
			if(getTreeViewerGroup().isValidDescriptor(descriptor, getTreeViewer().getContentProvider())) {
				createChildActions.add(createCreateChildAction(editorPart, selection, descriptor));
			}
		}
		ArrayList<IAction> createSiblingActions = new ArrayList<>();
		for (Object descriptor : newSiblingDescriptors) {
			if(getTreeViewerGroup().isValidDescriptor(descriptor, getTreeViewer().getContentProvider())) {
				createSiblingActions.add(createCreateSiblingAction(editorPart, selection, descriptor));					
			}
		}

		// Populate the context menu
		currentHoverLabels = new ArrayList<HoverLabel>();
		
		currentHoverLabels.addAll(addActions(createChildActions, actionContainer.get(0)));
		currentHoverLabels.addAll(addActions(createSiblingActions, actionContainer.get(1)));
					
		for (Button button : expandButtonsCompositeMap.keySet()) {
			Composite container = getActionContainerScrollByStyleComposite(expandButtonsCompositeMap.get(button));
			String buttonText = button.getText();
			
			buttonText = buttonText.replaceAll("\\([0-9]*\\)", "(" + container.getChildren().length + ")");
			button.setText(buttonText);
		}
		
		doResize();		
	}
	
	protected Composite getActionContainerScrollByStyleComposite (Composite styleContainer) {
		return (Composite)((Composite)styleContainer.getChildren()[1] /*ScrolledComposite*/).getChildren()[0];
	}
	
	protected int getMaxLabelWidth () {
		int result = 0;
		
		Iterator<HoverLabel> it = currentHoverLabels.iterator();
		while (it.hasNext()) {
			HoverLabel label = it.next();
			if (label.computeSize(SWT.DEFAULT, SWT.DEFAULT).x > result) {
				result = label.computeSize(SWT.DEFAULT, SWT.DEFAULT).x;
			}
		}	
		return result;
	}
	
	protected void doResize () {
		Collection<Composite> styleContainers = expandButtonsCompositeMap.values(); 			
		int[] weights = new int[styleContainers.size()];
		int i = 0;
		Iterator<Composite> it = styleContainers.iterator();
		int maxLabelWidth = getMaxLabelWidth(),
			containerWidth = paletteContainerScroll.getSize().x,
			targetWidth = containerWidth > maxLabelWidth ? containerWidth : maxLabelWidth;
		
		while(it.hasNext()) {
			Composite styleContainer = it.next();
			int size = styleContainer.getSize().y;
			weights[i++] = size > 0 ? size : styleContainer.computeSize(SWT.DEFAULT, SWT.DEFAULT).y;
			
			Composite actionContainer = getActionContainerScrollByStyleComposite(styleContainer);
			
			actionContainer.layout();
			
			Point aSize = actionContainer.computeSize(targetWidth, SWT.DEFAULT);
			actionContainer.setSize(aSize);
			actionContainer.redraw();
			((ScrolledComposite)actionContainer.getParent()).setMinHeight(aSize.y);
			actionContainer.getParent().layout(true, true);
			
		}
//		createChildrenScroll.layout();
//		createSiblingsScroll.layout();
		palettesSash.layout();
		paletteContainerScroll.setMinWidth(palettesSash.computeSize(SWT.DEFAULT, SWT.DEFAULT).x);
		
		palettesSash.setWeights(weights);
		
		parent.layout();	
	}

	protected IAction createCreateChildAction(IEditorPart editorPart, ISelection selection, Object descriptor) {
		return new CreateChildAction(editorPart, selection, descriptor);
	}
	protected IAction createCreateSiblingAction(IEditorPart editorPart, ISelection selection, Object descriptor) {
		return new CreateSiblingAction(editorPart, selection, descriptor);
	}
	
	protected Collection<HoverLabel> addActions(Collection<IAction> actions, Composite target) {
		Iterator<IAction> it = actions.iterator();
		ArrayList<HoverLabel> labels = new ArrayList<HoverLabel>();
		while (it.hasNext()) {
			IAction ac = it.next();
			labels.add(createActionButton(target, ac));
		}
		return labels;
	}
	
	public class HoverLabel extends CLabel {
		protected boolean hovering = false;
		protected boolean enabled = false;
		protected Listener mouseHoverListener;
		protected IAction action;
		
    	protected Display display = getDisplay();
    	
		@SuppressWarnings("restriction")
		public HoverLabel(Composite parent, IAction action) {
			super(parent, SWT.SHADOW_NONE | SWT.RIGHT); //SWT.BORDER | SWT.SHADOW_IN | 
			this.action = action;
			//lblNewLabel_1.setForeground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_FOREGROUND));
			setAlignment(SWT.LEFT);
			setLeftMargin(8);
			GridData gd_lblNewLabel_1 = new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1);
			setLayoutData(gd_lblNewLabel_1);
			setImage(ResourceManager.getImage(action.getImageDescriptor()));
			//lblNewLabel_1.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_LIGHT_SHADOW));
			setText(action.getText());
			setEnabled(action.isEnabled());
			setToolTipText(action.getToolTipText());	
			
			ColorRegistry registry = JFaceResources.getColorRegistry();
								
			mouseHoverListener = new Listener()
		    {
		        @Override
		        public void handleEvent(Event e)
		        {
		        	HoverLabel.this.hovering = e.type == SWT.MouseEnter || e.type == SWT.MouseMove;
		        	HoverLabel.this.redraw();
		        }
		    };

		    addListener(SWT.MouseEnter, mouseHoverListener);
		    addListener(SWT.MouseMove, mouseHoverListener);
		    addListener(SWT.MouseExit, mouseHoverListener);
		    
		    addListener(SWT.Paint, new Listener()
		    {
		    	 @Override
		        public void handleEvent(Event e)
		        {
		        	if (HoverLabel.this.enabled) {
		        		HoverLabel.this.setBackground(hovering ? registry.get("org.eclipse.ui.workbench.ACTIVE_TAB_BG_START") :
	        					registry.get("CONTENT_ASSIST_BACKGROUND_COLOR"));
		        		HoverLabel.this.setForeground(hovering ? 
		        				registry.get("org.eclipse.ui.workbench.ACTIVE_TAB_SELECTED_TEXT_COLOR") : 
		        					registry.get("CONTENT_ASSIST_FOREGROUND_COLOR"));
		        	} else {
		        		HoverLabel.this.setBackground(hovering ? 
	        				registry.get("org.eclipse.ui.editors.currentLineColor") : 
	        					registry.get("org.eclipse.ui.workbench.INACTIVE_TAB_BG_END"));
		        		HoverLabel.this.setForeground(hovering ? 
		        				registry.get("org.eclipse.ui.workbench.ACTIVE_TAB_UNSELECTED_TEXT_COLOR") : 
		        					registry.get("org.eclipse.ui.workbench.INACTIVE_TAB_TEXT_COLOR"));
		        	}
		        }
//		        @Override
//		        public void handleEvent(Event e)
//		        {
//		        	if (HoverLabel.this.enabled) {
//		        		HoverLabel.this.setBackground(hovering ? 
//	        				display.getSystemColor(SWT.COLOR_TITLE_BACKGROUND_GRADIENT) :
//	        					display.getSystemColor(SWT.COLOR_WIDGET_BACKGROUND));
//		        	} else {
//		        		HoverLabel.this.setBackground(hovering ? 
//	        				display.getSystemColor(SWT.COLOR_TITLE_INACTIVE_BACKGROUND) : 
//	        					display.getSystemColor(SWT.COLOR_WIDGET_BACKGROUND));
//		        	}
//		        }
		    });	
//		    HoverLabel.this.addListener(SWT., new Listener() {
//				@Override
//				public void handleEvent(Event e) {
//					System.out.println("selected" + e.widget);
//					
//				}
//			});
		}
		
		@Override
		public void setEnabled(boolean enabled) {
			this.enabled = enabled;
			
			setForeground(enabled ? display.getSystemColor(SWT.COLOR_WIDGET_FOREGROUND) : display.getSystemColor(SWT.COLOR_WIDGET_NORMAL_SHADOW));
			
			if(enabled) {
				addMouseListener(new MouseListener() {						
					@Override
					public void mouseUp(MouseEvent e) {
						if (e.x >= 0 && e.y >= 0 && e.x <= HoverLabel.this.getSize().x && e.y <= HoverLabel.this.getSize().y) {
							HoverLabel.this.action.run();								
						}							
					}						
					@Override
					public void mouseDown(MouseEvent e) {
					}
					
					@Override
					public void mouseDoubleClick(MouseEvent e) {
					}
				});
			} else {
			}
			super.setEnabled(enabled);
		}			
	}
	
	public HoverLabel createActionButton (Composite parent, IAction action) {
		HoverLabel label = new HoverLabel(parent, action);
		return label;
	}
	
	public void dispose() {
		paletteContainerScroll.dispose();
	}
}
