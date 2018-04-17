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

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcorePackage;

import de.tud.et.ifa.agtele.emf.connecting.EClassConnectionPath;
import de.tud.et.ifa.agtele.emf.connecting.EClassConnectionPathRequirement;
import de.tud.et.ifa.agtele.emf.connecting.Length;

/**
 * A builder that operates on an {@link EClassConnectionPathRequirement} and assembles all {@link EClassConnectionPath
 * EClassConnectionPaths} that fulfill this requirement.
 * <p />
 * The {@link #buildConnectionPaths() assembly process} works iteratively starting from a certain {@link EClass} and
 * assembling the resulting paths based on a list of {@link DirectEClassConnectionPath path segments}.
 *
 * @author mfreund
 */
@SuppressWarnings("javadoc")

public class EClassConnectionPathBuilder extends AbstractEClassConnectionPathBuilder {

	private Queue<EClassConnectionPath> potentialPathQueue;

	private EClassConnectionPath currentPotentialPath;

	public EClassConnectionPathBuilder(EClassConnectionPathRequirement requirement,
			EClassConnectionInformationRegistry eClassConnectionInformationRegistry) {

		super(requirement, eClassConnectionInformationRegistry);
	}

	@Override
	protected void doBuildConnectionPaths() {

		buildConnectionPathsIncrementally();
	}

	@Override
	protected void initBuilder() {

		super.initBuilder();

		potentialPathQueue = new LinkedList<>();
		currentPotentialPath = null;
	}

	private void buildConnectionPathsIncrementally() {

		do {

			// At the first iteration step, this returns 'null'. This is, however, no problem and will lead to the queue
			// to be initialized in 'buildNextPotentialPaths'
			//
			currentPotentialPath = potentialPathQueue.poll();

			if (currentPotentialPathLeadsToTargetClass()) {
				foundPaths.add(currentPotentialPath);

			} else if (!isCurrentPotentialPathOfMaxPathLength()) {
				buildNextPotentialPaths();
			}

		} while (!potentialPathQueue.isEmpty());
	}

	private boolean currentPotentialPathLeadsToTargetClass() {

		return currentPotentialPath != null && pathLeadsToRequiredTargetClass(currentPotentialPath);
	}

	private boolean pathLeadsToRequiredTargetClass(EClassConnectionPath path) {

		EClass pathPathTargetClass = path.getTargetClass();

		return pathPathTargetClass.isSuperTypeOf(targetClass)
				|| pathPathTargetClass.equals(EcorePackage.Literals.EOBJECT);
	}

	private boolean isCurrentPotentialPathOfMaxPathLength() {

		Length currentPotentialPathLength = getLengthOfCurrentPotentialPath();

		return isMaximumLength(currentPotentialPathLength);
	}

	private Length getLengthOfCurrentPotentialPath() {

		return currentPotentialPath != null ? currentPotentialPath.getLength() : Length.NO_CONNECTION;
	}

	private boolean isMaximumLength(Length length) {

		return maxPathLength.compareTo(length) <= 0;
	}

	private void buildNextPotentialPaths() {

		List<EClassConnectionPath> nextPossiblePathSegments = getNextPotentialPathSegments();

		for (EClassConnectionPath nextPossiblePathSegment : nextPossiblePathSegments) {
			buildNextPotentialPath(nextPossiblePathSegment);
		}
	}

	private List<EClassConnectionPath> getNextPotentialPathSegments() {

		if (currentPotentialPath == null) { // we are at the beginning of the building process
			return getFirstPotentialPathSegments();
		} else {
			return getAllAllowedOutgoingDirectConnectionPaths(currentPotentialPath.getTargetClass());
		}

	}

	private List<EClassConnectionPath> getFirstPotentialPathSegments() {

		if (startingClass != null) {
			return getAllAllowedOutgoingDirectConnectionPaths(startingClass);

		} else {
			// as no starting class was passed in the requirement, we consider all existing classes as potential
			// starting class
			Stream<EClass> concreteRegisteredClasses = eClassConnectionInformationRegistry.getRegisteredClasses()
					.stream().filter(c -> !c.isAbstract());
			return concreteRegisteredClasses.flatMap(c -> getAllAllowedOutgoingDirectConnectionPaths(c).stream())
					.collect(Collectors.toList());
		}
	}

	private List<EClassConnectionPath> getAllAllowedOutgoingDirectConnectionPaths(EClass startingClass) {

		List<EReference> outgoingReferences = getAllAllowedOutgoingReferences(startingClass);

		return getAllPossibleDirectConnectionPathsViaReferences(startingClass, outgoingReferences);
	}

	private List<EReference> getAllAllowedOutgoingReferences(EClass startingClass) {

		Set<EReference> allOutgoingReferences = eClassConnectionInformationRegistry
				.getAllReferencesFromClass(startingClass);

		return allOutgoingReferences.stream().filter(allowedReferenceType::allows).collect(Collectors.toList());
	}

	private List<EClassConnectionPath> getAllPossibleDirectConnectionPathsViaReferences(EClass startingClass,
			List<EReference> references) {

		return references.stream()
				.flatMap(r -> getAllPossibleDirectConnectionPathsViaReference(startingClass, r).stream())
				.collect(Collectors.toList());
	}

	private List<EClassConnectionPath> getAllPossibleDirectConnectionPathsViaReference(EClass startingClass,
			EReference outgoingReference) {

		List<EClass> potentialTargetClasses = getAllPotentialTargetClasses(outgoingReference);

		List<EClass> validTargetClasses = potentialTargetClasses.stream().filter(this::isValidClassForPath)
				.collect(Collectors.toList());

		return validTargetClasses.stream().map(c -> new DirectEClassConnectionPath(startingClass, outgoingReference, c))
				.collect(Collectors.toList());
	}

	private List<EClass> getAllPotentialTargetClasses(EReference reference) {

		EClass referenceType = reference.getEReferenceType();

		List<EClass> potentialTargetClasses = new ArrayList<>();
		potentialTargetClasses.add(referenceType);
		potentialTargetClasses.addAll(eClassConnectionInformationRegistry.getAllSubClasses(referenceType));

		return potentialTargetClasses;
	}

	private boolean isValidClassForPath(EClass eClass) {

		return !eClass.isAbstract() || eClass.equals(targetClass);
	}

	private void buildNextPotentialPath(EClassConnectionPath nextPossiblePathSegment) {

		EClassConnectionPath resultingConnectionPath = EClassConnectionPathUtil.join(currentPotentialPath,
				nextPossiblePathSegment);

		if (isValidPath(resultingConnectionPath)) {
			potentialPathQueue.add(resultingConnectionPath);
		}

	}

	private boolean isValidPath(EClassConnectionPath path) {

		return !path.containsLoop();

	}

}
