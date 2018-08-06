package controllers;

import java.io.File;

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
	
	protected void save(){
		SaveManager.save();
	}

	protected void importer(File file) {
		SaveManager.importSave(file);
		main.logout();
	}
	
	protected void importerOld(File file) {
		Mapper.getInstance().importApplantsOld(SaveManager.old2Appelants(file));
		main.logout();
	}

	public File getFileSaveDirectory() {
		File workingDirectory = new File(settings.getPathSaveDirectory());
		while (!workingDirectory.exists()) {
			workingDirectory = workingDirectory.getParentFile();
		}
		return workingDirectory;
	}
}
