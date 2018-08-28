package de.tud.et.ifa.agtele.emf;

import org.eclipse.emf.codegen.ecore.genmodel.GenFeature;
import org.eclipse.emf.codegen.ecore.genmodel.GenModelPackage;
import org.eclipse.emf.codegen.ecore.genmodel.impl.GenFeatureImpl;
import org.eclipse.emf.ecore.EAnnotation;
/**
 * This class is a helper, that can be used in the emf generator template overrides.
 * Add the following string to the 'Template Plug-in Variables' property of your genmodel 
 * in order to activate this feature: 'AGTELE_EMF_COMMONS=de.tud.et.ifa.agtele.eclipse.commons'.
 * 
 * @author Baron
 */
public interface AgtleEcoreGeneratorHelper {
	
	/**
	 * Uses a hack in order to deliver the content of the genmodel annotation in the correct 
	 * indentation level and providing the appropriate imports within the generated code
	 * @param genFeature the genFeature that is currently generated
	 * @param indentation the indentation string that shall be prepended in every line
	 * @param keyName the name of the details key of the genmodel annotation
	 * @return
	 */
	public static String getImplementationBody(GenFeature genFeature, String indentation, String keyName) {		
		try {
			if (genFeature == null) {
				return null;
			}
			EAnnotation annotation = genFeature.getEcoreModelElement().getEAnnotation(GenModelPackage.eNS_URI);
			if (annotation == null || !annotation.getDetails().containsKey(keyName)) {
				return null;
			}
			boolean isSetGet = AgtleEcoreGeneratorHelper.isSetGetSwitch(genFeature);
			String oldGetValue = AgtleEcoreGeneratorHelper.getGetSwitch(genFeature);
			String annotationValue = annotation.getDetails().get(keyName);
			if (isSetGet) {
				AgtleEcoreGeneratorHelper.unsetGetSwitch(genFeature);	
			}
			annotation.getDetails().put("get", annotationValue);
			
			String result = genFeature.getGetterBody(indentation);
			AgtleEcoreGeneratorHelper.unsetGetSwitch(genFeature);	
			annotation.getDetails().put("get", oldGetValue);
			
			return result;
		} catch (Exception e) {
			return "/* an exception occurred during the evaluation of the annotation that overrides this implementation: '" + e.getMessage() + "'";
		}
	}
	
	/**
	 * Returns the setter body override, use the following line in order to make use of this function within the generator template:
	 * 
	 * 'de.tud.et.ifa.agtele.emf.AgtleEcoreGeneratorHelper.getSetterBody(genFeature,genModel.getIndentation(stringBuffer))'
	 * 
	 * @param feature
	 * @param indentation
	 * @return
	 */
	public static String getSetterBody (GenFeature feature, String indentation) {
		return getImplementationBody(feature, indentation, "set");
	}
	
	/**
	 * Returns the basic setter body override, use the following line in order to make use of this function within the generator template:
	 * 
	 * 'de.tud.et.ifa.agtele.emf.AgtleEcoreGeneratorHelper.getBasicSetterBody(genFeature,genModel.getIndentation(stringBuffer))'
	 * 
	 * @param feature
	 * @param indentation
	 * @return
	 */
	public static String getBasicSetterBody (GenFeature feature, String indentation) {
		return getImplementationBody(feature, indentation, "basicSet");
	}

	/**
	 * Returns the basic getter body override, use the following line in order to make use of this function within the generator template:
	 * 
	 * 'de.tud.et.ifa.agtele.emf.AgtleEcoreGeneratorHelper.getBasicGetterBody(genFeature,genModel.getIndentation(stringBuffer))'
	 * 
	 * @param feature
	 * @param indentation
	 * @return
	 */
	public static String getBasicGetterBody (GenFeature feature, String indentation) {
		return getImplementationBody(feature, indentation, "basicGet");
	}

	/**
	 * Returns the is set override, use the following line in order to make use of this function within the generator template:
	 * 
	 * 'de.tud.et.ifa.agtele.emf.AgtleEcoreGeneratorHelper.getIsSetBody(genFeature,genModel.getIndentation(stringBuffer))'
	 * 
	 * @param feature
	 * @param indentation
	 * @return
	 */
	public static String getIsSetBody (GenFeature feature, String indentation) {
		return getImplementationBody(feature, indentation, "isSet");
	}

	/**
	 * Returns the property descriptor override, use the following line in order to make use of this function within the generator template:
	 * 
	 * 'de.tud.et.ifa.agtele.emf.AgtleEcoreGeneratorHelper.getPropertyDescriptorBody(genFeature,genModel.getIndentation(stringBuffer))'
	 * 
	 * @param feature
	 * @param indentation
	 * @return
	 */
	public static String getPropertyDescriptorBody (GenFeature feature, String indentation) {
		return getImplementationBody(feature, indentation, "propertyDescriptor");
	}

