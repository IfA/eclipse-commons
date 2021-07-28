/**
 */
package de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.importAdapter.impl;

import java.util.ArrayList;
import java.util.Collection;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.importAdapter.*;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class ImportAdapterFactoryImpl extends EFactoryImpl implements ImportAdapterFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static ImportAdapterFactory init() {
		try {
			ImportAdapterFactory theImportAdapterFactory = (ImportAdapterFactory)EPackage.Registry.INSTANCE.getEFactory(ImportAdapterPackage.eNS_URI);
			if (theImportAdapterFactory != null) {
				return theImportAdapterFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new ImportAdapterFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ImportAdapterFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case ImportAdapterPackage.IMPORT_ADAPTER: return createImportAdapter();
			case ImportAdapterPackage.FILE_SYSTEM_CONNECTOR: return createFileSystemConnector();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ImportAdapter createImportAdapter() {
		ImportAdapterImpl importAdapter = new ImportAdapterImpl();
		return importAdapter;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public FileSystemConnector createFileSystemConnector() {
		FileSystemConnectorImpl fileSystemConnector = new FileSystemConnectorImpl();
		return fileSystemConnector;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ImportAdapterPackage getImportAdapterPackage() {
		return (ImportAdapterPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static ImportAdapterPackage getPackage() {
		return ImportAdapterPackage.eINSTANCE;
	}

	protected ArrayList<RegEntry> entries = new ArrayList<>();
	
	protected boolean configured = false;
	
	public static final String CONNECTOR_EXTENSION_POINT_ID = "de.tud.et.ifa.agtele.eclipse.commons.emf.storage.connector";
	
	public synchronized void configure(){
		if (configured) {
			return;
		}
		configured = true;
		IExtensionRegistry registry = Platform.getExtensionRegistry();
		
		IConfigurationElement[] elements
	      = registry.getConfigurationElementsFor( CONNECTOR_EXTENSION_POINT_ID );
		
		for(IConfigurationElement element : elements) {
			String className = element.getAttribute("className"),
					packageUri = element.getAttribute("packageUri");
			ArrayList<String> schemes = new ArrayList<>();
			IConfigurationElement[] schemaEntries = element.getChildren("schemaEntry");
			
			for(IConfigurationElement schemaEntry : schemaEntries) {
				if (schemaEntry.getAttribute("prefix") != null) {
					schemes.add(schemaEntry.getAttribute("prefix"));
				}
			}
			
			
			EPackage.Registry eRegistry = EPackage.Registry.INSTANCE;			
			EPackage pkg = eRegistry.getEPackage(packageUri);
			if (pkg == null) {
				continue;
			}			
			EClassifier classifier = pkg.getEClassifier(className);			
			if (!(classifier instanceof EClass) || ((EClass)classifier).isAbstract()) {
				continue;
			}			
			EClass cls = (EClass) classifier;
			
			this.registerConnectorClass(schemes.toArray(new String[schemes.size()]), cls);						
		}
	}
	
	@Override
	public Collection<String> getRegisteredConnectionSchemes() {
		configure();
		ArrayList<String> result = new ArrayList<>();
		
		for (RegEntry entry : this.entries) {
			for (String scheme : entry.schema) {
				if (!result.contains(scheme)) {
					result.add(scheme);
				}
			}			
		}
		
		return result;
	}

	@Override
	public Connector createConnector(String uri) {
		EClass cls = this.getConnectorClassForUri(uri);
		if (cls != null) {
			Connector result = (Connector) cls.getEPackage().getEFactoryInstance().create(cls);
			result.setConnectionUri(uri);
			return result;
		}		
		return null;
	}

	public EClass getConnectorClassForUri(String uri) {
		configure();
		for (RegEntry entry : new ArrayList<>(this.entries)) {
			if (entry != null && this.matchesSchema(uri, entry)) {
				return entry.cls;
			}		
		}
		return null;
	}
	
	@Override
	public void registerConnectorClass(String[] schema, EClass cls) {
		if (//Connector.class.isAssignableFrom(cls.getClass()) && 
				cls.getEAllSuperTypes().contains(ImportAdapterPackage.Literals.CONNECTOR) &&
				schema != null && schema.length > 0) {			
			synchronized(this) {
				this.entries.add(new RegEntry(schema, cls));				
			}
			
		}		
	}
	
	public boolean matchesSchema(String uri, RegEntry entry) {
		for (String scheme : entry.schema) {
			if (uri.startsWith(scheme)) {
				return true;
			}
		}
		return false;
	}
	
	static class RegEntry {
		String[] schema;
		EClass cls;
		
		RegEntry (String[] schema, EClass cls) {
			this.schema = schema.clone();
			this.cls = cls;
		}
	}

} //ImportAdapterFactoryImpl
