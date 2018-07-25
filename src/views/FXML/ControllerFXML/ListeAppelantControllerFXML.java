package views.FXML.ControllerFXML;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import controllers.ListeAppelantController;
import javafx.fxml.Initializable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import models.Appelant;
import views.FXML.items.AppelantListCell;

public class ListeAppelantControllerFXML extends ListeAppelantController implements Initializable {
	@FXML
    private ListView<Appelant> listViewAppelant;
    @FXML
    private Button btnAdd;
    @FXML
    private TextField recherche;
    @FXML
    private TextField aide;
    @FXML
    private TextArea autre;
    @FXML
    private TextArea infos;
    @FXML
    private TextField nom;
    @FXML
    private TextField prenom;
    @FXML
    private TextField dateNais;
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
    private Label code;
    @FXML
    private Button btnDelete;
    @FXML
    private Button btnSave;
    @FXML
    private Button btnEdit;
    @FXML
    private Button btnAnnuler;

    public ListeAppelantControllerFXML() {
		setContolerSpec(this);
	}
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		setEditable(false);
		listViewAppelant.setCellFactory(lc -> new AppelantListCell());
		listViewAppelant.getItems().setAll(getAllAppelant());
		listViewAppelant.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Appelant>() {
		    @Override
		    public void changed(ObservableValue<? extends Appelant> observable, Appelant oldValue, Appelant newValue) {
		    	if(newValue!=null) {
		    		setAppelant(newValue);
		    	}
		    }
		});
		
		recherche.textProperty().addListener((observable, oldValue, newValue) -> {
		    search(newValue);
		});
		
		btnEdit.setOnAction((ActionEvent e) -> setEditable(true));
		btnAnnuler.setOnAction((ActionEvent e) -> setEditable(false));
	}
	
	private void setEditable(boolean b) {
		btnDelete.setVisible(b);
		btnSave.setVisible(b);
		btnAnnuler.setVisible(b);
		btnEdit.setVisible(!b);
		
		btnAdd.setDisable(b);
		listViewAppelant.setDisable(b);
		
		nom.setEditable(b);
		prenom.setEditable(b);
		dateNais.setEditable(b);
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
	
	@Override
	public void showAppelant(Appelant app) {
		code.setText(""+app.getId());
		nom.setText(app.getName());
		prenom.setText(app.getName());
		dateNais.setText(app.getBirthday().toString());
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

	@Override
	public void setListeAppelant(List<Appelant> appelants) {
		listViewAppelant.getItems().clear();
		listViewAppelant.getItems().setAll(appelants);
	}
}
