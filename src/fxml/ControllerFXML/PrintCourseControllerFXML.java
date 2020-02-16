package fxml.ControllerFXML;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import models.Appelant;
import models.Course;
import models.Utilisateur;
import util.DateTime;
import util.Trajet;

public class PrintCourseControllerFXML implements Initializable {
	@FXML
    private VBox affichage;
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
    private Label facturationAppelant;
    @FXML
    private Label affChaufeur;
    @FXML
    private Label affDate;
    @FXML
    private Label modeCourse;
    @FXML
    private Label affType;
    @FXML
    private Label affHeureDepart;
    @FXML
    private Label affAdresseDepart;
    @FXML
    private Label affLocaliteDepart;
    @FXML
    private Label affResidence;
    @FXML
    private Label affHeureRDV;
    @FXML
    private Label affAdresseRDV;
    @FXML
    private Label affLocaliteRDV;
    @FXML
    private Label affHopital;
    @FXML
    private Label affHeureRetour;
    @FXML
    private Label affAdresseRetour;
    @FXML
    private Label affLocaliteRetour;
    @FXML
    private Label affNote;
    @FXML
    private Label attributionName;
    @FXML
    private Label attributionDate;
    
    private Node content;
	
	public PrintCourseControllerFXML() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getClassLoader().getResource("fxml/views/PrintCourse.fxml"));
			loader.setController(this);
			content = (Node)loader.load();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Node getNode(Course course) {
		affCourse(course);
		return content;
	}
	
	private void affCourse(Course course) {
		Appelant app = course.getAppelant();
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
		codeApplant.setText(app.getId().toString());
	    nomCompletAppelant.setText(app.getFullName());
	    handicapAppelant.setText(app.getMobilite());
	    aideAppelant.setText(app.getAideParticuliere());
	    telAppelant.setText(app.getTel());
	    facturationAppelant.setText(app.getPayement());
		if (course.getChauffeur()!=null) {
			affChaufeur.setText(course.getChauffeur().getFullName());
		} else {
			affChaufeur.setText("");
		}
	    affDate.setText(DateTime.toDateJour(course.getDate()));
	    Trajet trajet = course.getTrajet();
	    modeCourse.setText(trajet.toString());
	    affType.setText(course.getTypeCourse().toString());
	    
	    affHeureDepart.setText(DateTime.toString(course.getHeureDomicile()));
	    affAdresseDepart.setText(course.getAdresseDep());
	    affLocaliteDepart.setText(course.getLocaliteDep());
	    
	    affHeureDepart.setVisible(trajet != Trajet.RETOUR);
	    affAdresseDepart.setVisible(trajet != Trajet.RETOUR);
	    affLocaliteDepart.setVisible(trajet != Trajet.RETOUR);
	    
	    
	    affResidence.setText(course.getResidence());
	    affHeureRDV.setText(DateTime.toString(course.getHeureRDV()));
	    affAdresseRDV.setText(course.getAdresseDest());
	    affLocaliteRDV.setText(course.getLocaliteDest());
	    affHopital.setText(course.getDestination());
	    
	    affHeureRetour.setText(DateTime.toString(course.getHeureRetour()));
	    affAdresseRetour.setText(course.getAdresseRet());
	    affLocaliteRetour.setText(course.getLocaliteRet());
	    
	    affHeureRetour.setVisible(trajet != Trajet.ALLER);
	    affAdresseRetour.setVisible(trajet != Trajet.ALLER);
	    affLocaliteRetour.setVisible(trajet != Trajet.ALLER);
	    
	    affNote.setText(course.getNotes());
	}
	

	public Node getNode(Appelant app,Utilisateur user) {
		creatDate.setText(DateTime.toString(LocalDate.now()));
	    creatHeure.setText(DateTime.toString(LocalTime.now()));
	    creatName.setText(user.getFullName());
		
		codeApplant.setText(app.getId().toString());
	    nomCompletAppelant.setText(app.getFullName());
	    handicapAppelant.setText(app.getMobilite());
	    aideAppelant.setText(app.getAideParticuliere());
	    telAppelant.setText(app.getTel());
	    facturationAppelant.setText(app.getPayement());
	    
	    affAdresseDepart.setText(app.getAdresse());
	    affLocaliteDepart.setText(app.getLocalite());
	    affResidence.setText(app.getResidence());
	    
		return content;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
	}
}
