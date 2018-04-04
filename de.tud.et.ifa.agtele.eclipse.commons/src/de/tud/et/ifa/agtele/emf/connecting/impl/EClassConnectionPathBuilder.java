/**
 *
 */
package de.tud.et.ifa.agtele.emf.connecting.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcorePackage;

import de.tud.et.ifa.agtele.emf.ExtendedMetaDataUtil;
import de.tud.et.ifa.agtele.emf.connecting.AllowedReferenceType;
import de.tud.et.ifa.agtele.emf.connecting.Capacity;
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
public class EClassConnectionPathBuilder {

	private EClass startingClass;

	private EClass targetClass;

	private EObject startingElement;

	private Length maxPathLength;

	private Capacity requiredCapacity;

	private AllowedReferenceType allowedReferenceType;

	private Collection<EReference> requiredReferences;

	private EClassConnectionInformationRegistry eClassConnectionInformationRegistry;

	private List<EClassConnectionPath> foundPaths;

	private Queue<EClassConnectionPath> potentialPathQueue;

	private EClassConnectionPath currentPotentialPath;

	/**
	 * Create an instance.
	 *
	 * @param requirement
	 *            The {@link EClassConnectionPathRequirement} that this builder operates on.
	 * @param eClassConnectionInformationRegistry
	 *            The {@link EClassConnectionInformationRegistry} that is consulted by the builder to retrieve potential
	 *            connections between various {@link EClass EClasses}.
	 */
	public EClassConnectionPathBuilder(EClassConnectionPathRequirement requirement,
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
	public List<EClassConnectionPath> buildConnectionPaths() {

		initBuilder();

		buildConnectionPathsIncrementally();

		filterPathsWithRequiredCapacity();

		filterPathsWithRequiredReferences();

		sortPaths();

		return foundPaths;

	}

	private void initBuilder() {

		foundPaths = new ArrayList<>();
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

			} else {
				buildNextPotentialPaths();
			}

		} while (!potentialPathQueue.isEmpty());
	}

	private boolean currentPotentialPathLeadsToTargetClass() {

		if (currentPotentialPath == null || currentPotentialPath.getTargetClass() == null) {
			return false;
		} else {
			EClass potentialPathTargetClass = currentPotentialPath.getTargetClass();
			return potentialPathTargetClass.isSuperTypeOf(targetClass)
					|| potentialPathTargetClass.equals(EcorePackage.Literals.EOBJECT);
		}

	}

	private void buildNextPotentialPaths() {

		if (isCurrentPotentialPathOfMaxPathLength()) {
			return;
		}

		List<EClassConnectionPath> nextPossiblePathSegments = getNextPotentialPathSegments();

		for (EClassConnectionPath nextPossiblePathSegment : nextPossiblePathSegments) {
			buildNextPotentialPath(nextPossiblePathSegment);
		}
	}

	private boolean isCurrentPotentialPathOfMaxPathLength() {

		Length currentPotentialPathLength = currentPotentialPath != null ? currentPotentialPath.getLength()
				: Length.NO_CONNECTION;

		return maxPathLength.compareTo(currentPotentialPathLength) <= 0;
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

		List<EReference> allOutgoingReferences = new ArrayList<>(startingClass.getEAllReferences());

		// for classes based on an xsd element with content of type 'xs:any'
		if (ExtendedMetaDataUtil.allowsAnyContent(startingClass)) {
			EReference xsAnyReference = ExtendedMetaDataUtil.getOrCreateVirtualAnyContentReference(startingClass);
			allOutgoingReferences.add(xsAnyReference);
		}

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

		List<EClass> nonAbstractPotentialTargetClasses = potentialTargetClasses.stream().filter(c -> !c.isAbstract())
				.collect(Collectors.toList());

		return nonAbstractPotentialTargetClasses.stream()
				.map(c -> new DirectEClassConnectionPath(startingClass, outgoingReference, c))
				.collect(Collectors.toList());
	}

	private List<EClass> getAllPotentialTargetClasses(EReference reference) {

		EClass referenceType = reference.getEReferenceType();

		List<EClass> potentialTargetClasses = new ArrayList<>();
		potentialTargetClasses.add(referenceType);
		potentialTargetClasses.addAll(eClassConnectionInformationRegistry.getAllSubClasses(referenceType));

		return potentialTargetClasses;
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

	private void filterPathsWithRequiredCapacity() {

		foundPaths = foundPaths.stream().filter(this::providesRequiredCapacity).collect(Collectors.toList());
	}

	private boolean providesRequiredCapacity(EClassConnectionPath path) {

		Capacity pathCapacity = startingElement != null ? path.getActualCapacity(startingElement)
				: path.getTheoreticalCapacity();

		return pathCapacity.isSufficientFor(requiredCapacity);
	}

	private void filterPathsWithRequiredReferences() {

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

	private void sortPaths() {

		foundPaths = EClassConnectionPathUtil.sortConnectionPathsFromShortestToLongest(foundPaths);
	}

}
