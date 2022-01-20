/*******************************************************************************
 * Copyright (C) 2016-2018 Institute of Automation, TU Dresden.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Institute of Automation, TU Dresden - initial API and implementation
 ******************************************************************************/
package de.tud.et.ifa.agtele.emf;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.codegen.ecore.genmodel.GenBase;
import org.eclipse.emf.codegen.ecore.genmodel.GenClass;
import org.eclipse.emf.codegen.ecore.genmodel.GenClassifier;
import org.eclipse.emf.codegen.ecore.genmodel.GenPackage;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EParameter;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.ExtendedMetaData;
import org.eclipse.emf.edit.command.CommandParameter;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.domain.IEditingDomainProvider;
import org.eclipse.emf.edit.provider.AdapterFactoryItemDelegator;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.ItemProviderAdapter;

import de.tud.et.ifa.agtele.emf.edit.IReferencingIdentificationStringProvider;

/**
 * This class contains convenient static methods for working with EMF objects.
 *
 * @author AG Tele
 *
 */
public interface AgteleEcoreUtil {

	/**
	 * Returns the {@link EditingDomain} of an {@link EObject} or null if none can be found
	 *
	 * @param object
	 * @return The {@link EditingDomain} or null
	 */
	public static EditingDomain getEditingDomainFor(EObject object) {

		IEditingDomainProvider editingDomainProvider =

				(IEditingDomainProvider) EcoreUtil.getExistingAdapter(object, IEditingDomainProvider.class);
		if (editingDomainProvider != null) {
			return editingDomainProvider.getEditingDomain();
		} else if (object.eResource() != null) {
			ResourceSet resourceSet = object.eResource().getResourceSet();
			if (resourceSet instanceof IEditingDomainProvider) {
				return ((IEditingDomainProvider) resourceSet).getEditingDomain();
			} else if (resourceSet != null) {
				editingDomainProvider = (IEditingDomainProvider) EcoreUtil.getExistingAdapter(resourceSet,
						IEditingDomainProvider.class);
				if (editingDomainProvider != null) {
					return editingDomainProvider.getEditingDomain();
				}
			}
		}
		return null;
	}

	/**
	 * Returns the {@link AdapterFactoryItemDelegator} of an {@link EObject} or null if none can be found
	 *
	 * @param object
	 * @return The {@link AdapterFactoryItemDelegator} or null
	 */
	public static AdapterFactoryItemDelegator getAdapterFactoryItemDelegatorFor(EObject object) {

		// get all property descriptors for the current eObject and
		// therefore do some weird voodoo stuff according to
		// https://www.eclipse.org/forums/index.php/t/162266/
		if (AgteleEcoreUtil.getEditingDomainFor(object) instanceof AdapterFactoryEditingDomain) {
			AdapterFactoryEditingDomain afed = (AdapterFactoryEditingDomain) AgteleEcoreUtil
					.getEditingDomainFor(object);
			if (afed.getAdapterFactory().isFactoryForType(object)) {
				return new AdapterFactoryItemDelegator(
						((ComposedAdapterFactory) afed.getAdapterFactory()).getRootAdapterFactory());
			}
		}
		return null;
	}

	/**
	 * Returns an adapter of the given <em>adapterClass</em> for the given <em>eObject</em>.
	 * <p />
	 * Note: This extends the functionality of the standard
	 * {@link AdapterFactory#adapt(org.eclipse.emf.common.notify.Notifier, Object)} method by providing a second way of
	 * retrieving an adapter if the <em>standard</em> way does not work.
	 *
	 * @param <T>
	 * @param eObject
	 *            The {@link EObject} for which an adapter shall be retrieved.
	 * @param adapterClass
	 *            The key indicating the required type of adapter.
	 * @return An adapter of the demanded <em>adapterClass</em> or <em>null</em> if no adapter (of the required type)
	 *         could be retrieved.
	 */
	@SuppressWarnings("unchecked")
	public static <T> T adapt(EObject eObject, Class<T> adapterClass) {

		// We need an AdapterFactoryItemDelegator in order to retrieve the
		// AdapterFactory which we will use for the
		// actual adaptation
		//
		AdapterFactoryItemDelegator delegator = AgteleEcoreUtil.getAdapterFactoryItemDelegatorFor(eObject);

		if (delegator == null) {
			return null;
		}

		// First, we try the 'standard' way of adapting
		Adapter adapter = delegator.getAdapterFactory().adapt(eObject, adapterClass);

		// If this does not work, we try an 'indirect' adaption by first
		// retrieving an adapter of type
		// 'IEditingDomainItemProvider' that we will then try to cast to the
		// required type
		//
		if (adapter == null) {
			adapter = AgteleEcoreUtil.getAdapterFactoryItemDelegatorFor(eObject).getAdapterFactory().adapt(eObject,
					IEditingDomainItemProvider.class);
		}

		// Finally, we check if the adapter that was found is really of the
		// required type
		//
		return adapterClass.isAssignableFrom(adapter.getClass()) ? (T) adapter : null;
	}

	/**
	 * Returns the set of {@link EClass EClasses} defined by the given {@link EPackage EPackages} and all of the
	 * {@link EPackageHelper#collectEPackages(Set, boolean, boolean, boolean, Optional) referenced} EPackages.
	 *
	 * @param ePackages
	 * @return the set of classes
	 */
	public static Set<EClass> getAllClassesInEPackagesAndReferencedEPackages(Collection<EPackage> ePackages) {

		Set<EPackage> packagesToScan = EPackageHelper.collectEPackages(new LinkedHashSet<>(ePackages), true, true, true,
				Optional.empty());

		return AgteleEcoreUtil.getAllClassesInEPackages(packagesToScan);
	}

