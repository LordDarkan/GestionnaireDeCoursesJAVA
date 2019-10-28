package fxml.ControllerFXML;

import java.io.IOException;
import java.net.URL;
import java.text.Normalizer;
import java.text.Normalizer.Form;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.bind.ValidationException;

import controllers.ListeAppelantController;
import fxml.Message;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.print.PageLayout;
import javafx.print.PageOrientation;
import javafx.print.Paper;
import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import models.Appelant;
import models.Residence;
import models.Utilisateur;
import models.itemList.AppelantItemList;
import models.itemList.ChauffeurItemList;
import models.itemList.CourseItemList;
import util.LoggerManager;
import util.Security;
import util.Titre;
import util.UserManager;

public class ListeAppelantControllerFXML extends ListeAppelantController implements Initializable,ITabController {
	private static final Logger LOG = LoggerManager.getLogger();
	@FXML
    private Button btnAdd;
	@FXML
    private Button btnPrint;
	@FXML
    private Button btnClear;
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
    private TextField nom;
    @FXML
    private TextField prenom;
    @FXML
    private DatePicker datePNais;
    @FXML
    private ComboBox<Titre> titre;
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
    private TextField infos;
    @FXML
    private ComboBox<String> residence;
    @FXML
    private TextField autre;
    @FXML
    private TextField tel;
    @FXML
    private TextField mobilite;
    @FXML
    private TextField payement;
    @FXML
    private TextField cotisation;
    @FXML
    private Button btnAddFamille;
    @FXML
    private Button btnDelFamille;
    @FXML
    private Button btnAddProche;
    @FXML
    private Button btnDelProche;
    @FXML
    private Button btnAddRestrict;
    @FXML
    private Button btnDelRestrict;
    @FXML
    private ListView<ChauffeurItemList> listViewProche;
    @FXML
    private ListView<ChauffeurItemList> listViewRestrict;
    @FXML
    private ListView<AppelantItemList> listViewFamille;
    @FXML
    private CheckBox cbOldCourse;
    @FXML
    private ListView<CourseItemList> listeViewCourse;
    
    private MainControllerFXML main;

