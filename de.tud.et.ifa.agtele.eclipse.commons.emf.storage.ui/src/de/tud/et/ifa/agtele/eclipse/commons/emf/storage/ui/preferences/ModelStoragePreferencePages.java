package de.tud.et.ifa.agtele.eclipse.commons.emf.storage.ui.preferences;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import de.tud.et.ifa.agtele.eclipse.commons.emf.storage.ui.EMFStorageUIPlugin;

import org.eclipse.jface.preference.*;

public class ModelStoragePreferencePages extends PreferencePage implements IWorkbenchPreferencePage {

	protected IPreferenceStore store;
	private Composite composite;

	public ModelStoragePreferencePages() {
		super("EMF Storage Settings");
		this.store = EMFStorageUIPlugin.getPlugin().getPreferenceStore();
		setDescription("Use sub preference pages to issue different settings to the AgTele EMF Model Storage Plugin.");		
	}
	
	/**
	 * Initialize the preference page.
	 */
	public void init(IWorkbench workbench) {
		// Initialize the preference page
	}

	@Override
	protected Control createContents(Composite parent) {
		composite = new Composite(parent, SWT.NULL);
		composite.setLayout(new GridLayout(4, false));
		
		return composite;
	}
}
