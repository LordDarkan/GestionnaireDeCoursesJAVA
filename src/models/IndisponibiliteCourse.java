package models;

public class IndisponibiliteCourse extends Indisponibilite {
	private String titre;
	
	public IndisponibiliteCourse(String titre) {
		this.titre = titre;
	}
	@Override
	public boolean isCourse() {
		return true;
	}
	@Override
	public String getTitre() {
		return titre;
	}
}
