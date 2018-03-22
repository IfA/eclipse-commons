/**
 *
 */
package de.tud.et.ifa.agtele.emf.connecting.impl;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import de.tud.et.ifa.agtele.emf.AgteleEcoreUtil;
import de.tud.et.ifa.agtele.emf.EPackageHelper;

/**
 * For a set of {@link EClass EClasses}, this registry keeps track of all sub-{@link EClass EClasses} and of all
 * {@link EReference EReferences} pointing to each of the classes.
 *
 * @author mfreund
 */
public class EClassConnectionInformationRegistry {

	private final Set<EPackage> registeredPackages;

	private final Set<EClass> registeredClasses;

	private final Map<EClass, Set<EClass>> classToSubClassesRegistry;

	private final Map<EClass, Set<EReference>> classToIncomingReferencesRegistry;

	private Optional<Logger> logger;

	/**
	 * Creates an instance without an attached logger.
	 *
	 * @see #EClassConnectionInformationRegistry(Logger)
	 */
	public EClassConnectionInformationRegistry() {

		this(null);
	}

	/**
	 * Creates an instance with an attached logger.
	 *
	 * @see #EClassConnectionInformationRegistry()
	 *
	 * @param logger
	 */
	public EClassConnectionInformationRegistry(Logger logger) {

		registeredPackages = Collections.synchronizedSet(new LinkedHashSet<>());
		registeredClasses = Collections.synchronizedSet(new LinkedHashSet<>());
		classToSubClassesRegistry = new ConcurrentHashMap<>();
		classToIncomingReferencesRegistry = new ConcurrentHashMap<>();
		this.logger = Optional.ofNullable(logger);
	}

	/**
	 * Registers the given collection of {@link EPackage EPackages} and all referenced packages to the registry.
	 *
	 * @param ePackages
	 */
	public synchronized void register(Collection<EPackage> ePackages) {

		Set<EPackage> ePackagesToRegister = getEPackagesToRegister(ePackages);

		registerEPackages(ePackagesToRegister);

		if (logger.isPresent()) {
			logger.get().info(() -> "The following EPackages were registered succesfully: "
					+ ePackagesToRegister.stream().map(EPackage::getName).collect(Collectors.joining(", ")));
		}

	}

	private Set<EPackage> getEPackagesToRegister(Collection<EPackage> ePackages) {

		Set<EPackage> ePackagesIncludingReferenced = EPackageHelper.collectEPackages(new LinkedHashSet<>(ePackages),
				true, true, true, Optional.empty());

		Set<EPackage> ePackagesToRegister = new LinkedHashSet<>(ePackagesIncludingReferenced);
		ePackagesToRegister.removeAll(registeredPackages);

		return ePackagesToRegister;
	}

	private void registerEPackages(Set<EPackage> ePackages) {

		registeredPackages.addAll(ePackages);

		Set<EClass> eClassesInEPackages = AgteleEcoreUtil.getAllClassesInEPackages(ePackages);

		this.register(eClassesInEPackages);
	}

	private void register(Set<EClass> eClasses) {

		Set<EClass> eClassesToRegister = new LinkedHashSet<>(eClasses);
		eClassesToRegister.removeAll(registeredClasses);

		registeredClasses.addAll(eClassesToRegister);

		this.registerInClassToSubClassesRegistry(eClassesToRegister);

		Set<EReference> eReferencesToRegister = eClassesToRegister.stream().flatMap(c -> c.getEAllReferences().stream())
				.collect(Collectors.toCollection(LinkedHashSet::new));

		this.registerInClassToIncomingReferencesRegistry(eReferencesToRegister);

	}

	private void registerInClassToSubClassesRegistry(Set<EClass> subClasses) {

		subClasses.stream().forEach(this::registerInClassToSubClassesRegistry);
	}

	private void registerInClassToSubClassesRegistry(EClass subClass) {

		List<EClass> superClasses = subClass.getEAllSuperTypes();
		superClasses.stream().forEach(superClass -> this.registerInClassToSubClassesRegistry(superClass, subClass));
	}

