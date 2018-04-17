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

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;

/**
 * Representation of an help entry of an {@link EObject}
 * 
 * @author martin
 *
 */
public abstract class HelpItemData {
	private String name;
	private String documentation;
	private String dataType;
	
	/**
	 * @return the name of the element to be described
	 */
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Returns the documentation as defined in the ecore model or an empty
	 * string if no documentation has been defined
	 * 
	 * @return the documentation or an empty string
	 */
	public String getDocumentation() {
		return documentation;
	}

	public void setDocumentation(String documentation) {
		this.documentation = documentation;
	}

	/**
	 * Return the data type of the object: The {@link EDataType} in case of an
	 * {@link EAttribute}, the target type in case of a {@link EReference} or
	 * {@link EClass}.
	 * 
	 * @return the data type of this item
	 */
	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
}
