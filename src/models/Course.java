package models;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import util.TypeCourse;

@Entity
public class Course implements IEvenement {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String nameCreation = "";
	private LocalDate dateCreation = LocalDate.now();
	private LocalTime  heureCreation = LocalTime.now();
	
	private Appelant appelant;
	private LocalDate date = LocalDate.now();
	private Chauffeur chauffeur = null;
	private String nameAttribution = "";
	
	private LocalTime heureDomicile = LocalTime.now();
	private String residence;
	private String adresseDep;
	private String cpDep;
	private String localiteDep;
	
	private TypeCourse typeCourse = TypeCourse.AUTRE;
	private boolean mutuelle;
	
	private LocalTime heureRDV = LocalTime.MIDNIGHT;
	private String hopital = "NaN";
	private String adresseDest = "";
	private String cpDest = "";
	private String localiteDest = "";
	
	private boolean attente = false;
	private Chauffeur chauffeurSec = null;
	private LocalTime heureRetour = LocalTime.MIDNIGHT;
	private String adresseRet;
	private String cpRet;
	private String localiteRet;
	
	private String notes = "";
	
	private boolean annulation = false;
	private String raisonAnnulation = "";
	private String nameAnnulation = "";
	
	public Course() {
	}
	
	public Course(Appelant appelant,String nameCreation) {
		this.appelant = appelant;
		this.nameCreation = nameCreation;
		dateCreation = LocalDate.now();
		heureCreation = LocalTime.now();
		residence = appelant.getResidence();
		adresseDep = appelant.getAdresse();
		cpDep = appelant.getCp();
		localiteDep = appelant.getLocalite();
		adresseRet = adresseDep;
		cpRet = cpDep;
		localiteRet = localiteDep;
	}
	
	public Long getId() {
		return id;
	}

	public LocalDate getDateCreation() {
		return dateCreation;
	}

	public LocalTime getHeureCreation() {
		return heureCreation;
	}

	public Appelant getAppelant() {
		return appelant;
	}

	public Chauffeur getChauffeur() {
		return chauffeur;
	}

	public void setChauffeur(Chauffeur chauffeur,String nameAttribution) {
		this.chauffeur = chauffeur;
		this.nameAttribution = nameAttribution;
	}
	
	public void setChauffeur() {
		this.chauffeur = null;
		this.nameAttribution = "";
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public LocalTime getHeureDomicile() {
		return heureDomicile;
	}

	public void setHeureDomicile(LocalTime heureDomicile) {
		this.heureDomicile = heureDomicile;
	}

	public String getAdresseDep() {
		return adresseDep;
	}

	public void setAdresseDep(String adresseDep) {
		this.adresseDep = adresseDep;
	}

	public String getCpDep() {
		return cpDep;
	}

	public void setCpDep(String cpDep) {
		this.cpDep = cpDep;
	}

	public String getLocaliteDep() {
		return localiteDep;
	}

	public void setLocaliteDep(String localiteDep) {
		this.localiteDep = localiteDep;
	}

	public TypeCourse getTypeCourse() {
		return typeCourse;
	}

	public void setTypeCourse(TypeCourse typeCourse) {
		this.typeCourse = typeCourse;
	}

	public boolean isMutuelle() {
		return mutuelle;
	}

	public void setMutuelle(boolean mutuelle) {
		this.mutuelle = mutuelle;
	}

	public LocalTime getHeureRDV() {
		return heureRDV;
	}

	public void setHeureRDV(LocalTime heureRDV) {
		this.heureRDV = heureRDV;
	}

	public String getHopital() {
		return hopital;
	}

	public void setHopital(String hopital) {
		this.hopital = hopital;
	}

	public String getAdresseDest() {
		return adresseDest;
	}

	public void setAdresseDest(String adresseDest) {
		this.adresseDest = adresseDest;
	}

	public String getCpDest() {
		return cpDest;
	}

	public void setCpDest(String cpDest) {
		this.cpDest = cpDest;
	}

	public String getLocaliteDest() {
		return localiteDest;
	}

	public void setLocaliteDest(String localiteDest) {
		this.localiteDest = localiteDest;
	}

	public Chauffeur getChauffeurSec() {
		return chauffeurSec;
	}

	public void setChauffeurSec(Chauffeur chauffeurSec) {
		this.chauffeurSec = chauffeurSec;
	}

	public LocalTime getHeureRetour() {
		return heureRetour;
	}

	public void setHeureRetour(LocalTime heureRetour) {
		this.heureRetour = heureRetour;
	}

	public String getAdresseRet() {
		return adresseRet;
	}

	public void setAdresseRet(String adresseRet) {
		this.adresseRet = adresseRet;
	}

	public String getCpRet() {
		return cpRet;
	}

	public void setCpRet(String cpRet) {
		this.cpRet = cpRet;
	}

	public String getLocaliteRet() {
		return localiteRet;
	}

	public void setLocaliteRet(String localiteRet) {
		this.localiteRet = localiteRet;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public boolean isAnnulation() {
		return annulation;
	}

	public void setAnnulation(boolean annulation, String nameAnnulation) {
		this.annulation = annulation;
		if(annulation) {
			this.nameAnnulation = nameAnnulation;
		}
	}

	public String getRaisonAnnulation() {
		return raisonAnnulation;
	}

	public void setRaisonAnnulation(String raisonAnnulation) {
		this.raisonAnnulation = raisonAnnulation;
	}

	public String getNameCreation() {
		return nameCreation;
	}

	public String getNameAnnulation() {
		return nameAnnulation;
	}

	public String getNameAttribution() {
		return nameAttribution;
	}

	public String getResidence() {
		return residence;
	}

	public void setResidence(String residence) {
		this.residence = residence;
	}

	public boolean isAttente() {
		return attente;
	}

	public void setAttente(boolean attente) {
		this.attente = attente;
	}
	
	public static void valdation(Course obj) throws IllegalArgumentException {//TODO
		if (obj.appelant == null) throw new IllegalArgumentException("La course n'est pas lié à un appelant");
	}
}