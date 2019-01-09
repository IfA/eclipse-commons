package de.tud.et.ifa.agtele.emf.sanitizer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;

import org.eclipse.emf.common.command.AbstractCommand;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.XMIResource;
import org.eclipse.emf.edit.command.CommandParameter;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

/**
 * A Model Sanitizer is a feature that maintains things in the whole model, and thus ensures model sanity.
 * The Sanitizer can be configured with varying inputs, depending on the target of the operation.
 * If the sanitizer is provided an {@link EditingDomain}, the output will be commands that can be executed afterwards, 
 * or else the modifications are applied to the model directly. 
 * 
 * @author Baron
 *
 */
public abstract class ModelSanitizer {
	
	protected ArrayList<EObject> input = new ArrayList<>();
	
	protected ArrayList<EObject> expandedInput = new ArrayList<>();
	
	protected CompoundCommand result = new CompoundCommand();	
	
	protected boolean processed = false;
	
	protected EditingDomain domain = null;
	
	protected ArrayList<ModelSanitizer> subSanitizers = new ArrayList<>();
	
	public ModelSanitizer () {}
		
	public boolean isConfigured() {
		return !this.input.isEmpty() || !this.expandedInput.isEmpty();
	}
		
	protected void checkState() {
		if (processed) {
			throw new IllegalStateException("The Sanitizer already run. Create a new one.");
		}
	}
	
	public void setEditingDomain (EditingDomain domain) {
		this.domain = domain;
	}
	
	public void addInput (EditingDomain domain) {
		this.domain = domain;

		domain.getResourceSet().getResources().forEach(r -> {
			if (r instanceof XMIResource) {
				((XMIResource)r).getContents().forEach(o -> {
					ModelSanitizer.this.addInput(o);
				});
			}
		});
	}
	
	public void addInput(Collection<EObject> elements) {
		for (EObject o : elements) {
			this.addInput(o);
		}
	}
	
	public void addInput(EObject element) {
		this.checkState();
		if (element != null && !this.input.contains(element)) {
			input.add(element);
		}
	}
	
	public AbstractCommand getResult() {
		return this.result;
	}
	
	public boolean isExecutable() {
		return this.result != null && (!(this.result instanceof CompoundCommand) || !((CompoundCommand)this.result).isEmpty());
	}
	
	public void process (boolean expand) {
		this.checkState();
		this.processed = true;
		
		if (expand) {
			this.expandInput();
		} else {
			this.expandedInput.addAll(this.input);
		}
		
		if (!this.expandedInput.isEmpty()) {
			this.internalProcess();
		}
	}
	
	protected void internalProcess () {
		this.processSanitizers(this.getSanitizersToProcess());
	};
	
	public void expandInput () {
		this.input.stream().forEach(be -> {			
			ModelSanitizer.this.expandedInput.add(be);
			be.eAllContents().forEachRemaining(eo -> {
				if (!ModelSanitizer.this.expandedInput.contains(eo)) {
					ModelSanitizer.this.expandedInput.add(eo);
				}
			});
		});
	}
	
	protected void setExpandedInput (Collection<EObject> col) {
		this.checkState();
		this.expandedInput.addAll(col);
	}
	
	protected Collection<ModelSanitizer> getSanitizersToProcess () {
		return this.subSanitizers;
	}
	
	protected void processSanitizers (Collection<ModelSanitizer> sanitizers) {
		sanitizers.forEach(s -> ModelSanitizer.this.processSanitizer(s));
	}
	
	protected void processSanitizer (ModelSanitizer sanitizer) {
		if (!sanitizer.isConfigured()) {
			sanitizer.setExpandedInput(this.expandedInput);
		}
		if (sanitizer.domain == null) {
			sanitizer.setEditingDomain(this.domain);
		}
		
		sanitizer.process(false);
		AbstractCommand result = sanitizer.getResult();
		if (result != null) {
			if (!(result instanceof CompoundCommand) || !((CompoundCommand)result).isEmpty()) {
				this.result.append(result);
			}
		}
	}
	
