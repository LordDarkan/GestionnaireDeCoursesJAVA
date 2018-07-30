package views.FXML.ControllerFXML;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import controllers.ListUserContreller;
import data.Mapper;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import models.Utilisateur;
import views.FXML.items.UserListCell;

public class ListUserControllerFXML extends ListUserContreller implements Initializable,ITabController {
	@FXML
    private ListView<Utilisateur> listUserView;

    @FXML
    private Button add;

    @FXML
    private TextField name;

    @FXML
    private TextField firstname;

    @FXML
    private CheckBox admin;
    
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
            tab.setDisable(!isAdmin());
            tabContainer.getTabs().add(tab);
		} catch (IOException e) {//TODO
			e.printStackTrace();
		}
		add.setOnAction((ActionEvent e) -> add());
	}

	

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		listUserView.setCellFactory(lc -> new UserListCell());
		setListUser(getListUser());
	}
	
	private void add() {
		Utilisateur user = new Utilisateur();
		user.setName(name.getText().trim());
		user.setFirstname(firstname.getText().trim());
		user.setAdmin(admin.isSelected());
		name.setText("");
		firstname.setText("");
		admin.setSelected(false);
		super.add(user);
		setListUser(getListUser());
	}
	
	private void setListUser(List<Utilisateur> users) {
		listUserView.getItems().clear();
		listUserView.getItems().setAll(users);
	}
	
	
	@Override
	public void logout(){
		clear();
	}

	@Override
	public void login(Utilisateur user) {
		newLog(user);
		tab.setDisable(!isAdmin());
		if (isAdmin()) {
			listUserView.getItems().setAll(Mapper.getInstance().getAllUser());
		}
	}
}
