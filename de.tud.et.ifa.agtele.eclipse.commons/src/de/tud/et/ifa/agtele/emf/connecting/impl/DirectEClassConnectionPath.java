/**
 *
 */
package de.tud.et.ifa.agtele.emf.connecting.impl;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;

import de.tud.et.ifa.agtele.emf.AgteleEcoreUtil;
import de.tud.et.ifa.agtele.emf.connecting.Capacity;
import de.tud.et.ifa.agtele.emf.connecting.EClassConnectionPath;
import de.tud.et.ifa.agtele.emf.connecting.EClassConnectionPathInstantiator;
import de.tud.et.ifa.agtele.emf.connecting.Length;

/**
 * An {@link EClassConnectionPath} that represents a direct path between two {@link EClass EClasses} that consists of
 * just one {@link EReference}.
 *
 * @author mfreund
 */
public class DirectEClassConnectionPath implements EClassConnectionPath {

	private final EClass startingClass;

	private final EReference reference;

	private final EClass targetClass;

	/**
	 * Creates a new instance.
	 *
	 * @param startingClass
	 * @param reference
	 * @param targetClass
	 */
	public DirectEClassConnectionPath(EClass startingClass, EReference reference, EClass targetClass) {

		this.startingClass = startingClass;
		this.reference = reference;
		this.targetClass = targetClass;
	}

	/**
	 * @return the {@link #reference}
	 */
	public EReference getReference() {

		return reference;
	}

	@Override
	public EClass getTargetClass() {

		return targetClass;
	}

	@Override
	public EClass getStartingClass() {

		return startingClass;
	}

	@Override
	public Length getLength() {

		return Length.DIRECT_CONNECTION;
	}

	@Override
	public boolean equals(Object obj) {

		if (this == obj) {
			return true;
		}

		if (!(obj instanceof DirectEClassConnectionPath)) {
			return false;
		}

		DirectEClassConnectionPath other = (DirectEClassConnectionPath) obj;

		return Objects.equals(startingClass, other.startingClass) && Objects.equals(reference, other.reference)
				&& Objects.equals(targetClass, other.targetClass);
	}

	@Override
	public int hashCode() {

		return Objects.hash(startingClass, reference, targetClass);
	}

	@Override
	public String toString() {

		StringBuilder stringBuilder = new StringBuilder();

		stringBuilder.append(getStartingClass().getName());

		stringBuilder.append("...").append(getReference().getName()).append("...");

		stringBuilder.append(getTargetClass().getName());

		return stringBuilder.toString();
	}

	@Override
	public Capacity getActualCapacity(EObject startingElement) {

		if (!isValidStartingElement(startingElement)) {
			return Capacity.ZERO;
		}

		Capacity theoreticalCapacity = getTheoreticalCapacity();

		if (theoreticalCapacity.isUnbounded()) {
			return Capacity.UNBOUNDED;
		}

		int numberOfExistingTargetElementss = getExistingTargetElements(startingElement).size();

		// should never be negative because actual cannot exceed theoretical capacity
		return Capacity.valueOf(theoreticalCapacity.getValue() - numberOfExistingTargetElementss);
	}

	@Override
	public List<EObject> getExistingTargetElements(EObject startingElement) {

		List<Object> existingTargetObjects = AgteleEcoreUtil.getStructuralFeatureValueAsList(startingElement,
				reference);

		return existingTargetObjects.stream().filter(EObject.class::isInstance).map(EObject.class::cast)
				.collect(Collectors.toList());
	}

	@Override
	public Capacity getTheoreticalCapacity() {

		return Capacity.valueOf(reference);
	}

	@Override
	public boolean describesConnectionBetween(EObject startingElement, EObject targetElement) {

		return isValidStartingElement(startingElement)
				&& AgteleEcoreUtil.getStructuralFeatureValueAsList(startingElement, reference).contains(targetElement);
	}

	@Override
	public List<EClass> getAllClasses() {

		return Arrays.asList(startingClass, targetClass);
	}

	@Override
	public List<EReference> getAllReferences() {

		return Arrays.asList(reference);
	}

	@Override
	public EClassConnectionPathInstantiator createInstantiator(EObject startingElement,
			Collection<EObject> targetElements) {

		return new DirectEClassConnectionPathInstantiator(this, startingElement, targetElements);
	}

}