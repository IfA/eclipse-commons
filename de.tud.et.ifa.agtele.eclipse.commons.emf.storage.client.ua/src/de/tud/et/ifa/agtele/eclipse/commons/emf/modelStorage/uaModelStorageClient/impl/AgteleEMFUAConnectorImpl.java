/**
 */
package de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.uaModelStorageClient.impl;

import de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.importAdapter.ImportAdapter;
import de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.importAdapter.impl.ConnectorImpl;
import de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.uaModelStorageClient.AgteleEMFUAConnector;
import de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.uaModelStorageClient.UAModelStorageClientFactory;
import de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage.uaModelStorageClient.UAModelStorageClientPackage;

import org.eclipse.milo.opcua.stack.core.UaException;
import de.tud.et.ifa.agtele.emf.importing.INodeDescriptor;
import de.tud.et.ifa.agtele.emf.importing.NodeDescriptorImpl;

import static org.eclipse.milo.opcua.stack.core.types.builtin.unsigned.Unsigned.uint;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.milo.opcua.sdk.client.AddressSpace;
import org.eclipse.milo.opcua.sdk.client.OpcUaClient;
import org.eclipse.milo.opcua.sdk.client.api.ServiceFaultListener;
import org.eclipse.milo.opcua.sdk.client.api.UaClient;
import org.eclipse.milo.opcua.sdk.client.api.config.OpcUaClientConfig;
import org.eclipse.milo.opcua.sdk.client.api.identity.AnonymousProvider;
import org.eclipse.milo.opcua.sdk.client.api.identity.IdentityProvider;
import org.eclipse.milo.opcua.sdk.client.nodes.UaNode;
import org.eclipse.milo.opcua.sdk.client.nodes.UaVariableNode;
import org.eclipse.milo.opcua.stack.client.DiscoveryClient;
import org.eclipse.milo.opcua.stack.client.UaStackClient;
import org.eclipse.milo.opcua.stack.core.AttributeId;
import org.eclipse.milo.opcua.stack.core.Identifiers;
import org.eclipse.milo.opcua.stack.core.security.SecurityPolicy;
import org.eclipse.milo.opcua.stack.core.types.builtin.ByteString;
import org.eclipse.milo.opcua.stack.core.types.builtin.DataValue;
import org.eclipse.milo.opcua.stack.core.types.builtin.ExpandedNodeId;
import org.eclipse.milo.opcua.stack.core.types.builtin.LocalizedText;
import org.eclipse.milo.opcua.stack.core.types.builtin.NodeId;
import org.eclipse.milo.opcua.stack.core.types.builtin.QualifiedName;
import org.eclipse.milo.opcua.stack.core.types.builtin.unsigned.UInteger;
import org.eclipse.milo.opcua.stack.core.types.enumerated.BrowseDirection;
import org.eclipse.milo.opcua.stack.core.types.enumerated.NodeClass;
import org.eclipse.milo.opcua.stack.core.types.enumerated.TimestampsToReturn;
import org.eclipse.milo.opcua.stack.core.types.structured.BrowseDescription;
import org.eclipse.milo.opcua.stack.core.types.structured.EndpointDescription;
import org.eclipse.milo.opcua.stack.core.types.structured.Node;
import org.eclipse.milo.opcua.stack.core.types.structured.ReferenceDescription;
import org.eclipse.milo.opcua.stack.core.types.structured.ServiceFault;
import org.eclipse.milo.opcua.stack.core.types.structured.VariableNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Agtele EMFUA Connector</b></em>'.
 * <!-- end-user-doc -->
 *
 * @generated
 */