	/**
	 * Returns the set of {@link EClass EClasses} defined by the given {@link EPackage} and all of the
	 * {@link EPackageHelper#collectEPackages(EPackage, boolean, boolean, boolean, Optional) referenced} EPackages.
	 *
	 * @param ePackage
	 * @return the set of classes
	 */
	public static Set<EClass> getAllClassesInEPackageAndReferencedEPackages(EPackage ePackage) {

		Set<EPackage> packagesToScan = EPackageHelper.collectEPackages(ePackage, true, true, true, Optional.empty());

		return packagesToScan.stream().flatMap(p -> AgteleEcoreUtil.getAllClassesInEPackage(p).stream())
				.collect(Collectors.toCollection(LinkedHashSet::new));
	}

	/**
	 * Returns the set of {@link EClass EClasses} defined by the given {@link EPackage EPackages}.
	 *
	 * @param ePackages
	 * @return the set of classes
	 */
	public static Set<EClass> getAllClassesInEPackages(Collection<? extends EPackage> ePackages) {

		List<EClass> classesInPackage = ePackages.stream()
				.flatMap(e -> e.getEClassifiers().stream().filter(EClass.class::isInstance).map(EClass.class::cast))
				.collect(Collectors.toList());

		List<EClass> classesWithOutDocumentRoot = classesInPackage.stream()
				.filter(c -> !AgteleEcoreUtil.isDocRootClass(c)).collect(Collectors.toList());

		return new LinkedHashSet<>(classesWithOutDocumentRoot);
	}

	/**
	 * Returns the set of {@link EClass EClasses} defined by the given {@link EPackage}.
	 *
	 * @param ePackage
	 * @return the set of classes
	 */
	public static Set<EClass> getAllClassesInEPackage(EPackage ePackage) {

		List<EClass> classesInPackage = ePackage.getEClassifiers().stream().filter(EClass.class::isInstance)
				.map(EClass.class::cast).collect(Collectors.toList());

		List<EClass> classesWithOutDocumentRoot = classesInPackage.stream()
				.filter(c -> !AgteleEcoreUtil.isDocRootClass(c)).collect(Collectors.toList());

		return new LinkedHashSet<>(classesWithOutDocumentRoot);
	}

	/**
	 * Whether the given {@link EClassifier} is a <em>DocumentRoot</em> class created when converting XSD-based to
	 * Ecore-based metamodels.
	 *
	 * @param eClassifier
	 * @return '<em>true</em>' if the given eClass is a DocumentRoot
	 */
	public static boolean isDocRootClass(EClassifier eClassifier) {

		EClass docroot = ExtendedMetaData.INSTANCE.getDocumentRoot(eClassifier.getEPackage());

		return docroot != null && docroot.equals(eClassifier);
	}

	/**
	 * Collects all sub- EClasses of a specific eClass in a collection of handed eClasses.
	 *
	 * @param classes
	 *            The class collection to search for sub classes in
	 * @param aClass
	 *            The class to search sub classes for
	 * @param includeGivenClass
	 *            Set to <code>true</code>, if the given class shall be included. If the given class is abstract, it
	 *            will only be added to the result, if includeAbstract is true.
	 * @param includeAbstract
	 *            Whether to include abstract subClasses too.
	 * @return All found sub classes according to the specified options.
	 */
	public static Collection<EClass> getAllSubClasses(Collection<EClass> classes, EClass aClass,
			boolean includeGivenClass, boolean includeAbstract) {

		Collection<EClass> result = new ArrayList<>();
		if (includeGivenClass && (!aClass.isAbstract() || aClass.isAbstract() && includeAbstract)) {
			result.add(aClass);
		}
		result.addAll(AgteleEcoreUtil.getAllSubClasses(classes, aClass, includeAbstract));
		return result;
	}

	/**
	 * Collects all sub- EClasses of a specific eClass within its respective root ePackage.
	 *
	 * @param includeGivenClass
	 *            Set to <code>true</code>, if the given class shall be included. If the given class is abstract, it
	 *            will only be added to the result, if includeAbstract is true.
	 * @param aClass
	 *            The class to search sub classes for
	 * @param includeAbstract
	 *            Whether to include abstract subClasses too.
	 * @return All found sub classes according to the specified options.
	 */
	public static Collection<EClass> getAllSubClasses(EClass aClass, boolean includeGivenClass,
			boolean includeAbstract) {

		EPackage rootPackage = AgteleEcoreUtil.getRootEPackage(aClass);
		return AgteleEcoreUtil.getAllSubClasses(
				AgteleEcoreUtil.getAllPackageEClasses(AgteleEcoreUtil.getAllSubPackages(rootPackage, true)), aClass,
				includeGivenClass, includeAbstract);
	}

	/**
	 * Collects all sub- EClasses of a specific eClass within its respective root ePackage. The given class is not
	 * included.
	 *
	 * @param aClass
	 *            The class to search sub classes for
	 * @param includeAbstract
	 *            Whether to include abstract subClasses too.
	 * @return All found sub classes according to the specified options.
	 */
	public static Collection<EClass> getAllSubClasses(EClass aClass, boolean includeAbstract) {

		return AgteleEcoreUtil.getAllSubClasses(aClass, false, includeAbstract);
	}

	/**
	 * Collects all sub- EClasses of a specific eClass within its respective root ePackage. Abstract sub classes are
	 * included.
	 *
	 * @param aClass
	 *            The class to search sub classes for
	 * @return All found sub classes according to the specified options.
	 */
	public static Collection<EClass> getAllSubClasses(EClass aClass) {

		return AgteleEcoreUtil.getAllSubClasses(aClass, false, true);
	}

