package de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.AbstractKeyVal;

public abstract class WebPageModelUtils {
	public static <T extends AbstractKeyVal> List<T> uniqueifyNames (List<T> values) {
		HashMap<String, T> result = new LinkedHashMap<>();
		for (T val : values) {
			if (val.getName() != null && !val.getName().isBlank() && !result.containsKey(val.getName())) {
				result.put(val.getName(), val);				
			}
		}				
		return new ArrayList<>(result.values());
	}
	
	public static <T extends AbstractKeyVal> List<T> applyKeyVal(List<T> values, List<T> apply) {
		HashMap<String, T> result = new LinkedHashMap<>();
		for (T val : values) {
			if (val.getName() != null && !val.getName().isBlank() && !result.containsKey(val.getName())) {
				result.put(val.getName(), val);				
			}
		}		
		for (T val : apply) {
			if (val.getName() != null && !val.getName().isBlank() && !result.containsKey(val.getName())) {
				result.put(val.getName(), val);				
			}
		}	
		return new ArrayList<>(result.values());
	}
	
	public static String getUrlSafeName (String name) {
		if (name != null) {
			return name.replaceAll("[^\\w\\.\\-_~]", "");
		}
		return null;
	}
	
	public static boolean isAbsolutePath(String path) {
		if (path != null && !path.isBlank()) {
			return path.charAt(0) == '/' || path.charAt(0) == '$' || path.charAt(0) == '\\';
		}
		return false;
	}
}
