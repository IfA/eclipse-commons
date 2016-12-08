package de.tud.et.ifa.agtele.ui.views;

import java.util.Iterator;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.events.HelpEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IPersistable;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;

import de.tud.et.ifa.agtele.emf.AgteleEcoreUtil;
import de.tud.et.ifa.agtele.help.IEMFModelHelpItemProvider;
import de.tud.et.ifa.agtele.resources.BundleContentHelper;
import de.tud.et.ifa.agtele.ui.AgteleUIPlugin;

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
		this.selectionListener = (IWorkbenchPart iw, ISelection selection) -> {EMFModelHelpView.showHelp(selection);};

		this.linkEditor = AgteleUIPlugin.getPlugin().getDialogSettings().getBoolean("link");
		if (this.linkEditor) {
			PlatformUI.getWorkbench().getActiveWorkbenchWindow().getSelectionService()
					.addSelectionListener(this.selectionListener);
		}

	}

	/**
	 * This is a callback that will allow us to create the viewer and initialize
	 * it.
	 */
	@Override
	public void createPartControl(Composite parent) {
		this.browser = new Browser(parent, SWT.NONE);

		this.makeActions();
		this.contributeToActionBars();

		this.currentText = AgteleUIPlugin.getPlugin().getDialogSettings().get("browserText");
		if (this.currentText != null) {
			this.browser.setText(this.currentText);
		}
		if (this.linkEditor) {
			EMFModelHelpView.show();
		}
	}

	/**
	 * Passing the focus request to the viewer's control.
	 */
	@Override
	public void setFocus() {
		this.browser.setFocus();
	}

	@Override
	public void dispose() {
		PlatformUI.getWorkbench().getActiveWorkbenchWindow().getSelectionService()
				.removeSelectionListener(this.selectionListener);
		AgteleUIPlugin.getPlugin().getDialogSettings().put("link", this.linkEditor);

		AgteleUIPlugin.getPlugin().getDialogSettings().put("browserText", this.currentText);

		super.dispose();
	}

	private void contributeToActionBars() {
		IActionBars bars = this.getViewSite().getActionBars();
		this.fillLocalToolBar(bars.getToolBarManager());
	}

	private void fillLocalToolBar(IToolBarManager manager) {
		manager.add(this.linkAction);
	}

	private void makeActions() {
		this.linkAction = new Action("Link Editor",
				BundleContentHelper.getBundleImageDescriptor(AgteleUIPlugin.PLUGIN_ID, "icons/synced.gif")) {
			@Override
			public void run() {
				EMFModelHelpView.this.linkEditor = !EMFModelHelpView.this.linkEditor;
				if (EMFModelHelpView.this.linkEditor) {
					PlatformUI.getWorkbench().getActiveWorkbenchWindow().getSelectionService()
							.addSelectionListener(EMFModelHelpView.this.selectionListener);
				} else {
					PlatformUI.getWorkbench().getActiveWorkbenchWindow().getSelectionService()
							.removeSelectionListener(EMFModelHelpView.this.selectionListener);
				}
				EMFModelHelpView.this.linkAction.setChecked(EMFModelHelpView.this.linkEditor);
			}
		};
		this.linkAction.setToolTipText("Link with Selection");
		this.linkAction.setChecked(this.linkEditor);
	}

	/**
	 * Opens the view and shows help based on the current selection
	 */
	public static void show() {
		EMFModelHelpView.showHelp(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getSelectionService().getSelection());
	}

	/**
	 * Opens the {@link EMFModelHelpView}, generates the help page for the given
	 * AminoUI {@link EObject} and displays it
	 *
	 * @param eObject
	 *            AminoUI model element
	 */
	public static void showHelp(EObject eObject) {
		EMFModelHelpView.showText(EMFModelHelpView.getHtml(eObject));
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
					.getActivePage().showView(EMFModelHelpView.ID, null, IWorkbenchPage.VIEW_VISIBLE);

			if (!text.equals(helpView.currentText)) {
				helpView.browser.setText(text);
				helpView.currentText = text;
			} else {
				return;
			}
		} catch (PartInitException e) {
			e.printStackTrace();
		}
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
					EMFModelHelpView.showText(
							AgteleUIPlugin.getPlugin().getString("_UI_EMFModelHelpView_TooManyElementsSelected"));
				}
			}
		}
	}

	/**
	 * Generates the documentation page of an {@link EObject} based on the
	 * {@link IEMFModelHelpItemProvider} matching the element
	 *
	 * @param eObject
	 * @return
	 */
	private static String getHtml(EObject eObject) {

		IEMFModelHelpItemProvider iEMFModelHelpItemProvider = AgteleEcoreUtil.adapt(eObject,
				IEMFModelHelpItemProvider.class);

		// Create a new provider if necessary
		//
		if (iEMFModelHelpItemProvider == null) {
			iEMFModelHelpItemProvider = new IEMFModelHelpItemProvider() {
			};
		}

		return iEMFModelHelpItemProvider.render(iEMFModelHelpItemProvider.getHelpItemDescription(eObject));
	}

	/**
	 * Adding this listener will show the {@link EMFModelHelpView} when pressing
	 * F1
	 *
	 * @author martin
	 *
	 */
	public static class HelpListener implements org.eclipse.swt.events.HelpListener {

		@Override
		public void helpRequested(HelpEvent e) {
			EMFModelHelpView.show();
		}
	}
}
