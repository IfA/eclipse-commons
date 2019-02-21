package de.tud.et.ifa.agtele.emf.edit;

import org.eclipse.emf.common.EMFPlugin;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceImpl;
import org.eclipse.jface.viewers.DelegatingStyledCellLabelProvider;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.StyledString;

public class AgteleStyledLabelProvider extends DelegatingStyledCellLabelProvider implements ILabelProvider {

	private EMFPlugin editPlugin = null;
	
	public AgteleStyledLabelProvider(IStyledLabelProvider labelProvider, EMFPlugin emfPlugin) {
		this(labelProvider);
		this.editPlugin = emfPlugin;
	}

	public AgteleStyledLabelProvider(IStyledLabelProvider labelProvider) {
		super(labelProvider);
	}
	
	public String getReferenceNameSeparator () {
		return AgteleStyledLabelProvider.getDefaultReferenceNameSeparator();
	}

	public static String getDefaultReferenceNameSeparator () {
		return "\u0020\u00bb\u0020";
	}
	
	public String getFeatureLabel (EStructuralFeature feature) {
		if (this.editPlugin != null) {
			return this.editPlugin.getString(
				"_UI_" + feature.getEContainingClass().getName()
				+ "_" + feature.getName() + "_feature");
		}
		return feature.getName();
	}
	
	@Override
	protected StyledString getStyledText(Object element) {
		StyledString typeString = super.getStyledText(element);
		StyledString result;
		if (element instanceof XMIResourceImpl) {
			return typeString;
		}
		if (element instanceof MinimalEObjectImpl && ((MinimalEObjectImpl)element).eContainer() == null) {
			result = typeString;
		} else if (element instanceof MinimalEObjectImpl) {
			EObject e = (EObject) element;
			try {
				result = new StyledString(
						this.getFeatureLabel(e.eContainingFeature())
						+ this.getReferenceNameSeparator(),
					StyledString.QUALIFIER_STYLER).append(typeString);
			} catch (Exception ex) {
				result = new StyledString(
						e.eContainingFeature().getName()
						+ this.getReferenceNameSeparator(),
						StyledString.QUALIFIER_STYLER).append(typeString);
			}
		} else {
			return typeString;
		}
		return result;
	}

	@Override
	public String getText(Object element) {
		return this.getStyledText(element).getString();
	}
}
