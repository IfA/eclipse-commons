package de.tud.et.ifa.agtele.help;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.util.EcoreUtil;

/**
 * Representation of an help entry for an ({@link EClass}
 * 
 * @author martin
 *
 */
public class EClassHelpItemData extends HelpItemData {
	private EClass eClass;

	public EClassHelpItemData(EClass eClass) {
		this.eClass = eClass;
		// Name
		setName(eClass.getName());
		// DataType
		setDataType(eClass.eClass().getName());
		// Documentation
		if (EcoreUtil.getDocumentation(eClass) != null)
			setDocumentation(EcoreUtil.getDocumentation(eClass));
		else
			setDocumentation("");
	}

	/**
	 * @return the {@link EClass} this item describes
	 */
	public EClass getEClass() {
		return eClass;
	}
	
}
