package models.itemList;

import models.Appelant;

public class AppelantItemList {
	private Long id;
	private String name;
	private String firstname;
	
	public AppelantItemList(Appelant a) {
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
		return String.format("%s %s", name, firstname);
	}
	
	@Override
	public String toString() {
		return String.format("(%d)\t\t%s %s", id, name, firstname);
	}
}
