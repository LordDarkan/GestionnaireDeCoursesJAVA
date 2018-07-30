package models.list;

import models.Chauffeur;

public class ChauffeurList {
	private Long id;
	private String fullName;
	
	public ChauffeurList(Chauffeur c) {
		id = c.getId();
		fullName = c.getFullName();
	}

	public Long getId() {
		return id;
	}

	public String getFullName() {
		return fullName;
	}
	
	@Override
	public String toString() {
		return fullName;
	}
}
