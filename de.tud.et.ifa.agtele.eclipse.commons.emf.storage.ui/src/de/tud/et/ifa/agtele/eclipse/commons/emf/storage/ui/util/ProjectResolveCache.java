package de.tud.et.ifa.agtele.eclipse.commons.emf.storage.ui.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import de.tud.et.ifa.agtele.eclipse.commons.emf.storage.ui.util.ICacheChangeListener.ChangeType;
import de.tud.et.ifa.agtele.eclipse.commons.emf.storage.ui.util.RefTargetResolveCache.CacheEntry;
import de.tud.et.ifa.agtele.eclipse.commons.emf.storage.ui.util.RefTargetResolveCache.CacheEntryImpl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EContentAdapter;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchListener;
import org.eclipse.ui.PlatformUI;

public class ProjectResolveCache {
	
	public static final String PROJECT_RESOLVE_CACHE_FILENAME = ".resolveCache"; 
	
	protected static Map<IProject, ProjectResolveCache> projectCaches = new ConcurrentHashMap<>();
	
	protected IProject project = null;
	protected List<IFile> activeResolvingFiles = new ArrayList<>();
	protected IFile persistedFile = null;
	
	protected Map<RefTargetResolveCache, Set<CacheEntry>> entries = new ConcurrentHashMap<>();
	
	protected Map<String, Set<CacheEntry>> restoredEntries = new HashMap<>();
	protected Set<CacheEntry> restoredNoStorageIdEntries = new LinkedHashSet<>();
	
	protected boolean persisted = false;
	
	protected List<ReferenceResolvingLabelProvider> providers = new CopyOnWriteArrayList<>();
	protected List<List<String>> relatedStringLists = new CopyOnWriteArrayList<>();
	
	private ProjectResolveCache (IProject project, RefTargetResolveCache cache) {
		this.project = project;
		
		IWorkbench workbench = PlatformUI.getWorkbench();
		
		workbench.addWorkbenchListener(new IWorkbenchListener() {
			@Override
			public boolean preShutdown(IWorkbench workbench, boolean forced) {
				ProjectResolveCache.this.persist();
				return true;
			}

			@Override
			public void postShutdown(IWorkbench workbench) {
			}			
		});
	}
	
	protected synchronized boolean activeResolvingFilesOperation(boolean checkContains, boolean add, IFile file) {
		if (checkContains) {
			if (file != null) {
				return this.activeResolvingFiles.contains(file);
			} else {
				return !this.activeResolvingFiles.isEmpty();
			}
		}
		if (add) {
			this.activeResolvingFiles.add(file);
			return true;
		}
		if (this.activeResolvingFiles.contains(file)) {
			this.activeResolvingFiles.remove(file);
			return true;			
		}
		return false;
	}
	
	protected void initialize () {
		this.persistedFile = 
				this.project.getFile(PROJECT_RESOLVE_CACHE_FILENAME);
		if (this.persistedFile.exists()) {
			InputStream iStream = null;
			try {
				iStream = this.persistedFile.getContents();
				
				String manifestContent = new String(iStream.readAllBytes(), StandardCharsets.UTF_8);  // IOUtils.toString(file.getContents(), file.getCharset());
				
				JsonObject manifestObject = (JsonObject) JsonParser.parseString(manifestContent);
				this.restore(manifestObject);	
			} catch (IOException | CoreException e) {
				e.printStackTrace();
			} finally {
				if (iStream != null) {
					try {
						iStream.close();
					} catch (IOException e2) {
						e2.printStackTrace();
					}
				}
			}
			
			//TODO register file modification listener in order to deal with vcs updates
		}
	}
	
