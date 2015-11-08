package gui;

import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import world.World;

public class Hexagon extends Polygon {
	private double rtthr = 1.732050808;
	private Inhabitant i;
	int row; //Row in the critter world
	int col; //col in the critter world
	double size;
	double [] position;
	
	public Hexagon() {
		super();
	}
	
	/**Row and col are given in the critter system)*/
	public Hexagon(Double size, int row, int col, World w) {
		super();
		this.size = size;
		this.row = row;
		this.col = col;
		resize(size);
		this.setFill(Color.FORESTGREEN);
		this.setStroke(Color.BLACK);
		
	}
	
	/*public void resize(Double size) {
		this.getPoints().setAll(
				0.0, 0.0,
				size, 0.0,
				size + size / 2, size * rtthr / 2,
				size, size * rtthr,
				0.0,  size * rtthr,
				-size / 2, size * rtthr / 2);
	}*/
	
	public void resize(Double size) {
		this.getPoints().setAll(
				0.0, -size * rtthr / 2,
				size, -size * rtthr / 2,
				size + size / 2, 0.,
				size, size * rtthr / 2,
				0.0,  size * rtthr / 2,
				-size / 2, 0.);
		//if (i != null) {
			//i.sizeupdate(size, position); TODO put this back
		//}
	}

	/**Invariant: size must already have a value, otherwise this might be weird*/
	/*public void getInhabitant(World w, AnchorPane a) { //TODO put this back
		int num = w.getNumRep(new int [] {row, col});
		if (i == null || i.erased()) {
			if (num != 0) {
				makenew (w, a);
			}
		}
	}
	
	private void makenew(World w, AnchorPane a) {
		int num = w.getNumRep(new int [] {row, col});
		if (num == w.ROCK_VALUE) {
			i = new RockGraphic(w, row, col, size, a, position);
		}
		else if (num < w.ROCK_VALUE) {
			i = new FoodGraphic(w, row, col, size, a, position);
		}
		else {
			i = new CritterGraphic(w, row, col, size, a, position);
		}
	}*/
}
