package application;

import data.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import util.UniqueInstanceTester;
import views.FXML.ControllerFXML.MainControllerFXML;

public class MyApp extends Application {
	
	@Override
	public void start(Stage stage) {
		if (UniqueInstanceTester.launch(null/*stage*/)) {
			stage.setTitle("Gestionnaire de Courses");
			stage.getIcons().add(new Image("file:img/short.png"));
			try{
				Mapper.setInstance(new MapperJPA());
				Mapper.getInstance().init();//TODO
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(MyApp.class.getClassLoader().getResource("views/FXML/Main.fxml"));
				loader.setController(new MainControllerFXML());
				GridPane pane = (GridPane)loader.load();
				Scene scene = new Scene(pane);
				stage.setScene(scene);
				stage.setOnCloseRequest(event -> System.exit(0));
				stage.setMaximized(true);
				stage.setResizable(true);//TODO
				stage.show();
			}catch(Exception e) {
				e.printStackTrace();
				System.exit(-1);
			}
			
		} else {
			System.exit(0);
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
