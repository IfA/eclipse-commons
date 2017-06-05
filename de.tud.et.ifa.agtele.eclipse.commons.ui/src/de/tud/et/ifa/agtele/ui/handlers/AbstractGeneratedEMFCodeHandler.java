package de.tud.et.ifa.agtele.ui.handlers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.codegen.ecore.genmodel.GenClass;
import org.eclipse.emf.codegen.ecore.genmodel.GenModel;
import org.eclipse.emf.codegen.ecore.genmodel.GenPackage;
import org.eclipse.emf.common.ui.viewer.IViewerProvider;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.edit.domain.IEditingDomainProvider;
import org.eclipse.jdt.core.IField;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.internal.core.CompilationUnit;
import org.eclipse.jdt.internal.ui.javaeditor.CompilationUnitEditor;
import org.eclipse.jdt.internal.ui.javaeditor.EditorUtility;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.StatusDialog;
import org.eclipse.jface.text.TextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.statushandlers.StatusManager;

import de.tud.et.ifa.agtele.emf.AgteleEcoreUtil;
import de.tud.et.ifa.agtele.resources.ResourceHelper;
import de.tud.et.ifa.agtele.ui.editors.AgteleEcoreEditor;
import de.tud.et.ifa.agtele.ui.util.UIHelper;

/**
 * An {@link AbstractHandler} that, based on a selection in a Java file, opens
 * an associated Ecore metamodel.
 * @author mfreund
 *
 */
//TODO this class has to be refactored, there is too much dirtywork in order not to put any state into the single instance of this class. It would be better to create an 'executor' instance each time the handler is being executed
@SuppressWarnings("restriction")
public abstract class AbstractGeneratedEMFCodeHandler extends AbstractHandler {

	/**
	 * The various types of JavaFiles generated by the GenModel.
	 *
	 * @author mfreund
	 */
	@SuppressWarnings("javadoc")
	public enum EMFGeneratedJavaFileType {
	
		// model
		INTERFACE(""), FACTORY("Factory"), PACKAGE("Package"), IMPL("Impl"), FACTORYIMPLY("FactoryImpl"), PACKAGEIMPL(
				"PackageImpl"), ADAPTERFACTORY("AdapterFactory"), SWITCH("Switch"), VALIDATOR("Validator"),
		// edit
		ITEMPROVIDER("ItemProvider"), ITEMPROVIDERADAPTERFACTORY("ItemProviderAdapterFactory"),
		// editor
		EDITOR("Editor"), ACTIONBARCONTRIBUTOR("ActionBarContributor");
	
		private final String fileEnding;
	
		EMFGeneratedJavaFileType(String fileEnding) {
			this.fileEnding = fileEnding;
		}
	
		/**
		 * Returns the special file ending associated with this
		 * {@link EMFGeneratedJavaFileType}.
		 *
		 * @return The {@link #fileEnding}.
		 */
		public String getFileEnding() {
			return this.fileEnding;
		}
	
		/**
		 * Return the {@link EMFGeneratedJavaFileType} of the java file with the given name.
		 *
		 * @param javaFileName
		 *            The name of a java file (including or excluding the
		 *            trailing '.java').
		 * @return The {@link EMFGeneratedJavaFileType}.
		 */
		public static EMFGeneratedJavaFileType getFileType(String javaFileName) {
	
			String f = javaFileName.replaceAll(".java$", "");
	
			if (f.endsWith(ACTIONBARCONTRIBUTOR.fileEnding)) {
				return ACTIONBARCONTRIBUTOR;
			} else if (f.endsWith(EDITOR.fileEnding)) {
				return EMFGeneratedJavaFileType.EDITOR;
			} else if (f.endsWith(ITEMPROVIDERADAPTERFACTORY.fileEnding)) {
				return EMFGeneratedJavaFileType.ITEMPROVIDERADAPTERFACTORY;
			} else if (f.endsWith(ITEMPROVIDER.fileEnding)) {
				return EMFGeneratedJavaFileType.ITEMPROVIDER;
			} else if (f.endsWith(VALIDATOR.fileEnding)) {
				return EMFGeneratedJavaFileType.VALIDATOR;
			} else if (f.endsWith(SWITCH.fileEnding)) {
				return EMFGeneratedJavaFileType.SWITCH;
			} else if (f.endsWith(ADAPTERFACTORY.fileEnding)) {
				return EMFGeneratedJavaFileType.ADAPTERFACTORY;
			} else if (f.endsWith(PACKAGEIMPL.fileEnding)) {
				return EMFGeneratedJavaFileType.PACKAGEIMPL;
			} else if (f.endsWith(FACTORYIMPLY.fileEnding)) {
				return EMFGeneratedJavaFileType.FACTORYIMPLY;
			} else if (f.endsWith(IMPL.fileEnding)) {
				return EMFGeneratedJavaFileType.IMPL;
			} else if (f.endsWith(PACKAGE.fileEnding)) {
				return EMFGeneratedJavaFileType.PACKAGE;
			} else if (f.endsWith(FACTORY.fileEnding)) {
				return EMFGeneratedJavaFileType.FACTORY;
			} else {
				return EMFGeneratedJavaFileType.INTERFACE;
			}
		}
	
