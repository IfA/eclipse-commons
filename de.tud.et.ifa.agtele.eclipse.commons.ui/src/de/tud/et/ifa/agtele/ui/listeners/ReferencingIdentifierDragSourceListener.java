package de.tud.et.ifa.agtele.ui.listeners;

import java.util.ArrayList;
import java.util.Iterator;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.swt.dnd.DragSourceEvent;
import de.tud.et.ifa.agtele.emf.edit.IReferencingIdentificationStringProvider;
import de.tud.et.ifa.agtele.ui.util.ReferencingIdentifierTransfer;

public class ReferencingIdentifierDragSourceListener extends CommonDragSourceListener {
	protected StructuredViewer viewer;
	protected AdapterFactory adapterFactory;

	public ReferencingIdentifierDragSourceListener(StructuredViewer viewer2, AdapterFactory adapterFactory) {
		super(viewer2);
		this.viewer = viewer2;
		this.adapterFactory = adapterFactory;
	}
		
	protected ArrayList<String> refs = null;

	protected boolean identifiersValid = false;
	
	@Override
	public void dragStart(DragSourceEvent event) {
		IStructuredSelection selection = this.viewer.getStructuredSelection();
		refs = new ArrayList<>();
		identifiersValid = true;
			
		for (@SuppressWarnings("rawtypes")
				Iterator i = selection.iterator(); i.hasNext();) {
			Object selected = i.next();
			if (selected instanceof EObject) {	
				if (selected instanceof ENamedElement) {
					String id = ReferencingIdentifierDragSourceListener.getEcoreIdentifier((ENamedElement)selected);
					if (id != null) {
						refs.add(id);
					} else {
						identifiersValid = false;
						break;
					}
				} else if (this.adapterFactory != null) {			
					Adapter adapter = this.adapterFactory.adapt((EObject)selected, IEditingDomainItemProvider.class);
					if (adapter instanceof IReferencingIdentificationStringProvider) {
						String ref = ((IReferencingIdentificationStringProvider)adapter).getReferencingIdentificationString(selected);
						if (ref == null || ref.isEmpty()) {
						} else {
							refs.add(ref);
						}
						
					} else {
						identifiersValid = false;
						break;			
					}
				}
			} else {
				identifiersValid = false;
				break;
			}
		}
		super.dragStart(event);	
	}

	@Override
	public void dragSetData(DragSourceEvent event) {
		if (ReferencingIdentifierTransfer.getInstance().isSupportedType(event.dataType)) {
			if (identifiersValid) {
				event.data = this.refs;
			}
			return;
		}		
		super.dragSetData(event);
	}

	@Override
	public void dragFinished(DragSourceEvent event) {
		if (identifiersValid && ReferencingIdentifierTransfer.getInstance().isSupportedType(event.dataType)) {
			return;
		}
		super.dragFinished(event);
	}
		
	public static String getEcoreIdentifier(ENamedElement element) {
		String prefix = "uri";
		if (element instanceof EPackage) {
			return prefix + ":" + ((EPackage)element).getNsURI();
		} else if (element.eContainer() instanceof EPackage) {
			return ReferencingIdentifierDragSourceListener.getEcoreIdentifier((EPackage)element.eContainer()) + "#" + element.getName();
		} else if (element instanceof EStructuralFeature) {
			return ReferencingIdentifierDragSourceListener.getEcoreIdentifier(((EStructuralFeature)element).getEContainingClass()) + "/" + element.getName();
		}
		return null;
	}
	
}