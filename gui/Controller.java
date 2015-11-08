package gui;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import world.World;

public class Controller {
	
	Scene view;
	World model;
	Timer timer;
	
	public Controller(Scene v, World m) {
		view = v;
		model = m;
		
		Button playAndPause = (Button) view.lookup("#play");
		
		playAndPause.setOnAction(new playPauseHandler());
		
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
				
				TextField speedInput = (TextField) view.lookup("#speed");
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
	
}
