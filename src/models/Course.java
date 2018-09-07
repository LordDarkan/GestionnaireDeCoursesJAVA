package models;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import util.DateTime;
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
	private LocalDate dateAttribution = null;
	
	private LocalTime heureDomicile = LocalTime.MIDNIGHT;
	private String residence;
	private String adresseDep;
	private String cpDep;
	private String localiteDep;
	
	private TypeCourse typeCourse = TypeCourse.AUTRE;
	private boolean mutuelle = false;
	
	private LocalTime heureRDV = LocalTime.MIDNIGHT;
	private String hopital = "";
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
	private LocalDate dateAnnulation = null;
	
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
		dateAttribution = LocalDate.now();
	}
	
	public void setChauffeur() {
		this.chauffeur = null;
		this.nameAttribution = "";
		dateAttribution = null;
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
		this.adresseDep = adresseDep.toUpperCase();
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
		this.localiteDep = localiteDep.toUpperCase();
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
		this.adresseDest = adresseDest.toUpperCase();
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
		this.localiteDest = localiteDest.toUpperCase();
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
		this.adresseRet = adresseRet.toUpperCase();
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
		this.localiteRet = localiteRet.toUpperCase();
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
			dateAnnulation = LocalDate.now();
		} else {
			this.nameAnnulation = "";
			dateAnnulation = null;
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
	
	public LocalDate getDateAttribution() {
		return dateAttribution;
	}

	public LocalDate getDateAnnulation() {
		return dateAnnulation;
	}
	
	public String getRowCsv() {
		StringBuilder str = new StringBuilder();
		str.append(nameCreation);
		str.append(";");
		str.append(DateTime.toString(dateCreation));
		str.append(";");
		str.append(DateTime.toString(heureCreation));
		str.append(";");
		str.append(appelant.getId());
		str.append(";");
		str.append(DateTime.toString(date));
		str.append(";");
		if (chauffeur!=null)
			str.append(chauffeur.getId());
		str.append(";");
		str.append(nameAttribution);
		str.append(";");
		if (dateAttribution!=null)
			str.append(DateTime.toString(dateAttribution));
		str.append(";");
		str.append(DateTime.toString(heureDomicile));
		str.append(";");
		str.append(residence);
		str.append(";");
		str.append(adresseDep);
		str.append(";");
		str.append(cpDep);
		str.append(";");
		str.append(localiteDep);
		str.append(";");
		str.append(typeCourse.toString());
		str.append(";");
		str.append(mutuelle?"OUI":"NON");
		str.append(";");
		str.append(DateTime.toString(heureRDV));
		str.append(";");
		str.append(hopital);
		str.append(";");
		str.append(adresseDest);
		str.append(";");
		str.append(cpDest);
		str.append(";");
		str.append(localiteDest);
		str.append(";");
		str.append(attente?"OUI":"NON");
		str.append(";");
		if (chauffeurSec!=null)
			str.append(chauffeurSec.getId());
		str.append(";");
		str.append(DateTime.toString(heureRetour));
		str.append(";");
		str.append(adresseRet);
		str.append(";");
		str.append(cpRet);
		str.append(";");
		str.append(localiteRet);
		str.append(";");
		str.append(notes);
		str.append(";");
		str.append(annulation?"OUI":"NON");
		str.append(";");
		str.append(raisonAnnulation);
		str.append(";");
		str.append(nameAnnulation);
		str.append(";");
		if (dateAnnulation != null)
			str.append(DateTime.toString(dateAnnulation));
		str.append(";END");
		return str.toString();
	}

	public void setNameCreation(String nameCreation) {
		this.nameCreation = nameCreation;
	}

	public void setDateCreation(LocalDate dateCreation) {
		this.dateCreation = dateCreation;
	}

	public void setHeureCreation(LocalTime heureCreation) {
		this.heureCreation = heureCreation;
	}

	public void setAppelant(Appelant appelant) {
		this.appelant = appelant;
	}

	public void setChauffeur(Chauffeur chauffeur) {
		this.chauffeur = chauffeur;
	}

	public void setNameAttribution(String nameAttribution) {
		this.nameAttribution = nameAttribution;
	}

	public void setDateAttribution(LocalDate dateAttribution) {
		this.dateAttribution = dateAttribution;
	}

	public void setAnnulation(boolean annulation) {
		this.annulation = annulation;
	}

	public void setNameAnnulation(String nameAnnulation) {
		this.nameAnnulation = nameAnnulation;
	}

	public void setDateAnnulation(LocalDate dateAnnulation) {
		this.dateAnnulation = dateAnnulation;
	}

	public static String getEnTeteCsv() {
		return "NAMECREATION;DATECREATION;HEURECREATION;APPELANT;DATE;CHAUFFEUR;NAMEATTRIBUTION;DATEATTRIBUTION;HEUREDOMICILE;RESIDENCE;ADRESSEDEP;CPDEP;LOCALITEDEP;TYPECOURSE;MUTUELLE;HEURERDV;HOPITAL;ADRESSEDEST;CPDEST;LOCALITEDEST;ATTENTE;CHAUFFEURSEC;HEURERETOUR;ADRESSERET;CPRET;LOCALITERET;NOTES;ANNULATION;RAISONANNULATION;NAMEANNULATION;DATEANNULATION;END";
	}

	public static void valdation(Course obj) throws IllegalArgumentException {//TODO
		if (obj.appelant == null) throw new IllegalArgumentException("La course n'est pas lié à un appelant");
	}
}