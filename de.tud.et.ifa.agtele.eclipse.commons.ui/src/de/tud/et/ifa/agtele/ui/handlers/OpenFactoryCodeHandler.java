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

import java.util.Arrays;
import java.util.List;

import org.eclipse.emf.codegen.ecore.genmodel.GenBase;
import org.eclipse.emf.codegen.ecore.genmodel.GenPackage;
import org.eclipse.emf.codegen.ecore.genmodel.impl.GenPackageImpl;
import org.eclipse.emf.ecore.impl.EPackageImpl;

/**
 * An {@link OpenCodeHandler} that opens the generated Java factory file for the
 * model object.
 *
 * @author mfreund
 */
public class OpenFactoryCodeHandler extends OpenCodeHandler {

	@Override
	protected List<Class<?>> getAllowedElementTypes() {
		return Arrays.asList(EPackageImpl.class, GenPackageImpl.class);
	}

	@Override
	protected String getDirectory(GenBase selectedElement) {

		return selectedElement.getGenModel().getModelDirectory();
	}

	@Override
	protected String getQualifiedClassName(GenBase element) {

		if (element instanceof GenPackage) {
			return ((GenPackage) element).getQualifiedFactoryClassName();
		} else {
			return null;
		}
	}
}
