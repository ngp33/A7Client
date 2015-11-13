package gui;

import java.io.File;
import java.util.Optional;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.TitledPane;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import world.Critter;
import world.Food;
import world.Hex;
import world.Rock;
import world.World;

public class Controller {
	
	Stage view;
	World model;
	Timer timer;
	WorldObject worldUpdater;
	PlayPauseHandler continuousSimHandler;
	StepHandler stepHandler;
	ObservableList<MemTableRow> critterMemData;
	Critter selectedCritter;
	
	boolean settingUp;
	
	
	public Controller(Stage v, World m) {
		view = v;
		model = m;
		continuousSimHandler = new PlayPauseHandler();
		stepHandler = new StepHandler();
		
		Scene scene = v.getScene();
		
		Button playAndPause = (Button) scene.lookup("#play");
		playAndPause.setOnAction(continuousSimHandler);
		
		Button step = (Button) scene.lookup("#step");
		step.setOnAction(stepHandler);
		
		worldUpdater = new WorldObject((ScrollPane) scene.lookup("#arena"), model, this);
		
		
		MenuBar topBar = (MenuBar) scene.lookup("#topbar");
		Menu file = topBar.getMenus().get(0);
		Menu help = topBar.getMenus().get(1);
		
		MenuItem newWorld = file.getItems().get(0);
		newWorld.setOnAction(new NewWorldHandler());
		
		MenuItem loadWorld = file.getItems().get(1);
		loadWorld.setOnAction(new LoadWorldHandler());
		
		MenuItem loadCritter = file.getItems().get(2);
		loadCritter.setOnAction(new LoadCritterHandler());
		
		
		TitledPane inspector = (TitledPane) scene.lookup("#inspector");
		ScrollPane inspectorScroll = (ScrollPane) inspector.getContent();
		VBox inspectorBox = (VBox) inspectorScroll.getContent();
		TableView<MemTableRow> memTable = (TableView<MemTableRow>) inspectorBox.lookup("#memtable");
		
		ObservableList<TableColumn<MemTableRow, ?>> cols = memTable.getColumns();
		TableColumn<MemTableRow, String> indexCol = (TableColumn<MemTableRow, String>) cols.get(0);
		TableColumn<MemTableRow, String> valueCol = (TableColumn<MemTableRow, String>) cols.get(1);
		
		indexCol.setCellValueFactory(new PropertyValueFactory<MemTableRow, String>("index"));
		valueCol.setCellValueFactory(new PropertyValueFactory<MemTableRow, String>("value"));
		
		ObservableList<MemTableRow> critterMemData = FXCollections.observableArrayList(
				new MemTableRow(0, null),
				new MemTableRow(1, null),
				new MemTableRow(2, null),
				new MemTableRow(3, null),
				new MemTableRow(4, null),
				new MemTableRow(5, null),
				new MemTableRow(6, null),
				new MemTableRow(7, null)
		); 
		
		memTable.setItems(critterMemData);
		
		
		scene.setOnKeyPressed(new KeyPressHandler());
		
	}
	
	public void onHexClicked(int row, int col) {
		//System.out.println(settingUp);
		
		Hex h = model.getHex(row, col);
		
		if (settingUp) {
			synchronized (this) {
				System.out.println("WAKE UP");
				settingUp = false;
				notifyAll();
			}
			return;
		}
		
		if (h instanceof Critter) {
			selectedCritter = (Critter) h;
			for (MemTableRow r : critterMemData) {
				r.setCritter(selectedCritter);
			}
			
			return;
		}
		
		selectedCritter = null;
		return;
	}

	class PlayPauseHandler implements EventHandler<ActionEvent> {
		
		boolean playing = false;
		Button step = (Button) view.getScene().lookup("#step");
		ImageView playPauseGraphic = (ImageView) ((Button) view.getScene().lookup("#play")).getGraphic();

		@Override
		public void handle(ActionEvent arg0) {
			if (playing) {
				playing = false;
				
				step.setDisable(false);
				playPauseGraphic.setImage(new Image("gui/Images/Play-01.png"));
				
				timer.cancel();
			} else {
				playing = true;
				
				step.setDisable(true);
				playPauseGraphic.setImage(new Image("gui/Images/Pause-01.png"));
				
				TextField speedInput = (TextField) view.getScene().lookup("#speed");
				String speedStr = speedInput.getText();
				double freq = Double.parseDouble(speedStr);
				long period = (long) (1000d/freq + .5d); //Round
				
				timer = new Timer(true);
				timer.schedule(new TimerStepHandler(), 0l, period);
			}
		}
		
	}
	
	class TimerStepHandler extends TimerTask {

		@Override
		public void run() {
			synchronized(model) {
				model.advance();
			}
			
		}
		
	}
	
