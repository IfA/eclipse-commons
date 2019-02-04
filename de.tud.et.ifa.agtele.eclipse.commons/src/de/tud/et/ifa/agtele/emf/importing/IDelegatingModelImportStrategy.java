package de.tud.et.ifa.agtele.emf.importing;

import java.util.Collection;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;

public interface IDelegatingModelImportStrategy extends IModelImportStrategy {
	
	public IModelImportStrategy getImportStrategyDelegate();
	public void setImportStrategyDelegate(IModelImportStrategy delegate);
	
	public IDelegatingModelImportStrategy wrap(IModelImportStrategy delegate);
	
	default void importContent(IModelImporter adapter, IModelConnector connector, EObject eObject, EReference reference) {
		this.getImportStrategyDelegate().importContent(adapter, connector, eObject, reference);
	}
	default void restoreReference(IModelImporter adapter, IModelConnector connector, EObject eObject, EReference reference) {
		this.getImportStrategyDelegate().restoreReference(adapter, connector, eObject, reference);
	}
	default void restoreAttribute(IModelImporter adapter, IModelConnector connector, EObject eObject, EAttribute attribute) {
		this.getImportStrategyDelegate().restoreAttribute(adapter, connector, eObject, attribute);
	}
	
	default Collection<EReference> getEContainmentsForImport (IModelImporter adapter, IModelConnector connector, EObject eObject) {
		return this.getImportStrategyDelegate().getEContainmentsForImport(adapter, connector, eObject);
	}
	default Collection<EReference> getEReferencesForImport (IModelImporter adapter, IModelConnector connector, EObject eObject) {
		return this.getImportStrategyDelegate().getEReferencesForImport(adapter, connector, eObject);
	}
	default Collection<EAttribute> getEAttributesForImport (IModelImporter adapter, IModelConnector connector, EObject eObject) {
		return this.getImportStrategyDelegate().getEAttributesForImport(adapter, connector, eObject);
	}
	
	default Object convertAttributeValue(String value, EAttribute attribute) {
		return this.getImportStrategyDelegate().convertAttributeValue(value, attribute);
	}
	default void postImport(IModelImporter adapter, IModelConnector connector, EObject eObject) {
		this.getImportStrategyDelegate().postImport(adapter, connector, eObject);
	}
	default IModelImportStrategy unwrap () {
		return this.getImportStrategyDelegate().unwrap();
	}
}
