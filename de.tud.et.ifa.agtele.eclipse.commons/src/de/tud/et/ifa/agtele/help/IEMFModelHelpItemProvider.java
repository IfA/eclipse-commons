package de.tud.et.ifa.agtele.help;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.command.CommandParameter;
import org.eclipse.emf.edit.provider.AdapterFactoryItemDelegator;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;

import de.tud.et.ifa.agtele.emf.AgteleEcoreUtil;
import de.tud.et.ifa.agtele.resources.BundleContentHelper;

/**
 * This Interface is used to render HTMl help text from an {@link EObject}.
 * Use the Methods {@link #getHelpItemDescription(EObject)} to render the default help view.
 * 
 * The generation of the HTML document containing the help relies on html snippets containing placeholdes like '%PLACEHOLDER%'
 * and render methods replacing the placeholders with data from the {@link HelpItemData} objects and the references 
 * {@link EObject}s such as {@link EClass}, {@link EReference}, etc. 
 * 
 * The {@link HelpItemData} and {@link HelpItemDescription} objects are created using an overridable {@link HelpItemFactory}.
 * 
 * The rendering can be adapted by:
 * <ul>
 * <li>overriding the getter methods for the general html templates (change the logical structure of the generated document): 
 * <ul>
 * 		<li>{@link #getHTMLTemplate()}</li> 
 * 		<li>{@link #getCSSTemplate()}</li>
 * 		<li>{@link #getJavaScriptTemplate()}</li>
 * 		<li>{@link #getTagTemplate()}</li>
 * 		<li>{@link #getTagContainerTemplate()}</li>
 * 		<li>{@link #getBodyTemplate(boolean, boolean, boolean)}</li>
 * </ul>
 * </li>
 * 
 * <li>overriding the methods for creating the default HTML utility (changes style and behavior)
 * <ul>
 * 		<li>{@link #getStyles()}</li>
 * 		<li>{@link #getScripts()}</li>
 * </ul>
 * </li>
 * 
 * <li>overriding the getter methods for the EMF specific html templates (change the logical structure of the help item display): 
 * <ul>
 * 		<li>{@link #getAttributeTemplate(boolean)}</li>
 * 		<li>{@link #getEEnumLiteralTemplate()}</li>
 * 		<li>{@link #getNCReferenceeTemplate()}, {@link #getNCReferenceTemplate(boolean)}</li>
 * 		<li>{@link #getCReferenceTemplate(boolean)}</li>
 * 		<li>{@link #getCReferenceChildTemplate(boolean)}</li>
 * </ul>
 * </li>
 * <li>overriding the render methods, that process the {@link HelpItemData} and replace the placeholders such as '%PLACEHOLDER%' from the html templates with the EMF specific content:
 * <ul>
 * 		<li>{@link #renderBody(HelpItemDescription)}</li>
 * 		<li>{@link #renderAttributes(List)}</li>
 * 		<li>{@link #renderCReferenceChildren(List)}</li>
 * 		<li>{@link #renderCReferences(List)}</li>
 * 		<li>{@link #renderEEnumLiterals(List)}</li>
 * 		<li>{@link #renderNCReferencees(List)}</li>
 * 		<li>{@link #renderNCReferences(List)}</li>
 * 		<li>{@link #renderNCRefTags(EReferenceHelpItemData)}</li>
 * </ul>
 * </li>
 * <li>providing a custom {@link HelpItemFactory} implementation to the {@link #getHelpItemDescription(EObject, HelpItemFactory)}
 * method that generates the {@link HelpItemDescription} including the {@link HelpItemData} objects that are consumed by the render-methods.</li>
 * </ul>
 * 
 * @author cmartin
 * @author Baron
 *
 */
public interface IEMFModelHelpItemProvider {

	/**
	 * The general structure of the HTML document.
	 * 
	 * @return Contains the following placeholders: %TITLE%, %HEAD%, %STYLE%, %BODY%, %SCRIPT%.
	 */
	public default String getHTMLTemplate () {
		return "<!DOCTYPE html>"
				+ "<html lang=\"en\">"
				+ "<head>"
				+ 	"<meta charset=\"UTF-8\">"
				+ 	"<title>%TITLE%</title>"
				+ 	"%HEAD%"
				+ 	"%STYLE%"
				+ "</head>"
				+ "<body>"
				+ 	"%BODY%"
				+	"%SCRIPT%"
				+ "</body>"
			+  "</html>";
	}
	
