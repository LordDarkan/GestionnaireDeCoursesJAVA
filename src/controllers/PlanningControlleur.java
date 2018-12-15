package controllers;

import java.time.LocalDate;
import java.util.Collection;

import data.Mapper;
import models.Utilisateur;
import models.itemList.PlanningChauffeur;

public class PlanningControlleur {
	private Utilisateur user;
	private Mapper mapper;
	
	protected void newLog(Utilisateur user) {
		this.user = user;
		mapper = Mapper.getInstance();
	}
	
	protected PlanningControlleur(Utilisateur user) {
		newLog(user);
	}
	
	protected void clear(){
		user.getId();//TODO to suppr
		user = null;
		mapper = null;
	}
	
	protected Collection<PlanningChauffeur> getListPlanningChauffeur(LocalDate date){
		return mapper.getPlanning(date);
	}
}
