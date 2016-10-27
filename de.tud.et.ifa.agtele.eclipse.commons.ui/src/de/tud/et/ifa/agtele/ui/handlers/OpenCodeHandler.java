/**
 *
 */
package de.tud.et.ifa.agtele.ui.handlers;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.emf.codegen.ecore.genmodel.GenBase;
import org.eclipse.emf.codegen.ecore.genmodel.GenClass;
import org.eclipse.emf.codegen.ecore.genmodel.GenEnum;
import org.eclipse.emf.codegen.ecore.genmodel.GenEnumLiteral;
import org.eclipse.emf.codegen.ecore.genmodel.GenFeature;
import org.eclipse.emf.codegen.ecore.genmodel.GenOperation;
import org.eclipse.emf.codegen.ecore.genmodel.GenPackage;
import org.eclipse.emf.codegen.ecore.genmodel.impl.GenClassImpl;
import org.eclipse.emf.codegen.ecore.genmodel.impl.GenEnumImpl;
import org.eclipse.emf.codegen.ecore.genmodel.impl.GenEnumLiteralImpl;
import org.eclipse.emf.codegen.ecore.genmodel.impl.GenFeatureImpl;
import org.eclipse.emf.codegen.ecore.genmodel.impl.GenOperationImpl;
import org.eclipse.emf.codegen.ecore.genmodel.impl.GenPackageImpl;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
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
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.FileEditorInput;

import de.tud.et.ifa.agtele.ui.util.UIHelper;

/**
 * An {@link IHandler} that, based on a selection in an Ecore model or GenModel,
 * opens an associated Java file.
 * <p />
 * Clients can overwrite {@link #getAllowedElementTypes()} to restrict the list
 * of types on which the handler may be executed.
 * <p />
 * Clients can specify {@link #getDirectory(GenBase)} and
 * {@link #getQualifiedClassName(GenBase)} in order to customize the concrete
 * Java class to be opened.
 *
 * @author mfreund
 */
public abstract class OpenCodeHandler extends AbstractHandler {

	/**
	 * The resource set that we use to load GenModels if necessary.
	 * <p />
	 * By using a separate resource set, potential errors that occur during the
	 * loading of GenModels are not reflected in the resource set of the
	 * original editor.
	 * <p />
	 * By using a static resource set, resources will not have to be loaded
	 * again and again (on every right click).
	 */
	private static ResourceSet resourceSet = new ResourceSetImpl();

	/**
	 * The list of types on which we allow this handler to be executed.
	 */
	private static final List<Class<?>> allowedElementTypes = Arrays.asList(EPackageImpl.class, EClassImpl.class,
			EOperationImpl.class, EAttributeImpl.class, EReferenceImpl.class, EEnumImpl.class, EEnumLiteralImpl.class,
			GenPackageImpl.class, GenClassImpl.class, GenOperationImpl.class, GenFeatureImpl.class, GenEnumImpl.class,
			GenEnumLiteralImpl.class);

	/**
	 * This returns the qualified name of the Java class to open for the given
	 * {@link GenBase GenModel element}.
	 *
	 * @param element
	 *            The {@link GenBase GenModel element} for which the Java class
	 *            name shall be returned.
	 * @return The qualified Java class name in the form
	 *         '<em>fully.qualified.ClassName</em>.
	 */
	protected abstract String getQualifiedClassName(GenBase element);

	/**
	 * This returns the path of the directory containing the Java files
	 * specified by {@link #getQualifiedClassName(GenBase)} in a
	 * workspace-relative form (i.e. <em>/project-name/src</em>).
	 *
	 * @param selectedElement
	 *            The {@link GenBase GenModel element} The {@link GenBase
	 *            GenModel element} selected by the user.
	 * @return The path of the directory containing the Java files.
	 */
	protected abstract String getDirectory(GenBase selectedElement);

	/**
	 * This returns the list of types on which we allow this handler to be
	 * executed.
	 *
	 * @return The list of types on which we allow this handler to be executed.
	 */
	protected List<Class<?>> getAllowedElementTypes() {
		return OpenCodeHandler.allowedElementTypes;
	}

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {

		if (!(UIHelper.getCurrentEditorInput() instanceof FileEditorInput)) {
			this.showError("Unable to determine associated model code");
			return null;
		}

		// The GenModel element that was selected by the user or the
		// corresponding GenModel element if an Ecore model element was selected
		//
		GenBase selectedElement = UIHelper.getFirstSelection() instanceof GenBase
				? (GenBase) UIHelper.getFirstSelection()
				: this.getGenModelElement((EObject) UIHelper.getFirstSelection());

		if (selectedElement == null) {
			this.showError("Unable to determine corresponding element in the GenModel");
			return null;
		}

		// The element for that we want to open the Java class
		//
		GenBase elementToOpen = this.getElementToOpen(selectedElement);

		if (!(elementToOpen instanceof GenClass || elementToOpen instanceof GenPackage
				|| elementToOpen instanceof GenEnum)) {
			this.showError("Unable to determine Java class to open");
			return null;
		}

		// Determine the path of the Java class to open
		//
		StringBuilder pathBuilder = new StringBuilder(this.getDirectory(selectedElement)).append(IPath.SEPARATOR)
				.append(this.getQualifiedClassName(elementToOpen).replace(".".charAt(0), IPath.SEPARATOR))
				.append(".java");

		// Find the Java class in the workspace
		//
		IResource javaFile = ResourcesPlugin.getWorkspace().getRoot().findMember(pathBuilder.toString());

		if (!(javaFile instanceof IFile) || !javaFile.exists()) {
			this.showError("File '" + pathBuilder.toString() + "' does not exist in the workspace!");
			return null;
		}

		// Open the GenModel editor
		//
		try {
			UIHelper.openEditor((IFile) javaFile);
		} catch (PartInitException e) {
			this.showError("Error while opening the Java editor");
			return null;
		}

		return null;

	}

