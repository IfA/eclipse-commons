/*******************************************************************************
 * Copyright (C) 2016-2018 Institute of Automation, TU Dresden.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Institute of Automation, TU Dresden - initial API and implementation
 ******************************************************************************/
package de.tud.et.ifa.agtele.ui.widgets;

import java.util.ArrayList;
import java.util.Arrays;

import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Sash;

import de.tud.et.ifa.agtele.ui.interfaces.IPersistable;

/**
 * A {@link SashForm} that supports minimizing of controls. Currently, this only
 * supports a single minimized child control.
 *
 * @author mfreund
 */
public class MinimizableSashForm extends SashForm implements IPersistable {

	/**
	 * The default height of minimized controls.
	 */
	private int defaultMinHeight = 18;

	/**
	 * The currently minimized {@link Control} in this sash form or
	 * '<em>null</em>' if no control is minimized.
	 */
	private Control minimizedControl;

	/**
	 * The listener that adjusts the weights if the sash form is resized.
	 */
	private Listener resizeListener;

	/**
	 * A listener that restores default behavior (no child is minimized) by
	 * removing the {@link #resizeListener}.
	 */
	private Listener removeResizeListenerListener;

	/**
	 * This creates an instance.
	 *
	 * @param parent
	 *            A widget which will be the parent of the new instance (cannot
	 *            be null).
	 * @param style
	 *            The style of widget to construct.
	 */
	public MinimizableSashForm(Composite parent, int style) {
		super(parent, style);
		this.minimizedControl = null;
		this.resizeListener = null;

		// this listener will be attached to every sash inside the sash form
		this.removeResizeListenerListener = event -> {
			if (MinimizableSashForm.this.minimizedControl != null) {
				if (MinimizableSashForm.this.resizeListener != null) {
					MinimizableSashForm.this.removeListener(SWT.Resize, MinimizableSashForm.this.resizeListener);
				}
				if (MinimizableSashForm.this.minimizedControl instanceof IMinimizable) {
					((IMinimizable) MinimizableSashForm.this.minimizedControl).restore();
				}
				MinimizableSashForm.this.minimizedControl = null;
				MinimizableSashForm.this.resizeListener = null;
			}
		};
	}

	/**
	 * This is the getter for the {@link #minimizedControl}.
	 *
	 * @return The currently minimized control in this sash form or
	 *         '<em>null</em>' if no control is minimized.
	 */
	public Control getMinimizedControl() {
		return this.minimizedControl;
	}

	/**
	 * Minimizes the given '<em>control</em>'.
	 *
	 * @param control
	 *            The {@link Control} to be minimized. If this is
	 *            '<em>null</em>', the method will return without doing
	 *            anything.
	 */
	public void minimizeControl(final Control control) {

		if (control == null || this.minimizedControl != null && this.minimizedControl.equals(control)) {
			return;
		}

		// minimize the control
		this.minimizedControl = control;
		this.setWeights(this.calculateWeights(control));
		for (Control child : this.getChildren()) {
			if (child instanceof IMinimizable) {
				if (control.equals(child)) {
					((IMinimizable) child).minimize();
				} else {
					((IMinimizable) child).restore();
				}
			}
		}

		// ensure that the control stays minimized even when the sash form is
		// resized
		if (this.resizeListener != null) {
			// remove the listener from the previously minimized control
			this.removeListener(SWT.Resize, this.resizeListener);
		}
		this.resizeListener = e -> MinimizableSashForm.this
				.setWeights(MinimizableSashForm.this.calculateWeights(control));
		this.addListener(SWT.Resize, this.resizeListener);

		// add a listener to each contained sash that ensures that above
		// 'resizeListener' is removed when the user
		// manually moves the sash
		for (int i = 0; i < this.getChildren().length; i++) {
			Control child = this.getChildren()[i];
			if (child instanceof Sash
					&& !Arrays.asList(child.getListeners(SWT.DragDetect)).contains(this.removeResizeListenerListener)) {
				child.addListener(SWT.DragDetect, this.removeResizeListenerListener);
			}
		}
	}

