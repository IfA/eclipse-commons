/**
 *
 */
package de.tud.et.ifa.agtele.ui.widgets;

import java.util.ArrayList;
import java.util.Arrays;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;

import de.tud.et.ifa.agtele.ui.listeners.KeyPressedListener;
import de.tud.et.ifa.agtele.ui.listeners.SelectionListener2;

/**
 * A widget that consists of a {@link Combo} holding a set of options to select
 * by the user, a {@link List} to display selected items, and a set of buttons
 * to add/delete items to/from the list or to change the order of the items.
 *
 * @author mfreund
 */
public class ManageableItemList extends Composite {

	/**
	 * The {@link Combo} holding the entries that can be selected by the user.
	 */
	protected Combo combo;

	/**
	 * The {@link List} showing the selected entries to the user.
	 */
	protected List list;

	/**
	 * A list of {@link ManageableItemListObserver observers} that will be
	 * notified about all changes made by the user (e.g. adding, removing, or
	 * reordering items in the {@link #list}).
	 */
	protected java.util.List<ManageableItemListObserver> observers;

	/**
	 * This creates an instance.
	 *
	 * @param parent
	 *            The parent composite to which this shall be added.
	 * @param style
	 *            The style to be used for the main composite.
	 * @param text
	 *            The text explaining the meaning of the items.
	 */
	public ManageableItemList(Composite parent, int style, String text) {
		super(parent, style);

		this.observers = new ArrayList<>();

		GridLayoutFactory.swtDefaults().numColumns(3).margins(0, 0).applyTo(this);

		// A label for the item selection combo
		//
		Label label = new Label(this, SWT.NONE);
		label.setText(text);

		// The combo to present the items to the user
		//
		this.combo = new Combo(this, SWT.DROP_DOWN | SWT.BORDER);
		this.combo.addModifyListener(e -> this.notifyObservers());
		GridDataFactory.swtDefaults().align(SWT.FILL, SWT.CENTER).grab(true, false).applyTo(this.combo);

		// A button that allows to add additional items to the list
		//
		Button addButton = new Button(this, SWT.NONE);
		addButton.setText("Add...");
		addButton.addSelectionListener((SelectionListener2) e -> this.handleAddButtonPressed());
		GridDataFactory.swtDefaults().align(SWT.FILL, SWT.CENTER).applyTo(addButton);

		// A scrolled composite that contains the list of items
		//
		ScrolledComposite scrolledComposite = new ScrolledComposite(this, SWT.V_SCROLL);
		scrolledComposite.setExpandVertical(true);
		scrolledComposite.setExpandHorizontal(true);
		GridDataFactory.swtDefaults().align(SWT.FILL, SWT.FILL).grab(true, false).span(2, 1).applyTo(scrolledComposite);

		// A list to display the selected items
		//
		this.list = new List(scrolledComposite, SWT.BORDER | SWT.MULTI);
		this.list.addKeyListener((KeyPressedListener) e -> {
			if (e.keyCode == SWT.DEL && this.list.getSelectionIndex() != -1) {

				this.handleDelButtonPressed();
			}
		});
		GridDataFactory.swtDefaults().align(SWT.FILL, SWT.FILL).grab(true, true).applyTo(this.list);

		scrolledComposite.setContent(this.list);
		scrolledComposite.setMinSize(this.list.computeSize(SWT.DEFAULT, SWT.DEFAULT));

		// A composite to host the 'Del', 'Up', and 'Down' buttons
		//
		Composite buttonComposite = new Composite(this, SWT.NONE);
		GridDataFactory.swtDefaults().align(SWT.FILL, SWT.FILL).grab(false, false).applyTo(buttonComposite);
		GridLayoutFactory.swtDefaults().margins(0, 0).applyTo(buttonComposite);

		// A button that allows to delete elements from the list
		//
		Button delButton = new Button(buttonComposite, SWT.NONE);
		delButton.setText("Del...");
		delButton.addSelectionListener((SelectionListener2) e -> this.handleDelButtonPressed());
		GridDataFactory.swtDefaults().align(SWT.FILL, SWT.CENTER).applyTo(delButton);

		// a button that allows to move elements up in the list
		//
		Button upSourceFileButton = new Button(buttonComposite, SWT.NONE);
		upSourceFileButton.setText("Up...");
		upSourceFileButton.addSelectionListener((SelectionListener2) e -> this.handleUpButtonPressed());
		GridDataFactory.swtDefaults().align(SWT.FILL, SWT.CENTER).applyTo(upSourceFileButton);

		// a button that allows to move elements down in the list
		//
		Button downSourceFileButton = new Button(buttonComposite, SWT.NONE);
		downSourceFileButton.setText("Down...");
		downSourceFileButton.addSelectionListener((SelectionListener2) e -> this.handleDownButtonPressed());
		GridDataFactory.swtDefaults().align(SWT.FILL, SWT.CENTER).applyTo(downSourceFileButton);

	}

	/**
	 * Add an {@link ManageableItemListObserver observer} that will be notified
	 * about any changes the user performs on this widget (e.g. selecting an
	 * item in the {@link #combo}, or adding/removing/reordering items in the
	 * {@link #list}.
	 *
	 * @param observer
	 *            The {@link ManageableItemListObserver observer} that will be
	 *            notified about changes (by calling
	 *            {@link ManageableItemListObserver#changed(ManageableItemList)}.
	 */
	public void addObserver(ManageableItemListObserver observer) {
		this.observers.add(observer);
	}

