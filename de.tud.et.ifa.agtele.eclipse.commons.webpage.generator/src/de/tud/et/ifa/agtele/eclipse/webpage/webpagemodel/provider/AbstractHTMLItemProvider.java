/**
 */
package de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.provider;


import de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.AbstractHTML;
import de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.WebPageModelFactory;
import de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.WebPageModelPackage;
import de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.util.WebPageModelUtils;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.StyledString;
import org.eclipse.emf.edit.provider.ViewerNotification;

/**
 * This is the item provider adapter for a {@link de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.AbstractHTML} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class AbstractHTMLItemProvider extends BaseItemProvider {
	/**
	 * This constructs an instance from a factory and a notifier.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AbstractHTMLItemProvider(AdapterFactory adapterFactory) {
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

			addSrcPathFragmentPropertyDescriptor(object);
			addTitlePropertyDescriptor(object);
			addStaticResourcesPropertyDescriptor(object);
			addExternalUrlPropertyDescriptor(object);
			addNavNamePropertyDescriptor(object);
		}
		return itemPropertyDescriptors;
	}

	/**
	 * This adds a property descriptor for the Src Path Fragment feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addSrcPathFragmentPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_AbstractHTML_srcPathFragment_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_AbstractHTML_srcPathFragment_feature", "_UI_AbstractHTML_type"),
				 WebPageModelPackage.Literals.ABSTRACT_HTML__SRC_PATH_FRAGMENT,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Title feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addTitlePropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_AbstractHTML_title_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_AbstractHTML_title_feature", "_UI_AbstractHTML_type"),
				 WebPageModelPackage.Literals.ABSTRACT_HTML__TITLE,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Static Resources feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addStaticResourcesPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_AbstractHTML_staticResources_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_AbstractHTML_staticResources_feature", "_UI_AbstractHTML_type"),
				 WebPageModelPackage.Literals.ABSTRACT_HTML__STATIC_RESOURCES,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the External Url feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addExternalUrlPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_AbstractHTML_externalUrl_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_AbstractHTML_externalUrl_feature", "_UI_AbstractHTML_type"),
				 WebPageModelPackage.Literals.ABSTRACT_HTML__EXTERNAL_URL,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Nav Name feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addNavNamePropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_AbstractHTML_navName_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_AbstractHTML_navName_feature", "_UI_AbstractHTML_type"),
				 WebPageModelPackage.Literals.ABSTRACT_HTML__NAV_NAME,
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
			childrenFeatures.add(WebPageModelPackage.Literals.ABSTRACT_HTML__FOOTER);
			childrenFeatures.add(WebPageModelPackage.Literals.ABSTRACT_HTML__HEADER);
			childrenFeatures.add(WebPageModelPackage.Literals.ABSTRACT_HTML__SCRIPTS);
			childrenFeatures.add(WebPageModelPackage.Literals.ABSTRACT_HTML__STYLES);
			childrenFeatures.add(WebPageModelPackage.Literals.ABSTRACT_HTML__CONTENT);
			childrenFeatures.add(WebPageModelPackage.Literals.ABSTRACT_HTML__ANNOUNCEMENT);
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
	 * @generated
	 */
	@Override
	public Object getStyledText(Object object) {
		String label = ((AbstractHTML)object).getName();
    	StyledString styledLabel = new StyledString();
		if (label == null || label.length() == 0) {
			styledLabel.append(getString("_UI_AbstractHTML_type"), StyledString.Style.QUALIFIER_STYLER); 
		} else {
			styledLabel.append(getString("_UI_AbstractHTML_type"), StyledString.Style.QUALIFIER_STYLER).append(" " + label);
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

		switch (notification.getFeatureID(AbstractHTML.class)) {
			case WebPageModelPackage.ABSTRACT_HTML__SRC_PATH_FRAGMENT:
			case WebPageModelPackage.ABSTRACT_HTML__TITLE:
			case WebPageModelPackage.ABSTRACT_HTML__STATIC_RESOURCES:
			case WebPageModelPackage.ABSTRACT_HTML__EXTERNAL_URL:
			case WebPageModelPackage.ABSTRACT_HTML__NAV_NAME:
				fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), false, true));
				return;
			case WebPageModelPackage.ABSTRACT_HTML__FOOTER:
			case WebPageModelPackage.ABSTRACT_HTML__HEADER:
			case WebPageModelPackage.ABSTRACT_HTML__SCRIPTS:
			case WebPageModelPackage.ABSTRACT_HTML__STYLES:
			case WebPageModelPackage.ABSTRACT_HTML__CONTENT:
			case WebPageModelPackage.ABSTRACT_HTML__ANNOUNCEMENT:
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
				(WebPageModelPackage.Literals.ABSTRACT_HTML__FOOTER,
				 WebPageModelFactory.eINSTANCE.createFileValue()));

		newChildDescriptors.add
			(createChildParameter
				(WebPageModelPackage.Literals.ABSTRACT_HTML__FOOTER,
				 WebPageModelFactory.eINSTANCE.createStringValue()));

		newChildDescriptors.add
			(createChildParameter
				(WebPageModelPackage.Literals.ABSTRACT_HTML__HEADER,
				 WebPageModelFactory.eINSTANCE.createFileValue()));

		newChildDescriptors.add
			(createChildParameter
				(WebPageModelPackage.Literals.ABSTRACT_HTML__HEADER,
				 WebPageModelFactory.eINSTANCE.createStringValue()));

		newChildDescriptors.add
			(createChildParameter
				(WebPageModelPackage.Literals.ABSTRACT_HTML__SCRIPTS,
				 WebPageModelFactory.eINSTANCE.createHtmlInclude()));

		newChildDescriptors.add
			(createChildParameter
				(WebPageModelPackage.Literals.ABSTRACT_HTML__STYLES,
				 WebPageModelFactory.eINSTANCE.createHtmlInclude()));

		newChildDescriptors.add
			(createChildParameter
				(WebPageModelPackage.Literals.ABSTRACT_HTML__CONTENT,
				 WebPageModelFactory.eINSTANCE.createFileValue()));

		newChildDescriptors.add
			(createChildParameter
				(WebPageModelPackage.Literals.ABSTRACT_HTML__CONTENT,
				 WebPageModelFactory.eINSTANCE.createStringValue()));

		newChildDescriptors.add
			(createChildParameter
				(WebPageModelPackage.Literals.ABSTRACT_HTML__ANNOUNCEMENT,
				 WebPageModelFactory.eINSTANCE.createAnnouncement()));
	}

	/**
	 * This returns the label text for {@link org.eclipse.emf.edit.command.CreateChildCommand}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getCreateChildText(Object owner, Object feature, Object child, Collection<?> selection) {
		Object childFeature = feature;
		Object childObject = child;

		boolean qualify =
			childFeature == WebPageModelPackage.Literals.ABSTRACT_HTML__FOOTER ||
			childFeature == WebPageModelPackage.Literals.ABSTRACT_HTML__HEADER ||
			childFeature == WebPageModelPackage.Literals.ABSTRACT_HTML__CONTENT ||
			childFeature == WebPageModelPackage.Literals.ABSTRACT_HTML__SCRIPTS ||
			childFeature == WebPageModelPackage.Literals.ABSTRACT_HTML__STYLES;

		if (qualify) {
			return getString
				("_UI_CreateChild_text2",
				 new Object[] { getTypeText(childObject), getFeatureText(childFeature), getTypeText(owner) });
		}
		return super.getCreateChildText(owner, feature, child, selection);
	}
	
	@Override
	protected Command createSetCommand(EditingDomain domain, EObject owner, EStructuralFeature feature, Object value) {
		if (feature == WebPageModelPackage.Literals.BASE__NAME) {
			AbstractHTML html = (AbstractHTML) owner;
			String oldValue = html.getName(), newValue = (String) value, oldFSName = WebPageModelUtils.getUrlSafeName(oldValue),
					title = html.getTitle();
			if (newValue != null && !newValue.isBlank()) {
				CompoundCommand cmd = new CompoundCommand();
				cmd.append(super.createSetCommand(domain, owner, feature, value));
				if (title == null || title.equals(oldValue)) {
					cmd.append(SetCommand.create(domain, owner, WebPageModelPackage.Literals.ABSTRACT_HTML__TITLE, newValue));					
				}				
				return cmd;
			}
		}
		return super.createSetCommand(domain, owner, feature, value);
	}

}
