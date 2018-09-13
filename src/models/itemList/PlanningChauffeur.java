package models.itemList;

import java.util.LinkedList;
import java.util.List;

import models.Indisponibilite;

public class PlanningChauffeur {
	private String chauffeurName;
	private List<Indisponibilite> indisponibilites;

	public PlanningChauffeur(String chauffeurName) {
		this.chauffeurName = chauffeurName;
		indisponibilites = new LinkedList<Indisponibilite>();
	}
	
	public String getChauffeurName() {
		return chauffeurName;
	}

	public List<Indisponibilite> getIndisponibilite() {
		return indisponibilites;
	}
	
	public void add(Indisponibilite indisponibilite) {
		indisponibilites.add(indisponibilite);
	}
}
