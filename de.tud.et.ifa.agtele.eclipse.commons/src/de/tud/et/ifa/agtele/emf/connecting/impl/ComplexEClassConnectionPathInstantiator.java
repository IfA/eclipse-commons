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
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import de.tud.et.ifa.agtele.emf.connecting.Capacity;
import de.tud.et.ifa.agtele.emf.connecting.EClassConnectionPath;
import de.tud.et.ifa.agtele.emf.connecting.EClassConnectionPathInstantiator;

/**
 * An {@link EClassConnectionPathInstantiator} that is able to automatically instantiate missing intermediary elements.
 *
 * @author mfreund
 */
public class ComplexEClassConnectionPathInstantiator extends EClassConnectionPathInstantiator {

	private DirectEClassConnectionPath firstPathSegment;

	// All 'pathSegments' excluding the 'firstPathSegment'
	private EClassConnectionPath remainingPath;

	private List<EObject> existingElementsOfFirstPathSegment;

	private Capacity requiredCapacity;

	@SuppressWarnings("javadoc")
	public ComplexEClassConnectionPathInstantiator(ComplexEClassConnectionPath pathToInstantiate,
			EObject startingElement, Collection<EObject> targetElements) {

		super(pathToInstantiate, startingElement, targetElements);

		firstPathSegment = pathToInstantiate.getPathSegments().get(0);
		remainingPath = pathToInstantiate.getSubPath(1);
		existingElementsOfFirstPathSegment = firstPathSegment.getExistingTargetElements(startingElement);
		requiredCapacity = Capacity.valueOf(targetElements);
	}

	/**
	 * Instantiates the {@link EClassConnectionPathInstantiator#pathToInstantiate pathToInstantiate}. If necessary,
	 * missing intermediary elements along the path will be created automatically.
	 *
	 */
	@Override
	protected void doInstantiate() {

		List<EObject> targetElementsOfFirstPathSegment = instantiateOrReuseFirstPathSegment();

		if (hasRemainingPath()) {
			instantiateRemainingPath(targetElementsOfFirstPathSegment);
		}

	}

	private List<EObject> instantiateOrReuseFirstPathSegment() {

		List<EObject> targetElementsOfFirstPathSegment;

		if (canFirstPathSegmentBeReused()) {
			targetElementsOfFirstPathSegment = existingElementsOfFirstPathSegment;
		} else {
			targetElementsOfFirstPathSegment = instantiateFirstPathSegment();
		}

		return targetElementsOfFirstPathSegment;
	}

	private boolean canFirstPathSegmentBeReused() {

		return hasRemainingPath() && canExistingTargetElementsOfFirstPathSegmentBeReused();

	}

	private boolean hasRemainingPath() {

		return !(remainingPath instanceof EmptyEClassConnectionPath);
	}

	private boolean canExistingTargetElementsOfFirstPathSegmentBeReused() {

		if (existingElementsOfFirstPathSegment.isEmpty()) {
			return false;

		} else if (existingElementsOfFirstPathSegment.size() == 1) {
			return canSingleTargetElementOfFirstPathSegmentBeReused(existingElementsOfFirstPathSegment.get(0));

		} else if (existingElementsOfFirstPathSegment.size() == targetElements.size()) {
			return canAllTargetElementsOfFirstPathSegmentBeReused(existingElementsOfFirstPathSegment);

		} else {
			return false;
		}

	}

	private boolean canSingleTargetElementOfFirstPathSegmentBeReused(EObject targetElementOfFirstPathSegment) {

		Capacity actualCapacityOfSubPath = remainingPath.getActualCapacity(targetElementOfFirstPathSegment);

		return actualCapacityOfSubPath.isSufficientFor(requiredCapacity);
	}

	private boolean canAllTargetElementsOfFirstPathSegmentBeReused(List<EObject> targetElementsOfFirstPathSegment) {

		return targetElementsOfFirstPathSegment.stream().noneMatch(e -> remainingPath.getActualCapacity(e).isZero());
	}

