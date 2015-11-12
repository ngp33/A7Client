package gui;

import java.io.FileReader;
import java.util.Random;

import ast.ProgramImpl;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TitledPane;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import parse.Parser;
import parse.ParserImpl;
import world.Critter;
import world.World;

public class Main extends Application {
	
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
		Scene scene = new Scene(root);
		
		((Button) scene.lookup("#play")).setPadding(Insets.EMPTY);
		((Button) scene.lookup("#step")).setPadding(Insets.EMPTY);
		
		primaryStage.setScene(scene);
		
		World w = new World(7,5, "hi");
		int [] mem = new int [] {8,1,1,2,1000,0,0,0};
		Random r = new Random();
		Parser p = new ParserImpl();
		ProgramImpl pr = (ProgramImpl) p.parse(new FileReader("example-rules.txt"));
		Critter crit = new Critter(mem, r, pr, w);
		crit.direction = 0;
		w.replace(crit, w.getHex(2, 1));
		w.addCritter(crit);
		Controller c = new Controller(primaryStage, w);
		
		primaryStage.setResizable(false);
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
}
