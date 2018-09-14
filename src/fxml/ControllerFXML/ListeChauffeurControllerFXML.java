package fxml.ControllerFXML;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import controllers.ListeChauffeurController;
import fxml.Message;
import fxml.dialog.DialogIndisponibiliteControllerFXML;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import models.Chauffeur;
import models.Indisponibilite;
import models.Utilisateur;
import models.itemList.ChauffeurItemList;
import models.itemList.PlanningChauffeur;
import util.Security;

public class ListeChauffeurControllerFXML extends ListeChauffeurController implements Initializable,ITabController {
	@FXML
    private Button btnAdd;
    @FXML
    private TextField recherche;
    @FXML
    private ListView<ChauffeurItemList> listViewChauffeur;
    @FXML
    private Button btnDelete;
    @FXML
    private Button btnEdit;
    @FXML
    private Button btnAnnuler;
    @FXML
    private Button btnSave;
    @FXML
    private TextField adresse;
    @FXML
    private TextField cp;
    @FXML
    private TextField localite;
    @FXML
    private TextArea infos;
    @FXML
    private TextField nom;
    @FXML
    private TextField prenom;
    @FXML
    private TextField tel;
    @FXML
    private ListView<PlanningChauffeur> listeViewPlanning;//TODO
    @FXML
    private Button btnIndisponibilite;

    public ListeChauffeurControllerFXML(Utilisateur user, TabPane tabContainer) {
    	super(user);
    	Tab tab = null;
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getClassLoader().getResource("fxml/views/ListeChauffeur.fxml"));
			loader.setController(this);
			VBox content;
			content = (VBox)loader.load();
			tab = new Tab();
			tab.setClosable(false);
            tab.setText("Chauffeur");
            tab.setContent(content);
            tab.setOnSelectionChanged(new EventHandler<Event>() {
                @Override
                public void handle(Event event) {
                	if (((Tab)event.getSource()).isSelected()) {
                		selected();
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
		listViewChauffeur.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ChauffeurItemList>() {
		    @Override
		    public void changed(ObservableValue<? extends ChauffeurItemList> observable, ChauffeurItemList oldValue, ChauffeurItemList newValue) {
		    	if(newValue!=null) {
		    		setSelectedChauffeur(newValue.getId());
		    		showChauffeur(getSelectedChauffeur());
		    	}
		    }
		});
		
		recherche.textProperty().addListener((observable, oldValue, newValue) -> {
			setListeChauffeur(search(newValue));
		});
		
		cp.textProperty().addListener((observable, oldValue, newValue) -> {
			if (!newValue.matches("\\d*")) {
				cp.setText(newValue.replaceAll("[^\\d]", ""));
	        } else if(newValue.length()>4) {
	        	cp.setText(oldValue);
	        }
		});
		
		
		btnAdd.setOnAction((ActionEvent e) -> addF());
		btnEdit.setOnAction((ActionEvent e) -> editerF());
		btnAnnuler.setOnAction((ActionEvent e) -> annulerF());
		btnDelete.setOnAction((ActionEvent e) -> deleteF());
		btnSave.setOnAction((ActionEvent e) -> saveF());
		btnIndisponibilite.setOnAction((ActionEvent e) -> newIndisponibiliteF());
		editMode(false);
		setListeChauffeur(search(""));
	}
	
	private void newIndisponibiliteF() {
		if (isSelected()) {//TODO gestion mémoire
			DialogIndisponibiliteControllerFXML dialog = new DialogIndisponibiliteControllerFXML();
			Indisponibilite i = dialog.showAndWait();
			if (dialog.isResult()) {
				super.newIndisponibilite(i);
			}
		}
	}

	private void addF() {
		showChauffeur(new Chauffeur());
		editMode(true);
	}

	private void saveF() {
		try {
			Chauffeur chauf = getInfoChauffeur();
			Chauffeur.valdation(chauf);
			if(Message.comfirmation("Sauvegarder",null)) {
				super.save(chauf);
				editMode(false);
				showChauffeur(getSelectedChauffeur());
				setListeChauffeur(search(null));
			}
		} catch (Exception e) {
			Message.alert(e.getMessage());
		}
	}

	private Chauffeur getInfoChauffeur() {
		Chauffeur app = new Chauffeur();
		app.setName(nom.getText().trim());
		app.setFirstname(prenom.getText().trim());
		app.setAdresse(adresse.getText().trim());
		app.setCp(cp.getText().trim());
		app.setLocalite(localite.getText().trim());
		app.setTel(tel.getText().trim());
		
		app.setInfos(infos.getText().trim());
		return app;
	}

	private void deleteF() {
		if (isSelected() && Message.comfirmation("Supprimer","")) {
			super.delete();
			showChauffeur(getSelectedChauffeur());
			setListeChauffeur(search(null));
		}
	}

	private void annulerF() {
		editMode(false);
		showChauffeur(getSelectedChauffeur());
	}

	private void editerF() {
		if(isSelected()) {
			editMode(true);
		}
	}

	private void editMode(boolean b) {
		btnIndisponibilite.setVisible(!b);
		b &= isAdmin();
		
		btnAdd.setVisible(!b && isAdmin());
		btnEdit.setVisible(!b && isAdmin());
		btnDelete.setVisible(Security.isDelOk() && !b && isAdmin());
		btnAnnuler.setVisible(b);
		btnSave.setVisible(b);
		
		listViewChauffeur.setDisable(b);
		
		nom.setEditable(b);
		prenom.setEditable(b);
		
		adresse.setEditable(b);
		cp.setEditable(b);
		localite.setEditable(b);
		tel.setEditable(b);
		infos.setEditable(b);
	}
	
	private void showChauffeur(Chauffeur app) {
		editMode(false);
		
		nom.setText(app.getName());
		prenom.setText(app.getFirstname());
		adresse.setText(app.getAdresse());
		cp.setText(app.getCp());
		localite.setText(app.getLocalite());
		tel.setText(app.getTel());
		infos.setText(app.getInfos());
	}
	
	private void setListeChauffeur(List<ChauffeurItemList> chauf) {
		listViewChauffeur.getItems().clear();
		listViewChauffeur.getItems().setAll(chauf);
	}
	
	@Override
	public void logout(){
		showChauffeur(new Chauffeur());
		recherche.setText("");
		clear();
	}

	@Override
	public void login(Utilisateur user) {
		newLog(user);
		editMode(false);
		setListeChauffeur(search(""));
	}
}
