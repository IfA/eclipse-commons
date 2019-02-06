package de.tud.et.ifa.agtele.emf.importing;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.util.EcoreUtil;

public interface IModelImportStrategy {
	@SuppressWarnings({ "unchecked", "rawtypes" })
	default void importContent(IModelImporter adapter, IModelConnector connector, EObject eObject, EReference reference) {
		ArrayList<EObject> createdElements = new ArrayList<>();
		for (Object node : connector.readReference(adapter.getOriginalNode(eObject), reference)) {
			INodeDescriptor descriptor = connector.getTypeInfo(node);
			if (descriptor != null) {
				if (eObject.eContainer() != null && adapter.getOriginalNode(eObject.eContainer()).equals(node)) {
					continue;
				}
				EObject obj = adapter.createEObject(descriptor);
				if (obj != null) {
					createdElements.add(obj);
				}
			}
		}
		if (!createdElements.isEmpty()) {
			if (reference.isMany()) {
				((EList)eObject.eGet(reference)).addAll(createdElements);
			} else {
				eObject.eSet(reference, createdElements.get(0));
			}				
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	default void restoreReference(IModelImporter adapter, IModelConnector connector, EObject eObject, EReference reference) {
		ArrayList<EObject> referencedElements = new ArrayList<>();
		for (Object node : connector.readReference(adapter.getOriginalNode(eObject), reference)) {
			EObject referencedElement = adapter.getCreatedEObject(node);
			if (referencedElement != null) {
				referencedElements.add(referencedElement);
			}
		}
		if (!referencedElements.isEmpty()) {
			try {
				if (reference.isMany()) {
					((EList)eObject.eGet(reference)).addAll(referencedElements);
				} else {
					eObject.eSet(reference, referencedElements.get(0));
				}
			} catch (Exception e) {
				//Do nothing
			}
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	default void restoreAttribute(IModelImporter adapter, IModelConnector connector, EObject eObject, EAttribute attribute) {
		ArrayList<Object> attrValues = new ArrayList<>();
		for (Object val : connector.readAttribute(adapter.getOriginalNode(eObject), attribute)) {
			Object restored = null;
			if (val instanceof String) {
				restored = this.convertAttributeValue((String)val, attribute);					
			} else {
				restored = this.convertAttributeValue(val.toString(), attribute);	
			}
			if (restored != null) {
				attrValues.add(restored);
			}
		}

		if (!attrValues.isEmpty()) {
			if (attribute.isMany()) {
				((EList)eObject.eGet(attribute)).addAll(attrValues);
			} else {
				eObject.eSet(attribute, attrValues.get(0));
			}				
		}			
	}

	default Collection<EReference> getEContainmentsForImport(IModelImporter adapter, IModelConnector connector, EObject eObject) {
		return eObject.eClass().getEAllContainments().stream().filter(f -> !f.isDerived()).collect(Collectors.toList());
	}

	default Collection<EReference> getEReferencesForImport(IModelImporter adapter, IModelConnector connector, EObject eObject) {
		return eObject.eClass().getEAllReferences().stream().filter(f -> /*!f.isDerived() &&*/ !f.isContainer()).collect(Collectors.toList());
	}

	default Collection<EAttribute> getEAttributesForImport(IModelImporter adapter, IModelConnector connector, EObject eObject) {
		return eObject.eClass().getEAllAttributes().stream().filter(f -> !f.isDerived()).collect(Collectors.toList());
	}

	default Object convertAttributeValue(String value, EAttribute attribute) {
		try {
			if (attribute.getEType() == EcorePackage.Literals.EDATE) {
				DateTimeFormatter format = DateTimeFormatter.ofPattern("EEE,MMM,dd,yyyy,HH:mm:ss,'GMT'Z", Locale.US);
				String fixedValue = value.substring(0, value.indexOf('(')).trim().replaceAll("\\s", ",");
				LocalDateTime time = LocalDateTime.parse(fixedValue, format);
				return Date.from(time.toInstant(ZoneOffset.ofHoursMinutes(0, 0)));
			}
			return EcoreUtil.createFromString(attribute.getEAttributeType(), value);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	default IModelImportStrategy unwrap() {
		return this;
	}

	default void postImport(IModelImporter iModelImporter, IModelConnector connector, EObject eObject) {}
}
