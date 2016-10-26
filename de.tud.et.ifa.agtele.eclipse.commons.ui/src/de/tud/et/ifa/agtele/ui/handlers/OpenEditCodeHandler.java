package de.tud.et.ifa.agtele.ui.handlers;

import org.eclipse.emf.codegen.ecore.genmodel.GenBase;
import org.eclipse.emf.codegen.ecore.genmodel.GenClass;
import org.eclipse.emf.codegen.ecore.genmodel.GenPackage;

/**
 * An {@link OpenCodeHandler} that opens the generated Java item provider file for the model object.
 *
 * @author mfreund
 */
public class OpenEditCodeHandler extends OpenCodeHandler {

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
