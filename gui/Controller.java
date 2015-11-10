package gui;

import java.io.File;
import java.util.Optional;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import world.Food;
import world.Rock;
import world.World;

public class Controller {
	
	Stage view;
	World model;
	Timer timer;
	
	public Controller(Stage v, World m) {
		view = v;
		model = m;
		
		Scene scene = v.getScene();
		
		Button playAndPause = (Button) scene.lookup("#play");
		playAndPause.setOnAction(new playPauseHandler());
		
		Button step = (Button) scene.lookup("#step");
		step.setOnAction(new StepHandler());
		
		
		MenuBar topBar = (MenuBar) scene.lookup("#topbar");
		Menu file = topBar.getMenus().get(0);
		Menu help = topBar.getMenus().get(1);
		
		MenuItem newWorld = file.getItems().get(0);
		newWorld.setOnAction(new NewWorldHandler());
		
		MenuItem loadWorld = file.getItems().get(1);
		loadWorld.setOnAction(new LoadWorldHandler());
		
		MenuItem loadCritter = file.getItems().get(2);
		loadCritter.setOnAction(new LoadCritterHandler());
		
	}

	class playPauseHandler implements EventHandler<ActionEvent> {
		
		boolean playing = false;

		@Override
		public void handle(ActionEvent arg0) {
			if (playing) {
				playing = false;
				
				timer.cancel();
			} else {
				playing = true;
				
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
			model.advance();
			
			//Update view from here or call a method in World?
		}
		
	}
	
	class StepHandler implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent arg0) {
			model.advance();
			
			//Update view from here or call a method in World? I think World would be better to avoid repeated code.
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
			
			TextInputDialog numCrittersDialog = new TextInputDialog();
			numCrittersDialog.initOwner(view);
			numCrittersDialog.setContentText("How many critters?");
			numCrittersDialog.setHeaderText(null);
			numCrittersDialog.setGraphic(null);
			numCrittersDialog.setTitle("Load critter");
			Optional<String> input = numCrittersDialog.showAndWait();
			
			if (!input.isPresent()) {
				return;
			}
			
			String inputStr = input.get();
			int numCritters = Integer.parseInt(inputStr);
			
			//Reluctant to copy and paste load code from Console until we get A5 back.
		}
		
	}
	
}
