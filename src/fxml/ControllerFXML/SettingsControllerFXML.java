package fxml.ControllerFXML;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import controllers.SettingsContreller;
import fxml.items.UserListCell;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import models.Hopital;
import models.Residence;
import models.Utilisateur;

public class SettingsControllerFXML extends SettingsContreller implements Initializable,ITabController {
	@FXML
    private Button addUser;
    @FXML
    private Button delUser;
    @FXML
    private TextField firstname;
    @FXML
    private TextField name;
    @FXML
    private ListView<Utilisateur> listViewUser;
    @FXML
    private Button addHop;
    @FXML
    private Button delHop;
    @FXML
    private TextField telHop;
    @FXML
    private TextField localiteHop;
    @FXML
    private TextField cpHop;
    @FXML
    private TextField adresseHop;
    @FXML
    private TextField nameHop;
    @FXML
    private ListView<Hopital> listViewHop;
    @FXML
    private Button addRes;
    @FXML
    private Button delRes;
    @FXML
    private TextField telRes;
    @FXML
    private TextField localiteRes;
    @FXML
    private TextField cpRes;
    @FXML
    private TextField adresseRes;
    @FXML
    private TextField nameRes;
    @FXML
    private ListView<Residence> listViewRes;
    
    private Tab tab;
    
	public SettingsControllerFXML(Utilisateur user, TabPane tabContainer) {
		super(user);
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getClassLoader().getResource("fxml/views/Settings.fxml"));
			loader.setController(this);
			VBox content = (VBox)loader.load();
			tab = new Tab();
			tab.setClosable(false);
            tab.setText("Administrateur");
            tab.setContent(content);
            /*tab.setOnSelectionChanged(new EventHandler<Event>() {
                @Override
                public void handle(Event event) {
                	if (((Tab)event.getSource()).isSelected()) {
                		//selected();
					}
                }
            });*/
            tab.setDisable(!isAdmin());
            tabContainer.getTabs().add(tab);
		} catch (IOException e) {//TODO
			e.printStackTrace();
		}
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		listViewUser.setCellFactory(lc -> new UserListCell());
		addUser.setOnAction((ActionEvent e) -> addUser());
		addHop.setOnAction((ActionEvent e) -> addHop());
		addRes.setOnAction((ActionEvent e) -> addRes());
		delUser.setOnAction((ActionEvent e) -> delUser());
		delHop.setOnAction((ActionEvent e) -> delHop());
		delRes.setOnAction((ActionEvent e) -> delRes());
		
		
		
		if (isAdmin()) {
			setListUser(getListUser());
			setListHopital(getListHopital());
			setListResidence(getListResidence());
		}
	}
	
	private void addUser() {
		Utilisateur user = new Utilisateur();
		user.setName(name.getText().trim());
		user.setFirstname(firstname.getText().trim());
		name.setText("");
		firstname.setText("");
		super.addUser(user);
		setListUser(getListUser());
	}
	
	private void addHop() {
		Hopital hop = new Hopital();
		hop.setName(nameHop.getText().trim());
		hop.setAdresse(adresseHop.getText().trim());
		hop.setLocalite(localiteHop.getText().trim());
		hop.setCp(cpHop.getText().trim());
		hop.setTel(telHop.getText().trim());
		
		nameHop.setText("");
		adresseHop.setText("");
		localiteHop.setText("");
		cpHop.setText("");
		telHop.setText("");
		
		try {
			super.addHopital(hop);
			setListHopital(getListHopital());
		} catch (Exception e) {
		}
	}
	
	private void addRes() {
		Residence res = new Residence();
		
		res.setName(nameRes.getText().trim());
		res.setAdresse(adresseRes.getText().trim());
		res.setLocalite(localiteRes.getText().trim());
		res.setCp(cpRes.getText().trim());
		res.setTel(telRes.getText().trim());
		
		nameRes.setText("");
		adresseRes.setText("");
		localiteRes.setText("");
		cpRes.setText("");
		telRes.setText("");
		
		try {
			super.addResidence(res);
			setListResidence(getListResidence());
		} catch (Exception e) {
		}
	}
	
	private void delUser() {
		Utilisateur selected = listViewUser.getSelectionModel().getSelectedItem();
		if (selected != null) {
			super.removeUser(selected);
			setListUser(getListUser());
		}
	}
	
	private void delHop() {
		Hopital selected = listViewHop.getSelectionModel().getSelectedItem();
		if (selected != null) {
			super.removeHopital(selected);
			setListHopital(getListHopital());
		}
	}
	
	private void delRes() {
		Residence selected = listViewRes.getSelectionModel().getSelectedItem();
		if (selected != null) {
			super.removeResidence(selected);
			setListResidence(getListResidence());
		}
	}
	
	private void setListUser(List<Utilisateur> users) {
		listViewUser.getItems().clear();
		listViewUser.getItems().setAll(users);
	}
	
	private void setListHopital(List<Hopital> hopitaux) {
		listViewHop.getItems().clear();
		listViewHop.getItems().setAll(hopitaux);
	}
	
	private void setListResidence(List<Residence> residences) {
		listViewRes.getItems().clear();
		listViewRes.getItems().setAll(residences);
	}
	
	@Override
	public void logout(){
		listViewUser.getItems().clear();
		listViewHop.getItems().clear();
		listViewRes.getItems().clear();
		clear();
	}

	@Override
	public void login(Utilisateur user) {
		newLog(user);
		tab.setDisable(!isAdmin());
		if (isAdmin()) {
			setListUser(getListUser());
			setListHopital(getListHopital());
			setListResidence(getListResidence());
		}
	}

	@Override
	public void select(Long id) {
		throw new UnsupportedOperationException();
	}
}
