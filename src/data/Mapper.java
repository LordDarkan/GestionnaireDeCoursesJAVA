package data;

import java.io.Closeable;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import models.Appelant;
import models.Chauffeur;
import models.Course;
import models.Destination;
import models.Indisponibilite;
import models.Residence;
import models.Settings;
import models.Utilisateur;
import models.itemList.AppelantItemList;
import models.itemList.ChauffeurItemList;
import models.itemList.CourseItemList;
import models.itemList.PlanningChauffeur;
import util.Select;

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


	public abstract void setLogger();
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
	public abstract List<Course> getIntervalCourse(LocalDate start, LocalDate end);
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
	
	public abstract List<String> getAllDestination();
	public abstract Destination getDestination(String name);
	public abstract void addOrUpdate(Destination entity);
	public abstract void delete(Destination entity);
	public abstract void importDestination(List<Destination> hopitals);
	
	public abstract List<CourseItemList> getCourse(boolean all, Long idChauffeur, Select select, LocalDate date);
	public abstract List<ChauffeurItemList> getAllChauffeurList();
	public abstract List<ChauffeurItemList> getChauffeurList();
	public abstract List<AppelantItemList> getAppelantList();

	public abstract List<ChauffeurItemList> getChauffeurList(List<Long> list);
	public abstract List<AppelantItemList> getAppelantList(List<Long> list);
	public abstract List<CourseItemList> getCourseApplant(Long id);
	public abstract List<CourseItemList> getOldCourseApplant(Long id);
	public abstract void deleteFamille(Long id, Long id2);
	
	public abstract void deleteAll();
	
	public abstract AppelantItemList addFamille(Long id, Long id2);
	public abstract void addProche(Long id, Long id2);
	public abstract void delProche(Long id, Long id2);
	public abstract void addRestrict(Long id, Long id2);
	public abstract void delRestrict(Long id, Long id2);
	public abstract void addRestrictA(Long id, Long id2);
	public abstract void delRestrictA(Long id, Long id2);
	public abstract List<Residence> getListResidence();
	public abstract List<Destination> getListDestination();
	
	public abstract Settings getSettings();
	public abstract void setSettings(Settings settings);
	public abstract void importApplantsOld(List<Appelant> appelants);
	
	public abstract Collection<PlanningChauffeur> getPlanning(LocalDate date);
	
	public abstract void addOrUpdateIndisponibilite(Indisponibilite indisponibilite);
	public abstract void delete(Indisponibilite entity);
	public abstract List<Indisponibilite> getAllIndisponibilite();
	public abstract List<Indisponibilite> getIntervalIndisponibilite(LocalDate start, LocalDate end);
	public abstract void importIndisponibilite(List<Indisponibilite> readIndisponibilite);
	public abstract void importUtilisateur(List<Utilisateur> readUtilisateur);
	public abstract Long addNew(Appelant entity);
	public abstract boolean validIdAppelant(Long id);
	
	
	public abstract List<Integer> getListYears();
}
