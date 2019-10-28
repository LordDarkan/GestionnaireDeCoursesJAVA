package util;

public enum TypeIndisponibilite {
	COURSE,
	INDISPO,
	PASDERANGER;
	
	
	public static TypeIndisponibilite get(String str) {
		TypeIndisponibilite type = TypeIndisponibilite.INDISPO;
		if (str.equalsIgnoreCase("COURSE")) {
			type = TypeIndisponibilite.COURSE;
		} else if (str.equalsIgnoreCase("INDISPO")) {
			type = TypeIndisponibilite.INDISPO;
		} else if (str.equalsIgnoreCase("PASDERANGER")) {
			type = TypeIndisponibilite.PASDERANGER;
		}
		return type;
	}

}
