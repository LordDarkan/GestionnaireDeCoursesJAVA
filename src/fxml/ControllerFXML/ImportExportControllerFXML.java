package fxml.ControllerFXML;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import controllers.ImportExportController;
import controllers.MainController;
import data.SaveManager;
import fxml.Message;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import models.Utilisateur;

public class ImportExportControllerFXML extends ImportExportController implements Initializable,ITabController {

	@FXML
    private Button importer;

    @FXML
    private Button exporter;
    
    @FXML
    private Button importerOld;
    
    private Tab tab;
	
	public ImportExportControllerFXML(Utilisateur user, TabPane tabContainer,MainController main) {
		super(user,main);
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getClassLoader().getResource("fxml/views/ImportExport.fxml"));
			loader.setController(this);
			GridPane content;
			content = (GridPane)loader.load();
			tab = new Tab();
			tab.setClosable(false);
            tab.setText("Import/Export");
            tab.setContent(content);
            tab.setDisable(!isAdmin());
            /*tab.setOnSelectionChanged(new EventHandler<Event>() {
                @Override
                public void handle(Event event) {
                	if (((Tab)event.getSource()).isSelected()) {
                		//selected();
					}
                }
            });*/
            
            tabContainer.getTabs().add(tab);
		} catch (IOException e) {//TODO
			e.printStackTrace();
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		importer.setOnAction((ActionEvent e) -> importer());
		exporter.setOnAction((ActionEvent e) -> saveF());
		importerOld.setOnAction((ActionEvent e) -> importerOld());
	}
	
	private void saveF() {
		try {
			super.save();
			Message.msg("Sauvegarde effectuée");
		} catch (Exception e) {
			Message.alert("Erreur lors de la sauvagarde");
		}
	}

	private void importerOld() {
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Save File (*.csv)", "*.csv");
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Choose Save File");
		fileChooser.getExtensionFilters().add(extFilter);
		File file = fileChooser.showOpenDialog((Stage)importer.getScene().getWindow());
		
		if (file!=null && Message.comfirmation("Importation", "Les anciennes données seront remplacé par de nouvelles!")) {
			importerOld(file);
		}
	}
	
	private void importer() {
		String extention = SaveManager.getExtention();
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Save File (*"+extention+")", "*"+extention);
		FileChooser fileChooser = new FileChooser();
		fileChooser.setInitialDirectory(getFileSaveDirectory());
		fileChooser.setTitle("Choose Save File");
		fileChooser.getExtensionFilters().add(extFilter);
		File file = fileChooser.showOpenDialog((Stage)importer.getScene().getWindow());
		
		if (file!=null && Message.comfirmation("Importation", "Les anciennes données seront remplacé par de nouvelles!")) {
			importer(file);
		}
	}
	
	
	@Override
	public void logout(){
		clear();
	}

	@Override
	public void login(Utilisateur user) {
		newLog(user);
		tab.setDisable(!isAdmin());
	}
}