package de.tud.et.ifa.agtele.emf.sanitizer;

import java.util.ArrayList;
import java.util.Collection;

public interface ModelSanitizerFactory {
	default Collection<ModelSanitizer> createSanitizers () {
		ArrayList<ModelSanitizer> result = new ArrayList<>();
		result.add(new ModelSanitizer.InternalIDSanitizer());
		return result;
	}
}
