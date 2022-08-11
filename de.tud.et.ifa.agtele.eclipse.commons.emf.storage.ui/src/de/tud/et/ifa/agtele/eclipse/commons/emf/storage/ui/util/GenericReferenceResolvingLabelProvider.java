package de.tud.et.ifa.agtele.eclipse.commons.emf.storage.ui.util;

import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.EMFPlugin;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.edit.provider.ItemProviderAdapter;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.jface.viewers.StyledString.Styler;

import de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.IResolveResult;
import de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.ModelStorage;
import de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.util.ReferenceResolvingLabelItemProviderAdapter;
import de.tud.et.ifa.agtele.emf.AgteleEcoreUtil;

public abstract class GenericReferenceResolvingLabelProvider extends ReferenceResolvingLabelProvider {

	public GenericReferenceResolvingLabelProvider(IStyledLabelProvider labelProvider, EMFPlugin emfPlugin,
			ModelStorage storage, RefTargetResolveCache cache, ResourceSet set) {
		super(labelProvider, emfPlugin, storage, cache, set);
	}

	public ReferenceResolvingLabelItemProviderAdapter adapt(EObject element) {
		ItemProviderAdapter result = AgteleEcoreUtil.getItemProviderAdapter(element);
		if (result instanceof ReferenceResolvingLabelItemProviderAdapter) {
			return (ReferenceResolvingLabelItemProviderAdapter) result;
		}
		return null;
	}
	
	@Override
	public boolean isReferencingElement(Object element) {
		if (!(element instanceof EObject)) {
			return false;
		}
		ReferenceResolvingLabelItemProviderAdapter adapter = this.adapt((EObject)element);
		if (adapter == null) {
			return false;
		}
		return adapter.isReferencingElement(element);
	}
	
	@Override
	public boolean isMultiTargetReferenceElement (EObject element) {
		if (!(element instanceof EObject)) {
			return false;
		}
		ReferenceResolvingLabelItemProviderAdapter adapter = this.adapt((EObject)element);
		if (adapter == null) {
			return false;
		}
		return adapter.isMultiTargetReferenceElement(element);
	}
	
	@Override
	public EObject getRefTargetLabelElement(EObject element) {
		if (!(element instanceof EObject)) {
			return super.getRefTargetLabelElement(element);
		}
		ReferenceResolvingLabelItemProviderAdapter adapter = this.adapt((EObject)element);
		if (adapter == null) {
			return super.getRefTargetLabelElement(element);
		}
		return adapter.getRefTargetLabelElement(element);
	}

	@Override
	public List<String> getReferenceTargetIds(Object element) {
		if (!(element instanceof EObject)) {
			return Collections.emptyList();
		}
		ReferenceResolvingLabelItemProviderAdapter adapter = this.adapt((EObject)element);
		if (adapter == null) {
			return Collections.emptyList();
		}
		return adapter.getReferenceTargetIds(element);
	}

	@Override
	public boolean isCacheRelevantElement(Object element) {
		if (!(element instanceof EObject)) {
			return false;
		}
		ReferenceResolvingLabelItemProviderAdapter adapter = this.adapt((EObject)element);
		if (adapter == null) {
			return false;
		}
		return adapter.isCacheRelevantElement(element);
	}
	
	@Override
	public boolean isRefTargetIdFeature(EObject element, EStructuralFeature feature) {
		if (!(element instanceof EObject)) {
			return false;
		}
		ReferenceResolvingLabelItemProviderAdapter adapter = this.adapt((EObject)element);
		if (adapter == null) {
			return false;
		}
		return adapter.isRefTargetIdFeature(element, feature);
	}
	
	@Override
	public List<EObject> getRefTargetUpdateNotificationReceivers(EObject element, EStructuralFeature feature) {
		if (!(element instanceof EObject)) {
			return Collections.emptyList();
		}
		ReferenceResolvingLabelItemProviderAdapter adapter = this.adapt((EObject)element);
		if (adapter == null) {
			return Collections.emptyList();
		}
		return adapter.getRefTargetUpdateNotificationReceivers(element, feature);
	}

	@Override
	public Styler getRefTargetAppendixStyler(EObject element) {
		ReferenceResolvingLabelItemProviderAdapter adapter = this.adapt((EObject)element);
		if (adapter == null) {
			return super.getRefTargetAppendixStyler(element);
		}
		return adapter.getRefTargetAppendixStyler(element);
	}
	
	@Override
	public Styler getRefTargetPrefixStyler(EObject element) {
		ReferenceResolvingLabelItemProviderAdapter adapter = this.adapt((EObject)element);
		if (adapter == null) {
			return super.getRefTargetPrefixStyler(element);
		}
		return adapter.getRefTargetPrefixStyler(element);
	}
	
	@Override
	public StyledString basicApplyLabelModification(EObject element, StyledString original, String prefix,
			String appendix) {
		ReferenceResolvingLabelItemProviderAdapter adapter = this.adapt((EObject)element);
		if (adapter == null) {
			return super.basicApplyLabelModification(element, original, prefix, appendix);
		}
		return adapter.basicApplyLabelModification(element, original, prefix, appendix);
	}
	
	@Override
	public List<IResolveResult> filterResults(EObject element, List<IResolveResult> resolves) {
		ReferenceResolvingLabelItemProviderAdapter adapter = this.adapt((EObject)element);
		if (adapter == null) {
			return super.filterResults(element, resolves);
		}
		return adapter.filterResults(element, resolves);
	}

}
