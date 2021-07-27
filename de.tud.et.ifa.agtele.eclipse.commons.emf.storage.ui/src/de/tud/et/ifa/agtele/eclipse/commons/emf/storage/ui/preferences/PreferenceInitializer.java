package de.tud.et.ifa.agtele.eclipse.commons.emf.storage.ui.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

import de.tud.et.ifa.agtele.eclipse.commons.emf.storage.ui.EMFStorageUIPlugin;

/**
 * Class used to initialize default preference values.
 */
public class PreferenceInitializer extends AbstractPreferenceInitializer {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer#initializeDefaultPreferences()
	 */
	public void initializeDefaultPreferences() {
		IPreferenceStore store = EMFStorageUIPlugin.getPlugin().getPreferenceStore();
		store.setDefault(PreferenceConstants.P_MODEL_STORAGE_DEFAULT_CONNECTIONS, "");
	}

}
