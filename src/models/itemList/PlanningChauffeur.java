package models.itemList;

import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;

import models.Indisponibilite;

public class PlanningChauffeur {
	private String chauffeurName;
	private Set<Indisponibilite> indisponibilites;

	public PlanningChauffeur(String chauffeurName) {
		this.chauffeurName = chauffeurName;
		indisponibilites = new TreeSet<Indisponibilite>();
	}
	
	public String getChauffeurName() {
		return chauffeurName;
	}

	public Collection<Indisponibilite> getIndisponibilite() {
		return indisponibilites;
	}
	
	public void add(Indisponibilite indisponibilite) {
		indisponibilites.add(indisponibilite);
	}
}
