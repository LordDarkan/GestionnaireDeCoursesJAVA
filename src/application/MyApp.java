package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class MyApp extends Application {

	@Override
	public void start(Stage stage) {
		stage.setTitle("Client");
		
		try{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MyApp.class.getResource("../views/Main.fxml"));
			AnchorPane pane = (AnchorPane)loader.load();
			Scene scene = new Scene(pane);
			stage.setResizable(false);
			stage.setScene(scene);
			stage.setOnCloseRequest(event -> System.exit(0));
			stage.show();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
