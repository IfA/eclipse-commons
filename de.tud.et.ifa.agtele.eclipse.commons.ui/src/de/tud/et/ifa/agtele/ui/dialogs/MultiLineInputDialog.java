package de.tud.et.ifa.agtele.ui.dialogs;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class MultiLineInputDialog extends InputDialog {
	public MultiLineInputDialog(Shell parentShell, String title, String message, String initialValue,
			IInputValidator validator) {
		super(parentShell, title, message, initialValue, validator);
		this.setShellStyle(this.getShellStyle() | SWT.RESIZE);
	}

	@Override
	protected Text createText(Composite composite) {
		Text text = new Text(composite, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
		GridData data = new GridData(GridData.FILL_HORIZONTAL | GridData.FILL_VERTICAL);
		data.heightHint = 5 * text.getLineHeight();
		data.widthHint = this.convertHorizontalDLUsToPixels(IDialogConstants.ENTRY_FIELD_WIDTH);
		text.setLayoutData(data);
		return text;
	}
}
