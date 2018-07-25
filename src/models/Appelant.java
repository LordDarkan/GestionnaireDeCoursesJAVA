package models;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Appelant {
	@Id
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String name = "";
	private String firstname = "";
	private Date birthday = new Date();
	private String tel = "";
	private String adresse = "";
	private String cp = "";
	private String localite = "";
	private String quartier = "";

	private List<Appelant> famille;

	private List<Chauffeur> affinite;
	private List<Chauffeur> restriction;

	private String mutualite = "";
	private String payement = "";
	private int cotisation = 0;
	
	private String mobilite = "";
	private String aideParticulière = "";
	private String infos = "";
	private String remarques = "";
	
	
	public Long getId() {
		return id;
	}
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
	@SuppressWarnings("deprecation")
	public String getBirthday() {
		return String.format("%d-%d-%d", birthday.getDay(),birthday.getMonth(),birthday.getYear());
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
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
	public String getQuartier() {
		return quartier;
	}
	public void setQuartier(String quartier) {
		this.quartier = quartier;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getMobilite() {
		return mobilite;
	}
	public void setMobilite(String mobilite) {
		this.mobilite = mobilite;
	}
	public String getMutualite() {
		return mutualite;
	}
	public void setMutualite(String mutualite) {
		this.mutualite = mutualite;
	}
	public List<Chauffeur> getAffinite() {
		return affinite;
	}
	public void setAffinite(List<Chauffeur> affinite) {
		this.affinite = affinite;
	}
	public List<Chauffeur> getRestriction() {
		return restriction;
	}
	public void setRestriction(List<Chauffeur> restriction) {
		this.restriction = restriction;
	}
	public String getPayement() {
		return payement;
	}
	public void setPayement(String payement) {
		this.payement = payement;
	}
	public int getCotisation() {
		return cotisation;
	}
	public void setCotisation(int cotisation) {
		this.cotisation = cotisation;
	}
	public List<Appelant> getFamille() {
		return famille;
	}
	public void setFamille(List<Appelant> famille) {
		this.famille = famille;
	}
	public String getAideParticulière() {
		return aideParticulière;
	}
	public void setAideParticulière(String aideParticulière) {
		this.aideParticulière = aideParticulière;
	}
	public String getInfos() {
		return infos;
	}
	public void setInfos(String infos) {
		this.infos = infos;
	}
	public String getRemarques() {
		return remarques;
	}
	public void setRemarques(String remarques) {
		this.remarques = remarques;
	}
	public String getAdresse() {
		return adresse;
	}
	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}
}
