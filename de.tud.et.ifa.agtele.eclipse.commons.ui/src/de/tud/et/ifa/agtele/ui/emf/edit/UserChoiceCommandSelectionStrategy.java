package de.tud.et.ifa.agtele.ui.emf.edit;

import java.util.ArrayList;

import org.eclipse.emf.common.command.AbstractCommand;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import de.tud.et.ifa.agtele.emf.edit.ICommandSelectionStrategy;
import de.tud.et.ifa.agtele.ui.util.UIHelper;

/**
 * This implements {@link ICommandSelectionStrategy} in a way that the various possibilities are
 * presented to the user via a pop-up.
 *  
 * @author mfreund
 */
public class UserChoiceCommandSelectionStrategy implements ICommandSelectionStrategy {

	@Override
	public AbstractCommand selectCommandToExecute(ArrayList<AbstractCommand> commands) {
		
		Menu menu = new Menu(UIHelper.getShell(), SWT.POP_UP);
		
		// We make use of a list as this allows us to specify a command in the listener although it is final
		//
		final ArrayList<AbstractCommand> ret = new ArrayList<>();
		
		// Create a menu item for each command
		//
		for (AbstractCommand command : commands) {
			MenuItem item = new MenuItem(menu, SWT.PUSH);
			item.setText(command.getLabel());
			item.addListener(SWT.Selection, e -> ret.add(command));			
		}
		
		// The menu is located at the position of the cursor
		//
		Point cursor = Display.getCurrent().getCursorLocation();
		menu.setLocation(cursor.x, cursor.y);
		
		// Open the menu and wait until the user selects an entry or clicks somewhere else 
		//
		menu.setVisible(true);
		while (!menu.isDisposed () && menu.isVisible()) {
			if (!Display.getDefault().readAndDispatch()) Display.getDefault().sleep();
		}
		while (Display.getDefault().readAndDispatch()); // needed, to get the selection event, which is fired AFTER the menu is hidden
		menu.dispose ();

		
		return (ret.isEmpty() ? null : ret.get(0));
	}
}
