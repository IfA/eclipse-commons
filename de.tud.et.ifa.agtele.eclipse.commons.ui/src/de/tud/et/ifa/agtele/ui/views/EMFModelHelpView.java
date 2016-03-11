package de.tud.et.ifa.agtele.ui.views;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.*;

import de.tud.et.ifa.agtele.emf.AgteleEcoreUtil;
import de.tud.et.ifa.agtele.help.IEMFModelHelpItemProvider;
import de.tud.et.ifa.agtele.resources.BundleContentHelper;
import de.tud.et.ifa.agtele.ui.AgteleUIPlugin;

import java.util.Iterator;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.*;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.events.HelpEvent;

/**
 * This view displays Amino UIs help contents
 */

public class EMFModelHelpView extends ViewPart implements IPersistable {

	/**
	 * The ID of the view as specified by the extension.
	 */
	public static final String ID = "de.tud.et.ifa.agtele.ui.views.EMFModelHelpView";

	private Browser browser;
	private Action linkAction;

	private String currentText;
	
	private ISelectionListener selectionListener;

	private Boolean linkEditor;

	/**
	 * The constructor.
	 */
	public EMFModelHelpView() {
		selectionListener = new ISelectionListener() {

			@Override
			public void selectionChanged(IWorkbenchPart part, ISelection selection) {
				showHelp(selection);
			}
		};

		linkEditor = AgteleUIPlugin.getPlugin().getDialogSettings().getBoolean("link");
		if (linkEditor)
			PlatformUI.getWorkbench().getActiveWorkbenchWindow().getSelectionService()
					.addSelectionListener(selectionListener);

	}

	/**
	 * This is a callback that will allow us to create the viewer and initialize
	 * it.
	 */
	@Override
	public void createPartControl(Composite parent) {
		browser = new Browser(parent, SWT.NONE);

		makeActions();
		contributeToActionBars();
		
		currentText = AgteleUIPlugin.getPlugin().getDialogSettings().get("browserText");
		if (currentText != null)
			browser.setText(currentText);
		if (linkEditor) {
			show();
		}
	}

	/**
	 * Passing the focus request to the viewer's control.
	 */
	@Override
	public void setFocus() {
		browser.setFocus();
	}

	@Override
	public void dispose() {
		PlatformUI.getWorkbench().getActiveWorkbenchWindow().getSelectionService()
				.removeSelectionListener(selectionListener);
		AgteleUIPlugin.getPlugin().getDialogSettings().put("link", linkEditor);
		
		AgteleUIPlugin.getPlugin().getDialogSettings().put("browserText", currentText);
		
		super.dispose();
	}

	private void contributeToActionBars() {
		IActionBars bars = getViewSite().getActionBars();
		fillLocalToolBar(bars.getToolBarManager());
	}

	private void fillLocalToolBar(IToolBarManager manager) {
		manager.add(linkAction);
	}

	private void makeActions() {
		linkAction = new Action("Link Editor",
				BundleContentHelper.getBundleImageDescriptor(AgteleUIPlugin.PLUGIN_ID, "icons/synced.gif")) {
			public void run() {
				linkEditor = !linkEditor;
				if (linkEditor) {
					PlatformUI.getWorkbench().getActiveWorkbenchWindow().getSelectionService()
							.addSelectionListener(selectionListener);
				} else {
					PlatformUI.getWorkbench().getActiveWorkbenchWindow().getSelectionService()
							.removeSelectionListener(selectionListener);
				}
				linkAction.setChecked(linkEditor);
			}
		};
		linkAction.setToolTipText("Link with Selection");
		linkAction.setChecked(linkEditor);
	}

	/**
	 * Opens the view and shows help based on the current selection
	 */
	public static void show() {
		showHelp(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getSelectionService().getSelection());
	}

	/**
	 * Opens the {@link EMFModelHelpView}, generates the help page for the given
	 * AminoUI {@link EObject} and displays it
	 * 
	 * @param eObject
	 *            AminoUI model element
	 */
	public static void showHelp(EObject eObject) {
		showText(getHtml(eObject));
	}

	/**
	 * Opens the {@link EMFModelHelpView} and displays text in it
	 * 
	 * @param text
	 *            text to be displayed
	 */
	public static void showText(String text) {
		try {
			EMFModelHelpView helpView = (EMFModelHelpView) PlatformUI.getWorkbench().getActiveWorkbenchWindow()
					.getActivePage().showView(ID, null, IWorkbenchPage.VIEW_VISIBLE);
			
			if (!text.equals(helpView.currentText)) {
				helpView.browser.setText(text);
				helpView.currentText = text;				
			}
			else return;
		} catch (PartInitException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Opens the {@link EMFModelHelpView} and shows the index help page
	 */
	public static void showIndex() {
		// TODO create index.html that explains how to use this help
		showText("EMF Model Help Index");
	}

	/**
	 * Determine the current selection and show the help if it is an
	 * {@link EObject}
	 * 
	 * @param selection
	 */
	private static void showHelp(ISelection selection) {
		if (selection instanceof StructuredSelection) {
			StructuredSelection structuredSelection = (StructuredSelection) selection;
			// show Help if one EObject is selected
			if (structuredSelection.size() == 1) {
				if (structuredSelection.getFirstElement() instanceof EObject) {
					EMFModelHelpView.showHelp((EObject) structuredSelection.getFirstElement());
				}
			}
			// else show nothing new
			else {
				Iterator<?> it = structuredSelection.iterator();
				while (it.hasNext()) {
					Object type = it.next();
					if (!(type instanceof EObject)) {
						return;
					}
				}
				if (!structuredSelection.isEmpty()) {
					EMFModelHelpView.showText(AgteleUIPlugin.getPlugin().getString("_UI_EMFModelHelpView_TooManyElementsSelected"));
				}
			}
		}
	}

	/**
	 * Generates the documentation page of an {@link EObject} based on the {@link IEMFModelHelpItemProvider} matching the element
	 * 
	 * @param eObject
	 * @return
	 */
	private static String getHtml(EObject eObject) {
		IEMFModelHelpItemProvider iEMFModelHelpItemProvider;
		// Try finding the IEMFModelHelpItemProvider using the AdapterFactory
		Adapter adapter = AgteleEcoreUtil.getAdapterFactoryItemDelegatorFor(eObject).getAdapterFactory().adapt(eObject, IEMFModelHelpItemProvider.class);
		// If it's not found, try it again using a different ItemProvider, like the IEditingDomainItemProvider that should alway be there
		if (adapter == null) {
			adapter = AgteleEcoreUtil.getAdapterFactoryItemDelegatorFor(eObject).getAdapterFactory().adapt(eObject, IEditingDomainItemProvider.class);
		}
		// Now check if the adapter that was found implements its own IEMFModelHelpItemProvider
		if (adapter instanceof IEMFModelHelpItemProvider) {
			iEMFModelHelpItemProvider = (IEMFModelHelpItemProvider) adapter;
		}
		// if it doesn't use the default implementation provided by the IEMFModelHelpItemProvider
		else {
			iEMFModelHelpItemProvider = new IEMFModelHelpItemProvider() { };
		}
		return iEMFModelHelpItemProvider.render(iEMFModelHelpItemProvider.getHelpItemDescription(eObject));
	}

	/**
	 * Adding this listener will show the {@link EMFModelHelpView} when pressing F1
	 * 
	 * @author martin
	 *
	 */
	public static class HelpListener implements org.eclipse.swt.events.HelpListener {

		@Override
		public void helpRequested(HelpEvent e) {
			show();
		}
	}
}
