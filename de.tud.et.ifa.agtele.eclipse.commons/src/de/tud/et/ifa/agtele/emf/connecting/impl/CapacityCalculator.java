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

import de.tud.et.ifa.agtele.emf.connecting.Capacity;

/**
 * A util class that helps calculating with {@link Capacity Capacities}.
 *
 * @author mfreund
 */
@SuppressWarnings("javadoc")
public class CapacityCalculator {

	private CapacityCalculator() {

	}

	public static Capacity addSequentialCapacities(Capacity... capacities) {

		return CapacityCalculator.addSequentialCapacities(Arrays.asList(capacities));
	}

	public static Capacity addSequentialCapacities(Collection<Capacity> capacities) {

		if (CapacityCalculator.containsZeroCapacity(capacities)) {
			return Capacity.ZERO;
		} else if (CapacityCalculator.containsUnboundedCapacity(capacities)) {
			return Capacity.UNBOUNDED;
		} else {
			return Capacity.valueOf(CapacityCalculator.multiplyValuesOfCapacities(capacities));
		}

	}

	public static Capacity addParallelCapacities(Capacity... capacities) {

		return CapacityCalculator.addParallelCapacities(Arrays.asList(capacities));
	}

	public static Capacity addParallelCapacities(Collection<Capacity> capacities) {

		if (CapacityCalculator.containsUnboundedCapacity(capacities)) {
			return Capacity.UNBOUNDED;
		} else {
			return Capacity.valueOf(CapacityCalculator.addValuesOfCapacities(capacities));
		}

	}

	public static Capacity subtract(Capacity minuend, Capacity subtrahend) {

		if (minuend.isUnbounded()) {
			return Capacity.UNBOUNDED;
		} else if (minuend.isSufficientFor(subtrahend)) {
			return Capacity.valueOf(minuend.getValue() - subtrahend.getValue());
		} else {
			return Capacity.ZERO;
		}
	}

	private static boolean containsZeroCapacity(Collection<Capacity> capacities) {

		return capacities.stream().anyMatch(Capacity::isZero);
	}

	private static boolean containsUnboundedCapacity(Collection<Capacity> capacities) {

		return capacities.stream().anyMatch(Capacity::isUnbounded);
	}

	private static int addValuesOfCapacities(Collection<Capacity> capacities) {

		return capacities.stream().mapToInt(Capacity::getValue).reduce(0, Math::addExact);
	}

	private static int multiplyValuesOfCapacities(Collection<Capacity> capacities) {

		return capacities.stream().mapToInt(Capacity::getValue).reduce(1, Math::multiplyExact);
	}

}
