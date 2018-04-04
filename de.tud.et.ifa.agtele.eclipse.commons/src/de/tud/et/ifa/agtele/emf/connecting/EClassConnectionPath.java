/**
 *
 */
package de.tud.et.ifa.agtele.emf.connecting;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;

import de.tud.et.ifa.agtele.emf.connecting.EClassConnectionPathInstantiator.EClassConnectionPathInstantiationException;

/**
 * Instances of this represent a connection path between two {@link EClass EClasses} via one or multiple
 * {@link EReference EReferences} (and intermediate EClasses in case of multiple references).
 *
 * @author mfreund
 */
public interface EClassConnectionPath {

	/**
	 * @return The starting {@link EClass} of the path.
	 */
	public EClass getStartingClass();

	/**
	 * @return The target {@link EClass} of the path.
	 */
	public EClass getTargetClass();

	/**
	 * @return The {@link Length} of the path.
	 */
	public Length getLength();

	/**
	 * Calculates the path's <em>theoretical</em> {@link Capacity} (how many elements of the {@link #getTargetClass()
	 * targetClass} can be connected to one instance of the {@link #getStartingClass() startingClass} via this path).
	 *
	 * @return The capacity of this path.
	 */
	public Capacity getTheoreticalCapacity();

	/**
	 * Calculates the paths <em>actual</em> {@link Capacity} for the given {@link EObject startingElement} (how many
	 * elements of the {@link #getTargetClass() targetClass} can be connected to the startingElement via this path).
	 * <p />
	 * Note: The actual capacity of a path is always less or equal to its {@link #getTheoreticalCapacity() theoretical
	 * capacity} because it takes into account existing elements along the path that reduce the available capacity.
	 *
	 * @param startingElement
	 * @return The capacity of this path.
	 */
	public Capacity getActualCapacity(EObject startingElement);

	/**
	 * The elements that are connected to the given {@link EObject startingElement} via this path.
	 */
	@SuppressWarnings("javadoc")
	public List<EObject> getExistingTargetElements(EObject startingElement);

	/**
	 * Whether following this path based on the given {@link EObject startingElement} leads to the given {@link EObject
	 * targetElement}.
	 */
	@SuppressWarnings("javadoc")
	public boolean describesConnectionBetween(EObject startingElement, EObject targetElement);

	/**
	 * @return A list of all {@link EClass EClasses} that make up this path.
	 */
	public List<EClass> getAllClasses();

	/**
	 * @return A list of all {@link EReference EReferences} that make up this path.
	 */
	public List<EReference> getAllReferences();

	/**
	 * @return '<em>true</em>' if {@link #getAllClasses()} contains the same EClass twice.
	 */
	public default boolean containsLoop() {

		return new HashSet<>(getAllClasses()).size() < getAllClasses().size();
	}

	/**
	 * @return '<em>true</em>' if any of the {@link #getAllClasses()} except the {@link #getStartingClass()} or the
	 *         {@link #getTargetClass()} is {@link EClass#isAbstract() abstract}.
	 */
	public default boolean containsIntermediateAbstractClass() {

		LinkedList<EClass> classList = new LinkedList<>(getAllClasses());
		try {
			classList.removeFirst();
			classList.removeLast();
		} catch (NoSuchElementException e) {
			return false;
		}
		return classList.stream().anyMatch(EClass::isAbstract);

	}

	/**
	 * @param startingElement
	 * @return '<em>true</em>' if the given {@link EObject startingElement} is an instance of the
	 *         {@link #getStartingClass()}
	 */
	public default boolean isValidStartingElement(EObject startingElement) {

		return getStartingClass() != null && getStartingClass().isInstance(startingElement);
	}

	/**
	 * @param targetElement
	 * @return '<em>true</em>' if the given {@link EObject targetElement} is an instance of the
	 *         {@link #getTargetClass()}
	 */
	public default boolean isValidTargetElement(EObject targetElement) {

		return getTargetClass() != null && getTargetClass().isInstance(targetElement);
	}

	/**
	 * Create an instantiator that can be used to instantiate the this path between the given {@link EObject
	 * startingElement} and the list of {@link EObject targetElements}.
	 *
	 * @param startingElement
	 * @param targetElements
	 * @return An {@link EClassConnectionPathInstantiator} that can be used to instantiate this connection.
	 *
	 * @throws EClassConnectionPathInstantiationException
	 *             If the given startingElement and/or targetElements do not satisfy the starting/target class of this
	 *             path.
	 */
	public EClassConnectionPathInstantiator createInstantiator(EObject startingElement,
			Collection<EObject> targetElements);
}
