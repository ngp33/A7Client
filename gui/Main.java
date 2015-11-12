package gui;

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
import world.World;

public class Main extends Application {
	
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
		Scene scene = new Scene(root);
		
		((Button) scene.lookup("#play")).setPadding(Insets.EMPTY);
		((Button) scene.lookup("#step")).setPadding(Insets.EMPTY);
		
		TitledPane inspector = (TitledPane) scene.lookup("#inspector");
		ScrollPane inspectorScroll = (ScrollPane) inspector.getContent();
		VBox inspectorBox = (VBox) inspectorScroll.getContent();
		TableView<MemTableRow> memTable = (TableView<MemTableRow>) inspectorBox.lookup("#memtable");
		
		ObservableList<TableColumn<MemTableRow, ?>> cols = memTable.getColumns();
		TableColumn<MemTableRow, String> indexCol = (TableColumn<MemTableRow, String>) cols.get(0);
		TableColumn<MemTableRow, String> valueCol = (TableColumn<MemTableRow, String>) cols.get(1);
		
		indexCol.setCellValueFactory(new PropertyValueFactory<MemTableRow, String>("index"));
		valueCol.setCellValueFactory(new PropertyValueFactory<MemTableRow, String>("value"));
		
		ObservableList<MemTableRow> defaultMemData = FXCollections.observableArrayList(
				new MemTableRow(0, null),
				new MemTableRow(1, null),
				new MemTableRow(2, null),
				new MemTableRow(3, null),
				new MemTableRow(4, null),
				new MemTableRow(5, null),
				new MemTableRow(6, null),
				new MemTableRow(7, null)
		); 
		
		memTable.setItems(defaultMemData);
		
		primaryStage.setScene(scene);
		
		Controller c = new Controller(primaryStage, new World());
		
		primaryStage.setResizable(false);
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
}