public class AgteleEMFUAConnectorImpl extends ConnectorImpl implements AgteleEMFUAConnector {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AgteleEMFUAConnectorImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return UAModelStorageClientPackage.Literals.AGTELE_EMFUA_CONNECTOR;
	}
	
	protected boolean connected = false;
	
	protected String getInternalConnectionUri () {
		String uri = this.getConnectionUri();
		if (uri == null || uri.isEmpty()) {
			return null;
		}
		
		String [] schemes = this.getConnectionSchemes();
		
		for (int i = 0; i < schemes.length; i+= 1) {
			if (uri.startsWith(schemes[i] + "://")) {
				uri = uri.replace(schemes[i] + "://", AgteleEMFUAConnector.INTERNAL_CONNECTION_SCHEMA + "://");
				break;
			}
		}
		
		return uri;
	}

	protected OpcUaClient client = null;
	protected UaClient uaClient = null;
	/**
	 * Completable future holding the opc ua client
	 */
	protected CompletableFuture<UaClient> connFuture = null;
	
	protected Logger logger = null;
	protected AddressSpace addressSpace;
	protected UaNode rootNode;
	protected String[] namespaces;
	protected UaVariableNode namespacesNode;
	protected UaNode objectsNode;
	
	public void connect() {
		if (logger == null) {
			this.logger = LoggerFactory.getLogger(getClass());
		}
		if (!connected && this.getInternalConnectionUri() != null) {
			try {
				this.client = this.createClient(this.getInternalConnectionUri());
			} catch (Exception e) {
				e.printStackTrace();
				return;
			}
						
			this.client.addFaultListener(new ServiceFaultListener() {				
				@Override
				public void onServiceFault(ServiceFault serviceFault) {
					logger.error(serviceFault.toString());
				}
			});
			
			this.connFuture = this.client.connect();
			try {
				this.uaClient = this.connFuture.get(); //connect synchronously
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
				return;
			}
			
			connected = true;
			
			try {
				this.initializeClient();
			} catch (Exception e) {
				e.printStackTrace();
				this.disconnect();
			}			
		}		
	}
	
	protected static interface Nodes {
		NodeId ROOT = new NodeId(0, 84);
		NodeId OBJECTS_FOLDER = new NodeId(0, 85);
		NodeId TYPES_FOLDER = new NodeId(0, 86);
		NodeId VIEWS_FOLDER = new NodeId(0, 87);
		NodeId SERVER_FOLDER = new NodeId(0, 2253);
		NodeId NAMESPACES_ARRAY = new NodeId(0, 2255);
		
	}
	
	protected void initializeClient () throws UaException {
		this.addressSpace = this.client.getAddressSpace();
		this.rootNode = this.addressSpace.browseNodes(Nodes.ROOT).get(0);
		this.objectsNode = addressSpace.browseNodes(Nodes.OBJECTS_FOLDER).get(0);
		this.namespacesNode = addressSpace.getVariableNode(Nodes.NAMESPACES_ARRAY);
		this.namespaces = (String[]) namespacesNode.getValue().getValue().getValue();
	}

	public void disconnect() {		
		if (connected) {
			connected=false;
			try {
				this.client.disconnect().get();
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
		}		
	}

	public boolean isConnected() {
		return this.connected;
	}

	
	
	public ImportAdapter createImportAdapter() {
		this.adapter = UAModelStorageClientFactory.eINSTANCE.createAgteleEMFUAImportAdapter();
		this.adapter.setConnector(this);
		return this.adapter;
	}
	
	public static <T> Collection<T> toCollection(T[] array) {
		ArrayList<T> result = new ArrayList<>();
		for (int i = 0; i < array.length; i += 1) {
			result.add(array[i]);
		}
		return result;
	}
	public static NodeId getNodeId(Object o) {
		if (o instanceof UaNode) {
			return ((UaNode)o).getNodeId();
		} else if (o instanceof NodeId) {
			return (NodeId) o;
		} else if (o instanceof ExpandedNodeId) {
			ExpandedNodeId eId = (ExpandedNodeId)o;
			switch (eId.getType()) {
			case Guid:
				return new NodeId(eId.getNamespaceIndex(), (UUID)eId.getIdentifier());
			case Numeric:
				return new NodeId(eId.getNamespaceIndex(), (UInteger)eId.getIdentifier());
			case Opaque:
				return new NodeId(eId.getNamespaceIndex(), (ByteString)eId.getIdentifier());
			case String:
				return new NodeId(eId.getNamespaceIndex(), (String)eId.getIdentifier());
			}
			return new NodeId(((ExpandedNodeId)o).getNamespaceIndex(), ((ExpandedNodeId)o).getIdentifier().toString());
		}
		return null;
	}
	
	public static NodeId getNodeIdFromReference(ReferenceDescription ref) {
		return getNodeId(ref.getNodeId());//NodeId.parse((ref.getNodeId().toParseableString()));
	}
	public static Collection<NodeId> getNodeIdsFromReferences(Collection<ReferenceDescription> refs) {
		ArrayList<NodeId> result = new ArrayList<>();
		for (ReferenceDescription ref : refs) {
			result.add(getNodeIdFromReference(ref));
		}
		return result;
	}

	@Override
	public boolean isValidRootSearchNode(Object node) {
		return !( node.equals(Nodes.TYPES_FOLDER) || node.equals(Nodes.VIEWS_FOLDER) || node.equals(Nodes.SERVER_FOLDER));
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Collection<Object> browse(Object node) {
		if (!this.connected) {
			return null;
		}
		try {
			BrowseDescription browse;
			if (node != null) {
				NodeId id = getNodeId(node);
				
				browse = new BrowseDescription(
						id, 
						BrowseDirection.Forward, 
						Identifiers.HierarchicalReferences, 
						true, 
						UInteger.valueOf(1), 
						UInteger.valueOf(63)
					);	
			} else {				
				browse = new BrowseDescription(
						Identifiers.RootFolder, 
						BrowseDirection.Forward, 
						Identifiers.HierarchicalReferences, 
						true, 
						UInteger.valueOf(1), 
						UInteger.valueOf(63)
					);	
			}
			Collection<ReferenceDescription> references = toCollection(this.client.browse(browse).get().getReferences());
			
			return (Collection)getNodeIdsFromReferences(references);
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
			this.connected = false;
		}
		return null;
	}

	public INodeDescriptor getTypeInfo(Object node) {
		NodeId id = getNodeId(node);
		if (id == null) {
			return null;
		}
		if (!this.connected) {
			return null;
		}
		BrowseDescription browse = new BrowseDescription(
				id, 
				BrowseDirection.Both, 
				Identifiers.HasTypeDefinition, 
				true, 
				UInteger.valueOf(NodeClass.ObjectType.getValue()), 
				UInteger.valueOf(63) //all data 
			);	
		try {
			Collection<ReferenceDescription> references = toCollection(this.client.browse(browse).get().getReferences());

			for (ReferenceDescription ref : references) {
				NodeId typeId = getNodeId(ref.getNodeId());
				long nameSpaceIndex = typeId.getNamespaceIndex().longValue();
				
				
				if (nameSpaceIndex > 0) {
					String typeNameSpaceUri = this.namespaces[(int) nameSpaceIndex];
					String typeName = typeNameSpaceUri.substring(typeNameSpaceUri.lastIndexOf("/")+1);
					String nameSpaceUri = typeNameSpaceUri.substring(0, typeNameSpaceUri.lastIndexOf("/"));
					
					EPackage pkg = null;
					pkg = EPackage.Registry.INSTANCE.getEPackage(nameSpaceUri);
					
					if (pkg != null && pkg.getEClassifier(typeName) != null && pkg.getEClassifier(typeName) instanceof EClass) {
						return new NodeDescriptorImpl(node, nameSpaceUri, typeName);
					}
				}
				
			}
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
			this.connected = false;
		}
		return null;
	}
	
	protected Map<Object, String> referenceNameCache = new HashMap<>();
		
	public boolean referenceMatches(ReferenceDescription description, EReference ref) {
		if (!this.connected) {
			return false;
		}
		String refNSUri = ref.getEContainingClass().getEPackage().getNsURI() + "/" + ref.getEContainingClass().getName();
		if (AgteleEMFUAConnectorImpl.this.namespaces[(int)description.getReferenceTypeId().getNamespaceIndex().longValue()].equals(refNSUri)) {
				NodeId id = getNodeId(description.getReferenceTypeId());
				
				ArrayList<NodeId> ids = new ArrayList<>();
				ids.add(id);
				ArrayList<UInteger> aIds = new ArrayList<>();
				aIds.add(UInteger.valueOf(AttributeId.BrowseName.id()));
				String uaReferenceName = this.referenceNameCache.get(id);
				if (uaReferenceName == null) {
					try {
						DataValue value = client.read(Integer.MAX_VALUE, TimestampsToReturn.Neither, ids, aIds).get().get(0);
						uaReferenceName = ((QualifiedName)value.getValue().getValue()).getName();
						uaReferenceName = uaReferenceName.substring(0, 1).toLowerCase() + uaReferenceName.substring(1);
						referenceNameCache.put(id, uaReferenceName);
					} catch (InterruptedException | ExecutionException e) {
						e.printStackTrace();
						this.connected = false;
						return false;
					}
					
				}
				return ref.getName().equals(uaReferenceName) ||  (ref.getName()+"Ref").equals(uaReferenceName);
			};
			return false;
	}
	
	public Collection<ReferenceDescription> filterReferenceDescriptions(Collection<ReferenceDescription> refs, EReference ref) {		
		return refs.parallelStream().filter(d -> {
			return referenceMatches(d, ref);
		}).collect(Collectors.toList());
	}

	protected Map<Object, Collection<ReferenceDescription>> referenceCache = new HashMap<>();
	
	public Collection<Object> readReference(Object node, EReference ref) {
		NodeId id = getNodeId(node);
		if (id == null) {
			return Collections.emptyList();
		}
		if (!this.connected) {
			return Collections.emptyList();
		}
		
		Collection<ReferenceDescription> references = this.referenceCache.get(id);
		if (references == null) {
			BrowseDescription browse = new BrowseDescription(
					id, 
					BrowseDirection.Forward, 
					Identifiers.References, 
					true, 
					UInteger.valueOf(NodeClass.Object.getValue()
							+ NodeClass.Variable.getValue()
					), 
					UInteger.valueOf(63) //all data 
				);	
			try {
				references = toCollection(this.client.browse(browse).get().getReferences());					
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
				this.connected = false;
				return Collections.emptyList();
			}			
			this.referenceCache.put(id, references);
		}			
		references = filterReferenceDescriptions(references, ref);
		ArrayList<Object> result = new ArrayList<>();				
		for (ReferenceDescription reference : references) {
			result.add(getNodeId(reference.getNodeId()));
		}	
		return result;	
	}
	
	public boolean variableMatches(ReferenceDescription description, EAttribute attr) {
		return description.getBrowseName().getName().equals(attr.getName());
	}
	
	public Collection<ReferenceDescription> filterVariableReferenceDescriptions(Collection<ReferenceDescription> refs, EAttribute attr) {		
		return refs.parallelStream().filter(d -> {
			return variableMatches(d, attr);
		}).collect(Collectors.toList());
	}

	protected Map<Object, Collection<ReferenceDescription>> variableCache = new HashMap<>();

	protected Map<Object, Map<Object, DataValue>> variableDataCache = new HashMap<>();
	
	protected Map<Object, DataValue> readObjectAttributes (Object n) {
		NodeId id = this.getNodeId(n);
		Map<Object, DataValue> attributeValues = new HashMap<>();
		if (!this.connected) {
			return attributeValues;
		}
		variableDataCache.put(n, attributeValues);
		
		ArrayList<NodeId> ids = new ArrayList<>();
		ids.add(id);
		ids.add(id);
		ids.add(id);
		ids.add(id);
		ArrayList<UInteger> aIds = new ArrayList<>();
		aIds.add(UInteger.valueOf(AttributeId.BrowseName.id()));
		aIds.add(UInteger.valueOf(AttributeId.Description.id()));
		aIds.add(UInteger.valueOf(AttributeId.NodeClass.id()));
		aIds.add(UInteger.valueOf(AttributeId.DisplayName.id()));

		try {
			List<DataValue> values = client.read(Integer.MAX_VALUE, TimestampsToReturn.Neither, ids, aIds).get();
			attributeValues.put(AttributeId.BrowseName, values.get(0));
			attributeValues.put(AttributeId.Description, values.get(1));
			attributeValues.put(AttributeId.NodeClass, values.get(2));
			attributeValues.put(AttributeId.DisplayName, values.get(3));
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
			this.connected = false;
		}		
		return attributeValues;
	}
	
	protected Map<Object, DataValue> readVariableNode (Object n) {
		NodeId id = (NodeId) n;
		Map<Object, DataValue> attributeValues = this.variableDataCache.get(id);
		if (!this.connected) {
			return attributeValues;
		}
		if (attributeValues == null) {
			attributeValues = new HashMap<>();
			variableDataCache.put(id, attributeValues);
			
			ArrayList<NodeId> ids = new ArrayList<>();
			ids.add(id);
			ids.add(id);
			ids.add(id);
			ids.add(id);
			ids.add(id);
			ids.add(id);
			ArrayList<UInteger> aIds = new ArrayList<>();
			aIds.add(UInteger.valueOf(AttributeId.BrowseName.id()));
			aIds.add(UInteger.valueOf(AttributeId.Value.id()));
			aIds.add(UInteger.valueOf(AttributeId.DataType.id()));
			aIds.add(UInteger.valueOf(AttributeId.ValueRank.id()));
			aIds.add(UInteger.valueOf(AttributeId.ArrayDimensions.id()));
			aIds.add(UInteger.valueOf(AttributeId.Description.id()));

			try {
				List<DataValue> values = client.read(Integer.MAX_VALUE, TimestampsToReturn.Neither, ids, aIds).get();
				attributeValues.put(AttributeId.BrowseName, values.get(0));
				attributeValues.put(AttributeId.Value, values.get(1));
				attributeValues.put(AttributeId.DataType, values.get(2));
				attributeValues.put(AttributeId.ValueRank, values.get(3));
				attributeValues.put(AttributeId.ArrayDimensions, values.get(4));
				attributeValues.put(AttributeId.Description, values.get(5));
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
				this.connected = false;
			}		
		}
		return attributeValues;
	}
	
	protected Object readDataValue(DataValue dataValue) {
		Object result =  ((DataValue)dataValue).getValue().getValue();
		if (result instanceof QualifiedName) {
			return ((QualifiedName)result).getName();
		}
		return result;
	}
	
	protected DataValue readVariableNodeValue(NodeId id) {
		return this.readVariableNode(id).get(AttributeId.Value);
	}
	
	public Collection<Object> readAttribute(Object node, EAttribute attribute) {
		if (node == null || !(node instanceof NodeId)) {
			return Collections.emptyList();
		}
		if (!this.connected) {
			return Collections.emptyList();
		}
		NodeId id = (NodeId)node;

		Collection<ReferenceDescription> variables = this.variableCache.get(id);
		
		if (variables == null) {
			BrowseDescription browse = new BrowseDescription(
					id, 
					BrowseDirection.Forward, 
					Identifiers.HasComponent, 
					true, 
					UInteger.valueOf(NodeClass.Variable.getValue()), 
					UInteger.valueOf(63) //all data 
				);	
			try {
				variables = toCollection(this.client.browse(browse).get().getReferences());					
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
				this.connected = false;
				return Collections.emptyList();
			}			
			this.variableCache.put(id, variables);			
		}
		variables = this.filterVariableReferenceDescriptions(variables, attribute);
		ReferenceDescription variableRef = null;
		
		for (ReferenceDescription d : variables) {
			variableRef = d;
			break;
		}
		
		if (variableRef != null) {
			DataValue dataValue = this.readVariableNodeValue(getNodeId(variableRef.getNodeId()));
			
			if (dataValue != null) {
				Object value = dataValue.getValue().getValue();
				
				if (value instanceof Array) {
					return toCollection((Object[])value);
				} else {
					ArrayList<Object> result = new ArrayList<>();
					result.add(value);
					return result;
				}	
			}		
		}	
		return Collections.emptyList();
	}
	
	protected SecurityPolicy getSecurityPolicy() {
        return SecurityPolicy.None;
    }

    protected IdentityProvider getIdentityProvider() {
        return new AnonymousProvider();
    }
    
    public OpcUaClient createClient(String address) throws Exception {    	
        SecurityPolicy securityPolicy = this.getSecurityPolicy();

        IdentityProvider idProv = this.getIdentityProvider();
        
        // get available endpoint
        List<EndpointDescription> endpoints =
                DiscoveryClient.getEndpoints(address).get();

        EndpointDescription endpoint = endpoints.stream()
    		  .filter(e -> e.getSecurityPolicyUri().equals(securityPolicy.getUri()))
    		  .findFirst().orElseThrow(() -> new Exception("no desired endpoints returned"));
      
        EndpointDescription newEp = new EndpointDescription(
      		address,
      		endpoint.getServer(),
      		endpoint.getServerCertificate(),
      		endpoint.getSecurityMode(),
      		endpoint.getSecurityPolicyUri(),
      		endpoint.getUserIdentityTokens(),
      		endpoint.getTransportProfileUri(),
      		endpoint.getSecurityLevel()
		);
      
        // create client config
	    OpcUaClientConfig config = OpcUaClientConfig.builder()
	            .setApplicationName(LocalizedText.english("agtele emf ua connector"))
	            .setApplicationUri("urn:de:tu-dresden:ifa:agtele:eclipse:commons")
	            .setEndpoint(newEp)
	            .setIdentityProvider(idProv)
	            .setRequestTimeout(uint(20000))
                .setSessionTimeout(UInteger.valueOf(1000l * 60l * 50l))
	            .build();
      
      	return OpcUaClient.create(config);
    }

} //AgteleEMFUAConnectorImpl
