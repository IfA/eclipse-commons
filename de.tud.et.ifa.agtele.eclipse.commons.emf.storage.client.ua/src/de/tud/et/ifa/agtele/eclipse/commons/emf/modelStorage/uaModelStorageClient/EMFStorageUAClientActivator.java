package de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.uaModelStorageClient;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.importAdapter.ImportAdapterFactory;
import de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.uaModelStorageClient.impl.AgteleEMFUAConnectorImpl;


public class EMFStorageUAClientActivator implements BundleActivator {

	private static BundleContext context;

	static BundleContext getContext() {
		return context;
	}
	
	public void start(BundleContext context) throws Exception {
		EMFStorageUAClientActivator.context = context;
		
		//ImportAdapterFactory.eINSTANCE.registerConnectorClass(AgteleEMFUAConnector.CONNECTION_SCHEMES, UAModelStorageClientPackage.Literals.AGTELE_EMFUA_CONNECTOR);
	}

	public void stop(BundleContext context) throws Exception {
		EMFStorageUAClientActivator.context = null;		
	}

}
