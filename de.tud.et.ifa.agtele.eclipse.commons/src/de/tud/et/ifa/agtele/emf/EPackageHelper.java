package de.tud.et.ifa.agtele.emf;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.EcoreResourceFactoryImpl;
import org.eclipse.xsd.ecore.XSDEcoreBuilder;
import org.eclipse.xsd.impl.XSDSchemaImpl;

/**
 * This provides convenience methods that are related to {@link EPackage EPackages}.
 *
 */
public interface EPackageHelper {

	/**
	 * This tries to determine the ePackages defined in a meta-model (either Ecore or XSD). Therefore, the resource is
	 * loaded and the ePackages are extracted. If an XSD file is specified, corresponding EPackages are automatically
	 * generated via the {@link XSDEcoreBuilder}.
	 *
	 * @param absolutePathToMetaModelFile
	 *            the absolute path to the meta-model to be analyzed. This may point either to an Ecore or to an XSD
	 *            file.
	 * @param adaptResourceUri
	 *            if true, the uri of the resource that contains the ePackage will be set to the namespace uri of the
	 *            ePackage. That way, this namespace uri is used for references to the package during serialization of
	 *            resources, otherwise there will be file-based references
	 * @param register
	 *            if the packages shall be registered to the global ePackage registry.
	 * @return a map of nsUris and corresponding ePackages found in the Ecore/XSD meta-model, null if any error occurs.
	 */
	public static Map<String, EPackage> getEPackages(String absolutePathToMetaModelFile, boolean adaptResourceUri,
			boolean register) {

		HashMap<String, EPackage> ePackages = new HashMap<>();

		// try to load the resource
		Resource metamodel = null;

		try {
			metamodel = new ResourceSetImpl().getResource(URI.createFileURI(absolutePathToMetaModelFile), true);
		} catch (Exception e) {
			return null;
		}

		if (metamodel == null) {
			return null;
		}

		// get the contents of the resource
		EList<EObject> contents = metamodel.getContents();
		if (contents == null || contents.isEmpty()) {
			return null;
		}

		// if we deal with an XSD, we need to generate the corresponding
		// EPackages on-the-fly
		if (!(contents.get(0) instanceof EPackage)) {
			if (!(contents.get(0) instanceof XSDSchemaImpl)) {
				return null;
			}

			XSDEcoreBuilder builder = new XSDEcoreBuilder();
			contents = new BasicEList<>(builder.generate(URI.createFileURI(absolutePathToMetaModelFile)));

			if (!(contents.get(0) instanceof EPackage)) {
				return null;
			}
		}

		// the root ePackages defined by the metamodel
		//
		List<EPackage> rootEPackages = contents.stream().filter(e -> e instanceof EPackage).map(e -> (EPackage) e)
				.collect(Collectors.toList());

		// the complete list of packages defined by the meta-model including all
		// sub-packages
		//
		HashSet<EPackage> ePackageSet = EPackageHelper.collectEPackages(rootEPackages);

		for (EPackage ePackage : ePackageSet) {

			if (adaptResourceUri && ePackage.eResource() != null) {
				// adapt the uri of the resource; we do this by creating a new
				// (virtual) resource and adding the package as just changing
				// the URI of the existing resource leads to errors if one
				// resource contains multiple EPackages (in which case all but
				// the last package would end up with the wrong resource URI)
				//
				Resource newResource = new EcoreResourceFactoryImpl()
						.createResource(URI.createURI(ePackage.getNsURI()));
				newResource.getContents().add(ePackage);
			}

			ePackages.put(ePackage.getNsURI(), ePackage);

			if (register) {
				// register the packages to the global package registry
				EPackage.Registry.INSTANCE.put(ePackage.getNsURI(), ePackage);
			}
		}

		return ePackages;
	}

	/**
	 * Recursively collects the sub-packages of an ePackage and returns them as a set (including the root package
	 * itself).
	 *
	 * @see #collectEPackages(EPackage, boolean, boolean, Optional)
	 *
	 * @param ePackage
	 *            The root ePackage.
	 * @return A set of sub-ePackages including the root ePackage itself.
	 */
	public static Set<EPackage> collectEPackages(EPackage ePackage) {

		HashSet<EPackage> ePackages = new HashSet<>();
		ePackages.add(ePackage);
		for (EPackage child : ePackage.getESubpackages()) {
			ePackages.addAll(EPackageHelper.collectEPackages(child));
		}
		return ePackages;
	}

	/**
	 * Recursively collects the sub-packages of a list of ePackages and returns them as a set (including the root
	 * packages themselves).
	 *
	 * @param ePackages
	 *            The list of root ePackages.
	 * @return A set of sub-ePackages including the root ePackages themselves.
	 */
	public static HashSet<EPackage> collectEPackages(Collection<EPackage> ePackages) {

		HashSet<EPackage> ret = new HashSet<>();

		for (EPackage ePackage : ePackages) {
			ret.addAll(EPackageHelper.collectEPackages(ePackage));
		}

		return ret;
	}

