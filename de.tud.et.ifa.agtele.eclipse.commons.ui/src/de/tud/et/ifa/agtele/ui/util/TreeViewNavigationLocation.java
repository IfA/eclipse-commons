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
/**
 * 
 */
package de.tud.et.ifa.agtele.ui.util;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.INavigationLocation;
import org.eclipse.ui.INavigationLocationProvider;
import org.eclipse.ui.NavigationLocation;

/**
 * @author Lukas
 * The tree view navigation location is used by TreeEditors in order to provide the {@link INavigationLocationProvider} feature.
 */
public class TreeViewNavigationLocation extends NavigationLocation {

	TreeViewer treeViewer;
	private ISelection selection;
	
	/**
	 * @param editorPart
	 * @param treeViewer 
	 */
	public TreeViewNavigationLocation(IEditorPart editorPart, TreeViewer treeViewer) {
		super(editorPart);
		
		this.treeViewer = treeViewer;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.INavigationLocation#saveState(org.eclipse.ui.IMemento)
	 */
	@Override
	public void saveState(IMemento memento) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.INavigationLocation#restoreState(org.eclipse.ui.IMemento)
	 */
	@Override
	public void restoreState(IMemento memento) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.INavigationLocation#restoreLocation()
	 */
	@Override
	public void restoreLocation() {
		this.treeViewer.setSelection(this.selection, true);		
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.INavigationLocation#mergeInto(org.eclipse.ui.INavigationLocation)
	 */
	@Override
	public boolean mergeInto(INavigationLocation currentLocation) {
		if (currentLocation == null)
			return false;

		if (getClass() != currentLocation.getClass())
			return false;
		
		if (this.selection == null) {
			return true;
		}
		if (this.selection.isEmpty()) {
			return true;
		}
		if (((TreeViewNavigationLocation)currentLocation).selection == null || ((TreeViewNavigationLocation)currentLocation).selection.isEmpty()) {
			((TreeViewNavigationLocation)currentLocation).selection = this.selection;
			return true;
		}
		if (((TreeViewNavigationLocation)currentLocation).selection.equals(this.selection)) {
			return true;
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.INavigationLocation#update()
	 */
	@Override
	public void update() {
		this.selection = this.treeViewer.getSelection();
	}

}
