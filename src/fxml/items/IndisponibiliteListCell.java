package fxml.items;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.AnchorPane;
import models.Indisponibilite;
import util.DateTime;

public class IndisponibiliteListCell extends ListCell<Indisponibilite> {
	private AnchorPane content;
	@FXML
    private Label heureStart;

    @FXML
    private Label heureEnd;

    @FXML
    private Label titre;

    @FXML
    private Label description;
    
    public IndisponibiliteListCell() {
    	FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("IndisponibiliteItem.fxml"));
	    fxmlLoader.setController(this);
	    try
	    {
	    	content = (AnchorPane)fxmlLoader.load();
	    }
	    catch (IOException e)
	    {
	        throw new RuntimeException(e);
	    }
    }
    
    @Override 
    protected void updateItem(Indisponibilite item, boolean empty) {
        super.updateItem(item, empty);
        setStyle("-fx-background-color:white");
        setGraphic(null); 
        setText(null); 
        setContentDisplay(ContentDisplay.LEFT); 
        if (!empty && item != null) {
        	heureStart.setText(DateTime.toString(item.getHeureStart()));
        	heureEnd.setText(DateTime.toString(item.getHeureEnd()));
        	titre.setText(item.getTitre());
        	description.setText(item.getDescription());
            setText(null); 
            setGraphic(content); 
            setContentDisplay(ContentDisplay.GRAPHIC_ONLY); 
        } 
    }
}
