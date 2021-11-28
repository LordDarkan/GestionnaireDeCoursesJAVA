package fxml.ControllerFXML;

import models.Utilisateur;

public interface ITabController{
	void logout();
	void login(Utilisateur user);
	ITabController select(Long id);
	void action(String action);
}
