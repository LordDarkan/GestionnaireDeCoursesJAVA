package data;

import java.util.List;

import models.Appelant;
import models.Utilisateur;

public abstract class Mapper {
	private static Mapper instance;
	
	public static void setInstance(Mapper mapper) {
		instance = mapper;
	}
	public static Mapper getInstance() {
		return instance;
	}

	public abstract void init();
	
	public abstract List<Utilisateur> getAllUser();
	public abstract void updateUser(Utilisateur user);
	public abstract void setAdminRole(Long id, boolean toAdmin);
	public abstract List<Appelant> getAllAppelant();
	public abstract void importApplants(List<Appelant> appelants);
}
