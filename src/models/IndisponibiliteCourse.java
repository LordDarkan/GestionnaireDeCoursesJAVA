package models;

import util.Trajet;
import util.TypeIndisponibilite;

public class IndisponibiliteCourse extends Indisponibilite {
	private String titre;
	private Trajet modeCourse;
	
	public IndisponibiliteCourse(String titre) {
		this.titre = titre;
	}
	
	@Override
	public TypeIndisponibilite getType() {
		return TypeIndisponibilite.COURSE;
	}
	@Override
	public String getTitre() {
		return titre;
	}
	@Override
	public Trajet getModeCourse() {
		return modeCourse;
	}
	@Override
	public void setModeCourse(Trajet mode) {
		this.modeCourse = mode;
	}
}
