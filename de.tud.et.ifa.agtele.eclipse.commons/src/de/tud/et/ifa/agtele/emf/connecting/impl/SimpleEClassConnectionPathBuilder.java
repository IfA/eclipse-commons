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

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EReference;

import de.tud.et.ifa.agtele.emf.connecting.EClassConnectionPath;
import de.tud.et.ifa.agtele.emf.connecting.EClassConnectionPathRequirement;
import de.tud.et.ifa.agtele.emf.connecting.Length;

/**
 * An {@link IEClassConnectionPathBuilder} that is able create simple, {@link Length#DIRECT_CONNECTION direct}
 * {@link EClassConnectionPath EClassConnectionPaths} that connect a starting {@link EClass} to a target {@link EClass}
 * via a single {@link EReference}.
 *
 * @author mfreund
 */
@SuppressWarnings("javadoc")
public class SimpleEClassConnectionPathBuilder extends AbstractEClassConnectionPathBuilder {

	private EReference reference;

	public SimpleEClassConnectionPathBuilder(EClassConnectionPathRequirement requirement,
			EClassConnectionInformationRegistry eClassConnectionInformationRegistry) {

		super(requirement, eClassConnectionInformationRegistry);

		if (requiredReferences != null && requiredReferences.size() == 1) {
			reference = requiredReferences.iterator().next();
		}
	}

	@Override
	protected void doBuildConnectionPaths() {

		if (validateParameters()) {
			createDirectConnectionPath();
		}
	}

	private void createDirectConnectionPath() {

		foundPaths.add(new DirectEClassConnectionPath(startingClass, reference, targetClass));
	}

	private boolean validateParameters() {

		return validateIsDirectConnection() && validateStartingClassDefinesReference()
				&& validateReferencePointsToTargetClass();
	}

	private boolean validateIsDirectConnection() {

		return Length.DIRECT_CONNECTION.equals(maxPathLength);
	}

	private boolean validateStartingClassDefinesReference() {

		return startingClass != null && reference != null
				&& eClassConnectionInformationRegistry.getAllReferencesFromClass(startingClass).contains(reference);
	}

	private boolean validateReferencePointsToTargetClass() {

		return targetClass != null && reference != null
				&& eClassConnectionInformationRegistry.getAllReferencesToClass(targetClass).contains(reference);
	}

}
