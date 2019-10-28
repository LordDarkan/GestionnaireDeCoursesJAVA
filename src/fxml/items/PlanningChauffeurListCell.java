package fxml.items;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import data.Mapper;
import fxml.Message;
import fxml.ControllerFXML.MainControllerFXML;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import models.Indisponibilite;
import models.itemList.PlanningChauffeur;
import util.TypeIndisponibilite;

public class PlanningChauffeurListCell extends ListCell<PlanningChauffeur> implements Initializable {
	private AnchorPane content;
	@FXML
    private Label nomChauffeur;
    @FXML
    private ListView<Indisponibilite> listViewPlanning;
    
    private MainControllerFXML main;
    
    public PlanningChauffeurListCell(MainControllerFXML main) {
		this.main = main;
    	FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("PlanningChauffeurItem.fxml"));
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
	public void initialize(URL arg0, ResourceBundle arg1) {
		//listViewPlanning.setMouseTransparent(false);
		listViewPlanning.setFocusTraversable(false);
		listViewPlanning.setCellFactory(lc -> new IndisponibiliteListCell());
		
		listViewPlanning.setOnMouseClicked(new EventHandler<MouseEvent>() {
		    @Override
		    public void handle(MouseEvent click) {
		        if (click.getClickCount() == 2) {
		        	Indisponibilite item = listViewPlanning.getSelectionModel().getSelectedItem();
		        	
		        	if(item != null) {
		        		if (item.getType() == TypeIndisponibilite.COURSE) {
		        			main.selectCourse(item.getId());
						} else if(Message.comfirmation("Suppression Indisponibilité", "Supprimer L'indisponibilité de "+nomChauffeur.getText()+" ?\n Attention L'indisponibilité peux s'étendre sur plusieurs jours!")) {
							Mapper.getInstance().delete(item);
						}
		        	}
		        }
		    }
		});
	}
	
    @Override 
    protected void updateItem(PlanningChauffeur item, boolean empty) {
        super.updateItem(item, empty);
        setStyle("-fx-padding: 0px;-fx-background-color:white;");
        setGraphic(null); 
        setText(null); 
        setContentDisplay(ContentDisplay.LEFT); 
        if (!empty && item != null) {
        	nomChauffeur.setText(item.getChauffeurName());
        	listViewPlanning.getItems().clear();
        	listViewPlanning.getItems().setAll(item.getIndisponibilite());
            setText(null); 
            setGraphic(content); 
            setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        } 
    }
}
