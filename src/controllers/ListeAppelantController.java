package controllers;

import java.util.ArrayList;
import java.util.List;

import data.Mapper;
import models.Appelant;
import models.Residence;
import models.Utilisateur;
import models.item.AppelantList;

public abstract class ListeAppelantController {
	private Utilisateur user;
	private Mapper mapper;
	private List<AppelantList> appelantsFull;
	private String recherche;
	private List<AppelantList> appelants;
	private Appelant selectedAppelant;
	
	public ListeAppelantController(Utilisateur user) {
		newLog(user);
	}
	
	protected void newLog(Utilisateur user) {
		this.user = user;
		mapper = Mapper.getInstance();
		appelantsFull = mapper.getAppelantList();
		recherche = "";
		appelants = new ArrayList<AppelantList>(appelantsFull);
		selectedAppelant = null;
	}
	
	protected void clear(){
		user = null;
		mapper = null;
		appelantsFull = null;
		recherche = null;
		appelants = null;
		selectedAppelant = null;
	}
	
	protected boolean isAdmin(){
		return user.isAdmin();
	}
	
	protected boolean isSelected(){
		return selectedAppelant!= null;
	}
	
	protected List<AppelantList> search(String search){
		if (search != null) {
			recherche = search.trim().toLowerCase();
			recherche(false);
		} else if (recherche == null) {
			recherche = "";
			recherche(true);
		} else {
			recherche(true);
		}
		return appelants;
	}
	
	private boolean recherche(boolean b){
		boolean search = false;
		if(recherche.length()>=3 || recherche.matches("\\p{Digit}+")) {
			appelants.clear();
			for (AppelantList appelant : appelantsFull) {
				if (appelant.getName().toLowerCase().startsWith(recherche)
						||appelant.getFirstname().toLowerCase().startsWith(recherche)
						||appelant.getId().toString().startsWith(recherche))
				{
					appelants.add(appelant);
				}
			}
			search = true;
		} else if(b || appelants.size()<appelantsFull.size()) {
			appelants.clear();
			appelants.addAll(appelantsFull);
			search = true;
		}
		return search;
	}
	
	protected void setSelectedAppelant(Long id) {
		selectedAppelant = mapper.getAppelant(id);
	}

	protected Appelant getSelectedAppelant() {
		Appelant app = selectedAppelant;
		if(app == null) {
			app = new Appelant();
		}
		return app;
	}

	protected void save(Appelant app) {
		mapper.addOrUpdate(app);
		if(app.getId()!=null)
			selectedAppelant = app;
		appelantsFull = mapper.getAppelantList();
		recherche(true);
	}

	protected void delete() {
		mapper.delete(selectedAppelant);
		selectedAppelant = null;
		appelantsFull = mapper.getAppelantList();
		recherche(true);
	}

	protected void editer() {
		
	}
	
	protected List<String> getResidence() {
		return mapper.getAllResidence();
	}
	
	protected Residence getResidence(String name) {
		return mapper.getResidence(name);
	}

	public void update() {
		appelantsFull = mapper.getAppelantList();
	}
}
