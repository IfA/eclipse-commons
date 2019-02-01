package de.tud.et.ifa.agtele.emf.edit;

import java.util.Collection;
import java.util.Collections;

import org.eclipse.emf.ecore.EObject;

public interface IReferencingIdentificationStringProvider {
	default String getReferencingIdentificationString (Object element) {
		Collection<String> ids = this.getReferencingIdentificationStrings(element);
		if (!ids.isEmpty()) {
			return ids.iterator().next();
		}
		return null;
	};

	Collection<String> getReferencingIdentificationStrings(Object element);
}
