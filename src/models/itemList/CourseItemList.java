package models.itemList;

import java.time.LocalDate;
import java.time.LocalTime;
import models.Course;
import util.DateTime;
import util.TypeCourse;

public class CourseItemList {
	private Long id;
	private AppelantItemList appelant;
	private ChauffeurItemList chauffeur;
	private TypeCourse type;
	private LocalDate date;
	private LocalTime heure;
	private String hopital;
	private String adresseDest;
	private String cpDest;
	private String localiteDest;
	
	public CourseItemList(Course course) {
		id = course.getId();
		appelant = new AppelantItemList(course.getAppelant());
		if (course.getChauffeur()!=null) {
			chauffeur = new ChauffeurItemList(course.getChauffeur());
		}
		type = course .getTypeCourse();
		date = course.getDate();
		heure = course.getHeureRDV();
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
	public LocalDate getDate() {
		return date;
	}
	public LocalTime getHeure() {
		return heure;
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
	public TypeCourse getType() {
		return type;
	}
	
	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		//str.append("Date: \t");
		str.append(DateTime.toDateJour(date));
		str.append(" à ");
		str.append(DateTime.toString(heure));
		str.append("\t\tpour\t\t");
		str.append(appelant.getFullName());
		str.append("\t\t(");
		str.append(type.toString());
		str.append(")\n");
		
		//str.append("Destination: \t");
		if (hopital!= null && !hopital.isEmpty()) {
			str.append(hopital);
			str.append(" : \t");
		}
		
		if (adresseDest!= null && !adresseDest.isEmpty()) {
			str.append(adresseDest);
			str.append(" à ");
		}
		
		str.append(localiteDest);
		if(cpDest!= null && !cpDest.isEmpty()) {

			str.append(" (");
			str.append(cpDest);
			str.append(")");
		}
		str.append("\n");
		if (chauffeur!= null) {
			str.append("Chauffeur: \t");
			str.append(chauffeur.getName());
			str.append(" ");
			str.append(chauffeur.getFirstname());
		}
		return str.toString();
	}
}
