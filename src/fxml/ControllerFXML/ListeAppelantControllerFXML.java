package views.FXML.ControllerFXML;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import controllers.ListeAppelantController;
import javafx.fxml.Initializable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.control.Alert.AlertType;
import models.Appelant;
import models.Residence;
import models.Utilisateur;
import models.item.AppelantList;
import models.item.CourseList;

public class ListeAppelantControllerFXML extends ListeAppelantController implements Initializable,ITabController {
	@FXML
    private Button btnAdd;
    @FXML
    private TextField recherche;
    @FXML
    private ListView<AppelantList> listViewAppelant;
    @FXML
    private Button btnDelete;
    @FXML
    private Button btnEdit;
    @FXML
    private Button btnNewCourse;
    @FXML
    private Button btnAnnuler;
    @FXML
    private Button btnSave;
    @FXML
    private Label code;
    @FXML
    private RadioButton cbMr;
    @FXML
    private RadioButton cbMme;
    @FXML
    private TextField nom;
    @FXML
    private TextField prenom;
    @FXML
    private DatePicker datePNais;
    @FXML
    private TextField aide;
    @FXML
    private TextField adresse;
    @FXML
    private TextField cp;
    @FXML
    private TextField localite;
    @FXML
    private TextField quartier;
    @FXML
    private TextArea infos;
    @FXML
    private ComboBox<String> residence;
    @FXML
    private TextArea autre;
    @FXML
    private TextField tel;
    @FXML
    private TextField mobilite;
    @FXML
    private TextField mutualite;
    @FXML
    private TextField cotisation;
    @FXML
    private ListView<CourseList> listeViewCourse;
    
    private MainControllerFXML main;

