package de.tud.et.ifa.agtele.ui.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.swt.dnd.ByteArrayTransfer;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.dnd.TransferData;

import de.tud.et.ifa.agtele.emf.AgteleEcoreUtil;

public class ReferencingIdentifierTransfer extends ByteArrayTransfer {

	public final static ReferencingIdentifierTransfer instance = new ReferencingIdentifierTransfer ();
	
	private ReferencingIdentifierTransfer() {}

	public static ReferencingIdentifierTransfer getInstance () {
		return instance;
	}
	
	static final String MIME_TYPE= "text/referencing-identifier-list";
	final int MIME_TYPE_ID = registerType(MIME_TYPE);
	
	@Override
	protected int[] getTypeIds() {
		return new int[] {MIME_TYPE_ID};
	}

	@Override
	protected String[] getTypeNames() {
		return new String[] {MIME_TYPE};
	}
	
	public boolean validate(Object o) {
		if (!(o instanceof Collection)) {
			return false;
		}
		for (Object element : (Collection)o) {
			if (!(element instanceof String)) {
				return false;
			}
		}
		return true;
	}
	
	public String convertToText (Collection c) {
		if (this.validate(c)) {
			String text = "[";
			
			for (Object element : c) {
				text += "" + ((String)element).length() + "_" + ((String)element);
			}
			
			text += "]";
			return text;
		}
		return null;
	}
	
	@Override
	protected void javaToNative (Object object, TransferData transferData) {
		if (this.validate(object)) {
			String text = convertToText((Collection) object);
			if (text != null) {
				byte[] bytes = text.getBytes(); 
				super.javaToNative(bytes, transferData);
			}
			
		}
	}
	
	public Collection<?> convertFromText (String text) {
		ReferencingIdentifierList result = new ReferencingIdentifierList();

		if (!text.startsWith("[") || !text.endsWith("]")) {
			return null;
		}
		
		text = text.substring(1, text.length()-1);
		
		if (text.isEmpty()) {
			return result;
		}
		while (!text.isEmpty()) {
			int delim = text.indexOf("_"),
				length = Integer.parseInt(text.substring(0, delim)); 
			result.add(text.substring(delim+1, delim+1+length));
			text = text.substring(delim+1+length);			
		}
		
		return Collections.singleton(result);
	}
	
	@Override
	public Object nativeToJava(TransferData transferData) {		
		if (transferData == null) {
			return null;
		}
		byte[] bytes = (byte[])super.nativeToJava(transferData);
		if (bytes == null) {
			return null;
		}
		String text = new String(bytes);
		if (text != null) {
			try {
				return this.convertFromText(text);
			} catch (Exception e) {
				System.err.println("Could not convert dnd-string: '" + text + "'");
			}
		}
		return null;
	}
	
	public static List<EObject> referencableObjects (Collection<? extends Object> objects) {
		ArrayList<EObject> result = new ArrayList<>();
		for (Object o : objects) {
			if (o instanceof EObject && AgteleEcoreUtil.isReferencable(o)) {
				result.add((EObject)o);
			}
		}
		return result;
	}
	
	public static boolean isFromOtherResource (Collection<?> objects, Resource res) {
		for (Object obj : objects) {
			if (obj instanceof EObject) {
				if (((EObject)obj).eResource() != res) {
					return true;
				}
			}
		}
		return false;
	}
	
	public static class ReferencingIdentifierList extends ArrayList<String> {
		private static final long serialVersionUID = -977424907021486244L;
		
		public ReferencingIdentifierList() {
			super();
		}
		
		public ReferencingIdentifierList(List<EObject> referencables) {
			super();
			
			for (EObject obj : referencables) {
				String id = this.getIdentifier(obj);
				if (id != null && !id.isBlank()) {
					this.add(id);
				}
			}
		}
		
		public String getIdentifier (EObject obj) {
			List<String> ids = AgteleEcoreUtil.getReferencableStrings(obj);			
			if (ids != null && !ids.isEmpty()) {
				return ids.get(0);
			}			
			return null;
		}
	}
}
