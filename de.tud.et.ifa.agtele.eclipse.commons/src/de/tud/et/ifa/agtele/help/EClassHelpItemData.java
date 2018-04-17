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
