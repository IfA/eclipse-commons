/**
 *
 */
package de.tud.et.ifa.agtele.ui.handlers;

import org.eclipse.emf.common.ui.viewer.IViewerProvider;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jdt.internal.core.CompilationUnit;
import org.eclipse.jdt.internal.ui.javaeditor.CompilationUnitEditor;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IEditorPart;

/**
 * An {@link AbstractGeneratedEMFCodeHandler} that simply sets focus to the
 * Ecore element corresponding to the selection in the java editor, provided by
 * the {@link AbstractGeneratedEMFCodeHandler}
 *
 *
 * @author mfreund
 */
@SuppressWarnings("restriction")
public class OpenMetamodelHandler extends AbstractGeneratedEMFCodeHandler {

	@Override
	protected void performAsyncActionOnEcoreEditor(IEditorPart ecoreEditor, CompilationUnit compilationUnit,
			CompilationUnitEditor javaEditor, EObject specificEcoreElement, ISelection javaSelection) {

		if (ecoreEditor instanceof IViewerProvider) {
			((IViewerProvider) ecoreEditor).getViewer().setSelection(new StructuredSelection(specificEcoreElement),
					true);
		}
	}
}