	/**
	 * The template for an html style tag.
	 * @return Contains the placeholder %RULES%
	 */
	public default String getCSSTemplate() {
		return "<style type=\"text/css\">%RULES%</style>";
	}
	
	/**
	 * The template for an html script tag (javascript).
	 * @return Contains the placeholder %CODE%
	 */
	public default String getJavaScriptTemplate() {
		return "<script type=\"application/javascript\">"
				+ "%CODE%"
			+  "</script>";
	}

	/**
	 * The template for a tag container that is to be displayed for some of the features.
	 * @return Contains placeholder %TAGS%
	 */
	public default String getTagContainerTemplate() {
		return "<div class=\"tag-container\">%TAGS%</div>";
	}
	/**
	 * The template for a tag, that is to be displayed inside the {@link #getTagContainerTemplate()}.
	 * @return Contains placeholders %TYPE%, %TEXT%
	 */
	public default String getTagTemplate() {
		return "<span class=\"tag %TYPE%\">%TEXT%</span>";
	}
		
	/**
	 * This method renders the HTML that may be used for the user help. This
	 * default implementation only lists all properties separated by line
	 * breaks.
	 * 
	 * @param helpItemDescription
	 *            contains the {@link HelpItemDescription properties} that are
	 *            relevant for the rendering of a help page
	 * @return the help text to be displayed (as HTML)
	 */
	public default String render(HelpItemDescription helpItemDescription) {
		EClassHelpItemData eClass = helpItemDescription.getEClassDescription();
		
		String result = getHTMLTemplate()
				.replace("%TITLE%", eClass.getName())
				.replace("%STYLE%", getStyles())
				.replace("%BODY%", renderBody(helpItemDescription))
				.replace("%SCRIPT%", getScripts());				
		return cleanHTML(result);
	}
	
	/**
	 * Cleans the generated text from the '%PLACEHOLDER%'s.
	 * @param rendered 
	 * @return The cleaned string.
	 */
	public default String cleanHTML(String rendered) {
		return rendered.replaceAll("(%[A-Z]*%)", "");		
	}
	
	/**
	 * Delivers the default styles in the Help view.
	 * @return Calls {@link #getCSSTemplate()} and replaces the %RULES% placeholder.
	 */
	public default String getStyles () {
		String styles = "";
		
//		styles+= getCSSTemplate().replace("%RULES%",
//				"/*COLORS*/"
//				+ "");	
	
		styles+= getCSSTemplate().replace("%RULES%",
//				".heading:not(h1), .description, .category, .sub-category, .tag-container {"
//				+ 	"padding-left: 20px;"
//				+ "}"
				  "* {"
				+ 	"font-size:16px;"
				+ 	"background-color:rgb(240,240,240);"
				+ 	"color:rgb(40,40,40);"
				+ "}"
				+ "body {"
				+ 	"overflow-y:scroll;"
				+ "}"
				+ ".tag {"
				+ 	"display:inline-block;"
				+ 	"border: 1px solid black;"
				+ 	"border-radius: 2px;"
				+ "}"
				+ "h1,h2,h3,h4,h5 {"
				+ 	"margin:0;"
				+ "}"
				+ ".tag{"
				+ 	"padding-left: 5px;"
				+ 	"padding-right: 5px;"
				+ "}"
				+ ".category{"
				+ 	"padding-left:10px;"
				+ "}"
				+ ".description{"
				+ 	"padding-left:20px;"
				+ 	"margin-top:3px;"
				+ 	"margin-bottom:6px;"
				+ "}"
				+ "h1 {"
				+ 	"font-size:24px;"
				+ 	"padding-top: 5px;"
				+ 	"padding-bottom:5px;"
				+ 	"padding-left:15px;"				
				+ 	"background-color:rgb(200,200,200);"
				+ "}"
				+ "h1 + div {"
//				+ 	"padding-left: 30px;"
				+ "}"
				+ "h2 {"
				+ 	"font-size:22px;"
				+ 	"padding-top: 4px;"
				+ 	"padding-bottom:4px;"
				+ 	"padding-left:15px;"	
				+ 	"background-color:rgb(210,210,210);"
				+ "}"
				+ "h2 + div {"
//				+ 	"padding-left:30px;"
				+ "}"
				+ "h3 {"
				+ 	"font-size:20px;"
				+ 	"padding-top: 3px;"
				+ 	"padding-bottom:3px;"
				+ 	"padding-left:15px;"	
				+ 	"background-color:rgb(220,220,220);"
				+ "}"
				+ "h3 + div {"
//				+ 	"padding-left:35px;"
				+ "}"
				+ "h4 {"
				+ 	"text-decoration:underline;"
				+   "font-weight:normal;"
				+ 	"padding-top: 3px;"
				+ 	"padding-bottom:3px;"
				+ 	"padding-left:25px;"	
			//	+ 	"background-color:rgb(230,230,230);"
				+ "}"
				+ "h4 + div {"
//				+ 	"padding-left:40px;"
				+ "}"
				+ "h5 {"
				+ 	"padding-bottom: 1px;"
				+ 	"padding-top: 3px;"
				+ 	"padding-bottom: 3px;"
				+ 	"padding-left:35px;"	
				+ 	"background-color:rgb(240,240,240);"
				+ "}"
				+ "h5+div {"
//				+ 	"padding-top:5px;"
				+ 	"padding-bottom:5px;"
				+ 	"padding-left:45px !important;"
				+ 	"margin:0;"
				+ "}"
				+ ".expandable {"
				+ 	"cursor:pointer;"
				+ "}"
				+ ".expandable.collapsed+.sub-category {"
				+ 	"display:none;"
				+ "}"
				+ ".expandable.collapsed:before{"
				+ 	"content: '+ ';"
				+ "}"
				+ ".expandable.expanded:before{"
				+ 	"content: '- ';"
				+ "}"
				+ "");	
		
		return styles;
	}
	
