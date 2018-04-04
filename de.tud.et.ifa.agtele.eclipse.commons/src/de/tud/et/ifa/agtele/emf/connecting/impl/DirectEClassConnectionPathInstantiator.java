/**
 *
 */
package de.tud.et.ifa.agtele.emf.connecting.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;

import de.tud.et.ifa.agtele.emf.AgteleEcoreUtil;
import de.tud.et.ifa.agtele.emf.XSDAnyContentUtil;
import de.tud.et.ifa.agtele.emf.connecting.EClassConnectionPathInstantiator;

/**
 * An {@link EClassConnectionPathInstantiator} that is able to instantiate {@link DirectEClassConnectionPath
 * DirectEClassConnectionPaths}.
 *
 * @author mfreund
 */
@SuppressWarnings("javadoc")
public class DirectEClassConnectionPathInstantiator extends EClassConnectionPathInstantiator {

	public DirectEClassConnectionPathInstantiator(DirectEClassConnectionPath pathToInstantiate, EObject startingElement,
			Collection<EObject> targetElements) {

		super(pathToInstantiate, startingElement, targetElements);
	}

	@Override
	protected void doInstantiate() {

		EReference reference = ((DirectEClassConnectionPath) pathToInstantiate).getReference();

		if (XSDAnyContentUtil.isAnyContentFeature(pathToInstantiate.getStartingClass(), reference)) {
			instantiateViaAnyContentReference();
		} else {
			instantiateViaNormalReference(reference);
		}
	}

	private void instantiateViaAnyContentReference() {

		XSDAnyContentUtil.addAnyConent(startingElement, targetElements);
	}

	private void instantiateViaNormalReference(EReference reference) {

		if (reference.isMany()) {
			instantiateViaManyValuedReference(reference);
		} else {
			instantiateViaSingleValuedReference(reference);
		}
	}

	private void instantiateViaManyValuedReference(EReference manyValuedReference) {

		List<Object> existingValues = AgteleEcoreUtil.getStructuralFeatureValueAsList(startingElement,
				manyValuedReference);

		List<Object> newValues = new ArrayList<>();
		newValues.addAll(existingValues);
		newValues.addAll(targetElements);

		startingElement.eSet(manyValuedReference, newValues);
	}

	private void instantiateViaSingleValuedReference(EReference singleValuedReference) {

		// We know that there is only a single target element because the 'actual capacity' was already checked
		EObject targetElement = targetElements.iterator().next();

		startingElement.eSet(singleValuedReference, targetElement);

	}

}
