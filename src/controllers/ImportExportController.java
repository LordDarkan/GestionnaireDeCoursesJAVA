package controllers;

import java.io.File;
import java.util.List;

import data.Import;
import data.Mapper;
import models.Appelant;
import models.Utilisateur;



public abstract class ImportExportController {
	private Utilisateur user;
	private Mapper mapper;
	
	protected ImportExportController(Utilisateur user) {
		newLog(user);
	}
	
	protected void newLog(Utilisateur user) {
		this.user = user;
		mapper = Mapper.getInstance();
	}
	
	protected void clear(){
		user = null;
		mapper = null;
	}
	
	protected boolean isAdmin(){
		return user.isAdmin();
	}

	protected void importer(File file) {
		List<Appelant> appelants = Import.old2Appelants(file);
		mapper.importApplants(appelants);
	}
}