	/**
	 * Delivers the default scripts for the generated help.
	 * Calls {@link #getJavaScriptTemplate()} and replaces the %CODE% placeholder.
	 * @return the jquery script and the custom script for collapsing and expanding the help texts.
	 */
	public default String getScripts() {
		String scripts = "";	
		boolean success = true;
		
		try {
			scripts += getJavaScriptTemplate().replace("%CODE%", BundleContentHelper.getBundleFileString("de.tud.et.ifa.agtele.eclipse.commons", "files/jquery.min.js"));
		} catch (IOException e) {
			success = false;
			e.printStackTrace();
		}
		if (success) {
			scripts += "\n" + getJavaScriptTemplate().replace("%CODE%", ""
				+ "(function(){"
				+ 	"$('.expandable').on('click', function () {"
				+ 		"if($(this).is('.collapsed')) {"
				+ 			"$(this).removeClass('collapsed').addClass('expanded');"
				+ 		"} else {"
				+ 			"$(this).removeClass('expanded').addClass('collapsed');"
				+ 		"}"
				+ 	"});"
				+ "}())");		
		}
		
		
		return scripts;
	}

	/**
	 * The logical structure of the help document. Contains the placeholders %TITLE%, %DESCRIPTION%, [%ATTRIBUTES%, %NCREFERENCES%, %CREFERENCES%].
	 * @param includeAttributes Whether to include the attributes section 
	 * @param includeNCReferences Whether to include the non-containment references section
	 * @param includeCReferences Whether to include the containment references section
	 * @return The prepared template.
	 */
	public default String getBodyTemplate (boolean includeAttributes, boolean includeNCReferences, boolean includeCReferences) {
		return "<h1 class=\"heading class-heading\">%TITLE% - Help</h1>"
				+ "<div class=\"class-description description\">%DESCRIPTION%</div>"
				+ (includeAttributes ? 
						"<h2 class=\"heading attributes-heading\">Attributes</h2>"
				+		 	"<div class=\"class-attributes category\">%ATTRIBUTES%</div>"
						: "")
				+ (includeNCReferences ? 
						"<h2 class=\"heading ncreferences-heading\">Non-Containment References</h2>"
				+ 			"<div class=\"class-nc-references category\">%NCREFERENCES%</div>"
						: "")
				+ (includeCReferences ?
						"<h2 class=\"heading creferences-heading\">Containment References</h2>"
				+ 			"<div class=\"class-c-references category\">%CREFERENCES%</div>"
						: "");
	}
	
