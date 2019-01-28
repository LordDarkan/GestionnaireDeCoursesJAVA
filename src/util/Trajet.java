package util; 
 
public enum Trajet { 
	ALLER("ALLER"), 
	RETOUR("RETOUR"), 
	ALLER_RETOUR("ALLER-RETOUR"); 
	
	private String name;
	private Trajet(String name) {
		this.name = name;
	}
	 
	public static Trajet get(String str) { 
		Trajet mode = null;
		if (str == null) {
			mode = Trajet.ALLER_RETOUR; 
		} else if (str.equalsIgnoreCase("ALLER")) { 
			mode = Trajet.ALLER; 
		} else if (str.equalsIgnoreCase("RETOUR")) { 
			mode = Trajet.RETOUR; 
		} else {// if (str.equalsIgnoreCase("ALLER_RETOUR")) { 
			mode = Trajet.ALLER_RETOUR;
		} 
		return mode; 
	}
	
	@Override
	public String toString() {
		return name;
	}
}