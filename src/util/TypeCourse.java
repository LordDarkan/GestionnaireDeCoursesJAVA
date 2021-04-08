package util;

public enum TypeCourse {
	HOPITAL,
	MEDICAL,
	ALIMENTAIRE,
	COURTOISIE,
	ECOLE,
	TRAVAIL,
	AUTRE;

	public static TypeCourse get(String str) {
		TypeCourse type = TypeCourse.AUTRE;
		if (str.equalsIgnoreCase("HOPITAL")) {
			type = TypeCourse.HOPITAL;
		} else if (str.equalsIgnoreCase("MEDICAL")) {
			type = TypeCourse.MEDICAL;
		} else if (str.equalsIgnoreCase("ALIMENTAIRE")) {
			type = TypeCourse.ALIMENTAIRE;
		} else if (str.equalsIgnoreCase("COURTOISIE")) {
			type = TypeCourse.COURTOISIE;
		} else if (str.equalsIgnoreCase("ECOLE")) {
			type = TypeCourse.ECOLE;
		} else if (str.equalsIgnoreCase("TRAVAIL")) {
			type = TypeCourse.TRAVAIL;
		}
		return type;
	}
	
	public static String toString(TypeCourse type) {
		return type==null?"NONE":type.toString();
	}
}
