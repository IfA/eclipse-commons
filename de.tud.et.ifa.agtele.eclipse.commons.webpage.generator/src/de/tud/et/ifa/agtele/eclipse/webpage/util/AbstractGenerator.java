package de.tud.et.ifa.agtele.eclipse.webpage.util;

import org.eclipse.core.runtime.IProgressMonitor;

import de.tud.et.ifa.agtele.ResultReporter;

public abstract class AbstractGenerator extends ResultReporter {

	public abstract void generate (IProgressMonitor monitor);
}
