package de.tud.et.ifa.agtele.eclipse.commons.emf.storage.ui.views;

import static org.eclipse.swt.events.SelectionListener.widgetSelectedAdapter;

import java.util.ArrayList;
import java.util.Collection;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.ListenerList;
import org.eclipse.emf.common.ui.ViewerPane;
import org.eclipse.jface.dialogs.IPageChangeProvider;
import org.eclipse.jface.dialogs.IPageChangedListener;
import org.eclipse.jface.dialogs.PageChangedEvent;
import org.eclipse.jface.util.SafeRunnable;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IPartService;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.ViewPart;

public abstract class MultiPageView extends ViewPart implements IViewPart, IPageChangeProvider, ISelectionProvider {

	protected ArrayList<Control> pageSites = new ArrayList<>(3);
	protected CTabFolder tabFolder;
	
	protected ListenerList<IPageChangedListener> pageChangeListeners = new ListenerList<>(
			ListenerList.IDENTITY);
	protected ViewerPane currentViewerPane;
	protected Viewer currentViewer;
	protected ISelectionChangedListener selectionChangedListener;
	protected Collection<ISelectionChangedListener> selectionChangedListeners = new ArrayList<ISelectionChangedListener>();
	protected ISelection viewSelection;

	public MultiPageView() {
		super();
	}
	
	protected abstract void createPages(Composite parent);
	
	protected CTabFolder createSiteContainer (Composite parent) {	
		parent.setLayout(new FillLayout());
		
		CTabFolder tabFolder = new CTabFolder(parent, SWT.FLAT | SWT.NO_BACKGROUND | SWT.BORDER);
		tabFolder.setTabPosition(SWT.BOTTOM);
//		tabFolder.setSelectionBackground(Display.getCurrent().getSystemColor(SWT.COLOR_TITLE_INACTIVE_BACKGROUND_GRADIENT));
		
		tabFolder.addSelectionListener(widgetSelectedAdapter(e -> {
			int newPageIndex = tabFolder.indexOf((CTabItem) e.item);
			pageChange(newPageIndex);
		}));
		tabFolder.addTraverseListener(e -> {
			switch (e.detail) {
				case SWT.TRAVERSE_PAGE_NEXT:
				case SWT.TRAVERSE_PAGE_PREVIOUS:
					int detail = e.detail;
					e.doit = true;
					e.detail = SWT.TRAVERSE_NONE;
					Control control = tabFolder.getParent();
					do {
						if (control.traverse(detail))
							return;
						if (control.getListeners(SWT.Traverse).length != 0)
							return;
						if (control instanceof Shell)
							return;
						control = control.getParent();
					} while (control != null);
			}
		});
		
		return tabFolder;
	}
	
	public void createPartControl(Composite parent) {	
		this.tabFolder = this.createSiteContainer(parent);
		createPages(this.tabFolder);

		if (getActivePage() == -1) {
			setActivePage(0);
		}
				
		if (this.pageSites.size()<= 1) {
			this.hideTabs();
		}
	}
		
	public int getActivePage() {
		CTabFolder tabFolder = getTabFolder();
		if (tabFolder != null && !tabFolder.isDisposed()) {
			return tabFolder.getSelectionIndex();
		}
		return -1;
	}

	public void setCurrentViewerPane(ViewerPane viewerPane) {
		if (currentViewerPane != viewerPane) {
			if (currentViewerPane != null) {
				currentViewerPane.showFocus(false);
			}
			currentViewerPane = viewerPane;
		}
		setCurrentViewer(currentViewerPane.getViewer());
	}
	public void setCurrentViewer(Viewer viewer) {
		// If it is changing...
		//
		if (currentViewer != viewer) {
			if (selectionChangedListener == null) {
				// Create the listener on demand.
				//
				selectionChangedListener =
					new ISelectionChangedListener() {
						// This just notifies those things that are affected by the section.
						//
						public void selectionChanged(SelectionChangedEvent selectionChangedEvent) {
							setSelection(selectionChangedEvent.getSelection());
						}
					};
			}

			// Stop listening to the old one.
			//
			if (currentViewer != null) {
				currentViewer.removeSelectionChangedListener(selectionChangedListener);
			}

			// Start listening to the new one.
			//
			if (viewer != null) {
				viewer.addSelectionChangedListener(selectionChangedListener);
			}

			// Remember it.
			//
			currentViewer = viewer;

			// Set the editors selection based on the current viewer's selection.
			//
			setSelection(currentViewer == null ? StructuredSelection.EMPTY : currentViewer.getSelection());
		}
	}
	public int addPage(Control control) {
		int index = getPageCount();
		addPage(index, control);
		return index;
	}
	
	abstract ViewerPane getPane(Control control);
	
	protected void setPageImage(int pageIndex, Image image) {
		getItem(pageIndex).setImage(image);
	}
	protected void setPageText(int pageIndex, String text) {
		getItem(pageIndex).setText(text);
	}

	protected Composite getContainer() {
		return tabFolder;
	}
	