	/**
	 * Collects all sub- EClasses of a specific eClass in a collection of handed eClasses.
	 *
	 * @param classes
	 *            The class collection to search for sub classes in
	 * @param aClass
	 *            The class to search sub classes for
	 * @param includeAbstract
	 *            Whether to include abstract subClasses too.
	 * @return All found sub classes according to the specified options.
	 */
	public static Collection<EClass> getAllSubClasses(Collection<EClass> classes, EClass aClass,
			boolean includeAbstract) {

		Collection<EClass> result = new ArrayList<>();
		Iterator<EClass> i = classes.iterator();

		while (i.hasNext()) {
			EClass checkClass = i.next();
			if (checkClass == aClass) {
				continue;
			}

			Collection<EClass> superClasses = checkClass.getEAllSuperTypes();

			if (superClasses.contains(aClass)
					&& (!checkClass.isAbstract() || checkClass.isAbstract() && includeAbstract)) {
				result.add(checkClass);
			}
		}
		return result;
	}

	/**
	 * Collects all sub- EClasses of a specific eClass in a collection of handed eClasses. This implementation delegates
	 * to .getAllSubClasses(Collection<EClass>, EClass, boolean)
	 *
	 * @param classes
	 *            The class collection to search for sub classes in
	 * @param aClass
	 *            The class to search sub classes for
	 * @return All found sub classes according to the specified options.
	 */
	public static Collection<EClass> getAllSubClasses(Collection<EClass> classes, EClass aClass) {

		return AgteleEcoreUtil.getAllSubClasses(classes, aClass, true);
	}

	/**
	 * Collects all subPackages that are descendants of a given EPackage.
	 *
	 * @param p
	 * @return The collection of all sub packages of the given package.
	 */
	public static Collection<EPackage> getAllSubPackages(EPackage p) {

		ArrayList<EPackage> result = new ArrayList<>();

		if (p != null) {
			Collection<EPackage> subPackages = p.getESubpackages();

			if (subPackages != null && !subPackages.isEmpty()) {
				result.addAll(subPackages);

				Iterator<EPackage> i = subPackages.iterator();
				while (i.hasNext()) {
					Collection<EPackage> subSubPackages = AgteleEcoreUtil.getAllSubPackages(i.next());
					result.addAll(subSubPackages);
				}
			}
		}
		return result;
	}

	/**
	 * Collects all subGenPackages that are descendants of a given GenPackage.
	 *
	 * @param p
	 * @return The collection of all sub packages of the given package.
	 */
	public static Collection<GenPackage> getAllSubPackages(GenPackage p) {

		ArrayList<GenPackage> result = new ArrayList<>();

		if (p != null) {
			Collection<GenPackage> subPackages = p.getSubGenPackages();

			if (subPackages != null && !subPackages.isEmpty()) {
				result.addAll(subPackages);

				Iterator<GenPackage> i = subPackages.iterator();
				while (i.hasNext()) {
					Collection<GenPackage> subSubPackages = AgteleEcoreUtil.getAllSubPackages(i.next());
					result.addAll(subSubPackages);
				}
			}
		}
		return result;
	}

	/**
	 * Collects all subPackages that are descendants of a given EPackage.
	 *
	 * @param p
	 * @param includeGiven
	 * @return The collection of all sub packages of the given package.
	 */
	public static Collection<EPackage> getAllSubPackages(EPackage p, boolean includeGiven) {

		Collection<EPackage> result = new ArrayList<>();
		if (includeGiven) {
			result.add(p);
		}
		result.addAll(AgteleEcoreUtil.getAllSubPackages(p));
		return result;
	}

	/**
	 * Collects all subGenPackages that are descendants of a given GenPackage.
	 *
	 * @param p
	 * @param includeGiven
	 * @return The collection of all sub packages of the given package.
	 */
	public static Collection<GenPackage> getAllSubPackages(GenPackage p, boolean includeGiven) {

		Collection<GenPackage> result = new ArrayList<>();
		if (includeGiven) {
			result.add(p);
		}
		result.addAll(AgteleEcoreUtil.getAllSubPackages(p));
		return result;
	}

	/**
	 * Collects a collection of all eClasses within a collection of given ePackages.
	 *
	 * @param packages
	 * @return The collection of eClasses within the given collection of ePackages.
	 */
	public static Collection<EClass> getAllPackageEClasses(Collection<EPackage> packages) {

		Collection<EClass> result = new ArrayList<>();
		Iterator<EPackage> it = packages.iterator();

		while (it.hasNext()) {
			Collection<EClassifier> c = it.next().getEClassifiers();
			Iterator<EClassifier> i2 = c.iterator();
			while (i2.hasNext()) {
				EClassifier aClassifier = i2.next();
				if (aClassifier instanceof EClass) {
					result.add((EClass) aClassifier);
				}
			}
		}
		return result;
	}

	/**
	 * Collects a collection of all GenClasses within a collection of given GenPackages.
	 *
	 * @param packages
	 * @return The collection of GenClasses within the given collection of GenPackages.
	 */
	public static Collection<GenClass> getAllGenPackageGenClasses(Collection<GenPackage> packages) {

		Collection<GenClass> result = new ArrayList<>();
		Iterator<GenPackage> it = packages.iterator();

		while (it.hasNext()) {
			Collection<GenClassifier> c = it.next().getGenClassifiers();
			Iterator<GenClassifier> i2 = c.iterator();
			while (i2.hasNext()) {
				GenClassifier aClassifier = i2.next();
				if (aClassifier instanceof GenClass) {
					result.add((GenClass) aClassifier);
				}
			}
		}
		return result;
	}

