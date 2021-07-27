package de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage;

import org.eclipse.emf.common.EMFPlugin;
import org.eclipse.emf.common.ui.EclipseUIPlugin;
import org.eclipse.emf.common.util.ResourceLocator;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class EMFStoragePlugin extends EMFPlugin {

	/**
	 * Keep track of the singleton.
	 */
	public static final EMFStoragePlugin INSTANCE = new EMFStoragePlugin();
	
	/**
	 * Keep track of the singleton.
	 */
	private static EMFStoragePlugin.Implementation plugin;

	/**
	 * Create the instance.
	 */
	public EMFStoragePlugin() {
		super
			(new ResourceLocator [] {
			});
	}

	/**
	 * Returns the singleton instance of the Eclipse plugin.
	 * @return the singleton instance.
	 */
	@Override
	public ResourceLocator getPluginResourceLocator() {
		return plugin;
	}
	
	/**
	 * Returns the singleton instance of the Eclipse plugin.
	 * @return the singleton instance.
	 */
	public static EMFStoragePlugin.Implementation getPlugin() {
		return plugin;
	}
	
	/**
	 * The actual implementation of the Eclipse <b>Plugin</b>.
	 */
	public static class Implementation extends EclipseUIPlugin {
		/**
		 * Creates an instance.
		 */
		public Implementation() {
			super();
	
			// Remember the static instance.
			//
			plugin = this;
		}
	}

}
