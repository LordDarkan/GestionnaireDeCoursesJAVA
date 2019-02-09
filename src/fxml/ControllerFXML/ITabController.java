package fxml.ControllerFXML;

import models.Utilisateur;

public interface ITabController{
	void logout();
	void login(Utilisateur user);
	void select(Long id);
}
