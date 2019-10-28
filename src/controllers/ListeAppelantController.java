package controllers;

import java.util.ArrayList;
import java.util.List;
import data.Mapper;
import models.Appelant;
import models.Residence;
import models.Utilisateur;
import models.itemList.AppelantItemList;
import models.itemList.ChauffeurItemList;
import models.itemList.CourseItemList;

public abstract class ListeAppelantController {
	private Utilisateur user;
	private Mapper mapper;
	private List<AppelantItemList> appelantsFull;
	private String recherche;
	private List<AppelantItemList> appelants;
	private Appelant selectedAppelant;
	
	public ListeAppelantController(Utilisateur user) {
		newLog(user);
	}
	
	protected Utilisateur getUser() {
		return user;
	}
	
	protected void newLog(Utilisateur user) {
		this.user = user;
		mapper = Mapper.getInstance();
		appelantsFull = mapper.getAppelantList();
		recherche = "";
		appelants = new ArrayList<AppelantItemList>(appelantsFull);
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
	
	protected List<AppelantItemList> search(String search){
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
		if(recherche.length()>=0 || recherche.matches("\\p{Digit}+")) {
			appelants.clear();
			for (AppelantItemList appelant : appelantsFull) {
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
	
	protected void saveNew(Appelant app) {
		mapper.addNew(app);
		selectedAppelant = app;
		appelantsFull = mapper.getAppelantList();
		recherche(true);
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
		List<String> list = new ArrayList<>();
		list.add("");
		list.addAll(mapper.getAllResidence());
		return list;
	}
	
	protected Residence getResidence(String name) {
		Residence r;
		if(name.isEmpty()) {
			r = new Residence();
		} else {
			r = mapper.getResidence(name);
		}
		return r;
	}

	protected void update() {
		appelantsFull = mapper.getAppelantList();
	}

	protected void delFamille(AppelantItemList selectedItem) {
		mapper.deleteFamille(selectedAppelant.getId(),selectedItem.getId());
	}

	protected AppelantItemList addFamille(Long id) {
		return mapper.addFamille(selectedAppelant.getId(),id);
	}

	protected void addProche(Long id) {
		mapper.addProche(selectedAppelant.getId(),id);
	}

	protected void delProche(ChauffeurItemList chauf) {
		mapper.delProche(selectedAppelant.getId(),chauf.getId());
	}

	protected void addRestrict(Long id) {
		mapper.addRestrict(selectedAppelant.getId(),id);
	}

	protected void delRestrict(ChauffeurItemList chauf) {
		mapper.delRestrict(selectedAppelant.getId(),chauf.getId());
	}
	
	protected List<ChauffeurItemList> getChauffeurList() {
		return mapper.getChauffeurList();
	}

	protected List<AppelantItemList> getFamille(List<Long> list) {
		return mapper.getAppelantList(list);
	}

	protected List<ChauffeurItemList> getChauffeurList(List<Long> list) {
		return mapper.getChauffeurList(list);
	}

	protected List<CourseItemList> getCourses(Long id) {
		return mapper.getCourseApplant(id);
	}

	protected boolean validIdApplant(Long id) {
		return mapper.validIdAppelant(id);
	}
}
