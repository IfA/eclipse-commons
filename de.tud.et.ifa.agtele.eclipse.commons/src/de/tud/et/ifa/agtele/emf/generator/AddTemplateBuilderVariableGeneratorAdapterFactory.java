package de.tud.et.ifa.agtele.emf.generator;

import org.eclipse.emf.codegen.ecore.genmodel.generator.GenModelGeneratorAdapterFactory;
import org.eclipse.emf.common.notify.Adapter;

/**
 * This EMF Generator extension automatically ensures that a genmodel using the
 * emitter templates from the agtele emf commons plugin is able to compile. For
 * that, this factory creates a GenModelAdapter that updates the template
 * settings in the genmodel.
 * 
 * @author Baron
 *
 */
public class AddTemplateBuilderVariableGeneratorAdapterFactory extends GenModelGeneratorAdapterFactory {

	@Override
	public Adapter createGenPackageAdapter() {
		return null;
	}

	@Override
	public Adapter createGenEnumAdapter() {
		return null;
	}

	@Override
	public Adapter createGenModelAdapter() {
		if (genModelGeneratorAdapter == null) {
			genModelGeneratorAdapter = new AddTemplateBuilderVariableGeneratorAdapter(this);
		}
		return genModelGeneratorAdapter;
	}

	@Override
	public Adapter createGenClassAdapter() { return null; }
}
