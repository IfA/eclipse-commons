package de.tud.et.ifa.agtele.ui.handlers;

import java.util.Arrays;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.emf.codegen.ecore.genmodel.GenModel;
import org.eclipse.emf.codegen.ecore.genmodel.GenModelPackage;
import org.eclipse.emf.common.command.AbstractCommand;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.ui.viewer.IViewerProvider;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EGenericType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EParameter;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.ETypedElement;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jdt.core.IImportDeclaration;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.internal.core.CompilationUnit;
import org.eclipse.jdt.internal.core.SourceField;
import org.eclipse.jdt.internal.ui.javaeditor.CompilationUnitEditor;
import org.eclipse.jface.text.TextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IEditorPart;

import de.tud.et.ifa.agtele.emf.AgteleEcoreUtil;
import de.tud.et.ifa.agtele.ui.handlers.AbstractGeneratedEMFCodeHandler.EMFGeneratedJavaFileType;
import de.tud.et.ifa.agtele.ui.util.UIHelper;

/**
 * This implementation of the {@link AbstractGeneratedEMFCodeHandler} aims to import a code section
 * to the opened ecore model. 
 * Supported sections in the model code implementation classes are:<br/>
 *   -  feature getter/setter method body: 'get', 'set' genannotation details key</br>
 *   -  feature basifGetter/basicSetter method body: 'basicGet', 'basicSet' genannotation details key</br>
 *   -  operation method body: 'body' genannotation details key</br>
 *   -  attribute field initialization expression: 'init' genannotation details key</br>
 *   -  eIsSet expression: 'isSet' genannotation details key (the details key is supported by generator templates, but no automatic push to ecore feature is available)
 * Supported sections in the edit code implementation classes are:<br/>
 *   -  property descriptor add method body: 'propertyDescriptor' genannotation details key</br>
 * 
 * In order to get the genmodel annotation details entries to work with the emf java generator (except for the body annotations of eOperations),
 * enable the dynamic templates in the genmodel and point the templates directory property to the templates directory included in the 
 * 
 * As implementation source, only implementation classes from the emf 'model' ore 'item provider' code are accepted.
 * 
 * @author baron
 */
@SuppressWarnings("restriction")
public class PushCodeToEcoreHandler extends AbstractGeneratedEMFCodeHandler {
	
	/**
	 * This method performs the actual push of the source code. The result heavily relies on the correct behavior of the 
	 * {@link #getMoreSpecificSelection(EObject, CompilationUnit, IJavaElement)}.
	 */
	@Override
	protected void performAsyncActionOnEcoreEditor(IEditorPart ecoreEditor, CompilationUnit compilationUnit, CompilationUnitEditor javaEditor, EObject specificEcoreElement,
			ISelection javaSelection) {
		
		IJavaElement javaElement = null;
		try {
			javaElement = compilationUnit.getElementAt(((TextSelection) javaSelection).getOffset());
		} catch (JavaModelException e) {
			e.printStackTrace();
		}
		
		String mainTypeName = String.valueOf(compilationUnit.getMainTypeName());
		EMFGeneratedJavaFileType type = EMFGeneratedJavaFileType.getFileType(mainTypeName);
		
		EditingDomain dom = AgteleEcoreUtil.getEditingDomainFor(specificEcoreElement);

		String detailsKey = null;
		String code = null;
		
		if (type.isClassImplementationType()) {
			if (specificEcoreElement instanceof EOperation) {
				detailsKey = "body";
				IMethod method = ((IMethod)javaElement);
				try {
					code = method.getSource();
				} catch (JavaModelException e) {
					this.bringEditorToFront((IEditorPart) javaEditor);
					e.printStackTrace();
					showError("Could not retrieve method source code.");
					return;
				}
				code = this.compileImplementation(code, compilationUnit, true, false);	
			} else if ((specificEcoreElement instanceof EReference || specificEcoreElement instanceof EAttribute) && javaElement instanceof IMethod) {
				IMethod method = ((IMethod)javaElement);
				try {
					code = method.getSource();
				} catch (JavaModelException e) {
					this.bringEditorToFront((IEditorPart) javaEditor);
					e.printStackTrace();
					showError("Could not retrieve method source code.");
					return;
				}
				String name = method.getElementName();
				if (name.startsWith("set")) {
					detailsKey = "set";
					code = this.compileImplementation(code, compilationUnit, true, false);	
				} else if (name.startsWith("get")) {
					detailsKey = "get";
					code = this.compileImplementation(code, compilationUnit, true, false);	
				} 
	//			//The eIsSet expression cannot be pushed to the ecore, without further work on identifying the ecore element from the selection within the java method
	//			else if (name.startsWith("eIsSet")) {
	//				detailsKey = "isSet";
	//				// determine the expression, it should follow: case --.CLASS_NAME__FEATURE_NAME:\n return 
	//			}		
			} else if (specificEcoreElement instanceof EAttribute && javaElement instanceof SourceField) {
				detailsKey = "init";
				try {
					code = ((SourceField)javaElement).getSource();
				} catch (JavaModelException e) {
					this.bringEditorToFront((IEditorPart) javaEditor);
					e.printStackTrace();
					showError("Could not retrieve field initialization source code.");
					return;
				}
				code = this.compileImplementation(code, compilationUnit, false, true);	
				
			} else {
				this.bringEditorToFront((IEditorPart) javaEditor);
				showError("Could not handle selection or ecore target element, this is probably due to a section of the source code, that cannot be pushed to the ecore model.");
				return;
			}
		} else if (type.isEditItemProviderType()) {
			if (javaElement instanceof IMethod && (
					specificEcoreElement instanceof EAttribute || (specificEcoreElement instanceof EReference && !((EReference)specificEcoreElement).isContainment()))
					) {
				detailsKey = "propertyDescriptor";
				try {
					code = ((IMethod)javaElement).getSource();
				} catch (JavaModelException e) {
					this.bringEditorToFront((IEditorPart) javaEditor);
					e.printStackTrace();
					showError("Could not retrieve add property descriptor source code.");
					return;
				}
				code = this.compileImplementation(code, compilationUnit, true, false);	
			}			
		}
		
		if (detailsKey == null) {
			this.bringEditorToFront((IEditorPart) javaEditor);
			return;
		}
			 
		AddImplementationEcoreAnnotationCommand cmd = new AddImplementationEcoreAnnotationCommand((ETypedElement)specificEcoreElement, detailsKey, code);
		dom.getCommandStack().execute(cmd);
		
		if (ecoreEditor instanceof IViewerProvider) {
			
			//set the selection in the ecore editor either to the details key or the manipulated annotation
			Object target = null;			
			Object entrySet[] = cmd.getManipulatedAnnotation().getDetails().entrySet().toArray();
			if (code != null) {
				target = entrySet[cmd.getManipulatedAnnotation().getDetails().indexOfKey(detailsKey)];
			} else if (cmd.getManipulatedAnnotation() != null) {
				target = cmd.getManipulatedAnnotation();
			} else {
				target = specificEcoreElement;
			}
			
			((IViewerProvider) ecoreEditor).getViewer()
				.setSelection(new StructuredSelection(target), true);
			if (code != null) {
				this.showStatus("Pushed the java code to the ecore model, to enable getters, setters, initializers or isSet evaluations, use custom emitter templates");
			} else {
				this.showStatus("Cleared the java code from the ecore model. Run the generator in order to get the default implementation.");
			}
		}
	}
		