	/**
	 * Update the items displayed to the user in the {@link #combo}.
	 *
	 * @param items
	 *            The items to display.
	 */
	public void setSelectableItems(java.util.List<String> items) {
		this.combo.setItems(items.toArray(new String[] {}));
	}

	/**
	 * Returns text currently displayed in the {@link #combo}.
	 *
	 * @return The text currently displayed in the {@link #combo}.
	 */
	public String getComboText() {
		return this.combo.getText();
	}

	/**
	 * Add an item to be displayed to the user in the {@link #combo}.
	 *
	 * @param item
	 *            The item to add.
	 */
	public void addSelectableItem(String item) {
		this.combo.add(item);
	}

	/**
	 * Return the items that can be selected by the user via the {@link #combo}.
	 *
	 * @return The items as displayed in the {@link #combo}.
	 */
	public java.util.List<String> getSelectableItems() {
		return Arrays.asList(this.combo.getItems());
	}

	/**
	 * Select the given <em>item</em> in the {@link #combo}.
	 *
	 * @param item
	 *            The item to select.
	 * @return '<em>True</em>' if the item was selected; '<em>false</em>'
	 *         otherwise (i.e. the given item is not one of the items of the
	 *         {@link #combo}).
	 */
	public boolean select(String item) {
		if (this.combo.indexOf(item) < 0) {
			return false;
		}

		this.combo.select(this.combo.indexOf(item));
		return true;
	}

	/**
	 * Select the item with the given index in the {@link #combo}.
	 *
	 * @param index
	 *            The index to select.
	 */
	public void select(int index) {
		this.combo.select(index < 0 ? 0 : index);
	}

	/**
	 * Get the index of the given <em>item</em> in the {@link #combo}.
	 *
	 * @param item
	 *            The item for which the index shall be retrieved.
	 * @return The index of the item or '-1' if the item is not part of the
	 *         items of the {@link #combo}.
	 */
	public int indexOf(String item) {
		return this.combo.indexOf(item);
	}

	/**
	 * Return the items selected and arranged by the user via the {@link #list}.
	 *
	 * @return The items as displayed in the {@link #list}.
	 */
	public java.util.List<String> getSelectedItems() {
		return Arrays.asList(this.list.getItems());
	}

	/**
	 * Set the items shown to the user via the {@link #list}.
	 *
	 * @param items
	 *            The list of items to set.
	 */
	public void setSelectedItems(java.util.List<String> items) {
		this.list.removeAll();
		items.stream().forEach(this.list::add);
	}

	/**
	 * Adds an item to the {@link #list}.
	 *
	 * @param item
	 *            The item to add.
	 */
	public void addSelectedItem(String item) {
		this.list.add(item);
	}

	/**
	 * Clears the items shown to the user in the {@link #list}.
	 *
	 */
	public void clearSelectedItems() {
		this.list.removeAll();
	}

	@Override
	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled);

		this.combo.setEnabled(enabled);
		this.list.setEnabled(enabled);
	}

	/**
	 * Add the item specified in the {@link #combo} to the {@link #list}.
	 */
	protected void handleAddButtonPressed() {

		this.list.add(this.combo.getText());
		this.list.deselectAll();
		this.list.select(this.list.getItemCount() - 1);
		this.notifyObservers();

	}

	/**
	 * Delete all elements that are selected in the {@link #list}.
	 */
	protected void handleDelButtonPressed() {

		int selected = this.list.getSelectionIndex();
		this.list.remove(this.list.getSelectionIndices());
		this.list.select(selected > this.list.getItemCount() - 1 ? this.list.getItemCount() - 1 : selected);
		this.notifyObservers();
	}

	/**
	 * Move all elements up that are selected in the {@link #list}.
	 */
	protected void handleUpButtonPressed() {

		for (int selected : this.list.getSelectionIndices()) {
			if (selected == 0) {
				return;
			}
			String[] items = this.list.getItems();
			String prevItem = this.list.getItem(selected - 1);
			items[selected - 1] = this.list.getItem(selected);
			items[selected] = prevItem;
			int[] currentSel = this.list.getSelectionIndices();
			this.list.setItems(items);
			this.list.select(currentSel);
			this.list.deselect(selected);
			this.list.select(selected - 1);
		}

		this.notifyObservers();
	}

	/**
	 * Move all elements down that are selected in the {@link #list}.
	 */
	protected void handleDownButtonPressed() {

		int[] selections = this.list.getSelectionIndices();
		for (int i = selections.length - 1; i >= 0; i--) {
			int sel = selections[i];
			if (sel == this.list.getItemCount() - 1) {
				return;
			}
			String[] items = this.list.getItems();
			String nextItem = this.list.getItem(sel + 1);
			items[sel + 1] = this.list.getItem(sel);
			items[sel] = nextItem;
			int[] currentSel = this.list.getSelectionIndices();
			this.list.setItems(items);
			this.list.select(currentSel);
			this.list.deselect(sel);
			this.list.select(sel + 1);
		}

		this.notifyObservers();
	}

	/**
	 * Notify all {@link #observers} about a change that was made by the user.
	 *
	 */
	protected void notifyObservers() {
		this.observers.stream().forEach(o -> o.changed(this));
	}

	/**
	 * A simple functional interface that allows to be informed about any change
	 * made to a {@link ManageableItemList} by the user.
	 *
	 * @author mfreund
	 */
	@FunctionalInterface
	public interface ManageableItemListObserver {

		/**
		 * This is called in order to inform the observer about a change to a
		 * {@link ManageableItemList} it observes.
		 *
		 * @param source
		 *            The {@link ManageableItemList} that issued the change.
		 */
		public void changed(ManageableItemList source);
	}
}
