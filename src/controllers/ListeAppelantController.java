package controllers;

import java.util.ArrayList;
import java.util.List;

import data.Mapper;
import models.Appelant;

public abstract class ListeAppelantController {
	private ListeAppelantController contSpec;
	private List<Appelant> appelants;
	private Appelant appelant;
	
	public ListeAppelantController() {
		appelants = Mapper.getInstance().getAllAppelant();
	}
	protected void setContolerSpec(ListeAppelantController contSpec){
		this.contSpec = contSpec;
	}
	
	protected List<Appelant> getAllAppelant(){
		return appelants;
	}
	
	protected void search(String recherche){
		recherche = recherche.trim().toLowerCase();
		
		if(recherche.length()>=3 || recherche.matches("\\p{Digit}+")) {
			List<Appelant> result = new ArrayList<Appelant>();
			for (Appelant appelant : appelants) {
				if (appelant.getName().toLowerCase().startsWith(recherche)
						||appelant.getFirstname().toLowerCase().startsWith(recherche)
						||appelant.getId().toString().startsWith(recherche))
				{
					result.add(appelant);
				}
			}
			contSpec.setListeAppelant(result);
		} else {
			contSpec.setListeAppelant(appelants);
		}
	}
	
	protected void setAppelant(Appelant app) {
		appelant = app;
		contSpec.showAppelant(app);
	}
	
	public abstract void setListeAppelant(List<Appelant> appelants);
	public abstract void showAppelant(Appelant app);
}
