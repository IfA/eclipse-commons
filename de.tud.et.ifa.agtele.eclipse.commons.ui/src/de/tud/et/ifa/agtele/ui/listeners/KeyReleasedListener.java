/**
 *
 */
package de.tud.et.ifa.agtele.ui.listeners;

import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;

/**
 * This interface extends the standard SWT {@link KeyListener} interface in a way that it provides a default
 * implementation for the {@link #keyPressed(KeyEvent)} method that does nothing. Thus, the user only has to implement
 * the {@link #keyReleased(KeyEvent)} method.
 *
 * @author mfreund
 */
public interface KeyReleasedListener extends KeyListener {

	@Override
	public default void keyPressed(KeyEvent e) {

		// do nothing when a key is released

	}
}
