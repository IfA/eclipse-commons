/**
 *
 */
package de.tud.et.ifa.agtele.ui.listeners;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EGenericType;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.jface.viewers.ITreeSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.TreeItem;

import de.tud.et.ifa.agtele.ui.providers.AgteleEcoreContentProvider.NonContainedChildWrapper;

/**
 * A {@link SelectionListener} that operates on a {@link TreeViewer} and jumps to a suitable target if the user clicks
 * on an element in the tree while holding a defined set of modifiers.
 * <p />
 * <b>Note:</b> The default modifier is {@link SWT#CTRL} but custom modifiers can be defined via the constructor
 * {@link #BasicJumpOnClickListener(TreeViewer, int)}.
 * <p />
 * <b>Note:</b> Clients can implement specific logic for determining a jump target by overriding
 * {@link #determineJumpTarget(Object)}.
 *
 * @author mfreund
 */
public class BasicJumpOnClickListener implements SelectionListener2 {

	/**
	 * The {@link TreeViewer} that this listener shall operate on, i.e. that is used to detect selections and to jump to
	 * suitable targets.
	 */
	private TreeViewer treeViewer;

	/**
	 * The required state of the keyboard modifier keys and mouse masks to trigger the 'jumping' (this will be compared
	 * to the {@link SelectionEvent#stateMask stateMask} of registered {@link SelectionEvent SelectionEvents}).
	 */
	private final int modifierMask;

	/**
	 * This creates an instance that operates on the given <em>treeViewer</em> and listens to 'CTRL'-clicks.
	 * <p />
	 * This constructor is equivalent to <em>BasicJumpOnCtrlClickListener(TreeViewer, SWT.CTRL)</em>.
	 *
	 * @see #BasicJumpOnClickListener(TreeViewer, int)
	 *
	 * @param treeViewer
	 *            The {@link TreeViewer} that this listener shall operate on, i.e. that is used to detect selections and
	 *            to jump to suitable targets.
	 *
	 */
	public BasicJumpOnClickListener(TreeViewer treeViewer) {
		this(treeViewer, SWT.CTRL);
	}

	/**
	 * This creates an instance that operates on the given <em>treeViewer</em> and uses the given <em>modifierMask</em>
	 * to determine whether a jump shall be triggered.
	 *
	 * @see #BasicJumpOnClickListener(TreeViewer)
	 *
	 * @param treeViewer
	 *            The {@link TreeViewer} that this listener shall operate on, i.e. that is used to detect selections and
	 *            to jump to suitable targets.
	 * @param modifierMask
	 *            The required state of the keyboard modifier keys and mouse masks to trigger the 'jumping'.
	 *
	 */
	public BasicJumpOnClickListener(TreeViewer treeViewer, int modifierMask) {
		this.treeViewer = treeViewer;
		this.modifierMask = modifierMask;
	}

	@Override
	public void widgetSelected(SelectionEvent e) {

		if (this.treeViewer == null || this.treeViewer.getTree().isDisposed()) {
			return;
		}

		if ((e.stateMask & this.modifierMask) == 0 || !(e.item instanceof TreeItem)) {
			return;
		}

		Object selectedElement = ((TreeItem) e.item).getData();

		if (selectedElement == null) {
			return;
		}

		ITreeSelection previousSelection = this.treeViewer.getStructuredSelection();

		// Check if the user selected a single element
		//
		if (previousSelection.size() > 1) {
			return;
		}
		if (!previousSelection.isEmpty() && !selectedElement.equals(previousSelection.getFirstElement())) {
			return;
		}

		// Check if the user selected an element where we need to act on and, if so, determine the
		// target that we will jump to
		//
		Object target = this.determineJumpTarget(selectedElement);

		if (target == null) {
			return;
		}

		// Try to 'jump to' (select) the determined target
		//
		this.treeViewer.setSelection(new StructuredSelection(target));

	}

	/**
	 * This determines the target that we shall 'jump' to based on the user's selection.
	 * <p />
	 * This base implementation realizes the following deductions:
	 * <ul>
	 * <li>In case an {@link EReference} is selected, the {@link EReference#getEReferenceType() reference type} will be
	 * returned.</li>
	 * <li>In case an {@link EGenericType} is selected, the {@link EGenericType#getEClassifier() classifier that it
	 * represents} will be returned.</li>
	 * </ul>
	 * <p />
	 * <b>Note:</b> Clients can override this in order to implement specific logic for determining a jump target.
	 *
	 * @param selected
	 *            The element selected by the user.
	 * @return The determined target or '<em><b>null</b></em>' if no suitable target could be determined/no jump shall
	 *         be performed.
	 */
	protected Object determineJumpTarget(Object selected) {

		Object target = null;

		if (selected instanceof EReference) {
			target = ((EReference) selected).getEReferenceType();
		} else if (selected instanceof EGenericType) {
			target = ((EGenericType) selected).getEClassifier();
		} else if (selected instanceof EAttribute && ((EAttribute) selected).getEAttributeType() instanceof EEnum) {
			target = ((EAttribute) selected).getEAttributeType();
		} else if (selected instanceof NonContainedChildWrapper) {
			target = ((NonContainedChildWrapper) selected).getNoncontainedChild();
		}

		return target;
	}

}