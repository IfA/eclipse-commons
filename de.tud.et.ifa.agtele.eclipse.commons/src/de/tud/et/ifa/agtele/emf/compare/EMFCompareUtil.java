/**
 *
 */
package de.tud.et.ifa.agtele.emf.compare;

import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.compare.Comparison;
import org.eclipse.emf.compare.EMFCompare;
import org.eclipse.emf.compare.Match;
import org.eclipse.emf.compare.diff.DefaultDiffEngine;
import org.eclipse.emf.compare.diff.DiffBuilder;
import org.eclipse.emf.compare.scope.DefaultComparisonScope;
import org.eclipse.emf.compare.scope.IComparisonScope;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

/**
 * A class that provides various utility methods for performing comparisons with the help of {@link EMFCompare}.
 *
 * @author mfreund
 */
public class EMFCompareUtil {

	/**
	 * This creates an instance.
	 */
	private EMFCompareUtil() {
		// just to prevent the implicit public constructor
		//
	}

	/**
	 * This finds and returns a match for the given <em>origin</em> after comparing the two {@link Notifier Notifiers}
	 * <em>left</em> and <em>right</em>.
	 * <p />
	 * Note: If the given <em>origin</em> is part of <em>left</em>, the returned match will be part of <em>right</em>
	 * and vice versa.
	 * <p />
	 * Note: This method only returns a match of the same {@link EClass type} - if there is a matching element of
	 * another type, <em>null</em> is returned instead.
	 *
	 * @see #getMatchOfSameType(Notifier, Notifier, EObject)
	 *
	 * @param left
	 *            The {@link Notifier} representing the left part of the comparison (this might e.g. be a resource, a
	 *            model, or an excerpt of a model).
	 * @param right
	 *            The {@link Notifier} representing the right part of the comparison (this might e.g. be a resource, a
	 *            model, or an excerpt of a model).
	 * @param origin
	 *            The element for that a match shall be returned.
	 * @return The returned match of the same {@link EClass type} as the given <em>origin</em>, or <em>null</em> if no
	 *         match of the same type was found.
	 */
	public static EObject getMatch(Notifier left, Notifier right, EObject origin) {

		// Compare the left and right sides
		//
		IComparisonScope scope = new DefaultComparisonScope(left, right, null);
		EMFCompare comparator = EMFComparatorFactory.getComparator(new DefaultDiffEngine(new DiffBuilder()));
		Comparison result = comparator.compare(scope);

		// Find a match for the origin
		//
		Match match = result.getMatch(origin);

		if (match == null) {
			return null;
		}

		if (match.getLeft().equals(origin)) {
			return match.getRight();
		} else {
			return match.getLeft();
		}
	}

	/**
	 * This finds and returns a match for the given <em>origin</em> after comparing the two {@link Notifier Notifiers}
	 * <em>left</em> and <em>right</em>.
	 * <p />
	 * Note: If the given <em>origin</em> is part of <em>left</em>, the returned match will be part of <em>right</em>
	 * and vice versa.
	 * <p />
	 * Note: This method only returns a match of the same {@link EClass type} - if there is a matching element of
	 * another type, <em>null</em> is returned instead.
	 *
	 * @see #getMatch(Notifier, Notifier, EObject)
	 *
	 * @param <T>
	 *            The concrete type of the match to be found and returned.
	 * @param left
	 *            The {@link Notifier} representing the left part of the comparison (this might e.g. be a resource, a
	 *            model, or an excerpt of a model).
	 * @param right
	 *            The {@link Notifier} representing the right part of the comparison (this might e.g. be a resource, a
	 *            model, or an excerpt of a model).
	 * @param origin
	 *            The element for that a match shall be returned.
	 * @return The returned match of the same {@link EClass type} as the given <em>origin</em>, or <em>null</em> if no
	 *         match of the same type was found.
	 */
	@SuppressWarnings("unchecked")
	public static <T extends EObject> T getMatchOfSameType(Notifier left, Notifier right, T origin) {

		// Find a match for the origin
		//
		EObject match = EMFCompareUtil.getMatch(left, right, origin);

		return match == null || !origin.eClass().equals(match.eClass()) ? null : (T) match;

	}

	/**
	 * This compares the two {@link Notifier Notifiers} <em>left</em> and <em>right</em> and checks if they match
	 * without difference.
	 *
	 * @param left
	 *            The {@link Notifier} representing the left part of the comparison (this might e.g. be a resource, a
	 *            model, or an excerpt of a model).
	 * @param right
	 *            The {@link Notifier} representing the right part of the comparison (this might e.g. be a resource, a
	 *            model, or an excerpt of a model).
	 * @return '<em><b>true</b></em>' if the two given notifiers match, '<em><b>false</b></em>' otherwise.
	 */
	public static boolean isMatch(Notifier left, Notifier right) {

		// Compare the left and right sides
		//
		IComparisonScope scope = new DefaultComparisonScope(left, right, null);
		EMFCompare comparator = EMFComparatorFactory.getComparator(new DefaultDiffEngine(new DiffBuilder()));
		Comparison result = comparator.compare(scope);

		return result.getDifferences().isEmpty();

	}
}
