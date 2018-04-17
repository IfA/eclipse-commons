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
package de.tud.et.ifa.agtele.ui.views;

import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import de.tud.et.ifa.agtele.ui.widgets.TreeViewerGroup;
import de.tud.et.ifa.agtele.ui.widgets.TreeViewerGroup.TreeViewerGroupOption;

public class TreeViewerGroupViewer extends Viewer {

	protected TreeViewerGroup group;
	
	public void createControl (Composite parent, ComposedAdapterFactory adapterFactory, EditingDomain editingDomain, IDialogSettings dialogSettings, String groupText, TreeViewerGroupOption... options) {
		group = new TreeViewerGroup(parent,adapterFactory, editingDomain,
				dialogSettings, null,
				new TreeViewerGroup.TreeViewerGroupToolbarCollapseAllButtonOption(),
				new TreeViewerGroup.TreeViewerGroupToolbarAddButtonOption(),
				new TreeViewerGroup.TreeViewerGroupToolbarToggleSplitEditorVerticallyButtonOption(),
				new TreeViewerGroup.TreeViewerGroupAddToolPaletteOption.TreeViewerGroupAddToolPaletteToolbarHideEMFPaletteOption());
	}
	
	public TreeViewerGroup getTreeViewerGroup () {
		return group;
	}
	
	@Override
	public Control getControl() {
		return group;
	}

	@Override
	public Object getInput() {
		return group.getViewer().getInput();
	}

	@Override
	public ISelection getSelection() {
		return group.getViewer().getSelection();
	}

	@Override
	public void refresh() {
		group.getViewer().refresh();

	}

	@Override
	public void setInput(Object input) {
		group.getViewer().setInput(input);
	}

	@Override
	public void setSelection(ISelection selection, boolean reveal) {
		group.getViewer().setSelection(selection, reveal);

	}

}
