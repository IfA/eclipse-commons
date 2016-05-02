package de.tud.et.ifa.agtele.emf;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.xsd.ecore.XSDEcoreBuilder;
import org.eclipse.xsd.impl.XSDSchemaImpl;

/**
 * This provides convenience methods that are related to {@link EPackage EPackages}.
 *
 */
public abstract class EPackageHelper {

	/**
	 * This tries to determine the ePackages defined in a meta-model (either Ecore or XSD). Therefore,
	 * the resource is loaded and the ePackages are extracted. If an XSD file is specified, corresponding
	 * EPackages are automatically generated via the {@link XSDEcoreBuilder}.
	 * 
	 * @param absolutePathToMetaModelFile the absolute path to the meta-model to be analyzed. This may point either to 
	 * an Ecore or to an XSD file.
	 * @param adaptResourceUri if true, the uri of the resource that contains the ePackage will be set to the
	 * namespace uri of the ePackage. That way, this namespace uri is used for references to the package during 
	 * serialization of resources, otherwise there will be file-based references 
	 * @param register if the packages shall be registered to the global ePackage registry.
	 * @return a map of nsUris and corresponding ePackages found in the Ecore/XSD meta-model, null if any error occurs.
	 */
	public static HashMap<String, EPackage> getEPackages(String absolutePathToMetaModelFile, boolean adaptResourceUri, boolean register) {

		HashMap<String, EPackage> ePackages = new HashMap<>();

		// try to load the resource
		Resource metamodel = null;

		try {
			metamodel = (new ResourceSetImpl()).getResource(
					URI.createFileURI(absolutePathToMetaModelFile), true);
		} catch (Exception e) {
			return null;
		}

		if(metamodel == null) {
			return null;
		}

		// get the contents of the resource
		EList<EObject> contents = metamodel.getContents();
		if(contents == null || contents.isEmpty()) {
			return null;
		}
		
		// if we deal with an XSD, we need to generate the corresponding EPackages on-the-fly
		if(!(contents.get(0) instanceof EPackage)) {
			if(!(contents.get(0) instanceof XSDSchemaImpl)) {
				return null;
			}
			
			XSDEcoreBuilder builder = new XSDEcoreBuilder();
			contents = new BasicEList<>(builder.generate(URI.createFileURI(absolutePathToMetaModelFile)));
			
			if(!(contents.get(0) instanceof EPackage)) {
				return null;
			}
		}

		// the root ePackages defined by the metamodel
		//
		List<EPackage> rootEPackages = (List<EPackage>) contents.stream().filter(e -> e instanceof EPackage).map(e -> (EPackage) e).
				collect(Collectors.toList());

		// the complete list of packages defined by the meta-model including all sub-packages
		//
		HashSet<EPackage> ePackageSet = collectEPackages(rootEPackages);

		for (EPackage ePackage : ePackageSet) {

			if(adaptResourceUri && ePackage.eResource() != null) {
				// adapt the uri of the resource
				ePackage.eResource().setURI(URI.createURI(ePackage.getNsURI()));				
			}

			ePackages.put(ePackage.getNsURI(), ePackage);

			if(register) {
				// register the packages to the global package registry
				EPackage.Registry.INSTANCE.put(ePackage.getNsURI(), ePackage);
			}
		}

		return ePackages;
	}

	/**
	 * Recursively collects the sub-packages of an ePackage and returns them as a 
	 * set (including the root package itself).
	 *  
	 * @param ePackage The root ePackage.
	 * @return A set of sub-ePackages including the root ePackage itself.
	 */
	public static HashSet<EPackage> collectEPackages(EPackage ePackage) {
		HashSet<EPackage> ePackages = new HashSet<>();
		ePackages.add(ePackage);
		for (EPackage child : ePackage.getESubpackages()) {
			ePackages.addAll(collectEPackages(child));
		}
		return ePackages;
	}
	
	/**
	 * Recursively collects the sub-packages of a list of ePackages and returns them as a 
	 * set (including the root packages themselves).
	 *  
	 * @param ePackages The list of root ePackages.
	 * @return A set of sub-ePackages including the root ePackages themselves.
	 */
	public static HashSet<EPackage> collectEPackages(Collection<EPackage> ePackages) {
		HashSet<EPackage> ret = new HashSet<>();
		
		for (EPackage ePackage : ePackages) {
			ret.addAll(collectEPackages(ePackage));
		}
		
		return ret;
	}

}
