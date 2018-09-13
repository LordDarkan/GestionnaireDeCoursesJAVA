package models;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Indisponibilite {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private Long idChauffeur;
	private LocalDate dateStart = LocalDate.now();
	private LocalTime heureStart = LocalTime.MIDNIGHT;
	private LocalDate dateEnd = LocalDate.now();
	private LocalTime heureEnd = LocalTime.MIDNIGHT;
	private String description = "";
	
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
	
	public boolean isCourse() {
		return false;
	}
	public String getTitre() {
		return "Indisponibilité";
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
}
