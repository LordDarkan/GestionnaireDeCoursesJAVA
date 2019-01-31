package models;

import java.time.LocalDate;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import data.CSVRow;
import util.DateTime;
import util.FLS;
import util.Gate;
import util.Titre;

@Entity
public class Appelant implements CSVRow {
	@Id
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private Titre titre = Titre.Aucun;
	private String name = "";
	private String firstname = "";
	private LocalDate birthday = null;
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
	private String aideParticuliere = "RAS";
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
		this.name =  Gate.encoding(name);
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname =  Gate.encoding(firstname);
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
		this.cp =  Gate.encoding(cp);
	}
	public String getLocalite() {
		return localite;
	}
	public void setLocalite(String localite) {
		this.localite =  Gate.encoding(localite);
	}
	public String getQuartier() {
		return quartier;
	}
	public void setQuartier(String quartier) {
		this.quartier =  Gate.encoding(quartier);
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel =  Gate.encoding(tel);
	}
	public String getMobilite() {
		return mobilite;
	}
	public void setMobilite(String mobilite) {
		this.mobilite =  Gate.encoding(mobilite);
	}
	public String getMutualite() {
		return mutualite;
	}
	public void setMutualite(String mutualite) {
		this.mutualite =  Gate.encoding(mutualite);
	}
	public String getAffiniteStr() {
		return affinite;
	}
	public void setAffiniteStr(String affinite) {
		this.affinite =  Gate.encoding(affinite);
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
		this.restriction =  Gate.encoding(restriction);
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
		this.payement =  Gate.encoding(payement);
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
		this.famille =  Gate.encoding(famille);
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
	public String getAideParticuliere() {
		return aideParticuliere;
	}
	public void setAideParticuliere(String aideParticuliere) {
		this.aideParticuliere =  Gate.encoding(aideParticuliere);
	}
	public String getInfos() {
		return infos;
	}
	public void setInfos(String infos) {
		this.infos =  Gate.encoding(infos);
	}
	public String getRemarques() {
		return remarques;
	}
	public void setRemarques(String remarques) {
		this.remarques =  Gate.encoding(remarques);
	}
	public String getAdresse() {
		return adresse;
	}
	public void setAdresse(String adresse) {
		this.adresse =  Gate.encoding(adresse);
	}
	public String getResidence() {
		return residence;
	}
	public void setResidence(String home) {
		this.residence =  Gate.encoding(home);
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
		if (birthday!=null)
			str.append(DateTime.saveToString(birthday));//date
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
		str.append(aideParticuliere);
		str.append(";");
		str.append(infos);
		str.append(";");
		str.append(remarques);
		str.append(";END");
		return str.toString();
	}
	
	@Override
	public String getEnTeteCsv() {
		return enteteCSV;
	}
	
	public static final String enteteCSV = "Code;Titre;Nom;Prenom;Date de naissance;Telephone, gsm;Residence;Adresse;CP;Localite;Quartier;Famille;Conducteurs proches;Restriction chauffeurs;Mutualite;Paiement;Cotisation;mobilite;Aide particuliere;Infos utiles;Autres remarques;END";
	
	public static void valdation(Appelant obj) throws IllegalArgumentException {//TODO
		if(obj.firstname.length()<2) throw new IllegalArgumentException("Le prénom est invalide");
	}
}
