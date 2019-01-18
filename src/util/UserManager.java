package util;

import models.Utilisateur;

public class UserManager {
    private static Utilisateur user = new Utilisateur();
    
    public static void setUser(Utilisateur user) {
		UserManager.user = user;
	}
    
    public static boolean isAdmin() {
    	return user.isAdmin();
	}
    
    public static String getFullName() {
    	return user.getFullName();
	}
}