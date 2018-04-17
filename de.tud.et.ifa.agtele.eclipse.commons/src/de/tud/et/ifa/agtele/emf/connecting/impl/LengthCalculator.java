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

import java.util.Arrays;
import java.util.Collection;

import de.tud.et.ifa.agtele.emf.connecting.Length;

/**
 * A util class that helps calculating with {@link Length Lengths}.
 *
 * @author mfreund
 */
@SuppressWarnings("javadoc")
public class LengthCalculator {

	private LengthCalculator() {

	}

	public static Length add(Length... lengths) {

		return LengthCalculator.add(Arrays.asList(lengths));
	}

	public static Length add(Collection<Length> lengths) {

		if (LengthCalculator.containsUnboundedLength(lengths)) {
			return Length.UNBOUNDED;
		} else if (LengthCalculator.containsNoConnectionLength(lengths)) {
			return Length.NO_CONNECTION;
		} else {
			return Length.valueOf(LengthCalculator.addValuesOfLengths(lengths));
		}
	}

	private static boolean containsUnboundedLength(Collection<Length> lengths) {

		return lengths.stream().anyMatch(Length::isUnbounded);
	}

	private static boolean containsNoConnectionLength(Collection<Length> lengths) {

		return lengths.stream().anyMatch(Length::isNoConnection);
	}

	private static int addValuesOfLengths(Collection<Length> lengths) {

		return lengths.stream().mapToInt(Length::getValue).reduce(0, Math::addExact);
	}

}
