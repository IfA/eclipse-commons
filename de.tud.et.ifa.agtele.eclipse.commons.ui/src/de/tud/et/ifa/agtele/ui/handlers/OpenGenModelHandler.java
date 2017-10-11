package de.tud.et.ifa.agtele.ui.handlers;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.resources.IFile;
import org.eclipse.emf.codegen.ecore.genmodel.GenBase;
import org.eclipse.emf.codegen.ecore.genmodel.GenModel;
import org.eclipse.emf.common.ui.viewer.IViewerProvider;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecore.impl.EAttributeImpl;
import org.eclipse.emf.ecore.impl.EClassImpl;
import org.eclipse.emf.ecore.impl.EEnumImpl;
import org.eclipse.emf.ecore.impl.EEnumLiteralImpl;
import org.eclipse.emf.ecore.impl.EOperationImpl;
import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.eclipse.emf.ecore.impl.EReferenceImpl;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.domain.IEditingDomainProvider;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.FileEditorInput;

import de.tud.et.ifa.agtele.resources.ResourceHelper;
import de.tud.et.ifa.agtele.ui.util.UIHelper;

/**
 * An {@link IHandler} that, based on a selection in an Ecore model, opens the associated GenModel and selects the
 * element in the GenModel that is associated with the selected element in the Ecore model.
 * <p />
 * Note: This assumes that the GenModel associated with an Ecore model is located in the same folder and has the same
 * base name (i.e. <em>basename.ecore</em> -> <em>basename.genmodel</em>).
 *
 * @author mfreund
 */
public class OpenGenModelHandler extends AbstractHandler {

	/**
	 * The list of types on which we allow this handler to be executed.
	 * <p />
	 * This are those elements of the Ecore metameta-model that are also represented in a GenModel.
	 */
	private static final List<Class<?>> allowedElementTypes = Arrays.asList(EPackageImpl.class, EClassImpl.class,
			EAttributeImpl.class, EReferenceImpl.class, EOperationImpl.class, EEnumImpl.class, EEnumLiteralImpl.class);

	/**
	 * This returns the list of types on which we allow this handler to be executed.
	 *
	 * @return The list of types on which we allow this handler to be executed.
	 */
	protected List<Class<?>> getAllowedElementTypes() {

		return OpenGenModelHandler.allowedElementTypes;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.core.commands.IHandler#execute(org.eclipse.core.commands. ExecutionEvent)
	 */
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {

		this.openGenModelAndSelectElement();

		return null;
	}

	/**
	 * Based on the current selection, opens the associated GenModel and selects the element in the GenModel that is
	 * associated with the selected element.
	 *
	 * @return The selected {@link GenBase GenModel element} or 'null' anything went wrong.
	 */
	protected GenBase openGenModelAndSelectElement() {

		if (!(UIHelper.getCurrentEditorInput() instanceof FileEditorInput)) {
			this.showError("Unable to determine associated GenModel");
			return null;
		}

		// The input for the current editor (the Ecore file)
		//
		FileEditorInput editorInput = (FileEditorInput) UIHelper.getCurrentEditorInput();

		// Determine the EObject currently selected in the Ecore editor
		//
		if (!(UIHelper.getFirstSelection() instanceof EObject)) {
			this.showError("Unable to determine current selection in the Ecore editor");
			return null;
		}

		EObject selectedElement = (EObject) UIHelper.getFirstSelection();

		IFile genModelFile = ResourceHelper
				.getFileForPath(editorInput.getPath().removeFileExtension().addFileExtension("genmodel"));

		if (genModelFile == null) {
			this.showError("Error while determining the associated GenModel file");
			return null;
		}

		// Open the GenModel editor
		//
		IEditorPart genModelEditor = null;
		try {
			genModelEditor = UIHelper.openEditor(genModelFile);
		} catch (PartInitException e) {
			this.showError("Error while opening the GenModelEditor");
			return null;
		}

		if (!(genModelEditor instanceof IEditingDomainProvider)) {
			this.showError("Unable to determine corresponding element in the GenModel");
			return null;
		}

		// As the Ecore editor and the GenModel editor are two distinct editors,
		// both use their own resource set to load
		// resources. Consequently, we have to first determine the resource that
		// is equivalent to the resource opened in
		// the Ecore editor
		//
		ResourceSet genModelEditorResourceSet = ((IEditingDomainProvider) genModelEditor).getEditingDomain()
				.getResourceSet();
		Resource ecoreResource = genModelEditorResourceSet.getResource(selectedElement.eResource().getURI(), true);

		if (ecoreResource == null) {
			this.showError("Unable to determine corresponding element in the GenModel");
			return null;
		}

		// Now that we now the equivalent resources, we can determine the
		// equivalent elements
		//
		String uriFragment = selectedElement.eResource().getURIFragment(selectedElement);
		EObject correspondingSelection = ecoreResource.getEObject(uriFragment);

		if (correspondingSelection == null) {
			this.showError("Unable to determine corresponding element in the GenModel");
			return null;
		}

		// Finally, we determine the element of the GenModel that
		// describes/references the selected EObject
		//
		Set<Resource> possibleGenModelResources = genModelEditorResourceSet.getResources().parallelStream()
				.filter(r -> !r.getContents().isEmpty() && r.getContents().get(0) instanceof GenModel)
				.collect(Collectors.toSet());
		Collection<Setting> references = EcoreUtil.UsageCrossReferencer
				.find(correspondingSelection, possibleGenModelResources).parallelStream()
				.filter(s -> s.getEObject() instanceof GenBase
						&& correspondingSelection.equals(((GenBase) s.getEObject()).getEcoreModelElement()))
				.collect(Collectors.toList());

		if (references.isEmpty()) {
			this.showError("Unable to determine corresponding element in the GenModel");
			return null;
		}

		GenBase correspondingGenModelElement = (GenBase) references.iterator().next().getEObject();

		// Now, reveal and select the determined element in the new GenModel
		// editor
		//
		if (genModelEditor instanceof IViewerProvider) {
			((IViewerProvider) genModelEditor).getViewer()
					.setSelection(new StructuredSelection(correspondingGenModelElement));
		}

		return correspondingGenModelElement;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.core.commands.AbstractHandler#isEnabled()
	 */
	@Override
	public boolean isEnabled() {

		// test if the menu is actually shown in an Editor
		if (!(UIHelper.getCurrentEditorInput() instanceof FileEditorInput)) {
			return false;
		}

		// test if only on item is currently selected
		if (!(UIHelper.getCurrentSelection() instanceof StructuredSelection)
				|| ((StructuredSelection) UIHelper.getCurrentSelection()).size() != 1) {
			return false;
		}

		// test if the current selection is an EObject
		Object selection = UIHelper.getFirstSelection();

		if (!this.getAllowedElementTypes().parallelStream().filter(t -> t.isInstance(selection)).findAny()
				.isPresent()) {
			return false;
		}

		// test if a genmodel actually exists
		IFile genModelFile = ResourceHelper.getFileForPath(((FileEditorInput) UIHelper.getCurrentEditorInput())
				.getPath().removeFileExtension().addFileExtension("genmodel"));

		if (!genModelFile.exists()) {
			return false;
		}

		// apparently everything is awesome... so keep going with the command
		return super.isEnabled();
	}

	/**
	 * Show an error to the user by opening a {@link MessageDialog}.
	 *
	 * @param errorMessage
	 *            The message to display to the user.
	 */
	protected void showError(String errorMessage) {

		Shell shell = UIHelper.getShell();
		MessageDialog.openError(shell, "ERROR", errorMessage);
	}

}