		/**
		 * Whether this {@link EMFGeneratedJavaFileType} represents an element of the
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
		 * Whether this {@link EMFGeneratedJavaFileType} represents an element of the
		 * generated 'edit' code.
		 *
		 * @return
		 */
		public boolean isEditType() {
			return this.equals(ITEMPROVIDER) || this.equals(ITEMPROVIDERADAPTERFACTORY);
		}
	
		/**
		 * Whether this {@link EMFGeneratedJavaFileType} represents an element of the
		 * generated 'editor' code.
		 *
		 * @return
		 */
		public boolean isEditorType() {
			return this.equals(EDITOR) || this.equals(ACTIONBARCONTRIBUTOR);
		}
	
		/**
		 * Whether this {@link EMFGeneratedJavaFileType} represents an element that was
		 * generated based on an {@link EClass}.
		 *
		 * @return
		 */
		public boolean isClassType() {
			return this.equals(INTERFACE) || this.equals(IMPL) || this.equals(ITEMPROVIDER);
		}

		/**
		 * Whether this {@link EMFGeneratedJavaFileType} represents a class implementation within the
		 * generated 'model' code.
		 *
		 * @return
		 */
		public boolean isClassImplementationType() {
			return this.equals(IMPL);
		}
		
		/**
		 * Whether this {@link EMFGeneratedJavaFileType} represents an element that was
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
		 *         its {@link EMFGeneratedJavaFileType type}.
		 */
		public static String getBaseName(String javaFileName) {
			EMFGeneratedJavaFileType type = EMFGeneratedJavaFileType.getFileType(javaFileName);
			String ret = javaFileName.replaceAll(".java$", "");
			return type == null ? ret : ret.replaceAll(type.getFileEnding() + "$", "");
		}
	}

	public AbstractGeneratedEMFCodeHandler() {
		super();
	}

	/**
	 * Recursively collects all GenModel ({@link IFile IFiles} with the file
	 * ending '.genmodel') in the given {@link IContainer}.
	 *
	 * @param container
	 *            The {@link IContainer} to recursively process.
	 * @return The list of {@link IFile IFiles} representing a GenModel.
	 */
	protected Set<IFile> collectGenModels(IContainer container) {
	
		Set<IFile> ret = new HashSet<>();
	
		try {
			List<IResource> members = Arrays.asList(container.members());
	
			for (IResource member : members) {
				if (member instanceof IFile && ((IFile) member).getName().endsWith(".genmodel")) {
					ret.add((IFile) member);
				} else if (member instanceof IContainer) {
					ret.addAll(this.collectGenModels((IContainer) member));
				}
			}
	
		} catch (CoreException e) {
			e.printStackTrace();
		}
	
		return ret;
	}