	protected void activateRefTargetResolveCache (RefTargetResolveCache cache) {
		if (this.entries.containsKey(cache)) {
			return;
		}
		Set<CacheEntry> activatedEntries = new LinkedHashSet<>();
		
		
		String id = null;
		if (cache.getModelStorage() != null && cache.getModelStorage().getName() != null && !cache.getModelStorage().getName().isBlank()) {
			id = cache.getModelStorage().getName();
		}
		
		// apply restored entries
		if (id == null) {
			for (CacheEntry e : this.restoredNoStorageIdEntries) {
				CacheEntry activatedEntry = cache.addCacheEntry(e.getId(), e.getResources(), e.getCachedLabel());
				if (activatedEntry != null) {
					activatedEntries.add(activatedEntry);
				}
			}
		} else if (this.restoredEntries.containsKey(id)) {
			for (CacheEntry e : this.restoredEntries.get(id)) {
				CacheEntry activatedEntry = cache.addCacheEntry(e.getId(), e.getResources(), e.getCachedLabel());
				if (activatedEntry != null) {
					activatedEntries.add(activatedEntry);
				}
			}
			
			this.restoredEntries.remove(id);
		}
		
		this.entries.put(cache, activatedEntries);
				
		this.addCacheChangeListener(cache);
	}
	
	protected void removeEntry (RefTargetResolveCache cache, String id) {
		for (CacheEntry e : this.entries.get(cache)) {
			if (e.getId().equals(id)) {
				this.entries.get(cache).remove(e);
				return;
			}
		}
	}

	protected void addCacheChangeListener(RefTargetResolveCache cache) {
		cache.registerCacheChangeListener((notification) -> {	
				if (ProjectResolveCache.this.isRelevantRefTarget(notification.getId())) {
					if (notification.getChangeType() == ChangeType.ADDED) {
						ProjectResolveCache.this.entries.get(cache).add(cache.getCacheEntry(notification.getId()));
						this.persisted = false;
					} else if (notification.getChangeType() == ChangeType.REMOVED) {
						ProjectResolveCache.this.removeEntry(cache, notification.getId());
						this.persisted = false;
					}
				}				
		});
	}


	protected static final String SERIALIZED_STORAGE_CACHE_PROPERTY = "storageLabelCache";
	protected static final String SERIALIZED_STORAGE_CACHE_NO_ID_PROPERTY = "storagaNoIdLabelCache";
	
	protected static final String SERIALIZED_ID_PROPERTY = "id";
	protected static final String SERIALIZED_LABEL_PROPERTY = "label";
	protected static final String SERIALIZED_RESOURCES_PROPERTY = "resources";
	
	protected Set<CacheEntry> restoreCacheEntries (JsonArray jEntries) {
		Set<CacheEntry> result = new LinkedHashSet<>();
		
		for (JsonElement jE : jEntries) {
			if (!(jE instanceof JsonObject)) {
				continue;
			}
			JsonObject jEntry = (JsonObject) jE;
			
			String id = null, label = null;
			List<String> resources = new ArrayList<>();;
			JsonArray jResources = null;
			
			try {
				id = jEntry.get(SERIALIZED_ID_PROPERTY).getAsString();
				label = jEntry.get(SERIALIZED_LABEL_PROPERTY).getAsString();
				jResources = jEntry.get(SERIALIZED_RESOURCES_PROPERTY).getAsJsonArray();
			} catch (Exception e) {
				continue;
			}
			
			for (JsonElement jResE : jResources) {
				try {
					String res = jResE.getAsString();
					resources.add(res);
				} catch (Exception e2) {
					continue;
				}
			}
			if (jResources.isEmpty()) {
				continue;
			}
			
			CacheEntry cacheEntry = new RefTargetResolveCache.CacheEntryImpl(id, label);
			cacheEntry.getResources().addAll(resources);
			result.add(cacheEntry);
		}
		
		return result;
	}
	
