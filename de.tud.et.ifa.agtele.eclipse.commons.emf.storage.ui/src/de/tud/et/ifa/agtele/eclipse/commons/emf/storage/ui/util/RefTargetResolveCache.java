package de.tud.et.ifa.agtele.eclipse.commons.emf.storage.ui.util;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.eclipse.jface.dialogs.IDialogSettings;

import de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.ModelStorage;
import de.tud.et.ifa.agtele.eclipse.commons.emf.storage.ui.EMFStorageUIPlugin;
import de.tud.et.ifa.agtele.eclipse.commons.emf.storage.ui.util.ICacheChangeListener.CacheChangeNotification;
import de.tud.et.ifa.agtele.eclipse.commons.emf.storage.ui.util.ICacheChangeListener.ChangeType;

public class RefTargetResolveCache {

	protected boolean isInitialized = false;
	protected boolean isPersisted = false;
	
	protected Map<String, CacheEntryImpl> entries = new HashMap<>();
	protected List<CacheEntryImpl> entriesToRemove = new ArrayList<>();
	
	protected Set<ModelStorage> filteredSourceModelStorages = new HashSet<>();
	protected ModelStorage staticStorage;
	
	private RefTargetResolveCache(ModelStorage staticStorage) {
		this.staticStorage = staticStorage;
		filteredSourceModelStorages.add(staticStorage);
		if (staticStorage != ModelStorage.DEFAULT_INSTANCE) {
			filteredSourceModelStorages.add(ModelStorage.DEFAULT_INSTANCE);
		}
	};
	
	public IDialogSettings getDefaultDialogSettings () {
		return EMFStorageUIPlugin.getPlugin().getDialogSettings();
	}
	
	public synchronized void persist(IDialogSettings settings) { //TODO call from the editors
		if (this.isPersisted) {
			return;
		}
		this.isPersisted = true;
		if (settings == null) {
			if (this.staticStorage == ModelStorage.DEFAULT_INSTANCE) {
				settings = this.getDefaultDialogSettings();
			} else {
				return;
			}
		}
		IDialogSettings section = settings.getSection("REF_TARGET_RESOLVE_CACHE");
		if (section == null) {
			section = settings.addNewSection("REF_TARGET_RESOLVE_CACHE");			
		}
		for (CacheEntry entry : this.entriesToRemove) {
			IDialogSettings entrySection = section.getSection(entry.getId());
			if (entrySection != null) {
				entrySection.put("Label", "");
				entrySection.put("RESOUCES", new String[0]);
			}
		}
		
		for (CacheEntry entry : this.entries.values()) {
			IDialogSettings entrySection = section.addNewSection(entry.getId());
			entrySection.put("ID", entry.getId());
			entrySection.put("LABEL", entry.getCachedLabel());
			entrySection.put("RESOURCES", entry.getResources().toArray(new String[entry.getResources().size()]));
		}
	}

	public synchronized void restore(IDialogSettings settings) {
		if (this.isInitialized) {
			return;
		}
		this.isInitialized = true;

		if (settings == null) {
			if (this.staticStorage == ModelStorage.DEFAULT_INSTANCE) {
				settings = this.getDefaultDialogSettings();
			} else {
				return;
			}
		}
		IDialogSettings section = settings.getSection("REF_TARGET_RESOLVE_CACHE");
		if (section != null) {
			for (IDialogSettings entry : section.getSections()) {
				String id = entry.get("ID");
				String cachedLabel = entry.get("LABEL");
				String[] resources = entry.getArray("RESOURCES");
				if (resources.length > 0 && id != null && !id.isBlank()) {
					this.entries.put(id, new CacheEntryImpl(id, resources, cachedLabel));
				}
			}
		}
	}
	
	public CacheEntry getCacheEntry(String id) {
		return this.entries.get(id);
	}
	
	public boolean hasCacheEntry(String id) {
		return this.entries.containsKey(id);
	}
	
	protected CacheEntryImpl internalAddCacheEntry(String id, String resource, String label) {		
		if (this.hasCacheEntry(id) ) {
			CacheEntryImpl entry = this.entries.get(id);
			boolean updated = false;
			if (resource != null && !entry.getResources().contains(resource) ) {
				this.isPersisted = false;
				entry.addResource(resource);
			}
			if (label != null && entry.getCachedLabel() == null || label != null && !label.equals(entry.getCachedLabel())) {
				entry.setCachedLabel(label);
				updated = true;
			}
			if (updated) {
				this.notifyChanged(new CacheChangeNotification(id, ChangeType.UPDATED));				
			}
			return entry;
		} else {
			CacheEntryImpl entry = new CacheEntryImpl(id, resource);
			this.entries.put(id, entry);
			if (label != null) {
				entry.setCachedLabel(label);
			}
			this.isPersisted = false;
			this.notifyChanged(new CacheChangeNotification(id, ChangeType.ADDED));	
			return entry;
		}
	}
	
	public CacheEntry addCacheEntry(String id, String resource) {
		return this.internalAddCacheEntry(id, resource, null);
	}

	public CacheEntry addCacheEntry(String id, String resource, String label) {
		return this.internalAddCacheEntry(id, resource, label);
	}
	
	public void setLabel(String id, String label) {
		if (this.hasCacheEntry(id)) {
			CacheEntryImpl entry = this.entries.get(id);
			if (label == null && entry.getCachedLabel() != null || label != null && !label.equals(entry.getCachedLabel())) {
				entry.setCachedLabel(label);
				this.isPersisted = false;
				this.notifyChanged(new CacheChangeNotification(id, ChangeType.UPDATED));	
			}
		}
	}
	
