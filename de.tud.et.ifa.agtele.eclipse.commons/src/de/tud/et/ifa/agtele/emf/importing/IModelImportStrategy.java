package de.tud.et.ifa.agtele.emf.importing;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Set;
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
				if (eObject.eContainer() != null && adapter.getOriginalNode(eObject.eContainer()).equals(node)) { //ignore container references
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
				try {
					((EList)eObject.eGet(reference)).addAll(createdElements);
					((EList)eObject.eGet(reference)).removeIf(e -> e==null);
				} catch (Exception e) {
					e.printStackTrace();
					//TODO update a problem indication
				}
			} else {
				try {
					eObject.eSet(reference, createdElements.get(0));
				} catch (Exception e) {
					e.printStackTrace();
					//TODO update a problem indication
				}
			}				
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	default void restoreReference(IModelImporter adapter, IModelConnector connector, EObject eObject, EReference reference) {
		ArrayList<EObject> referencedElements = new ArrayList<>();
		RefEntryLoop:
		for (Object node : connector.readReference(adapter.getOriginalNode(eObject), reference)) {
			if (node == null) {
				continue;
			}
			//1st try to restore reference from the same context		
			EObject ownerContext = adapter.getImportRegistry().getContextOfImported(eObject);
			EObject withinContextTarget = adapter.getImportRegistry().getImportedElement(node, ownerContext);
			if (withinContextTarget != null && reference.getEType().isInstance(withinContextTarget) || reference.getEType() == EcorePackage.Literals.EOBJECT) {
				referencedElements.add(withinContextTarget);
				continue;
			}
			
			//2nd if the reference is typed with an aas element type, try the target node directly
			if (node instanceof EObject && (reference.getEType().isInstance(node) || (reference.getEType() == EcorePackage.Literals.EOBJECT))) {
				referencedElements.add((EObject) node);
				continue;
			}
						
			//3nd find a restored element of the target that matches the reference type
			Set<EObject> restoredPossibleTargets = adapter.getCreatedEObjects(node);
			if (restoredPossibleTargets != null) {
				for (EObject possibleTarget : restoredPossibleTargets) {
					if (reference.getEType().isInstance(possibleTarget)) {
						referencedElements.add(possibleTarget);
						continue RefEntryLoop;
					}
				}
			}			
			//TODO log this reconstruction error
		}
		if (!referencedElements.isEmpty()) {
			try {
				if (reference.isMany()) {
					((EList)eObject.eGet(reference)).addAll(referencedElements);
					((EList)eObject.eGet(reference)).removeIf(e -> e==null);
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
			if (val == null) {
				continue;
			}
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
		return eObject.eClass().getEAllReferences().stream().filter(f -> /*!f.isDerived() &&*/ !f.isContainer() && !f.isContainment()).collect(Collectors.toList());
	}

	default Collection<EAttribute> getEAttributesForImport(IModelImporter adapter, IModelConnector connector, EObject eObject) {
		return eObject.eClass().getEAllAttributes().stream().filter(f -> !f.isDerived()).collect(Collectors.toList());
	}

	default Object convertAttributeValue(String value, EAttribute attribute) {
		String fixedValue =  value.indexOf('(') > 0 ? 
			value.substring(0, value.indexOf('(')).trim().replaceAll("\\s", ",") : 
				value.trim().replaceAll("\\s", ","),
				pattern1 = "EEE,MMM,dd,yyyy,HH:mm:ss,'GMT'Z",
				pattern2 = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
		try {
			if (attribute.getEType() == EcorePackage.Literals.EDATE) {
				DateTimeFormatter format = DateTimeFormatter.ofPattern(pattern1, Locale.US);
				
				LocalDateTime time = LocalDateTime.parse(fixedValue, format);
				return Date.from(time.toInstant(ZoneOffset.ofHoursMinutes(0, 0)));
			}
			return EcoreUtil.createFromString(attribute.getEAttributeType(), value);
		} catch (Exception e1) {
				//e.g. 2020-05-25T12:17:18.005Z
//			DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-ddEHH:mm.SSSVV", Locale.US);
			DateTimeFormatter format = DateTimeFormatter.ofPattern(pattern2, Locale.US);

			try {
				LocalDateTime time = LocalDateTime.parse(fixedValue, format);
				return Date.from(time.toInstant(ZoneOffset.ofHoursMinutes(0, 0)));
			} catch (Exception e2) {
				System.err.println("Could not parse date time string :'" + fixedValue + "', 1st try pattern: '" + pattern1 + "', 2nd try pattern: '" + pattern2 + "'");
//				e1.printStackTrace();
//				e2.printStackTrace();
			}
		}
		return null;
	}
	
	default IModelImportStrategy unwrap() {
		return this;
	}

	default void postImport(IModelImporter iModelImporter, IModelConnector connector, EObject eObject) {}
}
