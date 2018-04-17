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

import java.util.Collection;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

/**
 * An {@link ITreeContentProvider} that creates the tree structure based on the containment tree of an EMF model.
 * Therefore, it makes use of {@link EObject#eContents()} and {@link EObject#eContainer()} to determine children and
 * parents. <br />
 * <br />
 * This class and all of its methods may be overridden or extended.
 *
 * @author mfreund
 */
public class EObjectTreeContentProvider implements ITreeContentProvider {

	@Override
	public void dispose() {

		return;
	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {

		return;
	}

	@Override
	public Object[] getElements(Object inputElement) {

		if (inputElement instanceof EObject) {
			return this.getChildren(inputElement);
		} else if (inputElement instanceof Collection) {
			return ((Collection<?>) inputElement).toArray();
		} else if (inputElement instanceof Object[]) {
			return (Object[]) inputElement;
		} else {
			return new Object[] {};
		}
	}

	@Override
	public Object[] getChildren(Object parentElement) {

		return parentElement instanceof EObject ? ((EObject) parentElement).eContents().toArray() : new Object[] {};
	}

	@Override
	public Object getParent(Object element) {

		return element instanceof EObject ? ((EObject) element).eContainer() : null;
	}

	@Override
	public boolean hasChildren(Object element) {

		return this.getChildren(element).length > 0 ? true : false;
	}

}
