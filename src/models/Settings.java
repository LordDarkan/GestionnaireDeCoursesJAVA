package models;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Settings {
	@Id
	private Long id = 0L;
	private String pathExportDirectory;
	
	
	public String getPathExportDirectory() {
		return pathExportDirectory;
	}
	public void setPathExportDirectory(String pathExportDirectory) {
		this.pathExportDirectory = pathExportDirectory;
	}
}
