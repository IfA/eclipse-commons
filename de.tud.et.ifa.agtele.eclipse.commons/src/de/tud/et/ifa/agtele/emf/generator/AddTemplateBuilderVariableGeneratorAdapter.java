package de.tud.et.ifa.agtele.emf.generator;

import org.eclipse.emf.codegen.ecore.generator.GeneratorAdapterFactory;
import org.eclipse.emf.codegen.ecore.genmodel.GenModel;
import org.eclipse.emf.codegen.ecore.genmodel.GenModelPackage;
import org.eclipse.emf.codegen.ecore.genmodel.generator.GenBaseGeneratorAdapter;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.Monitor;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import de.tud.et.ifa.agtele.emf.AgteleEcoreUtil;

public class AddTemplateBuilderVariableGeneratorAdapter extends GenBaseGeneratorAdapter {

	public static final String AGTELE_TEMPLATE_VARIABLE_ENTRY = "AGTELE_EMF_COMMONS=de.tud.et.ifa.agtele.eclipse.commons";
	
	public AddTemplateBuilderVariableGeneratorAdapter() {
		super();
	}

	public AddTemplateBuilderVariableGeneratorAdapter(GeneratorAdapterFactory generatorAdapterFactory) {
		super(generatorAdapterFactory);
	}

	public static Diagnostic ensureGenModelTemplatePluginVariableSet (Object object, Monitor monitor) {		
		GenModel genModel = (GenModel) object;
		
		monitor.beginTask("", 1);
		monitor.subTask("Check and Template Build Path Settings");
		
		if (!genModel.getTemplatePluginVariables().contains(AGTELE_TEMPLATE_VARIABLE_ENTRY) && requiresAgteleTemplateVariableEntry(genModel)) {
			EditingDomain domain = AgteleEcoreUtil.getEditingDomainFor(genModel);
			
			domain.getCommandStack().execute(new AddCommand(domain, genModel, GenModelPackage.Literals.GEN_MODEL__TEMPLATE_PLUGIN_VARIABLES, AGTELE_TEMPLATE_VARIABLE_ENTRY));
		}
		
		monitor.done();
	    return Diagnostic.OK_INSTANCE;
	}
	
	@Override
	protected Diagnostic generateModel(Object object, Monitor monitor) {
		return ensureGenModelTemplatePluginVariableSet(object, monitor);
	}
	
	@Override
	protected Diagnostic generateEdit(Object object, Monitor monitor) {
		return ensureGenModelTemplatePluginVariableSet(object, monitor);
	}
	
	@Override
	protected Diagnostic generateTests(Object object, Monitor monitor) {
		return ensureGenModelTemplatePluginVariableSet(object, monitor);
	}
	
	@Override
	protected Diagnostic generateEditor(Object object, Monitor monitor) {
		return ensureGenModelTemplatePluginVariableSet(object, monitor);
	}
	
	public static boolean requiresAgteleTemplateVariableEntry (GenModel genModel) {
		
		return true;
	}
}
