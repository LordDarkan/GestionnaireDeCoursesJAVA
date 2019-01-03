package fxml.ControllerFXML;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

import controllers.ListeCourseController;
import fxml.Message;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.print.PageLayout;
import javafx.print.PageOrientation;
import javafx.print.Paper;
import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;
import models.Appelant;
import models.Course;
import models.Hopital;
import models.Residence;
import models.Utilisateur;
import models.itemList.ChauffeurItemList;
import models.itemList.CourseItemList;
import util.DateTime;
import util.TypeCourse;

public class ListeCourseControllerFXML extends ListeCourseController implements Initializable,ITabController {
	@FXML
    private Button btnImprimer;
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
    private ListView<CourseItemList> listViewCourses;
    @FXML
    private DatePicker selectDay;
    @FXML
    private ComboBox<Object> selectChauffeur;
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
    private ComboBox<Object> editChauffeur;
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
    private ComboBox<Object> editChauffeurSec;
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
    @FXML
    private Button btnAnnulerCourse;
    
    //private PrintCourseControllerFXML print;
	
	public ListeCourseControllerFXML(Utilisateur user, TabPane tabContainer) {
		super(user);
		Tab tab = null;
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getClassLoader().getResource("fxml/views/ListeCourse.fxml"));
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
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void selected() {
		editMode(btnSave.isVisible());
		//setListeCourse(getCourseList());
		setChauffeurList();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		ToggleGroup group = new ToggleGroup();
		selectFutur.setToggleGroup(group);
		selectFutur.setSelected(true);
		selectParJour.setToggleGroup(group);
		group.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
               if (group.getSelectedToggle() != null) {
            	   select();
               }
	       });
		selectChauffeur.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue != null) {
				select();
			}
			
		});
		selectDay.setValue(LocalDate.now());
		selectDay.valueProperty().addListener((ov, oldValue, newValue) -> {
			if (newValue != null) {
         	   select();
            }
        });
		
		//listViewCourses.setCellFactory(lc -> new CourseListCell());
		listViewCourses.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
	    	if(newValue!=null) {
	    		setSelectedCourse(newValue.getId());
	    		editMode(false);
	    	}
		    
		});
		listViewCourses.setOnMouseClicked(new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent arg0) {
				CourseItemList newValue = listViewCourses.getSelectionModel().getSelectedItem();
				if(newValue !=null && getSelectedCourse() == null) {
		    		setSelectedCourse(newValue.getId());
					editMode(false);
				}
			}

		});
		
		editCpDepart.textProperty().addListener((observable, oldValue, newValue) -> editCp(editCpDepart,newValue,oldValue));
		editCpRDV.textProperty().addListener((observable, oldValue, newValue) -> editCp(editCpRDV,newValue,oldValue));
		editCpRetour.textProperty().addListener((observable, oldValue, newValue) -> editCp(editCpRetour,newValue,oldValue));
		
		setChauffeurList();
		editType.getItems().addAll(TypeCourse.values());
		setResidence();
		editResidence.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue != null) {
				testResidence(newValue);
			}
		});
		editHopital.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue != null) {
				testHopital(newValue);
			}
		});
		setHopital();
		
		Integer hMin=0,hMax=23,mMin=0,mMax=59;
		setSpinner(editHeureDepart,hMin,hMax);
		setSpinner(editMinuteDepart,mMin,mMax);
		setSpinner(editHeureRDV,hMin,hMax);
		setSpinner(editMinuteRDV,mMin,mMax);
		setSpinner(editHeureRetour,hMin,hMax);
		setSpinner(editMinuteRetour,mMin,mMax);
		
		btnEdit.setOnAction((ActionEvent e) -> editerF());
		btnDel.setOnAction((ActionEvent e) -> deleteF());
		btnAnn.setOnAction((ActionEvent e) -> annulerF());
		btnSave.setOnAction((ActionEvent e) -> saveF());

		btnAnnulerCourse.setOnAction((ActionEvent e) -> annulationF());
		
		btnImprimer.setOnAction((ActionEvent e) -> imprimer());
		
		editMode(false);
	}
	
	private void imprimer() {
		if (isSelected()) {
			/*if (print == null) {
				print = new PrintCourseControllerFXML();
			}*/
			PrintCourseControllerFXML print = new PrintCourseControllerFXML();
			Node myPrint = print.getNode(getSelectedCourse());
			
			if (myPrint == null){
				System.err.println("No file is open!");
				return;
			}
			
			PrinterJob printAction = PrinterJob.createPrinterJob();
			if (printAction == null){
				System.err.println("Unable to access system print utilities");
				return;
			}
			PageLayout pageLayout = Printer.getDefaultPrinter().createPageLayout(Paper.A4, PageOrientation.PORTRAIT, Printer.MarginType.DEFAULT);
			printAction.getJobSettings().setPageLayout(pageLayout);
			boolean notCancelled = printAction.showPrintDialog(affichage.getScene().getWindow());
			if (notCancelled){
				//boolean success = printAction.printPage(pageLayout, myPrint);
				boolean success = printAction.printPage(myPrint);
				if (success)
					printAction.endJob();
				else
					 System.err.println("Print may have failed");
			}
		}
	}

	private void editCp(TextField editCp, String newValue, String oldValue) {
		if (!newValue.matches("\\d*")) {
			editCp.setText(newValue.replaceAll("[^\\d]", ""));
        } else if(newValue.length()>4) {
        	editCp.setText(oldValue);
        }
	}
	
	private void setSpinner(Spinner<Integer> spinner, Integer min, Integer max) {
		spinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(min, max, min));
		spinner.setEditable(true);
		TextField edit = spinner.getEditor();
		edit.textProperty().addListener((observable, oldValue, newValue) -> {
			if (!newValue.matches("\\d*")) {
				edit.setText(newValue.replaceAll("[^\\d]", ""));
	        } else if(newValue.length()>2) {
	        	edit.setText(oldValue);
	        }
		});
		spinner.focusedProperty().addListener((observable, oldValue, newValue) -> {
	        if (!newValue){
				String text = edit.getText();
				SpinnerValueFactory<Integer> valueFactory = spinner.getValueFactory();
				StringConverter<Integer> converter = valueFactory.getConverter();
				if (!text.isEmpty()) {
					try {
						Integer enterValue = converter.fromString(text);
						if (enterValue<min) {
							valueFactory.setValue(min);
						} else if (enterValue>max) {
							valueFactory.setValue(max);
						} else {
							valueFactory.setValue(enterValue);
						}
					} catch (Exception e) {
						valueFactory.setValue(min);
					}
				} else {
					valueFactory.setValue(min);
					edit.setText(min.toString());
				}
	        }
		    
		});
	}

	private void setChauffeurList() {
		Object obj = selectChauffeur.getSelectionModel().getSelectedItem();
		selectChauffeur.getItems().clear();
		editChauffeur.getItems().clear();
		editChauffeurSec.getItems().clear();
		
		List<ChauffeurItemList> chauffeurItemList = getChauffeurList();
		
		editChauffeur.getItems().add("");
		editChauffeur.getItems().addAll(chauffeurItemList);
		editChauffeurSec.getItems().add("");
		editChauffeurSec.getItems().addAll(chauffeurItemList);
		selectChauffeur.getItems().add("Tout");
		selectChauffeur.getItems().add("Sans Chauffeur");
		selectChauffeur.getItems().addAll(chauffeurItemList);
		
		if (obj != null) {
			selectChauffeur.getSelectionModel().select(obj);
		} else {
			selectChauffeur.getSelectionModel().select(0);
		}
	}
	
	private void select() {
		boolean all = false;
		Object obj = selectChauffeur.getSelectionModel().getSelectedItem();
		ChauffeurItemList chauf = null;
		if (obj instanceof ChauffeurItemList) {
			chauf = (ChauffeurItemList)obj;
		} else if (obj instanceof String) {
			all = ((String)obj).equals("Tout");
		}
		
		boolean day = selectParJour.isSelected();
		LocalDate date = selectDay.getValue();
		
		selectDay.setDisable(!day);
		super.select(all,chauf,day,date);
		setListeCourse(getCourseList());
	}

	private void testResidence(String value) {
		boolean nan = value.equals("");
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
		boolean nan = value.equals("");
		editAdresseRDV.setDisable(!nan);
	    editLocaliteRDV.setDisable(!nan);
	    editCpRDV.setDisable(!nan);
		if(!nan) {
			Hopital ho = getHopital(value);
			editAdresseRDV.setText(ho.getAdresse());
			editLocaliteRDV.setText(ho.getLocalite());
			editCpRDV.setText(ho.getCp());
		}
	}
	
	private void saveF() {
		try {
			Course course = getInfoCourse();
			timeCourse(course);
			Course.valdation(course);
			if(Message.comfirmation("Sauvegarder","")) {
				super.save(course);
				editMode(false);
				setListeCourse(getCourseList());
			}
		} catch (Exception e) {
			e.printStackTrace();
			Message.alert(e.getMessage());
		}
	}
	
	private void timeCourse(Course course) {
		if (course.getHeureDomicile().isAfter(course.getHeureRDV().minusMinutes(10))) {
			course.setHeureRDV(course.getHeureDomicile().plusMinutes(10));
		}
		if (course.getHeureRDV().isAfter(course.getHeureRetour().minusMinutes(10))) {
			course.setHeureRetour(course.getHeureRDV().plusMinutes(10));
		}
	}

	private Course getInfoCourse() {
		Course course = getSelectedCourse();
		if (course!=null) {
			Object obj = editChauffeur.getSelectionModel().getSelectedItem();
			if(obj instanceof ChauffeurItemList) {
				course.setChauffeur(getChauffeur(((ChauffeurItemList)obj).getId()), getUserName());
			} else {
				course.setChauffeur();
			}
			course.setDate(editDateCourse.getValue());
			course.setTypeCourse(editType.getSelectionModel().getSelectedItem());
			course.setMutuelle(editAttestation.isSelected());
			course.setHeureDomicile(DateTime.getLocalTime(editHeureDepart.getValueFactory().getValue(), editMinuteDepart.getValueFactory().getValue()));
			course.setAdresseDep(editAdresseDepart.getText().trim());
			course.setLocaliteDep(editLocaliteDepart.getText().trim());
			course.setCpDep(editCpDepart.getText().trim());
			course.setResidence(editResidence.getSelectionModel().getSelectedItem());
			course.setHeureRDV(DateTime.getLocalTime(editHeureRDV.getValueFactory().getValue(), editMinuteRDV.getValueFactory().getValue()));
			course.setAdresseDest(editAdresseRDV.getText().trim());
			course.setLocaliteDest(editLocaliteRDV.getText().trim());
			course.setCpDest(editCpRDV.getText().trim());
			course.setHopital(editHopital.getSelectionModel().getSelectedItem());
			course.setHeureRetour(DateTime.getLocalTime(editHeureRetour.getValueFactory().getValue(), editMinuteRetour.getValueFactory().getValue()));
			course.setAdresseRet(editAdresseRetour.getText().trim());
			course.setLocaliteRet(editLocaliteRetour.getText().trim());
			course.setCpRet(editCpRetour.getText().trim());
			course.setAttente(editAttente.isSelected());
			obj = editChauffeurSec.getSelectionModel().getSelectedItem();
			if(obj instanceof ChauffeurItemList) {
				course.setChauffeurSec(getChauffeur(((ChauffeurItemList)obj).getId()));
			} else {
				course.setChauffeurSec(null);
			}
			course.setNotes(editNote.getText().trim());
		}
		return course;
	}
	
	private void annulationF() {
		if (isSelected()) {
			String str = Message.getString("Raison de l'annulation",null);
			if (str!=null) {
				super.annulation(str);
			}
		}
	}

	private void deleteF() {
		if (isSelected() && Message.comfirmation("Supprimer","")) {
			super.delete();
			editMode(false);
			setListeCourse(getCourseList());
		}
	}

	private void annulerF() {
		super.annuler();
		editMode(false);
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
		setChauffeurList();
		setHopital();
		setResidence();
	}

	public void newCourse(Long id) {
		affEditCourse(getNewCourse(id));
		editMode(true);
	}
	


	public void selectCourse(Long idCourse) {
		setSelectedCourse(idCourse);
		affCourse(getSelectedCourse());
		editMode(false);
	}
	
	private void editMode(boolean edit) {
		affichage.setVisible(isSelected());
		edit &=isSelected();
		if (edit)
			setResidence();
		
		btnImprimer.setVisible(!edit);
		
		btnEdit.setVisible(!edit);
		btnDel.setVisible(!edit);
		btnSave.setVisible(edit);
		btnAnn.setVisible(edit);
		
		btnAnnulerCourse.setVisible(!edit);
		
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
			creatDate.setText(DateTime.toString(course.getDateCreation()));
		    creatHeure.setText(DateTime.toString(course.getHeureCreation()));
		    creatName.setText(course.getNameCreation());
		    
		    if(course.getDateAttribution()!=null) {
		    	attributionDate.setText(DateTime.toString(course.getDateAttribution()));
		    	attributionName.setText(course.getNameAttribution());
		    } else {
		    	attributionDate.setText("");
		    	attributionName.setText("");
		    }
		    
		    if(course.getDateAnnulation()!=null) {
		    	annulationDate.setText(DateTime.toString(course.getDateAnnulation()));
		    	annulationName.setText(course.getNameAttribution());
		    	annulationRaison.setText(course.getRaisonAnnulation());
		    } else {
		    	annulationDate.setText("");
		    	annulationName.setText("");
		    	annulationRaison.setText("");
		    }
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
		} else {
			affChaufeur.setText("");
		}
	    affDate.setText(DateTime.toString(course.getDate()));
	    affType.setText(course.getTypeCourse().toString());
	    affAttestation.setText(course.isMutuelle()?"oui":"non");
	    affHeureDepart.setText(DateTime.toString(course.getHeureDomicile()));
	    affAdresseDepart.setText(course.getAdresseDep());
	    affLocaliteDepart.setText(course.getLocaliteDep());
	    affCpDepart.setText(course.getCpDep());
	    affResidence.setText(course.getResidence());
	    affHeureRDV.setText(DateTime.toString(course.getHeureRDV()));
	    affAdresseRDV.setText(course.getAdresseDest());
	    affLocaliteRDV.setText(course.getLocaliteDest());
	    affCpRDV.setText(course.getCpDest());
	    affHopital.setText(course.getHopital());
	    affHeureRetour.setText(DateTime.toString(course.getHeureRetour()));
	    affAdresseRetour.setText(course.getAdresseRet());
	    affLocaliteRetour.setText(course.getLocaliteRet());
	    affCpRetour.setText(course.getCpRet());
	    if (course.getChauffeurSec()!=null) {
	    	affChaufeurSec.setText(course.getChauffeurSec().getFullName());
		}  else {
			affChaufeurSec.setText("");
		}
	    affAttente.setText(course.isAttente()?"oui":"non");
	    affNote.setText(course.getNotes());
	}
	
	private void affEditCourse(Course course) {
		if(course.getChauffeur()!=null) {
			System.out.println("Yousk2");
			editChauffeur.getSelectionModel().select(new ChauffeurItemList(course.getChauffeur()));
	    } else {
	    	editChauffeur.getSelectionModel().select(0);
	    }
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
	    if(course.getChauffeurSec()!=null) {
	    	editChauffeurSec.getSelectionModel().select(new ChauffeurItemList(course.getChauffeurSec()));
	    } else {
		    editChauffeurSec.getSelectionModel().select(0);
	    }
	    editNote.setText(course.getNotes());
	}
	
	private void affApplant(Appelant app) {
		if(app==null)
			app = new Appelant();
		codeApplant.setText(app.getId().toString());
	    nomCompletAppelant.setText(app.getFullName());
	    handicapAppelant.setText(app.getMobilite());
	    aideAppelant.setText(app.getAideParticuliere());
	}
	
	private void setResidence() {
		editResidence.getItems().clear();
		editResidence.getItems().addAll(getResidence());
	}
	
	private void setHopital() {
		editHopital.getItems().clear();
		editHopital.getItems().addAll(getHopital());
	}
	
	private void setListeCourse(List<CourseItemList> list) {
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
