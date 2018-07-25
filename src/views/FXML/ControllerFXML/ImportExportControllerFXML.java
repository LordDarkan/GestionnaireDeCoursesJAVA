package views.FXML.ControllerFXML;

import java.io.File;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import controllers.ImportExportController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class ImportExportControllerFXML extends ImportExportController implements Initializable {

	@FXML
    private Button importer;

    @FXML
    private Button exporter;
	
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

}