	/**
	 * Check if the {@link GenModel} defined by the given {@link IFile} was
	 * responsible for creating the given {@link CompilationUnit Java file}.
	 *
	 * @param genModelFile
	 *            The {@link IFile} defining the {@link GenModel} to check.
	 * @param javaFile
	 *            The {@link CompilationUnit Java file} to check.
	 * @return The {@link EObject metamodel element} associated with the given
	 *         Java file or an empty optional if the metamodel was not
	 *         responsible for the generation of the Java file.
	 */
	protected Optional<EObject> checkIsGenModelForJavaClass(IFile genModelFile, CompilationUnit javaFile) {
	
		ResourceSet resourceSet = new ResourceSetImpl();
	
		// Load the GenModel
		//
		Resource res = null;
		try {
			res = resourceSet.getResource(URI.createPlatformResourceURI(genModelFile.getFullPath().toString(), true),
					true);
		} catch (RuntimeException e) {
			e.printStackTrace();
			return Optional.empty();
		}
	
		if (!(res.getContents().get(0) instanceof GenModel)) {
			return Optional.empty();
		}
	
		// All GenPackages defined in the GenModel
		//
		Collection<GenPackage> genPackages = ((GenModel) res.getContents().get(0)).getGenPackages().stream()
				.flatMap(e -> AgteleEcoreUtil.getAllSubPackages(e, true).stream()).collect(Collectors.toSet());
	
		String mainTypeName = String.valueOf(javaFile.getMainTypeName());
		EMFGeneratedJavaFileType type = EMFGeneratedJavaFileType.getFileType(mainTypeName);
	
		// The name of the metamodel element we will open
		//
		String metamodelElementName = EMFGeneratedJavaFileType.getBaseName(mainTypeName);
	
		if (type.isClassType()) {
	
			// Collect all genClasses with the given metamodelElementName
			//
			Collection<GenClass> genClasses = AgteleEcoreUtil.getAllGenPackageGenClasses(genPackages,
					metamodelElementName);
	
			// Check if one of the found genClasses was responsible for
			// generating
			// the given javaFile
			//
			for (GenClass genClass : genClasses) {
	
				if (this.checkGenClassCreatedJavaFile(genClass, javaFile)) {
					return Optional.of(genClass.getEcoreClass());
				}
	
			}
	
		} else if (type.isPackageType()) {
	
			// Check if one of the found genPackages was responsible for
			// generating the given javaFile
			//
			for (GenPackage genPackage : genPackages) {
	
				if (this.checkGenPackageCreatedJavaFile(genPackage, javaFile)) {
					return Optional.of(genPackage.getEcorePackage());
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
		EMFGeneratedJavaFileType type = EMFGeneratedJavaFileType.getFileType(String.valueOf(javaFile.getMainTypeName()));
	
		if (type == null) {
			return false;
		}
	
		if (type.isModelType()) {
	
			String modelDirectory = genClass.getGenModel().getModelDirectory();
	
			if (type.equals(EMFGeneratedJavaFileType.INTERFACE) && javaFileResource.equals(
					modelDirectory + "/" + this.getJavaPathFromQualifiedName(genClass.getQualifiedInterfaceName()))) {
				return true;
			} else if (type.equals(EMFGeneratedJavaFileType.IMPL) && javaFileResource.equals(
					modelDirectory + "/" + this.getJavaPathFromQualifiedName(genClass.getQualifiedClassName()))) {
				return true;
			}
	
		} else if (type.isEditType()) {
	
			String editDirectory = genClass.getGenModel().getEditDirectory();
	
			if (type.equals(EMFGeneratedJavaFileType.ITEMPROVIDER) && javaFileResource.equals(editDirectory + "/"
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
		EMFGeneratedJavaFileType type = EMFGeneratedJavaFileType.getFileType(String.valueOf(javaFile.getMainTypeName()));
	
		if (type == null) {
			return false;
		}
	
		if (type.isModelType()) {
	
			String modelDirectory = genPackage.getGenModel().getModelDirectory();
	
			if (type.equals(EMFGeneratedJavaFileType.FACTORY) && javaFileResource.equals(modelDirectory + "/"
					+ this.getJavaPathFromQualifiedName(genPackage.getQualifiedFactoryInterfaceName()))) {
				return true;
			} else if (type.equals(EMFGeneratedJavaFileType.PACKAGE) && javaFileResource.equals(modelDirectory + "/"
					+ this.getJavaPathFromQualifiedName(genPackage.getQualifiedPackageInterfaceName()))) {
				return true;
			} else if (type.equals(EMFGeneratedJavaFileType.FACTORYIMPLY) && javaFileResource.equals(modelDirectory + "/"
					+ this.getJavaPathFromQualifiedName(genPackage.getQualifiedFactoryClassName()))) {
				return true;
			} else if (type.equals(EMFGeneratedJavaFileType.PACKAGEIMPL) && javaFileResource.equals(modelDirectory + "/"
					+ this.getJavaPathFromQualifiedName(genPackage.getQualifiedPackageClassName()))) {
				return true;
			} else if (type.equals(EMFGeneratedJavaFileType.ADAPTERFACTORY) && javaFileResource.equals(modelDirectory + "/"
					+ this.getJavaPathFromQualifiedName(genPackage.getQualifiedAdapterFactoryClassName()))) {
				return true;
			} else if (type.equals(EMFGeneratedJavaFileType.SWITCH) && javaFileResource.equals(modelDirectory + "/"
					+ this.getJavaPathFromQualifiedName(genPackage.getQualifiedSwitchClassName()))) {
				return true;
			} else if (type.equals(EMFGeneratedJavaFileType.VALIDATOR) && javaFileResource.equals(modelDirectory + "/"
					+ this.getJavaPathFromQualifiedName(genPackage.getQualifiedValidatorClassName()))) {
				return true;
			}
	
		} else if (type.isEditType()) {
	
			String editDirectory = genPackage.getGenModel().getEditDirectory();
	
			if (type.equals(EMFGeneratedJavaFileType.ITEMPROVIDERADAPTERFACTORY)
					&& javaFileResource.equals(editDirectory + "/" + this.getJavaPathFromQualifiedName(
							genPackage.getQualifiedItemProviderAdapterFactoryClassName()))) {
				return true;
			}
	
		} else if (type.isEditorType()) {
	
			String editorDirectory = genPackage.getGenModel().getEditorDirectory();
	
			if (type.equals(EMFGeneratedJavaFileType.EDITOR) && javaFileResource.equals(editorDirectory + "/"
					+ this.getJavaPathFromQualifiedName(genPackage.getQualifiedEditorClassName()))) {
				return true;
			} else if (type.equals(EMFGeneratedJavaFileType.ACTIONBARCONTRIBUTOR) && javaFileResource.equals(editorDirectory + "/"
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
	 * Based on the given {@link EObject elementToSelect} that corresponds to
	 * the given {@link CompilationUnit Java file} and on the
	 * {@link IJavaElement} that the user selected in this file, we check if
	 * there is a more specialized element to select.
	 * <p />
	 * E.g.: If the user selected an {@link EOperation}, we may reveal and
	 * select this instead of the containing {@link EClass}.
	 *
	 * @param elementToSelect
	 *            The {@link EClass} or {@link EPackage} that is associated with
	 *            the given {@link CompilationUnit Java file}.
	 * @param javaFile
	 *            The {@link CompilationUnit Java file} based on which the user
	 *            launch this handler.
	 * @param javaElement
	 *            The {@link IJavaElement element} of the given Java file that
	 *            was selected by the user when launching this handler.
	 * @return A more specialized {@link EObject element to select} or the given
	 *         <em>elementToSelect</em> if no more specialized element could be
	 *         determined.
	 */
	protected EObject getMoreSpecificSelection(EObject elementToSelect, CompilationUnit javaFile, IJavaElement javaElement) {
	
		// If the user selected an EPackage, we cannot select anything more
		// specialized.
		//
		if (!(elementToSelect instanceof EClass)) {
			return elementToSelect;
		}
	
		EClass eClass = (EClass) elementToSelect;
	
		if (javaElement instanceof IMethod) {
	
			String methodName = javaElement.getElementName();
	
			// Check if there is an EOperation corresponding to the selection
			//
			Optional<EOperation> eOperation = eClass.getEOperations().stream()
					.filter(o -> methodName.equals(o.getName())).findAny();
			if (eOperation.isPresent()) {
				return eOperation.get();
			}
	
			// Check if there is an EAttribute or EReference corresponding to
			// the selection
			//
			Optional<EStructuralFeature> eFeature = eClass.getEAllStructuralFeatures().stream()
					.filter(o -> methodName.equalsIgnoreCase("get" + o.getName())
							|| methodName.equalsIgnoreCase("set" + o.getName())
							|| methodName.equalsIgnoreCase("add" + o.getName() + "propertydescriptor"))
					.findAny();
			if (eFeature.isPresent()) {
				return eFeature.get();
			}
	
		} else if (javaElement instanceof IField) {
	
			String fieldName = javaElement.getElementName();
	
			// Check if there is an EAttribute or EReference corresponding to
			// the selection
			//
			Optional<EStructuralFeature> eFeature = eClass.getEAllStructuralFeatures().stream()
					.filter(o -> fieldName.equalsIgnoreCase(o.getName())).findAny();
			if (eFeature.isPresent()) {
				return eFeature.get();
			}
		}
	
		return elementToSelect;
	}

	/**
	 * Show an error to the user by sending an error to the status line.
	 *
	 * @param errorMessage
	 *            The message to display to the user.
	 */
	protected void showError(String errorMessage) {
		UIHelper.getCurrentEditor().getEditorSite().getActionBars().getStatusLineManager().setErrorMessage(errorMessage);
	}
	
	/**
	 * Clears the error from the status line.
	 */
	protected void clearError() {
		UIHelper.getCurrentEditor().getEditorSite().getActionBars().getStatusLineManager().setErrorMessage(null);
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
	 * This implementation of the execute method provides the following hooks for subclasses to specify the behavior:
	 * {@link #showError(String)}, {@link #determineAssociatedMetamodelElement(CompilationUnit)},
	 * {@link #openEcoreEditor(IFile)}, {@link #getMoreSpecificSelection(EObject, CompilationUnit, IJavaElement)},
	 * {@link #performAsyncActionOnEcoreEditor(IEditorPart, EObject, ISelection)}
	 */
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
	
		clearError();
		
		IFile ecoreFile = null;
		
		final CompilationUnitEditor javaEditor = (CompilationUnitEditor) UIHelper.getCurrentEditor();
	
		// In order to prevent manual parsing of the Java document, we make use
		// of the CompilationUnit type that
		// represents a structured Java document
		//
		final CompilationUnit root = determineCompilationUnit();
	
		EObject metamodelElement = determineAssociatedMetamodelElement(root);
		
		if (metamodelElement != null) {
			ecoreFile = ResourceHelper.getFileForResource(metamodelElement.eResource());
		}	
		
		// We found an Ecore file so we open it
		//
		if (ecoreFile == null) {
			this.showError("Unable to determine associated Ecore metamodel!");
	
			return null;
		}
			
		IEditorPart ecoreEditor = openEcoreEditor(ecoreFile);
		
		if (ecoreEditor == null) {
			return null;
		}		
	
		// As the Ecore editor may have used a different resource set to load
		// the Ecore metamodel,
		// we have to first determine the resource that is equivalent to the
		// resource opened in the Ecore editor
		//
		ResourceSet ecoreEditorResourceSet = ((IEditingDomainProvider) ecoreEditor).getEditingDomain()
				.getResourceSet();
		Resource ecoreResource = ecoreEditorResourceSet.getResource(metamodelElement.eResource().getURI(), true);
	
		if (ecoreResource == null) {
			return null;
		}
	
		// Now that we know the equivalent resources, we can determine the
		// equivalent elements
		//
		String uriFragment = metamodelElement.eResource().getURIFragment(metamodelElement);
		EObject correspondingSelection = ecoreResource.getEObject(uriFragment);
	
		if (correspondingSelection == null) {
			return null;
		}
	
		// Finally, we check if we want to select a more specialized element
		// (e.g. an EAttribute of an EClass) instead
		//
		final ISelection selection = javaEditor.getSelectionProvider().getSelection();
		
		try {
			IJavaElement javaElement = root.getElementAt(((TextSelection) selection).getOffset());
			if (javaElement != null) {
				correspondingSelection = this.getMoreSpecificSelection(correspondingSelection, root, javaElement);
			}
	
		} catch (JavaModelException e) {
			e.printStackTrace();
		}
	
		// Now, reveal and select the determined element in the Ecore editor
		//
		final EObject specificCorrespondingElement = correspondingSelection;
	
		// If we had to open a new editor instead of being able to reuse an
		// existing one, the editor might not be completely opened yet.
		// Thus, we use an asynchronous runnable to reveal the selection
		UIHelper.getShell().getDisplay().asyncExec(() -> performAsyncActionOnEcoreEditor(ecoreEditor, root, javaEditor, specificCorrespondingElement, selection));
	
		return null;
	}

	/**
	 * Determines the @link {@link org.eclipse.jdt.core.dom.CompilationUnit}, if the current editor is a Java editor.
	 * @return
	 */
	protected CompilationUnit determineCompilationUnit() {
		return (CompilationUnit) EditorUtility.getEditorInputJavaElement(UIHelper.getCurrentEditor(),
				false);
	}
	/**
	 * Determines the @link {@link org.eclipse.jdt.core.dom.CompilationUnit}, if the current editor is a Java editor.
	 * @param part The compilation unit editor part.
	 * @return
	 */
	protected CompilationUnit determineCompilationUnit(IEditorPart part) {
		return (CompilationUnit) EditorUtility.getEditorInputJavaElement(part,
				false);
	}
	
	/**
	 * When the specific Ecore element has been determined, the concrete handler subclass shall determine, what is going to happen.
	 * @param javaEditor 
	 * @param specificEcoreElement
	 */
	abstract protected void performAsyncActionOnEcoreEditor (IEditorPart ecoreEditor, CompilationUnit compilationUnit, CompilationUnitEditor javaEditor, EObject specificEcoreElement, ISelection javaSelection);

	/**
	 * Tries to open the default editor for the specified ecore file. If the opening fails, the method displays an error using {@link #showError(String)}.
	 * The editor is accepted only, if it is an {@link IEditingDomainProvider}. If the default editor is not capable to use, 
	 * the {@link AgteleEcoreEditor} will be opened.
	 * @param ecoreFile
	 * @return the opened ecore editor 
	 */
	protected IEditorPart openEcoreEditor(IFile ecoreFile) {
		IEditorPart ecoreEditor;
		List<IEditorPart> openEditors = UIHelper.getAllEditors();
		try {
			ecoreEditor = UIHelper.openEditor(ecoreFile);	
		} catch (PartInitException e1) {
			e1.printStackTrace();
			this.showError("Unable to open editor for associated Ecore metamodel!");
			return null;
		}
	
		if (!(ecoreEditor instanceof IEditingDomainProvider)) {
			if (!openEditors.contains(ecoreEditor)) {
				ecoreEditor.getEditorSite().getPage().closeEditor(ecoreEditor, false);
			}
			
			try {
				ecoreEditor = UIHelper.openEditor(ecoreFile, "de.tud.et.ifa.agtele.ui.editors.EcoreEditorID");	
			} catch (PartInitException e1) {
				e1.printStackTrace();
				this.showError("Unable to open ecore editor for associated Ecore metamodel!");
				return null;
			}
			if (!(ecoreEditor instanceof AgteleEcoreEditor)) {
				this.showError("Unable to open compatible ecore editor!");
				return null;
			}
		}
		return ecoreEditor;
	}

	/**
	 * Determines a metamodel element for the compilation unit specified. Opened Ecore editors will be preferred over all available genmodels.
	 * @param root The compilation unit to determine the metamodel element for.
	 * @return
	 */
	protected EObject determineAssociatedMetamodelElement(CompilationUnit root) {
		// The list of currently opened Ecore files (potential targets for our
		// selection)
		//
		List<IFile> ecoreFiles = UIHelper.getAllEditorInputs().stream().filter(
				e -> e instanceof FileEditorInput && "ecore".equals(((FileEditorInput) e).getFile().getFileExtension()))
				.map(e -> ((FileEditorInput) e).getFile()).collect(Collectors.toList());
	
		// The list of GenModel files corresponding to the opened Ecore files
		//
		List<IFile> genModelFiles = ecoreFiles.stream()
				.map(e -> e.getProject()
						.findMember(e.getProjectRelativePath().removeFileExtension().addFileExtension("genmodel")))
				.filter(g -> g instanceof IFile).map(g -> (IFile) g).collect(Collectors.toList());
	
		// Check if one of those GenModel files was responsible for generating
		// our Java class
		//
		for (IFile file : genModelFiles) {
			Optional<EObject> element = this.checkIsGenModelForJavaClass(file, root);
			if (element.isPresent()) {
				return element.get();
			}
		}
		// As none of the opened Ecore files seem to match our selection, we now
		// collect all GenModel files present in the workspace and check if one
		// of those represents the Ecore metamodel we want to open
		//
		genModelFiles = new ArrayList<>(this.collectGenModels(ResourcesPlugin.getWorkspace().getRoot()));
		for (IFile file : genModelFiles) {
			Optional<EObject> element = this.checkIsGenModelForJavaClass(file, root);
			if (element.isPresent()) {
				return element.get();
			}
		}
		return null;
	}

}