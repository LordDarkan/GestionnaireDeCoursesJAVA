package models.item;

import models.Appelant;

public class AppelantList {
	private Long id;
	private String name;
	private String firstname;
	
	public AppelantList(Appelant a) {
		id = a.getId();
		name = a.getName();
		firstname = a.getFirstname();
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

	public String getFullName() {
		return String.format("%s %s", firstname, name);
	}
	
	@Override
	public String toString() {
		return String.format("%d %s %s", id, firstname, name);
	}
}
