package de.tud.et.ifa.agtele.help;

import java.util.List;
import java.util.ArrayList;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;

/**
 * Representation of all relevant properties for the creation of a help
 * documentation
 * 
 * @author martin
 *
 */
public class HelpItemDescription {
	private EClassHelpItemData eClassDescription;
	private List<EAttributeHelpItemData> attributeDescription;
	private List<EReferenceHelpItemData> nonContainmentReferenceDescription;
	private List<EReferenceHelpItemData> containmentReferenceDescription;
	private EObject eObject;

	public HelpItemDescription(EObject eObject) {
		this.eObject = eObject;
		this.attributeDescription = new ArrayList<>();
		this.nonContainmentReferenceDescription = new ArrayList<>();
		this.containmentReferenceDescription = new ArrayList<>();
	}

	/**
	 * Returns the {@link EClassHelpItemData} description of the {@link EClass}
	 * of an {@link EObject}
	 * 
	 * @return {@link EClassHelpItemData} describing the name and documentation
	 */
	public EClassHelpItemData getEClassDescription() {
		return eClassDescription;
	}

	public void setEClassDescription(EClassHelpItemData eClassDescription) {
		this.eClassDescription = eClassDescription;
	}

	/**
	 * Returns a {@link List} of the {@link EAttributeHelpItemData} descriptions
	 * of the {@link EAttribute} of an {@link EObject}
	 * 
	 * @return @{@link List} of {@link EAttributeHelpItemData}s describing their
	 *         name, type and documentation
	 */
	public List<EAttributeHelpItemData> getAttributeDescription() {
		return attributeDescription;
	}

	public void addAllAttributeDescription(List<EAttributeHelpItemData> attributeDescriptions) {
		attributeDescription.addAll(attributeDescriptions);
	}

	/**
	 * Adds a help description of a {@link EAttribute}
	 * 
	 * @param attributeHelpItemData
	 *            {@link EAttributeHelpItemData} of the attribute
	 */
	public void addAttributeDescription(EAttributeHelpItemData attributeHelpItemData) {
		attributeDescription.add(attributeHelpItemData);
	}

	/**
	 * Returns a {@link List} of the {@link EReferenceHelpItemData} descriptions
	 * of the {@link EReference non-containment References} of an
	 * {@link EObject}
	 * 
	 * @return @{@link List} of {@link EReferenceHelpItemData}s describing their
	 *         name, type and documentation
	 */
	public List<EReferenceHelpItemData> getNonContainmentReferenceDescription() {
		return nonContainmentReferenceDescription;
	}

	/**
	 * Adds a help description of a non-containment reference
	 * 
	 * @param nonContainmentReferenceHelpItemData
	 *            {@link EReferenceHelpItemData} of the reference
	 */
	public void addNonContainmentReferenceDescription(EReferenceHelpItemData nonContainmentReferenceHelpItemData) {
		this.nonContainmentReferenceDescription.add(nonContainmentReferenceHelpItemData);
	}

	public void removeNonContainmentReferenceDescription(EReferenceHelpItemData nonContainmentReferenceHelpItemData) {
		this.nonContainmentReferenceDescription.remove(nonContainmentReferenceHelpItemData);
	}

	public void setNonContainmentReferenceDescription(List<EReferenceHelpItemData> nonContainmentReferenceDescription) {
		this.nonContainmentReferenceDescription = nonContainmentReferenceDescription;
	}

	/**
	 * Returns a {@link List} of the {@link HelpItemData} descriptions of the
	 * {@link EReference non-containment References} of an {@link EObject}
	 * 
	 * @return @{@link List} of {@link HelpItemData}s describing their name,
	 *         type and documentation
	 */
	public List<EReferenceHelpItemData> getContainmentReferenceDescription() {
		return containmentReferenceDescription;
	}

	/**
	 * Adds a help description of a containment reference
	 * 
	 * @param containmentReferenceHelpItemData
	 *            {@link EReferenceHelpItemData} of the reference
	 */
	public void addContainmentReferenceDescription(EReferenceHelpItemData containmentReferenceHelpItemData) {
		this.containmentReferenceDescription.add(containmentReferenceHelpItemData);
	}

	public void removeContainmentReferenceDescription(EReferenceHelpItemData containmentReferenceHelpItemData) {
		this.containmentReferenceDescription.remove(containmentReferenceHelpItemData);
	}

	public void setContainmentReferenceDescription(List<EReferenceHelpItemData> containmentReferenceDescription) {
		this.containmentReferenceDescription = containmentReferenceDescription;
	}

	/**
	 * Returns the {@link EObject} that the {@link HelpItemDescription} was
	 * created for
	 * 
	 * @return the currently selected {@link EObject}
	 */
	public EObject getEObject() {
		return eObject;
	}
	
	
}