	/**
	 * Calls {@link #getBodyTemplate(boolean, boolean, boolean)} and replaces its placeholders by invoking
	 * {@link #renderAttributes(List)}, {@link #renderNCReferences(List)}, {@link #renderCReferences(List)}
	 * @param helpItemDescription
	 * @return The rendered body content.
	 */
	public default String renderBody(HelpItemDescription helpItemDescription) {
		EClassHelpItemData eClass = helpItemDescription.getEClassDescription();
		List<EAttributeHelpItemData> attributes = helpItemDescription.getAttributeDescription();
		List<EReferenceHelpItemData> ncRefs = helpItemDescription.getNonContainmentReferenceDescription();
		List<EReferenceHelpItemData> cRefs = helpItemDescription.getContainmentReferenceDescription();
		
		return getBodyTemplate(!attributes.isEmpty(), !ncRefs.isEmpty(), !cRefs.isEmpty()) 
				.replace("%TITLE%", eClass.getName())
				.replace("%DESCRIPTION%", eClass.getDocumentation())
				.replace("%ATTRIBUTES%", renderAttributes(attributes))
				.replace("%NCREFERENCES%", renderNCReferences(ncRefs))
				.replace("%CREFERENCES%", renderCReferences(cRefs));	
	}
	/**
	 * The default html template for the display of an attribute help text. 
	 * Contains the placeholders %NAME%, %TYPE%, %DESCRIPTION%, [%EENUMDESCRIPTION%, %EENUMVALUES%]
	 * @param isEEnum Whether to include the sections for documenting an EEnum.
	 * @return The template including the placeholders
	 */
	public default String getAttributeTemplate(boolean isEEnum) {
		return "<h3 class=\"heading attribute-heading\">%NAME% \u00bb %TYPE% " + (isEEnum ? "(EEnum)" : "") + "</h3>"
				+ "<div class=\"attribute-description description\">%DESCRIPTION%"
				+ (isEEnum ? 
						"</br>"
						+ "%EENUMDESCRIPTION%" 
						: "")
				
				+ "</div>"
				+ (isEEnum ? 
						"<h4 class=\"heading attribute-values-heading expandable collapsed\">Possible Values</h4>"
						+ "<div class=\"eenum-values sub-category\">%EENUMVALUES%</div>" 
						: "");
	}

	/**
	 * Calls {@link #getAttributeTemplate(boolean)} and replaces the placeholders according to the {@link EAttributeHelpItemData}.
	 * Invokes {@link #renderEEnumLiterals(List)} in case the attribute type is an {@link EEnum}.
	 * @param attributes The attributes to render
	 * @return The rendered attributes help text.
	 */
	public default String renderAttributes(List<EAttributeHelpItemData> attributes) {
		String text = "";
		if (!attributes.isEmpty()) {
			for (EAttributeHelpItemData attr : attributes) {				
				text+= getAttributeTemplate(attr.isEEnum())
					.replace("%NAME%", attr.getName())
					.replace("%TYPE%", attr.getDataType())
					.replace("%DESCRIPTION%", attr.getDocumentation())
					.replace("%EENUMVALUES%", attr.isEEnum() ? renderEEnumLiterals(attr.getEEnumLiterals()) : "")
					.replace("%EENUMDESCRIPTION%", attr.isEEnum() ? attr.getEEnumDocumentation(): "");
			}
		}
		return text;
	};
	
	/**
	 * The default html template for documenting an {@link EEnumLiteral}. 
	 * @return Contains the placeholders %NAME% and %DESCRIPTION%.
	 */
	public default String getEEnumLiteralTemplate() {
		return "<h5 class=\"heading eenum-literal-heading\">%NAME%</h5>"
				+ "<div class=\"eenum-literal-description description\">%DESCRIPTION%</div>";
	}
	
	/**
	 * Calls {@link #getEEnumLiteralTemplate()} and replaces the placeholders.
	 * @param eEnumLiterals The enum literals to display.
	 * @return The help text for EEnum values to set.
	 */
	public default String renderEEnumLiterals(List<EEnumLiteralHelpItemData> eEnumLiterals) {
		String text = "";
		
		for (EEnumLiteralHelpItemData lit : eEnumLiterals) {
			text += getEEnumLiteralTemplate()
					.replace("%NAME%", lit.getName())
					.replace("%DESCRIPTION%", lit.getDocumentation());
		}
		
		return text;		
	};

