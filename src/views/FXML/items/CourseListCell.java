package views.FXML.items;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.AnchorPane;
import models.list.CourseList;

public class CourseListCell extends ListCell<CourseList> {
	@FXML
    private AnchorPane userContent;
    @FXML
    private Label nom;
    @FXML
    private Label prenom;
    
    public CourseListCell() {
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
    protected void updateItem(CourseList item, boolean empty) {
        super.updateItem(item, empty);
        setGraphic(null); 
        setText(null); 
        setContentDisplay(ContentDisplay.LEFT); 
        if (!empty && item != null) {
        	nom.setText(item.getAppelant());
        	prenom.setText(item.getAdresseDest());
            setText(null); 
            setGraphic(userContent); 
            //setContentDisplay(ContentDisplay.GRAPHIC_ONLY); 
        } 
    }
}
