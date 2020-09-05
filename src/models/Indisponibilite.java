package models;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import data.CSVRow;
import util.DateTime;
import util.Gate;
import util.Trajet;
import util.TypeIndisponibilite;

@Entity
public class Indisponibilite implements CSVRow, Comparable<Indisponibilite> {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="id")
	private Long id;
	@Column(name="idChauffeur")
	private Long idChauffeur;
	@Column(name="dateStart")
	private LocalDate dateStart = LocalDate.now();
	@Column(name="heureStart")
	private LocalTime heureStart = LocalTime.MIDNIGHT;
	@Column(name="dateEnd")
	private LocalDate dateEnd = LocalDate.now();
	@Column(name="heureEnd")
	private LocalTime heureEnd = LocalTime.MIDNIGHT;
	@Column(name="description")
	private String description = "";
	@Column(name="type")
	private TypeIndisponibilite type = TypeIndisponibilite.INDISPO;
	
	@Column(name="str1")
	private String str1 = null;
	@Column(name="str2")
	private String str2 = null;
	@Column(name="str3")
	private String str3 = null;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getIdChauffeur() {
		return idChauffeur;
	}
	public void setIdChauffeur(Long idChauffeur) {
		this.idChauffeur = idChauffeur;
	}
	public LocalDate getDateStart() {
		return dateStart;
	}
	public void setDateStart(LocalDate dateStart) {
		this.dateStart = dateStart;
	}
	public LocalTime getHeureStart() {
		return heureStart;
	}
	public void setHeureStart(LocalTime heureStart) {
		this.heureStart = heureStart;
	}
	public LocalDate getDateEnd() {
		return dateEnd;
	}
	public void setDateEnd(LocalDate dateEnd) {
		this.dateEnd = dateEnd;
	}
	public LocalTime getHeureEnd() {
		return heureEnd;
	}
	public void setHeureEnd(LocalTime heureEnd) {
		this.heureEnd = heureEnd;
	}
	public void setType(TypeIndisponibilite type) {
		this.type = type;
	}
	public TypeIndisponibilite getType() {
		return type;
	}
	public String getTitre() {
		String str = "Indisponibilité";
		if (type == TypeIndisponibilite.PASDERANGER) {
			str = "Ne pas déranger";
		}
		return str;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description =  Gate.encoding(description);
	}
	
	public Trajet getModeCourse() {
		return null;
	}
	public void setModeCourse(Trajet mode) {
	}
	@Override
	public int compareTo(Indisponibilite arg0) {
		int resultat = dateStart.compareTo(arg0.dateStart);
		
		if (resultat == 0) resultat = heureStart.compareTo(arg0.heureStart);
		
		return resultat;
	}
	
	@Override
	public String getRowIdentity() {
		return Indisponibilite.class.getName();
	}

	@Override
	public String getRowCsv() {
		StringBuilder str = new StringBuilder();
		str.append(idChauffeur);
		str.append(";");
		str.append(DateTime.saveToString(dateStart));//date
		str.append(";");
		str.append(DateTime.toString(heureStart));
		str.append(";");
		str.append(DateTime.saveToString(dateEnd));//date
		str.append(";");
		str.append(DateTime.toString(heureEnd));
		str.append(";");
		str.append(description);
		str.append(";");
		str.append(type.toString());
		str.append(";END");
		return str.toString();
	}

	@Override
	public String getEnTeteCsv() {
		return enteteCSV;
	}
	
	public static final String enteteCSV = "ID_CHAUFFEUR;DATE_START;HEURE_START;DATE_END;HEURE_END;DESCRIPTION;TYPE;END";
}
