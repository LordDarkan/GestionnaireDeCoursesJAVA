package views.FXML.ControllerFXML;

import java.net.URL;
import java.util.ResourceBundle;

import controllers.ListUserContreller;
import data.Mapper;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import models.Utilisateur;
import views.FXML.items.UserListCell;

public class ListUserControllerFXML extends ListUserContreller implements Initializable {
	@FXML
    private ListView<Utilisateur> listUserView;

    @FXML
    private Button listUserBtnAdd;
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		listUserView.setCellFactory(lc -> new UserListCell());
		listUserView.getItems().setAll(Mapper.getInstance().getAllUser());
	}
}
