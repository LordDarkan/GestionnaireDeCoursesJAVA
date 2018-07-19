package application;

import data.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import util.FileName;
import views.ControllerFXML.MainControllerFXML;

public class MyApp extends Application {

	@Override
	public void start(Stage stage) {
		System.out.println(FileName.getNameLogFile());
		stage.setTitle("Gestionnaire de Courses");
		try{
			IMapper mapper = new Mapper();
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MyApp.class.getClassLoader().getResource("views/Main.fxml"));
			loader.setController(new MainControllerFXML(mapper));
			AnchorPane pane = (AnchorPane)loader.load();
			Scene scene = new Scene(pane);
			stage.setResizable(false);
			stage.setScene(scene);
			stage.setOnCloseRequest(event -> System.exit(0));
			stage.setMaximized(true);
			stage.show();
		}catch(Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
		
	}

	public static void main(String[] args) {
		launch(args);
	}
}
