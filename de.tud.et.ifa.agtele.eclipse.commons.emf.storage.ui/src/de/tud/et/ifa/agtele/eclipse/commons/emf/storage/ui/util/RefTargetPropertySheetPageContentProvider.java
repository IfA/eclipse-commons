package de.tud.et.ifa.agtele.eclipse.commons.emf.storage.ui.util;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.ui.provider.ExtendedImageRegistry;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;

import de.tud.et.ifa.agtele.ui.providers.AgtelePropertySource;
import de.tud.et.ifa.agtele.ui.providers.StateRestoringViewerContentProvider;

public class RefTargetPropertySheetPageContentProvider extends StateRestoringViewerContentProvider {	
	
	public RefTargetPropertySheetPageContentProvider(AdapterFactory adapterFactory, StructuredViewer structuredViewer) {
		super(adapterFactory, structuredViewer);
	}
	
	ReferenceResolvingLabelProvider labelProvider = null;
	public void setReferenceResolvingLabelProvider (ReferenceResolvingLabelProvider labelProvider) {
		this.labelProvider = labelProvider;
	}
	
	@Override
	protected IPropertySource createPropertySource(Object object, IItemPropertySource itemPropertySource) {
		return new AgtelePropertySource(object, itemPropertySource) {			
			@Override
			protected IPropertyDescriptor createPropertyDescriptor(IItemPropertyDescriptor itemPropertyDescriptor) {
				return new AgtelePropertyDescriptor(this.object, itemPropertyDescriptor) {
					@Override
					public ILabelProvider getLabelProvider() {
						if (RefTargetPropertySheetPageContentProvider.this.labelProvider != null) {
							final PropertySheetPageGenericReferenceResolvingLabelProvider labelProvider = 
									new PropertySheetPageGenericReferenceResolvingLabelProvider (RefTargetPropertySheetPageContentProvider.this.labelProvider, itemPropertyDescriptor);
							//For the property sheet page cell editors, no CellLabelProvider can be used since it allows one viewer only.
							return 
					    	      new LabelProvider() {
						    	        @Override
						    	        public String getText(Object object)
						    	        {
						    	          return labelProvider.getText(object);
						    	        }
						    	        @Override
						    	        public Image getImage(Object object)
						    	        {
						    	          return ExtendedImageRegistry.getInstance().getImage(labelProvider.getImage(object));
						    	        }
				    	        };
						}
						
						final IItemLabelProvider itemLabelProvider = itemPropertyDescriptor.getLabelProvider(object);
				    	return 
				    	      new LabelProvider()
				    	      {
				    	        @Override
				    	        public String getText(Object object)
				    	        {
				    	          return itemLabelProvider.getText(object);
				    	        }
				    	        @Override
				    	        public Image getImage(Object object)
				    	        {
				    	          return ExtendedImageRegistry.getInstance().getImage(itemLabelProvider.getImage(object));
				    	        }
		    	        };
					}
				};
			}
		};
	}
}
