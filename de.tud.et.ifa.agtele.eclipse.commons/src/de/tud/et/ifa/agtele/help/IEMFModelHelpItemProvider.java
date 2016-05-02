package de.tud.et.ifa.agtele.help;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.command.CommandParameter;
import org.eclipse.emf.edit.provider.AdapterFactoryItemDelegator;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;

import de.tud.et.ifa.agtele.emf.AgteleEcoreUtil;
import de.tud.et.ifa.agtele.resources.BundleContentHelper;

/**
 * This interface may be implemented to provide a help text.
 * 
 * @author martin
 *
 */
public interface IEMFModelHelpItemProvider {

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
	
	public default String getCSSTemplate() {
		return "<style type=\"text/css\">%RULES%</style>";
	}
	
	public default String getJavaScriptTemplate() {
		return "<script type=\"application/javascript\">"
				+ "%CODE%"
			+  "</script>";
	}

	public default String getTagContainerTemplate() {
		return "<div class=\"tag-container\">%TAGS%</div>";
	}
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
	
	public default String cleanHTML(String rendered) {
		return rendered.replaceAll("(%[A-Z]*%)", "");		
	}
	
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
	
	public default String getEEnumLiteralTemplate() {
		return "<h5 class=\"heading eenum-literal-heading\">%NAME%</h5>"
				+ "<div class=\"eenum-literal-description description\">%DESCRIPTION%</div>";
	}
	
	public default String renderEEnumLiterals(List<EEnumLiteralHelpItemData> eEnumLiterals) {
		String text = "";
		
		for (EEnumLiteralHelpItemData lit : eEnumLiterals) {
			text += getEEnumLiteralTemplate()
					.replace("%NAME%", lit.getName())
					.replace("%DESCRIPTION%", lit.getDocumentation());
		}
		
		return text;		
	};

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

	public default String getNCReferenceeTemplate() {
		return "<h5 class=\"heading ncreferencee-heading\">%NAME%</h5>"
				+ "<div class=\"ncreferencee-description description\">%DESCRIPTION%</div>"; 
	};
	
	public default String renderNCReferencees(List<EClassHelpItemData> childData) {
		String text = "";
		
		for (EClassHelpItemData target : childData) {
			text += getNCReferenceeTemplate()
					.replace("%NAME%", target.getName())
					.replace("%DESCRIPTION%", target.getDocumentation());
		}
		
		return text;
	}

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
	
	public default String getCReferenceChildTemplate (boolean descriptionAvailable) {
		return "<h5 class=\"heading creference-child-heading\">\u00bb %NAME%</h5>" +
				(descriptionAvailable ? "<div class=\"creference-child-description description\">%DESCRIPTION%</div>" : ""); 
	}
	
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
	 * @param factory
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
	 * @author Lukas
	 *
	 */
	public interface HelpItemFactory {
		
		public default HelpItemDescription createHelpItemDescription(EObject eObject) {
			return new HelpItemDescription(eObject); 
		}
		
		public default EClassHelpItemData createEClassHelpItemData(EClass eClass) {
			return new EClassHelpItemData(eClass);
		}
		
		public default EAttributeHelpItemData createEAttributeHelpItemData(EAttribute attribute) {
			return new EAttributeHelpItemData(attribute);
		}
		
		public default EReferenceHelpItemData createEReferenceHelpItemData(EReference eReference, List<EObject> list) {
			return new EReferenceHelpItemData(eReference, list);
		}
	}
}
