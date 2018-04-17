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
package de.tud.et.ifa.agtele.emf.connecting.impl;

import java.util.Collection;

import org.eclipse.emf.ecore.EObject;

import de.tud.et.ifa.agtele.emf.connecting.EClassConnectionPath;
import de.tud.et.ifa.agtele.emf.connecting.EClassConnectionPathInstantiator;

/**
 * An {@link EClassConnectionPathInstantiator} that is able to instantiate {@link EmptyEClassConnectionPath
 * EmptyEClassConnectionPath}.
 *
 * @author mfreund
 */
@SuppressWarnings("javadoc")
public class EmptyEClassConnectionPathInstantiator extends EClassConnectionPathInstantiator {

	public EmptyEClassConnectionPathInstantiator(EClassConnectionPath pathToInstantiate, EObject startingElement,
			Collection<EObject> targetElements) {

		super(pathToInstantiate, startingElement, targetElements);
	}

	@Override
	protected void doInstantiate() {

		// nothing needs to be instantiated because this represents an 'empty' path

	}

}
