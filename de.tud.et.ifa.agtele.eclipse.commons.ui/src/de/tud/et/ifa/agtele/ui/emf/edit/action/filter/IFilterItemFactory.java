package de.tud.et.ifa.agtele.ui.emf.edit.action.filter;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Item;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

import de.tud.et.ifa.agtele.ui.listeners.SelectionListener2;

public interface IFilterItemFactory {
	public default List<Item> createControls(ICreateActionFilter filter, Composite parent) {
		List<Item> result = new ArrayList<>();
		this.doCreateControls(filter, result, parent);
		return result;
	}
	
	public default void doCreateControls(ICreateActionFilter filter, List<Item> result, Composite parent) {
		if (filter instanceof ICompoundCreateActionFilter) {
			for (ICreateActionFilter subFilter : ((ICompoundCreateActionFilter)filter).getSubFilters()) {
				this.doCreateControls(subFilter, result, parent);
			}
		} else {
			Item item = this.createControl(filter, parent);
			if (item != null) {
				result.add(item);
			}
		}
	}
	
	public default ToolItem createSimpleToolItem(ICreateActionFilter filter, Composite parent) {
		ToolItem result = new ToolItem((ToolBar) parent, SWT.CHECK);
		
		result.setSelection(filter.getFilterState() > 0);
		result.addSelectionListener(new SelectionListener2 () {
			@Override
			public void widgetSelected(SelectionEvent e) {
				filter.setFilterState(result.getSelection() ? 1 : 0);
			}			
		});
		
		return result;
	}
	
	public Item createControl(ICreateActionFilter filter, Composite parent);
}
