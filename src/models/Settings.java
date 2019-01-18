package models;

import java.io.File;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.logging.Level;

import javax.persistence.Entity;
import javax.persistence.Id;

import util.LoggerManager;

@Entity
public class Settings {
	public static final Long ID = 666L;
	
	@Id
	private Long idSettings = ID;
	private String pathSaveDirectory;
	private Long saveFrequency;
	private LocalDate lasteSave;
	
	public void init() {
		idSettings = ID;
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
		setSaveFrequency(30L);//TODO setSaveFrequency
		lasteSave = LocalDate.now();
	}
	
	public String getPathSaveDirectory() {
		return pathSaveDirectory;
	}
	public void setPathSaveDirectory(String pathSaveDirectory) {
		this.pathSaveDirectory = pathSaveDirectory;
	}
	public LocalDate getLastSave() {
		return lasteSave;
	}
	public void save() {
		this.lasteSave = LocalDate.now();
	}

	public long getSaveFrequency() {
		return saveFrequency;
	}

	public void setSaveFrequency(Long saveFrequency) {
		this.saveFrequency = saveFrequency;
	}
}