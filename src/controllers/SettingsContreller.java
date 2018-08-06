package controllers;

import java.util.List;

import data.Mapper;
import models.Hopital;
import models.Residence;
import models.Utilisateur;

public abstract class SettingsContreller {
	private Utilisateur user;
	private Mapper mapper;

	protected SettingsContreller(Utilisateur user) {
		newLog(user);
	}
	
	protected void newLog(Utilisateur user) {
		this.user = user;
		mapper = Mapper.getInstance();
	}
	
	protected void clear(){
		user = null;
		mapper = null;
	}
	
	protected boolean isAdmin() {
		return user.isAdmin();
	}
	
	protected List<Utilisateur> getListUser() {
		return mapper.getAllUser();
	}
	
	protected List<Hopital> getListHopital() {
		return mapper.getListHopital();
	}
	
	protected List<Residence> getListResidence() {
		return mapper.getListResidence();
	}

	protected void addUser(Utilisateur u) {
		mapper.addOrUpdateUser(u);
	}
	
	protected void addHopital(Hopital h) {
		mapper.addOrUpdate(h);
	}
	
	protected void addResidence(Residence r) {
		mapper.addOrUpdate(r);
	}
	
	protected void removeUser(Utilisateur u) {
		mapper.delete(u);
	}
	
	protected void removeHopital(Hopital h) {
		mapper.delete(h);
	}
	
	protected void removeResidence(Residence r) {
		mapper.delete(r);
	}
}
