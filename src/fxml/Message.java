package fxml;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;

public class Message {
	
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
	
	public static String justification(String msg) {
		
		TextInputDialog dialog = new TextInputDialog("test");
		dialog.setTitle("Validaton");
		dialog.setHeaderText("msg");
		Optional<String> result = dialog.showAndWait();
		String entered = null;
		if (result.isPresent()) {
		    entered = result.get();
		}
		return entered;
	}

	
}
