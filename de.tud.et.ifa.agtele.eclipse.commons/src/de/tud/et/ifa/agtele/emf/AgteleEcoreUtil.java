/**
 * 
 */
package de.tud.et.ifa.agtele.emf;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.domain.IEditingDomainProvider;
import org.eclipse.emf.edit.provider.AdapterFactoryItemDelegator;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;

/**
 * This class contains convenient static methods for working with EMF objects.
 * 
 * @author AG Tele
 *
 */
public class AgteleEcoreUtil {

	/**
	 * Returns the {@link EditingDomain} of an {@link EObject} or null if none
	 * can be found
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
				EditingDomain editingDomain = ((IEditingDomainProvider) resourceSet).getEditingDomain();
				return editingDomain;
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
	 * Returns the {@link AdapterFactoryItemDelegator} of an {@link EObject} or
	 * null if none can be found
	 * 
	 * @param object
	 * @return The {@link AdapterFactoryItemDelegator} or null
	 */
	public static AdapterFactoryItemDelegator getAdapterFactoryItemDelegatorFor(EObject object) {
		// get all property descriptors for the current eObject and
		// therefore do some weird voodoo stuff according to
		// https://www.eclipse.org/forums/index.php/t/162266/
		if (getEditingDomainFor(object) instanceof AdapterFactoryEditingDomain) {
			AdapterFactoryEditingDomain afed = (AdapterFactoryEditingDomain) getEditingDomainFor(object);
			if (afed.getAdapterFactory().isFactoryForType(object)) {
				return new AdapterFactoryItemDelegator(
						((ComposedAdapterFactory) afed.getAdapterFactory()).getRootAdapterFactory());
			}
		}
		return null;
	}

	/**
	 * Collects all sub- EClasses of a specific eClass in a collection of handed eClasses.
	 * @param classes The class collection to search for sub classes in
	 * @param aClass The class to search sub classes for
	 * @param includeGivenClass Set to <code>true</code>, if the given class shall be included. If the given class is abstract, it will only be added to the result, if includeAbstract is true.
	 * @param includeAbstract Whether to include abstract subClasses too.
	 * @return All found sub classes according to the specified options.
	 */
	public static Collection<EClass> getAllSubClasses(Collection<EClass> classes, EClass aClass, boolean includeGivenClass, boolean includeAbstract) {
		Collection<EClass> result = new ArrayList<EClass>();
		if (includeGivenClass && (!aClass.isAbstract() || aClass.isAbstract() && includeAbstract)) {
			result.add(aClass);
		}
		result.addAll(getAllSubClasses(classes, aClass, includeAbstract));
		return result;
	}
	
	/**
	 * Collects all sub- EClasses of a specific eClass within its respective root ePackage.
	 * @param includeGivenClass Set to <code>true</code>, if the given class shall be included. If the given class is abstract, it will only be added to the result, if includeAbstract is true.
	 * @param aClass The class to search sub classes for
	 * @param includeAbstract Whether to include abstract subClasses too.
	 * @return All found sub classes according to the specified options.
	 */
	public static Collection<EClass> getAllSubClasses(EClass aClass, boolean includeGivenClass, boolean includeAbstract) {
		EPackage rootPackage = getRootEPackage(aClass);
		return getAllSubClasses(getAllPackageEClasses(getAllSubPackages(rootPackage, true)), aClass, includeGivenClass, includeAbstract);
	}
	
	/**
	 * Collects all sub- EClasses of a specific eClass within its respective root ePackage. The given class is not included.
	 * @param aClass The class to search sub classes for
	 * @param includeAbstract Whether to include abstract subClasses too.
	 * @return All found sub classes according to the specified options.
	 */
	public static Collection<EClass> getAllSubClasses(EClass aClass, boolean includeAbstract) {
		return getAllSubClasses(aClass, false, includeAbstract);
	}
	
	/**
	 * Collects all sub- EClasses of a specific eClass within its respective root ePackage. Abstract sub classes are included.
	 * @param aClass The class to search sub classes for
	 * @return All found sub classes according to the specified options.
	 */
	public static Collection<EClass> getAllSubClasses(EClass aClass) {
		return getAllSubClasses(aClass, false, true);
	}
	
	/**
	 * Collects all sub- EClasses of a specific eClass in a collection of handed eClasses.
	 * @param classes The class collection to search for sub classes in
	 * @param aClass The class to search sub classes for
	 * @param includeAbstract Whether to include abstract subClasses too.
	 * @return All found sub classes according to the specified options.
	 */
	public static Collection<EClass> getAllSubClasses(Collection<EClass> classes, EClass aClass, boolean includeAbstract) {
		Collection<EClass> result = new ArrayList<EClass>();
		Iterator<EClass> i = classes.iterator();
		
		while(i.hasNext()) {
			EClass checkClass = i.next();
			if (checkClass == aClass) {
				continue;
			}
			
			Collection<EClass> superClasses = checkClass.getEAllSuperTypes();
			
			if(superClasses.contains(aClass)) {
				if (!checkClass.isAbstract() || (checkClass.isAbstract() && includeAbstract)) {
					result.add(checkClass);
				}
			}
		}
		return result;
	}
	
	/**
	 * Collects all sub- EClasses of a specific eClass in a collection of handed eClasses.
	 * This implementation delegates to .getAllSubClasses(Collection<EClass>, EClass, boolean)
	 * @param classes The class collection to search for sub classes in
	 * @param aClass The class to search sub classes for
	 * @return All found sub classes according to the specified options.
	 */
	public static Collection<EClass> getAllSubClasses(Collection<EClass> classes, EClass aClass) {
		return getAllSubClasses(classes, aClass, true);
	}
	
	/**
	 * Collects all subPackages that are descendants of a given EPackage.
	 * @param p
	 * @return The collection of all sub packages of the given package.
	 */
	public static Collection<EPackage> getAllSubPackages (EPackage p) {
		ArrayList<EPackage> result = new ArrayList<EPackage>();
		
		if (p != null) {
			Collection<EPackage> subPackages = p.getESubpackages();
			
			if (subPackages != null && subPackages.size() > 0) {
				result.addAll(subPackages);
				
				Iterator<EPackage> i = subPackages.iterator();
				while (i.hasNext()) {
					Collection <EPackage> subSubPackages = getAllSubPackages(i.next());
					result.addAll(subSubPackages);
				}
			}			
		}
		return result;
	}
	/**
	 * Collects all subPackages that are descendants of a given EPackage.
	 * @param p
	 * @param includeGiven
	 * @return The collection of all sub packages of the given package.
	 */
	public static Collection<EPackage> getAllSubPackages (EPackage p, boolean includeGiven) {
		Collection<EPackage> result = new ArrayList<EPackage>();
		if (includeGiven) {
			result.add(p);
		}
		result.addAll(getAllSubPackages(p));
		return result;
	}
	
	/**
	 * Collects a collection of all eClasses within a collection of given ePackages.
	 * @param packages
	 * @return The collection of eClasses within the given collection of ePackages.
	 */
	public static Collection<EClass> getAllPackageEClasses(Collection<EPackage> packages) {			
		Collection<EClass> result = new ArrayList<EClass>();		
		Iterator<EPackage> it = packages.iterator();
		
		while(it.hasNext()) {
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
	 * @param e
	 * @return The root ePackage
	 */
	public static EPackage getRootEPackage(EClass e) {
		return getRootEPackage(e.getEPackage());
	}
}
