package data;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import models.Permanent;


public class Mapper implements IMapper {
	
	private static final String PERSISTENCE_UNIT_NAME = "GestionnaireDeCourses";
	private static EntityManagerFactory factory = null;
	
	public Mapper() {
		 if(factory == null) {
			try{
				factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
			}catch(Exception e) {
				e.printStackTrace();
				//System.exit(-1);
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Permanent> getAllPermanent() {
		List<Permanent> permanents = new ArrayList<>();
		try{
			EntityManager em = factory.createEntityManager();
			Query q = em.createQuery("select t from Permanent t");
			permanents = q.getResultList();
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return permanents;
	}
}
