/**
 *
 */
package de.tud.et.ifa.agtele.ui.handlers;

import org.eclipse.emf.common.ui.viewer.IViewerProvider;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.edit.domain.IEditingDomainProvider;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jface.text.TextSelection;
import org.eclipse.jface.viewers.StructuredSelection;

import de.tud.et.ifa.agtele.emf.AgteleEcoreUtil;

/**
 * An {@link EditorOpeningGeneratedEMFCodeHandler} that simply sets focus to the Ecore element corresponding to the
 * selection in the Java editor.
 *
 *
 * @author mfreund
 */
@SuppressWarnings("restriction")
public class OpenMetamodelHandler extends EditorOpeningGeneratedEMFCodeHandler {

	/**
	 * Select the metamodel element that corresponds to the current selection in the Java editor.
	 */
	@Override
	protected void performAsyncActionOnEcoreEditor() throws Exception {

		if (!(this.ecoreEditor instanceof IViewerProvider) || !(this.ecoreEditor instanceof IEditingDomainProvider)
				|| !(this.javaSelection instanceof TextSelection)) {
			return;
		}

		Object elementToSelect = this.getElementToSelect();

		// Finally, reveal and select the determined element in the Ecore editor
		//
		((IViewerProvider) this.ecoreEditor).getViewer().setSelection(new StructuredSelection(elementToSelect), true);
	}

	/**
	 * Returns the element to select in the opened editor.
	 */
	protected Object getElementToSelect() throws Exception {

		// The element currently selected in the Java editor
		//
		IJavaElement javaElement = this.helper.getCompilationUnit()
				.getElementAt(((TextSelection) this.javaSelection).getOffset());

		// The element that we are going to select in the editor
		//
		EObject elementToSelect = javaElement != null ? this.helper.getMoreSpecificSelection(javaElement)
				: this.helper.getMetamodelElement();

		// As the Ecore editor may have used a different resource set to load the Ecore metamodel, we have to first
		// determine the element equivalent to the 'elementToSelect' from this ResourceSet
		//
		ResourceSet ecoreEditorResourceSet = ((IEditingDomainProvider) this.ecoreEditor).getEditingDomain()
				.getResourceSet();

		elementToSelect = AgteleEcoreUtil.getEquivalentElementFrom(elementToSelect, ecoreEditorResourceSet);
		return elementToSelect;
	}
}
