/**
 */
package de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.uaModelStorageClient;

import de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.importAdapter.Connector;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Agtele EMFUA Connector</b></em>'.
 * <!-- end-user-doc -->
 *
 *
 * @see de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.uaModelStorageClient.UAModelStorageClientPackage#getAgteleEMFUAConnector()
 * @model
 * @generated
 */
public interface AgteleEMFUAConnector extends Connector {
	public static final String[] CONNECTION_SCHEMES = {"emf.opc.tcp"};
	public static final String INTERNAL_CONNECTION_SCHEMA = "opc.tcp";
	public default String[] getConnectionSchemes () {
		return AgteleEMFUAConnector.CONNECTION_SCHEMES;
	}
	public static interface GenericCallResult {
		public boolean isSuccessful();
		public long getErrorCode();
		public Object[] getResults();
		public Object[] getParams();
		String getErrorText();
		void copyTo(GenericCallResult target);
		boolean isError();
	}

	GenericCallResult invokeGenericMethod(Object genericServiceInvocationNode,
			Object genericServiceInvocationMethodNode, Object[] params);
} // AgteleEMFUAConnector
