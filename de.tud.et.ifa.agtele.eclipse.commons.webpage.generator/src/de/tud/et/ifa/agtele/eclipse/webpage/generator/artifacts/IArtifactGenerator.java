package de.tud.et.ifa.agtele.eclipse.webpage.generator.artifacts;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;

import de.tud.et.ifa.agtele.eclipse.webpage.generator.DirectoryManager;
import de.tud.et.ifa.agtele.eclipse.webpage.util.IStringSubstitutor;
import de.tud.et.ifa.agtele.eclipse.webpage.util.ResultReporter;
import de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.AbstractHTML;
import de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.FileValue;
import de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.StringValue;
import de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.Value;
import de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.WebPage;

import org.eclipse.emf.common.util.URI;


public interface IArtifactGenerator {
	public default boolean generate() throws Exception {
		AbstractHTML html = this.getHtmlFragment();
		String fileUri = html.getTargetFilePath();
		IFile file = DirectoryManager.getRoot().getFile(new Path(fileUri));
		
		FileWriter writer = null;
		try {
			writer = new FileWriter(file.getLocation().toOSString());
			writer.write(this.getContent());
		} catch (Exception e) {
			throw e;
		} finally {
			if (writer != null) {				
				writer.close();
			}
		}
		return true;
	}
	
	public AbstractHTML getHtmlFragment();
	
	public IStringSubstitutor getStringSubstitutor();
	
	public ResultReporter getReporter();
	
	public default WebPage getWebPage() {
		return this.getHtmlFragment().getWebPage();
	}
	
	public String getContent();
	
	public default String getValueContent(Value val) {
		if (val instanceof StringValue) {
			return ((StringValue)val).getValue() != null ? ((StringValue)val).getValue() : "";
		}
		if (val instanceof FileValue) {
			URI fileUri = ((FileValue)val).getSrcUri(this.getStringSubstitutor());
						
			IFile file = ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(fileUri.toPlatformString(true)));
			try {
				return new String(file.getContents().readAllBytes());
			} catch (IOException | CoreException e) {
				this.getReporter().addError(e);
			}
			return null;
		}
		
		return null;
	}
	
	public default List<String> getValueContents (List<Value> values)  {
		ArrayList<String>result = new ArrayList<>();
		for (Value val : values) {
			String content = this.getValueContent(val);
			if (content != null) {
				result.add(content);
			}
		}
		return result;
	}
}