	/**
	 * Recursively collect those {@link EPackage EPackages} that define the {@link EClass EClasses} that are
	 * <em>referenced</em> (via {@link EReference EReferences}) and/or <em>extended</em> (via
	 * {@link EClass#getEAllSuperTypes()}) by the {@link EClass EClasses} contained in the given {@link EPackage}.
	 * <p />
	 * Note: While {@link #collectEPackages(EPackage)} should be faster, this will also collect any referenced packages
	 * that are not contained in the given {@link EPackage}.
	 *
	 * @see #collectEPackages(EPackage)
	 *
	 * @param ePackage
	 *            The {@link EPackage} to scan.
	 * @param includeReferenced
	 *            Whether <em>referenced</em> (via {@link EReference EReferences}) classes resp. the containing packages
	 *            shall be considered.
	 * @param includeExtended
	 *            Whether <em>extended</em> (via {@link EClass#getEAllSuperTypes()}) classes resp. the containing
	 *            packages shall be considered.
	 * @param packagesToIgnore
	 *            An optional set of {@link EPackage EPackages} that shall be ignored.
	 * @return A set of ePackages including the root ePackage itself.
	 */
	public static Set<EPackage> collectEPackages(EPackage ePackage, boolean includeReferenced, boolean includeExtended,
			Optional<Set<EPackage>> packagesToIgnore) {

		Set<EPackage> ePackages = new HashSet<>();
		ePackages.add(ePackage);

		Set<EPackage> newPackagesToIngore = packagesToIgnore.orElse(new HashSet<>());
		newPackagesToIngore.add(ePackage);

		List<EClass> containedClasses = ePackage.getEClassifiers().stream().filter(e -> e instanceof EClass)
				.map(e -> (EClass) e).collect(Collectors.toList());

		Set<EPackage> referencedPackages = containedClasses.stream()
				.flatMap(c -> EPackageHelper
						.collectEPackages(c, includeReferenced, includeExtended, Optional.of(newPackagesToIngore))
						.stream())
				.collect(Collectors.toSet());

		ePackages.addAll(EPackageHelper.collectEPackages(referencedPackages, includeReferenced, includeExtended,
				Optional.of(newPackagesToIngore)));

		return ePackages;
	}

	/**
	 * Recursively collect those {@link EPackage EPackages} that define the {@link EClass EClasses} that are
	 * <em>referenced</em> (via {@link EReference EReferences}) and/or <em>extended</em> (via
	 * {@link EClass#getEAllSuperTypes()}) by the {@link EClass EClasses} contained in the given {@link EPackage
	 * EPackages}.
	 *
	 * @see #collectEPackages(EPackage, boolean, boolean, Optional)
	 *
	 * @param ePackages
	 *            The {@link EPackage EPackages} to scan.
	 * @param includeReferenced
	 *            Whether <em>referenced</em> (via {@link EReference EReferences}) classes resp. the containing packages
	 *            shall be considered.
	 * @param includeExtended
	 *            Whether <em>extended</em> (via {@link EClass#getEAllSuperTypes()}) classes resp. the containing
	 *            packages shall be considered.
	 * @param packagesToIgnore
	 *            An optional set of {@link EPackage EPackages} that shall be ignored.
	 * @return A set of ePackages including the root ePackage itself.
	 */
	public static Set<EPackage> collectEPackages(Set<EPackage> ePackages, boolean includeReferenced,
			boolean includeExtended, Optional<Set<EPackage>> packagesToIgnore) {

		return ePackages.stream().flatMap(
				p -> EPackageHelper.collectEPackages(p, includeReferenced, includeExtended, packagesToIgnore).stream())
				.collect(Collectors.toSet());
	}

	/**
	 * Collect those {@link EPackage EPackages} that define the {@link EClass EClasses} that are <em>referenced</em>
	 * (via {@link EReference EReferences}) and/or <em>extended</em> (via {@link EClass#getEAllSuperTypes()}) by the
	 * given {@link EClass}.
	 *
	 * @param eClass
	 *            The {@link EClass} to scan.
	 * @param includeReferenced
	 *            Whether <em>referenced</em> (via {@link EReference EReferences}) classes resp. the containing packages
	 *            shall be considered.
	 * @param includeExtended
	 *            Whether <em>extended</em> (via {@link EClass#getEAllSuperTypes()}) classes resp. the containing
	 *            packages shall be considered.
	 * @param packagesToIgnore
	 *            An optional set of {@link EPackage EPackages} that shall be ignored.
	 * @return The collected set of packages.
	 */
	public static Set<EPackage> collectEPackages(EClass eClass, boolean includeReferenced, boolean includeExtended,
			Optional<Set<EPackage>> packagesToIgnore) {

		Set<EPackage> packages = new HashSet<>();
		if (includeReferenced) {
			packages.addAll(eClass.getEAllReferences().stream().map(EReference::getEReferenceType)
					.map(EClass::getEPackage).filter(p -> !packagesToIgnore.orElse(new HashSet<>()).contains(p))
					.collect(Collectors.toSet()));
		}
		if (includeExtended) {
			packages.addAll(eClass.getEAllSuperTypes().stream().map(EClass::getEPackage)
					.filter(p -> !packagesToIgnore.orElse(new HashSet<>()).contains(p)).collect(Collectors.toSet()));
		}
		return packages;
	}

}
