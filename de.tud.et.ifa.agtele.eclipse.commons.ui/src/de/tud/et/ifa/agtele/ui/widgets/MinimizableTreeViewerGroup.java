package de.tud.et.ifa.agtele.ui.widgets;

import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

import de.tud.et.ifa.agtele.resources.BundleContentHelper;
import de.tud.et.ifa.agtele.ui.listeners.SelectionListener2;

/**
 * A {@link TreeViewerGroup} that can be minimized (and restored) when placed inside a {@link MinimizableSashForm}.
 *
 * @author mfreund
 *
 */
public class MinimizableTreeViewerGroup extends TreeViewerGroup implements IMinimizedHeightProvider, IMinimizable {

	/**
	 * The {@link Image} used for the icon of the 'minimize' button.
	 */
	private Image minimizeImage;

	/**
	 * The {@link Image} used for the icon of the 'restore' button.
	 */
	private Image restoreImage;

	/**
	 * This keeps track of the state of the control.
	 */
	private boolean isMinimized;

	/**
	 * The {@link ToolItem} that toggles the state of the tree viewer group between <em>minimized</em> and
	 * <em>normal</em>.
	 */
	private ToolItem minimizeItem;

	/**
	 * This stores the minimized height that is only calculate once.
	 */
	private int minimizedHeight;

	/**
	 * Use this constructor if you want to add a tool bar to the viewer.
	 *
	 * @param parent
	 *            The parent composite.
	 * @param adapterFactory
	 *            The adapter factory used to create label and content adapters.
	 * @param editingDomain
	 *            The editing domain that is used for the viewer.
	 * @param dialogSettings
	 *            The dialog settings belonging to the editor (e.g. XYZPlugin..getPlugin().getDialogSettings()).
	 * @param groupText
	 *            The label of the group widget that hold all other widgets. If this is null no surrounding group will
	 *            created.
	 * @param options
	 *            A set of options that are used to alter the default composition of the TreeViewerGroup
	 */
	public MinimizableTreeViewerGroup(MinimizableSashForm parent, ComposedAdapterFactory adapterFactory,
			EditingDomain editingDomain, IDialogSettings dialogSettings, String groupText,
			TreeViewerGroupOption... options) {
		super(parent, adapterFactory, editingDomain, dialogSettings, groupText, options);
	}

	@Override
	protected void createToolbar(Composite parent) {

		super.createToolbar(parent);

		if (this.toolbar == null) {
			// Create the button area
			this.toolbar = new ToolBar(parent, SWT.NONE);
			this.toolbar.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		}

		this.isMinimized = false;
		this.minimizedHeight = -1;
		this.minimizeImage = BundleContentHelper.getBundleImage(this.bundleID, "icons/minimize.gif");
		this.restoreImage = BundleContentHelper.getBundleImage(this.bundleID, "icons/restore.gif");

		this.minimizeItem = new ToolItem(this.toolbar, SWT.PUSH);
		this.minimizeItem.setImage(this.minimizeImage);
		this.minimizeItem.setToolTipText("Minimize");

		// either minimize or restore the control depending on the 'minimized' state
		this.minimizeItem.addSelectionListener((SelectionListener2) e -> {

			if (MinimizableTreeViewerGroup.this.isMinimized) {
				((MinimizableSashForm) MinimizableTreeViewerGroup.this.parent).restoreLayout();
			} else {
				((MinimizableSashForm) MinimizableTreeViewerGroup.this.parent)
						.minimizeControl(MinimizableTreeViewerGroup.this);
			}
		});

		if (this.toolbar != null) {
			this.toolbar.pack();
		}
	}

	@Override
	public void dispose() {

		this.minimizeImage.dispose();
		this.restoreImage.dispose();
		super.dispose();
	}

	@Override
	public int getMinimizedHeight() {

		if (this.minimizedHeight == -1) {
			// in the 'minimized' state, we do not want to display the tree viewer
			this.minimizedHeight = this.group.getSize().y - this.treeViewer.getTree().getSize().y;
		}

		return this.minimizedHeight;
	}

	@Override
	public void minimize() {

		this.isMinimized = true;
		this.minimizeItem.setImage(this.restoreImage);
		this.minimizeItem.setToolTipText("Restore");
	}

	@Override
	public void restore() {

		this.isMinimized = false;
		this.minimizeItem.setImage(this.minimizeImage);
		this.minimizeItem.setToolTipText("Minimize");
	}

}
