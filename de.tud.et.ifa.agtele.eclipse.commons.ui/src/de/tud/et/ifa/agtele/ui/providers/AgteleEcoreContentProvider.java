package de.tud.et.ifa.agtele.ui.providers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.provider.ViewerNotification;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.ui.views.properties.IPropertySource;

import de.tud.et.ifa.agtele.ui.handlers.ShowInheritedEcoreClassFeaturesCommandHandler;

/**
 * This provider enhances the default content provider for ecore models in order
 * to be able to display {@link EStructuralFeature}s and {@link EOperation}s
 * that are derived from super-{@link EClass}es. The display of the additionally
 * provided content can be switched on and off globally by use of the
 * {@link ShowInheritedEcoreClassFeaturesCommandHandler}.
 *
 * This provider keeps track of its instances in order to refresh the associated
 * views, when the
 * {@link ShowInheritedEcoreClassFeaturesCommandHandler#isVisible()} changes.
 * For that, the
 * {@link AgteleEcoreContentProvider#setInheritedContentVisibility(boolean)}
 * method is to be called.
 *
 * @author Baron
 *
 */
public class AgteleEcoreContentProvider extends StateRestoringViewerContentProvider {

	/**
	 * The list of content provider instances.
	 */
	static protected List<AgteleEcoreContentProvider> instances = new ArrayList<>();

	/**
	 * Flag indicating whether the inherited content is to be displayed.
	 */
	static protected boolean inheritedContentVisible = true;

	/**
	 * @param adapterFactory
	 * @param structuredViewer
	 */
	public AgteleEcoreContentProvider(AdapterFactory adapterFactory, StructuredViewer structuredViewer) {
		super(adapterFactory, structuredViewer);

		AgteleEcoreContentProvider.instances.add(this);
		AgteleEcoreContentProvider.inheritedContentVisible = AgteleEcoreContentProvider.getCurrentVisibilityState();
	}

	@Override
	public void notifyChanged(Notification notification) {
		//Update the inheriting classes in the view, when a inherited feature changes in the superclass
		if (AgteleEcoreContentProvider.inheritedContentVisible && notification instanceof ViewerNotification) {
			EClass changedClass = null;
			ViewerNotification n = (ViewerNotification) notification;

			if (n.getElement() instanceof EClass) {
				changedClass = (EClass) n.getElement();
			} else if (n.getElement() instanceof EStructuralFeature) {
				changedClass = ((EStructuralFeature) n.getElement()).getEContainingClass();
			}
			if (changedClass != null && this.viewer != null) {
				this.viewer.refresh();
			}

		}
		super.notifyChanged(notification);
	}

	/**
	 * Standard
	 * {@link StateRestoringViewerContentProvider#getPropertySource(Object)}. In
	 * case of a {@link NonContainedChildWrapper} it delegates to the
	 * {@link NonContainedChildWrapper#noncontainedChild}.
	 */
	@Override
	public IPropertySource getPropertySource(Object object) {
		if (object instanceof NonContainedChildWrapper) {
			return this.getPropertySource(((NonContainedChildWrapper) object).getNoncontainedChild());
		}
		return super.getPropertySource(object);
	}

	/**
	 * Standard {@link StateRestoringViewerContentProvider#getChildren(Object)},
	 * except in case the object is an {@link EClass}, for each inherited (tree-)
	 * child, a {@link NonContainedChildWrapper} is created. This wrapper is needed
	 * in order to keep track of the respective parent node in the tree, which in
	 * case of inherited features is not the eContainer of the feature.
	 *
	 * The order of inherited features (and {@link EStructuralFeature}s and
	 * {@link EOperation}s within) cannot be set right now.
	 */
	@Override
	public Object[] getChildren(Object object) {
		List<Object> result = new ArrayList<>();

		if (object instanceof NonContainedChildWrapper) {
			return this.getChildren(((NonContainedChildWrapper) object).getNoncontainedChild());
		}

		if (AgteleEcoreContentProvider.inheritedContentVisible && object instanceof EClass) {
			EClass eClass = (EClass) object;

			//Display all inherited fields first
			for (EClass superClass : eClass.getEAllSuperTypes()) {
				for (EStructuralFeature feature : superClass.getEStructuralFeatures()) {
					result.add(new NonContainedChildWrapper(feature, object));
				}
			}
			//Display all inherited operations
			for (EClass superClass : eClass.getEAllSuperTypes()) {
				for (EOperation operation : superClass.getEOperations()) {
					result.add(new NonContainedChildWrapper(operation, object));
				}
			}
		}
		result.addAll(Arrays.asList(super.getChildren(object)));
		return result.toArray();
	}

	/**
	 * Standard {@link StateRestoringViewerContentProvider#hasChildren(Object)}.
	 * In case of a {@link NonContainedChildWrapper} it delegates to the
	 * {@link NonContainedChildWrapper#noncontainedChild}.
	 */
	@Override
	public boolean hasChildren(Object object) {
		if (object instanceof NonContainedChildWrapper) {
			return this.hasChildren(((NonContainedChildWrapper) object).getNoncontainedChild());
		}
		return super.hasChildren(object);
	}

	/**
	 * Sets the visibility of inherited {@link EClass} content and
	 * {@link #refreshViewers()} afterwards.
	 *
	 * @param visible
	 */
	static public void setInheritedContentVisibility(boolean visible) {
		AgteleEcoreContentProvider.inheritedContentVisible = visible;
		AgteleEcoreContentProvider.refreshViewers();
	}

	/**
	 * Iterates the {@link #instances} and {@link StructuredViewer#refresh()}es
	 * the views, if a view is available.
	 */
	static public void refreshViewers() {
		for (AgteleEcoreContentProvider provider : AgteleEcoreContentProvider.instances) {
			if (provider.viewer != null) {
				try {
					provider.viewer.refresh();
				} catch (Exception e) {
					//Do nothing
				}
			}
		}
	}

	/**
	 * This class is used for displaying children in a tree view, that are
	 * actually not contained in the object of the underlying model that is
	 * represented by the parent node in the tree.
	 *
	 * @author Baron
	 */
	public static class NonContainedChildWrapper {

		/**
		 * The child that usually is contained in another container.
		 */
		protected Object noncontainedChild;

		/**
		 * The parent node to which the non contained child is virtually
		 * displayed.
		 */
		protected Object parentNode;

		@SuppressWarnings("unused")
		private NonContainedChildWrapper() {
		}

		/**
		 * @param child
		 *            The child element to display
		 * @param parentNode
		 *            The parent node in the tree
		 */
		public NonContainedChildWrapper(Object child, Object parentNode) {
			this.noncontainedChild = child;
			this.parentNode = parentNode;
		}

		/**
		 * @return The child.
		 */
		public Object getNoncontainedChild() {
			return this.noncontainedChild;
		}

		/**
		 * @return The parent node.
		 */
		public Object getParentNode() {
			return this.parentNode;
		}
	}

	/**
	 * Queries the
	 * {@link ShowInheritedEcoreClassFeaturesCommandHandler#isVisible()}
	 *
	 * @return
	 */
	protected static boolean getCurrentVisibilityState() {
		return ShowInheritedEcoreClassFeaturesCommandHandler.isVisible();
	}
}
