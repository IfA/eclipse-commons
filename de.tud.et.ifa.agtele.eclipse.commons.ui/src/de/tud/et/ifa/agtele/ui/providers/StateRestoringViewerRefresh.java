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
package de.tud.et.ifa.agtele.ui.providers;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider.ViewerRefresh;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.jface.viewers.TreeViewer;

/**
 * A {@link ViewerRefresh} that implements state persistence of the associated {@link StructuredViewer} when the
 * underlying resource changes, i.e. the {@link StructuredViewer#getStructuredSelection() selection} and (if the
 * underlying viewer is a {@link TreeViewer}) {@link TreeViewer#getExpandedElements() expansion} states are restored
 * (as far as possible) after a change to the underlying resource.
 *
 * @author mfreund
 */
public class StateRestoringViewerRefresh extends ViewerRefresh {

	/**
	 * The {@link StructuredViewer} that this ViewerRefresh is associated with.
	 */
	protected StructuredViewer structuredViewer;

	/**
	 * This creates an instance.
	 *
	 * @param structuredViewer
	 *            The {@link StructuredViewer} that this ViewerRefresh is associated with.
	 */
	public StateRestoringViewerRefresh(StructuredViewer structuredViewer) {
		super(structuredViewer);

		this.structuredViewer = structuredViewer;
	}

	@Override
	public void run() {
		if (this.structuredViewer.getControl().isDisposed()) {
			return;
		}
		
		Object resource = this.structuredViewer.getInput();

		// Save the selection and expansion before refreshing the viewer
		//
		IStructuredSelection selection = this.structuredViewer.getStructuredSelection();

		Object[] expanded = new Object[] {};

		if (this.structuredViewer instanceof TreeViewer) {
			expanded = ((TreeViewer) this.structuredViewer).getExpandedElements();
		}


		super.run();

		if (resource instanceof Resource) {

			// Restore the previous expansion
			//
			if (this.structuredViewer instanceof TreeViewer) {

				List<Object> newExpanded = Arrays.asList(expanded).stream()
						.map(obj -> obj instanceof EObject && ((EObject) obj).eIsProxy()
								? EcoreUtil.resolve((EObject) obj, ((Resource) resource).getResourceSet()) : obj)
						.filter(obj -> obj != null).collect(Collectors.toList());

				((TreeViewer) this.structuredViewer).setExpandedElements(newExpanded.toArray());
			}

			// Restore the previous selection
			//
			List<Object> newSelection = Arrays.asList(selection.toArray()).stream()
					.map(obj -> obj instanceof EObject && ((EObject) obj).eIsProxy()
							? EcoreUtil.resolve((EObject) obj, ((Resource) resource).getResourceSet()) : obj)
					.filter(obj -> obj != null).collect(Collectors.toList());

			this.structuredViewer.setSelection(new StructuredSelection(newSelection));
		}
	}
}
