package models;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;

import data.CSVRow;
import util.DateTime;
import util.Gate;
import util.Trajet;
import util.TypeCourse;

@Entity
public class Course implements CSVRow {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;

	@Column(name="nameCreation")
	private String nameCreation = "";
	@Column(name="dateCreation")
	private LocalDate dateCreation = LocalDate.now();
	@Column(name="heureCreation")
	private LocalTime  heureCreation = LocalTime.now();

	@JoinColumn(name="appelant")
	private Appelant appelant;
	@Column(name="date")
	private LocalDate date = LocalDate.now();
	@JoinColumn(name="chauffeur")
	private Chauffeur chauffeur = null;
	@Column(name="nameAttribution")
	private String nameAttribution = "";//TODO RENAME MODIF + séparé chauffeur 
	@Column(name="dateAttribution")
	private LocalDate dateAttribution = null;//RENAME MODIF + séparé chauffeur 

	@Column(name="heureDomicile")
	private LocalTime heureDomicile = LocalTime.MIDNIGHT;
	@Column(name="residence")
	private String residence;
	@Column(name="adresseDep")
	private String adresseDep;
	@Column(name="cpDep")
	private String cpDep;
	@Column(name="localiteDep")
	private String localiteDep;
	@Column(name="trajet")
	private Trajet trajet = Trajet.ALLER_RETOUR;//TODO ADD mode course
	@Column(name="typeCourse")
	private TypeCourse typeCourse = TypeCourse.AUTRE;

	@Column(name="heureRDV")
	private LocalTime heureRDV = LocalTime.MIDNIGHT;
	@Column(name="destination")
	private String destination = "";
	@Column(name="adresseDest")
	private String adresseDest = "";
	@Column(name="localiteDest")
	private String localiteDest = "";

	@Column(name="heureRetour")
	private LocalTime heureRetour = LocalTime.MIDNIGHT;
	@Column(name="adresseRet")
	private String adresseRet;
	@Column(name="cpRet")
	private String cpRet;
	@Column(name="localiteRet")
	private String localiteRet;

	@Column(name="notes")
	private String notes = "";
	

	@Column(name="str1")
	private String str1 = null;
	@Column(name="str2")
	private String str2 = null;
	@Column(name="str3")
	private String str3 = null;
	
	public Course() {
	}
	
	public Course(Appelant appelant,String nameCreation) {
		this.appelant = appelant;
		this.nameCreation =  Gate.encoding(nameCreation);
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
	
//	public Course(Course course) {
//		//TODO
//	}

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
		this.nameAttribution =  Gate.encoding(nameAttribution);
		dateAttribution = LocalDate.now();
	}
	
	public void setChauffeur(String nameAttribution) {
		this.chauffeur = null;
		this.nameAttribution =  Gate.encoding(nameAttribution);
		dateAttribution = LocalDate.now();
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		if(date!=null)
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
		this.adresseDep =  Gate.encoding(adresseDep);
	}

	public String getCpDep() {
		return cpDep;
	}

	public void setCpDep(String cpDep) {
		this.cpDep =  Gate.encoding(cpDep);
	}

	public String getLocaliteDep() {
		return localiteDep;
	}

	public void setLocaliteDep(String localiteDep) {
		this.localiteDep =  Gate.encoding(localiteDep);
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

	public String getDestination() {
		return destination;
	}

	public void setDestination(String hopital) {
		this.destination =  Gate.encoding(hopital);
	}

	public String getAdresseDest() {
		return adresseDest;
	}

	public void setAdresseDest(String adresseDest) {
		this.adresseDest =  Gate.encoding(adresseDest);
	}

	public String getLocaliteDest() {
		return localiteDest;
	}

	public void setLocaliteDest(String localiteDest) {
		this.localiteDest =  Gate.encoding(localiteDest);
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
		this.adresseRet =  Gate.encoding(adresseRet);
	}

	public String getCpRet() {
		return cpRet;
	}

	public void setCpRet(String cpRet) {
		this.cpRet =  Gate.encoding(cpRet);
	}

	public String getLocaliteRet() {
		return localiteRet;
	}

	public void setLocaliteRet(String localiteRet) {
		this.localiteRet =  Gate.encoding(localiteRet);
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes =  Gate.encoding(notes);
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
		this.residence =  Gate.encoding(residence);
	}
	
	public LocalDate getDateAttribution() {
		return dateAttribution;
	}
	
	public void setNameCreation(String nameCreation) {
		this.nameCreation =  Gate.encoding(nameCreation);
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
		this.nameAttribution =  Gate.encoding(nameAttribution);
	}

	public void setDateAttribution(LocalDate dateAttribution) {
		this.dateAttribution = dateAttribution;
	}
	
	public Trajet getTrajet() {
		return trajet;
	}
	
	public void setTrajet(Trajet mode) {
		this.trajet = mode;
	}
	
	public static final String enteteCSV = "NAMECREATION;DATECREATION;HEURECREATION;APPELANT;DATE;CHAUFFEUR;NAMEATTRIBUTION;DATEATTRIBUTION;HEUREDOMICILE;RESIDENCE;ADRESSEDEP;CPDEP;LOCALITEDEP;TYPECOURSE;TRAJET;HEURERDV;DESTINATION;ADRESSEDEST;VIDE;LOCALITEDEST;VIDE;VIDE;HEURERETOUR;ADRESSERET;CPRET;LOCALITERET;NOTES;ID(PEUT CHANGER);VIDE;VIDE;VIDE;END";

	@Override
	public String getRowIdentity() {
		return Course.class.getName();
	}
	
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
		str.append(TypeCourse.toString(typeCourse));
		str.append(";");
		str.append(trajet.toString());
		str.append(";");
		str.append(DateTime.toString(heureRDV));
		str.append(";");
		str.append(destination);
		str.append(";");
		str.append(adresseDest);
		str.append(";");
		//18
		str.append(";");
		str.append(localiteDest);
		str.append(";");
		//20
		str.append(";");
		//21
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
		str.append(id);
		str.append(";");
		
		str.append(";");
		
		str.append(";");
		
		str.append(";END");
		return str.toString();
	}

	public static void valdation(Course obj) throws IllegalArgumentException {//TODO
		if (obj.appelant == null) throw new IllegalArgumentException("La course n'est pas lié à un appelant");
	}
}