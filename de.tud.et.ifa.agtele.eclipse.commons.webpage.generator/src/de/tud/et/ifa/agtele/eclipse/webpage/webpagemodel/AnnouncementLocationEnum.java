/**
 */
package de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Announcement Location Enum</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.WebPageModelPackage#getAnnouncementLocationEnum()
 * @model
 * @generated
 */
public enum AnnouncementLocationEnum implements Enumerator {
	/**
	 * The '<em><b>Above Page</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #ABOVE_PAGE_VALUE
	 * @generated
	 * @ordered
	 */
	ABOVE_PAGE(0, "AbovePage", "AbovePage"),

	/**
	 * The '<em><b>Above Content</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #ABOVE_CONTENT_VALUE
	 * @generated
	 * @ordered
	 */
	ABOVE_CONTENT(1, "AboveContent", "AboveContent"),

	/**
	 * The '<em><b>Above Heading</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #ABOVE_HEADING_VALUE
	 * @generated
	 * @ordered
	 */
	ABOVE_HEADING(2, "AboveHeading", "AboveHeading"),

	/**
	 * The '<em><b>Below Heading</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #BELOW_HEADING_VALUE
	 * @generated
	 * @ordered
	 */
	BELOW_HEADING(3, "BelowHeading", "BelowHeading"),

	/**
	 * The '<em><b>Below Content</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #BELOW_CONTENT_VALUE
	 * @generated
	 * @ordered
	 */
	BELOW_CONTENT(4, "BelowContent", "BelowContent"),

	/**
	 * The '<em><b>Above Footer</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #ABOVE_FOOTER_VALUE
	 * @generated
	 * @ordered
	 */
	ABOVE_FOOTER(5, "AboveFooter", "AboveFooter"),

	/**
	 * The '<em><b>Below Footer</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #BELOW_FOOTER_VALUE
	 * @generated
	 * @ordered
	 */
	BELOW_FOOTER(6, "BelowFooter", "BelowFooter");

	/**
	 * The '<em><b>Above Page</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #ABOVE_PAGE
	 * @model name="AbovePage"
	 * @generated
	 * @ordered
	 */
	public static final int ABOVE_PAGE_VALUE = 0;

	/**
	 * The '<em><b>Above Content</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #ABOVE_CONTENT
	 * @model name="AboveContent"
	 * @generated
	 * @ordered
	 */
	public static final int ABOVE_CONTENT_VALUE = 1;

	/**
	 * The '<em><b>Above Heading</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #ABOVE_HEADING
	 * @model name="AboveHeading"
	 * @generated
	 * @ordered
	 */
	public static final int ABOVE_HEADING_VALUE = 2;

	/**
	 * The '<em><b>Below Heading</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #BELOW_HEADING
	 * @model name="BelowHeading"
	 * @generated
	 * @ordered
	 */
	public static final int BELOW_HEADING_VALUE = 3;

	/**
	 * The '<em><b>Below Content</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #BELOW_CONTENT
	 * @model name="BelowContent"
	 * @generated
	 * @ordered
	 */
	public static final int BELOW_CONTENT_VALUE = 4;

	/**
	 * The '<em><b>Above Footer</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #ABOVE_FOOTER
	 * @model name="AboveFooter"
	 * @generated
	 * @ordered
	 */
	public static final int ABOVE_FOOTER_VALUE = 5;

	/**
	 * The '<em><b>Below Footer</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #BELOW_FOOTER
	 * @model name="BelowFooter"
	 * @generated
	 * @ordered
	 */
	public static final int BELOW_FOOTER_VALUE = 6;

	/**
	 * An array of all the '<em><b>Announcement Location Enum</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final AnnouncementLocationEnum[] VALUES_ARRAY =
		new AnnouncementLocationEnum[] {
			ABOVE_PAGE,
			ABOVE_CONTENT,
			ABOVE_HEADING,
			BELOW_HEADING,
			BELOW_CONTENT,
			ABOVE_FOOTER,
			BELOW_FOOTER,
		};

	/**
	 * A public read-only list of all the '<em><b>Announcement Location Enum</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<AnnouncementLocationEnum> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Announcement Location Enum</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param literal the literal.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static AnnouncementLocationEnum get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			AnnouncementLocationEnum result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Announcement Location Enum</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param name the name.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static AnnouncementLocationEnum getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			AnnouncementLocationEnum result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Announcement Location Enum</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the integer value.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static AnnouncementLocationEnum get(int value) {
		switch (value) {
			case ABOVE_PAGE_VALUE: return ABOVE_PAGE;
			case ABOVE_CONTENT_VALUE: return ABOVE_CONTENT;
			case ABOVE_HEADING_VALUE: return ABOVE_HEADING;
			case BELOW_HEADING_VALUE: return BELOW_HEADING;
			case BELOW_CONTENT_VALUE: return BELOW_CONTENT;
			case ABOVE_FOOTER_VALUE: return ABOVE_FOOTER;
			case BELOW_FOOTER_VALUE: return BELOW_FOOTER;
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private final int value;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private final String name;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private final String literal;

	/**
	 * Only this class can construct instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private AnnouncementLocationEnum(int value, String name, String literal) {
		this.value = value;
		this.name = name;
		this.literal = literal;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int getValue() {
	  return value;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getName() {
	  return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getLiteral() {
	  return literal;
	}

	/**
	 * Returns the literal value of the enumerator, which is its string representation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		return literal;
	}
	
} //AnnouncementLocationEnum
