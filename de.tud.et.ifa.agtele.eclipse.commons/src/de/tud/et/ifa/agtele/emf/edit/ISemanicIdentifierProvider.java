package de.tud.et.ifa.agtele.emf.edit;

import java.util.Collection;

public interface ISemanicIdentifierProvider {
	Collection<String> getSemanicIdentifiers(Object element);
}
