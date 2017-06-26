package de.tud.et.ifa.agtele.ui.views;

import java.util.Iterator;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.edit.provider.ItemProvider;
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
 * This {@link ViewPart view} provides help contents for {@link EObject
 * EObjects} by displaying information about their {@link EObject#eClass()
 * EClass} as well as about corresponding {@link EAttribute EAttributes} and
 * {@link EReference EReferences}. <br />
 * <b>Note</b>: In order to enable this for your meta-model, your generated
 * {@link ItemProvider ItemProviders} (resp. the root ItemProvider) need to
 * implement the {@link IEMFModelHelpItemProvider} interface. <br />
 * <b>Note</b>: The view is able to display the content of 'documentation'
 * annotations specified in your meta-model. However, you need to set the
 * 'Suppress GenModel Annotations' setting in your genmodel to 'false' for this
 * to work! Otherwise, the 'documentation' annotations are not present in the
 * generated package impls and can thus not be evaluated by the 'EMF Model Help
 * View'.
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
		this.selectionListener = (IWorkbenchPart iw, ISelection selection) -> {
			EMFModelHelpView.showHelp(selection, false);
		};

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
			EMFModelHelpView.show(false);
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
	 *
	 * @param activateView
	 *            bring view to front on text update
	 */
	public static void show(Boolean activateView) {
		EMFModelHelpView.showHelp(
				PlatformUI.getWorkbench().getActiveWorkbenchWindow().getSelectionService().getSelection(),
				activateView);
	}

	/**
	 * Opens the {@link EMFModelHelpView}, generates the help page for the given
	 * AminoUI {@link EObject} and displays it
	 *
	 * @param eObject
	 *            AminoUI model element
	 * @param activateView
	 *            bring view to front on text update
	 */
	public static void showHelp(EObject eObject, Boolean activateView) {
		EMFModelHelpView.showText(EMFModelHelpView.getHtml(eObject), activateView);
	}

	/**
	 * Opens the {@link EMFModelHelpView} and displays text in it
	 *
	 * @param text
	 *            text to be displayed
	 * @param activateView
	 *            bring view to front on text update
	 */
	public static void showText(String text, Boolean activateView) {
		try {
			int viewMode = IWorkbenchPage.VIEW_CREATE;

			if (activateView) {
				viewMode = IWorkbenchPage.VIEW_ACTIVATE;
			}
			EMFModelHelpView helpView = (EMFModelHelpView) PlatformUI.getWorkbench().getActiveWorkbenchWindow()
					.getActivePage().showView(EMFModelHelpView.ID, null, viewMode);

			helpView.browser.setText(text);
			helpView.currentText = text;

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
	private static void showHelp(ISelection selection, Boolean activateView) {
		if (selection instanceof StructuredSelection) {
			StructuredSelection structuredSelection = (StructuredSelection) selection;
			// show Help if one EObject is selected
			if (structuredSelection.size() == 1) {
				if (structuredSelection.getFirstElement() instanceof EObject) {
					EMFModelHelpView.showHelp((EObject) structuredSelection.getFirstElement(), activateView);
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
							AgteleUIPlugin.getPlugin().getString("_UI_EMFModelHelpView_TooManyElementsSelected"),
							false);
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
			EMFModelHelpView.show(true);
		}
	}
}
