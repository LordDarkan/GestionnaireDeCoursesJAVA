package models;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Hopital {
	@Id
	private String name = "";
	private String adresse = "";
	private String cp = "";
	private String localite = "";
	private String tel = "";
	
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
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	
	@Override
	public String toString() {
		return String.format("%s\t|\t%s\t%s (%s)\t|\tN° Tel: %s", name,adresse,localite,cp,tel);
	}
	
	public String getRowCsv() {
		StringBuilder str = new StringBuilder();
		str.append(name);
		str.append(";");
		str.append(adresse);
		str.append(";");
		str.append(cp);
		str.append(";");
		str.append(localite);
		str.append(";");
		str.append(tel);
		str.append(";END");
		return str.toString();
	}
	
	public static String getEnTeteCsv() {
		return "Nom;Adresse;CP;Localité;Téléphone;END";
	}

	public static void valdation(Hopital obj) throws IllegalArgumentException {//TODO
		
	}
}
