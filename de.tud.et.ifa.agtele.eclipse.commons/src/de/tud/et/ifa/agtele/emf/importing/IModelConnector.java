package de.tud.et.ifa.agtele.emf.importing;

import java.util.Collection;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EReference;

public interface IModelConnector {

	void connect();
	
	void disconnect();
	
	boolean isConnected();
	
	IModelImporter getImportAdapter();
	
	Collection<Object> browse (Object node);
	
	INodeDescriptor getTypeInfo(Object node);
	
	Collection<Object> readReference(Object node, EReference ref);
	
	Collection<Object> readAttribute(Object node, EAttribute attribute);
	
	default boolean isValidRootSearchNode(Object node) {
		return true;
	};
}
