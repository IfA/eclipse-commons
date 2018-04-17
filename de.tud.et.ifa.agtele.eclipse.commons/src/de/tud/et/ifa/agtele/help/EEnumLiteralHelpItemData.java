/*******************************************************************************
 * Copyright (C) 2016-2018 Institute of Automation, TU Dresden.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Institute of Automation, TU Dresden - initial API and implementation
 ******************************************************************************/
package de.tud.et.ifa.agtele.help;

import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.emf.ecore.util.EcoreUtil;

/**
 * Representation of an help entry for an ({@link EEnumLiteral}
 * 
 * @author martin
 *
 */
public class EEnumLiteralHelpItemData extends HelpItemData {
	private EEnumLiteral eEnumLiteral;
	private int value;
	private String literal;
	
	public EEnumLiteralHelpItemData(EEnumLiteral eEnumLiteral) {
		this.eEnumLiteral = eEnumLiteral;
		// Name
		this.setName(eEnumLiteral.getName());
		// DataType
		this.setDataType("EENumLiteral");
		// Documentation
		if (EcoreUtil.getDocumentation(eEnumLiteral) != null)
			setDocumentation(EcoreUtil.getDocumentation(eEnumLiteral));
		else
			setDocumentation("");
		// Value
		setValue(eEnumLiteral.getValue());
		// Literal
		setLiteral(eEnumLiteral.getLiteral());
	}
	
	/**
	 * @return the {@link EEnumLiteral} this item describes
	 */
	public EEnumLiteral geteEnumLiteral() {
		return eEnumLiteral;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public String getLiteral() {
		return literal;
	}

	public void setLiteral(String literal) {
		this.literal = literal;
	}
}
