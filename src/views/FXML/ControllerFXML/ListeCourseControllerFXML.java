package views.FXML.ControllerFXML;

import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import controllers.ListeCourseController;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import models.Adresse;
import models.Appelant;
import models.Course;
import models.Residence;
import models.Utilisateur;
import models.list.ChauffeurList;
import models.list.CourseList;
import util.Time;
import util.TypeCourse;
import views.FXML.items.ChauffeurListCell;
import views.FXML.items.ChauffeurListListCell;
import views.FXML.items.CourseListCell;

public class ListeCourseControllerFXML extends ListeCourseController implements Initializable,ITabController {
	private static final DateTimeFormatter formatHeure = DateTimeFormatter.ofPattern("HH:mm");
	private static final DateTimeFormatter formatDate = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	@FXML
	private VBox affichage;
	@FXML
    private Button btnEdit;
    @FXML
    private Button btnDel;
    @FXML
    private Button btnAnn;
    @FXML
    private Button btnSave;
	@FXML
    private ListView<CourseList> listViewCourses;
    @FXML
    private DatePicker selectDay;
    @FXML
    private ComboBox<ChauffeurList> selectChauffeur;
    @FXML
    private RadioButton selectParJour;
    @FXML
    private RadioButton selectFutur;
    @FXML
    private Label creatDate;
    @FXML
    private Label creatHeure;
    @FXML
    private Label creatName;
    @FXML
    private Label codeApplant;
    @FXML
    private Label nomCompletAppelant;
    @FXML
    private Label  handicapAppelant;
    @FXML
    private Label  aideAppelant;
    @FXML
    private ComboBox<ChauffeurList> editChauffeur;
    @FXML
    private DatePicker editDateCourse;
    @FXML
    private ComboBox<TypeCourse> editType;
    @FXML
    private CheckBox editAttestation;
    @FXML
    private Label affChaufeur;
    @FXML
    private Label affDate;
    @FXML
    private Label affType;
    @FXML
    private Label affAttestation;
    @FXML
    private HBox edit1;
    @FXML
    private Spinner<Integer> editHeureDepart;
    @FXML
    private Spinner<Integer> editMinuteDepart;
    @FXML
    private TextField editAdresseDepart;
    @FXML
    private TextField editLocaliteDepart;
    @FXML
    private TextField editCpDepart;
    @FXML
    private ComboBox<String> editResidence;
    @FXML
    private Label affHeureDepart;
    @FXML
    private Label affAdresseDepart;
    @FXML
    private Label affLocaliteDepart;
    @FXML
    private Label affCpDepart;
    @FXML
    private Label affResidence;
    @FXML
    private HBox edit2;
    @FXML
    private Spinner<Integer> editHeureRDV;
    @FXML
    private Spinner<Integer> editMinuteRDV;
    @FXML
    private TextField editAdresseRDV;
    @FXML
    private TextField editLocaliteRDV;
    @FXML
    private TextField editCpRDV;
    @FXML
    private ComboBox<String> editHopital;
    @FXML
    private Label affHeureRDV;
    @FXML
    private Label affAdresseRDV;
    @FXML
    private Label affLocaliteRDV;
    @FXML
    private Label affCpRDV;
    @FXML
    private Label affHopital;
    @FXML
    private HBox edit3;
    @FXML
    private Spinner<Integer> editHeureRetour;
    @FXML
    private Spinner<Integer> editMinuteRetour;
    @FXML
    private TextField editAdresseRetour;
    @FXML
    private TextField editLocaliteRetour;
    @FXML
    private TextField editCpRetour;
    @FXML
    private CheckBox editAttente;
    @FXML
    private ComboBox<ChauffeurList> editChauffeurSec;
    @FXML
    private Label affHeureRetour;
    @FXML
    private Label affAdresseRetour;
    @FXML
    private Label affLocaliteRetour;
    @FXML
    private Label affCpRetour;
    @FXML
    private Label affChaufeurSec;
    @FXML
    private Label affAttente;
    @FXML
    private TextArea editNote;
    @FXML
    private Label  affNote;
    @FXML
    private Label attributionName;
    @FXML
    private Label attributionDate;
    @FXML
    private Label annulationName;
    @FXML
    private Label annulationDate;
    @FXML
    private Label annulationRaison;
	
	public ListeCourseControllerFXML(Utilisateur user, TabPane tabContainer) {
		super(user);
		Tab tab = null;
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getClassLoader().getResource("views/FXML/ListeCourse.fxml"));
			loader.setController(this);
			AnchorPane content;
			content = (AnchorPane)loader.load();
			tab = new Tab();
			tab.setClosable(false);
            tab.setText("Courses");
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

