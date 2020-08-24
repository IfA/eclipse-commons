/**
 */
package de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel;

import java.util.List;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Page</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.Page#isSuppressMainMenu <em>Suppress Main Menu</em>}</li>
 *   <li>{@link de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.Page#getNavIcon <em>Nav Icon</em>}</li>
 * </ul>
 *
 * @see de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.WebPageModelPackage#getPage()
 * @model
 * @generated
 */
public interface Page extends SubPage {
	/**
	 * Returns the value of the '<em><b>Nav Icon</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Nav Icon</em>' containment reference.
	 * @see #setNavIcon(Value)
	 * @see de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.WebPageModelPackage#getPage_NavIcon()
	 * @model containment="true"
	 * @generated
	 */
	Value getNavIcon();

	/**
	 * Sets the value of the '{@link de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.Page#getNavIcon <em>Nav Icon</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Nav Icon</em>' containment reference.
	 * @see #getNavIcon()
	 * @generated
	 */
	void setNavIcon(Value value);

	/**
	 * Returns the value of the '<em><b>Suppress Main Menu</b></em>' attribute.
	 * The default value is <code>"false"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Suppress Main Menu</em>' attribute.
	 * @see #setSuppressMainMenu(boolean)
	 * @see de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.WebPageModelPackage#getPage_SuppressMainMenu()
	 * @model default="false"
	 * @generated
	 */
	boolean isSuppressMainMenu();

	/**
	 * Sets the value of the '{@link de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.Page#isSuppressMainMenu <em>Suppress Main Menu</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Suppress Main Menu</em>' attribute.
	 * @see #isSuppressMainMenu()
	 * @generated
	 */
	void setSuppressMainMenu(boolean value);

	default boolean isNode() {
		return true;
	}
	
	default boolean hasIcon () {
		return this.getNavIcon() != null;
	}
		
	default String getIconUrl () {
		if (this.hasIcon() && this.getNavIcon() instanceof FileValue) {
			String iconPath = ((FileValue)this.getNavIcon()).getValue();
			if (iconPath.charAt(0) == '/') {
				return this.getMainPage().getParentUrl() + iconPath;
			}
			return this.getParentUrl() + "/" + iconPath;
		}
		return null;
	}
	
	default List<String> getAllLocalStaticResources() {
		List<String> result = SubPage.super.getAllLocalStaticResources();
		if (this.hasIcon()) {
			result.add(this.getIconUrl());
		}
		return result;
	}
} // Page
