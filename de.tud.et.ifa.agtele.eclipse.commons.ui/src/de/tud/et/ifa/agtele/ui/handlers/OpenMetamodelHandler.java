/**
 *
 */
package de.tud.et.ifa.agtele.ui.handlers;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.emf.codegen.ecore.genmodel.GenBase;
import org.eclipse.emf.codegen.ecore.genmodel.GenClass;
import org.eclipse.emf.codegen.ecore.genmodel.GenPackage;
import org.eclipse.emf.common.ui.viewer.IViewerProvider;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.edit.domain.IEditingDomainProvider;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.internal.core.CompilationUnit;
import org.eclipse.jdt.internal.ui.javaeditor.CompilationUnitEditor;
import org.eclipse.jdt.internal.ui.javaeditor.EditorUtility;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.text.TextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.FileEditorInput;

import de.tud.et.ifa.agtele.emf.AgteleEcoreUtil;
import de.tud.et.ifa.agtele.ui.util.UIHelper;

/**
 *
 * @author mfreund
 */
@SuppressWarnings("restriction")
public class OpenMetamodelHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {

		CompilationUnitEditor javaEditor = (CompilationUnitEditor) UIHelper.getCurrentEditor();

		ISelection selection = javaEditor.getSelectionProvider().getSelection();

		// In order to prevent manual parsing of the Java document, we make use
		// of the CompilationUnit type that
		// represents a structured Java document
		//
		CompilationUnit root = (CompilationUnit) EditorUtility.getEditorInputJavaElement(UIHelper.getCurrentEditor(),
				false);

		IJavaElement javaElement = null;
		try {
			javaElement = root.getElementAt(((TextSelection) selection).getOffset());
		} catch (JavaModelException e) {
			e.printStackTrace();
		}

		// The list of currently opened Ecore files (potential targets for our
		// selection)
		//
		List<IFile> ecoreFiles = UIHelper.getAllEditorInputs().stream().filter(
				e -> e instanceof FileEditorInput && ((FileEditorInput) e).getFile().getFileExtension().equals("ecore"))
				.map(e -> ((FileEditorInput) e).getFile()).collect(Collectors.toList());

		IFile ecoreFile = null;
		EObject metamodelElement = null;
		for (IFile file : ecoreFiles) {
			Optional<EObject> element = this.checkIsMetaModelForJavaClass(file, root);
			if (element.isPresent()) {
				ecoreFile = file;
				metamodelElement = element.get();
				break;
			}
		}

		// We found an Ecore file so we open it
		//
		if (ecoreFile == null) {
			this.showError("Unable to determine associated Ecore metamodel!");

			return null;
		}

		IEditorPart ecoreEditor;
		try {
			ecoreEditor = UIHelper.openEditor(ecoreFile);

		} catch (PartInitException e1) {
			e1.printStackTrace();
			this.showError("Unable to open editor for associated Ecore metamodel!");
			return null;
		}

		if (!(ecoreEditor instanceof IEditingDomainProvider)) {
			return null;
		}

		// As the Ecore editor may have used a different resource set to load
		// the Ecore metamodel,
		// we have to first determine the resource that is equivalent to the
		// resource opened in the Ecore editor
		//
		ResourceSet genModelEditorResourceSet = ((IEditingDomainProvider) ecoreEditor).getEditingDomain()
				.getResourceSet();
		Resource ecoreResource = genModelEditorResourceSet.getResource(metamodelElement.eResource().getURI(), true);

		if (ecoreResource == null) {
			return null;
		}

		// Now that we now the equivalent resources, we can determine the
		// equivalent elements
		//
		String uriFragment = metamodelElement.eResource().getURIFragment(metamodelElement);
		EObject correspondingSelection = ecoreResource.getEObject(uriFragment);

		if (correspondingSelection == null) {
			return null;
		}

		// Now, reveal and select the determined element in the Ecore editor
		//
		if (ecoreEditor instanceof IViewerProvider) {
			((IViewerProvider) ecoreEditor).getViewer().setSelection(new StructuredSelection(correspondingSelection));
		}

