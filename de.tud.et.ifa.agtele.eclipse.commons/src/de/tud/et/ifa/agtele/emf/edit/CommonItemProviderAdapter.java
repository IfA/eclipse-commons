package de.tud.et.ifa.agtele.emf.edit;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EGenericType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.ExtendedMetaData;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.ecore.util.FeatureMapUtil;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.CommandParameter;
import org.eclipse.emf.edit.command.CreateChildCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.provider.ComposedImage;
import org.eclipse.emf.edit.provider.ItemProviderAdapter;

/**
 * A special {@link ItemProviderAdapter} that encapsulates common
 * functionalities.
 * <p />
 * In order to make use of it, the root item provider of your generated model
 * must extend this instead of the default '<em>ItemProviderAdapter</em>'.
 *
 * @author mfreund
 */
public class CommonItemProviderAdapter extends ItemProviderAdapter {

	/**
	 * This create child command allows accessing the intended owner of the child by
	 * adding a single Method to the super class.
	 *
	 * @author Baron
	 *
	 */
	public static class CreateChildCommandWithExtendedAccess extends CreateChildCommand {
		public CreateChildCommandWithExtendedAccess(EditingDomain domain, EObject owner, EStructuralFeature feature,
				Object child, Collection<?> selection) {
			super(domain, owner, feature, child, selection);
		}

		public CreateChildCommandWithExtendedAccess(EditingDomain domain, EObject owner, EStructuralFeature feature,
				Object child, Collection<?> selection, Helper helper) {
			super(domain, owner, feature, child, selection, helper);
		}

		public CreateChildCommandWithExtendedAccess(EditingDomain domain, EObject owner, EStructuralFeature feature,
				Object child, int index, Collection<?> selection) {
			super(domain, owner, feature, child, index, selection);
		}

		public CreateChildCommandWithExtendedAccess(EditingDomain domain, EObject owner, EStructuralFeature feature,
				Object child, int index, Collection<?> selection, Helper helper) {
			super(domain, owner, feature, child, index, selection, helper);
		}

		/**
		 * Returns the owner.
		 *
		 * @return
		 */
		public EObject getOwner() {
			return this.owner;
		}

		/**
		 * Returns the editing domain for this command.
		 *
		 * @return
		 */
		public EditingDomain getDomain() {
			return this.domain;
		}

	}

	/**
	 * This class realizes a fix for the creation of e.g. PropertySamples
	 *
	 * the preparation of their {@link AddCommand} fails the type check in line
	 * 324 of the AddCommand: <code>if (!eType.isInstance(object)) {</code>,
	 * e.g., etype: StylePropertyGroupable; object: StylePropertyGroupImpl fails
	 *
	 * @author cmartin
	 */
	public static class AddCommandWithEnhancedGenericTypeSupport extends AddCommand {

		/**
		 * This creates an instance of an {@link AddCommand} with enhanced
		 * support of GenericTypes
		 *
		 * @param domain
		 * @param owner
		 * @param feature
		 * @param collection
		 * @param index
		 */
		public AddCommandWithEnhancedGenericTypeSupport(EditingDomain domain, EObject owner,
				EStructuralFeature feature, Collection<?> collection, int index) {
			super(domain, owner, feature, collection, index);
		}

