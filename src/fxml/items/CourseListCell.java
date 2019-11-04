package fxml.items;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.AnchorPane;
import models.itemList.CourseItemList;
import util.DateTime;

public class CourseListCell extends ListCell<CourseItemList> {
	@FXML
    private AnchorPane content;
    @FXML
    private Label heure;
    @FXML
    private Label date;
    //@FXML
    //private Label cp;
    @FXML
    private Label localite;
    @FXML
    private Label adresse;
    @FXML
    private Label type;
    @FXML
    private Label fullNameAppelant;
    @FXML
    private Label hopital;

    
    public CourseListCell() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("CourseItem.fxml"));
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
    protected void updateItem(CourseItemList item, boolean empty) {
        super.updateItem(item, empty);
        setGraphic(null); 
        setText(null); 
        setContentDisplay(ContentDisplay.LEFT); 
        if (!empty && item != null) {
        	fullNameAppelant.setText(item.getAppelant());
        	type.setText(item.getType().toString());
        	adresse.setText(item.getAdresseDest());
        	//cp.setText(item.getCpDest());
        	localite.setText(item.getLocaliteDest());
        	heure.setText(DateTime.toString(item.getHeure()));
        	date.setText(DateTime.toString(item.getDate()));
        	hopital.setText(item.getHopital());
            setText(null); 
            setGraphic(content); 
            //setContentDisplay(ContentDisplay.GRAPHIC_ONLY); 
        } 
    }
}
