/**
 * Copyright (c) 2000-2006 IBM Corporation and others.
 * All rights reserved.   This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   IBM - Initial API and implementation
 */
package de.tud.et.ifa.agtele.ui.dialogs;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

/**
 * A simple input dialog for soliciting an input string from the user.
 * <p>
 * This concrete dialog class can be instantiated as is, or further subclassed
 * as required.
 * </p>
 * <p>
 * This class is cloned from <code>org.eclipse.jface.dialogs.InputDialog</code>,
 * with minimal changes required for extensibility. A bugzilla has been filed to
 * request a more flexible implementation. If it is fixed, this class will go
 * away.
 * </p>
 */
public class InputDialog extends Dialog {
	/**
	 * The title of the dialog.
	 */
	protected String title;

	/**
	 * The message to display, or <code>null</code> if none.
	 */
	protected String message;

	/**
	 * The input value; the empty string by default.
	 */
	protected String value = "";//$NON-NLS-1$

	/**
	 * The input validator, or <code>null</code> if none.
	 */
	protected IInputValidator validator;

	/**
	 * OK button widget.
	 */
	protected Button okButton;

	/**
	 * Input text widget.
	 */
	protected Text text;

	/**
	 * Error message label widget.
	 */
	protected Text errorMessageText;

	/**
	 * Error message string.
	 */
	protected String errorMessage;

	/**
	 * Creates an input dialog with OK and Cancel buttons. Note that the dialog
	 * will have no visual representation (no widgets) until it is told to open.
	 * <p>
	 * Note that the <code>open</code> method blocks for input dialogs.
	 * </p>
	 * 
	 * @param parentShell
	 *            the parent shell, or <code>null</code> to create a top-level
	 *            shell
	 * @param dialogTitle
	 *            the dialog title, or <code>null</code> if none
	 * @param dialogMessage
	 *            the dialog message, or <code>null</code> if none
	 * @param initialValue
	 *            the initial input value, or <code>null</code> if none
	 *            (equivalent to the empty string)
	 * @param validator
	 *            an input validator, or <code>null</code> if none
	 */
	public InputDialog(Shell parentShell, String dialogTitle, String dialogMessage, String initialValue,
			IInputValidator validator) {
		super(parentShell);
		this.title = dialogTitle;
		this.message = dialogMessage;
		if (initialValue == null) {
			this.value = "";//$NON-NLS-1$
		} else {
			this.value = initialValue;
		}
		this.validator = validator;
	}

	/*
	 * (non-Javadoc) Method declared on Dialog.
	 */
	@Override
	protected void buttonPressed(int buttonId) {
		if (buttonId == IDialogConstants.OK_ID) {
			this.value = this.text.getText();
		} else {
			this.value = null;
		}
		super.buttonPressed(buttonId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.window.Window#configureShell(org.eclipse.swt.widgets.
	 * Shell)
	 */
	@Override
	protected void configureShell(Shell shell) {
		super.configureShell(shell);
		if (this.title != null) {
			shell.setText(this.title);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.dialogs.Dialog#createButtonsForButtonBar(org.eclipse.
	 * swt.widgets.Composite)
	 */
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		// create OK and Cancel buttons by default
		this.okButton = this.createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL, true);
		this.createButton(parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, false);
		//do this here because setting the text will set enablement on the OK
		// button
		this.text.setFocus();
		if (this.value != null) {
			this.text.setText(this.value);
			this.text.selectAll();
		}
	}

	/*
	 * (non-Javadoc) Method declared on Dialog.
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		// create composite
		Composite composite = (Composite) super.createDialogArea(parent);
		// create message
		if (this.message != null) {
			Label label = new Label(composite, SWT.WRAP);
			label.setText(this.message);
			GridData data = new GridData(GridData.GRAB_HORIZONTAL
					/* | GridData.GRAB_VERTICAL */ | GridData.HORIZONTAL_ALIGN_FILL | GridData.VERTICAL_ALIGN_CENTER);
			data.widthHint = this.convertHorizontalDLUsToPixels(IDialogConstants.MINIMUM_MESSAGE_AREA_WIDTH);
			label.setLayoutData(data);
			label.setFont(parent.getFont());
		}
		/*
		 * text = new Text(composite, SWT.SINGLE | SWT.BORDER);
		 * text.setLayoutData(new GridData(GridData.GRAB_HORIZONTAL |
		 * GridData.HORIZONTAL_ALIGN_FILL));
		 */
		this.text = this.createText(composite);
		this.text.addModifyListener(e -> InputDialog.this.validateInput());
		this.errorMessageText = new Text(composite, SWT.READ_ONLY);
		this.errorMessageText.setLayoutData(new GridData(GridData.GRAB_HORIZONTAL | GridData.HORIZONTAL_ALIGN_FILL));
		this.errorMessageText
				.setBackground(this.errorMessageText.getDisplay().getSystemColor(SWT.COLOR_WIDGET_BACKGROUND));
		// Set the error message text
		// See https://bugs.eclipse.org/bugs/show_bug.cgi?id=66292
		this.setErrorMessage(this.errorMessage);

		Dialog.applyDialogFont(composite);
		return composite;
	}

	/**
	 * Creates the text widget for the user's input.
	 * 
	 * @return the text widget
	 */
	protected Text createText(Composite composite) {
		Text text = new Text(composite, SWT.SINGLE | SWT.BORDER);
		text.setLayoutData(new GridData(GridData.GRAB_HORIZONTAL | GridData.HORIZONTAL_ALIGN_FILL));
		return text;
	}

	/**
	 * Returns the error message label.
	 * 
	 * @return the error message label
	 * @deprecated use setErrorMessage(String) instead
	 */
	@Deprecated
	protected Label getErrorMessageLabel() {
		return null;
	}

	/**
	 * Returns the OK button.
	 * 
	 * @return the OK button
	 */
	protected Button getOkButton() {
		return this.okButton;
	}

	/**
	 * Returns the text area.
	 * 
	 * @return the text area
	 */
	protected Text getText() {
		return this.text;
	}

	/**
	 * Returns the validator.
	 * 
	 * @return the validator
	 */
	protected IInputValidator getValidator() {
		return this.validator;
	}

	/**
	 * Returns the string typed into this input dialog.
	 * 
	 * @return the input string
	 */
	public String getValue() {
		return this.value;
	}

	/**
	 * Validates the input.
	 * <p>
	 * The default implementation of this framework method delegates the request
	 * to the supplied input validator object; if it finds the input invalid,
	 * the error message is displayed in the dialog's message line. This hook
	 * method is called whenever the text changes in the input field.
	 * </p>
	 */
	protected void validateInput() {
		String errorMessage = null;
		if (this.validator != null) {
			errorMessage = this.validator.isValid(this.text.getText());
		}
		// Bug 16256: important not to treat "" (blank error) the same as null
		// (no error)
		this.setErrorMessage(errorMessage);
	}

	/**
	 * Sets or clears the error message. If not <code>null</code>, the OK button
	 * is disabled.
	 * 
	 * @param errorMessage
	 *            the error message, or <code>null</code> to clear
	 * @since 3.0
	 */
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
		if (this.errorMessageText != null && !this.errorMessageText.isDisposed()) {
			this.errorMessageText.setText(errorMessage == null ? "" : errorMessage); //$NON-NLS-1$
			this.errorMessageText.getParent().update();
			// Access the OK button by id, in case clients have overridden button creation.
			// See https://bugs.eclipse.org/bugs/show_bug.cgi?id=113643
			Control button = this.getButton(IDialogConstants.OK_ID);
			if (button != null) {
				button.setEnabled(errorMessage == null);
			}
		}
	}
}

