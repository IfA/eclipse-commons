/*******************************************************************************
 * Copyright (C) 2014-2018 Matthias Freund and others, Institute of Automation, TU Dresden
 *
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
/**
 *
 */
package de.tud.et.ifa.agtele.emf;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.util.ExtendedMetaData;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.ecore.util.FeatureMap.Entry.Internal;
import org.eclipse.emf.ecore.util.FeatureMapUtil;

/**
 * A utility class to simplify handling of {@link ExtendedMetaData} elements that result from converting an XSD-based to
 * an EMF-based metamodel.
 *
 * @author mfreund
 */
public class ExtendedMetaDataUtil {

	private static final String NAME_OF_ANY_CONTENT_FEATURE = "any";

	private ExtendedMetaDataUtil() {

	}

	private static ExtendedMetaData getMetaData() {

		return ExtendedMetaData.INSTANCE;
	}

	/**
	 * Returns whether the given {@link EStructuralFeature} can be used to add 'xs:any'-content to an instance of the
	 * given {@link EClass}.
	 *
	 * @param eClass
	 *            The {@link EClass} that allows 'xs:any'-content.
	 * @param feature
	 *            The {@link EStructuralFeature} to check.
	 * @return '<em>true</em>' if equipped with 'xs:any'-content.
	 */
	public static boolean isAnyContentFeature(EClass eClass, EStructuralFeature feature) {

		if (feature instanceof EAttribute) {
			return isAnyContentAttribute((EAttribute) feature);
		} else if (feature instanceof EReference) {
			return isAnyContentReference(eClass, (EReference) feature);
		} else {
			return false;
		}
	}

	private static boolean isAnyContentAttribute(EAttribute eAttribute) {

		return eAttribute != null && NAME_OF_ANY_CONTENT_FEATURE.equals(eAttribute.getName())
				&& EcorePackage.Literals.EFEATURE_MAP_ENTRY.equals(eAttribute.getEAttributeType());
	}

	private static boolean isAnyContentReference(EClass eClass, EReference eReference) {

		return getAnyContentAttributeFor(eClass, eReference).isPresent();
	}

	/**
	 * Returns whether the given class is based on an XSD element with 'xs:any'-content.
	 *
	 * @param parentClass
	 *            The {@link EClass} to check.
	 * @return '<em>true</em>' if equipped with 'xs:any'-content.
	 */
	public static boolean allowsAnyContent(EClass parentClass) {

		return getAnyContentAttribute(parentClass).isPresent();
	}

	private static Optional<EAttribute> getAnyContentAttribute(EClass parentEClass) {

		return parentEClass.getEAllAttributes().stream().filter(ExtendedMetaDataUtil::isAnyContentAttribute).findAny();
	}

	/**
	 * This allows to add the given <em>childElement</em> as child of the given <em>parentElement</em> that represents
	 * an 'xs:any'-content element.
	 *
	 * @see #getOrCreateVirtualAnyContentReference(EClass)
	 *
	 * @param parentElement
	 *            The parent {@link EObject}.
	 * @param childElement
	 *            The child {@link EObject} to add.
	 * @return '<em>true</em>' if the element was successfully added; '<em>false</em>' otherwise (e.g. if the given
	 *         parentElement does not allow 'xs:any'-content).
	 */
	public static boolean addAnyConent(EObject parentElement, EObject childElement) {

		return ExtendedMetaDataUtil.addAnyConent(parentElement, Arrays.asList(childElement));
	}

	/**
	 * This allows to add the given <em>childElements</em> as children of the given <em>parentElement</em> that
	 * represents an 'xs:any'-content element.
	 *
	 * @see #getOrCreateVirtualAnyContentReference(EClass)
	 *
	 * @param parentElement
	 *            The parent {@link EObject}.
	 * @param childElements
	 *            The child {@link EObject EObjects} to add.
	 * @return '<em>true</em>' if the elements were successfully added; '<em>false</em>' otherwise (e.g. if the given
	 *         parentElement does not allow 'xs:any'-content).
	 */
	public static boolean addAnyConent(EObject parentElement, Collection<EObject> childElements) {

		Optional<EAttribute> anyContentAttribute = getAnyContentAttribute(parentElement.eClass());

		if (anyContentAttribute.isPresent()) {
			return addAnyContent(parentElement, anyContentAttribute.get(), childElements);
		} else {
			return false;
		}
	}

