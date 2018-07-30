package models;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Residence {
	@Id
	private String name = "";
	private String adresse = "";
	private String cp = "";
	private String localite = "";
	
	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public String getAdresse() {
		return adresse;
	}



	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}



	public String getCp() {
		return cp;
	}



	public void setCp(String cp) {
		this.cp = cp;
	}



	public String getLocalite() {
		return localite;
	}



	public void setLocalite(String localite) {
		this.localite = localite;
	}



	public static void valdation(Hopital obj) throws IllegalArgumentException {//TODO
		
	}
}