	class StepHandler implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent arg0) {
			synchronized(model) {
				model.advance();
			}

		}
		
	}
	
	class NewWorldHandler implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent event) {
			model = new World();
	    	
	    	int rows = model.getNumRows();
	    	int cols = model.getNumColumns();
	    	
	    	Random rand = new Random();
	    	
	    	for (int i = 0; i < cols; i++) {
	    		for (int j = 0; j < rows; j++) {
	    			model.setHex(j, i, rand.nextFloat() < .15 ? new Rock() : new Food(0)); //15% chance of Rock
	    		}
	    	}
	    	
	    	worldUpdater = new WorldObject((ScrollPane) view.getScene().lookup("#arena"), model, Controller.this);
		}
		
	}
	
	class LoadWorldHandler implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent event) {
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Load world");
			fileChooser.getExtensionFilters().add(new ExtensionFilter("World Files", "*.txt"));
			File selectedFile = fileChooser.showOpenDialog(view);
			
			if (selectedFile == null) {
				return;
			}
			
			//Reluctant to copy and paste load code from Console until we get A5 back.
		}
		
	}
	
	class LoadCritterHandler implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent event) {
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Load critter");
			fileChooser.getExtensionFilters().add(new ExtensionFilter("World Files", "*.txt"));
			File selectedFile = fileChooser.showOpenDialog(view);
			
			if (selectedFile == null) {
				return;
			}
			
			ChoiceDialog<String> placeTypeDialog = new ChoiceDialog<String>(
					"Choose one...",
					"Randomly place a certain number of critters.",
					"Pick a hex to place the critter.");
			placeTypeDialog.initOwner(view);
			placeTypeDialog.setContentText("How do you want to load this critter?");
			placeTypeDialog.setHeaderText(null);
			placeTypeDialog.setGraphic(null);
			placeTypeDialog.setTitle("Load critter");
			Optional<String> methodInput = placeTypeDialog.showAndWait();
			
			if (!methodInput.isPresent()) {
				return;
			}
			
			(new Thread() {
				public void run() {
			
			String methodInputStr = methodInput.get();
			switch (methodInputStr.charAt(0)) {
			case 'C':
				return;
			case 'R':
				TextInputDialog numCrittersDialog = new TextInputDialog();
				numCrittersDialog.initOwner(view);
				numCrittersDialog.setContentText("How many critters?");
				numCrittersDialog.setHeaderText(null);
				numCrittersDialog.setGraphic(null);
				numCrittersDialog.setTitle("Load critter");
				Optional<String> numInput = numCrittersDialog.showAndWait();
				
				if (!numInput.isPresent()) {
					return;
				}
				
				String numInputStr = numInput.get();
				int numCritters = Integer.parseInt(numInputStr);
			case 'P':
				synchronized (this) {
					settingUp = true;
					while (settingUp) {
						System.out.println("CHECKING");
						try {
							System.out.println("WAITING");
							wait();
							System.out.println("OK");
						} catch (InterruptedException e) {}
					}
				}
			
				System.out.println("OKOKOK");
			}
			
			
			TextInputDialog numCrittersDialog = new TextInputDialog();
			numCrittersDialog.initOwner(view);
			numCrittersDialog.setContentText("How many critters?");
			numCrittersDialog.setHeaderText(null);
			numCrittersDialog.setGraphic(null);
			numCrittersDialog.setTitle("Load critter");
			Optional<String> numInput = numCrittersDialog.showAndWait();
			
			if (!numInput.isPresent()) {
				return;
			}
			
			String numInputStr = numInput.get();
			int numCritters = Integer.parseInt(numInputStr);
			
			//Reluctant to copy and paste load code from Console until we get A5 back.
			
				}
			}).start();
		}
		
	}
	
	class KeyPressHandler implements EventHandler<KeyEvent> {

		@Override
		public void handle(KeyEvent event) {

			KeyCode code = event.getCode();
			
			if (code.equals(KeyCode.P)) {
				continuousSimHandler.handle(null);
			} else if (code.equals(KeyCode.RIGHT)) {
				stepHandler.handle(null);
			}
			else if (code.equals(KeyCode.UP)) {
				worldUpdater.zoom(true);
			}
			else if (code.equals(KeyCode.DOWN)) {
				worldUpdater.zoom(false);
			}
			else {
				switch (code.getName()) {
				case "A":
					worldUpdater.move(-.1, 0);
					break;
				case "S":
					worldUpdater.move(0, .1);
					break;
				case "D":
					worldUpdater.move(.1, 0);
					break;
				case "W":
					worldUpdater.move(0, -.1);
					break;
				}
			}
		}
		
	}
	
}
