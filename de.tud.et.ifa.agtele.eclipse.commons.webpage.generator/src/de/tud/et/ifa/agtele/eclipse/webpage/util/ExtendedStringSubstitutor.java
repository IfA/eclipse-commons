package de.tud.et.ifa.agtele.eclipse.webpage.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;

public class ExtendedStringSubstitutor extends DefaultStringSubstitutor {
	public static final String VAR_PREFIX = "${";
	public static final String VAR_SUFFIX = "}";
	public static final String VAR_ARG = ":";
	List<SubstitutionVariable> variables = new ArrayList<>();
	
	public ExtendedStringSubstitutor () {
		super();
	}
	
	public ExtendedStringSubstitutor (ResultReporter reporter) {
		super(reporter);		
		this.factorVariables();
	}
	
	public ExtendedStringSubstitutor (ExtendedStringSubstitutor copy) {
		this(copy != null ? copy.reporter : null);
		if (copy != null) {
			this.variables.addAll(copy.variables);
		}
	}
	
	protected void factorVariables() {}
	
	public void addVariable(SubstitutionVariable var) {
		this.variables.add(var);
	}
	
	protected String applyVariable(String name, String parameter) {
		for (SubstitutionVariable var : this.variables) {
			if (var.getName().equals(name)) {
				String result = var.getValue(parameter);
				if (result != null) {
					return result;
				}				
			}
		}
		return null;
	}
	
	@Override
	public String substitute (String original) {
		String result = original;
		
		while (result.contains(VAR_PREFIX)) {
			boolean substitutedVariable = false, foundVariable = false, foundRecursiveVariable = false;
			int index = 0;
			
			while (result.indexOf(VAR_PREFIX, index) >= 0) {
				index = result.indexOf(VAR_PREFIX, index);
				int suffixIndex = result.indexOf(VAR_SUFFIX, index),
					nextIndex = result.indexOf(VAR_PREFIX, index+1);
				
				if (suffixIndex < 0) {
					break;
				}
				
				if (suffixIndex < nextIndex || nextIndex<0) {
					//regular variable to Replace
					String variableName = "", parameter = "";
					int separatorIndex = result.indexOf(VAR_ARG, index);
					if (separatorIndex < 0 || separatorIndex > suffixIndex) {
						variableName = result.substring(index + VAR_PREFIX.length(), suffixIndex);
					} else {
						variableName = result.substring(index + VAR_PREFIX.length(), separatorIndex);
						parameter = result.substring(separatorIndex + VAR_ARG.length(), suffixIndex);
					}
					String newContent = this.applyVariable(variableName, parameter);
					if (newContent != null) {
						result = result.substring(0, index) + newContent + result.substring(suffixIndex + VAR_SUFFIX.length());
						substitutedVariable = true;
					} else {
						foundVariable = true;
						index = suffixIndex;
					}					
				} else {
					//recursive variable
					foundRecursiveVariable = true;
					index = nextIndex;
					continue;
				}				
			}
			if (foundVariable) {
				String newResult = result;
				try {
					newResult = this.manager.performStringSubstitution(result, false);
				} catch (CoreException e) {
					this.reporter.addError(e);
				}
				if (newResult.equals(result)) {
					try {
						newResult = this.manager.performStringSubstitution(result, true);
					} catch (CoreException e) {
						this.reporter.addError(e);
					}
					result = newResult;
					break;
				}
				result = newResult;
				substitutedVariable = true;
			} 
			if (substitutedVariable && foundRecursiveVariable) {
				continue;
			} else {
				break;
			}
		}		
		
		return result;
	}
	
	public static abstract class SubstitutionVariable {
		
		protected String name;

		public SubstitutionVariable (String name) {
			this.name = name;
		}
		
		public String getValue(String parameter) {
			return "";
		}
		
		public String getName() {
			return this.name;
		}
	}
}
