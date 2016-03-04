package de.tud.et.ifa.agtele.ui.views;


import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.*;

import de.tud.et.ifa.agtele.ui.AgteleUIPlugin;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EModelElement;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.command.CommandParameter;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.domain.IEditingDomainProvider;
import org.eclipse.emf.edit.provider.AdapterFactoryItemDelegator;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.*;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.events.HelpListener;


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

	private ISelectionListener selectionListener;

	private Boolean linkEditor;
	 
	/**
	 * The constructor.
	 */
	public EMFModelHelpView() {
		selectionListener = new ISelectionListener() {
			
			@Override
			public void selectionChanged(IWorkbenchPart part, ISelection selection) {
				// TODO Auto-generated method stub
				if (selection instanceof StructuredSelection) {
					StructuredSelection structuredSelection = (StructuredSelection) selection;
					if (structuredSelection.size() == 1) {
						if (structuredSelection.getFirstElement() instanceof EObject) {
							EMFModelHelpView.showHelp((EObject) structuredSelection.getFirstElement());
						}
						else {
							EMFModelHelpView.showIndex();
						}
					}
				}
			}
		};
		
		linkEditor = AgteleUIPlugin.getPlugin().getDialogSettings().getBoolean("link");
		if (linkEditor)
			PlatformUI.getWorkbench().getActiveWorkbenchWindow().getSelectionService().addSelectionListener(selectionListener);
		
	}

	/**
	 * This is a callback that will allow us
	 * to create the viewer and initialize it.
	 */
	@Override
	public void createPartControl(Composite parent) {
		browser = new Browser(parent, SWT.NONE);
		
		makeActions();
		contributeToActionBars();
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
		PlatformUI.getWorkbench().getActiveWorkbenchWindow().getSelectionService().removeSelectionListener(selectionListener);
		AgteleUIPlugin.getPlugin().getDialogSettings().put("link", linkEditor);
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
		linkAction = new Action("Link Editor", ImageDescriptor.createFromImage(BundleContentHelper.getBundleImage(AgteleUIPlugin.PLUGIN_ID, "icons/synced.gif"))) {
			public void run() {
				linkEditor = !linkEditor;
				if (linkEditor) {
					PlatformUI.getWorkbench().getActiveWorkbenchWindow().getSelectionService().addSelectionListener(selectionListener);
				}
				else {
					PlatformUI.getWorkbench().getActiveWorkbenchWindow().getSelectionService().removeSelectionListener(selectionListener);
				}
				linkAction.setChecked(linkEditor);
			}
		};
		linkAction.setToolTipText("Link with Selection");
		linkAction.setChecked(linkEditor);
	}
	
	public Browser getBrowser() {
		return browser;
	}
	
	/**
	 * Opens the {@link EMFModelHelpView}, generates the help page for the given AminoUI
	 * {@link EObject} and displays it
	 * 
	 * @param eObject AminoUI model element
	 */
	public static void showHelp(EObject eObject) {
		showText(getHtml(eObject));
	}
	
	/**
	 * Opens the {@link EMFModelHelpView} and displays text in it
	 * 
	 * @param text text to be displayed
	 */
	public static void showText(String text) {
		try {
			EMFModelHelpView helpView = (EMFModelHelpView) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView(ID, null, IWorkbenchPage.VIEW_VISIBLE);
			helpView.getBrowser().setText(text);
		} catch (PartInitException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Opens the {@link EMFModelHelpView} and shows the index help page
	 */
	public static void showIndex() {
		showText("AMINO UI Help Index");
	}
	
	/**
	 * Opens the view and shows help based on the current selection
	 */
	public void show() {
		//TODO implement awesome logic that generated the documentation
		PlatformUI.getWorkbench().getActiveWorkbenchWindow().getSelectionService().getSelection();
	}
	
	/**
	 * Generates the documentation page of an eObject
	 * @param eObject
	 * @return
	 */
	private static String getHtml(EObject eObject) {
		String html = "<HTML><BODY>";
		
		if (((EObject) eObject).eClass() instanceof EModelElement) {
			EModelElement eModelElement = (EModelElement) ((EObject) eObject).eClass();
			// EClass Name
			html += eObject.eClass().getName();
			
			// EClass Documentation
			if (EcoreUtil.getDocumentation(eModelElement) != null) {
				html += "<br/><br/>" + EcoreUtil.getDocumentation(eModelElement);
			}
			
			// Non-containment reference and attribute documentation
			if (getEditingDomainFor(eObject) instanceof AdapterFactoryEditingDomain) {
				// get all property descriptors for the current eObject and
				// therefore do some weird voodoo stuff according to
				// https://www.eclipse.org/forums/index.php/t/162266/
				AdapterFactoryEditingDomain afed = (AdapterFactoryEditingDomain) getEditingDomainFor(eObject);
				if (afed.getAdapterFactory().isFactoryForType(eObject)) {
					List<IItemPropertyDescriptor> propertyDescriptors = new AdapterFactoryItemDelegator(
							((ComposedAdapterFactory) afed.getAdapterFactory()).getRootAdapterFactory())
									.getPropertyDescriptors(eObject);
				}
			}
			
			if (!eObject.eClass().getEAllAttributes().isEmpty()) {
				html += "<br/><br/>Attributes";
				for (EAttribute attr : eObject.eClass().getEAllAttributes()) {
					html += "<br/><br/>" + attr.getName() + " : " + attr.getEAttributeType().getName() + "<br/>";
					if (EcoreUtil.getDocumentation(attr) != null)
						html += EcoreUtil.getDocumentation(attr);
				}
			}
			
			
			// Containment reference and possible targets documentation
			if (!getEditingDomainFor(eObject).getNewChildDescriptors(eObject, null).isEmpty()) {
				HashMap<EStructuralFeature, List<EObject>> childDescriptors = sortChildDescriptors(getEditingDomainFor(eObject).getNewChildDescriptors(eObject, null));
				html += "<br/><br/>Containment-References and possible children";
				
				for (EStructuralFeature reference : childDescriptors.keySet()) {
					html += "<br/><br/>" + reference.getName();
					for (EObject eValue : childDescriptors.get(reference)) {
						html += "<br/>" + eValue.eClass().getName() + "<br/>";
						if (EcoreUtil.getDocumentation(eValue.eClass()) != null)
								html += EcoreUtil.getDocumentation(eValue.eClass()) + "<br/>";
					}
				}
				
//				for (Object childDescriptor : childDescriptors) {
//					if (childDescriptor instanceof CommandParameter) {
//						CommandParameter createNewChildCommand = (CommandParameter) childDescriptor;
//						if (createNewChildCommand.getFeature() instanceof EStructuralFeature) {
//							EStructuralFeature feature = (EStructuralFeature) createNewChildCommand.getFeature();
//							html +=  "<br/><br/>" + feature.getName() + AminoUI_UIPlugin.getPlugin().getString("_UI_LabelReferenceNameSeparator");
//						}
//						if (createNewChildCommand.getEValue() != null) {
//							html += createNewChildCommand.getEValue().eClass().getName() + "<br/>";
//							if (EcoreUtil.getDocumentation(createNewChildCommand.getEValue().eClass()) != null)
//									html += EcoreUtil.getDocumentation(createNewChildCommand.getEValue().eClass());
//						}
//					}
//				}
			}
		}
		else html += "No help text available!";
		
		html += "</BODY></HTML>";
		
		return html;
	}
	
	/**
	 * Sorts {@link CommandParameter}s of the getNewChildDescriptors method of an EObject into a {@link HashMap}
	 * with {@link EStructuralFeature}s as keys in such way, that each child ({@link EObject}) is matched with its {@link EStructuralFeature}
	 * @param childDescriptors Collection of CommandParameters (ChildDescriptors)
	 * @return Sorted HashMap
	 */
	private static HashMap<EStructuralFeature, List<EObject>> sortChildDescriptors(Collection<?> childDescriptors) {
		HashMap<EStructuralFeature, List<EObject>> children = new HashMap();
		
		for (Object childDescriptor : childDescriptors) {
			if (childDescriptor instanceof CommandParameter) {
				EStructuralFeature feature = ((CommandParameter) childDescriptor).getEStructuralFeature();
				if (children.containsKey(feature)) {
					children.get(feature).add(((CommandParameter) childDescriptor).getEValue());
				}
				else {
					ArrayList<EObject> objects = new ArrayList<>();
					objects.add(((CommandParameter) childDescriptor).getEValue());
					children.put(feature, objects);
				}
			}
		}
		return children;
	}
	
	
	static private EditingDomain getEditingDomainFor(EObject object)
	{
		
		IEditingDomainProvider editingDomainProvider =

				(IEditingDomainProvider)EcoreUtil.getExistingAdapter(object,
						IEditingDomainProvider.class);
		if (editingDomainProvider != null)
		{
			return editingDomainProvider.getEditingDomain();
		}
		else if (object.eResource() != null)
		{
			ResourceSet resourceSet = object.eResource().getResourceSet();
			if (resourceSet instanceof IEditingDomainProvider)
			{
				EditingDomain editingDomain =
						((IEditingDomainProvider)resourceSet).getEditingDomain();
				return editingDomain;
			}
			else if (resourceSet != null)
			{
				editingDomainProvider =
						(IEditingDomainProvider)EcoreUtil.getExistingAdapter(resourceSet,
								IEditingDomainProvider.class);
				if (editingDomainProvider != null)
				{
					return editingDomainProvider.getEditingDomain();
				}
			}
		}
		return null;
	}
	
	public static class HelpListener implements HelpListener {

		@Override
		public void helpRequested(HelpEvent e) {
			show();
		}
	}
}
