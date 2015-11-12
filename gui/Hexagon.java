package gui;


import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import world.World;

public class Hexagon extends Polygon {
	private double rtthr = 1.732050808;
	int row; //Row in the critter world
	int col; //col in the critter world
	double size;
	double [] position;
	

	
	/**Row and col are given in the critter system)*/
	public Hexagon(Double size, int row, int col, World w) {
		super();
		this.size = size;
		this.row = row;
		this.col = col;
		resize(size);
		this.setFill(Color.FORESTGREEN);
		this.setStroke(Color.BLACK);
		
		setOnMouseClicked(new EventHandler <Event> () {

			@Override
			public void handle(Event event) {
				ClickedHexControl.HandleHex(row, col);
			}
			
		});
		
	}
	
	public void resize(Double size) {
		this.getPoints().setAll(
				0.0, -size * rtthr / 2,
				size, -size * rtthr / 2,
				size + size / 2, 0.,
				size, size * rtthr / 2,
				0.0,  size * rtthr / 2,
				-size / 2, 0.);
	}


}