	/**
	 * Collects a collection of all eClasses with the given <em>className</em> within a collection of given ePackages.
	 *
	 * @param packages
	 * @param className
	 * @return The collection of eClasses within the given collection of ePackages that have the given className.
	 */
	public static Collection<EClass> getAllPackageEClasses(Collection<EPackage> packages, String className) {

		return AgteleEcoreUtil.getAllPackageEClasses(packages).stream().filter(c -> className.equals(c.getName()))
				.collect(Collectors.toList());
	}

	/**
	 * Collects a collection of all GenClasses with the given <em>className</em> within a collection of given
	 * GenPackages.
	 *
	 * @param packages
	 * @param className
	 * @return The collection of GenClasses within the given collection of GenPackages that have the given className.
	 */
	public static Collection<GenClass> getAllGenPackageGenClasses(Collection<GenPackage> packages, String className) {

		return AgteleEcoreUtil.getAllGenPackageGenClasses(packages).stream().filter(c -> className.equals(c.getName()))
				.collect(Collectors.toList());
	}

	/**
	 * Returns the topmost package in the ePackage hierarchy of the given ePackage.
	 *
	 * @param p
	 * @return The root ePackage.
	 */
	public static EPackage getRootEPackage(EPackage p) {

		EObject root = EcoreUtil.getRootContainer(p);
		if (root instanceof EPackage) {
			return (EPackage) root;
		} else {
			return (EPackage) root.eContents().get(0);
		}
	}

	/**
	 * Returns the topmost package in the package hierarchy of a given eClass.
	 *
	 * @param e
	 * @return The root ePackage
	 */
	public static EPackage getRootEPackage(EClass e) {

		return AgteleEcoreUtil.getRootEPackage(e.getEPackage());
	}

	/**
	 * Returns the topmost packages in the ePackage hierarchy of the given list of ePackages.
	 *
	 * @param p
	 *            The list of packages for that the root packages shall be determined.
	 * @return The root ePackage.
	 */
	public static HashSet<EPackage> getRootEPackages(Collection<EPackage> p) {

		HashSet<EPackage> ret = new HashSet<>();
		for (EPackage ePackage : p) {
			ret.add(AgteleEcoreUtil.getRootEPackage(ePackage));
		}
		return ret;
	}

	/**
	 * Returns all instances of an eClass, that are contained in a resource.
	 *
	 * @param eClass
	 * @param res
	 * @return All instances of the eClass
	 */
	public static Collection<EObject> getAllInstances(EClass eClass, Resource res) {

		Collection<EObject> result = new ArrayList<>();

		Iterator<EObject> it = new AgteleContainmentTreeIterator(res, true, true);

		while (it.hasNext()) {
			EObject obj = it.next();
			if (eClass.isSuperTypeOf(obj.eClass())) {
				result.add(obj);
			}
		}
		return result;
	}

	/**
	 * Returns all instances of an eClass, that are contained in an eObject.
	 *
	 * @param eClass
	 * @param root
	 * @return All instances of the eClass
	 */
	public static Collection<EObject> getAllInstances(EClass eClass, EObject root) {

 		Collection<EObject> result = new ArrayList<>();

		Iterator<EObject> it = new AgteleContainmentTreeIterator(root, true, true);

		while (it.hasNext()) {
			EObject obj = it.next();
			if (eClass.isSuperTypeOf(obj.eClass()) || eClass ==obj.eClass() || eClass == EcorePackage.Literals.EOBJECT) {
				result.add(obj);
			}
		}
		return result;
	}

	/**
	 * Returns all instances of an eClass, that are contained in the list of roots.
	 *
	 * @param eClass
	 * @param roots
	 * @return All instances of the eClass
	 */
	public static Collection<EObject> getAllInstances(EClass eClass, Collection<EObject> roots) {
		return AgteleEcoreUtil.getAllInstances(eClass, roots, false);
	}

	/**
	 * Returns all instances of an eClass, that are contained in the list of roots.
	 *
	 * @param eClass
	 * @param roots
	 * @param includeRoots 
	 * @return All instances of the eClass
	 */
	public static Collection<EObject> getAllInstances(EClass eClass, Collection<EObject> roots, boolean includeRoots) {
		Collection<EObject> result = new ArrayList<>();

		for (EObject root : roots) {
			if (root == null) {
				continue;
			}
			if (includeRoots && (eClass == null || eClass.isSuperTypeOf(root.eClass()) || (eClass == EcorePackage.Literals.EOBJECT && root instanceof EObject))) {
				result.add(root);
			}
			Iterator<EObject> it = new AgteleContainmentTreeIterator(root, true, true);
			while (it.hasNext()) {
				EObject obj = it.next();
				if (obj == null) {
					continue;
				}
				if (eClass == null || eClass.isSuperTypeOf(obj.eClass()) || (eClass == EcorePackage.Literals.EOBJECT && obj instanceof EObject)) {
					result.add(obj);
				}
			}
		}
		return result;
	}
	
	/**
	 * Returns all instances of an eClass, that are contained in an eObject.
	 *
	 * @param eClass
	 * @param anObject
	 * @param findRoot
	 *            if true, the search begins at the local containment root of anObject, if false only instances of the
	 *            given eClass are found, that are subsequent to the given eObject.
	 * @return All instances of the eClass
	 */
	public static Collection<EObject> getAllInstances(EClass eClass, EObject anObject, boolean findRoot) {

		if (findRoot) {
			if (anObject.eResource() != null) {
				return AgteleEcoreUtil.getAllInstances(eClass, anObject.eResource());
			}
			return AgteleEcoreUtil.getAllInstances(eClass, EcoreUtil.getRootContainer(anObject, true));
		} else {
			return AgteleEcoreUtil.getAllInstances(eClass, anObject);
		}
	}

