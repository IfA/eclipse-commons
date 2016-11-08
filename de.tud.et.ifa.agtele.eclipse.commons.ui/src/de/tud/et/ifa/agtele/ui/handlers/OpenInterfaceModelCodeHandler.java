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
