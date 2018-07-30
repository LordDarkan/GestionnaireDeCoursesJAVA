package views.FXML.ControllerFXML;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import controllers.ListUserContreller;
import data.Mapper;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.GridPane;
import models.Utilisateur;
import views.FXML.items.UserListCell;

public class ListUserControllerFXML extends ListUserContreller implements Initializable,ITabController {
	@FXML
    private ListView<Utilisateur> listUserView;

    @FXML
    private Button listUserBtnAdd;
    
    private Tab tab;
    
	public ListUserControllerFXML(Utilisateur user, TabPane tabContainer) {
		super(user);
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getClassLoader().getResource("views/FXML/ListUser.fxml"));
			loader.setController(this);
			GridPane content;
			content = (GridPane)loader.load();
			tab = new Tab();
			tab.setClosable(false);
            tab.setText("Utilisateur");
            tab.setContent(content);
            tab.setOnSelectionChanged(new EventHandler<Event>() {
                @Override
                public void handle(Event event) {
                	if (((Tab)event.getSource()).isSelected()) {
                		//selected();
					}
                }
            });
            
            tabContainer.getTabs().add(tab);
		} catch (IOException e) {//TODO
			e.printStackTrace();
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		listUserView.setCellFactory(lc -> new UserListCell());
		listUserView.getItems().setAll(Mapper.getInstance().getAllUser());
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void logout(){
		try {
			//Event.fireEvent(tab, new Event(Tab.CLOSED_EVENT));
			finalize();
		} catch (Throwable e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void login(Utilisateur user) {
		// TODO Auto-generated method stub
		
	}
}
