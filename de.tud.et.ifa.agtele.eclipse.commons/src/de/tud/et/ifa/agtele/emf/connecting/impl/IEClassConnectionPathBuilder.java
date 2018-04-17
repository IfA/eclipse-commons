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

import java.util.List;

import de.tud.et.ifa.agtele.emf.connecting.EClassConnectionPath;

/**
 * A base interface for builders that are able to construct a list of {@link EClassConnectionPath EClassConnectionPaths}
 * based on some kind of requirements.
 *
 * @author mfreund
 */
public interface IEClassConnectionPathBuilder {

	/**
	 * Determine all {@link EClassConnectionPath EClassConnectionPaths} based on the strategy implemented by the class
	 * implementing this interface.
	 *
	 * @return The list of paths.
	 */
	public List<EClassConnectionPath> buildConnectionPaths();

}
