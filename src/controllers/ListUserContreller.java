package controllers;

import java.util.ArrayList;

import data.Mapper;
import models.Appelant;
import models.Utilisateur;

public abstract class ListUserContreller {
	private Utilisateur user;

	protected ListUserContreller(Utilisateur user) {
		this.user = user;
	}
	
	protected void newLog(Utilisateur user) {
		this.user = user;
	}
	
	protected void clear(){
		user = null;
	}
	
	protected boolean isAdmin() {
		return user.isAdmin();
	}
}
