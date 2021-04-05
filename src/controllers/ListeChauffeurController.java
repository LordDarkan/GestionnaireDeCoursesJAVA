package controllers;

import java.util.ArrayList;
import java.util.List;

import data.Mapper;
import models.Chauffeur;
import models.Indisponibilite;
import models.Utilisateur;
import models.itemList.ChauffeurItemList;

public abstract class ListeChauffeurController {
	private Utilisateur user;
	private Mapper mapper;
	private List<ChauffeurItemList> chauffeursFull;
	private String recherche;
	private List<ChauffeurItemList> chauffeurs;
	private Chauffeur selectedChauffeur;
	
	public ListeChauffeurController(Utilisateur user) {
		newLog(user);
	}
	
	protected void newLog(Utilisateur user) {
		this.user = user;
		mapper = Mapper.getInstance();
		chauffeursFull = mapper.getAllChauffeurList();
		recherche = "";
		chauffeurs = new ArrayList<ChauffeurItemList>(chauffeursFull);
		selectedChauffeur = null;
	}
	
	protected void clear(){
		user = null;
		mapper = null;
		chauffeursFull = null;
		recherche = null;
		chauffeurs = null;
		selectedChauffeur = null;
	}
	
	protected boolean isAdmin(){
		return user.isAdmin();
	}
	
	protected boolean isSelected(){
		return selectedChauffeur!= null;
	}
	
	protected List<ChauffeurItemList> search(String search){
		if (search != null) {
			recherche = search.trim().toLowerCase();
			recherche(false);
		} else if (recherche == null) {
			recherche = "";
			recherche(true);
		} else {
			recherche(true);
		}
		return chauffeurs;
	}
	
	private boolean recherche(boolean b){
		boolean search = false;
		if(recherche.length()>=3) {
			chauffeurs.clear();
			for (ChauffeurItemList chauffeur : chauffeursFull) {
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
	
	protected void setSelectedChauffeur(Long id) {
		selectedChauffeur = mapper.getChauffeur(id);
	}

	protected Chauffeur getSelectedChauffeur() {
		Chauffeur app = selectedChauffeur;
		if(app == null) {
			app = new Chauffeur();
		}
		return app;
	}

	protected void save(Chauffeur app) {
		if(app.getId()!=null)
			selectedChauffeur = app;
		mapper.addOrUpdate(app);
		chauffeursFull = mapper.getAllChauffeurList();
		recherche(true);
	}

	protected void delete() {
		mapper.delete(selectedChauffeur);
		selectedChauffeur = null;
		chauffeursFull = mapper.getAllChauffeurList();
		recherche(true);
	}

	protected void editer() {
		
	}
	
	public void selected() {
		
	}
	
	public void newIndisponibilite(Indisponibilite indisponibilite) {
		indisponibilite.setIdChauffeur(selectedChauffeur.getId());
		mapper.addOrUpdateIndisponibilite(indisponibilite);
	}
}
