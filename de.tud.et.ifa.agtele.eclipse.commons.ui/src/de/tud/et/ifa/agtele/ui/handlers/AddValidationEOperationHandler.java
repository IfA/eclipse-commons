/*******************************************************************************
 * Copyright (C) 2016-2018 Institute of Automation, TU Dresden.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Institute of Automation, TU Dresden - initial API and implementation
 ******************************************************************************/
package de.tud.et.ifa.agtele.ui.handlers;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.emf.codegen.ecore.genmodel.GenModelPackage;
import org.eclipse.emf.common.command.AbstractCommand;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EGenericType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EParameter;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.part.FileEditorInput;

import de.tud.et.ifa.agtele.emf.AgteleEcoreUtil;
import de.tud.et.ifa.agtele.ui.util.UIHelper;

/**
 * An {@link AbstractHandler} that, based on a selection in an ecore editor, adds a validation eoperation to the ecore model.
 * This handler accepts multiple features or classes to create validation methods for.
 *
 * @author baron
 */
public class AddValidationEOperationHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {

		List<EObject> validationTargets = ((StructuredSelection)UIHelper.getCurrentSelection()).toList();
		
		EditingDomain dom = AgteleEcoreUtil.getEditingDomainFor(validationTargets.get(0));
		CompoundCommand command = new CompoundCommand();
		
		ArrayList<EPackage> validationEnabledEPackages = new ArrayList<>();
		
		for (EObject o : validationTargets) {
			EClass cls;
			String targetName;
			if (o instanceof EClass) {
				cls = (EClass)o;
				targetName = cls.getName();
			} else {
				cls = ((EStructuralFeature) o).getEContainingClass();
				targetName = ((EStructuralFeature) o).getName();
			}
			if (validationEnabledEPackages.contains(cls.getEPackage()) || AgteleEcoreUtil.isValidationEnabledForEPackage(cls.getEPackage())) {
				command.append(new CreateValidationEOperationCommand(cls, false, targetName));
			} else {
				command.append(new CreateValidationEOperationCommand(cls, true, targetName));
				validationEnabledEPackages.add(cls.getEPackage());
			}
		}
		
		dom.getCommandStack().execute(command);
		
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean isEnabled() {

		// test if the menu is actually shown in an Editor
		if (!(UIHelper.getCurrentEditorInput() instanceof FileEditorInput)) {
			return false;
		}
		
		if (!(UIHelper.getCurrentSelection() instanceof StructuredSelection)) {
			return false;
		}
		
		//check if all selected elements are possible validation targets and have the same editing domain
		if (((StructuredSelection)UIHelper.getCurrentSelection()).toList().stream().allMatch(s -> {
			if  (s != null && (s instanceof EClass || s instanceof EReference || s instanceof EAttribute)) {
				if (s instanceof EClass && ((EClass)s).getEPackage() == null) {
					return false;
				}
				if (s instanceof EStructuralFeature && (
						((EStructuralFeature)s).getEContainingClass() == null || 
						((EStructuralFeature)s).getEContainingClass().getEPackage() == null)) {
					return false;
				}
				return true;
			}
			return false;
		})) {
			EditingDomain dom = AgteleEcoreUtil.getEditingDomainFor((EObject)((StructuredSelection)UIHelper.getCurrentSelection()).getFirstElement());
			
			return dom != null &&
					((StructuredSelection)UIHelper.getCurrentSelection()).toList().stream().allMatch(s -> {
						return AgteleEcoreUtil.getEditingDomainFor((EObject)s) == dom;
					}) 
				&& super.isEnabled();
		}
		return false;
	}
	
	/**
	 * This command adds a validation EOperation to a specific EClass with the name of the target to validate. 
	 * If the EPackage is not enabled to integrate with the validation framework, a dummy constraint is created within the EClass specified.
	 * @author Lukas
	 *
	 */
	public static class CreateValidationEOperationCommand extends AbstractCommand {

		protected EClass eClass;
		protected boolean createDummyConstraint;
		protected String targetName;
		protected EAnnotation constraintAnnotation;
		protected EOperation validationEOperation;
		private String operationName;

		public CreateValidationEOperationCommand (EClass eClass, boolean createDummyConstraint, String targetName) {
			super();
			this.eClass = eClass;
			this.createDummyConstraint = createDummyConstraint;
			this.targetName = targetName;
			if (Character.isLowerCase(this.targetName.charAt(0))) {
				this.operationName = String.valueOf(Character.toUpperCase(this.targetName.charAt(0))) + this.targetName.substring(1);
			} else {
				this.operationName = this.targetName;
			}
			this.operationName = "validate" + this.operationName;
		}
		
		@Override
		protected boolean prepare() {
			if (this.createDummyConstraint) {
				if (this.eClass.getEAnnotation(EcorePackage.eNS_URI) == null) {
					this.constraintAnnotation = EcoreFactory.eINSTANCE.createEAnnotation();
					this.constraintAnnotation.setSource(EcorePackage.eNS_URI);
					this.constraintAnnotation.getDetails().put("constraints", "DummyConstraint");
				}
			}
			this.validationEOperation = EcoreFactory.eINSTANCE.createEOperation();
			this.validationEOperation.setName(this.operationName);
			this.validationEOperation.setEType(EcorePackage.Literals.EBOOLEAN);
			EParameter p1 = EcoreFactory.eINSTANCE.createEParameter();
			p1.setName("diagnostics");
			p1.setEType(EcorePackage.Literals.EDIAGNOSTIC_CHAIN);
			this.validationEOperation.getEParameters().add(p1);
			EParameter p2 = EcoreFactory.eINSTANCE.createEParameter();
			p2.setName("context");
			EGenericType g = EcoreFactory.eINSTANCE.createEGenericType();
			g.setEClassifier(EcorePackage.Literals.EMAP);
			g.getETypeArguments().add(EcoreFactory.eINSTANCE.createEGenericType());	
			g.getETypeArguments().add(EcoreFactory.eINSTANCE.createEGenericType());	
			p2.setEType(EcorePackage.Literals.EMAP);
			p2.setEGenericType(g);
			this.validationEOperation.getEParameters().add(p2);
			EAnnotation genAnnotation = EcoreFactory.eINSTANCE.createEAnnotation();
			genAnnotation.setSource(GenModelPackage.eNS_URI);
			genAnnotation.getDetails().put("rename me to body", "generate the model code first, insert the validation method body here");
			this.validationEOperation.getEAnnotations().add(genAnnotation);
			return true;
		}
		
		@Override
		public void execute() {
			if (this.createDummyConstraint) {
				if (this.constraintAnnotation == null) {
					this.eClass.getEAnnotation(EcorePackage.eNS_URI).getDetails().put("constraints", "DummyConstraint");
				} else {
					this.eClass.getEAnnotations().add(this.constraintAnnotation);
				}
			}
			this.eClass.getEOperations().add(this.validationEOperation);			
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
			if (this.createDummyConstraint) {
				if (this.constraintAnnotation == null) {				
					this.eClass.getEAnnotation(EcorePackage.eNS_URI).getDetails().remove("constraints");
				} else {
					this.eClass.getEAnnotations().remove(this.constraintAnnotation);
				}
			}
			this.eClass.getEOperations().remove(this.validationEOperation);
		}
		
		
	}
}
