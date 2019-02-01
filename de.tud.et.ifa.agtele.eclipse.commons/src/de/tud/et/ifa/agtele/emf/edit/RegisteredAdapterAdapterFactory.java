package de.tud.et.ifa.agtele.emf.edit;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;
import org.eclipse.emf.ecore.EObject;

import de.tud.et.ifa.agtele.emf.AgteleEcoreUtil;

public class RegisteredAdapterAdapterFactory extends AdapterFactoryImpl {
	@Override
	public boolean isFactoryForType(Object type) {
		return true;
	}
	
	@Override
	protected Adapter createAdapter(Notifier target, Object type) {
		if (target instanceof EObject) {
			return AgteleEcoreUtil.getAdapter((EObject)target, type);
		}
		return null;
	}
}
