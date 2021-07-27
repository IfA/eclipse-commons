package de.tud.et.ifa.agtele.eclipse.commons.emf.storage.ui.views;

import org.eclipse.emf.common.ui.ViewerPane;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ViewForm;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.dialogs.FilteredTree;
import org.eclipse.ui.dialogs.PatternFilter;

import de.tud.et.ifa.agtele.ui.widgets.SelectionRestoringFilteredTree;

public class FilteredTreeViewerPane extends ViewerPane {
		protected FilteredTree filteredTree;

		public FilteredTreeViewerPane(IWorkbenchPage page, IWorkbenchPart part) {
			super(page, part);
		}
		
		@Override
		public void createControl(Composite parent) {
			if (getControl() == null)
		    {
		      container = parent;

		      // Create view form.    
		      //control = new ViewForm(parent, getStyle());
		      control = new ViewForm(parent, SWT.NONE);
		      control.addDisposeListener
		        (new DisposeListener()
		         {
		           public void widgetDisposed(DisposeEvent event)
		           {
		             dispose();
		           }
		         });
		      control.marginWidth = 0;
		      control.marginHeight = 0;

		      // Create a title bar.
		      createTitleBar();

		      viewer = createViewer(control);
		      control.setContent(this.filteredTree);

		      control.setTabList(new Control [] { this.filteredTree });
		      
		      // When the pane or any child gains focus, notify the workbench.
		      control.addListener(SWT.Activate, this);
		      hookFocus(control);
		      hookFocus(viewer.getControl());
		    }
		}

		@Override
		public Viewer createViewer(Composite composite) {
			
			PatternFilter filter = new PatternFilter();
			this.filteredTree = new SelectionRestoringFilteredTree(composite, SWT.MULTI | SWT.H_SCROLL
					| SWT.V_SCROLL, filter, true, true);
			//return new TreeViewer(composite, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
			return this.filteredTree.getViewer();
		}

		@Override
		public void requestActivation() {
			//super.requestActivation();
			showFocus(true);
			((MultiPageView)this.part).setCurrentViewerPane(this);
		}

		@Override
		protected void createTitleBar() {
//						super.createTitleBar();
		}
	}