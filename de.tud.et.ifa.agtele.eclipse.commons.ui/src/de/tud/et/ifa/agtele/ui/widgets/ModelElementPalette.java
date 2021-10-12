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
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.ui.action.CreateChildAction;
import org.eclipse.emf.edit.ui.action.CreateSiblingAction;
import org.eclipse.jface.action.IAction;
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
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.wb.swt.ResourceManager;
import org.eclipse.wb.swt.SWTResourceManager;

import de.tud.et.ifa.agtele.ui.emf.edit.action.filter.CreateActionChangeListener;
import de.tud.et.ifa.agtele.ui.emf.edit.action.filter.FilterChangedNotification;
import de.tud.et.ifa.agtele.ui.emf.editor.ActionUtil;
import de.tud.et.ifa.agtele.ui.listeners.SelectionListener2;
import de.tud.et.ifa.agtele.ui.widgets.TreeViewerGroup.TreeViewerGroupAddToolPaletteOption.AddToolPaletteFilterOption;

public abstract class ModelElementPalette implements CreateActionChangeListener {

	private Composite parent;

	private SashForm palettesSash;

	private ScrolledComposite paletteContainerScroll;

	private Map<Button, Composite> expandButtonsCompositeMap;

	private ArrayList<Composite> actionContainer;

	private ArrayList<HoverLabel> currentHoverLabels;

	private AddToolPaletteFilterOption filterOption;

	public ModelElementPalette() {
		this.actionContainer = new ArrayList<>();
		this.expandButtonsCompositeMap = new LinkedHashMap<>();
		this.currentHoverLabels = new ArrayList<>();
	}

	public void createPalette(Composite parent) {
		this.parent = parent;

		this.paletteContainerScroll = new ScrolledComposite(parent, SWT.H_SCROLL);
		this.paletteContainerScroll.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		this.paletteContainerScroll.setExpandHorizontal(true);
		this.paletteContainerScroll.setExpandVertical(true);

		this.palettesSash = new SashForm(this.paletteContainerScroll, SWT.SMOOTH | SWT.VERTICAL);

		this.createSubPalette(this.palettesSash, "Create Child", 0);
		this.createSubPalette(this.palettesSash, "Create Siblings", 1);

		this.palettesSash.setWeights(new int[] {2, 1});
		this.paletteContainerScroll.setContent(this.palettesSash);
		this.paletteContainerScroll.setMinSize(this.palettesSash.computeSize(SWT.DEFAULT, SWT.DEFAULT));

		this.update();
	}

	protected void createSubPalette (Composite parent, String categoryString, int index) {
		Composite styleComposite = new Composite(parent, SWT.BORDER);
		styleComposite.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		GridLayout gl_styleComposite = new GridLayout(1, false);
		gl_styleComposite.verticalSpacing = 2;
		gl_styleComposite.marginWidth = 0;
		gl_styleComposite.marginHeight = 0;
		styleComposite.setLayout(gl_styleComposite);

		this.createExpandButton(styleComposite, categoryString);
		
		if (this.filterOption != null) {
			this.filterOption.createFilterBar(styleComposite, index);
		}

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
		scroll.setMinSize(containerComposite.computeSize(this.paletteContainerScroll.getSize().x, SWT.DEFAULT));

		this.actionContainer.add(containerComposite);
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

		expandButton.addSelectionListener((SelectionListener2) e -> ModelElementPalette.this.expandComposite(ModelElementPalette.this.expandButtonsCompositeMap.get(expandButton)));

		this.expandButtonsCompositeMap.put(expandButton, parent);
	}

	public void expandComposite(Composite comp) {
		Collection<Composite> cCollection = this.expandButtonsCompositeMap.values();
		Composite[] cArray = cCollection.toArray(new Composite[cCollection.size()]);
		int expandedWeight, minimizedWeight;

		minimizedWeight = (int)Math.round(18.0 / this.palettesSash.getSize().y * 1000);
		expandedWeight = (int)Math.round((this.palettesSash.getSize().y - 18.0 * cArray.length) / this.palettesSash.getSize().y * 1000);

		int[] weights = new int[cArray.length];

		for (int i=0; i<cArray.length; i++) {
			if (cArray[i] == comp) {
				weights[i] = expandedWeight;
			} else {
				weights[i] = minimizedWeight;
			}
		}

		this.palettesSash.setWeights(weights);
	}