	/**
	 * This moves upward in the containment hierarchy of the given {@link EObject} and checks whether (at least) one of
	 * its ancestors is an instance of the given {@link EClass type}.
	 *
	 * @param child
	 *            The {@link EObject} of which the containers shall be checked.
	 * @param ancestorType
	 *            The {@link EClass} to check against.
	 * @return '<em><b>true</b></em>' if at least one of the ancestors is of the specified type; '<em><b>false</b></em>'
	 *         otherwise.
	 */
	public static boolean hasAncestorOfType(EObject child, EClass ancestorType) {

		EObject parent = child.eContainer();

		while (parent != null) {

			if (parent.eClass().equals(ancestorType)) {
				return true;
			}
			parent = parent.eContainer();
		}
		return false;
	}

	/**
	 * This moves upward in the containment hierarchy of the given {@link EObject} and checks whether (at least) one of
	 * its ancestors is an instance of the given {@link EClass type} or any of its sub-types.
	 *
	 * @param child
	 *            The {@link EObject} of which the containers shall be checked.
	 * @param ancestorType
	 *            The {@link EClass} to check against.
	 * @return '<em><b>true</b></em>' if at least one of the ancestors is of the specified type or one of its sub-types;
	 *         '<em><b>false</b></em>' otherwise.
	 */
	public static boolean hasAncestorOfKind(EObject child, EClass ancestorType) {

		EObject parent = child.eContainer();

		while (parent != null) {

			List<EClass> typesToCheck = new ArrayList<>();
			typesToCheck.add(parent.eClass());
			typesToCheck.addAll(parent.eClass().getEAllSuperTypes());

			if (typesToCheck.contains(ancestorType)) {
				return true;
			}
			parent = parent.eContainer();
		}
		return false;
	}

	/**
	 * This moves upward in the containment hierarchy of the given {@link EObject} and returns the first ancestor that
	 * is an instance of the given {@link EClass type}.
	 *
	 * @param child
	 *            The {@link EObject} of which the containers shall be checked.
	 * @param ancestorType
	 *            The {@link EClass} to check against.
	 * @return The first ancestor that is of the specified type or '<em><b>null</b></em>' if there is no such ancestor.
	 */
	public static EObject getAncestorOfType(EObject child, EClass ancestorType) {

		EObject parent = child.eContainer();

		while (parent != null) {

			if (parent.eClass().equals(ancestorType)) {
				return parent;
			}
			parent = parent.eContainer();
		}
		return null;
	}
	@SuppressWarnings("unchecked")
	public static <T extends EObject> T getAncestorOfKind(EObject child, Class<T> ancestorType) {

		EObject parent = child.eContainer();

		while (parent != null) {

			if (ancestorType.isInstance(parent)) {
				return (T)parent;
			}
			parent = parent.eContainer();
		}
		return null;
	}

	/**
	 * This moves upward in the containment hierarchy of the given {@link EObject} and returns the first ancestor that
	 * is an instance of the given {@link EClass type} or any of its sub-types.
	 *
	 * @param child
	 *            The {@link EObject} of which the containers shall be checked.
	 * @param ancestorType
	 *            The {@link EClass} to check against.
	 * @return The first ancestor that is of the specified type or one of its sub-types or '<em><b>null</b></em>' if
	 *         there is no such ancestor.
	 */
	public static EObject getAncestorOfKind(EObject child, EClass ancestorType) {

		EObject parent = child.eContainer();

		while (parent != null) {

			List<EClass> typesToCheck = new ArrayList<>();
			typesToCheck.add(parent.eClass());
			typesToCheck.addAll(parent.eClass().getEAllSuperTypes());

			if (typesToCheck.contains(ancestorType)) {
				return parent;
			}
			parent = parent.eContainer();
		}
		return null;
	}

	/**
	 * For the given {@link EObject}, this returns the {@link EObject#eGet(org.eclipse.emf.ecore.EStructuralFeature)
	 * value or values} of the given {@link EStructuralFeature}.
	 * <p />
	 * Note: As EStructuralFeatures can be {@link EStructuralFeature#isMany() many-valued}, this will return either no
	 * value, a single value, or a list of values. Note: The type of the entries inside the list will match the type of
	 * the given {@link EAttribute#getEAttributeType() EAttribute} or {@link EReference#getEReferenceType() EReference}.
	 *
	 * @param eObject
	 *            The {@link EObject} for that the values shall be returned.
	 * @param eFeature
	 *            The {@link EStructuralFeature} for that the values shall be returned.
	 * @return The determined values (either an empty list, a list consisting of a single value, or multiple values). If
	 *         the given feature is not a valid feature of the given eObject, an empty list will be returned..
	 */
	public static List<Object> getStructuralFeatureValueAsList(EObject eObject, EStructuralFeature eFeature) {

		Object value = null;

		try {
			value = eObject.eGet(eFeature);
		} catch (IllegalArgumentException e) {
			// we will just return an empty list
		}

		if (value == null) {
			return Collections.emptyList();
		} else if (value instanceof EObject) {
			return Arrays.asList(value);
		} else if (value instanceof Collection<?>) {
			return Collections.unmodifiableList(new ArrayList<>((Collection<?>) value));
		} else {
			return Arrays.asList(value);
		}

	}

