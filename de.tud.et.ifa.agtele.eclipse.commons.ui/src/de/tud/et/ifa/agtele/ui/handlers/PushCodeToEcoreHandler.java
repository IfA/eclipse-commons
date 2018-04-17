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

import org.eclipse.emf.codegen.ecore.genmodel.GenModelPackage;
import org.eclipse.emf.common.command.AbstractCommand;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.ETypedElement;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.edit.domain.IEditingDomainProvider;
import org.eclipse.ui.IEditorPart;

import de.tud.et.ifa.agtele.ui.emf.PushCodeToEcoreExecutor;
import de.tud.et.ifa.agtele.ui.emf.PushCodeToEcoreExecutor.PushCodeToEcoreResult;

/**
 * This implementation of the {@link AbstractGeneratedEMFCodeHandler} aims to import a code section to the opened ecore
 * model. Supported sections in the model code implementation classes are:<br/>
 * - feature getter/setter method body: 'get', 'set' genannotation details key</br>
 * - feature basifGetter/basicSetter method body: 'basicGet', 'basicSet' genannotation details key</br>
 * - operation method body: 'body' genannotation details key</br>
 * - attribute field initialization expression: 'init' genannotation details key</br>
 * - eIsSet expression: 'isSet' genannotation details key (the details key is supported by generator templates, but no
 * automatic push to ecore feature is available) Supported sections in the edit code implementation classes are:<br/>
 * - property descriptor add method body: 'propertyDescriptor' genannotation details key</br>
 *
 * In order to get the genmodel annotation details entries to work with the emf java generator (except for the body
 * annotations of eOperations), enable the dynamic templates in the genmodel and point the templates directory property
 * to the templates directory included in the
 *
 * As implementation source, only implementation classes from the emf 'model' ore 'item provider' code are accepted.
 *
 * @author baron, mfreund
 */
public class PushCodeToEcoreHandler extends OpenMetamodelHandler {

	/**
	 * After the handler was {@link #doExecute() executed}, this describes the {@link PushCodeToEcoreResult result} of
	 * the push.
	 */
	protected PushCodeToEcoreResult result;

	/**
	 * Push the code to the metamodel.
	 */
	@Override
	protected void performSyncAction() throws Exception {

		super.performSyncAction();

		// We need to use the ResourceSet of the opened editor for the push to ensure that the correct command stack
		// gets used
		//
		ResourceSet resourceSet = this.ecoreEditor instanceof IEditingDomainProvider
				? ((IEditingDomainProvider) this.ecoreEditor).getEditingDomain().getResourceSet()
				: null;
		this.result = new PushCodeToEcoreExecutor(this.helper, resourceSet).pushToEcore(this.javaSelection);

	}

	/**
	 * Select the target element of the push and show the result in the status bar.
	 */
	@Override
	protected void performAsyncActionOnEcoreEditor() throws Exception {

		super.performAsyncActionOnEcoreEditor();

		if (this.result != null) {
			this.showStatus(this.result.getResult());
		}
	}

	@Override
	protected Object getElementToSelect() throws Exception {

		return this.result != null ? this.result.getTarget() : null;
	}

	/**
	 * Brings the selected editor to front via activating it.
	 *
	 * @param part
	 */
	protected void bringEditorToFront(IEditorPart part) {

		part.getSite().getPage().activate(part);
	}

	@Override
	public boolean isEnabled() {

		if (!super.isEnabled()) {
			return false;
		}

		PushCodeToEcoreExecutor executor = new PushCodeToEcoreExecutor(this.helper, null);

		return executor.isPushable(this.javaSelection);

	}

	/**
	 * This command adds a genmodel annotation with the specified implementation code assigned to the specified details
	 * key.
	 *
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

		public AddImplementationEcoreAnnotationCommand(ETypedElement feature, String keyName, String code) {

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
				this.feature.getEAnnotations().add(this.annotation);
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
		 *
		 * @return
		 */
		public EAnnotation getManipulatedAnnotation() {

			return this.annotation;
		}
	}
}
