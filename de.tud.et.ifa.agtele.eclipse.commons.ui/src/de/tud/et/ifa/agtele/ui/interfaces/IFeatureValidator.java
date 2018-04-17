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

import org.eclipse.emf.ecore.EStructuralFeature;

/**
 * This interface can be implemented to indicate that an object is able to <em>validate</em> an 
 * {@link EStructuralFeature}. The concrete meaning of the term <em>validate</em> is dependent
 * on the concrete use case. 
 * 
 * @author mfreund
 */
public interface IFeatureValidator {

	/**
	 * Whether the given <em>feature</em> is supported by/valid for this object. The default implementation
	 * simply returns <em>true</em> for any feature.
	 * 
	 * @param feature The {@link EStructuralFeature} to check.
	 * @return '<em><b>true</b></em>' if the feature is supported by this class, '<em><b>false</b></em>' otherwise.
	 */
	public default boolean isValidFeature(EStructuralFeature feature) {
		return true;
	}
}
