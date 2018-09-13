package data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.persistence.*;

import models.Appelant;
import models.Chauffeur;
import models.Course;
import models.Hopital;
import models.Indisponibilite;
import models.IndisponibiliteCourse;
import models.Residence;
import models.Settings;
import models.Utilisateur;
import models.itemList.AppelantItemList;
import models.itemList.ChauffeurItemList;
import models.itemList.CourseItemList;
import models.itemList.PlanningChauffeur;


public class MapperJPA extends Mapper {
	
	private static final String PERSISTENCE_UNIT_NAME = "GestionnaireDeCourses";
	private static EntityManagerFactory factory = null;
	
	public MapperJPA() {
		 if(factory == null) {
			try{
				factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
			}catch(Exception e) {
				e.printStackTrace();
				//System.exit(-1);
			}
		}
	}
	
	@Override
	public void close() {
		if(factory != null)
			factory.close();
		factory = null;
	}
	


	@Override
	public Settings getSettings() {
		EntityManager em = factory.createEntityManager();
		Settings settings = em.find(Settings.class, Settings.ID);
		if (settings == null) {
			settings = new Settings();
			settings.init();
			em.getTransaction().begin();
			em.persist(settings);
			em.getTransaction().commit();
		}
		em.close();
		return settings;
	}
	
	@Override
	public void init() {
		EntityManager em = factory.createEntityManager();
		Settings settings = em.find(Settings.class, Settings.ID);
		if (settings == null) {
			settings = new Settings();
			settings.init();
			em.getTransaction().begin();
			em.persist(settings);
			em.getTransaction().commit();
		}
		int nbr = em.createQuery("SELECT t FROM Utilisateur t",Utilisateur.class).getResultList().size();
		if (nbr==0) {
			em.getTransaction().begin();
			Utilisateur temp = new Utilisateur();
			temp.setName("Temporaire");
			temp.setFirstname(System.getProperty("user.name"));
			temp.setAdmin(true);
			em.persist(temp);
			em.getTransaction().commit();
		}
		em.close();
	}
	
