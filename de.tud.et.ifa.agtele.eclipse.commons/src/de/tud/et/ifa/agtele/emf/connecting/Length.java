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

/**
 * Instances of this class describe the 'length' of an {@link EClassConnectionPath} (via how many references this path
 * leads from the {@link EClassConnectionPath#getStartingClass() startingClass} to the
 * {@link EClassConnectionPath#getTargetClass() targetClass}).
 * <p />
 * This is basically just a wrapper for a simple integer. However, three special static instances
 * {@link #NO_CONNECTION}, {@link #DIRECT_CONNECTION} and {@link #UNBOUNDED} are provided to simplify handling of
 * special cases.
 *
 * @author mfreund
 */
@SuppressWarnings("javadoc")
public class Length extends UnsignedIntegerWithUnbounded {

	public static final Length NO_CONNECTION = new Length(0);

	public static final Length DIRECT_CONNECTION = new Length(1);

	public static final Length UNBOUNDED = new Length(-1);

	private Length(int value) {

		super(value);
	}

	/**
	 * Returns a {@link Length} instance representing the given value.
	 */
	public static Length valueOf(int value) {

		if (Length.UNBOUNDED.getValue() == value) {
			return Length.UNBOUNDED;
		} else if (Length.NO_CONNECTION.getValue() == value) {
			return Length.NO_CONNECTION;
		} else if (Length.DIRECT_CONNECTION.getValue() == value) {
			return Length.DIRECT_CONNECTION;
		} else {
			return new Length(value);
		}
	}

	/**
	 * Returns a {@link Length} instance representing the size of the given {@link Collection}.
	 */
	public static Length valueOf(Collection<?> collection) {

		return Length.valueOf(collection.size());
	}

	public boolean isDirectConnection() {

		return Length.DIRECT_CONNECTION.equals(this);
	}

	public boolean isNoConnection() {

		return Length.NO_CONNECTION.equals(this);
	}

}
