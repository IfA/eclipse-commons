package de.tud.et.ifa.agtele.ui.handlers;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.emf.codegen.ecore.genmodel.GenBase;
import org.eclipse.emf.codegen.ecore.genmodel.GenClass;
import org.eclipse.emf.codegen.ecore.genmodel.GenEnum;
import org.eclipse.emf.codegen.ecore.genmodel.GenOperation;
import org.eclipse.emf.codegen.ecore.genmodel.GenPackage;
import org.eclipse.emf.codegen.ecore.genmodel.GenParameter;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.internal.core.CompilationUnit;
import org.eclipse.jdt.internal.core.SourceType;
import org.eclipse.jdt.internal.ui.javaeditor.CompilationUnitEditor;
import org.eclipse.jdt.internal.ui.javaeditor.EditorUtility;
import org.eclipse.jface.text.TextSelection;

/**
 * An {@link OpenCodeHandler} that opens the generated Java implementation file for the model object.
 *
 * @author mfreund
 */
@SuppressWarnings("restriction")
public class OpenImplModelCodeHandler extends OpenCodeHandler {

	@Override
	protected String getDirectory(GenBase selectedElement) {

		return selectedElement.getGenModel().getModelDirectory();
	}

	@Override
	protected String getQualifiedClassName(GenBase element) {

		if (element instanceof GenClass) {
			return ((EClass) ((GenClass) element).getEcoreModelElement()).isInterface() ? null
					: ((GenClass) element).getQualifiedClassName();
		} else if (element instanceof GenPackage) {
			return ((GenPackage) element).getQualifiedPackageClassName();
		} else if (element instanceof GenEnum) {
			return ((GenEnum) element).getQualifiedInstanceClassName();
		} else {
			return null;
		}
	}

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {

		super.execute(event);

		// After the editor has been opened, we select and reveal the appropriate part of the Java file if a
		// GenOperation was selected
		//

		if (this.editor == null || !(this.editor instanceof CompilationUnitEditor)
				|| ((CompilationUnitEditor) this.editor).getViewer() == null) {
			return null;
		}

		CompilationUnitEditor javaEditor = (CompilationUnitEditor) this.editor;

		// In order to prevent manual parsing of the Java document, we make use of the CompilationUnit type that
		// represents a structured Java document
		//
		CompilationUnit root = (CompilationUnit) EditorUtility.getEditorInputJavaElement(this.editor, false);

		try {

			// This represents the main class inside the Java document
			//
			Optional<IJavaElement> sourceType = Arrays.asList(root.getChildren()).parallelStream()
					.filter(j -> j instanceof SourceType).findFirst();

			if (!sourceType.isPresent()) {
				return null;
			}

			SourceType source = (SourceType) sourceType.get();

			if (this.selectedElement instanceof GenOperation) {
				GenOperation operation = (GenOperation) this.selectedElement;

				// Find an IMethod with the same name and the same parameter names as the GenOperation
				//
				IMethod method = this.findMethod(source, operation);

				if (method != null) {

					// Select and reveal the method
					//
					javaEditor.getSelectionProvider().setSelection(new TextSelection(
							method.getNameRange().getOffset(), method.getNameRange().getLength()));
				}

			}

		} catch (JavaModelException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * For a given {@link SourceType} representing a Java class, this finds and returns an {@link IMethod} that is part
	 * of this class and this equivalent to the given {@link GenOperation}.
	 * <p />
	 * Equivalence currently only checks if the name of the method as well as the names of all parameters match.
	 *
	 * @param sourceType
	 *            The {@link SourceType} representing the Java class for that a method shall be found.
	 * @param operation
	 *            The {@link GenOperation} for that a method shall be found.
	 *
	 * @return An {@link IMethod} that is equivalent to the given {@link GenOperation} or <em>null</em> if no suitable
	 *         method could be found.
	 */
	private IMethod findMethod(SourceType sourceType, GenOperation operation) {

		Optional<IMethod> method;
		try {
			method = Arrays.asList(sourceType.getMethods()).parallelStream().filter(m -> {
				try {
					return m.getElementName().equals(operation.getName())
							&& Arrays.asList(m.getParameters()).parallelStream()
							.allMatch(p -> operation.getGenParameters().stream().map(GenParameter::getName)
									.collect(Collectors.toList()).contains(p.getElementName()));
				} catch (JavaModelException e) {
					e.printStackTrace();
					return false;
				}
			}).findFirst();
		} catch (JavaModelException e) {
			e.printStackTrace();
			return null;
		}

		return method.isPresent() ? method.get() : null;
	}
}
