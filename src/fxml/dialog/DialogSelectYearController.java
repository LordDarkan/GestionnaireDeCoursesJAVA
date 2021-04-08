package fxml.dialog;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import data.Mapper;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;
import models.Indisponibilite;

public class DialogSelectYearController implements Initializable {
	private boolean isResult;
	private GridPane content;
	@FXML
    private ComboBox<Integer> selectYear;
    
    public DialogSelectYearController() {
    	isResult = false;
    	FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("DialogSelectYear.fxml"));
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
		List<Integer> years = Mapper.getInstance().getListYears();
		
	}
    
    public Indisponibilite showAndWait() {
    	isResult = false;
    	Dialog<Integer> dialog = new Dialog<>();
		dialog.setTitle("Interval");
		dialog.setResizable(true);

		
		dialog.getDialogPane().setContent(content);
				
		ButtonType buttonTypeOk = new ButtonType("Okay", ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);

		dialog.setResultConverter(new Callback<ButtonType, Integer>() {
		    @Override
		    public Integer call(ButtonType b) {
		        if (b == buttonTypeOk) {
		            return s;
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
