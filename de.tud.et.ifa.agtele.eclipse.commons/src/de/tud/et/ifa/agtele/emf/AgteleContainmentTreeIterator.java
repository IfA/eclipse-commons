package de.tud.et.ifa.agtele.emf;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.Resource;

public class AgteleContainmentTreeIterator implements Iterator<EObject> {

	protected AgteleContainmentTreeIterator subIterator = null;
	
	protected EList<EReference> refs = null; 
	
	protected AgteleResourceContainmentTreeIterator resourceIterator = null;
		
	protected EObject rootObj = null;
	
	protected ArrayList<EObject> contents = null;

	protected boolean ignoreDerived;

	protected boolean ignoreVolatile;
	
	protected int i = -1;
	
	private AgteleContainmentTreeIterator() {}	
	
	private AgteleContainmentTreeIterator(boolean ignoreDerived, boolean ignoreVolatile) {
		this.ignoreDerived = ignoreDerived;
		this.ignoreVolatile = ignoreVolatile;
	}	
		
	public AgteleContainmentTreeIterator (EObject obj, boolean ignoreDerived, boolean ignoreVolatile) {
		this(ignoreDerived, ignoreVolatile);
		this.rootObj = obj;
		contents = getFilteredContent(); 
	}
	
	public AgteleContainmentTreeIterator (Resource res, boolean ignoreDerived, boolean ignoreVolatile) {
		this(ignoreDerived, ignoreVolatile);
		this.resourceIterator = new AgteleResourceContainmentTreeIterator(res);
		contents = getFilteredContent();
	}
	
	@Override
	public boolean hasNext() {
		if (i + 1 < contents.size()) {
			return true;
		}
		if (subIterator == null || !subIterator.hasNext()) {
			return false;
		}
		return subIterator.hasNext();
	}

	@Override
	public EObject next() {		
		if (subIterator != null && subIterator.hasNext()) {
			return subIterator.next();
		} else if (i+1 < contents.size()) {			
			subIterator = new AgteleContainmentTreeIterator(contents.get(++i), ignoreDerived, ignoreVolatile);
			return contents.get(i);
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	protected ArrayList<EObject> getFilteredContent () {
		if (this.contents != null) {
			return this.contents;
		}
		
		if (this.resourceIterator != null) {
			return this.resourceIterator.getFilteredContent();
		} else if (rootObj != null){
			ArrayList<EObject> result = new ArrayList<>();
			EClass cls = rootObj.eClass();
			EList<EReference> containmentFeatures = cls.getEAllContainments();
			
			for (EReference ref : containmentFeatures) {
				if (!ref.isDerived() || !ignoreDerived || (ref.isDerived() && (!ref.isVolatile() || !ignoreVolatile))) {
					if (ref.isMany()) {
						EList<EObject> theContent = (EList<EObject>) rootObj.eGet(ref);
						if (!theContent.isEmpty()) {
							result.addAll((Collection<EObject>) theContent);
						}
					} else {
						EObject theContent = (EObject) rootObj.eGet(ref);
						if (theContent != null) {
							result.add(theContent);
						}
					}
				}
			}
			
			return result;
		} 
		return null;
	}

	protected class AgteleResourceContainmentTreeIterator extends AgteleContainmentTreeIterator {

		protected Resource res = null;
				
		AgteleResourceContainmentTreeIterator(Resource res) {
			this.res = res;
		}
		
		protected ArrayList<EObject> getFilteredContent () {
			if (this.contents != null) {
				return this.contents;
			}
			
			ArrayList<EObject> result = new ArrayList<>();
			
			for (EObject obj : res.getContents()) {
				result.add(obj);
			}			
			return result;
		}
	}
}
