/**
 *
 */
package de.tud.et.ifa.agtele.ui.handlers;

import java.util.Arrays;
import java.util.List;

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
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.impl.EAttributeImpl;
import org.eclipse.emf.ecore.impl.EClassImpl;
import org.eclipse.emf.ecore.impl.EEnumImpl;
import org.eclipse.emf.ecore.impl.EEnumLiteralImpl;
import org.eclipse.emf.ecore.impl.EOperationImpl;
import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.eclipse.emf.ecore.impl.EReferenceImpl;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.FileEditorInput;

import de.tud.et.ifa.agtele.emf.AgteleEcoreUtil;
import de.tud.et.ifa.agtele.ui.util.UIHelper;

/**
 * An {@link IHandler} that, based on a selection in an Ecore model or GenModel, opens an associated Java file.
 * <p />
 * Clients can overwrite {@link #getAllowedElementTypes()} to restrict the list of types on which the handler may be
 * executed.
 * <p />
 * Clients can specify {@link #getDirectory(GenBase)} and {@link #getQualifiedClassName(GenBase)} in order to customize
 * the concrete Java class to be opened.
 *
 * @author mfreund
 */
public abstract class OpenCodeHandler extends OpenGenModelHandler {

	/**
	 * The resource set that we use to load GenModels if necessary.
	 * <p />
	 * By using a separate resource set, potential errors that occur during the loading of GenModels are not reflected
	 * in the resource set of the original editor.
	 * <p />
	 * By using a static resource set, resources will not have to be loaded again and again (on every right click).
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
	 * The {@link GenBase GenModel element} that was selected by the user or that is equivalent to the user's selection.
	 */
	protected GenBase selectedElement;

	/**
	 * The {@link IEditorPart} used to display the generated code.
	 */
	protected IEditorPart editor;

	/**
	 * This returns the qualified name of the Java class to open for the given {@link GenBase GenModel element}.
	 *
	 * @param element
	 *            The {@link GenBase GenModel element} for which the Java class name shall be returned.
	 * @return The qualified Java class name in the form '<em>fully.qualified.ClassName</em>.
	 */
	protected abstract String getQualifiedClassName(GenBase element);

	/**
	 * This returns the path of the directory containing the Java files specified by
	 * {@link #getQualifiedClassName(GenBase)} in a workspace-relative form (i.e. <em>/project-name/src</em>).
	 *
	 * @param selectedElement
	 *            The {@link GenBase GenModel element} The {@link GenBase GenModel element} selected by the user.
	 * @return The path of the directory containing the Java files.
	 */
	protected abstract String getDirectory(GenBase selectedElement);

	/**
	 * This returns the list of types on which we allow this handler to be executed.
	 *
	 * @return The list of types on which we allow this handler to be executed.
	 */
	@Override
	protected List<Class<?>> getAllowedElementTypes() {

		return OpenCodeHandler.allowedElementTypes;
	}

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {

		if (!(UIHelper.getCurrentEditorInput() instanceof FileEditorInput)) {
			this.showError("Unable to determine associated model code");
			return null;
		}

		Object selection = UIHelper.getFirstSelection();

		// First, we try to determine the corresponding element without manually opening the GenModel editor
		//
		this.selectedElement = selection instanceof GenBase ? (GenBase) selection
				: AgteleEcoreUtil.getGenModelElement((EObject) selection, OpenCodeHandler.resourceSet).orElse(null);

		// However, this sometimes does not work because the GenModel is not always automatically populated with
		// elements that have recently been added to an Ecore metamodel (this is true especially for eOperations). In
		// this case, we need to open the GenModel in an editor (which will generate the necessary missing elements) and
		// determine the corresponding GenModel element via this editor
		if (this.selectedElement == null) {

			this.selectedElement = this.openGenModelAndSelectElement();
		}

		if (this.selectedElement == null) {
			this.showError("Unable to determine corresponding element in the GenModel");
			return null;
		}

		// The element for that we want to open the Java class
		//
		GenBase elementToOpen = this.getElementToOpen(this.selectedElement);

		if (!(elementToOpen instanceof GenClass || elementToOpen instanceof GenPackage
				|| elementToOpen instanceof GenEnum)) {
			this.showError("Unable to determine Java class to open");
			return null;
		}

		// Determine the path of the Java class to open
		//
		StringBuilder pathBuilder = new StringBuilder(this.getDirectory(this.selectedElement)).append(IPath.SEPARATOR)
				.append(this.getQualifiedClassName(elementToOpen).replace(".".charAt(0), IPath.SEPARATOR))
				.append(".java");

		// Find the Java class in the workspace
		//
		IResource javaFile = ResourcesPlugin.getWorkspace().getRoot().findMember(pathBuilder.toString());

		if (!(javaFile instanceof IFile) || !javaFile.exists()) {
			this.showError("File '" + pathBuilder.toString() + "' does not exist in the workspace!");
			return null;
		}

		try {
			this.editor = UIHelper.openEditor((IFile) javaFile);

		} catch (PartInitException e) {
			this.showError("Error while opening the Java editor");
			return null;
		}

		return null;

	}

	/**
	 * Based on the given {@link GenBase GenModel element} that was selected by the user, this returns the GenModel
	 * element based on which the {@link #getQualifiedClassName(GenBase) Java class to be opened} will be determined.
	 *
	 * @param selectedElement
	 *            The {@link GenBase GenModel} element that was selected by the user.
	 * @return The {@link GenBase GenModel element} that shall be used as the basis for
	 *         {@link #getQualifiedClassName(GenBase)}.
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

}