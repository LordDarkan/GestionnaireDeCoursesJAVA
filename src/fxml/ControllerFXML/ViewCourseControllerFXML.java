package fxml.ControllerFXML;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import controllers.ViewCourseController;
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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import models.Appelant;
import models.Course;
import models.Utilisateur;
import util.DateTime;
import util.LoggerManager;
import util.Trajet;
import util.UserManager;

public class ViewCourseControllerFXML extends ViewCourseController implements Initializable, ITabController {
	private static final Logger LOG = LoggerManager.getLogger();
	@FXML
	private Button btnImprimer;
	@FXML
	private Button btnDupliquer;
	@FXML
	private VBox affichage;
	@FXML
	private Button btnEdit;
	@FXML
	private Button btnDel;
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
	private Label handicapAppelant;
	@FXML
	private Label aideAppelant;
	@FXML
	private Label telAppelant;
	@FXML
	private Label payementAppelant;
	@FXML
	private Label affModeCourse;
	@FXML
	private Label affChaufeur;
	@FXML
	private Label affDate;
	@FXML
	private Label affType;
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
	private Label affHeureRDV;
	@FXML
	private Label affAdresseRDV;
	@FXML
	private Label affLocaliteRDV;
	@FXML
	private Label affDestination;
	@FXML
	private Label affHeureRetour;
	@FXML
	private Label affAdresseRetour;
	@FXML
	private Label affLocaliteRetour;
	@FXML
	private Label affCpRetour;
	@FXML
	private Label affNote;
	@FXML
	private Label attributionName;
	@FXML
	private Label attributionDate;
	@FXML
	private GridPane gridDeparture;
	@FXML
	private GridPane gridReturn;
    
    private MainControllerFXML main;

	public ViewCourseControllerFXML(MainControllerFXML main,Utilisateur user, TabPane tabContainer) {
		super(user);
		this.main = main;
		Tab tab = null;
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getClassLoader().getResource("fxml/views/ViewCourse.fxml"));
			loader.setController(this);
			AnchorPane content;
			content = (AnchorPane) loader.load();
			tab = new Tab();
			tab.setClosable(false);
			tab.setText("ViewCourses");
			tab.setContent(content);
			tab.setOnSelectionChanged(new EventHandler<Event>() {
				@Override
				public void handle(Event event) {
					if (((Tab) event.getSource()).isSelected()) {
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
		affichage.setVisible(isSelected());
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		btnEdit.setOnAction((ActionEvent e) -> editerF());
		btnDel.setOnAction((ActionEvent e) -> deleteF());

		btnImprimer.setOnAction((ActionEvent e) -> imprimer());
		btnDupliquer.setOnAction((ActionEvent e) -> dupliquerF());
	}

	private void imprimer() {
		if (isSelected()) {
			/*
			 * if (print == null) { print = new PrintCourseControllerFXML(); }
			 */
			PrintCourseControllerFXML print = new PrintCourseControllerFXML();
			Node myPrint = print.getNode(getSelectedCourse());

			if (myPrint == null) {
				System.err.println("No file is open!");
				return;
			}

			PrinterJob printAction = PrinterJob.createPrinterJob();
			if (printAction == null) {
				LOG.log(Level.WARNING,
						"IMPRIMER FROM Course Unable to access system print utilities " + UserManager.getFullName());
				return;
			}
			PageLayout pageLayout = Printer.getDefaultPrinter().createPageLayout(Paper.A4, PageOrientation.PORTRAIT,
					Printer.MarginType.DEFAULT);
			printAction.getJobSettings().setPageLayout(pageLayout);
			boolean notCancelled = printAction.showPrintDialog(affichage.getScene().getWindow());
			if (notCancelled) {
				// boolean success = printAction.printPage(pageLayout, myPrint);
				boolean success = printAction.printPage(myPrint);
				if (success)
					printAction.endJob();
				else
					LOG.log(Level.WARNING, "IMPRIMER FROM Course " + UserManager.getFullName());
			}
		}
	}
	
	private void dupliquerF() {//TODO
		if (isSelected()) {
			main.GoToCourse(null, "dupliquer", getSelectedCourse().getId());
		}
	}
	
	private void deleteF() {
		if (isSelected()) {
			main.GoToCourse(null, "delete", getSelectedCourse().getId());
			super.blank();
		}
	}

	private void editerF() {
		if (isSelected()) {
			main.GoToCourse(null, "edit", getSelectedCourse().getId());
			super.blank();
		}
	}

	@Override
	public void logout() {
		clear();
	}

	@Override
	public void login(Utilisateur user) {
		newLog(user);
	}
	
	@Override
	public void action(String action) {
		throw new UnsupportedOperationException();
	}

	@Override
	public ITabController select(Long id) {
		setSelectedCourse(id);
		
		Course course = getSelectedCourse();
		
		creatDate.setText(DateTime.toString(course.getDateCreation()));
		creatHeure.setText(DateTime.toString(course.getHeureCreation()));
		creatName.setText(course.getNameCreation());
		
		if (course.getDateAttribution() != null) {
			attributionDate.setText(DateTime.toString(course.getDateAttribution()));
			attributionName.setText(course.getNameAttribution());
		} else {
			attributionDate.setText("");
			attributionName.setText("");
		}
		
		affApplant(course.getAppelant());
		affCourse(course);
		
		affichage.setVisible(isSelected());
		return this;
	}

	private void affCourse(Course course) {
		if (course.getChauffeur() != null) {
			affChaufeur.setText(course.getChauffeur().getFullName());
		} else {
			affChaufeur.setText("");
		}
		affDate.setText(DateTime.toDateJour(course.getDate()));
		affType.setText(course.getTypeCourse().toString());
		affModeCourse.setText(course.getTrajet().toString());

		affHeureDepart.setText(DateTime.toString(course.getHeureDomicile()));
		affAdresseDepart.setText(course.getAdresseDep());
		affLocaliteDepart.setText(course.getLocaliteDep());
		affCpDepart.setText(course.getCpDep());
		affResidence.setText(course.getResidence());
		affHeureRDV.setText(DateTime.toString(course.getHeureRDV()));
		affAdresseRDV.setText(course.getAdresseDest());
		affLocaliteRDV.setText(course.getLocaliteDest());
		affDestination.setText(course.getDestination());
		affHeureRetour.setText(DateTime.toString(course.getHeureRetour()));
		affAdresseRetour.setText(course.getAdresseRet());
		affLocaliteRetour.setText(course.getLocaliteRet());
		affCpRetour.setText(course.getCpRet());

		affNote.setText(course.getNotes());

		affAllerRetour(course.getTrajet());
	}

	private void affAllerRetour(Trajet trajet) {
		gridDeparture.setVisible(trajet != Trajet.RETOUR);
		gridReturn.setVisible(trajet != Trajet.ALLER);
	}

	private void affApplant(Appelant app) {
		if (app == null)
			app = new Appelant();
		codeApplant.setText(app.getId().toString());
		nomCompletAppelant.setText(app.getFullName());
		handicapAppelant.setText(app.getMobilite());
		aideAppelant.setText(app.getAideParticuliere());
		telAppelant.setText(app.getTel());
		payementAppelant.setText(app.getPayement());
	}
}