		/**
		 * Fix the type check <code>if (!eType.isInstance(object)) {</code>
		 */
		@Override
		protected boolean prepare() {
			// If there is no list to add to, no collection or an empty
			// collection from which to add, or the index is out of range...
			//
			if (this.ownerList == null || this.collection == null || this.collection.size() == 0
					|| this.index != CommandParameter.NO_INDEX
					&& (this.index < 0 || this.index > this.ownerList.size())) {
				return false;
			}

			if (this.feature != null) {
				// If it's a feature map, we'll need to validate the entry
				// feature and enforce its multiplicity restraints.
				//
				FeatureMapUtil.Validator validator = null;
				boolean documentRoot = false;
				Set<EStructuralFeature> entryFeatures = Collections.emptySet();

				if (FeatureMapUtil.isFeatureMap(this.feature)) {
					EClass eClass = this.owner.eClass();
					validator = FeatureMapUtil.getValidator(eClass, this.feature);

					// Keep track of all the entry features that are already
					// in the feature map and that will be added, excluding
					// XML text, CDATA, and comments (if we're in a mixed
					// type).
					//
					documentRoot = ExtendedMetaData.INSTANCE.getDocumentRoot(eClass.getEPackage()) == eClass;
					boolean mixed = documentRoot
							|| ExtendedMetaData.INSTANCE.getContentKind(eClass) == ExtendedMetaData.MIXED_CONTENT;
					entryFeatures = new HashSet<>();
					for (Object entry : this.ownerList) {
						EStructuralFeature entryFeature = ((FeatureMap.Entry) entry).getEStructuralFeature();
						if (!mixed || this.isUserElement(entryFeature)) {
							entryFeatures.add(entryFeature);
						}
					}
				}

				// Check each object...
				//
				EGenericType eType = this.owner == null ? this.feature.getEGenericType()
						: this.owner.eClass().getFeatureType(this.feature);
				for (Object object : this.collection) {
					boolean containment = false;

					// Check type of object.
					//
					if (!eType.isInstance(object)
							&& !(eType.getEClassifier() != null && eType.getEClassifier().isInstance(object))) {
						return false;
					}

					// Check that the object isn't already in a unique list.
					//
					if (this.feature.isUnique() && this.ownerList.contains(object)) {
						return false;
					}

					// For feature maps, test that the entry feature is a
					// valid type, that the entry value is an instance of
					// it,
					// that there is not already something in a document
					// root, and that there is not already something in a
					// single-valued entry feature.
					//
					if (validator != null) {
						FeatureMap.Entry entry = (FeatureMap.Entry) object;
						EStructuralFeature entryFeature = entry.getEStructuralFeature();
						containment = entryFeature instanceof EReference && ((EReference) entryFeature).isContainment();

						if (!validator.isValid(entryFeature) || !entryFeature.getEType().isInstance(entry.getValue())) {
							return false;
						}

						if (documentRoot) {
							if (this.isUserElement(entryFeature)) {
								if (!entryFeatures.isEmpty()) {
									return false;
								}
								entryFeatures.add(entryFeature);
							}
						} else if (!entryFeatures.add(entryFeature)
								&& !FeatureMapUtil.isMany(this.owner, entryFeature)) {
							return false;
						}
					}

					// Check to see if a container is being put into a
					// contained object.
					//
					containment |= this.feature instanceof EReference && ((EReference) this.feature).isContainment();
					if (containment) {
						for (EObject container = this.owner; container != null; container = container.eContainer()) {
							if (object == container) {
								return false;
							}
						}
					}
				}
			}

			if (this.owner != null && this.domain.isReadOnly(this.owner.eResource())) {
				return false;
			}

			return true;
		}
	}

	/**
	 * A special {@link ComposedImage} where the last of the given images is be
	 * shifted by a given x- and yOffset.
	 * <p />
	 * It can e.g. be used in {@link ItemProviderAdapter#getImage(Object)} to
	 * return a decorated image. Usage examples:
	 * <p />
	 * <code>
	 * <pre>
	 * {@code
	 * return new DecoratedComposedImage(Arrays.asList(
	 * 		this.overlayImage(object, this.getResourceLocator().getImage("full/obj16/NameOfModelElement")),
	 * 		this.getResourceLocator().getImage("full/obj16/NameOfDecorationImage")),
	 * 		5, 0);
	 *
	 * return new DecoratedComposedImage(Arrays.asList(
	 * 		this.overlayImage(object, this.getResourceLocator().getImage("full/obj16/NameOfModelElement")),
	 * 		BundleContentHelper.getBundleImage("my.fancy.plugin", "icons/NameOfDecorationImage.gif"))),
	 * 		5, 0);
	 * }
	 * </pre>
	 * </code>
	 *
	 * @author mfreund
	 */
	public static final class DecoratedComposedImage extends ComposedImage {

