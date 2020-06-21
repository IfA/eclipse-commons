package de.tud.et.ifa.agtele.ui.widgets;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.dialogs.FilteredTree;
import org.eclipse.ui.dialogs.PatternFilter;
import org.eclipse.ui.internal.WorkbenchMessages;
import org.eclipse.ui.progress.WorkbenchJob;

public class SelectionRestoringFilteredTree extends FilteredTree {
	
	public SelectionRestoringFilteredTree(Composite parent, boolean useNewLook, boolean useFastHashLookup) {
		super(parent, useNewLook);
	}
	
	public SelectionRestoringFilteredTree(Composite parent, int treeStyle, PatternFilter filter, boolean useNewLook,
			boolean useFastHashLookup) {
		super(parent, treeStyle, filter, useNewLook);
	}

	protected static final long SOFT_MAX_EXPAND_TIME2 = 200;
	
	protected boolean quickSelectionMode2 = false;
	
	protected String previousFilterText2;
	
	protected boolean narrowingDown2 = false;

	public void setQuickSelectionMode(boolean enabled) {
		this.quickSelectionMode2 = enabled;
		super.setQuickSelectionMode(enabled);
	}
	
	protected void textChanged() {
		narrowingDown2 = previousFilterText2 == null
				|| previousFilterText2.equals(WorkbenchMessages.FilteredTree_FilterMessage)
				|| getFilterString().startsWith(previousFilterText2);
		previousFilterText2 = getFilterString();
		super.textChanged();
	}
	
	//modified in order to keep the previous selection when the filtertext has been cleared
	protected WorkbenchJob doCreateRefreshJob() {
		//copied and modified from package org.eclipse.ui.dialogs.FilteredTree
		return new WorkbenchJob("Refresh Filter") {//$NON-NLS-1$
			@Override
			public IStatus runInUIThread(IProgressMonitor monitor) {
				if (treeViewer.getControl().isDisposed()) {
					return Status.CANCEL_STATUS;
				}

				String text = getFilterString();
				if (text == null) {
					return Status.OK_STATUS;
				}

				boolean initial = initialText != null && initialText.equals(text);
				TreeItem[] selection = treeViewer.getTree().getSelection();
				if (initial) {
					SelectionRestoringFilteredTree.this.getPatternFilter().setPattern(null);
				} else if (text != null) {
					SelectionRestoringFilteredTree.this.getPatternFilter().setPattern(text);
				}

				Control redrawFalseControl = treeComposite != null ? treeComposite : treeViewer.getControl();
				try {
					// don't want the user to see updates that will be made to
					// the tree
					// we are setting redraw(false) on the composite to avoid
					// dancing scrollbar
					redrawFalseControl.setRedraw(false);
					if (!narrowingDown2 && text.length()!=0 && !initial) {
						// collapse all
						TreeItem[] is = treeViewer.getTree().getItems();
						for (TreeItem item : is) {
							if (item.getExpanded()) {
								treeViewer.setExpandedState(item.getData(), false);
							}
						}
					}
					treeViewer.refresh(true);
					
					long stopTime = SOFT_MAX_EXPAND_TIME2 + System.currentTimeMillis();
					int treeHeight = getViewer().getTree().getBounds().height;
					int numVisibleItems = treeHeight / getViewer().getTree().getItemHeight();
					
					if (initial || text.length() == 0) {
						if (selection.length > 0
								&& recursiveExpand(selection, monitor, stopTime, new int[] { numVisibleItems })) {
							return Status.CANCEL_STATUS;
						}
						//treeViewer.setSelection(new StructuredSelection(selection), true);
					}

					if (text.length() > 0 && !initial) {
						/*
						 * Expand elements one at a time. After each is expanded, check to see if the
						 * filter text has been modified. If it has, then cancel the refresh job so the
						 * user doesn't have to endure expansion of all the nodes.
						 */
						TreeItem[] items = getViewer().getTree().getItems();
						if (items.length > 0
								&& recursiveExpand(items, monitor, stopTime, new int[] { numVisibleItems })) {
							return Status.CANCEL_STATUS;
						}
					}
				} finally {
					// done updating the tree - set redraw back to true
					TreeItem[] items = getViewer().getTree().getItems();
					if (items.length > 0 && getViewer().getTree().getSelectionCount() == 0) {
						treeViewer.getTree().setTopItem(items[0]);
					}
					if (quickSelectionMode2)
						updateTreeSelection(false);
					redrawFalseControl.setRedraw(true);
				}
				return Status.OK_STATUS;
			}

			/**
			 * Returns true if the job should be canceled (because of timeout or actual
			 * cancellation).
			 *
			 * @param items
			 * @param monitor
			 * @param cancelTime
			 * @param numItemsLeft
			 * @return true if canceled
			 */
			private boolean recursiveExpand(TreeItem[] items, IProgressMonitor monitor, long cancelTime,
					int[] numItemsLeft) {
				boolean canceled = false;
				for (int i = 0; !canceled && i < items.length; i++) {
					TreeItem item = items[i];
					boolean visible = numItemsLeft[0]-- >= 0;
					if (monitor.isCanceled() || (!visible && System.currentTimeMillis() > cancelTime)) {
						canceled = true;
					} else {
						if (!item.isDisposed()) {
							Object itemData = item.getData();
							if (itemData != null) {
								if (!item.getExpanded()) {
									// do the expansion through the viewer so that
									// it can refresh children appropriately.
									treeViewer.setExpandedState(itemData, true);
								}
								TreeItem[] children = item.getItems();
								if (items.length > 0) {
									canceled = recursiveExpand(children, monitor, cancelTime, numItemsLeft);
								}
							}
						}
					}
				}
				return canceled;
			}

		};
	}
}
