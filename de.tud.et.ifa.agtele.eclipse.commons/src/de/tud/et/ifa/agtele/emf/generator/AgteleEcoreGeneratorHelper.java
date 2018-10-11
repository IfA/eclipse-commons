package de.tud.et.ifa.agtele.emf.generator;

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
public interface AgteleEcoreGeneratorHelper {
	
	public static final String AGTELE_ECORE_GENERATOR_NS_URI = "http://et.tu-dresden.de/ifa/emf/commons/2018/AgTeleGenModel",
			SET_KEY = "set",
			GET_KEY = "get",
			BASIC_GET_KEY = "basicGet",
			BASIC_SET_KEY = "basicSet",
			BODY_KEY = "body",
			INIT_KEY = "init",
			PROPERTY_DESCRIPTOR_KEY = "propertyDescriptor",
			IS_SET_KEY = "isSet";
	
	/**
	 * Uses a hack in order to deliver the content of the agtele or emf genmodel annotation in the correct 
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
			EAnnotation generatorAnnotation = genFeature.getEcoreModelElement().getEAnnotation(GenModelPackage.eNS_URI),
					agteleGeneratorAnnotation = genFeature.getEcoreModelElement().getEAnnotation(AGTELE_ECORE_GENERATOR_NS_URI);
			String annotationValue = null;
			if (agteleGeneratorAnnotation == null || !agteleGeneratorAnnotation.getDetails().containsKey(keyName)) {				
				if (generatorAnnotation == null || !generatorAnnotation.getDetails().containsKey(keyName)) {
					return null;
				} else {
					annotationValue = generatorAnnotation.getDetails().get(keyName);
				}
			} else {
				annotationValue = agteleGeneratorAnnotation.getDetails().get(keyName);
			}
			boolean isSetGet = AgteleEcoreGeneratorHelper.isSetGetSwitch(genFeature);
			String oldGetValue = AgteleEcoreGeneratorHelper.getGetSwitch(genFeature);
			if (isSetGet) {
				AgteleEcoreGeneratorHelper.unsetGetSwitch(genFeature);	
			}
			
			AgteleEcoreGeneratorHelper.setGetSwitch(genFeature, annotationValue);
			
			String result = genFeature.getGetterBody(indentation);
			
			if (isSetGet) {
				AgteleEcoreGeneratorHelper.setGetSwitch(genFeature, oldGetValue);				
			} else {
				AgteleEcoreGeneratorHelper.unsetGetSwitch(genFeature);				
			}
				
			return result;
		} catch (Exception e) {
			return "/* an exception occurred during the evaluation of the annotation that overrides this implementation: '" + e.getMessage() + "'*/";
		}
	}
	
	/**
	 * Returns if the details key is an extended agtele generator annotation key.
	 * @param key
	 * @return
	 */
	public static boolean isExtendedAnnotationKey (String key) {
		if (key.equals(BASIC_GET_KEY) || 
				key.equals(BASIC_SET_KEY) || 
				key.equals(SET_KEY) || 
				key.equals(IS_SET_KEY) || 
				key.equals(INIT_KEY) || 
				key.equals(PROPERTY_DESCRIPTOR_KEY)) {
			return true;
		}
		return false;
	}
	
	/**
	 * Returns if the generator annotation details key specified is a method body key
	 * @param key
	 * @return
	 */
	public static boolean isMethodBodyKey(String key) {
		return AgteleEcoreGeneratorHelper.BODY_KEY.equals(key);
	}
	
	/**
	 * returns the annotation source namespace identifier for the type of details key specified
	 * @param key
	 * @return
	 */
	public static String getAnnotationNSUriForKeyType (String key) {
		if (AgteleEcoreGeneratorHelper.isMethodBodyKey(key)) {
			return GenModelPackage.eNS_URI;
		}
		return AGTELE_ECORE_GENERATOR_NS_URI;
	}
	
	/**
	 * Returns the setter body override, use the following line in order to make use of this function within the generator template:
	 * 
	 * 'de.tud.et.ifa.agtele.emf.AgteleEcoreGeneratorHelper.getSetterBody(genFeature,genModel.getIndentation(stringBuffer))'
	 * 
	 * @param feature
	 * @param indentation
	 * @return
	 */
	public static String getSetterBody (GenFeature feature, String indentation) {
		return getImplementationBody(feature, indentation, SET_KEY);
	}
	
	/**
	 * Returns the getter body override, use the following line in order to make use of this function within the generator template:
	 * 
	 * 'de.tud.et.ifa.agtele.emf.AgteleEcoreGeneratorHelper.getGetterBody(genFeature,genModel.getIndentation(stringBuffer))'
	 * 
	 * @param feature
	 * @param indentation
	 * @return
	 */
	public static String getGetterBody (GenFeature feature, String indentation) {
		return getImplementationBody(feature, indentation, GET_KEY);
	}
	
	/**
	 * Returns the method body override, use the following line in order to make use of this function within the generator template:
	 * 
	 * 'de.tud.et.ifa.agtele.emf.AgteleEcoreGeneratorHelper.getMethodBody(genFeature,genModel.getIndentation(stringBuffer))'
	 * 
	 * @param feature
	 * @param indentation
	 * @return
	 */
	public static String getMethodBody (GenFeature feature, String indentation) {
		return getImplementationBody(feature, indentation, BODY_KEY);
	}
	
	/**
	 * Returns the basic setter body override, use the following line in order to make use of this function within the generator template:
	 * 
	 * 'de.tud.et.ifa.agtele.emf.AgteleEcoreGeneratorHelper.getBasicSetterBody(genFeature,genModel.getIndentation(stringBuffer))'
	 * 
	 * @param feature
	 * @param indentation
	 * @return
	 */
	public static String getBasicSetterBody (GenFeature feature, String indentation) {
		return getImplementationBody(feature, indentation, BASIC_SET_KEY);
	}

	/**
	 * Returns the basic getter body override, use the following line in order to make use of this function within the generator template:
	 * 
	 * 'de.tud.et.ifa.agtele.emf.AgteleEcoreGeneratorHelper.getBasicGetterBody(genFeature,genModel.getIndentation(stringBuffer))'
	 * 
	 * @param feature
	 * @param indentation
	 * @return
	 */
	public static String getBasicGetterBody (GenFeature feature, String indentation) {
		return getImplementationBody(feature, indentation, BASIC_GET_KEY);
	}

	/**
	 * Returns the is set override, use the following line in order to make use of this function within the generator template:
	 * 
	 * 'de.tud.et.ifa.agtele.emf.AgteleEcoreGeneratorHelper.getIsSetBody(genFeature,genModel.getIndentation(stringBuffer))'
	 * 
	 * @param feature
	 * @param indentation
	 * @return
	 */
	public static String getIsSetBody (GenFeature feature, String indentation) {
		return getImplementationBody(feature, indentation, IS_SET_KEY);
	}

	/**
	 * Returns the property descriptor override, use the following line in order to make use of this function within the generator template:
	 * 
	 * 'de.tud.et.ifa.agtele.emf.AgteleEcoreGeneratorHelper.getPropertyDescriptorBody(genFeature,genModel.getIndentation(stringBuffer))'
	 * 
	 * @param feature
	 * @param indentation
	 * @return
	 */
	public static String getPropertyDescriptorBody (GenFeature feature, String indentation) {
		return getImplementationBody(feature, indentation, PROPERTY_DESCRIPTOR_KEY);
	}

	/**
	 * Returns the field init override, use the following line in order to make use of this function within the generator template:
	 * 
	 * 'de.tud.et.ifa.agtele.emf.AgteleEcoreGeneratorHelper.getFieldInitBody(genFeature,genModel.getIndentation(stringBuffer))'
	 * 
	 * @param feature
	 * @param indentation
	 * @return
	 */
	public static String getFieldInitBody (GenFeature feature, String indentation) {
		return getImplementationBody(feature, indentation, INIT_KEY);
	}
	
	/**
	 * Checks if the feature specified provides a details entry within the agtele genmodel annotation or the emf genmodel annotation with the key name specified 
	 * @param genFeature
	 * @param keyName
	 * @return
	 */
	public static boolean hasGenAnnotationDetailsEntry (GenFeature genFeature, String keyName) {	
		if (genFeature == null) {
			return false;
		}
		EAnnotation agteleAnnotation = genFeature.getEcoreModelElement().getEAnnotation(AGTELE_ECORE_GENERATOR_NS_URI);
		if (agteleAnnotation != null && agteleAnnotation.getDetails().containsKey(keyName)) {
			return true;
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
		return AgteleEcoreGeneratorHelper.hasGenAnnotationDetailsEntry(feature, SET_KEY);
	}
	
	/**
	 * checks if the getter body override has been modeled
	 * @param feature
	 * @return
	 */
	public static boolean hasGetterOverride(GenFeature feature) {
		return AgteleEcoreGeneratorHelper.hasGenAnnotationDetailsEntry(feature, GET_KEY);
	}

	/**
	 * checks if the method body override has been modeled
	 * @param feature
	 * @return
	 */
	public static boolean hasMethodBodyOverride(GenFeature feature) {
		return AgteleEcoreGeneratorHelper.hasGenAnnotationDetailsEntry(feature, BODY_KEY);
	}

	/**
	 * checks if the basic getter body override has been modeled
	 * @param feature
	 * @return
	 */
	public static boolean hasBasicGetterOverride(GenFeature feature) {
		return AgteleEcoreGeneratorHelper.hasGenAnnotationDetailsEntry(feature, BASIC_GET_KEY);
	}

	/**
	 * checks if the basic setter body override has been modeled
	 * @param feature
	 * @return
	 */
	public static boolean hasBasicSetterOverride(GenFeature feature) {
		return AgteleEcoreGeneratorHelper.hasGenAnnotationDetailsEntry(feature, BASIC_SET_KEY);
	}

	/**
	 * checks if the is set override has been modeled
	 * @param feature
	 * @return
	 */
	public static boolean hasIsSetOverride(GenFeature feature) {
		return AgteleEcoreGeneratorHelper.hasGenAnnotationDetailsEntry(feature, IS_SET_KEY);
	}

	/**
	 * checks if the property descriptor override has been modeled
	 * @param feature
	 * @return
	 */
	public static boolean hasPropertyDescriptorOverride(GenFeature feature) {
		return AgteleEcoreGeneratorHelper.hasGenAnnotationDetailsEntry(feature, PROPERTY_DESCRIPTOR_KEY);
	}

	/**
	 * checks if the field init override has been modeled
	 * @param feature
	 * @return
	 */
	public static boolean hasFieldInitOverride(GenFeature feature) {
		return AgteleEcoreGeneratorHelper.hasGenAnnotationDetailsEntry(feature, INIT_KEY);
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
