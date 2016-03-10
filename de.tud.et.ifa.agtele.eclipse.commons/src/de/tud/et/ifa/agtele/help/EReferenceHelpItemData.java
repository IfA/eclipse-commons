package de.tud.et.ifa.agtele.help;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.util.EcoreUtil;

/**
 * Representation of an help entry for an ({@link EReference Non-Containment Reference}
 * 
 * @author martin
 *
 */
public class EReferenceHelpItemData extends HelpItemData {
	private EReference eReference;
	private List<EClassHelpItemData> childrenData; 
	
	/**
	 * Creates an help entry for an {@link EReference}
	 * 
	 * @param eReference the {@link EReference} this item should describe
	 * @param children a list of possible children of the eReference
	 *
	 */
	public EReferenceHelpItemData(EReference eReference, List<EObject> children) {
		this.eReference = eReference;
		
		// Name
		setName(eReference.getName());
		// DataType
		setDataType(eReference.getEGenericType().getEClassifier().getName());
		// Documentation
		if (EcoreUtil.getDocumentation(eReference) != null)
			setDocumentation(EcoreUtil.getDocumentation(eReference));
		else
			setDocumentation("");
		
		System.out.println("Ref:"+this.getName());
		
		// Child elements
		childrenData = new ArrayList<EClassHelpItemData>();
		if (children != null) {
			for (EObject child : children) {
				if (child != null) {
					System.out.println(child.eClass());
					childrenData.add(new EClassHelpItemData(child.eClass()));
				}
			}
		}
	}

	/**
	 * @return the {@link EReference Non-Containment Reference} this item describes
	 */
	public EReference getEReference() {
		return eReference;
	}

	/**
	 * @return the {@link List} of possible child elements
	 */
	public List<EClassHelpItemData> getChildrenData() {
		return childrenData;
	}
}
