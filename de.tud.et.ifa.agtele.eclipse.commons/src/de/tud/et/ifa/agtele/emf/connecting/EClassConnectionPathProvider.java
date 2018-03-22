/**
 *
 */
package de.tud.et.ifa.agtele.emf.connecting;

import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

import org.eclipse.emf.ecore.EPackage;

import de.tud.et.ifa.agtele.emf.connecting.impl.CachedEClassConnectionPathProvider;

/**
 * Instances of this class are able to retrieve/provide a list of {@link EClassConnectionPath EClassConnectionPaths}
 * based on a given {@link EClassConnectionPathRequirement EClassConnectionPathRequirement}.
 * <p />
 * Note: An implementation of this provider interface can be retrieved via {@link #getInstance(Collection)} or
 * {@link #getInstance(Collection, Logger)}.
 *
 * @author mfreund
 */
@SuppressWarnings("javadoc")
public interface EClassConnectionPathProvider {

	/**
	 * Creates a new instance for the given list of {@link EPackage EPackages}.
	 */
	public static EClassConnectionPathProvider getInstance(Collection<EPackage> ePackages, Logger logger) {

		return new CachedEClassConnectionPathProvider(ePackages, logger);
	}

	/**
	 * Creates a new instance for the given list of {@link EPackage EPackages}.
	 */
	public static EClassConnectionPathProvider getInstance(Collection<EPackage> ePackages) {

		return new CachedEClassConnectionPathProvider(ePackages);
	}

	public List<EClassConnectionPath> getConnections(EClassConnectionPathRequirement connectionRequirement);

}
