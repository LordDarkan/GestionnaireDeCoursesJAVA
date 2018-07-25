package views.FXML.items;

import java.io.IOException;
import java.util.Optional;

import controllers.MainController;
import data.Mapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.AnchorPane;
import models.Utilisateur;
import util.Role;

public class UserListCell extends ListCell<Utilisateur> {
	@FXML
    private AnchorPane userContent;
	@FXML
    private CheckBox isAdminCb;
    @FXML
    private Label labelUser;
	
	public UserListCell() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("UserItem.fxml"));
        fxmlLoader.setController(this);
        try
        {
            fxmlLoader.load();
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
        
    }
	
	private void updateUser(Utilisateur user) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Confirmation");
		alert.setHeaderText("Modification de rôle!");
		if (isAdminCb.isSelected()) {
			alert.setContentText(user.getFullName()+" aura le rôle d'administrateur!");
		} else {
			alert.setContentText(user.getFullName()+" ne sera plus administrateur!");
		}
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK){
			Mapper.getInstance().setAdminRole(user.getId(),isAdminCb.isSelected());
		} else {
			isAdminCb.setSelected(!isAdminCb.isSelected());
		}
	}

	@Override 
    protected void updateItem(Utilisateur user, boolean empty) {
        super.updateItem(user, empty);
        
        setGraphic(null); 
        setText(null); 
        setContentDisplay(ContentDisplay.LEFT); 
        if (!empty && user != null) {
        	if(MainController.isAdminMode()) {
	        	isAdminCb.setSelected(user.getRole()==Role.ADMIN);
	    		isAdminCb.setOnAction((ActionEvent e) -> updateUser(user));
	        } else {
	        	isAdminCb.setVisible(false);
	        } 
        	labelUser.setText(user.getFullName());
            setText(null); 
            setGraphic(userContent); 
            //setContentDisplay(ContentDisplay.GRAPHIC_ONLY); 
        } 
    }
}