	/**
	 * Brings the selected editor to front via activating it.
	 * @param part
	 */
	protected void bringEditorToFront(IEditorPart part) {
		part.getSite().getPage().activate(part);
	}
	
	/**
	 * Compiles the java implementation for pushing it to the ecore.
	 * The compilation includes the removal of preceding whitespace and replacing
	 *  unqualified Symbols with their qualified versions.
	 * @param code The code to compile
	 * @param compilationUnit the java compilation unit where the code fragment originates
	 * @return the modified code version
	 */
	protected String compileImplementation (String code, CompilationUnit compilationUnit, boolean isMethod, boolean isInitialization) {
		IImportDeclaration imports[] = null;
		try {
			imports = compilationUnit.getImports();
		} catch (JavaModelException e) {
			e.printStackTrace();
			return code;
		}
		
		//remove leading comments
		code = code.trim();
		while (code.startsWith("/*")) {
			code = code.substring(code.indexOf("*/") + 2).trim();
		}
		
		//get implementation body
		if (isMethod) {
			code = code.substring(code.indexOf("{") + 1, code.lastIndexOf("}")); //do not trim here, we want the whitespace at the beginning of the line
		} else if (isInitialization) {
			if (!code.contains("=")) {
				return null;
			}
			code = code.substring(code.indexOf("=")+1);
		}
		
		//make type Symbols fully qualified
		for (int i = 0; i < imports.length; i += 1) {
			String importDeclaration = imports[i].getElementName();
			if (importDeclaration.endsWith(".*")) {
				continue;
			}
			int lastDotIndex = importDeclaration.lastIndexOf(".");
			String packageName = importDeclaration.substring(0, lastDotIndex);
			String typeName = importDeclaration.substring(lastDotIndex + 1);
			
			code = code.replaceAll("([\\(\\[\\{\\s<])" + typeName + "([\\s\\)>\\.])", "$1<%" + packageName + "." + typeName + "%>$2");
		}
		
		if (isInitialization) {
			return code;
		}
		
		//remove preceding whitespace
		String result = "";
		
		//determine the minimum count of whitespace characters at the beginnint of all lines
		int minSpaceCount = 999999;
		String[] lines;
		String nl;
		if (code.contains("\n\r")) {
			lines = code.split("\n\r");
			nl="\n\r";
		} else if (code.contains("\r\n")) {
			lines = code.split("\r\n");
			nl="\r\n";
		} else if (code.contains("\r")) {
			lines = code.split("\r");
			nl="\r";
		} else {
			lines = code.split("\n");
			nl = "\n";
		}
		if (lines.length <= 1) {
			result = code;
		} else {
			for (int line = 0; line < lines.length; line += 1) {
				String s = lines[line];
				int spaceCount = 0;
				boolean empty = true;
				for (int i=0; i < s.length(); i += 1) {
					if (s.charAt(i) == " ".charAt(0)) {
						spaceCount += 1;
					} else if (s.charAt(i) == "\t".charAt(0)) {
						spaceCount += 4;
					} else if (!Character.isWhitespace(s.charAt(i))){
						empty = false;
						break;
					}					
				}	
				if (!empty && spaceCount < minSpaceCount) {
					minSpaceCount = spaceCount;
				}
			}
		}
		
		//remove a the minimal count of whitespace characters 
		if (minSpaceCount >= 999999) {
			result = code;
		} else {
			for (int line = 0; line < lines.length; line += 1) {
				String s = lines[line];
				int charCount = 0;
				int charWeight = 0;
				for (int i=0; i < s.length(); i += 1) {
					if (s.charAt(i) == " ".charAt(0)) {
						charCount += 1;
						charWeight += 1;
					} else if (s.charAt(i) == "\t".charAt(0)) {
						charCount += 1;
						charWeight += 4;
					} else if (!Character.isWhitespace(s.charAt(i))){
						break;
					}					
					if (charWeight >= minSpaceCount) {
						break;
					}
				}	
				result += (result.length() > 0 ? nl : "") + s.substring(charCount <= s.length() ? charCount : 0);
			}
		}
		
		return result.trim();
	}
	
	
	@Override
	public boolean isEnabled() {
		if (super.isEnabled()) {
			// check if the current compilation unit is a model code implementation		
			CompilationUnit cUnit = determineCompilationUnit();
			String mainTypeName = String.valueOf(cUnit.getMainTypeName());
			EMFGeneratedJavaFileType type = EMFGeneratedJavaFileType.getFileType(mainTypeName);
			
			return type.isClassImplementationType() || type.isEditItemProviderType();
		}
		return false;
		
	}
	
