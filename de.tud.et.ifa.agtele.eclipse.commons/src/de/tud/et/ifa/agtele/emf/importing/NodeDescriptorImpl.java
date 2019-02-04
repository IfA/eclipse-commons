package de.tud.et.ifa.agtele.emf.importing;

public class NodeDescriptorImpl implements INodeDescriptor {

	protected String nsUri;
	protected String typeName;
	protected Object node;
	
	public NodeDescriptorImpl(Object node, String nsUri, String typeName) {
		this.nsUri = nsUri;
		this.typeName = typeName;
		this.node = node;
	}
	
	@Override
	public Object getNode() {
		return this.node;
	}
	@Override
	public String getNsUri() {
		return this.nsUri;
	}
	@Override
	public String getTypeName() {
		return this.typeName;
	}
}