	private static Optional<EAttribute> getAnyContentAttributeFor(EClass eClass, EStructuralFeature anyContentFeature) {

		// if the 'anyContentFeature' is a reference, we try to retrieve the affiliated attribute
		EStructuralFeature affiliation = ExtendedMetaDataUtil.getMetaData().getAffiliation(eClass, anyContentFeature);

		if (affiliation instanceof EAttribute && isAnyContentAttribute((EAttribute) affiliation)) {
			return Optional.of((EAttribute) affiliation);
		} else {
			return Optional.empty();
		}
	}

	private static boolean addAnyContent(EObject parentElement, EAttribute anyContentAttribute,
			Collection<EObject> childElements) {

		if (!isAnyContentAttribute(anyContentAttribute)) {
			return false;
		}

		try {
			FeatureMap rootMixed = (FeatureMap) parentElement.eGet(anyContentAttribute);

			for (EObject childElement : childElements) {

				EReference virtualReference = ExtendedMetaDataUtil
						.getOrCreateVirtualAnyContentReference(parentElement.eClass(), childElement.eClass());

				Internal featureMapEntry = FeatureMapUtil.createRawEntry(virtualReference, childElement);

				rootMixed.add(anyContentAttribute, featureMapEntry);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		return true;

	}

	/**
	 * If the given {@link EClass} is based on an XSD-element that allows 'xs:any'-content, returns a (virtual)
	 * {@link EReference} that can be used to add such 'xs:any'-content.
	 * <p />
	 * Note: The returned reference can be used as a convenient way to add 'any'-content via the usual
	 * {@link EObject#eSet(EStructuralFeature, Object)} functionality instead of heaving to rely on special
	 * functionality that directly operates on the required {@link FeatureMap}
	 * <p />
	 * Note: The returned reference will have the name 'any' and the {@link EReference#getEReferenceType()
	 * referenceType} {@link EObject}.
	 *
	 * @see #getOrCreateVirtualAnyContentReference(EClass, EClass)
	 *
	 * @param eClass
	 *            The {@link EClass} based on which the reference shall be created.
	 * @return The created {@link EReference}.
	 */
	public static EReference getOrCreateVirtualAnyContentReference(EClass eClass) {

		return getOrCreateVirtualAnyContentReference(eClass, NAME_OF_ANY_CONTENT_FEATURE,
				EcorePackage.Literals.EOBJECT);
	}

	/**
	 * If the given {@link EClass} is based on an XSD-element that allows 'xs:any'-content, returns a (virtual)
	 * {@link EReference} that can be used to add such 'xs:any'-content.
	 * <p />
	 * Note: The returned reference can be used as a convenient way to add 'any'-content via the usual
	 * {@link EObject#eSet(EStructuralFeature, Object)} functionality instead of heaving to rely on special
	 * functionality that directly operates on the required {@link FeatureMap}
	 * <p />
	 * Note: The returned reference will have the name and type of the given <em>eReferenceType</em>.
	 *
	 * @see #getOrCreateVirtualAnyContentReference(EClass)
	 *
	 * @param eClass
	 *            The {@link EClass} based on which the reference shall be created.
	 * @param eReferenceType
	 *            The {@link EClass} used as {@link EReference#getEReferenceType() referenceType}.
	 * @return The created {@link EReference}.
	 */
	public static EReference getOrCreateVirtualAnyContentReference(EClass eClass, EClass eReferenceType) {

		String referenceName = eReferenceType.getName();

		return getOrCreateVirtualAnyContentReference(eClass, referenceName, eReferenceType);

	}

	private static EReference getOrCreateVirtualAnyContentReference(EClass eClass, String referenceName,
			EClass eReferenceType) {

		Optional<EAttribute> anyContentAttribute = getAnyContentAttribute(eClass);

		if (!anyContentAttribute.isPresent()) {
			return null;
		}

		return getOrCreateVirtualAnyContentReference(referenceName, eReferenceType);

	}

	private static EReference getOrCreateVirtualAnyContentReference(String referenceName, EClass referenceType) {

		String referenceNsURI = referenceType.getEPackage().getNsURI();

		EReference virtualReference = (EReference) ExtendedMetaDataUtil.getMetaData().demandFeature(referenceNsURI,
				referenceName, true);

		if (virtualReference != null) {

			// explicitly setting the reference type instead of leaving the generic 'EObject' helps with serialization:
			// instead of unnecessarily serializing to <MyType xsi:type="MyType" .../>, this will serialize to <MyType
			// .../>
			virtualReference.setEType(referenceType);
		}

		return virtualReference;
	}

}