	/**
	 * The default html template for rendering the help text of a non-containment {@link EReference}.
	 * @param refsAvailable Whether to include the available references section.
	 * @return Contains placeholders for %NAME%, %TYPE%, %TAGS%, %DESCRIPTION%, [%NCREFERENCEES%]
	 */
	public default String getNCReferenceTemplate(boolean refsAvailable) {
		return "<h3 class=\"heading ncreference-heading\">%NAME% \u00bb %TYPE%</h3>"
				+ "%TAGS%"
				+ "<div class=\"ncreference-description description\">"				
				+ 	"%DESCRIPTION%"			
				+ "</div>"
				+ (refsAvailable ? 
						"<h4 class=\"heading ncreference-referencees-heading expandable collapsed\">Available Referencees</h4>"
						+ "<div class=\"ncreference-referencees sub-category\">%NCREFERENCEES%</div>" 
						: "");
	}
	
	/**
	 * Calls {@link #getNCReferenceTemplate(boolean)} and replaces the placeholders according to the list of 
	 * {@link EReferenceHelpItemData} of non-containment references.
	 * @param ncRefs
	 * @return The rendered help text of the non-containment references.
	 */
	public default String renderNCReferences(List<EReferenceHelpItemData> ncRefs) {
		String text = "";
		if (!ncRefs.isEmpty()) {
			for (EReferenceHelpItemData ncRef : ncRefs) {
				text+= getNCReferenceTemplate(!ncRef.getChildData().isEmpty())
						.replace("%NAME%", ncRef.getName())
						.replace("%TYPE%", ncRef.getDataType())
						.replace("%DESCRIPTION%", ncRef.getDocumentation())
						.replace("%NCREFERENCEES%", !ncRef.getChildData().isEmpty() ? renderNCReferencees(ncRef.getChildData()) : "")
						.replace("%TAGS%", renderNCRefTags(ncRef));
			}
		}
		return text;
	}
	
	/**
	 * Renders the tags of a reference help text. 
	 * Calls {@link #getTagContainerTemplate()} and {@link #getTagTemplate()} and replaces the placeholder.
	 * @param ncRef The non-containment reference to get the tags for.
	 * @return The rendered tags section.
	 */
	public default String renderNCRefTags(EReferenceHelpItemData ncRef) {
		String text = "";
			if (ncRef.getEReference().isDerived()) {
				text += getTagTemplate()
					.replace("%TYPE%", "ncref-tags")
					.replace("%TEXT%", "derived");				
			}		
		if (!text.isEmpty()) {
			text = getTagContainerTemplate().replace("%TAGS%", text);
		}
			
		return text;
	}

	/**
	 * Returns the template for including possible referenced objects (referencee) within the model in the help text.
	 * @return Contains placeholders %NAME%, %DESCRIPTION%
	 */
	public default String getNCReferenceeTemplate() {
		return "<h5 class=\"heading ncreferencee-heading\">%NAME%</h5>"
				+ "<div class=\"ncreferencee-description description\">%DESCRIPTION%</div>"; 
	};
	
	/**
	 * Calls {@link #getNCReferenceeTemplate()} and replaces the placeholders.
	 * @param childData
	 * @return The possible referencees of a reference.
	 */
	public default String renderNCReferencees(List<EClassHelpItemData> childData) {
		String text = "";
		
		for (EClassHelpItemData target : childData) {
			text += getNCReferenceeTemplate()
					.replace("%NAME%", target.getName())
					.replace("%DESCRIPTION%", target.getDocumentation());
		}
		
		return text;
	}

