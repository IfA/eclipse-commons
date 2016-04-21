package de.tud.et.ifa.agtele.emf.edit.commands;

import java.util.Arrays;
import java.util.Collection;

import org.eclipse.emf.common.command.AbstractCommand;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.edit.command.DragAndDropFeedback;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.swt.dnd.DND;

/**
 * A {@link CompoundCommand} that changes the containing feature of a single element or of a collection
 * of elements and that provides DragAndDropFeedback and can thus be returned by
 * e.g. the 'createDragAndDropCommand' function in EMF item providers. *
 */
public class DragAndDropChangeContainingFeatureCommand extends AbstractCommand implements
		DragAndDropFeedback {
	
	protected EReference originalReference, reference;
	protected EObject originalOwner, owner;
	protected Collection<EObject> values;

	private DragAndDropChangeContainingFeatureCommand(EObject owner,
			EReference feature, Collection<EObject> values) {
		
		super();
		
		this.reference = feature;
		this.owner = owner;
		this.values = new BasicEList<>(values);
	}
	
	/**
	 * This creates an instance.
	 * 
	 * @param domain The {@link EditingDomain} on which the command will be executed.
	 * @param owner The new container of the given '<em>value</em>'.
	 * @param feature The new containing feature of the given '<em>value</em>'.
	 * @param value The {@link EObject} for which the containing feature shall be changed.
	 */
	public DragAndDropChangeContainingFeatureCommand(EditingDomain domain, EObject owner,
			EReference feature, EObject value) {

		this(owner, feature, Arrays.asList(value));
		
		this.originalReference = (EReference) value.eContainingFeature();
		this.originalOwner = value.eContainer();

	}
	
	/**
	 * This creates an instance.
	 * 
	 * @param domain The {@link EditingDomain} on which the command will be executed.
	 * @param owner The new container of the given '<em>values</em>'.
	 * @param feature The new containing feature of the given '<em>values</em>'.
	 * @param values The list of {@link EObject EObjects} for which the containing feature shall be changed.
	 */
	public DragAndDropChangeContainingFeatureCommand(EditingDomain domain, EObject owner,
			EReference feature, Collection<EObject> values) {

		this(owner, feature, values);
		
		if(values.size() > 1 && !feature.isMany()) {
			throw new RuntimeException("Trying to set multiple values for a single-valued feature!");
		}
		
		for (EObject value : values) {
			if(this.originalReference == null) {
				this.originalReference = (EReference) value.eContainingFeature();
			} else if(this.originalReference != value.eContainingFeature()) {
				throw new RuntimeException("Not all dragged objects are contained in the same feature!");
			}
			if(this.originalOwner == null) {
				this.originalOwner = value.eContainer();
			} else if(this.originalOwner != value.eContainer()) {
				throw new RuntimeException("Not all dragged objects are contained in the same feature!");
			}
		}

	}

	@Override
	public boolean validate(Object owner, float location, int operations,
			int operation, Collection<?> collection) {
		return location >= 0.2 && location <= 0.8;
	}

	@Override
	public int getFeedback() {
		return DND.FEEDBACK_SELECT;
	}

	@Override
	public int getOperation() {
		return DND.DROP_LINK;
	}
	
	@Override
	public String getLabel() {

		String ret = "";
		
		if(reference.isMany()) {
			ret += "Add to '";
		} else if(owner.eGet(reference) != null) {
			ret += "Replace '";
		} else {
			ret += "Set as '";
		}
		
		ret += reference.getName() + "' (containment reference)";
		
		return ret;
	}
	
	@Override
	protected boolean prepare() {
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void execute() {
		if(reference.isMany()) {
			((Collection<EObject>) owner.eGet(reference)).addAll(values);
		} else {
			owner.eSet(reference, values.iterator().next());
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void redo() {
		if(reference.isMany()) {
			((Collection<EObject>) owner.eGet(reference)).addAll(values);
		} else {
			owner.eSet(reference, values.iterator().next());
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void undo() {
		if(originalReference.isMany()) {
			((Collection<EObject>) originalOwner.eGet(originalReference)).addAll(values);
		} else {
			originalOwner.eSet(originalReference, values.iterator().next());
		}
	}
	
}
