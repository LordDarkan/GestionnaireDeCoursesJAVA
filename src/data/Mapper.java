package data;

import java.io.Closeable;
import java.time.LocalDate;
import java.util.List;

import models.Appelant;
import models.Chauffeur;
import models.Course;
import models.Hopital;
import models.Residence;
import models.Utilisateur;
import models.item.AppelantList;
import models.item.ChauffeurList;
import models.item.CourseList;

public abstract class Mapper implements Closeable{
	private static Mapper instance;
	
	public static void setInstance(Mapper mapper) {
		if(instance != null)
			instance.close();
		instance = mapper;
	}
	public static Mapper getInstance() {
		return instance;
	}

	public abstract void close();
	public abstract void init();
	
	public abstract List<Utilisateur> getAllUser();
	public abstract Utilisateur getUser(Long id);
	public abstract Long addOrUpdateUser(Utilisateur entity);
	public abstract void delete(Utilisateur entity);
	public abstract void importUsers(List<Utilisateur> users);
	
	public abstract List<Appelant> getAllAppelant();
	public abstract Appelant getAppelant(Long id);
	public abstract Long addOrUpdate(Appelant entity);
	public abstract void delete(Appelant entity);
	public abstract void importApplants(List<Appelant> appelants);
	
	public abstract List<Course> getAllCourse();
	public abstract Course getCourse(Long id);
	public abstract Long addOrUpdate(Course entity);
	public abstract void delete(Course entity);
	public abstract void importCourses(List<Course> courses);
	
	public abstract List<Chauffeur> getAllChauffeur();
	public abstract Chauffeur getChauffeur(Long id);
	public abstract Long addOrUpdate(Chauffeur entity);
	public abstract void delete(Chauffeur entity);
	public abstract void importChauffeurs(List<Chauffeur> chauffeurs);
	
	public abstract List<String> getAllResidence();
	public abstract Residence getResidence(String name);
	public abstract void addOrUpdate(Residence entity);
	public abstract void delete(Residence entity);
	public abstract void importResidences(List<Residence> residences);
	
	public abstract List<String> getAllHopital();
	public abstract Hopital getHopital(String name);
	public abstract void addOrUpdate(Hopital entity);
	public abstract void delete(Hopital entity);
	public abstract void importHopitals(List<Hopital> hopitals);
	
	public abstract List<CourseList> getCourse(boolean all, Long idChauffeur, boolean day, LocalDate date);
	public abstract List<ChauffeurList> getChauffeurList();
	public abstract List<AppelantList> getAppelantList();
	
}