	/**
	 * The default html template of a containment reference help text.
	 * @param childrenAvailable Whether to include the creatable children section.
	 * @return Contains placeholders %NAME%, %TYPE%, %DESCRIPTION%, [%CHILDREN%]
	 */
	public default String getCReferenceTemplate(boolean childrenAvailable) {
		return "<h3 class=\"heading creference-heading\">%NAME% \u00bb %TYPE%</h3>"
				+ "<div class=\"creference-description description\">"
				+ 	"%DESCRIPTION%"			
				+ "</div>"
				+ (childrenAvailable ? 
						"<h4 class=\"heading creference-children-heading expandable collapsed\">Creatable Children</h4>"
						+ "<div class=\"creference-children sub-category\">%CHILDREN%</div>" 
						: "");
	}
	/**
	 * Calls {@link #getCReferenceTemplate(boolean)} and replaces the placeholders.
	 * @param cRefs
	 * @return The rendered help text for containment references.
	 */
	public default String renderCReferences(List<EReferenceHelpItemData> cRefs) {
		String text = "";
			for (EReferenceHelpItemData cRef : cRefs) {
				
				text += getCReferenceTemplate(!cRef.getChildData().isEmpty())
						.replace("%NAME%", cRef.getName())
						.replace("%TYPE%", cRef.getDataType())
						.replace("%DESCRIPTION%", cRef.getDocumentation())
						.replace("%CHILDREN%", !cRef.getChildData().isEmpty() ? renderCReferenceChildren(cRef.getChildData()) : "");
			}
		return text;
	}
	
	/**
	 * The default html template for providing the help text for creatable children of a containment reference.
	 * @param descriptionAvailable Whether to include the description section of the creatable child.
	 * @return Contains placeholders %NAME% [%DESCRIPTION%]
	 */
	public default String getCReferenceChildTemplate (boolean descriptionAvailable) {
		return "<h5 class=\"heading creference-child-heading\">\u00bb %NAME%</h5>" +
				(descriptionAvailable ? "<div class=\"creference-child-description description\">%DESCRIPTION%</div>" : ""); 
	}
	
	/**
	 * Calls {@link #getCReferenceChildTemplate(boolean)} and replaces the placeholders.
	 * @param childData
	 * @return The rendered help text for the creatable children of a containment reference.
	 */
	public default String renderCReferenceChildren(List<EClassHelpItemData> childData) {
		String text = "";
		for (EClassHelpItemData child : childData) {
			text += getCReferenceChildTemplate (!child.getDocumentation().isEmpty())
					.replace("%NAME%", child.getName())
					.replace("%TYPE%", child.getDataType())
					.replace("%DESCRIPTION%", child.getDocumentation());
		}
		return text;
	}

	/**
	 * Returns all relevant {@link HelpItemDescription help data} of an
	 * {@link EObject} by gathering {@link HelpItemData} for the {@link EClass},
	 * {@link EAttribute EAttributes}, {@link EReference containment References}
	 * including their possible {@link EClass child elements}, and
	 * {@link EReference non-containment References} including their possible
	 * targets in the model that the eObject originates from.
	 * This implementation uses the default HelpItemFactory implementation.
	 * 
	 * @param eObject
	 * @return all relevant {@link HelpItemDescription}
	 */
	public default HelpItemDescription getHelpItemDescription(EObject eObject) {
		return getHelpItemDescription(eObject, new HelpItemFactory() {});
	}
	