		return null;
	}

	/**
	 * Check if the Ecore metamodel contained in/defined by the given
	 * {@link IFile} was responsible for creating the given
	 * {@link CompilationUnit Java file}.
	 *
	 * @param ecoreFile
	 *            The {@link IFile} defining the Ecore metamodel to check.
	 * @param javaFile
	 *            The {@link CompilationUnit Java file} to check.
	 * @return The {@link EObject metamodel element} associated with the given
	 *         Java file or an empty optional if the metamodel was not
	 *         responsible for the generation of the Java file.
	 */
	private Optional<EObject> checkIsMetaModelForJavaClass(IFile ecoreFile, CompilationUnit javaFile) {

		ResourceSet resourceSet = new ResourceSetImpl();

		// Load the metamodel
		//
		Resource res = null;
		try {
			res = resourceSet.getResource(URI.createPlatformResourceURI(ecoreFile.getFullPath().toString(), true),
					true);
		} catch (RuntimeException e) {
			e.printStackTrace();
			return Optional.empty();
		}

		if (!(res.getContents().get(0) instanceof EPackage)) {
			return Optional.empty();
		}

		// All EPackages defined by the metamodel
		//
		Collection<EPackage> ePackages = res.getContents().stream().filter(c -> c instanceof EPackage)
				.flatMap(e -> AgteleEcoreUtil.getAllSubPackages((EPackage) e, true).stream())
				.collect(Collectors.toSet());

		String mainTypeName = String.valueOf(javaFile.getMainTypeName());
		JavaFileType type = JavaFileType.getFileType(mainTypeName);

		// The name of the metamodel element we will open
		//
		String metamodelElementName = JavaFileType.getBaseName(mainTypeName);

		if (type.isClassType()) {

			// Collect all eClasses with the given metamodelElementName
			//
			Collection<EClass> eClasses = AgteleEcoreUtil.getAllPackageEClasses(ePackages, metamodelElementName);

			// Check if one of the found eClasses was responsible for generating
			// the
			// given javaFile
			for (EClass eClass : eClasses) {

				Optional<GenBase> genModelElement = AgteleEcoreUtil.getGenModelElement(eClass, resourceSet);
				if (!genModelElement.isPresent() || !(genModelElement.get() instanceof GenClass)) {
					continue;
				}

				GenClass genClass = (GenClass) genModelElement.get();

				if (this.checkGenClassCreatedJavaFile(genClass, javaFile)) {
					return Optional.of(eClass);
				}

			}

		} else if (type.isPackageType()) {

			// Check if one of the found ePackages was responsible for
			// generating the
			// given javaFile
			for (EPackage ePackage : ePackages) {

				Optional<GenBase> genModelElement = AgteleEcoreUtil.getGenModelElement(ePackage, resourceSet);
				if (!genModelElement.isPresent() || !(genModelElement.get() instanceof GenPackage)) {
					continue;
				}

				GenPackage genPackage = (GenPackage) genModelElement.get();

				if (this.checkGenPackageCreatedJavaFile(genPackage, javaFile)) {
					return Optional.of(ePackage);
				}

			}
		}

		return Optional.empty();
	}

	/**
	 * This checks if the given {@link GenClass} was responsible for creating
	 * the given <em>javaFile</em>.
	 *
	 * @param genClass
	 *            The {@link GenClass} to check.
	 * @param javaFile
	 *            The {@link CompilationUnit Java file} to check.
	 * @return '<em>true</em>' if the given <em>javaFile</em> was created based
	 *         on the given {@link GenClass}; '<em>false</em>' otherwise.
	 */
	public boolean checkGenClassCreatedJavaFile(GenClass genClass, CompilationUnit javaFile) {

		String javaFileResource = javaFile.getResource().getFullPath().toString();

		// The type of the given javaFile
		//
		JavaFileType type = JavaFileType.getFileType(String.valueOf(javaFile.getMainTypeName()));

		if (type == null) {
			return false;
		}

		if (type.isModelType()) {

			String modelDirectory = genClass.getGenModel().getModelDirectory();

			if (type.equals(JavaFileType.INTERFACE) && javaFileResource.equals(
					modelDirectory + "/" + this.getJavaPathFromQualifiedName(genClass.getQualifiedInterfaceName()))) {
				return true;
			} else if (type.equals(JavaFileType.IMPL) && javaFileResource.equals(
					modelDirectory + "/" + this.getJavaPathFromQualifiedName(genClass.getQualifiedClassName()))) {
				return true;
			}

		} else if (type.isEditType()) {

			String editDirectory = genClass.getGenModel().getEditDirectory();

			if (type.equals(JavaFileType.ITEMPROVIDER) && javaFileResource.equals(editDirectory + "/"
					+ this.getJavaPathFromQualifiedName(genClass.getQualifiedProviderClassName()))) {
				return true;
			}

		}

		return false;
	}

	/**
	 * This checks if the given {@link GenPackage} was responsible for creating
	 * the given <em>javaFile</em>.
	 *
	 * @param genPackage
	 *            The {@link GenPackage} to check.
	 * @param javaFile
	 *            The {@link CompilationUnit Java file} to check.
	 * @return '<em>true</em>' if the given <em>javaFile</em> was created based
	 *         on the given {@link GenPackage}; '<em>false</em>' otherwise.
	 */
	public boolean checkGenPackageCreatedJavaFile(GenPackage genPackage, CompilationUnit javaFile) {

		String javaFileResource = javaFile.getResource().getFullPath().toString();

		// The type of the given javaFile
		//
		JavaFileType type = JavaFileType.getFileType(String.valueOf(javaFile.getMainTypeName()));

		if (type == null) {
			return false;
		}

		if (type.isModelType()) {

			String modelDirectory = genPackage.getGenModel().getModelDirectory();

			if (type.equals(JavaFileType.FACTORY) && javaFileResource.equals(modelDirectory + "/"
					+ this.getJavaPathFromQualifiedName(genPackage.getQualifiedFactoryInterfaceName()))) {
				return true;
			} else if (type.equals(JavaFileType.PACKAGE) && javaFileResource.equals(modelDirectory + "/"
					+ this.getJavaPathFromQualifiedName(genPackage.getQualifiedPackageInterfaceName()))) {
				return true;
			} else if (type.equals(JavaFileType.FACTORYIMPLY) && javaFileResource.equals(modelDirectory + "/"
					+ this.getJavaPathFromQualifiedName(genPackage.getQualifiedFactoryClassName()))) {
				return true;
			} else if (type.equals(JavaFileType.PACKAGEIMPL) && javaFileResource.equals(modelDirectory + "/"
					+ this.getJavaPathFromQualifiedName(genPackage.getQualifiedPackageClassName()))) {
				return true;
			} else if (type.equals(JavaFileType.ADAPTERFACTORY) && javaFileResource.equals(modelDirectory + "/"
					+ this.getJavaPathFromQualifiedName(genPackage.getQualifiedAdapterFactoryClassName()))) {
				return true;
			} else if (type.equals(JavaFileType.SWITCH) && javaFileResource.equals(modelDirectory + "/"
					+ this.getJavaPathFromQualifiedName(genPackage.getQualifiedSwitchClassName()))) {
				return true;
			} else if (type.equals(JavaFileType.VALIDATOR) && javaFileResource.equals(modelDirectory + "/"
					+ this.getJavaPathFromQualifiedName(genPackage.getQualifiedValidatorClassName()))) {
				return true;
			}

		} else if (type.isEditType()) {

			String editDirectory = genPackage.getGenModel().getEditDirectory();

			if (type.equals(JavaFileType.ITEMPROVIDERADAPTERFACTORY)
					&& javaFileResource.equals(editDirectory + "/" + this.getJavaPathFromQualifiedName(
							genPackage.getQualifiedItemProviderAdapterFactoryClassName()))) {
				return true;
			}

		} else if (type.isEditorType()) {

			String editorDirectory = genPackage.getGenModel().getEditorDirectory();

			if (type.equals(JavaFileType.EDITOR) && javaFileResource.equals(editorDirectory + "/"
					+ this.getJavaPathFromQualifiedName(genPackage.getQualifiedEditorClassName()))) {
				return true;
			} else if (type.equals(JavaFileType.ACTIONBARCONTRIBUTOR) && javaFileResource.equals(editorDirectory + "/"
					+ this.getJavaPathFromQualifiedName(genPackage.getQualifiedActionBarContributorClassName()))) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Computes the path to the Java file for the given qualified name of a Java
	 * class.
	 * <p />
	 * Example: Calling this with 'my.fancy.JavaClass' will return
	 * 'my/fancy/JavaClass.java'.
	 *
	 * @param qualifiedName
	 *            The qualified name of a Java class.
	 * @return The path to the Java file.
	 */
	private String getJavaPathFromQualifiedName(String qualifiedName) {
		return qualifiedName.replaceAll("\\.", "/") + ".java";
	}

	/**
	 * The various types of JavaFiles generated by the GenModel.
	 *
	 * @author mfreund
	 */
	@SuppressWarnings("javadoc")
	public enum JavaFileType {

		// model
		INTERFACE(""), FACTORY("Factory"), PACKAGE("Package"), IMPL("Impl"), FACTORYIMPLY("FactoryImpl"), PACKAGEIMPL(
				"PackageImpl"), ADAPTERFACTORY("AdapterFactory"), SWITCH("Switch"), VALIDATOR("Validator"),
		// edit
		ITEMPROVIDER("ItemProvider"), ITEMPROVIDERADAPTERFACTORY("ItemProviderAdapterFactory"),
		// editor
		EDITOR("Editor"), ACTIONBARCONTRIBUTOR("ActionBarContributor");

		private final String fileEnding;

		JavaFileType(String fileEnding) {
			this.fileEnding = fileEnding;
		}

		/**
		 * Returns the special file ending associated with this
		 * {@link JavaFileType}.
		 *
		 * @return The {@link #fileEnding}.
		 */
		public String getFileEnding() {
			return this.fileEnding;
		}

		/**
		 * Return the {@link JavaFileType} of the java file with the given name.
		 *
		 * @param javaFileName
		 *            The name of a java file (including or excluding the
		 *            trailing '.java').
		 * @return The {@link JavaFileType}.
		 */
		public static JavaFileType getFileType(String javaFileName) {

			String f = javaFileName.replaceAll(".java$", "");

			if (f.endsWith(ACTIONBARCONTRIBUTOR.fileEnding)) {
				return ACTIONBARCONTRIBUTOR;
			} else if (f.endsWith(EDITOR.fileEnding)) {
				return JavaFileType.EDITOR;
			} else if (f.endsWith(ITEMPROVIDERADAPTERFACTORY.fileEnding)) {
				return JavaFileType.ITEMPROVIDERADAPTERFACTORY;
			} else if (f.endsWith(ITEMPROVIDER.fileEnding)) {
				return JavaFileType.ITEMPROVIDER;
			} else if (f.endsWith(VALIDATOR.fileEnding)) {
				return JavaFileType.VALIDATOR;
			} else if (f.endsWith(SWITCH.fileEnding)) {
				return JavaFileType.SWITCH;
			} else if (f.endsWith(ADAPTERFACTORY.fileEnding)) {
				return JavaFileType.ADAPTERFACTORY;
			} else if (f.endsWith(PACKAGEIMPL.fileEnding)) {
				return JavaFileType.PACKAGEIMPL;
			} else if (f.endsWith(FACTORYIMPLY.fileEnding)) {
				return JavaFileType.FACTORYIMPLY;
			} else if (f.endsWith(IMPL.fileEnding)) {
				return JavaFileType.IMPL;
			} else if (f.endsWith(PACKAGE.fileEnding)) {
				return JavaFileType.PACKAGE;
			} else if (f.endsWith(FACTORY.fileEnding)) {
				return JavaFileType.FACTORY;
			} else {
				return JavaFileType.INTERFACE;
			}
		}

		/**
		 * Whether this {@link JavaFileType} represents an element of the
		 * generated 'model' code.
		 *
		 * @return
		 */
		public boolean isModelType() {
			return this.equals(INTERFACE) || this.equals(FACTORY) || this.equals(PACKAGE) || this.equals(IMPL)
					|| this.equals(FACTORYIMPLY) || this.equals(PACKAGEIMPL) || this.equals(ADAPTERFACTORY)
					|| this.equals(SWITCH) || this.equals(VALIDATOR);
		}

		/**
		 * Whether this {@link JavaFileType} represents an element of the
		 * generated 'edit' code.
		 *
		 * @return
		 */
		public boolean isEditType() {
			return this.equals(ITEMPROVIDER) || this.equals(ITEMPROVIDERADAPTERFACTORY);
		}

		/**
		 * Whether this {@link JavaFileType} represents an element of the
		 * generated 'editor' code.
		 *
		 * @return
		 */
		public boolean isEditorType() {
			return this.equals(EDITOR) || this.equals(ACTIONBARCONTRIBUTOR);
		}

		/**
		 * Whether this {@link JavaFileType} represents an element that was
		 * generated based on an {@link EClass}.
		 *
		 * @return
		 */
		public boolean isClassType() {
			return this.equals(INTERFACE) || this.equals(IMPL) || this.equals(ITEMPROVIDER);
		}

		/**
		 * Whether this {@link JavaFileType} represents an element that was
		 * generated based on an {@link EPackage}.
		 *
		 * @return
		 */
		public boolean isPackageType() {
			return !this.isClassType();
		}

		/**
		 * Returns the base of the given <em>javaFileName</em>.
		 *
		 * @param javaFileName
		 *            The name of a Java File.
		 * @return The given <em>javaFileName</em> without any optional trailing
		 *         '.java' and without the special {@link #getFileEnding()} of
		 *         its {@link JavaFileType type}.
		 */
		public static String getBaseName(String javaFileName) {
			JavaFileType type = JavaFileType.getFileType(javaFileName);
			String ret = javaFileName.replaceAll(".java$", "");
			return type == null ? ret : ret.replaceAll(type.getFileEnding() + "$", "");
		}
	}

	@Override
	public boolean isEnabled() {

		// test if the menu is actually shown in an Editor
		if (!(UIHelper.getCurrentEditorInput() instanceof FileEditorInput)) {
			return false;
		}

		// test if the menu is shown in a Java editor
		if (!(UIHelper.getCurrentEditor() instanceof CompilationUnitEditor)) {
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
}
