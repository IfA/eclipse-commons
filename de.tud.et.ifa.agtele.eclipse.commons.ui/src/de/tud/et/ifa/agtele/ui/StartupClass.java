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

	@Override
	public void earlyStartup() {

		GeneratedCodeChangedListener.init();
	}

}
