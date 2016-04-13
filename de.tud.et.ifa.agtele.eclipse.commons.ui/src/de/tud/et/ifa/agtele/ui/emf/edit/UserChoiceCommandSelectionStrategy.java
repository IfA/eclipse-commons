package de.tud.et.ifa.agtele.ui.emf.edit;

import java.util.ArrayList;

import org.eclipse.emf.common.command.AbstractCommand;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.PopupList;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;

import de.tud.et.ifa.agtele.emf.edit.ICommandSelectionStrategy;
import de.tud.et.ifa.agtele.ui.util.UIHelper;

public class UserChoiceCommandSelectionStrategy implements ICommandSelectionStrategy {

	public UserChoiceCommandSelectionStrategy() {
	}

	@Override
	public AbstractCommand selectCommandToExecute(ArrayList<AbstractCommand> commands) {
		
        Point cursor = Display.getCurrent().getCursorLocation();
		
		Menu menu = new Menu (UIHelper.getShell(), SWT.POP_UP);
		final ArrayList<AbstractCommand> ret = new ArrayList<>();
		
		for (AbstractCommand command : commands) {
			MenuItem item = new MenuItem (menu, SWT.PUSH);
			item.setText (command.getLabel());
			item.addListener (SWT.Selection, e -> ret.add(command));			
		}
		menu.setLocation (cursor.x, cursor.y);
		menu.setVisible (true);
		while (!menu.isDisposed () && menu.isVisible ()) {
			if (!Display.getDefault().readAndDispatch ()) Display.getDefault().sleep ();
		}
		while (Display.getDefault().readAndDispatch()); // needed, to get the selection event, which is fired AFTER the menu is hidden
		menu.dispose ();

		
		
		return (ret.isEmpty() ? null : ret.get(0));
	}
}
