package de.tud.et.ifa.agtele.ui.emf.edit.action.filter;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.CommandParameter;

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
	public List<CommandParameter> filterCommands(List<? extends CommandParameter> currentFilterState,
			List<? extends CommandParameter> originalCommands) {
		List<CommandParameter> result = new ArrayList<>(currentFilterState);
		
		result.removeIf(p -> {
			EObject child = p.getEValue();
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

		return result;
	}

}