	private int getRequiredNumberOfIntermediaryElements() {

		Capacity theoreticalCapacityOfPathWithoutFirstSegment = remainingPath.getTheoreticalCapacity();

		if (theoreticalCapacityOfPathWithoutFirstSegment.isSufficientFor(requiredCapacity)) {
			// all target elements can be connected to a single intermediary element
			return 1;

		} else {
			// create an own intermediary element for each target element (we do not consider more complex cases where,
			// e.g., 5 target elements are connected to 3 elements of the first path segment)
			//
			return requiredCapacity.getValue();
		}
	}

	private EObject createIntermediaryElementForFirstPathSegment() {

		EClass classToInstantiate = firstPathSegment.getTargetClass();

		return classToInstantiate.getEPackage().getEFactoryInstance().create(classToInstantiate);
	}

	private List<EObject> instantiateFirstPathSegment() {

		List<EObject> targetElementsOfFirstPathSegment;

		if (!hasRemainingPath()) {
			targetElementsOfFirstPathSegment = targetElements;
		} else {
			targetElementsOfFirstPathSegment = createRequiredIntermediaryElementsForFirstPathSegment();
		}

		EClassConnectionPathInstantiator firstPathSegmentInstantiator = firstPathSegment
				.createInstantiator(startingElement, targetElementsOfFirstPathSegment);
		executeInstantiatorAndUpdateCreatedElements(firstPathSegmentInstantiator);

		return targetElementsOfFirstPathSegment;
	}

	private List<EObject> createRequiredIntermediaryElementsForFirstPathSegment() {

		int requiredNumberOfElements = getRequiredNumberOfIntermediaryElements();

		List<EObject> createdElements = IntStream.range(0, requiredNumberOfElements)
				.mapToObj(i -> createIntermediaryElementForFirstPathSegment()).collect(Collectors.toList());

		createdIntermediaryElements.addAll(createdElements);

		return createdElements;
	}

	private void instantiateRemainingPath(List<EObject> targetElementsOfFirstPathSegment) {

		List<EObject> startingElementsOfRemainingPath = targetElementsOfFirstPathSegment;

		int targetElementsPerStartingElement = getTargetElementsPerStartingElementOfRemainingPath(
				startingElementsOfRemainingPath);

		for (int i = 0; i < startingElementsOfRemainingPath.size(); i++) {

			EObject startingElementOfRemainingPath = startingElementsOfRemainingPath.get(i);
			List<EObject> correspondingTargetElements = targetElements.subList(targetElementsPerStartingElement * i,
					targetElementsPerStartingElement * (i + 1));

			instantiateRemainingPath(startingElementOfRemainingPath, correspondingTargetElements);
		}

	}

	private int getTargetElementsPerStartingElementOfRemainingPath(List<EObject> startingElementsOfRemainingPath) {

		if (startingElementsOfRemainingPath.size() == 1) {
			return targetElements.size();

		} else if (startingElementsOfRemainingPath.size() == targetElements.size()) {
			return 1;

		} else {
			throw new EClassConnectionPathInstantiationException(
					"Internal error during instantiation of EClassConnectionPath '" + pathToInstantiate
							+ "'! Trying to connect " + targetElements.size() + " target elements to "
							+ startingElementsOfRemainingPath.size() + " starting elements.");
		}
	}

	private void instantiateRemainingPath(EObject startingElementOfRemainingPath,
			List<EObject> correspondingTargetElements) {

		EClassConnectionPathInstantiator remainingPathInstantiator = remainingPath
				.createInstantiator(startingElementOfRemainingPath, correspondingTargetElements);
		executeInstantiatorAndUpdateCreatedElements(remainingPathInstantiator);
	}

	private void executeInstantiatorAndUpdateCreatedElements(EClassConnectionPathInstantiator instantiator) {

		instantiator.instantiate();

		createdIntermediaryElements.addAll(instantiator.getCreatedIntermediaryElements());
		unconnectedElements.addAll(instantiator.getUnconnectedElements());
	}

}