	public void addSubSanitizer (ModelSanitizer sanitizer) {
		this.subSanitizers.add(sanitizer);
	}
	
	public static class ModelSanitizerImpl extends ModelSanitizer {		

		public ModelSanitizerImpl (EditingDomain input) {
			this.addInput(input);
		}
		
		public ModelSanitizerImpl (EObject input) {
			this.addInput(input);
		}
		
		public ModelSanitizerImpl (Collection<EObject> input) {
			this.addInput(input);
		}
		
		public Collection<ModelSanitizer> getDefaultSanitizers () {
			ArrayList<ModelSanitizer> result = new ArrayList<>();
			result.add(new ModelSanitizer.InternalIDSanitizer());
			
			return result;
		}
		
		protected Collection<ModelSanitizer> getSanitizersToProcess () {
			ArrayList<ModelSanitizer> result = this.subSanitizers;
			if (result.isEmpty()) {
				result.addAll(this.getDefaultSanitizers());
			}		
			return result;
		}		
	}
	
	public static class InternalIDSanitizer extends ModelSanitizer {
		
		ArrayList<EObject> roots = new ArrayList<>();
		LinkedHashMap<Object, EObject> idMap = new LinkedHashMap<>();
		ArrayList<EObject> elementsToCreateIdFor = new ArrayList<>();
		
		@Override
		protected void internalProcess() {
			this.findRoots();
			this.buildIdMap();
			this.checkInputIds();
			this.uniqueifyIds();
		}
		
		protected void findRoots () {
			this.expandedInput.forEach(o -> {
				EObject root = EcoreUtil.getRootContainer(o, true);
				if (!roots.contains(root)) {
					roots.add(root);
				}
			});
		}
		
		protected void buildIdMap () {
			this.roots.forEach(o -> {
				o.eAllContents().forEachRemaining(io -> {
					if (io.eClass().getEIDAttribute() != null) {
						ModelSanitizer.InternalIDSanitizer.this.registerElementId(io);
					}
				});
			});
		}
		
		protected void registerElementId (EObject o) {
			Object idValue = this.getElementId(o);
			
			if (idValue != null) {
				if (this.idMap.containsKey(idValue)) {
					if (this.idMap.get(idValue) != o) {
						//Swap elements in order to uniquify the id of an input element
						if (!this.expandedInput.contains(o) && this.expandedInput.contains(this.idMap.get(idValue))) {
							this.idMap.put(idValue, o);
						}
					}
				} else {
					this.idMap.put(idValue, o);
				}
			}
		}
		
		protected Object getElementId (EObject o) {
			Object idValue = o.eGet(o.eClass().getEIDAttribute());
			if (idValue != null && (!(idValue instanceof String) || !((String)idValue).trim().isEmpty() )) {
				return idValue;
			}
			return null;
		}
		
		protected void checkInputIds () {
			this.expandedInput.forEach(i -> {
				if (i.eClass().getEIDAttribute() != null) {
					Object idValue = ModelSanitizer.InternalIDSanitizer.this.getElementId(i);
					if (idValue == null || (ModelSanitizer.InternalIDSanitizer.this.idMap.containsKey(idValue) && 
							ModelSanitizer.InternalIDSanitizer.this.idMap.get(idValue) != i)) {
						ModelSanitizer.InternalIDSanitizer.this.elementsToCreateIdFor.add(i);
					}
				}
			});
		}
		
		protected void uniqueifyIds () {
			this.elementsToCreateIdFor.forEach(e -> {
				if (this.domain != null) {
					Command cmd = this.domain.createCommand(SetCommand.class, new CommandParameter(e, e.eClass().getEIDAttribute(), EcoreUtil.generateUUID()));
					this.result.append(cmd);					
				} else {
					e.eSet(e.eClass().getEIDAttribute(), EcoreUtil.generateUUID());
				}
			});
		}
	}
}
