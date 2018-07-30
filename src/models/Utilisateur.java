package models;

import javax.persistence.*;

@Entity
public class Utilisateur {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name = "";
	private String firstname = "";
	private boolean admin = false;

	public Long getId() {
		return id;
	}
	
	public void setName(String name) {
		this.name = name.trim().toUpperCase();
	}

	public void setFirstname(String firstname) {
		firstname = firstname.trim();
		this.firstname = firstname.substring(0,1).toUpperCase()+firstname.substring(1);
	}
	
	public String getFullName() {
		return String.format("%s %s", firstname,name);
	}

	public String getShortName() {
		String result;
		if(name.isEmpty()) {
			result = String.format("%s", firstname);
		} else {
			result = String.format("%s %s.", firstname,name.substring(0,1));
		}
		return result;
	}

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}
	
	public static void valdation(Utilisateur obj) throws IllegalArgumentException {//TODO
		if(obj.firstname.length()<2) throw new IllegalArgumentException("Le prénom est invalide");
		if(obj.name.length()<2) throw new IllegalArgumentException("Le nom est invalide");
	}
}
