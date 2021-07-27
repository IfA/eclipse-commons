package de.tud.et.ifa.agtele.eclipse.commons.emf.storage.ui.views;

import java.util.Collection;
import java.util.Collections;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.ui.ViewerPane;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DragSourceListener;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.ui.IActionBars;

import de.tud.et.ifa.agtele.ui.interfaces.IPersistable;
import de.tud.et.ifa.agtele.ui.listeners.ReferencingIdentifierDragSourceListener;
import de.tud.et.ifa.agtele.ui.util.ReferencingIdentifierTransfer;

public interface ModelStorageViewPage extends IPersistable {

	ViewerPane getViewerPane(ModelStorageView view);

	String getPageText();

	void configure();

	default Transfer[] getTransferTypes () {
		return getDefaultTransferTypes();
	}
	
	static Transfer[] getDefaultTransferTypes () {
		return new Transfer[]{ReferencingIdentifierTransfer.getInstance()};		
	}
	
	default DragSourceListener getDragSourceListener (TreeViewer viewer) {
		return getDefaultDragSourceListener(viewer, this.getAdapterFactory());
	}
	
	AdapterFactory getAdapterFactory ();
	
	static DragSourceListener getDefaultDragSourceListener (TreeViewer viewer, AdapterFactory adapterFactory) {
		return new ReferencingIdentifierDragSourceListener((TreeViewer)viewer, adapterFactory);
	}
	
	default int getDNDOperations () {
		return getDefaultDNDOperations();
	}
	static int getDefaultDNDOperations () {
		return DND.DROP_LINK;
	}
	
	default void contributeToActionBars(IActionBars bars) {}
	
	default void removeActionBarContributions(IActionBars bars) {}
	
	public void requestActivation();

	void dispose();
}
