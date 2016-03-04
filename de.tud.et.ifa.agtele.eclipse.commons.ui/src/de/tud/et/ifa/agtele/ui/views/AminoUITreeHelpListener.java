package de.tud.et.ifa.agtele.ui.views;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.events.HelpEvent;
import org.eclipse.swt.events.HelpListener;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

public class AminoUITreeHelpListener implements HelpListener {
	
	@Override
	public void helpRequested(HelpEvent e) {
		if (e.getSource() instanceof Tree) {
			Tree tree = (Tree) e.getSource();
			if (tree.getSelection().length == 1) {
				TreeItem ti = tree.getSelection()[0];
				if (ti.getData() instanceof EObject) {
					EObject selectedEObject = (EObject) ti.getData();
					EMFModelHelpView.showHelp(selectedEObject);
				}
				else {
					EMFModelHelpView.showIndex();
				}
			}
			else {
				EMFModelHelpView.showText("Please select only one element.");
			}
		}
		else {
			EMFModelHelpView.showIndex();
		}
	}
}
