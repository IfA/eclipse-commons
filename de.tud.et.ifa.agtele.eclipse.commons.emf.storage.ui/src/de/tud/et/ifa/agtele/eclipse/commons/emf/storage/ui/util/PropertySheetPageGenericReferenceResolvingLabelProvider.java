package de.tud.et.ifa.agtele.eclipse.commons.emf.storage.ui.util;

import java.util.List;

import org.eclipse.emf.common.EMFPlugin;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EContentAdapter;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.ui.provider.ExtendedImageRegistry;
import org.eclipse.swt.graphics.Image;

import de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.ModelStorage;

public class PropertySheetPageGenericReferenceResolvingLabelProvider extends GenericReferenceResolvingLabelProvider {
	IStyledLabelProvider originalLabelProvider = null;
	IItemPropertyDescriptor propertyDescriptor = null;
	
	public PropertySheetPageGenericReferenceResolvingLabelProvider(
			ReferenceResolvingLabelProvider refResolvingLabelProvider, IItemPropertyDescriptor propertyDescriptor) {
		super(refResolvingLabelProvider.getStyledLabelProvider(), refResolvingLabelProvider.getEmfPlugin(), refResolvingLabelProvider.getModelStorage(), refResolvingLabelProvider.getRefTargetResolveCache());
		
		this.propertyDescriptor = propertyDescriptor;		
	}
		
	@Override
	public boolean isEnabledQualifiedLabels(Object element) {
		return false;
	}

	@Override
	public String getText(Object element) {
		if (this.propertyDescriptor == null) {
			return super.getText(element);
		}
		if (!(this.propertyDescriptor.getFeature(element) instanceof EReference)) { 
			return this.propertyDescriptor.getLabelProvider(element).getText(element);
		}
		
		if (element instanceof EList<?>)
	    {
	      StringBuilder result = new StringBuilder();
	      for (Object child : ((List<?>)element))
	      {
	        if (result.length() != 0)
	        {
	          result.append(", ");
	        }
	        result.append(getText(child));
	      }
	      return result.toString();
	    }
		return this.getStyledText(element).getString();
	}
	
	@Override
	public Image getImage(Object element) {
        return ExtendedImageRegistry.getInstance().getImage(this.propertyDescriptor.getLabelProvider(element).getImage(element));
	}
	
	
	@Override
	protected RefreshRunner getRefreshRunner() {
		return null;
	}
	@Override
	public EContentAdapter getReferenceTargetUpdateNotifier(Object element) {
		return null;
	}
	@Override
	public List<EObject> getRefTargetUpdateNotificationReceivers(EObject element, EStructuralFeature feature) {
		return null;
	}
	@Override
	public EContentAdapter getTargetNameUpdateNotifier() {
		return null;
	}
}
