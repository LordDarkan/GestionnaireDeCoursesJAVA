package views.FXML.items;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.AnchorPane;
import models.Chauffeur;
import models.item.ChauffeurList;
import models.item.CourseList;

public class ChauffeurListListCell extends ListCell<ChauffeurList> {
	@FXML
    private AnchorPane userContent;
    @FXML
    private Label nom;
    @FXML
    private Label prenom;
    
    public ChauffeurListListCell() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("nomPrenomItem.fxml"));
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
    protected void updateItem(ChauffeurList item, boolean empty) {
        super.updateItem(item, empty);
        setGraphic(null); 
        setText(null); 
        setContentDisplay(ContentDisplay.LEFT); 
        if (!empty && item != null) {
        	nom.setText(item.getFullName());
            setText(null); 
            setGraphic(userContent); 
            //setContentDisplay(ContentDisplay.GRAPHIC_ONLY); 
        } 
    }
}
