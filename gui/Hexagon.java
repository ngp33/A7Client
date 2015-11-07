package gui;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class Hexagon extends Polygon {
	private double rtthr = 1.732050808;
	public Hexagon() {
		super();
	}
	public Hexagon(Double size) {
		super();
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
	}

}