    public ListeAppelantControllerFXML(MainControllerFXML main, Utilisateur user, TabPane tabContainer) {
    	super(user);
    	this.main = main;
    	Tab tab = null;
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getClassLoader().getResource("views/FXML/ListeAppelant.fxml"));
			loader.setController(this);
			VBox content;
			content = (VBox)loader.load();
			tab = new Tab();
			tab.setClosable(false);
            tab.setText("Appelant");
            tab.setContent(content);
            tab.setOnSelectionChanged(new EventHandler<Event>() {
                @Override
                public void handle(Event event) {
                	if (((Tab)event.getSource()).isSelected()) {
                		majList();
					}
                }
            });
            tabContainer.getTabs().add(tab);
		} catch (IOException e) {//TODO
			e.printStackTrace();
		}
	}

	private void majList() {
		super.update();
		setListeAppelant(search(null));
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		cbMr.setVisible(false);
		cbMme.setVisible(false);
		listViewAppelant.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<AppelantList>() {
		    @Override
		    public void changed(ObservableValue<? extends AppelantList> observable, AppelantList oldValue, AppelantList newValue) {
		    	if(newValue!=null) {
		    		setSelectedAppelant(newValue.getId());
		    		showAppelant(getSelectedAppelant());
		    	}
		    }
		});
		
		recherche.textProperty().addListener((observable, oldValue, newValue) -> {
			setListeAppelant(search(newValue));
		});
		
		cotisation.textProperty().addListener((observable, oldValue, newValue) -> {
			if (!newValue.matches("\\d*")) {
				cotisation.setText(newValue.replaceAll("[^\\d]", ""));
	        }
		});
		residence.getItems().addAll(getResidence());
		residence.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			public void changed(ObservableValue<? extends String> ov, String value, String newValue) {
				if (newValue != null) {
					testResidence(newValue);
				}
			}
		});
		
		datePNais.setStyle("-fx-opacity: 1");
		datePNais.getEditor().setStyle("-fx-opacity: 1");
		
		btnAdd.setOnAction((ActionEvent e) -> addF());
		btnNewCourse.setOnAction((ActionEvent e) -> newCourse());
		btnEdit.setOnAction((ActionEvent e) -> editerF());
		btnAnnuler.setOnAction((ActionEvent e) -> annulerF());
		btnDelete.setOnAction((ActionEvent e) -> deleteF());
		btnSave.setOnAction((ActionEvent e) -> saveF());
		
		editMode(false);
		setListeAppelant(search(""));
	}
	
	private void newCourse() {
		if(isSelected()){
			main.newCourse(getSelectedAppelant().getId());
		}
	}

	private void addF() {
		if (isAdmin()) {
			showAppelant(new Appelant());
			editMode(true);
		}
	}

	private void saveF() {
		try {
			Appelant app = getInfoAppelant();
			Appelant.valdation(app);
			if(comfirmation("Sauvegarder","")) {
				super.save(app);
				editMode(false);
				showAppelant(getSelectedAppelant());
				majList();
			}
		} catch (Exception e) {
			alert(e.getMessage());
		}
	}

	private Appelant getInfoAppelant() {
		Appelant app = new Appelant();
		try {
			app.setId(Long.parseLong(code.getText()));
		} catch (Exception e) {
			// id == null
		}
		app.setName(nom.getText().trim());
		app.setFirstname(prenom.getText().trim());
		app.setBirthday(datePNais.getValue());
		app.setResidence(residence.getSelectionModel().getSelectedItem());
		app.setAdresse(adresse.getText().trim());
		app.setCp(cp.getText().trim());
		app.setLocalite(localite.getText().trim());
		app.setQuartier(quartier.getText().trim());
		app.setTel(tel.getText().trim());
		app.setMobilite(mobilite.getText().trim());
		app.setMutualite(mutualite.getText().trim());
		try {
			app.setCotisation(Integer.parseInt(cotisation.getText().trim()));
		} catch (Exception e) {
			// TODO: handle exception
		}
		app.setAideParticulière(aide.getText().trim());
		app.setInfos(infos.getText().trim());
		app.setRemarques(autre.getText().trim());
		return app;
	}

	private void deleteF() {
		if (isSelected() && comfirmation("Supprimer","")) {
			super.delete();
			showAppelant(getSelectedAppelant());
			majList();
		}
	}

	private void annulerF() {
		if (comfirmation("Annuler","")) {
			editMode(false);
			showAppelant(getSelectedAppelant());
		}
	}

	private void editerF() {
		if(isSelected()) {
			editMode(true);
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

	private void editMode(boolean b) {
		b &= isAdmin();
		
		btnAdd.setVisible(!b && isAdmin());
		btnDelete.setVisible(!b && isAdmin());
		btnEdit.setVisible(!b && isAdmin());
		btnAnnuler.setVisible(b);
		btnSave.setVisible(b);
		
		btnNewCourse.setVisible(!b);
		
		listViewAppelant.setDisable(b);
		
		datePNais.setDisable(!b);
		
		nom.setEditable(b);
		prenom.setEditable(b);
		
		residence.setEditable(b);
		adresse.setEditable(b);
		cp.setEditable(b);
		localite.setEditable(b);
		quartier.setEditable(b);
		tel.setEditable(b);
		mobilite.setEditable(b);
		mutualite.setEditable(b);
		cotisation.setEditable(b);
		aide.setEditable(b);
		infos.setEditable(b);
		autre.setEditable(b);
	}
	
	private void showAppelant(Appelant app) {
		editMode(false);
		
		if(app.getId()!=null) {
			code.setText(""+app.getId());
		} else {
			code.setText("xxx");
		}
		nom.setText(app.getName());
		prenom.setText(app.getFirstname());
		datePNais.setValue(app.getBirthday());
		residence.getSelectionModel().select(app.getResidence());
		adresse.setText(app.getAdresse());
		cp.setText(app.getCp());
		localite.setText(app.getLocalite());
		quartier.setText(app.getQuartier());
		tel.setText(app.getTel());
		mobilite.setText(app.getMobilite());
		mutualite.setText(app.getMutualite());
		cotisation.setText(""+app.getCotisation());
		aide.setText(app.getAideParticulière());
		infos.setText(app.getInfos());
		autre.setText(app.getRemarques());
		testResidence(app.getResidence());//TODO a en levé
	}
	
	private void setListeAppelant(List<AppelantList> list) {
		listViewAppelant.getItems().clear();
		listViewAppelant.getItems().setAll(list);
	}
	
	@Override
	public void logout(){
		showAppelant(new Appelant());
		recherche.setText("");
		clear();
	}

	@Override
	public void login(Utilisateur user) {
		newLog(user);
		editMode(false);
		setListeAppelant(search(""));
	}
	
	private void testResidence(String value) {
		boolean nan = value.equals("NaN");
		adresse.setDisable(!nan);
	    localite.setDisable(!nan);
	    cp.setDisable(!nan);
		if(!nan) {
			Residence re = getResidence(value);
			adresse.setText(re.getAdresse());
			localite.setText(re.getLocalite());
			cp.setText(re.getCp());
		}
	}
	
	
}