	public void removeEntry(String id, String resource) {
		CacheEntryImpl entry = this.entries.get(id);
		if (entry != null) {
			boolean changed = false;
			if (entry.resources.contains(resource)) {
				entry.removeResource(resource);
				this.isPersisted = false;
				changed = true;
			}
			if (entry.getResources().isEmpty()) {
				this.entries.remove(id);
				this.entriesToRemove.add(entry);
				this.isPersisted = false;
				this.notifyChanged(new CacheChangeNotification(id, ChangeType.REMOVED));	
			} else if (changed) {
				this.notifyChanged(new CacheChangeNotification(id, ChangeType.UPDATED));	
			}
		}
	}
	
	public void removeResource(String resource) {
		for (CacheEntry e : new ArrayList<>(this.entries.values())) {
			this.removeEntry(e.getId(), resource);
		}
	}
	
	public boolean isInitialized() {
		return this.isInitialized;
	}

	protected static Map<ModelStorage, RefTargetResolveCache> INSTANCES = new ConcurrentHashMap<>();
	
	public static RefTargetResolveCache getInstance(ModelStorage staticStorageInstance) {
		if (staticStorageInstance == null) {
			staticStorageInstance = ModelStorage.DEFAULT_INSTANCE;
		}
		if (INSTANCES.get(staticStorageInstance) == null) {
			synchronized (RefTargetResolveCache.class) {
				if (INSTANCES.get(staticStorageInstance) == null) {
					RefTargetResolveCache newInstance = new RefTargetResolveCache(staticStorageInstance);
					INSTANCES.put(staticStorageInstance, newInstance);
				}
			}
		}
		return INSTANCES.get(staticStorageInstance);
	}
	
	public static RefTargetResolveCache initializeInstance(ModelStorage staticStorageInstance, IDialogSettings settings) {
		if (staticStorageInstance == null) {
			staticStorageInstance = ModelStorage.DEFAULT_INSTANCE;
		}
		if (INSTANCES.get(staticStorageInstance) == null) {
			synchronized (RefTargetResolveCache.class) {
				if (INSTANCES.get(staticStorageInstance) == null) {
					RefTargetResolveCache newInstance = new RefTargetResolveCache(staticStorageInstance);
					INSTANCES.put(staticStorageInstance, newInstance);
					newInstance.restore(settings);
				}
			}
		} else {			
			synchronized (RefTargetResolveCache.class) {
				INSTANCES.get(staticStorageInstance).restore(settings);
			}
		}
		return INSTANCES.get(staticStorageInstance);
	}
	
	public static interface CacheEntry {
		public Set<String> getResources();
		public String getId();
		public String getCachedLabel();
	}
	
	protected static class CacheEntryImpl implements CacheEntry {
		protected String id;
		protected LinkedHashSet<String> resources = new LinkedHashSet<>();
		protected String cachedLabel = null;

		protected CacheEntryImpl(String id, String resource) {
			this.id = id;
			this.resources.add(resource);
		}

		protected CacheEntryImpl(String id, String[] resources, String label) {
			this.id = id;
			this.cachedLabel = label;
			for (String str : resources) {
				this.resources.add(str);
			}
		}
		@Override
		public Set<String> getResources() {
			return new LinkedHashSet<>(this.resources);
		}

		@Override
		public String getId() {
			return this.id;
		}

		@Override
		public String getCachedLabel() {
			return this.cachedLabel;
		}
		
		protected void removeResource(String res) {
			this.resources.remove(res);
		}

		public void setCachedLabel(String label) {
			this.cachedLabel = label;			
		}

		public void addResource(String res) {
			this.resources.add(res);			
		}
	}
	
	protected List<WeakReference<ICacheChangeListener>> listeners = new ArrayList<>();
	
	public List<ICacheChangeListener> getCacheChangeListeners() {
		this.listeners.removeAll(this.listeners.parallelStream().filter(r -> r.get() == null).collect(Collectors.toList()));
		
		return this.listeners.parallelStream().map(r -> r.get()).collect(Collectors.toList());
	}

	public void registerCacheChangeListener(ICacheChangeListener listener) {
		this.listeners.add(new WeakReference<>(listener));
	}

	public void deregisterCacheChangeListener(ICacheChangeListener listener) {
		this.listeners.removeAll(this.listeners.parallelStream().filter(r -> r.get() == null || r.get() == listener).collect(Collectors.toList()));		
	}
	
	public void notifyChanged(CacheChangeNotification notification) {
		List<ICacheChangeListener> listeners = 
				this.getCacheChangeListeners().parallelStream().filter(l -> 
					(l.filterByChangeType() == null || l.filterByChangeType() == notification.getChangeType()) &&
					(l.filterById() == null || l.filterById().contains(notification.getId()))
						).collect(Collectors.toList());
		for (ICacheChangeListener listener : listeners) {
			listener.notifyChanged(notification);
		}
	}
	
//	
//	@Override
//	public Set<ModelStorage> filterByModelStorage() {
//		return this.filteredSourceModelStorages;
//	}
//
//	@Override
//	public ChangeType filterByChangeType() {
//		return ChangeType.REGISTERED;
//	}
//	
//	@Override
//	public void notifyChanged(RegistrationChangeNotification notification) {
//		EObject obj = notification.getElement();
//		
//		if (obj instanceof Identifier || obj instanceof Entity) {			
//			for (String id : notification.getIds()) {
//				this.addCacheEntry(id, resource)
//			}
//		}
//		
//	}
}
