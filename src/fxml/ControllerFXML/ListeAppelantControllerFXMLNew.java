package fxml.ControllerFXML;

/*import java.io.IOException;
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

public class ListeAppelantControllerFXMLNew extends ListeAppelantController implements Initializable,ITabController {
	@FXML
    private Button btnAdd;

    @FXML
    private TextField recherche;

    @FXML
    private ListView<AppelantItemList> listViewAppelant;

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
    private ScrollPane content;
    
    private MainControllerFXML main;

    public ListeAppelantControllerFXMLNew(MainControllerFXML main, Utilisateur user, TabPane tabContainer) {
    	super(user);
    	this.main = main;
    	Tab tab = null;
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getClassLoader().getResource("fxml/views/ListeAppelant.fxml"));
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
                		setResidence(getResidence());//TODO
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
		setListeAppelant(search(null));//TODO
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		cbMr.setVisible(false);
		cbMme.setVisible(false);
		listViewAppelant.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<AppelantItemList>() {
		    @Override
		    public void changed(ObservableValue<? extends AppelantItemList> observable, AppelantItemList oldValue, AppelantItemList newValue) {
		    	if(newValue!=null) {
		    		setSelectedAppelant(newValue.getId());
		    		showAppelant(getSelectedAppelant());
		    	}
		    }
		});
		
		recherche.textProperty().addListener((observable, oldValue, newValue) -> {
			setListeAppelant(search(newValue));
		});
		
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
		setResidence(getResidence());
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
		
		btnAddFamille.setOnAction((ActionEvent e) -> addFamille());
		btnDelFamille.setOnAction((ActionEvent e) -> delFamille());
		btnAddProche.setOnAction((ActionEvent e) -> addProche());
	    btnDelProche.setOnAction((ActionEvent e) -> delProche());
	    btnAddRestrict.setOnAction((ActionEvent e) -> addRestriction());
	    btnDelRestrict.setOnAction((ActionEvent e) -> delRestriction());
		
		editMode(false);
		setListeAppelant(search(""));
	}
	
	private void addFamille() {
		if (isSelected()) {
			TextInputDialog dialog = new TextInputDialog();
			dialog.setTitle("Ajout");
			dialog.setHeaderText("Ajout d'un applant à la famille");
			dialog.setContentText("Code appelant:");

			// Traditional way to get the response value.
			Optional<String> result = dialog.showAndWait();
			if (result.isPresent() && result.get().matches("\\d*")){
			    Long id = Long.parseLong(result.get());
			    AppelantItemList app = addFamille(id);
				if (app!= null) {
					listViewFamille.getItems().add(app);
				} else {
					Message.alert("Code appelant invalide!");
				}
			}
			
		}
	}
	
	private void delFamille() {
		int indice = listViewFamille.getSelectionModel().getSelectedIndex();
		if (isSelected() && indice>=0 && Message.comfirmation("Supprimer", "Voulez-vous supprimer ce membre de la famille?")) {
			delFamille(listViewFamille.getItems().get(indice));
			listViewFamille.getItems().remove(indice);
		}
	}
	
	private void addProche() {
		if (isSelected()) {
			ChoiceDialog<ChauffeurItemList> dialog = new ChoiceDialog<>(null, getChauffeurList());
			dialog.setTitle("Ajout");
			dialog.setHeaderText("Look, a Choice Dialog");
			dialog.setContentText("Selectioner un chauffeur:");

			// Traditional way to get the response value.
			Optional<ChauffeurItemList> result = dialog.showAndWait();
			if (result.isPresent() && result.get()!=null){
				ChauffeurItemList ch = result.get();
				addProche(ch.getId());
				listViewProche.getItems().add(ch);
			}
		}
	}
	
	private void delProche() {
		int indice = listViewProche.getSelectionModel().getSelectedIndex();
		if (isSelected() && indice>=0 && Message.comfirmation("Supprimer", "Voulez-vous supprimer ce chauffeur proche?")) {
			delProche(listViewProche.getItems().get(indice));
			listViewProche.getItems().remove(indice);
		}
	}
	
	private void addRestriction() {
		if (isSelected()) {
			ChoiceDialog<ChauffeurItemList> dialog = new ChoiceDialog<>(null, getChauffeurList());
			dialog.setTitle("Ajout");
			dialog.setHeaderText("Look, a Choice Dialog");
			dialog.setContentText("Selectioner un chauffeur:");

			// Traditional way to get the response value.
			Optional<ChauffeurItemList> result = dialog.showAndWait();
			if (result.isPresent() && result.get()!=null){
				ChauffeurItemList ch = result.get();
				addRestrict(ch.getId());
				listViewRestrict.getItems().add(ch);
			}
		}
	}
	
	private void delRestriction() {
		int indice = listViewRestrict.getSelectionModel().getSelectedIndex();
		if (isSelected() && indice>=0 && Message.comfirmation("Supprimer", "Voulez-vous supprimer cette restriction?")) {
			delRestrict(listViewRestrict.getItems().get(indice));
			listViewRestrict.getItems().remove(indice);
		}
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
			super.save(app);
			editMode(false);
			showAppelant(getSelectedAppelant());
			majList();
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
		editMode(false);
		showAppelant(getSelectedAppelant());
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

	private void editMode(boolean a) {
		boolean b = a && isAdmin();
		
		btnAdd.setVisible(!b && isAdmin());
		btnDelete.setVisible(Security.isDelOk() && !b && isAdmin());
		btnEdit.setVisible(!a);
		btnAnnuler.setVisible(a);
		btnSave.setVisible(a);
		
		btnAddFamille.setVisible(!b && isAdmin());
		btnDelFamille.setVisible(!b && isAdmin());
		btnAddProche.setVisible(!b && isAdmin());
	    btnDelProche.setVisible(!b && isAdmin());
	    btnAddRestrict.setVisible(!b && isAdmin());
	    btnDelRestrict.setVisible(!b && isAdmin());
		
		btnNewCourse.setVisible(!a);
		
		listViewAppelant.setDisable(b);
		
		datePNais.setDisable(!b);
		
		nom.setEditable(b);
		prenom.setEditable(b);
		
		residence.setDisable(!b);
		
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
		autre.setEditable(a);
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
*/