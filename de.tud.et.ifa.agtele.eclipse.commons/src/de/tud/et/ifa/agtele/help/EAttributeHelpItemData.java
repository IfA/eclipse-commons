package de.tud.et.ifa.agtele.help;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.emf.ecore.util.EcoreUtil;

/**
 * Representation of an help entry for an ({@link EAttribute}
 * 
 * @author martin
 *
 */
public class EAttributeHelpItemData extends HelpItemData {
	private EAttribute eAttribute;
	private Boolean isEEnum;
	private List<EEnumLiteralHelpItemData> eEnumLiterals;
	private String eEnumDocumentation;
	
	public EAttributeHelpItemData(EAttribute eAttribute) {
		this.eAttribute = eAttribute;
		this.isEEnum = eAttribute.getEAttributeType() instanceof EEnum;
		
		// Name
		setName(eAttribute.getName());
		// DataType
		setDataType(eAttribute.getEAttributeType().getName());
		// Documentation
		if (EcoreUtil.getDocumentation(eAttribute) != null)
			setDocumentation(EcoreUtil.getDocumentation(eAttribute));
		else
			setDocumentation("");
		
		this.eEnumLiterals = new ArrayList<>();
		this.eEnumDocumentation = "";
		if (isEEnum) {
			// Documentation EEnum
			if (EcoreUtil.getDocumentation((EEnum) eAttribute.getEAttributeType()) != null)
				setDocumentation(EcoreUtil.getDocumentation((EEnum) eAttribute.getEAttributeType()));
			//Literals
			for (EEnumLiteral eEnumLiteral : ((EEnum) eAttribute.getEAttributeType()).getELiterals()) {
				this.eEnumLiterals.add(new EEnumLiteralHelpItemData(eEnumLiteral));
			}
		}
	}
	
	/**
	 * @return the {@link EAttribute} this item describes
	 */
	public EAttribute getEAttribute() {
		return eAttribute;
	}
	
	public Boolean isEEnum() {
		return isEEnum;
	}

	public List<EEnumLiteralHelpItemData> getEEnumLiterals() {
		return eEnumLiterals;
	}

	public String getEEnumDocumentation() {
		return eEnumDocumentation;
	}
}
