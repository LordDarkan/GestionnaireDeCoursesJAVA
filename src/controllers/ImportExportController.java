package controllers;

import java.io.File;
import java.time.LocalDate;

import data.Mapper;
import data.SaveManager;
import models.Settings;
import models.Utilisateur;



public abstract class ImportExportController {
	MainController main;
	private Utilisateur user;
	private Mapper mapper;
	private Settings settings;
	
	protected ImportExportController(Utilisateur user, MainController main) {
		this.main = main;
		newLog(user);
	}
	
	protected void newLog(Utilisateur user) {
		this.user = user;
		mapper = Mapper.getInstance();
		settings = mapper.getSettings();
	}
	
	protected void clear(){
		user = null;
		mapper = null;
		settings = null;
	}
	
	protected boolean isAdmin(){
		return user.isAdmin();
	}
	
	protected boolean export(){
		return SaveManager.export();
	}
	
	protected boolean save(){
		return SaveManager.save();
	}
	
	protected boolean saveInterval(LocalDate start, LocalDate end){
		return SaveManager.saveInterval(start, end);
	}

	protected void importer(File file) {
		SaveManager.importSave(file);
		main.logoutAuto();
	}
	
	protected void changeSaveDirectory(File file) {
		settings.setPathSaveDirectory(file.getAbsolutePath());
		Mapper.getInstance().setSettings(settings);
	}

	public File getFileSaveDirectory() {
		File workingDirectory = new File(settings.getPathSaveDirectory());
		while (!workingDirectory.exists()) {
			workingDirectory = workingDirectory.getParentFile();
		}
		return workingDirectory;
	}
}
