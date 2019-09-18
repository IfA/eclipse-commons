package de.tud.et.ifa.agtele.ui.emf.edit.action.filter;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.ui.action.CreateChildAction;
import org.eclipse.jface.action.IAction;

import de.tud.et.ifa.agtele.ui.emf.editor.IExtendedCreateElementAction;

public class EClassCreateChildFilter extends CreateActionFilter {	
	protected EClass eClass;
	protected boolean kindOf = true;
	protected boolean subClass = true;

	public EClassCreateChildFilter(EClass cls) {
		this.eClass = cls;
	}

	public EClassCreateChildFilter(EClass cls, boolean kindOf, boolean subClass) {
		this(cls);
		this.kindOf = kindOf;
		this.subClass = subClass;
	}
	
	@Override
	public List<IAction> filterActions(List<? extends IAction> currentFilterState,
			List<? extends IAction> originalActions) {
		List<IExtendedCreateElementAction> result = new ArrayList<>(this.getCreateChildActions(currentFilterState));
		
		result.removeIf(a -> {
			EObject child = a.getDescriptor().getEValue();
			if (child == null) {
				return true;
			}
			EClass cls = child.eClass();
			if (cls == eClass) {
				return !kindOf;
			}
			if (eClass.isSuperTypeOf(cls)) {
				return !subClass;
			}
			return true;
		});
		// TODO Auto-generated method stub
		return null;
	}

}
