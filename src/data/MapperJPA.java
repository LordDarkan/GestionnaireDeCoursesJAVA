package data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import models.Appelant;
import models.Chauffeur;
import models.Course;
import models.Destination;
import models.Indisponibilite;
import models.IndisponibiliteCourse;
import models.Residence;
import models.Settings;
import models.Utilisateur;
import models.itemList.AppelantItemList;
import models.itemList.ChauffeurItemList;
import models.itemList.CourseItemList;
import models.itemList.PlanningChauffeur;
import util.LoggerManager;
import util.Select;
import util.TypeIndisponibilite;
import util.UserManager;

public class MapperJPA extends Mapper {
	private static Logger LOG;
	private static final String PERSISTENCE_UNIT_NAME = "GestionnaireDeCourses";
	private static EntityManagerFactory factory = null;

	public MapperJPA() {
		if (factory == null) {
			try {
				factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
			} catch (Exception e) {
				e.printStackTrace();
				// System.exit(-1);
			}
		}
	}

	@Override
	public void close() {
		if (factory != null)
			factory.close();
		factory = null;
	}
	
	@Override
	public void setLogger() {
		LOG = LoggerManager.getLogger();
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
		int nbr = em.createQuery("SELECT t FROM Utilisateur t", Utilisateur.class).getResultList().size();
		if (nbr == 0) {
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
		try {
			EntityManager em = factory.createEntityManager();
			TypedQuery<Utilisateur> q = em.createQuery("SELECT t FROM Utilisateur t ORDER BY t.name, t.firstname",
					Utilisateur.class);
			result = q.getResultList();
			em.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public Utilisateur getUser(Long id) {
		Utilisateur user = null;
		try {
			EntityManager em = factory.createEntityManager();
			user = em.find(Utilisateur.class, id);
			em.close();
		} catch (Exception e) {
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
		try {
			EntityManager em = factory.createEntityManager();
			TypedQuery<Appelant> q = em.createQuery("SELECT t FROM Appelant t ORDER BY t.id", Appelant.class);
			result = new LinkedList<Appelant>(q.getResultList());
			em.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public Appelant getAppelant(Long id) {
		Appelant result = null;
		try {
			EntityManager em = factory.createEntityManager();
			result = em.find(Appelant.class, id);
			em.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	

	
	@Override
	public Long addNew(Appelant entity) {
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		em.persist(entity);
		em.getTransaction().commit();
		em.close();
		LOG.info("AJOUT APPLANT '"+entity.getId()+"' par "+UserManager.getFullName());
		return entity.getId();
	}

	@Override
	public Long addOrUpdate(Appelant entity) {
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		boolean add = entity.getId() == null;
		if (add) {
			em.persist(entity);
		} else {
			Appelant oldEntity = em.find(Appelant.class, entity.getId());
			entity.setFamilleStr(oldEntity.getFamilleStr());
			entity.setAffiniteStr(oldEntity.getAffiniteStr());
			entity.setRestrictionStr(oldEntity.getRestrictionStr());
			entity.setRestrictionAStr(oldEntity.getRestrictionAStr());
			em.merge(entity);
			Appelant fam;
			for (Long famille : entity.getFamille()) {
				fam = em.find(Appelant.class, famille);
				if (fam != null)
					fam.setCotisation(entity.getCotisation());
			}
		}
		em.getTransaction().commit();
		em.close();
		
		if (add) {
			em.persist(entity);
			LOG.info("AJOUT APPLANT "+entity.getId()+" par "+UserManager.getFullName());
		} else {
			LOG.info("MODIFICATION APPLANT "+entity.getId()+" par "+UserManager.getFullName());
		}
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
		try {
			EntityManager em = factory.createEntityManager();
			TypedQuery<Course> q = em.createQuery("SELECT t FROM Course t ORDER BY t.id", Course.class);
			result = new LinkedList<Course>(q.getResultList());
			em.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	


	@Override
	public List<Course> getIntervalCourse(LocalDate start, LocalDate end) {
		List<Course> result = new LinkedList<Course>();
		try {
			EntityManager em = factory.createEntityManager();
			TypedQuery<Course> q = em.createQuery("SELECT t FROM Course t WHERE t.date>=:start AND t.date<=:end ORDER BY t.id", Course.class);
			q.setParameter("start", start);
			q.setParameter("end", end);
			result = new LinkedList<Course>(q.getResultList());
			em.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public Course getCourse(Long id) {
		Course result = null;
		try {
			EntityManager em = factory.createEntityManager();
			result = em.find(Course.class, id);
			em.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public Long addOrUpdate(Course entity) {
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		boolean add = entity.getId() == null;
		if (add) {
			em.persist(entity);
		} else {
			em.merge(entity);
		}
		em.getTransaction().commit();
		em.close();
		
		if (add) {
			LOG.info("AJOUT COURSE "+entity.getId()+" par "+UserManager.getFullName());
		} else {
			LOG.info("MODIFICATION COURSE "+entity.getId()+" par "+UserManager.getFullName());
		}
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
		try {
			EntityManager em = factory.createEntityManager();
			TypedQuery<Chauffeur> q = em.createQuery("SELECT t FROM Chauffeur t ORDER BY t.name", Chauffeur.class);
			result = new LinkedList<Chauffeur>(q.getResultList());
			em.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public Chauffeur getChauffeur(Long id) {
		Chauffeur result = null;
		try {
			EntityManager em = factory.createEntityManager();
			result = em.find(Chauffeur.class, id);
			em.close();
		} catch (Exception e) {
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
	public void delete(Chauffeur entity) {//TODO
		EntityManager em = factory.createEntityManager();
		try {
			entity = em.find(Chauffeur.class, entity.getId());
			em.getTransaction().begin();
			em.merge(entity);
			TypedQuery<Course> q = em.createQuery("SELECT t FROM Course t WHERE t.chauffeur IS NOT NULL AND t.chauffeur.id=:idchauf", Course.class);
			q.setParameter("idchauf", entity.getId());
			Chauffeur none = null;
			int i=0;
			for (Course course : q.getResultList()) {
				course.setChauffeur(none);
				em.merge(course);
				i++;
			}
			em.remove(entity);
			em.getTransaction().commit();
			LOG.info("Chauffeur: "+entity.getFullName()+" supprimé et était dans "+i+" courses");
		} catch (Exception e) {
			LOG.log(Level.SEVERE, "ERR del chauffeur " +entity.getFullName()+" ("+ entity.getId()+")", e);
		} finally {
			em.close();
		}
	}

	@Override
	public List<String> getAllResidence() {
		List<String> result = new ArrayList<>();
		try {
			EntityManager em = factory.createEntityManager();
			TypedQuery<String> q = em.createQuery("SELECT t.name FROM Residence t ORDER BY t.name", String.class);
			result = q.getResultList();
			em.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public Residence getResidence(String name) {
		Residence result = null;
		try {
			EntityManager em = factory.createEntityManager();
			result = em.find(Residence.class, name);
			em.close();
		} catch (Exception e) {
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
	public List<String> getAllDestination() {
		List<String> result = new ArrayList<>();
		try {
			EntityManager em = factory.createEntityManager();
			TypedQuery<String> q = em.createQuery("SELECT t.name FROM Destination t ORDER BY t.name", String.class);
			result = q.getResultList();
			em.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public Destination getDestination(String name) {
		Destination result = null;
		try {
			EntityManager em = factory.createEntityManager();
			result = em.find(Destination.class, name);
			em.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (result == null)
			result = new Destination();
		return result;
	}

	@Override
	public void addOrUpdate(Destination entity) {
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		em.merge(entity);
		em.getTransaction().commit();
		em.close();
	}

	@Override
	public void delete(Destination entity) {
		EntityManager em = factory.createEntityManager();
		entity = em.find(Destination.class, entity.getName());
		em.getTransaction().begin();
		em.remove(entity);
		em.getTransaction().commit();
		em.close();
	}

	@Override//TODO sup chauf sec
	public List<CourseItemList> getCourse(boolean all, Long idChauffeur, Select select, LocalDate date) {
		StringBuilder query = new StringBuilder("SELECT t FROM Course t");
		query.append(" WHERE t.date");
		switch (select) {
		case DAY:
			query.append("=:locdate");
			break;
		case PASSE:
			query.append("<CURRENT_DATE");
			break;
		case FUTUR:
		default:
			query.append(">=CURRENT_DATE");
			break;
		}
		/*if (day) {
			query.append("=:locdate");
		} else {
			query.append(">=CURRENT_DATE");
		}*/
		if (!all) {
			query.append(" AND ");
			if (idChauffeur == null) {
				query.append("t.chauffeur IS NULL");
			} else {
				query.append(
						"(t.chauffeur IS NOT NULL AND t.chauffeur.id=:idchauf)");
			}
		}
		if (select == Select.PASSE) {
			query.append(" ORDER BY t.date DESC, t.heureRDV DESC");
		} else {
			query.append(" ORDER BY t.date, t.heureRDV");
		}
		List<CourseItemList> result = new LinkedList<CourseItemList>();
		try {
			EntityManager em = factory.createEntityManager();
			TypedQuery<Course> q = em.createQuery(query.toString(), Course.class);
			if (select == Select.DAY) {
				q.setParameter("locdate", date);
			}
			if (!all && idChauffeur != null) {
				q.setParameter("idchauf", idChauffeur);
			}
			;
			for (Course course : q.getResultList()) {
				result.add(new CourseItemList(course));
			}
			em.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	@Override
	public List<ChauffeurItemList> getAllChauffeurList() {
		List<ChauffeurItemList> result = new LinkedList<ChauffeurItemList>();
		try {
			EntityManager em = factory.createEntityManager();
			TypedQuery<Chauffeur> q = em.createQuery("SELECT t FROM Chauffeur t ORDER BY t.name, t.firstname", Chauffeur.class);//LOWER(t.name)
			for (Chauffeur tuple : q.getResultList()) {
				result.add(new ChauffeurItemList(tuple));
			}
			em.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	@Override
	public List<ChauffeurItemList> getChauffeurList() {
		List<ChauffeurItemList> result = new LinkedList<ChauffeurItemList>();
		try {
			EntityManager em = factory.createEntityManager();
			TypedQuery<Chauffeur> q = em.createQuery("SELECT t FROM Chauffeur t WHERE t.display = TRUE ORDER BY t.name, t.firstname", Chauffeur.class);//LOWER(t.name)
			for (Chauffeur tuple : q.getResultList()) {
				result.add(new ChauffeurItemList(tuple));
			}
			em.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public List<AppelantItemList> getAppelantList() {
		List<AppelantItemList> result = new LinkedList<AppelantItemList>();
		try {
			EntityManager em = factory.createEntityManager();
			TypedQuery<Appelant> q = em.createQuery("SELECT t FROM Appelant t ORDER BY t.id", Appelant.class);
			for (Appelant tuple : q.getResultList()) {
				result.add(new AppelantItemList(tuple));
			}
			em.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public void deleteAll() {
		try {
			EntityManager em = factory.createEntityManager();
			em.getTransaction().begin();
			 em.createQuery("DELETE FROM Utilisateur").executeUpdate();
			em.createQuery("DELETE FROM Course").executeUpdate();
			em.createQuery("DELETE FROM Appelant").executeUpdate();
			em.createQuery("DELETE FROM Chauffeur").executeUpdate();
			em.createQuery("DELETE FROM Residence").executeUpdate();
			em.createQuery("DELETE FROM Destination").executeUpdate();
			em.createQuery("DELETE FROM Indisponibilite").executeUpdate();
			em.getTransaction().commit();
			em.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Deprecated
	public void importSave(List<Course> courses, List<Appelant> appelants, List<Chauffeur> chauffeurs,
			List<Residence> residences, List<Destination> destinations) {
		try {
			EntityManager em = factory.createEntityManager();
			em.getTransaction().begin();
			em.createQuery("DELETE FROM Course").executeUpdate();
			em.createQuery("DELETE FROM Appelant").executeUpdate();
			em.createQuery("DELETE FROM Chauffeur").executeUpdate();
			em.createQuery("DELETE FROM Residence").executeUpdate();
			em.createQuery("DELETE FROM Destination").executeUpdate();
			em.createQuery("DELETE FROM Indisponibilite").executeUpdate();
			/**
			 * / em.getTransaction().commit(); em.getTransaction().begin(); /
			 **/
			for (Destination entity : destinations) {
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
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void deleteFamille(Long id, Long id2) {
		EntityManager em = factory.createEntityManager();

		Appelant app = em.find(Appelant.class, id);
		Appelant app2 = em.find(Appelant.class, id2);

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
		Appelant app2 = em.find(Appelant.class, id2);
		if (app2 != null) {
			Appelant app = em.find(Appelant.class, id);

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

		Appelant app = em.find(Appelant.class, id);

		em.getTransaction().begin();
		app.addAffinite(id2);
		em.getTransaction().commit();

		em.close();
	}

	@Override
	public void delProche(Long id, Long id2) {
		EntityManager em = factory.createEntityManager();

		Appelant app = em.find(Appelant.class, id);

		em.getTransaction().begin();
		app.removeAffinite(id2);
		em.getTransaction().commit();

		em.close();
	}

	@Override
	public void addRestrict(Long id, Long id2) {
		EntityManager em = factory.createEntityManager();

		Appelant app = em.find(Appelant.class, id);

		em.getTransaction().begin();
		app.addRestriction(id2);
		em.getTransaction().commit();

		em.close();
	}

	@Override
	public void delRestrict(Long id, Long id2) {
		EntityManager em = factory.createEntityManager();

		Appelant app = em.find(Appelant.class, id);

		em.getTransaction().begin();
		app.removeRestriction(id2);
		em.getTransaction().commit();

		em.close();
	}
	
	@Override
	public void addRestrictA(Long id, Long id2) {
		EntityManager em = factory.createEntityManager();

		Appelant app = em.find(Appelant.class, id);

		em.getTransaction().begin();
		app.addRestrictionA(id2);
		em.getTransaction().commit();

		em.close();
	}

	@Override
	public void delRestrictA(Long id, Long id2) {
		EntityManager em = factory.createEntityManager();

		Appelant app = em.find(Appelant.class, id);

		em.getTransaction().begin();
		app.removeRestrictionA(id2);
		em.getTransaction().commit();

		em.close();
	}

	@Override
	public List<AppelantItemList> getAppelantList(List<Long> ids) {
		List<AppelantItemList> result = new LinkedList<AppelantItemList>();
		try {
			EntityManager em = factory.createEntityManager();
			TypedQuery<Appelant> q = em.createQuery("SELECT t FROM Appelant t ORDER BY t.id", Appelant.class);
			for (Appelant tuple : q.getResultList()) {
				if (ids.contains(tuple.getId())) {
					result.add(new AppelantItemList(tuple));
				}
			}
			em.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public List<ChauffeurItemList> getChauffeurList(List<Long> ids) {
		List<ChauffeurItemList> result = new LinkedList<ChauffeurItemList>();
		try {
			EntityManager em = factory.createEntityManager();
			TypedQuery<Chauffeur> q = em.createQuery("SELECT t FROM Chauffeur t ORDER BY t.name, t.firstname", Chauffeur.class);
			for (Chauffeur tuple : q.getResultList()) {
				if (ids.contains(tuple.getId())) {
					result.add(new ChauffeurItemList(tuple));
				}
			}
			em.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public List<CourseItemList> getCourseApplant(Long id) {
		List<CourseItemList> result = new LinkedList<CourseItemList>();
		try {
			EntityManager em = factory.createEntityManager();
			TypedQuery<Course> q = em.createQuery(
					"SELECT c FROM Course c WHERE c.appelant.id=:arg1 AND c.date>=CURRENT_DATE ORDER BY c.date, c.heureRDV", Course.class);
			q.setParameter("arg1", id);
			for (Course tuple : q.getResultList()) {
				result.add(new CourseItemList(tuple));
			}
			em.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	@Override
	public List<CourseItemList> getOldCourseApplant(Long id) {
		List<CourseItemList> result = new LinkedList<CourseItemList>();
		try {
			EntityManager em = factory.createEntityManager();
			TypedQuery<Course> q = em.createQuery(
					"SELECT c FROM Course c WHERE c.appelant.id=:arg1 AND c.date<CURRENT_DATE ORDER BY c.date DESC, c.heureRDV DESC", Course.class);
			q.setParameter("arg1", id);
			for (Course tuple : q.getResultList()) {
				result.add(new CourseItemList(tuple));
			}
			em.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public List<Residence> getListResidence() {
		List<Residence> result = new ArrayList<>();
		try {
			EntityManager em = factory.createEntityManager();
			TypedQuery<Residence> q = em.createQuery("SELECT t FROM Residence t ORDER BY t.name", Residence.class);
			result = q.getResultList();
			em.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public List<Destination> getListDestination() {
		List<Destination> result = new ArrayList<>();
		try {
			EntityManager em = factory.createEntityManager();
			TypedQuery<Destination> q = em.createQuery("SELECT t FROM Destination t ORDER BY t.name", Destination.class);
			result = q.getResultList();
			em.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	@Deprecated
	public void importUsers(List<Utilisateur> users) {
		try {
			EntityManager em = factory.createEntityManager();
			em.getTransaction().begin();
			// em.createQuery("DELETE FROM Utilisateur").executeUpdate();

			for (Utilisateur user : users) {
				em.persist(user);
				em.flush();
				em.clear();
			}

			em.getTransaction().commit();
			em.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	@Deprecated
	public void importApplantsOld(List<Appelant> appelants) {
		try {
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
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void importApplants(List<Appelant> appelants) {
		try {
			EntityManager em = factory.createEntityManager();
			em.getTransaction().begin();
			// em.createQuery("DELETE FROM Course").executeUpdate();
			// em.createQuery("DELETE FROM Appelant").executeUpdate();

			for (Appelant appelant : appelants) {
				em.persist(appelant);
				em.flush();
				em.clear();
			}

			em.getTransaction().commit();
			em.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void importChauffeurs(List<Chauffeur> chauffeurs) {
		try {
			EntityManager em = factory.createEntityManager();
			em.getTransaction().begin();
			// em.createQuery("DELETE FROM Chauffeur").executeUpdate();

			for (Chauffeur chauffeur : chauffeurs) {
				em.persist(chauffeur);
				em.flush();
				em.clear();
			}

			em.getTransaction().commit();
			em.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void importCourses(List<Course> courses) {
		try {
			EntityManager em = factory.createEntityManager();
			em.getTransaction().begin();
			// em.createQuery("DELETE FROM Course").executeUpdate();

			for (Course course : courses) {
				em.persist(course);
				em.flush();
				em.clear();
			}

			em.getTransaction().commit();
			em.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void importResidences(List<Residence> residences) {
		try {
			EntityManager em = factory.createEntityManager();
			em.getTransaction().begin();
			// em.createQuery("DELETE FROM Course").executeUpdate();

			for (Residence entity : residences) {
				em.persist(entity);
				em.flush();
				em.clear();
			}

			em.getTransaction().commit();
			em.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void importDestination(List<Destination> destinations) {
		try {
			EntityManager em = factory.createEntityManager();
			em.getTransaction().begin();
			// em.createQuery("DELETE FROM Course").executeUpdate();

			for (Destination entity : destinations) {
				em.persist(entity);
				em.flush();
				em.clear();
			}

			em.getTransaction().commit();
			em.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public Collection<PlanningChauffeur> getPlanning(LocalDate date) {
		Map<Long, PlanningChauffeur> result = new HashMap<Long, PlanningChauffeur>();
		try {
			EntityManager em = factory.createEntityManager();
			TypedQuery<Chauffeur> q1 = em.createQuery("SELECT t FROM Chauffeur t WHERE t.display = TRUE", Chauffeur.class);
			for (Chauffeur tuple : q1.getResultList()) {
				result.put(tuple.getId(), new PlanningChauffeur(tuple.getFullName()));
			}

			TypedQuery<Course> q = em.createQuery(
					//TODO MODECOURSE "SELECT t FROM Course t WHERE t.date=:locdate AND (t.chauffeur IS NOT NULL OR t.chauffeurSec IS NOT NULL) ORDER BY t.date, t.heureDomicile",
					"SELECT t FROM Course t WHERE t.date=:locdate AND t.chauffeur IS NOT NULL ORDER BY t.date, t.heureDomicile",
					Course.class);
			q.setParameter("locdate", date);

			Chauffeur c;
			Indisponibilite i;

			for (Course course : q.getResultList()) {
				c = course.getChauffeur();
				if (c != null && result.containsKey(c.getId())) {
					i = new IndisponibiliteCourse(course.getAppelant().getFullName());
					i.setId(course.getId());
					i.setDescription(course.getTypeCourse() + ": " + course.getAdresseDest());
					i.setDateStart(date);
					i.setDateEnd(date);
					i.setHeureStart(course.getHeureRDV());
					//LocalTime fin = course.getHeureRetour().equals(LocalTime.MIDNIGHT)?course.getHeureRDV().plusHours(Variable.FORFAIT_HEURE):course.getHeureRetour();
					//i.setHeureEnd(fin);
					i.setModeCourse(course.getTrajet());
					result.get(c.getId()).add(i);
				}
			}

			TypedQuery<Indisponibilite> q2 = em.createQuery(
					"SELECT t FROM Indisponibilite t WHERE :locdate BETWEEN t.dateStart AND t.dateEnd",
					Indisponibilite.class);
			q2.setParameter("locdate", date);

			for (Indisponibilite indisponibilite : q2.getResultList()) {
				if(result.containsKey(indisponibilite.getIdChauffeur())) {
					result.get(indisponibilite.getIdChauffeur()).add(indisponibilite);
				}
			}

			em.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<PlanningChauffeur> planning = new ArrayList<PlanningChauffeur>(result.values());
		Collections.sort(planning);
		return planning;
	}

	@Override
	public void addOrUpdateIndisponibilite(Indisponibilite entity) {
		if(entity.getType() != TypeIndisponibilite.COURSE) {
			EntityManager em = factory.createEntityManager();
			em.getTransaction().begin();
			if (entity.getId() == null) {
				em.persist(entity);
			} else {
				em.merge(entity);
			}
			em.getTransaction().commit();
			em.close();
		}
	}
	
	@Override
	public void delete(Indisponibilite entity) {
		if(entity.getType() != TypeIndisponibilite.COURSE) {
			EntityManager em = factory.createEntityManager();
			entity = em.find(Indisponibilite.class, entity.getId());
			em.getTransaction().begin();
			em.remove(entity);
			em.getTransaction().commit();
			em.close();
		}
	}

	@Override
	public List<Indisponibilite> getAllIndisponibilite() {
		List<Indisponibilite> result = new LinkedList<Indisponibilite>();
		try {
			EntityManager em = factory.createEntityManager();
			TypedQuery<Indisponibilite> q = em.createQuery("SELECT t FROM Indisponibilite t ORDER BY t.idChauffeur", Indisponibilite.class);
			result = new LinkedList<Indisponibilite>(q.getResultList());
			em.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	
	@Override
	public List<Indisponibilite> getIntervalIndisponibilite(LocalDate start, LocalDate end) {
		List<Indisponibilite> result = new LinkedList<Indisponibilite>();
		try {
			EntityManager em = factory.createEntityManager();
			TypedQuery<Indisponibilite> q = em.createQuery("SELECT t FROM Indisponibilite t WHERE t.dateEnd>=:end AND t.dateStart<=:start ORDER BY t.idChauffeur", Indisponibilite.class);
			q.setParameter("start", start);
			q.setParameter("end", end);
			result = new LinkedList<Indisponibilite>(q.getResultList());
			em.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public void importIndisponibilite(List<Indisponibilite> indisponibilites) {
		try {
			EntityManager em = factory.createEntityManager();
			em.getTransaction().begin();
			// em.createQuery("Indisponibilite FROM Course").executeUpdate();

			for (Indisponibilite indisp : indisponibilites) {
				em.persist(indisp);
				em.flush();
				em.clear();
			}

			em.getTransaction().commit();
			em.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void importUtilisateur(List<Utilisateur> utilisateurs) {
		try {
			EntityManager em = factory.createEntityManager();
			em.getTransaction().begin();
			// em.createQuery("DELETE FROM Utilisateur").executeUpdate();
			for (Utilisateur utilisateur : utilisateurs) {
				em.persist(utilisateur);
				em.flush();
				em.clear();
			}

			em.getTransaction().commit();
			em.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void setSettings(Settings settings) {
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		em.merge(settings);
		em.getTransaction().commit();
		em.close();
	}

	@Override
	public boolean validIdAppelant(Long id) {
		EntityManager em = factory.createEntityManager();
		Appelant app = em.find(Appelant.class, id);
		em.close();
		return app == null;
	}

	
	
	@Override
	public List<Integer> getListYears() {
		// TODO Auto-generated method stub
		return null;
	}
}
