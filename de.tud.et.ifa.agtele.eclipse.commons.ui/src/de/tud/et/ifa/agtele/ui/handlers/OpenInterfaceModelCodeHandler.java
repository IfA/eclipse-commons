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
package de.tud.et.ifa.agtele.ui.handlers;

import org.eclipse.emf.codegen.ecore.genmodel.GenBase;
import org.eclipse.emf.codegen.ecore.genmodel.GenClass;
import org.eclipse.emf.codegen.ecore.genmodel.GenEnum;
import org.eclipse.emf.codegen.ecore.genmodel.GenPackage;

/**
 * An {@link OpenCodeHandler} that opens the generated Java interface file for the model object.
 *
 * @author mfreund
 */
public class OpenInterfaceModelCodeHandler extends OpenCodeHandler {

	@Override
	protected String getDirectory(GenBase selectedElement) {

		return selectedElement.getGenModel().getModelDirectory();
	}

	@Override
	protected String getQualifiedClassName(GenBase element) {

		if (element instanceof GenClass) {
			return ((GenClass) element).getQualifiedInterfaceName();
		} else if (element instanceof GenPackage) {
			return ((GenPackage) element).getQualifiedPackageInterfaceName();
		} else if (element instanceof GenEnum) {
			return ((GenEnum) element).getQualifiedInstanceClassName();
		} else {
			return null;
		}
	}
}
