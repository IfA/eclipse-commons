package de.tud.et.ifa.agtele.ui.handlers;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jdt.internal.core.CompilationUnit;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IEditorPart;

import de.tud.et.ifa.agtele.ui.handlers.AbstractGeneratedEMFCodeHandler.EMFGeneratedJavaFileType;

/**
 * This implementation of the {@link AbstractGeneratedEMFCodeHandler} aims to import a code section
 * to the opened ecore model. 
 * As implementation source, only implementation classes from the emf 'model' code are accepted.
 * @author baron
 */
@SuppressWarnings("restriction")
public class PushCodeToEcoreHandler extends AbstractGeneratedEMFCodeHandler {

	@Override
	protected void performAsyncActionOnEcoreEditor(IEditorPart ecoreEditor, EObject specificEcoreElement,
			ISelection javaSelection) {
		// TODO Auto-generated method stub

	}
	
	@Override
	public boolean isEnabled() {
		if (!super.isEnabled()) {
			return false;
		}
		
		// check if the current compilation unit is a model code implementation		
		CompilationUnit cUnit = determineCompilationUnit();
		String mainTypeName = String.valueOf(cUnit.getMainTypeName());
		EMFGeneratedJavaFileType type = EMFGeneratedJavaFileType.getFileType(mainTypeName);
		
		return type.isClassImplementationType();
	}

}
