package de.tud.et.ifa.agtele.resources;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;

public interface FileUtils {
	

	public static IWorkspaceRoot getRoot () {
		return ResourcesPlugin.getWorkspace().getRoot();
	}
	
	public static void createDirectories (String path,IProgressMonitor monitor) throws Exception {
		IFolder folder = FileUtils.getRoot().getFolder(new Path(path));
		List<IFolder> parents = new ArrayList<>();
		if (!folder.exists()) {
			synchronized (FileUtils.class) {
				parents.add(folder);
				while(folder.getParent() != null) {
					if (folder.getParent() instanceof IFolder) {
						if (!((IFolder)folder.getParent()).exists()) {
							folder = (IFolder) folder.getParent();
							parents.add(folder);
						} else {
							break;					
						}					
					} else if (folder.getParent().exists()) {
						break;
					} else {
						throw new Exception("Cannot create folder '" + path + "', no viable parent found.");
					}
				}			
				for (int i = parents.size() - 1 ; i >= 0; i-= 1) {
					if (!parents.get(i).exists()) {
						parents.get(i).create(true, true, monitor);
					}
				}
			}	
		}
	}
}
