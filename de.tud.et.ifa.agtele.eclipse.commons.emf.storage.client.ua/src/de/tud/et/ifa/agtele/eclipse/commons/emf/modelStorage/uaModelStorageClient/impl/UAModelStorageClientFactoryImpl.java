/**
 */
package de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.uaModelStorageClient.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.uaModelStorageClient.*;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class UAModelStorageClientFactoryImpl extends EFactoryImpl implements UAModelStorageClientFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static UAModelStorageClientFactory init() {
		try {
			UAModelStorageClientFactory theUAModelStorageClientFactory = (UAModelStorageClientFactory)EPackage.Registry.INSTANCE.getEFactory(UAModelStorageClientPackage.eNS_URI);
			if (theUAModelStorageClientFactory != null) {
				return theUAModelStorageClientFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new UAModelStorageClientFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public UAModelStorageClientFactoryImpl() {
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
			case UAModelStorageClientPackage.AGTELE_EMFUA_CONNECTOR: return createAgteleEMFUAConnector();
			case UAModelStorageClientPackage.AGTELE_EMFUA_IMPORT_ADAPTER: return createAgteleEMFUAImportAdapter();
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
	public AgteleEMFUAConnector createAgteleEMFUAConnector() {
		AgteleEMFUAConnectorImpl agteleEMFUAConnector = new AgteleEMFUAConnectorImpl();
		return agteleEMFUAConnector;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public AgteleEMFUAImportAdapter createAgteleEMFUAImportAdapter() {
		AgteleEMFUAImportAdapterImpl agteleEMFUAImportAdapter = new AgteleEMFUAImportAdapterImpl();
		return agteleEMFUAImportAdapter;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public UAModelStorageClientPackage getUAModelStorageClientPackage() {
		return (UAModelStorageClientPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static UAModelStorageClientPackage getPackage() {
		return UAModelStorageClientPackage.eINSTANCE;
	}

} //UAModelStorageClientFactoryImpl
