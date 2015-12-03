package gui;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import clientRequestHandler.ClientRequestHandler;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
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
	TableView<MemTableRow> memTable;
	Label latestRule;
	Button sourceButton;
	Label timeLabel;
	Label crittersLabel;
	
	//boolean settingUp;
	File critterFileToLoad;
	
	int sessionId;
	ClientRequestHandler requestHandler;
	
	
	public Controller(Stage v, World m) {
		view = v;
		model = m;
		continuousSimHandler = new PlayPauseHandler();
		stepHandler = new StepHandler();
		
		requestHandler = new ClientRequestHandler(this);
		
		Scene scene = v.getScene();
		
		Button playAndPause = (Button) scene.lookup("#play");
		playAndPause.setOnAction(continuousSimHandler);
		
		Button step = (Button) scene.lookup("#step");
		step.setOnAction(stepHandler);
		
		timeLabel = (Label) scene.lookup("#time");
		crittersLabel = (Label) scene.lookup("#alive");
		
		worldUpdater = new WorldObject((ScrollPane) scene.lookup("#arena"), model, this);
		
		
		MenuBar topBar = (MenuBar) scene.lookup("#topbar");
		Menu file = topBar.getMenus().get(0);
		Menu help = topBar.getMenus().get(1);
		
		/*help.getItems().get(0).setOnAction(new EventHandler<ActionEvent>() {
			Alert dialog = new Alert<AlertType.NONE>();
			dialog.
		});*/
		
		MenuItem newWorld = file.getItems().get(0);
		newWorld.setOnAction(new NewWorldHandler());
		
		MenuItem loadWorld = file.getItems().get(1);
		loadWorld.setOnAction(new LoadWorldHandler());
		
		MenuItem loadCritter = file.getItems().get(2);
		loadCritter.setOnAction(new LoadCritterHandler());
		
		Button zoomIn = (Button) scene.lookup("#zoomin");
		zoomIn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				worldUpdater.zoom(true);
			}
		});
		
		Button zoomOut = (Button) scene.lookup("#zoomout");
		zoomOut.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				worldUpdater.zoom(false);
			}
		});
		
		
		TitledPane inspector = (TitledPane) scene.lookup("#inspector");
		ScrollPane inspectorScroll = (ScrollPane) inspector.getContent();
		VBox inspectorBox = (VBox) inspectorScroll.getContent();
		memTable = (TableView<MemTableRow>) inspectorBox.lookup("#memtable");
		
		ObservableList<TableColumn<MemTableRow, ?>> cols = memTable.getColumns();
		TableColumn<MemTableRow, String> indexCol = (TableColumn<MemTableRow, String>) cols.get(0);
		TableColumn<MemTableRow, String> valueCol = (TableColumn<MemTableRow, String>) cols.get(1);
		
		indexCol.setCellValueFactory(new PropertyValueFactory<MemTableRow, String>("index"));
		valueCol.setCellValueFactory(new PropertyValueFactory<MemTableRow, String>("value"));
		
		critterMemData = FXCollections.observableArrayList(
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
		
		
		VBox programInfo = (VBox) ((TitledPane) inspectorBox.lookup("#programinfo")).getContent();
		latestRule = (Label) programInfo.lookup("#latestrule");
		sourceButton = (Button) programInfo.lookup("#viewsource");
		
		sourceButton.setOnAction(new SourceButtonHandler());
		
		
		scene.setOnKeyPressed(new KeyPressHandler());
		
		String permLevel = loginDialog();
		if (permLevel != "admin") {
			loadWorld.setDisable(true);
			if (permLevel == "read") {
				loadCritter.setDisable(true);
				playAndPause.setDisable(true);
				step.setDisable(true);
			}
		}
	}
	
	/**Brings up login dialog. Sets sessionId.
	 * 
	 * @return The permission level logged into.
	 */
	private String loginDialog() {
		ChoiceDialog<String> permissionDialog = new ChoiceDialog<String>(
				"Choose a permission level...",
				"read",
				"write",
				"admin");
		permissionDialog.initOwner(view);
		permissionDialog.setContentText("What permission level would you like to log in to?");
		permissionDialog.setHeaderText(null);
		permissionDialog.setGraphic(null);
		permissionDialog.setTitle("Login");
		Optional<String> methodInput = permissionDialog.showAndWait();
		
		if (! methodInput.isPresent()) {
			System.exit(0);
		}
		
		String toLogIn = methodInput.get();
		boolean notSuccessful = true;
		
		while (notSuccessful) {
			TextInputDialog numCrittersDialog = new TextInputDialog();
			numCrittersDialog.initOwner(view);
			numCrittersDialog.setContentText("Enter password:");
			numCrittersDialog.setHeaderText(null);
			numCrittersDialog.setGraphic(null);
			numCrittersDialog.setTitle("Login");
			Optional<String> passwordInput = numCrittersDialog.showAndWait();
			
			if (passwordInput.isPresent()) {
				int sessId = requestHandler.login(toLogIn, passwordInput.get());
				if (sessId != -1) {
					sessionId = sessId;
					notSuccessful = false;
				}
			}
		}
		
		return toLogIn;
	}
	
	public void onHexClicked(int row, int col) {
		//System.out.println(settingUp);
		
		Hex h = model.getHex(row, col);
		
		if (critterFileToLoad != null) {
			synchronized (this) {
				System.out.println("WAKE UP");
				
				try {
					Critter c = WorldCritterLoader.createCritter(critterFileToLoad.getPath(), model);
					model.replace(c, h);
					model.addCritter(c);
					worldUpdater.update(null, null);
					updateBottomLabels();
				} catch (NumberFormatException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				critterFileToLoad = null;
				notifyAll();
			}
			return;
		}
		
		if (h instanceof Critter) {
			selectedCritter = (Critter) h;
			for (MemTableRow r : critterMemData) {
				r.setCritter(selectedCritter);
			}
			updateInspector();
			
			return;
		}
		
		selectedCritter = null;
		for (MemTableRow r : critterMemData) {
			r.setCritter(selectedCritter);
		}
		updateInspector();
		
		return;
	}
	
	private void updateInspector() {
		Platform.runLater(new Runnable() {
            public void run() {
		
		memTable.refresh();
		
		if (selectedCritter == null) {
			latestRule.setText("Nothing selected.");
			sourceButton.setDisable(true);
			return;
		}
		sourceButton.setDisable(false);
		
		if (selectedCritter != model.getHex(selectedCritter.row, selectedCritter.col)) {
			selectedCritter = null;
			for (MemTableRow r : critterMemData) {
				r.setCritter(selectedCritter);
			}
			updateInspector();
			return;
		}
		
		if (selectedCritter.mostrecentrule == null) {
			latestRule.setText("This critter hasn't acted yet.");
			return;
		}
		latestRule.setText(selectedCritter.mostrecentrule.toString());
		
            }
      	});
	}
	
	private void updateBottomLabels() {
		Platform.runLater(new Runnable() {
            public void run() {
		
		timeLabel.setText("Time elapsed: " + model.getTime());
		crittersLabel.setText("Critters alive: " + model.getNumCritters());
		
            }
      	});
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
				updateInspector();
				updateBottomLabels();
			}
		}
		
	}
	
	class StepHandler implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent arg0) {
			synchronized(model) {
				model.advance();
				updateInspector();
				updateBottomLabels();
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
	    	updateBottomLabels();
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
			
			model = WorldCritterLoader.loadWorld(selectedFile.getPath());
	    	worldUpdater = new WorldObject((ScrollPane) view.getScene().lookup("#arena"), model, Controller.this);
	    	updateBottomLabels();
			
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
				
				WorldCritterLoader.loadCrittersOntoWorld(selectedFile.getPath(), numCritters, model);
				worldUpdater.update(null, null);
				updateBottomLabels();
				
				return;
			case 'P':
				(new Thread() {
					public void run() {
				
				synchronized (Controller.this) {
					critterFileToLoad = selectedFile;
					while (critterFileToLoad != null) {
						System.out.println("CHECKING");
						try {
							System.out.println("WAITING");
							Controller.this.wait();
						} catch (InterruptedException e) {}
					}
				}
				
					}
				}).start();
				
				return;
			}

		}
		
	}
	
	class SourceButtonHandler implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent event) {
			Dialog dialog = new Dialog<ButtonType>();
			dialog.initOwner(view);
			Label text = new Label(selectedCritter.genes.toString());
			ScrollPane textBox = new ScrollPane(text);
			DialogPane pane = new DialogPane();
			pane.setMaxHeight(500d);
			pane.setContent(textBox);
			pane.getButtonTypes().add(ButtonType.OK);
			dialog.setDialogPane(pane);
			dialog.setX(100d);
			dialog.setY(100d);
			dialog.setHeaderText("Critter Program");
			dialog.setTitle("Critter Program Viewer");
			dialog.showAndWait();
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
			else if (code.equals(KeyCode.I)) {
				worldUpdater.zoom(true);
			}
			else if (code.equals(KeyCode.O)) {
				worldUpdater.zoom(false);
			} else {
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
