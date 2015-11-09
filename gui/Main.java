package gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import world.World;

public class Main extends Application {
	
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
		Scene scene = new Scene(root);
		
		primaryStage.setScene(scene);
		
		Controller c = new Controller(primaryStage, new World());
		
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
}
