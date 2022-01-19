package de.tud.et.ifa.agtele.eclipse.commons.emf.storage.ui.util;

import java.util.Set;

public interface ICacheChangeListener {
	public static enum ChangeType {
		REMOVED,
		ADDED,
		UPDATED;
	}
		
	public void notifyChanged(CacheChangeNotification notification);
	
	public default Set<String> filterById() {
		return null;
	}
	public default ChangeType filterByChangeType() {
		return null;
	}
	
	public static class CacheChangeNotification {
		private String id;
		private ChangeType changeType;

		@SuppressWarnings("unused")
		private CacheChangeNotification() {
		}
		
		public CacheChangeNotification(String id, ChangeType changeType) {
			this.id = id;
			this.changeType = changeType;
		}
		
		public String getId() {
			return this.id;
		}
		public ChangeType getChangeType() {
			return this.changeType;
		}
	}
}
