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

import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.emf.codegen.ecore.genmodel.GenModelPackage;
import org.eclipse.emf.common.command.AbstractCommand;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EModelElement;
import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.part.FileEditorInput;

import de.tud.et.ifa.agtele.emf.AgteleEcoreUtil;
import de.tud.et.ifa.agtele.ui.dialogs.InputDialog;
import de.tud.et.ifa.agtele.ui.dialogs.MultiLineInputDialog;
import de.tud.et.ifa.agtele.ui.util.UIHelper;

public class AddDocumentationAnnotationHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {

		List<EModelElement> targets = ((StructuredSelection) UIHelper.getCurrentSelection()).toList();

		CompoundCommand command = new CompoundCommand();

		boolean singleElement = targets.size() == 1;

		targets = targets.stream().filter(t -> {
			return !(t instanceof EAnnotation);
		}).collect(Collectors.toList());

		if (targets.size() > 0) {

			if (singleElement) {
				String oldDocValue = null;
				EModelElement element = targets.get(0);
				if (element.getEAnnotation(GenModelPackage.eNS_URI) != null
						&& element.getEAnnotation(GenModelPackage.eNS_URI).getDetails().containsKey("documentation")) {
					oldDocValue = element.getEAnnotation(GenModelPackage.eNS_URI).getDetails().get("documentation");
				}
				if (oldDocValue == null) {
					oldDocValue = "";
				}

				String newDocValue = null;

				InputDialog dialog = new MultiLineInputDialog(UIHelper.getShell(), element.eClass().getName()
						+ (element instanceof ENamedElement ? " >" + ((ENamedElement) element).getName() + "<" : ""),
						"Set Documentation String", oldDocValue, null);
				if (dialog.open() == Window.OK) {
					newDocValue = dialog.getValue();
				} else {
					return null;
				}

				if (!oldDocValue.equals(newDocValue)) {
					command.append(new AddDocumentationAnnotationCommand(element, newDocValue));
				} else {
					return null;
				}
			} else {
				targets = targets.stream().filter(t -> {
					return t.getEAnnotation(GenModelPackage.eNS_URI) == null
							|| t.getEAnnotation(GenModelPackage.eNS_URI).getDetails().get("documentation") == null;
				}).collect(Collectors.toList());
				for (EModelElement o : targets) {
					command.append(new AddDocumentationAnnotationCommand(o, null));
				}
			}

			EditingDomain dom = AgteleEcoreUtil.getEditingDomainFor(targets.get(0));
			dom.getCommandStack().execute(command);
		}


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
		if (((StructuredSelection) UIHelper.getCurrentSelection()).toList().stream().allMatch(s -> {
			if (s != null && s instanceof EModelElement && !(s instanceof EAnnotation)) {
				return true;
			}
			return false;
		})) {
			EditingDomain dom = AgteleEcoreUtil.getEditingDomainFor(
					(EObject) ((StructuredSelection) UIHelper.getCurrentSelection()).getFirstElement());

			return dom != null
					&& ((StructuredSelection) UIHelper.getCurrentSelection()).toList().stream().allMatch(s -> {
						return AgteleEcoreUtil.getEditingDomainFor((EObject) s) == dom;
					}) && super.isEnabled();
		}
		return false;
	}

	/**
	 * This command adds a validation EOperation to a specific EClass with the
	 * name of the target to validate. If the EPackage is not enabled to
	 * integrate with the validation framework, a dummy constraint is created
	 * within the EClass specified.
	 *
	 * @author Lukas
	 *
	 */
	public static class AddDocumentationAnnotationCommand extends AbstractCommand {

		protected EModelElement ele;
		protected boolean createDummyConstraint;
		protected boolean createdAnnotation = false;
		protected boolean createdEntry = true;
		protected EAnnotation annotation;
		protected String docString;
		protected String oldDocString;

		public AddDocumentationAnnotationCommand(EModelElement ele, String docString) {
			super();
			this.ele = ele;
			this.docString = docString;
			this.createdAnnotation = ele.getEAnnotation(GenModelPackage.eNS_URI) == null;
			if (!this.createdAnnotation) {
				this.annotation = ele.getEAnnotation(GenModelPackage.eNS_URI);
				this.createdEntry = !this.annotation.getDetails().containsKey("documentation");
				if (!this.createdEntry) {
					this.oldDocString = this.annotation.getDetails().get("documentation");
				}
			}
		}

		@Override
		protected boolean prepare() {
			if (this.createdAnnotation) {
				this.annotation = EcoreFactory.eINSTANCE.createEAnnotation();
				this.annotation.setSource(GenModelPackage.eNS_URI);
			}
			return true;
		}

		@Override
		public void execute() {
			if (this.createdAnnotation) {
				this.ele.getEAnnotations().add(this.annotation);
			}
			if (this.createdEntry) {
				this.annotation.getDetails().put("documentation", this.docString == null ? "" : this.docString);
			} else if (this.docString != null) {
				this.annotation.getDetails().put("documentation", this.docString);
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
			if (this.createdEntry) {
				this.annotation.getDetails().removeKey("documentation");
			} else {
				this.annotation.getDetails().put("documentation", this.oldDocString);
			}
			if (this.createdAnnotation) {
				this.ele.getEAnnotations().remove(this.annotation);
			}
		}
	}	
}
