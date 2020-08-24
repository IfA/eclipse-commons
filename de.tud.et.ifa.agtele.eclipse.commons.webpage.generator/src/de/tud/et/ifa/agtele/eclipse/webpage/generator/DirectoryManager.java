package de.tud.et.ifa.agtele.eclipse.webpage.generator;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceRuleFactory;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;

import de.tud.et.ifa.agtele.eclipse.webpage.util.IStringSubstitutor;
import de.tud.et.ifa.agtele.eclipse.webpage.util.ResultReporter;
import de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.AbstractHTML;
import de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.WebPage;
import de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.util.WebPageModelUtils;
import de.tud.et.ifa.agtele.resources.BundleContentHelper;

public class DirectoryManager extends ResultReporter {

	protected List<WebPage> pages = null;
	protected List<IFolder> folders = new ArrayList<>();
	protected IProgressMonitor monitor;
	protected IStringSubstitutor stringSubstitutor;
	protected IWorkspaceRoot root = null;
	protected IWorkspace workspace = null;
	protected IResourceRuleFactory ruleFactory = null;
	public static final String TEMP_DIRECTORY = ".tmp";	
	public static final String RESOURCE_DIRECTORY = "templateResources";	
	public static final String BUNDLE_ID = "de.tud.et.ifa.agtele.eclipse.commons.webpage.generator";	
	public static final String SEGMENT_SEPARATOR = "/";
	
	public DirectoryManager (List<WebPage> pages) {
		this.pages = pages;
	}
	
	public static IWorkspaceRoot getRoot () {
		return ResourcesPlugin.getWorkspace().getRoot();
	}
	
	public boolean prepareDirectories (IProgressMonitor monitor) {

		if (monitor != null) {
			this.monitor = monitor;
			monitor.beginTask("Preparing the output directory.", 0);
		}
		
		this.determineDirectoriesToCreate();
		
		if (this.createTempDirectory(monitor) &&
				this.createTargetDirectories(monitor) &&
				//this.cleanTargetDirectory(monitor) &&
				this.copyStaticResources(monitor) && 
				this.copyTemplateResources(monitor)) {
			return true;
		}		
		
		return !this.hasErrors();
	}

	public String substitute (String str) {
		if (this.stringSubstitutor != null && str != null) {
			return this.stringSubstitutor.substitute(str);
		}
		return str;
	}

	public URI getOutputPath (AbstractHTML page) {
		return this.getOutputPath(page, null);
	}
	public URI getOutputPath (AbstractHTML page, String appendix) {
		String outputPath = appendix != null && WebPageModelUtils.isAbsolutePath(appendix) ? this.substitute(appendix) : this.substitute(page.getTargetDir() + "/" + appendix);
		URI p = URI.createURI(outputPath);
		if (!p.hasAbsolutePath()) {
			return p.resolve(page.getWebPage().getBasePath().appendSegment("someFile.tmp"));
		}
		return URI.createURI(outputPath);
	}

	public URI getSrcPath (AbstractHTML page) {
		return this.getOutputPath(page, null);
	}
	public URI getSrcPath (AbstractHTML page, String appendix) {
		return page.getSrcPath(this.stringSubstitutor, appendix);
	}
	
	protected IFolder getFolder(URI uri) {
		return this.getFolder(new Path(uri.toPlatformString(true)));
	}
	protected IFolder getFolder(Path path) {
		return root.getFolder(path);
	}
	protected IFile getFile(URI uri) {
		return this.getFile(new Path(uri.toPlatformString(true)));
	}
	protected IFile getFile(Path path) {
		return root.getFile(path);
	}
	
	protected void determineDirectoriesToCreate() {
		for (WebPage page : this.pages) {
			IFolder outFolder = this.getFolder(this.getOutputPath(page));
			this.folders.add(outFolder);
			for (AbstractHTML node : page.getAllNodes()) {
				this.folders.add(this.getFolder(this.getOutputPath(node)));
			}
			Map<String, String> resourceMap = page.getAllResourcesToCopy();
			for (String src : resourceMap.keySet()) {
				if (this.isFolder(page, src)) {
					this.folders.add(this.getFolder(this.getOutputPath(page, resourceMap.get(src))));
				}
			}
		}
	}
	
	protected boolean isFolder(WebPage page, String path) {
		IFolder folder = this.getFolder(this.getSrcPath(page, path));		
		return folder.exists();
	}
	

	protected boolean existsDirectory(String path) {
		return this.getFolder(new Path(path)).exists();
	}
	
	public WebPage getPrimaryWebPage() {
		if (this.pages.size() > 0 && 
				this.pages.get(0).eResource() != null &&
				this.pages.get(0).eResource().getResourceSet() != null) {
			EObject webPage = this.pages.get(0).eResource().getResourceSet().getResources().get(0).getContents().stream().filter(o -> o instanceof WebPage).findFirst().orElse(null);
			if (webPage != null) {
				return (WebPage)webPage;
			}
		}
		return null;
	}
	
	public String getModelRoot () {
		if (this.pages.size() > 0 && 
				this.pages.get(0).eResource() != null &&
				this.pages.get(0).eResource().getResourceSet() != null) {
			return this.pages.get(0).eResource().getResourceSet().getResources().get(0).getURI().toPlatformString(true);
		}
		return null;
	}
	
