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
package de.tud.et.ifa.agtele.emf.connecting;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.ecore.EObject;

/**
 * An abstract base implementation for classes that allow to {@link #instantiate() instantiate} an
 * {@link EClassConnectionPath}, i.e. connect one or multiple target elements to a starting elements according to the
 * path specification.
 * <p />
 * A suitable instantiator for a concrete {@link EClassConnectionPath} can be created via
 * {@link EClassConnectionPath#createInstantiator(EObject, Collection)}.
 *
 * @author mfreund
 */
public abstract class EClassConnectionPathInstantiator {

	protected final EClassConnectionPath pathToInstantiate;

	protected List<EObject> createdIntermediaryElements;

	protected List<EObject> unconnectedElements;

	protected EObject startingElement;

	protected List<EObject> targetElements;

	protected EClassConnectionPathInstantiator(EClassConnectionPath pathToInstantiate, EObject startingElement,
			Collection<EObject> targetElements) {

		validateParameters(pathToInstantiate, startingElement, targetElements);

		this.pathToInstantiate = pathToInstantiate;
		this.startingElement = startingElement;
		this.targetElements = new ArrayList<>(targetElements);

		createdIntermediaryElements = new ArrayList<>();
		unconnectedElements = new ArrayList<>();
	}

	private void validateParameters(EClassConnectionPath pathToInstantiate, EObject startingElement,
			Collection<EObject> targetElements) {

		if (!pathToInstantiate.isValidStartingElement(startingElement)) {
			throw new EClassConnectionPathInstantiationException("Error creating instantiator for connection path '"
					+ pathToInstantiate + "'! The given starting element " + startingElement
					+ " does not match the starting EClass.");
		}

		for (EObject targetElement : targetElements) {

			if (!pathToInstantiate.isValidTargetElement(targetElement)) {
				throw new EClassConnectionPathInstantiationException("Error creating instantiator for connection path '"
						+ pathToInstantiate + "'! The given starting element " + startingElement
						+ " does not match the starting EClass.");
			}
		}
	}

	/**
	 * Connect the {@link #targetElements} to the {@link #startingElement} according to the {@link #pathToInstantiate}.
	 *
	 * @throws EClassConnectionPathInstantiationException
	 */
	public void instantiate() {

		if (targetElements.isEmpty()) {
			return;
		}

		checkStartingElementMatchesStartingClass();

		checkStartingElementProvidesRequiredCapacity();

		doInstantiate();
	}

	protected void checkStartingElementMatchesStartingClass() {

		if (!pathToInstantiate.isValidStartingElement(startingElement)) {

			unconnectedElements.addAll(targetElements);

			throw new EClassConnectionPathInstantiationException("Error instantiating EClassConnectionPath '"
					+ pathToInstantiate + "'! The EClass of the given starting element ('"
					+ startingElement.eClass().getName() + "') does not conform to the path specification.");
		}
	}

	protected void checkStartingElementProvidesRequiredCapacity() {

		Capacity actualCapacity = pathToInstantiate.getActualCapacity(startingElement);
		Capacity requiredCapactiy = Capacity.valueOf(targetElements);

		if (!actualCapacity.isSufficientFor(requiredCapactiy)) {

			unconnectedElements.addAll(targetElements);

			throw new EClassConnectionPathInstantiationException(
					"Error instantiating EClassConnectionPath '" + pathToInstantiate
							+ "'! The given starting element does not provide the required capacity to connect the "
							+ targetElements.size() + " target elements.");
		}

	}

	protected abstract void doInstantiate();

	/**
	 * @return the list of elements that have been created automatically during instantiation of the path
	 */
	public List<EObject> getCreatedIntermediaryElements() {

		return createdIntermediaryElements != null ? createdIntermediaryElements : new ArrayList<>();
	}

	/**
	 * @return the list of elements that could not be connected while instantiating the path
	 */
	public List<EObject> getUnconnectedElements() {

		return unconnectedElements != null ? unconnectedElements : new ArrayList<>();
	}

	@SuppressWarnings("javadoc")
	public class EClassConnectionPathInstantiationException extends RuntimeException {

		private static final long serialVersionUID = 6362792531128516301L;

		public EClassConnectionPathInstantiationException(String message, Throwable cause) {

			super(message, cause);
		}

		public EClassConnectionPathInstantiationException(String message) {

			super(message);
		}

		public EClassConnectionPathInstantiationException(Throwable cause) {

			super(cause);
		}

	}
}
