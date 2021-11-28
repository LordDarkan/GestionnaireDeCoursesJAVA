package fxml.ControllerFXML;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import application.MyApp;
import application.Variable;
import controllers.MainController;
import data.SaveManager;
import fxml.Message;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import models.Utilisateur;
import util.LoggerManager;
import util.UserManager;

public class MainControllerFXML extends MainController implements Initializable {
	private static final Logger LOG = LoggerManager.getLogger();

	@FXML
    private VBox mainContainer;
	@FXML
    private HBox mainBoxLogged;
    @FXML
    private Button mainBtnLogout;
    @FXML
    private Label mainLabelName;
    
    private List<ITabController> tabsController;
    
    private TabPane tabContainer;
    
    public MainControllerFXML() {
    	tabsController = new LinkedList<ITabController>();
    	tabContainer = null;
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		mainBtnLogout.setOnAction((ActionEvent e) -> logout());
		loginPage();
	}
	
	private void loginPage() {
		try {
			tabsController.forEach(c->c.logout());
			mainContainer.getChildren().clear();
			mainBoxLogged.setVisible(false);
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MyApp.class.getClassLoader().getResource("fxml/views/Login.fxml"));
			loader.setController(new LoginControllerFXML(this));
			GridPane content;
			content = (GridPane)loader.load();
			mainContainer.getChildren().add(content);
		} catch (IOException e) {//TODO
			e.printStackTrace();
		}
	}
	
	@Override
	public void connectWith(Utilisateur user) {
		LOG.info("LOGIN "+user.getFullName());
		UserManager.setUser(user);
		super.connectWith(user);
		mainContainer.getChildren().clear();
		mainLabelName.setText(getUser().getShortName());
		mainBoxLogged.setVisible(true);
		
		if(tabContainer == null) {
			tabContainer = new TabPane();
			tabContainer.setPrefHeight(99999);
			tabContainer.setMinWidth(Region.USE_COMPUTED_SIZE);
			tabContainer.setTabMinHeight(Region.USE_COMPUTED_SIZE);

			tabsController.add(new ListeAppelantControllerFXML(this,getUser(),tabContainer));
			tabsController.add(new ListeCourseControllerFXML(getUser(),tabContainer));
			tabsController.add(new ViewCourseControllerFXML(this,getUser(),tabContainer));
			tabsController.add(new PlanningControlleurFXML(this,getUser(),tabContainer));
			tabsController.add(new ListeChauffeurControllerFXML(getUser(),tabContainer));
			tabsController.add(new SettingsControllerFXML(getUser(),tabContainer));
			tabsController.add(new ImportExportControllerFXML(getUser(),tabContainer,this));
			
		}
		
		tabsController.forEach(c->c.login(user));
		
		mainContainer.getChildren().add(tabContainer);
		tabContainer.getSelectionModel().select(0);
	}
	
	//TODO@Deprecated
	public void selectCourse(Long idCourse) {
		int indiceViewCourseControllerFXML = 2;
		tabContainer.getSelectionModel().select(indiceViewCourseControllerFXML);
		tabsController.get(indiceViewCourseControllerFXML).select(idCourse);
	}
	
	//TODO@Deprecated
	public void newCourse(Long id) {
		int indiceCourseControllerFXML = 1;
		tabContainer.getSelectionModel().select(indiceCourseControllerFXML);
		((ListeCourseControllerFXML)tabsController.get(indiceCourseControllerFXML)).newCourse(id);
	}
	
	//TODO GoTo
	public void GoToCourse(String tab,String action,Long idSelect) {//TODO enum + dictionaire
		int indiceCourseControllerFXML = 1;
		tabContainer.getSelectionModel().select(indiceCourseControllerFXML);
		((ListeCourseControllerFXML)tabsController.get(indiceCourseControllerFXML)).select(idSelect).action(action);
	}

	@Override
	public void logout() {
		LOG.info("LOGOUT "+UserManager.getFullName());
		try {
			if (!SaveManager.saveDeco())
				Message.alert(Variable.MSG_ERROR_SAVE);
		} catch (Exception e) {
			LOG.log(Level.SEVERE, "SAVE LOGOUT "+ UserManager.getFullName(), e);
			Message.alert(e.getMessage());
		}
		loginPage();
	}

	@Override
	public void logoutAuto() {
		LOG.info("LOGOUT AUTO "+UserManager.getFullName());
		loginPage();
	}

	
}
