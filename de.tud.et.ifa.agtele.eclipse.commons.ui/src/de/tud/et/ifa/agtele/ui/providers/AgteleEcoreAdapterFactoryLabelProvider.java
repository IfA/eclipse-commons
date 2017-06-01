package de.tud.et.ifa.agtele.ui.providers;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.provider.EModelElementItemProvider;
import org.eclipse.emf.edit.provider.ComposedImage;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.jface.viewers.StyledString.Styler;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.TextStyle;
import org.eclipse.swt.widgets.Display;

import de.tud.et.ifa.agtele.resources.BundleContentHelper;
import de.tud.et.ifa.agtele.ui.providers.AgteleEcoreContentProvider.NonContainedChildWrapper;

public class AgteleEcoreAdapterFactoryLabelProvider extends AdapterFactoryLabelProvider.StyledLabelProvider {

	public AgteleEcoreAdapterFactoryLabelProvider(AdapterFactory adapterFactory, Font defaultFont,
			Color defaultForeground, Color defaultBackground) {
		super(adapterFactory, defaultFont, defaultForeground, defaultBackground);
	}

	public AgteleEcoreAdapterFactoryLabelProvider(AdapterFactory adapterFactory, Viewer viewer) {
		super(adapterFactory, viewer);
	}


	@Override
	public Image getImage(Object object) {
		boolean inherited = false;
		Image result = null;
		if (object instanceof NonContainedChildWrapper) {
			object = ((NonContainedChildWrapper) object).noncontainedChild;
			inherited = true;
		}
		if (object instanceof EReference || object instanceof EClass) {

			EModelElementItemProvider labelProvider = (EModelElementItemProvider) this.adapterFactory
					.adapt(object, IItemLabelProvider.class);

			Object image = labelProvider.getImage(object);
			String imagePath = null;

			if (object instanceof EReference && ((EReference) object).isContainment()) {
				imagePath = "icons/ContainmentReference.gif";
			} else if (object instanceof EClass && ((EClass)object).isInterface()) {
				imagePath = "icons/EInterface.gif";
			} else if (object instanceof EClass && ((EClass)object).isAbstract()) {
				imagePath = "icons/EAbstractClass.gif";
			}

			if (imagePath != null) {
				if (image instanceof ComposedImage) {
					// The first sub-image of the composed image always represent the 'base' image (i.e.
					// the icon for the 'Reference'). Thus we can simply replace this with our special
					// icon.
					//
					((ComposedImage) image).getImages().set(0,
							BundleContentHelper.getBundleImage(
									"de.tud.et.ifa.agtele.eclipse.commons.ui",
									imagePath));

					result = this.getImageFromObject(image);
				} else {
					result = this.getImageFromObject(BundleContentHelper.getBundleImage(
							"de.tud.et.ifa.agtele.eclipse.commons.ui",
							imagePath));
				}
			}
		}
		if (result == null) {
			result = super.getImage(object);
		}

		if (inherited) {
			List<Image> imgs = new ArrayList<>();
			imgs.add(BundleContentHelper.getBundleImage("de.tud.et.ifa.agtele.eclipse.commons.ui",
					"icons/InheritedUnderlay.gif"));
			imgs.add(result);
			ComposedImage inheritedImage = new ComposedImage(imgs);
			result = this.getImageFromObject(inheritedImage);
		}
		return result;
	}

	/**
	 * Determines, if a feature or an operation displayed as contained in an
	 * eClass is actually inherited from another eClass.
	 *
	 * @param object
	 * @return True, if the container of the object is an EClass, if the Object
	 *         is a EStructuralFeature or an EOperation and the
	 */
	public static boolean isChildInherited(Object object) {

		if (!(object instanceof EObject) && !(object instanceof NonContainedChildWrapper)
				&& !(object instanceof NonContainedChildWrapper
						&& ((NonContainedChildWrapper) object).getNoncontainedChild() instanceof EObject)) {
			return false;
		}
		EObject parent = object instanceof NonContainedChildWrapper
				? (EObject) ((NonContainedChildWrapper) object).getParentNode() : ((EObject) object).eContainer();
				if (parent == null || !(parent instanceof EClass)) {
					return false;
				}

				EClass eClass = (EClass) parent;
				if (object instanceof NonContainedChildWrapper) {
					object = ((NonContainedChildWrapper) object).noncontainedChild;
				}

				if (object instanceof EStructuralFeature) {
					return !eClass.getEStructuralFeatures().contains(object);
				} else if (object instanceof EOperation) {
					return !eClass.getEOperations().contains(object);
				}
				return false;
	}

	@Override
	public String getText(Object object) {
		return this.getStyledText(object).getString();
	}

	@Override
	public StyledString getStyledText(Object object) {
		StyledString result;

		if (AgteleEcoreAdapterFactoryLabelProvider.isChildInherited(object)
				&& object instanceof NonContainedChildWrapper
				&& ((NonContainedChildWrapper) object).getParentNode() instanceof EClass
				&& ((NonContainedChildWrapper) object).getNoncontainedChild() instanceof EObject) {
			Object nonContainedChild = ((NonContainedChildWrapper) object).getNoncontainedChild();
			result = super.getStyledText(nonContainedChild);

			EClass eClass = (EClass) ((EObject) nonContainedChild)
					.eContainer();

			result.append(" (inherited from '" + eClass.getName() + "' in " + eClass.getEPackage().getNsURI() + ")",
					StyledString.DECORATIONS_STYLER);

			result.setStyle(0, result.length(), new InheritedFeatureStyler());
		} else {
			result = new StyledString(super.getText(object));
			if (object instanceof EStructuralFeature && ((EStructuralFeature) object).isDerived()) {
				result = this.modifyDerivedFeatureLabel(result);
			}
		}

		return result;
	}

	/**
	 * This method can be called in order to modify the label of a derived
	 * feature.
	 *
	 * @param text
	 * @return
	 */
	public String modifyDerivedFeatureLabel(String text) {
		return this.modifyDerivedFeatureLabel(new StyledString(text)).getString();
	}

	/**
	 * This method can be called in order to modify the label of a derived
	 * feature.
	 *
	 * @param text
	 * @return
	 */
	public StyledString modifyDerivedFeatureLabel(StyledString text) {
		return new StyledString("/").append(text);
	}

	public static class InheritedFeatureStyler extends Styler {
		@Override
		public void applyStyles(TextStyle textStyle) {
			Font italic = new Font(Display.getCurrent(), new FontData[] { new FontData("Arial", 8, SWT.ITALIC) });
			textStyle.font = italic;
			textStyle.foreground = new Color(Display.getCurrent(), 100, 100, 100);
		}
	}
}
