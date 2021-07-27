/**
 */
package de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.importAdapter;

import java.util.Collection;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.importAdapter.ImportAdapterPackage
 * @generated
 */
public interface ImportAdapterFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	ImportAdapterFactory eINSTANCE = de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.importAdapter.impl.ImportAdapterFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Import Adapter</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Import Adapter</em>'.
	 * @generated
	 */
	ImportAdapter createImportAdapter();

	/**
	 * Returns a new object of class '<em>File System Connector</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>File System Connector</em>'.
	 * @generated
	 */
	FileSystemConnector createFileSystemConnector();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	ImportAdapterPackage getImportAdapterPackage();
	
	Collection<String> getRegisteredConnectionSchemes();
	
	Connector createConnector(String uri);
	
	void registerConnectorClass(String[] schema, EClass cls);

} //ImportAdapterFactory
