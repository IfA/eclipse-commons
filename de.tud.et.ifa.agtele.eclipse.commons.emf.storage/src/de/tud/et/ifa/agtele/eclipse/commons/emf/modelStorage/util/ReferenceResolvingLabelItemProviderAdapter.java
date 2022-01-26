package de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.util;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.jface.viewers.StyledString.Styler;

import de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.IResolveResult;

public interface ReferenceResolvingLabelItemProviderAdapter {

	boolean isReferencingElement (Object element);
	boolean isRefTargetIdFeature (EObject element, EStructuralFeature feature);
	List<EObject> getRefTargetUpdateNotificationReceivers(EObject element, EStructuralFeature feature);	
	List<String> getReferenceTargetIds (Object element);
	
	default boolean isCacheRelevantElement (Object element) {
		return true;
	}
	
	default public StyledString basicApplyLabelModification(EObject element, StyledString original, String prefix, String appendix) {
		StyledString result = new StyledString();
		if (prefix != null) {
			result.append(prefix, this.getRefTargetPrefixStyler(element));
		}
		result.append(original);
		if (appendix != null) {
			result.append(appendix, this.getRefTargetAppendixStyler(element));
		}
		return result;
	}

	default public Styler getRefTargetAppendixStyler (EObject element) {
		return StyledString.COUNTER_STYLER;
	}
	default public Styler getRefTargetPrefixStyler (EObject element) {
		return StyledString.COUNTER_STYLER;
	}
	
	public default EObject getRefTargetLabelElement(EObject resolved) {
		return resolved;
	}
	
	public default List<IResolveResult> filterResults(EObject element, List<IResolveResult> resolves) {
		return resolves;
	}
	
	public default boolean isMultiTargetReferenceElement (EObject element) {
		return false;
	}
	
}
