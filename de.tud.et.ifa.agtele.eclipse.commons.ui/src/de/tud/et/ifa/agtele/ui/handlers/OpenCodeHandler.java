/**
 *
 */
package de.tud.et.ifa.agtele.ui.handlers;

import java.util.Arrays;
import java.util.List;

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
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.FileEditorInput;

import de.tud.et.ifa.agtele.ui.util.UIHelper;

/**
 * An {@link IHandler} that, based on a selection in a GenModel, opens an associated Java file.
 * <p />
 * Clients can specify {@link #getDirectory(GenBase)} and {@link #getQualifiedClassName(GenBase)} in order to customize
 * the concrete Java class to be opened.
 *
 * @author mfreund
 */
public abstract class OpenCodeHandler extends AbstractHandler {

	/**
	 * The list of types on which we allow this handler to be executed.
	 */
	protected static final List<Class<?>> allowedElementTypes = Arrays.asList(GenPackageImpl.class, GenClassImpl.class,
			GenOperationImpl.class, GenFeatureImpl.class, GenEnumImpl.class, GenEnumLiteralImpl.class);

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

	/** This creates an instance.
	 *
	 */
	public OpenCodeHandler() {
		super();
	}

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {

		if (!(UIHelper.getCurrentEditorInput() instanceof FileEditorInput)) {
			this.showError("Unable to determine associated model code");
			return null;
		}

		// Determine the EObject currently selected in the Ecore editor
		//
		if (!(UIHelper.getFirstSelection() instanceof GenBase)) {
			this.showError("Unable to determine current selection in the GenModel editor");
			return null;
		}

		// The element that was selected by the user
		//
		GenBase selectedElement = (GenBase) UIHelper.getFirstSelection();

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
		StringBuilder pathBuilder = new StringBuilder(this.getDirectory(selectedElement))
				.append(IPath.SEPARATOR)
				.append(this.getQualifiedClassName(elementToOpen).replace(".".charAt(0), IPath.SEPARATOR))
				.append(".java");

		// Find the Java class in the workspace
		//
		IResource javaFile = ResourcesPlugin.getWorkspace().getRoot()
				.findMember(pathBuilder.toString());

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

		if (!OpenCodeHandler.allowedElementTypes.parallelStream().filter(t -> t.isInstance(selection)).findAny().isPresent()) {
			return false;
		}

		IResource sourceDirectory = ResourcesPlugin.getWorkspace().getRoot()
				.findMember(this.getDirectory((GenBase) selection));
		if (sourceDirectory == null || !sourceDirectory.exists()) {
			return false;
		}

		if (this.getQualifiedClassName((GenBase) selection) == null) {
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