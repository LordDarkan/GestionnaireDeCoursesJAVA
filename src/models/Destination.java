package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import data.CSVRow;
import util.Gate;
import util.TypeCourse;

@Entity
public class Destination implements CSVRow {
	@Id
	@Column(name="name")
	private String name = "";
	@Column(name="adresse")
	private String adresse = "";
	@Column(name="cp")
	private String cp = "";
	@Column(name="localite")
	private String localite = "";
	@Column(name="tel")
	private String tel = "";
	@Column(name="typeCourse")
	private TypeCourse typeCourse = TypeCourse.AUTRE;
	
	@Column(name="str1")
	private String str1 = null;
	@Column(name="str2")
	private String str2 = null;
	@Column(name="str3")
	private String str3 = null;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name =  Gate.encoding(name);
	}
	public String getAdresse() {
		return adresse;
	}
	public void setAdresse(String adresse) {
		this.adresse =  Gate.encoding(adresse);
	}
	public String getCp() {
		return cp;
	}
	public void setCp(String cp) {
		this.cp =  Gate.encoding(cp);
	}
	public String getLocalite() {
		return localite;
	}
	public void setLocalite(String localite) {
		this.localite =  Gate.encoding(localite);
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel =  Gate.encoding(tel);
	}
	
	public TypeCourse getTypeCourse() {
		return typeCourse;
	}
	public void setTypeCourse(TypeCourse typeCourse) {
		this.typeCourse = typeCourse;
	}
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(name);
		sb.append("\t|\t");
		sb.append(adresse);
		if (!localite.isEmpty()||!cp.isEmpty()) {
			sb.append(String.format("\t%s (%s)",localite,cp));
		}
		if (!tel.isEmpty()) {
			sb.append(String.format("\t|\tN° Tel: %s",tel));
		}

		sb.append("\t|\t");
		sb.append(typeCourse.toString());
		
		return sb.toString();
	}
	

	@Override
	public String getRowCsv() {
		StringBuilder str = new StringBuilder();
		str.append(name);
		str.append(";");
		str.append(adresse);
		str.append(";");
		str.append(cp);
		str.append(";");
		str.append(localite);
		str.append(";");
		str.append(tel);
		str.append(";");
		str.append(typeCourse.toString());
		str.append(";END");
		return str.toString();
	}
	
	@Override
	public String getEnTeteCsv() {
		return enteteCSV;
	}
	
	public static final String enteteCSV = "Nom;Adresse;CP;Localité;Téléphone;TypeCourse;END";

	public static void valdation(Destination obj) throws IllegalArgumentException {//TODO
		
	}
}
