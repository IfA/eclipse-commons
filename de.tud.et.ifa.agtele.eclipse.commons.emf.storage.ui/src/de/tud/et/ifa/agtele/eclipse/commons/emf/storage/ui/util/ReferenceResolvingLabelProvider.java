package de.tud.et.ifa.agtele.eclipse.commons.emf.storage.ui.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.EMFPlugin;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EContentAdapter;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.jface.viewers.StyledString.Styler;
import org.eclipse.swt.widgets.Display;

import de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.IRegistrationChangeListener;
import de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.IResolveResult;
import de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.ModelStorage;
import de.tud.et.ifa.agtele.eclipse.commons.emf.storage.ui.util.RefTargetResolveCache.CacheEntry;
import de.tud.et.ifa.agtele.emf.AgteleEcoreUtil;
import de.tud.et.ifa.agtele.emf.edit.AgteleStyledLabelProvider;

public abstract class ReferenceResolvingLabelProvider extends AgteleStyledLabelProvider {

	protected static final String refTargetSeparator = " \u2192 "; 
	protected static final String nonResolvedAppendix = "?"; 

	protected Set<String> requestedIds = new HashSet<>();
	protected Set<String> resolvedIds = new HashSet<>();
	

	protected RefreshRunner refreshRunner = this.getRefreshRunner();
	protected IRegistrationChangeListener registrationChangeListener = this.getRegistrationChangeListener();
	protected CacheChangeListener cacheChangeListener = this.getCacheChangeListener();
	
	protected ModelStorage modelStorage;
	protected RefTargetResolveCache refTargetResolveCache;
	
	public ReferenceResolvingLabelProvider(IStyledLabelProvider labelProvider, EMFPlugin emfPlugin) {
		super(labelProvider, emfPlugin);
	}

	public ReferenceResolvingLabelProvider(IStyledLabelProvider labelProvider, EMFPlugin emfPlugin, ModelStorage storage, RefTargetResolveCache cache) {
		this(labelProvider, emfPlugin);

		this.modelStorage = storage;
		this.modelStorage.registerGlobalRegistrationChangeListener(this.registrationChangeListener);

		this.refTargetResolveCache = cache;		
		this.refTargetResolveCache.registerCacheChangeListener(this.cacheChangeListener);
	}

	@Override
	protected StyledString getStyledText(Object element) {	
		StyledString result = super.getStyledText(element);
		if (element instanceof EObject && this.isReferencingElement(element)) {
			result = this.applyRefTargetLabel((EObject)element, result);
			if (result != null) {
				return result;
			}
			return super.getStyledText(element);
		}
		return result;
	}
	
	public abstract boolean isReferencingElement (Object element);
	public abstract List<String> getReferenceTargetIds (Object element);
	public abstract boolean isCacheRelevantElement (Object element);
	public abstract EContentAdapter getReferenceTargetUpdateNotifier (Object element);
	
	public StyledString applyVoidReferenceLabel(EObject element, StyledString originalLabel) {
		return this.basicApplyLabelModification(element, originalLabel, null, refTargetSeparator + nonResolvedAppendix);
	}
	
	public String getReferenceTargetLabelAppendix(EObject element, EObject target) {
		String result = target.eClass().getName();
		String elementName = ReferenceResolvingLabelProvider.getName(target);
	
		if (elementName != null) {
			result = result + " " + elementName;
		}		
		return result;
	}
	
