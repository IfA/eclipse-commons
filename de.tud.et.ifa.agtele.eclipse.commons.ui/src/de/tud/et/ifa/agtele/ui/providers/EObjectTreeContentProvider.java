package de.tud.et.ifa.agtele.ui.providers;

import java.util.Collection;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

/**
 * An {@link ITreeContentProvider} that creates the tree structure based on the containment
 * tree of an EMF model. Therefore, it makes use of {@link EObject#eContents()} and 
 * {@link EObject#eContainer()} to determine children and parents.
 * <br /><br />
 * This class and all of its methods may be overridden or extended.
 * 
 * @author mfreund
 */
public class EObjectTreeContentProvider implements ITreeContentProvider {

	@Override
	public void dispose() {
		return;
	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		return;
	}


	@Override
	public Object[] getElements(Object inputElement) {
		if(inputElement instanceof EObject) {
			return getChildren(inputElement);
		} else if(inputElement instanceof Collection) {
			return ((Collection<?>) inputElement).toArray();
		} else {
			return new Object[]{};
		}
	}

	@Override
	public Object[] getChildren(Object parentElement) {
		if(!(parentElement instanceof EObject)) {
			return new Object[]{};
		}
		return ((EObject) parentElement).eContents().toArray();
	}

	@Override
	public Object getParent(Object element) {
		if(!(element instanceof EObject)) {
			return null;
		}
		return ((EObject) element).eContainer();
	}

	@Override
	public boolean hasChildren(Object element) {
		return getChildren(element).length > 0 ? true : false;
	}

}