	protected Control getControl(int pageIndex) {
		return getItem(pageIndex).getControl();
	}
	private CTabItem getItem(int pageIndex) {
		return getTabFolder().getItem(pageIndex);
	}
	public void setSelection(ISelection selection) {
		viewSelection = selection;

		for (ISelectionChangedListener listener : selectionChangedListeners) {
			listener.selectionChanged(new SelectionChangedEvent(this, selection));
		}
	}
	/**
	 * Returns the number of pages in this multi-page editor.
	 *
	 * @return the number of pages
	 */
	protected int getPageCount() {
		CTabFolder folder = getTabFolder();
		// May not have been created yet, or may have been disposed.
		if (folder != null && !folder.isDisposed()) {
			return folder.getItemCount();
		}
		return 0;
	}

	/**
	 * Returns the image for the page with the given index, or <code>null</code>
	 * if no image has been set for the page. The page index must be valid.
	 *
	 * @param pageIndex
	 *            the index of the page
	 * @return the image, or <code>null</code> if none
	 */
	protected Image getPageImage(int pageIndex) {
		return getItem(pageIndex).getImage();
	}

	protected String getPageText(int pageIndex) {
		return getItem(pageIndex).getText();
	}
	protected Control getPage(int pageIndex) {
		return this.pageSites.get(pageIndex);
	}

	private CTabFolder getTabFolder() {
		return tabFolder;
	}

	protected void handlePropertyChange(int propertyId) {
		firePropertyChange(propertyId);
	}

	@Override
	public void init(IViewSite site) throws PartInitException {
		super.init(site);
		this.setSite(site);
		site.setSelectionProvider((ISelectionProvider) this);
	}


	public void addPage(int index, Control control) {
		createItem(index, control);
	}

	protected CTabItem createItem(int index, Control control) {
		CTabItem item = new CTabItem(getTabFolder(), SWT.NONE, index);
		item.setControl(control);
		this.pageSites.add(index, control);
		return item;
	}
	
	protected CTabFolder createContainer(Composite parent) {
		// use SWT.FLAT style so that an extra 1 pixel border is not reserved
		// inside the folder
		parent.setLayout(new FillLayout());
		final CTabFolder newContainer = new CTabFolder(parent, SWT.BOTTOM
				| SWT.FLAT);
		newContainer.addSelectionListener(widgetSelectedAdapter(e -> {
			int newPageIndex = newContainer.indexOf((CTabItem) e.item);
			pageChange(newPageIndex);
		}));
		newContainer.addTraverseListener(e -> {
			switch (e.detail) {
				case SWT.TRAVERSE_PAGE_NEXT:
				case SWT.TRAVERSE_PAGE_PREVIOUS:
					int detail = e.detail;
					e.doit = true;
					e.detail = SWT.TRAVERSE_NONE;
					Control control = newContainer.getParent();
					do {
						if (control.traverse(detail))
							return;
						if (control.getListeners(SWT.Traverse).length != 0)
							return;
						if (control instanceof Shell)
							return;
						control = control.getParent();
					} while (control != null);
			}
		});
		return newContainer;
	}
			
	protected void setActivePage(int pageIndex) {
		Assert.isTrue(pageIndex >= 0 && pageIndex < pageSites.size());
		tabFolder.setSelection(pageIndex);
		pageChange(pageIndex);
	}
	
	protected void pageChange(int newPageIndex) {
		IPartService partService = getSite().getService(
				IPartService.class);
		
		if (partService != null && partService.getActivePart() == this) {
			setFocus();
		}

		Object selectedPage = getSelectedPage();
				
		if (selectedPage != null) {
			firePageChanged(new PageChangedEvent(this, selectedPage));
		}
	}
	
	protected void hideTabs() {
		if (pageSites.size() <= 1) {
			setPageText(0, "");
			this.tabFolder.setTabHeight(1);
			Point point = this.tabFolder.getSize();
			this.tabFolder.setSize(point.x, point.y + 6);
		}
	}

	protected void showTabs() {
		if (pageSites.size() > 1) {
			setPageText(0, getPageText(0));
			this.tabFolder.setTabHeight(SWT.DEFAULT);
			Point point = this.tabFolder.getSize();
			this.tabFolder.setSize(point.x, point.y - 6);
		}
	}

	public void setFocus() {
		setFocus(getActivePage());
	}

	private void setFocus(int pageIndex) {
		if (pageIndex < 0 || pageIndex >= getPageCount()) {
			// page index out of bounds, don't set focus.
			return;
		}
		// Give the page's control focus.
		final Control control = getControl(pageIndex);
		if (control != null) {
			control.setFocus();
		}
	}
	
	public Object getSelectedPage() {
		int index = getActivePage();
		if (index == -1) {
			return null;
		}
		return getControl(index);
	}

	public void addPageChangedListener(IPageChangedListener listener) {
		pageChangeListeners.add(listener);
	}

	public void removePageChangedListener(IPageChangedListener listener) {
		pageChangeListeners.remove(listener);
	}

	private void firePageChanged(final PageChangedEvent event) {
		for (final IPageChangedListener l : pageChangeListeners) {
			SafeRunnable.run(new SafeRunnable() {
				@Override
				public void run() {
					l.pageChanged(event);
				}
			});
		}
	}
	
	public void addSelectionChangedListener(ISelectionChangedListener listener) {
		selectionChangedListeners.add(listener);
	}

	public void removeSelectionChangedListener(ISelectionChangedListener listener) {
		selectionChangedListeners.remove(listener);
	}

	public ISelection getSelection() {
		return viewSelection;
	}
}