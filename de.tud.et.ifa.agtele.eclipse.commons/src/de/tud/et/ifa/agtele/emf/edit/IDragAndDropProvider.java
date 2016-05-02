package de.tud.et.ifa.agtele.emf.edit;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.emf.common.command.AbstractCommand;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.edit.command.CommandParameter;
import org.eclipse.emf.edit.command.DragAndDropCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.provider.AdapterFactoryItemDelegator;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ItemProviderAdapter;
import de.tud.et.ifa.agtele.emf.AgteleEcoreUtil;
import de.tud.et.ifa.agtele.emf.edit.commands.AmbiguousDragAndDropCommandWrapper;
import de.tud.et.ifa.agtele.emf.edit.commands.BasicDragAndDropAddCommand;
import de.tud.et.ifa.agtele.emf.edit.commands.BasicDragAndDropCompoundCommand;
import de.tud.et.ifa.agtele.emf.edit.commands.BasicDragAndDropSetCommand;
import de.tud.et.ifa.agtele.emf.edit.commands.DragAndDropChangeContainingFeatureCommand;


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
	 * <p />
	 * This will usually be called inside an implementation of '<em>createDragAndDropCommand(...)</em>' in a 
	 * custom ItemProvider instead of just calling '<em>super.createDragAndDropCommand(...)</em>'.
	 * 
	 * @param domain The {@link EditingDomain} that will be used to execute the command.
	 * @param owner The object on that the command will be executed.
	 * @param location The location (between 0.0 and 1.0) in relation to the '<em>owner</em>' where the command will be executed.
	 * @param operations The permitted operations.
	 * @param operation The desired operation.
	 * @param collection The dragged elements.
	 * @param strategy The {@link ICommandSelectionStrategy} that shall be applied to select an unambiguous 
	 * command before execution.
	 * @return The custom drag and drop command.
	 */
	@SuppressWarnings("unchecked")
	public default Command createCustomDragAndDropCommand(EditingDomain domain, Object owner, float location, int operations,
			int operation, Collection<?> collection, ICommandSelectionStrategy strategy) {
		
		DragAndDropCommand dragAndDropCommand = new DragAndDropCommand(domain, owner, location, operations, operation, collection) {
			
			@Override
			public boolean validate(Object owner, float location, int operations, int operation,
					Collection<?> collection) {
				// use the default d'n'd command only for move/copy operations (i.e. if the location is near 0 or near 1)
				return super.validate(owner, location, operations, operation, collection) && (location < 0.2 || location > 0.8);
			}
		};

		if(!(owner instanceof EObject) || collection.isEmpty()) {
			return dragAndDropCommand;
		}
		
		// Determine the common super type of all dragged objects (this will be checked against the types of the possible references)
		// and if all elements are of the exact same type
		//
		Set<EClass> eClassSet = new HashSet<>();
		for (Iterator<?> iterator = collection.iterator(); iterator.hasNext();) {
			Object object = iterator.next();
			
			if(!(object instanceof EObject)) {
				return dragAndDropCommand;
			}
			
			eClassSet.add(((EObject) object).eClass());
		}

		EObject parent = (EObject) owner;
		AdapterFactoryItemDelegator delegator = AgteleEcoreUtil.getAdapterFactoryItemDelegatorFor(parent);
		ArrayList<EReference> possibleReferences = new ArrayList<>();
		
		if(delegator != null) {
			
			// Determine all possible references that could be used to drop the collection
			//
			for (EReference ref : parent.eClass().getEAllReferences()) {
				if((ref.isContainment() || ref.getEOpposite() == null || !ref.getEOpposite().isContainment())
						&& !ref.isDerived() 
						&& (collection.size() > 1 ? ref.isMany() : true)) {
					
					// If there is an 'IItemPropertyDescriptor' for the reference, we use this to check suitable values...
					//
					IItemPropertyDescriptor propDesc;
					Collection<?> childDescs;
					if((propDesc = delegator.getPropertyDescriptor(parent, ref)) != null) {
						if(propDesc.getChoiceOfValues(parent).containsAll(collection)) {					
							possibleReferences.add(ref);
						}
						// ... otherwise, we evaluate the 'NewChildDescriptors'.
						//
					} else if(!(delegator.getNewChildDescriptors(parent, domain, null)).isEmpty()) {
						childDescs = delegator.getNewChildDescriptors(parent, domain, null);
						
						Iterator<CommandParameter> it = (Iterator<CommandParameter>) childDescs.parallelStream().filter(c -> c instanceof CommandParameter 
								&& ref.equals(((CommandParameter) c).getEStructuralFeature()) && ((CommandParameter) c).getEValue() != null).collect(Collectors.toList()).iterator();
						
						// Check that there is a child descriptor for the required 'ref' and the set of required 'EClasses'
						//
						Set<EClass> eClasses = new HashSet<>();
						while(it.hasNext()) {
							eClasses.add(it.next().getEValue().eClass());
						}
						
						if(eClassSet.containsAll(eClasses)) {
							possibleReferences.add(ref);
						}
						
					}
					
				}
			}
		} else {
			//TODO should we apply another strategy in case the delegator could not be determined?
		}
		
		ArrayList<AbstractCommand> commands = new ArrayList<>();
		if(dragAndDropCommand.canExecute()) {
			commands.add((AbstractCommand) dragAndDropCommand);
		}
		
		// Create a suitable drag and drop command depending on the number of possibilities
		//
		if(possibleReferences.isEmpty()) {
			return dragAndDropCommand;			
		} else if(possibleReferences.size() == 1) {
			return createDragAndDropCommand(domain, (Collection<EObject>) collection, parent, possibleReferences.iterator().next());
		} else {
			for (EReference ref : possibleReferences) {
				commands.add(createDragAndDropCommand(domain, (Collection<EObject>) collection, parent, ref));
			}
		}
		
		if(commands.size() == 1) {
			return commands.get(0);
		} else {
			return new AmbiguousDragAndDropCommandWrapper(owner, location, operations,
					operation, collection, commands, strategy == null ? new ICommandSelectionStrategy() {} : strategy);
		}
	}

	/**
	 * Based on the type of reference to set, creates either a {@link BasicDragAndDropSetCommand}, a 
	 * {@link BasicDragAndDropAddCommand}, or a {@link BasicDragAndDropCompoundCommand} (in case the collection needs to
	 * be removed from an old feature first).
	 * 
	 * @param domain The {@link EditingDomain} that will be used to execute the command.
	 * @param collection The dragged elements.
	 * @param parent The {@link EObject} on that the command will be executed.
	 * @param ref The {@link EReference} that to that the collection of objects shall be added/that shall be set.
	 * @return The drag and drop command.
	 */
	default AbstractCommand createDragAndDropCommand(EditingDomain domain, Collection<EObject> collection, EObject parent,
			EReference ref) {
		
		if(ref.isContainment()) {
			return new DragAndDropChangeContainingFeatureCommand(parent, ref, collection);
		} else {
			if(ref.isMany()) {
				return new BasicDragAndDropAddCommand(domain, parent, ref, collection);
			} else {
				return new BasicDragAndDropSetCommand(domain, parent, ref, collection.iterator().next(), 0);
			}
		}
	}
	
	/**
	 * This returns the {@link ICommandSelectionStrategy} that is used by 
	 * {@link #createCustomDragAndDropCommand(EditingDomain, Object, float, int, int, Collection, ICommandSelectionStrategy)}.
	 * 
	 * @return The {@link ICommandSelectionStrategy} to be used by 
	 * {@link #createCustomDragAndDropCommand(EditingDomain, Object, float, int, int, Collection, ICommandSelectionStrategy)}.
	 */
	public default ICommandSelectionStrategy getCommandSelectionStrategy() {
		return new ICommandSelectionStrategy() {};
	}
	
}
