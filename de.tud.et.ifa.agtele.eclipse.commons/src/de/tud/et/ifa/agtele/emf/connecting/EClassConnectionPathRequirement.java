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

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;

import de.tud.et.ifa.agtele.emf.XSDAnyContentUtil;

/**
 * Instances of this class represent a requirement/request for a {@link EClassConnectionPath connection path}. It can be
 * passed to an instance of {@link EClassConnectionPathProvider} in order to determine all paths that satisfy this
 * requirement.
 * <p />
 * <b>Usage</b>: The most simple requirement can be specified via
 * <code>new EClassConnectionPathRequirement(anEClass)</code>. Passing this to an instance of
 * {@link EClassConnectionPathProvider} will return all potential paths that can be used to connect an instance of
 * 'anEClass' to an arbitrary other model element via containment and/or non-containment references.
 * <p />
 * The various <em>with...</em> methods can be used to further concretize a requirement.
 *
 * @author mfreund
 */
@SuppressWarnings("javadoc")
public class EClassConnectionPathRequirement {

	private final EClass requiredTargetClass;

	private EClass requiredStartingClass;

	private EObject requiredStartingElement;

	private Length requiredMaximumPathLength;

	private Capacity requiredMinimumCapacity;

	private AllowedReferenceType allowedReferenceType;

	private Collection<EReference> requiredReferences;

	public EClassConnectionPathRequirement(EClass requiredTargetClass) {

		this.requiredTargetClass = requiredTargetClass;
		requiredStartingClass = null;
		requiredStartingElement = null;
		requiredMaximumPathLength = Length.UNBOUNDED;
		requiredMinimumCapacity = Capacity.ZERO;
		allowedReferenceType = AllowedReferenceType.BOTH;
		requiredReferences = null;
	}

	/**
	 * Specify the requested starting {@link EClass} for the path.
	 * <p />
	 * If this is not specified, this means that all potential target classes may be used as starting class.
	 */
	public EClassConnectionPathRequirement withRequiredStartingClass(EClass requiredStartingClass) {

		this.requiredStartingClass = requiredStartingClass;
		return this;
	}

	/**
	 * This can be used to specify a specific starting {@link EObject}.
	 * <p />
	 * If set, this starting element will be used in order to determine the
	 * {@link EClassConnectionPath#getActualCapacity(EObject) actual capacity} of a potential path and ensure that this
	 * fulfills the {@link #getRequiredMinimumCapacity() required minimum}. If no starting element is set, only the
	 * {@link EClassConnectionPath#getTheoreticalCapacity() theoretical capacity} of a potential path will be checked.
	 */
	public EClassConnectionPathRequirement withRequiredStartingElement(EObject requiredStartingElement) {

		this.requiredStartingElement = requiredStartingElement;
		withRequiredStartingClass(requiredStartingElement.eClass());
		return this;
	}

	/**
	 * Specify the maximum allowed {@link Length}.
	 * <p />
	 * If not set, an {@link Length#UNBOUNDED unbounded} length is used as default.
	 */
	public EClassConnectionPathRequirement withRequiredMaximumPathLength(Length requiredMaximumPathLength) {

		this.requiredMaximumPathLength = requiredMaximumPathLength;
		return this;
	}

	/**
	 * Specify the minimum required {@link Capacity}.
	 * <p />
	 * If not set, a minimum capacity of {@link Capacity#ZERO 0} is used as default.
	 *
	 * @see #withRequiredStartingElement(EObject)
	 */
	public EClassConnectionPathRequirement withRequiredMinimumCapacity(Capacity requiredMinimumCapacity) {

		this.requiredMinimumCapacity = requiredMinimumCapacity;
		return this;
	}

	/**
	 * Specify the {@link AllowedReferenceType}.
	 * <p />
	 * If not set, {@link AllowedReferenceType#ALL} types are allowed.
	 */
	public EClassConnectionPathRequirement withAllowedReferenceType(AllowedReferenceType allowedReferenceType) {

		this.allowedReferenceType = allowedReferenceType;
		return this;
	}

	/**
	 * Add a list of {@link EReference EReferences} that need to be present in a path in the given order.
	 * <p />
	 * Note: This could also contain a {@link XSDAnyContentUtil#createVirtualAnyContentReference(EClass)
	 * virtualReference} representing an <em>xs:any</em>-based element.
	 */
	public EClassConnectionPathRequirement withRequiredReferences(List<EReference> references) {

		requiredReferences = references;
		return this;
	}

	/**
	 * Add a set of {@link EReference EReferences} that need to be present in a path in any order.
	 * <p />
	 * Note: This could also contain a {@link XSDAnyContentUtil#createVirtualAnyContentReference(EClass)
	 * virtualReference} representing an <em>xs:any</em>-based element.
	 */
	public EClassConnectionPathRequirement withRequiredReferences(Set<EReference> references) {

		requiredReferences = references;
		return this;
	}

	public EClass getRequiredStartingClass() {

		return requiredStartingClass;
	}

	public EObject getRequiredStartingElement() {

		return requiredStartingElement;
	}

	public EClass getRequiredTargetClass() {

		return requiredTargetClass;
	}

	public Length getRequiredMaximumPathLength() {

		return requiredMaximumPathLength;
	}

	public Capacity getRequiredMinimumCapacity() {

		return requiredMinimumCapacity;
	}

	public AllowedReferenceType getAllowedReferenceType() {

		return allowedReferenceType;
	}

	public Collection<EReference> getRequiredReferences() {

		return requiredReferences;
	}

	@Override
	public boolean equals(Object obj) {

		if (this == obj) {
			return true;
		} else if (!(obj instanceof EClassConnectionPathRequirement)) {
			return false;
		}

		EClassConnectionPathRequirement requirement = (EClassConnectionPathRequirement) obj;

		return Objects.equals(requiredTargetClass, requirement.requiredTargetClass)
				&& Objects.equals(requiredStartingClass, requirement.requiredStartingClass)
				&& Objects.equals(requiredStartingElement, requirement.requiredStartingElement)
				&& Objects.equals(requiredMaximumPathLength, requirement.requiredMaximumPathLength)
				&& Objects.equals(requiredMinimumCapacity, requirement.requiredMinimumCapacity)
				&& Objects.equals(allowedReferenceType, requirement.allowedReferenceType)
				&& Objects.equals(requiredReferences, requirement.requiredReferences);
	}

	@Override
	public int hashCode() {

		return Objects.hash(requiredStartingClass, requiredTargetClass, requiredStartingElement,
				requiredMaximumPathLength, requiredMinimumCapacity, allowedReferenceType, requiredReferences);
	}

	public EClassConnectionPathRequirement copy() {

		EClassConnectionPathRequirement copy = new EClassConnectionPathRequirement(requiredTargetClass)
				.withRequiredStartingClass(requiredStartingClass).withRequiredStartingElement(requiredStartingElement)
				.withRequiredMaximumPathLength(requiredMaximumPathLength)
				.withRequiredMinimumCapacity(requiredMinimumCapacity).withAllowedReferenceType(allowedReferenceType);

		if (requiredReferences instanceof List<?>) {
			copy.withRequiredReferences((List<EReference>) requiredReferences);
		} else if (requiredReferences instanceof Set<?>) {
			copy.withRequiredReferences((Set<EReference>) requiredReferences);
		}

		return copy;
	}

}
