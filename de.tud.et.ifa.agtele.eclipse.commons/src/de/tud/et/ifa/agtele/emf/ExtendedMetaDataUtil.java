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
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage.Registry;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.util.BasicExtendedMetaData;
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

	/**
	 * The single instance of {@link ExtendedMetaData} that we use to create/read/set metadata.
	 */
	private static ExtendedMetaData extendedMetaData;

	private ExtendedMetaDataUtil() {

	}

	/**
	 * This initializes the {@link #extendedMetaData}.
	 */
	private static synchronized void initMetaData() {

		ExtendedMetaDataUtil.extendedMetaData = new BasicExtendedMetaData(Registry.INSTANCE);

	}

	/**
	 * Returns the {@link #extendedMetaData}.
	 *
	 * @return the {@link #extendedMetaData}}
	 */
	private static ExtendedMetaData getMetaData() {

		if (ExtendedMetaDataUtil.extendedMetaData == null) {
			ExtendedMetaDataUtil.initMetaData();
		}

		return ExtendedMetaDataUtil.extendedMetaData;
	}

	/**
	 * Returns whether the given {@link EStructuralFeature} represents a {@link FeatureMap} created based on an 'xs:any'
	 * element.
	 *
	 * @param feature
	 *            The {@link EStructuralFeature} to check.
	 * @return '<em>true</em>' if equipped with 'xs:any'-content.
	 */
	public static boolean isAnyContentFeature(EStructuralFeature feature) {

		if (feature instanceof EAttribute) {
			return isAnyContentAttribute((EAttribute) feature);
		} else if (feature instanceof EReference) {
			return isAnyContentReference((EReference) feature);
		} else {
			return false;
		}
	}

	private static boolean isAnyContentAttribute(EAttribute eAttribute) {

		return "any".equals(eAttribute.getName())
				&& eAttribute.getEAttributeType().equals(EcorePackage.Literals.EFEATURE_MAP_ENTRY);
	}

	/**
	 * Returns whether the given {@link EReference} was {@link #createVirtualAnyContentReference(EClass) created} based
	 * on an {@link #isAnyContentAttribute(EAttribute)}.
	 *
	 * @param eReference
	 *            The {@link EReference} to check.
	 * @return '<em>true</em>' if created based on an attribute with 'xs:any'-content.
	 */
	private static boolean isAnyContentReference(EReference eReference) {

		if (eReference == null) {
			return false;
		}

		EReference anyContentReference = getOrCreateVirtualAnyContentReference(
				eReference.getEContainingClass().getEPackage().getNsURI(), eReference.getName());
		return eReference.equals(anyContentReference);
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

	/**
	 * Returns an {@link #isAnyContentAttribute(EAttribute) 'any-content attribute'} for the given {@link EClass}.
	 *
	 * @param parentEClass
	 *            The {@link EClass}.
	 * @return A suitable {@link EAttribute} or an empty optional if no such attribute exists.
	 */
	public static Optional<EAttribute> getAnyContentAttribute(EClass parentEClass) {

		return parentEClass.getEAllAttributes().stream().filter(ExtendedMetaDataUtil::isAnyContentAttribute).findAny();
	}

	/**
	 * This allows to add the given <em>childElement</em> as child of the given <em>parentElement</em> that represents
	 * an 'xs:any' element.
	 * <p />
	 * Therefore, a new (virtual) reference is created on the fly.
	 * <p />
	 * Note: A suitable {@link #isAnyContentAttribute(EAttribute) 'any-content attribute'} is determined automatically
	 * before redirecting to {@link #addAnyConent(EObject, EStructuralFeature, EObject)}.
	 *
	 * @param parentElement
	 *            The parent {@link EObject}.
	 * @param childElement
	 *            The child {@link EObject} to add.
	 * @return '<em>true</em>' if the element was successfully added; '<em>false</em>' otherwise.
	 */
	public static boolean addAnyConent(EObject parentElement, EObject childElement) {

		return ExtendedMetaDataUtil.addAnyConent(parentElement, Arrays.asList(childElement));
	}

	/**
	 * This allows to add the given <em>childElements</em> as children of the given <em>parentElement</em> that
	 * represents an 'xs:any' element.
	 * <p />
	 * Therefore, a new (virtual) reference is created on the fly.
	 * <p />
	 * Note: A suitable {@link #isAnyContentAttribute(EAttribute) 'any-content attribute'} is determined automatically
	 * before redirecting to {@link #addAnyConent(EObject, EStructuralFeature, EObject)}.
	 *
	 * @param parentElement
	 *            The parent {@link EObject}.
	 * @param childElements
	 *            The child {@link EObject EObjects} to add.
	 * @return '<em>true</em>' if the elements were successfully added; '<em>false</em>' otherwise.
	 */
	public static boolean addAnyConent(EObject parentElement, Collection<EObject> childElements) {

		Optional<EAttribute> anyContentAttribute = getAnyContentAttribute(parentElement.eClass());

		if (!anyContentAttribute.isPresent()) {
			return false;
		} else {
			return ExtendedMetaDataUtil.addAnyConent(parentElement, anyContentAttribute.get(), childElements);
		}

	}

	/**
	 * This allows to add the given <em>childElement</em> as child of the given <em>parentElement</em> via the given
	 * {@link EStructuralFeature} that represents a {@link FeatureMap} created based on an 'xs:any' element.
	 * <p />
	 * Therefore, a new (virtual) reference is created on the fly
	 *
	 * @param parentElement
	 *            The parent {@link EObject}.
	 * @param anyContentFeature
	 *            The {@link EStructuralFeature} representing the 'xs:any' element.
	 * @param childElement
	 *            The child {@link EObject} to add.
	 * @return '<em>true</em>' if the element was successfully added; '<em>false</em>' otherwise.
	 */
	public static boolean addAnyConent(EObject parentElement, EStructuralFeature anyContentFeature,
			EObject childElement) {

		return ExtendedMetaDataUtil.addAnyConent(parentElement, anyContentFeature, Arrays.asList(childElement));
	}

	/**
	 * This allows to add the given <em>childElements</em> as children of the given <em>parentElement</em> via the given
	 * {@link EStructuralFeature} that represents a {@link FeatureMap} created based on an 'xs:any' element.
	 * <p />
	 * Therefore, a new (virtual) reference is created on the fly
	 *
	 * @param parentElement
	 *            The parent {@link EObject}.
	 * @param anyContentFeature
	 *            The {@link EAttribute} representing the 'xs:any' element.
	 * @param childElements
	 *            The child {@link EObject EObjects} to add.
	 * @return '<em>true</em>' if the elements were successfully added; '<em>false</em>' otherwise.
	 */
	public static boolean addAnyConent(EObject parentElement, EStructuralFeature anyContentFeature,
			Collection<EObject> childElements) {

		Optional<EAttribute> anyContentAttribute = getAnyContentAttributeFor(anyContentFeature);

		if (anyContentAttribute.isPresent()) {
			return addAnyContent(parentElement, anyContentAttribute.get(), childElements);
		} else {
			return false;
		}
	}

	private static Optional<EAttribute> getAnyContentAttributeFor(EStructuralFeature anyContentFeature) {

		if (!isAnyContentFeature(anyContentFeature)) {
			return Optional.empty();
		}

		if (anyContentFeature instanceof EAttribute) {
			return Optional.of((EAttribute) anyContentFeature);

		} else if (anyContentFeature instanceof EReference) {
			return getAnyContentAttribute(anyContentFeature.getEContainingClass());

		} else {
			return Optional.empty();
		}
	}

	private static boolean addAnyContent(EObject parentElement, EAttribute anyContentAttribute,
			Collection<EObject> childElements) {

		try {
			FeatureMap rootMixed = (FeatureMap) parentElement.eGet(anyContentAttribute);

			EReference virtualReference = ExtendedMetaDataUtil
					.getOrCreateVirtualAnyContentReference(anyContentAttribute);

			List<Internal> featureMapEntries = childElements.stream()
					.map(c -> FeatureMapUtil.createRawEntry(virtualReference, c)).collect(Collectors.toList());

			// using 'addAll' throws an exception for any weird reason
			for (Internal featureMapEntry : featureMapEntries) {
				rootMixed.add(anyContentAttribute, featureMapEntry);
			}

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

	/**
	 * Create a new (virtual) reference that can be used to add content to the given {@link EClass} via an attribute
	 * that represents a {@link FeatureMap} created based on an 'xs:any' element.
	 *
	 * @param eClass
	 *            The {@link EClass} based on which the reference shall be created.
	 * @return The created {@link EReference}.
	 */
	public static EReference getOrCreateVirtualAnyContentReference(EClass eClass) {

		Optional<EAttribute> anyContentAttribute = getAnyContentAttribute(eClass);
		if (anyContentAttribute.isPresent()) {
			return getOrCreateVirtualAnyContentReference(anyContentAttribute.get());
		} else {
			return null;
		}
	}

	/**
	 * Create a new (virtual) reference that can be used to add content to the given {@link EAttribute} that represents
	 * a {@link FeatureMap} created based on an 'xs:any' element.
	 *
	 * @param anyContentAttribute
	 *            The {@link EAttribute} based on which the reference shall be created.
	 * @return The created {@link EReference}.
	 */
	public static EReference getOrCreateVirtualAnyContentReference(EAttribute anyContentAttribute) {

		if (!isAnyContentAttribute(anyContentAttribute)) {
			return null;
		}

		String referenceNsURI = anyContentAttribute.getEContainingClass().getEPackage().getNsURI();
		String referenceName = anyContentAttribute.getName();

		return getOrCreateVirtualAnyContentReference(referenceNsURI, referenceName);
	}

	private static EReference getOrCreateVirtualAnyContentReference(String referenceNsURI, String referenceName) {

		EReference virtualReference = (EReference) ExtendedMetaDataUtil.getMetaData().demandFeature(referenceNsURI,
				referenceName, true);

		// use 'EObject' as common super type for all EClasses
		EClass referenceType = EcorePackage.Literals.EOBJECT;
		virtualReference.setEType(referenceType);

		virtualReference.setContainment(true);
		return virtualReference;
	}

}
