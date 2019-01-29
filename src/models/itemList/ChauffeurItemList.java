package models.itemList;

import models.Chauffeur;

public class ChauffeurItemList {
	private Long id;
	private String name;
	private String firstname;
	
	public ChauffeurItemList(Chauffeur c) {
		id = c.getId();
		name = c.getName();
		firstname = c.getFirstname();
	}

	public Long getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}

	public String getFirstname() {
		return firstname;
	}

	@Override
	public String toString() {
		return String.format("%s %s", name, firstname);
	}
}