	@Override
	public boolean isEnabled() {

		if (!(UIHelper.getCurrentEditorInput() instanceof FileEditorInput)) {
			return false;
		}

		if (!(UIHelper.getCurrentSelection() instanceof StructuredSelection)
				|| ((StructuredSelection) UIHelper.getCurrentSelection()).size() != 1) {
			return false;
		}

		Object selection = UIHelper.getFirstSelection();

		if (!this.getAllowedElementTypes().parallelStream().filter(t -> t.isInstance(selection)).findAny()
				.isPresent()) {
			return false;
		}

		GenBase genModelElement = selection instanceof GenBase ? (GenBase) selection
				: this.getGenModelElement((EObject) selection);

		IResource sourceDirectory = ResourcesPlugin.getWorkspace().getRoot()
				.findMember(this.getDirectory(genModelElement));
		if (sourceDirectory == null || !sourceDirectory.exists()) {
			return false;
		}

		GenBase elementToOpen = this.getElementToOpen(genModelElement);

		if (elementToOpen == null || this.getQualifiedClassName(elementToOpen) == null) {
			return false;
		}

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

	/**
	 * Based on the given {@link GenBase GenModel element} that was selected by
	 * the user, this returns the GenModel element based on which the
	 * {@link #getQualifiedClassName(GenBase) Java class to be opened} will be
	 * determined.
	 *
	 * @param selectedElement
	 *            The {@link GenBase GenModel} element that was selected by the
	 *            user.
	 * @return The {@link GenBase GenModel element} that shall be used as the
	 *         basis for {@link #getQualifiedClassName(GenBase)}.
	 */
	protected GenBase getElementToOpen(GenBase selectedElement) {

		if (selectedElement instanceof GenPackage || selectedElement instanceof GenClass
				|| selectedElement instanceof GenEnum) {
			return selectedElement;
		} else if (selectedElement instanceof GenFeature || selectedElement instanceof GenOperation
				|| selectedElement instanceof GenEnumLiteral) {
			return (GenBase) selectedElement.eContainer();
		} else {
			return null;
		}
	}

	/**
	 * For a given element of an Ecore model, this return the corresponding
	 * GenModel element.
	 *
	 * @param ecoreModelElement
	 *            An {@link EObject element} of an Ecore model.
	 * @return The corresponding {@link GenBase GenModel element} or
	 *         <em>null</em> if no corresponding GenModel or GenModel element
	 *         could be determined.
	 */
	private GenBase getGenModelElement(EObject ecoreModelElement) {

		if (!(EcoreUtil.getRootContainer(ecoreModelElement) instanceof EPackage)
				|| ecoreModelElement.eResource() == null) {
			return null;
		}

		// We assume that the associated GenModel is located in the same folder
		// and has the same base name
		//
		URI genModelURI = ecoreModelElement.eResource().getURI().trimFileExtension().appendFileExtension("genmodel");

		Resource genModelResource = OpenCodeHandler.resourceSet.getResource(genModelURI, true);

		if (genModelResource == null || !genModelResource.getErrors().isEmpty()) {
			return null;
		}

		// As we load the GenModel in a separate resource set, we have to first
		// determine the resource that
		// is equivalent to the resource opened in
		// the Ecore editor
		//
		Resource ecoreResource = OpenCodeHandler.resourceSet.getResource(ecoreModelElement.eResource().getURI(), true);

		if (ecoreResource == null || ecoreResource.getContents().isEmpty()) {
			this.showError("Unable to determine corresponding element in the GenModel");
			return null;
		}

		// Now, we can determine the equivalent elements in both loaded Ecore
		// resources
		//
		String uriFragment = ecoreModelElement.eResource().getURIFragment(ecoreModelElement);
		EObject correspondingElement = ecoreResource.getEObject(uriFragment);

		if (correspondingElement == null) {
			return null;
		}

		// Finally, we can determine the element of the GenModel that
		// describes/references the selected EObject
		//
		Optional<Setting> setting = EcoreUtil.UsageCrossReferencer.find(correspondingElement, genModelResource)
				.parallelStream().filter(s -> s.getEObject() instanceof GenBase
						&& correspondingElement.equals(((GenBase) s.getEObject()).getEcoreModelElement()))
				.findAny();

		return setting.isPresent() ? (GenBase) setting.get().getEObject() : null;
	}

}