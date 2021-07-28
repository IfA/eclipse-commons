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
package de.tud.et.ifa.agtele.ui;

import org.eclipse.ui.IStartup;

import de.tud.et.ifa.agtele.ui.listeners.GeneratedCodeChangedListener;

/**
 * A class that implements {@link IStartup} and is used as implementation for the 'org.eclipse.ui.startup' extension
 * point.
 * <p />
 * Currently, it is only used to initialize the {@link GeneratedCodeChangedListener}.
 *
 * @author mfreund
 */
public class StartupClass implements IStartup {

	public StartupClass() {
	}
	
	@Override
	public void earlyStartup() {

		GeneratedCodeChangedListener.init();
	}

}
