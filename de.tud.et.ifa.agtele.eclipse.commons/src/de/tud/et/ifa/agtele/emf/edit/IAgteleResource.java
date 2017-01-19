package de.tud.et.ifa.agtele.emf.edit;

import org.eclipse.emf.ecore.resource.Resource;

/**
 * This interface may be implemented in order to allow generic model editors to
 * set options that are only available in specific {@link Resource}
 * implementations.
 *
 * @author Baron
 *
 */
public interface IAgteleResource {
	/**
	 * Returns whether the {@link Resource} will be stored using UUIDs.
	 *
	 * @return <code>true</code>, if UUIDs will be used.
	 */
	boolean isSaveUsingUUIDs();

	/**
	 * Sets whether the {@link Resource} will be stored using UUIDs.
	 *
	 * @param useUUIDs
	 */
	void setSaveUsingUUIDs(boolean useUUIDs);

	/**
	 * Returns, wether the resource implementation allows to change the useUUID
	 * option.
	 * 
	 * @return <code>true</code>, if changing this option ins supported.
	 */
	default boolean isSaveUsingUUIDsChangeAllowed() {
		return true;
	}
}
