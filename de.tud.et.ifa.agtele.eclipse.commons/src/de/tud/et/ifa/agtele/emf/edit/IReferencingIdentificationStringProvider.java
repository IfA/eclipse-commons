package de.tud.et.ifa.agtele.emf.edit;

import java.util.Collection;
import java.util.Collections;

import org.eclipse.emf.ecore.EObject;

public interface IReferencingIdentificationStringProvider {
	String getReferencingIdentificationString (Object element);

	default Collection<String> getReferencingIdentificationStrings(EObject eObject) {
		return Collections.singleton(this.getReferencingIdentificationString(eObject));
	}
}
