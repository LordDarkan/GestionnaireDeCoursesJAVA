package util;

public enum Titre {
	Mr,
	Mme,
	Aucun;
	
;

	public static Titre get(String string) {
		Titre titre = Titre.Aucun;
		string = string.toLowerCase();
		if (string.startsWith("mo")) {
			titre = Titre.Mr;
		} else if (string.startsWith("ma")) {
			titre = Titre.Mme;
		}
		return titre;
	}
}