	public Styler getRefTargetAppendixStyler (EObject element) {
		return StyledString.COUNTER_STYLER;
	}
	public Styler getRefTargetPrefixStyler (EObject element) {
		return StyledString.COUNTER_STYLER;
	}
	
	
	public StyledString basicApplyLabelModification(EObject element, StyledString original, String prefix, String appendix) {
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
	
	public StyledString applyResolvedReferenceLabel(EObject element, StyledString originalLabel, String labelAppendix) {
		return this.basicApplyLabelModification(element, originalLabel, null, refTargetSeparator + labelAppendix);
	}
	
	public StyledString applyRefTargetLabel (EObject element, StyledString originalLabel) {
		EContentAdapter refTargetUpdateNotifier = this.getReferenceTargetUpdateNotifier(element);
		if (refTargetUpdateNotifier != null && !element.eAdapters().contains(refTargetUpdateNotifier)) {
			element.eAdapters().add(refTargetUpdateNotifier);
		}
		
		
		List<String> idsToResolve = this.getReferenceTargetIds(element);
		
		if (idsToResolve.isEmpty() || !(element instanceof EObject)) {
			return this.applyVoidReferenceLabel(element, originalLabel);
		}
		
		List<IResolveResult> resolvedTargets = ReferenceResolvingLabelProvider.resolve(idsToResolve); 
		
		resolvedTargets = resolvedTargets != null ? this.filterResults(element, resolvedTargets) : null;
		
		if (resolvedTargets == null || resolvedTargets.isEmpty()) {
			return this.applyCachedReferenceLabel(element, originalLabel, idsToResolve);
		}
		
		IResolveResult picked = this.pickBestResolveResult(element, resolvedTargets, getElementResourceSet(element));
		
		if (picked == null) {
			return this.applyCachedReferenceLabel(element, originalLabel, idsToResolve);
		}
		
		EObject target = picked.getElement();
		
		target = this.getRefTargetLabelElement(target);

		EContentAdapter targetUpdateNotifier = this.getTargetNameUpdateNotifier();
		if (targetUpdateNotifier != null && !target.eAdapters().contains(targetUpdateNotifier)) {
			target.eAdapters().add(targetUpdateNotifier);
		}
		
		StyledString result = new StyledString();
		result.append(originalLabel);
		
		String labelAppendix = this.getReferenceTargetLabelAppendix(element, target);
		result = this.applyResolvedReferenceLabel(element, originalLabel, labelAppendix);
		
		if (this.isCacheRelevantElement(element)) {
			for (String id : idsToResolve) {
				if (ReferenceResolvingLabelProvider.getResourceId(target.eResource()) == null ) {
					this.refTargetResolveCache.addCacheEntry(id, picked.getModel().getUri(), labelAppendix);
				} else {
					this.refTargetResolveCache.addCacheEntry(id, ReferenceResolvingLabelProvider.getResourceId(target.eResource()), labelAppendix);
				}
				this.resolvedIds.add(id);
			}
		}
		return result;
		
	}
	
	protected StyledString applyCachedReferenceLabel(EObject element, StyledString originalLabel,
			List<String> idsToResolve) {
		StyledString result = new StyledString();
		boolean appliedCachedString = false;
		
		result.append(originalLabel);
		
		for (String id : idsToResolve) {
			if (!requestedIds.contains(id) && this.isCacheRelevantElement(element)) {
				this.requestedIds.add(id);		
			}
			if (this.refTargetResolveCache.hasCacheEntry(id) && !appliedCachedString) {
				CacheEntry entry = this.getCachedEntry(id, true);
				if (entry.getCachedLabel() != null) {
					result = this.basicApplyLabelModification(element, originalLabel, null, refTargetSeparator + entry.getCachedLabel());		
					appliedCachedString = true;
				}
			}
		}
		if (!appliedCachedString) {
			result = this.applyVoidReferenceLabel(element, originalLabel);
		}
		return result;
	}

	public EObject getRefTargetLabelElement(EObject resolved) {
		return resolved;
	}
	
	public List<IResolveResult> filterResults(EObject element, List<IResolveResult> resolves) {
		return resolves;
	}
	
	public List<Class<?>> getPreferredContributorClasses () {
		return Collections.emptyList();
	}
	
	public IResolveResult pickBestResolveResult (EObject element, List<IResolveResult> resolves, ResourceSet set) {
		if (resolves == null || resolves.isEmpty()) {
			return null;
		}
		
		IResolveResult result = null;
	
		//find result in the same resource first
		if (element != null) {
			result = resolves.stream().filter(r -> r.getElement().eResource() == element.eResource()).findFirst().orElse(null);
			if (result != null) {
				return result;
			}
		}
		
		//find result that is in the current resource set		
		result = resolves.stream().filter(r -> set.getResources().contains(r.getElement().eResource())).findFirst().orElse(null);
		if (result != null) {
			return result;
		}

		//find result, that is opened in one of the preferred contributors
		List<Class<?>> preferredContributorClasses = this.getPreferredContributorClasses();
		if (preferredContributorClasses != null && !preferredContributorClasses.isEmpty()) {
			for (Class<?> cls : preferredContributorClasses) {
				for (IResolveResult r : resolves) {
					if (r.getContributors().stream().anyMatch(c -> cls.isInstance(c))) {
						return r;
					}
				}
			}			
		}

		//find an element with at least one contributor
		result = resolves.stream().filter(r -> !r.getContributors().isEmpty() && r.getContributors().stream().anyMatch(c -> c.isWorkbenchPart())).findFirst().orElse(null);
		if (result != null) {
			return result;
		}
		return resolves.get(0);
	}

	public ResourceSet getElementResourceSet (Object element) {
		if (element instanceof EObject) {
			EObject obj = (EObject) element;
			if (obj.eResource() != null && obj.eResource().getResourceSet() != null) {
				return obj.eResource().getResourceSet();
			}			
		}
		return null;
	}

	protected static EAttribute getNameAttribute(EObject element) {
		return element.eClass().getEAllAttributes().stream().filter(a -> a.getName().toLowerCase().startsWith("name")).findFirst().orElse(null);
	}
	@SuppressWarnings("rawtypes")
	protected static String getName(EObject element) {
		EAttribute nameAttribute = ReferenceResolvingLabelProvider.getNameAttribute(element);
		if (nameAttribute != null && element.eGet(nameAttribute) != null) {
			String elementName = null;
			Object nameValue = element.eGet(nameAttribute);
			if (nameValue instanceof Collection) {
				for (Object nameVal : (Collection)nameValue) {
					if (nameVal == null) {
						continue;
					}
					if (nameVal instanceof String) {
						if (!((String)nameVal).isBlank()) {
							elementName = (String)nameVal;
							break;							
						}
						continue;
					}
					elementName = nameVal.toString();
					break;
				}
			} else {
				if (nameValue instanceof String && !((String)nameValue).isBlank()) {					
					elementName = (String)nameValue;
				} else if (!(nameValue instanceof String) && nameValue != null) {
					elementName = nameValue.toString();					
				}
			}
			return elementName;
		}
		return null;
	}

	public static List<IResolveResult> resolve(List<String> ids) {
		List<IResolveResult> result = new ArrayList<>();
		
		for (String id : ids) {
			result.addAll(ReferenceResolvingLabelProvider.resolve(id));
		}
		
		return result;
	}
	
	public static List<IResolveResult> resolve(String id) {
		if (id != null && !id.isBlank()) {
			return ModelStorage.resolveGlobal(id);
		}		
		return Collections.emptyList();
	}

	public static String getResourceId(Resource res) {
		if (res == null || res.getURI() == null) {
			return null;
		}
		return res.getURI().toString();
	}
	
	
	public CacheEntry getCachedEntry (String id, boolean withLabel) {
		return getCachedEntry(this.modelStorage, id, withLabel);
	}
	
	public static CacheEntry getCachedEntry(ModelStorage storage, String id, boolean withLabel) {
		if (id != null && RefTargetResolveCache.getInstance(storage).hasCacheEntry(id)) {
			CacheEntry entry = RefTargetResolveCache.getInstance(storage).getCacheEntry(id);
			if (!withLabel || entry.getCachedLabel() != null) {
				return entry;							
			}
		}
		return null;
	}
	
	protected abstract RefreshRunner getRefreshRunner ();
//	{
//		return new RefreshRunner ();
//	}

	protected class RefreshRunner implements Runnable {
		protected boolean refreshExecuting = false;
		protected boolean meanTimeRequests = false;
		
		synchronized boolean refreshExecuting(boolean change, boolean newValue) {
			if (change) {
				this.refreshExecuting = newValue;
			}
			return this.refreshExecuting;
		}
		
		synchronized boolean meanTimeRequests(boolean change, boolean newValue) {
			if (change) {
				this.meanTimeRequests = newValue;
			}
			return this.meanTimeRequests;
		}
		
		public void performRefresh () {	
		}
		
		@Override
		public void run() {
			this.meanTimeRequests(true,false);
			ReferenceResolvingLabelProvider.this.requestedIds.clear();
			ReferenceResolvingLabelProvider.this.resolvedIds.clear();
			this.performRefresh();
			if (this.meanTimeRequests(false,false)) {
				Display.getDefault().asyncExec(this);	
			} else {
				this.refreshExecuting(true,false);
			}
		}
		
		public void invoke() {
			if (!this.refreshExecuting(false,false)) {
				this.refreshExecuting(true,true);
				Display.getDefault().asyncExec(this);				
			} else {
				this.meanTimeRequests(true,true);
			}
		}
	}

	protected RegistrationChangeListener getRegistrationChangeListener () {
		return new RegistrationChangeListener();
	}
	
	protected class RegistrationChangeListener implements IRegistrationChangeListener {

		@Override
		public Set<String> filterById() {
			Set<String> listening = new HashSet<>();
			listening.addAll(requestedIds);
			listening.addAll(resolvedIds);
			return listening;
		};
		
		@Override
		public ChangeType filterByChangeType() {
			return null; //ChangeType.REGISTERED; //Listen to registration and deregistration
		}
		
		
		@Override
		public void notifyChanged(RegistrationChangeNotification notification) {
			ReferenceResolvingLabelProvider.this.refreshRunner.invoke();
		}		
	}

	protected CacheChangeListener getCacheChangeListener () {
		return new CacheChangeListener();
	}
	protected class CacheChangeListener implements ICacheChangeListener {
		@Override
		public Set<String> filterById() {
			return ReferenceResolvingLabelProvider.this.resolvedIds;
		}
		@Override
		public ChangeType filterByChangeType() {
			return ChangeType.REMOVED;
		}
		@Override
		public void notifyChanged(CacheChangeNotification notification) {
			ReferenceResolvingLabelProvider.this.refreshRunner.invoke();
		}
	}
	
	protected class ElementRemoveListener extends EContentAdapter {
		@SuppressWarnings({ "rawtypes", "unchecked" })
		@Override
		public void notifyChanged(Notification notification) {
			ArrayList<EObject> removed = new ArrayList<>();
			Resource res = notification.getNotifier() instanceof EObject ? ((EObject)notification.getNotifier()).eResource() : null;
			if (res != null && 
					notification.getFeature() instanceof EReference && 
					((EReference)notification.getFeature()).isContainment() && 
					(notification.getEventType() == Notification.REMOVE || notification.getEventType() == Notification.REMOVE_MANY)) {
				if (notification.getOldValue() instanceof Collection) {
					for (Object o : ((Collection)notification.getOldValue())) {
						if (!(o instanceof EObject)) {
							continue;
						}
						if (ReferenceResolvingLabelProvider.this.isReferencingElement(o)) {
							removed.add((EObject)o);
						}
						
						for (EObject obj : AgteleEcoreUtil.getAllInstances(EcorePackage.Literals.EOBJECT, (EObject)o)) {
							if (ReferenceResolvingLabelProvider.this.isReferencingElement(obj)) {
								removed.add((EObject)obj);
							}
						}
					}
				} else if (notification.getOldValue() instanceof EObject) {
					EObject o = (EObject) notification.getOldValue();
					if (ReferenceResolvingLabelProvider.this.isReferencingElement(o)) {
						removed.add(o);
					}
					
					for (EObject obj : AgteleEcoreUtil.getAllInstances(EcorePackage.Literals.EOBJECT, o)) {
						if (ReferenceResolvingLabelProvider.this.isReferencingElement(obj)) {
							removed.add((EObject)obj);
						}
					}
				}
				
				String resourceId = ReferenceResolvingLabelProvider.getResourceId(res);
				
				if (resourceId != null) {					
					for (EObject o : removed) {
						List<String> ids = ReferenceResolvingLabelProvider.this.getReferenceTargetIds(o);
						for (String id : ids) {
							ReferenceResolvingLabelProvider.this.refTargetResolveCache.removeEntry(id, resourceId);
						}
					}	
				}			
			}			
			super.notifyChanged(notification);
		}
	}
	
	ElementRemoveListener removeListener = new ElementRemoveListener();

	public EContentAdapter getElementRemoveListener () {
		return this.removeListener;
	}
	
	protected class RefTargetNameLabelUpdateNotifier extends EContentAdapter {		
		public void notifyChanged(Notification notification) {
			if (notification.getNotifier() instanceof EObject && notification.getFeature() == ReferenceResolvingLabelProvider.getNameAttribute((EObject)notification.getNotifier())) {
				ReferenceResolvingLabelProvider.this.refreshRunner.invoke();
			}
		};
	}
	RefTargetNameLabelUpdateNotifier labelTargetUpdater = new RefTargetNameLabelUpdateNotifier();
	
	public EContentAdapter getTargetNameUpdateNotifier () {
		return this.labelTargetUpdater;
	}
}
