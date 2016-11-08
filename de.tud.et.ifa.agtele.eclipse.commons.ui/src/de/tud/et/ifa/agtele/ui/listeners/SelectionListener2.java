package de.tud.et.ifa.agtele.ui.listeners;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;

/**
 * This interface extends the standard SWT 'SelectionListener' interface
 * in a way that it provides a default implementation for the
 * 'widgetDefaultSelected' method that does nothing. Thus, the user only
 * has to implement the 'widgetSelected' method.
 *
 * @author mfreund
 */
@FunctionalInterface
public interface SelectionListener2 extends SelectionListener {

	@Override
	public default void widgetDefaultSelected(SelectionEvent e) {
		// do nothing on default selection
	}
}
