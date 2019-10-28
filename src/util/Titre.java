package util;

public enum Titre {
	Mr,
	Mme,
	Aucun;
	

	public static Titre get(String string) {
		Titre titre = Titre.Aucun;
		string = string.toUpperCase();
		if (string.equals("H")) {
			titre = Titre.Mr;
		} else if (string.equals("F")) {
			titre = Titre.Mme;
		}
		return titre;
	}
	
	public static String toString(Titre titre) {
		String str = "";
		if (titre == Titre.Mr) {
			str = "M";
		} else if (titre == Titre.Mme) {
			str = "F";
		}
		return str;
	}
}
