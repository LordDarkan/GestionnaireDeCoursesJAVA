package controllers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import data.Mapper;
import models.Appelant;
import models.Chauffeur;
import models.Course;
import models.Hopital;
import models.Residence;
import models.Utilisateur;
import models.itemList.ChauffeurItemList;
import models.itemList.CourseItemList;

public class ListeCourseController {
	private Utilisateur user;
	private Mapper mapper;
	private Course selected;
	private boolean all = false;
	private boolean day = false;
	private LocalDate date = LocalDate.now();
	private Long idChauffeur;
	
	public ListeCourseController(Utilisateur user) {
		newLog(user);
	}
	
	protected void newLog(Utilisateur user) {
		this.user = user;
		mapper = Mapper.getInstance();
		selected = null;
	}
	
	protected void clear(){
		user = null;
		mapper = null;
		selected = null;
	}
	
	protected Course getNewCourse(Long id) {
		Appelant app = mapper.getAppelant(id);
		selected = new Course(app, user.getFullName());
		return selected;
	}
	
	protected boolean isAdmin(){
		return user.isAdmin();
	}
	
	protected String getUserName(){
		return user.getFullName();
	}
	
	protected boolean isSelected(){
		return selected != null;
	}
	
	protected void setSelectedCourse(Long id) {
		this.selected = mapper.getCourse(id);
	}
	
	protected Course getSelectedCourse() {
		return selected;
	}
	protected void save(Course course) {
		selected = course;
		mapper.addOrUpdate(course);
	}

	protected void delete() {
		if (isSelected()) {
			mapper.delete(selected);
			selected = null;
		}
	}

	protected void annuler() {
		selected = null;
	}

	protected void editer() {
		
	}
	
	protected List<CourseItemList> getCourseList() {
		return mapper.getCourse(all,idChauffeur,day,date);
	}
	
	protected List<ChauffeurItemList> getChauffeurList() {
		return mapper.getChauffeurList();
	}
	
	protected Chauffeur getChauffeur(Long id) {
		return mapper.getChauffeur(id);
	}
	
	protected List<String> getResidence() {
		List<String> l = new ArrayList<>();
		l.add("");
		if (selected != null && !selected.getAppelant().getResidence().equals("")) {
			l.add(selected.getAppelant().getResidence());
		}
		return l;
	}
	
	protected List<String> getHopital() {
		List<String> list = new ArrayList<>();
		list.add("");
		list.addAll(mapper.getAllHopital());
		return list;
	}
	
	protected Residence getResidence(String name) {
		return mapper.getResidence(name);
	}
	
	protected Hopital getHopital(String name) {
		return mapper.getHopital(name);
	}

	protected void select(boolean all, ChauffeurItemList chauf, boolean day, LocalDate date) {
		this.all = all;
		if(chauf == null) {
			idChauffeur = null;
		} else {
			idChauffeur = chauf.getId();
		}
		this.day = day;
		this.date = date;
	}

	protected void annulation(String str) {
		selected.setAnnulation(true, user.getFullName());
		selected.setRaisonAnnulation(str);
		Chauffeur none = null;
		selected.setChauffeur(none);
		selected.setChauffeurSec(none);
		mapper.addOrUpdate(selected);
	}
}