	public String getTempPath () {
		String modelRoot = this.getModelRoot();
		if (modelRoot != null) {
			return modelRoot + "/" + DirectoryManager.TEMP_DIRECTORY;
		}
		return null;
	}
	
	protected boolean createTempDirectory(IProgressMonitor monitor) {
		if (!this.existsDirectory(this.getTempPath())) {
			try {
				this.getFolder(new Path(this.getTempPath())).create(true, true, monitor);
			} catch (CoreException e) {
				this.errors.add(e);
				return false;
			}
		}
		return true;
	}
	
	protected boolean createTargetDirectories(IProgressMonitor monitor) {
		boolean error = false;
		for (IFolder folder : this.folders) {
			try {
				if (!folder.exists()) {
					folder.create(true, true, monitor);					
				}
			} catch (CoreException e) {
				this.addError(e);
				error = true;
			}
		}
		return !error;
	}
	
	protected boolean copyStaticResources(IProgressMonitor monitor) {
		boolean error = false;
		
		for (WebPage page : this.pages) {
			if (monitor.isCanceled()) {
				return false;
			}
						
			Map<String, String> resourceMap = page.getAllResourcesToCopy();
			
			Map<IFile,String> files = new LinkedHashMap<>();
			Map<IFolder,String> folders = new LinkedHashMap<>();
			for (String src : resourceMap.keySet()) {
				if (this.isFolder(page, src)) {
					folders.put(this.getFolder(this.getOutputPath(page, src)), resourceMap.get(src));
				} else {
					files.put(this.getFile(this.getOutputPath(page, src)), resourceMap.get(src));
				}
			}
			
			for (IFolder folder : folders.keySet()) {
				try {
					folder.copy(new Path(this.getOutputPath(page, folders.get(folder)).toPlatformString(true)), true, monitor);
				} catch (Exception e) {
					this.addError(e);
					error = true;
				} 
			}
			for (IFile file : files.keySet()) {
				try {
					file.copy(new Path(this.getOutputPath(page, files.get(file)).toPlatformString(true)), true, monitor);
				} catch (Exception e) {
					this.addError(e);
					error = true;
				} 
			}
		}
		return !error;	
	}
	
	protected IFile getTempFile (File file, IProgressMonitor monitor) throws CoreException {
		URI path = URI.createURI(this.getTempPath() + DirectoryManager.SEGMENT_SEPARATOR + file.getName());
		IFile result = this.root.getFile(new Path(path.toPlatformString(true)));
		result.createLink(Path.fromOSString(file.toPath().toString()), IFile.FORCE, monitor);
		return result;
	}
	
	protected boolean copyTemplateResources(IProgressMonitor monitor2) {
		boolean error = false;
		for (java.io.File sourceF : this.getTemplateResources()) {
			if (monitor.isCanceled()) {
				return false;
			}
			IFile sourceFile = null;
			try {
				sourceFile = this.getTempFile(sourceF, monitor);
				IFile targetFile = this.getStaticResourceTargetFile(
						sourceF,
						this.getTempPath());
				sourceFile.copy(targetFile.getFullPath(), true, monitor);
			} catch (Exception e) {
				this.addError(e);
				error = true;
			} finally {
				try {
					if (sourceFile != null) {
						sourceFile.delete(true, monitor);								
					}
				} catch (CoreException e) {
					this.addError(e);
					error = true;
				} 					
			}
		}
		return error;
	}

	public String getBundleId () {
		return BUNDLE_ID;
	}
	
	protected IFile getStaticResourceTargetFile (File sourceFile, String sourceDirName) throws IOException {
		String sourcePath = sourceFile.getCanonicalPath().replaceAll("\\\\", DirectoryManager.SEGMENT_SEPARATOR),
				separator = DirectoryManager.SEGMENT_SEPARATOR+ sourceDirName+ DirectoryManager.SEGMENT_SEPARATOR,
				relativePath = sourcePath.substring(sourcePath.indexOf(separator)+ separator.length());
				
		return this.getFile(this.getOutputPath(this.getPrimaryWebPage(), relativePath));	
	}
	
	protected List<File> getTemplateResources () {
		ArrayList<File> result = new ArrayList<>();
		result.addAll(this.getStaticResourcesByDirName(RESOURCE_DIRECTORY, this.getBundleId()));
		return result;
	}
	
	protected List<File> getStaticResourcesByDirName(String dirName, String bundleId) {
		List<File> result = new ArrayList<>();
		
		for (java.io.File file : BundleContentHelper.getBundleContents(bundleId, dirName)) {
			if (!file.isDirectory()) {
				result.add(file);				
			}
		}
		
		return result;
	}

	public void refreshOutDirectories(IProgressMonitor monitor) {
		for (WebPage page : this.pages) {			
			try {
				this.getFolder(new Path(page.getOutDir()))
					.refreshLocal(IResource.DEPTH_INFINITE, monitor);
			} catch (CoreException e) {
				this.addError(e);
			}
		}
	}
	
	public void cleanTempDirectory() {
		if (this.existsDirectory(this.getTempPath())) {
			try {
				this.getFolder(URI.createURI(this.getTempPath())).delete(true, null);
			} catch (CoreException e) {
				this.errors.add(e);
			}
		}
	}
	
}
