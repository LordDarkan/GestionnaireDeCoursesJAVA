package models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import util.Titre;

@Entity
public class Chauffeur {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private Titre titre = Titre.Aucun;
	private String name = "";
	private String firstname = "";
	private String tel = "";
	private String adresse = "";

	private String cp = "";
	private String localite = "";
	private String infos = "";
	
	
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public Long getId() {
		return id;
	}
	public String getAdresse() {
		return adresse;
	}
	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}
	public String getInfos() {
		return infos;
	}
	public void setInfos(String infos) {
		this.infos = infos;
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
	
	public String getFullName() {
		return String.format("%s %s", name, firstname);
	}
	public Titre getTitre() {
		return titre;
	}
	public void setTitre(Titre titre) {
		this.titre = titre;
	}
	
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
		str.append(";END");
		return str.toString();
	}
	
	public static String getEnTeteCsv() {
		return "ID;Titre;Nom;Prénom;Téléphone, gsm;Adresse;CP;Localité;Infos;END";
	}
	
	public static void valdation(Chauffeur obj) throws IllegalArgumentException {//TODO
		if(obj.getFirstname().length()<2) throw new IllegalArgumentException("Le prénom est invalide");
	}
}
