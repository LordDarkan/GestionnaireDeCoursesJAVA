package views.FXML.ControllerFXML;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import controllers.ImportExportController;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import models.Utilisateur;

public class ImportExportControllerFXML extends ImportExportController implements Initializable,ITabController {

	@FXML
    private Button importer;

    @FXML
    private Button exporter;
    
    private Tab tab;
	
	public ImportExportControllerFXML(Utilisateur user, TabPane tabContainer) {
		super(user);
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getClassLoader().getResource("views/FXML/ImportExport.fxml"));
			loader.setController(this);
			GridPane content;
			content = (GridPane)loader.load();
			tab = new Tab();
			tab.setClosable(false);
            tab.setText("Import/Export");
            tab.setContent(content);
            tab.setDisable(!isAdmin());
            tab.setOnSelectionChanged(new EventHandler<Event>() {
                @Override
                public void handle(Event event) {
                	if (((Tab)event.getSource()).isSelected()) {
                		//selected();
					}
                }
            });
            
            tabContainer.getTabs().add(tab);
		} catch (IOException e) {//TODO
			e.printStackTrace();
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		importer.setOnAction((ActionEvent e) -> importer());

	}
	
	private void importer() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open Resource File");
		File file = fileChooser.showOpenDialog((Stage)importer.getScene().getWindow());
		
		if (file!=null) {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Confirmation");
			alert.setHeaderText("Importation");
			alert.setContentText("Les anciennes données seront remplacé par les nouvelles!");
			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK){
				importer(file);
			}
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