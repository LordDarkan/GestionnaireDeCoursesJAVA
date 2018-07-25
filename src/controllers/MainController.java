package controllers;

import java.util.List;

import data.Mapper;
import models.Utilisateur;
import util.Role;

public abstract class MainController {
	private static boolean isAdminMode = false;
	
	public static boolean isAdminMode() {
		return isAdminMode;
	}
	
	private Mapper mapper;
	private Utilisateur user;

	protected MainController() {
		mapper = Mapper.getInstance();
		user = null;
	}
	
	protected List<Utilisateur> getAllPermanent(){
		return mapper.getAllUser();
	}
	
	//ToOverride
	protected void connectWith(Utilisateur user) {
		this.user = user;
		isAdminMode = user != null && user.getRole() == Role.ADMIN;
	}
	
	protected Utilisateur getUser(){
		return user;
	}
}