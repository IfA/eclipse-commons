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
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.wb.swt.ResourceManager;
import org.eclipse.wb.swt.SWTResourceManager;

import de.tud.et.ifa.agtele.ui.emf.editor.ActionUtil;
import de.tud.et.ifa.agtele.ui.listeners.SelectionListener2;

public abstract class ModelElementPalette {

	private Composite parent;

	private SashForm palettesSash;

	private ScrolledComposite paletteContainerScroll;

	private Map<Button, Composite> expandButtonsCompositeMap;

	private ArrayList<Composite> actionContainer;

	private ArrayList<HoverLabel> currentHoverLabels;

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

		this.createSubPalette(this.palettesSash, "Create Child");
		this.createSubPalette(this.palettesSash, "Create Siblings");

		this.palettesSash.setWeights(new int[] {2, 1});
		this.paletteContainerScroll.setContent(this.palettesSash);
		this.paletteContainerScroll.setMinSize(this.palettesSash.computeSize(SWT.DEFAULT, SWT.DEFAULT));

		this.createButtons();
	}

	protected void createSubPalette (Composite parent, String categoryString) {
		Composite styleComposite = new Composite(parent, SWT.BORDER);
		styleComposite.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		GridLayout gl_styleComposite = new GridLayout(1, false);
		gl_styleComposite.verticalSpacing = 2;
		gl_styleComposite.marginWidth = 0;
		gl_styleComposite.marginHeight = 0;
		styleComposite.setLayout(gl_styleComposite);

		this.createExpandButton(styleComposite, categoryString);

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

	public void update () {
		this.createButtons();
	}

	public void createButtons () {

		// Clear the palette
		//
		this.removeButtons();

		ISelection selection = this.getTreeViewer().getSelection();
		if (!(selection instanceof IStructuredSelection) || ((IStructuredSelection) selection).size() > 1) {
			// nothing to be done
			return;
		}

		List<CreateChildAction> createChildActions;
		List<CreateSiblingAction> createSiblingActions = new ArrayList<>();

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

	public class HoverLabel extends CLabel {
		protected boolean hovering = false;
		protected boolean enabled = false;
		protected Listener mouseHoverListener;
		protected IAction action;

		protected Display display = this.getDisplay();

		@SuppressWarnings("restriction")
		public HoverLabel(Composite parent, IAction action) {
			super(parent, SWT.SHADOW_NONE | SWT.RIGHT); //SWT.BORDER | SWT.SHADOW_IN |
			this.action = action;
			//lblNewLabel_1.setForeground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_FOREGROUND));
			this.setAlignment(SWT.LEFT);
			this.setLeftMargin(8);
			GridData gd_lblNewLabel_1 = new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1);
			this.setLayoutData(gd_lblNewLabel_1);
			this.setImage(ResourceManager.getImage(action.getImageDescriptor()));
			//lblNewLabel_1.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_LIGHT_SHADOW));
			this.setText(action.getText());
			this.setEnabled(action.isEnabled());
			this.setToolTipText(action.getToolTipText());

			// ColorRegistry registry = JFaceResources.getColorRegistry();

			this.mouseHoverListener = e -> {
				HoverLabel.this.hovering = e.type == SWT.MouseEnter || e.type == SWT.MouseMove;
				HoverLabel.this.redraw();
			};

			this.addListener(SWT.MouseEnter, this.mouseHoverListener);
			this.addListener(SWT.MouseMove, this.mouseHoverListener);
			this.addListener(SWT.MouseExit, this.mouseHoverListener);

			this.addListener(SWT.Paint, e -> {
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
					HoverLabel.this.setBackground(
							this.hovering ? this.display.getSystemColor(SWT.COLOR_TITLE_BACKGROUND_GRADIENT)
									: this.display.getSystemColor(SWT.COLOR_WIDGET_BACKGROUND));
				} else {
					HoverLabel.this.setBackground(
							this.hovering ? this.display.getSystemColor(SWT.COLOR_TITLE_INACTIVE_BACKGROUND)
									: this.display.getSystemColor(SWT.COLOR_WIDGET_BACKGROUND));
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

		@Override
		public void setEnabled(boolean enabled) {
			this.enabled = enabled;

			this.setForeground(enabled ? this.display.getSystemColor(SWT.COLOR_WIDGET_FOREGROUND) : this.display.getSystemColor(SWT.COLOR_WIDGET_NORMAL_SHADOW));

			if(enabled) {
				this.addMouseListener(new MouseListener() {
					@Override
					public void mouseUp(MouseEvent e) {
						if (e.x >= 0 && e.y >= 0 && e.x <= HoverLabel.this.getSize().x && e.y <= HoverLabel.this.getSize().y) {
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
		this.paletteContainerScroll.dispose();
	}
}