	/**
	 * For a given element of an Ecore model, this returns the corresponding GenModel element.
	 *
	 * @param ecoreModelElement
	 *            An {@link EObject element} of an Ecore model.
	 * @param resourceSet
	 *            The {@link ResourceSet} that shall be used to load the GenModel or '<em>null</em>' if the ResourceSet
	 *            containing the given <em>ecoreModelElement</em> shall be used.
	 * @return The corresponding {@link GenBase GenModel element} or an empty optional if no corresponding GenModel or
	 *         GenModel element could be determined.
	 */
	static Optional<GenBase> getGenModelElement(EObject ecoreModelElement, ResourceSet resourceSet) {

		ResourceSet localResourceSet = resourceSet != null ? resourceSet
				: ecoreModelElement.eResource().getResourceSet();

		if (!(EcoreUtil.getRootContainer(ecoreModelElement) instanceof EPackage)
				|| ecoreModelElement.eResource() == null) {
			return Optional.empty();
		}

		// We assume that the associated GenModel is located in the same folder
		// and has the same base name
		//
		URI genModelURI = ecoreModelElement.eResource().getURI().trimFileExtension().appendFileExtension("genmodel");

		Resource genModelResource = localResourceSet.getResource(genModelURI, true);

		if (genModelResource == null || !genModelResource.getErrors().isEmpty()) {
			return Optional.empty();
		}

		// As we load the GenModel in a separate resource set, we have to first
		// determine the resource that
		// is equivalent to the resource opened in
		// the Ecore editor
		//
		Resource ecoreResource = localResourceSet.getResource(ecoreModelElement.eResource().getURI(), true);

		if (ecoreResource == null || ecoreResource.getContents().isEmpty()) {
			return Optional.empty();
		}

		// Now, we can determine the equivalent elements in both loaded Ecore
		// resources
		//
		String uriFragment = ecoreModelElement.eResource().getURIFragment(ecoreModelElement);
		EObject correspondingElement = ecoreResource.getEObject(uriFragment);

		if (correspondingElement == null) {
			return Optional.empty();
		}

		// Finally, we can determine the element of the GenModel that
		// describes/references the selected EObject
		//
		Optional<Setting> setting = EcoreUtil.UsageCrossReferencer.find(correspondingElement, genModelResource)
				.parallelStream().filter(s -> s.getEObject() instanceof GenBase
						&& correspondingElement.equals(((GenBase) s.getEObject()).getEcoreModelElement()))
				.findAny();

		return setting.isPresent() ? Optional.of((GenBase) setting.get().getEObject()) : Optional.empty();
	}

	/**
	 * Checks if at least one classifier of the package contains a ecore annotation enabling the validation framework
	 * for the package by naming a constraint.
	 *
	 * @param p
	 *            The package to check.
	 * @return
	 */
	public static boolean isValidationEnabledForEPackage(EPackage p) {

		return p.getEClassifiers().stream().anyMatch(cl -> {
			if (!(cl instanceof EClass)) {
				return false;
			}
			EAnnotation ecoreAnnotation = cl.getEAnnotation(EcorePackage.eNS_URI);
			if (ecoreAnnotation == null || ecoreAnnotation.getDetails().get("constraints") == null
					|| ecoreAnnotation.getDetails().get("constraints").isEmpty()) {
				return false;
			}
			return true;
		});
	}

	/**
	 * Returns a collection of the chain of containers, an element is contained at its bottom. Delegates to
	 * {@link #getAllContainers(EObject, boolean)}.
	 *
	 * @param obj
	 * @return
	 */
	public static Collection<EObject> getAllContainers(EObject obj) {

		return AgteleEcoreUtil.getAllContainers(obj, false);
	}

	public static EObject getClosestContainerOfType(EObject obj, EClass cls) {

		Collection<EObject> containers = AgteleEcoreUtil.getAllContainers(obj);
		for (EObject container : containers) {
			if (cls.isInstance(container)) {
				return container;
			}
		}
		return null;
	}

	/**
	 * Returns a collection of the chain of containers, an element is contained at its bottom
	 *
	 * @param obj
	 * @param includeSelf
	 *            whether to include the object specified itself
	 * @return
	 */
	public static Collection<EObject> getAllContainers(EObject obj, boolean includeSelf) {

		ArrayList<EObject> result = new ArrayList<>();
		if (obj != null) {
			EObject current = obj;

			if (includeSelf) {
				result.add(current);
			}

			while (current.eContainer() != null) {
				result.add(current.eContainer());
				current = current.eContainer();
			}

			return result;
		}
		return null;
	}

	/**
	 * Returns an {@link EObject} from the given <em>otherResourceSet</em> that is equivalent to the given
	 * <em>element</em>.
	 * <p />
	 * Note: If the given EObject is part of the given <em>otherResourceSet</em>, this simply returns this
	 * <em>element</em>.
	 *
	 * @param element
	 *            The {@link EObject} for that an equivalent element shall be returned.
	 * @param otherResourceSet
	 *            The {@link ResourceSet} from which an element shall be returned.
	 * @return The equivalent element or '<em>null</em>' if no such element exists.
	 */
	public static EObject getEquivalentElementFrom(EObject element, ResourceSet otherResourceSet) {

		// First, determine the Resource from the 'otherResourceSet' that is equivalent to the Resource of the given
		// 'element'
		//
		Resource otherEcoreResource = otherResourceSet.getResource(element.eResource().getURI(), true);

		if (otherEcoreResource == null) {
			return null;
		}

		// Now that we know the equivalent resources, we can determine the
		// equivalent elements
		//
		String uriFragment = element.eResource().getURIFragment(element);

		return otherEcoreResource.getEObject(uriFragment);

	}