	private void registerInClassToSubClassesRegistry(EClass superClass, EClass subClass) {

		classToSubClassesRegistry.computeIfAbsent(superClass, c -> new LinkedHashSet<>());
		classToSubClassesRegistry.get(superClass).add(subClass);
	}

	private void registerInClassToIncomingReferencesRegistry(Set<EReference> incomingReferences) {

		incomingReferences.stream().forEach(this::registerInClassToIncomingReferencesRegistry);
	}

	private void registerInClassToIncomingReferencesRegistry(EReference incomingReference) {

		EClass referenceTarget = incomingReference.getEReferenceType();

		Set<EClass> referenceTargetIncludingSubClasses = new LinkedHashSet<>();
		referenceTargetIncludingSubClasses.add(referenceTarget);
		referenceTargetIncludingSubClasses.addAll(getAllSubClasses(referenceTarget));

		referenceTargetIncludingSubClasses.stream()
				.forEach(target -> this.registerInClassToIncomingReferencesRegistry(target, incomingReference));
	}

	private void registerInClassToIncomingReferencesRegistry(EClass targetClass, EReference incomingReference) {

		classToIncomingReferencesRegistry.computeIfAbsent(targetClass, c -> new LinkedHashSet<>());
		classToIncomingReferencesRegistry.get(targetClass).add(incomingReference);

	}

	/**
	 * @return an unmodifiable view of the {@link #registeredPackages}}
	 */
	public Set<EPackage> getRegisteredPackages() {

		return Collections.unmodifiableSet(registeredPackages);
	}

	/**
	 * @return an unmodifiable view of the {@link #registeredClasses}}
	 */
	public Set<EClass> getRegisteredClasses() {

		return Collections.unmodifiableSet(registeredClasses);
	}

	/**
	 * Returns all sub-classes of a specific {@link EClass} based on the {@link #classToSubClassesRegistry}.
	 * <p />
	 * If not already present in the registry, sub-classes are determined on-the-fly.
	 *
	 * @param superClass
	 *            The class to search sub-classes for
	 * @return the sub classes
	 */
	public Set<EClass> getAllSubClasses(EClass superClass) {

		if (!registeredClasses.contains(superClass)) {
			this.register(Arrays.asList(superClass.getEPackage()));
		}

		return Collections.unmodifiableSet(classToSubClassesRegistry.getOrDefault(superClass, new LinkedHashSet<>()));
	}

	/**
	 * Returns all {@link EReference references} pointing to the given {@link EClass} (or one of its super-types) based
	 * on the {@link #classToSubClassesRegistry}.
	 * <p />
	 * If not already present in the registry, references are determined on-the-fly.
	 *
	 * @param targetClass
	 *            The class to search incoming references for
	 * @return the references
	 */
	public Set<EReference> getAllReferencesToClass(EClass targetClass) {

		if (!registeredClasses.contains(targetClass)) {
			this.register(Arrays.asList(targetClass.getEPackage()));
		}

		return Collections
				.unmodifiableSet(classToIncomingReferencesRegistry.getOrDefault(targetClass, new LinkedHashSet<>()));
	}

	/**
	 * Retrieve all {@link EClass EClasses} that define/hold the given {@link EReference}.
	 * <p />
	 * Note: This will return the EClass defining the given EReference as well as all EClasses inheriting from this
	 * EClass.
	 *
	 * @param reference
	 *            The reference for that the defining/holding classes shall be retrieved
	 * @return the classes
	 */
	public Set<EClass> getAllDefiningClassesForReference(final EReference reference) {

		Set<EClass> referenceSources = new LinkedHashSet<>();

		referenceSources.add(reference.getEContainingClass());
		referenceSources.addAll(getAllSubClasses(reference.getEContainingClass()));

		return referenceSources;

	}

	/**
	 * Clear the registry.
	 *
	 */
	public void clear() {

		classToSubClassesRegistry.clear();
	}

}
