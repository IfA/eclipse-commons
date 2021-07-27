package de.tud.et.ifa.agtele.eclipse.commons.emf.modelStorage;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;

public interface IRegistrationChangeListener {
	public static enum ChangeType {
		DEREGISTERED,
		REGISTERED;
	}
		
	public void notifyChanged(RegistrationChangeNotification notification);
	
	public default Set<ModelStorage> filterByModelStorage() {
		return null;
	}
	public default Set<Model> filterByModel(){
		return null;
	}
	public default Set<EObject> filterByElement() {
		return null;
	}
	public default Set<String> filterById() {
		return null;
	}
	public default ChangeType filterByChangeType() {
		return null;
	}
	
	public static class RegistrationChangeNotification {
		private ModelStorage storage;
		private Model model;
		private EObject element;
		private Set<String> ids;
		private ChangeType changeType;

		@SuppressWarnings("unused")
		private RegistrationChangeNotification() {
		}
		
		public RegistrationChangeNotification(ModelStorage storage, Model model, EObject element, Set<String> ids, ChangeType changeType) {
			this.storage = storage;
			this.model = model;
			this.element = element;
			this.ids = ids;
			this.changeType = changeType;
		}
		
		public ModelStorage getModelStorage() {
			return this.storage;
		}
		public Model getModel() {
			return this.model;
		}
		public EObject getElement() {
			return this.element;
		}
		public Set<String> getIds() {
			return new HashSet<>(this.ids);
		}
		public ChangeType getChangeType() {
			return this.changeType;
		}
	}
}
