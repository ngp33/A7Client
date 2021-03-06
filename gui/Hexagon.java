package gui;


import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import world.Critter;
import world.World;

public class Hexagon extends Polygon {
	private double rtthr = 1.732050808;
	int row; //Row in the critter world
	int col; //col in the critter world
	double size;
	double [] position;
	private Controller c;
	public ObjectLayer ol;
	private World w;
	

	
	/**Row and col are given in the critter system)*/
	public Hexagon(Double size, int row, int col, World w, Controller c) {
		super();
		this.size = size;
		this.row = row;
		this.col = col;
		resize(size);
		this.setFill(Color.FORESTGREEN);
		this.setStroke(Color.BLACK);
		this.c = c;
		this.w = w;
		
		setOnMouseClicked(new EventHandler <Event> () {

			@Override
			public void handle(Event event) {
				if (!ol.CritterEqual(row, col)) {
					ol.deselect();//Make sure this doesn't throw a null pointer exception.
				}
				
				if (w.getNumRep(new int [] {row, col}) > 0) {
					Critter crit = (Critter) w.getHex(row, col);
					ol.unique = crit;
					ol.updateDelete();
				}
				c.onHexClicked(row, col);
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
