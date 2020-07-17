package models;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.Id;

import data.FileManager;

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
		
		pathSaveDirectory = FileManager.initSaveDirectory();
		
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