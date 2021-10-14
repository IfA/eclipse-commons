package de.tud.et.ifa.agtele.ui.emf.edit.action.filter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.ItemProvider;
import org.eclipse.emf.edit.provider.ItemProviderAdapter;
import org.eclipse.jface.dialogs.IDialogSettings;

import de.tud.et.ifa.agtele.emf.AgteleEcoreUtil;
import de.tud.et.ifa.agtele.ui.AgteleUIPlugin;

public class ItemProviderCreateActionFilter extends CompoundCreateActionFilter
		implements IItemProviderCreateActionFilter {

	protected Map<EClass, List<ICreateActionFilter>> filterCache = new LinkedHashMap<>(); //This map may need to be made thread safe.
	
	protected List<List<ICreateActionFilter>> restoredFilters = new ArrayList<>();
	
	protected static IDialogSettings settings = null;
	
	protected static AgteleUIPlugin.Implementation getPlugin() {
		return AgteleUIPlugin.getPlugin();
	}
	
	protected static synchronized IDialogSettings getStaticSettings() {		
		if (ItemProviderCreateActionFilter.settings != null) {
			IDialogSettings settings = ItemProviderCreateActionFilter.getPlugin().getDialogSettings().addNewSection("ITEM_PROVIDER_CREATE_ACTION_FILTER");
			ItemProviderCreateActionFilter.settings = settings;
		}
		return ItemProviderCreateActionFilter.settings;
	}
	
	protected IDialogSettings getSettings() {
		return ItemProviderCreateActionFilter.getStaticSettings();
	}
	
	protected boolean areFiltersDifferent(List<ICreateActionFilter> filters) {
		return !this.subFilters.equals(filters); //TODO this may not work, if list classes differ
	}
	
	protected void activateFilters (List<ICreateActionFilter> filters, EClass cacheKey) {
		if (filters == null || filters.isEmpty()) {
			if (!this.getSubFilters().isEmpty()) {
				this.removeSubFilters(this.getSubFilters());
			}
		} else if (this.areFiltersDifferent(filters)) {
			FilterChangedNotification notification = new FilterChangedNotification();
			this.removeSubFilters(this.getSubFilters(), notification);
			this.addSubFilters(filters, notification);
			if (cacheKey != null && !this.restoredFilters.contains(filters)) {
				this.restoreFilters(cacheKey, filters);			
			}
			if (cacheKey != null && !this.filterCache.containsValue(filters)) {
				this.filterCache.put(cacheKey, filters);
			}
			this.dispatchNotification(notification);
		}		
	}
	
	@Override
	public List<ICreateActionFilter> getFilters(EObject selection) {
		if (selection == null) {
			return Collections.emptyList();
		}
		
		Adapter adapter = AgteleEcoreUtil.getAdapter(selection, IEditingDomainItemProvider.class);
		if (adapter == null || !(adapter instanceof ItemProviderAdapter)) {
			adapter = AgteleEcoreUtil.getAdapter(selection, ItemProviderAdapter.class);
		}
		
		ItemProviderAdapter provider = (ItemProviderAdapter) adapter;
	
		List<ICreateActionFilter> result = Collections.emptyList();
		if (provider instanceof CreateActionFilterItemProvider) {
			CreateActionFilterItemProvider cafProvider = (CreateActionFilterItemProvider)provider;
			EClass cacheKey = cafProvider.getCacheKey(selection);
			if (cafProvider.isFilterReuseAllowed(selection) && cacheKey != null && this.filterCache.containsKey(cacheKey) && this.filterCache.get(selection.eClass()) != null) {
				result = this.filterCache.get(selection.eClass());
				this.activateFilters(result, cacheKey);
			} else {
				result = cafProvider.getFilter(selection, this.filterCache);
				this.activateFilters(result, cacheKey);
			}
		} else {
			this.activateFilters(null, null);
		}
		return result != null ? result : Collections.emptyList();
	}
	
	protected void restoreFilters(EClass cls, List<ICreateActionFilter> filters) {
		if (cls != null) {
			String classKey = AgteleEcoreUtil.getEcoreElementUri(cls);
			IDialogSettings settings = this.getSettings() != null ? this.getSettings().getSection(classKey) : null;
			if (settings != null) {
				for (ICreateActionFilter filter : filters) {
					if (filter.getContext() != null && !filter.getContext().isBlank() ) {
						IDialogSettings filterSettings = settings.getSection(filter.getContext());
						if (filterSettings != null) {
							filter.restore(filterSettings);							
						}
					}
				}
			}
			//TODO maybe also store the state independently from the eClass in order to initialize filters for new eClass contexts
		}
	}
	
	protected void persistFilters(EClass cls, List<ICreateActionFilter> filters) {
		if (cls != null) {
			String classKey = AgteleEcoreUtil.getEcoreElementUri(cls);
			IDialogSettings settings = this.getSettings() != null ? this.getSettings().addNewSection(classKey) : null;
			if (settings != null) {
				for (ICreateActionFilter filter : filters) {
					if (filter.getContext() != null && !filter.getContext().isBlank() ) {
						IDialogSettings filterSettings = settings.addNewSection(filter.getContext());
						filter.persist(filterSettings);
					}
				}
			}
			//TODO maybe also store the state independently from the eClass in order to initialize filters for new eClass contexts
		}
	}
	
	@Override
	public void persist(IDialogSettings settings) {
		this.filterCache.entrySet().forEach(e -> this.persistFilters(e.getKey(), e.getValue()));
		ItemProviderCreateActionFilter.getPlugin().saveDialogSettings();
	}
	
	@Override
	public void restore(IDialogSettings settings) {
		//DO nothing, this happens on the fly, when cached filters are being created
	}
}