	/**
	 * Restores the layout (no control is minimized).
	 */
	public void restoreLayout() {

		if (this.resizeListener != null) {
			this.removeListener(SWT.Resize, this.resizeListener);
		}
		for (Control child : this.getChildren()) {
			if (child instanceof IMinimizable) {
				((IMinimizable) child).restore();
			}
		}
		this.minimizedControl = null;
		this.resizeListener = null;

		this.setWeights(this.calculateWeights(null));
	}

	/**
	 * Calculate the weights so that the given '<em>controlToBeMinimized</em>'
	 * is minimized. If 'controlToBeMinimized' is '<em>null</em>', default
	 * weights will be returned to generate a non-minimized layout.
	 *
	 * @param controlToBeMinimized
	 *            The control to be minimized or '<em>null</em>' if no control
	 *            shall be minimized so that the default layout is restored.
	 * @return The calculated weights.
	 */
	private int[] calculateWeights(Control controlToBeMinimized) {

		/*
		 * determine the 'real' children (excluding sashes) that have to be
		 * considered when the weights are calculated
		 */
		ArrayList<Control> realChildren = new ArrayList<>();
		for (Control child : this.getChildren()) {
			if (!(child instanceof Sash)) {
				realChildren.add(child);
			}
		}

		if (realChildren.isEmpty()) {
			return this.getWeights();
		}

		int[] weights = new int[realChildren.size()];
		if (controlToBeMinimized == null || !realChildren.contains(controlToBeMinimized)) {
			// use a default layout
			for (int i = 0; i < realChildren.size(); i++) {
				weights[i] = 1;
			}

		} else {
			// determine the weights so that height of the 'minimizedControl'
			// will be 'minimized'
			int minHeight = controlToBeMinimized instanceof IMinimizedHeightProvider
					? ((IMinimizedHeightProvider) controlToBeMinimized).getMinimizedHeight() : this.defaultMinHeight;
			for (int i = 0; i < realChildren.size(); i++) {
				Control child = realChildren.get(i);

				if (controlToBeMinimized.equals(child)) {
					weights[i] = minHeight;
				} else {
					weights[i] = (this.getClientArea().height - minHeight) / (realChildren.size() - 1);
				}

			}
		}

		return weights;
	}

	@Override
	public void dispose() {

		// remove the 'removeResizeListenerListener' from each sash
		for (int i = 0; i < this.getChildren().length; i++) {

			if (this.getChildren()[i] instanceof Sash
					&& Arrays.asList(this.getChildren()[i].getListeners(SWT.DragDetect))
							.contains(this.removeResizeListenerListener)) {

				this.getChildren()[i].removeListener(SWT.DragDetect, this.removeResizeListenerListener);
			}
		}

		super.dispose();
	}

	@Override
	public void persist(IDialogSettings settings) {

		// Persist the minimized control and/or the weights
		//
		String minimized = "";

		if (this.minimizedControl != null) {
			minimized = Integer.toString(Arrays.asList(this.getChildren()).indexOf(this.minimizedControl));
		}

		if (minimized.isEmpty()) {
			settings.put("MINIMIZED_CONTROL", "");
			settings.put("WEIGHTS", Arrays.stream(this.getWeights()).mapToObj(String::valueOf).toArray(String[]::new));
		} else {
			settings.put("MINIMIZED_CONTROL", minimized);
			settings.put("WEIGHTS", "");
		}
	}

	@Override
	public void restore(IDialogSettings settings) {

		// Restore the minimized control and/or the weights
		//
		String minimizedControl = settings.get("MINIMIZED_CONTROL");
		if (minimizedControl != null && !minimizedControl.isEmpty()) {
			try {
				int index = Integer.parseInt(minimizedControl);
				Control control = Arrays.asList(this.getChildren()).get(index);
				this.minimizeControl(control);
			} catch (NumberFormatException | IndexOutOfBoundsException e) {
				// do nothing
			}
		} else {
			this.restoreLayout();
			String[] weights = settings.getArray("WEIGHTS");
			if (weights != null) {
				try {
					int[] parsedWeights = new int[weights.length];
					for (int i = 0; i < weights.length; i++) {
						parsedWeights[i] = Integer.parseInt(weights[i]);
					}
					this.setWeights(parsedWeights);
				} catch (NumberFormatException e) {
					// do nothing
				}
			}
		}
	}

}
