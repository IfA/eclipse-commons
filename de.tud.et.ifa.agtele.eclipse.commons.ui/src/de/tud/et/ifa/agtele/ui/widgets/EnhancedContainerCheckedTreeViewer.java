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

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Item;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.ui.dialogs.ContainerCheckedTreeViewer;

/**
 * A special {@link ContainerCheckedTreeViewer} that - compared to the original
 * one - modifies the {@link #doCheckStateChanged(Object)} method in a way that
 * parents are not automatically unchecked if all children are unchecked.
 * <p />
 * Additionally, the private methods
 * {@link EnhancedContainerCheckedTreeViewer#updateChildrenItems(TreeItem)
 * updateChildrenItems(TreeItem)} and
 * {@link EnhancedContainerCheckedTreeViewer#updateParentItems(TreeItem)
 * updateParentItems(TreeItem)} are now protected so that they can be further
 * customized easily.
 *
 * @author mfreund
 */
public class EnhancedContainerCheckedTreeViewer extends ContainerCheckedTreeViewer {

	/**
	 * This creates an instance.
	 *
	 * @param parent
	 *
	 * @see ContainerCheckedTreeViewer#ContainerCheckedTreeViewer(Composite)
	 */
	public EnhancedContainerCheckedTreeViewer(Composite parent) {
		super(parent);
	}

	/**
	 * This creates an instance.
	 *
	 * @param parent
	 * @param style
	 *
	 * @see ContainerCheckedTreeViewer#ContainerCheckedTreeViewer(Composite,
	 *      int)
	 */
	public EnhancedContainerCheckedTreeViewer(Composite parent, int style) {
		super(parent, style);
	}

	/**
	 * This creates an instance.
	 *
	 * @param tree
	 *
	 * @see ContainerCheckedTreeViewer#ContainerCheckedTreeViewer(Tree)
	 */
	public EnhancedContainerCheckedTreeViewer(Tree tree) {
		super(tree);
	}

	@Override
	protected void doCheckStateChanged(Object element) {
		// Copied from 'ContainerCheckedTreeViewer'
		//
		Widget item = this.findItem(element);
		if (item instanceof TreeItem) {
			TreeItem treeItem = (TreeItem) item;
			treeItem.setGrayed(false);
			this.updateChildrenItems(treeItem);
			this.updateParentItems(treeItem.getParentItem());
		}
	}

	/**
	 * Updates the check state of all created children
	 */
	protected void updateChildrenItems(TreeItem parent) {
		// Copied from 'ContainerCheckedTreeViewer'
		//
		Item[] children = this.getChildren(parent);
		boolean state = parent.getChecked();
		for (Item element : children) {
			TreeItem curr = (TreeItem) element;
			if (curr.getData() != null && (curr.getChecked() != state || curr.getGrayed())) {
				curr.setChecked(state);
				curr.setGrayed(false);
				this.updateChildrenItems(curr);
			}
		}
	}

	/**
	 * Updates the check / gray state of all parent items
	 */
	protected void updateParentItems(TreeItem item) {
		// Copied from 'ContainerCheckedTreeViewer' and modified
		//
		//
		if (item != null) {
			Item[] children = this.getChildren(item);

			boolean containsChecked = false;
			boolean containsUnchecked = false;
			for (Item element : children) {
				TreeItem curr = (TreeItem) element;
				containsChecked |= curr.getChecked();
				containsUnchecked |= !curr.getChecked() || curr.getGrayed();
			}
			// Begin modified
			//
			if (containsChecked) {
				// Do not uncheck a parent automatically
				item.setChecked(true);
			}
			// End modified
			//
			item.setGrayed(containsUnchecked);
			this.updateParentItems(item.getParentItem());
		}
	}
}
