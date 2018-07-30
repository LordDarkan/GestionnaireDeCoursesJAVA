package controllers;

import java.util.ArrayList;
import java.util.List;

import data.Mapper;
import models.Chauffeur;
import models.Utilisateur;

public abstract class ListeChauffeurController {
	private Utilisateur user;
	private Mapper mapper;
	private List<Chauffeur> chauffeursFull;
	private String recherche;
	private List<Chauffeur> chauffeurs;
	private Chauffeur chauffeur;
	
	public ListeChauffeurController(Utilisateur user) {
		newLog(user);
	}
	
	protected void newLog(Utilisateur user) {
		this.user = user;
		mapper = Mapper.getInstance();
		chauffeursFull = mapper.getAllChauffeur();
		recherche = "";
		chauffeurs = new ArrayList<Chauffeur>(chauffeursFull);
		chauffeur = null;
	}
	
	protected void clear(){
		user = null;
		mapper = null;
		chauffeursFull = null;
		recherche = null;
		chauffeurs = null;
		chauffeur = null;
	}
	
	protected boolean isAdmin(){
		return user.isAdmin();
	}
	
	protected boolean isSelected(){
		return chauffeur!= null;
	}
	
	protected List<Chauffeur> search(String search){
		recherche = search.trim().toLowerCase();
		recherche(false);
		return chauffeurs;
	}
	
	private boolean recherche(boolean b){
		boolean search = false;
		if(recherche.length()>=3) {
			chauffeurs.clear();
			for (Chauffeur chauffeur : chauffeursFull) {
				if (chauffeur.getName().toLowerCase().startsWith(recherche)
						||chauffeur.getFirstname().toLowerCase().startsWith(recherche))
				{
					chauffeurs.add(chauffeur);
				}
			}
			search = true;
		} else if(b || chauffeurs.size()<chauffeursFull.size()) {
			chauffeurs.clear();
			chauffeurs.addAll(chauffeursFull);
			search = true;
		}
		return search;
	}
	
	protected void setSelectedChauffeur(Chauffeur newValue) {
		chauffeur = newValue;
	}

	protected Chauffeur getSelectedAppelant() {
		Chauffeur app = chauffeur;
		if(app == null) {
			app = new Chauffeur();
		}
		return app;
	}

	protected void save(Chauffeur app) {
		mapper.addOrUpdate(app);
		if(app.getId()!=null)
			chauffeur = app;
		chauffeursFull = mapper.getAllChauffeur();
		recherche(true);
	}

	protected void delete() {
		mapper.delete(chauffeur);
		chauffeur = null;
		chauffeursFull = mapper.getAllChauffeur();
		recherche(true);
	}

	protected void editer() {
		
	}
	
	protected void valdation(Chauffeur chauf) {//TODO
		if(chauf.getFirstname().length()<2) throw new IllegalArgumentException("Le prénom est invalide");
	}
	
	public void selected() {
		
	}
}
