package controllers;

import java.util.ArrayList;
import java.util.List;

import data.Mapper;
import models.Utilisateur;
import util.Security;

public abstract class LoginController {
	private MainController mainController;
	private List<Utilisateur> listUser;
	private int indiceUser;

	protected LoginController(MainController mainController) {
		this.mainController = mainController;
		this.listUser = Mapper.getInstance().getAllUser();
		indiceUser = -1;
	}

	protected List<String> getListUser() {
		List<String> listStr = new ArrayList<String>();
		for(Utilisateur prm : listUser) {
			listStr.add(prm.getFullName());
		}
		return listStr;
	}

	protected boolean selectedValue(Number newValue) {
		indiceUser = newValue.intValue();
		boolean show = false;
		if(indiceUser >= listUser.size()) {
			show = true;
		}
		return show;
	}
	
	protected void connect(String name) {
		if (indiceUser >= 0 && indiceUser < listUser.size()) {
			Utilisateur perm = listUser.get(indiceUser);
			mainController.connectWith(perm);
		}
		else if(indiceUser >= listUser.size()) {
			if (name == null || name.trim().length()<3) throw new IllegalArgumentException("Le Nom n'est pas valide");
			Utilisateur user = new Utilisateur();
			user.setFirstname(name.trim());
			
			if(Security.lexDev(name))
				user.setRole(Security.getlexDevRole());
			
			mainController.connectWith(user);
		}
	}
}
