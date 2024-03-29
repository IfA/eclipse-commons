/**
 */
package de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.provider;


import java.net.URL;
import java.util.Collection;
import java.util.List;

import javax.swing.ImageIcon;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EStructuralFeature;

import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.StyledString;
import org.eclipse.emf.edit.provider.ViewerNotification;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

import de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.ModelStorage;
import de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.ModelStorageFactory;
import de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.ModelStoragePackage;
import de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.UpdateableElement;
import de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.importAdapter.ImportAdapterFactory;

/**
 * This is the item provider adapter for a {@link de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.ModelStorage} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class ModelStorageItemProvider 
	extends UpdateableElementItemProvider {
	/**
	 * This constructs an instance from a factory and a notifier.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ModelStorageItemProvider(AdapterFactory adapterFactory) {
		super(adapterFactory);
	}

	/**
	 * This returns the property descriptors for the adapted class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public List<IItemPropertyDescriptor> getPropertyDescriptors(Object object) {
		if (itemPropertyDescriptors == null) {
			super.getPropertyDescriptors(object);

			addNamePropertyDescriptor(object);
		}
		return itemPropertyDescriptors;
	}

	/**
	 * This adds a property descriptor for the Name feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addNamePropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_ModelStorage_name_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_ModelStorage_name_feature", "_UI_ModelStorage_type"),
				 ModelStoragePackage.Literals.MODEL_STORAGE__NAME,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This specifies how to implement {@link #getChildren} and is used to deduce an appropriate feature for an
	 * {@link org.eclipse.emf.edit.command.AddCommand}, {@link org.eclipse.emf.edit.command.RemoveCommand} or
	 * {@link org.eclipse.emf.edit.command.MoveCommand} in {@link #createCommand}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Collection<? extends EStructuralFeature> getChildrenFeatures(Object object) {
		if (childrenFeatures == null) {
			super.getChildrenFeatures(object);
			childrenFeatures.add(ModelStoragePackage.Literals.MODEL_STORAGE__MODEL);
			childrenFeatures.add(ModelStoragePackage.Literals.MODEL_STORAGE__CONNECTOR);
		}
		return childrenFeatures;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EStructuralFeature getChildFeature(Object object, Object child) {
		// Check the type of the specified child object and return the proper feature to use for
		// adding (see {@link AddCommand}) it as a child.

		return super.getChildFeature(object, child);
	}

	/**
	 * This returns ModelStorage.gif.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT adds spinner in case of updating state
	 */
	@Override
	public Object getImage(Object object) {
		if (((UpdateableElement)object).isUpdating()) {
			return ((URL)getResourceLocator().getImage("full/obj16/spinner"));
		}
		return overlayImage(object, getResourceLocator().getImage("full/obj16/ModelStorage"));
	}

	/**
	 * This returns the label text for the adapted class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getText(Object object) {
		return ((StyledString)getStyledText(object)).getString();
	}

	/**
	 * This returns the label styled text for the adapted class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT adds pending state
	 */
	@Override
	public Object getStyledText(Object object) {
		String label = ((ModelStorage)object).getName();
    	StyledString styledLabel = new StyledString();
    	if (((UpdateableElement)object).isUpdating()) {
    		styledLabel.append("[pending...] ", StyledString.Style.COUNTER_STYLER);
    	}
    	
		if (label == null || label.length() == 0) {
			styledLabel.append(getString("_UI_ModelStorage_type"), StyledString.Style.QUALIFIER_STYLER); 
		} else {
			styledLabel.append(getString("_UI_ModelStorage_type"), StyledString.Style.QUALIFIER_STYLER).append(" " + label);
		}
		
		return styledLabel;
	}

	/**
	 * This handles model notifications by calling {@link #updateChildren} to update any cached
	 * children and by creating a viewer notification, which it passes to {@link #fireNotifyChanged}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void notifyChanged(Notification notification) {
		updateChildren(notification);

		switch (notification.getFeatureID(ModelStorage.class)) {
			case ModelStoragePackage.MODEL_STORAGE__NAME:
				fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), false, true));
				return;
			case ModelStoragePackage.MODEL_STORAGE__MODEL:
			case ModelStoragePackage.MODEL_STORAGE__CONNECTOR:
				fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), true, false));
				return;
		}
		super.notifyChanged(notification);
	}

	/**
	 * This adds {@link org.eclipse.emf.edit.command.CommandParameter}s describing the children
	 * that can be created under this object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected void collectNewChildDescriptors(Collection<Object> newChildDescriptors, Object object) {
		super.collectNewChildDescriptors(newChildDescriptors, object);

		newChildDescriptors.add
			(createChildParameter
				(ModelStoragePackage.Literals.MODEL_STORAGE__MODEL,
				 ModelStorageFactory.eINSTANCE.createModel()));

		newChildDescriptors.add
			(createChildParameter
				(ModelStoragePackage.Literals.MODEL_STORAGE__MODEL,
				 ModelStorageFactory.eINSTANCE.createLinkedModel()));

		newChildDescriptors.add
			(createChildParameter
				(ModelStoragePackage.Literals.MODEL_STORAGE__CONNECTOR,
				 ImportAdapterFactory.eINSTANCE.createFileSystemConnector()));
	}

}
