package models;

import javax.persistence.*;

import util.Role;

@Entity
public class Utilisateur {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name = "";
	private String firstname = "";
	private Role role = Role.PT;

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

	public void setRole(Role role) {
		this.role = role;
	}
	
	public Role getRole() {
		return role;
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
}
