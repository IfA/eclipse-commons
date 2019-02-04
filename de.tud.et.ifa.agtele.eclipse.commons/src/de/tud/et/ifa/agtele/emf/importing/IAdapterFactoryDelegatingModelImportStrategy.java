package de.tud.et.ifa.agtele.emf.importing;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.ecore.EObject;

import de.tud.et.ifa.agtele.emf.AgteleEcoreUtil;

public interface IAdapterFactoryDelegatingModelImportStrategy extends IDelegatingModelImportStrategy {

	default public Adapter getAdapter (EObject eObject, Object type) {
		return AgteleEcoreUtil.getAdapter(eObject, type);
	}
}
