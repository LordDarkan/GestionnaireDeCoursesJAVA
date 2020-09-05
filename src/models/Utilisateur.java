package models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import data.CSVRow;
import util.Gate;
import util.Titre;

@Entity
public class Utilisateur implements CSVRow  {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Titre titre = Titre.Aucun;
	private String name = "";
	private String firstname = "";
	private boolean admin = false;

	public Long getId() {
		return id;
	}
	
	public void setName(String name) {
		this.name =  Gate.encoding(name.trim());
	}

	public void setFirstname(String firstname) {
		this.firstname = Gate.encoding(firstname.trim());
	}
	
	public String getFullName() {
		String fullName;
		if (firstname.isEmpty() && name.isEmpty()) {
			fullName = "SOMEONE";
		} else {
			fullName = String.format("%s %s", name, firstname);
		}
		return fullName;
	}

	public String getShortName() {
		String result;
		if(name.isEmpty()) {
			result = String.format("%s", firstname);
		} else {
			result = String.format("%s %s.", firstname,name.substring(0,1));
		}
		return result;
	}

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}
	
	public static void valdation(Utilisateur obj) throws IllegalArgumentException {//TODO
		if(obj.firstname.length()<2) throw new IllegalArgumentException("Le prénom est invalide");
		if(obj.name.length()<2) throw new IllegalArgumentException("Le nom est invalide");
	}

	public Titre getTitre() {
		return titre;
	}

	public void setTitre(Titre titre) {
		this.titre = titre;
	}

	@Override
	public String getRowIdentity() {
		return Utilisateur.class.getName()+" "+id;
	}
	
	@Override
	public String getRowCsv() {
		StringBuilder str = new StringBuilder();
		str.append(name);
		str.append(";");
		str.append(firstname);
		str.append(";");
		str.append(admin?"OUI":"NON");
		str.append(";END");
		return str.toString();
	}

	@Override
	public String getEnTeteCsv() {
		return enteteCSV;
	}
	
	public static final String enteteCSV = "NOM;PRENOM;ADMIN;END";
}
