/**
 */
package de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.provider;


import de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.WebPage;
import de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.WebPageModelPackage;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ViewerNotification;

/**
 * This is the item provider adapter for a {@link de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.WebPage} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class WebPageItemProvider extends MainPageItemProvider {
	/**
	 * This constructs an instance from a factory and a notifier.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public WebPageItemProvider(AdapterFactory adapterFactory) {
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

			addBaseUrlPropertyDescriptor(object);
			addOutDirPropertyDescriptor(object);
			addLangPropertyDescriptor(object);
			addResourcesOutPathFragmentPropertyDescriptor(object);
			addAlternativesPropertyDescriptor(object);
			addInvAlternativesPropertyDescriptor(object);
		}
		return itemPropertyDescriptors;
	}

	/**
	 * This adds a property descriptor for the Base Url feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addBaseUrlPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_WebPage_baseUrl_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_WebPage_baseUrl_feature", "_UI_WebPage_type"),
				 WebPageModelPackage.Literals.WEB_PAGE__BASE_URL,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Out Dir feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addOutDirPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_WebPage_outDir_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_WebPage_outDir_feature", "_UI_WebPage_type"),
				 WebPageModelPackage.Literals.WEB_PAGE__OUT_DIR,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Lang feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addLangPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_WebPage_lang_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_WebPage_lang_feature", "_UI_WebPage_type"),
				 WebPageModelPackage.Literals.WEB_PAGE__LANG,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Resources Out Path Fragment feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addResourcesOutPathFragmentPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_WebPage_resourcesOutPathFragment_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_WebPage_resourcesOutPathFragment_feature", "_UI_WebPage_type"),
				 WebPageModelPackage.Literals.WEB_PAGE__RESOURCES_OUT_PATH_FRAGMENT,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Alternatives feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addAlternativesPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_WebPage_alternatives_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_WebPage_alternatives_feature", "_UI_WebPage_type"),
				 WebPageModelPackage.Literals.WEB_PAGE__ALTERNATIVES,
				 true,
				 false,
				 true,
				 null,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Inv Alternatives feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addInvAlternativesPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_WebPage_invAlternatives_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_WebPage_invAlternatives_feature", "_UI_WebPage_type"),
				 WebPageModelPackage.Literals.WEB_PAGE__INV_ALTERNATIVES,
				 false,
				 false,
				 true,
				 null,
				 null,
				 null));
	}

	/**
	 * This returns WebPage.gif.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object getImage(Object object) {
		return overlayImage(object, getResourceLocator().getImage("full/obj16/WebPage"));
	}

	/**
	 * This returns the label text for the adapted class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getText(Object object) {
		String label = ((WebPage)object).getName();
		return label == null || label.length() == 0 ?
			getString("_UI_WebPage_type") :
			getString("_UI_WebPage_type") + " " + label;
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

		switch (notification.getFeatureID(WebPage.class)) {
			case WebPageModelPackage.WEB_PAGE__BASE_URL:
			case WebPageModelPackage.WEB_PAGE__OUT_DIR:
			case WebPageModelPackage.WEB_PAGE__LANG:
			case WebPageModelPackage.WEB_PAGE__RESOURCES_OUT_PATH_FRAGMENT:
				fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), false, true));
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
			childFeature == WebPageModelPackage.Literals.PAGE__NAV_ICON ||
			childFeature == WebPageModelPackage.Literals.ABSTRACT_HTML__SCRIPTS ||
			childFeature == WebPageModelPackage.Literals.ABSTRACT_HTML__STYLES ||
			childFeature == WebPageModelPackage.Literals.SUB_PAGE__SUB_PAGE ||
			childFeature == WebPageModelPackage.Literals.MAIN_PAGE__ADDITIONAL_PAGES ||
			childFeature == WebPageModelPackage.Literals.MAIN_PAGE__MAIN_PAGES;

		if (qualify) {
			return getString
				("_UI_CreateChild_text2",
				 new Object[] { getTypeText(childObject), getFeatureText(childFeature), getTypeText(owner) });
		}
		return super.getCreateChildText(owner, feature, child, selection);
	}

}
