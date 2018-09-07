package util;

public enum TypeCourse {
	HOPITAL,
	MEDICAL,
	ALIMENTAIRE,
	COURTOISIE,
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
		}
		return type;
	}
}
