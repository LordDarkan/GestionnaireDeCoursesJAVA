package controllers;

import java.util.List;

import data.Mapper;
import models.Utilisateur;

public abstract class MainController {
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
	}
	
	protected Utilisateur getUser(){
		return user;
	}
}