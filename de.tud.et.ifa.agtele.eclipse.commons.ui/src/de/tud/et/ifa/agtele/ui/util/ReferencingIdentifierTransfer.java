package de.tud.et.ifa.agtele.ui.util;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.swt.dnd.ByteArrayTransfer;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.dnd.TransferData;

public class ReferencingIdentifierTransfer extends ByteArrayTransfer {

	public final static ReferencingIdentifierTransfer instance = new ReferencingIdentifierTransfer ();
	
	private ReferencingIdentifierTransfer() {}

	public static ReferencingIdentifierTransfer getInstance () {
		return instance;
	}
	
	static final String MIME_TYPE= "custom/ReferencingIdentifier";
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
			boolean first = true;
			String text = "[";
			
			for (Object element : c) {
				text += (first ? "" : ",\n") + "'" + ((String)element) + "'";
				first = false;
			}
			
			text += "]";
			return text;
		}
		return null;
	}
	
	@Override
	protected void javaToNative (Object object, TransferData transferData) {
		if (this.validate(object)) {
			TextTransfer tt = TextTransfer.getInstance();
			String text = convertToText((Collection) object);
			
			if (text != null) {
				tt.javaToNative(text, transferData);
			}
			
		}
	}
	
	public Collection<String> convertFromText (String text) {
		ArrayList<String> result = new ArrayList<>();
		
		//TODO
		
		return result;
	}
	
	@Override
	protected Object nativeToJava(TransferData transferData) {		
		TextTransfer tt = TextTransfer.getInstance();
		String text = (String) tt.nativeToJava(transferData);
		if (text != null) {
			return this.convertFromText(text);
		}
		return null;
	}
}