	protected void selected() {
		editMode(btnSave.isVisible());
		setListeCourse(getCourseList());
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		listViewCourses.setCellFactory(lc -> new CourseListCell());
		listViewCourses.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<CourseList>() {
		    @Override
		    public void changed(ObservableValue<? extends CourseList> observable, CourseList oldValue, CourseList newValue) {
		    	if(newValue!=null) {
		    		setSelectedCourse(newValue.getId());
		    		editMode(false);
		    	}
		    }
		});
		listViewCourses.setOnMouseClicked(new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent arg0) {
				CourseList newValue = listViewCourses.getSelectionModel().getSelectedItem();
				if(newValue !=null && getSelectedCourse() == null) {
		    		setSelectedCourse(newValue.getId());
					editMode(false);
				}
			}

		});
		List<ChauffeurList> l = getChauffeurList();
		selectChauffeur.getItems().addAll(l);
		editChauffeur.getItems().addAll(l);
		editChauffeurSec.getItems().addAll(l);
		editType.getItems().addAll(TypeCourse.values());
		setResidence();
		editResidence.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			public void changed(ObservableValue<? extends String> ov, String value, String newValue) {
				if (newValue != null) {
					testResidence(newValue);
				}
			}
		});
		editHopital.getItems().addAll(getHopital());
		
		editHeureDepart.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23, 0));
		editMinuteDepart.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 0));
		editHeureRDV.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23, 0));
		editMinuteRDV.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 0));
		editHeureRetour.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23, 0));
		editMinuteRetour.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 0));
		
		btnEdit.setOnAction((ActionEvent e) -> editerF());
		btnDel.setOnAction((ActionEvent e) -> deleteF());
		btnAnn.setOnAction((ActionEvent e) -> annulerF());
		btnSave.setOnAction((ActionEvent e) -> saveF());
		
		editMode(false);
	}
	
	private void testResidence(String value) {
		boolean nan = value.equals("NaN");
		editAdresseDepart.setDisable(!nan);
	    editLocaliteDepart.setDisable(!nan);
	    editCpDepart.setDisable(!nan);
		if(!nan) {
			Residence re = getResidence(value);
			editAdresseDepart.setText(re.getAdresse());
		    editLocaliteDepart.setText(re.getLocalite());
		    editCpDepart.setText(re.getCp());
		}
	}
	
	private void testHopital(String value) {
		boolean nan = value.equals("NaN");
		editAdresseDepart.setDisable(!nan);
	    editLocaliteDepart.setDisable(!nan);
	    editCpDepart.setDisable(!nan);
		if(!nan) {
			Residence re = getResidence(value);
			editAdresseDepart.setText(re.getAdresse());
		    editLocaliteDepart.setText(re.getLocalite());
		    editCpDepart.setText(re.getCp());
		}
	}
	
	private void saveF() {
		try {
			Course course = getInfoCourse();
			Course.valdation(course);
			if(comfirmation("Sauvegarder","")) {
				super.save(course);
				editMode(false);
				setListeCourse(getCourseList());
			}
		} catch (Exception e) {
			e.printStackTrace();
			alert(e.getMessage());
		}
	}
	
	private Course getInfoCourse() {
		Course course = getSelectedCourse();
		if (course!=null) {
			ChauffeurList cl = editChauffeur.getSelectionModel().getSelectedItem();
			if(cl!= null) {
				course.setChauffeur(getChauffeur(cl.getId()), getUserName());
			} else {
				course.setChauffeur();
			}
			course.setDate(editDateCourse.getValue());
			course.setTypeCourse(editType.getSelectionModel().getSelectedItem());
			course.setMutuelle(editAttestation.isSelected());
			course.setHeureDomicile(Time.getLocalTime(editHeureDepart.getValueFactory().getValue(), editMinuteDepart.getValueFactory().getValue()));
			course.setAdresseDep(editAdresseDepart.getText().trim());
			course.setLocaliteDep(editLocaliteDepart.getText().trim());
			course.setCpDep(editCpDepart.getText().trim());
			course.setResidence(editResidence.getSelectionModel().getSelectedItem());
			course.setHeureRDV(Time.getLocalTime(editHeureRDV.getValueFactory().getValue(), editMinuteRDV.getValueFactory().getValue()));
			course.setAdresseDest(editAdresseRDV.getText().trim());
			course.setLocaliteDest(editLocaliteRDV.getText().trim());
			course.setCpDest(editCpRDV.getText().trim());
			course.setHopital(editHopital.getSelectionModel().getSelectedItem());
			course.setHeureRetour(Time.getLocalTime(editHeureRetour.getValueFactory().getValue(), editMinuteRetour.getValueFactory().getValue()));
			course.setAdresseRet(editAdresseRetour.getText().trim());
			course.setLocaliteRet(editLocaliteRetour.getText().trim());
			course.setCpRet(editCpRetour.getText().trim());
			course.setAttente(editAttente.isSelected());
		    cl = editChauffeurSec.getSelectionModel().getSelectedItem();
			if(cl!= null) {
				course.setChauffeurSec(getChauffeur(cl.getId()));
			} else {
				course.setChauffeurSec(null);
			}
			course.setNotes(editNote.getText().trim());
		}
		return course;
	}

	private void deleteF() {
		if (isSelected() && comfirmation("Supprimer","")) {
			super.delete();
			editMode(false);
			setListeCourse(getCourseList());
		}
	}

	private void annulerF() {
		if (comfirmation("Annuler","")) {
			super.annuler();
			editMode(false);
		}
	}

	private void editerF() {
		if(isSelected()) {
			super.editer();
			editMode(true);
			setListeCourse(getCourseList());
		}
	}
	
	@Override
	public void logout() {
		
		clear();
	}

	@Override
	public void login(Utilisateur user) {
		newLog(user);
		editMode(false);
		setListeCourse(getCourseList());
	}

	public void newCourse(Long id) {
		affEditCourse(getNewCourse(id));
		editMode(true);
	}
	
	private void editMode(boolean edit) {
		affichage.setVisible(isSelected());
		edit &=isSelected();
		if (edit)
			setResidence();
		
		btnEdit.setVisible(!edit);
		btnDel.setVisible(!edit);
		btnSave.setVisible(edit);
		btnAnn.setVisible(edit);
		
		listViewCourses.setDisable(edit);
		editChauffeur.setVisible(edit);
	    editDateCourse.setVisible(edit);
	    editType.setVisible(edit);
	    editAttestation.setVisible(edit);
	    affChaufeur.setVisible(!edit);
	    affDate.setVisible(!edit);
	    affType.setVisible(!edit);
	    affAttestation.setVisible(!edit);
	    edit1.setVisible(edit);
	    editAdresseDepart.setVisible(edit);
	    editLocaliteDepart.setVisible(edit);
	    editCpDepart.setVisible(edit);
	    editResidence.setVisible(edit);
	    affHeureDepart.setVisible(!edit);
	    affAdresseDepart.setVisible(!edit);
	    affLocaliteDepart.setVisible(!edit);
	    affCpDepart.setVisible(!edit);
	    affResidence.setVisible(!edit);
	    edit2.setVisible(edit);
	    editAdresseRDV.setVisible(edit);
	    editLocaliteRDV.setVisible(edit);
	    editCpRDV.setVisible(edit);
	    editHopital.setVisible(edit);
	    affHeureRDV.setVisible(!edit);
	    affAdresseRDV.setVisible(!edit);
	    affLocaliteRDV.setVisible(!edit);
	    affCpRDV.setVisible(!edit);
	    affHopital.setVisible(!edit);
	    edit3.setVisible(edit);
	    editAdresseRetour.setVisible(edit);
	    editLocaliteRetour.setVisible(edit);
	    editCpRetour.setVisible(edit);
	    editAttente.setVisible(edit);
	    editChauffeurSec.setVisible(edit);
	    affHeureRetour.setVisible(!edit);
	    affAdresseRetour.setVisible(!edit);
	    affLocaliteRetour.setVisible(!edit);
	    affCpRetour.setVisible(!edit);
	    affChaufeurSec.setVisible(!edit);
	    affAttente.setVisible(!edit);
	    editNote.setVisible(edit);
	    affNote.setVisible(!edit);
	    
		if (isSelected()) {
			Course course = getSelectedCourse();
			affApplant(course.getAppelant());
			if (edit) {
				affEditCourse(course);
			} else {
				affCourse(course);
			}
		}
	}
	
	private void affCourse(Course course) {
		if (course.getChauffeur()!=null) {
			affChaufeur.setText(course.getChauffeur().getFullName());
		}
	    affDate.setText(course.getDate().format(formatDate));
	    affType.setText(course.getTypeCourse().toString());
	    affAttestation.setText(course.isMutuelle()?"oui":"non");
	    affHeureDepart.setText(course.getHeureDomicile().format(formatHeure));
	    affAdresseDepart.setText(course.getAdresseDep());
	    affLocaliteDepart.setText(course.getLocaliteDep());
	    affCpDepart.setText(course.getCpDep());
	    affResidence.setText(course.getResidence());
	    affHeureRDV.setText(course.getHeureRDV().format(formatHeure));
	    affAdresseRDV.setText(course.getAdresseDest());
	    affLocaliteRDV.setText(course.getLocaliteDest());
	    affCpRDV.setText(course.getCpDest());
	    affHopital.setText(course.getHopital());
	    affHeureRetour.setText(course.getHeureRetour().format(formatHeure));
	    affAdresseRetour.setText(course.getAdresseRet());
	    affLocaliteRetour.setText(course.getLocaliteRet());
	    affCpRetour.setText(course.getCpRet());
	    if (course.getChauffeurSec()!=null) {
	    	affChaufeurSec.setText(course.getChauffeurSec().getFullName());
		}
	    affAttente.setText(course.isAttente()?"oui":"non");
	    affNote.setText(course.getNotes());
	}
	
	private void affEditCourse(Course course) {
		editChauffeur.getSelectionModel().select(course.getChauffeur()==null?null:new ChauffeurList(course.getChauffeur()));
	    editDateCourse.setValue(course.getDate());
	    editType.getSelectionModel().select(course.getTypeCourse());
	    editAttestation.setSelected(course.isMutuelle());
	    editHeureDepart.getValueFactory().setValue(course.getHeureDomicile().getHour());
	    editMinuteDepart.getValueFactory().setValue(course.getHeureDomicile().getMinute());
	    editAdresseDepart.setText(course.getAdresseDep());
	    editLocaliteDepart.setText(course.getLocaliteDep());
	    editCpDepart.setText(course.getCpDep());
	    editResidence.getSelectionModel().select(course.getResidence());
	    editHeureRDV.getValueFactory().setValue(course.getHeureRDV().getHour());
	    editMinuteRDV.getValueFactory().setValue(course.getHeureRDV().getMinute());
	    editAdresseRDV.setText(course.getAdresseDest());
	    editLocaliteRDV.setText(course.getLocaliteDest());
	    editCpRDV.setText(course.getCpDest());
	    editHopital.getSelectionModel().select(course.getHopital());
	    editHeureRetour.getValueFactory().setValue(course.getHeureRetour().getHour());
	    editMinuteRetour.getValueFactory().setValue(course.getHeureRetour().getMinute());
	    editAdresseRetour.setText(course.getAdresseRet());
	    editLocaliteRetour.setText(course.getLocaliteRet());
	    editCpRetour.setText(course.getCpRet());
	    editAttente.setSelected(course.isAttente());
	    editChauffeurSec.getSelectionModel().select(course.getChauffeurSec()==null?null:new ChauffeurList(course.getChauffeurSec()));
	    editNote.setText(course.getNotes());
	}
	
	private void affApplant(Appelant app) {
		if(app==null)
			app = new Appelant();
		codeApplant.setText(app.getId().toString());
	    nomCompletAppelant.setText(app.getFullName());
	    handicapAppelant.setText(app.getMobilite());
	    aideAppelant.setText(app.getAideParticulière());
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
	
	private void setResidence() {
		editResidence.getItems().clear();
		editResidence.getItems().addAll(getResidence());
	}
	
	private void setListeCourse(List<CourseList> list) {
		listViewCourses.getItems().clear();
		listViewCourses.getItems().setAll(list);
	}
	
	/*private void selectChauffeur() {
		Dialog<Long> dialog = new Dialog<>();
		dialog.setTitle("");
		dialog.setHeaderText("Sélectionner un chauffeur");
		dialog.setResizable(true);
		ListView<ChauffeurList> lv = new ListView<ChauffeurList>();
		List<ChauffeurList> l = getChauffeurList();
		lv.getItems().setAll(l);
		GridPane grid = new GridPane();
		grid.add(lv, 1, 1);
		dialog.getDialogPane().setContent(grid);
		ButtonType buttonTypeOk = new ButtonType("Okay", ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);
		dialog.setResultConverter(new Callback<ButtonType, Long>() {
		    @Override
		    public Long call(ButtonType b) {
		        if (b == buttonTypeOk) {
		            return 0L;
		        }
		        return null;
		    }
		});
		Optional<Long> result = dialog.showAndWait();
		if (result.isPresent()) {
		    result.get();
		}

	}*/
}
