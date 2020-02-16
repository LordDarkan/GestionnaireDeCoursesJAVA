package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import data.CSVRow;
import util.Gate;
import util.Titre;

@Entity
public class Chauffeur implements CSVRow {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="id")
	private Long id;
	@Column(name="titre")
	private Titre titre = Titre.Aucun;
	@Column(name="name")
	private String name = "";
	@Column(name="firstname")
	private String firstname = "";
	@Column(name="tel")
	private String tel = "";
	@Column(name="adresse")
	private String adresse = "";
	
	@Column(name="cp")
	private String cp = "";
	@Column(name="localite")
	private String localite = "";
	@Column(name="infos")
	private String infos = "";
	@Column(name="display")
	private boolean display = true;
	
	@Column(name="str1")
	private String str1 = null;
	@Column(name="str2")
	private String str2 = null;
	@Column(name="str3")
	private String str3 = null;
	
	
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name =  Gate.encoding(name);
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname =  Gate.encoding(firstname);
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel =  Gate.encoding(tel);
	}
	public Long getId() {
		return id;
	}
	public String getAdresse() {
		return adresse;
	}
	public void setAdresse(String adresse) {
		this.adresse =  Gate.encoding(adresse);
	}
	public String getInfos() {
		return infos;
	}
	public void setInfos(String infos) {
		this.infos =  Gate.encoding(infos);
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
	
	public String getFullName() {
		return String.format("%s %s", name, firstname);
	}
	public Titre getTitre() {
		return titre;
	}
	public void setTitre(Titre titre) {
		this.titre = titre;
	}

	public boolean isDisplay() {
		return display;
	}
	public void setDisplay(boolean display) {
		this.display = display;
	}
	@Override
	public String getRowCsv() {
		StringBuilder str = new StringBuilder();
		str.append(id);
		str.append(";");
		str.append(titre);
		str.append(";");
		str.append(name);
		str.append(";");
		str.append(firstname);
		str.append(";");
		str.append(tel);
		str.append(";");
		str.append(adresse);
		str.append(";");
		str.append(cp);
		str.append(";");
		str.append(localite);
		str.append(";");
		str.append(infos);
		str.append(";");
		str.append(display?"OUI":"NON");
		str.append(";END");
		return str.toString();
	}
	
	@Override
	public String getEnTeteCsv() {
		return enteteCSV;
	}
	
	public static final String enteteCSV = "ID;Titre;Nom;Prénom;Téléphone, gsm;Adresse;CP;Localité;Infos;Display;END";
	
	public static void valdation(Chauffeur obj) throws IllegalArgumentException {//TODO
		if(obj.getFirstname().length()<2) throw new IllegalArgumentException("Le prénom est invalide");
	}
}
