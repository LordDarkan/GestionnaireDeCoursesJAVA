package fxml;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;

public class Message {
	
	public static int choix(String titre, String msg,String btnOne,String btnTwo) {
		return show(AlertType.CONFIRMATION,"Confirmation",titre,msg,btnOne,btnTwo);
	}
	
	public static boolean comfirmation(String titre, String msg) {
		return show(AlertType.CONFIRMATION,"Confirmation",titre,msg);
	}
	
	public static boolean alert(String msg) {
		return show(AlertType.WARNING,"Erreur",msg,null);
	}
	
	public static boolean msg(String msg) {
		return show(AlertType.INFORMATION,"Info",msg,null);
	}
	
	private static boolean show(AlertType type,String titre,String head,String content) {
		Alert alert = new Alert(type);
		alert.setTitle(titre);
		alert.setHeaderText(head);
		alert.setContentText(content);
		Optional<ButtonType> result = alert.showAndWait();
		return result.get() == ButtonType.OK;
	}
	
	private static int show(AlertType type,String titre,String head,String content,String btnOne,String btnTwo) {
		Alert alert = new Alert(type);
		alert.setTitle(titre);
		alert.setHeaderText(head);
		alert.setContentText(content);
		
		ButtonType buttonTypeOne = new ButtonType(btnOne);
		ButtonType buttonTypeTwo = new ButtonType(btnTwo);
		ButtonType buttonTypeCancel = new ButtonType("Annuler", ButtonData.CANCEL_CLOSE);
		alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo, buttonTypeCancel);
		
		
		Optional<ButtonType> result = alert.showAndWait();
		int response = 0;
		if (result.get() == buttonTypeOne){
			response = 1;
		} else if (result.get() == buttonTypeTwo) {
			response = 2;
		}
		
		return response;
	}
	
	public static String getString(String msg,String txt) {
		
		TextInputDialog dialog = new TextInputDialog();
		dialog.setTitle("Encodage");
		dialog.setHeaderText(msg);
		dialog.setContentText(txt);
		Optional<String> result = dialog.showAndWait();
		String entered = null;
		if (result.isPresent()) {
		    entered = result.get();
		}
		return entered;
	}
}
