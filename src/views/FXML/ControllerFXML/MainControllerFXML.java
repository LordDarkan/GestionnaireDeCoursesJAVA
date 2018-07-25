package views.FXML.ControllerFXML;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.MyApp;
import controllers.MainController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import models.Utilisateur;

public class MainControllerFXML extends MainController implements Initializable {

	@FXML
    private VBox mainContainer;
	@FXML
    private HBox mainBoxLoged;
    @FXML
    private Button mainBtnLogout;
    @FXML
    private Label mainLabelName;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		mainBtnLogout.setOnAction((ActionEvent e) -> login());
		login();
	}
	
	private void login() {
		try {
			mainContainer.getChildren().clear();
			mainBoxLoged.setVisible(false);
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MyApp.class.getClassLoader().getResource("views/FXML/Login.fxml"));
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
		super.connectWith(user);
		mainContainer.getChildren().clear();
		mainLabelName.setText(getUser().getShortName());
		mainBoxLoged.setVisible(true);
		
		TabPane tabPane = new TabPane();
		tabPane.setMinWidth(Region.USE_COMPUTED_SIZE);
		tabPane.setTabMinHeight(Region.USE_COMPUTED_SIZE);
		
		Tab tab = loadListeCourse();
		if(tab != null)
			tabPane.getTabs().add(tab);
		
		tab = loadListeAppelant();
		if(tab != null)
			tabPane.getTabs().add(tab);
		
		
		if(isAdminMode()) {
			tab = loadListUser();
			if(tab != null)
				tabPane.getTabs().add(tab);
			
			tab = loadImportExport();
			if(tab != null)
				tabPane.getTabs().add(tab);
		}
		
		mainContainer.getChildren().add(tabPane);
	}

	private Tab loadListeCourse() {
		Tab tab = null;
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MyApp.class.getClassLoader().getResource("views/FXML/ListeCourse.fxml"));
			//loader.setController(new ListeCourseControllerFXML());
			GridPane content;
			content = (GridPane)loader.load();
			tab = new Tab();
			tab.setClosable(false);
            tab.setText("Courses");
            tab.setContent(content);
		} catch (IOException e) {//TODO
			e.printStackTrace();
		}
		return tab;
	}
	
	private Tab loadListeAppelant() {
		Tab tab = null;
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MyApp.class.getClassLoader().getResource("views/FXML/ListeAppelant.fxml"));
			loader.setController(new ListeAppelantControllerFXML());
			GridPane content;
			content = (GridPane)loader.load();
			tab = new Tab();
			tab.setClosable(false);
            tab.setText("Appelant");
            tab.setContent(content);
		} catch (IOException e) {//TODO
			e.printStackTrace();
		}
		return tab;
	}
	
	private Tab loadListUser() {
		Tab tab = null;
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MyApp.class.getClassLoader().getResource("views/FXML/ListUser.fxml"));
			loader.setController(new ListUserControllerFXML());
			GridPane content;
			content = (GridPane)loader.load();
			tab = new Tab();
			tab.setClosable(false);
            tab.setText("Permanents");
            tab.setContent(content);
		} catch (IOException e) {//TODO
			e.printStackTrace();
		}
		return tab;
	}

	private Tab loadImportExport() {
		Tab tab = null;
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MyApp.class.getClassLoader().getResource("views/FXML/ImportExport.fxml"));
			loader.setController(new ImportExportControllerFXML());
			GridPane content;
			content = (GridPane)loader.load();
			tab = new Tab();
			tab.setClosable(false);
            tab.setText("Import/Export");
            tab.setContent(content);
		} catch (IOException e) {//TODO
			e.printStackTrace();
		}
		return tab;
	}

}
