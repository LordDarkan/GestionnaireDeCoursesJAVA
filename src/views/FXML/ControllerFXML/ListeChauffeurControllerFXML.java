package views.FXML.ControllerFXML;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import controllers.ListeChauffeurController;
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
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import models.Chauffeur;
import models.Utilisateur;
import views.FXML.items.ChauffeurListCell;

public class ListeChauffeurControllerFXML extends ListeChauffeurController implements Initializable,ITabController {
	@FXML
    private ListView<Chauffeur> listViewChauffeur;
    @FXML
    private TextField recherche;
    @FXML
    private TextField nom;
    @FXML
    private TextField prenom;
    @FXML
    private TextField adresse;
    @FXML
    private TextField cp;
    @FXML
    private TextField localite;
    @FXML
    private TextField tel;
    @FXML
    private TextArea infos;
    @FXML
    private HBox boxAff;
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnDelete;
    @FXML
    private Button btnEdit;
    @FXML
    private ListView<?> listViewCourse;
    @FXML
    private HBox boxEdit;
    @FXML
    private Button btnAnnuler;
    @FXML
    private Button btnSave;

    public ListeChauffeurControllerFXML(Utilisateur user, TabPane tabContainer) {
    	super(user);
    	Tab tab = null;
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getClassLoader().getResource("views/FXML/ListeChauffeur.fxml"));
			loader.setController(this);
			GridPane content;
			content = (GridPane)loader.load();
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
		listViewChauffeur.setCellFactory(lc -> new ChauffeurListCell());
		listViewChauffeur.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Chauffeur>() {
		    @Override
		    public void changed(ObservableValue<? extends Chauffeur> observable, Chauffeur oldValue, Chauffeur newValue) {
		    	if(newValue!=null) {
		    		setSelectedChauffeur(newValue);
		    		showChauffeur(newValue);
		    	}
		    }
		});
		
		recherche.textProperty().addListener((observable, oldValue, newValue) -> {
			setListeChauffeur(search(newValue));
		});
		
		
		setEditable(false);
		
		boxAff.setVisible(isAdmin());
		boxEdit.setVisible(false);
		if (isAdmin()) {
			btnAdd.setOnAction((ActionEvent e) -> addF());
			btnEdit.setOnAction((ActionEvent e) -> editerF());
			btnAnnuler.setOnAction((ActionEvent e) -> annulerF());
			btnDelete.setOnAction((ActionEvent e) -> deleteF());
			btnSave.setOnAction((ActionEvent e) -> saveF());
		}
		setListeChauffeur(search(""));
	}
	
	private void addF() {
		showChauffeur(new Chauffeur());
		setEditable(true);
	}

	private void saveF() {
		try {
			Chauffeur app = getInfoChauffeur();
			super.valdation(app);
			if(comfirmation("Sauvegarder","")) {
				setEditable(false);
				super.save(app);
				showChauffeur(getSelectedAppelant());
			}
		} catch (Exception e) {
			alert(e.getMessage());
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
		if (isSelected() && comfirmation("Supprimer","")) {
			super.delete();
			showChauffeur(getSelectedAppelant());
		}
	}

	private void annulerF() {
		if (comfirmation("Annuler","")) {
			setEditable(false);
			showChauffeur(getSelectedAppelant());
		}
	}

	private void editerF() {
		if(isSelected()) {
			setEditable(true);
		}
	}

	private boolean comfirmation(String titre, String msg) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Confirmation");
		alert.setHeaderText(titre);
		alert.setContentText(msg);
		Optional<ButtonType> result = alert.showAndWait();
		return result.get() == ButtonType.OK;
	}
	
	private boolean alert(String msg) {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Erreur");
		alert.setHeaderText(msg);
		alert.setContentText(null);
		Optional<ButtonType> result = alert.showAndWait();
		return result.get() == ButtonType.OK;
	}

	private void setEditable(boolean b) {
		b &= isAdmin();
		if (isAdmin()) {
			boxAff.setVisible(!b);
			boxEdit.setVisible(b);
		}
		
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
		setEditable(false);
		
		nom.setText(app.getName());
		prenom.setText(app.getFirstname());
		adresse.setText(app.getAdresse());
		cp.setText(app.getCp());
		localite.setText(app.getLocalite());
		tel.setText(app.getTel());
		infos.setText(app.getInfos());
	}
	
	private void setListeChauffeur(List<Chauffeur> appelants) {
		listViewChauffeur.getItems().clear();
		listViewChauffeur.getItems().setAll(appelants);
	}
	
	@Override
	public void logout(){
		
	}

	@Override
	public void login(Utilisateur user) {
		// TODO Auto-generated method stub
		
	}
}
