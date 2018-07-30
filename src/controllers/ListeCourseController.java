package controllers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import data.Mapper;
import models.Appelant;
import models.Chauffeur;
import models.Course;
import models.Residence;
import models.Utilisateur;
import models.list.ChauffeurList;
import models.list.CourseList;

public class ListeCourseController {
	private Utilisateur user;
	private Mapper mapper;
	private Course selected;
	private LocalDate date;
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

	public void annuler() {
		selected = null;
	}

	public void editer() {
		
	}
	
	protected List<CourseList> getCourseList() {
		return mapper.getCourse(date,idChauffeur);
	}
	
	protected List<ChauffeurList> getChauffeurList() {
		return mapper.getChauffeurList();
	}
	
	protected Chauffeur getChauffeur(Long id) {
		return mapper.getChauffeur(id);
	}
	
	protected List<String> getResidence() {
		List<String> l = new ArrayList<>();
		l.add("NaN");
		if (selected != null && !selected.getAppelant().getResidence().equals("NaN")) {
			l.add(selected.getAppelant().getResidence());
		}
		return l;
	}
	
	protected List<String> getHopital() {
		return mapper.getAllHopital();
	}
	
	protected Residence getResidence(String name) {
		return mapper.getResidence(name);
	}
}
