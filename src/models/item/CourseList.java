package models.list;

import java.time.LocalDate;
import java.time.LocalTime;
import models.Course;

public class CourseList {
	private Long id;
	private AppelantList appelant;
	private ChauffeurList chauffeur;
	private LocalDate date;
	private LocalTime heureRDV;
	private String hopital;
	private String adresseDest;
	private String cpDest;
	private String localiteDest;
	
	public CourseList(Course course) {
		id = course.getId();
		appelant = new AppelantList(course.getAppelant());
		if(course.getChauffeur() != null) {
			chauffeur = new ChauffeurList(course.getChauffeur());
		}
		date = course.getDate();
		heureRDV = course.getHeureRDV();
		hopital = course.getHopital();
		adresseDest = course.getAdresseDest();
		cpDest = course.getCpDest();
		localiteDest = course.getLocaliteDest();
	}
	
	public Long getId() {
		return id;
	}
	public String getAppelant() {
		return appelant.getFullName();
	}
	public String getChauffeurFN() {
		return chauffeur.getFullName();
	}
	public LocalDate getDate() {
		return date;
	}
	public LocalTime getHeureRDV() {
		return heureRDV;
	}
	public String getHopital() {
		return hopital;
	}
	public String getAdresseDest() {
		return adresseDest;
	}
	public String getCpDest() {
		return cpDest;
	}
	public String getLocaliteDest() {
		return localiteDest;
	}
}
