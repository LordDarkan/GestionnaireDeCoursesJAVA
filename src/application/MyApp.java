package application;

import data.*;
import fxml.ControllerFXML.MainControllerFXML;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import util.UniqueInstanceTester;

public class MyApp extends Application {

	@Override
	public void start(Stage stage) {
		if (UniqueInstanceTester.launch(null/* FIXME stage */)) {
			stage.setTitle("Gestionnaire de Courses");
			stage.getIcons().add(new Image(MyApp.class.getResourceAsStream("short.png")));
			try {
				Mapper.setInstance(new MapperJPA());
				Mapper.getInstance().init();
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(MyApp.class.getClassLoader().getResource("fxml/views/Main.fxml"));
				loader.setController(new MainControllerFXML());
				GridPane pane = (GridPane) loader.load();
				Scene scene = new Scene(pane);
				// scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
				stage.setScene(scene);
				stage.setOnCloseRequest(event -> close(stage));
				stage.setMaximized(true);
				stage.setResizable(true);// TODO
				stage.show();
			} catch (Exception e) {
				e.printStackTrace();
				System.exit(-1);
			}
		} else {
			fxml.Message.alert("Le programme est déjà en cours d'exécution");
			System.exit(0);
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

	public void close(Stage stage) {
		stage.close();

		// SaveManager.compress("D:/Users/Denis/OneDrive/perso/teleservice/test2");

		SaveManager.autoSave();

		System.exit(0);
	}
}
