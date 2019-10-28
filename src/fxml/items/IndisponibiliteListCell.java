package fxml.items;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import models.Indisponibilite;
import util.DateTime;
import util.Trajet;
import util.TypeIndisponibilite;

public class IndisponibiliteListCell extends ListCell<Indisponibilite> {
	private AnchorPane content;
	
	@FXML
    private VBox vBox;
    
	@FXML
    private Label heureStart;

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
        	String str = DateTime.toString(item.getHeureStart());
        	titre.setText(item.getTitre());
        	description.setText(item.getDescription());
        	
        	if (item.getType() == TypeIndisponibilite.COURSE) {
        		Trajet mode = item.getModeCourse();
        		if (mode == Trajet.ALLER) {
        			vBox.setStyle("-fx-border-color: black;-fx-background-color:#C2FF80;");
				} else if (mode == Trajet.RETOUR) {
					vBox.setStyle("-fx-border-color: black;-fx-background-color:#80B6FF;");
				} else {
					vBox.setStyle("-fx-border-color: black;-fx-background-color:white;");
				}
        		str+=" "+item.getModeCourse().toString();
        	} else {
        		vBox.setStyle("-fx-border-color: black;-fx-background-color:#DD0000;");
        		str+=" - "+DateTime.toString(item.getHeureEnd());
        	}
        	
        	heureStart.setText(str);
            setText(null); 
            setGraphic(content); 
            setContentDisplay(ContentDisplay.GRAPHIC_ONLY); 
        } 
    }
}
