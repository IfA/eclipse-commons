package de.tud.et.ifa.agtele.emf.edit;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.eclipse.emf.common.command.AbstractCommand;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.edit.command.DragAndDropCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.provider.AdapterFactoryItemDelegator;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ItemProviderAdapter;
import org.eclipse.xtext.EcoreUtil2;

import de.tud.et.ifa.agtele.emf.AgteleEcoreUtil;
import de.tud.et.ifa.agtele.emf.edit.commands.AmbiguousCommandWrapper;
import de.tud.et.ifa.agtele.emf.edit.commands.AmbiguousCommandWrapper.ICommandSelectionStrategy;
import de.tud.et.ifa.agtele.emf.edit.commands.BasicDragAndDropAddCommand;
import de.tud.et.ifa.agtele.emf.edit.commands.BasicDragAndDropSetCommand;


/**
 * This provides custom implementations for the '<em>createDragAndDropCommand(...)</em>' function provided by
 * the {@link ItemProviderAdapter}.
 * 
 * @author mfreund
 */
public interface IDragAndDropProvider {
	
	/**
	 * Create a custom {@link DragAndDropCommand}. The given '<em>strategy</em>' can be used to select 
	 * one of multiple possible commands to execute.
	 */
	public default Command createCustomDragAndDropCommand(EditingDomain domain, Object owner, float location, int operations,
			int operation, Collection<?> collection, ICommandSelectionStrategy strategy) {
		
		Command dragAndDropCommand = new DragAndDropCommand(domain, owner, location, operations, operation, collection);
		
		if(!(owner instanceof EObject) || collection.isEmpty() || (location < 0.25 && location > 0.75)) {
			return dragAndDropCommand;
		}
		
		// Determine the common super type of all dragged objects (this will be checked against the types of the possible references)
		//
		EClassifier commonSuperType = null;
		for (Iterator<?> iterator = collection.iterator(); iterator.hasNext();) {
			Object object = (Object) iterator.next();
			
			if(!(object instanceof EObject)) {
				return dragAndDropCommand;
			}
			
			if(commonSuperType == null) {
				commonSuperType = ((EObject) object).eClass();
			} else {
				commonSuperType = EcoreUtil2.getCompatibleType(commonSuperType, ((EObject) object).eClass());
			}
		}

		if(commonSuperType == null || !(commonSuperType instanceof EClassifier)) {
			// this should never happen as the common super type should at least be 'EObject'
			return dragAndDropCommand;
		}

		EObject parent = (EObject) owner;
		EClass commonSuperClass = (EClass) commonSuperType;
		
		// Determine all possible non-containment references that could be used to drop the collection
		//
		ArrayList<EReference> possibleReferences = new ArrayList<>();
		for (EReference ref : parent.eClass().getEAllReferences()) {
			if((ref.isContainment() || ref.getEOpposite() == null || !ref.getEOpposite().isContainment())
					&& !ref.isDerived() 
					&& (collection.size() > 1 ? ref.isMany() : true)) {
				
				// If there is an 'IItemPropertyDescriptor' for the reference, we use this to check suitable values...
				//
				AdapterFactoryItemDelegator delegator = AgteleEcoreUtil.getAdapterFactoryItemDelegatorFor(parent);
				IItemPropertyDescriptor desc = null;
				if(delegator != null && (desc = delegator.getPropertyDescriptor(parent, ref)) != null) {
					if(desc.getChoiceOfValues(parent).containsAll(collection)) {					
						possibleReferences.add(ref);
					}
				// ... otherwise, only check type conformance.
				} else {
					if(ref.getEReferenceType().isSuperTypeOf((EClass) commonSuperClass)) {
						possibleReferences.add(ref);						
					}
				}
				
			}
		}
		
		// Create a suitable drag and drop command depending on the number of possibilities
		//
		if(possibleReferences.isEmpty()) {
			return dragAndDropCommand;			
		} else if(possibleReferences.size() == 1) {
			
			EReference ref = possibleReferences.iterator().next();
			if(ref.isMany()) {
				return new BasicDragAndDropAddCommand(domain, parent, ref, collection);
			} else {
				return new BasicDragAndDropSetCommand(domain, parent, ref, collection.iterator().next(), 0);
			}
		} else {
			ArrayList<AbstractCommand> commands = new ArrayList<>();
			for (EReference ref : possibleReferences) {
				if(ref.isMany()) {
					commands.add(new BasicDragAndDropAddCommand(domain, parent, ref, collection));
				} else {
					commands.add(new BasicDragAndDropSetCommand(domain, parent, ref, collection.iterator().next(), 0));
				}				
			}
			return new AmbiguousCommandWrapper(commands, (strategy == null ? new ICommandSelectionStrategy() {} : strategy));
		}
		
	}
	
}
