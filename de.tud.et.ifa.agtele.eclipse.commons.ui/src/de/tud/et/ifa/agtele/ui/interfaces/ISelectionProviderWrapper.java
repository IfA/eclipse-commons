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
package de.tud.et.ifa.agtele.ui.interfaces;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.Viewer;

/**
 * This interface can be implemented by classes that provide the {@link ISelectionProvider} functionality by redirecting
 * to one or multiple other {@link ISelectionProvider providers} (e.g. contained {@link Viewer viewers}) instead of
 * providing a selection on their own.
 * <p />
 * The default implementations of the various interface methods of {@link ISelectionProvider} just redirect to the
 * implementations in all of the {@link #getWrappedProviders() wrapped providers}.
 *
 * @author mfreund
 */
public interface ISelectionProviderWrapper extends ISelectionProvider {

	/**
	 * This returns the collection of {@link ISelectionProvider ISelectionProviders} wrapped by this.
	 *
	 * @return The wrapped {@link ISelectionProvider ISelectionProviders}.
	 */
	public abstract Collection<ISelectionProvider> getWrappedProviders();

	@Override
	public default void addSelectionChangedListener(ISelectionChangedListener listener) {

		this.getWrappedProviders().stream().forEach(p -> p.addSelectionChangedListener(listener));
	}

	@Override
	public default ISelection getSelection() {

		return new StructuredSelection(
				this.getWrappedProviders().stream().filter(p -> p.getSelection() instanceof IStructuredSelection)
						.flatMap(p -> Arrays.asList(((IStructuredSelection) p.getSelection()).toArray()).stream())
						.collect(Collectors.toList()));
	}

	@Override
	public default void removeSelectionChangedListener(ISelectionChangedListener listener) {

		this.getWrappedProviders().stream().forEach(p -> p.removeSelectionChangedListener(listener));
	}

	@Override
	public default void setSelection(ISelection selection) {

		this.getWrappedProviders().stream().forEach(p -> p.setSelection(selection));
	}
}
