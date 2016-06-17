package de.tud.et.ifa.agtele.resources;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;

import java.util.jar.Manifest;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * This provides a set of helper functions that allow to deal with files and resources.
 * 
 */
public class ResourceHelper {
	
	private ResourceHelper() {
		
	}
	
	/**
	 * Copy file to a project folder
	 * 
	 * @param file file to be copied
	 * @param targetFolder target folder path relative to the project
	 * @param project project to which the file should be copied
	 */
	public static void copyFile(File file, String targetFolder, IProject project) {
    	
		// Get the path of the new File from the project
		IFile newFile = project.getFile(new Path(targetFolder).append(new Path(file.getName())));
		
		try {
			if (newFile.exists()) {
				newFile.delete(true, null);
			}
	
			newFile.create(new FileInputStream(file), true, null);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
	
	/**
	 * Copy file to a local folder
	 * 
	 * @param file file to be copied
	 * @param targetFolder absolute target folder path on the local file system
	 * @return copied file
	 * @throws IOException
	 */
	public static File copyFile(File file, String targetFolder) throws IOException {
    	// create folders if necessary
    	new File(targetFolder).mkdirs();
    	
    	// Get the path of the new File
    	File newFile = new Path(targetFolder).append(new Path(file.getName())).toFile();
    	
    	// only try to copy the file, if it really is a file (no dirs)
    	if (file.isFile()) {
	    	// and copy it
			try (FileInputStream is = new FileInputStream(file); FileOutputStream os = new FileOutputStream(newFile)) {
				byte[] buffer = new byte[1024];
				int length;
				while ((length = is.read(buffer)) > 0) {
					os.write(buffer, 0, length);
				}
	        }
    	}
    	else if (file.isDirectory()) {
    		newFile.mkdirs();
    	}
    	return newFile;
    }
	
	/**
	 * Copy List of Files to a target folder while keeping the folder structure
	 * 
	 * @param files List of source files that should be copied 
	 * @param srcDir source directory that the files reside in (used to relativize the file paths against)
	 * @param targetFolder absolute target folder path on the local file system
	 */
	public static void copyFiles(ArrayList<File> files, File srcDir, String targetFolder) {
		try {
			for (File file : files) {
				String targetPath = srcDir.toPath().relativize(file.toPath().getParent()).toString();
				ResourceHelper.copyFile(file, new File(targetFolder, targetPath).getCanonicalPath());
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Copies all files of a folder to a new location
	 * 
	 * @param srcDir folder to copy the files from
	 * @param targetFolder new target path
	 * @throws IOException 
	 */
	public static void copyAllFiles(File srcDir, String targetFolder) throws IOException {
		for (File file : srcDir.listFiles()) {
			// copy the file to the targetFolder
			copyFile(file, targetFolder);
			// if the file is a directory, then copy its contents, too
			if (file.isDirectory()) {
				String newTargetFolder = new File(targetFolder, file.getName()).getCanonicalPath();
				copyAllFiles(file, newTargetFolder);
			}
		}
	}
	
	/**
	 * Create a folder
	 * 
	 * @param folder folder to be created
	 * @throws CoreException
	 */
	
	public static void createFolder(IFolder folder) throws CoreException {
    	IContainer parent = folder.getParent();
    	if (parent instanceof IFolder) {
    		createFolder((IFolder) parent);
    	}
    	if (!folder.exists()) {
    		folder.create(false, true, null);
    	}
    }
	
	/**
	 * Deletes a folder and its contents
	 * 
	 * @see <a href="http://stackoverflow.com/questions/7768071/how-to-delete-folder-content-in-java">http://stackoverflow.com/questions/7768071/how-to-delete-folder-content-in-java</a>
	 * 
	 * @param folder folder to be deleted
	 */
	public static void deleteFolder(File folder) {
		deleteFilesInFolder(folder);
	    folder.delete();
	}
	
	/**
	 * Deletes all contents of a folder
	 * 
	 * @see <a href="http://stackoverflow.com/questions/7768071/how-to-delete-folder-content-in-java">http://stackoverflow.com/questions/7768071/how-to-delete-folder-content-in-java</a>
	 * 
	 * @param folder folder of which the contents shall be deleted
	 */
	public static void deleteFilesInFolder(File folder) {
		File[] files = folder.listFiles();
	    if(files!=null) { //some JVMs return null for empty dirs
	        for(File f: files) {
	            if(f.isDirectory()) {
	                deleteFolder(f);
	            } else {
	                f.delete();
	            }
	        }
	    }
	}
	
	/**
     * Create a folder structure from given path strings
     *
     * @param project
     * @param paths
     * @throws CoreException
     */
    public static void addToProjectStructure(IProject project, String[] paths) throws CoreException {
        for (String path : paths) {
            IFolder etcFolders = project.getFolder(path);
            ResourceHelper.createFolder(etcFolders);
        }
    }
    
    /**
     * Refreshes a Resource in the workspace
     * 
     * @param resource
     */
    public static void refresh(IResource resource) {
    	try {
    		resource.refreshLocal(IResource.DEPTH_INFINITE, 
					new org.eclipse.core.runtime.NullProgressMonitor());
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
	
    /**
     * Creates a jar file from an exisiting folder
     * 
     * @see <a href="http://stackoverflow.com/questions/1281229/how-to-use-jaroutputstream-to-create-a-jar-file">http://stackoverflow.com/questions/1281229/how-to-use-jaroutputstream-to-create-a-jar-file</a>
     * 
     * @param sourceDir path to the files that should be added to the jar
     * @param targetFile jar file that should be created
     * @throws IOException
     */
    
    public static void createJar(File sourceDir, File targetFile) throws IOException {
    	Manifest manifest = new Manifest();
		manifest.getMainAttributes().put(Attributes.Name.MANIFEST_VERSION, "1.0");
		JarOutputStream target = new JarOutputStream(new FileOutputStream(targetFile), manifest);
		add(sourceDir, sourceDir.getPath(), target);
		target.close();
    }
    
    
    
    /**
     * Adds Content to a .jar File
     * 
     * @see <a href="http://stackoverflow.com/questions/1281229/how-to-use-jaroutputstream-to-create-a-jar-file">http://stackoverflow.com/questions/1281229/how-to-use-jaroutputstream-to-create-a-jar-file</a>
     * @see <a href="http://stackoverflow.com/questions/16381281/creating-folders-in-a-zip-folder-in-java">http://stackoverflow.com/questions/16381281/creating-folders-in-a-zip-folder-in-java</a>
     * 
     * @param source File to be added
     * @param sourcePath Path to the File (needed for relativizing the paths)
     * @param target JarOutputStream the File should be written to
     * @throws IOException
     */
    private static void add(File source, String sourcePath, JarOutputStream target) throws IOException {
		if (source.isDirectory()) {
			String name = getRelativePath(source, sourcePath).replace("\\", "/");
			if (!name.isEmpty() || "/".equals(name)) {
				if (!name.endsWith("/"))
					name += "/";
				JarEntry entry = new JarEntry(name);
				entry.setTime(source.lastModified());
				target.putNextEntry(entry);
				target.closeEntry();
			}
			for (File nestedFile : source.listFiles()) {
				add(nestedFile, sourcePath, target);
			}
			return;
		}
		
		JarEntry entry = new JarEntry(getRelativePath(source, sourcePath).replace("\\", "/"));
		entry.setTime(source.lastModified());
		target.putNextEntry(entry);

		try (BufferedInputStream in = new BufferedInputStream(new FileInputStream(source))) {
			byte[] buffer = new byte[1024];
			while (true) {
				int count = in.read(buffer);
				if (count == -1)
					break;
				target.write(buffer, 0, count);
			}
			target.closeEntry();
		}
    }
    
    private static String getRelativePath(File file, String sourceDir) {
        // Trim off the start of source dir path...
        String path = file.getPath().substring(sourceDir.length());
        while (path.startsWith("/") || path.startsWith("\\") || path.startsWith(File.pathSeparator)) {
        	path = path.substring(1);
        }
        return path;
    }
    
    /**
	 * Zips a Folder
	 * 
	 * @see http://stackoverflow.com/questions/15968883/how-to-zip-a-folder-
	 *      itself-using-java
	 * 
	 * @param sourceFolder
	 *            Path of the folder to be zipped
	 * @param zipFile
	 *            Path of the zip file to be created
	 */
	public static void zipFolder(String sourceFolder, String zipFile) {
		byte[] buffer = new byte[1024];
		// get the files inside the sourcefolder
		List<String> fileList = generateFileList(new File(sourceFolder), sourceFolder);

		try {

			FileOutputStream fos = new FileOutputStream(zipFile);
			ZipOutputStream zos = new ZipOutputStream(fos);

			System.out.println("Output to Zip : " + zipFile);

			for (String file : fileList) {

				System.out.println("File Added : " + file);
				ZipEntry ze = new ZipEntry(file);
				zos.putNextEntry(ze);

				FileInputStream in = new FileInputStream(sourceFolder + File.separator + file);

				int len;
				while ((len = in.read(buffer)) > 0) {
					zos.write(buffer, 0, len);
				}

				in.close();
			}

			zos.closeEntry();
			// remember close it
			zos.close();

			System.out.println("Done");
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Unzips a zip file into a directory
	 * 
	 * @param zipFile
	 *            Zip file to be unzipped
	 * @param targetFolder
	 *            Path of the target folder
	 */
	public static void unzipFolder(String zipFile, String targetFolder) {
		try {
			ZipInputStream zin = new ZipInputStream(new FileInputStream(new File(zipFile)));
			ZipEntry entry;
			String name, dir;
			while ((entry = zin.getNextEntry()) != null) {
				name = entry.getName();
				if (entry.isDirectory()) {
					mkdir(new File(targetFolder), name);
					continue;
				}
				/*
				 * this part is necessary because file entry can come before
				 * directory entry where is file located i.e.: /foo/foo.txt
				 * /foo/
				 */
				dir = dirpart(name);
				if (dir != null)
					mkdir(new File(targetFolder), dir);

				extractFile(zin, new File(targetFolder), name);
			}
			zin.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Writes the unzipped content onto the disk
	 * 
	 * @param in
	 * @param outdir
	 * @param name
	 * @throws IOException
	 */
	private static void extractFile(ZipInputStream in, File outdir, String name) throws IOException {
		byte[] buffer = new byte[1024];
		BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(new File(outdir, name)));
		int count = -1;
		while ((count = in.read(buffer)) != -1)
			out.write(buffer, 0, count);
		out.close();
	}

	/**
	 * Creates a new folder at the defined location
	 * 
	 * @param outdir
	 * @param path
	 */
	private static void mkdir(File outdir, String path) {
		File d = new File(outdir, path);
		if (!d.exists())
			d.mkdirs();
	}

	/**
	 * Gets the last part of a Path, i.e., a file or folder name
	 * 
	 * @param name
	 * @return
	 */
	private static String dirpart(String name) {
		int s = name.lastIndexOf(File.separatorChar);
		return s == -1 ? null : name.substring(0, s);
	}

	/**
	 * Traverse a directory and get all files
	 * 
	 * @param node
	 *            file or folder
	 * @return list of files inside a folder
	 */
	private static List<String> generateFileList(File node, String sourceFolder) {
		List<String> fileList = new ArrayList<String>();

		// add file only
		if (node.isFile()) {
			fileList.add(generateZipEntry(node.getAbsoluteFile().toString(), sourceFolder));
		}

		if (node.isDirectory()) {
			String[] subNote = node.list();
			for (String filename : subNote) {
				fileList.addAll(generateFileList(new File(node, filename), sourceFolder));
			}
		}
		return fileList;
	}

	/**
	 * Format the file path for zip
	 * 
	 * @param file
	 *            file path
	 * @return Formatted file path
	 */
	private static String generateZipEntry(String file, String sourceFolder) {
		return file.substring(sourceFolder.length() + 1, file.length());
	}
}
