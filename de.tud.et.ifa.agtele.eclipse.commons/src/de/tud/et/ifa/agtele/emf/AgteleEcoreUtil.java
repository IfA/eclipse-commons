/**
 *
 */
package de.tud.et.ifa.agtele.emf;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.domain.IEditingDomainProvider;
import org.eclipse.emf.edit.provider.AdapterFactoryItemDelegator;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;

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

		// We need an AdapterFactoryItemDelegator in order to retrieve the AdapterFactory which we will use for the
		// actual adaptation
		//
		AdapterFactoryItemDelegator delegator = AgteleEcoreUtil.getAdapterFactoryItemDelegatorFor(eObject);

		if (delegator == null) {
			return null;
		}

		// First, we try the 'standard' way of adapting
		Adapter adapter = delegator.getAdapterFactory().adapt(eObject, adapterClass);

		// If this does not work, we try an 'indirect' adaption by first retrieving an adapter of type
		// 'IEditingDomainItemProvider' that we will then try to cast to the required type
		//
		if (adapter == null) {
			adapter = AgteleEcoreUtil.getAdapterFactoryItemDelegatorFor(eObject).getAdapterFactory().adapt(eObject,
					IEditingDomainItemProvider.class);
		}

		// Finally, we check if the adapter that was found is really of the required type
		//
		return adapterClass.isAssignableFrom(adapter.getClass()) ? (T) adapter : null;
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
	 * value or values} of the given {@link EAttribute}.
	 * <p />
	 * Note: As EAttributes can be {@link EAttribute#isMany() many-valued}, too, this will return either no value, a
	 * single value, or a list of values. Note: The type of the entries inside the list will match the
	 * {@link EAttribute#getEAttributeType() type} of the given EAttribute.
	 *
	 * @param eObject
	 *            The {@link EObject} for that the values shall be returned.
	 * @param eAttribute
	 *            The {@link EAttribute} for that the values shall be returned.
	 * @return The determined values (either an empty list, a list consisting of a single value, or mutliple values).
	 */
	public static List<Object> getAttributeValueAsList(EObject eObject, EAttribute eAttribute) {

		final Object value = eObject.eGet(eAttribute);

		if (value == null) {
			return new ArrayList<>(Collections.emptyList());
		}

		if (eAttribute.isMany()) {
			return new ArrayList<>((Collection<?>) value);
		} else {
			return new ArrayList<>(Arrays.asList(value));
		}

	}
}
