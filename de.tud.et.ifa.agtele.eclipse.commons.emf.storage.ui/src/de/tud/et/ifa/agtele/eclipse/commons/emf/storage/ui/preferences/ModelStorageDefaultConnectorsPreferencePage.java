package de.tud.et.ifa.agtele.eclipse.commons.emf.storage.ui.preferences;

import org.eclipse.jface.preference.*;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.wb.swt.ResourceManager;
import org.eclipse.ui.IWorkbench;

import de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.importAdapter.ImportAdapterFactory;
import de.tud.et.ifa.agtele.eclipse.commons.emf.storage.ui.EMFStorageUIPlugin;

/**
 * This class represents a preference page that
 * is contributed to the Preferences dialog. By 
 * subclassing <samp>FieldEditorPreferencePage</samp>, we
 * can use the field support built into JFace that allows
 * us to create a page that is small and knows how to 
 * save, restore and apply itself.
 * <p>
 * This page is used to modify preferences only. They
 * are stored in the preference store that belongs to
 * the main plug-in class. That way, preferences can
 * be accessed directly via the preference store.
 */

public class ModelStorageDefaultConnectorsPreferencePage
	extends PreferencePage
	implements IWorkbenchPreferencePage {

	protected IPreferenceStore store;
	protected Text uriEdit;
	protected Combo schemaCombo;
	protected List defaultConnectionList;
	protected Button addButton;
	protected Button removeButton;
	protected Button upButton;
	protected Button downButton;
	protected Composite composite;
	
	public ModelStorageDefaultConnectorsPreferencePage() {
		super();
		this.store = EMFStorageUIPlugin.getPlugin().getPreferenceStore();
		setDescription("Use this preference page to set connections for default OPC UA servers");
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 */
	public void init(IWorkbench workbench) {
		
	}
	
	protected Control createContents(Composite parent) {
		composite = new Composite(parent, SWT.NULL);
		composite.setLayout(new GridLayout(4, false));
		
		Label lblNewLabel = new Label(composite, SWT.NONE);
		lblNewLabel.setText("schema");
		
		schemaCombo = new Combo(composite, SWT.NONE);
		GridData gd_schemaCombo = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_schemaCombo.minimumWidth = 50;
		gd_schemaCombo.widthHint = 55;
		schemaCombo.setLayoutData(gd_schemaCombo);
		
		schemaCombo.setItems(ImportAdapterFactory.eINSTANCE.getRegisteredConnectionSchemes().toArray(new String[ImportAdapterFactory.eINSTANCE.getRegisteredConnectionSchemes().size()]));
		
		Label lblNewLabel_1 = new Label(composite, SWT.NONE);
		lblNewLabel_1.setText("://");
		
		uriEdit = new Text(composite, SWT.BORDER);
		GridData gd_uriEdit = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_uriEdit.minimumWidth = 150;
		uriEdit.setLayoutData(gd_uriEdit);
		
		Composite composite_1 = new Composite(composite, SWT.NONE);
		composite_1.setLayout(new GridLayout(2, false));
		GridData gd_composite_1 = new GridData(SWT.FILL, SWT.FILL, true, true, 4, 1);
		gd_composite_1.heightHint = 37;
		composite_1.setLayoutData(gd_composite_1);
		
		defaultConnectionList = new List(composite_1, SWT.BORDER);
		defaultConnectionList.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		
		defaultConnectionList.addListener(SWT.MouseDoubleClick, new Listener() {
			@Override
			public void handleEvent(Event event) {
				int selectionIndex = defaultConnectionList.getSelectionIndex();
				if (selectionIndex >= 0) {
					String text = defaultConnectionList.getItem(selectionIndex);
					schemaCombo.setText(text.substring(0, text.indexOf("://")));
					uriEdit.setText(text.substring(text.indexOf("://") + 3, text.length()));
				}
			}			
		});
		
		defaultConnectionList.setItems(EMFStorageUIPlugin.getDefaultModelStorageConnectionUris());
		
		Composite composite_2 = new Composite(composite_1, SWT.NONE);
		composite_2.setLayout(new GridLayout(1, false));
		composite_2.setLayoutData(new GridData(SWT.FILL, SWT.TOP, false, false, 1, 1));
		
		addButton = new Button(composite_2, SWT.NONE);
		addButton.setToolTipText("add new connection uri to list");
		addButton.setImage(ResourceManager.getPluginImage("de.tud.et.ifa.agtele.eclipse.commons.ui", "icons/add_obj.gif"));
		addButton.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		addButton.setBounds(0, 0, 75, 25);
		addButton.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				int selectionIndex = defaultConnectionList.getSelectionIndex();
				if (selectionIndex < 0) {
					selectionIndex = defaultConnectionList.getItemCount();
				}
				if (schemaCombo.getText().isEmpty() || uriEdit.getText().isEmpty()) {
					return;
				}
				String newEntry = schemaCombo.getText() + "://" + uriEdit.getText();
				if (defaultConnectionList.indexOf(newEntry) < 0) {
					if (defaultConnectionList.getSelectionIndex() < 0) {
						if (defaultConnectionList.getItemCount() == 0) {
							defaultConnectionList.add(newEntry);
							defaultConnectionList.setSelection(0);	
						} else {
							defaultConnectionList.add(newEntry);
							defaultConnectionList.setSelection(defaultConnectionList.getItemCount() -1);								
						}
					} else {
						defaultConnectionList.add(newEntry, selectionIndex + 1);
						defaultConnectionList.setSelection(defaultConnectionList.getSelectionIndex() + 1);						
					}
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}			
		});
		
		removeButton = new Button(composite_2, SWT.NONE);
		removeButton.setImage(ResourceManager.getPluginImage("de.tud.et.ifa.agtele.eclipse.commons.ui", "icons/remove.png"));
		removeButton.setToolTipText("remove connection uri from list");
		removeButton.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		removeButton.setBounds(0, 0, 75, 25);
		removeButton.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				int selectionIndex = defaultConnectionList.getSelectionIndex();
				if (selectionIndex >= 0) {
					defaultConnectionList.remove(selectionIndex);
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}			
		});
		
		upButton = new Button(composite_2, SWT.NONE);
		upButton.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		upButton.setText("up");
		upButton.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				int selectionIndex = defaultConnectionList.getSelectionIndex();
				if (selectionIndex > 0) {
					String valueUpper = defaultConnectionList.getItem(selectionIndex-1);
					String value = defaultConnectionList.getItem(selectionIndex);
					defaultConnectionList.setItem(selectionIndex -1, value);
					defaultConnectionList.setItem(selectionIndex, valueUpper);
					defaultConnectionList.setSelection(selectionIndex-1);
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}			
		});
		
		downButton = new Button(composite_2, SWT.NONE);
		downButton.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		downButton.setText("down");
		downButton.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				int selectionIndex = defaultConnectionList.getSelectionIndex();
				if (selectionIndex >= 0 && selectionIndex < defaultConnectionList.getItemCount() - 1) {
					String valueBelow = defaultConnectionList.getItem(selectionIndex+1);
					String value = defaultConnectionList.getItem(selectionIndex);
					defaultConnectionList.setItem(selectionIndex + 1, value);
					defaultConnectionList.setItem(selectionIndex, valueBelow);
					defaultConnectionList.setSelection(selectionIndex+1);
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
			
		});
		return composite;
	}
	/**
	 * Performs special processing when this page's Restore Defaults button has 
	 * been pressed.
	 * Sets the contents of the color field to the default value in the preference
	 * store.
	 */
	protected void performDefaults() {
		this.store.setValue(PreferenceConstants.P_MODEL_STORAGE_DEFAULT_CONNECTIONS, 
				this.store.getDefaultString(PreferenceConstants.P_MODEL_STORAGE_DEFAULT_CONNECTIONS));
		this.defaultConnectionList.setItems(EMFStorageUIPlugin.getDefaultModelStorageConnectionUris());
	}
	/** 
	 * Method declared on IPreferencePage. Save the
	 * color preference to the preference store.
	 */
	public boolean performOk() {
		EMFStorageUIPlugin.setDefaultModelStorageConnectionUris(this.defaultConnectionList.getItems());
		return super.performOk();
	}
}