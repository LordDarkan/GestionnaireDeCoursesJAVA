package data;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.logging.Level;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import util.LoggerManager;

public class FileManager {
	


	public static String checkPathPathSaveDirectory(String pathSaveDirectory) {
		String newPath = null;
		File file = new File(pathSaveDirectory);
		if(!file.exists()) { 
			newPath = initSaveDirectory();
		}
		return newPath;
	}
	
	public static String initSaveDirectory() {
		String pathSaveDirectory = null;
		String myDocuments = null;
		try {//TODO WINDOWS
		    Process p =  Runtime.getRuntime().exec("reg query \"HKCU\\Software\\Microsoft\\Windows\\CurrentVersion\\Explorer\\Shell Folders\" /v personal");
		    p.waitFor();

		    InputStream in = p.getInputStream();
		    byte[] b = new byte[in.available()];
		    in.read(b);
		    in.close();

		    myDocuments = new String(b);
		    myDocuments = myDocuments.split("\\s\\s+")[4];
		    pathSaveDirectory = myDocuments+"\\Sauvegarde Gestionnaire de courses";
		} catch(Exception t) {
		    pathSaveDirectory = System.getProperty("user.home")+System.getProperty("file.separator")+"Sauvegarde Gestionnaire de courses";
			LoggerManager.getPathLogger(pathSaveDirectory).log(Level.SEVERE, "path save : "+myDocuments, t);
		}
		
		File file = new File(pathSaveDirectory);
		if(!file.exists()) { 
			file.mkdirs();
		}
		
		return pathSaveDirectory;
	}

	public static void compress(String dirPath,String extention) {
		Path sourceDir = Paths.get(dirPath);
		String zipFileName = dirPath.concat(extention);
		File zip = new File(zipFileName);
		
		if (zip.exists()) {
			delete(zip);
		}
		
		try {
			ZipOutputStream outputStream = new ZipOutputStream(new FileOutputStream(zipFileName));
			Files.walkFileTree(sourceDir, new SimpleFileVisitor<Path>() {
				@Override
				public FileVisitResult visitFile(Path file, BasicFileAttributes attributes) {
					try {
						Path targetFile = sourceDir.relativize(file);
						outputStream.putNextEntry(new ZipEntry(targetFile.toString()));
						byte[] bytes = Files.readAllBytes(file);
						outputStream.write(bytes, 0, bytes.length);
						outputStream.closeEntry();
					} catch (IOException e) {
						e.printStackTrace();
					}
					return FileVisitResult.CONTINUE;
				}
			});
			outputStream.close();
		} catch (IOException e) {
			//TODO err chemin dsauvegarde
			e.printStackTrace();
		}
	}

	public static void delete(File file) {
		if (file.isDirectory()) {
			for (File content : file.listFiles()) {
				delete(content);
			}
		}
		
		file.delete();
	}

	public static void deleteContent(File file) {
		if (file.isDirectory()) {
			for (File content : file.listFiles()) {
				delete(content);
			}
		}
	}
}
