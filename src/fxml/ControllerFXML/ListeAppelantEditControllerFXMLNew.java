package fxml.ControllerFXML;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import controllers.ListeAppelantController;
import fxml.Message;
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
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.VBox;
import javafx.scene.control.Alert.AlertType;
import models.Appelant;
import models.Residence;
import models.Utilisateur;
import models.itemList.AppelantItemList;
import models.itemList.ChauffeurItemList;
import models.itemList.CourseItemList;
import util.Security;

public class ListeAppelantEditControllerFXMLNew implements Initializable,ITabController {

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
    private TextField tel;

    @FXML
    private TextField mobilite;

    @FXML
    private TextField mutualite;

    @FXML
    private TextField cotisation;

    @FXML
    private TextArea autre;

    private VBox editContent;
    private Appelant app;

    public ListeAppelantEditControllerFXMLNew() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getClassLoader().getResource("fxml/views/ListeAppelantEdit.fxml"));
			loader.setController(this);
			editContent = (VBox)loader.load();
		} catch (IOException e) {//TODO
			e.printStackTrace();
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		cbMr.setVisible(false);
		cbMme.setVisible(false);
		
		cp.textProperty().addListener((observable, oldValue, newValue) -> {
			if (!newValue.matches("\\d*")) {
				cp.setText(newValue.replaceAll("[^\\d]", ""));
	        } else if(newValue.length()>4) {
	        	cp.setText(oldValue);
	        }
		});
		
		cotisation.textProperty().addListener((observable, oldValue, newValue) -> {
			if (!newValue.matches("\\d*")) {
				cotisation.setText(newValue.replaceAll("[^\\d]", ""));
	        }
		});
		residence.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			public void changed(ObservableValue<? extends String> ov, String value, String newValue) {
				if (newValue != null) {
					testResidence(newValue);
				}
			}
		});
	}

	private Appelant getInfoAppelant() {
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
		}
		app.setAideParticulière(aide.getText().trim());
		app.setInfos(infos.getText().trim());
		app.setRemarques(autre.getText().trim());
		return app;
	}

	private void editMode(boolean admin) {
		
		datePNais.setDisable(!admin);
		
		nom.setDisable(admin);
		prenom.setDisable(!admin);
		
		residence.setDisable(!admin);
		
		adresse.setDisable(!admin);
		cp.setDisable(!admin);
		localite.setDisable(!admin);
		quartier.setDisable(!admin);
		tel.setDisable(!admin);
		mobilite.setDisable(!admin);
		mutualite.setDisable(!admin);
		cotisation.setDisable(!admin);
		aide.setDisable(!admin);
		infos.setDisable(!admin);
		//autre.setEditable(admin);
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
		//testResidence(app.getResidence());//TODO a en levé
		setListFamille(getFamille(app.getFamille()));
		setListProche(getChauffeurList(app.getAffinite()));
		setListRestrict(getChauffeurList(app.getRestriction()));
		setListCourse(getCourses(app.getId()));
	}
	
	private void setListFamille(List<AppelantItemList> list) {
		listViewFamille.getItems().clear();
		listViewFamille.getItems().setAll(list);
	}
	
	private void setListProche(List<ChauffeurItemList> list) {
		listViewProche.getItems().clear();
		listViewProche.getItems().setAll(list);
	}
	
	private void setListRestrict(List<ChauffeurItemList> list) {
		listViewRestrict.getItems().clear();
		listViewRestrict.getItems().setAll(list);
	}
	
	private void setListeAppelant(List<AppelantItemList> list) {
		listViewAppelant.getItems().clear();
		listViewAppelant.getItems().setAll(list);
	}
	
	private void setResidence(List<String> list) {
		residence.getItems().clear();
		residence.getItems().setAll(list);
	}
	
	private void setListCourse(List<CourseItemList> list) {
		listeViewCourse.getItems().clear();
		listeViewCourse.getItems().setAll(list);
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
		setResidence(getResidence());
	}
	
	private void testResidence(String value) {
		boolean nan = value==null || value.isEmpty();
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
