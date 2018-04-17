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
package de.tud.et.ifa.agtele.emf.exceptions;

public class SorryNoOtherWorkaroundException extends EMFIsStupidException {
	private static final long serialVersionUID = 3045557510623315131L;

	@Override
	public String toString() {
		return "Sorry, but " + super.toString();
	}
}
