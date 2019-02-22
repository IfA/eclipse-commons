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

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.jface.viewers.TreeViewer;

/**
 * An {@link AdapterFactoryContentProvider} that implements state persistence of the associated {@link StructuredViewer}
 * when the underlying resource changes, i.e. the {@link TreeViewer#getStructuredSelection() selection} and (if the
 * underlying viewer is a {@link TreeViewer}) {@link TreeViewer#getExpandedElements() expansion} states are restored (as
 * far as possible) after a change to the underlying resource.
 *
 * @author lbaron
 */
public class StateRestoringViewerContentProvider extends AgteleContentProvider {

	/**
	 * This creates an instance.
	 *
	 * @param adapterFactory
	 *            The {@link AdapterFactory} that this provider will consult to retrieve children, parents, etc.
	 * @param structuredViewer
	 *            The {@link TreeViewer} that this provider is associated with.
	 */
	public StateRestoringViewerContentProvider(AdapterFactory adapterFactory, StructuredViewer structuredViewer) {

		super(adapterFactory);

		if (structuredViewer != null) {
			this.viewerRefresh = new StateRestoringViewerRefresh(structuredViewer);
		}
	}
}
