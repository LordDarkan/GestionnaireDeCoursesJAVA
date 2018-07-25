package views.FXML.ControllerFXML;

import java.net.URL;
import java.util.ResourceBundle;

import controllers.LoginController;
import controllers.MainController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;

public class LoginControllerFXML extends LoginController implements Initializable {

	@SuppressWarnings("rawtypes")
	@FXML
    private ChoiceBox loginCbName;
	
	@FXML
    private Button loginBtn;

    @FXML
    private TextField loginTxt;
	
	public LoginControllerFXML(MainController mainController) {
		super(mainController);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		ObservableList<Object> obs = FXCollections.observableArrayList();
		obs.addAll(getListUser());
		obs.add(new Separator());
		obs.add("Autres");
		loginCbName.setItems(obs);
		loginCbName.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
			@SuppressWarnings("rawtypes")
			public void changed(ObservableValue ov, Number value, Number newValue) {
				selectedUser(newValue);
			}
		});
		
		loginBtn.setOnAction((ActionEvent e) -> connect());
		
		loginTxt.setVisible(false);
	}
	
	private void selectedUser(Number newValue) {
		boolean showField = selectedValue(newValue);
		loginTxt.setVisible(showField);
	}
	
	private void connect() {
		try {
			super.connect(loginTxt.getText());
		} catch (IllegalArgumentException e) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("ERREUR");
			alert.setHeaderText(null);
			alert.setContentText(e.getMessage());
			alert.showAndWait();
		}
	}

}
