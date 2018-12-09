package de.tud.et.ifa.agtele.ui.providers;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EGenericType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.edit.command.CommandParameter;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.provider.IChildCreationExtender;
import org.eclipse.emf.edit.provider.ReflectiveItemProvider;
import org.eclipse.emf.edit.provider.ReflectiveItemProviderAdapterFactory;

public class AgteleReflectiveItemProviderAdapterFactory extends ReflectiveItemProviderAdapterFactory {
	public AgteleReflectiveItemProviderAdapterFactory() {
	    reflectiveItemProviderAdapter = new AgteleReflectiveItemProvider(this);
	}
	
	public static class AgteleReflectiveItemProvider extends ReflectiveItemProvider {

		public AgteleReflectiveItemProvider(AdapterFactory adapterFactory) {
			super(adapterFactory);
		}
		
		/**
		 * This is a temporary way to get the structural features that contribute children. It first calls the new
		 * {link #getChildrenFeatues getChildrenFeatures} method and then, if the result is empty, tries the deprecated
		 * {@link #getChildrenReferences getChildrenReferences} method. It is used, instead of just the new method,
		 * throughout this class.
		 */
		protected Collection<? extends EStructuralFeature> getAnyChildrenFeatures(Object object)
		{
		    Collection<? extends EStructuralFeature> result = getChildrenFeatures(object);
		    return result.isEmpty() ? getChildrenReferences(object) : result;
		}
		
		@Override
    	public Collection<?> getNewChildDescriptors(Object object, EditingDomain editingDomain,
    			Object sibling) {
    		
    		EObject eObject = (EObject)object;

    	    ArrayList<Object> newChildDescriptors = getBasicNewChildDescriptors(object, editingDomain);

    	    filterNewChildDescriptors(eObject, newChildDescriptors);

    	    modifyNewSiblingChildDescriptors(object, sibling, eObject, newChildDescriptors);
    	    return newChildDescriptors;
    	}

		protected void modifyNewSiblingChildDescriptors(Object object, Object sibling, EObject eObject,
				ArrayList<Object> newChildDescriptors) {
			// If a sibling has been specified, add the best index possible to each CommandParameter.
    	    //
    	    if (sibling != null)
    	    {
    	      sibling = unwrap(sibling);

    	      // Find the index of a feature containing the sibling, or an equivalent value, in the collection of children
    	      // features.
    	      //
    	      Collection<? extends EStructuralFeature> childrenFeatures = getAnyChildrenFeatures(object);
    	      int siblingFeatureIndex = -1;
    	      int i = 0;

    	      FEATURES_LOOP:
    	      for (EStructuralFeature feature : childrenFeatures)
    	      {
    	        Object featureValue = getValue(eObject, feature);
    	        if (feature.isMany())
    	        {
    	          for (Object value : (Collection<?>)featureValue)
    	          {
    	            if (isEquivalentValue(sibling, value))
    	            {
    	              siblingFeatureIndex = i;
    	              break FEATURES_LOOP;
    	            }
    	          }
    	        }
    	        else if (isEquivalentValue(sibling, featureValue))
    	        {
    	          siblingFeatureIndex = i;
    	          break FEATURES_LOOP;
    	        }
    	        ++i;
    	      }

    	      // For each CommandParameter with a non-null, multi-valued structural feature...
    	      //
    	      DESCRIPTORS_LOOP:
    	      for (Object descriptor : newChildDescriptors)
    	      {
    	        if (descriptor instanceof CommandParameter)
    	        {
    	          CommandParameter parameter = (CommandParameter)descriptor;
    	          EStructuralFeature childFeature = parameter.getEStructuralFeature();
    	          if (childFeature == null || !childFeature.isMany())
    	          {
    	            continue DESCRIPTORS_LOOP;
    	          }
    	  
    	          // Look for the sibling value or an equivalent in the new child's feature. If it is found, the child should
    	          // immediately follow it.
    	          //
    	          i = 0;
    	          for (Object v  : (Collection<?>)getValue(eObject, childFeature))
    	          {
    	            if (isEquivalentValue(sibling, v))
    	            {
    	              parameter.index = i + 1;
    	              continue DESCRIPTORS_LOOP;
    	            }
    	            ++i;
    	          }
    	  
    	          // Otherwise, if a sibling feature was found, iterate through the children features to find the index of
    	          // the child feature... 
    	          //
    	          if (siblingFeatureIndex != -1)
    	          {
    	            i = 0;
    	            for (EStructuralFeature feature : childrenFeatures)
    	            {
    	              if (feature == childFeature)
    	              {
    	                // If the child feature follows the sibling feature, the child should be first in its feature.
    	                //
    	                if (i > siblingFeatureIndex)
    	                {
    	                  parameter.index = 0;
    	                }
    	                continue DESCRIPTORS_LOOP;
    	              }
    	              ++i;
    	            }
    	          }
    	        }
    	      }
    	    }
		}

		protected void filterNewChildDescriptors(EObject eObject, ArrayList<Object> newChildDescriptors) {
			EClass eClass = eObject.eClass();
    	    for (int i = newChildDescriptors.size(); --i >= 0; )
    	    {
    	      Object descriptor = newChildDescriptors.get(i);
    	      if (descriptor instanceof CommandParameter)
    	      {
    	        // Check that it's a command parameter and has a child value.
    	        //
    	        CommandParameter parameter = (CommandParameter)descriptor;
    	        EStructuralFeature childFeature = parameter.getEStructuralFeature();
    	        Object child = parameter.getValue();
    	        if (childFeature != null && child != null)
    	        {
    	          // If that child is properly an instance of the feature's type...
    	          //
    	          EClassifier eType = childFeature.getEType();
    	          if (eType.isInstance(child))
    	          {
    	            // Check that it's also properly an instance of the feature's reified type.
    	            //
    	            EGenericType reifiedType = eClass.getFeatureType(childFeature);
    	            if (reifiedType != null && !reifiedType.isInstance(child))
    	            {
    	            	EClassifier classifier = reifiedType.getEClassifier();
    	            	if (classifier == null ||
    	            			!classifier.getName().equals("EObject") || 
    	            			!classifier.getEPackage().getNsURI().equals(EcorePackage.eNS_URI) || 
    	            			!(child instanceof EObject)) {    	            		
	    	                // If not, remove it.
	    	                //
	    	                newChildDescriptors.remove(i);
    	            	}
    	            }
    	          }
    	        }
    	      }

    	    }
		}

		protected ArrayList<Object> getBasicNewChildDescriptors(Object object, EditingDomain editingDomain) {
			// Build the collection of new child descriptors.
    	    //
    	    ArrayList<Object> newChildDescriptors = new ArrayList<Object>();
    	    collectNewChildDescriptors(newChildDescriptors, object);

    	    // Add child descriptors contributed by extenders.
    	    //
    	    if (adapterFactory instanceof IChildCreationExtender)
    	    {
    	      newChildDescriptors.addAll(((IChildCreationExtender)adapterFactory).getNewChildDescriptors(object, editingDomain));
    	    }
			return newChildDescriptors;
		}
	}
}