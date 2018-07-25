package data;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.*;

import models.Appelant;
import models.Chauffeur;
import models.Utilisateur;
import util.Role;


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
	public void init() {
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		
		Utilisateur perm = new Utilisateur();
		perm.setName("dupont");
		perm.setFirstname("alain");
		Utilisateur user = new Utilisateur();
		user.setName("antoine");
		user.setFirstname("jean-paul");
		user.setRole(Role.ADMIN);
		
		em.persist(perm);
		em.flush();
        em.clear();
		em.persist(user);
		em.flush();
        em.clear();
		
		em.getTransaction().commit(); 
        em.close(); 
		
        em = factory.createEntityManager();
		em.getTransaction().begin();
		
		Appelant appelant = new Appelant();
		appelant.setName("Dupont");
		appelant.setFirstname("Luc");
		Appelant appelant2 = new Appelant();
		appelant2.setName("Luc");
		appelant2.setFirstname("Luc");
		
		em.persist(appelant);
		em.flush();
        em.clear();
		em.persist(appelant2);
		em.flush();
        em.clear();
		
		em.getTransaction().commit(); 
        em.close();
        
        em = factory.createEntityManager();
		em.getTransaction().begin();
		
		Chauffeur c = new Chauffeur();
		c.setName("Dupont");
		c.setFirstname("Luc");
		Appelant c2 = new Appelant();
		c2.setName("Luc");
		c2.setFirstname("Luc");
		
		em.persist(c);
		em.flush();
        em.clear();
		em.persist(c2);
		em.flush();
        em.clear();
		
		em.getTransaction().commit(); 
        em.close(); 
		
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Utilisateur> getAllUser() {
		List<Utilisateur> permanents = new ArrayList<>();
		try{
			EntityManager em = factory.createEntityManager();
			Query q = em.createQuery("select t from Utilisateur t");
			permanents = q.getResultList();
			em.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return permanents;
	}
	
	

	@Override
	public void updateUser(Utilisateur user) {
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		em.merge(user);
		em.getTransaction().commit(); 
        em.close(); 
	}

	@Override
	public void setAdminRole(Long id,boolean toAdmin) {
		EntityManager em = factory.createEntityManager();
		Utilisateur user = em.find(Utilisateur.class, id);
		em.getTransaction().begin();
		//em.merge(user);
		user.setRole(toAdmin?Role.ADMIN:Role.PT);
		em.getTransaction().commit(); 
        em.close(); 
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Appelant> getAllAppelant() {
		List<Appelant> appelants = new LinkedList<Appelant>();
		try{
			EntityManager em = factory.createEntityManager();
			Query q = em.createQuery("select t from Appelant t");
			appelants = new LinkedList<Appelant>(q.getResultList());
			em.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return appelants;
	}

	@Override
	public void importApplants(List<Appelant> appelants) {
		try{
			EntityManager em = factory.createEntityManager();
			em.getTransaction().begin();
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
}