	/**
	 * Returns the field init override, use the following line in order to make use of this function within the generator template:
	 * 
	 * 'de.tud.et.ifa.agtele.emf.AgtleEcoreGeneratorHelper.getFieldInitBody(genFeature,genModel.getIndentation(stringBuffer))'
	 * 
	 * @param feature
	 * @param indentation
	 * @return
	 */
	public static String getFieldInitBody (GenFeature feature, String indentation) {
		return getImplementationBody(feature, indentation, "init");
	}
	
	/**
	 * Checks if the feature specified provides a details entry within the genmodel annotation with the key name specified 
	 * @param genFeature
	 * @param keyName
	 * @return
	 */
	public static boolean hasGenAnnotationDetailsEntry (GenFeature genFeature, String keyName) {	
		if (genFeature == null) {
			return false;
		}
		EAnnotation annotation = genFeature.getEcoreModelElement().getEAnnotation(GenModelPackage.eNS_URI);
		return annotation != null && annotation.getDetails().containsKey(keyName);		
	}
	/**
	 * checks if the setter body override has been modeled
	 * @param feature
	 * @return
	 */
	public static boolean hasSetterOverride(GenFeature feature) {
		return AgtleEcoreGeneratorHelper.hasGenAnnotationDetailsEntry(feature, "set");
	}

	/**
	 * checks if the getter body override has been modeled
	 * @param feature
	 * @return
	 */
	public static boolean hasGetterOverride(GenFeature feature) {
		return AgtleEcoreGeneratorHelper.hasGenAnnotationDetailsEntry(feature, "get");
	}

	/**
	 * checks if the basic getter body override has been modeled
	 * @param feature
	 * @return
	 */
	public static boolean hasBasicGetterOverride(GenFeature feature) {
		return AgtleEcoreGeneratorHelper.hasGenAnnotationDetailsEntry(feature, "basicGet");
	}

	/**
	 * checks if the basic setter body override has been modeled
	 * @param feature
	 * @return
	 */
	public static boolean hasBasicSetterOverride(GenFeature feature) {
		return AgtleEcoreGeneratorHelper.hasGenAnnotationDetailsEntry(feature, "basicSet");
	}

	/**
	 * checks if the is set override has been modeled
	 * @param feature
	 * @return
	 */
	public static boolean hasIsSetOverride(GenFeature feature) {
		return AgtleEcoreGeneratorHelper.hasGenAnnotationDetailsEntry(feature, "isSet");
	}

	/**
	 * checks if the property descriptor override has been modeled
	 * @param feature
	 * @return
	 */
	public static boolean hasPropertyDescriptorOverride(GenFeature feature) {
		return AgtleEcoreGeneratorHelper.hasGenAnnotationDetailsEntry(feature, "propertyDescriptor");
	}

	/**
	 * checks if the field init override has been modeled
	 * @param feature
	 * @return
	 */
	public static boolean hasFieldInitOverride(GenFeature feature) {
		return AgtleEcoreGeneratorHelper.hasGenAnnotationDetailsEntry(feature, "init");
	}
	
	/**
	 * Delegates to the isSetGet method of subclasses of {@link GenFeature}, e.g. {@link GenFeatureImpl}
	 * @param genFeature
	 * @return
	 * @throws Exception
	 */
	public static boolean isSetGetSwitch (GenFeature genFeature) throws Exception {
		if (genFeature instanceof GenFeatureImpl) {
			return ((GenFeatureImpl) genFeature).isSetGet();
		}
		throw new Exception("Not implemented");		
	}
	/**
	 * 
	 * @param genFeature
	 * @return
	 * @throws Exception
	 */
	public static String getGetSwitch (GenFeature genFeature) throws Exception {
		if (genFeature instanceof GenFeatureImpl) {
			return ((GenFeatureImpl) genFeature).getGet();
		}
		throw new Exception("Not implemented");		
	}
	/**
	 * Delegates to the setGet method of subclasses of {@link GenFeature}, e.g. {@link GenFeatureImpl}
	 * @param genFeature
	 * @param val
	 * @throws Exception
	 */
	public static void setGetSwitch (GenFeature genFeature, String val) throws Exception {
		if (genFeature instanceof GenFeatureImpl) {
			((GenFeatureImpl) genFeature).setGet(val);
			return;
		}
		throw new Exception("Not implemented");		
	}
	/**
	 * Delegates to the unsetGet method of subclasses of {@link GenFeature}, e.g. {@link GenFeatureImpl}
	 * @param genFeature
	 * @throws Exception
	 */
	public static void unsetGetSwitch (GenFeature genFeature) throws Exception {
		if (genFeature instanceof GenFeatureImpl) {
			((GenFeatureImpl) genFeature).unsetGet();
			return;
		}
		throw new Exception("Not implemented");		
	}
}
