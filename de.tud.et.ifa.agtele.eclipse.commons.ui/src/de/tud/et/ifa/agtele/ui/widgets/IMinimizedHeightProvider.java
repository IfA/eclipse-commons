package de.tud.et.ifa.agtele.ui.widgets;

/**
 * This interface may be implemented by {@link IMinimizable minimizable controls} that specify a specific <em>minimized
 * height</em>, i.e. their preferred height in a <em>minimized</em> state. If a {@link IMinimizable minimizable control}
 * does not implement this interface, the parent control will assign a <em>default minimized height</em>.
 *
 * @see IMinimizable
 *
 * @author mfreund
 */
@FunctionalInterface
public interface IMinimizedHeightProvider {

	/**
	 * Return the preferred height that the control should have in <em>minimized</em> state.
	 *
	 * @return The preferred height of the control in <em>minimized</em> state.
	 */
	public int getMinimizedHeight();
}
