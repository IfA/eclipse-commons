/**
 */
package de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Announcement</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.Announcement#isPropagate <em>Propagate</em>}</li>
 *   <li>{@link de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.Announcement#getType <em>Type</em>}</li>
 *   <li>{@link de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.Announcement#getLocation <em>Location</em>}</li>
 *   <li>{@link de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.Announcement#getContent <em>Content</em>}</li>
 *   <li>{@link de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.Announcement#isClosable <em>Closable</em>}</li>
 * </ul>
 *
 * @see de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.WebPageModelPackage#getAnnouncement()
 * @model
 * @generated
 */
public interface Announcement extends Base {
	/**
	 * Returns the value of the '<em><b>Propagate</b></em>' attribute.
	 * The default value is <code>"false"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Propagate</em>' attribute.
	 * @see #setPropagate(boolean)
	 * @see de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.WebPageModelPackage#getAnnouncement_Propagate()
	 * @model default="false"
	 * @generated
	 */
	boolean isPropagate();

	/**
	 * Sets the value of the '{@link de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.Announcement#isPropagate <em>Propagate</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Propagate</em>' attribute.
	 * @see #isPropagate()
	 * @generated
	 */
	void setPropagate(boolean value);

	/**
	 * Returns the value of the '<em><b>Type</b></em>' attribute.
	 * The default value is <code>"primary"</code>.
	 * The literals are from the enumeration {@link de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.AnnouncementTypeEnum}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type</em>' attribute.
	 * @see de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.AnnouncementTypeEnum
	 * @see #setType(AnnouncementTypeEnum)
	 * @see de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.WebPageModelPackage#getAnnouncement_Type()
	 * @model default="primary"
	 * @generated
	 */
	AnnouncementTypeEnum getType();

	/**
	 * Sets the value of the '{@link de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.Announcement#getType <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type</em>' attribute.
	 * @see de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.AnnouncementTypeEnum
	 * @see #getType()
	 * @generated
	 */
	void setType(AnnouncementTypeEnum value);

	/**
	 * Returns the value of the '<em><b>Location</b></em>' attribute.
	 * The default value is <code>"AboveHeading"</code>.
	 * The literals are from the enumeration {@link de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.AnnouncementLocationEnum}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Location</em>' attribute.
	 * @see de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.AnnouncementLocationEnum
	 * @see #setLocation(AnnouncementLocationEnum)
	 * @see de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.WebPageModelPackage#getAnnouncement_Location()
	 * @model default="AboveHeading"
	 * @generated
	 */
	AnnouncementLocationEnum getLocation();

	/**
	 * Sets the value of the '{@link de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.Announcement#getLocation <em>Location</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Location</em>' attribute.
	 * @see de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.AnnouncementLocationEnum
	 * @see #getLocation()
	 * @generated
	 */
	void setLocation(AnnouncementLocationEnum value);

	/**
	 * Returns the value of the '<em><b>Content</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Content</em>' containment reference.
	 * @see #setContent(Value)
	 * @see de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.WebPageModelPackage#getAnnouncement_Content()
	 * @model containment="true"
	 * @generated
	 */
	Value getContent();

	/**
	 * Sets the value of the '{@link de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.Announcement#getContent <em>Content</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Content</em>' containment reference.
	 * @see #getContent()
	 * @generated
	 */
	void setContent(Value value);

	/**
	 * Returns the value of the '<em><b>Closable</b></em>' attribute.
	 * The default value is <code>"false"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Closable</em>' attribute.
	 * @see #setClosable(boolean)
	 * @see de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.WebPageModelPackage#getAnnouncement_Closable()
	 * @model default="false"
	 * @generated
	 */
	boolean isClosable();

	/**
	 * Sets the value of the '{@link de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.Announcement#isClosable <em>Closable</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Closable</em>' attribute.
	 * @see #isClosable()
	 * @generated
	 */
	void setClosable(boolean value);

} // Announcement
