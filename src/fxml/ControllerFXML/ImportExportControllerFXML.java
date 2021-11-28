package fxml.ControllerFXML;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import controllers.ImportExportController;
import controllers.MainController;
import data.SaveManager;
import fxml.Message;
import fxml.dialog.DialogSelectIntervalControllerFXML;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.GridPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import models.Indisponibilite;
import models.Utilisateur;

public class ImportExportControllerFXML extends ImportExportController implements Initializable,ITabController {

	@FXML
    private Button importer;
    @FXML
    private Button exporter;
    @FXML
    private Button exporterInterval;
    @FXML
    private Button changeD;
    @FXML
    private Button export;
    
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
		changeD.setOnAction((ActionEvent e) -> changeSaveDirectoryF());
		export.setOnAction((ActionEvent e) -> exportF());
		exporterInterval.setOnAction((ActionEvent e) -> saveIntervalF());
	}
	
	private void exportF() {
		try {
			if (super.export()) {
				Message.msg("Exportation effectuée");
			} else {
				Message.alert("Exportation effectuée\nMais une erreur s'est produite!");
			}
		} catch (Exception e) {
			Message.alert("Erreur lors de l'exportation");
		}
	}
	
	private void saveF() {
		try {
			if (super.save()) {
				Message.msg("Sauvegarde effectuée");
			} else {
				Message.alert("Sauvegarde effectuée\nMais une erreur s'est produite!");
			}
		} catch (Exception e) {
			Message.alert("Erreur lors de la sauvagarde");
		}
	}
	
	private void saveIntervalF() {
		DialogSelectIntervalControllerFXML dialog = new DialogSelectIntervalControllerFXML();
		Indisponibilite i = dialog.showAndWait();
		if (dialog.isResult()) {
			LocalDate start = i.getDateStart();
			LocalDate end = i.getDateEnd();
			try {
				if (super.saveInterval(start, end)) {
					Message.msg("Sauvegarde effectuée");
				} else {
					Message.alert("Sauvegarde effectuée\nMais une erreur s'est produite!");
				}
			} catch (Exception e) {
				Message.alert("Erreur lors de la sauvagarde");
			}
		}
	}

	
	private void changeSaveDirectoryF() {
		DirectoryChooser dc = new DirectoryChooser();
		dc.setInitialDirectory(getFileSaveDirectory());
		dc.setTitle("Choose Save Directory");
		
		File file = dc.showDialog((Stage)importer.getScene().getWindow());
		
		if (file!=null && file.isDirectory() && Message.comfirmation("Importation", "Le dossier de sauvegarde sera modifié !")) {
			changeSaveDirectory(file);
		}
	}
	
	/* 
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
	 * */
	
	private void importer() {
		String extention = SaveManager.getExtention();
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Save File (*"+extention+")", "*"+extention);
		FileChooser fileChooser = new FileChooser();
		fileChooser.setInitialDirectory(getFileSaveDirectory());
		fileChooser.setTitle("Choose Save File");
		fileChooser.getExtensionFilters().add(extFilter);
		File file = fileChooser.showOpenDialog((Stage)importer.getScene().getWindow());
		try {
			if (file!=null && Message.comfirmation("Importation", "Les anciennes données seront remplacé par de nouvelles!")) {
				importer(file);
			}
		} catch (Exception e) {
			Message.alert("Erreur lors de l'importation");
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

	@Override
	public ITabController select(Long id) {
		throw new UnsupportedOperationException();
	}
	@Override
	public void action(String action) {
		throw new UnsupportedOperationException();
	}
}