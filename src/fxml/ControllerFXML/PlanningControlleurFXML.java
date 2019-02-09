package fxml.ControllerFXML;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Collection;
import java.util.ResourceBundle;

import controllers.PlanningControlleur;
import fxml.items.PlanningChauffeurListCell;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.GridPane;
import models.Utilisateur;
import models.itemList.PlanningChauffeur;

public class PlanningControlleurFXML extends PlanningControlleur implements Initializable,ITabController {

	@FXML
    private DatePicker selectedDate;

    @FXML
    private Label date;

    @FXML
    private ListView<PlanningChauffeur> listViewChauf;
    
    private MainControllerFXML main;
	
	protected PlanningControlleurFXML(MainControllerFXML main,Utilisateur user, TabPane tabContainer) {
		super(user);
		this.main = main;
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getClassLoader().getResource("fxml/views/Planning.fxml"));
			loader.setController(this);
			GridPane content = (GridPane)loader.load();
			Tab tab = new Tab();
			tab.setClosable(false);
            tab.setText("Planning");
            tab.setContent(content);
            tab.setOnSelectionChanged(new EventHandler<Event>() {
                @Override
                public void handle(Event event) {
                	if (((Tab)event.getSource()).isSelected()) {
                		select();
					}
                }
            });
            tabContainer.getTabs().add(tab);
		} catch (IOException e) {//TODO
			e.printStackTrace();
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		listViewChauf.setFocusTraversable(false);
		listViewChauf.setCellFactory(lc -> new PlanningChauffeurListCell(main));
		selectedDate.setValue(LocalDate.now());
		selectedDate.valueProperty().addListener((ov, oldValue, newValue) -> {
			if (newValue != null) {
         	   select();
            }
        });
	}

	@Override
	public void logout() {
		clear();
	}

	@Override
	public void login(Utilisateur user) {
		newLog(user);
		//selectedDate.setValue(LocalDate.now());
		select();
	}
	
	private void setListPlanningChauffeur(Collection<PlanningChauffeur> collection) {
		listViewChauf.getItems().clear();
		listViewChauf.getItems().setAll(collection);
	}
	
	private void select() {
		setListPlanningChauffeur(getListPlanningChauffeur(selectedDate.getValue()));
	}

	@Override
	public void select(Long id) {
		throw new UnsupportedOperationException();
	}
}
