package de.tud.et.ifa.agtele.emf.edit;

import java.util.Collection;

public interface IReferencingIdentificationStringProvider {
	default String getReferencingIdentificationString (Object element) {
		Collection<String> ids = this.getReferencingIdentificationStrings(element);
		if (!ids.isEmpty()) {
			return ids.iterator().next();
		}
		return null;
	};

	Collection<String> getReferencingIdentificationStrings(Object element);
	
	
	static String getUriPrefix (String uri) {
		if (uri.contains(":") && (uri.length() > (uri.indexOf(":") + 1)) && uri.indexOf(":") > 0) {
			int index = uri.indexOf(":");
			if (uri.charAt(index + 1) == '/') {
				return null;
			}
			if (Character.isDigit(uri.charAt(index + 1))) {
				if (uri.indexOf("/", index) > -1) {
					if (uri.substring(index +1, uri.indexOf("/", index)).matches("{\\d}+")) {
						return null;
					}
				}
			}
			return uri.substring(0, index);
		}
		return null;		
	}
	
	static boolean hasUriPrefix (String uri) {
		return IReferencingIdentificationStringProvider.getUriPrefix(uri) != null;
	}
	
	static String removeUriPrefix (String uri) {
		if (IReferencingIdentificationStringProvider.hasUriPrefix(uri)) {
			return uri.substring(uri.indexOf(":") +1);
		}
		return uri;
	}
}
