/**
 *
 */
package de.tud.et.ifa.agtele.emf.connecting.impl;

import java.util.List;

import de.tud.et.ifa.agtele.emf.connecting.EClassConnectionPath;

/**
 * A base interface for builders that are able to construct a list of {@link EClassConnectionPath EClassConnectionPaths}
 * based on some kind of requirements.
 *
 * @author mfreund
 */
public interface IEClassConnectionPathBuilder {

	/**
	 * Determine all {@link EClassConnectionPath EClassConnectionPaths} based on the strategy implemented by the class
	 * implementing this interface.
	 *
	 * @return The list of paths.
	 */
	public List<EClassConnectionPath> buildConnectionPaths();

}
