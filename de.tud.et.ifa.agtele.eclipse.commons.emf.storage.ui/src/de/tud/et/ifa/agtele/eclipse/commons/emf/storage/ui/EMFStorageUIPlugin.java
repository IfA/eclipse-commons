package de.tud.et.ifa.agtele.eclipse.commons.emf.storage.ui;

import java.util.StringTokenizer;

import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.emf.common.EMFPlugin;
import org.eclipse.emf.common.ui.EclipseUIPlugin;
import org.eclipse.emf.common.util.ResourceLocator;
import org.osgi.framework.BundleContext;
import org.osgi.service.prefs.BackingStoreException;

import de.tud.et.ifa.agtele.eclipse.commons.emf.storage.ui.preferences.PreferenceConstants;

public class EMFStorageUIPlugin extends EMFPlugin {

	/**
	 * The Plugin ID
	 */
	public static final String PLUGIN_ID = "de.tud.et.ifa.agtele.eclipse.emf.storage.ui";
	
	private static BundleContext context;
	
	public static final EMFStorageUIPlugin INSTANCE = new EMFStorageUIPlugin();

	/**
	 * Keep track of the singleton.
	 */
	private static Implementation plugin;
	
	public EMFStorageUIPlugin () {
		super
		(new ResourceLocator [] {
		});
	}

	static BundleContext getContext() {
		return context;
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext bundleContext) throws Exception {
		EMFStorageUIPlugin.context = bundleContext;
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext bundleContext) throws Exception {
		EMFStorageUIPlugin.context = null;
	}

	public static EMFStorageUIPlugin getDefault() {
		return EMFStorageUIPlugin.INSTANCE;
	}

	/**
	 * Returns the singleton instance of the Eclipse plugin.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the singleton instance.
	 */
	@Override
	public ResourceLocator getPluginResourceLocator() {
		return plugin;
	}
	
	/**
	 * Returns the singleton instance of the Eclipse plugin.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the singleton instance.
	 */
	public static Implementation getPlugin() {
		return plugin;
	}

	/**
	 * The actual implementation of the Eclipse <b>Plugin</b>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 */
	public static class Implementation extends EclipseUIPlugin {
		/**
		 * Creates an instance.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 */
		public Implementation() {
			super();
	
			// Remember the static instance.
			//
			plugin = this;
		}
		
		@Override
		public void saveDialogSettings() {
			super.saveDialogSettings();
			try {
				InstanceScope.INSTANCE.getNode(PLUGIN_ID).flush();
			} catch (BackingStoreException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static final String ARRAY_PREFERENCE_DELIMITER = "\n";
	
	public static String convertPreference (String [] entries) {		
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < entries.length; i++) {
			buffer.append(entries[i]);
			buffer.append(ARRAY_PREFERENCE_DELIMITER);
		}
		return buffer.toString();
	}
	
	public static String[] convertPreference (String serialized) {
		StringTokenizer tokenizer =
				new StringTokenizer(serialized, ARRAY_PREFERENCE_DELIMITER);
		int tokenCount = tokenizer.countTokens();
		String[] elements = new String[tokenCount];
		for (int i = 0; i < tokenCount; i++) {
			elements[i] = tokenizer.nextToken();
		}

		return elements;
	}
	
	public static String[] getDefaultModelStorageConnectionUris () {
		return convertPreference(getPlugin().getPreferenceStore().getString(PreferenceConstants.P_MODEL_STORAGE_DEFAULT_CONNECTIONS));
	}

	public static void setDefaultModelStorageConnectionUris (String[] uris) {
		getPlugin().getPreferenceStore().setValue(PreferenceConstants.P_MODEL_STORAGE_DEFAULT_CONNECTIONS, convertPreference(uris));
		try {
			InstanceScope.INSTANCE.getNode(PLUGIN_ID).flush();
		} catch (BackingStoreException e) {
			e.printStackTrace();
		}
	}	
}
