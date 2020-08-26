package de.tud.et.ifa.agtele.eclipse.webpage.util;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.variables.IStringVariableManager;
import org.eclipse.core.variables.VariablesPlugin;

public class DefaultStringSubstitutor implements IStringSubstitutor {
	protected IStringVariableManager manager;
	protected ResultReporter reporter;
	
	public DefaultStringSubstitutor() {
		this.manager = VariablesPlugin.getDefault().getStringVariableManager();
		this.reporter = new ResultReporter() {};
	}
	
	public DefaultStringSubstitutor(ResultReporter reporter) {
		this();
		this.reporter = reporter;
	}
	@Override
	public String substitute(String original) {
		try {
			return this.manager.performStringSubstitution(original);
		} catch (CoreException e) {
			this.reporter.addError(e);
		}
		return original;
	}
	
	public void setResultReporter (ResultReporter reporter) {
		this.reporter = reporter;
	}
}