	protected void restore(JsonObject manifest) {		
		try {
			JsonObject jO = manifest.getAsJsonObject(SERIALIZED_STORAGE_CACHE_PROPERTY);
			for (String o : jO.keySet()) {
				if (o == null || o.isBlank()) {
					continue;
				}
				
				try {
					JsonArray a = jO.getAsJsonArray(o);
					
					Set<CacheEntry> entries = this.restoreCacheEntries(a);
					if (!entries.isEmpty()) {
						this.restoredEntries.put(o, entries);
					}
				} catch (Exception e2) {
					continue;
				}			
			}			
		} catch (Exception e) {}
			//Do nothing
		try {
			JsonArray a = manifest.getAsJsonArray(SERIALIZED_STORAGE_CACHE_NO_ID_PROPERTY);

			Set<CacheEntry> entries = this.restoreCacheEntries(a);
			if (!entries.isEmpty()) {
				this.restoredNoStorageIdEntries.addAll(entries);
			}		
		} catch (Exception e3) {
			//Do nothing
		}
	}
	
	protected JsonArray serializeCacheEntries(Set<CacheEntry> entries) {
		JsonArray result = new JsonArray();		
		for (CacheEntry entry : entries) {
			JsonObject jEntry = new JsonObject();
	
			jEntry.addProperty(SERIALIZED_ID_PROPERTY, entry.getId());
			jEntry.addProperty(SERIALIZED_LABEL_PROPERTY, entry.getCachedLabel());
			
			JsonArray jResources = new JsonArray();
			for (String res : entry.getResources()) {
				jResources.add(res);
			}
			jEntry.add(SERIALIZED_RESOURCES_PROPERTY, jResources);
			
			result.add(jEntry);
		}		
		return result;
	}

	protected void serialize (JsonObject manifest) {		
		Map<String, CacheEntry> entriesNoId = new LinkedHashMap<>();
		
		for (CacheEntry e : this.restoredNoStorageIdEntries) {
			entriesNoId.put(e.getId(), e);
		}
		
		Map<String, Set<CacheEntry>> entryMap = new HashMap<>(this.restoredEntries);
		
		for (RefTargetResolveCache cache : this.entries.keySet()) {
			if (cache.getModelStorage() == null || cache.getModelStorage().getName() == null || cache.getModelStorage().getName().isBlank()) {
				for (CacheEntry e : this.entries.get(cache)) {
					CacheEntry existing = entriesNoId.get(e.getId());
					if (existing == null) {
						existing = new CacheEntryImpl(e.getId(), e.getResources(), e.getCachedLabel());
						entriesNoId.put(e.getId(), existing);
					} else {
						for (String res : e.getResources()) {
							((CacheEntryImpl)existing).addResource(res);
						}
						if (existing.getCachedLabel() == null || existing.getCachedLabel().isBlank()) {
							((CacheEntryImpl)existing).setCachedLabel(e.getCachedLabel());
						}
					}
				}
				continue;
			}
			String id = cache.getModelStorage().getName();			
			Set<CacheEntry> entrySet = entryMap.get(id);
			if (entrySet == null) {
				entrySet = new LinkedHashSet<>();
				entryMap.put(id, entrySet);
			}
			entrySet.addAll(this.entries.get(cache));
		}
		
		JsonArray noIdResult = this.serializeCacheEntries(new LinkedHashSet<>(entriesNoId.values()));
		JsonObject idResult = new JsonObject();
		
		
		for (String id : entryMap.keySet()) {
			JsonArray oneIdResult = this.serializeCacheEntries(entryMap.get(id));
			if (!oneIdResult.isEmpty()) {
				idResult.add(id, oneIdResult);
			}
		}
		
		if (!noIdResult.isEmpty()) {
			manifest.add(SERIALIZED_STORAGE_CACHE_NO_ID_PROPERTY, noIdResult);
		}
		if (!idResult.keySet().isEmpty()) {
			manifest.add(SERIALIZED_STORAGE_CACHE_PROPERTY, idResult);
		}
	}

