package controllers;

import models.Utilisateur;

public interface IAbsTabContraller {
	void newLog(Utilisateur user);
	void clear();
}
