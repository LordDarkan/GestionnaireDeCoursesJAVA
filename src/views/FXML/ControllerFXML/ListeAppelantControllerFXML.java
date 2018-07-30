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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.control.Alert.AlertType;
import models.Appelant;
import models.Residence;
import models.Utilisateur;
import models.list.CourseList;
import views.FXML.items.AppelantListCell;

public class ListeAppelantControllerFXML extends ListeAppelantController implements Initializable,ITabController {
	@FXML
    private ListView<Appelant> listViewAppelant;
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
    private TextField quartier;
    @FXML
    private TextField tel;
    @FXML
    private TextField mobilite;
    @FXML
    private TextField mutualite;
    @FXML
    private TextField cotisation;
    @FXML
    private TextField aide;
    @FXML
    private TextArea infos;
    @FXML
    private TextArea autre;
    @FXML
    private Label code;
    @FXML
    private DatePicker datePNais;
    @FXML
    private HBox boxAff;
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnDelete;
    @FXML
    private Button btnEdit;
    @FXML
    private HBox boxEdit;
    @FXML
    private Button btnAnnuler;
    @FXML
    private Button btnSave;
    @FXML
    private ListView<CourseList> listeViewCourse;
    @FXML
    private Button btnNewCourse;
    
    private MainControllerFXML main;

    public ListeAppelantControllerFXML(MainControllerFXML main, Utilisateur user, TabPane tabContainer) {
    	super(user);
    	this.main = main;
    	Tab tab = null;
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getClassLoader().getResource("views/FXML/ListeAppelant.fxml"));
			loader.setController(this);
			GridPane content;
			content = (GridPane)loader.load();
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

	protected void majList() {
		super.update();
		setListeAppelant(search(null));
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		listViewAppelant.setCellFactory(lc -> new AppelantListCell());
		listViewAppelant.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Appelant>() {
		    @Override
		    public void changed(ObservableValue<? extends Appelant> observable, Appelant oldValue, Appelant newValue) {
		    	if(newValue!=null) {
		    		setSelectedAppelant(newValue);
		    		showAppelant(newValue);
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
		
		datePNais.setStyle("-fx-opacity: 1");
		datePNais.getEditor().setStyle("-fx-opacity: 1");
		setEditable(false);
		btnNewCourse.setOnAction((ActionEvent e) -> newCourse());
		initAffichage();
	}
	
	private void newCourse() {
		if(isSelected()){
			main.newCourse(getSelectedAppelant().getId());
		}
	}

	private void addF() {
		showAppelant(new Appelant());
		setEditable(true);
	}

	private void saveF() {
		try {
			Appelant app = getInfoAppelant();
			Appelant.valdation(app);
			if(comfirmation("Sauvegarder","")) {
				setEditable(false);
				super.save(app);
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
			setEditable(false);
			showAppelant(getSelectedAppelant());
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
		
		btnNewCourse.setVisible(!b);
		
		listViewAppelant.setDisable(b);
		
		datePNais.setDisable(!b);
		
		nom.setEditable(b);
		prenom.setEditable(b);
		
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
		setEditable(false);
		
		if(app.getId()!=null) {
			code.setText(""+app.getId());
		} else {
			code.setText("xxx");
		}
		nom.setText(app.getName());
		prenom.setText(app.getFirstname());
		datePNais.setValue(app.getBirthday());
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
	}
	
	private void setListeAppelant(List<Appelant> appelants) {
		listViewAppelant.getItems().clear();
		listViewAppelant.getItems().setAll(appelants);
	}
	
	@Override
	public void logout(){
		showAppelant(new Appelant());
		clear();
	}

	@Override
	public void login(Utilisateur user) {
		newLog(user);
		initAffichage();
	}

	private void initAffichage() {
		boxAff.setVisible(isAdmin());
		boxEdit.setVisible(false);
		if (isAdmin()) {
			btnAdd.setOnAction((ActionEvent e) -> addF());
			btnEdit.setOnAction((ActionEvent e) -> editerF());
			btnAnnuler.setOnAction((ActionEvent e) -> annulerF());
			btnDelete.setOnAction((ActionEvent e) -> deleteF());
			btnSave.setOnAction((ActionEvent e) -> saveF());
		}
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
