package data;

import javax.persistence.*;


public class Mapper implements IMapper {
	
	private static final String PERSISTENCE_UNIT_NAME = "GestionnaireDeCourses";
	private static EntityManagerFactory factory;
	 
	 public Mapper() {
		 try{
			 factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
			 EntityManager em = factory.createEntityManager();
			}catch(Exception e) {
				e.printStackTrace();
				//System.exit(-1);
			}
		 
	 }

}
