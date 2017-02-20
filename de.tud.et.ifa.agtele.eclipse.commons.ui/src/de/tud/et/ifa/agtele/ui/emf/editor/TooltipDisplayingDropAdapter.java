/**
 *
 */
package de.tud.et.ifa.agtele.ui.emf.editor;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.edit.command.DragAndDropCommand;
import org.eclipse.emf.edit.command.DragAndDropFeedback;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.ui.dnd.EditingDomainViewerDropAdapter;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.ToolTip;
import org.eclipse.ui.progress.UIJob;

/**
 * A special {@link EditingDomainViewerDropAdapter} that displays a {@link ToolTip} to notify the user about the command
 * that will be executed on a 'Drop' event.
 *
 * @author mfreund
 */
public class TooltipDisplayingDropAdapter extends EditingDomainViewerDropAdapter {

	/**
	 * The {@link ToolTip} that will notify the user about the commands to be executed after a 'drop'.
	 */
	protected ToolTip toolTip;

	/**
	 * The {@link Shell} on which the {@link #toolTip} will be displayed.
	 */
	protected Shell toolTipShell;

	/**
	 * The x position of the last mouse move.
	 */
	protected int lastX;

	/**
	 * The y position of the last mouse move.
	 */
	protected int lastY;

	/**
	 * The {@link UIJob} that we use to show the {@link #toolTip} with a certain delay.
	 */
	protected UIJob showToolTipJob;

	/**
	 * The internal variable that controls if the tooltip should be shown.
	 */
	protected boolean showToolTip;

	/**
	 * This creates an instance with a given domain and viewer.
	 *
	 * @param domain
	 * @param viewer
	 */
	public TooltipDisplayingDropAdapter(EditingDomain domain, Viewer viewer) {
		super(domain, viewer);
	}

	@Override
	public void dragEnter(DropTargetEvent event) {

		super.dragEnter(event);

		this.showToolTip = false;

		// Prepare the tooltip
		//
		this.toolTipShell = new Shell();
		this.toolTip = new ToolTip(this.toolTipShell, SWT.BORDER);
		this.toolTip.setAutoHide(true);
		this.updateToolTip(event);

		// Show the tooltip after one second
		//
		this.showToolTipJob = new UIJob("show dnd tooltip") {

			@Override
			public IStatus runInUIThread(IProgressMonitor monitor) {

				if (!TooltipDisplayingDropAdapter.this.showToolTip) {
					TooltipDisplayingDropAdapter.this.showToolTip = true;
					TooltipDisplayingDropAdapter.this.showToolTip();
				}
				return Status.OK_STATUS;
			}
		};
		this.showToolTipJob.schedule(100);
	}

	@Override
	public void dragOver(DropTargetEvent event) {

		// Only update the tooltip if the user moved the mouse (this reduces unnecessary redraws)
		//
		if (this.lastX != event.x || this.lastY != event.y) {
			this.updateToolTip(event);

			// Reset the timer to show the tooltip
			//
			if (!this.showToolTip) {
				this.showToolTipJob.cancel();
				this.showToolTipJob.schedule(1000);
			}
		}

		super.dragOver(event);
	}

	@Override
	public void dragLeave(DropTargetEvent event) {

		this.showToolTip = false;
		this.showToolTipJob.cancel();
		this.toolTip.setVisible(false);
		this.toolTip.dispose();
		this.toolTipShell.dispose();

		super.dragLeave(event);
	}

	/**
	 * This updates the position and text of the {@link ToolTip}.
	 *
	 * @param event
	 *            The {@link DropTargetEvent} based on which the tooltip shall be updated.
	 */
	private void updateToolTip(DropTargetEvent event) {

		this.toolTip.setVisible(false);

		String label;

		if (this.command instanceof DragAndDropCommand) {
			int operation = ((DragAndDropCommand) this.command).getOperation();
			if (operation == DragAndDropFeedback.DROP_MOVE) {
				label = "Move here";
				label += ((DragAndDropCommand) this.command).getLocation() < 0.5 ? " (Above)" : " (Below)";
			} else if (operation == DragAndDropFeedback.DROP_COPY) {
				label = "Copy here";
				label += ((DragAndDropCommand) this.command).getLocation() < 0.5 ? " (Above)" : " (Below)";
			} else {
				label = this.command.getLabel();
			}
		} else {
			label = this.command.getLabel();
		}

		if (label == null || label.isEmpty()) {
			label = "Drag and Drop";
		}

		this.toolTip.setMessage(label);
		this.toolTip.setLocation(event.x + 15, event.y + 10);
		this.lastX = event.x;
		this.lastY = event.y;

		this.showToolTip();
	}

	/**
	 * This makes the tooltip visible if {@link #showToolTip} is '<em>true</em>' and if the {@link #command} is
	 * executable.
	 *
	 */
	private void showToolTip() {

		if (this.showToolTip && this.command.canExecute()) {
			this.toolTip.setVisible(true);
		}
	}
}