	/**
	 * Checks if the root element specified contains the element
	 *
	 * @param root
	 * @param element
	 * @return whether the element is contained in the root or its subordinate elements or not
	 */
	public static boolean subTreeContainsElement(EObject root, EObject element) {

		return root != element && EcoreUtil.isAncestor(root, element);
	}
	
	static public ItemProviderAdapter getItemProviderAdapter(EObject eObject) {
		ItemProviderAdapter itemProviderAdapter = null;
		try {
			Adapter testAdapter = AgteleEcoreUtil.getAdapter(eObject, IEditingDomainItemProvider.class);
			itemProviderAdapter = (ItemProviderAdapter) testAdapter;
		} catch (Exception e) {
			//Do nothing
		}
		if (itemProviderAdapter == null) {
			try {
				itemProviderAdapter = (ItemProviderAdapter) AgteleEcoreUtil.getAdapter(eObject, ItemProviderAdapter.class);
			} catch (Exception e) {
				//Do nothing
			}
		}
		return itemProviderAdapter;
	}
	
	/**
	 * Returns an adapter of the specified type for the specified object. If none is registered in the resource set, 
	 * the ecore adapter factory extension point is being queried for registered adapters.
	 * @param eObject
	 * @param type
	 * @return
	 */
	static public Adapter getAdapter (EObject eObject, Object type) {
		if (eObject == null) {
			return null;
		}
		
		Resource res = eObject.eResource();
		ResourceSet set = res != null ? res.getResourceSet() : null;
		if (set != null) {
			List<AdapterFactory> factories = new ArrayList<>(set.getAdapterFactories());
			for (int i = factories.size() -1 ; i >= 0; i-=1) {
				if (factories.get(i) == null) {
					factories.remove(i);
				}
			}
		}
		Adapter adapter = null;
		try {
			adapter = EcoreUtil.getRegisteredAdapter(eObject, type);			
		} catch (Exception e) {
			//Do nothing
		}
		
		if (adapter == null) {
			AdapterFactory factory = null;
			if (eObject.eResource() != null && eObject.eResource().getResourceSet() != null) {
				factory = EcoreUtil.getAdapterFactory(eObject.eResource().getResourceSet().getAdapterFactories(), type);
				try {
					adapter = factory.adapt(eObject, type);
				} catch (Exception e) {
					//Do nothing
				}
			}
			
			if (factory == null || adapter == null) {
				factory = AgteleEcoreUtil.createRegisteredAdapterFactory(eObject.eClass().getEPackage().getNsURI());
				if (factory != null) {
					adapter = factory.adapt(eObject, type);
					if (adapter != null && eObject.eResource() != null && eObject.eResource().getResourceSet() != null && !eObject.eResource().getResourceSet().getAdapterFactories().contains(factory)) {
						eObject.eResource().getResourceSet().getAdapterFactories().add(factory);
					}
				}				
			}			
		}
		return adapter;
	}
	
	public static final String ITEM_PROVIDER_ADAPTER_FACTORIES_EXTENSION_POINT_ID = "org.eclipse.emf.edit.itemProviderAdapterFactories";
	
	public static final Map<String, AdapterFactory> adapterFactoryCache = new HashMap<>();
	
	static public AdapterFactory getCachedAdapterFactory (String nameSpaceUri) {
		return adapterFactoryCache.get(nameSpaceUri);
	}
	
	static public AdapterFactory createRegisteredAdapterFactory (EObject obj) {
		if (obj != null) {
			return createRegisteredAdapterFactory(obj.eClass().getEPackage().getNsURI());
		}
		return null;
	}
	
