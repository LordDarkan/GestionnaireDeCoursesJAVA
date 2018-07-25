package models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Course {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Chauffeur chauffeur = null;
	private Appelant appelant;
	
	private String destination;
	private Date date;
	private int heureRDV;//h = int/60 et m = int%60
	private int heureDomicile;
	
	private boolean annuler = false;
}
