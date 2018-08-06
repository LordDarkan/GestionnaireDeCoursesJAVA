package fxml.items;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.AnchorPane;
import models.Appelant;

public class AppelantListCell extends ListCell<Appelant> {
	@FXML
    private AnchorPane userContent;
    @FXML
    private Label code;
    @FXML
    private Label nom;
    @FXML
    private Label prenom;
    
    public AppelantListCell() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AppelantItem.fxml"));
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
    
    @Override 
    protected void updateItem(Appelant app, boolean empty) {
        super.updateItem(app, empty);
        setGraphic(null); 
        setText(null); 
        setContentDisplay(ContentDisplay.LEFT); 
        if (!empty && app != null) {
        	code.setText(""+app.getId());
        	nom.setText(app.getName());
        	prenom.setText(app.getFirstname());
            setText(null); 
            setGraphic(userContent); 
            //setContentDisplay(ContentDisplay.GRAPHIC_ONLY); 
        } 
    }
}
