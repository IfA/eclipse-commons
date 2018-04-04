/**
 *
 */
package de.tud.et.ifa.agtele.emf.connecting.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;

import de.tud.et.ifa.agtele.emf.connecting.AllowedReferenceType;
import de.tud.et.ifa.agtele.emf.connecting.Capacity;
import de.tud.et.ifa.agtele.emf.connecting.EClassConnectionPath;
import de.tud.et.ifa.agtele.emf.connecting.EClassConnectionPathRequirement;
import de.tud.et.ifa.agtele.emf.connecting.Length;

/**
 * An abstract base implementation for builders that are able to construct a list of {@link EClassConnectionPath
 * EClassConnectionPaths} based on an {@link EClassConnectionPathRequirement}.
 *
 * @author mfreund
 */
public abstract class AbstractEClassConnectionPathBuilder implements IEClassConnectionPathBuilder {

	protected EClass startingClass;

	protected EClass targetClass;

	protected EObject startingElement;

	protected Length maxPathLength;

	protected Capacity requiredCapacity;

	protected AllowedReferenceType allowedReferenceType;

	protected Collection<EReference> requiredReferences;

	protected EClassConnectionInformationRegistry eClassConnectionInformationRegistry;

	protected List<EClassConnectionPath> foundPaths;

	/**
	 * @param requirement
	 *            The {@link EClassConnectionPathRequirement} that this builder operates on.
	 * @param eClassConnectionInformationRegistry
	 *            The {@link EClassConnectionInformationRegistry} that is consulted by the builder to retrieve potential
	 *            connections between various {@link EClass EClasses}.
	 */
	public AbstractEClassConnectionPathBuilder(EClassConnectionPathRequirement requirement,
			EClassConnectionInformationRegistry eClassConnectionInformationRegistry) {

		startingClass = requirement.getRequiredStartingClass();
		targetClass = requirement.getRequiredTargetClass();
		maxPathLength = requirement.getRequiredMaximumPathLength();
		allowedReferenceType = requirement.getAllowedReferenceType();
		requiredCapacity = requirement.getRequiredMinimumCapacity();
		startingElement = requirement.getRequiredStartingElement();
		requiredReferences = requirement.getRequiredReferences();
		this.eClassConnectionInformationRegistry = eClassConnectionInformationRegistry;

	}

	/**
	 * Determine all {@link EClassConnectionPath EClassConnectionPaths} that satisfy the
	 * {@link EClassConnectionPathRequirement} that was passed to the constructor.
	 *
	 * @return The list of paths.
	 */
	@Override
	public List<EClassConnectionPath> buildConnectionPaths() {

		initBuilder();

		doBuildConnectionPaths();

		filterPathsWithRequiredCapacity();

		filterPathsWithRequiredReferences();

		sortPaths();

		return foundPaths;

	}

	protected void initBuilder() {

		foundPaths = new ArrayList<>();

	}

	/**
	 * The actual process of determining potential connection paths needs to be implemented here. Found paths are to be
	 * stored in {@link #foundPaths}.
	 */
	protected abstract void doBuildConnectionPaths();

	protected void filterPathsWithRequiredCapacity() {

		foundPaths = foundPaths.stream().filter(this::providesRequiredCapacity).collect(Collectors.toList());
	}

	private boolean providesRequiredCapacity(EClassConnectionPath path) {

		Capacity pathCapacity = startingElement != null ? path.getActualCapacity(startingElement)
				: path.getTheoreticalCapacity();

		return pathCapacity.isSufficientFor(requiredCapacity);
	}

	protected void filterPathsWithRequiredReferences() {

		foundPaths = foundPaths.stream().filter(this::containsRequiredReferences).collect(Collectors.toList());
	}

	private boolean containsRequiredReferences(EClassConnectionPath path) {

		if (requiredReferences instanceof List<?>) {
			return containsRequiredReferencesInCorrectOrder(path);
		} else {
			return containsRequiredReferencesInAnyOrder(path);
		}
	}

	private boolean containsRequiredReferencesInCorrectOrder(EClassConnectionPath path) {

		if (!containsRequiredReferencesInAnyOrder(path)) {
			return false;
		}

		List<Integer> referenceIndices = requiredReferences.stream().map(r -> path.getAllReferences().indexOf(r))
				.collect(Collectors.toList());

		List<Integer> sortedReferenceIndices = new ArrayList<>(referenceIndices);
		Collections.sort(sortedReferenceIndices);

		return referenceIndices.equals(sortedReferenceIndices);
	}

	private boolean containsRequiredReferencesInAnyOrder(EClassConnectionPath path) {

		return requiredReferences != null ? path.getAllReferences().containsAll(requiredReferences) : true;
	}

	protected void sortPaths() {

		foundPaths = EClassConnectionPathUtil.sortConnectionPathsFromShortestToLongest(foundPaths);
	}
}
