/*******************************************************************************
 * Copyright (C) 2016-2018 Institute of Automation, TU Dresden.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Institute of Automation, TU Dresden - initial API and implementation
 ******************************************************************************/
package de.tud.et.ifa.agtele.emf.connecting.impl;

import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import de.tud.et.ifa.agtele.emf.connecting.EClassConnectionPath;
import de.tud.et.ifa.agtele.emf.connecting.EClassConnectionPathProvider;
import de.tud.et.ifa.agtele.emf.connecting.EClassConnectionPathRequirement;
import de.tud.et.ifa.agtele.emf.connecting.Length;

/**
 * A concrete implementation of {@link EClassConnectionPathProvider} that is able to create {@link EClassConnectionPath
 * EClassConnectionPaths} based on {@link EClassConnectionPathRequirement EClassConnectionPathRequirements}. The actual
 * work is delegated to instances of {@link EClassConnectionPathBuilder}.
 *
 * @author mfreund
 */
public class EClassConnectionPathFactory implements EClassConnectionPathProvider {

	private final EClassConnectionInformationRegistry eClassConnectionInformationRegistry;

	/**
	 * This creates an instance without an attached logger.
	 *
	 * @param ePackages
	 *            The collection of {@link EPackage EPackages} that shall be considered when determining potential
	 *            connection paths.
	 */
	public EClassConnectionPathFactory(Collection<EPackage> ePackages) {

		this(ePackages, null);
	}

	/**
	 * This creates an instance with an attached logger.
	 *
	 * @param ePackages
	 *            The collection of {@link EPackage EPackages} that shall be considered when determining potential
	 *            connection paths.
	 * @param logger
	 */
	public EClassConnectionPathFactory(Collection<EPackage> ePackages, Logger logger) {

		eClassConnectionInformationRegistry = logger == null ? new EClassConnectionInformationRegistry()
				: new EClassConnectionInformationRegistry(logger);
		eClassConnectionInformationRegistry.register(ePackages);
	}

	@Override
	public List<EClassConnectionPath> getConnections(EClassConnectionPathRequirement connectionRequirement) {

		IEClassConnectionPathBuilder pathBuilder = createPathBuilder(connectionRequirement);

		return pathBuilder.buildConnectionPaths();
	}

	private IEClassConnectionPathBuilder createPathBuilder(EClassConnectionPathRequirement connectionRequirement) {

		IEClassConnectionPathBuilder pathBuilder;

		if (isSimpleRequirement(connectionRequirement)) {
			pathBuilder = new SimpleEClassConnectionPathBuilder(connectionRequirement,
					eClassConnectionInformationRegistry);
		} else {
			pathBuilder = new EClassConnectionPathBuilder(connectionRequirement, eClassConnectionInformationRegistry);
		}

		return pathBuilder;
	}

	private boolean isSimpleRequirement(EClassConnectionPathRequirement connectionRequirement) {

		return isDirectConnection(connectionRequirement) && specifiesStartingClass(connectionRequirement)
				&& specifiesRequiredReference(connectionRequirement);
	}

	private boolean isDirectConnection(EClassConnectionPathRequirement connectionRequirement) {

		return connectionRequirement.getRequiredMaximumPathLength().equals(Length.DIRECT_CONNECTION);
	}

	private boolean specifiesStartingClass(EClassConnectionPathRequirement connectionRequirement) {

		return connectionRequirement.getRequiredStartingClass() != null;
	}

	private boolean specifiesRequiredReference(EClassConnectionPathRequirement connectionRequirement) {

		Collection<EReference> requiredReferences = connectionRequirement.getRequiredReferences();
		return requiredReferences != null && requiredReferences.size() == 1;
	}

}