	public void removeButtons () {
		this.currentHoverLabels.forEach(l -> l.dispose());
		
		Iterator<Composite> c = this.actionContainer.iterator();

		while (c.hasNext()) {
			Composite actionsComposite = c.next();
			Control[] children = actionsComposite.getChildren();
			for (Control element : children) {
				//				if (children[i] instanceof CLabel) {
				element.dispose();
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
		return this.getTreeViewerGroup().getTreeViewer();
	}
	
	protected EObject getSelectedElement () {
		if (this.getSelection() instanceof IStructuredSelection) {			
			Object object = ((IStructuredSelection)this.getSelection()).getFirstElement();
			if (object == null) {
				object = this.getTreeViewer().getInput();
			}
			if (!(object instanceof EObject)) {
				return null;
			}
			return (EObject) object;
		}
		return null;
	}
	

	public void update () {
		//The update has to be postponed in order to let the editor and action bar contributor update before
		Display.getCurrent().asyncExec(() -> {
			if (this.filterOption != null) {
				this.filterOption.updateSelection(this.getSelectedElement());
			}		
			
			this.createButtons();
		});
		
	}

	public void createButtons () {

		// Clear the palette
		//
		this.removeButtons();

		List<? extends IAction> createChildActions;
		List<? extends IAction> createSiblingActions = new ArrayList<>();
		
		ISelection selection = this.getTreeViewer().getSelection();
		if (!(selection instanceof IStructuredSelection) || ((IStructuredSelection) selection).size() > 1) {
			// nothing to be done
			return;
		}


		if (((IStructuredSelection) selection).isEmpty()) {
			// if nothing is selected,we manually select the viewer input; this will allow to add the
			// top level elements in this viewer
			//
			try {
				selection = new StructuredSelection(this.getTreeViewer().getInput());
			} catch (Exception e) {
				return;
			}
			createChildActions = ActionUtil.getCreateChildActions(this.getTreeViewer(),
					(StructuredSelection) selection);
		} else {
			try {
				createChildActions = ActionUtil.getCreateChildActions(this.getTreeViewer());
				createSiblingActions = ActionUtil.getCreateSiblingActions(this.getTreeViewer());
			} catch (Exception e) {
				e.printStackTrace();
				return;
			}
		}
		
		if (this.filterOption != null) {
			createChildActions = this.filterOption.filterChildren(createChildActions);
			createSiblingActions = this.filterOption.filterSiblings(createSiblingActions);
		}
		// Populate the palette
		//
		this.currentHoverLabels = new ArrayList<>();

		this.currentHoverLabels
		.addAll(this.addActions(new ArrayList<IAction>(createChildActions), this.actionContainer.get(0)));
		this.currentHoverLabels
		.addAll(this.addActions(new ArrayList<IAction>(createSiblingActions), this.actionContainer.get(1)));

		for (Button button : this.expandButtonsCompositeMap.keySet()) {
			Composite container = this.getActionContainerScrollByStyleComposite(this.expandButtonsCompositeMap.get(button));
			String buttonText = button.getText();

			buttonText = buttonText.replaceAll("\\([0-9]*\\)", "(" + container.getChildren().length + ")");
			button.setText(buttonText);
		}

		this.doResize();
	}

	protected Composite getActionContainerScrollByStyleComposite (Composite styleContainer) {
		if (this.filterOption != null) {
			return (Composite)((Composite)styleContainer.getChildren()[2] /*ScrolledComposite*/).getChildren()[0];
		}
		return (Composite)((Composite)styleContainer.getChildren()[1] /*ScrolledComposite*/).getChildren()[0];
	}

	protected int getMaxLabelWidth () {
		int result = 0;

		Iterator<HoverLabel> it = this.currentHoverLabels.iterator();
		while (it.hasNext()) {
			HoverLabel label = it.next();
			if (label.computeSize(SWT.DEFAULT, SWT.DEFAULT).x > result) {
				result = label.computeSize(SWT.DEFAULT, SWT.DEFAULT).x;
			}
		}
		return result;
	}

	protected void doResize () {
		Collection<Composite> styleContainers = this.expandButtonsCompositeMap.values();
		int[] weights = new int[styleContainers.size()];
		int i = 0;
		Iterator<Composite> it = styleContainers.iterator();
		int maxLabelWidth = this.getMaxLabelWidth(),
				containerWidth = this.paletteContainerScroll.getSize().x,
				targetWidth = containerWidth > maxLabelWidth ? containerWidth : maxLabelWidth;

				while(it.hasNext()) {
					Composite styleContainer = it.next();
					int size = styleContainer.getSize().y;
					weights[i++] = size > 0 ? size : styleContainer.computeSize(SWT.DEFAULT, SWT.DEFAULT).y;

					Composite actionContainer = this.getActionContainerScrollByStyleComposite(styleContainer);

					actionContainer.layout();

					Point aSize = actionContainer.computeSize(targetWidth, SWT.DEFAULT);
					actionContainer.setSize(aSize);
					actionContainer.redraw();
					((ScrolledComposite)actionContainer.getParent()).setMinHeight(aSize.y);
					actionContainer.getParent().layout(true, true);
					actionContainer.getParent().redraw();

				}
				//		createChildrenScroll.layout();
				//		createSiblingsScroll.layout();
				this.palettesSash.layout();
				this.paletteContainerScroll.setMinWidth(this.palettesSash.computeSize(SWT.DEFAULT, SWT.DEFAULT).x);

				this.palettesSash.setWeights(weights);

				this.parent.layout();
	}

	protected Collection<HoverLabel> addActions(Collection<IAction> actions, Composite target) {
		Iterator<IAction> it = actions.iterator();
		ArrayList<HoverLabel> labels = new ArrayList<>();
		while (it.hasNext()) {
			IAction ac = it.next();
			labels.add(this.createActionButton(target, ac));
		}
		return labels;
	}

	public class HoverLabel {
		protected boolean hovering = false;
		protected boolean enabled = false;
		protected Listener mouseHoverListener;
		protected MouseListener mouseActionListener;
		protected IAction action;
		protected Image greyImage = null;

		
		protected CLabel label = null;
		protected Display display = null;
		protected Composite parent;

		@SuppressWarnings("restriction")
		public HoverLabel(Composite parent, IAction action) {
			this.parent = parent;
			this.label = new CLabel(parent,  SWT.SHADOW_NONE | SWT.RIGHT);
			this.display = this.label.getDisplay();
//			super(parent,); //SWT.BORDER | SWT.SHADOW_IN |
			this.action = action;
			//lblNewLabel_1.setForeground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_FOREGROUND));
			this.label.setAlignment(SWT.LEFT);
			this.label.setLeftMargin(8);
			GridData gd_lblNewLabel_1 = new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1);
			this.label.setLayoutData(gd_lblNewLabel_1);
			this.label.setImage(ResourceManager.getImage(action.getImageDescriptor()));
			//lblNewLabel_1.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_LIGHT_SHADOW));
			this.label.setText(action.getText());
			this.setEnabled(action.isEnabled());
			this.label.setToolTipText(action.getToolTipText());

			// ColorRegistry registry = JFaceResources.getColorRegistry();

			this.mouseHoverListener = e -> {
				HoverLabel.this.hovering = e.type == SWT.MouseEnter || e.type == SWT.MouseMove;
				HoverLabel.this.label.redraw();
			};

			this.label.addListener(SWT.MouseEnter, this.mouseHoverListener);
			this.label.addListener(SWT.MouseMove, this.mouseHoverListener);
			this.label.addListener(SWT.MouseExit, this.mouseHoverListener);

			this.label.addListener(SWT.Paint, e -> {
				// if (HoverLabel.this.enabled) {
				// HoverLabel.this.setBackground(HoverLabel.this.hovering ?
				// registry.get("org.eclipse.ui.workbench.ACTIVE_TAB_BG_START")
				// :
				// registry.get("CONTENT_ASSIST_BACKGROUND_COLOR"));
				// HoverLabel.this.setForeground(HoverLabel.this.hovering ?
				// registry.get("org.eclipse.ui.workbench.ACTIVE_TAB_SELECTED_TEXT_COLOR")
				// :
				// registry.get("CONTENT_ASSIST_FOREGROUND_COLOR"));
				// } else {
				// HoverLabel.this.setBackground(HoverLabel.this.hovering ?
				// registry.get("org.eclipse.ui.editors.currentLineColor") :
				// registry.get("org.eclipse.ui.workbench.INACTIVE_TAB_BG_END"));
				// HoverLabel.this.setForeground(HoverLabel.this.hovering ?
				// registry.get("org.eclipse.ui.workbench.ACTIVE_TAB_UNSELECTED_TEXT_COLOR")
				// :
				// registry.get("org.eclipse.ui.workbench.INACTIVE_TAB_TEXT_COLOR"));
				// }
				if (HoverLabel.this.enabled) {
					if (this.hovering) {
						HoverLabel.this.label.setBackground(
								this.display.getSystemColor(SWT.COLOR_WIDGET_NORMAL_SHADOW)
								);
						HoverLabel.this.label.setForeground(new Color(Display.getCurrent(), 0, 0, 0));
					} else {
						HoverLabel.this.label.setBackground(
								this.parent.getBackground()
								);
						HoverLabel.this.label.setForeground(this.parent.getForeground());
					}
				} else {
					if (this.hovering) {
						HoverLabel.this.label.setBackground(
								this.display.getSystemColor(SWT.COLOR_WIDGET_DISABLED_FOREGROUND)
								);
					} else {
						HoverLabel.this.label.setBackground(
								this.parent.getBackground()
								);
					}
				}
			});
			//		    HoverLabel.this.addListener(SWT., new Listener() {
			//				@Override
			//				public void handleEvent(Event e) {
			//					System.out.println("selected" + e.widget);
			//
			//				}
			//			});
		}

		public Point computeSize(int wHint, int hHint) {
			return this.label.computeSize(wHint, hHint);
		}

		public void setEnabled(boolean enabled) {
			this.enabled = enabled;

			if(enabled) {
				this.mouseActionListener = new MouseListener() {
					@Override
					public void mouseUp(MouseEvent e) {
						if (e.x >= 0 && e.y >= 0 && e.x <= HoverLabel.this.label.getSize().x && e.y <= HoverLabel.this.label.getSize().y) {
							Command lastCommand = ModelElementPalette.this.getEditingDomain().getCommandStack().getMostRecentCommand();
							
							HoverLabel.this.action.run();
							Command newCommand = ModelElementPalette.this.getEditingDomain().getCommandStack().getMostRecentCommand();
						}
					}
					@Override
					public void mouseDown(MouseEvent e) {
						ModelElementPalette.this.getTreeViewer().getTree().setFocus();
					}

					@Override
					public void mouseDoubleClick(MouseEvent e) {
					}
				};
				this.label.addMouseListener(this.mouseActionListener);
			} else {
			}
//			this.label.setEnabled(enabled);
			if (enabled) {
				this.label.setForeground(this.parent.getForeground());
//				this.label.setForeground(new Color(Display.getCurrent(), 255, 0, 0));
			} else {
				this.label.setForeground(this.display.getSystemColor(SWT.COLOR_WIDGET_DARK_SHADOW));
				Image i = ResourceManager.getImage(action.getImageDescriptor());
				Image grey = new Image(Display.getDefault(), i, SWT.IMAGE_GRAY);
				this.label.setImage(grey);
//				this.label.setForeground(new Color(Display.getCurrent(), 0, 255, 0));
			}

		}
		public void dispose () {
			if (!this.label.isDisposed()) {
				this.label.removeListener(SWT.MouseEnter, this.mouseHoverListener);
				this.label.removeListener(SWT.MouseMove, this.mouseHoverListener);
				this.label.removeListener(SWT.MouseExit, this.mouseHoverListener);
				if (this.mouseActionListener != null) {
					this.label.removeMouseListener(this.mouseActionListener);
				}
				this.label.dispose();
			}
			if (this.greyImage != null) {
				this.greyImage.dispose();
			}
		}
	}

	public HoverLabel createActionButton (Composite parent, IAction action) {
		HoverLabel label = new HoverLabel(parent, action);
		return label;
	}

	public void dispose() {
		this.paletteContainerScroll.dispose();
	}
	

	public void setFilterOption(AddToolPaletteFilterOption filterOption) {
		if (this.filterOption != null) {
			return;
		}
		this.filterOption = filterOption;
		this.filterOption.addFilterChangeListener(this);
	}
	
	@Override
	public void notifiy(FilterChangedNotification notification) {
		this.createButtons();
	}
}
