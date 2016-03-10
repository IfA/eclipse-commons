package de.tud.et.ifa.agtele.help;

import java.util.List;
import java.util.ArrayList;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;

/**
 * Representation of all relevant properties for the creation of a help documentation
 * 
 * @author martin
 *
 */
public class HelpItemDescription {
		private EClassHelpItemData eClassDescription;
		private List<EAttributeHelpItemData> attributeDescription;
		private HashMap<HelpItemData, List<HelpItemData>> nonContainmentReferenceDescription;
		private HashMap<HelpItemData, List<HelpItemData>> containmentReferenceDescription;
		private EObject eObject;
		
		public HelpItemDescription(EObject eObject) {
			this.eObject = eObject;
			this.attributeDescription = new ArrayList<EAttributeHelpItemData>();
		}


		/**
		 * Returns the {@link EClassHelpItemData} description of the {@link EClass} of an {@link EObject}
		 * 
		 * @return {@link EClassHelpItemData} describing the name and documentation
		 */
		public HelpItemData getEClassDescription() {
			return eClassDescription;
		}


		public void setEClassDescription(EClassHelpItemData eClassDescription) {
			this.eClassDescription = eClassDescription;
		}


		/**
		 * Returns a {@link List} of the {@link EAttributeHelpItemData} descriptions of the {@link EAttribute} of an {@link EObject}
		 * 
		 * @return @{@link List} of {@link EAttributeHelpItemData}s describing their name, type and documentation
		 */
		public List<EAttributeHelpItemData> getAttributeDescription() {
			return attributeDescription;
		}



		public void addAllAttributeDescription(List<EAttributeHelpItemData> attributeDescription) {
			attributeDescription.addAll(attributeDescription);
		}
		
		/**
		 * Adds a help description of a {@link EAttribute}
		 * @param attributeHelpItemData {@link EAttributeHelpItemData} of the attribute
		 */
		public void addAttributeDescription(EAttributeHelpItemData attributeHelpItemData) {
			attributeDescription.add(attributeHelpItemData);
		}


		/**
		 * Returns a {@link List} of the {@link HelpItemData} descriptions of the {@link EReference containment References} of an {@link EObject}
		 * 
		 * @return @{@link List} of {@link HelpItemData}s describing their name, type and documentation
		 */
		public HashMap<HelpItemData, List<HelpItemData>> getNonContainmentReferenceDescription() {
			return nonContainmentReferenceDescription;
		}



		public void setNonContainmentReferenceDescription(HashMap<HelpItemData, List<HelpItemData>> nonContainmentReferenceDescription) {
			this.nonContainmentReferenceDescription = nonContainmentReferenceDescription;
		}


		/**
		 * Returns a {@link List} of the {@link HelpItemData} descriptions of the {@link EReference non-containment References} of an {@link EObject}
		 * 
		 * @return @{@link List} of {@link HelpItemData}s describing their name, type and documentation
		 */
		public HashMap<HelpItemData, List<HelpItemData>> getContainmentReferenceDescription() {
			return containmentReferenceDescription;
		}



		public void setContainmentReferenceDescription(HashMap<HelpItemData, List<HelpItemData>> containmentReferenceDescription) {
			this.containmentReferenceDescription = containmentReferenceDescription;
		}

		/**
		 * Returns the {@link EObject} that the {@link HelpItemDescription} was created for
		 * 
		 * @return the currently selected {@link EObject}
		 */
		public EObject getEObject() {
			return eObject;
		}


		public void setEObject(EObject eObject) {
			this.eObject = eObject;
		}		
			
	}