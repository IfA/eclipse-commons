package de.tud.et.ifa.agtele.ui.util;

import java.util.Collection;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.ui.dnd.EditingDomainViewerDropAdapter;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.dnd.DropTargetEvent;

import de.tud.et.ifa.agtele.ui.util.ReferencingIdentifierTransfer.ReferencingIdentifierList;

public class CommonEditingDomainViewerDropAdapter extends EditingDomainViewerDropAdapter {

	public CommonEditingDomainViewerDropAdapter(EditingDomain domain, Viewer viewer) {
		super(domain, viewer);
	}

	@Override
	protected Collection<?> getDragSource(DropTargetEvent event) {
		ReferencingIdentifierTransfer idt = ReferencingIdentifierTransfer.getInstance();
		if(idt.isSupportedType(event.currentDataType)){
			Object object = idt.nativeToJava(event.currentDataType);
			return object == null ? null : extractDragSource(object);
		} 
		return super.getDragSource(event);
	}
	
	@Override
	protected Collection<?> extractDragSource(Object object) {
		if(object instanceof Collection && ((Collection)object).size() > 0) {
			Object first = ((Collection)object).iterator().next();
			if (first instanceof ReferencingIdentifierList) {
				return (Collection) object;
			}
		} 
		return super.extractDragSource(object);
	}

}
