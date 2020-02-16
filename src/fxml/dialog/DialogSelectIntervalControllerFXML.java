package fxml.dialog;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;
import models.Indisponibilite;

public class DialogSelectIntervalControllerFXML implements Initializable {
	private boolean isResult;
	private GridPane content;
	@FXML
    private DatePicker dateStart;
    @FXML
    private DatePicker dateEnd;
    
    public DialogSelectIntervalControllerFXML() {
    	isResult = false;
    	FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("DialogSelectInterval.fxml"));
	    fxmlLoader.setController(this);
	    try
	    {
	    	content = (GridPane)fxmlLoader.load();
	    }
	    catch (IOException e)
	    {
	        throw new RuntimeException(e);
	    }
    }
    
    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		dateStart.setValue(LocalDate.now());
		dateStart.valueProperty().addListener((ov, oldValue, newValue) -> {
			if (newValue != null) {
         	   if (newValue.isAfter(dateEnd.getValue())) {
         		  dateEnd.setValue(newValue);
         	   }
            }
        });
		
		dateEnd.setValue(LocalDate.now());
		dateEnd.valueProperty().addListener((ov, oldValue, newValue) -> {
			if (newValue != null) {
         	   if (newValue.isBefore(dateStart.getValue())) {
         		  dateStart.setValue(newValue);
         	   }
            }
        });
	}
    
    public Indisponibilite showAndWait() {
    	isResult = false;
    	Dialog<Indisponibilite> dialog = new Dialog<>();
		dialog.setTitle("Interval");
		dialog.setResizable(true);

		
		dialog.getDialogPane().setContent(content);
				
		ButtonType buttonTypeOk = new ButtonType("Okay", ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);

		dialog.setResultConverter(new Callback<ButtonType, Indisponibilite>() {
		    @Override
		    public Indisponibilite call(ButtonType b) {
		        if (b == buttonTypeOk) {
		        	Indisponibilite i = new Indisponibilite();
		        	i.setDateStart(dateStart.getValue());
		        	i.setDateEnd(dateEnd.getValue());
		            return i;
		        }

		        return null;
		    }
		});
				
		Optional<Indisponibilite> result = dialog.showAndWait();
		if (result.isPresent()) {
			isResult = true;
			return result.get();
		} else {
			return null;
		}
    }

	public boolean isResult() {
		return isResult;
	}
}
