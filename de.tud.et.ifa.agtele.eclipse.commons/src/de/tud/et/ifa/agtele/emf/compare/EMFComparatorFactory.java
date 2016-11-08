/**
 * 
 */
package de.tud.et.ifa.agtele.emf.compare;

import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.compare.Diff;
import org.eclipse.emf.compare.EMFCompare;
import org.eclipse.emf.compare.Match;
import org.eclipse.emf.compare.EMFCompare.Builder;
import org.eclipse.emf.compare.diff.DefaultDiffEngine;
import org.eclipse.emf.compare.diff.DiffBuilder;
import org.eclipse.emf.compare.diff.FeatureFilter;
import org.eclipse.emf.compare.diff.IDiffEngine;
import org.eclipse.emf.compare.match.DefaultComparisonFactory;
import org.eclipse.emf.compare.match.DefaultEqualityHelperFactory;
import org.eclipse.emf.compare.match.DefaultMatchEngine;
import org.eclipse.emf.compare.match.IComparisonFactory;
import org.eclipse.emf.compare.match.IMatchEngine;
import org.eclipse.emf.compare.match.eobject.IEObjectMatcher;
import org.eclipse.emf.compare.match.impl.MatchEngineFactoryImpl;
import org.eclipse.emf.compare.match.impl.MatchEngineFactoryRegistryImpl;
import org.eclipse.emf.compare.scope.IComparisonScope;
import org.eclipse.emf.compare.utils.UseIdentifiers;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;

/**
 * This class provides some convenience methods to create comparators (instances of {@link EMFCompare}) that can be
 * used to {@link EMFCompare#compare(IComparisonScope) compare} model elements, models, or complete resources.
 *
 * @author mfreund
 */
public class EMFComparatorFactory {

	/**
	 * The {@link IMatchEngine.Factory.Registry} to be used during the creation of new {@link EMFCompare} objects
	 * by {@link #getComparator(IDiffEngine)}.
	 */
	private static IMatchEngine.Factory.Registry matchEngineRegistry;

	/**
	 * This returns an {@link EMFCompare} object that can be used to compare {@link Notifier Notifiers}.
	 * It makes use of a default implementation of {@link IEObjectMatcher} and - unless a custom
	 * implementation is provides via the '<em>diffEngine</em>' parameter - of a default implementation of
	 * {@link IDiffEngine}.
	 * <p/>
	 * Note: This is taken from <a href="https://www.eclipse.org/emf/compare/documentation/latest/developer/developer-guide.html#Using_The_Compare_APIs">
	 * https://www.eclipse.org/emf/compare/documentation/latest/developer/developer-guide.html#Using_The_Compare_APIs</a>.
	 *
	 * @param diffEngine The {@link IDiffEngine} to be used during comparisons. If this is '<em><b>null</b></em>',
	 * a default implementation will be used.
	 * @return An instance of {@link EMFCompare} that can be used to compare {@link Notifier Notifiers}.
	 */
	public static EMFCompare getComparator(IDiffEngine diffEngine) {

		/*
		 * Initialize the match engine registry (this has to be done only once.
		 */
		if(EMFComparatorFactory.matchEngineRegistry == null) {
			IEObjectMatcher matcher = DefaultMatchEngine.createDefaultEObjectMatcher(UseIdentifiers.WHEN_AVAILABLE);
			IComparisonFactory comparisonFactory = new DefaultComparisonFactory(new DefaultEqualityHelperFactory());

			IMatchEngine.Factory matchEngineFactory = new MatchEngineFactoryImpl(matcher, comparisonFactory);
			matchEngineFactory.setRanking(20);
			EMFComparatorFactory.matchEngineRegistry = new MatchEngineFactoryRegistryImpl();
			EMFComparatorFactory.matchEngineRegistry.add(matchEngineFactory);
		}

		/*
		 * Create a new comparator with the specified diff engine.
		 */
		Builder builder = EMFCompare.builder().
				setMatchEngineFactoryRegistry(EMFComparatorFactory.matchEngineRegistry);
		if(diffEngine != null) {
			builder.setDiffEngine(diffEngine);
		}
		return builder.build();

	}

	/**
	 * This returns an {@link EMFCompare} object that can be used to compare {@link Notifier Notifiers}.
	 * It makes use of a default implementation of {@link IEObjectMatcher} and of an implementation of
	 * {@link IDiffEngine} that <b>ignores any changes to {@link EReference EReferences}</b>. Consequently, only changes to the
	 * elements themselves (including attribute changes) are marked as {@link Diff Differences} - changes to
	 * contained or referenced elements are not marked!
	 * <p/>
	 * Note: This is taken from <a href="https://www.eclipse.org/emf/compare/documentation/latest/developer/developer-guide.html#Using_The_Compare_APIs">
	 * https://www.eclipse.org/emf/compare/documentation/latest/developer/developer-guide.html#Using_The_Compare_APIs</a>.
	 *
	 * @return An instance of {@link EMFCompare} that can be used to compare {@link Notifier Notifiers}.
	 */
	public static EMFCompare getIgnoreAllReferenceChangesComparator() {
		return EMFComparatorFactory.getComparator(new DefaultDiffEngine(new DiffBuilder()) {
			@Override
			protected FeatureFilter createFeatureFilter() {
				return new FeatureFilter() {
					@Override
					protected boolean isIgnoredReference(Match match, EReference reference) {
						/*
						 * We ignore changes to references (cf. https://www.eclipse.org/emf/compare/documentation/latest/developer/developer-guide.html#Changing_the_FeatureFilter).
						 */
						return true;
					}

					@Override
					public boolean checkForOrderingChanges(EStructuralFeature feature) {
						return false;
					}
				};
			}
		});
	}

	/**
	 * This returns an {@link EMFCompare} object that can be used to compare {@link Notifier Notifiers}.
	 * It makes use of a default implementation of {@link IEObjectMatcher} and of an implementation of
	 * {@link IDiffEngine} that <b>ignores any changes to non-containment {@link EReference EReferences}</b>. Consequently, only changes to the
	 * elements themselves (including attribute changes and changes to contained elements) are marked as {@link Diff Differences} - changes to
	 * referenced elements are not marked!
	 * <p/>
	 * Note: This is taken from <a href="https://www.eclipse.org/emf/compare/documentation/latest/developer/developer-guide.html#Using_The_Compare_APIs">
	 * https://www.eclipse.org/emf/compare/documentation/latest/developer/developer-guide.html#Using_The_Compare_APIs</a>.
	 *
	 * @return An instance of {@link EMFCompare} that can be used to compare {@link Notifier Notifiers}.
	 */
	public static EMFCompare getIgnoreNonContainmentReferenceChangesComparator() {
		return EMFComparatorFactory.getComparator(new DefaultDiffEngine(new DiffBuilder()) {
			@Override
			protected FeatureFilter createFeatureFilter() {
				return new FeatureFilter() {
					@Override
					protected boolean isIgnoredReference(Match match, EReference reference) {
						/*
						 * We ignore changes to non-containment references (cf. https://www.eclipse.org/emf/compare/documentation/latest/developer/developer-guide.html#Changing_the_FeatureFilter).
						 */
						return !reference.isContainment();
					}

					@Override
					public boolean checkForOrderingChanges(EStructuralFeature feature) {
						return false;
					}
				};
			}
		});
	}

}