	/**
	 * Returns all relevant {@link HelpItemDescription help data} of an
	 * {@link EObject} by gathering {@link HelpItemData} for the {@link EClass},
	 * {@link EAttribute EAttributes}, {@link EReference containment References}
	 * including their possible {@link EClass child elements}, and
	 * {@link EReference non-containment References} including their possible
	 * targets in the model that the eObject originates from.
	 * 
	 * @param eObject
	 * @param factory Specify a custom factory for the {@link HelpItemData} and {@link HelpItemDescription} objects
	 * in order to provide custom information to the {@link #render(HelpItemDescription)} method.
	 * @return all relevant {@link HelpItemDescription}
	 */
	@SuppressWarnings("unchecked")
	public default HelpItemDescription getHelpItemDescription(EObject eObject, HelpItemFactory factory) {
		HelpItemDescription helpItemDescription = factory.createHelpItemDescription(eObject);

		/**
		 * Generates the Documentation of the properties of a given
		 * {@link EObject}
		 */
		helpItemDescription.setEClassDescription(factory.createEClassHelpItemData(eObject.eClass()));

		if (AgteleEcoreUtil.getAdapterFactoryItemDelegatorFor(eObject) != null) {
			AdapterFactoryItemDelegator afid = AgteleEcoreUtil.getAdapterFactoryItemDelegatorFor(eObject);
			/**
			 * Generates the Documentation of the {@link EReference Containment
			 * References} and the possible Children of a given {@link EObject}
			 */
			List<IItemPropertyDescriptor> propertyDescriptors = afid.getPropertyDescriptors(eObject);

			for (IItemPropertyDescriptor itemPropertyDescriptor : propertyDescriptors) {
				// EAttribute
				if (itemPropertyDescriptor.getFeature(null) instanceof EAttribute) {
					EAttribute attr = (EAttribute) itemPropertyDescriptor.getFeature(null);

					helpItemDescription.addAttributeDescription(factory.createEAttributeHelpItemData(attr));
				}
				// Non-Containment References
				else if (itemPropertyDescriptor.getFeature(null) instanceof EReference) {
					EReference ncRef = (EReference) itemPropertyDescriptor.getFeature(null);
					// display Non-Containment References only if a type is
					// bound to it
					if (ncRef.getEGenericType().getEClassifier() != null || ncRef.getEGenericType().getERawType() != null) {
						helpItemDescription.addNonContainmentReferenceDescription(factory.createEReferenceHelpItemData(ncRef,
								(List<EObject>) itemPropertyDescriptor.getChoiceOfValues(eObject)));
					}
				}
			}
			// Containment References
			for (Object cd : afid.getNewChildDescriptors(eObject, AgteleEcoreUtil.getEditingDomainFor(eObject), null)) {
				if (cd instanceof CommandParameter) {
					// the reference and the target type from the command
					EStructuralFeature feature = ((CommandParameter) cd).getEStructuralFeature();
					EClass target = ((CommandParameter) cd).getEValue().eClass();
					// check if a description for this feature already exists
					List<EReferenceHelpItemData> matchingDesc = helpItemDescription.getContainmentReferenceDescription()
							.stream().filter(cRD -> cRD.getEReference().equals((EReference) feature))
							.collect(Collectors.toList());
					// if it doesn't create a new description for it
					if (matchingDesc.isEmpty()) {
						helpItemDescription.addContainmentReferenceDescription(
								factory.createEReferenceHelpItemData((EReference) feature, Arrays.asList(((CommandParameter) cd).getEValue())));
					} else {
						// if it does add the child element to it
						// therefore remove the old version of it first
						helpItemDescription.removeContainmentReferenceDescription(matchingDesc.get(0));
						// add a child description
						matchingDesc.get(0).addChildData(factory.createEClassHelpItemData(target));
						// and add it again
						helpItemDescription.addContainmentReferenceDescription(matchingDesc.get(0));
					}
				}
			}
		}
		return helpItemDescription;
	}
	/**
	 * An overridable factory interface that creates HelpItems in order to be consumed by the rendering algorithm.
	 * Override the factory with custom methods and provide it to the
	 * {@link IEMFModelHelpItemProvider#getHelpItemDescription(EObject, HelpItemFactory)} in order to customize 
	 * the content of the {@link HelpItemDescription} and {@link HelpItemData} objects that are used 
	 * for the rendering of the help view.
	 * @author Baron
	 */
	public interface HelpItemFactory {
		
		/**
		 * Creates a help item description.
		 * @param eObject
		 * @return a help item description for the specified object.
		 */
		public default HelpItemDescription createHelpItemDescription(EObject eObject) {
			return new HelpItemDescription(eObject); 
		}
		
		/**
		 * Creates an {@link EClass} help item data object
		 * @param eClass
		 * @return An {@link EClass} help item data object
		 */
		public default EClassHelpItemData createEClassHelpItemData(EClass eClass) {
			return new EClassHelpItemData(eClass);
		}
		
		/**
		 * Creates an {@link EAttribute} help item data object.
		 * @param attribute
		 * @return An {@link EAttribute} help item data object.
		 */
		public default EAttributeHelpItemData createEAttributeHelpItemData(EAttribute attribute) {
			return new EAttributeHelpItemData(attribute);
		}
		
		/**
		 * Crates an {@link EReference} help item data object.
		 * @param eReference
		 * @param list
		 * @return A {@link EReference} new help item data object.
		 */
		public default EReferenceHelpItemData createEReferenceHelpItemData(EReference eReference, List<EObject> list) {
			return new EReferenceHelpItemData(eReference, list);
		}
	}
}