		/**
		 * The horizontal offset by which the last of the images shall be
		 * shifted.
		 */
		private final int xOffset;

		/**
		 * The vertical offset by which the last of the images shall be shifted.
		 */
		private final int yOffset;

		/**
		 * This creates an instance.
		 *
		 * @param images
		 *            The list of images that this image shall be composed of.
		 * @param xOffset
		 *            The horizontal offset by which the last of the images
		 *            shall be shifted.
		 * @param yOffSet
		 *            The vertical offset by which the last of the images shall
		 *            be shifted.
		 */
		public DecoratedComposedImage(Collection<?> images, int xOffset, int yOffSet) {
			super(images);
			this.xOffset = xOffset;
			this.yOffset = yOffSet;
		}

		/**
		 * @return the {@link #xOffset}
		 */
		public int getXOffset() {
			return this.xOffset;
		}

		/**
		 * @return the {@link #yOffset}
		 */
		public int getYOffset() {
			return this.yOffset;
		}

		@Override
		public List<ComposedImage.Point> getDrawPoints(Size size) {
			List<ComposedImage.Point> result = super.getDrawPoints(size);
			result.get(result.size() - 1).y = this.yOffset;
			result.get(result.size() - 1).x = this.xOffset;
			return result;
		}

		@Override
		public boolean equals(Object that) {
			return that instanceof DecoratedComposedImage
					&& ((DecoratedComposedImage) that).getImages().equals(this.images)
					&& ((DecoratedComposedImage) that).getXOffset() == this.getXOffset()
					&& ((DecoratedComposedImage) that).getYOffset() == this.getYOffset();
		}

		@Override
		public int hashCode() {
			return this.images.hashCode() + this.xOffset + this.yOffset;
		}
	}

	/**
	 * This creates an instance.
	 *
	 * @param adapterFactory
	 *            An instance is created from an adapter factory. The factory is
	 *            used as a key so that we always know which factory created
	 *            this adapter.
	 */
	public CommonItemProviderAdapter(AdapterFactory adapterFactory) {
		super(adapterFactory);
	}

	@Override
	protected Command createDragAndDropCommand(EditingDomain domain, Object owner, float location, int operations,
			int operation, Collection<?> collection) {

		// If possible use the special functionality provided by the
		// 'IDragAndDropProvider' interface...
		if (this instanceof IDragAndDropProvider) {
			return ((IDragAndDropProvider) this).createCustomDragAndDropCommand(domain, owner, location, operations,
					operation, collection, ((IDragAndDropProvider) this).getCommandSelectionStrategy());
		} else {
			return super.createDragAndDropCommand(domain, owner, location, operations, operation, collection);
		}
	}

	/**
	 * Overwrites the default implementation in order to fix a bug that occurs
	 * only when using GenericTypes
	 */
	@Override
	protected Command createAddCommand(EditingDomain domain, EObject owner, EReference feature,
			Collection<?> collection, int index) {

		return new AddCommandWithEnhancedGenericTypeSupport(domain, owner, feature, collection, index);
	}

	/**
	 * Delegates to the super implementation but wraps the original command into a
	 * {@link CompoundCommand} by use of the
	 * {@link IRequireRelatedModelUpdateProvider}, if other model updates have to be
	 * done.
	 */
	@Override
	public Command createCommand(Object object, EditingDomain domain, Class<? extends Command> commandClass,
			CommandParameter commandParameter) {
		Command result = super.createCommand(object, domain, commandClass, commandParameter);
		return IRequireRelatedModelUpdateProvider.wrapOriginalCommand(this, result);
	}

	@Override
	protected Command createCreateChildCommand(EditingDomain domain, EObject owner, EStructuralFeature feature,
			Object value, int index, Collection<?> collection) {

		if (feature instanceof EReference && value instanceof EObject) {
			return new CreateChildCommandWithExtendedAccess(domain, owner, feature, value, index, collection, this);
		}
		return new CreateChildCommandWithExtendedAccess(domain, owner, feature, value, index, collection, this);
	}

}
