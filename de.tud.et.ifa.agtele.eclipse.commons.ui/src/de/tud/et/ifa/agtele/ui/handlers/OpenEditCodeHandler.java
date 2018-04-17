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
import org.eclipse.emf.codegen.ecore.genmodel.GenClass;
import org.eclipse.emf.codegen.ecore.genmodel.GenPackage;
import org.eclipse.emf.codegen.ecore.genmodel.impl.GenClassImpl;
import org.eclipse.emf.codegen.ecore.genmodel.impl.GenFeatureImpl;
import org.eclipse.emf.codegen.ecore.genmodel.impl.GenOperationImpl;
import org.eclipse.emf.codegen.ecore.genmodel.impl.GenPackageImpl;
import org.eclipse.emf.ecore.impl.EAttributeImpl;
import org.eclipse.emf.ecore.impl.EClassImpl;
import org.eclipse.emf.ecore.impl.EOperationImpl;
import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.eclipse.emf.ecore.impl.EReferenceImpl;

/**
 * An {@link OpenCodeHandler} that opens the generated Java item provider file
 * for the model object.
 *
 * @author mfreund
 */
public class OpenEditCodeHandler extends OpenCodeHandler {

	@Override
	protected List<Class<?>> getAllowedElementTypes() {
		return Arrays.asList(EPackageImpl.class, EClassImpl.class, EOperationImpl.class, EAttributeImpl.class,
				EReferenceImpl.class, GenPackageImpl.class, GenClassImpl.class, GenOperationImpl.class,
				GenFeatureImpl.class);
	}

	@Override
	protected String getDirectory(GenBase selectedElement) {

		return selectedElement.getGenModel().getEditDirectory();
	}

	@Override
	protected String getQualifiedClassName(GenBase element) {

		if (element instanceof GenClass) {
			return ((GenClass) element).getQualifiedProviderClassName();
		} else if (element instanceof GenPackage) {
			return ((GenPackage) element).getQualifiedItemProviderAdapterFactoryClassName();
		} else {
			return null;
		}
	}
}
