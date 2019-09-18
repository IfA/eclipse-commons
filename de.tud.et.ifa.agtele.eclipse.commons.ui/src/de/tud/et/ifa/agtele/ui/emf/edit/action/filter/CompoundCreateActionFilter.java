package de.tud.et.ifa.agtele.ui.emf.edit.action.filter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.IDialogSettings;

public class CompoundCreateActionFilter extends CreateActionFilter implements ICompoundCreateActionFilter {

	protected List<ICreateActionFilter> subFilters = new ArrayList<>();
	
	protected CompoundFilterType type = CompoundFilterType.SIMPLE_GROUP;
	
	public CompoundCreateActionFilter() {}
	
	public CompoundCreateActionFilter(CompoundFilterType type) {
		this.type = type;
	}	
	
	@Override
	public boolean isActive() {
		return this.subFilters.stream().anyMatch(f -> f.isActive());
	}
	
	@Override
	public void persist(IDialogSettings settings) {
		this.doPersist(settings);
		this.subFilters.forEach(f -> {
			if (f.getContext() != null && !f.getContext().isBlank()) {
				IDialogSettings subSettings = settings.addNewSection(f.getContext());
				f.persist(subSettings);
			}
		});
	}

	@Override
	public void restore(IDialogSettings settings) {
		this.doRestore(settings);
		this.subFilters.forEach(f -> {
			if (f.getContext() != null && !f.getContext().isBlank()) {
				IDialogSettings subSettings = settings.getSection(f.getContext());
				if (subSettings != null) {
					f.restore(subSettings);					
				}
			}
		});
	}

	@Override
	public List<ICreateActionFilter> getSubFilters() {
		return new ArrayList<>(this.subFilters);
	}
	
	protected boolean isRadioGroup () {
		return this.type == CompoundFilterType.RADIO_GROUP || this.type == CompoundFilterType.RADIO_GROUP_ALWAYS_ON;
	}

	@Override
	public void addSubFilter(ICreateActionFilter filter) {	
		FilterChangedNotification myNotification = new FilterChangedNotification();
		if (!this.subFilters.contains(filter)) {
			if (filter.isActive()) {
				if (this.isActive()) {
					this.subFilters.add(filter);
					if (this.isRadioGroup()) {
						this.subFilters.forEach(f -> {
							if (f != filter) {
								f.deactivate(myNotification);
							}
						});
					}
				} else {
					this.subFilters.add(filter);
				}
				myNotification.addChangedFilter(filter);
			} else {
				this.subFilters.add(filter);				
			}
		}
		if (this.type == CompoundFilterType.RADIO_GROUP_ALWAYS_ON && !this.isActive()) {
			this.activate(myNotification);
		}
		this.dispatchNotification(myNotification);
	}

	@Override
	public void removeSubFilter(ICreateActionFilter filter) {
		if (!this.subFilters.contains(filter)) {
			FilterChangedNotification myNotification = new FilterChangedNotification();			
			if (!filter.isActive()) {
				this.subFilters.remove(filter);
			} else {
				myNotification.addChangedFilter(filter);
				if (this.type == CompoundFilterType.RADIO_GROUP_ALWAYS_ON) {
					this.subFilters.remove(filter);
					this.activate(myNotification);
				}
			}
			this.dispatchNotification(myNotification);
		}
				
	}
	
	@Override
	public void setActive(boolean active, FilterChangedNotification notification) {
		FilterChangedNotification myNotification = notification != null ? notification : new FilterChangedNotification();
		if (this.isActive() && !active) {
			if (this.type == CompoundFilterType.RADIO_GROUP_ALWAYS_ON) {
				return;
			}
			this.subFilters.forEach(f -> f.setActive(active, myNotification));
		} else if (!this.isActive() && active) {
			for (ICreateActionFilter filter : this.subFilters) {
				filter.activate(myNotification);
				if (filter.isActive()) {
					break;
				}
			}
		}
		if (notification == null) {
			this.dispatchNotification(myNotification);
		}
	}

	@Override
	public boolean isChangeAllowed(ICreateActionFilter filter) {
		if (!filter.isActive() || !this.isRadioGroup()) {
			return true;
		}
		return this.isChangeAllowed();
	}
	
	protected List<ICreateActionFilter> getActiveFilters () {
		return this.subFilters.stream().filter(f -> f.isActive()).collect(Collectors.toList());
	}

	@Override
	public List<IAction> filterActions(List<? extends IAction> currentFilterState, List<? extends IAction> originalActions) {
		List<IAction> result = new ArrayList<>(currentFilterState);
		
		for (ICreateActionFilter filter : this.getActiveFilters()) {
			result = filter.filterActions(this.getCreateChildActions(result), originalActions);
		}		
		return result;
	}

	@Override
	public void notifySubFilterChange(ICreateActionFilter filter, FilterChangedNotification notification) {
		if (filter.isActive() && this.isActive() && this.isRadioGroup()) {
			for (ICreateActionFilter subFilter : this.subFilters) {
				if (subFilter != filter) {
					subFilter.deactivate(notification);
				}
			}
		}		
	}

}
