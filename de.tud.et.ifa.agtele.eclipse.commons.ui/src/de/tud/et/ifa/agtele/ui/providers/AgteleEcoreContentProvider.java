package de.tud.et.ifa.agtele.ui.providers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.ui.views.properties.IPropertySource;

import de.tud.et.ifa.agtele.ui.handlers.ShowInheritedEcoreClassFeaturesCommandHandler;

public class AgteleEcoreContentProvider extends StateRestoringViewerContentProvider {

	static protected List<AgteleEcoreContentProvider> instances = new ArrayList<>();

	static protected boolean inheritedContentVisible = true;

	public AgteleEcoreContentProvider(AdapterFactory adapterFactory, StructuredViewer structuredViewer) {
		super(adapterFactory, structuredViewer);

		AgteleEcoreContentProvider.instances.add(this);
		AgteleEcoreContentProvider.inheritedContentVisible = AgteleEcoreContentProvider.getCurrentVisibilityState();
	}

	@Override
	public IPropertySource getPropertySource(Object object) {
		if (object instanceof NonContainedChildWrapper) {
			return this.getPropertySource(((NonContainedChildWrapper) object).getNoncontainedChild());
		}
		return super.getPropertySource(object);
	}

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
				for (EOperation operation : superClass.getEAllOperations()) {
					result.add(new NonContainedChildWrapper(operation, object));
				}
			}
		}
		result.addAll(Arrays.asList(super.getChildren(object)));
		return result.toArray();
	}

	@Override
	public boolean hasChildren(Object object) {
		if (object instanceof NonContainedChildWrapper) {
			return this.hasChildren(((NonContainedChildWrapper) object).getNoncontainedChild());
		}
		return super.hasChildren(object);
	}

	static public void setInheritedContentVisibility(boolean visible) {
		AgteleEcoreContentProvider.inheritedContentVisible = visible;
		AgteleEcoreContentProvider.refreshViewers();
	}

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

		protected Object noncontainedChild;

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

	protected static boolean getCurrentVisibilityState() {
		return ShowInheritedEcoreClassFeaturesCommandHandler.isVisible();
	}
}