    public ListeAppelantControllerFXML(MainControllerFXML main, Utilisateur user, TabPane tabContainer) {
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
		
		/*listeViewCourse.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<CourseItemList>() {
		    @Override
		    public void changed(ObservableValue<? extends CourseItemList> observable, CourseItemList oldValue, CourseItemList newValue) {
		    	if(newValue!=null) {
		    		selectCourse(newValue.getId());
		    		listeViewCourse.getSelectionModel().select(-1);
		    		//TODO int index = listeViewCourse.getSelectionModel().getSelectedIndex();
		    		//listeViewCourse.getSelectionModel().clearSelection(index);
		    	}
		    }
		});*/
		
		titre.getItems().addAll(Titre.values());
		
		cbOldCourse.setSelected(false);
		cbOldCourse.selectedProperty().addListener((isOld)->setListCourse(getCourses(cbOldCourse.isSelected())));
		
		listeViewCourse.setOnMouseClicked(new EventHandler<MouseEvent>() {

		    @Override
		    public void handle(MouseEvent click) {

		        if (click.getClickCount() == 2) {
		        	CourseItemList courseItemList = listeViewCourse.getSelectionModel()
		                                                    .getSelectedItem();
		        	if(courseItemList != null) {
		        		selectCourse(courseItemList.getId());
		        	}
		        }
		    }
		});
		
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
		
		localite.textProperty().addListener((observable, oldValue, newValue) -> {
			localite.setText(Normalizer.normalize(newValue.toUpperCase(), Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", ""));
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
		
		btnClear.setOnAction((ActionEvent e) -> clearSearch());
		btnPrint.setOnAction((ActionEvent e) -> imprimer());
		
		
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
	
	private void selectCourse(long idCourse) {
		if(isSelected()){
			main.selectCourse(idCourse);
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
			
			if (app.getId() == null && Message.comfirmation("Voulez-vous encoder un identifiant ?", null)) {
				app.setId(selectId());
				super.saveNew(app);
			} else {
				super.save(app);
			}
			editMode(false);
			showAppelant(getSelectedAppelant());
			majList();
		} catch (ValidationException e) {//id
		} catch (Exception e) {//validation
			alert(e.getMessage());
		}
	}

	private Long selectId() throws ValidationException {
		Long id = null;
		boolean valid = false;
		String err = null;
		String strId;
		while (!valid) {
			strId = Message.getString(err,"CODE:");
			if (strId==null)
				throw new ValidationException("Annulation encodage");
			
			try {
				id = Long.parseLong(strId.trim());
				valid = validIdApplant(id);
				err = "L'identifiant est déjà utilisé";
			} catch (Exception e) {
				err = "L'identifiant doit être un chiffre";
			}
		}
		
		return id;
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
		app.setTitre(titre.getValue());
		app.setResidence(residence.getSelectionModel().getSelectedItem());
		app.setAdresse(adresse.getText().trim());
		app.setCp(cp.getText().trim());
		app.setLocalite(localite.getText().trim());
		app.setQuartier(quartier.getText().trim());
		app.setTel(tel.getText().trim());
		app.setMobilite(mobilite.getText().trim());
		//app.setMutualite(mutualite.getText().trim());
		app.setPayement(payement.getText().trim());
		try {
			app.setCotisation(Integer.parseInt(cotisation.getText().trim()));
		} catch (Exception e) {
		}
		app.setAideParticuliere(aide.getText().trim());
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
			if(!isAdmin()) {
				autre.requestFocus();
			}
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
		
		cbOldCourse.setVisible(!b);
		listeViewCourse.setVisible(!b);
		
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
		titre.setDisable(!b);
		
		residence.setDisable(!b);
		
		adresse.setEditable(b);
		cp.setEditable(b);
		localite.setEditable(b);
		quartier.setEditable(b);
		tel.setEditable(b);
		mobilite.setEditable(b);
		payement.setEditable(b);
		//mutualite.setEditable(b);
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
		titre.getSelectionModel().select(app.getTitre());
		residence.getSelectionModel().select(app.getResidence());
		adresse.setText(app.getAdresse());
		cp.setText(app.getCp());
		localite.setText(app.getLocalite());
		quartier.setText(app.getQuartier());
		tel.setText(app.getTel());
		mobilite.setText(app.getMobilite());
		//mutualite.setText(app.getMutualite());
		payement.setText(app.getPayement());
		cotisation.setText(""+app.getCotisation());
		aide.setText(app.getAideParticuliere());
		infos.setText(app.getInfos());
		autre.setText(app.getRemarques());
		//testResidence(app.getResidence());//TODO a en levé
		setListFamille(getFamille(app.getFamille()));
		setListProche(getChauffeurList(app.getAffinite()));
		setListRestrict(getChauffeurList(app.getRestriction()));
		setListCourse(getCourses(cbOldCourse.isSelected()));
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
	
	private void clearSearch() {
		recherche.setText("");
		recherche.requestFocus();
		//setListeAppelant(search(""));
	}
	
	
	private void imprimer() {
		if (isSelected()) {
			/*if (print == null) {
				print = new PrintCourseControllerFXML();
			}*/
			PrintCourseControllerFXML print = new PrintCourseControllerFXML();
			Node myPrint = print.getNode(getSelectedAppelant(),getUser());
			
			if (myPrint == null){
				System.err.println("No file is open!");
				return;
			}
			
			PrinterJob printAction = PrinterJob.createPrinterJob();
			if (printAction == null){
				LOG.log(Level.WARNING, "IMPRIMER FROM APPELANT Unable to access system print utilities "+ UserManager.getFullName());
				return;
			}
			PageLayout pageLayout = Printer.getDefaultPrinter().createPageLayout(Paper.A4, PageOrientation.PORTRAIT, Printer.MarginType.DEFAULT);
			printAction.getJobSettings().setPageLayout(pageLayout);
			boolean notCancelled = printAction.showPrintDialog(listViewAppelant.getScene().getWindow());
			if (notCancelled){
				//boolean success = printAction.printPage(pageLayout, myPrint);
				boolean success = printAction.printPage(myPrint);
				if (success)
					printAction.endJob();
				else
					LOG.log(Level.WARNING, "IMPRIMER FROM APPELANT "+ UserManager.getFullName());
			}
		}
	}

	@Override
	public void select(Long id) {
		throw new UnsupportedOperationException();
	}
}
