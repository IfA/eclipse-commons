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

import org.eclipse.emf.ecore.EReference;

/**
 * An enum indicating the type (containment/non-containment) of references allowed in an {@link EClassConnectionPath}.
 *
 * @author mfreund
 */
@SuppressWarnings("javadoc")
public enum AllowedReferenceType {

	CONTAINMENT, NONCONTAINMENT, BOTH, UNKNOWN;

	public static AllowedReferenceType createFromReference(EReference reference) {

		if (reference == null) {
			return AllowedReferenceType.UNKNOWN;
		} else {
			return reference.isContainment() ? AllowedReferenceType.CONTAINMENT : NONCONTAINMENT;
		}
	}

	public boolean allows(EReference reference) {

		if (reference == null) {
			return false;
		} else if (this == AllowedReferenceType.BOTH) {
			return true;
		} else if (this == AllowedReferenceType.CONTAINMENT) {
			return reference.isContainment();
		} else if (this == AllowedReferenceType.NONCONTAINMENT) {
			return !reference.isContainment();
		} else {
			return false;
		}
	}
}
