package fxml.dialog;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;
import javafx.util.StringConverter;
import models.Indisponibilite;
import util.DateTime;
import util.TypeIndisponibilite;

public class DialogIndisponibiliteControllerFXML implements Initializable {
	private boolean isResult;
	private GridPane content;
	@FXML
	private RadioButton selectIndis;
	@FXML
	private RadioButton selectPasDer;
	@FXML
    private DatePicker dateStart;

    @FXML
    private Spinner<Integer> heureStart;

    @FXML
    private Spinner<Integer> minuteStart;

    @FXML
    private DatePicker dateEnd;

    @FXML
    private Spinner<Integer> heureEnd;

    @FXML
    private Spinner<Integer> minuteEnd;
    
    public DialogIndisponibiliteControllerFXML() {
    	isResult = false;
    	FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("DialogIndisponibilite.fxml"));
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
		Integer hMin=0,hMax=23,mMin=0,mMax=59;
		setSpinner(heureStart,hMin,hMax);
		setSpinner(minuteStart,mMin,mMax);
		setSpinner(heureEnd,hMin,hMax);
		setSpinner(minuteEnd,mMin,mMax);
		
		ToggleGroup group = new ToggleGroup();
		selectIndis.setToggleGroup(group);
		selectIndis.setSelected(true);
		selectPasDer.setToggleGroup(group);
		
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
    
    public Indisponibilite showAndWait() {
    	isResult = false;
    	Dialog<Indisponibilite> dialog = new Dialog<>();
		dialog.setTitle("Indisponibilité");
		//dialog.setHeaderText("This is a custom dialog. Enter info and \npress Okay (or click title bar 'X' for cancel).");
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
		        	i.setHeureStart(DateTime.getLocalTime(heureStart.getValueFactory().getValue(), minuteStart.getValueFactory().getValue()));
		        	i.setHeureEnd(DateTime.getLocalTime(heureEnd.getValueFactory().getValue(), minuteEnd.getValueFactory().getValue()));
		        	//i.setDescription(description);TODO
		        	if (selectPasDer.isSelected()) {
		        		i.setType(TypeIndisponibilite.PASDERANGER);
					}
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
