package data;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class FileManager {

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