	protected synchronized void persist () {
		//update cache entries from the resolve cache that may have been added to the resolve cache without this project cache being notified (i.e. no file from project was oben)
		for (RefTargetResolveCache cache : this.entries.keySet()) {
			for (CacheEntry e : cache.entries.values()) {
				if (this.isRelevantRefTarget(e.getId())) {
					this.persisted = false;
					this.entries.get(cache).add(e);
				}
			}
		}
		
		if (this.persisted) {
			return;
		}
		this.persisted = true;

		JsonObject manifest = new JsonObject();
		this.serialize(manifest);
		
		Gson gson = new GsonBuilder()
		        .setPrettyPrinting()
		        .create();
		
		try {
			if (!this.persistedFile.exists()) {
				this.persistedFile.create(new  ByteArrayInputStream(gson.toJson(manifest).getBytes(StandardCharsets.UTF_8)), true, null);
			} else {
				this.persistedFile.setContents(new  ByteArrayInputStream(gson.toJson(manifest).getBytes(StandardCharsets.UTF_8)), true, true, null);
			}
			//Files.writeString(Path.of(this.persistedFile.getFullPath().toOSString()), gson.toJson(manifest));
//		} catch (IOException e) {
//			e.printStackTrace();
		} catch (CoreException e) {
			e.printStackTrace();
		}
	}
	
	public void notifyEditorClose (IFile file) {
		this.activeResolvingFilesOperation(false, false, file);
		if (!this.activeResolvingFilesOperation(true, false, null)) {
			//no files monitored anymore
			this.persist();
		}
	}
	
	public static IFile resourceToIFile(Resource res) {
		return ResourcesPlugin.getWorkspace().getRoot().getFile(new org.eclipse.core.runtime.Path(res.getURI().toString()));
	}
	
	public static ProjectResolveCache getProjectCache (Resource res, RefTargetResolveCache cache) {
		return ProjectResolveCache.getProjectCache(ProjectResolveCache.resourceToIFile(res), cache);
	}
	
	public static IProject getProject (IFile file) {
		
		String project = file.getFullPath().toString();
		org.eclipse.core.runtime.Path pPath = new org.eclipse.core.runtime.Path(project);
		//project = "platform:" + pPath.segment(0) + "/" + pPath.segment(1);
		
		for (IProject p : ResourcesPlugin.getWorkspace().getRoot().getProjects()) {
			if (p.getName().equals(pPath.segment(1))) {
				return p;
			}
		}
		return null;
	}
	
	public static synchronized ProjectResolveCache getProjectCache (IFile file, RefTargetResolveCache cache) {
		if (file == null) {
			return null;
		}
		
		IProject project = ProjectResolveCache.getProject(file);
		
		if (project == null) {
			return null;
		}
		
		ProjectResolveCache result = projectCaches.get(project);
				
		if (result != null) {
			result.activeResolvingFilesOperation(false, true, file);
			
			result.activateRefTargetResolveCache(cache);
			return result; 
		}
		result = new ProjectResolveCache(project, cache);
		projectCaches.put(project, result);
		
		result.initialize();
		result.activateRefTargetResolveCache(cache);
		
		return result;
	}

	public void registerLabelProvider(ReferenceResolvingLabelProvider provider) {
		if (this.providers.contains(provider)) {
			return;
		}
		this.persisted = false;
		this.providers.add(provider);
		this.relatedStringLists.add(provider.requestedIds);
		this.relatedStringLists.add(provider.resolvedIds);
		
		provider.getResourceSet().eAdapters().add(new EContentAdapter() {
			@Override
			public void notifyChanged(Notification notification) {
				if (notification.getFeatureID(ResourceSet.class) == ResourceSet.RESOURCE_SET__RESOURCES && notification.getEventType() == Notification.ADD) {
					Resource newR = (Resource) notification.getNewValue();

					ProjectResolveCache projectCache = ProjectResolveCache.getProjectCache(newR, provider.getRefTargetResolveCache());
					projectCache.registerLabelProvider(provider);
				}
				super.notifyChanged(notification);
			}
		});
		
		//add already resolved entries to the project specific cache
		for(String id : provider.getResolvedIdList()) {
			CacheEntry e = provider.getCachedEntry(id, false);
			if (e != null) {
				this.entries.get(provider.getRefTargetResolveCache()).add(e);
			}
		}
	}
	public boolean isRelevantRefTarget (String id) {
		for (List<String> list : this.relatedStringLists) {
			if (list.contains(id)) {
				return true;
			}
		}
		return false;
	}
}