	@Override
	public List<Utilisateur> getAllUser() {
		List<Utilisateur> result = new ArrayList<>();
		try{
			EntityManager em = factory.createEntityManager();
			TypedQuery<Utilisateur> q = em.createQuery("SELECT t FROM Utilisateur t ORDER BY t.name, t.firstname",Utilisateur.class);
			result = q.getResultList();
			em.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public Utilisateur getUser(Long id) {
		Utilisateur user = null;
		try{
			EntityManager em = factory.createEntityManager();
			user = em.find(Utilisateur.class, id);
			em.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}
	
	
	@Override
	public Long addOrUpdateUser(Utilisateur entity) {
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		if (entity.getId() == null) {
			em.persist(entity);
		} else {
			em.merge(entity);
		}
		em.getTransaction().commit(); 
        em.close();
        return entity.getId();
	}

	@Override
	public void delete(Utilisateur entity) {
		EntityManager em = factory.createEntityManager();
		entity = em.find(Utilisateur.class, entity.getId());
		em.getTransaction().begin();
		em.remove(entity);
		em.getTransaction().commit();
		em.close();
	}
	
	@Override
	public List<Appelant> getAllAppelant() {
		List<Appelant> result = new LinkedList<Appelant>();
		try{
			EntityManager em = factory.createEntityManager();
			TypedQuery<Appelant> q = em.createQuery("SELECT t FROM Appelant t ORDER BY t.id",Appelant.class);
			result = new LinkedList<Appelant>(q.getResultList());
			em.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public Appelant getAppelant(Long id) {
		Appelant result = null;
		try{
			EntityManager em = factory.createEntityManager();
			result = em.find(Appelant.class, id);
			em.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public Long addOrUpdate(Appelant entity) {
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		if (entity.getId() == null) {
			em.persist(entity);
		} else {
			Appelant oldEntity = em.find(Appelant.class, entity.getId());
			entity.setFamilleStr(oldEntity.getFamilleStr());
			entity.setAffiniteStr(oldEntity.getAffiniteStr());
			entity.setRestrictionStr(oldEntity.getRestrictionStr());
			em.merge(entity);
			Appelant fam;
			for (Long famille : entity.getFamille()) {
				fam = em.find(Appelant.class, famille);
				if (fam!=null)
					fam.setCotisation(entity.getCotisation());
			}
		}
		em.getTransaction().commit(); 
        em.close();
        return entity.getId();
	}

	@Override
	public void delete(Appelant entity) {
		EntityManager em = factory.createEntityManager();
		try {
			entity = em.find(Appelant.class, entity.getId());
			em.getTransaction().begin();
			em.remove(entity);
			em.getTransaction().commit();
		} catch (Exception e) {
			
		} finally {
			em.close();
		}
	}
	
	@Override
	public List<Course> getAllCourse() {
		List<Course> result = new LinkedList<Course>();
		try{
			EntityManager em = factory.createEntityManager();
			TypedQuery<Course> q = em.createQuery("SELECT t FROM Course t ORDER BY t.id",Course.class);
			result = new LinkedList<Course>(q.getResultList());
			em.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public Course getCourse(Long id) {
		Course result = null;
		try{
			EntityManager em = factory.createEntityManager();
			result = em.find(Course.class, id);
			em.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public Long addOrUpdate(Course entity) {
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		if (entity.getId() == null) {
			em.persist(entity);
		} else {
			em.merge(entity);
		}
		em.getTransaction().commit(); 
        em.close();
        return entity.getId();
	}

	@Override
	public void delete(Course entity) {
		EntityManager em = factory.createEntityManager();
		entity = em.find(Course.class, entity.getId());
		em.getTransaction().begin();
		em.remove(entity);
		em.getTransaction().commit();
		em.close();
	}
	
	@Override
	public List<Chauffeur> getAllChauffeur() {
		List<Chauffeur> result = new LinkedList<Chauffeur>();
		try{
			EntityManager em = factory.createEntityManager();
			TypedQuery<Chauffeur> q = em.createQuery("SELECT t FROM Chauffeur t ORDER BY t.id",Chauffeur.class);
			result = new LinkedList<Chauffeur>(q.getResultList());
			em.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	@Override
	public Chauffeur getChauffeur(Long id) {
		Chauffeur result = null;
		try{
			EntityManager em = factory.createEntityManager();
			result = em.find(Chauffeur.class, id);
			em.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	@Override
	public Long addOrUpdate(Chauffeur entity) {
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		if (entity.getId() == null) {
			em.persist(entity);
		} else {
			em.merge(entity);
		}
		em.getTransaction().commit(); 
        em.close();
        return entity.getId();
	}

	@Override
	public void delete(Chauffeur entity) {
		EntityManager em = factory.createEntityManager();
		try {
			entity = em.find(Chauffeur.class, entity.getId());
			em.getTransaction().begin();
			em.remove(entity);
			em.getTransaction().commit();
		}catch (Exception e) {
			/*TypedQuery<Course> q = em.createQuery("SELECT t FROM Course t WHERE ",Course.class);
			for (Course course : q.getResultList()) {
				
			}*/
		} finally {
			em.close();
		}
	}
	
	@Override
	public List<String> getAllResidence() {
		List<String> result = new ArrayList<>();
		try{
			EntityManager em = factory.createEntityManager();
			TypedQuery<String> q = em.createQuery("SELECT t.name FROM Residence t ORDER BY t.name",String.class);
			result = q.getResultList();
			em.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public Residence getResidence(String name) {
		Residence result = null;
		try{
			EntityManager em = factory.createEntityManager();
			result = em.find(Residence.class, name);
			em.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		if (result == null)
			result = new Residence();
		return result;
	}

	@Override
	public void addOrUpdate(Residence entity) {
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		em.merge(entity);
		em.getTransaction().commit(); 
        em.close(); 
	}

	@Override
	public void delete(Residence entity) {
		EntityManager em = factory.createEntityManager();
		entity = em.find(Residence.class, entity.getName());
		em.getTransaction().begin();
		em.remove(entity);
		em.getTransaction().commit();
		em.close();
	}

	@Override
	public List<String> getAllHopital() {
		List<String> result = new ArrayList<>();
		try{
			EntityManager em = factory.createEntityManager();
			TypedQuery<String> q = em.createQuery("SELECT t.name FROM Hopital t ORDER BY t.name",String.class);
			result = q.getResultList();
			em.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public Hopital getHopital(String name) {
		Hopital result = null;
		try{
			EntityManager em = factory.createEntityManager();
			result = em.find(Hopital.class, name);
			em.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		if (result == null)
			result = new Hopital();
		return result;
	}

	@Override
	public void addOrUpdate(Hopital entity) {
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		em.merge(entity);
		em.getTransaction().commit(); 
        em.close(); 
	}

	@Override
	public void delete(Hopital entity) {
		EntityManager em = factory.createEntityManager();
		entity = em.find(Hopital.class, entity.getName());
		em.getTransaction().begin();
		em.remove(entity);
		em.getTransaction().commit();
		em.close();
	}
	
	@Override
	public List<CourseItemList> getCourse(boolean all, Long idChauffeur, boolean day, LocalDate date) {
		StringBuilder query = new StringBuilder("SELECT t FROM Course t");
		query.append(" WHERE t.date");
		if (day) {
			query.append("=:locdate");
		} else {
			query.append(">=CURRENT_DATE");
		}
		if (!all) {
			query.append(" AND ");
			if (idChauffeur == null) {
				query.append("t.chauffeur IS NULL");
			} else {
				query.append("((t.chauffeur IS NOT NULL AND t.chauffeur.id=:idchauf) OR (t.chauffeurSec IS NOT NULL AND t.chauffeurSec.id=:idchauf2))");
			}
		}
		query.append(" ORDER BY t.date, t.heureDomicile");
		List<CourseItemList> result = new LinkedList<CourseItemList>();
		try{
			EntityManager em = factory.createEntityManager();
			TypedQuery<Course> q = em.createQuery(query.toString(),Course.class);
			if (day) {
				q.setParameter("locdate", date);
			}
			if (!all && idChauffeur != null) {
				q.setParameter("idchauf", idChauffeur);
				q.setParameter("idchauf2", idChauffeur);
			}
			;
			for (Course course : q.getResultList()) {
				result.add(new CourseItemList(course));
			}
			em.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public List<ChauffeurItemList> getChauffeurList() {
		List<ChauffeurItemList> result = new LinkedList<ChauffeurItemList>();
		try{
			EntityManager em = factory.createEntityManager();
			TypedQuery<Chauffeur> q = em.createQuery("SELECT t FROM Chauffeur t ORDER BY t.id",Chauffeur.class);
			for (Chauffeur tuple : q.getResultList()) {
				result.add(new ChauffeurItemList(tuple));
			}
			em.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public List<AppelantItemList> getAppelantList() {
		List<AppelantItemList> result = new LinkedList<AppelantItemList>();
		try{
			EntityManager em = factory.createEntityManager();
			TypedQuery<Appelant> q = em.createQuery("SELECT t FROM Appelant t ORDER BY t.id",Appelant.class);
			for (Appelant tuple : q.getResultList()) {
				result.add(new AppelantItemList(tuple));
			}
			em.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public void deleteAll() {
		try{
			EntityManager em = factory.createEntityManager();
			em.getTransaction().begin();
			em.createQuery("DELETE FROM Course").executeUpdate();
			em.createQuery("DELETE FROM Appelant").executeUpdate();
			em.createQuery("DELETE FROM Chauffeur").executeUpdate();
			em.createQuery("DELETE FROM Residence").executeUpdate();
			em.createQuery("DELETE FROM Hopital").executeUpdate();
			em.getTransaction().commit(); 
	        em.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void importSave(List<Course> courses, List<Appelant> appelants, List<Chauffeur> chauffeurs, List<Residence> residences, List<Hopital> hopitals) {
		try{
			EntityManager em = factory.createEntityManager();
			em.getTransaction().begin();
			em.createQuery("DELETE FROM Course").executeUpdate();
			em.createQuery("DELETE FROM Appelant").executeUpdate();
			em.createQuery("DELETE FROM Chauffeur").executeUpdate();
			em.createQuery("DELETE FROM Residence").executeUpdate();
			em.createQuery("DELETE FROM Hopital").executeUpdate();
			/** /
			em.getTransaction().commit();
			em.getTransaction().begin();
			/**/
			for (Hopital entity : hopitals) {
				em.persist(entity);
	            em.flush();
	            em.clear();
			}
			for (Residence entity : residences) {
				em.persist(entity);
	            em.flush();
	            em.clear();
			}
			for (Chauffeur entity : chauffeurs) {
				em.persist(entity);
	            em.flush();
	            em.clear();
			}
			for (Appelant entity : appelants) {
				em.persist(entity);
	            em.flush();
	            em.clear();
			}
			for (Course entity : courses) {
				em.persist(entity);
	            em.flush();
	            em.clear();
			}
			
			em.getTransaction().commit(); 
	        em.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void deleteFamille(Long id, Long id2) {
		EntityManager em = factory.createEntityManager();
		
		Appelant app = em.find(Appelant.class,id);
		Appelant app2 = em.find(Appelant.class,id2);
		
		em.getTransaction().begin();
		app.removeFamille(id2);
		app2.removeFamille(id);
		em.getTransaction().commit();
		
		em.close();
	}

	@Override
	public AppelantItemList addFamille(Long id, Long id2) {
		EntityManager em = factory.createEntityManager();
		AppelantItemList retour = null;
		Appelant app2 = em.find(Appelant.class,id2);
		if (app2!=null) {
			Appelant app = em.find(Appelant.class,id);
			
			em.getTransaction().begin();
			app.addFamille(id2);
			app2.addFamille(id);
			app.setCotisation(app2.getCotisation());
			app2.setCotisation(app.getCotisation());
			em.getTransaction().commit();
			
			em.close();
			retour = new AppelantItemList(app2);
		}
		return retour;
	}

	@Override
	public void addProche(Long id, Long id2) {
		EntityManager em = factory.createEntityManager();
		
		Appelant app = em.find(Appelant.class,id);
		
		em.getTransaction().begin();
		app.addAffinite(id2);
		em.getTransaction().commit();
		
		em.close();
	}

	@Override
	public void delProche(Long id, Long id2) {
		EntityManager em = factory.createEntityManager();
		
		Appelant app = em.find(Appelant.class,id);
		
		em.getTransaction().begin();
		app.removeAffinite(id2);
		em.getTransaction().commit();
		
		em.close();
	}

	@Override
	public void addRestrict(Long id, Long id2) {
		EntityManager em = factory.createEntityManager();
		
		Appelant app = em.find(Appelant.class,id);
		
		em.getTransaction().begin();
		app.addRestriction(id2);
		em.getTransaction().commit();
		
		em.close();
	}

	@Override
	public void delRestrict(Long id, Long id2) {
		EntityManager em = factory.createEntityManager();
		
		Appelant app = em.find(Appelant.class,id);
		
		em.getTransaction().begin();
		app.removeRestriction(id2);
		em.getTransaction().commit();
		
		em.close();
	}

	@Override
	public List<AppelantItemList> getAppelantList(List<Long> ids) {
		List<AppelantItemList> result = new LinkedList<AppelantItemList>();
		try{
			EntityManager em = factory.createEntityManager();
			TypedQuery<Appelant> q = em.createQuery("SELECT t FROM Appelant t ORDER BY t.id",Appelant.class);
			for (Appelant tuple : q.getResultList()) {
				if (ids.contains(tuple.getId())) {
					result.add(new AppelantItemList(tuple));
				}
			}
			em.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public List<ChauffeurItemList> getChauffeurList(List<Long> ids) {
		List<ChauffeurItemList> result = new LinkedList<ChauffeurItemList>();
		try{
			EntityManager em = factory.createEntityManager();
			TypedQuery<Chauffeur> q = em.createQuery("SELECT t FROM Chauffeur t ORDER BY t.id",Chauffeur.class);
			for (Chauffeur tuple : q.getResultList()) {
				if (ids.contains(tuple.getId())) {
					result.add(new ChauffeurItemList(tuple));
				}
			}
			em.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public List<CourseItemList> getCourseApplant(Long id) {
		List<CourseItemList> result = new LinkedList<CourseItemList>();
		try{
			EntityManager em = factory.createEntityManager();
			TypedQuery<Course> q = em.createQuery(
					"SELECT c FROM Course c WHERE  c.appelant.id=:arg1 ORDER BY c.date, c.heureDomicile",Course.class);
			q.setParameter("arg1", id);
			for (Course tuple : q.getResultList()) {
				result.add(new CourseItemList(tuple));
			}
			em.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public List<Residence> getListResidence() {
		List<Residence> result = new ArrayList<>();
		try{
			EntityManager em = factory.createEntityManager();
			TypedQuery<Residence> q = em.createQuery("SELECT t FROM Residence t ORDER BY t.name",Residence.class);
			result = q.getResultList();
			em.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public List<Hopital> getListHopital() {
		List<Hopital> result = new ArrayList<>();
		try{
			EntityManager em = factory.createEntityManager();
			TypedQuery<Hopital> q = em.createQuery("SELECT t FROM Hopital t ORDER BY t.name",Hopital.class);
			result = q.getResultList();
			em.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	@Override
	public void importUsers(List<Utilisateur> users) {
		try{
			EntityManager em = factory.createEntityManager();
			em.getTransaction().begin();
			//em.createQuery("DELETE FROM Utilisateur").executeUpdate();
			
			for (Utilisateur user : users) {
				em.persist(user);
	            em.flush();
	            em.clear();
			}
			
			em.getTransaction().commit(); 
	        em.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void importApplantsOld(List<Appelant> appelants) {
		try{
			EntityManager em = factory.createEntityManager();
			em.getTransaction().begin();
			em.createQuery("DELETE FROM Course").executeUpdate();
			em.createQuery("DELETE FROM Appelant").executeUpdate();
			
			for (Appelant appelant : appelants) {
				em.persist(appelant);
	            em.flush();
	            em.clear();
			}
			
			em.getTransaction().commit(); 
	        em.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void importApplants(List<Appelant> appelants) {
		try{
			EntityManager em = factory.createEntityManager();
			em.getTransaction().begin();
			//em.createQuery("DELETE FROM Course").executeUpdate();
			//em.createQuery("DELETE FROM Appelant").executeUpdate();
			
			for (Appelant appelant : appelants) {
				em.persist(appelant);
	            em.flush();
	            em.clear();
			}
			
			em.getTransaction().commit(); 
	        em.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}


	@Override
	public void importChauffeurs(List<Chauffeur> chauffeurs) {
		try{
			EntityManager em = factory.createEntityManager();
			em.getTransaction().begin();
			//em.createQuery("DELETE FROM Chauffeur").executeUpdate();
			
			for (Chauffeur chauffeur : chauffeurs) {
				em.persist(chauffeur);
	            em.flush();
	            em.clear();
			}
			
			em.getTransaction().commit(); 
	        em.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void importCourses(List<Course> courses) {
		try{
			EntityManager em = factory.createEntityManager();
			em.getTransaction().begin();
			//em.createQuery("DELETE FROM Course").executeUpdate();
			
			for (Course course : courses) {
				em.persist(course);
	            em.flush();
	            em.clear();
			}
			
			em.getTransaction().commit(); 
	        em.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void importResidences(List<Residence> residences) {
		try{
			EntityManager em = factory.createEntityManager();
			em.getTransaction().begin();
			//em.createQuery("DELETE FROM Course").executeUpdate();
			
			for (Residence entity : residences) {
				em.persist(entity);
	            em.flush();
	            em.clear();
			}
			
			em.getTransaction().commit(); 
	        em.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void importHopitaux(List<Hopital> hopitals) {
		try{
			EntityManager em = factory.createEntityManager();
			em.getTransaction().begin();
			//em.createQuery("DELETE FROM Course").executeUpdate();
			
			for (Hopital entity : hopitals) {
				em.persist(entity);
	            em.flush();
	            em.clear();
			}
			
			em.getTransaction().commit(); 
	        em.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public Collection<PlanningChauffeur> getPlanning(LocalDate date) {
		Map<Long,PlanningChauffeur> result = new HashMap<Long,PlanningChauffeur>();
		try{
			EntityManager em = factory.createEntityManager();
			TypedQuery<Chauffeur> q1 = em.createQuery("SELECT t FROM Chauffeur t ORDER BY t.name", Chauffeur.class);
			for (Chauffeur tuple : q1.getResultList()) {
				result.put(tuple.getId(),new PlanningChauffeur(tuple.getFullName()));
			}
			
			TypedQuery<Course> q= em.createQuery("SELECT t FROM Course t WHERE t.annulation = FALSE AND t.date=:locdate AND (t.chauffeur IS NOT NULL OR t.chauffeurSec IS NOT NULL) ORDER BY t.date, t.heureDomicile",Course.class);
			q.setParameter("locdate", date);
			
			Chauffeur c,cs;
			Indisponibilite i;
			
			for (Course course : q.getResultList()) {
				c = course.getChauffeur();
				cs = course.getChauffeurSec();
				
				if(course.isAttente()) {
					if(c!=null) {
						i=new IndisponibiliteCourse(course.getAppelant().getFullName());
						i.setDescription(course.getTypeCourse()+": "+course.getAdresseDest());
						i.setDateStart(date);
						i.setDateEnd(date);
						i.setHeureStart(course.getHeureDomicile());
						i.setHeureEnd(course.getHeureRetour());
						result.get(c.getId()).add(i);
					}
				} else {
					if(c!=null) {
						i=new IndisponibiliteCourse(course.getAppelant().getFullName());
						i.setDescription(course.getTypeCourse()+": "+course.getAdresseDest());
						i.setDateStart(date);
						i.setDateEnd(date);
						i.setHeureStart(course.getHeureDomicile());
						i.setHeureEnd(course.getHeureRDV().plusMinutes(30));
						result.get(c.getId()).add(i);
					}
					
					if(cs!=null) {
						i=new IndisponibiliteCourse(course.getAppelant().getFullName());
						i.setDescription(course.getTypeCourse()+": "+course.getAdresseDest());
						i.setDateStart(date);
						i.setDateEnd(date);
						i.setHeureStart(course.getHeureRetour().minusHours(1));
						i.setHeureEnd(course.getHeureRetour());
						result.get(cs.getId()).add(i);
					}
				}
			}
			
			
			
			
			em.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return result.values();
	}
}