	/**
	 * Fetches the Ecore Adapter Factory extension point and creates the registered adapter factory.
	 * @param nameSpaceUri
	 * @return
	 */
	static public AdapterFactory createRegisteredAdapterFactory (String nameSpaceUri) {
		if (adapterFactoryCache.containsKey(nameSpaceUri)) {
			return adapterFactoryCache.get(nameSpaceUri);
		}
		
		IExtensionRegistry registry = Platform.getExtensionRegistry();
		
		IConfigurationElement[] elements
	      = registry.getConfigurationElementsFor(ITEM_PROVIDER_ADAPTER_FACTORIES_EXTENSION_POINT_ID);
		
		for(IConfigurationElement element : elements) {
			if (element.getAttribute("uri").equals(nameSpaceUri)) {
	            try {
					final Object o =
							element.createExecutableExtension("class");
					if (o instanceof AdapterFactory) {
						synchronized (AgteleEcoreUtil.class) {
							adapterFactoryCache.put(nameSpaceUri, (AdapterFactory) o);
						}						
						return (AdapterFactory)o;					
					}
				} catch (CoreException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}
	
	public static final String URI_PREFIX = "uri";
	public static final String PREFIX_SEPARATOR = ":";
	
	/**
	 * Returns a registerable URI for an ecore element;
	 * @param eElement
	 * @return
	 */
	static String getEcoreElementReferencingIdentifier (EObject eElement) {
		String uri = AgteleEcoreUtil.getEcoreElementUri(eElement);
		if (uri == null) {
			return null;
		}
		return URI_PREFIX + PREFIX_SEPARATOR + uri;
	}
	
	/**
	 * Returns a unique string that corresponds to the ecore element.
	 * @param eElement
	 * @return
	 */
	static String getEcoreElementUri (EObject eElement) {
		if (eElement instanceof EPackage) {
			return ((EPackage)eElement).getNsURI();
		} else if (eElement instanceof EClassifier) {
			return AgteleEcoreUtil.getEcoreElementUri(((EClassifier)eElement).getEPackage()) + "#" + ((EClassifier)eElement).getName();
		} else if (eElement instanceof EStructuralFeature) {
			return AgteleEcoreUtil.getEcoreElementUri(((EStructuralFeature)eElement).getEContainingClass()) + "/" + ((EStructuralFeature)eElement).getName();
		} else if (eElement instanceof EOperation) {
			return AgteleEcoreUtil.getEcoreElementUri(((EOperation)eElement).getEContainingClass()) + "/" + ((EOperation)eElement).getName();
		} else if (eElement instanceof EParameter) {
			return AgteleEcoreUtil.getEcoreElementUri(((EParameter)eElement).getEOperation()) + "/" + ((EParameter)eElement).getName();
		}
		return null;		
	}
	/**
	 * Returns the EPackage-Namespace-URI from an element string that has been created with {@link #getEcoreElementUri(EObject)}. 
	 * @param uri
	 * @return
	 */
	static String getEcorePkgUriFromElementUri(String uri) {
		if (uri == null || uri.isEmpty()) {
			return null;
		}
		if (!uri.contains("#") ) {
			return uri;
		} 
		return uri.substring(0, uri.indexOf('#'));
	}

	/**
	 * Returns the EClass-Name from an element string that has been created with {@link #getEcoreElementUri(EObject)}. 
	 * @param uri
	 * @return
	 */
	static String getEClassNameFromElementUri(String uri) {		
		if (uri == null || uri.isEmpty() || !uri.contains("#") || uri.length() <= uri.indexOf('#')) {
			return null;
		}
		uri = uri.substring(uri.indexOf('#')+1);
		if (uri.contains("/")) {
			return uri.substring(0, uri.indexOf('/'));
		}
		return uri;
	}

	/**
	 * Returns the Feature-Name from an element string that has been created with {@link #getEcoreElementUri(EObject)}. 
	 * @param uri
	 * @return
	 */
	static String getFeatureNameFromElementUri(String uri) {		
		if (uri == null || uri.isEmpty() || !uri.contains("#") || uri.length() <= uri.indexOf('#')) {
			return null;
		}
		uri = uri.substring(uri.indexOf('#')+1);
		if (!uri.contains("/")|| uri.length() <= uri.indexOf('/')) {
			return null;
		}
		return uri.substring(uri.indexOf('/') + 1);
	}
	
	/**
	 * Filters a list of {@link CommandParameter}, if the created element is an instance of any of specified {@link EClass}es 
	 * by the removeInstances List, except, the element is an instance of the exceptImplements List. 
	 * @param parameters
	 * @param removeInstances
	 * @param exceptImplements
	 * @return
	 */
	public static void filterCommandParameter(Collection<Object> parameters, List<EClass> removeInstances, List<EClass> exceptImplements) {
		if (exceptImplements == null) {
			exceptImplements = Collections.emptyList();
		}
		if (parameters != null) {
			ParameterLoop:
			for (Object o : new ArrayList<>(parameters)) {
				if (o instanceof CommandParameter) {
					CommandParameter c = (CommandParameter)o;
					
					EObject created = c.getEValue();
					for (EClass cls : removeInstances) {
						if (cls.isInstance(created)) {
							if (exceptImplements.contains(created.eClass()) || new ArrayList<>(created.eClass().getEAllSuperTypes()).removeAll(exceptImplements)) {
								continue ParameterLoop;
							}			
							parameters.remove(c);
							continue ParameterLoop;		
						}
					}			
				}
			}
		}
	}
	
	public static <T> List<T> list (@SuppressWarnings("unchecked") T...element) {
		ArrayList<T> result = new ArrayList<>();
		for (T e : element) {
			result.add(e);
		}
		return result;
	}
		
	public static boolean isReferencable(Object o) {
		if (o instanceof EObject) {
			return AgteleEcoreUtil.isReferencable((EObject)o);
		}
		return false;
	}
	
	public static boolean isReferencable(EObject eObject) {
		try {
			if (AgteleEcoreUtil.getAllContainers(eObject, true).contains(EcorePackage.eINSTANCE)) {
				return true;
			}
			Adapter provider = AgteleEcoreUtil.getAdapter(eObject, IEditingDomainItemProvider.class);
			if (provider == null || !(provider instanceof ItemProviderAdapter)) {
				provider = AgteleEcoreUtil.getAdapter(eObject, ItemProviderAdapter.class);
			}
		
			if (provider != null && provider instanceof IReferencingIdentificationStringProvider) {
				return true;
			}
		} catch (Exception e) {
			//Do nothing
		}
		return false;
	}
	
	public static List<String> getReferencableStrings(EObject eObject) {

		if (AgteleEcoreUtil.getAllContainers(eObject, true).contains(EcorePackage.eINSTANCE)) {
			return Collections.singletonList(AgteleEcoreUtil.getEcoreElementReferencingIdentifier(eObject));
		}
		
		Adapter provider = null;
		try {
			provider = AgteleEcoreUtil.getAdapter(eObject, IEditingDomainItemProvider.class);
			if (provider == null || !(provider instanceof ItemProviderAdapter)) {
				provider = AgteleEcoreUtil.getAdapter(eObject, ItemProviderAdapter.class);
			}
		} catch (Exception e) {
			//Do nothing
		}
		
		if (provider != null && 
				provider instanceof IReferencingIdentificationStringProvider) {
			ArrayList<String> identifiers = new ArrayList<>(((IReferencingIdentificationStringProvider)provider).getReferencingIdentificationStrings(eObject));
			return identifiers;
		}
		return Collections.emptyList();
	}
	
	public static <T> Iterable<T> wrapTreeIterator(TreeIterator<T> it) {
		return new Iterable<T>() {
			@Override
			public Iterator<T> iterator() {
				return it;
			}			
		};
	}
} 
