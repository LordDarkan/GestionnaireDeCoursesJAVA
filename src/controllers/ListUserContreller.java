package controllers;

import java.util.List;

import data.Mapper;
import models.Utilisateur;

public abstract class ListUserContreller {
	private Utilisateur user;

	protected ListUserContreller(Utilisateur user) {
		newLog(user);
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
	
	protected List<Utilisateur> getListUser() {
		return Mapper.getInstance().getAllUser();
	}

	protected void add(Utilisateur user) {
		Mapper.getInstance().addOrUpdateUser(user);
	}
}
