package controllers;

import java.util.ArrayList;
import java.util.List;

import data.Mapper;
import models.Appelant;
import models.Chauffeur;
import models.Course;
import models.Destination;
import models.Residence;
import models.Utilisateur;

public class ViewCourseController {
	private Utilisateur user;
	private Mapper mapper;
	private Course selected;

	public ViewCourseController(Utilisateur user) {
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

	protected List<String> getDestination() {
		List<String> list = new ArrayList<>();
		list.add("");
		list.addAll(mapper.getAllDestination());
		return list;
	}

	protected Residence getResidence(String name) {
		return mapper.getResidence(name);
	}

	protected Destination getDestination(String name) {
		return mapper.getDestination(name);
	}

	public void blank() {
		selected = null;
	}
}
