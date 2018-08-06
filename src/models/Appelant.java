package models;

import java.time.LocalDate;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import util.DateTime;
import util.FLS;
import util.Titre;

@Entity
public class Appelant {
	@Id
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private Titre titre = Titre.Aucun;
	private String name = "";
	private String firstname = "";
	private LocalDate birthday = LocalDate.now();
	private String tel = "";
	
	private String residence = "";
	private String adresse = "";
	private String cp = "";
	private String localite = "";
	private String quartier = "";

	private String famille = "";
	private String affinite = "";
	private String restriction = "";
	
	private String mutualite = "";
	private String payement = "";
	private int cotisation = 0;
	
	private String mobilite = "RAS";
	private String aideParticulière = "RAS";
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
	
	public String getStrBirthday() {
		return String.format("%d-%d-%d", birthday.getDayOfMonth(),birthday.getMonth(),birthday.getYear());
	}
	
	public LocalDate getBirthday() {
		return birthday;
	}
	
	public void setBirthday(LocalDate birthday) {
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
		this.localite = localite.toUpperCase();
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
	public String getAffiniteStr() {
		return affinite;
	}
	public void setAffiniteStr(String affinite) {
		this.affinite = affinite;
	}
	public List<Long> getAffinite() {
		return FLS.toList(affinite);
	}
	public void addAffinite(Long id) {
		this.affinite = FLS.addToString(affinite, id);
	}
	public void removeAffinite(Long id) {
		this.affinite = FLS.removeToString(affinite, id);
	}
	public String getRestrictionStr() {
		return restriction;
	}
	public void setRestrictionStr(String restriction) {
		this.restriction = restriction;
	}
	public List<Long> getRestriction() {
		return FLS.toList(restriction);
	}
	public void addRestriction(Long id) {
		this.restriction = FLS.addToString(restriction, id);
	}
	public void removeRestriction(Long id) {
		this.restriction = FLS.removeToString(restriction, id);
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
		if (cotisation>this.cotisation)
			this.cotisation = cotisation;
	}
	public String getFamilleStr() {
		return famille;
	}
	public void setFamilleStr(String famille) {
		this.famille = famille;
	}
	public List<Long> getFamille() {
		return FLS.toList(famille);
	}
	public void removeFamille(Long id) {
		this.famille = FLS.removeToString(famille, id);
	}
	public void addFamille(Long id) {
		this.famille = FLS.addToString(famille, id);
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
		this.adresse = adresse.toUpperCase();
	}
	public String getResidence() {
		return residence;
	}
	public void setResidence(String home) {
		this.residence = home;
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
		str.append(DateTime.toString(birthday));
		str.append(";");
		str.append(tel);
		str.append(";");
		str.append(residence);
		str.append(";");
		str.append(adresse);
		str.append(";");
		str.append(cp);
		str.append(";");
		str.append(localite);
		str.append(";");
		str.append(quartier);
		str.append(";");
		str.append(famille);
		str.append(";");
		str.append(affinite);
		str.append(";");
		str.append(restriction);
		str.append(";");
		str.append(mutualite);
		str.append(";");
		str.append(payement);
		str.append(";");
		str.append(cotisation);
		str.append(";");
		str.append(mobilite);
		str.append(";");
		str.append(aideParticulière);
		str.append(";");
		str.append(infos);
		str.append(";");
		str.append(remarques);
		str.append(";END");
		return str.toString();
	}
	
	public static String getEnTeteCsv() {
		return "Code;Titre;Nom;Prénom;Date de naissance;Téléphone, gsm;Résidence;Adresse;CP;Localité;Quartier;Famille;Conducteurs proches;Restriction chauffeurs;Mutualité;Paiement;Cotisation;mobilite;Aide particulière;Infos utiles;Autres remarques;END";
	}
	
	public static void valdation(Appelant obj) throws IllegalArgumentException {//TODO
		if(obj.firstname.length()<2) throw new IllegalArgumentException("Le prénom est invalide");
	}
}
