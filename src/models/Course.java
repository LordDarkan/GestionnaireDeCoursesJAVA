package models;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import data.CSVRow;
import util.DateTime;
import util.Trajet;
import util.TypeCourse;

@Entity
public class Course implements CSVRow {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String nameCreation = "";
	private LocalDate dateCreation = LocalDate.now();
	private LocalTime  heureCreation = LocalTime.now();
	
	private Appelant appelant;
	private LocalDate date = LocalDate.now();
	private Chauffeur chauffeur = null;
	private String nameAttribution = "";//TODO RENAME MODIF + séparé chauffeur 
	private LocalDate dateAttribution = null;//RENAME MODIF + séparé chauffeur 
	
	private LocalTime heureDomicile = LocalTime.MIDNIGHT;
	private String residence;
	private String adresseDep;
	private String cpDep;
	private String localiteDep;
	//private Trajet mode = Trajet.ALLER_RETOUR;//TODO ADD mode course
	private TypeCourse typeCourse = TypeCourse.AUTRE;
	
	@SuppressWarnings("unused")
	@Deprecated
	private boolean mutuelle = false;
	
	private LocalTime heureRDV = LocalTime.MIDNIGHT;
	private String hopital = "";
	private String adresseDest = "";
	private String cpDest = "";
	private String localiteDest = "";
	
	@SuppressWarnings("unused")
	@Deprecated
	private boolean attente = true;
	
	@SuppressWarnings("unused")
	@Deprecated
	private Chauffeur chauffeurSec = null;
	
	private LocalTime heureRetour = LocalTime.MIDNIGHT;
	private String adresseRet;
	private String cpRet;
	private String localiteRet;
	
	private String notes = "";
	
	@SuppressWarnings("unused")
	@Deprecated
	private boolean annulation = false;
	
	@Deprecated//Trajet
	private String raisonAnnulation = "";
	
	@SuppressWarnings("unused")
	@Deprecated
	private String nameAnnulation = "";
	@SuppressWarnings("unused")
	@Deprecated
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
	
	public void setChauffeur(String nameAttribution) {
		this.chauffeur = null;
		this.nameAttribution = nameAttribution;
		dateAttribution = LocalDate.now();
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

	public String getNameCreation() {
		return nameCreation;
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
	
	public LocalDate getDateAttribution() {
		return dateAttribution;
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
	
	public Trajet getTrajet() {
		return Trajet.get(raisonAnnulation);
	}
	
	public void setTrajet(Trajet mode) {
		if (mode == Trajet.ALLER) {
			raisonAnnulation = "ALLER";
		} else if (mode == Trajet.RETOUR) {
			raisonAnnulation = "RETOUR";
		} else {
			raisonAnnulation = "ALLER-RETOUR";
		}
	}
	
	public static final String enteteCSV = "NAMECREATION;DATECREATION;HEURECREATION;APPELANT;DATE;CHAUFFEUR;NAMEATTRIBUTION;DATEATTRIBUTION;HEUREDOMICILE;RESIDENCE;ADRESSEDEP;CPDEP;LOCALITEDEP;TYPECOURSE;TRAJET;HEURERDV;HOPITAL;ADRESSEDEST;CPDEST;LOCALITEDEST;VIDE;VIDE;HEURERETOUR;ADRESSERET;CPRET;LOCALITERET;NOTES;VIDE;VIDE;VIDE;VIDE;END";

	@Override
	public String getEnTeteCsv() {
		return enteteCSV;
	}

	@Override
	public String getRowCsv() {
		StringBuilder str = new StringBuilder();
		str.append(nameCreation);
		str.append(";");
		str.append(DateTime.saveToString(dateCreation));//date
		str.append(";");
		str.append(DateTime.toString(heureCreation));
		str.append(";");
		str.append(appelant.getId());
		str.append(";");
		str.append(DateTime.saveToString(date));//date
		str.append(";");
		if (chauffeur!=null)
			str.append(chauffeur.getId());
		str.append(";");
		str.append(nameAttribution);
		str.append(";");
		if (dateAttribution!=null)
			str.append(DateTime.saveToString(dateAttribution));//date
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
		str.append(raisonAnnulation);//Trajet
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
		
		str.append(";");
		
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
		
		str.append(";");
		
		str.append(";");
		
		str.append(";");
		
		str.append(";END");
		return str.toString();
	}
	/*
	public static String getEnTeteCsvNew() { 
		return "NAMECREATION;DATECREATION;HEURECREATION;APPELANT;DATE;CHAUFFEUR;NAMEATTRIBUTION;DATEATTRIBUTION;HEUREDOMICILE;RESIDENCE;ADRESSEDEP;CPDEP;LOCALITEDEP;MODECOURSE;TYPECOURSE;HEURERDV;HOPITAL;ADRESSEDEST;CPDEST;LOCALITEDEST;HEURERETOUR;ADRESSERET;CPRET;LOCALITERET;NOTES;END"; 
	} 
	 
	public String getRowCsvNew() { 
		StringBuilder str = new StringBuilder(); 
		str.append(nameCreation); 
		str.append(";"); 
		str.append(DateTime.saveToString(dateCreation));//date 
		str.append(";"); 
		str.append(DateTime.toString(heureCreation)); 
		str.append(";"); 
		str.append(appelant.getId()); 
		str.append(";"); 
		str.append(DateTime.saveToString(date));//date 
		str.append(";"); 
		if (chauffeur!=null) 
			str.append(chauffeur.getId()); 
		str.append(";"); 
		str.append(nameAttribution); 
		str.append(";"); 
		if (dateAttribution!=null) 
			str.append(DateTime.saveToString(dateAttribution));//date 
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
		//str.append(mode.toString()); 
		str.append(";"); 
		str.append(typeCourse.toString()); 
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
		str.append(DateTime.toString(heureRetour)); 
		str.append(";"); 
		str.append(adresseRet); 
		str.append(";"); 
		str.append(cpRet); 
		str.append(";"); 
		str.append(localiteRet); 
		str.append(";"); 
		str.append(notes); 
		str.append(";END"); 
		return str.toString(); 
	} */

	public static void valdation(Course obj) throws IllegalArgumentException {//TODO
		if (obj.appelant == null) throw new IllegalArgumentException("La course n'est pas lié à un appelant");
	}
}