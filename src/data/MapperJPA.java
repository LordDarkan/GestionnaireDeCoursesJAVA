package data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.*;

import models.Appelant;
import models.Chauffeur;
import models.Course;
import models.Hopital;
import models.Residence;
import models.Utilisateur;
import models.item.AppelantList;
import models.item.ChauffeurList;
import models.item.CourseList;


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
	
	public void close() {
		if(factory != null)
			factory.close();
		factory = null;
	}
	
	@Override
	public void init() {
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		Residence res1 = new Residence();
		res1.setName("NaN");
		res1.setAdresse("");
		res1.setCp("");
		res1.setLocalite("");
		Residence res2 = new Residence();
		res2.setName("test");
		res2.setAdresse("test, 10");
		res2.setCp("0000");
		res2.setLocalite("test");
		Residence res3 = new Residence();
		res3.setName("Les Acacias");
		res3.setAdresse("Rue du Vicinal, 81");
		res3.setCp("4400");
		res3.setLocalite("MONS LEZ LIEGE");
		em.persist(res1);
		em.persist(res2);
		em.persist(res3);
		em.getTransaction().commit(); 
        em.close(); 
		
		em = factory.createEntityManager();
		em.getTransaction().begin();
		Hopital hop1 = new Hopital();
		hop1.setName("NaN");
		hop1.setAdresse("");
		hop1.setCp("");
		hop1.setLocalite("");
		Hopital hop2 = new Hopital();
		hop2.setName("test");
		hop2.setAdresse("test, 10");
		hop2.setCp("0000");
		hop2.setLocalite("test");
		em.persist(hop1);
		em.persist(hop2);
		em.getTransaction().commit(); 
        em.close(); 
		
		em = factory.createEntityManager();
		em.getTransaction().begin();
		Utilisateur perm = new Utilisateur();
		perm.setName("dupont");
		perm.setFirstname("alain");
		Utilisateur user = new Utilisateur();
		user.setName("antoine");
		user.setFirstname("jean-paul");
		user.setAdmin(true);
		em.persist(perm);
		em.persist(user);
		em.getTransaction().commit(); 
        em.close(); 
		
        em = factory.createEntityManager();
		em.getTransaction().begin();
		Chauffeur c = new Chauffeur();
		c.setName("Luc");
		c.setFirstname("Dupont");
		Chauffeur c2 = new Chauffeur();
		c2.setName("Dupont");
		c2.setFirstname("Dupont");
		em.persist(c);
		em.persist(c2);
		em.getTransaction().commit();
        em.close();
        
        em = factory.createEntityManager();
		em.getTransaction().begin();
		Appelant appelant = new Appelant();
		appelant.setName("Dupont");
		appelant.setFirstname("Luc");
		Appelant appelant2 = new Appelant();
		appelant2.setName("resident");
		appelant2.setFirstname("permanant");
		appelant2.setResidence("Les Acacias");
		em.persist(appelant);
		em.persist(appelant2);
		em.getTransaction().commit(); 
        em.close();
        
        em = factory.createEntityManager();
		em.getTransaction().begin();
		Course course = new Course(appelant2, "Denis");
		course.setAttente(true);
		em.persist(course);
		em.getTransaction().commit(); 
        em.close();
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Utilisateur> getAllUser() {
		List<Utilisateur> result = new ArrayList<>();
		try{
			EntityManager em = factory.createEntityManager();
			Query q = em.createQuery("SELECT t FROM Utilisateur t ORDER BY t.firstname");
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
	public void importUsers(List<Utilisateur> users) {
		try{
			EntityManager em = factory.createEntityManager();
			em.getTransaction().begin();
			em.createQuery("DELETE FROM Utilisateur").executeUpdate();
			
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

	@SuppressWarnings("unchecked")
	@Override
	public List<Appelant> getAllAppelant() {
		List<Appelant> result = new LinkedList<Appelant>();
		try{
			EntityManager em = factory.createEntityManager();
			Query q = em.createQuery("SELECT t FROM Appelant t ORDER BY t.id");
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
			em.merge(entity);
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
	public void importApplants(List<Appelant> appelants) {
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

	@SuppressWarnings("unchecked")
	@Override
	public List<Course> getAllCourse() {
		List<Course> result = new LinkedList<Course>();
		try{
			EntityManager em = factory.createEntityManager();
			Query q = em.createQuery("SELECT t FROM Course t ORDER BY t.id");
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
	public void importCourses(List<Course> courses) {
		try{
			EntityManager em = factory.createEntityManager();
			em.getTransaction().begin();
			em.createQuery("DELETE FROM Course").executeUpdate();
			
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

	@SuppressWarnings("unchecked")
	@Override
	public List<Chauffeur> getAllChauffeur() {
		List<Chauffeur> result = new LinkedList<Chauffeur>();
		try{
			EntityManager em = factory.createEntityManager();
			Query q = em.createQuery("select t from Chauffeur t ORDER BY t.id");
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
	public void importChauffeurs(List<Chauffeur> chauffeurs) {
		try{
			EntityManager em = factory.createEntityManager();
			em.getTransaction().begin();
			em.createQuery("DELETE FROM Chauffeur").executeUpdate();
			
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

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getAllResidence() {
		List<String> result = new ArrayList<>();
		try{
			EntityManager em = factory.createEntityManager();
			Query q = em.createQuery("SELECT t.name FROM Residence t ORDER BY t.name");
			result = q.getResultList();
			result.forEach(c->System.out.println(c));
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
	public void importResidences(List<Residence> residences) {
		// TODO Auto-generated method stub
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getAllHopital() {
		List<String> result = new ArrayList<>();
		try{
			EntityManager em = factory.createEntityManager();
			Query q = em.createQuery("SELECT t.name FROM Hopital t ORDER BY t.name");
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
	public void importHopitals(List<Hopital> hopitals) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<CourseList> getCourse(boolean all, Long idChauffeur, boolean day, LocalDate date) {
		/*EntityManager em = factory.createEntityManager();
		TypedQuery<Object[]> query = em.createQuery("SELECT c.name, c.capital.name FROM Course t", Object[].class);
		List<Object[]> results = query.getResultList();
		for (Object[] result : results) {
			System.out.println("Country: " + result[0] + ", Capital: " + result[1]);
		}*/
		
		List<CourseList> result = new LinkedList<CourseList>();
		try{
			EntityManager em = factory.createEntityManager();
			TypedQuery<Course> q = em.createQuery("SELECT t FROM Course t ORDER BY t.id",Course.class);
			for (Course course : q.getResultList()) {
				if(day && course.getDate().equals(date)) {
					if (all) {
						result.add(new CourseList(course));
					} else if(idChauffeur==null && course.getChauffeur()==null) {
						result.add(new CourseList(course));
					} else if (idChauffeur!=null && course.getChauffeur()!=null && course.getChauffeur().getId()==idChauffeur) {
						result.add(new CourseList(course));
					} else if (idChauffeur!=null && course.getChauffeurSec()!=null && course.getChauffeurSec().getId()==idChauffeur) {
						result.add(new CourseList(course));
					}
				} else if(!day && (course.getDate().isAfter(LocalDate.now())||course.getDate().equals(LocalDate.now()))) {
					if (all) {
						result.add(new CourseList(course));
					} else if(idChauffeur==null && course.getChauffeur()==null) {
						result.add(new CourseList(course));
					} else if (idChauffeur!=null && course.getChauffeur()!=null && course.getChauffeur().getId()==idChauffeur) {
						result.add(new CourseList(course));
					} else if (idChauffeur!=null && course.getChauffeurSec()!=null && course.getChauffeurSec().getId()==idChauffeur) {
						result.add(new CourseList(course));
					}
				}
			}
			em.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public List<ChauffeurList> getChauffeurList() {
		List<ChauffeurList> result = new LinkedList<ChauffeurList>();
		try{
			EntityManager em = factory.createEntityManager();
			TypedQuery<Chauffeur> q = em.createQuery("SELECT t FROM Chauffeur t ORDER BY t.id",Chauffeur.class);
			for (Chauffeur course : q.getResultList()) {
				result.add(new ChauffeurList(course));
			}
			em.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public List<AppelantList> getAppelantList() {
		List<AppelantList> result = new LinkedList<AppelantList>();
		try{
			EntityManager em = factory.createEntityManager();
			TypedQuery<Appelant> q = em.createQuery("SELECT t FROM Appelant t ORDER BY t.id",Appelant.class);
			for (Appelant tuple : q.getResultList()) {
				result.add(new AppelantList(tuple));
			}
			em.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