	/**
	 * This command adds a genmodel annotation with the specified implementation code assigned to the specified details key.
	 * @author Lukas
	 *
	 */
	public static class AddImplementationEcoreAnnotationCommand extends AbstractCommand {

		protected ETypedElement feature;
		protected String keyName;
		protected String code;
		protected EAnnotation constraintAnnotation;
		protected EOperation validationEOperation;
		protected String operationName;
		protected EAnnotation annotation;
		protected boolean createdAnnotation = false;
		protected boolean createdDetailsEntry = false;
		protected String previousCode = null;

		public AddImplementationEcoreAnnotationCommand (ETypedElement feature, String keyName, String code) {
			super();
			this.feature = feature;
			this.keyName = keyName;
			this.code = code;
		}
		
		@Override
		protected boolean prepare() {
			this.annotation = this.feature.getEAnnotation(GenModelPackage.eNS_URI);
			if (this.annotation == null && this.code != null) {
				this.annotation = EcoreFactory.eINSTANCE.createEAnnotation();
				this.annotation.setSource(GenModelPackage.eNS_URI);
				this.feature.getEAnnotations().add(annotation);
				this.createdAnnotation = true;
				this.annotation.getDetails().put(this.keyName, this.code);
			} else if (this.annotation != null) {
				this.createdDetailsEntry = !this.annotation.getDetails().containsKey(this.keyName);
				if (!this.createdDetailsEntry) {
					this.previousCode = this.annotation.getDetails().get(this.keyName);
				}
			}

			return this.annotation != null;
		}
		
		@Override
		public void execute() {
			if (this.code == null) {
				if (!this.createdDetailsEntry) {
					this.annotation.getDetails().remove(this.annotation.getDetails().indexOfKey(this.keyName));
				}
			} else if (this.createdAnnotation) {
				this.feature.getEAnnotations().add(this.annotation);
			} else {
				this.annotation.getDetails().put(this.keyName, this.code);
			}
		}

		@Override
		public void redo() {
			this.execute();			
		}	
		
		@Override
		public boolean canExecute() {
			return this.prepare();
		}
		@Override
		public boolean canUndo() {
			return true;
		}
		
		@Override
		public void undo() {
			if (this.code == null) {
				if (!this.createdAnnotation && this.previousCode != null) {
					this.annotation.getDetails().put(this.keyName, this.previousCode);
				}
			} else if (this.createdAnnotation) {
				this.feature.getEAnnotations().remove(this.annotation);
			} else if (this.createdDetailsEntry) {
				this.annotation.getDetails().remove(this.annotation.getDetails().indexOfKey(this.keyName));
			} else {
				this.annotation.getDetails().put(this.keyName, this.previousCode);
			}
		}	
		
		/**
		 * Returns the newly created or altered annotation.
		 * @return
		 */
		public EAnnotation getManipulatedAnnotation() {
			return this.annotation;
		}
	}
}
