package models;

import javax.persistence.Entity;
import javax.persistence.Id;

import data.CSVRow;
import util.Gate;

@Entity
public class Residence implements CSVRow {
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
		this.name =  Gate.encoding(name);
	}
	public String getAdresse() {
		return adresse;
	}
	public void setAdresse(String adresse) {
		this.adresse =  Gate.encoding(adresse);
	}
	public String getCp() {
		return cp;
	}
	public void setCp(String cp) {
		this.cp =  Gate.encoding(cp);
	}
	public String getLocalite() {
		return localite;
	}
	public void setLocalite(String localite) {
		this.localite =  Gate.encoding(localite);
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel =  Gate.encoding(tel);
	}
	
	@Override
	public String toString() {
		return String.format("%s\t|\t%s\t%s (%s)\t|\tN° Tel: %s", name,adresse,localite,cp,tel);
	}

	@Override
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
	
	@Override
	public String getEnTeteCsv() {
		return enteteCSV;
	}
	
	public static final String enteteCSV = "Nom;Adresse;CP;Localité;Téléphone;END";
	
	public static void valdation(Hopital obj) throws IllegalArgumentException {//TODO
		
	}
}
