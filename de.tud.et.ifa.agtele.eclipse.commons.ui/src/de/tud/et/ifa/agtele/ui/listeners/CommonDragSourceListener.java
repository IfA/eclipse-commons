package de.tud.et.ifa.agtele.ui.listeners;

import org.eclipse.emf.edit.ui.dnd.ViewerDragAdapter;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.dnd.DragSourceEvent;

public class CommonDragSourceListener extends ViewerDragAdapter {

	public CommonDragSourceListener(Viewer viewer) {
		super(viewer);
	}	

	@Override
	public void dragStart(DragSourceEvent event) {
		super.dragStart(event);
	}
	@Override
	public void dragSetData(DragSourceEvent event) {
		super.dragSetData(event);
	}
	
	@Override
	public void dragFinished(DragSourceEvent event) {
		super.dragFinished(event);
	}
}
